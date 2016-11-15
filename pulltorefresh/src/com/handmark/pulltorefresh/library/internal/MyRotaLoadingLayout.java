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
import android.view.animation.TranslateAnimation;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by Lee_yting on 2016/11/6 0006.
 * 说明：
 */
public class MyRotaLoadingLayout extends LoadingLayout {
    static final String Tag = "MyRotaLoadingLayout--->";
    static final int ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation,animation345;
//    private final Matrix mHeaderImageMatrix;

    private float mRotationPivotX, mRotationPivotY;

    private final boolean mRotateDrawableWhilePulling;
    ObjectAnimator animator, animator2;
    boolean isFirstChange = true;
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
//        animation345=new TranslateAnimation(mHeaderImage.getX(),)
        animation345=new TranslateAnimation(mHeaderImage.getX(),mHeaderImage.getX(),mHeaderImage.getY(),mHeaderImage.getY()-100);
        animation345.setDuration(1200);
        animation345.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
//                view.layout(b.getLeft(), 300, b.getRight(), 300+view.getHeight());
                mHeaderImage.clearAnimation();
                mHeaderImage.setY(mHeaderImage.getY() - 120f);
            }

            public void onAnimationRepeat(Animation animation) { }
            public void onAnimationStart(Animation animation) { }

        });

        //如果setFillAfter为ture是不会出现闪动的，但是下一次拖拽就不正常
        animation345.setFillAfter(true);


        float curTranslationY = mHeaderImage.getTranslationY();
        animator = ObjectAnimator.ofFloat(mHeaderImage, "translationY", -120f);
        animator.setDuration(1200);


        animator2 = ObjectAnimator.ofFloat(mHeaderImage, "rotation", 0f, 720);
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

    boolean isFromBottom = false;

    /**
     * 下拉的过程进行的动画
     **/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onPullImpl(float scaleOfLayout) {
        Log.e(Tag, "onPullImpl--->scaleOfLayout--->" + scaleOfLayout);
        if (backImg.getVisibility() == View.INVISIBLE) {

            backImg.setVisibility(VISIBLE);
        }
        if (scaleOfLayout < 0) {
            isFromBottom = true;
        } else {
            isFromBottom = false;
        }
        Log.e(Tag, "onPullImpl--->isFromBottom--->" + isFromBottom);

    }
    boolean isFirstUp = true;

    //下来刷新
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void pullToRefreshImpl() {
        Log.e(Tag, "pullToRefreshImpl---animator.isRunning()--->" + animator.isRunning());
        Log.e(Tag, "pullToRefreshImpl--->isFromBottom--->" + isFromBottom + ",isFirstUp--->" + isFirstUp);

        // NO-OP
        if (animator2.isRunning()) {
            animator2.cancel();
        }
        if (isFromBottom){
            if (mHeaderImage.getVisibility()==VISIBLE){
                mHeaderImage.setVisibility(GONE);
            }
            if (backImg.getVisibility() == View.VISIBLE) {

                backImg.setVisibility(GONE);
            }
        }
        if (isFirstUp && !isFromBottom) {
            Log.e(Tag, "进入刷新动画" );
//         resetImpl();
//            Log.e(Tag, "进入刷新动画---animator.isRunning()--->" + animator.isRunning());
            animator.start();
//            mHeaderImage.startAnimation(animation345);
            isFirstUp = false;
        }

    }

    //正在刷新回调
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void refreshingImpl() {
        Log.e(Tag, "refreshingImpl");
        if (isFirstChange) {
            mHeaderImage.setY(mHeaderImage.getY() - 120f);
            isFirstChange = false;
        }
//        mHeaderImage.startAnimation(new RotateAnimation(0, 720,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f));
        animator2.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void resetImpl() {
        Log.e(Tag, "resetImpl");
        if (animator2 != null) {

            animator2.cancel();
//            isFirstUp=true;
        }
        mHeaderImage.clearAnimation();
        resetImageRotation();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void resetImageRotation() {
        Log.e(Tag, "resetImageRotation");
        if (animator2 != null) {

            animator2.cancel();
            isFirstUp = true;
        }
//        if (null != mHeaderImageMatrix) {
//            mHeaderImageMatrix.reset();
//            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
//        }
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







}
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (!isPullToRefreshEnabled()) {
//            return false;
//        }
//
//        final int action = event.getAction();
//
//        if (action == MotionEvent.ACTION_CANCEL
//                || action == MotionEvent.ACTION_UP) {
//            mIsBeingDragged = false;
//            return false;
//        }
//
//        if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
//            return true;
//        }
//
//        switch (action) {
//            case MotionEvent.ACTION_MOVE: {
//                // If we're refreshing, and the flag is set. Eat all MOVE events
//                if (!mScrollingWhileRefreshingEnabled && isRefreshing()) {
//                    return true;
//                }
//
//                if (isReadyForPull()) {
//                    final float y = event.getY(), x = event.getX();
//                    final float diff, oppositeDiff, absDiff;
//
//                    // We need to use the correct values, based on scroll
//                    // direction
//                    switch (getPullToRefreshScrollDirection()) {
//                        case HORIZONTAL:
//                            diff = x - mLastMotionX;
//                            oppositeDiff = y - mLastMotionY;
//                            break;
//                        case VERTICAL:
//                        default:
//                            diff = y - mLastMotionY;
//
//                            Log.e(Tag,"y--->"+y+",mLastMotionY--->"+mLastMotionY+",diff--->"+diff);
//
//                            oppositeDiff = x - mLastMotionX;
//                            break;
//                    }
//                    absDiff = Math.abs(diff);
//
//                    if (absDiff > mTouchSlop
//                            && (!mFilterTouchEvents || absDiff > Math
//                            .abs(oppositeDiff))) {
//                        if (mMode.showHeaderLoadingLayout() && diff >= 1f
//                                && isReadyForPullStart()) {
//                            mLastMotionY = y;
//                            mLastMotionX = x;
//                            mIsBeingDragged = true;
//                            if (mMode == Mode.BOTH) {
//                                mCurrentMode = Mode.PULL_FROM_START;
//                            }
//
//                        } else if (mMode.showFooterLoadingLayout() && diff <= -1f
//                                && isReadyForPullEnd()) {
//                            mLastMotionY = y;
//                            mLastMotionX = x;
//                            mIsBeingDragged = true;
//                            if (mMode == Mode.BOTH) {
//                                mCurrentMode = Mode.PULL_FROM_END;
//                            }
//                        }
//                    }
//                }
//                break;
//            }
//            case MotionEvent.ACTION_DOWN: {
//                if (isReadyForPull()) {
//                    mLastMotionY = mInitialMotionY = event.getY();
//                    mLastMotionX = mInitialMotionX = event.getX();
//                    mIsBeingDragged = false;
//                }
//                break;
//            }
//        }
//
//        return mIsBeingDragged;
//
//    }

