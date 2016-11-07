package com.handmark.pulltorefresh.library.internal;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by Lee_yting on 2016/11/6 0006.
 * 说明：
 */
public class MyRotaLoadingLayout extends LoadingLayout {
    static final String Tag = "MyRotaLoadingLayout--->";
    static final int ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation;
//    private final Matrix mHeaderImageMatrix;

    private float mRotationPivotX, mRotationPivotY;

    private final boolean mRotateDrawableWhilePulling;
    ObjectAnimator animator, animator2;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MyRotaLoadingLayout(Context context, PullToRefreshBase.Mode mode,
                               PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mRotateDrawableWhilePulling = attrs.getBoolean(
                R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);

//        mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
//        mHeaderImageMatrix = new Matrix();
//        mHeaderImage.setImageMatrix(mHeaderImageMatrix);

        mRotateAnimation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);


        float curTranslationY = mHeaderImage.getTranslationY();
        animator = ObjectAnimator.ofFloat(mHeaderImage, "translationY", -150f);
        animator.setDuration(1200);


        animator2 = ObjectAnimator.ofFloat(mHeaderImage, "rotation", 0f, 900f);
        animator2.setDuration(1000);
        animator2.setRepeatCount(Animation.INFINITE);
        animator2.setRepeatMode(Animation.RESTART);


        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            mRotationPivotX = Math
                    .round(imageDrawable.getIntrinsicWidth() / 2f);
            mRotationPivotY = Math
                    .round(imageDrawable.getIntrinsicHeight() / 2f);
        }
    }

    /**
     * 下拉的过程进行的动画
     **/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onPullImpl(float scaleOfLayout) {
        Log.e(Tag, "onPullImpl");
//        float angle;
//        if (mRotateDrawableWhilePulling) {
//            angle = scaleOfLayout * 90f;
//        } else {
//            angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
//        }
//
//        mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
//        mHeaderImage.setImageMatrix(mHeaderImageMatrix);
//        mHeaderImage.startAnimation(mRotateAnimation);
        if (backImg.getVisibility() == View.INVISIBLE) {

            backImg.setVisibility(VISIBLE);
        }

    }

    //正在刷新回调
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void refreshingImpl() {
        Log.e(Tag, "refreshingImpl");
//        ViewGroup.LayoutParams mHeaderImageLayoutParams = mHeaderImage.getLayoutParams();
//        mHeaderImageLayoutParams.h
        if (isFirstChange) {
            mHeaderImage.setY(mHeaderImage.getY() - 150f);
            isFirstChange = false;
        }
//        mHeaderImage.startAnimation(new RotateAnimation(0, 720,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f));
        animator2.start();
    }

    @Override
    protected void resetImpl() {
        Log.e(Tag, "resetImpl");

        mHeaderImage.clearAnimation();
        resetImageRotation();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void resetImageRotation() {
        Log.e(Tag, "resetImageRotation");
        if (animator2 != null) {

            animator2.cancel();
        }
//        if (null != mHeaderImageMatrix) {
//            mHeaderImageMatrix.reset();
//            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
//        }
    }

    //下来刷新
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void pullToRefreshImpl() {
        Log.e(Tag, "pullToRefreshImpl");

        // NO-OP


        animator.start();
    }

    //释放刷新
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void releaseToRefreshImpl() {
        Log.e(Tag, "releaseToRefreshImpl");
//if ()
        // NO-OP
//        animator2.start();

    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.img_refresh_sun;
    }

    boolean isFirstChange = true;

//
//    //下来刷新
//    @Override
//    protected void pullToRefreshImpl() {
//        mHeaderImage.setVisibility(View.VISIBLE);
//    }
//
//    //正在刷新回调
//    @Override
//    protected void refreshingImpl() {
//        mHeaderImage.setVisibility(View.VISIBLE);
////        mHeaderImage.startAnimation(loadAnimation);
//    }
//
//    //释放刷新
//    @Override
//    protected void releaseToRefreshImpl() {
////        mHeaderImage.startAnimation(loadAnimation);
//    }
//
//    //重新设置
//    @Override
//    protected void resetImpl() {
//        mHeaderImage.clearAnimation();
//    /*mHeaderProgress.setVisibility(View.GONE);*/
//        mHeaderImage.setVisibility(View.VISIBLE);
//    }
}
