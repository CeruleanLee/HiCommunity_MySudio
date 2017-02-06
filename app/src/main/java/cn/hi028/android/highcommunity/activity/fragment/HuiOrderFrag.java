/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.HuiOrderListAdapter;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：用户订单<br> 从侧边栏我的订单跳过来的订单
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-27<br>
 */
@EFragment(resName = "frag_huilife_order_vp")
public class HuiOrderFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "HuiOrderFrag";
    @ViewById(R.id.vPager)
    ViewPager mPager;
    @ViewById(R.id.rg_huil_ife)
    RadioGroup rg;//
    public List<ExpandableListView> viewList;
    public List<View> proPressList;
    public List<TextView> noDataList;
    private List<HuiOrderListAdapter> adapterList;

    @AfterViews
    void initView() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_order_all) {
                    if (currentPo != 0)
                        mPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_no_pay) {
                    if (currentPo != 1)
                        mPager.setCurrentItem(1);
                } else if (checkedId == R.id.rb_reserve) {
                    if (currentPo != 2)
                        mPager.setCurrentItem(2);
                } else if (checkedId == R.id.rb_comment) {
                    if (currentPo != 3)
                        mPager.setCurrentItem(3);
                }
            }
        });
        proPressList = new ArrayList<View>();
        noDataList = new ArrayList<TextView>();
        viewList = new ArrayList<ExpandableListView>();
        adapterList = new ArrayList<HuiOrderListAdapter>();
        HuiOrderAdapter adapter = new HuiOrderAdapter();
        List<View> viewList = new ArrayList<View>();
        viewList.add(getPageView(0));
        viewList.add(getPageView(1));
        viewList.add(getPageView(2));
        viewList.add(getPageView(3));
        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPo = i;
                if (!((RadioButton) rg.getChildAt(i)).isChecked()) {
                    ((RadioButton) rg.getChildAt(i)).setChecked(true);
                }
                upateRefresh(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        adapter.setViewList(viewList);
        currentPo = getActivity().getIntent().getIntExtra(MenuLeftAct.INTENTTAG, 0);
    }

    public void upateRefresh(int i) {
        if (i == 0) {
            HTTPHelper.GetHuiOrder(mIbpi, "order/all-order.html", HighCommunityApplication.mUserInfo.getId() + "");
        } else if (i == 1) {
            HTTPHelper.GetHuiOrder(mIbpi, "order/non-payment.html", HighCommunityApplication.mUserInfo.getId() + "");
        } else if (i == 2) {
            HTTPHelper.GetHuiOrder(mIbpi, "order/wait-send.html", HighCommunityApplication.mUserInfo.getId() + "");
        } else if (i == 3) {
            HTTPHelper.GetHuiOrder(mIbpi, "order/success.html", HighCommunityApplication.mUserInfo.getId() + "");
        }
    }

    int currentPo = 0;

    View getPageView(int flagTp) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_huilife_order, null);
        HuiOrderListAdapter adapter = new HuiOrderListAdapter(this, flagTp);
        PullToRefreshExpandableListView ptfl = (PullToRefreshExpandableListView) view.findViewById(R.id.lv_list);
        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        mProgress.setVisibility(View.VISIBLE);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        proPressList.add(mProgress);
        noDataList.add(mNodata);
        ExpandableListView lv_list = ptfl.getRefreshableView();
        ptfl.setMode(PullToRefreshBase.Mode.DISABLED);
        lv_list.setGroupIndicator(null);
        lv_list.setAdapter(adapter);
        adapterList.add(adapter);
        viewList.add(lv_list);
        lv_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
        return view;
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            proPressList.get(currentPo).setVisibility(View.GONE);
            noDataList.get(currentPo).setVisibility(View.VISIBLE);
            noDataList.get(currentPo).setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            proPressList.get(currentPo).setVisibility(View.GONE);
            noDataList.get(currentPo).setVisibility(View.GONE);
            if (null == message)
                return;
            List<HuiOrderBean> data = (List<HuiOrderBean>) message;
            adapterList.get(currentPo).setData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveOrderList(result);
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

    @Override
    public void onResume() {
        super.onResume();
        if (currentPo == mPager.getCurrentItem()) {
            upateRefresh(currentPo);
        }
        mPager.setCurrentItem(currentPo);
    }
}
