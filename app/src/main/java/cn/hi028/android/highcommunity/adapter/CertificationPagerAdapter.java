package cn.hi028.android.highcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CerChecking;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CerFailed;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CerSuccess;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;

/**
 * @功能： 业主认证pager适配器<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/28<br>
 */

public class CertificationPagerAdapter extends FragmentPagerAdapter {

    final String Tag = "CertificationPagerAdapter";
    AutoFrag_CerSuccess mCerSuccessFrag;
    AutoFrag_CerChecking mCerChecking;
    AutoFrag_CerFailed mCerFailedFrag;
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList;
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList_success = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList_checking = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList_failed = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();

    public CertificationPagerAdapter(FragmentManager fm, List<Auto_CertificationInitBean.CertificationInitDataEntity> mList) {
        super(fm);
        this.mList = mList;
        sortList(mList);
        Log.d(Tag, "CertificationPagerAdapter");
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == 0) {
            if (mCerSuccessFrag == null) {
                Log.d(Tag, "new AutoFrag_CerSuccess()");
                mCerSuccessFrag = new AutoFrag_CerSuccess();
            }
            if (mList_success != null) {
                mCerSuccessFrag.updateList(mList_success);
            }
            return mCerSuccessFrag;
        } else if (arg0 == 1) {
            if (mCerChecking == null) {
                Log.d(Tag, "new AutoFrag_CerChecking()");
                mCerChecking = new AutoFrag_CerChecking();
            }
            if (mList_checking != null) {
                mCerChecking.updateList(mList_checking);
            }
            return mCerChecking;
        } else {
            if (mCerFailedFrag == null) {
                Log.d(Tag, "new AutoFrag_CerFailed()");
                mCerFailedFrag = new AutoFrag_CerFailed();
            }
            if (mList_failed != null) {
                mCerFailedFrag.updateList(mList_failed);
            }
            return mCerFailedFrag;


        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void sortList(List<Auto_CertificationInitBean.CertificationInitDataEntity> mList) {
        List<Auto_CertificationInitBean.CertificationInitDataEntity> list = mList;
        mList_success = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
        mList_checking = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
        mList_failed = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
        for (int i = 0; i < list.size(); i++) {
            Auto_CertificationInitBean.CertificationInitDataEntity dataEntity = list.get(i);
            if (dataEntity.getStatus().equals("0")) {
                mList_checking.add(dataEntity);
            } else if (dataEntity.getStatus().equals("1")) {
                mList_success.add(dataEntity);
            } else if (dataEntity.getStatus().equals("-1")) {
                mList_failed.add(dataEntity);
            }

        }


    }
}
