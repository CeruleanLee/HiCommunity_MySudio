package net.duohuo.dhroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * activity 栈管理
 * 
 * @author duohuo-jinghao
 * 
 */
public class ActivityTack {
	static  final String Tag="ActivityTack--->";
	public List<Activity> activityList = new ArrayList<Activity>();
	public static ActivityTack tack = new ActivityTack();
	public static ActivityTack getInstanse() {
		return tack;
	}

	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	public boolean exitToast(Context context) {
		if (isExit == false) {
			isExit = true;
			Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			if (!hasTask) {
				tExit.schedule(task, 2000);
			}
		} else {
			exit(context);
		}
		return false;
	}

	private ActivityTack() {

	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}
	private static Activity scanForActivity(Context cont) {
		if (cont == null)
			return null;
		else if (cont instanceof Activity)
			return (Activity) cont;
		else if (cont instanceof ContextWrapper)
			return scanForActivity(((ContextWrapper) cont).getBaseContext());

		return null;
	}
	/**
	 * 完全退出
	 * 
	 * @param context
	 */
	public void exit(Context context) {
		Log.e(Tag, "栈 exit0");
		while (activityList.size() > 0) {
			Log.e(Tag, "栈 exit1");
			activityList.get(activityList.size() - 1).finish();
		}
		Log.e(Tag, "栈 exit2");
		System.exit(0);
	}

	public void clear() {
		while (activityList.size() > 0) {
			activityList.get(activityList.size() - 1).finish();
		}
	}

	/**
	 * 根据class name获取activity
	 * 
	 * @param name
	 * @return
	 */
	public Activity getActivityByClassName(String name) {
		for (Activity ac : activityList) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Activity getActivityByClass(Class cs) {
		for (Activity ac : activityList) {
			if (ac.getClass().equals(cs)) {
				return ac;
			}
		}
		return null;
	}

	/**
	 * 弹出activity
	 * 
	 * @param activity
	 */
	public void popActivity(Activity activity) {
		removeActivity(activity);
		activity.finish();
	}

	/**
	 * 弹出activity到
	 * 
	 * @param cs
	 */
	public void popUntilActivity(Class... cs) {
		List<Activity> list = new ArrayList<Activity>();
		for (int i = activityList.size() - 1; i >= 0; i--) {
			Activity ac = activityList.get(i);
			boolean isTop = false;
			for (int j = 0; j < cs.length; j++) {
				if (ac.getClass().equals(cs[j])) {
					isTop = true;
					break;
				}
			}
			if (!isTop) {
				list.add(ac);
			} else
				break;
		}
		for (Iterator<Activity> iterator = list.iterator(); iterator.hasNext();) {
			Activity activity = iterator.next();
			popActivity(activity);
		}
	}
}
