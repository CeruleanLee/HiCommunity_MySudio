package com.don.tools;

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Handler;
import android.os.Message;

public abstract class MyAsyncTask<Params, Progress, Result> {
	private static final String LOG_TAG = "MyAsyncTask";

	// 获取当前的cpu核心数
	private static final int CPU_COUNT = Runtime.getRuntime()
			.availableProcessors();
	// 线程池核心容量
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	// 线程池最大容量
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
	// 过剩的空闲线程的存活时间
	private static final int KEEP_ALIVE = 1;
	// ThreadFactory 线程工厂，通过工厂方法newThread来获取新线程
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		// 原子整数，可以在超高并发下正常工作
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, LOG_TAG+" #" + mCount.getAndIncrement());
		}
	};
	// 静态阻塞式队列，用来存放待执行的任务，初始容量：128个
	private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(
			128);

	/**
	 * 静态并发线程池，可以用来并行执行任务，尽管从3.0开始，AsyncTask默认是串行执行任务 但是我们仍然能构造出并行的AsyncTask
	 */
	public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
			sPoolWorkQueue, sThreadFactory);

	/**
	 * 静态串行任务执行器，其内部实现了串行控制， 循环的取出一个个任务交给上述的并发线程池去执行
	 */
	public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
	// 消息类型：发送结果
	private static final int MESSAGE_POST_RESULT = 0x1;
	// 消息类型：更新进度
	private static final int MESSAGE_POST_PROGRESS = 0x2;
	/**
	 * 静态Handler，用来发送上述两种通知，采用UI线程的Looper来处理消息 这就是为什么AsyncTask必须在UI线程调用，因为子线程
	 * 默认没有Looper无法创建下面的Handler，程序会直接Crash
	 */
	private static final InternalHandler sHandler = new InternalHandler();
	// 默认任务执行器，被赋值为串行任务执行器，就是它，AsyncTask变成串行的了
	private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;
	// 如下两个变量我们先不要深究，不影响我们对整体逻辑的理解
	private final WorkerRunnable<Params, Result> mWorker;
	private final FutureTask<Result> mFuture;
	// 任务的状态 默认为挂起，即等待执行，其类型标识为易变的（volatile）
	private volatile Status mStatus = Status.PENDING;
	// 原子布尔型，支持高并发访问，标识任务是否被取消
	private final AtomicBoolean mCancelled = new AtomicBoolean();
	// 原子布尔型，支持高并发访问，标识任务是否被执行过
	private final AtomicBoolean mTaskInvoked = new AtomicBoolean();

	/*
	 * 串行执行器的实现，我们要好好看看，它是怎么把并行转为串行的目前我们需要知道，asyncTask.execute(Params ...)实际上会调用
	 * SerialExecutor的execute方法，这一点后面再说明。也就是说：当你的asyncTask执行的时候，
	 * 首先你的task会被加入到任务队列，然后排队，一个个执行
	 */
	private static class SerialExecutor implements Executor {
		// 线性双向队列，用来存储所有的AsyncTask任务
		final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
		// 当前正在执行的AsyncTask任务
		Runnable mActive;

		public synchronized void execute(final Runnable r) {
			// 将新的AsyncTask任务加入到双向队列中
			mTasks.offer(new Runnable() {
				public void run() {
					try {
						// 执行AsyncTask任务
						r.run();
					} finally {
						// 当前AsyncTask任务执行完毕后，进行下一轮执行，如果还有未执行任务的话
						// 这一点很明显体现了AsyncTask是串行执行任务的，总是一个任务执行完毕才会执行下一个任务
						scheduleNext();
					}
				}
			});
			// 如果当前没有任务在执行，直接进入执行逻辑
			if (mActive == null) {
				scheduleNext();
			}
		}

		protected synchronized void scheduleNext() {
			// 从任务队列中取出队列头部的任务，如果有就交给并发线程池去执行
			if ((mActive = mTasks.poll()) != null) {
				THREAD_POOL_EXECUTOR.execute(mActive);
			}
		}
	}

	/**
	 * 任务的三种状态
	 */
	public enum Status {
		/**
		 * 任务等待执行
		 */
		PENDING,
		/**
		 * 任务正在执行
		 */
		RUNNING,
		/**
		 * 任务已经执行结束
		 */
		FINISHED,
	}

	/** 隐藏API：在UI线程中调用，用来初始化Handler */
	public static void init() {
		sHandler.getLooper();
	}

	/** 隐藏API：为AsyncTask设置默认执行器 */
	public static void setDefaultExecutor(Executor exec) {
		sDefaultExecutor = exec;
	}

	/**
	 * Creates a new asynchronous task. This constructor must be invoked on the
	 * UI thread.
	 */
	public MyAsyncTask() {
		mWorker = new WorkerRunnable<Params, Result>() {
			public Result call() throws Exception {
				mTaskInvoked.set(true);

				android.os.Process
						.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
				// noinspection unchecked
				return postResult(doInBackground(mParams));
			}
		};

		mFuture = new FutureTask<Result>(mWorker) {
			@Override
			protected void done() {
				try {
					postResultIfNotInvoked(get());
				} catch (InterruptedException e) {
					android.util.Log.w(LOG_TAG, e);
				} catch (ExecutionException e) {
					throw new RuntimeException(
							"An error occured while executing doInBackground()",
							e.getCause());
				} catch (CancellationException e) {
					postResultIfNotInvoked(null);
				}
			}
		};
	}

	private void postResultIfNotInvoked(Result result) {
		final boolean wasTaskInvoked = mTaskInvoked.get();
		if (!wasTaskInvoked) {
			postResult(result);
		}
	}

	// doInBackground执行完毕，发送消息
	private Result postResult(Result result) {
		@SuppressWarnings("unchecked")
		Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
				new AsyncTaskResult<Result>(this, result));
		message.sendToTarget();
		return result;
	}

	/**
	 * 返回任务的状态
	 */
	public final Status getStatus() {
		return mStatus;
	}

	/**
	 * 这个方法是我们必须要重写的，用来做后台计算 所在线程：后台线程
	 */
	protected abstract Result doInBackground(Params... params);

	/**
	 * 在doInBackground之前调用，用来做初始化工作 所在线程：UI线程
	 */
	protected void onPreExecute() {
	}

	/**
	 * 在doInBackground之后调用，用来接受后台计算结果更新UI 所在线程：UI线程
	 */
	protected void onPostExecute(Result result) {
	}

	/**
	 * Runs on the UI thread after {@link #publishProgress} is invoked. /**
	 * 在publishProgress之后调用，用来更新计算进度 所在线程：UI线程
	 */
	protected void onProgressUpdate(Progress... values) {
	}

	/**
	 * cancel被调用并且doInBackground执行结束，会调用onCancelled，表示任务被取消
	 * 这个时候onPostExecute不会再被调用，二者是互斥的，分别表示任务取消和任务执行完成 所在线程：UI线程
	 */
	@SuppressWarnings({ "UnusedParameters" })
	protected void onCancelled(Result result) {
		onCancelled();
	}

	protected void onCancelled() {
	}

	public final boolean isCancelled() {
		return mCancelled.get();
	}

	public final boolean cancel(boolean mayInterruptIfRunning) {
		mCancelled.set(true);
		return mFuture.cancel(mayInterruptIfRunning);
	}

	public final Result get() throws InterruptedException, ExecutionException {
		return mFuture.get();
	}

	public final Result get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return mFuture.get(timeout, unit);
	}

	/**
	 * 这个方法如何执行和系统版本有关，在AsyncTask的使用规则里已经说明，如果你真的想使用并行AsyncTask， 也是可以的，只要稍作修改
	 * 必须在UI线程调用此方法
	 */
	public  MyAsyncTask<Params, Progress, Result> execute(Params... params) {
		// 串行执行
		return executeOnExecutor(sDefaultExecutor, params);
		// 如果我们想并行执行，这样改就行了，当然这个方法我们没法改
		// return executeOnExecutor(THREAD_POOL_EXECUTOR, params);
	}

	/**
	 * 通过这个方法我们可以自定义AsyncTask的执行方式，串行or并行，甚至可以采用自己的Executor
	 * 为了实现并行，我们可以在外部这么用AsyncTask：
	 * asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Params...
	 * params); 必须在UI线程调用此方法
	 */
	public MyAsyncTask<Params, Progress, Result> executeOnExecutor(
			Executor exec, Params... params) {
		if (mStatus != Status.PENDING) {
			switch (mStatus) {
			case RUNNING:
				throw new IllegalStateException("Cannot execute task:"
						+ " the task is already running.");
			case FINISHED:
				throw new IllegalStateException("Cannot execute task:"
						+ " the task has already been executed "
						+ "(a task can be executed only once)");
			}
		}

		mStatus = Status.RUNNING;
		// 这里#onPreExecute会最先执行
		onPreExecute();

		mWorker.mParams = params;
		// 然后后台计算#doInBackground才真正开始
		exec.execute(mFuture);
		// 接着会有#onProgressUpdate被调用，最后是#onPostExecute

		return this;
	}

	/**
	 * 这是AsyncTask提供的一个静态方法，方便我们直接执行一个runnable
	 */
	public static void execute(Runnable runnable) {
		sDefaultExecutor.execute(runnable);
	}

	/**
	 * 打印后台计算进度，onProgressUpdate会被调用
	 */
	protected final void publishProgress(Progress... values) {
		if (!isCancelled()) {
			sHandler.obtainMessage(MESSAGE_POST_PROGRESS,
					new AsyncTaskResult<Progress>(this, values)).sendToTarget();
		}
	}

	// 任务结束的时候会进行判断，如果任务没有被取消，则onPostExecute会被调用
	private void finish(Result result) {
		if (isCancelled()) {
			onCancelled(result);
		} else {
			onPostExecute(result);
		}
		mStatus = Status.FINISHED;
	}

	// AsyncTask内部Handler，用来发送后台计算进度更新消息和计算完成消息
	private static class InternalHandler extends Handler {
		@SuppressWarnings({ "unchecked", "RawUseOfParameterizedType" })
		@Override
		public void handleMessage(Message msg) {
			AsyncTaskResult result = (AsyncTaskResult) msg.obj;
			switch (msg.what) {
			case MESSAGE_POST_RESULT:
				// There is only one result
				result.mTask.finish(result.mData[0]);
				break;
			case MESSAGE_POST_PROGRESS:
				result.mTask.onProgressUpdate(result.mData);
				break;
			}
		}
	}

	private static abstract class WorkerRunnable<Params, Result> implements
			Callable<Result> {
		Params[] mParams;
	}

	@SuppressWarnings({ "RawUseOfParameterizedType" })
	private static class AsyncTaskResult<Data> {
		 MyAsyncTask mTask;
		 Data[] mData;

		AsyncTaskResult(MyAsyncTask task, Data... data) {
			mTask = task;
			mData = data;
		}
	}
}
