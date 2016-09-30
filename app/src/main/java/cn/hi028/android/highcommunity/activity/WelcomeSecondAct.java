/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.ForgetPsdFrag;
import cn.hi028.android.highcommunity.activity.fragment.RegisterFrag;
import cn.hi028.android.highcommunity.activity.fragment.WelcomeFrag;

/**
 * @功能：忘记密码、注册等页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/18<br>
 */
@EActivity(resName = "act_welcome_second")
public class WelcomeSecondAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "WelcomeSecondAct";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    FragmentManager fm;

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        fm = getSupportFragmentManager();
        Intent mintent = getIntent();
        String flag = mintent.getStringExtra(ACTIVITYTAG);
        if (!TextUtils.isEmpty(flag)) {
            mTitle.setText(flag);
        }
        if (flag.equals("忘记密码")) {
            FragmentTransaction ft = fm.beginTransaction();
            ForgetPsdFrag mWelcomeFragment = (ForgetPsdFrag) new ForgetPsdFrag();
            ft.replace(R.id.ll_welcome_Second_Act, mWelcomeFragment,
                    mWelcomeFragment.FRAGMENTTAG);
            ft.commit();
        } else {
            FragmentTransaction ft = fm.beginTransaction();
            RegisterFrag mWelcomeFragment = (RegisterFrag) new RegisterFrag();
            ft.replace(R.id.ll_welcome_Second_Act, mWelcomeFragment,
                    mWelcomeFragment.FRAGMENTTAG);
            ft.commit();
        }


    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }
}
