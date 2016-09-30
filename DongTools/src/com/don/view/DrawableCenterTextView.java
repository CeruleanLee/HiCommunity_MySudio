package com.don.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * @author dong
 * @category drawableLeft与文本一起居中显示(PaddingLeft==0)具有Checked的TextView
 * 
 */
public class DrawableCenterTextView extends TextView implements Checkable {
	int mPaddingLeft = 0;
	int mPosition = -1;
	boolean mNeedChecked = false;
	private boolean checked;
	private static final int[] CheckedStateSet = { android.R.attr.state_checked };
	int mdrawoffer = 0;
	int IntrinsicWidth = 0, IntrinsicHeight = 0;

	public DrawableCenterTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public DrawableCenterTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawableCenterTextView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable[] drawables = getCompoundDrawables();
		if (drawables != null) {
			Drawable drawableLeft = drawables[0];
			Drawable drawableRight = drawables[2];
			if (drawableLeft != null) {
				if (mPaddingLeft == 0) {
					float textWidth = getPaint().measureText(
							getText().toString());
					int drawablePadding = getCompoundDrawablePadding();
					int drawableWidth = 0;
					drawableWidth = drawableLeft.getIntrinsicWidth();
					float bodyWidth = textWidth + drawableWidth
							+ drawablePadding;
					canvas.translate((getWidth() - bodyWidth) / 2, 0);
				} else {
					canvas.translate(mPaddingLeft, 0);
				}
			}
			if (drawableRight != null) {
				if (IntrinsicWidth == 0) {
					IntrinsicWidth = drawableRight.getIntrinsicWidth();
					IntrinsicHeight = drawableRight.getIntrinsicHeight();
				}
				if (mdrawoffer != 0) {
					drawableRight.setBounds(0, 0, IntrinsicWidth + mdrawoffer,
							IntrinsicHeight + mdrawoffer);
				} else {
					drawableRight.setBounds(0, 0, IntrinsicWidth,
							IntrinsicHeight);
				}
			}
		}
		super.onDraw(canvas);
	}

	/**
	 * @category 设置drawableRight变化量
	 * @param offer
	 */
	public void drawoffer(int offer) {
		mdrawoffer = offer;
	}

	/**
	 * @category 是否需要具有Checked状态
	 * @param flag
	 */
	public void IsNeedChecked(boolean flag) {
		this.mNeedChecked = flag;
	}

	/**
	 * @category 设置水平偏移量(默认居中)
	 * @param padding
	 */
	public void setMyPaddingLeft(int padding) {
		this.mPaddingLeft = padding;
	}

	/**
	 * @category 设置position 方便listview使用
	 * @param position
	 */
	public void setPosition(int position) {
		this.mPosition = position;
	}

	/**
	 * @category 状态刷新 清除Checked状态
	 * @param position
	 */
	public void setSelectPosition(int position) {
		if (position < 0)
			return;
		if (mPosition != position) {
			this.checked = false;
		} else {
			this.checked = true;
		}
		refreshDrawableState();
	}

	/**
	 * @category 状态刷新 清除Checked状态
	 * @param position
	 */
	public void RemoveChecked() {
		this.checked = false;
		refreshDrawableState();
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return checked;
	}

	@Override
	public void setChecked(boolean arg0) {
		// TODO Auto-generated method stub
		this.checked = arg0;
		refreshDrawableState();
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		setChecked(!checked);
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CheckedStateSet);
		}
		return drawableState;
	}

	@Override
	public boolean performClick() {
		if (mNeedChecked) {
			toggle();
		}
		return super.performClick();
	}
}