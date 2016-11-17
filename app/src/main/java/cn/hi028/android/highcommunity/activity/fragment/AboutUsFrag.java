/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.GuideMainActivity;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：关于我们<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
@EFragment(resName = "frag_aboutus")
public class AboutUsFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "AboutUsFrag";
    @ViewById(R.id.tv_AboutUs_AppVersion)
    TextView mAppVersion;
    @ViewById(R.id.tv_AboutUs_Protocol)
    TextView mProtocol;
    @ViewById(R.id.tv_AboutUs_FunctionIntro)
    TextView mFunctionIntro;
    @ViewById(R.id.tv_AboutUs_Share)
    TextView mShare;
    @ViewById(R.id.tv_AboutUs_ContactUs)
    TextView mContactUs;
    @ViewById(R.id.tv_AboutUs_UpdateContent)
    TextView mUpdateContent;

    PopupWindow mWaitingWindow;

    /**
     * 联系我们
     */
    @Click(R.id.tv_AboutUs_ContactUs)
    void constactUs() {
//        Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftThirdAct.class));
        Intent mIntent=null;
        mIntent.putExtra(MenuLeftThirdAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_SETTINGCONSTACT);
        startActivity(mIntent);
    }

    /**
     * 功能介绍
     */
    @Click(R.id.tv_AboutUs_FunctionIntro)
    void functionIntro() {
        Intent guide = new Intent(getActivity(), GuideMainActivity.class);
        startActivity(guide);
//        BrowseActivity.toBrowseActivity(getActivity(), "功能介绍", "http://028hi.cn/api/default/function.html");
    }

    /**
     * 用户协议
     */
    @Click(R.id.tv_AboutUs_Protocol)
    void protocolTo() {
        BrowseActivity.toBrowseActivity(getActivity(), "用户协议", "http://028hi.cn/api/default/agreement.html");
    }

    /**
     * 联系我们
     */
    @Click(R.id.tv_AboutUs_Share)
    void share() {
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowGroupShare(getActivity(), "http://www.028hi.cn/index.html");
        mWaitingWindow.showAtLocation(mShare, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
    }
}
