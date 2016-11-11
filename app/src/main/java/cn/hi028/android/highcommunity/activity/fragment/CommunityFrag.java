package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.util.LogUtil;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MainActivity;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.CommunityListAdapter2;
import cn.hi028.android.highcommunity.bean.CommunityBean;
import cn.hi028.android.highcommunity.bean.CommunityListBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LoadingView;
import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;
/**
 * @功能：邻里界面<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
public class CommunityFrag extends Fragment {

	public static final String FRAGMENTTAG = "CommunityFrag";
	final String  Tag="CommunityFrag--->";
	private int mCount = -1;
	private PullToRefreshListView mListView;
	private ImageView mChange;
	RelativeLayout layoutContainer;
	private LoadingView mLoadingView;
	private TextView mNodata;
	private View mProgress;
	CommunityListAdapter2 mAdapter;
	CommunityListBean mList = new CommunityListBean();
	CommunityBean mBean = null;
	public static boolean isNeedRefresh = true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(Tag,"onCreateView");
		//		if (mFragmeView == null) {
		//			initView();
		//		}
		View view = inflater.inflate(R.layout.frag_community_list, null);
		findView(view);

		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null)
			parent.removeView(view);
		initReceiver();
		initView();
		return view;
	}

	private void initReceiver() {
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(Constacts.BROADCAST);
		getActivity().registerReceiver(mReceiver, mFilter);
	}
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			isNeedRefresh = true;
			onResume();
		}
	};
	private void initView() {
		Log.e(Tag,"---initView");
		mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
		mAdapter = new CommunityListAdapter2((MainActivity) getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setEmptyView(mNodata);
		mListView.setMode(PullToRefreshBase.Mode.BOTH);
		mProgress.setVisibility(View.VISIBLE);
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				RefreshData(0);
//				mAdapter.notifyDataSetChanged();
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				RefreshData(1);
			}
		});
		mChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (HighCommunityUtils.isLogin()) {
					VallageAct.toStartAct(getActivity(), 1, false);
				} else {
					VallageAct.toStartAct(getActivity(), 0, false);
				}
				isGetInitMessage=true;

				isNeedRefresh = true;
			}
		});
		//		mListView.setLoadingDrawable(getResources().getDrawable(R.drawable.ic_refreshanim));
		//		mListView.setd
		//		mListView.setLayoutAnimation(new LayoutAnimationController(animation));
		initDatas();
	}

	private void findView(View view) {
		mListView = (PullToRefreshListView) view.findViewById(R.id.ptrlv_community_listview);
		mChange = (ImageView) view.findViewById(R.id.iv_community_change);
		mNodata = (TextView) view.findViewById(R.id.tv_community_Nodata);
		mProgress = view.findViewById(R.id.progress_Community);
		layoutContainer=(RelativeLayout) view.findViewById(R.id.layoutContainer);
		mLoadingView=(LoadingView)view.findViewById(R.id.loadingView);
	}

	OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {

		@Override
		public void onTryAgainClick() {
						if(!isNoNetwork)
							initDatas();
		}
	};
	private void initDatas() {
		mLoadingView.startLoading();
		mCount = -1;
		//        if (isNeedRefresh) {
		String time = "";
		if (mAdapter != null && mAdapter.getCount() > 0) {
			time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
		}

		HTTPHelper.GetMessage(mIbpi, HighCommunityApplication.mUserInfo.getId(), HighCommunityApplication.mUserInfo.getV_id(), time);
		Log.e(Tag,"GetMessage2 ");
	}
	int isNeedToClearData=-1;
	public static boolean isGetInitMessage;

	public boolean isGetInitMessage() {
		return isGetInitMessage;
	}

	public void setGetInitMessage(boolean getInitMessage) {
		isGetInitMessage = getInitMessage;
	}

	@Override
	public void onResume() {
		Log.e(Tag,"---onResume");
		//		initReceiver();
		//		mCount = -1;
		//		//        if (isNeedRefresh) {
		//		String time = "";
		//		if (mAdapter != null && mAdapter.getCount() > 0) {
		//			time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
		//		}
		//
		//		HTTPHelper.GetMessage(mIbpi, HighCommunityApplication.mUserInfo.getId(), HighCommunityApplication.mUserInfo.getV_id(), time);
		//		Log.e(Tag,"GetMessage2 ");

		//        }

		super.onResume();
		if (isGetInitMessage){
			mAdapter.ClearData();
			initDatas();
			isGetInitMessage=false;
		}
		RefreshData(0);
		registNetworkReceiver();
		isNeedRefresh = false;
	}

	@Override
	public void onDestroy() {
		Log.e(Tag,"---onDestroy");

		super.onDestroy();
		unregistNetworkReceiver();
	}

	private void RefreshData(int type) {
		Log.e(Tag,"RefreshData  mCount--- "+mCount);
		String time = "";
		if (type == 0) {
			mCount = 0;
			if (mAdapter != null && mAdapter.getCount() > 0) {
				time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
			}
		} else {
			mCount = 1;
			if (mAdapter != null && mAdapter.getCount() > 0) {
				time = mAdapter.getItem(mAdapter.getCount() - 1).getCreate_time();//mList.getData().get(mList.getData().size() - 1).getCreate_time();
			}
		}
		//刷新方式（0-下拉刷新，1-加载更多）
		HTTPHelper.RefreshMessage(mIbpi, type, time, HighCommunityApplication.mUserInfo.getId(), HighCommunityApplication.mUserInfo.getV_id());//
	}

	/**
	 * 只做back监听
	 *
	 * @return
	 */
	public boolean onKeyDown() {
		if (mAdapter != null)
			return mAdapter.onKeyDown();
		return false;
	}

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mProgress.setVisibility(View.GONE);
			mListView.onRefreshComplete();
			if (mCount == -1) {
				mAdapter.ClearData();
			}
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
			//            if(!isNoNetwork){
			//			mLoadingView.loadFailed();
			//			}
		}

		@Override
		public void onSuccess(Object message) {
			Log.e(Tag,"onSuccess---"+message.toString());
			mProgress.setVisibility(View.GONE);
			Log.e("TAG", message.toString());
			if (null == message)
				return;
			mList = (CommunityListBean) message;
			Log.e(Tag,"mCount:"+mCount+",数据长度："+mList.getData().size());
			if (mCount == 0) {
				mAdapter.AddNewData(mList.getData());
			} else if (mCount == 1) {
				mAdapter.RefreshData(mList.getData());
			} else if (mCount == -1) {
				mAdapter.SetData(mList.getData());
			}
//			mListView.setAdapter(mAdapter);
			mListView.onRefreshComplete();
			mLoadingView.loadSuccess();
			LogUtil.d("-------------  initView   loadSuccess");
			layoutContainer.setVisibility(View.VISIBLE);
			LogUtil.d("-------------  initView   setVisibility");

		}
		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveMessage(result);
		}
		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}
		@Override
		public void cancleAsyncTask() {
			mProgress.setVisibility(View.GONE);
			mListView.onRefreshComplete();
		}
	};
	/****
	 * 与网络状态相关
	 */
	private BroadcastReceiver receiver;
	private void registNetworkReceiver(){
		if(receiver == null){
			receiver = new NetworkReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			getActivity().registerReceiver(receiver, filter );
		}
	}
	private void unregistNetworkReceiver(){
		getActivity().unregisterReceiver(receiver);
	}
	public class NetworkReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
				ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
				NetworkInfo networkInfo = manager.getActiveNetworkInfo();
				if(networkInfo != null && networkInfo.isAvailable()){
					int type = networkInfo.getType(); 
					if(ConnectivityManager.TYPE_WIFI == type){

					}else if(ConnectivityManager.TYPE_MOBILE == type){

					}else if(ConnectivityManager.TYPE_ETHERNET == type){

					}
					//有网络
					//					Toast.makeText(getActivity(), "有网络", 0).show();
					LogUtils.d("有网络");
//					initDatas();
					//					if(nextPage == 1){
					//					  RefreshData(0);
					//					}
					isNoNetwork = false;
				}else{
					//没有网络
					LogUtils.d("没有网络");
//					Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
					//					if(nextPage == 1){
					mLoadingView.noNetwork();
					//					}
					isNoNetwork = true;
				}
			}
		}
	}
	private boolean isNoNetwork;
	private class GetDataTask extends AsyncTask<Void, Void,String > {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			RefreshData(0);

			// Call onRefreshComplete when the list has been refreshed.
			//				mListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}



//@Override
//protected Void doInBackground(Void... params) {
//	// Simulates a background job.
//	try {
//		Thread.sleep(4000);
//	} catch (InterruptedException e) {
//	}
////	return mStrings;
//	return null;
//}
//
//@Override
//protected void onPostExecute() {
////	mListItems.addFirst("Added after refresh...");
//	mAdapter.notifyDataSetChanged();
//
//	// Call onRefreshComplete when the list has been refreshed.
//	mListView.onRefreshComplete();
//
//	super.onPostExecute();
//}

