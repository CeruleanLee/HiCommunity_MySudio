package cn.hi028.android.highcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.activity.fragment.SupplyShopMoreFrag;
import cn.hi028.android.highcommunity.view.CustomViewpager;

/**
 * 直供商品更多pager适配器
 */
public class SupplyMorePagerAdapter extends FragmentPagerAdapter {
    static    final String Tag = "MotionPagerAdapter";
    CustomViewpager vp;
    List<SupplyShopMoreFrag> mFragList= new ArrayList<SupplyShopMoreFrag>();

    public SupplyMorePagerAdapter(FragmentManager fm,int mFragListSize) {
        super(fm);
        Log.d(Tag, "MotionPagerAdapter");
        Log.d(Tag,"SIZE="+mFragList.size());
        while (mFragList.size()<mFragListSize){
            SupplyShopMoreFrag mPublicMotionFrag = new SupplyShopMoreFrag();
            mFragList.add( mPublicMotionFrag);
        }
    }
    int page=0;

    @Override
    public Fragment getItem(int arg0) {
        page = arg0;
        Log.d(Tag,"getItem--->"+arg0);
        Log.d(Tag,"getItem-SIZE="+mFragList.size());
        return mFragList.get(arg0);

    }

    @Override
    public int getCount() {
        return mFragList.size();
    }

    public void updateFragmentData(int page,String category_id, int sort){
        Log.e(Tag,"page: "+page);
       mFragList.get(page).updateSort(category_id,sort);
}
}
