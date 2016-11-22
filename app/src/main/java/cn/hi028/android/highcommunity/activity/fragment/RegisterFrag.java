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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.bean.UserInfoBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.RegexValidateUtil;

/**
 * @功能：注册界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/10<br>
 */
public class RegisterFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "RegisterFrag";

	private View mFragmeView;
	private EditText mPhone, mIdnetfyCode, mPassword, mPsdRepeat;
	private TextView mGetIdentfy, mRegist, mAgree;
	private onCounter mCounter;

	private PopupWindow mWindow;

	private CheckBox mAgreebox;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mFragmeView = inflater.inflate(R.layout.frag_register, null);
		return mFragmeView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		// mFragmeView = LayoutInflater.from(getActivity()).inflate(
		// R.layout.frag_register, null);
		mPhone = (EditText) mFragmeView.findViewById(R.id.et_register_phone);
		mIdnetfyCode = (EditText) mFragmeView
				.findViewById(R.id.et_register_identifycode);
		mPassword = (EditText) mFragmeView
				.findViewById(R.id.et_register_password);
		mPsdRepeat = (EditText) mFragmeView
				.findViewById(R.id.et_register_psdrepeat);
		mGetIdentfy = (TextView) mFragmeView
				.findViewById(R.id.tv_register_getIdentyCode);
		mRegist = (TextView) mFragmeView.findViewById(R.id.tv_register_button);
		mAgree = (TextView) mFragmeView.findViewById(R.id.tv_register_agree);
		mAgreebox = (CheckBox) mFragmeView.findViewById(R.id.cn_mb);

		mGetIdentfy.setOnClickListener(mListener);
		mRegist.setOnClickListener(mListener);
		mAgree.setOnClickListener(mListener);
	}

	View.OnClickListener mListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.tv_register_getIdentyCode:
				if (RegexValidateUtil.checkMobileNumber(mPhone.getText().toString())) {
					mCounter = new onCounter(60000, 1000);
					mCounter.start();
					HTTPHelper.Send(mIbpi, mPhone.getText().toString(), "1");
					mWindow = HighCommunityUtils.GetInstantiation()
							.ShowWaittingPopupWindow(getActivity(), mPhone,
									Gravity.CENTER);
				} else {
					HighCommunityUtils.GetInstantiation().ShowToast(
							"请输入正确的电话号码", 0);
				}
				break;
			case R.id.tv_register_button:
				if (mAgreebox.isChecked()) {
					Register(mPhone.getText().toString(), mPassword.getText()
							.toString(), mIdnetfyCode.getText().toString());
				}else{
					Toast.makeText(getActivity(),"请同意《嗨社区服务协议》",Toast.LENGTH_SHORT).show();

				}
				break;
			case R.id.tv_register_agree:
				BrowseActivity.toBrowseActivity(getActivity(), "用户协议",
						"http://028hi.cn/api/default/agreement.html");
				break;

			}
		}
	};

	private void Register(String username, String password, String IdentfyCode) {
		if (TextUtils.isEmpty(username)) {
			HighCommunityUtils.GetInstantiation().ShowToast(
					getResources().getString(R.string.toast_username_enpty), 0);
			return;
		} else if (TextUtils.isEmpty(password)) {
			HighCommunityUtils.GetInstantiation().ShowToast(
					getResources().getString(R.string.toast_password_enpty), 0);
			return;
		} else if (!password.equals(mPsdRepeat.getText().toString())) {
			HighCommunityUtils.GetInstantiation().ShowToast(
					getResources().getString(R.string.toast_password_NotSame),
					0);
			return;
		} else if (TextUtils.isEmpty(IdentfyCode)) {
			HighCommunityUtils.GetInstantiation().ShowToast(
					getResources().getString(R.string.toast_Identfy_enpty), 0);
			return;
		}
		mWindow = HighCommunityUtils.GetInstantiation()
				.ShowWaittingPopupWindow(getActivity(), mPhone, Gravity.CENTER);
		HTTPHelper.Register(mRegisterIbpi, username, password, IdentfyCode);
	}

	public class onCounter extends CountDownTimer {

		public onCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			mGetIdentfy.setClickable(false);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mGetIdentfy.setText((millisUntilFinished / 1000) + "秒后重新获取");
		}

		@Override
		public void onFinish() {
			mGetIdentfy.setText("获取验证码");
			mGetIdentfy.setClickable(true);
		}

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
			HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
					0);
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
	};

	BpiHttpHandler.IBpiHttpHandler mRegisterIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			if (null != mWindow && mWindow.isShowing()) {
				mWindow.dismiss();
			}
			HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
					0);
		}

		@Override
		public void onSuccess(Object message) {
			if (null != mWindow && mWindow.isShowing()) {
				mWindow.dismiss();
			}
			if (null == message)
				return;
			HighCommunityApplication.mUserInfo = (UserInfoBean) message;
			HighCommunityApplication.mUserInfo.setV_id(0);
			HighCommunityApplication.SaveUser();
			VallageAct.toStartAct(getActivity(), 0, true);
			getActivity().finish();
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveLogin(result);
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
	};

}
