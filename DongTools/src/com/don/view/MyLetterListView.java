package com.don.view;

import com.don.tools.DongUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author dong
 * @category 字母条
 */
public class MyLetterListView extends View {
	
	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	public static final String[] Letter = {"#","A","B","C","D","E","F","G","H","I","J","K","L"
			,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;

	public void setTv_dialog(TextView tv_dialog) {
		this.tv_dialog = tv_dialog;
	}

	public TextView tv_dialog;
	public MyLetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLetterListView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(showBkg){
		    canvas.drawColor(Color.parseColor("#ffffff"));
			if (this.tv_dialog!=null){
				this.tv_dialog.setVisibility(View.VISIBLE);
			}
		}else{
			if (this.tv_dialog!=null){
				this.tv_dialog.setVisibility(View.INVISIBLE);
			}
		}
		
	    int height = getHeight();
	    int width = getWidth();
	    int singleHeight = height / Letter.length;
	    for(int i=0;i<Letter.length;i++){
	       paint.setColor(Color.parseColor("#1fc796"));
//	       paint.setTypeface(Typeface.DEFAULT_BOLD);
	       paint.setAntiAlias(true);
	       paint.setTextSize(DongUtils.GetInstantiation().sp2px(12));
	       if(i == choose){
	    	   paint.setColor(Color.parseColor("#3399ff"));
	    	   paint.setFakeBoldText(true);
	       }
	       float xPos = width/2  - paint.measureText(Letter[i])/2;
	       float yPos = singleHeight * (i+0.8f);
	       canvas.drawText(Letter[i], xPos, yPos, paint);
	       paint.reset();
	    }
	   
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
	    final float y = event.getY();
	    final int oldChoose = choose;
	    final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
	    final int c = (int) (y/getHeight()*Letter.length);
	    
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				showBkg = true;
				if(oldChoose != c && listener != null){
					if(c >= 0 && c< Letter.length){
						listener.onTouchingLetterChanged(Letter[c]);
						choose = c;
						invalidate();
					}
				}
				
				break;
			case MotionEvent.ACTION_MOVE:
				if(oldChoose != c && listener != null){
					if(c >= 0 && c< Letter.length){
						listener.onTouchingLetterChanged(Letter[c]);
						if (this.tv_dialog!=null){
							this.tv_dialog.setText(Letter[c]);
						}
						choose = c;
						invalidate();
					}

				}
				break;
			case MotionEvent.ACTION_UP:
				showBkg = false;
				choose = -1;
				invalidate();
				break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener{
		public void onTouchingLetterChanged(String s);
	}
	
}
