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
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.adapter.HuiSuppCommAdapter;
import cn.hi028.android.highcommunity.adapter.HuiSuppPicAdapter;
import cn.hi028.android.highcommunity.adapter.HuiSupportAdapter;
import cn.hi028.android.highcommunity.bean.HuiSupportBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.GridViewUtils;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LoadingView;
import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;
import cn.hi028.android.highcommunity.view.NoScroolGridView;
import cn.hi028.android.highcommunity.view.PagerListView;

/**
 * @功能：物业直供<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-15<br>
 */
public class HuiSupplyFrag extends BaseFragment implements ViewPager.OnPageChangeListener {

    private View mFragmeView;
    ViewPager viewPager;
    RelativeLayout pager_layout;
    TextView tv_name;
    TextView tv_price;
    TextView tv_sale_count;
    TextView tv_stock;
    TextView tv_info;
    TextView tv_comment;
    NoScroolGridView cg_pic;

    PagerListView clv_comment;
    Button btn_order, btn_pay;
    HuiSuppCommAdapter commentAdapter;
    HuiSuppPicAdapter picAdapter;
    View headView;
    HuiSupportAdapter viewPagerAdapter;
    int currentPositon = 0;
    PopupWindow waitPop;
    View layoutContainer;
    private LoadingView mLoadingView;
    private PopupWindow mWatingWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmeView == null) {
            initView();
        }
        ViewGroup parent = (ViewGroup) mFragmeView.getParent();

        if (parent != null)
            parent.removeView(mFragmeView);
        return mFragmeView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static final String Tag = "HuiSupplyFrag--->";
    private int childCount;

    void initView() {
        findView();
        registerListener();
        initHeadView();
        commentAdapter = new HuiSuppCommAdapter(this);
        picAdapter = new HuiSuppPicAdapter(this.getActivity());
        cg_pic.setAdapter(picAdapter);
        cg_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPagerAdapter.getData().get(currentPositon).setCurrentPicPosition(position);
                viewPagerAdapter.updateImage(currentPositon);
            }
        });
        clv_comment.setAdapter(commentAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        viewPagerAdapter = new HuiSupportAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.requestFocus();
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
        viewPager.setOnPageChangeListener(this);
        //        pager_layout.setOnTouchListener(new View.OnTouchListener() {
        //
        //                                            @Override
        //                                            public boolean onTouch(View v, MotionEvent event) {
        //                                                switch (event.getAction()) {
        //                                                    case MotionEvent.ACTION_DOWN:// 手指按下时
        //                                                        ((MainActivity) getActivity()).menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //                                                        break;
        //                                                    case MotionEvent.ACTION_MOVE:// 手指移动时
        //                                                        break;
        //                                                    case MotionEvent.ACTION_UP:// 手指离开时
        //                                                        ((MainActivity) getActivity()).menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //                                                        break;
        //                                                }
        //                                                return viewPager.dispatchTouchEvent(event);
        //                                            }
        //                                        }
        //        );

        initData();


    }

    private void registerListener() {
        mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
        btn_order.setOnClickListener(mClickListener);
        btn_pay.setOnClickListener(mClickListener);
    }

    private void findView() {
        mFragmeView = LayoutInflater.from(getActivity()).inflate(
                R.layout.frag_hui_support, null);

        layoutContainer = mFragmeView.findViewById(R.id.layoutContainer);
        clv_comment = (PagerListView) mFragmeView.findViewById(R.id.clv_comment);
        btn_order = (Button) mFragmeView.findViewById(R.id.btn_order);
        btn_pay = (Button) mFragmeView.findViewById(R.id.btn_pay);
        mLoadingView = (LoadingView) mFragmeView.findViewById(R.id.loadingView);
    }

    @Override
    public void onResume() {
        super.onResume();
        registNetworkReceiver();

    }

    /**
     * 监听 LoadingView 按钮的点击
     */
    OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {

        @Override
        public void onTryAgainClick() {
            if (!isNoNetwork)
                initData();
        }
    };

    private void initData() {
        mLoadingView.startLoading();
        HTTPHelper.GetHuiSupportList(mIbpi);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_order:
                    addCarGoods();
                    break;
                case R.id.btn_pay:
                    buyGoods();
                    break;
            }
        }
    };

    public void buyGoods() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(HuiLifeSecondAct.class));
            mIntent.putExtra(HuiLifeSecondAct.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_ORDER);
            mIntent.putExtra(HuiLifeSecondAct.INTENTTAG, 0);
            HuiLifeSuppBuyFrag.entyBean = viewPagerAdapter.getData().get(currentPositon);
            startActivity(mIntent);
        }
    }

    public void addCarGoods() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_order, Gravity.CENTER);
            HTTPHelper.addHuiSupportCar(mIbpiAddShopCar, viewPagerAdapter.getData().get(currentPositon).getGid() + "", HighCommunityApplication.mUserInfo.getId() + "");
        }
    }

    /**
     * 获取直供商品列表
     */
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (!isNoNetwork) {
                mLoadingView.loadFailed();
            }
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            List<HuiSupportBean> data = (List<HuiSupportBean>) message;
            viewPagerAdapter.getData().clear();
            viewPagerAdapter.setData(data);
            if (ListUtils.getSize(data) > currentPositon) {
                viewPager.setCurrentItem(currentPositon);
                updateData();
            }
            mLoadingView.loadSuccess();
            layoutContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiSupportList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
        }
    };

    /**
     * 加入购物车回调
     */
    BpiHttpHandler.IBpiHttpHandler mIbpiAddShopCar = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast("成功加入购物车", 0);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiSupportList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };
    private int picCount;
    TextView original_price;

    /**
     * 初始化直供评论上部所有组件
     */
    private void initHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_support, null);
        viewPager = (ViewPager) headView.findViewById(R.id.view_pager);
        pager_layout = (RelativeLayout) headView.findViewById(R.id.pager_layout);
        tv_name = (TextView) headView.findViewById(R.id.tv_name);
        tv_price = (TextView) headView.findViewById(R.id.tv_price);
        tv_sale_count = (TextView) headView.findViewById(R.id.tv_sale_count);
        tv_stock = (TextView) headView.findViewById(R.id.tv_stock);
        tv_info = (TextView) headView.findViewById(R.id.tv_info);
        tv_comment = (TextView) headView.findViewById(R.id.tv_comment);
        cg_pic = (NoScroolGridView) headView.findViewById(R.id.cg_pic);
        original_price = (TextView) headView.findViewById(R.id.tv_original_price);

        clv_comment.addHeaderView(headView);
    }

    @Override
    public void onPageSelected(int position) {
        currentPositon = position;
        updateData();
    }

    //    public int getCurrentPo() {
    //        int curr = currentPositon % ListUtils.getSize(viewPagerAdapter.getData());
    //        if (curr < 0) {
    //            curr = ListUtils.getSize(viewPagerAdapter.getData()) + curr;
    //        }
    //        return curr;
    //    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (pager_layout != null) {
            pager_layout.invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    private void updateData() {
        tv_comment.setText("评论" + viewPagerAdapter.getData().get(currentPositon).getCount());
        tv_info.setText(viewPagerAdapter.getData().get(currentPositon).getDescribe());
        tv_name.setText(viewPagerAdapter.getData().get(currentPositon).getGoods_name());
        if (viewPagerAdapter.getData().get(currentPositon).getOriginal_price() == 0) {
            original_price.setVisibility(View.GONE);
        } else if (viewPagerAdapter.getData().get(currentPositon).getOriginal_price() > viewPagerAdapter.getData().get(currentPositon).getPrice()) {
            original_price.setVisibility(View.VISIBLE);
            Spannable spanStrikethrough = new SpannableString("￥" + viewPagerAdapter.getData().get(currentPositon).getOriginal_price());
            StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
//			spanStrikethrough.setSpan(stSpan, 0, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            Log.e(Tag, "长度--->" + spanStrikethrough.length());
            try {
                spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } catch (Exception ex) {

            }
            original_price.setText(spanStrikethrough);
        } else {
            original_price.setVisibility(View.GONE);

        }


        tv_price.setText("￥" + viewPagerAdapter.getData().get(currentPositon).getPrice());
        tv_sale_count.setText("销量" + viewPagerAdapter.getData().get(currentPositon).getSales() + "笔");
        tv_stock.setText("库存" + viewPagerAdapter.getData().get(currentPositon).getNumber() + "件");
        int picCount = viewPagerAdapter.getData().get(currentPositon).getPic().size();
        picAdapter.setData(viewPagerAdapter.getData().get(currentPositon).getPic());
        GridViewUtils.updateGridViewLayoutParams(cg_pic, 7);

        //		int childCount = cg_pic.getAdapter().getCount();
        //		LayoutParams params=new LayoutParams(3*70, 70);
        //		cg_pic.setLayoutParams(params);
        commentAdapter.setData(viewPagerAdapter.getData().get(currentPositon).getGoods_comment());
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
                    //有网络
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    //					if(nextPage == 1){
                    initData();
                    //					}
                    isNoNetwork = false;
                } else {
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


}
