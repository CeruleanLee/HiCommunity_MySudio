/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MainActivity;
import cn.hi028.android.highcommunity.bean.LabelBean;
import cn.hi028.android.highcommunity.bean.UserInfoBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：欢迎界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/9<br>
 */
public class WelcomeFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "WelcomeFrag";
	private View mFragmeView;
	private FragmentManager fm;
	private Handler mHandler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mFragmeView == null) {
			initView();
		}
		ViewGroup parent = (ViewGroup) mFragmeView.getParent();
		if (parent != null)
			parent.removeView(mFragmeView);
		return mFragmeView;
	}

	private void initView() {
		mFragmeView = LayoutInflater.from(getActivity()).inflate(
				R.layout.frag_welcome, null);
		mFragmeView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
		fm = getFragmentManager();
		toLogin();
	}

	public void toLogin() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				String token = HighCommunityApplication.share.getString(
						Constacts.APPTOKEN, "");
				if (TextUtils.isEmpty(token)) {
					if (!getActivity().isFinishing())
						toLoginFragment();
				} else {
					HTTPHelper.Token(mIbpi, token);
				}
			}
		}, 2000);
	}

	public static ObjectAnimator nope(View view) {
		int delta = view.getResources().getDimensionPixelOffset(
				R.dimen.spacing_medium);
		PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(
				View.TRANSLATION_X, Keyframe.ofFloat(0f, 0),
				Keyframe.ofFloat(.10f, -delta), Keyframe.ofFloat(.26f, delta),
				Keyframe.ofFloat(.42f, -delta), Keyframe.ofFloat(.58f, delta),
				Keyframe.ofFloat(.74f, -delta), Keyframe.ofFloat(.90f, delta),
				Keyframe.ofFloat(1f, 0f));

		return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX)
				.setDuration(500);
	}

	public static ObjectAnimator tada(View view) {
		return tada(view, 1f);
	}

	public static ObjectAnimator tada(View view, float shakeFactor) {

		PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(
				View.SCALE_X, Keyframe.ofFloat(0f, 1f),
				Keyframe.ofFloat(.1f, .9f), Keyframe.ofFloat(.2f, .9f),
				Keyframe.ofFloat(.3f, 1.1f), Keyframe.ofFloat(.4f, 1.1f),
				Keyframe.ofFloat(.5f, 1.1f), Keyframe.ofFloat(.6f, 1.1f),
				Keyframe.ofFloat(.7f, 1.1f), Keyframe.ofFloat(.8f, 1.1f),
				Keyframe.ofFloat(.9f, 1.1f), Keyframe.ofFloat(1f, 1f));

		PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(
				View.SCALE_Y, Keyframe.ofFloat(0f, 1f),
				Keyframe.ofFloat(.1f, .9f), Keyframe.ofFloat(.2f, .9f),
				Keyframe.ofFloat(.3f, 1.1f), Keyframe.ofFloat(.4f, 1.1f),
				Keyframe.ofFloat(.5f, 1.1f), Keyframe.ofFloat(.6f, 1.1f),
				Keyframe.ofFloat(.7f, 1.1f), Keyframe.ofFloat(.8f, 1.1f),
				Keyframe.ofFloat(.9f, 1.1f), Keyframe.ofFloat(1f, 1f));

		PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(
				View.ROTATION, Keyframe.ofFloat(0f, 0f),
				Keyframe.ofFloat(.1f, -3f * shakeFactor),
				Keyframe.ofFloat(.2f, -3f * shakeFactor),
				Keyframe.ofFloat(.3f, 3f * shakeFactor),
				Keyframe.ofFloat(.4f, -3f * shakeFactor),
				Keyframe.ofFloat(.5f, 3f * shakeFactor),
				Keyframe.ofFloat(.6f, -3f * shakeFactor),
				Keyframe.ofFloat(.7f, 3f * shakeFactor),
				Keyframe.ofFloat(.8f, -3f * shakeFactor),
				Keyframe.ofFloat(.9f, 3f * shakeFactor),
				Keyframe.ofFloat(1f, 0));

		return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX,
				pvhScaleY, pvhRotate).setDuration(1000);
	}

