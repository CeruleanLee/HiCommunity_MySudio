/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.ConstantsFrag;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.activity.fragment.PersonalAuthInfoFrag;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：菜单的四级级页面统统都在这里<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/27<br>
 */
@EActivity(resName = "act_menusecond_left")
public class MenuLeftThirdAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "MenuLeftThirdAct";
    public static final String INTENTTAG = "MenuLeftThirdActIntent";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_menulef)
    View mTitleLayout;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    @AfterViews
    void initView() {
LogUtil.d("------MenuLeftThirdAct");
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (-1 == flag)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.MENU_LEFTTHIRD_ORDER_COMMENT://编辑资料
                mTitle.setText("评论");
                HuiCommOrderFrag moCmment = (HuiCommOrderFrag) new HuiCommOrderFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, moCmment, HuiCommOrderFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFTSECOND_SETTINGCONSTACT://联系我们
                mTitle.setText("联系我们");
                ConstantsFrag mConstants = (ConstantsFrag) new ConstantsFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, mConstants, ConstantsFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_THIRD_AUTOINFO://认证资料
                mTitle.setText("认证资料");
                PersonalAuthInfoFrag mAuth = (PersonalAuthInfoFrag) new PersonalAuthInfoFrag_();
                ft.replace(R.id.ll_menuleftSecond_layout, mAuth, PersonalAuthInfoFrag.FRAGMENTTAG);
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
