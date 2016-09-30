package cn.hi028.android.highcommunity.view.myradiobutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;

public class IconRadioButton extends LinearLayout  {
    private Context mContext;
    private Resources mResources;
    private boolean mIsCheck;
    private View mContentView;	
    private ImageView mRadio;
    private TextView mTextView;
    private ColorStateList mTextColor;

    public IconRadioButton(Context context) {
        this(context, null);
    }

    public IconRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
	public IconRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mResources = context.getResources();
        mContentView = LayoutInflater.from(context).inflate(R.layout.btn_icon_radio, this);
        mRadio = (ImageView) mContentView.findViewById(R.id.radio);
        mTextView = (TextView) mContentView.findViewById(R.id.text);
    }

    public void setChecked(boolean isChecked) {
        mIsCheck = isChecked;
        changeUIByChecked(isChecked);
    }

    public boolean isChecked() {
        return mIsCheck;
    }

    private void changeUIByChecked(boolean isChecked) {
        if (mRadio.getVisibility() != GONE) {
            mRadio.setImageResource(isChecked ? R.drawable.radio_selected
                    : R.drawable.radio_unselected);
        }
        mTextView.setEnabled(isChecked);

    }

    public void setTextColor(ColorStateList color){
        mTextColor = color;
        mTextView.setTextColor(color);
    }
    public void setText(CharSequence text) {
        mTextView.setText(text);
    }

    public void setText(int resId) {
        mTextView.setText(resId);
    }

    public void hiddenRadio(boolean isHidden) {
        mRadio.setVisibility(isHidden ? GONE : VISIBLE);
    }
}