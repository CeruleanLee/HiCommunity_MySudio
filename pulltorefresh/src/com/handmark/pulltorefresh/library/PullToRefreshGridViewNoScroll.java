package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Lee_yting on 2016/11/29 0029.
 * 说明：
 */
public class PullToRefreshGridViewNoScroll extends PullToRefreshGridView {
    public PullToRefreshGridViewNoScroll(Context context) {
        super(context);
    }

    public PullToRefreshGridViewNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshGridViewNoScroll(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshGridViewNoScroll(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
