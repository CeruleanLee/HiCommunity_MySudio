/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.duohuo.dhroid.activity.BaseFragment;

import java.lang.reflect.Field;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.NeighborViewPagerAdapter;

/**
 * @功能：邻里<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
public class NeighborFrag extends BaseFragment {
    final String Tag = "NeighborFrag--->";
    public static final String FRAGMENTTAG = "NeighborFrag";
    private View mFragmeView;
    private NeighborViewPagerAdapter mAdapter;
    private MyChangeListener myChangelistener;
    private ViewPager mViewPager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myChangelistener = (MyChangeListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmeView == null) {
            initView();
        }
        ViewGroup parent = (ViewGroup) mFragmeView.getParent();
        if (parent != null)
            parent.removeView(mFragmeView);
        return mFragmeView;
    }

    private void initView() {
        mFragmeView = LayoutInflater.from(getActivity()).inflate(
                R.layout.frag_neighbor, null);
        mViewPager = (ViewPager) mFragmeView.findViewById(R.id.vp_neighbor_viewpager);
        mAdapter = new NeighborViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                myChangelistener.onChange(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public void setCurrentPage(int page) {
        if (page == 0) {
            mViewPager.setCurrentItem(0);
        } else if (page == 1) {
            mViewPager.setCurrentItem(1);
        } else {
            mViewPager.setCurrentItem(2);
        }
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if (KeyCode == KeyEvent.KEYCODE_BACK) {
            return ((CommunityFrag) mAdapter.getItem(0)).onKeyDown();
        }
        return false;
    }


    public interface MyChangeListener {
        public void onChange(int flag);
    }
}
