/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.ActivityTack;
import net.duohuo.dhroid.activity.BackHandledFragment;
import net.duohuo.dhroid.activity.BackHandledInterface;
import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.*;

/**
 * @功能：小区城市选择<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-30<br>
 */
@EActivity(resName = "act_vallage")
public class VallageAct extends BaseFragmentActivity implements
		BackHandledInterface {
	private BackHandledFragment mBackHandedFragment;
	public static String TYPE = "type";
	public static String ISTOMAINACT = "toMainAct";
	public static boolean isToMain = false;
	public static boolean isFinish = true;
	public static int REQUEST_VILLAGE_CODE = 0x15;
	public static int RESULT_VILLAGE_CODE = 0x15;
	@ViewById(R.id.fragment)
	FrameLayout fl;

	/**
	 * 类型
	 *
	 * @param activity
	 * @param type
	 *            0:未登录，1：其他、登录
	 */
	public static void toStartAct(Activity activity, int type, boolean toMainAct) {
		Intent intent = new Intent(activity,
				GeneratedClassUtils.get(VallageAct.class));
		intent.putExtra(TYPE, type);
		intent.putExtra(ISTOMAINACT, toMainAct);
		if (toMainAct) {
			activity.startActivity(intent);
		} else {
			activity.startActivityForResult(intent, REQUEST_VILLAGE_CODE);
		}

	}

	/**
	 * 类型
	 *
	 * @param frag
	 * @param type
	 *            0:未登录，1：其他、登录
	 */
	public static void toStartAct(BaseFragment frag, int type, boolean toMainAct) {
		Intent intent = new Intent(frag.getActivity(),
				GeneratedClassUtils.get(VallageAct.class));
		intent.putExtra(TYPE, type);
		intent.putExtra(ISTOMAINACT, toMainAct);
		if (toMainAct) {
			frag.startActivity(intent);
		} else {
			frag.startActivityForResult(intent, REQUEST_VILLAGE_CODE);
		}
	}

	/**
	 * 类型
	 *
	 * @param frag
	 * @param type
	 *            0:未登录，1：其他、登录
	 *            requestData  0 否  1 是
	 */
	public static void toStartAct(BaseFragment frag, int type, int requestData) {
		Intent intent = new Intent(frag.getActivity(),
				GeneratedClassUtils.get(VallageAct.class));
		intent.putExtra(TYPE, type);
		if (requestData==0) {
			frag.startActivity(intent);
		} else {
			frag.startActivityForResult(intent, REQUEST_VILLAGE_CODE);
		}
	}


	@Override
	public void setSelectedFragment(BackHandledFragment selectedFragment) {
		this.mBackHandedFragment = selectedFragment;
	}

	@AfterViews
	void intView() {
		isToMain = getIntent().getBooleanExtra(ISTOMAINACT, false);
		CommunityFrag.isGetInitMessage=true;
		loadFragment();
	}

	@Override
	public void onBackPressed() {
		if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
			if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
				super.onBackPressed();
			} else {
				getSupportFragmentManager().popBackStack();
			}
		} else {
			finish();
		}
	}

	/**
	 * 加载fragment
	 */
	public void loadFragment() {
		BackHandledFragment citySelctFrag = new VallageCityFrag_();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragment, citySelctFrag, "other");
		ft.addToBackStack("tag");
		ft.commit();
	}

	@Override
	public void onResultActivity_Address(String address) {
		Intent intent = getIntent();
		intent.putExtra("AD",address);
		setResult(RESULT_VILLAGE_CODE, intent);
	}

	@Override
	public void onResultActivity(int id) {
		Activity actLogin = ActivityTack.getInstanse().getActivityByClass(
				WelcomeAct.class);
		if (actLogin != null) {
			actLogin.finish();
		}
		HighCommunityApplication.mUserInfo.setV_id(id);
		Log.e("renk", HighCommunityApplication.mUserInfo.getV_id() + "");
		if (isToMain) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		} else {
			Intent intent = getIntent();

			setResult(RESULT_VILLAGE_CODE, intent);
		}
		finish();
	}
}
