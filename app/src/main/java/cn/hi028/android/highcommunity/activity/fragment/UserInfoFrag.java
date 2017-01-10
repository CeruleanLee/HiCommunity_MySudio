/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.bean.CertifiBean;
import cn.hi028.android.highcommunity.bean.PersonalInfoBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：个人资料<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/27<br>
 */
@EFragment(resName = "frag_userinfo")
public class UserInfoFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "UserInfoFrag";
    @ViewById(R.id.tv_UserInfo_title)
    TextView mTitle;
    @ViewById(R.id.tv_UserInfo_NickName)
    TextView mName;
    @ViewById(R.id.tv_UserInfo_UserAge)
    TextView mAge;
    @ViewById(R.id.tv_UserInfo_UserSex)
    TextView mSex;
    @ViewById(R.id.tv_UserInfo_UserIntro)
    TextView mIntro;
    @ViewById(R.id.ll_UserInfo_AuthZiliao)
    LinearLayout mAuth;
    @ViewById(R.id.tv_UserInfo_RightnMenu)
    TextView mRight;
    @ViewById(R.id.civ_UserInfo_Avatar)
    CircleImageView mAvatar;
    @ViewById(R.id.progress_UserInfo)
    View mProgress;
    PopupWindow mWaitingWindow;
    String uid;
    PersonalInfoBean mBean;
    BpiHttpHandler.IBpiHttpHandler mIbpiRepairCeri = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            if (id == 1002) {

                ECAlertDialog.buildAlert(getActivity(), "你还未认证,是否去认证?", "去认证", "取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mAuth = new Intent(getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
                        mAuth.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_SECOND_PERSONALAUTH);
                        startActivity(mAuth);
                    }
                }, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                }).show();
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
            }

        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (null == message)
                return;
            CertifiBean bean = (CertifiBean) message;
            if (bean.getStatus() != null && bean.getStatus().equals("1")) {
                Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftThirdAct.class));
                mIntent.putExtra(MenuLeftThirdAct.ACTIVITYTAG, Constacts.MENU_THIRD_AUTOINFO);
                startActivity(mIntent);
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast("用户认证正在审核中", 0);
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCertification(result);
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
    @AfterViews
    void initView() {
//        mProgress.setVisibility(View.VISIBLE);
        uid = getActivity().getIntent().getStringExtra(MenuLeftAct.INTENTTAG);
        String UserId = HighCommunityApplication.mUserInfo.getId() + "";
        if (UserId.equals(uid)) {
            mRight.setVisibility(View.VISIBLE);
            mAuth.setVisibility(View.VISIBLE);
        } else {
            mRight.setVisibility(View.GONE);
            mAuth.setVisibility(View.GONE);
        }
    }
    @Click(R.id.ll_UserInfo_AuthZiliao)
    public void authClick() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowPopupWindow(getActivity(), mAvatar, Gravity.CENTER, "正在验证用户信息");
            HTTPHelper.GetRepairCertification(mIbpiRepairCeri, HighCommunityApplication.mUserInfo.getId() + "");
        }
    }

    @Override
    public void onResume() {
        if (!TextUtils.isEmpty(uid)) {
            HTTPHelper.getPersonalInfo(mIbpi, uid);
        }
        super.onResume();
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mBean = (PersonalInfoBean) message;
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mAvatar);
            mName.setText(mBean.getNick());
            mAge.setText(mBean.getAge());
            if (mBean.getSex().equals("1")) {
                mSex.setText("女");
            } else {
                mSex.setText("男");
            }
            mIntro.setText(mBean.getSign());
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolvePersonalInfo(result);
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

    @Click(R.id.iv_UserInfo_back)
    void back() {
        getActivity().finish();
    }

    @Click(R.id.tv_UserInfo_RightnMenu)
    public  void write() {
        Intent mModify = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
        mModify.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_PERSONAL);
        mModify.putExtra(MenuLeftSecondAct.INTENTTAG, mBean);
        startActivity(mModify);
    }
}
