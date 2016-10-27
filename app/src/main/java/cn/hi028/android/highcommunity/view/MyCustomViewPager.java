package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Lee_yting on 2016/10/27 0027.
 * 说明：可控制滑动的viewpager
 */
public class MyCustomViewPager extends ViewPager {
    private boolean isPagingEnabled = true;

    public MyCustomViewPager(Context context) {
        super(context);
    }

    public MyCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }


}
