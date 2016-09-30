/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsDetailFrag;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：惠生活第二层界面<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015/12/28<br>
 */
@EActivity(resName = "act_huilife_second")
public class HuiLifeSecondAct extends BaseFragmentActivity {
    public static final String ACTIVITYTAG = "HuiLifeSecondAct";
    public static final String INTENTTAG = "HuiLifeSecondActIntent";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    @AfterViews
    void intView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (flag == -1)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.HUILIFE_SUPPORT_ORDER:
                mTitle.setText("订单");
                HuiLifeSuppBuyFrag mDetails = (HuiLifeSuppBuyFrag) new HuiLifeSuppBuyFrag_();
                ft.replace(R.id.ll_huilife_second_layout, mDetails, ServiceNoticeDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.HUILIFE_CHIPS_DETAIL:
                mTitle.setText("众筹详情");
                HuiChipsDetailFrag mChipsDetail = (HuiChipsDetailFrag) new HuiChipsDetailFrag_();
                ft.replace(R.id.ll_huilife_second_layout, mChipsDetail, ServiceCaftsDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_TENEMENT_DETAIL:
                mTitle.setText("租房详情");
                ServiceTenDetailFrag mTenDetail = (ServiceTenDetailFrag) new ServiceTenDetailFrag_();
                ft.replace(R.id.ll_service_second_layout, mTenDetail, ServiceCaftsDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.SERVICE_REPAIR_DETAIL_ORDER:
                mTitle.setText("预约报修");

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

        }
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getIntent().getIntExtra(ACTIVITYTAG, -1) == Constacts.SERVICE_REPAIR_DETAIL_RECORD) {
        }
        return super.onKeyDown(keyCode, event);
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }
}
