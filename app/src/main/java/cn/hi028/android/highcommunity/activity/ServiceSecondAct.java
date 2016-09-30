/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：服务2级界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/28<br>
 */
@EActivity(resName = "act_second_service")
public class ServiceSecondAct extends BaseFragmentActivity {
    public static final String ACTIVITYTAG = "ServiceSecondAct";
    public static final String INTENTTAG = "ServiceSecondActIntent";
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.img_back)
    ImageView img_back;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    PersonalAuthFrag mAuth;

    @AfterViews
    void intView() {
    	LogUtil.d("------ServiceSecondAct");
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (flag == -1)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.SERVICENOTICE_DETAILS:
                mTitle.setText("公告");
                ServiceNoticeDetailFrag mDetails = (ServiceNoticeDetailFrag) new ServiceNoticeDetailFrag_();
                ft.replace(R.id.ll_service_second_layout, mDetails, ServiceNoticeDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICECARFTS_DETAILS:
                mTitle.setText("手艺人详情");
                ServiceCaftsDetailFrag mCarftsDetail = (ServiceCaftsDetailFrag) new ServiceCaftsDetailFrag_();
                ft.replace(R.id.ll_service_second_layout, mCarftsDetail, ServiceCaftsDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICEPAYMENT_DETAILS:
                mTitle.setText("缴费账单");
                ServicePaymentDetailFrag mPayDetail = (ServicePaymentDetailFrag) new ServicePaymentDetailFrag_();
                ft.replace(R.id.ll_service_second_layout, mPayDetail, ServicePaymentDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_TENEMENT_DETAIL:
                mTitle.setText("租房详情");
                ServiceTenDetailFrag mTenDetail = (ServiceTenDetailFrag) new ServiceTenDetailFrag_();
                ft.replace(R.id.ll_service_second_layout, mTenDetail, ServiceCaftsDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_REPAIR_DETAIL_ORDER:
                mTitle.setText("预约报修");
                SeriRepairOrderFrag mSeriRepairOrderFrag = (SeriRepairOrderFrag) new SeriRepairOrderFrag_();
                ft.replace(R.id.ll_service_second_layout, mSeriRepairOrderFrag, SeriRepairOrderFrag.FRAGMENTTAG);

                break;
            case Constacts.SERVICE_REPAIR_DETAIL_RECORD:
                mTitle.setText("报修记录");
                SeriRepairRecordFrag mSeriRepairRecordFrag = (SeriRepairRecordFrag) new SeriRepairRecordFrag_();
                ft.replace(R.id.ll_service_second_layout, mSeriRepairRecordFrag, SeriRepairRecordFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_REPAIR_DETAIL_JJ:
                mTitle.setText("紧急报修");
                SeriRepairJJFrag mSeriRepairJJFrag = (SeriRepairJJFrag) new SeriRepairJJFrag_();
                ft.replace(R.id.ll_service_second_layout, mSeriRepairJJFrag, SeriRepairJJFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_SECOND_PERSONALAUTH:
                mTitle.setText("认证");
                mAuth = (PersonalAuthFrag) new PersonalAuthFrag_();
                ft.replace(R.id.ll_service_second_layout, mAuth, PersonalAuthFrag.FRAGMENTTAG);
                break;

        }
        ft.commit();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getIntent().getIntExtra(ACTIVITYTAG, -1) == Constacts.SERVICE_REPAIR_DETAIL_RECORD) {
            SeriRepairOrderFrag mSeriRepairOrderFrag = (SeriRepairOrderFrag) getSupportFragmentManager()
                    .findFragmentByTag(SeriRepairOrderFrag.FRAGMENTTAG);
            if (mSeriRepairOrderFrag != null && mSeriRepairOrderFrag.onKeyDown(keyCode, event))
                return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK &&
                getIntent().getIntExtra(ACTIVITYTAG, -1) == Constacts.SERVICE_SECOND_PERSONALAUTH) {
            if (mAuth != null) {
                if (mAuth.onKeyDown(keyCode, event)) {
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }
}
