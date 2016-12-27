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

    public SupplyMorePagerAdapter(FragmentManager fm,int mFragListSize ,CustomViewpager vp) {
        super(fm);
        Log.d(Tag, "MotionPagerAdapter");
this.vp=vp;
        Log.d(Tag,"SIZE="+mFragList.size());
        while (mFragList.size()<mFragListSize){
            SupplyShopMoreFrag mPublicMotionFrag = new SupplyShopMoreFrag(vp);
            mFragList.add( mPublicMotionFrag);
        }
    }
    int page=0;

    @Override
    public Fragment getItem(int arg0) {
        page = arg0;
        Log.d(Tag,"getItem--->"+arg0);
//        if (arg0==0||arg0==1||mFragList.size()-1 < arg0) {
//            Log.d(Tag, "new mPublicMotionFrag()");
//            SupplyShopMoreFrag mPublicMotionFrag = new SupplyShopMoreFrag();
//            mFragList.add(arg0, mPublicMotionFrag);
//        }
        Log.d(Tag,"getItem-SIZE="+mFragList.size());

        return mFragList.get(arg0);
//        } else if (arg0 == 4) {
//            if (mPublicMotionFrag == null) {
//                Log.d(Tag, "new mPublicMotionFrag()");
//                mPublicMotionFrag = new AutoFrag_Motion_forback1027();
//            }
//            return mPublicMotionFrag;
//        } else  {
//            if (myMotionFrag == null) {
//                Log.d(Tag, "new myMotionFrag()");
//                myMotionFrag = new AutoFrag_myMotion();
//            }
//            return myMotionFrag;
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
