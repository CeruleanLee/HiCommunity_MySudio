package cn.hi028.android.highcommunity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import net.duohuo.dhroid.activity.BaseFragment;

/**
 * 惠生活订单适配器
 */
public class HuiOrderAdapter  extends PagerAdapter {
	public List<View> getViewList() {
		return viewList;
	}

	public void setViewList(List<View> viewList) {
		this.viewList = viewList;
		notifyDataSetChanged();
	}

	List<View>viewList=new ArrayList<View>();
	// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
	@Override
	public int getCount() {
		return viewList.size();
	}

	// 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		view.removeView(viewList.get(position));
	}

	// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		view.addView(viewList.get(position));
		return viewList.get(position);
	}
}