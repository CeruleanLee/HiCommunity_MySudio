/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.don.tools.BpiUniveralImage;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.bean.PraisesBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：活动详情页面点赞人员头像adapter<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
public class ActivityAssistAdapter extends BaseFragmentAdapter {

    public Context mContext;
    private List<PraisesBean> mList = new ArrayList<PraisesBean>();

    public ActivityAssistAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PraisesBean getItem(int position) {
        return mList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_activity_assist, null);
            mViewHolder.mAvatar = (CircleImageView) convertView
                    .findViewById(R.id.civ_activityAssist_img);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final PraisesBean mBean = mList.get(i);
        if (mBean.getHead_pic() == null || mBean.getHead_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, mViewHolder.mAvatar);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getHead_pic(), mViewHolder.mAvatar);
        }
        mViewHolder.mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mDetails = new Intent(mContext, GeneratedClassUtils.get(MenuLeftAct.class));
                mDetails.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_USERINFO);
                mDetails.putExtra(MenuLeftAct.INTENTTAG, mBean.getId() + "");
                mContext.startActivity(mDetails);
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
            mList = (List<PraisesBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
