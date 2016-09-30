/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.bean.GridMembersBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：群成员数据adapter<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
public class GroupGrideAdapter extends BaseFragmentAdapter {

    public Context mContext;
    List<GridMembersBean> mlist = new ArrayList<GridMembersBean>();

    public GroupGrideAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public GridMembersBean getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_group_member, null);
            mViewHolder.mAvatar = (CircleImageView) convertView
                    .findViewById(R.id.civ_groupmember_img);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final GridMembersBean mBean = mlist.get(i);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mViewHolder.mAvatar);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mUserInfo = new Intent(mContext, GeneratedClassUtils.get(MenuLeftAct.class));
                mUserInfo.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_USERINFO);
                mUserInfo.putExtra(MenuLeftAct.INTENTTAG, mBean.getUid() + "");
                mContext.startActivity(mUserInfo);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        CircleImageView mAvatar;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mlist = (List<GridMembersBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
