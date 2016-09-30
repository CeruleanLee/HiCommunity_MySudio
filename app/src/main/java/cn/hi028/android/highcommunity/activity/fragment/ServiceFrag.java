package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.util.LogUtil;
import net.duohuo.dhroid.view.AutoScrollViewPager;
import net.duohuo.dhroid.view.CirclePageIndicator;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceAct;
import cn.hi028.android.highcommunity.activity.Service_AutonomousActivity;
import cn.hi028.android.highcommunity.activity.Service_ManageGuideActivity;
import cn.hi028.android.highcommunity.activity.Service_SurveyWorldActivity;
import cn.hi028.android.highcommunity.activity.Service_VoluntaryActivity;
import cn.hi028.android.highcommunity.adapter.ImagePagerAdapter;
import cn.hi028.android.highcommunity.adapter.ThirdServiceAdapter;
import cn.hi028.android.highcommunity.bean.ServiceBean;
import cn.hi028.android.highcommunity.bean.ThirdServiceBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LoadingView;
import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;

/**
 * @功能：服务<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */

public class ServiceFrag extends BaseFragment implements OnClickListener {
	public static final String Tag = "~~~ServiceFrag~~~";
	public static final String FRAGMENTTAG = "ServiceFrag";
	public ImagePagerAdapter pagerAdapter;
	AutoScrollViewPager viewPager;
	CirclePageIndicator vgcpi;
	PullToRefreshGridView mGridView;
	PullToRefreshScrollView mScrollview;

	LoadingView mLoadingView;

	ThirdServiceAdapter mAdapter;
	Intent mIntent;
	//租房  公告
	LinearLayout  payment,tenement,notice,tenement2,guide,research,
	voluntary,one,crafts,become;
	ViewGroup tatalLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtil.d(Tag+"onCreateView");
		View view = inflater.inflate(R.layout.frag_service, null);
		findView(view);
		registerListener();

