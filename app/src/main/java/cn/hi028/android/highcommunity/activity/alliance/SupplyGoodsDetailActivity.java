package cn.hi028.android.highcommunity.activity.alliance;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.don.tools.BpiUniveralImage;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.view.AutoScrollViewPager;
import net.duohuo.dhroid.view.CirclePageIndicator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.GoodImageDetailOrEvaluationActivity;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.ShowPayActivity;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.adapter.SupplGoodsDetailGridAdapter;
import cn.hi028.android.highcommunity.adapter.SupplyGoodsDetailCommentAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyCarlistBean;
import cn.hi028.android.highcommunity.bean.GoodsData;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.bean.SubmitOrderBean;
import cn.hi028.android.highcommunity.lisenter.PayPop2FragFace;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;
import cn.hi028.android.highcommunity.view.MyCustomViewPager;
import cn.hi028.android.highcommunity.view.MyGoodetailScrollView;
import cn.hi028.android.highcommunity.view.NoScrollListview;
import cn.hi028.android.highcommunity.view.NoScroolGridView;
import cn.hi028.android.highcommunity.view.ScrollWebView;
import cn.hi028.android.highcommunity.view.snap.McoyProductContentPage;
import cn.hi028.android.highcommunity.view.snap.McoyProductDetailInfoPage;
import cn.hi028.android.highcommunity.view.snap.McoySnapPageLayout;
import cn.hi028.android.highcommunity.view.snap.McoySnapPageLayout.PageSnapedListener;

/**
 * 新版直供商品详情
 *
 * @author Lee_yting
 */
public class SupplyGoodsDetailActivity extends BaseFragmentActivity implements
        OnClickListener, PayPop2FragFace {

    static String Tag = "SupplyGoodsDetailActivity";
    private static final int TAB_PICDETAIL = 0;
    public static final int TAB_COMMENTDETAIL = 1;
    int currentTab = 0;
    String good_id;
    int good_count;
    ImageView back;
    TextView name, price;
    ImageView headimg;
    TextView subimg, addimg, kucun, conttv, detail, goodname, guige, origin;
    //	TextView time,telephone;
    TextView guige_, origin_, edible_;
    Button goPay, addToCar;
    TextView caramount, mAllprice, telephone, time;
    View viewline1, viewline2, viewline3;
    ImageButton call;
    FrameLayout shopcar;
    RelativeLayout tuwenxiangqing;
    RelativeLayout goodevaluation;
    LinearLayout payrl;
    RadioGroup mRadioGroup;
    RadioButton mPicDetail, mCommentDetail;
    /**
     * 下半页的viewpager
     **/
    MyCustomViewPager mPager;
    ScrollView mScrollView2;
    private TextView edible, scrollText;


    onCounter mCounter;
    /**
     * 接口获取的data
     **/
    NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity goodsdata;
    ArrayList<Goods_info> goodslist;
    private String goods_price;
    private int goods_count;
    private String good_sales;
    private String storeId;
    private String storeName;
    private String telPhone = "";
    private Goods_info goods_info = null;
    private McoySnapPageLayout mcoySnapPageLayout = null;
    private McoyProductContentPage bottomPage = null;
    private McoyProductDetailInfoPage topPage = null;
    View top_view, bottom_View;
    //    ScrollWebView mWebview;
//    NoScrollListview mCommentListview;
    CheckBox toSeeMore;
    /**
     * 更多商品参数  动态添加
     **/
    LinearLayout moreDetailGroup;
    /**
     * 规格的容器
     **/
    RelativeLayout mStandardLayout;
    /**
     * 规格的RadioGroup
     **/
    RadioGroup mStandardRadiogroup;
    TextView tv_pic_nodata;
    TextView tv_noData;
    /**
     * 获取商品详情数据的商品id
     **/
    String id = -1 + "";
    String goodsId;
    String standardId = "";
    int width, height;

    Handler mHandler = new Handler();
    private PopupWindow mWatingWindow;
    /**
     * 购物车价格合计
     **/
    float mCarPriceSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(Tag, Tag + "——————————————————啦啦啦  进入详情3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail_activity_supply);
        mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);

        topPage = new McoyProductDetailInfoPage(SupplyGoodsDetailActivity.this,
                getLayoutInflater().inflate(
                        R.layout.page_top_supply, null));
        bottomPage = new McoyProductContentPage(SupplyGoodsDetailActivity.this,
                getLayoutInflater().inflate(
                        R.layout.page_bottom_supply, null));
        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);
        top_view = topPage.getRootView();
        bottom_View = bottomPage.getRootView();
        id = getIntent().getStringExtra("id");
        goodsId = id;


        mcoySnapPageLayout.setPageSnapListener(new PageSnapedListener() {
            @Override
            public void onSnapedCompleted(int derection) {
                if (mcoySnapPageLayout.getCurrentScreen() == 0) {
                    scrollText.setText("—— 继续拖动，查看图文详情 ——");
                } else if (mcoySnapPageLayout.getCurrentScreen() == 1) {
                    scrollText.setText("—— 继续拖动，查看商品信息 ——");
                }

            }
        });
