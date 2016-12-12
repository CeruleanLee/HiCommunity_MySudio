/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.CommunityDetilsFrag;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.activity.fragment.VillageMessageFrag;
import cn.hi028.android.highcommunity.activity.fragment.ReportFrag;

/**
 * @功能：评论详情<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
@EActivity(resName = "act_comm_detail")
public class CommunityDetailAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "CommunityDetailAct";
    public static final String INTENTTAG = "CommunityDetailActIntent";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    String flag;
    CommunityDetilsFrag mDetails = null;
    Boolean isMePra;

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        flag = getIntent().getStringExtra(ACTIVITYTAG);
        String name =getIntent().getStringExtra(INTENTTAG)+"";
        isMePra=getIntent().getBooleanExtra("isPra", false);
        if (!TextUtils.isEmpty(flag) && flag.equals("Details")) {
            mTitle.setText("帖子详情");
            mDetails = new CommunityDetilsFrag_();
            ft.replace(R.id.ll_community_detils_layout, mDetails, CommunityDetilsFrag.FRAGMENTTAG);
        } else if (!TextUtils.isEmpty(flag) && flag.equals("message")) {
            mTitle.setText(name);
            VillageMessageFrag mFrag = new VillageMessageFrag();
            ft.replace(R.id.ll_community_detils_layout, mFrag, VillageMessageFrag.FRAGMENTTAG);
        } else {
            mTitle.setText("举报");
            ReportFrag mReport = new ReportFrag_();
            ft.replace(R.id.ll_community_detils_layout, mReport, ReportFrag.FRAGMENTTAG);
        }
        ft.commit();
    }

    @Click(R.id.img_back)
    void back() {
        if (!TextUtils.isEmpty(flag) && flag.equals("Details")) {
            if (mDetails != null) {
                mDetails.finish();
            }
        }
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        CommunityDetilsFrag mFragment = (CommunityDetilsFrag) getSupportFragmentManager()
                .findFragmentByTag(CommunityDetilsFrag.FRAGMENTTAG);
        if (mFragment != null) {
            if (mFragment.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
