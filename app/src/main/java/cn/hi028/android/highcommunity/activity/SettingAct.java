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
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：设置Activity<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
@EActivity(resName = "act_setting")
public class SettingAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "SettingAct";
    public static final String INTENTTAG = "SettingActIntent";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

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
            case Constacts.MENU_LEFTSECOND_SETTINGABOUTUS:
                AboutUsFrag mAbout = (AboutUsFrag) new AboutUsFrag_();
                ft.replace(R.id.ll_setting_layout, mAbout, AboutUsFrag.FRAGMENTTAG);
                mTitle.setText("关于我们");
                break;
            case Constacts.MENU_LEFTSECOND_SETTINGCONSTACT://联系我们
                mTitle.setText("联系我们");
                EditPersonalFrag mPersonal = (EditPersonalFrag) new EditPersonalFrag_();
                ft.replace(R.id.ll_setting_layout, mPersonal, EditPersonalFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFTSECOND_SETTINGMODIFYPSD://联系我们
                mTitle.setText("密码修改");
                ModifyPsdFrag modifyPsd = (ModifyPsdFrag) new ModifyPsdFrag_();
                ft.replace(R.id.ll_setting_layout, modifyPsd, ModifyPsdFrag.FRAGMENTTAG);
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
