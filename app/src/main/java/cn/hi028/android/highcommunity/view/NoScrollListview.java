package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoScrollListview extends ListView{



	public NoScrollListview(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NoScrollListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListview(Context context) {
		super(context);
	}
///**
//	 * 设置不滚动
//	 */
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//	{
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//
//	}
}
