package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class Mylistview extends ListView{



	public Mylistview(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public Mylistview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Mylistview(Context context) {
		super(context);
	}
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//
//				MeasureSpec.AT_MOST);
//
//		super.onMeasure(widthMeasureSpec, expandSpec);
//
//	}
}
