package cn.hi028.android.highcommunity.activity;

import cn.hi028.android.highcommunity.R;
import android.os.Bundle;
import android.view.View;

/****
 * 积分获取规则
 * @author Administrator
 *
 */
public class MyIntegralCore extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_interagel_core);
	}

	public void onBack(View v) {
		this.finish();
	}
}
