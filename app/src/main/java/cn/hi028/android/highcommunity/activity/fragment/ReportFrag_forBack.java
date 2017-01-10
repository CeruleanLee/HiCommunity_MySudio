/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：举报<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/22<br>
 */
@EFragment(resName = "frag_report")
public class ReportFrag_forBack extends BaseFragment {

    public static final String FRAGMENTTAG = "ReportFrag";

    @ViewById(R.id.tv_report_submit)
    TextView submit;
    @ViewById(R.id.cb_reportResone_type1)
    CheckBox mType1;
    @ViewById(R.id.cb_reportResone_type2)
    CheckBox mType2;
    @ViewById(R.id.cb_reportResone_type3)
    CheckBox mType3;
    @ViewById(R.id.cb_reportResone_type4)
    CheckBox mType4;
    @ViewById(R.id.cb_reportResone_type5)
    CheckBox mType5;

    String type = "";
    String mid = "";
    PopupWindow mWaitingWindow;

    @AfterViews
    void initView() {
        mid = getActivity().getIntent().getStringExtra(CommunityDetailAct.INTENTTAG);
        mType1.setOnCheckedChangeListener(mCheckListener);
        mType2.setOnCheckedChangeListener(mCheckListener);
        mType3.setOnCheckedChangeListener(mCheckListener);
        mType4.setOnCheckedChangeListener(mCheckListener);
        mType5.setOnCheckedChangeListener(mCheckListener);
    }

    CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton v, boolean b) {
            switch (v.getId()) {
                case R.id.cb_reportResone_type1:
                    type = "1";
                    mType2.setChecked(false);
                    mType3.setChecked(false);
                    mType4.setChecked(false);
                    mType5.setChecked(false);
                    break;
                case R.id.cb_reportResone_type2:
                    type = "2";
                    mType1.setChecked(false);
                    mType3.setChecked(false);
                    mType4.setChecked(false);
                    mType5.setChecked(false);
                    break;
                case R.id.cb_reportResone_type3:
                    type = "3";
                    mType2.setChecked(false);
                    mType1.setChecked(false);
                    mType4.setChecked(false);
                    mType5.setChecked(false);
                    break;
                case R.id.cb_reportResone_type4:
                    type = "4";
                    mType2.setChecked(false);
                    mType3.setChecked(false);
                    mType1.setChecked(false);
                    mType5.setChecked(false);
                    break;
                case R.id.cb_reportResone_type5:
                    type = "5";
                    mType2.setChecked(false);
                    mType3.setChecked(false);
                    mType4.setChecked(false);
                    mType1.setChecked(false);
                    break;
            }
        }
    };

    @Click(R.id.tv_report_submit)
    void submit() {
        if (TextUtils.isEmpty(type)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择举报原因", 0);
            return;
        } else if (HighCommunityApplication.mUserInfo.getId() == 0) {
            HighCommunityUtils.GetInstantiation().ShowToast("请先登录再操作", 0);
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mType5, Gravity.CENTER);
        HTTPHelper.Report(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", mid, type);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
            getActivity().finish();
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
    };

}
