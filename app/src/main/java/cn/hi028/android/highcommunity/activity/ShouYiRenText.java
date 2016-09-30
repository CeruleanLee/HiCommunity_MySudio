package cn.hi028.android.highcommunity.activity;

import cn.hi028.android.highcommunity.R;
import android.os.Bundle;
import android.widget.TextView;

public class ShouYiRenText extends BaseFragmentActivity {

	TextView xieYi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouyiren_xieyi);
		xieYi = (TextView) findViewById(R.id.shouyiren_xieyi);
	}
}
