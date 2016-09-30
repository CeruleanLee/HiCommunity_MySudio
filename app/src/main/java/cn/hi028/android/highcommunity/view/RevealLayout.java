package cn.hi028.android.highcommunity.view;

import java.util.ArrayList;

import net.duohuo.dhroid.util.LogUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import cn.hi028.android.highcommunity.R;


/**
 * 一个特殊的LinearLayout,任何放入内部的clickable元素都具有波纹效果，当它被点击的时候，
 * 为了性能，尽量不要在内部放入复杂的元素
 * note: long click listener is not supported current for fix compatible bug.
 */
public class RevealLayout extends LinearLayout implements Runnable {

	long offsetTime,timeBeforeDraw;
	
	
    private static final String TAG = "DxRevealLayout";
    private static final boolean DEBUG = true;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mTargetWidth;
    private int mTargetHeight;
    /**宽高中较小的数**/
    private int mMinBetweenWidthAndHeight;
    /**宽高中较大的数**/
    private int mMaxBetweenWidthAndHeight;
   /**最大绘制半径**/
    private int mMaxRevealRadius;
    /**半径的间距**/
    private int mRevealRadiusGap;
    /**绘制半径**/
    private int mRevealRadius = 0;
    private float mCenterX;
    private float mCenterY;
    private int[] mLocationInScreen = new int[2];//点击的点在屏幕上的位置

    private boolean mShouldDoAnimation = false;//是否执行动画
    private boolean mIsPressed = false;//是否被点击
    private int INVALIDATE_DURATION = 0;//无效时间 

    private View mTouchTarget;//触摸目标
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();

    public RevealLayout(Context context) {
        super(context);
        init();
    }

    public RevealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    	LogUtil.d("-----------init");
        setWillNotDraw(false);//重写ondraw需调用  否则ondraw无效
        mPaint.setColor(getResources().getColor(R.color.reveal_color));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	LogUtil.d("-----------onLayout");
    	super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }

    private void initParametersForChild(MotionEvent event, View view) {
    	LogUtil.d("-----------initParametersForChild");
        mCenterX = event.getX() ;//相对于view的触摸位置坐标
        mCenterY = event.getY() ;
        mTargetWidth = view.getMeasuredWidth();//view的原始宽度
        mTargetHeight = view.getMeasuredHeight();
        mMinBetweenWidthAndHeight = Math.min(mTargetWidth, mTargetHeight);//返回宽高中较小的数
        mMaxBetweenWidthAndHeight = Math.max(mTargetWidth, mTargetHeight);
        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mIsPressed = true;
        mRevealRadiusGap = mMinBetweenWidthAndHeight / 2;

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];//
        int transformedCenterX = (int)mCenterX - left;
        mMaxRevealRadius = Math.max(transformedCenterX, mTargetWidth - transformedCenterX);
    }
    @Override
    protected void onDraw(Canvas canvas) {
    	LogUtil.d("-----------onDraw");
    	// TODO Auto-generated method stub
    	super.onDraw(canvas);
    	 timeBeforeDraw=System.currentTimeMillis();
    	
    }
    long upTime,mytime;
    protected void dispatchDraw(Canvas canvas) {
    	LogUtil.d("-----------dispatchDraw");
        super.dispatchDraw(canvas);//绘制自己的孩子
        LogUtil.d("-----------dispatchDraw");
        long recordTime=System.currentTimeMillis();
        LogUtil.d("-----------记录绘制时间:"+recordTime);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null) {
           //如果不执行动画或目标view宽度为0 或目标view空 跳出方法
        	  LogUtil.d("-----------return了");
        	  mytime=fourTime-upTime;
        	return;
        }

        if (mRevealRadius > mMinBetweenWidthAndHeight / 2) {
          //绘制半径比宽高中最小数的一半还要大    绘制半径就不断扩大
        	LogUtil.d("-----------1"+mRevealRadius);
        	mRevealRadius += mRevealRadiusGap * 4;
        } else {
            mRevealRadius += mRevealRadiusGap;
            LogUtil.d("-----------2"+mRevealRadius);
        }
        this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        canvas.restore();

        if (mRevealRadius <= mMaxRevealRadius) {
        	LogUtil.d("-----------3");
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        } else if (!mIsPressed) {
        	LogUtil.d("-----------4");
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
            fourTime=System.currentTimeMillis();
        }
        long offset=System.currentTimeMillis()-recordTime;
        LogUtil.d("-----------offset:"+offset);
        offsetTime+=offset;
        LogUtil.d("-----------offsetTime:"+offsetTime);
        
    }
long fourTime;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
    	LogUtil.d("-----------dispatchTouchEvent");
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
        	LogUtil.d("-----------ACTION_DOWN");
            View touchTarget = getTouchTarget(this, x, y);
            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
                mTouchTarget = touchTarget;
                initParametersForChild(event, touchTarget);
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
        } else if (action == MotionEvent.ACTION_UP) {
        	LogUtil.d("-----------ACTION_UP");
        	upTime=System.currentTimeMillis();
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
            mDispatchUpTouchEventRunnable.event = event;
            LogUtil.d("-----------准备postdelay  time:"+System.currentTimeMillis());
            postDelayed(mDispatchUpTouchEventRunnable, 400);
            LogUtil.d("-----------postdelay  time:"+System.currentTimeMillis());
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL) {
        	LogUtil.d("-----------ACTION_CANCEL");
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }

        return super.dispatchTouchEvent(event);
    }

    private View getTouchTarget(View view, int x, int y) {
    	LogUtil.d("-----------getTouchTarget");
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
       	LogUtil.d("-----------isTouchPointInView");
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom
                && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
    	
    	LogUtil.d("-----------进入performClick");
        postDelayed(this, 400);
        return true;
    }

    @Override
    public void run() {
      	LogUtil.d("-----------runw");
        super.performClick();
    }

    private class DispatchUpTouchEventRunnable implements Runnable {
        public MotionEvent event;

        @Override
        public void run() {
        	LogUtil.d("-----------DispatchUpTouchEventRunnable  run");
        	LogUtil.d("-----------run的时间："+System.currentTimeMillis());           
if (mTouchTarget == null || !mTouchTarget.isEnabled()) {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int)event.getRawX(), (int)event.getRawY())) {
                mTouchTarget.performClick();
            }
        }
    };

}
