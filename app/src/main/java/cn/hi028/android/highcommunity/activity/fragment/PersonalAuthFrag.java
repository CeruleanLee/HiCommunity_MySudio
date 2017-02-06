/***************************************************************************

 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.SearchActivity;
import cn.hi028.android.highcommunity.bean.CitysBean;
import cn.hi028.android.highcommunity.bean.DistrictBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ShowListUtils;

/**
 * @功能：个人认证<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>//要用到的类 大概我都放在上面的
 * @时间：2016/1/19<br>
 */
@EFragment(resName = "frag_personalauth")
public class PersonalAuthFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "PersonalAuthFrag";
    @ViewById(R.id.et_personalauth_name)
    EditText mName;
    @ViewById(R.id.et_personalauth_phone)
    EditText mPhone;
    @ViewById(R.id.et_personalauth_city)
    TextView mCity;
    @ViewById(R.id.et_personalauth_quxian)
    TextView mQuxian;
    @ViewById(R.id.et_personalauth_xiaoqu)
    TextView mXiaoqu;
    @ViewById(R.id.et_personalauth_build)
    EditText mBuild;
    @ViewById(R.id.et_personalauth_unit)
    EditText mUnit;
    @ViewById(R.id.et_personalauth_doorNumber)
    EditText mDoorNumber;

    PopupWindow mWaitingWindow, mListWindow;
    List<CitysBean> mCityBean;
    List<DistrictBean> mDistrictBean;
    List<VallageBean> mVillageBean = new ArrayList<VallageBean>();
    String CityId, QuxianId, XiaoquId;

    @AfterViews
    void initView() {
        HTTPHelper.getAddressCity(mCityIbpi);
        mCity.setOnClickListener(mClick);
        mQuxian.setOnClickListener(mClick);
        mXiaoqu.setOnClickListener(mClick);
    }
    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.et_personalauth_city:
                    mListWindow = ShowListUtils.GetInstantiation().ShowCityList(getActivity(), mCity, mBack, mCityBean);
                    break;
                case R.id.et_personalauth_quxian:
                    mListWindow = ShowListUtils.GetInstantiation().ShowDistrictList(getActivity(), mQuxian, mBack, mDistrictBean);
                    break;
                case R.id.et_personalauth_xiaoqu:
                    String city = mCity.getText().toString().trim();
                    String quxian = mQuxian.getText().toString().trim();
                    if (TextUtils.isEmpty(city)) {
                        HighCommunityUtils.GetInstantiation().ShowToast("请先选择城市", 0);
                        return;
                    }
                    if (TextUtils.isEmpty(quxian)) {
                        HighCommunityUtils.GetInstantiation().ShowToast("请先选择区县不能", 0);
                        return;
                    }
                    SearchActivity.toSearch(PersonalAuthFrag.this, mXiaoqu, mXiaoqu.getLineHeight(), QuxianId);
                    break;
            }
        }
    };


    ShowListUtils.OnItemClickBack mBack = new ShowListUtils.OnItemClickBack() {
        @Override
        public void onClick(Object mBack) {
            if (mBack instanceof CitysBean) {
                mCity.setText(((CitysBean) mBack).getCity());
                CityId = ((CitysBean) mBack).getCity_code();
                HTTPHelper.getAddressDistrist(mDistristIbpi, CityId);
            } else if (mBack instanceof DistrictBean) {
                mQuxian.setText(((DistrictBean) mBack).getDistrict());
                QuxianId = ((DistrictBean) mBack).getDistrict_code();
                HTTPHelper.getVillagesByDistrict(mVillageIbpi, QuxianId,
                        Constacts.location.getLongitude() + "", Constacts.location.getLatitude() + "", "");
            }

        }
    };

    BpiHttpHandler.IBpiHttpHandler mDistristIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mDistrictBean = (List<DistrictBean>) message;
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveDistrict(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    BpiHttpHandler.IBpiHttpHandler mVillageIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mVillageBean = (List<VallageBean>) message;
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVillage(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    BpiHttpHandler.IBpiHttpHandler mCityIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mCityBean = (List<CitysBean>) message;

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCityList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    @Click(R.id.tv_personalauth_submit)
    void submit() {
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String city = mCity.getText().toString().trim();
        String quxian = mQuxian.getText().toString().trim();
        String build = mBuild.getText().toString().trim();
        String unit = mUnit.getText().toString().trim();
        String doornumber = mDoorNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            HighCommunityUtils.GetInstantiation().ShowToast("姓名不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(phone)) {
            HighCommunityUtils.GetInstantiation().ShowToast("联系方式不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(city)) {
            HighCommunityUtils.GetInstantiation().ShowToast("城市不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(quxian)) {
            HighCommunityUtils.GetInstantiation().ShowToast("区县不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(XiaoquId)) {
            HighCommunityUtils.GetInstantiation().ShowToast("小区不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(build)) {
            HighCommunityUtils.GetInstantiation().ShowToast("楼栋不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(doornumber)) {
            HighCommunityUtils.GetInstantiation().ShowToast("门牌号不能为空", 0);
            return;
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mBuild, Gravity.CENTER);
        HTTPHelper.personalAuth(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", name, phone, CityId, QuxianId,XiaoquId, unit, doornumber, build, XiaoquId);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }
        
        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if(message.equals("获取验证码成功")){
            	String msg="验证提交成功";
            	HighCommunityUtils.GetInstantiation().ShowToast(msg.toString(), 0);
            	getActivity().finish();
            }
        }
        
        @Override
        public Object onResolve(String result) {
            return null;
        }
        
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        	
        }
        
        @Override
        public void cancleAsyncTask() {
            mWaitingWindow.dismiss();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x22&&data!=null) {
            VallageBean mBean = (VallageBean) data.getSerializableExtra(Constacts.SEARCH_RESULT);
            if (mBean != null) {
                mXiaoqu.setText(mBean.getName());
                XiaoquId = mBean.getId() + "";
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWaitingWindow != null && mWaitingWindow.isShowing()) {
                mWaitingWindow.dismiss();
                return true;
            }
        }
        return false;
    }

    ;
}
