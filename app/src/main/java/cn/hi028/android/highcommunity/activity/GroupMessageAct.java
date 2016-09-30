/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.GroupMessageFrag;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：群组消息<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/31<br>
 */
@EActivity(resName = "act_groupmessage")
public class GroupMessageAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "GroupMessageAct";
    public static final String INTENTTAG = "GroupMessageActIntent";
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    @ViewById(R.id.img_second_GoupHead)
    CircleImageView mAvatar;

    String gid = "";
    String mHeadPic = "";

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mTitle.setText("群消息");
        gid = getIntent().getStringExtra(INTENTTAG);
        mHeadPic = getIntent().getStringExtra(ACTIVITYTAG);
        GroupMessageFrag mGroupMessage = (GroupMessageFrag) new GroupMessageFrag();
        ft.replace(R.id.ll_groupmessage_layout, mGroupMessage, GroupMessageFrag.FRAGMENTTAG);
        ft.commit();
        mAvatar.setVisibility(View.VISIBLE);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mHeadPic, mAvatar);
    }


    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

    @Click(R.id.img_second_GoupHead)
    void details() {
        Intent mDetail = new Intent(this, GeneratedClassUtils.get(GroupDataAct.class));
        mDetail.putExtra(GroupDataAct.ACTIVITYTAG, "Detils");
        mDetail.putExtra(GroupDataAct.INTENTTAG, gid);
        startActivity(mDetail);
    }
}
