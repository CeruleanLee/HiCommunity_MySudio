package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.duohuo.dhroid.activity.BaseFragment;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.NewHuiLifePagerAdapter;

/**
 * @功能：惠生活模块<br>
 * @作者： renk<br>
 * @版本：1.1<br>
 * @时间：2015-12-08<br>
 */
public class HuiLifeFrag extends BaseFragment {
    public static final String Tag = "HuiLifeNewFrag--->";
    public static final String FRAGMENTTAG = "HuiLifeFrag";


    View view;
    NewHuiLifePagerAdapter mPagerAdapter;
    @Bind(R.id.vp_huilife_viewpager)
    ViewPager mViewPager;
    private MyChangeListener4HuiLife mlistenerHuiLife;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mlistenerHuiLife = (MyChangeListener4HuiLife) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(Tag, "onDetach");
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
        Log.d(Tag,  "onCreateView");
        view = inflater.inflate(R.layout.fragment_hui_life_new, null);
        ButterKnife.bind(this, view);
        initView();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);
        return view;
    }

    void initView() {
        Log.d(Tag,"initView");

        mPagerAdapter = new NewHuiLifePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {

                    mlistenerHuiLife.onHuiLifeChange(0);
                } else {

                    mlistenerHuiLife.onHuiLifeChange(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//        setCurrentPage(0);
    }

    public void setCurrentPage(int page) {
        if (page == 0) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(1);
        }
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        //TODO 新版需改
//        if (KeyCode == KeyEvent.KEYCODE_BACK) {
//            return ((CommunityFrag) mAdapter.getItem(0)).onKeyDown();
//        }
        return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public interface MyChangeListener4HuiLife {
        public void onHuiLifeChange(int i);
    }

    @Override
    public void onResume() {
        Log.e(Tag,"---onResume ");
        super.onResume();
    }
}
