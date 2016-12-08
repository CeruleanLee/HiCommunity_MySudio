package cn.hi028.android.highcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.hi028.android.highcommunity.activity.fragment.*;

/**
 * 我的钱包适配器
 */
public class WalletSwitchAdapter extends FragmentPagerAdapter {

    public WalletSwitchAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == 0) {
            WalletScoreFrag mFragment = new WalletScoreFrag_();
            return mFragment;
        } else {
            WalletTickitFrag mFragment = new WalletTickitFrag_();
            return mFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
