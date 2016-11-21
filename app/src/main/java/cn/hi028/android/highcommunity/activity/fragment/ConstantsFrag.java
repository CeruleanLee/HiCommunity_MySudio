/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;

/**
 * @功能：联系我们<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-02-15<br>
 */
@EFragment(resName = "frag_constants")
public class ConstantsFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "ConstantsFrag";
    @ViewById(R.id.tv_version)
    TextView tv_version;

    @AfterViews
    void initView() {
        tv_version.setText("嗨社区 " + getVersion()+" ");
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    private String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }
}
