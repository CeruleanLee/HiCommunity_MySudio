package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

import cn.hi028.android.highcommunity.R;
/**
 * @功能： 可调节drawable大小的radiobut
 * @作者： Lee_yting<br>
 * @版本：新版本 2.0<br>
 * @时间：2016/11/30<br>
 */
public class MyRadioButton extends RadioButton {
	private int drawableWith;
	private int drawableHeight;
	private int mDrawableSize;// xml文件中设置的大小

	public MyRadioButton(Context context) {
		this(context, null, 0);
	}

	public MyRadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyRadioButton);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			Log.i("MyRadioButton", "attr:" + attr);
			switch (attr) {
				case R.styleable.MyRadioButton_mdrawableSize:
					mDrawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_mdrawableSize, 0);
					Log.i("MyRadioButton", "mDrawableSize:" + mDrawableSize);
					break;
				case R.styleable.MyRadioButton_drawableWith:
					drawableWith = a.getDimensionPixelSize(R.styleable.MyRadioButton_drawableWith, 0);
					Log.i("MyRadioButton", "drawableWith:" + drawableWith);
					break;
				case R.styleable.MyRadioButton_drawableHeight:
					drawableHeight = a.getDimensionPixelSize(R.styleable.MyRadioButton_drawableHeight, 0);
					Log.i("MyRadioButton", "drawableHeight:" + drawableHeight);
					break;
				case R.styleable.MyRadioButton_drawableTop:
					drawableTop = a.getDrawable(attr);
					break;
				case R.styleable.MyRadioButton_drawableBottom:
//					drawableBottom
							drawableBottom = a.getDrawable(attr);
					break;
				case R.styleable.MyRadioButton_drawableRight:
					drawableRight = a.getDrawable(attr);
					break;
				case R.styleable.MyRadioButton_drawableLeft:
					drawableLeft = a.getDrawable(attr);
					break;
				default :
					break;
			}
		}
		a.recycle();

		setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

	}

	public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
														Drawable top, Drawable right, Drawable bottom) {

		if (left != null) {
			if (mDrawableSize!=0){

				left.setBounds(0, 0, mDrawableSize, mDrawableSize);
			}else if (drawableWith!=0||drawableHeight!=0){
				left.setBounds(0, 0, drawableWith, drawableHeight);
			}
		}
		if (right != null) {
//			right.setBounds(0, 0, mDrawableSize, mDrawableSize);
			if (mDrawableSize!=0){

				right.setBounds(0, 0, mDrawableSize, mDrawableSize);
			}else if (drawableWith!=0||drawableHeight!=0){
				right.setBounds(0, 0, drawableWith, drawableHeight);
			}
		}
		if (top != null) {
//			top.setBounds(0, 0, mDrawableSize, mDrawableSize);
			if (mDrawableSize!=0){

				top.setBounds(0, 0, mDrawableSize, mDrawableSize);
			}else if (drawableWith!=0||drawableHeight!=0){
				top.setBounds(0, 0, drawableWith, drawableHeight);
			}
		}
		if (bottom != null) {
//			bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
			if (mDrawableSize!=0){

				bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
			}else if (drawableWith!=0||drawableHeight!=0){
				bottom.setBounds(0, 0, drawableWith, drawableHeight);
			}
		}
		setCompoundDrawables(left, top, right, bottom);
	}


}
