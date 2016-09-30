/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.don.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.CarftsBean;
import cn.hi028.android.highcommunity.bean.ScoreBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：积分兑换记录数据适配<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-1-29<br>
 */
public class WalletScoreAdapter extends BaseFragmentAdapter {
    Context mContext;
    List<ScoreBean.RecodeBean> mList = new ArrayList<ScoreBean.RecodeBean>();

    public WalletScoreAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_walletscore, null);
            mViewHolder.mContent = (TextView) convertView
                    .findViewById(R.id.tv_walletScore_Content);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.tv_walletScore_Time);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ScoreBean.RecodeBean mBean = mList.get(position);
        mViewHolder.mContent.setText(mBean.getScores() + "积分兑换成零钱￥" + mBean.getMoney());
        mViewHolder.mTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(Long.parseLong(mBean.getCreate_time())));
        return convertView;
    }

    private class ViewHolder {
        TextView mContent;
        TextView mTime;
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
