/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.mob.tools.utils.UIHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.HashMap;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MainActivity;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.activity.WelcomeSecondAct;
import cn.hi028.android.highcommunity.bean.LabelBean;
import cn.hi028.android.highcommunity.bean.UserInfoBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.login.LoginApi;
import cn.hi028.android.highcommunity.utils.login.OnLoginListener;
import cn.hi028.android.highcommunity.utils.login.UserInfo;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @功能：登陆界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/9<br>
 */
public class LoginFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "LoginFrag";
    private View mFragmeView;
    private EditText mUserName, mPassword;
    private TextView mLogin, mForget, mTourist;
    private LinearLayout weixin, qq, weibo;
    private TextView mTitle, mRightButton;
    ImageView mTopLeft,mTopLeftCount;
    private PopupWindow mWatingWindow;

    private static final String TAG = "LoginFrag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmeView == null) {
            initView();
        }
        ViewGroup parent = (ViewGroup) mFragmeView.getParent();
        if (parent != null)
            parent.removeView(mFragmeView);
        return mFragmeView;
    }

    private void initView() {
        if (HighCommunityApplication.share != null) {
            HighCommunityApplication.share.edit().putInt("VILLAGEID", 0).commit();
        }
        mFragmeView = LayoutInflater.from(getActivity()).inflate(
                R.layout.frag_login, null);
        mTitle = (TextView) mFragmeView.findViewById(R.id.tv_mainlevel_title);
        mRightButton = (TextView) mFragmeView.findViewById(R.id.tv_mainlevel_RightnMenu);
        mTopLeft=(ImageView) mFragmeView.findViewById(R.id.tv_mainlevel_LeftButton);
        mTopLeftCount=(ImageView) mFragmeView.findViewById(R.id.iv_mainlevel_LeftNewMessage);
        ShareSDK.initSDK(getActivity());
        mUserName = (EditText) mFragmeView.findViewById(R.id.ET_Login_UserName);
        mPassword = (EditText) mFragmeView.findViewById(R.id.ET_Login_Passowrd);
        mLogin = (TextView) mFragmeView.findViewById(R.id.TV_login_button);
        mForget = (TextView) mFragmeView.findViewById(R.id.TV_login_forgetPassword);
        mTourist = (TextView) mFragmeView.findViewById(R.id.TV_login_tourist);
        weixin = (LinearLayout) mFragmeView.findViewById(R.id.ll_login_thirdPary_weixin);
        qq = (LinearLayout) mFragmeView.findViewById(R.id.ll_login_thirdPary_qq);
        weibo = (LinearLayout) mFragmeView.findViewById(R.id.ll_login_thirdPary_weibo);
//测试信息
//        mUserName.setText("18780209794");
//        mPassword.setText("123456");
//        mTitle.setText("登录");
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.tv_login_login));
        mRightButton.setVisibility(View.VISIBLE);
        mTopLeft.setVisibility(View.GONE);mTopLeftCount.setVisibility(View.GONE);
        mLogin.setOnClickListener(mListener);
        mRightButton.setOnClickListener(mListener);
        mForget.setOnClickListener(mListener);
        mTourist.setOnClickListener(mListener);
        weixin.setOnClickListener(mListener);
        qq.setOnClickListener(mListener);
        weibo.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.TV_login_button:
                    login(mUserName.getText().toString(), mPassword.getText().toString());
                    break;
                case R.id.TV_login_forgetPassword:
                    Intent forget = new Intent(getActivity(), GeneratedClassUtils.get(WelcomeSecondAct.class));
                    forget.putExtra(WelcomeSecondAct.ACTIVITYTAG, "忘记密码");
                    startActivity(forget);
                    break;
                case R.id.TV_login_tourist:
                    VallageAct.toStartAct(getActivity(), 0, true);
                    break;
                case R.id.ll_login_thirdPary_weixin:
                    login(Wechat.NAME);
                    break;
                case R.id.ll_login_thirdPary_qq:
                    login(QQ.NAME);
                    break;
                case R.id.ll_login_thirdPary_weibo:
                    login(SinaWeibo.NAME);
                    break;
                case R.id.tv_mainlevel_RightnMenu:
                    Intent intent = new Intent(getActivity(), GeneratedClassUtils.get(WelcomeSecondAct.class));
                    intent.putExtra(WelcomeSecondAct.ACTIVITYTAG, "注册新帐号");
                    startActivity(intent);
                    break;
            }
        }
    };

    private void login(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            HighCommunityUtils.GetInstantiation().ShowToast(getResources().getString(R.string.toast_username_enpty), 0);
            return;
        } else if (TextUtils.isEmpty(password)) {
            HighCommunityUtils.GetInstantiation().ShowToast(getResources().getString(R.string.toast_password_enpty), 0);
            return;
        }
        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mFragmeView, Gravity.CENTER);
        HTTPHelper.Login(mLoginHandler, username, password, "0", null, null);
    }

    // 登陆处理
    BpiHttpHandler.IBpiHttpHandler mLoginHandler = new BpiHttpHandler.IBpiHttpHandler() {

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void onSuccess(Object message) {
        	LogUtil.d("~~~messageonSuccess"+message.toString());
            if (null == message)
                return;
            LogUtil.d("~~~message"+message.toString());
            HighCommunityApplication.mUserInfo = (UserInfoBean) message;
            HighCommunityApplication.mUserInfo.setV_id(HighCommunityApplication.mUserInfo.getV_id());
            HighCommunityApplication.SaveUser();
            if (!TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
                HighCommunityApplication.share.edit().putString(Constacts.APPTOKEN, HighCommunityApplication.mUserInfo.getToken()).commit();
            }
            HTTPHelper.Getlabel(mLabelIbpi, HighCommunityApplication.mUserInfo.getId() + "", HighCommunityApplication.mUserInfo.getToken());
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveLogin(result);
        }

        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void cancleAsyncTask() {
            mWatingWindow.dismiss();
        }
    };

    BpiHttpHandler.IBpiHttpHandler mLabelIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
            if (HighCommunityApplication.mUserInfo.getV_id() == 0) {
                VallageAct.toStartAct(getActivity(), 0, true);
            } else {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }

        @Override
        public void onSuccess(Object message) {
            if (message != null)
                Constacts.mCustomLabel = (List<LabelBean>) message;
            mWatingWindow.dismiss();
            if (HighCommunityApplication.mUserInfo.getV_id() == 0) {
                VallageAct.toStartAct(getActivity(), 0, true);
            } else {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveLabels(result);
        }

        @Override
        public void cancleAsyncTask() {
            mWatingWindow.dismiss();
        }
    };

    /**
     * 根据平台登录</br>
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /*
     * 演示执行第三方登录/注册的方法
     * <p>
     * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
     *
     * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
     */
    private void login(final String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(Platform platForm, String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
//解析部分用户资料字段

//                HighCommunityUtils.GetInstantiation().ShowToast("授权成功", 0);
                String id, name, description, profile_image_url, typeStr = "1";
//                id = res.get("id").toString();//ID
//                name = res.get("name").toString();//用户名
//                description = res.get("description").toString();//描述
//                profile_image_url = res.get("profile_image_url").toString();//头像链接
                if (platformName.equals(QQ.NAME)) {
                    typeStr = "2";
                } else if (platformName.equals(SinaWeibo.NAME)) {
                    typeStr = "3";
                } else if (platformName.equals(Wechat.NAME)) {
                    typeStr = "1";
                }
                PlatformDb platDB = platForm.getDb();//获取数平台数据DB
                //通过DB获取各种数据
//                platDB.getToken();
//                platDB.getUserGender();
//                platDB.getUserIcon();
//                platDB.getUserName();

                mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mFragmeView, Gravity.CENTER);
                HTTPHelper.Login(mLoginHandler, null, null, typeStr + "", platDB.getUserId(), platDB.getUserName());
                return false;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭

                HighCommunityUtils.GetInstantiation().ShowToast("授权成功", 0);
                return true;
            }
        });
        api.login(getActivity());
    }


}
