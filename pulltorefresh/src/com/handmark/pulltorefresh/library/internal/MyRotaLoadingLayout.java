package com.handmark.pulltorefresh.library.internal;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
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

    private final Animation mRotateAnimation, animation345;
//    private final Matrix mHeaderImageMatrix;

    private float mRotationPivotX, mRotationPivotY;

    private final boolean mRotateDrawableWhilePulling;
    ObjectAnimator animator, animator2;
    boolean isFirstChange = true;
    boolean isFirstRun = true;
    Context context;
    float offHeight=120;

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public MyRotaLoadingLayout(Context context, PullToRefreshBase.Mode mode,
                               PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mRotateDrawableWhilePulling = attrs.getBoolean(
                R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);

        this.context=context;
//        mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
//        mHeaderImageMatrix = new Matrix();
//        mHeaderImage.setImageMatrix(mHeaderImageMatrix);
//if (isFirstRun){

//    offHeight = Math.abs(mHeaderImage.getTranslationY()-backImg.getTranslationY());

    Log.e(Tag,"position[1]--->"+ mHeaderImage.getTranslationY()+"position2[1]--->"+backImg.getTranslationY());
//    Log.e(Tag,"offHeight--->"+ offHeight);

    isFirstRun=false;
DisplayMetrics dm = new DisplayMetrics();
if (scanForActivity(context)!=null){

    scanForActivity(context).getWindowManager().getDefaultDisplay().getMetrics(dm);
}
        int widthPixels= dm.widthPixels;
        int heightPixels= dm.heightPixels;
        float density = dm.density;
       float screenWidth = widthPixels * density;
    float screenHeight = heightPixels * density ;

        Log.e(Tag,"widthPixels-->"+ widthPixels+",heightPixels---"+heightPixels+",screenWidth---"+screenWidth+",screenHeight"+screenHeight);
        Log.e(Tag,"heightPixels/16--->"+ heightPixels/16);
        offHeight=heightPixels/16;

    mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
//        animation345=new TranslateAnimation(mHeaderImage.getX(),)
        animation345 = new TranslateAnimation(mHeaderImage.getX(), mHeaderImage.getX(), mHeaderImage.getY(), mHeaderImage.getY() - 48);
        animation345.setDuration(1200);
        animation345.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationEnd(Animation animation) {
//                view.layout(b.getLeft(), 300, b.getRight(), 300+view.getHeight());
                mHeaderImage.clearAnimation();
                mHeaderImage.setY(mHeaderImage.getY() - 120f);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

        });

        //如果setFillAfter为ture是不会出现闪动的，但是下一次拖拽就不正常
        animation345.setFillAfter(true);
        Log.e(Tag,"dip2px--->"+ dip2px(getContext(),48f));

        float curTranslationY = mHeaderImage.getTranslationY();
        dip2px(getContext(),48f);
        animator = ObjectAnimator.ofFloat(mHeaderImage, "translationY", curTranslationY, -offHeight);
        animator.setDuration(1200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(Tag, "Listener--->onAnimationStart");

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(Tag, "Listener--->onAnimationEnd");

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(Tag, "Listener--->onAnimationCancel");

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(Tag, "Listener--->onAnimationRepeat");

            }
        });

        animator2 = ObjectAnimator.ofFloat(mHeaderImage, "rotation", 0f, 720);
        animator2.setDuration(1200);
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
//        Log.e(Tag, "onPullImpl--->scaleOfLayout--->" + scaleOfLayout);
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void pullToRefreshImpl() {
//        Log.e(Tag, "pullToRefreshImpl---animator.isRunning()--->" + animator.isRunning());
//        Log.e(Tag, "pullToRefreshImpl--->isFromBottom--->" + isFromBottom + ",isFirstUp--->" + isFirstUp);

        // NO-OP
        if (animator2.isRunning()) {
            animator2.cancel();
        }
        if (isFromBottom) {
            mHeaderImage.setVisibility(GONE);
            backImg.setVisibility(GONE);
            return;
        }
        if (isFirstUp && !isFromBottom) {
            Log.e(Tag, "进入刷新动画");
            Log.e(Tag,"dip2px--->"+ dip2px(getContext(),48f));
            animator.start();
            isFirstUp = false;
        }

    }

    //正在刷新回调
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void refreshingImpl() {
//        Log.e(Tag, "refreshingImpl");
        if (isFirstChange) {
            mHeaderImage.setY(mHeaderImage.getY() - offHeight);
            isFirstChange = false;
        }
//        mHeaderImage.startAnimation(new RotateAnimation(0, 720,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f));
        animator2.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void resetImpl() {
//        Log.e(Tag, "resetImpl");
        if (animator2 != null) {

            animator2.cancel();
//            isFirstUp=true;
        }
        mHeaderImage.clearAnimation();
        resetImageRotation();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void resetImageRotation() {
//        Log.e(Tag, "resetImageRotation");
        if (animator2 != null) {

            animator2.cancel();
            isFirstUp = true;
        }
    }

    //释放刷新
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void releaseToRefreshImpl() {
        Log.e(Tag, "releaseToRefreshImpl");
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.img_refresh_sun;
    }

    /**
     * dp转px
     **/
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}


