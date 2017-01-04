package cn.hi028.android.highcommunity.activity.fragment.newhui;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.AutoScrollViewPager;
import net.duohuo.dhroid.view.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：新版直供商品详情 上半页<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class NewTopPageFrag extends BaseFragment {
    public static final String Tag = "NewTopPageFrag--->";
    public static final String FRAGMENTTAG = "NewTopPageFrag";
    public static  boolean isFistRequest = true;
    public static  boolean isFistRequestHttp = true;
    View view;

    private PopupWindow mWatingWindow;
    private StandardChangeListener mStandardChangeListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mStandardChangeListener = (StandardChangeListener) activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.page_top_supply2, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity goodsdata;
    private void initView() {

        Log.e(Tag, "initView");
findView(view);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {

            goodsdata = bundle.getParcelable("goodsdata");
            setUi(goodsdata);

            Log.e(Tag, "传过来的对象：" + goodsdata.toString());
        } else {

            Log.e(Tag, "传过来的对象：null");
        }

    }
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
    TextView name, price;
    ImageView headimg;
    TextView subimg, addimg, kucun, conttv, detail, goodname, guige, origin;
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
    TextView caramount, mAllprice, telephone, time;
    View viewline1, viewline2, viewline3;
    ImageButton call;
    private TextView edible, scrollText;
    TextView guige_, origin_, edible_;
    PopupWindow mWaittingPop;
    private void findView(View view) {

        mViewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.view_cpi);
        mCommonTitleLayout = (RelativeLayout) view.findViewById(R.id.goodsDetail_commonLayout);
        mSaleCount = (TextView) view.findViewById(R.id.ac_shop_detail_goods_saledCount);
        mKucunCount = (TextView) view.findViewById(R.id.ac_shop_detail_goods_count);
        mKucunCount_Standard = (TextView) view.findViewById(R.id.ac_shop_detail_goods_count2);
        mOldprice = (TextView) view.findViewById(R.id.ac_shop_detail_goods_oldprice);
        name = (TextView) view.findViewById(R.id.ac_shop_detail_goods_name);
        price = (TextView) view.findViewById(R.id.ac_shop_detail_goods_price);
        headimg = (ImageView) view.findViewById(R.id.ac_shop_goods_head_iv);
        subimg = (TextView) view.findViewById(R.id.ac_shop_goods_right_sub_iv);
        addimg = (TextView) view.findViewById(R.id.ac_shop_goods_right_add_iv);
        kucun = (TextView) view.findViewById(R.id.ac_shop_detail_goods_count);
        conttv = (TextView) view.findViewById(R.id.ac_shop_goods_list_right_counts);

        mFlashTitleLayout = (RelativeLayout) view.findViewById(R.id.goodsDetail_flashSaleLayout);
        mSaledTime = (TextView) view.findViewById(R.id.ac_shop_detail_goods_saledTime);
        mFlashName = (TextView) view.findViewById(R.id.ac_shop_detail_goods_nameflash);
        mFlashNowPrice = (TextView) view.findViewById(R.id.ac_shop_detail_goods_priceFlash);
        mFlashOldPrice = (TextView) view.findViewById(R.id.ac_shop_detail_goods_oldpriceFlash);
        mFlashKucun = (TextView) view.findViewById(R.id.goodsDetail_tv_kucun);
        mFlashprogressTV = (TextView) view.findViewById(R.id.goodsDetail_tv_progress);
        mFlashProgressBar = (ProgressBar) view.findViewById(R.id.goodsDetail_progressBar);
        moreDetailGroup = (LinearLayout) view.findViewById(R.id.moredetail_layout);
        mStandardLayout = (RelativeLayout) view.findViewById(R.id.layout_standard_container);
        mStandardRadiogroup = (RadioGroup) view.findViewById(R.id.standard_radiogroup);
        detail = (TextView) view.findViewById(R.id.ac_shop_detail_tv);
        telephone = (TextView) view.findViewById(R.id.shop_detail_service_telephone);
        time = (TextView) view.findViewById(R.id.ac_shop_detail_service_time);
        call = (ImageButton) view.findViewById(R.id.call);
        viewline1 = view.findViewById(R.id.view11);
        viewline2 = view.findViewById(R.id.view12);
        viewline3 = view.findViewById(R.id.view13);
        scrollText = (TextView) view.findViewById(R.id.scroll_Text);

//暂时无用的---
        goodname = (TextView) view.findViewById(R.id.shop_detail_ac_goods_name);
        guige = (TextView) view.findViewById(R.id.ac_shop_detail_speci_tv);
        origin = (TextView) view.findViewById(R.id.ac_shop_origin_tv);

        guige_ = (TextView) view.findViewById(R.id.ac_shop_good_speci);
        origin_ = (TextView) view.findViewById(R.id.ac_shop_origin);
        edible_ = (TextView) view.findViewById(R.id.ac_shop_edible);
        edible = (TextView) view.findViewById(R.id.ac_shop_edible_tv);

    }
    float singlePrice = 0;
    /**
     * 展示数据
     *
     * @param msg
     **/
    public void setUi(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        if (msg == null) {
            return;
        }
//        if (getActivity().hasWindowFocus()&&mViewPager!=null){
//
//            mWaittingPop= HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mViewPager, Gravity.CENTER);
//        }
        goodsdata = msg;

//        if (msg.getMid().equals("0")) {//商品所属商家(0=>来自嗨社区)
//            mHishequTV.setVisibility(View.GONE);
//        } else {
//            mHishequTV.setVisibility(View.GONE);
//        }
        if (msg.getType().equals("0")) {//商品类型(0=>普通商品,1=>抢购商品,2=>非卖品,比如服务什么的)
            mCommonTitleLayout.setVisibility(View.VISIBLE);
            mFlashTitleLayout.setVisibility(View.GONE);
            if (null != msg.getName()) name.setText(msg.getName());
            if (null != msg.getSale())
                mSaleCount.setText("已售" + msg.getSale());
            if (null != msg.getStandard().get(0).getPrice())
                price.setText("￥:" + msg.getStandard().get(0).getPrice());
            singlePrice = Float.parseFloat(msg.getStandard().get(0).getPrice());
            standardId=msg.getStandard().get(0).getId();
            mStandardChangeListener.onStandardChange(false,singlePrice,standardId);
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
            standardId=msg.getStandard().get(0).getId();
            mStandardChangeListener.onStandardChange(false,singlePrice,standardId);

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
        pagerAdapter = new PicPageAdapter(getActivity()).setInfiniteLoop(true);
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
        mViewPager.setInterval(3000);
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
            layoutInflater = LayoutInflater.from(getActivity());
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
            layoutInflater = LayoutInflater.from(getActivity());

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
//TODO
                            mStandardChangeListener.onStandardChange(false, singlePrice,standardId);
//                            updateCarNum(false, singlePrice);
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
//TODO
                            mStandardChangeListener.onStandardChange(false, singlePrice,standardId);

//                            updateCarNum(false, singlePrice);


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
//        setBottomPageUI(msg);

    }
    String standardId = "";
    public PicPageAdapter pagerAdapter;
    private String telPhone = "";
    List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity> mStandardList = new ArrayList<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity>();
    onCounter mCounter;
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public interface StandardChangeListener {
        public void onStandardChange(boolean isAddCar, float singlePrice,String standardId);
    }


}
