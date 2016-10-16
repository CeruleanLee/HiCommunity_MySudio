/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressAct;
import cn.hi028.android.highcommunity.activity.SettingAct;
import cn.hi028.android.highcommunity.activity.WelcomeAct;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.updateutil.UpdateUtil;
import cn.hi028.android.highcommunity.utils.wchatpay.ClearUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：设置界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
@EFragment(resName = "frag_setting")
public class SettingFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "SettingFrag";
    @ViewById(R.id.ll_setting_thirdPary_qq)
    LinearLayout mQQ;
    @ViewById(R.id.ll_setting_thirdPary_weibo)
    LinearLayout mWeibo;
    @ViewById(R.id.ll_setting_thirdPary_weixin)
    LinearLayout mWeixin;
    @ViewById(R.id.tv_settings_Logout)
    TextView tv_settings_Logout;

    Intent mIntent;

    @AfterViews
    void initView() {
        if (HighCommunityUtils.isLogin()) {
            tv_settings_Logout.setVisibility(View.VISIBLE);
        } else {
            tv_settings_Logout.setVisibility(View.INVISIBLE);
        }
        mIntent = new Intent(getActivity(), GeneratedClassUtils.get(SettingAct.class));
        mQQ.setOnClickListener(null);
        mWeibo.setOnClickListener(null);
        mWeixin.setOnClickListener(null);
    }

    @Click(R.id.tv_settings_modifyPassword)
    void modify() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            mIntent.putExtra(SettingAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_SETTINGMODIFYPSD);
            startActivity(mIntent);
        }
    }

    @Click(R.id.tv_settings_AboutUs)
    void aboutus() {
        mIntent.putExtra(SettingAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_SETTINGABOUTUS);
        startActivity(mIntent);
    }

    @Click(R.id.tv_settings_clear)
    void clearCache() {
        ECAlertDialog dialog = ECAlertDialog.buildAlert(getActivity(), "是否清除缓存？", "清除", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ClearUtils.cleanApplicationData(SettingFrag.this.getActivity());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                HighCommunityUtils.GetInstantiation().ShowToast("缓存数据已清除", 0);
                            }
                        });
                    }
                }).start();
            }
        }, null);
        dialog.setTitle("提示");
        dialog.show();
    }

    @Click(R.id.tv_settings_ReciveAddress)
    void address() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            Intent mAddress = new Intent(getActivity(), GeneratedClassUtils.get(AddressAct.class));
            mAddress.putExtra(AddressAct.ACTIVITYTAG, "fromSetting");
            startActivity(mAddress);
        }
    }

    @Click(R.id.tv_settings_PhoneNumber)
    void call() {
//        mIntent.putExtra(SettingAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_SETTINGABOUTUS);
//        startActivity(mIntent);
    }

    @Click(R.id.tv_settings_AppUpdate)
    void update() {
        //检查更新
      boolean isUpdate=  new UpdateUtil(getActivity(), getContext()).checkIsToUpdate();
        if (isUpdate){
            new UpdateUtil(getActivity(), getContext()).initUpdate();
        }else{
            Toast.makeText(getActivity(), "已经是最新版本了", Toast.LENGTH_SHORT).show();
        }
//        UmengUpdateAgent.setUpdateAutoPopup(false);
//
//        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//            @Override
//            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
//                switch (i) {
//                    case UpdateStatus.Yes: // has update
//                        UmengUpdateAgent.showUpdateDialog(getActivity(), updateResponse);
//                        break;
//                    case UpdateStatus.No: // has no update
//                        Toast.makeText(getActivity(), "已经是最新版本了", Toast.LENGTH_SHORT).show();
//                        break;
//                    case UpdateStatus.NoneWifi: // none wifi
//                        Toast.makeText(getActivity(), "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
//                        break;
//                    case UpdateStatus.Timeout: // time out
//                        Toast.makeText(getActivity(), "超时", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
//        UmengUpdateAgent.forceUpdate(getActivity());
    }

    @Click(R.id.tv_settings_Logout)
    void Logout() {
        ECAlertDialog dialog = ECAlertDialog.buildAlert(getActivity(), "是否退出登录？", "退出", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HighCommunityApplication.isLogOut = true;
                HighCommunityApplication.share.edit().putString(Constacts.APPTOKEN, "").commit();
                HighCommunityApplication.mUserInfo.setToken("");
                HighCommunityApplication.mUserInfo.setId(0);
                Intent mToMain = new Intent(getActivity(), WelcomeAct.class);
                startActivity(mToMain);
                getActivity().finish();
            }
        }, null);
        dialog.setTitle("提示");
        dialog.show();
    }

}
