//package cn.hi028.android.highcommunity.activity.fragment;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.don.tools.BpiHttpHandler;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.lidroid.xutils.util.LogUtils;
//
//import net.duohuo.dhroid.util.LogUtil;
//
//import cn.hi028.android.highcommunity.HighCommunityApplication;
//import cn.hi028.android.highcommunity.R;
//import cn.hi028.android.highcommunity.activity.MainActivity;
//import cn.hi028.android.highcommunity.activity.VallageAct;
//import cn.hi028.android.highcommunity.adapter.CommunityListAdapter2;
//import cn.hi028.android.highcommunity.bean.CommunityBean;
//import cn.hi028.android.highcommunity.bean.CommunityListBean;
//import cn.hi028.android.highcommunity.utils.Constacts;
//import cn.hi028.android.highcommunity.utils.HTTPHelper;
//import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
//import cn.hi028.android.highcommunity.view.LoadingView;
//import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;
///**
// * @功能：邻里界面<br>
// * @作者： 赵海<br>
// * @版本：1.0<br>
// * @时间：2015-12-08<br>
// */
//public class CommunityFrag_forBack extends Fragment {
//
//	public static final String FRAGMENTTAG = "CommunityFrag";
//	final String  Tag="------------CommunityFrag";
//	private View mFragmeView;
//	private int mCount = -1;
//	CommunityListAdapter2 mAdapter;
//	private PullToRefreshListView mListView;
//	private ImageView mChange;
//	RelativeLayout layoutContainer;
//	private LoadingView mLoadingView;
//	private TextView mNodata;
//	private View mProgress;
//	CommunityListBean mList = new CommunityListBean();
//	CommunityBean mBean = null;
//	public static boolean isNeedRefresh = true;
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		LogUtil.d(Tag+"onCreateView");
//		if (mFragmeView == null) {
//			initView();
//		}
//		ViewGroup parent = (ViewGroup) mFragmeView.getParent();
//		if (parent != null)
//			parent.removeView(mFragmeView);
//		initReceiver();
//		return mFragmeView;
//	}
//	@Override
//	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		LogUtil.d(Tag+"onActivityCreated");
//		super.onActivityCreated(savedInstanceState);
//		initDatas();
//	}
//	private void initReceiver() {
//		IntentFilter mFilter = new IntentFilter();
//		mFilter.addAction(Constacts.BROADCAST);
//		getActivity().registerReceiver(mReceiver, mFilter);
//	}
//	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			isNeedRefresh = true;
//			onResume();
//		}
//	};
//	private void initView() {
//		LogUtil.d(Tag+"---initView");
//		mFragmeView = LayoutInflater.from(getActivity()).inflate(
//				R.layout.frag_community_list, null);
//		findView();
////		mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
//		LogUtil.d(Tag+" initView   startLoading");
//
//		mAdapter = new CommunityListAdapter2((MainActivity) getActivity());
//		mListView.setAdapter(mAdapter);
//		mListView.setEmptyView(mNodata);
//		mListView.setMode(PullToRefreshBase.Mode.BOTH);
//		mProgress.setVisibility(View.VISIBLE);
//		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				RefreshData(0);
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				RefreshData(1);
//			}
//		});
//		mChange.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (HighCommunityUtils.isLogin()) {
//					VallageAct.toStartAct(getActivity(), 1, false);
//				} else {
//					VallageAct.toStartAct(getActivity(), 0, false);
//				}
//				isNeedRefresh = true;
//			}
//		});
//
//		//        RefreshData(1);
//	}
//
//	private void findView() {
//		mListView = (PullToRefreshListView) mFragmeView.findViewById(R.id.ptrlv_community_listview);
//		mChange = (ImageView) mFragmeView.findViewById(R.id.iv_community_change);
//		mNodata = (TextView) mFragmeView.findViewById(R.id.tv_community_Nodata);
//		mProgress = mFragmeView.findViewById(R.id.progress_Community);
//		layoutContainer=(RelativeLayout) mFragmeView.findViewById(R.id.layoutContainer);
//		mLoadingView=(LoadingView)mFragmeView.findViewById(R.id.loadingView);
//	}
//
//	OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {
//
//		@Override
//		public void onTryAgainClick() {
//			if(!isNoNetwork)
//				Toast.makeText(getActivity(), "------------OnLoadingViewListener", Toast.LENGTH_SHORT).show();
//		}
//	};
//	private void initDatas() {
//		mLoadingView.startLoading();
//		mCount = -1;
//		//        if (isNeedRefresh) {
//		String time = "";
//		if (mAdapter != null && mAdapter.getCount() > 0) {
//			time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
//		}
//
//		HTTPHelper.GetMessage(mIbpi, HighCommunityApplication.mUserInfo.getId(), HighCommunityApplication.mUserInfo.getV_id(), time);
//		LogUtil.d(Tag+"GetMessage2 ");
//	}
//	@Override
//	public void onResume() {
//		LogUtil.d(Tag+"---onResume");
//		initReceiver();
//		//		mCount = -1;
//		//		//        if (isNeedRefresh) {
//		//		String time = "";
//		//		if (mAdapter != null && mAdapter.getCount() > 0) {
//		//			time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
//		//		}
//		//
//		//		HTTPHelper.GetMessage(mIbpi, HighCommunityApplication.mUserInfo.getId(), HighCommunityApplication.mUserInfo.getV_id(), time);
//		//		LogUtil.d(Tag+"GetMessage2 ");
//
//		//        }
//		super.onResume();
//		registNetworkReceiver();
//		isNeedRefresh = false;
//	}
//
//	private void RefreshData(int type) {
//		LogUtil.d(Tag+"RefreshData ");
//		String time = "";
//		if (type == 0) {
//			mCount = 0;
//			if (mAdapter != null && mAdapter.getCount() > 0) {
//				time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
//			}
//		} else {
//			mCount = 1;
//			if (mAdapter != null && mAdapter.getCount() > 0) {
//				time = mAdapter.getItem(mAdapter.getCount() - 1).getCreate_time();//mList.getData().get(mList.getData().size() - 1).getCreate_time();
//			}
//		}
//		HTTPHelper.RefreshMessage(mIbpi, type, time, HighCommunityApplication.mUserInfo.getId(), HighCommunityApplication.mUserInfo.getV_id());//
//	}
//
//	/**
//	 * 只做back监听
//	 *
//	 * @return
//	 */
//	public boolean onKeyDown() {
//		if (mAdapter != null)
//			return mAdapter.onKeyDown();
//		return false;
//	}
//
//	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
//		@Override
//		public void onError(int id, String message) {
//			mProgress.setVisibility(View.GONE);
//			mListView.onRefreshComplete();
//			if (mCount == -1) {
//				mAdapter.ClearData();
//			}
//			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
//			//            if(!isNoNetwork){
//							mLoadingView.loadFailed();
//			//			}
//		}
//
//		@Override
//		public void onSuccess(Object message) {
//			LogUtil.d(Tag+"onSuccess");
//
//			LogUtil.d(Tag+"onSuccess---"+message.toString());
//			mProgress.setVisibility(View.GONE);
//			Log.e("TAG", message.toString());
//			if (null == message)
//				return;
//			mList = (CommunityListBean) message;
//			if (mCount == 0) {
//				mAdapter.AddNewData(mList.getData());
//			} else if (mCount == 1) {
//				mAdapter.RefreshData(mList.getData());
//			} else if (mCount == -1) {
//				mAdapter.SetData(mList.getData());
//			}
//			mLoadingView.loadSuccess();
//			LogUtil.d("-------------  initView   loadSuccess");
//			layoutContainer.setVisibility(View.VISIBLE);
//			LogUtil.d("-------------  initView   setVisibility");
//			mListView.onRefreshComplete();
//
//
//		}
//
//		@Override
//		public Object onResolve(String result) {
//			return HTTPHelper.ResolveMessage(result);
//		}
//
//		@Override
//		public void setAsyncTask(AsyncTask asyncTask) {
//
//		}
//
//		@Override
//		public void cancleAsyncTask() {
//			mProgress.setVisibility(View.GONE);
//			mListView.onRefreshComplete();
//		}
//	};
//
//
//
//
//
//
//	/****
//	 * 与网络状态相关
//	 */
//	private BroadcastReceiver receiver;
//	private void registNetworkReceiver(){
//		if(receiver == null){
//			receiver = new NetworkReceiver();
//			IntentFilter filter = new IntentFilter();
//			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//			getActivity().registerReceiver(receiver, filter );
//		}
//	}
//	private void unregistNetworkReceiver(){
//		getActivity().unregisterReceiver(receiver);
//	}
//	public class NetworkReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
//				ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//				NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//				if(networkInfo != null && networkInfo.isAvailable()){
//					int type = networkInfo.getType();
//					if(ConnectivityManager.TYPE_WIFI == type){
//
//					}else if(ConnectivityManager.TYPE_MOBILE == type){
//
//					}else if(ConnectivityManager.TYPE_ETHERNET == type){
//
//					}
//					//有网络
//					Toast.makeText(getActivity(), "有网络", Toast.LENGTH_SHORT).show();
//					LogUtils.d("有网络");
//					//					if(nextPage == 1){
//					//					  RefreshData(0);
//					//					}
//					isNoNetwork = false;
//				}else{
//					//没有网络
//					LogUtils.d("没有网络");
//					Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
//					//					if(nextPage == 1){
//										mLoadingView.noNetwork();
//					//					}
//					isNoNetwork = true;
//				}
//			}
//		}
//	}
//	private boolean isNoNetwork;
//
//	@Override
//	public void onAttach(Activity activity) {
//		// TODO Auto-generated method stub
//		LogUtil.d(Tag+"onAttach");
//		super.onAttach(activity);
//	}
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		LogUtil.d(Tag+"onCreate");
//	}
//	@Override
//	public void onStart() {
//		LogUtil.d(Tag+"onStart");
//		super.onStart();
//	}
//	@Override
//	public void onPause() {
//		LogUtil.d(Tag+"onPause");
//		//        getActivity().unregisterReceiver(mReceiver);
//		super.onPause();
//	}
//
//	@Override
//	public void onStop() {
//		LogUtil.d(Tag+"onStop");
//		super.onStop();
//	}
//
//	@Override
//	public void onDestroy() {
//		LogUtil.d(Tag+"onDestroy");
//		super.onDestroy();
//	}
//	@Override
//	public void onDestroyView() {
//		LogUtil.d(Tag+"onDestroyView");
//		super.onDestroyView();
//	}
//	@Override
//	public void onDetach() {
//		LogUtil.d(Tag+"onDetach");
//		super.onDetach();
//	}
//
//
//}
