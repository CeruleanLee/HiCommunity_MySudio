/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.activity.fragment.*;

/**
 * @功能：收货地址列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/21<br>
 */
@EActivity(resName = "act_address")
public class AddressAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "AddressAct";
    public static final String INTENTTAG = "AddressActIntent";
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mTitle.setText("收货地址");
        AddressListFrag maddress = (AddressListFrag) new AddressListFrag_();
        ft.replace(R.id.ll_address_layout, maddress, AddressListFrag.FRAGMENTTAG);
        ft.commit();

    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }
}
