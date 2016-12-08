/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.*;

/**
 * @功能：群资料页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/15<br>
 */
@EActivity(resName = "act_groupdata")
public class GroupDataAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "GroupDataAct";
    public static final String INTENTTAG = "GroupDataActIntent";

    @AfterViews
    void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        String flag = getIntent().getStringExtra(ACTIVITYTAG);
        if (!TextUtils.isEmpty(flag) && flag.equals("Detils")) {
            GroupDetilsFrag mDetilsFrag = new GroupDetilsFrag_();
            ft.add(R.id.ll_GroupActiviy_MainLayout, mDetilsFrag, GroupDetilsFrag.FRAGMENTTAG);
        } else {
            CreateGroupFrag mCreate = new CreateGroupFrag_();
            ft.add(R.id.ll_GroupActiviy_MainLayout, mCreate, GroupDetilsFrag.FRAGMENTTAG);
        }
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CreateGroupFrag mCreate = (CreateGroupFrag) getSupportFragmentManager()
                .findFragmentByTag(CreateGroupFrag.FRAGMENTTAG);
        if (mCreate != null) {
            mCreate.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == 000) {
            System.out.println("哈哈哈哈 i'm back");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        CreateGroupFrag mCreate = (CreateGroupFrag) getSupportFragmentManager()
                .findFragmentByTag(CreateGroupFrag.FRAGMENTTAG);
        if (mCreate != null) {
            if (mCreate.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
