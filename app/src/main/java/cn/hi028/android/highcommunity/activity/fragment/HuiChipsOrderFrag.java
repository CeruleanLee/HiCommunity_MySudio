/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.HuiChipOrderAdapter;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.HuiOrderListAdapter;
import cn.hi028.android.highcommunity.bean.ChipsOrderBean;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * @功能：用户众筹订单<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-27<br>
 */
@EFragment(resName = "frag_huilife_chip_vp")
public class HuiChipsOrderFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "HuiChipsOrderFrag";
    @ViewById(R.id.vPager)
    ViewPager mPager;// 页卡内容
    @ViewById(R.id.rg_huil_ife)
    RadioGroup rg;//
    public List<ListView> viewList; // Tab页面列表
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表

    private List<HuiChipOrderAdapter> adapterList; // Tab页面列表

    @SuppressWarnings("deprecation")
	@AfterViews
    void initView() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_no_pay) {
                    if (currentPo != 0)
                        mPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_send) {
                    if (currentPo != 1)
                        mPager.setCurrentItem(1);
                } else if (checkedId == R.id.rb_completed) {
                    if (currentPo != 2)
                        mPager.setCurrentItem(2);
                }
            }
        });
        proPressList = new ArrayList<View>();
        noDataList = new ArrayList<TextView>();
        viewList = new ArrayList<ListView>();
        adapterList = new ArrayList<HuiChipOrderAdapter>();
        HuiOrderAdapter adapter = new HuiOrderAdapter();
        List<View> viewList = new ArrayList<View>();
        viewList.add(getPageView(0));
        viewList.add(getPageView(1));
        viewList.add(getPageView(2));
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
                        HTTPHelper.GetHuiMyChipsOrder(mIbpi, 0);
                    } else if (i == 1) {
                        HTTPHelper.GetHuiMyChipsOrder(mIbpi, 1);
                    } else if (i == 2) {
                        HTTPHelper.GetHuiMyChipsOrder(mIbpi, 2);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        adapter.setViewList(viewList);

    }

    int currentPo = 0;

    @Override
    public void onResume() {
        super.onResume();
        HTTPHelper.GetHuiMyChipsOrder(mIbpi, currentPo);
    }

    View getPageView(int flagTp) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_chip_order, null);
        HuiChipOrderAdapter adapter = new HuiChipOrderAdapter(this, flagTp);
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
            List<ChipsOrderBean> data = (List<ChipsOrderBean>) message;
            LogUtil.d("~~~众筹订单全部数据："+data.toString());
            adapterList.get(currentPo).setData(data);
            
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveChipsOrderList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };
}