//        mcoySnapPageLayout.setScrollToTopListener(new McoySnapPageLayout.ScrollToTopListener() {
//            @Override
//            public void onScrollToTop(boolean isScrollToTop) {
//
//            }
//        });
//        mWebview.setOnScrollChangeListener(new OnScrollChangeListener() {
//
//            @Override
//            public void onScrollChanged(int l, int t, int oldl, int oldt) {
//            }
//
//            @Override
//            public void onPageTop(int l, int t, int oldl, int oldt) {
//            }
//
//            @Override
//            public void onPageEnd(int l, int t, int oldl, int oldt) {
//            }
//        });
        init();
        registerListener();
    }

    private Handler popupHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity.this, back, Gravity.CENTER);

                    break;
            }
        }

    };

    private void initData() {
        popupHandler.sendEmptyMessageDelayed(0, 50);
//        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity.this, caramount, Gravity.CENTER);

        HTTPHelper.getGdCarList2(mGetCarIbpi);
    }

    BpiHttpHandler.IBpiHttpHandler mGetCarIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.d(Tag, "onSuccess");
            if (null == message)
                return;
            mCarPriceSum = 0.00f;
            List<NewSupplyCarlistBean.SupplyCarlistDataEntity> mlist = (List<NewSupplyCarlistBean.SupplyCarlistDataEntity>) message;
            for (int i = 0; i < mlist.size(); i++) {
                mCarPriceSum += mlist.get(i).getSum();
            }
            HTTPHelper.GetNewSupplyGoodsDetail(mIbpi, id);
//            setCarAmount();
//            adapter.setData(mlist);
//            mListView.onRefreshComplete();
        }

        @Override
        public Object onResolve(String result) {


            return HTTPHelper.ResolvGdCarList2(result);
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
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(SupplyGoodsDetailActivity.this);
            }
        }

    };

    private void getDatas() {
        Bundle bundle = getIntent().getExtras();
        try {
            good_id = bundle.getString("id");
            good_count = bundle.getInt("count");
            good_sales = bundle.getString("sales");
            goods_count = bundle.getInt("allcount");
            goods_price = bundle.getString("price");
            goodslist = bundle.getParcelableArrayList("list");
            storeId = bundle.getString("storeid");
            storeName = bundle.getString("storename");
            goods_info = bundle.getParcelable("good_info");
            Log.e("renk", good_id);
            Log.e("renk", goodslist.toString());
            Log.e("renk", good_id);
            Log.e("renk", good_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        initView();

        if (goods_count > 99) {
            caramount.setText("99");
        } else {
            caramount.setText(goods_count + "");
        }
        initData();

    }

    Bundle bundle = new Bundle();

    private void registerListener() {
        back.setOnClickListener(this);
        subimg.setOnClickListener(this);
        addimg.setOnClickListener(this);
        shopcar.setOnClickListener(this);
        goPay.setOnClickListener(this);
        addToCar.setOnClickListener(this);
        call.setOnClickListener(this);
        telephone.setOnClickListener(this);

//        mPicDetail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//
//                    mWebview.setVisibility(View.VISIBLE);
//                    mCommentListview.setVisibility(View.GONE);
//                    mCommentDetail.setChecked(false);
//                }
//            }
//        });
//        mCommentDetail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    mWebview.setVisibility(View.GONE);
//                    mCommentListview.setVisibility(View.VISIBLE);
//                    mPicDetail.setChecked(false);
//                }
//            }
//        });
        toSeeMore.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    moreDetailGroup.setVisibility(View.VISIBLE);
                    top_view.invalidate();
                    Log.e(Tag, "~~~ 准备滑动top_view.getHeight() " + top_view.getHeight() + "---height=" + height);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((ScrollView) top_view).fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    //					mcoySnapPageLayout.scrollTo(0,top_view.getBottom());
                    //					Log.e(Tag,"~~~ 准备滑动完成"+scrollText.getX()+"---scrollText.getY()="+scrollText.getY());

                } else {
//                    moreDetailGroup.setVisibility(View.GONE);
                }
            }
        });
        toSeeMoreLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toSeeMore.isChecked()) {
//                    moreDetailGroup.setVisibility(View.VISIBLE);
                    top_view.invalidate();
                    Log.e(Tag, "~~~ 准备滑动top_view.getHeight() " + top_view.getHeight() + "---height=" + height);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((ScrollView) top_view).fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    //					mcoySnapPageLayout.scrollTo(0,top_view.getBottom());
                    //					Log.e(Tag,"~~~ 准备滑动完成"+scrollText.getX()+"---scrollText.getY()="+scrollText.getY());

                } else {
//                    moreDetailGroup.setVisibility(View.GONE);
                }
            }
        });

    }

    RelativeLayout toSeeMoreLayout;
    AutoScrollViewPager mViewPager;
    CirclePageIndicator mIndicator;
    RelativeLayout mCommonTitleLayout;
    TextView mSaleCount;
    TextView mKucunCount, mKucunCount_Standard;
    TextView mOldprice;
    RelativeLayout mFlashTitleLayout;
    TextView mSaledTime;
    TextView mFlashName;
    TextView mFlashNowPrice;
    TextView mFlashOldPrice;
    TextView mFlashKucun;
    TextView mFlashprogressTV;
    ProgressBar mFlashProgressBar;
    TextView mHishequTV;
