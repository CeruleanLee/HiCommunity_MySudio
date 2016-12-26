package cn.hi028.android.highcommunity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import cn.hi028.android.highcommunity.lisenter.ScrollViewListener;

/**
 * @说明：专为商品详情图文详情自定义的scrollview
 * @作者： Lee_yting
 * @时间：2016/12/26 0026
 */
public class MyGoodetailScrollView extends ScrollView {
    public MyGoodetailScrollView(Context context) {
        super(context);
    }

    public MyGoodetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGoodetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyGoodetailScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private ScrollViewListener scrollViewListener = null;
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }



}
