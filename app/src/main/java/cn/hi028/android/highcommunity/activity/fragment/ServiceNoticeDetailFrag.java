/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.TimeFormat;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.AutoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.adapter.SimplePageAdapter;
import cn.hi028.android.highcommunity.bean.NoticeDetailsBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：通知详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/29<br>
 */
@EFragment(resName = "frag_noticedetails")
public class ServiceNoticeDetailFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "ServiceNoticeDetailFrag";

    @ViewById(R.id.tv_NoticeDetails_title)
    TextView mTitle;
    @ViewById(R.id.tv_NoticeDetails_PublishTime)
    TextView mTime;
    @ViewById(R.id.tv_NoticeDetails_PublishName)
    TextView mName;
    @ViewById(R.id.tv_NoticeDetails_Content)
    TextView mContent;
    @ViewById(R.id.iv_NoticeDetails_Picture)
    AutoScrollViewPager mImage;
    @ViewById(R.id.tv_NoticeDetails_noData)
    TextView mNoData;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View mProgress;

    SimplePageAdapter mAdapter;
    NoticeDetailsBean mBean = new NoticeDetailsBean();

    @AfterViews
    void initView() {
        mProgress.setVisibility(View.VISIBLE);
        String id = getActivity().getIntent().getStringExtra(ServiceSecondAct.INTENTTAG);
        if (!TextUtils.isEmpty(id)) {
            HTTPHelper.GetNoticeDetail(mIbpi, id);
        } else {
            mNoData.setVisibility(View.VISIBLE);
        }
        mAdapter = new SimplePageAdapter(getActivity());
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mBean = (NoticeDetailsBean) message;
            mTitle.setText(mBean.getTitle());
            mTime.setText("发布时间:" + TimeFormat.TimedateFormat(Long.parseLong(mBean.getCreate_time()) * 1000));
            mName.setText("发布人:" + mBean.getPublish_man());
            mAdapter.setImageIdList(mBean.getPic());
            mAdapter.setInfiniteLoop(false);
            mImage.setAdapter(mAdapter);
            mContent.setText("    " + mBean.getContent());

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveNoticeDetails(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
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

    @Override
    public void onPause() {
        super.onPause();
        mImage.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        mImage.startAutoScroll();
    }
}
