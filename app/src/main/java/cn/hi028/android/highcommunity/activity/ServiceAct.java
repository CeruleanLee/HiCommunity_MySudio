/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.ServiceBeCarftsFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServiceBeCarftsFrag_;
import cn.hi028.android.highcommunity.activity.fragment.ServiceCarftsFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServiceCarftsFrag_;
import cn.hi028.android.highcommunity.activity.fragment.ServiceNoticeFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServiceNoticeFrag_;
import cn.hi028.android.highcommunity.activity.fragment.ServicePaymentFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServicePaymentFrag_;
import cn.hi028.android.highcommunity.activity.fragment.ServiceTenementFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServiceTenementFrag_;
import cn.hi028.android.highcommunity.activity.fragment.ShakeFrag;
import cn.hi028.android.highcommunity.activity.fragment.ShakeFrag_;
import cn.hi028.android.highcommunity.utils.Constacts;

//import cn.hi028.android.highcommunity.activity.fragment.ServiceRepairFrag;
//import cn.hi028.android.highcommunity.activity.fragment.ServiceRepairFrag_;

/**
 * @功能：服务的二级页面统统都在这里<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/28<br>
 */
@EActivity(resName = "act_service")
public class ServiceAct extends BaseFragmentActivity {
    public static final String ACTIVITYTAG = "ServiceAct";
    public static final String INTENTTAG = "ServiceActIntent";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.img_right)
    ImageView img_right;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    ServiceTenementFrag mTenementFrag;

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (-1 == flag)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.SERVICE_PAYMENT:
                mTitle.setText("缴费");
                ServicePaymentFrag mPayment = (ServicePaymentFrag) new ServicePaymentFrag_();
                ft.replace(R.id.ll_service_layout, mPayment, ServicePaymentFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_TENEMENT:
                mTitle.setText("租房");
                img_right.setVisibility(View.VISIBLE);
                img_right.setImageResource(R.mipmap.img_tenent_loc);
                mTenementFrag = (ServiceTenementFrag) new ServiceTenementFrag_();
                ft.replace(R.id.ll_service_layout, mTenementFrag, ServiceTenementFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_REPAIR:
                mTitle.setText("报修");
                ft.replace(R.id.ll_service_layout, mTenementFrag, ServiceTenementFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_NOTICE:
                ServiceNoticeFrag mNotice = (ServiceNoticeFrag) new ServiceNoticeFrag_();
                ft.replace(R.id.ll_service_layout, mNotice, ServiceNoticeFrag.FRAGMENTTAG);
                mTitle.setText("物业公告");
                break;
            case Constacts.SERVICE_GUIDE:
                mTitle.setText("办事指南");
                break;
            case Constacts.SERVICE_RESEARCH:
                mTitle.setText("调查天地");
                break;
            case Constacts.SERVICE_NOTICE_ONE:
                ServiceNoticeFrag mService = (ServiceNoticeFrag) new ServiceNoticeFrag_();
                ft.replace(R.id.ll_service_layout, mService, ServiceNoticeFrag.FRAGMENTTAG);
                mTitle.setText("政务公告");
                break;
            case Constacts.SERVICE_THIRD:
                mTitle.setText("第三方服务");
                break;
            case Constacts.SERVICE_CARFSMAN:
                mTitle.setText("手艺人");
                ServiceCarftsFrag mCarfts = (ServiceCarftsFrag) new ServiceCarftsFrag_();
                ft.replace(R.id.ll_service_layout, mCarfts, ServiceCarftsFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_BECOME_CARFSMAN:
                ServiceBeCarftsFrag mBeCarfts = (ServiceBeCarftsFrag) new ServiceBeCarftsFrag_();
                ft.replace(R.id.ll_service_layout, mBeCarfts, ServiceBeCarftsFrag.FRAGMENTTAG);
                mTitle.setText("成为手艺人");
                break;
            case Constacts.SERVICE_SHAKE:
                ShakeFrag mShakeFrag = (ShakeFrag) new ShakeFrag_();
                ft.replace(R.id.ll_service_layout, mShakeFrag, ShakeFrag.FRAGMENTTAG);
                mTitle.setText("摇一摇");
                break;
        }
        ft.commit();
    }

    @Click(R.id.img_right)
    void onClick() {
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (-1 == flag)
            return;
        switch (flag) {
            case Constacts.SERVICE_TENEMENT:
                mTenementFrag.onClickRight();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ServiceBeCarftsFrag mBeCarfts = (ServiceBeCarftsFrag) getSupportFragmentManager()
                .findFragmentByTag(ServiceBeCarftsFrag.FRAGMENTTAG);
        if (mBeCarfts != null) {
            if (mBeCarfts.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

}