//	public void startAmin() {
//		final ImageView imageview = (ImageView) mFragmeView
//				.findViewById(R.id.img_welcome_logo);
//		final ImageView zhshView = (ImageView) mFragmeView
//				.findViewById(R.id.img_welcome_zhsh);
//		zhshView.setVisibility(View.INVISIBLE);
//		imageview.setVisibility(View.VISIBLE);
//		Animation animat = AnimationUtils.loadAnimation(getActivity(),
//				R.anim.push_from_bottom);
//		imageview.startAnimation(animat);
//		animat.setAnimationListener(new Animation.AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				ObjectAnimator animator = tada(imageview);
//				animator.setRepeatCount(1);
//				animator.start();
//
//				ObjectAnimator nopeAnimator = nope(imageview);
//				nopeAnimator.setRepeatCount(1);
//				nopeAnimator.start();
//				nopeAnimator.addListener(new Animator.AnimatorListener() {
//					@Override
//					public void onAnimationStart(Animator animation) {
//
//					}
//
//					@Override
//					public void onAnimationEnd(Animator animation) {
//						zhshView.setVisibility(View.VISIBLE);
//						Animation animat = AnimationUtils.loadAnimation(
//								getActivity(), R.anim.push_from_bottom);
//						zhshView.startAnimation(animat);
//						animat.setAnimationListener(new Animation.AnimationListener() {
//							@Override
//							public void onAnimationStart(Animation animation) {
//
//							}
//
//							@Override
//							public void onAnimationEnd(Animation animation) {
//								toLogin();
//							}
//
//							@Override
//							public void onAnimationRepeat(Animation animation) {
//
//							}
//						});
//					}
//
//					@Override
//					public void onAnimationCancel(Animator animation) {
//
//					}
//
//					@Override
//					public void onAnimationRepeat(Animator animation) {
//
//					}
//				});
//
//			}
//		});
//		/** 开始动画 */
//		// animation.startNow();
//	}

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (!getActivity().isFinishing())
						toLoginFragment();
				}
			}, 3 * 100);
		}

		@Override
		public void onSuccess(Object message) {
			if (null == message)
				return;
			HighCommunityApplication.mUserInfo = (UserInfoBean) message;
			HighCommunityApplication.SaveUser();
			if (!TextUtils.isEmpty(HighCommunityApplication.mUserInfo
					.getToken()))
				HighCommunityApplication.share
						.edit()
						.putString(
								HighCommunityApplication.mUserInfo.getToken(),
								"").commit();
			HTTPHelper.Getlabel(mLabelIbpi,
					HighCommunityApplication.mUserInfo.getId() + "",
					HighCommunityApplication.mUserInfo.getToken());

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

	BpiHttpHandler.IBpiHttpHandler mLabelIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {

		}

		@Override
		public void onSuccess(Object message) {
			if (message != null)
				Constacts.mCustomLabel = (List<LabelBean>) message;
			Intent goMainIntent = new Intent(getActivity(), MainActivity.class);
			startActivity(goMainIntent);
			// getActivity().overridePendingTransition(R.anim.alpha_in,
			// R.anim.alpha_out);
			getActivity().finish();
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveLabels(result);
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

	void toLoginFragment() {
		FragmentTransaction ft = fm.beginTransaction();
		LoginFrag mLoginFragment = new LoginFrag();
		ft.replace(R.id.ll_welcomeAct, mLoginFragment,
				mLoginFragment.FRAGMENTTAG);
		// fm.addToBackStack("");
		ft.commit();
		// ft.setCustomAnimations(R.anim.alpha_in, R.anim.alpha_out);

	}

}
