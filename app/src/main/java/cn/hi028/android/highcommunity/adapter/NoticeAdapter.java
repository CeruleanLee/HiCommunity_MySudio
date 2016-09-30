/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.tools.TimeFormat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.NoticeBean;
import cn.hi028.android.highcommunity.bean.PraisesBean;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：公告adapter<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
public class NoticeAdapter extends BaseFragmentAdapter {

    public Context mContext;
    public int flag = 0;
    private List<NoticeBean> mList = new ArrayList<NoticeBean>();

    public NoticeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public NoticeAdapter(Context mContext, int flag) {
        this.mContext = mContext;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NoticeBean getItem(int position) {
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
            if (flag == 0) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_service_notice, null);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_service_notice_one, null);

            }
            mViewHolder.mTitle = (TextView) convertView
                    .findViewById(R.id.tv_ServiceNotice_item_Title);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.tv_ServiceNotice_item_Time);
            mViewHolder.mContent = (TextView) convertView
                    .findViewById(R.id.tv_ServiceNotice_item_Content);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        NoticeBean mBean = mList.get(i);
        mViewHolder.mTitle.setText(mBean.getTitle());
        mViewHolder.mTime.setText(TimeFormat.TimedateFormat(Long.parseLong(mBean.getCreate_time()) * 1000));
        if (flag == 0) {
            mViewHolder.mContent.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mContent.setVisibility(View.GONE);
        }
        mViewHolder.mContent.setText(mBean.getContent());
        return convertView;
    }

    public class ViewHolder {
        TextView mTitle;
        TextView mTime;
        TextView mContent;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<NoticeBean>) mObject;
        }
        notifyDataSetChanged();
    }

    @Override
    public void RefreshData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList.addAll((List<NoticeBean>) mObject);
        }
        notifyDataSetChanged();
        super.RefreshData(mObject);
    }
}
