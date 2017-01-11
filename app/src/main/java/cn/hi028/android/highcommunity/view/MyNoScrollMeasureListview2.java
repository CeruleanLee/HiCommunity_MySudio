package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/***
 *
 */
public class MyNoScrollMeasureListview2 extends ListView{

public boolean isMeasure=false;

	public MyNoScrollMeasureListview2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyNoScrollMeasureListview2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyNoScrollMeasureListview2(Context context) {
		super(context);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		Log.e("MyNoScrollMeasure--","onMeasure");
		isMeasure=true;
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		Log.e("MyNoScrollMeasure--","onLayout");
		isMeasure=false;
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onLayout(changed, l, t, r, expandSpec);
		super.onLayout(changed, l, t, r, b);
	}
}
