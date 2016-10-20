/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.DhUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.CommunityMsgAdapter;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.adapter.NoticeAdapter;
import cn.hi028.android.highcommunity.bean.CommunityMsgBean;
import cn.hi028.android.highcommunity.bean.NoticeBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * @功能：消息中心页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-01-30<br>
 */
@EFragment(resName = "frag_message_center")
public class MessageCenterFrag_Forback extends BaseFragment {

    public static final String FRAGMENTTAG = "MessageCenterFrag";
    @ViewById(R.id.vp_MessageCenter_ViewPager)
    ViewPager mPager;// 页卡内容
    @ViewById(R.id.rg_Message_Center)
    RadioGroup rg;//
    /**当前页**/
    int currentPo = 0;
    public List<ListView> listViewList; // Tab页面列表
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表
    private List<BaseAdapter> adapterList; // Tab页面列表

    @AfterViews
    void initView() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_Message_System) {
                    if (currentPo != 0)
                        mPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_Message_Notice) {
                    if (currentPo != 1)
                        mPager.setCurrentItem(1);
                }
            }
        });
        proPressList = new ArrayList<View>();
        noDataList = new ArrayList<TextView>();
        listViewList = new ArrayList<ListView>();
        adapterList = new ArrayList<BaseAdapter>();
        HuiOrderAdapter adapter = new HuiOrderAdapter();//与我相关用
        List<View> viewList = new ArrayList<View>();
        //将与我相关和系统消息添加进viewlist
        viewList.add(getRelatedList());
        viewList.add(getPageView());
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
                // 如果页面滑动的时候 adapter里面的数据是空的 就访问接口获取数据  与我相关是0  系统消息是1
                if (adapterList.get(i).getCount() == 0) {
                    if (i == 0) {
                        HTTPHelper.GetRelatedMsg(mRelateIbpi);
                    } else if (i == 1) {
                        HTTPHelper.GetNotice(mIbpi, "2", HighCommunityApplication.mUserInfo.getV_id() + "", "0");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        adapter.setViewList(viewList);
        HTTPHelper.GetRelatedMsg(mRelateIbpi);
    }
/**应该是系统消息的view**/
    View getPageView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_chip_order, null);
        NoticeAdapter adapter = new NoticeAdapter(getActivity());
        PullToRefreshListView ptfl = (PullToRefreshListView) view.findViewById(R.id.lv_list);
        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        ptfl.getRefreshableView().setDivider(getResources().getDrawable(R.drawable.diliver_related_msg));
        ptfl.getRefreshableView().setDividerHeight(DhUtil.dip2px(getActivity(), 0.5f));
        mProgress.setVisibility(View.VISIBLE);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        proPressList.add(mProgress);
        noDataList.add(mNodata);
        ListView lv_list = ptfl.getRefreshableView();
        ptfl.setMode(PullToRefreshBase.Mode.DISABLED);
        lv_list.setAdapter(adapter);
        adapterList.add(adapter);
        listViewList.add(lv_list);
        return view;
    }
/**返回与我相关的view**/
    View getRelatedList() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_chip_order, null);
        CommunityMsgAdapter adapter = new CommunityMsgAdapter(getActivity());
        PullToRefreshListView ptfl = (PullToRefreshListView) view.findViewById(R.id.lv_list);
        View mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        ptfl.getRefreshableView().setDivider(getResources().getDrawable(R.drawable.diliver_related_msg));
        ptfl.getRefreshableView().setDividerHeight(DhUtil.dip2px(getActivity(), 0.5f));
        mProgress.setVisibility(View.GONE);
        TextView mNodata = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        proPressList.add(mProgress);
        noDataList.add(mNodata);
        ListView lv_list = ptfl.getRefreshableView();
        lv_list.setEmptyView(mNodata);
        ptfl.setMode(PullToRefreshBase.Mode.DISABLED);
        lv_list.setAdapter(adapter);
        adapterList.add(adapter);
        listViewList.add(lv_list);
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
            List<NoticeBean> data = (List<NoticeBean>) message;
            ((NoticeAdapter) adapterList.get(1)).AddNewData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveNotice(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };
    BpiHttpHandler.IBpiHttpHandler mRelateIbpi = new BpiHttpHandler.IBpiHttpHandler() {
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
            List<CommunityMsgBean> data = (List<CommunityMsgBean>) message;
            ((CommunityMsgAdapter) adapterList.get(0)).setMlist(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveRelated(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };
}
