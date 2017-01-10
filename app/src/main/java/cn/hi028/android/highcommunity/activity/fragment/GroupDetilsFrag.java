/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.GroupDataAct;
import cn.hi028.android.highcommunity.activity.GroupMessageAct;
import cn.hi028.android.highcommunity.adapter.GroupGrideAdapter;
import cn.hi028.android.highcommunity.bean.GroupDetailsBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：群资料 （关注前/关注后）<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/18<br>
 */
@EFragment(resName = "frag_groupdetils")
public class GroupDetilsFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "GroupDetilsFrag";

    @ViewById(R.id.tv_transTitle_name)
    TextView mTitle;
    @ViewById(R.id.ptrgv_group_member_grideview)
    PullToRefreshGridView mGrideView;
    @ViewById(R.id.tv_groupdetils_title)
    TextView mFirstName;
    @ViewById(R.id.iv_transTitle_before)
    TextView mBefore;
    @ViewById(R.id.iv_transTitle_after)
    TextView mAfter;
    @ViewById(R.id.civ_groupdetils_avatar)
    CircleImageView mAvatar;
    @ViewById(R.id.tv_groupdetils_name)
    TextView mName;
    @ViewById(R.id.tv_groupdetils_creater)
    TextView mCreater;
    @ViewById(R.id.tv_groupdetils_introduce)
    TextView mIntroduce;

    GroupGrideAdapter mAdapter;
    GroupDetailsBean mBean = new GroupDetailsBean();
    PopupWindow mWaitingWindow;

    @AfterViews
    void initView() {
        mBefore.setVisibility(View.VISIBLE);
        mAfter.setVisibility(View.GONE);
        mTitle.setText("群资料");
        mAdapter = new GroupGrideAdapter(getActivity());
        mGrideView.setAdapter(mAdapter);
        mGrideView.setMode(PullToRefreshBase.Mode.DISABLED);
        String gid = getActivity().getIntent().getStringExtra(GroupDataAct.INTENTTAG);
        HTTPHelper.GroupDetail(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", gid);
        mBefore.setOnClickListener(mClicklistener);
        mAfter.setOnClickListener(mClicklistener);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mBean = (GroupDetailsBean) message;
            if (mBean.getIsin().equals("1")) {
                mAfter.setVisibility(View.VISIBLE);
                mBefore.setVisibility(View.GONE);
            } else {
                mAfter.setVisibility(View.GONE);
                mBefore.setVisibility(View.VISIBLE);
            }
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getPic(), mAvatar);
            mFirstName.setText(mBean.getName());
            mName.setText("名称:" + mBean.getName());
            mCreater.setText("创建者:" + mBean.getOw_nick());
            mIntroduce.setText("" + mBean.getIntro());
            if (mBean.getMembers() != null && mBean.getMembers().size() > 0) {
                mAdapter.AddNewData(mBean.getMembers());
                HighCommunityUtils.GetInstantiation().setGridViewHeightBasedOnChildren(mGrideView, mAdapter, 6);
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveGridDetails(result);
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

    View.OnClickListener mClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
            switch (view.getId()) {
                case R.id.iv_transTitle_before:
                    HTTPHelper.AttentionGroup(new BpiHttpHandler.IBpiHttpHandler() {
                        @Override
                        public void onError(int id, String message) {
                            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                            mWaitingWindow.dismiss();
                        }

                        @Override
                        public void onSuccess(Object message) {
                            mWaitingWindow.dismiss();
                            mBean.setIsin("1");
                            mAfter.setVisibility(View.VISIBLE);
                            mBefore.setVisibility(View.GONE);
                            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
                        }

                        @Override
                        public Object onResolve(String result) {
                            return result;
                        }

                        @Override
                        public void setAsyncTask(AsyncTask asyncTask) {

                        }

                        @Override
                        public void cancleAsyncTask() {
                            mWaitingWindow.dismiss();
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
                    }, HighCommunityApplication.mUserInfo.getId() + "", mBean.getGid() + "");
                    break;
                case R.id.iv_transTitle_after:
                    HTTPHelper.CancelAttention(new BpiHttpHandler.IBpiHttpHandler() {
                        @Override
                        public void onError(int id, String message) {
                            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                            mWaitingWindow.dismiss();
                        }

                        @Override
                        public void onSuccess(Object message) {
                            mWaitingWindow.dismiss();
                            mBean.setIsin("0");
                            mAfter.setVisibility(View.GONE);
                            mBefore.setVisibility(View.VISIBLE);
                            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
                        }

                        @Override
                        public Object onResolve(String result) {
                            return result;
                        }

                        @Override
                        public void setAsyncTask(AsyncTask asyncTask) {

                        }

                        @Override
                        public void cancleAsyncTask() {
                            mWaitingWindow.dismiss();
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
                    }, HighCommunityApplication.mUserInfo.getId() + "", mBean.getGid() + "");
                    break;
            }
        }
    };

    @Click(R.id.iv_transTitle_back)
    void back() {
        getActivity().finish();
    }

    @Click(R.id.tv_groupdetils_message)
    void message() {
        Intent mGroup = new Intent(getActivity(), GeneratedClassUtils.get(GroupMessageAct.class));
        mGroup.putExtra(GroupMessageAct.ACTIVITYTAG, mBean.getPic());
        mGroup.putExtra(GroupMessageAct.INTENTTAG, mBean.getGid() + "");
        startActivity(mGroup);
    }
}
