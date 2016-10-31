package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.CertificationPagerAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
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
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList;

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(Tag, "onDetach");
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
        Log.e(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_auto_certificati_page, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        Log.e(Tag, "initView");
        mViewPager.setPagingEnabled(false);
//        mPagerAdapter = new CertificationPagerAdapter(getChildFragmentManager(),mList);
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
                } else if (i == 1) {
                    but_Checking.setChecked(true);
                    but_Success.setChecked(false);
                    but_Failed.setChecked(false);
                } else if (i == 2) {
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
                        but_Success.setChecked(true);
                        but_Checking.setChecked(false);
                        but_Failed.setChecked(false);
                        break;
                    case R.id.frag_Certification_checking:
                        setCurrentPage(1);
                        but_Checking.setChecked(true);
                        but_Success.setChecked(false);
                        but_Failed.setChecked(false);
                        break;
                    case R.id.frag_Certification_failed:
                        setCurrentPage(2);
                        but_Failed.setChecked(true);
                        but_Checking.setChecked(false);
                        but_Success.setChecked(false);
                        break;
                }
            }
        });
//        setCurrentPage(0);
    }


    private void initDatas() {
        HTTPHelper.GetOwnersList(mIbpi);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mList = (List<Auto_CertificationInitBean.CertificationInitDataEntity>) message;
            mPagerAdapter = new CertificationPagerAdapter(getChildFragmentManager(), mList);
            mViewPager.setAdapter(mPagerAdapter);

        }

        @Override
        public Object onResolve(String result) {
            LogUtil.d(Tag + " ~~~result" + result);
            return HTTPHelper.ResolveCerDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    public void setCurrentPage(int page) {
        if (page == 0) {
            mViewPager.setCurrentItem(0);
        } else if (page == 1) {
            mViewPager.setCurrentItem(1);
        } else if (page == 2) {
            mViewPager.setCurrentItem(2);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
        Log.e(Tag,"success 选中"+but_Success.isChecked()+",checking 选中"+but_Checking.isChecked()+",failed 选中"+but_Failed.isChecked());
        if (but_Success.isChecked()) {
            Log.e(Tag,"1");
        } else if (but_Checking.isChecked()) {
            Log.e(Tag,"2");
            mViewPager.setCurrentItem(1);

        } else if (but_Failed.isChecked()) {
            Log.e(Tag,"3");
            mViewPager.setCurrentItem(2);
        }
    }
}
