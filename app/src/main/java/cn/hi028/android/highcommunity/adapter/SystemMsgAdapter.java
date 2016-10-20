/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.don.tools.TimeFormat;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.SystemMessageBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：系统消息adapter<br>
 * @作者： Lee_yting<br>
 * @版本：1.0<br>
 * @时间：2016/10/16<br>
 */
public class SystemMsgAdapter extends BaseFragmentAdapter {

    public Context mContext;
    public int flag = 0;
    //    private List<NoticeBean> mList = new ArrayList<NoticeBean>();
    List<SystemMessageBean.SystemMsgDataEntity> mList;

    public SystemMsgAdapter(Context mContext, List<SystemMessageBean.SystemMsgDataEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public SystemMsgAdapter(Context mContext, int flag) {
        this.mContext = mContext;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SystemMessageBean.SystemMsgDataEntity getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_systemmsg_order, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_sysmsg_title);
            mViewHolder.mPic = (ImageView) convertView.findViewById(R.id.item_sysmsg_pic);
            mViewHolder.mContent = (TextView) convertView.findViewById(R.id.item_sysmsg_ontent);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.item_sysmsg_time);
            mViewHolder.mOrderId = (TextView) convertView.findViewById(R.id.item_sysmsg_order_id);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        SystemMessageBean.SystemMsgDataEntity mBean = mList.get(i);
        mViewHolder.mTitle.setText(mBean.getTitle());
        if (mBean.getPic() == null || mBean.getPic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, mViewHolder.mPic);
        } else {
            if (mViewHolder.mPic!=null){
                BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mPic);
            }else{
                Log.d("~~~","mViewHolder.mYWHAvatar null");
            }
        }
        mViewHolder.mContent.setText(mBean.getContent());
        mViewHolder.mTime.setText(TimeFormat.TimedateFormat(Long.parseLong(mBean.getCreate_time()) * 1000));
        mViewHolder.mOrderId.setText("运单号："+mBean.getOrder_num());
        return convertView;
    }

    class ViewHolder {
        TextView mTitle;
        ImageView mPic;
        TextView mContent;
        TextView mTime;
        TextView mOrderId;

    }
    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<SystemMessageBean.SystemMsgDataEntity>) mObject;
        }
        notifyDataSetChanged();
    }

    @Override
    public void RefreshData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList.addAll((List<SystemMessageBean.SystemMsgDataEntity>) mObject);
        }
        notifyDataSetChanged();
        super.RefreshData(mObject);
    }


}
