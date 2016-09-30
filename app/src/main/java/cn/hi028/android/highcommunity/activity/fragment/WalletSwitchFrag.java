/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.NeighborViewPagerAdapter;
import cn.hi028.android.highcommunity.adapter.WalletSwitchAdapter;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * @功能：钱包界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
@EFragment(resName = "frag_walletswitch")
public class WalletSwitchFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "WalletSwitchFrag";
    private WalletSwitchAdapter mAdapter;
    @ViewById(R.id.vp_walletSwitch_viewpager)
    ViewPager mViewPager;
    @ViewById(R.id.rg_walletSwitch)
    RadioGroup mRadioGroup;
    int currentPo = 0;

    @AfterViews
    void initView() {
        mAdapter = new WalletSwitchAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_walletSwitch_Left) {
                    if (currentPo != 0)
                        mViewPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_walletSwitch_Right) {
                    if (currentPo != 1)
                        mViewPager.setCurrentItem(1);
                }
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                currentPo = i;
                if (!((RadioButton) mRadioGroup.getChildAt(i)).isChecked()) {
                    ((RadioButton) mRadioGroup.getChildAt(i)).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
