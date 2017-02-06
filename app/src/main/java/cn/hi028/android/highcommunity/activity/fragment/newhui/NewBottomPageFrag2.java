package cn.hi028.android.highcommunity.activity.fragment.newhui;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.widget.vertical.VerticalScrollView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.SupplGoodsDetailGridAdapter;
import cn.hi028.android.highcommunity.adapter.SupplyGoodsDetailCommentAdapter;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.view.MyNoScrollListview;
import cn.hi028.android.highcommunity.view.NoScroolGridView;
import cn.hi028.android.highcommunity.view.ScrollWebView;

/**
 * @功能：新版直供商品详情 下半页  高度固定的webview <br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class NewBottomPageFrag2 extends BaseFragment {
    public static final String Tag = "NewBottomPageFrag2--->";
    public static final String FRAGMENTTAG = "NewBottomPageFrag2";
    public static boolean isFistRequest = true;
    public static boolean isFistRequestHttp = true;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.page_bottom_supply2, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity goodsdata;

    private void initView() {


        Log.e(Tag, "initView");
        findView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            goodsdata = bundle.getParcelable("goodsdata");
            setBottomPageUI(goodsdata);
            Log.e(Tag, "传过来的对象：" + goodsdata.toString());
        } else {
            Log.e(Tag, "传过来的对象：null");
        }


    }

    RadioGroup mRadioGroup;
    RadioButton mPicDetail, mCommentDetail;
    TextView tv_noData;
    TextView mHishequTV;

    private void findView(View view) {
        mRadioGroup = (RadioGroup) view.findViewById(R.id.ac_shopdetail_RadioGroup);
        mPicDetail = (RadioButton) view.findViewById(R.id.ac_shopdetail_mypicdetail);
        mCommentDetail = (RadioButton) view.findViewById(R.id.ac_shopdetail_mycommentdetail);
        mPager = (ViewPager) view.findViewById(R.id.detail_pager);
    }

    /**
     * 设置底部的数据
     *
     * @param msg
     */
    private void setBottomPageUI(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        initPager(msg);
    }

    private void initPager(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
        viewList.add(0, getPicDetail(msg));
        viewList.add(1, getCommentPageView(msg));
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
     * 下半页的viewpager
     **/
    ViewPager mPager;
    /**
     * 当前页
     **/
    int currentPo = 0;
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表

    /**
     * 评论 view
     **/
    View getCommentPageView(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        Log.e(Tag, "getCommentPageView");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.page_commentlistdetail2, null);
        MyNoScrollListview evaluation_listview = (MyNoScrollListview) view.findViewById(R.id.page2_evaluation_listview);
        TextView tv_Hishequ = (TextView) view.findViewById(R.id.page2_shopdetail_tv_Hishequ);
        NoScroolGridView shopdetail_recommendGoods = (NoScroolGridView) view.findViewById(R.id.page2_shopdetail_recommendGoods);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        evaluation_listview.setEmptyView(mNodata);
        //set评价内容
        if (null != msg.getComment()) {
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity> mCommentList = msg.getComment();
            //排序
            Collections.sort(mCommentList);
            SupplyGoodsDetailCommentAdapter mEvaluationAdapter = new SupplyGoodsDetailCommentAdapter(mCommentList, getActivity());
            evaluation_listview.setAdapter(mEvaluationAdapter);
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
            SupplGoodsDetailGridAdapter mAdapter = new SupplGoodsDetailGridAdapter(mRecommendList, getActivity());
            shopdetail_recommendGoods.setAdapter(mAdapter);
        }
        return view;
    }

    ScrollWebView detail_webview;
    public static String HTTPHOST = Constacts.IMAGEHTTP;

    /**
     * 返回图文详情的view
     **/
    View getPicDetail(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        Log.e(Tag, "getPicDetail");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.page_picdetail_webview, null);
        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        VerticalScrollView mScrollView1 = (VerticalScrollView) view.findViewById(R.id.srcollview_page_picdetail);
        detail_webview = (ScrollWebView) view.findViewById(R.id.page1_good_detail_webview);
        detail_webview.getSettings().setDefaultTextEncodingName("utf-8");
        detail_webview.getSettings().setAppCacheEnabled(true);// 设置启动缓存
        detail_webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        detail_webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        detail_webview.getSettings().setJavaScriptEnabled(true);
        detail_webview.getSettings().setDisplayZoomControls(false);// 设置显示缩放按钮
        detail_webview.getSettings().setSupportZoom(true); // 支持缩放
        detail_webview.getSettings().setBuiltInZoomControls(true);

        detail_webview.getSettings().setUseWideViewPort(true);//让webview读取网页设置的viewport，pc版网页
        detail_webview.getSettings().setLoadWithOverviewMode(true);
        detail_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放
        detail_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        TextView tv_Hishequ = (TextView) view.findViewById(R.id.page1_shopdetail_tv_Hishequ);
        TextView tv_nopicurl = (TextView) view.findViewById(R.id.tv_nopicurl);
        TextView tv_webview = (TextView) view.findViewById(R.id.tv_webview);
        NoScroolGridView recommendGoodsGview = (NoScroolGridView) view.findViewById(R.id.page1_shopdetail_recommendGoods);
        mProgress.setVisibility(View.GONE);
        boolean iSNoData = true;
        if (null != msg.getDetail()) {
            if (msg.getDetail().endsWith("</p>")) {
                iSNoData = false;
            } else {
                iSNoData = true;
            }
        }
        if (null != msg.getDetail() && !iSNoData) {
            if (msg.getDetail().startsWith("http")) {
                Log.e(Tag, "getPicDetail 图文详情url:" + msg.getDetail());
            } else {
                tv_nopicurl.setVisibility(View.GONE);
                detail_webview.setVisibility(View.VISIBLE);
                Log.e(Tag, "图文详情:" + msg.getDetail());
                Document doc = Jsoup.parse(msg.getDetail());
                Elements pngs = doc.select("img[src]");
                for (Element element : pngs) {
                    String imgUrl = element.attr("src");
                    if (imgUrl.trim().startsWith("/")) {
                        imgUrl = HTTPHOST + imgUrl;
                        element.attr("src", imgUrl);
                    }
                }
                String newsBody = doc.toString();
                Log.e(Tag, "newsBody:" + newsBody);
                detail_webview.loadDataWithBaseURL(null, newsBody, "text/html",
                        "utf-8", null);
            }
        } else {
            tv_nopicurl.setVisibility(View.VISIBLE);
            detail_webview.setVisibility(View.GONE);
            Log.e(Tag, "图文详情url: null");
            tv_nopicurl.setText("暂无图文详情");
            tv_nopicurl.getLayoutParams().width = 50;
        }
        if (null != msg.getSupply()) {
            tv_Hishequ.setText("—— 本商品由" + msg.getSupply() + "所有 ——");
        } else {
            tv_Hishequ.setVisibility(View.INVISIBLE);
        }
        //set推荐商品
        if (msg.getRecommend() != null) {
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.RecommendEntity> mRecommendList = msg.getRecommend();
            SupplGoodsDetailGridAdapter mAdapter = new SupplGoodsDetailGridAdapter(mRecommendList, getActivity());
            recommendGoodsGview.setAdapter(mAdapter);
        }
        return view;
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
}
