package cn.hi028.android.highcommunity.activity.fragment;

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
import cn.hi028.android.highcommunity.adapter.CertificationPagerAdapter;
import cn.hi028.android.highcommunity.view.MyCustomViewPager;

/**
 * @功能：自治大厅 业主认证<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_Certigication extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_Certigication~~~";
    public static final String FRAGMENTTAG = "AutoFrag_Certigication";
    View view;
    CertificationPagerAdapter mPagerAdapter;
    @Bind(R.id.frag_Certification_success)
    RadioButton but_Success;
    @Bind(R.id.frag_Certification_checking)
    RadioButton but_Checking;
    @Bind(R.id.frag_Certification_failed)
    RadioButton but_Failed;
    @Bind(R.id.frag_Certification_RadioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.frag_Certification_ViewPager)
    MyCustomViewPager mViewPager;

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d(Tag + "onDetach");
        try {
            //参数是固定写法
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
        LogUtil.d(Tag + "onCreateView");
        view = inflater.inflate(R.layout.frag_auto_certificati_page, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        LogUtil.d(Tag + "initView");
        mViewPager.setPagingEnabled(false);
        mPagerAdapter = new CertificationPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    but_Success.setChecked(true);
                    but_Checking.setChecked(false);
                    but_Failed.setChecked(false);
                } else if (i==1){
                    but_Checking.setChecked(true);
                    but_Success.setChecked(false);
                    but_Failed.setChecked(false);
                }else if (i==2){
                    but_Failed.setChecked(true);
                    but_Checking.setChecked(false);
                    but_Success.setChecked(false);
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.frag_Certification_success:
                        setCurrentPage(0);
                        break;
                    case R.id.frag_Certification_checking:
                        setCurrentPage(1);
                        break;
                    case R.id.frag_Certification_failed:
                        setCurrentPage(2);
                        break;
                }
            }
        });
        setCurrentPage(0);
    }

    public void setCurrentPage(int page) {
        if (page == 0) {
            mViewPager.setCurrentItem(0);
        } else if (page==1){
            mViewPager.setCurrentItem(1);
        }else if (page==2){
            mViewPager.setCurrentItem(2);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
