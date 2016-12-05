/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AddressModifyFrag;
import cn.hi028.android.highcommunity.activity.fragment.AddressModifyFrag_;
import cn.hi028.android.highcommunity.bean.AddressBean;

/**
 * @功能：地址操作  新增 修改地址<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/21<br>
 */
@EActivity(resName = "act_activity")
public class AddressModifyAct extends BaseFragmentActivity {
static  final  String Tag="AddressModifyAct:";
    public static final String ACTIVITYTAG = "AddressModifyAct";
    public static final String INTENTTAG = "AddressModifyActIntent";
    public static final String INTENTTAGDELETE_TAG = "addModifyDelete";
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.img_second_delete)
    ImageView mDelete;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    AddressModifyFrag mCreate;

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);

        AddressBean modifyData = getIntent().getParcelableExtra("modifyData");
        if (modifyData!=null){
            Log.e(Tag,"传过来的对象："+modifyData.toString());
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            mTitle.setText("新增收货地址");
            mDelete.setVisibility(View.GONE);
            mCreate = (AddressModifyFrag) new AddressModifyFrag_();
        } else {
            mTitle.setText("修改收货地址");
            mDelete.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            if (modifyData!=null){
                bundle.putParcelable("modifyData", modifyData);
            }
            bundle.putInt("isModify",1);
            mCreate = (AddressModifyFrag) new AddressModifyFrag_();
            mCreate.setArguments(bundle);
        }
        int flagDelete = getIntent().getIntExtra(INTENTTAGDELETE_TAG, 0);
        if (flagDelete != 0) {
            mDelete.setVisibility(View.INVISIBLE);
        } else {
            mDelete.setVisibility(View.VISIBLE);
        }
//        mCreate = (AddressModifyFrag) new AddressModifyFrag_();
        ft.replace(R.id.ll_activity_mainLayout, mCreate, AddressModifyFrag.FRAGMENTTAG);
        ft.commit();
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

    @Click(R.id.img_second_delete)
    void delete() {
        if (mCreate != null) {
            mCreate.onRight();
        } else {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCreate != null && mCreate.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x22) {
            if (mCreate != null) {
                mCreate.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
