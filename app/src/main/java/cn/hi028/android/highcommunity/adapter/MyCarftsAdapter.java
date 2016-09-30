/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.CarftsBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：手艺人列表适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015-12-29<br>
 */
public class MyCarftsAdapter extends BaseFragmentAdapter {
    Context mContext;
    List<CarftsBean> mList = new ArrayList<CarftsBean>();

    public MyCarftsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CarftsBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_servicecarfts, null);
            mViewHolder.mAvatar = (CircleImageView) convertView
                    .findViewById(R.id.civl_ServiceCarftsItem_avatar);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_ServiceCarftsItem_Name);
            mViewHolder.mPhone = (TextView) convertView
                    .findViewById(R.id.tv_ServiceCarftsItem_phone);
            mViewHolder.mStatus = (TextView) convertView
                    .findViewById(R.id.tv_ServiceCarftsItem_status);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        CarftsBean mBean = mList.get(position);
        if (mBean.getStatus() != null) {
            mViewHolder.mStatus.setVisibility(View.VISIBLE);
            if (mBean.getStatus().equals("-1")) {
                mViewHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.color_deep_red));
                mViewHolder.mStatus.setText("审核失败");
            } else if (mBean.getStatus().equals("0")) {
                mViewHolder.mStatus.setText("审核中");
            } else {
                mViewHolder.mStatus.setText("已通过");
            }
        }
        mViewHolder.mName.setText(mBean.getTitle());
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mViewHolder.mAvatar);
        mViewHolder.mPhone.setText(mBean.getName() + ":" + mBean.getTel());
        return convertView;
    }

    private class ViewHolder {
        CircleImageView mAvatar;
        TextView mName;
        TextView mPhone;
        TextView mStatus;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<CarftsBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList.addAll((List<CarftsBean>) mObject);
        }
        notifyDataSetChanged();
        super.RefreshData(mObject);
    }

}
