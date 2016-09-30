/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.ScoreBean;

/**
 * @功能：所有类型优惠券数据适配<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-1-29<br>
 */
public class WalletTicketAdapter extends BaseFragmentAdapter {
    Context mContext;
    List<ScoreBean.RecodeBean> mList = new ArrayList<ScoreBean.RecodeBean>();

    public WalletTicketAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public ScoreBean.RecodeBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_walletticket, null);
            mViewHolder.mContent = (ImageView) convertView
                    .findViewById(R.id.iv_walletticket_imageView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView mContent;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<ScoreBean.RecodeBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList.addAll((List<ScoreBean.RecodeBean>) mObject);
        }
        notifyDataSetChanged();
        super.RefreshData(mObject);
    }

}
