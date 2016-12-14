/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SuperViseBean;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治 监督_询问adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoSuperviseAdapter_Inq extends BaseFragmentAdapter {
    public static final int TAG_INQUIRY_DETAIL = 1;
    /**留言详情**/
    public static final int TAG_MESSAGE_DETAIL = 8;
    public AddressListFrag mFrag;
    List<Auto_SuperViseBean.SuperViseDataEntity> mList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    private boolean isMessage = false;

    public AutoSuperviseAdapter_Inq(List<Auto_SuperViseBean.SuperViseDataEntity> list, Context context, boolean isMessage) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isMessage = isMessage;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_SuperViseBean.SuperViseDataEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_supervise_inquery, null);
            mViewHolder.mTitle = (TextView) convertView
                    .findViewById(R.id.item_supervise_inquiry_title);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.item_supervise_inquiry_time);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_SuperViseBean.SuperViseDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
//        TimeUtil.getDayAllTime(Long.parseLong(mBean.getCreate_time()))
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getTime())));
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());
        if (isMessage) {
            // 跳转得到留言详情
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(context,"mBean.getId() "+mBean.getId(),Toast.LENGTH_SHORT).show();
                    Log.d("~~~", "留言adapter");
                    Intent mIntent_report = new Intent(context, AutonomousAct_Third.class);
                    mIntent_report.putExtra("title", TAG_MESSAGE_DETAIL);
                    mIntent_report.putExtra("message_id", mBean.getId());
                    context.startActivity(mIntent_report);
                }
            });

        } else {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(context,"mBean.getId() "+mBean.getId(),Toast.LENGTH_SHORT).show();
                    Log.d("~~~", "询问adapter");
                    Intent mIntent_report = new Intent(context, AutonomousAct_Third.class);
                    mIntent_report.putExtra("title", TAG_INQUIRY_DETAIL);
                    mIntent_report.putExtra("inquiry_id", mBean.getId());
                    context.startActivity(mIntent_report);
                }
            });
        }


        return convertView;
    }


    class ViewHolder {
        TextView mTitle;
        TextView mTime;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_SuperViseBean.SuperViseDataEntity>) mObject;

        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

//    /**
//     * 动态刷新数据
//     *
//     * @param isReplay
//     * @param content
//     */
//    public void setNewData(boolean isReplay, String content, String id) {
//        if (isReplay) {
//            mTempReply.setReply_content(content);
//            mTempReplies.getReply().add(mTempReply);
//        } else {
//            RepliesBean mTempBean = new RepliesBean();
//            mTempBean.setParentId(id);
//            mTempBean.setHead_pic(HighCommunityApplication.mUserInfo.getHead_pic());
//            mTempBean.setId(HighCommunityApplication.mUserInfo.getId() + "");
//            mTempBean.setNick(HighCommunityApplication.mUserInfo.getNick());
//            mTempBean.setReply_time(System.currentTimeMillis() / 1000 + "");
//            mTempBean.setReply_content(content);
//            mTempBean.setReply(new ArrayList<ReplyBean>());
//            mList.add(mTempBean);
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }

    private void avoidHintColor(View view) {
        if (view instanceof TextView)
            ((TextView) view).setHighlightColor(mFrag.getActivity().getResources().getColor(R.color.defult_color_transparent));
    }
}
