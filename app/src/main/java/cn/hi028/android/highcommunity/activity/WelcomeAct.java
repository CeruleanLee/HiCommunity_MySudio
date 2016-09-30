/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.ActivityTack;
import net.duohuo.dhroid.activity.BaseActivity;

import java.lang.reflect.Method;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.LoginFrag;
import cn.hi028.android.highcommunity.activity.fragment.WelcomeFrag;
import cn.hi028.android.highcommunity.utils.LocUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * @功能：欢迎/登录activity<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/9<br>
 */
public class WelcomeAct extends BaseActivity {

	LinearLayout mWelcomeLayout;
	FragmentManager ft = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 透明状态栏
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 透明导航栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		ActivityTack.getInstanse().clear();
		super.onCreate(savedInstanceState);
//		UmengUpdateAgent.setUpdateOnlyWifi(false);
//		UmengUpdateAgent.update(this);
		mWelcomeLayout = (LinearLayout) this.findViewById(R.id.ll_welcomeAct);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.act_welcome);
		LocUtils.startLocation(this, null);
		ft = getSupportFragmentManager();
		// MyPushMessageReceiver.MessageCount = 0;
		if (!HighCommunityApplication.isLogOut) {
			FragmentTransaction fm = ft.beginTransaction();
			WelcomeFrag mWelcomeFragment = (WelcomeFrag) new WelcomeFrag();
			fm.replace(R.id.ll_welcomeAct, mWelcomeFragment,
					WelcomeFrag.FRAGMENTTAG);
			fm.commit();
		} else {
			HighCommunityApplication.isLogOut = false;
			FragmentTransaction fm = ft.beginTransaction();
			LoginFrag mLoginFragment = (LoginFrag) new LoginFrag();
			fm.replace(R.id.ll_welcomeAct, mLoginFragment,
					mLoginFragment.FRAGMENTTAG);
			fm.commit();
		}

		// HighCommunityApplication.SoftKeyHight =
		// HighCommunityUtils.GetInstantiation().dip2px(getDpi() -
		// HighCommunityUtils.DisplayMetricsHeight);
	}

	private int getDpi() {
		int dpi = 0;
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			dpi = dm.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dpi;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LoginFrag mLogin = (LoginFrag) getSupportFragmentManager()
				.findFragmentByTag(LoginFrag.FRAGMENTTAG);
		if (mLogin != null) {
			mLogin.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		 registNetworkReceiver();
	}
	
	 /****
	  * 与网络状态相关
	  */
	 private BroadcastReceiver receiver;
	 private void registNetworkReceiver(){
		 if(receiver == null){
			 receiver = new NetworkReceiver();
			 IntentFilter filter = new IntentFilter();
			 filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			 WelcomeAct.this.registerReceiver(receiver, filter );
		 }
	 }
	 private void unregistNetworkReceiver(){
		 WelcomeAct.this.unregisterReceiver(receiver);
	 }
	 public class NetworkReceiver extends BroadcastReceiver{

		 @Override
		 public void onReceive(Context context, Intent intent) {
			 if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
				 ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
				 NetworkInfo networkInfo = manager.getActiveNetworkInfo();
				 if(networkInfo != null && networkInfo.isAvailable()){
					 int type = networkInfo.getType(); 
					 if(ConnectivityManager.TYPE_WIFI == type){
					 }else if(ConnectivityManager.TYPE_MOBILE == type){
					 }else if(ConnectivityManager.TYPE_ETHERNET == type){
					 }
					 //有网络
					 //					Toast.makeText(getActivity(), "有网络", 0).show();
					 LogUtils.d("有网络");
					 //					if(nextPage == 1){
					 //					}
					 isNoNetwork = false;
				 }else{
					 //没有网络
					 LogUtils.d("没有网络");
					 Toast.makeText(WelcomeAct.this, "没有网络", Toast.LENGTH_LONG).show();
					 //					if(nextPage == 1){
//					 mLoadingView.noNetwork();
					 //					}
					 isNoNetwork = true;
				 }
			 }
		 }
	 }
	 private boolean isNoNetwork;
	
	
	
}
