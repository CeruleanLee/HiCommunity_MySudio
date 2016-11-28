package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MainActivity;

/**
 * @功能：惠生活模块<br>
 * @作者： renk<br>
 * @版本：1.1<br>
 * @时间：2015-12-08<br>
 */
@EFragment(resName = "frag_huilife")
public class HuiLifeFrag_newVersion4back extends BaseFragment {
	private List<Fragment> fragmentList = new ArrayList<Fragment>();// fragment列表
	private FragmentManager fm;// fragment管理器
	private int fragmentIndex = 0;
	public static final String FRAGMENTTAG = "HuiLifeFrag";
	@ViewById(R.id.rg_huil_ife)
	RadioGroup rg;
	// @ViewById(R.id.rb_shops)
	// RadioButton shopbtn;
	BaseFragment fg_support, fg_chips;
	ShopNearbyFrag fg_shops;

	/** 定位链接 */
	LocationClient locationClient;
	String district;

	@AfterViews
	void initView() {

		fm = getChildFragmentManager();
		fg_support = new HuiSupplyFrag();
		fg_chips = new HuiChipsFrag_();
		fg_shops = new ShopNearbyFrag();
		// CommonUtils.createfragment(fm, fg_support, R.id.fl_huiLife, false);
		replaceFrag(fg_support, 0);

		fragmentList.add(fg_support);
		fragmentList.add(fg_chips);
		fragmentList.add(fg_shops);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rb_chips) {
					replaceFrag(fg_chips, 1);
					// createfragment(1, R.id.fl_huiLife, false);
					update(false);
				} else if (checkedId == R.id.rb_support) {
					replaceFrag(fg_support, 0);
					// createfragment(0, R.id.fl_huiLife, false);
					update(true);
				} else {
					replaceFrag(fg_shops, 2);
					// createfragment(2, R.id.fl_huiLife, true);
					update(false);

				}
			}
		});
		// replaceFrag(fg_support);
		// update(true);

		/****/
		locationClient = new LocationClient(getActivity());
		MyListener myBDLocationListener = new MyListener();
		locationClient.registerLocationListener(myBDLocationListener);
		// 设置定位参数
		LocationClientOption option = new LocationClientOption();
		// 设置打开gps
		option.setOpenGps(true);
		// 设置坐标类型
		option.setCoorType("bd09ll");
		// 设置每隔2秒得一次坐标
		// 少于1000，只得一次
		// 重新得坐标 btn
		// locationClient.requestLocation();
		option.setScanSpan(999);
		locationClient.setLocOption(option);

		locationClient.start();
	}

	public void replaceFrag(BaseFragment frag, int a) {

		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("city", cityBean);
		// frag.setArguments(bundle);

		ft.replace(R.id.fl_huiLife, frag);

		ft.addToBackStack("renk" + a);
		ft.commit();
	}

	/***/
	class MyListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			district = location.getDistrict();
			// fg_shops.setDistrict(district);
			fg_shops.setDistrict("成华区");
		}
	}

	/**
	 * 侧边是否可以滑动
	 *
	 * @param isSuppp
	 */
	public void update(boolean isSuppp) {
		if (isSuppp) {
			((MainActivity) getActivity()).menu
			.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		} else {
			((MainActivity) getActivity()).menu
			.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}

	}

	/**
	 * 创建fragment界面
	 * 
	 * @param fragment
	 *            显示的界面
	 * @param ra
	 *            布局id
	 * @param cancel
	 *            是否加入返回栈中
	 * @param fm
	 *            fragment管理器
	 */
	public void createfragment(int index, int ra, boolean cancel) {

		// 创建处理事务
		FragmentTransaction ft2 = fm.beginTransaction();
		// 设置界面
		try {
			if (!fragmentList.get(index).isAdded()
					&& !fragmentList.contains(fragmentList.get(index))) {
				ft2.add(ra, fragmentList.get(index));
			} else {
				ft2.hide(fragmentList.get(fragmentIndex));
			}
		} catch (Exception e) {
			// ft2.add(ra, fragmentList.get(index));
		}
		// ft2.hide(fragmentList.get(fragmentIndex));
		ft2.show(fragmentList.get(index));
		fragmentIndex = index;
		if (cancel) {
			// 添加返回键弹出功能
			ft2.addToBackStack("" + index);
		}
		// 完成修改
		// ft2.commitAllowingStateLoss();
		ft2.commit();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("renk", "onactivityresult>frag,hlf");
		Log.e("renk", "onactivityresult>frag,hlf");
//		Toast.makeText(getActivity(), "onactivityresult>frag,hlf", 0).show();

		super.onActivityResult(requestCode, resultCode, data);
	}

	// @Override
	// public void onAttach(Context context) {
	// Log.i("renk", "onattach");
	// super.onAttach(context);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("renk", "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i("renk", "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.i("renk", "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i("renk", "onPause");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.i("renk", "onDestroy");
		Log.i("renk", "onDestroy");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i("renk", "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i("renk", "onDetach");
		// TODO Auto-generated method stub
		super.onDetach();
	}

}
