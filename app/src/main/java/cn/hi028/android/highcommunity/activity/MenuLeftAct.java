/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsOrderFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsOrderFrag_;
import cn.hi028.android.highcommunity.activity.fragment.HuiOrderFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiOrderFrag_;
import cn.hi028.android.highcommunity.activity.fragment.HuiSuppGdCarFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiSuppGdCarFrag_;
import cn.hi028.android.highcommunity.activity.fragment.MessageCenterFrag;
import cn.hi028.android.highcommunity.activity.fragment.MessageCenterFrag_;
import cn.hi028.android.highcommunity.activity.fragment.MyCarftsFrag;
import cn.hi028.android.highcommunity.activity.fragment.MyCarftsFrag_;
import cn.hi028.android.highcommunity.activity.fragment.MyCollectionSwitchFrag;
import cn.hi028.android.highcommunity.activity.fragment.MyCollectionSwitchFrag_;
import cn.hi028.android.highcommunity.activity.fragment.MyMessageFrag;
import cn.hi028.android.highcommunity.activity.fragment.PersonalAuthFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServicePaymentFrag;
import cn.hi028.android.highcommunity.activity.fragment.SettingFrag;
import cn.hi028.android.highcommunity.activity.fragment.SettingFrag_;
import cn.hi028.android.highcommunity.activity.fragment.SysMessageFrag;
import cn.hi028.android.highcommunity.activity.fragment.TenementBillFrag;
import cn.hi028.android.highcommunity.activity.fragment.TenementBillFrag_;
import cn.hi028.android.highcommunity.activity.fragment.UserInfoFrag;
import cn.hi028.android.highcommunity.activity.fragment.UserInfoFrag_;
import cn.hi028.android.highcommunity.activity.fragment.WalletSwitchFrag;
import cn.hi028.android.highcommunity.activity.fragment.WalletSwitchFrag_;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：菜单的二级页面统统都在这里<br>  从侧边栏点进去的所有项目都在这个界面加载   联盟订单没有经过这里
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015/12/28<br>
 */
