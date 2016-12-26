package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee_yting on 2016/10/27 0027.
 * 说明：可控制滑动的viewpager
 */
public class MyCustomViewPager extends ViewPager {
    private boolean isPagingEnabled = true;
    private List<View> ViewList=new ArrayList<View>();

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

    public List<View> getViewList() {
        return ViewList;
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    @Override
    public void addView(View child) {
        ViewList.add(child);
        super.addView(child);
    }

}
