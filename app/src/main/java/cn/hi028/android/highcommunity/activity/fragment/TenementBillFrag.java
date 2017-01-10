/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.TenementBillAdapter;
import cn.hi028.android.highcommunity.bean.BillSimpleBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：物业账单页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-01-30<br>
 */
@EFragment(resName = "frag_tenementbill")
public class TenementBillFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "TenementBillFrag";
    @ViewById(R.id.vp_tenement_ViewPager)
    ViewPager mPager;// 页卡内容
    @ViewById(R.id.rg_tenement_bill)
    RadioGroup rg;//

    int currentPo = 0;
    public List<ListView> viewList; // Tab页面列表
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表

    private List<TenementBillAdapter> adapterList; // Tab页面列表

    @AfterViews
    void initView() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_tenement_nopay) {
                    if (currentPo != 0)
                        mPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_tenement_paied) {
                    if (currentPo != 1)
                        mPager.setCurrentItem(1);
                }
            }
        });
        proPressList = new ArrayList<View>();
        noDataList = new ArrayList<TextView>();
        viewList = new ArrayList<ListView>();
        adapterList = new ArrayList<TenementBillAdapter>();
        HuiOrderAdapter adapter = new HuiOrderAdapter();
        List<View> viewList = new ArrayList<View>();
        viewList.add(getPageView(0));
        viewList.add(getPageView(1));
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
                if (ListUtils.getSize(adapterList.get(i).getData()) == 0) {
                    if (i == 0) {
                        HTTPHelper.getTenementBillList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", "0");
                    } else if (i == 1) {
                        HTTPHelper.getTenementBillList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", "1");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        adapter.setViewList(viewList);
        HTTPHelper.getTenementBillList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", "0");
    }

    View getPageView(int flagTp) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_chip_order, null);
        TenementBillAdapter adapter = new TenementBillAdapter(this, flagTp);
        PullToRefreshListView ptfl = (PullToRefreshListView) view.findViewById(R.id.lv_list);
        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        mProgress.setVisibility(View.VISIBLE);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        proPressList.add(mProgress);
        noDataList.add(mNodata);
        ListView lv_list = ptfl.getRefreshableView();
        ptfl.setMode(PullToRefreshBase.Mode.DISABLED);
        lv_list.setAdapter(adapter);
        adapterList.add(adapter);
        viewList.add(lv_list);
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
            List<BillSimpleBean> data = (List<BillSimpleBean>) message;
            adapterList.get(currentPo).setData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveTenementBillList(result);
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
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
}
