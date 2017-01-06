package cn.hi028.android.highcommunity.activity.fragment.newhui;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import java.util.List;

import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.PicDetailListAdapter;
import cn.hi028.android.highcommunity.adapter.SupplGoodsDetailGridAdapter;
import cn.hi028.android.highcommunity.adapter.SupplyGoodsDetailCommentAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.PicBean;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.view.MyNoScrollListview;
import cn.hi028.android.highcommunity.view.NoScroolGridView;
import cn.hi028.android.highcommunity.view.ScrollWebView;

/**
 * @功能：新版直供商品详情 下半页<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class NewBottomPageFrag extends BaseFragment {
    public static final String Tag = "NewBottomPageFrag--->";
    public static final String FRAGMENTTAG = "NewBottomPageFrag";
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
        Bundle bundle = getArguments();//从activity传过来的Bundle
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
        // toSeeMore.setChecked(false);
        mPicDetail = (RadioButton) view.findViewById(R.id.ac_shopdetail_mypicdetail);
        mCommentDetail = (RadioButton) view.findViewById(R.id.ac_shopdetail_mycommentdetail);
        mPager = (ViewPager) view.findViewById(R.id.detail_pager);
//        mWebview = (ScrollWebView) view.findViewById(R.id.ac_good_detail_webview);
//        mCommentListview = (NoScrollListview) view.findViewById(R.id.ac_good_evaluation_listview);
//        tv_pic_nodata = (TextView) view.findViewById(R.id.ac_good_picdetail_nodata);
//        mRecommendGridView = (NoScroolGridView) view.findViewById(R.id.ac_shopdetail_recommendGoods);
        //		mScrollView2=(ScrollView) findViewById(R.id.scrollView2);
        //		mScrollView2.smoothScrollTo(0, 20);
//        mPager.setPagingEnabled(false);


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
        VerticalScrollView mScrollView2 = (VerticalScrollView) view.findViewById(R.id.srcollview_page_comment);

        MyNoScrollListview evaluation_listview = (MyNoScrollListview) view.findViewById(R.id.page2_evaluation_listview);
        TextView tv_Hishequ = (TextView) view.findViewById(R.id.page2_shopdetail_tv_Hishequ);
        NoScroolGridView shopdetail_recommendGoods = (NoScroolGridView) view.findViewById(R.id.page2_shopdetail_recommendGoods);
        View mProgress = view.findViewById(R.id.ll_sysMsg_Progress);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        evaluation_listview.setEmptyView(mNodata);

        //set评价内容
        if (null != msg.getComment()) {
            List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity> mCommentList = msg.getComment();
            SupplyGoodsDetailCommentAdapter mEvaluationAdapter = new SupplyGoodsDetailCommentAdapter(mCommentList, getActivity());
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
            SupplGoodsDetailGridAdapter mAdapter = new SupplGoodsDetailGridAdapter(mRecommendList, getActivity());
            shopdetail_recommendGoods.setAdapter(mAdapter);
        }


        return view;
    }

    /**
     * 返回图文详情的view
     **/
    View getPicDetail(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        Log.e(Tag, "getPicDetail");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.page_picdetail2, null);

        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        VerticalScrollView mScrollView1 = (VerticalScrollView) view.findViewById(R.id.srcollview_page_picdetail);
        LinearLayout layout_showhtml = (LinearLayout) view.findViewById(R.id.page1_tv_showhtml);
        MyNoScrollListview pic_noscrolllistview = (MyNoScrollListview) view.findViewById(R.id.pic_noscrolllistview);

        List<PicBean> imguilList = new ArrayList<PicBean>();

//        ImageView img_showhtml = (ImageView) view.findViewById(R.id.page1_img_showhtml);

        ScrollWebView detail_webview = (ScrollWebView) view.findViewById(R.id.page1_good_detail_webview);
        TextView tv_Hishequ = (TextView) view.findViewById(R.id.page1_shopdetail_tv_Hishequ);
        TextView tv_nopicurl = (TextView) view.findViewById(R.id.tv_nopicurl);
        NoScroolGridView recommendGoodsGview = (NoScroolGridView) view.findViewById(R.id.page1_shopdetail_recommendGoods);
        mProgress.setVisibility(View.GONE);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);

        PicDetailListAdapter mPicDetailListAdapter = new PicDetailListAdapter(imguilList, getActivity());
        pic_noscrolllistview.setEmptyView(mNodata);
        pic_noscrolllistview.setAdapter(mPicDetailListAdapter);
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

                for (int i = 0; i < allElements.size(); i++) {
                    if (i == allElements.size()) {

                        lastElement = allElements.get(i);
                        Log.e(Tag, "终标签：" + lastElement.toString());

                    }
                }
                Elements media = document.select("[src]");
                if (media != null) {
                    for (int i = 0; i < media.size(); i++) {
                        Element src = media.get(i);

                        if (src.tagName().equals("img")) {
                            Log.e(Tag, src.tagName() + "," + src.attr("src") + "," + src.attr("width") + "," + src.attr("height") + "," + src.attr("alt"));
                            PicBean mPicbean = new PicBean();
                            if (src.attr("height") != null && !src.attr("height").equals("")) {
//                               html_img.getLayoutParams().height=Integer.parseInt(src.attr("height"));
                                Log.e(Tag, "height ! ");

                                mPicbean.setHeight(src.attr("height"));

                            } else {
                                Log.e(Tag, "height null ");
                            }
                            if (src.attr("src") != null && !src.attr("src").equals("")) {
                                Log.e(Tag, "src ! null uri:" + Constacts.IMAGEHTTP + src.attr("src"));
                                mPicbean.setImgUrl(src.attr("src"));
                            }
                            imguilList.add(mPicbean);
                            mPicDetailListAdapter.AddNewData(imguilList);
                        } else {
                            Log.e(Tag, src.tagName() + ",_____________" + src.attr("src"));
                        }
                    }


//                    for (Element src : media) {
//                        if (src.tagName().equals("img")) {
//                            Log.e(Tag, src.tagName() + "," + src.attr("src") + "," + src.attr("width") + "," + src.attr("height") + "," + src.attr("alt"));
//                            PicBean mPicbean = new PicBean();
//                            if (src.attr("height") != null && !src.attr("height").equals("")) {
////                               html_img.getLayoutParams().height=Integer.parseInt(src.attr("height"));
//                                Log.e(Tag, "height ! ");
//
//                                mPicbean.setHeight(src.attr("height"));
//
//                            } else {
//                                Log.e(Tag, "height null ");
//                            }
//                            if (src.attr("src") != null && !src.attr("src").equals("")) {
//                                Log.e(Tag, "src ! null uri:" + Constacts.IMAGEHTTP + src.attr("src"));
//                                mPicbean.setImgUrl(src.attr("src"));
//                            }
//
//                        } else {
//                            Log.e(Tag, src.tagName() + ",_____________" + src.attr("src"));
//                        }
//                    }
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
            tv_nopicurl.getLayoutParams().width = 50;
//            tv_nopicurl.getLayoutParams().width= CommonUtils.px2dip(50);
        }


        if (null != msg.getSupply()) {
            tv_Hishequ.setText("—— 本商品由" + msg.getSupply() + "所有 ——");
        } else {
            tv_Hishequ.setVisibility(View.INVISIBLE);


        }
//        mGridview.addHeaderView(tv_Hishequ);
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
