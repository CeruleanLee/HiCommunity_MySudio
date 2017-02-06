/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.RegexValidateUtil;

/**
 * @功能：忘记密码<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/17<br>
 */
public class ForgetPsdFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "ForgetPsdFrag";
    private View mFragmeView;
    private EditText mPhone, mIdnetfyCode, mPassword, mPsdRepeat;
    private TextView mGetIdentfy, mRegist;
    private onCounter mCounter;
    private PopupWindow mWindow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmeView = inflater.inflate(
                R.layout.frag_forget, null);
        return mFragmeView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView() {
        mPhone = (EditText) mFragmeView.findViewById(R.id.et_forget_phone);
        mIdnetfyCode = (EditText) mFragmeView.findViewById(R.id.et_forget_identifycode);
        mPassword = (EditText) mFragmeView.findViewById(R.id.et_forget_password);
        mPsdRepeat = (EditText) mFragmeView.findViewById(R.id.et_forget_psdrepeat);
        mGetIdentfy = (TextView) mFragmeView.findViewById(R.id.tv_forget_getIdentyCode);
        mRegist = (TextView) mFragmeView.findViewById(R.id.tv_forget_button);
        mGetIdentfy.setOnClickListener(mListener);
        mRegist.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_forget_getIdentyCode:
                    if (RegexValidateUtil.checkMobileNumber(mPhone.getText().toString())) {
                        mCounter = new onCounter(60000, 1000);
                        mCounter.start();
                        HTTPHelper.Send(mIbpi, mPhone.getText().toString(), "2");
                        mWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mPhone, Gravity.CENTER);
                    } else {
                        HighCommunityUtils.GetInstantiation().ShowToast("请输入正确的电话号码", 0);
                    }
                    break;
                case R.id.tv_forget_button:
                    Register(mPhone.getText().toString(), mPassword.getText().toString(), mIdnetfyCode.getText().toString());
                    break;
            }
        }
    };

    private void Register(String username, String password, String IdentfyCode) {
        if (TextUtils.isEmpty(username)) {
            HighCommunityUtils.GetInstantiation().ShowToast(getResources().getString(R.string.toast_username_enpty), 0);
            return;
        } else if (TextUtils.isEmpty(password)) {
            HighCommunityUtils.GetInstantiation().ShowToast(getResources().getString(R.string.toast_password_enpty), 0);
            return;
        } else if (!password.equals(mPsdRepeat.getText().toString())) {
            HighCommunityUtils.GetInstantiation().ShowToast(getResources().getString(R.string.toast_password_NotSame), 0);
            return;
        } else if (TextUtils.isEmpty(IdentfyCode)) {
            HighCommunityUtils.GetInstantiation().ShowToast(getResources().getString(R.string.toast_Identfy_enpty), 0);
            return;
        }
        mWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mPhone, Gravity.CENTER);
        HTTPHelper.Forget(mForgetIbpi, username, password, IdentfyCode);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
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
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
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

    public class onCounter extends CountDownTimer {

        public onCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mGetIdentfy.setClickable(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mGetIdentfy.setText((millisUntilFinished / 1000)
                    + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            mGetIdentfy.setText("获取验证码");
            mGetIdentfy.setClickable(true);
        }

    }

    BpiHttpHandler.IBpiHttpHandler mForgetIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }

        }

        @Override
        public void onSuccess(Object message) {
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
            if (null == message)
                return;
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
