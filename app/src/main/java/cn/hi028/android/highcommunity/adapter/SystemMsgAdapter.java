/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.don.tools.TimeFormat;

import java.util.ArrayList;
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
    static final String Tag = "~~~ SystemMsgAdapter:";
    public Context mContext;
    public int flag = 0;
    //    private List<NoticeBean> mList = new ArrayList<NoticeBean>();
    List<SystemMessageBean.SystemMsgDataEntity> mList=new ArrayList<SystemMessageBean.SystemMsgDataEntity>();

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
            mViewHolder.mTimeRight = (TextView) convertView.findViewById(R.id.item_sysmsg_time_right);
            mViewHolder.mOrderId = (TextView) convertView.findViewById(R.id.item_sysmsg_order_id);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        SystemMessageBean.SystemMsgDataEntity mBean = mList.get(i);
        String type = mBean.getType();
        int mType = Integer.parseInt(type);
        Log.d(Tag, "type===" + mType);
        switch (mType) {
            case 1://订单消息
                mViewHolder.mTime.setVisibility(View.VISIBLE);
                mViewHolder.mTimeRight.setVisibility(View.GONE);
                if (mBean.getPic() == null || mBean.getPic().equals("")) {
                    BpiUniveralImage.displayImage("drawable://" + R.drawable.ic_nopic_default, mViewHolder.mPic);
                } else {
                    if (mViewHolder.mPic != null) {
                        BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mPic);
                    } else {
                        Log.d("~~~", "mViewHolder.mYWHAvatar null");
                    }
                }
                mViewHolder.mTime.setText(TimeFormat.TimedateFormat(Long.parseLong(mBean.getCreate_time()) * 1000));
                if (mBean.getOrder_num() != null) {
                    mViewHolder.mOrderId.setVisibility(View.VISIBLE);
                    mViewHolder.mOrderId.setText("运单号：" + mBean.getOrder_num());
                }
//                else{
//                    mViewHolder.mOrderId.setVisibility(View.GONE);
//                }
                break;
            case 2://优惠券
                mViewHolder.mTime.setVisibility(View.GONE);
                mViewHolder.mTimeRight.setVisibility(View.VISIBLE);
                mViewHolder.mOrderId.setVisibility(View.GONE);
                if (mBean.getPic() == null || mBean.getPic().equals("")) {
                    BpiUniveralImage.displayImage("drawable://" + R.mipmap.img_sysmsg_coupon, mViewHolder.mPic);
                }
                mViewHolder.mTimeRight.setText(TimeFormat.TimedateFormat(Long.parseLong(mBean.getCreate_time()) * 1000));
//                else {
//                    if (mViewHolder.mPic != null) {
//                        BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mPic);
//                    } else {
//                        Log.d("~~~", "mViewHolder.mYWHAvatar null");
//                    }
//                }

                break;
            case 3://积分

                mViewHolder.mTime.setVisibility(View.GONE);
                mViewHolder.mTimeRight.setVisibility(View.VISIBLE);
                mViewHolder.mOrderId.setVisibility(View.GONE);
                if (mBean.getPic() == null || mBean.getPic().equals("")) {
                    BpiUniveralImage.displayImage("drawable://" + R.mipmap.img_sysmsg_jifen, mViewHolder.mPic);
                }
                mViewHolder.mTimeRight.setText(TimeFormat.TimedateFormat(Long.parseLong(mBean.getCreate_time()) * 1000));
                break;
        }
        String title=mBean.getTitle();
        Log.d(Tag,"TITLE "+title.toString());
        if (title.indexOf("过期")!=-1) {
            Log.d(Tag,"1 ");
            mViewHolder.mTitle.setTextColor(Color.RED);
        } else if(title.indexOf("恭喜")!=-1) {
            Log.d(Tag,"2 ");
            mViewHolder.mTitle.setTextColor(0xff2D8719);
            //#2D8719
        }else if (title.indexOf("订单")!=-1){
            Log.d(Tag,"3 ");

            mViewHolder.mTitle.setTextColor(0xff2D8719);


        }
        mViewHolder.mTitle.setText(mBean.getTitle());
        mViewHolder.mContent.setText(mBean.getContent());
        return convertView;
    }

    class ViewHolder {
        TextView mTitle;
        ImageView mPic;
        TextView mContent;
        TextView mTime, mTimeRight;
        TextView mOrderId;

    }
    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
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
