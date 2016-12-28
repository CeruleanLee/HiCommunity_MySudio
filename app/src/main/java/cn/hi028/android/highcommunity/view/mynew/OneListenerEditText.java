package cn.hi028.android.highcommunity.view.mynew;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class OneListenerEditText extends EditText{

	public OneListenerEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	private TextWatcher watcher;
	public void setTextWatcher(TextWatcher watcher){
		if(this.watcher != null){
			removeTextChangedListener(this.watcher);
		}
		this.watcher = watcher;
		addTextChangedListener(this.watcher);
	}
}
