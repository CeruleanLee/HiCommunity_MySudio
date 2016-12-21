package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import cn.hi028.android.highcommunity.adapter.BaseFragmentAdapter;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/21 0021
 */
public class LinearLayoutForListView extends LinearLayout {

    private BaseFragmentAdapter adapter;
    private OnClickListener onClickListener = null;

    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);

            v.setOnClickListener(this.onClickListener);
            addView(v, i);
        }
        Log.v("countTAG", "" + count);
    }

    public LinearLayoutForListView(Context context) {
        super(context);

    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

    }

    /**
     * 获取Adapter
     *
     * @return adapter
     */
    public BaseAdapter getAdpater() {
        return adapter;
    }

    /**
     * 设置数据
     *
     * @param adpater
     */
    public void setAdapter(BaseFragmentAdapter adpater) {
        this.adapter = adpater;
        bindLinearLayout();
    }

    /**
     * 获取点击事件
     *
     * @return
     */
    public OnClickListener getOnclickListner() {
        return onClickListener;
    }

    /**
     * 设置点击事件
     *
     * @param onClickListener
     */
    public void setOnclickLinstener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}