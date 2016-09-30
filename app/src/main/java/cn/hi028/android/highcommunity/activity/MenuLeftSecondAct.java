/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.EditPersonalFrag;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipOrderDetailFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiOrderDetailFrag;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：菜单的三级级页面统统都在这里<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/27<br>
 */
@EActivity(resName = "act_menusecond_left")
public class MenuLeftSecondAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "MenuLeftAct";
    public static final String INTENTTAG = "MenuLeftActIntent";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_menulef)
    View mTitleLayout;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    @AfterViews
    void initView() {
    	LogUtil.d("------MenuLeftSecondAct");
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (-1 == flag)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.MENU_LEFTSECOND_PERSONAL://编辑资料
                mTitle.setText("编辑资料");
                EditPersonalFrag mPersonal = (EditPersonalFrag) new EditPersonalFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, mPersonal, EditPersonalFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFTSECOND_PAYFINISH://缴费账单
                mTitle.setText("缴费账单");
                ServicePaymentDetailFrag mBill = (ServicePaymentDetailFrag) new ServicePaymentDetailFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, mBill, ServicePaymentDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFTSECOND_ORDER_DETAIL://订单详情
                mTitle.setText("订单详情");
                HuiOrderDetailFrag mOrder = (HuiOrderDetailFrag) new HuiOrderDetailFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, mOrder, HuiOrderDetailFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFTSECOND_CHIP_ORDER_DETAIL://众筹订单详情
                mTitle.setText("订单详情");
                HuiChipOrderDetailFrag mChipOrder = (HuiChipOrderDetailFrag) new HuiChipOrderDetailFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, mChipOrder, HuiOrderDetailFrag.FRAGMENTTAG);
                break;

        }
        ft.commit();

    }


    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        EditPersonalFrag mPersonal = (EditPersonalFrag) getSupportFragmentManager()
                .findFragmentByTag(EditPersonalFrag.FRAGMENTTAG);
        if (mPersonal != null) {
            if (mPersonal.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