		if (mLoadingView == null) {
			Log.e("test", "11111");
		}
		initView();
		return view;
	}


	private void registerListener() {
		payment.setOnClickListener(this);
		tenement.setOnClickListener(this);
		notice.setOnClickListener(this);
		tenement2.setOnClickListener(this);
		guide.setOnClickListener(this);
		research.setOnClickListener(this);
		voluntary.setOnClickListener(this);
		one.setOnClickListener(this);
		crafts.setOnClickListener(this);
		become.setOnClickListener(this);
	}


	private void findView(View view) {
		payment=(LinearLayout) view.findViewById(R.id.ll_service_payment);
		tenement=(LinearLayout) view.findViewById(R.id.ll_service_tenement);
		notice=(LinearLayout) view.findViewById(R.id.ll_service_notice);
		tenement2=(LinearLayout) view.findViewById(R.id.ll_service_tenement2);
		guide=(LinearLayout) view.findViewById(R.id.ll_service_guide);
		research=(LinearLayout) view.findViewById(R.id.ll_service_research);
		voluntary=(LinearLayout) view.findViewById(R.id.ll_service_voluntary);
		one=(LinearLayout) view.findViewById(R.id.ll_service_notice_one);
		crafts=(LinearLayout) view.findViewById(R.id.ll_service_craftsman);
		become=(LinearLayout) view.findViewById(R.id.ll_service_become_craftsman);

		tatalLayout=(ViewGroup) view.findViewById(R.id.service_scrollView_LinearLayoutContainer);
		viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
		vgcpi = (CirclePageIndicator) view.findViewById(R.id.home_cpi);
		mGridView = (PullToRefreshGridView) view.findViewById(R.id.ptrgv_service_thirdParty);
		mScrollview = (PullToRefreshScrollView) view.findViewById(R.id.service_scrollView_layout);
		mLoadingView = (LoadingView) view.findViewById(R.id.loadingView);

	}


	void initView() {
		LogUtil.d(Tag+"initView");
		mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
		//		LogUtil.d(Tag+" initView   startLoading");
		mScrollview.setMode(PullToRefreshBase.Mode.DISABLED);
		initPager();
		mIntent = new Intent(getActivity(),
				GeneratedClassUtils.get(ServiceAct.class));
		mAdapter = new ThirdServiceAdapter(getActivity());
		mGridView.setMode(PullToRefreshBase.Mode.DISABLED);
		mGridView.setOnItemClickListener(mItemClickListener);
		initDatas();
	}
	private void initDatas() {
		LogUtil.d(Tag+"initDatas");

		mLoadingView.startLoading();
		LogUtil.d(Tag+"---startLoading");
		HTTPHelper.GetThirdService(mIbpi);
		LogUtil.d(Tag+"---GetThirdService");
		viewPager.startAutoScroll();
	}

	/****
	 * 初始化头部viewpager
	 */
	private void initPager() {
		pagerAdapter = new ImagePagerAdapter(getActivity())
		.setInfiniteLoop(true);
		viewPager.setAdapter(pagerAdapter);
		vgcpi.setViewPager(viewPager);
		vgcpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) { }
			@Override
			public void onPageSelected(int i) { }
			@Override
			public void onPageScrollStateChanged(int i) { }
		});
		viewPager.setInterval(2000);
		viewPager.startAutoScroll();
		viewPager.setCurrentItem(0);
	}
	/**
	 * 监听 LoadingView 
	 */
	OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {

		@Override
		public void onTryAgainClick() {
			if(!isNoNetwork)
				initDatas();
			//				HTTPHelper.GetThirdService(mIbpi);
			//			Toast.makeText(getActivity(), "------------OnLoadingViewListener", 0).show();
		}
	};
	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			LogUtil.d(Tag+"---~~~onError");
			LogUtil.d(Tag+"-------------  initView   onError");
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
			if(!isNoNetwork){
				mLoadingView.loadFailed();
			}
		}

		@Override
		public void onSuccess(Object message) {
			mLoadingView.loadSuccess();
			mLoadingView.setVisibility(View.GONE);
			LogUtil.d(Tag+"---~~~initViewonSuccess");
//						if (null == message) return;
			LogUtil.d(Tag+"---~~~ initView   message:"+message);
			ThirdServiceBean mBean = (ThirdServiceBean) message;
			mAdapter.AddNewData(mBean.getServices());
			mGridView.setAdapter(mAdapter);
			pagerAdapter.setImageIdList(mBean.getBanners());
			HighCommunityUtils.GetInstantiation()
			.setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
			tatalLayout.setVisibility(View.VISIBLE);

		}
		@Override
		public Object onResolve(String result) {
			Log.e("renk", result);
			LogUtil.d(Tag+"---~~~iresult"+result);
			return HTTPHelper.ResolveThirdService(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {

		}
	};

	//	public void payment(View view) {
	//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_PAYMENT);
	//		startActivity(mIntent);
	//	}
	//
	//	void tenement(View view) {
	//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_TENEMENT);
	//		startActivity(mIntent);
	//	}
	//
	//	void notice(View view) {
	//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE);
	//		startActivity(mIntent);
	//	}
	//
	//	/**
	//	 * 此处处理社区
	//	 */
	//	void tenement2(View view) {
	//		Intent i = new Intent(getActivity(), Service_AutonomousActivity.class);
	//		startActivity(i);
	//	}
	//
	//	void guide(View view) {
	//		Intent i = new Intent(getActivity(), Service_ManageGuideActivity.class);
	//		startActivity(i);
	//
	//	}
	//
	//	void research(View view) {
	//		Intent i = new Intent(getActivity(), Service_SurveyWorldActivity.class);
	//		startActivity(i);
	//	}
	//
	//	void voluntary(View view) {
	//		Intent i = new Intent(getActivity(), Service_VoluntaryActivity.class);
	//		startActivity(i);
	//	}
	//
	//	void one(View view) {
	//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE_ONE);
	//		startActivity(mIntent);
	//	}
	//
	//	void crafts(View view) {
	//		mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_CARFSMAN);
	//		startActivity(mIntent);
	//	}
	//
	//	void become(View view) {
	//		if (HighCommunityUtils.GetInstantiation().isLogin(getActivity())) {
	//			mIntent.putExtra(ServiceAct.ACTIVITYTAG,
	//					Constacts.SERVICE_BECOME_CARFSMAN);
	//			startActivity(mIntent);
	//		}
	//	}

	AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i,
				long l) {
			ServiceBean mServie = mAdapter.getItem(i);
			LogUtil.d("--------------mServie.getUrl() :"+mServie.getUrl());
			BrowseActivity.toBrowseActivity(getActivity(), mServie.getName(),
					mServie.getUrl());
		}
	};

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.d(Tag+"onPause");
		viewPager.stopAutoScroll();
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d(Tag+"onResume");

		//		mLoadingView.startLoading();
		registNetworkReceiver();
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
										initDatas();
					//					HTTPHelper.GetThirdService(mIbpi);
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




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_service_payment:
			mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_PAYMENT);
			startActivity(mIntent);
			break;
		case R.id.ll_service_tenement:
			mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_TENEMENT);
			startActivity(mIntent);
			break;
		case R.id.ll_service_notice:
			mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE);
			startActivity(mIntent);
			break;
		case R.id.ll_service_tenement2:
			Intent i = new Intent(getActivity(), Service_AutonomousActivity.class);
			startActivity(i);
			break;
		case R.id.ll_service_guide:
			Intent i2 = new Intent(getActivity(), Service_ManageGuideActivity.class);
			startActivity(i2);
			break;
		case R.id.ll_service_research:
			Intent i3 = new Intent(getActivity(), Service_SurveyWorldActivity.class);
			startActivity(i3);
			break;
		case R.id.ll_service_voluntary:
			LogUtil.d("~~~~~~点击了志愿服务");
			Intent i4 = new Intent(getActivity(), Service_VoluntaryActivity.class);
			startActivity(i4);
			break;
		case R.id.ll_service_craftsman:
			mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_CARFSMAN);
			startActivity(mIntent);
			break;
		case R.id.ll_service_become_craftsman:
			if (HighCommunityUtils.GetInstantiation().isLogin(getActivity())) {
				mIntent.putExtra(ServiceAct.ACTIVITYTAG,
						Constacts.SERVICE_BECOME_CARFSMAN);
				startActivity(mIntent);
			}
			break;
		case R.id.ll_service_notice_one:
			if (HighCommunityUtils.GetInstantiation().isLogin(getActivity())) {
				mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE_ONE);
				startActivity(mIntent);
			}
			break;
		}

		//		
		//	one.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				mIntent.putExtra(ServiceAct.ACTIVITYTAG, Constacts.SERVICE_NOTICE_ONE);
		//				startActivity(mIntent);
		//				
		//			}
		//		});


	}

}
