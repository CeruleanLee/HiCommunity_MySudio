/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;


public  class MyLoadingLayout extends LoadingLayout {

	private Animation loadAnimation,rotaAnim,loadAnimation2;

	public MyLoadingLayout(Context context, Mode mode,Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);

		mHeaderImage.setImageResource(R.drawable.img_refresh_sun);
		loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_top);
		loadAnimation2 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_top);
		rotaAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rota);


	}

	@Override
	protected int getDefaultDrawableResId() {
		// TODO Auto-generated method stub
		return R.drawable.img_refresh_sun;
	}

	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPullImpl(float scaleOfLayout) {
		// TODO Auto-generated method stub

//		LayoutParams layoutParams = (LayoutParams) mInnerLayout.getLayoutParams();
//		// if (mUseIntrinsicAnimation) {
//		// ((AnimationDrawable) mHeaderImage.getDrawable()).start();
//		TranslateAnimation translateAnimation = new TranslateAnimation(0,0 ,-layoutParams.width, 0);
//		translateAnimation.setDuration(1000);
//		translateAnimation.setRepeatCount(TranslateAnimation.INFINITE);
//		translateAnimation.setRepeatMode(TranslateAnimation.RESTART);
//		mHeaderImage.startAnimation(translateAnimation);

		mHeaderImage.startAnimation(loadAnimation2);


	}

	//下来刷新
	@Override
	protected void pullToRefreshImpl() {
		mHeaderImage.setVisibility(View.VISIBLE);
	}

	//正在刷新回调
	@Override
	protected void refreshingImpl() {
		mHeaderImage.setVisibility(View.VISIBLE);
//		mHeaderImage.startAnimation(loadAnimation);
	}

	//释放刷新
	@Override
	protected void releaseToRefreshImpl() {
//		mHeaderImage.startAnimation(loadAnimation);
		mHeaderImage.startAnimation(rotaAnim);
	}

	//重新设置
	@Override
	protected void resetImpl() {
		mHeaderImage.clearAnimation();
    /*mHeaderProgress.setVisibility(View.GONE);*/
		mHeaderImage.setVisibility(View.VISIBLE);
	}
}