//    NoScroolGridView mRecommendGridView;

    private void initView() {
        caramount = (TextView) findViewById(R.id.ac_shop_count);
        back = (ImageView) findViewById(R.id.ac_good_title_go_back);
        payrl = (LinearLayout) findViewById(R.id.shop_deatil_bottom_pay_rl);
        mAllprice = (TextView) findViewById(R.id.ac_shop_car_price);
        goPay = (Button) findViewById(R.id.ac_shop_car_go_pay);
        addToCar = (Button) findViewById(R.id.ac_shop_car_addtocar);
        shopcar = (FrameLayout) findViewById(R.id.ac_shop_car_fl);
/*************************   top  ********************************/

        mViewPager = (AutoScrollViewPager) top_view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) top_view.findViewById(R.id.view_cpi);
        mCommonTitleLayout = (RelativeLayout) top_view.findViewById(R.id.goodsDetail_commonLayout);
        mSaleCount = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_saledCount);
        mKucunCount = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_count);
        mKucunCount_Standard = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_count2);
        mOldprice = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_oldprice);
        name = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_name);
        price = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_price);
        headimg = (ImageView) top_view.findViewById(R.id.ac_shop_goods_head_iv);
        subimg = (TextView) top_view.findViewById(R.id.ac_shop_goods_right_sub_iv);
        addimg = (TextView) top_view.findViewById(R.id.ac_shop_goods_right_add_iv);
        kucun = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_count);
        conttv = (TextView) top_view.findViewById(R.id.ac_shop_goods_list_right_counts);

        mFlashTitleLayout = (RelativeLayout) top_view.findViewById(R.id.goodsDetail_flashSaleLayout);
        mSaledTime = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_saledTime);
        mFlashName = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_nameflash);
        mFlashNowPrice = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_priceFlash);
        mFlashOldPrice = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_oldpriceFlash);
        mFlashKucun = (TextView) top_view.findViewById(R.id.goodsDetail_tv_kucun);
        mFlashprogressTV = (TextView) top_view.findViewById(R.id.goodsDetail_tv_progress);
        mFlashProgressBar = (ProgressBar) top_view.findViewById(R.id.goodsDetail_progressBar);
        moreDetailGroup = (LinearLayout) top_view.findViewById(R.id.moredetail_layout);
        mStandardLayout = (RelativeLayout) top_view.findViewById(R.id.layout_standard_container);
        mStandardRadiogroup = (RadioGroup) top_view.findViewById(R.id.standard_radiogroup);
        detail = (TextView) top_view.findViewById(R.id.ac_shop_detail_tv);
        telephone = (TextView) top_view.findViewById(R.id.shop_detail_service_telephone);
        time = (TextView) top_view.findViewById(R.id.ac_shop_detail_service_time);
        call = (ImageButton) top_view.findViewById(R.id.call);
        viewline1 = top_view.findViewById(R.id.view11);
        viewline2 = top_view.findViewById(R.id.view12);
        viewline3 = top_view.findViewById(R.id.view13);
        scrollText = (TextView) top_view.findViewById(R.id.scroll_Text);

//暂时无用的---
        goodname = (TextView) top_view.findViewById(R.id.shop_detail_ac_goods_name);
        guige = (TextView) top_view.findViewById(R.id.ac_shop_detail_speci_tv);
        origin = (TextView) top_view.findViewById(R.id.ac_shop_origin_tv);

        guige_ = (TextView) top_view.findViewById(R.id.ac_shop_good_speci);
        origin_ = (TextView) top_view.findViewById(R.id.ac_shop_origin);
        edible_ = (TextView) top_view.findViewById(R.id.ac_shop_edible);
        edible = (TextView) top_view.findViewById(R.id.ac_shop_edible_tv);
        toSeeMore = (CheckBox) top_view.findViewById(R.id.toSeeMore);

        toSeeMoreLayout = (RelativeLayout) top_view.findViewById(R.id.seemoreLayout);

//        moreDetailGroup.post(new Runnable() {
//            @Override
//            public void run() {
//                width = moreDetailGroup.getWidth();
//                height = moreDetailGroup.getHeight();
//                Log.e(Tag, "~~~width=" + width + ",height=" + height);
////                moreDetailGroup.setVisibility(View.GONE);
//            }
//        });
//---暂时无用的
/*************************   bottom_View  ********************************/
        //		moreDetailGroup.setVisibility(View.GONE);
        mRadioGroup = (RadioGroup) bottom_View.findViewById(R.id.ac_shopdetail_RadioGroup);
        // toSeeMore.setChecked(false);
        mPicDetail = (RadioButton) bottom_View.findViewById(R.id.ac_shopdetail_mypicdetail);
        mCommentDetail = (RadioButton) bottom_View.findViewById(R.id.ac_shopdetail_mycommentdetail);
        mPager = (MyCustomViewPager) bottom_View.findViewById(R.id.detail_pager);
//        mWebview = (ScrollWebView) bottom_View.findViewById(R.id.ac_good_detail_webview);
//        mCommentListview = (NoScrollListview) bottom_View.findViewById(R.id.ac_good_evaluation_listview);
        tv_noData = (TextView) bottom_View.findViewById(R.id.ac_good_nodata);
//        tv_pic_nodata = (TextView) bottom_View.findViewById(R.id.ac_good_picdetail_nodata);
        mHishequTV = (TextView) bottom_View.findViewById(R.id.ac_shopdetail_tv_Hishequ);
