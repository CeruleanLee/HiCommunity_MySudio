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
import cn.hi028.android.highcommunity.activity.fragment.ActivityCreateFrag;
import cn.hi028.android.highcommunity.activity.fragment.ActivityCreateFrag_;
import cn.hi028.android.highcommunity.activity.fragment.ActivityDetailsFrag;
import cn.hi028.android.highcommunity.activity.fragment.ActivityDetailsFrag_;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：活动详情页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/12<br>
 */
@EActivity(resName = "act_activity")

public class ActiveAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "ActiveAct";
    public static final String INTENTTAG = "ActiveActIntent";
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.img_second_share)
    ImageView mShare;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        
        switch (flag) {
        
            case Constacts.ACTIVITY_DETAILS:
                mTitle.setText("活动详情");
                mShare.setVisibility(View.VISIBLE);
                ActivityDetailsFrag mDetails = (ActivityDetailsFrag) new ActivityDetailsFrag_();
                ft.replace(R.id.ll_activity_mainLayout, mDetails, ActivityDetailsFrag.FRAGMENTTAG);
                break;
            case Constacts.ACTIVITY_CREATE:
                mTitle.setText("创建活动");
                mShare.setVisibility(View.GONE);
                ActivityCreateFrag mCreate = (ActivityCreateFrag) new ActivityCreateFrag_();
                ft.replace(R.id.ll_activity_mainLayout, mCreate, ActivityCreateFrag.FRAGMENTTAG);
                break;
        }
        ft.commit();
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

    @Click(R.id.img_second_share)
    void share() {
        ActivityDetailsFrag mDetail = (ActivityDetailsFrag) getSupportFragmentManager()
                .findFragmentByTag(ActivityDetailsFrag.FRAGMENTTAG);
        if (mDetail != null) {
            mDetail.onRight();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ActivityDetailsFrag mFrag = (ActivityDetailsFrag) getSupportFragmentManager()
                .findFragmentByTag(ActivityDetailsFrag.FRAGMENTTAG);
        if (mFrag != null && mFrag.onkeyDown()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
