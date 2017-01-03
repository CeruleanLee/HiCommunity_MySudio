package cn.hi028.android.highcommunity.activity.alliance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.lzy.widget.VerticalSlide;

import net.duohuo.dhroid.view.AutoScrollViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.GoodImageDetailOrEvaluationActivity;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.fragment.newhui.NewBottomPageFrag;
import cn.hi028.android.highcommunity.activity.fragment.newhui.NewTopPageFrag;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyCarlistBean;
import cn.hi028.android.highcommunity.bean.GoodsData;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.lisenter.PayPop2FragFace;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 新版直供商品详情2  换了框架
 *
 * @author Lee_yting
 */
public class SupplyGoodsDetailActivity2 extends BaseFragmentActivity implements
        OnClickListener, PayPop2FragFace, NewTopPageFrag.StandardChangeListener {

    static String Tag = "SupplyGoodsDetailActivity";
    private static final int TAB_PICDETAIL = 0;
    public static final int TAB_COMMENTDETAIL = 1;
    int currentTab = 0;
    int good_count;
    ImageView back;
    TextView conttv;
    Button addToCar;
    TextView caramount, mAllprice, telephone, time,tv_mynodata;
    FrameLayout shopcar;
    LinearLayout payrl;
    RelativeLayout layout_hasData;
    RadioGroup mRadioGroup;


    /**
     * 接口获取的data
     **/
    NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity goodsdata;
    ArrayList<Goods_info> goodslist;
    private String goods_price;
    private int goods_count;
    private String telPhone = "";
    private Goods_info goods_info = null;
    /**
     * 获取商品详情数据的商品id
     **/
    String id = -1 + "";
    String goodsId;
    String standardId = "";

    Handler mHandler = new Handler();
    /**
     * 购物车价格合计
     **/
    float mCarPriceSum;
    private VerticalSlide verticalSlide;
    private NewTopPageFrag topFragment;
    private NewBottomPageFrag bottomFragment;
    private FloatingActionButton fab;
    PopupWindow mWaittingPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(Tag, "~~~啦啦啦  进入详情onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail_activity_supply2);
        id = getIntent().getStringExtra("id");
        goodsId = id;
        verticalSlide = (VerticalSlide) findViewById(R.id.dragLayout);
        init();
    }

    private void init() {
        findView();
        registerListener();
//        if (goods_count > 99) {
//            caramount.setText("99");
//        } else {
//            caramount.setText(goods_count + "");
//        }
        initData();
    }

    private void findView() {
        caramount = (TextView) findViewById(R.id.ac_shop_count);
        tv_mynodata = (TextView) findViewById(R.id.tv_mynodata);
        back = (ImageView) findViewById(R.id.ac_good_title_go_back);
        payrl = (LinearLayout) findViewById(R.id.shop_deatil_bottom_pay_rl);
        layout_hasData = (RelativeLayout) findViewById(R.id.layout_hasData);
        mAllprice = (TextView) findViewById(R.id.ac_shop_car_price);
        addToCar = (Button) findViewById(R.id.ac_shop_car_addtocar);
        shopcar = (FrameLayout) findViewById(R.id.ac_shop_car_fl);

    }

    private void initViewNew() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        if (goodsdata != null) {
            bundle.putParcelable("goodsdata", goodsdata);
        }
        topFragment = new NewTopPageFrag();
        topFragment.setArguments(bundle);
        transaction.replace(R.id.first, topFragment);
/******* 上 下 *************/
        Bundle bundle2 = new Bundle();
        if (goodsdata != null) {
            bundle2.putParcelable("goodsdata", goodsdata);
        }
        bottomFragment = new NewBottomPageFrag();
        bottomFragment.setArguments(bundle2);
        transaction.replace(R.id.second, bottomFragment);

        transaction.commit();
    }

    @Override
    public void onStandardChange(boolean isAddCar, float singlePrice, String standardId) {
        this.singlePrice = singlePrice;
        this.standardId = standardId;
        updateCarNum(isAddCar, singlePrice);
    }

    private Handler popupHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (SupplyGoodsDetailActivity2.this.hasWindowFocus() && mViewPager != null) {
                        mWaittingPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity2.this, caramount, Gravity.CENTER);
                    }
                    break;
            }
        }

    };

    private void initData() {
        popupHandler.sendEmptyMessageDelayed(0, 50);
//        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity.this, caramount, Gravity.CENTER);
        HTTPHelper.getGdCarList2(mGetCarIbpi);
    }

    IBpiHttpHandler mGetCarIbpi = new IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();
            }
            layout_hasData.setVisibility(View.GONE);
            tv_mynodata.setText(message);
            tv_mynodata.setVisibility(View.VISIBLE);


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
            if (mWaittingPop!=null){
                mWaittingPop.dismiss();
            }
        }

    };


    Bundle bundle = new Bundle();

    private void registerListener() {
        back.setOnClickListener(this);
        shopcar.setOnClickListener(this);

        addToCar.setOnClickListener(this);
//        call.setOnClickListener(this);
//        telephone.setOnClickListener(this);

    }
    AutoScrollViewPager mViewPager;
    @Override
    protected void onResume() {
        super.onResume();
//        mViewPager.startAutoScroll();
    }
    private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();
            }
            layout_hasData.setVisibility(View.GONE);
            tv_mynodata.setText(message);
            tv_mynodata.setVisibility(View.VISIBLE);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }
        @Override
        public void onSuccess(Object message) {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();
            }
            layout_hasData.setVisibility(View.VISIBLE);
            tv_mynodata.setVisibility(View.GONE);
            if (message == null) return;
            goodsdata = (NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity) message;
            Log.e(Tag, "商品详情数据message-" + message);
            Log.e(Tag, "商品详情数据-" + goodsdata.toString());
            goodsId = goodsdata.getId();
            setCarAmount(goodsdata);
            initViewNew();
