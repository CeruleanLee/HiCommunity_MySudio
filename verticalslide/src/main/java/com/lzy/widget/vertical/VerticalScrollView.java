package com.lzy.widget.vertical;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/1
 * 描    述：当ScrollView在最顶部或者最底部的时候，不消费事件
 * 修订历史：
 * ================================================
 */
public class VerticalScrollView extends ScrollView implements ObservableView {
    public static final String Tag = "NewBottomPageFrag--->";

    private float downX;
    private float downY;

    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.scrollViewStyle);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                //如果滑动到了最底部，就允许继续向上滑动加载下一页，否者不允许
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - downX;
                float dy = ev.getY() - downY;
                boolean allowParentTouchEvent;
                Log.d(Tag,"dx:"+dx+",dy:"+dy);
                if (Math.abs(dy) > Math.abs(dx)/4) {
                    if (dy > 0) {
                        //位于顶部时下拉，让父View消费事件
                        allowParentTouchEvent = isTop();
                        Log.d(Tag,"dy>0  allowParentTouchEvent:"+allowParentTouchEvent);

                    } else {

                        //位于底部时上拉，让父View消费事件
                        allowParentTouchEvent = isBottom();
                        Log.d(Tag,"dy<0  allowParentTouchEvent:"+allowParentTouchEvent);
                    }
                } else {
                    //水平方向滑动
                    allowParentTouchEvent = true;
                    Log.d(Tag,"水平方向滑动  allowParentTouchEvent:"+allowParentTouchEvent);

                }
                Log.d(Tag,"最后返回父view 是否处理："+allowParentTouchEvent);

                getParent().requestDisallowInterceptTouchEvent(!allowParentTouchEvent);
        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean isTop() {
//        (LinearLayout)(this.getChildAt(0))
        if (Build.VERSION.SDK_INT >= 14) {
            Log.d(Tag,"isTop SDK_INT >= 14  gety:"+getY());

            return !canScrollVertically(-1)&&getY()<=0;
        } else {
            Log.d(Tag,"isTop SDK_INT< 14");

            return getScrollY() <= 0;
        }
    }

    @Override
    public boolean isBottom() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.d(Tag,"isBottom isTop SDK_INT> 14");

            return !canScrollVertically(1);
        } else {
            Log.d(Tag,"isBottom isTop SDK_INT< 14");

            return getScrollY() + getHeight() >= computeVerticalScrollRange();
        }
    }

    @Override
    public void goTop() {
        scrollTo(0, 0);
    }
}
