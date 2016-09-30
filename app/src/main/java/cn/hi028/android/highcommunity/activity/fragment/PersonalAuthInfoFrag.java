/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.DistrictBean;
import cn.hi028.android.highcommunity.bean.PersonAuthBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：个人认证材料<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/19<br>
 */
@EFragment(resName = "frag_personalauth_info")
public class PersonalAuthInfoFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "PersonalAuthInfoFrag";

    @ViewById(R.id.et_personalauth_name)
    TextView mName;
    @ViewById(R.id.et_personalauth_phone)
    TextView mPhone;
    @ViewById(R.id.et_personalauth_city)
    TextView mCity;
    @ViewById(R.id.et_personalauth_quxian)
    TextView mQuxian;
    @ViewById(R.id.et_personalauth_xiaoqu)
    TextView mXiaoqu;
    @ViewById(R.id.et_personalauth_build)
    TextView mBuild;
    @ViewById(R.id.et_personalauth_unit)
    TextView mUnit;
    @ViewById(R.id.et_personalauth_doorNumber)
    TextView mDoorNumber;

    @AfterViews
    void initView() {
        HTTPHelper.getAuthInfo(mbpi);
    }


    BpiHttpHandler.IBpiHttpHandler mbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            PersonAuthBean data = (PersonAuthBean) message;
            update(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolvePersonAuth(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    public void update(PersonAuthBean data) {
        mName.setText(data.getReal_name());
        mPhone.setText(data.getTel());
        mXiaoqu.setText(data.getVillage());
        mBuild.setText(data.getBuilding());
        mUnit.setText(data.getUnit());
        mDoorNumber.setText(data.getDoorplate());
        mCity.setText(data.getCity_name());
        mQuxian.setText(data.getDistrict_name());
        mXiaoqu.setText(data.getName());
    }
    ;
}
