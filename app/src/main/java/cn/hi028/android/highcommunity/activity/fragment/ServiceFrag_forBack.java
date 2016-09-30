package cn.hi028.android.highcommunity.activity.fragment;//package cn.hi028.android.highcommunity.activity.fragment;
//
//import net.duohuo.dhroid.activity.BaseFragment;
//import net.duohuo.dhroid.activity.BrowseActivity;
//import net.duohuo.dhroid.util.LogUtil;
//import net.duohuo.dhroid.view.AutoScrollViewPager;
//import net.duohuo.dhroid.view.CirclePageIndicator;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EFragment;
//import org.androidannotations.annotations.ViewById;
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
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//import cn.hi028.android.highcommunity.R;
//import cn.hi028.android.highcommunity.activity.ServiceAct;
//import cn.hi028.android.highcommunity.activity.Service_AutonomousActivity;
//import cn.hi028.android.highcommunity.activity.Service_ManageGuideActivity;
//import cn.hi028.android.highcommunity.activity.Service_SurveyWorldActivity;
//import cn.hi028.android.highcommunity.activity.Service_VoluntaryActivity;
//import cn.hi028.android.highcommunity.adapter.ImagePagerAdapter;
//import cn.hi028.android.highcommunity.adapter.ThirdServiceAdapter;
//import cn.hi028.android.highcommunity.bean.ServiceBean;
//import cn.hi028.android.highcommunity.bean.ThirdServiceBean;
//import cn.hi028.android.highcommunity.utils.Constacts;
//import cn.hi028.android.highcommunity.utils.HTTPHelper;
//import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
//import cn.hi028.android.highcommunity.view.LoadingView;
//import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;
//
//import com.don.tools.BpiHttpHandler;
//import com.don.tools.GeneratedClassUtils;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
//import com.lidroid.xutils.ViewUtils;
//import com.lidroid.xutils.util.LogUtils;
//import com.lidroid.xutils.view.annotation.ViewInject;
//
///**
// * @功能：服务<br>
// * @作者： 赵海<br>
// * @版本：1.0<br>
// * @时间：2015-12-08<br>
// */
//@EFragment(resName = "frag_service")
//public class ServiceFrag_forBack extends BaseFragment {
//	public static final String Tag = "~~~ServiceFrag~~~";
//	public static final String FRAGMENTTAG = "ServiceFrag";
//	public ImagePagerAdapter pagerAdapter;
//	@ViewById(R.id.view_pager)
//	AutoScrollViewPager viewPager;
//	@ViewById(R.id.home_cpi)
//	CirclePageIndicator vgcpi;
//	@ViewById(R.id.ptrgv_service_thirdParty)
//	com.handmark.pulltorefresh.library.PullToRefreshGridView mGridView;
//	@ViewById(R.id.service_scrollView_layout)
//	PullToRefreshScrollView mScrollview;
//
//	@ViewInject(R.id.loadingView)
//	private LoadingView mLoadingView;
//	ViewGroup layoputContainer;
//	ThirdServiceAdapter mAdapter;
//	Intent mIntent;
//	@ViewInject(R.id.loadingviewContainer)
//	View loadingviewContainer;
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		LogUtil.d(Tag+"onActivityCreated");
//		super.onActivityCreated(savedInstanceState);
//		mLoadingView.startLoading();
//		initDatas();
//	}
//	@AfterViews
//	void initView() {
//		LogUtil.d(Tag+"initView");
//		ViewUtils.inject(this, getView());
//		mLoadingView=(LoadingView) getActivity().findViewById(R.id.loadingView);
//		loadingviewContainer=getActivity().findViewById(R.id.loadingviewContainer);
//		//		mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
//		//		LogUtil.d(Tag+" initView   startLoading");
//		mScrollview.setMode(PullToRefreshBase.Mode.DISABLED);
//		initPager();
//		mIntent = new Intent(getActivity(),
//				GeneratedClassUtils.get(ServiceAct.class));
//		mAdapter = new ThirdServiceAdapter(getActivity());
//		mGridView.setMode(PullToRefreshBase.Mode.DISABLED);
//		mGridView.setOnItemClickListener(mItemClickListener);
//		layoputContainer=(ViewGroup) getActivity().findViewById(R.id.service_scrollView_LinearLayoutContainer);
//		mLoadingView.startLoading();
//	}
//	private void initDatas() {
//		LogUtil.d(Tag+"initDatas");
//
//		mLoadingView.startLoading();
//		LogUtil.d(Tag+"---startLoading");
//		HTTPHelper.GetThirdService(mIbpi);
//		LogUtil.d(Tag+"---GetThirdService");
//	}
//
//	/****
//	 * 初始化头部viewpager
//	 */
//	private void initPager() {
//		pagerAdapter = new ImagePagerAdapter(getActivity())
//		.setInfiniteLoop(true);
//		viewPager.setAdapter(pagerAdapter);
//		vgcpi.setViewPager(viewPager);
//		vgcpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//			@Override
//			public void onPageScrolled(int i, float v, int i1) { }
//			@Override
//			public void onPageSelected(int i) { }
//			@Override
//			public void onPageScrollStateChanged(int i) { }
//		});
//		viewPager.setInterval(2000);
//		viewPager.startAutoScroll();
//		viewPager.setCurrentItem(0);
//	}
//	/**
//	 * 监听 LoadingView 
//	 */
//	OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {
//
//		@Override
//		public void onTryAgainClick() {
//			if(!isNoNetwork)
//				HTTPHelper.GetThirdService(mIbpi);
//			Toast.makeText(getActivity(), "------------OnLoadingViewListener", 0).show();
//		}
//	};
//	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
//		@Override
//		public void onError(int id, String message) {
//			LogUtil.d("-------------  initView   onError");
//			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
//			if(!isNoNetwork){
//				mLoadingView.loadFailed();
//			}
//		}
//
//		@Override
//		public void onSuccess(Object message) {
//			LogUtil.d("-------------  initView   onSuccess");
//			if (null == message) return;
//			LogUtil.d("-------------  initView   message:"+message);
//			ThirdServiceBean mBean = (ThirdServiceBean) message;
//			mAdapter.AddNewData(mBean.getServices());
//			mGridView.setAdapter(mAdapter);
//			pagerAdapter.setImageIdList(mBean.getBanners());
//			HighCommunityUtils.GetInstantiation()
//			.setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
//			mLoadingView.loadSuccess();
//			loadingviewContainer.setVisibility(View.GONE);
//
//			LogUtil.d("-------------  initView   loadSuccess");
//			layoputContainer.setVisibility(View.VISIBLE);
//			LogUtil.d("-------------  initView   setVisibility");
//		}
//		@Override
//		public Object onResolve(String result) {
//			Log.e("renk", result);
//			return HTTPHelper.ResolveThirdService(result);
//		}
//
//		@Override
//		public void setAsyncTask(AsyncTask asyncTask) {
//
//		}
//
//		@Override
//		public void cancleAsyncTask() {
//
//		}
//	};
//
//	@Click(R.id.ll_service_payment)
//	public void payment() {
//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_PAYMENT);
//		startActivity(mIntent);
//	}
//
//	@Click(R.id.ll_service_tenement)
//	void tenement() {
//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_TENEMENT);
//		startActivity(mIntent);
//	}
//
//	// @Click(R.id.ll_service_repair)
//	// void repair() {
//	// mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_REPAIR);
//	// startActivity(mIntent);
//	// }
//
//	@Click(R.id.ll_service_notice)
//	void notice() {
//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE);
//		startActivity(mIntent);
//	}
//
//	/**
//	 * 此处处理社区
//	 */
//	@Click(R.id.ll_service_tenement2)
//	void tenement2() {
//		Intent i = new Intent(getActivity(), Service_AutonomousActivity.class);
//		startActivity(i);
//	}
//
//	@Click(R.id.ll_service_guide)
//	void guide() {
//		Intent i = new Intent(getActivity(), Service_ManageGuideActivity.class);
//		startActivity(i);
//
//	}
//
//	@Click(R.id.ll_service_research)
//	void research() {
//		Intent i = new Intent(getActivity(), Service_SurveyWorldActivity.class);
//		startActivity(i);
//	}
//
//	@Click(R.id.ll_service_voluntary)
//	void voluntary() {
//		Intent i = new Intent(getActivity(), Service_VoluntaryActivity.class);
//		startActivity(i);
//	}
//
//	@Click(R.id.ll_service_notice_one)
//	void one() {
//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE_ONE);
//		startActivity(mIntent);
//	}
//
//	@Click(R.id.ll_service_craftsman)
//	void crafts() {
//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_CARFSMAN);
//		startActivity(mIntent);
//	}
//
//	@Click(R.id.ll_service_become_craftsman)
//	void become() {
//		if (HighCommunityUtils.GetInstantiation().isLogin(getActivity())) {
//			mIntent.putExtra(ServiceAct.ACTIVITYTAG,
//					Constacts.SERVICE_BECOME_CARFSMAN);
//			startActivity(mIntent);
//		}
//	}
//
//	AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> adapterView, View view, int i,
//				long l) {
//			ServiceBean mServie = mAdapter.getItem(i);
//			LogUtil.d("--------------mServie.getUrl() :"+mServie.getUrl());
//			BrowseActivity.toBrowseActivity(getActivity(), mServie.getName(),
//					mServie.getUrl());
//		}
//	};
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		LogUtil.d(Tag+"onPause");
//		viewPager.stopAutoScroll();
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		LogUtil.d(Tag+"onResume");
//		viewPager.startAutoScroll();
//		//		mLoadingView.startLoading();
//		registNetworkReceiver();
//	}
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
//					Toast.makeText(getActivity(), "有网络", 0).show();
//					LogUtils.d("有网络");
//					//					if(nextPage == 1){
//					HTTPHelper.GetThirdService(mIbpi);
//					//					}
//					isNoNetwork = false;
//				}else{
//					//没有网络
//					LogUtils.d("没有网络");
//					Toast.makeText(getActivity(), "没有网络", 0).show();
//					//					if(nextPage == 1){
//					mLoadingView.noNetwork();
//					//					}
//					isNoNetwork = true;
//				}
//			}
//		}
//	}
//	private boolean isNoNetwork;
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		LogUtil.d(Tag+"onCreateView");
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		LogUtil.d(Tag+"onDestroy");
//	}
//	@Override
//	public void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		LogUtil.d(Tag+"onStart");
//
//	}
//	@Override
//	public void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		LogUtil.d(Tag+"onStop");
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//		// TODO Auto-generated method stub
//		super.onAttach(activity);
//		LogUtil.d(Tag+"onAttach");
//	}
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		LogUtil.d(Tag+"onCreate");
//
//	}
//
//}