@EActivity(resName = "act_menu_left")
public class MenuLeftAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "MenuLeftAct";
    public static final String INTENTTAG = "MenuLeftActIntent";
    public static final String Tag = "MenuLeftAct:";

    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_menuleft_layout)
    View mTitleLayout;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    @ViewById(R.id.tv_right_name)
    TextView tv_right_name;
    HuiSuppGdCarFrag mGdCarment;

    @AfterViews
    void initView() {
    	Log.d(Tag,"------MenuLeftAct");
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (-1 == flag)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.MENU_LEFT_USERINFO:
                mTitleLayout.setVisibility(View.GONE);
                Log.d(Tag,"------gotoUSERINFO");
                UserInfoFrag mPersonal = (UserInfoFrag) new UserInfoFrag_();
                ft.replace(R.id.ll_menuleft_layout, mPersonal, PersonalAuthFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_TOPIC:
                mTitle.setText("我的话题");
                Log.d(Tag,"------goto我我的话题");
                MyMessageFrag mMyMessageFrag = new MyMessageFrag();
                ft.replace(R.id.ll_menuleft_layout, mMyMessageFrag, MyMessageFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_COLLECTION:
                mTitle.setText("我的收藏");
                Log.d(Tag,"------goto我的收藏");
                MyCollectionSwitchFrag mCollection = (MyCollectionSwitchFrag) new MyCollectionSwitchFrag_();
                ft.replace(R.id.ll_menuleft_layout, mCollection, MyCollectionSwitchFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_WALLET:
                mTitle.setText("我的钱包");
                Log.d(Tag,"------goto我的钱包");
                WalletSwitchFrag mSwitch = (WalletSwitchFrag) new WalletSwitchFrag_();
                ft.replace(R.id.ll_menuleft_layout, mSwitch, WalletSwitchFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_GDCAR://购物车
                mTitle.setText("购物车");
                Log.d(Tag,"------goto购物车");
                tv_right_name.setVisibility(View.VISIBLE);
                mGdCarment = (HuiSuppGdCarFrag) new HuiSuppGdCarFrag_();
                ft.replace(R.id.ll_menuleft_layout, mGdCarment, ServicePaymentFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_BILL:
                mTitle.setText("物业账单");
                Log.d(Tag,"------goto物业账单");
                TenementBillFrag mBill = (TenementBillFrag) new TenementBillFrag_();
                ft.replace(R.id.ll_menuleft_layout, mBill, TenementBillFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_ORDER:
                mTitle.setText("我的订单");
                Log.d(Tag,"------goto我的订单");
                HuiOrderFrag mOrder = (HuiOrderFrag) new HuiOrderFrag_();
                ft.replace(R.id.ll_menuleft_layout, mOrder, HuiOrderFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_ZHONGCOU:
                mTitle.setText("我的众筹");
                Log.d(Tag,"------goto我的众筹");
                HuiChipsOrderFrag mChipsOrder = (HuiChipsOrderFrag) new HuiChipsOrderFrag_();
                ft.replace(R.id.ll_menuleft_layout, mChipsOrder, HuiChipsOrderFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_CARFTS:
                mTitle.setText("我的手艺");
                Log.d(Tag,"------goto我的手艺");
                MyCarftsFrag mCarfts = (MyCarftsFrag) new MyCarftsFrag_();
                ft.replace(R.id.ll_menuleft_layout, mCarfts, MyCarftsFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_SETTING:
                mTitle.setText("设置");
                Log.d(Tag,"------goto设置");
                SettingFrag mSetting = (SettingFrag) new SettingFrag_();
                ft.replace(R.id.ll_menuleft_layout, mSetting, SettingFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_LEFT_MESSAGECENTER:
                mTitle.setText("与我相关");
                Log.d(Tag,"------goto消息");
                MessageCenterFrag mMessage = (MessageCenterFrag) new MessageCenterFrag_();

                ft.replace(R.id.ll_menuleft_layout, mMessage, MessageCenterFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_MYMESSAGE:
                mTitle.setText("与我相关");
                Log.d(Tag,"------goto与我相关");
                MyMessageFrag mMyMessage = (MyMessageFrag) new MyMessageFrag();

                ft.replace(R.id.ll_menuleft_layout, mMyMessage, MyMessageFrag.FRAGMENTTAG);
                break;
            case Constacts.MENU_SYSMESSAGE:
                mTitle.setText("系统消息");
                Log.d(Tag,"------goto系统消息");
                SysMessageFrag mSysMessage = (SysMessageFrag) new SysMessageFrag();
                Log.d(Tag,"------goto系统消息2");

                ft.replace(R.id.ll_menuleft_layout, mSysMessage, SysMessageFrag.FRAGMENTTAG);
                break;
        }
        ft.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0x22) {
            PersonalAuthFrag mFrag = (PersonalAuthFrag)
                    getSupportFragmentManager().findFragmentByTag(PersonalAuthFrag.FRAGMENTTAG);
            if (mFrag != null) {
                mFrag.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MyMessageFrag mMyMessageFrag = (MyMessageFrag) getSupportFragmentManager()
                .findFragmentByTag(MyMessageFrag.FRAGMENTTAG);
        MyCollectionSwitchFrag mCollection = (MyCollectionSwitchFrag) getSupportFragmentManager()
                .findFragmentByTag(MyCollectionSwitchFrag.FRAGMENTTAG);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mMyMessageFrag != null && mMyMessageFrag.onKeyDown()) {
            if (mMyMessageFrag != null ) {
                return true;
            } else if (mCollection != null && mCollection.onKeyDown()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

    @Click(R.id.tv_right_name)
    public void onRight() {
        if (tv_right_name.getText().toString().trim().equals("编辑")) {
            mGdCarment.btn_pay.setSelected(true);
            tv_right_name.setText("完成");
        } else {
            mGdCarment.btn_pay.setSelected(false);
            tv_right_name.setText("编辑");
        }
        mGdCarment.adapter.notifyDataSetChanged();
    }
}