//            setUi(goodsdata);
//            mPicDetail.setChecked(true);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSupplyGoodsDetailEntity(result);
        }



        @Override
        public void cancleAsyncTask() {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();

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
//    public void setUi(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
//        if (msg == null) {
//            return;
//        }
//        goodsdata = msg;
//        setCarAmount(msg);
//
//        if (msg.getMid().equals("0")) {//商品所属商家(0=>来自嗨社区)
//            mHishequTV.setVisibility(View.GONE);
//        } else {
//            mHishequTV.setVisibility(View.GONE);
//        }
//        if (msg.getType().equals("0")) {//商品类型(0=>普通商品,1=>抢购商品,2=>非卖品,比如服务什么的)
//            mCommonTitleLayout.setVisibility(View.VISIBLE);
//            mFlashTitleLayout.setVisibility(View.GONE);
//            if (null != msg.getName()) name.setText(msg.getName());
//            if (null != msg.getSale())
//                mSaleCount.setText("已售" + msg.getSale());
//            if (null != msg.getStandard().get(0).getPrice())
//                price.setText("￥:" + msg.getStandard().get(0).getPrice());
//            singlePrice = Float.parseFloat(msg.getStandard().get(0).getPrice());
//            if (null != msg.getStandard().get(0).getOld_price() && !msg.getStandard().get(0).getOld_price().equals("")
//                    && !msg.getStandard().get(0).getOld_price().equals("null")) {
//                Spannable spanStrikethrough = new SpannableString("￥:" + msg.getStandard().get(0).getOld_price());
//                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
//                try {
//                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//                } catch (Exception ex) {
//
//                }
//                mOldprice.setText(spanStrikethrough);
//            } else {
//                mOldprice.setVisibility(View.GONE);
//            }
//            if (null != msg.getStandard().get(0).getStorage())
//                mKucunCount_Standard.setText("库存" + msg.getStandard().get(0).getStorage());
//
//        } else if (msg.getType().equals("1")) {
//            mFlashTitleLayout.setVisibility(View.VISIBLE);
//            mCommonTitleLayout.setVisibility(View.GONE);
//            if (null != msg.getName()) mFlashName.setText(msg.getName());
//            if (null != msg.getRemainTime()) mSaledTime.setText(msg.getName());
//            if (null != msg.getStandard().get(0).getPrice())
//                mFlashNowPrice.setText("￥:" + msg.getStandard().get(0).getPrice());
//            singlePrice = Float.parseFloat(msg.getStandard().get(0).getPrice());
//
//            if (null != msg.getStandard().get(0).getOld_price() && !msg.getStandard().get(0).getOld_price().equals("")
//                    && !msg.getStandard().get(0).getOld_price().equals("null")) {
//                Spannable spanStrikethrough = new SpannableString("￥:" + msg.getStandard().get(0).getOld_price());
//                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
//                try {
//                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//                } catch (Exception ex) {
//
//                }
//
//                mFlashOldPrice.setText(spanStrikethrough);
//            } else {
//                mFlashOldPrice.setVisibility(View.GONE);
//            }
//            if (null != msg.getStandard().get(0).getStorage())
//                mFlashKucun.setText(msg.getStandard().get(0).getStorage());
//            if (null != msg.getPercent()) {
//                if (msg.getPercent().contains("%")) {
//                    String[] strings = msg.getPercent().split("%");
//                    mFlashProgressBar.setProgress(Integer.parseInt(strings[0]));
//                }
//                mFlashprogressTV.setText(msg.getPercent());
//            }
//
//            //set倒计时
//            if (null != msg.getRemainTime()) {
////                mCounter = new onCounter(Long.parseLong(msg.getRemainTime()) * 1000 - System.currentTimeMillis(), 1000);
////                mCounter.start();
//            }
//
//        }
//        //公共部分
//        //商品轮播图
//        pagerAdapter = new PicPageAdapter(this).setInfiniteLoop(true);
//        mViewPager.setAdapter(pagerAdapter);
//        mIndicator.setViewPager(mViewPager);
//        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//        mViewPager.setInterval(2000);
//        mViewPager.startAutoScroll();
//        mViewPager.setCurrentItem(0);
//        if (null != msg.getPic()) {
//            pagerAdapter.setImageIdList(msg.getPic());
//        }
//        //商品简介
//        if (null != msg.getIntro())
//            detail.setText(msg.getIntro());
//        //动态添加属性
//        if (msg.getAttr() != null) {
//            LayoutInflater layoutInflater;
//            layoutInflater = LayoutInflater.from(this);
//            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.AttrEntity> attrEntityList = msg.getAttr();
//            Log.e(Tag, "属性长度：" + attrEntityList.size());
//            for (int i = 0; i < attrEntityList.size(); i++) {
//                Log.e(Tag, "new一个属性");
//                View inflateView = layoutInflater.inflate(R.layout.goodsdetail_attrs_layout, null);
//                TextView mAttr_name = (TextView) inflateView.findViewById(R.id.ac_shop_origin);
//                TextView mAttr_val = (TextView) inflateView.findViewById(R.id.ac_shop_origin_tv);
//                mAttr_name.setText(attrEntityList.get(i).getAttr_name());
//                mAttr_val.setText(attrEntityList.get(i).getAttr_val());
//                moreDetailGroup.addView(inflateView);
//            }
//        }
//        //动态添加规格
//        if (msg.getStandard() != null) {
//            LayoutInflater layoutInflater;
//            layoutInflater = LayoutInflater.from(this);
//
//            mStandardList = msg.getStandard();
//            Log.e(Tag, "排序前list：" + mStandardList.toString());
//
//            Collections.sort(mStandardList);
//            Log.e(Tag, "排序后list：" + mStandardList.toString());
//
//
//            Log.e(Tag, "规格长度：" + mStandardList.size());
//
//            for (int i = 0; i < mStandardList.size(); i++) {
//                Log.e(Tag, "new一个规格");
//                RadioButton newRadioBut = (RadioButton) layoutInflater.inflate(R.layout.radiobut_standard, null);
//                newRadioBut.setId(Integer.parseInt(mStandardList.get(i).getId()));
////                newRadioBut.setWidth(0);
//                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
//                        RadioGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(20, 0, 0, 0);
////                newRadioBut.setBackground(getDrawable(R.drawable.bg_selector_but_coner_green_stroke));
//                newRadioBut.setBackgroundResource(R.drawable.bg_selector_but_coner_green_stroke);
//                newRadioBut.setLayoutParams(layoutParams);
//                newRadioBut.setText(mStandardList.get(i).getName());
//                mStandardRadiogroup.addView(newRadioBut);
//            }
//        } else {
//
//            mStandardLayout.setVisibility(View.GONE);
//        }
//
//        //电话
//        if (null != msg.getTel()) {
//            telephone.setText(msg.getTel());
//            telPhone = msg.getTel();
//        } else {
//            telephone.setVisibility(View.GONE);
//        }
//        //服务时间
//        if (null != msg.getDelivery()) {
//            time.setText(msg.getDelivery());
//        } else {
//            telephone.setVisibility(View.GONE);
//        }
//        mStandardRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                for (int i = 0; i < mStandardList.size(); i++) {
//                    if (checkedId == Integer.parseInt(mStandardList.get(i).getId())) {
//                        standardId = mStandardList.get(i).getId();
//                        Log.e(Tag, "规格id:" + standardId);
//                        if (goodsdata.getType().equals("0")) {//商品类型(0=>普通商品,1=>抢购商品,2=>非卖品,比如服务什么的)
//                            if (mStandardList.get(i).getPrice() != null)
//                                price.setText("￥:" + mStandardList.get(i).getPrice());
//                            singlePrice = Float.parseFloat(mStandardList.get(i).getPrice());
//
//                            updateCarNum(false, singlePrice);
//                            if (null != mStandardList.get(i).getOld_price() && !mStandardList.get(i).getOld_price().equals("")
//                                    && !mStandardList.get(i).getOld_price().equals("null")) {
//                                Spannable spanStrikethrough = new SpannableString("￥:" + mStandardList.get(i).getOld_price());
//                                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
//                                try {
//                                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//                                } catch (Exception ex) {
//
//                                }
//                                mOldprice.setText(spanStrikethrough);
//                            } else {
//                                mOldprice.setVisibility(View.GONE);
//                            }
//                            if (null != mStandardList.get(i).getStorage())
//                                mKucunCount_Standard.setText("库存" + mStandardList.get(i).getStorage());
//
//                        } else if (goodsdata.getType().equals("1")) {
//                            if (null != mStandardList.get(i).getPrice())
//                                mFlashNowPrice.setText("￥:" + mStandardList.get(i).getPrice());
//                            singlePrice = Float.parseFloat(mStandardList.get(i).getPrice());
//
//                            updateCarNum(false, singlePrice);
//
//
//                            if (null != mStandardList.get(i).getOld_price() && !mStandardList.get(i).getOld_price().equals("")
//                                    && !mStandardList.get(i).getOld_price().equals("null")) {
//                                Spannable spanStrikethrough = new SpannableString("￥:" + mStandardList.get(i).getOld_price());
//                                StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
//                                try {
//                                    spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//                                } catch (Exception ex) {
//
//                                }
//                                mFlashOldPrice.setText(spanStrikethrough);
//                            } else {
//                                mFlashOldPrice.setVisibility(View.GONE);
//                            }
//                            if (null != mStandardList.get(i).getStorage())
//                                mKucunCount_Standard.setText(mStandardList.get(i).getStorage());
//                        }
//
//                    }
//                }
//            }
//        });
//        //默认第一个选中
//        if (mStandardRadiogroup.getChildCount() > 0) {
//            ((RadioButton) (mStandardRadiogroup.getChildAt(0))).setChecked(true);
//        }
//
//
//    }

    List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity> mStandardList = new ArrayList<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity>();


    private void aboutCallService(GoodsData msg) {
        if (null != msg.getTel()) {
            telephone.setText("客服电话：" + msg.getTel());
            telPhone = msg.getTel();
        }
        if (null != msg.getDelivery())
            time.setText("服务时间：" + msg.getDelivery());
    }


    /**
     * 返回
     */
    public void goBack() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("list", goodslist);
        setResult(888, data);
        this.finish();
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
//            case R.id.call:
//                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telPhone));
//                startActivity(intent2);
//                break;

        }
    }

    /**
     * 加入购物车
     **/
    public void addCarGoods() {
        if (HighCommunityUtils.isLogin(SupplyGoodsDetailActivity2.this)) {
            if (waitPop != null && mRadioGroup != null) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity2.this, mRadioGroup, Gravity.CENTER);
            }
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
    IBpiHttpHandler mIbpiAddShopCar = new IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (waitPop != null) {

                waitPop.dismiss();
            }
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (waitPop != null) {

                waitPop.dismiss();
            }
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
    };
    float singlePrice = 0;


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
//        HTTPHelper.GetOrderNo(getOrderNo, getAllPrice(popBackList), sb.toString(), storeId);
    }

    public String getAllPrice(ArrayList<Goods_info> popBackList) {
        DecimalFormat df = new DecimalFormat("0.00");
        double price = 0.0;
        for (Goods_info bean : popBackList) {
            price += bean.getCounts() * Double.parseDouble(bean.getPrice());
        }
        return df.format(price);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mViewPager.stopAutoScroll();
    }


    /**
     * 当前页
     **/
    int currentPo = 0;
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表

}
