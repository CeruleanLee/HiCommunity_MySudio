package cn.hi028.android.highcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.duohuo.dhroid.util.LogUtil;

import cn.hi028.android.highcommunity.activity.fragment.*;

/**
 * 邻里适配器
 */
public class NeighborViewPagerAdapter extends FragmentPagerAdapter {
    CommunityFrag mCommuFrag;
    GroupFrag mGroupFrag;
    final String Tag="------------NeighborFrag";
    public NeighborViewPagerAdapter(FragmentManager fm) {
        super(fm);
        LogUtil.d(Tag+"NeighborViewPagerAdapter");
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == 0) {
            if (mCommuFrag == null) {
            	   LogUtil.d(Tag+"new CommunityFrag()");
                mCommuFrag = new CommunityFrag();
            }
            return mCommuFrag;
        } else {
            if (mGroupFrag == null) {
            	 LogUtil.d(Tag+"new GroupFrag_()");
                mGroupFrag = new GroupFrag_();
            }
            return mGroupFrag;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}
