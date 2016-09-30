/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.hi028.android.highcommunity.activity.fragment.MyCollectionActFrag;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.activity.fragment.MyCollectionMessageFrag;

/**
 * @功能：我的收藏viewpage适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-2-18<br>
 */
public class MyCollectionSwitchAdapter extends FragmentPagerAdapter {

    MyCollectionMessageFrag mMessageFrag;
    MyCollectionActFrag mActFrag;

    public MyCollectionSwitchAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == 0) {
            if (mMessageFrag == null) {
                mMessageFrag = new MyCollectionMessageFrag();
            }
            return mMessageFrag;
        } else {
            if (mActFrag == null) {
                mActFrag = new MyCollectionActFrag_();
            }
            return mActFrag;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
