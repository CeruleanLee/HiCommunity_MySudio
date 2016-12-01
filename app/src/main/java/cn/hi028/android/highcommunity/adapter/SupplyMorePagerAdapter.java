package cn.hi028.android.highcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.duohuo.dhroid.util.LogUtil;

import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Motion_forback1027;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_myMotion;

/**
 * 直供商品更多pager适配器
 */
public class SupplyMorePagerAdapter extends FragmentPagerAdapter {

    AutoFrag_Motion_forback1027 mPublicMotionFrag;
    AutoFrag_myMotion myMotionFrag;

    final String Tag = "MotionPagerAdapter";

    public SupplyMorePagerAdapter(FragmentManager fm) {
        super(fm);
        LogUtil.d(Tag + "MotionPagerAdapter");
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == 0) {
            if (mPublicMotionFrag == null) {
                LogUtil.d(Tag + "new mPublicMotionFrag()");
                mPublicMotionFrag = new AutoFrag_Motion_forback1027();
            }
            return mPublicMotionFrag;
        } else if (arg0 == 1) {
            if (myMotionFrag == null) {
                LogUtil.d(Tag + "new myMotionFrag()");
                myMotionFrag = new AutoFrag_myMotion();
            }
            return myMotionFrag;
        } else if (arg0 == 2) {
            if (mPublicMotionFrag == null) {
                LogUtil.d(Tag + "new mPublicMotionFrag()");
                mPublicMotionFrag = new AutoFrag_Motion_forback1027();
            }
            return mPublicMotionFrag;

        } else if (arg0 == 3) {
            if (myMotionFrag == null) {
                LogUtil.d(Tag + "new myMotionFrag()");
                myMotionFrag = new AutoFrag_myMotion();
            }
            return myMotionFrag;
        } else if (arg0 == 4) {
            if (mPublicMotionFrag == null) {
                LogUtil.d(Tag + "new mPublicMotionFrag()");
                mPublicMotionFrag = new AutoFrag_Motion_forback1027();
            }
            return mPublicMotionFrag;
        } else  {
            if (myMotionFrag == null) {
                LogUtil.d(Tag + "new myMotionFrag()");
                myMotionFrag = new AutoFrag_myMotion();
            }
            return myMotionFrag;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }


}
