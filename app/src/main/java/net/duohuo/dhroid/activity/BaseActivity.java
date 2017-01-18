package net.duohuo.dhroid.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import net.duohuo.dhroid.BaseApplication;
import net.duohuo.dhroid.Const;

import cn.hi028.android.highcommunity.R;

/***
 * @author duohuo-jinghao
 */
public class BaseActivity extends AppCompatActivity {
    public BaseApplication getBaseApplication() {
        return (BaseApplication) getApplication();
    }


    private ActivityTack tack = ActivityTack.getInstanse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        tack.addActivity(this);
    }
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.red),0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
        tack.removeActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
        if (Const.auto_inject) {
//            InjectUtil.inject(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 返回事件
     *
     * @param v
     */
    public void onBack(View v) {
        finish();
    }

    /**
     * @return the bus
     */

//	@Override
//	public void startActivity(Intent intent) {
//		if (NetworkUtils.isNetworkAvailable()) {
//			super.startActivity(intent);
//		} else {
//		}
//
//	}
}
