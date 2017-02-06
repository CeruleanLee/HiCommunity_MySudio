/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.MyCollectionSwitchAdapter;

/**
 * @功能：我的收藏切换页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
@EFragment(resName = "frag_mycollectionswitch")
public class MyCollectionSwitchFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "MyCollectionSwitchFrag";
    private MyCollectionSwitchAdapter mAdapter;
    @ViewById(R.id.vp_MyCollectionSwitch_viewpager)
    ViewPager mViewPager;
    @ViewById(R.id.rg_MyCollectionSwitch)
    RadioGroup mRadioGroup;
    int currentPo = 0;

    @AfterViews
    void initView() {
        mAdapter = new MyCollectionSwitchAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_MyCollectionSwitch_Left) {
                    if (currentPo != 0)
                        mViewPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_MyCollectionSwitch_Right) {
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

    public boolean onKeyDown() {
        return ((MyCollectionMessageFrag) mAdapter.getItem(0)).onKeyDown();
    }
}
