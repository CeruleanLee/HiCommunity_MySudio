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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.util.LogUtil;
import net.duohuo.dhroid.view.AutoScrollViewPager;
import net.duohuo.dhroid.view.CirclePageIndicator;

import cn.hi028.android.highcommunity.HighCommunityApplication;
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
import cn.hi028.android.highcommunity.utils.MHttpHolder;
import cn.hi028.android.highcommunity.view.GuideView;
import cn.hi028.android.highcommunity.view.LoadingView;
import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;

/**
 * @功能：服务<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */

public class ServiceFrag extends BaseFragment implements OnClickListener {
    public static final String Tag = "ServiceFrag--->";
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
    LinearLayout payment, tenement, notice, tenement2, guide, research,
            voluntary, one, crafts, become;
    ViewGroup tatalLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
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
        payment = (LinearLayout) view.findViewById(R.id.ll_service_payment);
        tenement = (LinearLayout) view.findViewById(R.id.ll_service_tenement);
        notice = (LinearLayout) view.findViewById(R.id.ll_service_notice);
        tenement2 = (LinearLayout) view.findViewById(R.id.ll_service_tenement2);
        guide = (LinearLayout) view.findViewById(R.id.ll_service_guide);
        research = (LinearLayout) view.findViewById(R.id.ll_service_research);
        voluntary = (LinearLayout) view.findViewById(R.id.ll_service_voluntary);
        one = (LinearLayout) view.findViewById(R.id.ll_service_notice_one);
        crafts = (LinearLayout) view.findViewById(R.id.ll_service_craftsman);
        become = (LinearLayout) view.findViewById(R.id.ll_service_become_craftsman);

        tatalLayout = (ViewGroup) view.findViewById(R.id.service_scrollView_LinearLayoutContainer);
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        vgcpi = (CirclePageIndicator) view.findViewById(R.id.home_cpi);
        mGridView = (PullToRefreshGridView) view.findViewById(R.id.ptrgv_service_thirdParty);
        mScrollview = (PullToRefreshScrollView) view.findViewById(R.id.service_scrollView_layout);
        mLoadingView = (LoadingView) view.findViewById(R.id.loadingView);

    }

    void initView() {
        mHttpUtils = MHttpHolder.getHttpUtils();
        mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
        //		Log.d(Tag," initView   startLoading");
        mScrollview.setMode(PullToRefreshBase.Mode.DISABLED);
        initPager();
        mIntent = new Intent(getActivity(),
                GeneratedClassUtils.get(ServiceAct.class));
        mAdapter = new ThirdServiceAdapter(getActivity());
        mGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        mGridView.setOnItemClickListener(mItemClickListener);
        initDatas();
    }

    boolean isFirstShowGuid = true;

    private void initDatas() {
        Log.d(Tag, "initDatas");
        mLoadingView.startLoading();
        HTTPHelper.GetThirdService(mIbpi);
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
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setInterval(3500);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(0);
    }

    /**
     * 监听 LoadingView
     */
    OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {

        @Override
        public void onTryAgainClick() {
        }
    };
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.d(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
            if (!isNoNetwork) {
                mLoadingView.loadFailed();
            }
        }

        @Override
        public void onSuccess(Object message) {
            mLoadingView.loadSuccess();
            mLoadingView.setVisibility(View.GONE);
            ThirdServiceBean mBean = (ThirdServiceBean) message;
            mAdapter.AddNewData(mBean.getServices());
            mGridView.setAdapter(mAdapter);
            pagerAdapter.setImageIdList(mBean.getBanners());
            HighCommunityUtils.GetInstantiation()
                    .setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
            tatalLayout.setVisibility(View.VISIBLE);
            viewPager.startAutoScroll();

        }

        @Override
        public Object onResolve(String result) {
            Log.e("renk", result);
            return HTTPHelper.ResolveThirdService(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i,
                                long l) {
            ServiceBean mServie = mAdapter.getItem(i);
            Log.d(Tag, "mServie.getUrl() :" + mServie.getUrl());

            BrowseActivity.toBrowseActivity(getActivity(), mServie.getName(), mServie.getUrl());
//            if (mServie.getName().equals("连锁") ) {
//                Log.d(Tag, "---连锁");
//                String url="https://www.jd.com/";
//                BrowseActivity.toBrowseActivity(getActivity(), mServie.getName(),url);
//            } else {
////                BrowseActivity.toBrowseActivity(getActivity(), mServie.getName(),
////                        mServie.getUrl());
//            }
        }
    };
    private HttpUtils mHttpUtils;

    /**
     * 单独展示生鲜界面
     */
    private void showShengXianMsg(final ServiceBean mServie) {
        Log.e(Tag, "单独展示生鲜界面");
        String url = mServie.getEdata().getApi_url();
        RequestParams params = new RequestParams();
//        params.addBodyParameter("uid", HighCommunityApplication.mUserInfo.getId()+"");
        params.addBodyParameter("id", mServie.getEdata().getId());
        params.addBodyParameter("logo", mServie.getEdata().getLogo());
        params.addBodyParameter("openid", mServie.getEdata().getOpenid());
        params.addBodyParameter("username", mServie.getEdata().getUsername());
        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.e(Tag, "http 访问失败的 arg1--->" + arg1.toString());
                Toast.makeText(getActivity(), arg1.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                String content = arg0.result;
                Log.e(Tag, "http 访问success的 content--->" + content);
                BrowseActivity.toBrowseActivity(getActivity(), mServie.getName(),
                        mServie.getUrl());
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Tag, "onPause");
        viewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.startAutoScroll();
        Log.d(Tag, "onResume");
    }

    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    initDatas();
                    isNoNetwork = false;
                } else {
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    mLoadingView.noNetwork();
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


    }

    GuideView guideView1;

    private void showGuid() {

        TextView tv = new TextView(getContext());
        tv.setText("新增业主大厅");
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);

        guideView1 = GuideView.Builder.newInstance(getContext()).setTargetView(tatalLayout).setCustomGuideView(tv)
                .setDirction(GuideView.Direction.RIGHT_TOP).setShape(GuideView.MyShape.RECTANGULAR)
                .setBgColor(getResources().getColor(R.color.shadow)).setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
                        guideView1.hide();
                    }
                }).build();
        guideView1.show();


    }
}