//        mRecommendGridView = (NoScroolGridView) bottom_View.findViewById(R.id.ac_shopdetail_recommendGoods);
        //		mScrollView2=(ScrollView) findViewById(R.id.scrollView2);
        //		mScrollView2.smoothScrollTo(0, 20);
        Log.e(Tag, "~~~ top_view.getHeight()==" + top_view.getHeight());
        mPager.setPagingEnabled(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.startAutoScroll();
    }

    private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
            if (mWatingWindow != null) {
                mWatingWindow.dismiss();
            }

        }

        @Override
        public void onSuccess(Object message) {
            if (mWatingWindow != null) {
                mWatingWindow.dismiss();

            }
            if (message == null) return;
            goodsdata = (NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity) message;
            Log.e(Tag, "商品详情数据message-" + message);
            Log.e(Tag, "商品详情数据-" + goodsdata.toString());
            goodsId = goodsdata.getId();

            setUi(goodsdata);
            mPicDetail.setChecked(true);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSupplyGoodsDetailEntity(result);
        }

        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void cancleAsyncTask() {
            if (mWatingWindow != null) {
                mWatingWindow.dismiss();

            }
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(SupplyGoodsDetailActivity.this);
            }
        }
    };
    /**
     * 传递给商品评价的列表
     **/
    private List<MerchantEvaluationInfoListBean> comment;
    public PicPageAdapter pagerAdapter;

    /**
     * 展示数据
     *
     * @param msg
     **/
    public void setUi(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        if (msg == null) {
            return;
        }
        goodsdata = msg;
        setCarAmount(msg);

        if (msg.getMid().equals("0")) {//商品所属商家(0=>来自嗨社区)
            mHishequTV.setVisibility(View.GONE);
        } else {
            mHishequTV.setVisibility(View.GONE);
        }
        if (msg.getType().equals("0")) {//商品类型(0=>普通商品,1=>抢购商品,2=>非卖品,比如服务什么的)
            mCommonTitleLayout.setVisibility(View.VISIBLE);
            mFlashTitleLayout.setVisibility(View.GONE);
            if (null != msg.getName()) name.setText(msg.getName());
            if (null != msg.getSale())
                mSaleCount.setText("已售" + msg.getSale());
            if (null != msg.getStandard().get(0).getPrice())
                price.setText("￥:" + msg.getStandard().get(0).getPrice());
            singlePrice = Float.parseFloat(msg.getStandard().get(0).getPrice());
            if (null != msg.getStandard().get(0).getOld_price() && !msg.getStandard().get(0).getOld_price().equals("")
                    && !msg.getStandard().get(0).getOld_price().equals("null")) {
                Spannable spanStrikethrough = new SpannableString("￥:" + msg.getStandard().get(0).getOld_price());
                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
                try {
                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                } catch (Exception ex) {

                }
                mOldprice.setText(spanStrikethrough);
            } else {
                mOldprice.setVisibility(View.GONE);
            }
            if (null != msg.getStandard().get(0).getStorage())
                mKucunCount_Standard.setText("库存" + msg.getStandard().get(0).getStorage());

        } else if (msg.getType().equals("1")) {
            mFlashTitleLayout.setVisibility(View.VISIBLE);
            mCommonTitleLayout.setVisibility(View.GONE);
            if (null != msg.getName()) mFlashName.setText(msg.getName());
            if (null != msg.getRemainTime()) mSaledTime.setText(msg.getName());
            if (null != msg.getStandard().get(0).getPrice())
                mFlashNowPrice.setText("￥:" + msg.getStandard().get(0).getPrice());
            singlePrice = Float.parseFloat(msg.getStandard().get(0).getPrice());

            if (null != msg.getStandard().get(0).getOld_price() && !msg.getStandard().get(0).getOld_price().equals("")
                    && !msg.getStandard().get(0).getOld_price().equals("null")) {
                Spannable spanStrikethrough = new SpannableString("￥:" + msg.getStandard().get(0).getOld_price());
                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
                try {
                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                } catch (Exception ex) {

                }

                mFlashOldPrice.setText(spanStrikethrough);
            } else {
                mFlashOldPrice.setVisibility(View.GONE);
            }
            if (null != msg.getStandard().get(0).getStorage())
                mFlashKucun.setText(msg.getStandard().get(0).getStorage());
            if (null != msg.getPercent()) {
                if (msg.getPercent().contains("%")) {
                    String[] strings = msg.getPercent().split("%");
                    mFlashProgressBar.setProgress(Integer.parseInt(strings[0]));
                }
                mFlashprogressTV.setText(msg.getPercent());
            }

            //set倒计时
            if (null != msg.getRemainTime()) {
                mCounter = new onCounter(Long.parseLong(msg.getRemainTime()) * 1000 - System.currentTimeMillis(), 1000);
                mCounter.start();
            }

        }
        //公共部分
        //商品轮播图
        pagerAdapter = new PicPageAdapter(this).setInfiniteLoop(true);
        mViewPager.setAdapter(pagerAdapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mViewPager.setInterval(2000);
        mViewPager.startAutoScroll();
        mViewPager.setCurrentItem(0);
        if (null != msg.getPic()) {
            pagerAdapter.setImageIdList(msg.getPic());
        }
        //商品简介
        if (null != msg.getIntro())
            detail.setText(msg.getIntro());
        //动态添加属性
        if (msg.getAttr() != null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(this);
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.AttrEntity> attrEntityList = msg.getAttr();
            Log.e(Tag, "属性长度：" + attrEntityList.size());
            for (int i = 0; i < attrEntityList.size(); i++) {
                Log.e(Tag, "new一个属性");
                View inflateView = layoutInflater.inflate(R.layout.goodsdetail_attrs_layout, null);
                TextView mAttr_name = (TextView) inflateView.findViewById(R.id.ac_shop_origin);
                TextView mAttr_val = (TextView) inflateView.findViewById(R.id.ac_shop_origin_tv);
                mAttr_name.setText(attrEntityList.get(i).getAttr_name());
                mAttr_val.setText(attrEntityList.get(i).getAttr_val());
                moreDetailGroup.addView(inflateView);
            }
        }
        //动态添加规格
        if (msg.getStandard() != null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(this);

            mStandardList = msg.getStandard();
            Log.e(Tag, "排序前list：" + mStandardList.toString());

            Collections.sort(mStandardList);
            Log.e(Tag, "排序后list：" + mStandardList.toString());


            Log.e(Tag, "规格长度：" + mStandardList.size());

            for (int i = 0; i < mStandardList.size(); i++) {
                Log.e(Tag, "new一个规格");
                RadioButton newRadioBut = (RadioButton) layoutInflater.inflate(R.layout.radiobut_standard, null);
                newRadioBut.setId(Integer.parseInt(mStandardList.get(i).getId()));
//                newRadioBut.setWidth(0);
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 0, 0, 0);
//                newRadioBut.setBackground(getDrawable(R.drawable.bg_selector_but_coner_green_stroke));
                newRadioBut.setBackgroundResource(R.drawable.bg_selector_but_coner_green_stroke);
                newRadioBut.setLayoutParams(layoutParams);
                newRadioBut.setText(mStandardList.get(i).getName());
                mStandardRadiogroup.addView(newRadioBut);
            }
        } else {

            mStandardLayout.setVisibility(View.GONE);
        }

        //电话
        if (null != msg.getTel()) {
            telephone.setText(msg.getTel());
            telPhone = msg.getTel();
        } else {
            telephone.setVisibility(View.GONE);
        }
        //服务时间
        if (null != msg.getDelivery()) {
            time.setText(msg.getDelivery());
        } else {
            telephone.setVisibility(View.GONE);
        }
        mStandardRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < mStandardList.size(); i++) {
                    if (checkedId == Integer.parseInt(mStandardList.get(i).getId())) {
                        standardId = mStandardList.get(i).getId();
                        Log.e(Tag, "规格id:" + standardId);
                        if (goodsdata.getType().equals("0")) {//商品类型(0=>普通商品,1=>抢购商品,2=>非卖品,比如服务什么的)
                            if (mStandardList.get(i).getPrice() != null)
                                price.setText("￥:" + mStandardList.get(i).getPrice());
                            singlePrice = Float.parseFloat(mStandardList.get(i).getPrice());

                            updateCarNum(false, singlePrice);
                            if (null != mStandardList.get(i).getOld_price() && !mStandardList.get(i).getOld_price().equals("")
                                    && !mStandardList.get(i).getOld_price().equals("null")) {
                                Spannable spanStrikethrough = new SpannableString("￥:" + mStandardList.get(i).getOld_price());
                                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
                                try {
                                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                } catch (Exception ex) {

                                }
                                mOldprice.setText(spanStrikethrough);
                            } else {
                                mOldprice.setVisibility(View.GONE);
                            }
                            if (null != mStandardList.get(i).getStorage())
                                mKucunCount_Standard.setText("库存" + mStandardList.get(i).getStorage());

                        } else if (goodsdata.getType().equals("1")) {
                            if (null != mStandardList.get(i).getPrice())
                                mFlashNowPrice.setText("￥:" + mStandardList.get(i).getPrice());
                            singlePrice = Float.parseFloat(mStandardList.get(i).getPrice());

                            updateCarNum(false, singlePrice);


                            if (null != mStandardList.get(i).getOld_price() && !mStandardList.get(i).getOld_price().equals("")
                                    && !mStandardList.get(i).getOld_price().equals("null")) {
                                Spannable spanStrikethrough = new SpannableString("￥:" + mStandardList.get(i).getOld_price());
                                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
                                try {
                                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                } catch (Exception ex) {

                                }
                                mFlashOldPrice.setText(spanStrikethrough);
                            } else {
                                mFlashOldPrice.setVisibility(View.GONE);
                            }
                            if (null != mStandardList.get(i).getStorage())
                                mKucunCount_Standard.setText(mStandardList.get(i).getStorage());
                        }

                    }
                }
            }
        });
        //默认第一个选中
        if (mStandardRadiogroup.getChildCount() > 0) {
            ((RadioButton) (mStandardRadiogroup.getChildAt(0))).setChecked(true);
        }

        //设置底部的数据
        setBottomPageUI(msg);

    }

    List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity> mStandardList = new ArrayList<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity>();
    boolean isNoWebDetail = false;


    private void aboutCallService(GoodsData msg) {
        if (null != msg.getTel()) {
            telephone.setText("客服电话：" + msg.getTel());
            telPhone = msg.getTel();
        }
        if (null != msg.getDelivery())
            time.setText("服务时间：" + msg.getDelivery());
    }

    String contentdetail;
    ArrayList<String> contentevaluation;

    /**
     * 返回
     */
    public void goBack() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("list", goodslist);
        setResult(888, data);
        this.finish();
    }

    private boolean hasThisGoodsInfo(ArrayList<Goods_info> goodslist,
                                     Goods_info goods_info) {
        if (goodslist == null || goodslist.size() < 1 || goods_info == null) {
            return false;
        }
        for (Goods_info info : goodslist) {
            if (info.getGoods_id().equals(goods_info.getGoods_id())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,
                GoodImageDetailOrEvaluationActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ac_good_title_go_back:
                goBack();
                break;
            case R.id.ac_shop_car_fl://点击购物车跳转购物车列表
                Intent mIntent = new Intent(this, GeneratedClassUtils.get(MenuLeftAct.class));
                mIntent.putExtra(MenuLeftAct.ACTIVITYTAG,
                        Constacts.MENU_LEFT_GDCAR);
                startActivity(mIntent);
                break;

            case R.id.ac_shop_car_addtocar:
                addCarGoods();
                break;
            // activity 中点击去支付
            case R.id.ac_shop_car_go_pay:
                if (good_count == 0) {
                    Toast.makeText(SupplyGoodsDetailActivity.this, "请选择商品数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                upDateList();
                StringBuffer sb = new StringBuffer();
                sb.append("[");
                for (int i = 0; i < goodslist.size(); i++) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    String rs = df.format(Double.parseDouble(goodslist.get(i)
                            .getPrice()) * goodslist.get(i).getCounts());
                    sb.append(
                            "{\"goods_id\":\"" + goodslist.get(i).getGoods_id()
                                    + "\"")
                            .append(",")
                            .append("\"goods_name\":\""
                                    + goodslist.get(i).getGoods_name() + "\"")
                            .append(",")
                            .append("\"goods_price\":\""
                                    + goodslist.get(i).getPrice() + "\"")
                            .append(",")
                            .append("\"number\":\"" + goodslist.get(i).getCounts()
                                    + "\"")
                            .append(",")
                            .append("\"goods_total_price\":\"" + rs + "\"")
                            .append(",")
                            .append("\"goods_image\":\""
                                    + goodslist.get(i).getThumb_pic() + "\"")
                            .append("}");
                    if (i != goodslist.size() - 1) {
                        sb.append(",");
                    }
                }
                sb.append("]");

                HTTPHelper.GetOrderNo(getOrderNo, goods_price, sb.toString(),
                        storeId);
                break;
            case R.id.call:
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telPhone));
                startActivity(intent2);
                break;

        }
    }

    /**
     * 加入购物车
     **/
    public void addCarGoods() {
        if (HighCommunityUtils.isLogin(SupplyGoodsDetailActivity.this)) {
            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity.this, mRadioGroup, Gravity.CENTER);
            HTTPHelper.addNewHuiGoodsToCar(mIbpiAddShopCar, goodsId, standardId);

        }
    }

    /**
     * 加入购物车弹窗
     */
    PopupWindow waitPop;
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

            updateCarNum(true, singlePrice);
        }


        @Override
        public Object onResolve(String result) {
            Log.e(Tag, "onResolve result" + result);
            return result;
//            return HTTPHelper.ResolveHuiSupportList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(SupplyGoodsDetailActivity.this);
            }
        }
    };
    float singlePrice = 0;


    private void upDateList() {
        for (Goods_info info : goodslist) {
            if (info.getGoods_id().equals(good_id)) {
                info.setCounts(good_count);
            }
        }
    }

    private void updateCarNum(boolean isAddCar, float singlePrice) {
        if (isAddCar) {
            int carNum = Integer.parseInt(caramount.getText().toString());
            caramount.setText(carNum + 1 + "");
            mCarPriceSum += singlePrice;
            Log.e(Tag, "mCarPriceSum:" + mCarPriceSum);
            mAllprice.setText("购物车合计 ￥:" + mCarPriceSum);
        }


    }

    /**
     * set底部购物车数量等
     *
     * @param msg
     */
    private void setCarAmount(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        if (msg.getCartNum() == null || msg.getCartNum() == "") {

            caramount.setText("0");
        } else {
            caramount.setText(msg.getCartNum());
        }
        mAllprice.setText("购物车合计 ￥:" + mCarPriceSum);
//        if (msg.getStandard().get(0).getPrice()!=null||!msg.getStandard().get(0).getPrice().equals("")){
//            mAllprice.setText("购物车合计 ￥:" + Float.parseFloat(msg.getStandard().get(0).getPrice())*Integer.parseInt(caramount.getText().toString().trim()));
//        }else{
//        }

//
//
//        if (goods_count > 99) {
//            caramount.setText("99");
//        } else {
//            caramount.setText(goods_count + "");
//        }
//        BigDecimal total_price = new BigDecimal(goods_price);
//        double all = total_price.setScale(2, BigDecimal.ROUND_HALF_DOWN)
//                .doubleValue();
//        if (all == 0) {
//            mAllprice.setText("0.00");
//        } else {
//            mAllprice.setText(all + "");
//        }
    }

    // Popupwindow 关闭后返回列表数据，更新rightlist 列表数据
    @Override
    public void backAllList(List<Goods_info> glist) {
        payrl.setVisibility(View.VISIBLE);
        goodslist = (ArrayList<Goods_info>) glist;
        double price = 0.0;
        int count = 0;
        for (Goods_info info : goodslist) {
            if (goods_info.getGoods_id().equals(info.getGoods_id())) {
                good_count = info.getCounts();
                conttv.setText(String.valueOf(good_count));
            }
            price += info.getCounts() * Double.parseDouble(info.getPrice());
            count += info.getCounts();
        }
        DecimalFormat bd = new DecimalFormat("0.00");
        goods_price = bd.format(price);
        mAllprice.setText(goods_price);
        goods_count = count;
        if (count > 99) {
            caramount.setText("99");
        } else {
            caramount.setText(goods_count + "");
        }
    }

    /**
     * pop回调设置数量和金额
     **/
    @Override
    public void setNumAndAmount(int num, double amount) {
    }

    @Override
    public void goPay(ArrayList<Goods_info> popBackList) {
        // popwindow 点击去支付，
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < popBackList.size(); i++) {
            DecimalFormat df = new DecimalFormat("0.00");
            String rs = df.format(Double.parseDouble(popBackList.get(i)
                    .getPrice()) * popBackList.get(i).getCounts());
            sb.append(
                    "{\"goods_id\":\"" + popBackList.get(i).getGoods_id()
                            + "\"")
                    .append(",")
                    .append("\"goods_name\":\""
                            + popBackList.get(i).getGoods_name() + "\"")
                    .append(",")
                    .append("\"goods_price\":\""
                            + popBackList.get(i).getPrice() + "\"")
                    .append(",")
                    .append("\"number\":\"" + popBackList.get(i).getCounts()
                            + "\"")
                    .append(",")
                    .append("\"goods_total_price\":\"" + rs + "\"")
                    .append(",")
                    .append("\"goods_image\":\""
                            + popBackList.get(i).getThumb_pic() + "\"")
                    .append("}");
            if (i != popBackList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        HTTPHelper.GetOrderNo(getOrderNo, getAllPrice(popBackList), sb.toString(), storeId);
    }

    public String getAllPrice(ArrayList<Goods_info> popBackList) {
        DecimalFormat df = new DecimalFormat("0.00");
        double price = 0.0;
        for (Goods_info bean : popBackList) {
            price += bean.getCounts() * Double.parseDouble(bean.getPrice());
        }
        return df.format(price);
    }

    private SubmitOrderBean mOrderBean;
    /**
     * 请求服务器 获得订单号的方法
     */
    IBpiHttpHandler getOrderNo = new IBpiHttpHandler() {

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;

            mOrderBean = (SubmitOrderBean) message;

            Intent intent = new Intent(SupplyGoodsDetailActivity.this,
                    ShowPayActivity.class);
            intent.putParcelableArrayListExtra("gods_list", goodslist);
            intent.putExtra("total_price",
                    String.valueOf(mOrderBean.getTotal_price()));
            intent.putExtra("shop", storeName);
            if (mOrderBean != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("MerchantShopFrag", mOrderBean);
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSubmitOrder(result);
        }

        @Override
        public void onError(int id, String message) {
        }

        @Override
        public void cancleAsyncTask() {
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(SupplyGoodsDetailActivity.this);
            }
        }
    };

    @Override
    public void onBackPressed() {
        goBack();
    }

    public class onCounter extends CountDownTimer {

        public onCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mSaledTime.setText(TimeUtil.getCountTime(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPager.stopAutoScroll();
    }

    private void initPager(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {




        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.ac_shopdetail_mypicdetail) {
//                    if (isNoWebDetail) {
//                        mWebview.setVisibility(View.GONE);
////    tv_noData.setText("暂无图文详情");
//                        tv_pic_nodata.setVisibility(View.VISIBLE);
//                    } else {
//
//                        mWebview.setVisibility(View.VISIBLE);
//                        tv_pic_nodata.setVisibility(View.GONE);
//                    }
//                    mCommentListview.setVisibility(View.GONE);
//
//                } else if (checkedId == R.id.ac_shopdetail_mycommentdetail) {
//
//                    mWebview.setVisibility(View.GONE);
//                    mCommentListview.setVisibility(View.VISIBLE);
//
//
//                }
                if (checkedId == R.id.ac_shopdetail_mypicdetail) {
                    if (currentPo != 0)
                        mPager.setCurrentItem(0);
                } else if (checkedId == R.id.ac_shopdetail_mycommentdetail) {
                    if (currentPo != 1)
                        mPager.setCurrentItem(1);
                }

            }
        });

        mPager.setCurrentItem(0);
        proPressList = new ArrayList<View>();
        noDataList = new ArrayList<TextView>();
        HuiOrderAdapter adapter = new HuiOrderAdapter();//与我相关用

        List<View> viewList = new ArrayList<View>();
        viewList.add(0,getPicDetail(msg));
        viewList.add(1,getCommentPageView(msg));
        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPo = i;
                mPager.setTag(currentPo);
                if (!((RadioButton) mRadioGroup.getChildAt(i)).isChecked()) {
                    ((RadioButton) mRadioGroup.getChildAt(i)).setChecked(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        adapter.setViewList(viewList);
        mPager.setCurrentItem(0);


    }

    /**
     * 评论 view
     **/
    View getCommentPageView(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        Log.e(Tag, "getCommentPageView");
        View view = LayoutInflater.from(this).inflate(R.layout.page_commentlistdetail, null);
        MyGoodetailScrollView mScrollView2 = (MyGoodetailScrollView) view.findViewById(R.id.srcollview_page_comment);

        NoScrollListview evaluation_listview = (NoScrollListview) view.findViewById(R.id.page2_evaluation_listview);
        TextView tv_Hishequ = (TextView) view.findViewById(R.id.page2_shopdetail_tv_Hishequ);
        NoScroolGridView shopdetail_recommendGoods = (NoScroolGridView) view.findViewById(R.id.page2_shopdetail_recommendGoods);
        View mProgress = view.findViewById(R.id.ll_sysMsg_Progress);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        evaluation_listview.setEmptyView(mNodata);

        //set评价内容
        if (null != msg.getComment()) {
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity> mCommentList = msg.getComment();
            SupplyGoodsDetailCommentAdapter mEvaluationAdapter = new SupplyGoodsDetailCommentAdapter(mCommentList, SupplyGoodsDetailActivity.this);
            evaluation_listview.setAdapter(mEvaluationAdapter);
//            setCarAmount();
        }

        if (null != msg.getSupply()) {
            tv_Hishequ.setVisibility(View.VISIBLE);
            tv_Hishequ.setText("—— 本商品由" + msg.getSupply() + "所有 ——");
        } else {
            tv_Hishequ.setVisibility(View.INVISIBLE);


        }
        //set推荐商品
        if (msg.getRecommend() != null) {
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.RecommendEntity> mRecommendList = msg.getRecommend();
            SupplGoodsDetailGridAdapter mAdapter = new SupplGoodsDetailGridAdapter(mRecommendList, SupplyGoodsDetailActivity.this);
            shopdetail_recommendGoods.setAdapter(mAdapter);
        }


        return view;
    }

    /**
     * 返回图文详情的view
     **/
    View getPicDetail(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        Log.e(Tag, "getPicDetail");

        View view = LayoutInflater.from(this).inflate(R.layout.page_picdetail, null);
//        bottom_View.findViewById(R.id.page_pic)
//        View view = LayoutInflater.from(this).inflate(R.layout.page_picdetail_newsupply, null);
//        GridViewWithHeaderAndFooter mGridview= (GridViewWithHeaderAndFooter) view.findViewById(R.id.GridWithHeader_page_picdetail);

        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        MyGoodetailScrollView mScrollView1 = (MyGoodetailScrollView) view.findViewById(R.id.srcollview_page_picdetail);
        LinearLayout layout_showhtml = (LinearLayout) view.findViewById(R.id.page1_tv_showhtml);
        ImageView img_showhtml = (ImageView) view.findViewById(R.id.page1_img_showhtml);
        ScrollWebView detail_webview = (ScrollWebView) view.findViewById(R.id.page1_good_detail_webview);
        TextView tv_Hishequ = (TextView) view.findViewById(R.id.page1_shopdetail_tv_Hishequ);
        TextView tv_nopicurl = (TextView) view.findViewById(R.id.tv_nopicurl);
        NoScroolGridView recommendGoodsGview = (NoScroolGridView) view.findViewById(R.id.page1_shopdetail_recommendGoods);
        mProgress.setVisibility(View.GONE);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        Element lastElement = null;
        if (null != msg.getDetail()) {
            if (msg.getDetail().startsWith("http")) {

                Log.e(Tag, "getPicDetail 图文详情url:" + msg.getDetail());
//                loadPicDetail(msg.getDetail());
//                isNoWebDetail = false;
            } else {
                Log.e(Tag, "图文详情:" + msg.getDetail());

//                Log.e(Tag, "解析图文详情" + Html.fromHtml(msg.getDetail()));
//                tv_showhtml.setMovementMethod(LinkMovementMethod.getInstance());
//                tv_showhtml.setText(Html.fromHtml(msg.getDetail()));
                Document document = Jsoup.parse(msg.getDetail());
                Elements allElements = document.getAllElements();
                Log.e(Tag, allElements.toString() + "," + allElements.text());
//                for (Element em :
//                        allElements) {
//                    Log.e(Tag,em.tagName()+"，遍历属性："+em.attributes().toString());
//
//
//                }
                for (int i = 0; i < allElements.size(); i++) {
                    if (i == allElements.size()) {

                        lastElement = allElements.get(i);
                        Log.e(Tag, "终标签：" + lastElement.toString());

                    }
                }
                Elements media = document.select("[src]");
                if (media != null) {

                    for (Element src : media) {
                        if (src.tagName().equals("img")) {
                            Log.e(Tag, src.tagName() + "," + src.attr("src") + "," + src.attr("width") + "," + src.attr("height") + "," + src.attr("alt"));

//                            View imgview = LayoutInflater.from(this).inflate(R.layout.html_img, null);

//                            if (imgview == null) {
//                                Log.e(Tag, "imgview null");
//
//                            } else {
//                                Log.e(Tag, "imgview ! null");
//
//                            }
//                            SimpleDraweeView html_img = (SimpleDraweeView) imgview.findViewById(R.id.page1_img_showhtml);
//                            if (html_img == null) {
//                                Log.e(Tag, "html_img null");
//
//                            } else {
//                                Log.e(Tag, "html_img ! null");
//
////                                imgview.getLayoutParams().height=400;
//                            }

                            if (src.attr("height") != null && !src.attr("height").equals("")&&img_showhtml.getLayoutParams()!=null) {
//                               html_img.getLayoutParams().height=Integer.parseInt(src.attr("height"));
                                Log.e(Tag, "height ! " );

                                img_showhtml.getLayoutParams().height=(int) (Integer.parseInt(src.attr("height"))*1.5);

                            } else {
                                Log.e(Tag, "height null " );
                            }
                            if (src.attr("src") != null && !src.attr("src").equals("")) {
                                Log.e(Tag, "src ! null uri:"+Constacts.IMAGEHTTP + src.attr("src"));
//                                View imgview_common = LayoutInflater.from(this).inflate(R.layout.html_img_common, null);
//                                ImageView html_img_common = (ImageView) imgview_common.findViewById(R.id.page1_img_showhtml);
//                                BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + src.attr("src"), html_img_common);
//                                mGridview.addHeaderView(imgview_common);
//                                html_img.setImageURI(Constacts.IMAGEHTTP + src.attr("src"));
                                BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + src.attr("src"), img_showhtml);
//                                if (src.attr("height") != null && !src.attr("height").equals("")&&img_showhtml.getLayoutParams()!=null) {
////                               html_img.getLayoutParams().height=Integer.parseInt(src.attr("height"));
//                                    Log.e(Tag, "height ! " );
//
//                                    img_showhtml.getLayoutParams().height=Integer.parseInt(src.attr("height"))*2;
//
//                                } else {
//                                    Log.e(Tag, "height null " );
////                               html_img.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
//                                }
                            }

                        } else {
                            Log.e(Tag, src.tagName() + ",_____________" + src.attr("src"));
                        }
                    }
                } else {
                    Log.e(Tag, "img null   显示text");

                    if (lastElement != null) {
                        Log.e(Tag, "img null   lastElement!=null");

                        Log.e(Tag, "img null   lastElement!=null222" + lastElement.text());

                        tv_nopicurl.setText(lastElement.text());
//                        mGridview.addHeaderView(tv);

                    }
                }


            }
        } else {
            Log.e(Tag, "图文详情url: null");
            tv_nopicurl.setText("暂无图文详情");
            tv_nopicurl.getLayoutParams().width= 50;
//            tv_nopicurl.getLayoutParams().width= CommonUtils.px2dip(50);
        }
//        recommendGoodsGview.
//        TextView tv_Hishequ=new TextView(this);
        if (null != msg.getSupply()) {

            tv_Hishequ.setVisibility(View.VISIBLE);
            tv_Hishequ.setText("—— 本商品由" + msg.getSupply() + "所有 ——");
        } else {
            tv_Hishequ.setVisibility(View.INVISIBLE);


        }
//        mGridview.addHeaderView(tv_Hishequ);
        //set推荐商品
        if (msg.getRecommend() != null) {
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.RecommendEntity> mRecommendList = msg.getRecommend();
            SupplGoodsDetailGridAdapter mAdapter = new SupplGoodsDetailGridAdapter(mRecommendList, SupplyGoodsDetailActivity.this);
            recommendGoodsGview.setAdapter(mAdapter);
        }
        return view;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }


    /**
     * 设置底部的数据
     *
     * @param msg
     */
    private void setBottomPageUI(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {


        initPager(msg);
        initPager2(msg);


    }

    private void initPager2(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {








    }


    /**
     * 当前页
     **/
    int currentPo = 0;
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表

}
