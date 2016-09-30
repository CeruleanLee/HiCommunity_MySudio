package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

public class PagerListView extends ListView {

    private GestureDetector mGestureDetector;

    public PagerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PagerListView(Context context) {
        super(context);
        init();
    }

    public PagerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(),
                new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
	mGestureDetector.onTouchEvent(ev);
	 super.dispatchTouchEvent(ev);
	 return true;
}
    private class YScrollDetector extends SimpleOnGestureListener {
    	@Override
    	public boolean onDown(MotionEvent e) {
    		// TODO Auto-generated method stub
    		return true;
    	}
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
    		if (e2.getAction()==MotionEvent.ACTION_MOVE) {
			
    			 if (Math.abs(distanceY) >5|| Math.abs(distanceX)>5) {
//                   if (distanceY-distanceX >10) {     	
                   return true;
               }
    			
    			return true;
		}
           
            return false;
        }
    }
//    @Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if (ev.getAction()==MotionEvent.ACTION_MOVE) {
//			return true;
//		}
////		return true;
//		return super.dispatchTouchEvent(ev);
//	}
}
