/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;
import java.util.List;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ActiveAct;
import cn.hi028.android.highcommunity.adapter.ActivityAdapter;
import cn.hi028.android.highcommunity.bean.ActiveBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LoadingView;
import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;

/**
 * @功能：活动<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
public class ActFrag extends BaseFragment {
String Tag="~~~ActFrag~~~";
	public static final String FRAGMENTTAG = "ActFrag";
	private View mFragmeView;
	PullToRefreshListView mListView;
	View mProgress;
	TextView mNodata;
	TextView mCreate;

	List<ActiveBean> mlist;
	ActivityAdapter mAdapter;
	View layoutContainer;
	/** loadingView控制该容器显示与隐藏**/
	View layout_Container;
	private LoadingView mLoadingView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	LogUtil.d(Tag+"onCreateView");
		if (mFragmeView == null) {
			iniView();
		}
		ViewGroup parent = (ViewGroup) mFragmeView.getParent();
		if (parent != null)
			parent.removeView(mFragmeView);
		return mFragmeView;
	}
	/**
	 *初始化VIew
	 */
	 void iniView() {
		 LogUtil.d(Tag+"iniView");
		 mFragmeView = LayoutInflater.from(getActivity()).inflate(
				 R.layout.frag_activity, null);
		 layoutContainer=mFragmeView.findViewById(R.id.ll_act);
		 layout_Container=mFragmeView.findViewById(R.id.loadingview_Container);
		 mLoadingView=(LoadingView)mFragmeView.findViewById(R.id.loadingView);

		 mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
		
		 mListView = (PullToRefreshListView) mFragmeView.findViewById(R.id.ptrlv_activity_listView);
		 mProgress = mFragmeView.findViewById(R.id.progress_activity_notice);
		 mNodata = (TextView) mFragmeView.findViewById(R.id.tv_activity_Nodata);
		 mCreate = (TextView) mFragmeView.findViewById(R.id.tv_activity_create);
		 mProgress.setVisibility(View.VISIBLE);
		 mListView.setEmptyView(mNodata);
		 mAdapter = new ActivityAdapter(getActivity());
		 mListView.setAdapter(mAdapter);
		 mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			 @Override
			 public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				 new GetDataTask().execute();
				 
				 
//				 HTTPHelper.GetActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
			 }

			 @Override
			 public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

			 }
		 });
		 mCreate.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 create();
			 }
		 });
//		 Toast.makeText(getActivity(), "进入活动页", 0).show()
		 
		 initDatas();
	 }
	 private void initDatas() {
		 mLoadingView.startLoading();
		 HTTPHelper.GetActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
		
	}
	OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {

		 @Override
		 public void onTryAgainClick() {
			 if(!isNoNetwork)
				 HTTPHelper.GetActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
		 }
	 };

	 
	 @Override
	 public void onResume() {
		 LogUtil.d(Tag+"onResume");
		 super.onResume();
		 registNetworkReceiver();
	 }

	 /**
	  * 获取活动列表回掉handler
	  */
	 BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		 @Override
		 public void onError(int id, String message) {
			 mListView.onRefreshComplete();
			 mProgress.setVisibility(View.GONE);
			 if(!isNoNetwork){
				 mLoadingView.loadFailed();
			 }
		 }

		 @Override
		 public void onSuccess(Object message) {
			 mListView.onRefreshComplete();
			 mProgress.setVisibility(View.GONE);
			 if (message == null)
				 return;
			 mlist = (List<ActiveBean>) message;
			 mAdapter.AddNewData(mlist);
			 mLoadingView.loadSuccess();
			 LogUtil.d("-------------  initView   loadSuccess");
			 layout_Container.setVisibility(View.VISIBLE);
			 //			layoutContainer.setVisibility(View.VISIBLE);
			 LogUtil.d("-------------  initView   setVisibility");


		 }

		 @Override
		 public Object onResolve(String result) {
			 return HTTPHelper.ResolveActivitylist(result);
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

	 /**
	  * 创建活动
	  */
	 void create() {
		 if (HighCommunityUtils.GetInstantiation().isLogin(getActivity())) {
			 Intent ActCreate = new Intent(getActivity(), GeneratedClassUtils.get(ActiveAct.class));
			 ActCreate.putExtra(ActiveAct.ACTIVITYTAG, Constacts.ACTIVITY_CREATE);
			 startActivity(ActCreate);
		 }
	 }


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
					 //					if(nextPage == 1){
					 HTTPHelper.GetActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
					 //					}
					 isNoNetwork = false;
				 }else{
					 //没有网络
					 LogUtils.d("没有网络");
					 Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
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
				 HTTPHelper.GetActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
				 

				// Call onRefreshComplete when the list has been refreshed.
				//				mListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}


}
