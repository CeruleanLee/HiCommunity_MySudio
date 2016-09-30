package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class NoScroolGridView extends GridView {  

	public NoScroolGridView(Context context) {  
		super(context);  
	}  

	public NoScroolGridView(Context context, AttributeSet attrs) {  
		super(context, attrs);  
	}  

	public NoScroolGridView(Context context, AttributeSet attrs, int defStyle) {  
		super(context, attrs, defStyle);  
	}  

	//不出现滚动条  
	@Override  
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
		super.onMeasure(widthMeasureSpec, expandSpec);  
	}  
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_MOVE) {
			return false;
		}
		//		return true;
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_MOVE) {
			return false;
		}
		return super.onTouchEvent(ev);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev);
	}

}  