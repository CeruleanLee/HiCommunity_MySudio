package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoMoitionAdapter;
import cn.hi028.android.highcommunity.adapter.MotionPagerAdapter;
import cn.hi028.android.highcommunity.view.MyCustomViewPager;

/**
 * @功能：自治大厅 提案<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_Motion extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_Motion~~~";
    public static final String FRAGMENTTAG = "AutoFrag_Motion";
    /**
     * 创建提案
     **/
    public static final int TAG_CREAT_MOTION = 7;
    AutoMoitionAdapter mAdapter;
    @Bind(R.id.frag_motion_motionlist)
    RadioButton but_motionList;
    @Bind(R.id.frag_motion_mymotion)
    RadioButton but_Mymotion;
    @Bind(R.id.frag_motion_Radiogroup)
    RadioGroup mRadiogroup;
    @Bind(R.id.frag_motion_ViewPager)
    MyCustomViewPager mViewPager;
    View view;
    MotionPagerAdapter mPagerAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d(Tag+"onDetach");
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
        view = inflater.inflate(R.layout.frag_auto_motion_twopage, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        mViewPager.setPagingEnabled(false);
        mPagerAdapter = new MotionPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    but_motionList.setChecked(true);
                    but_Mymotion.setChecked(false);
                } else {
                    but_Mymotion.setChecked(true);
                    but_motionList.setChecked(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.frag_motion_motionlist:
                        setCurrentPage(0);
                        break;
                    case R.id.frag_motion_mymotion:
                        setCurrentPage(1);
                        break;
                }
            }
        });
setCurrentPage(0);
    }

   public  void setCurrentPage(int page){
       if (page==0){
           mViewPager.setCurrentItem(0);
       }else{
           mViewPager.setCurrentItem(1);
       }
   }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
