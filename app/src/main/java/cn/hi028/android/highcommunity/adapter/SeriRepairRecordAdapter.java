/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.don.tools.TimeFormat;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.RepairBean;
import cn.hi028.android.highcommunity.bean.RepairBean;

/**
 * @功能：报修记录<br>
 * @作者：赵海<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
public class SeriRepairRecordAdapter extends BaseAdapter {

    public Context mContext;

    public List<RepairBean> getmList() {
        return mList;
    }

    public void setmList(List<RepairBean> mList) {
        if(!ListUtils.isEmpty(mList)){
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }

    }

    private List<RepairBean> mList = new ArrayList<RepairBean>();

    public SeriRepairRecordAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_seri_repair_record, null);
            mViewHolder.mState = (TextView) convertView
                    .findViewById(R.id.tv_repair_state);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.tv_repair_time);
            mViewHolder.mContent = (TextView) convertView
                    .findViewById(R.id.tv_repair_content);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RepairBean mBean = mList.get(i);
        if(mBean.getStatus()==0){
            mViewHolder.mState.setText("待维修");
            mViewHolder.mState.setTextColor(mContext.getResources().getColor(R.color.color_deep_red));
        }else{
            mViewHolder.mState.setTextColor(mContext.getResources().getColor(R.color.Defult_Color_AppGreen));
            mViewHolder.mState.setText("已维修");
        }

        mViewHolder.mTime.setText(TimeFormat.TimedateFormat(mBean.getCreate_time() * 1000));
        mViewHolder.mContent.setText(mBean.getContent());
        return convertView;
    }

    public class ViewHolder {
        TextView mState;
        TextView mTime;
        TextView mContent;
    }
}
