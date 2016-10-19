/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteList_Vote;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 投票列表 - 问卷adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoVoteList_Q_Adapter extends BaseFragmentAdapter {
    public static final int TAG_VOTE_DETAIL = 3;
    public AddressListFrag mFrag;
    List<Auto_VoteList_Vote.VoteVVDataEntity> mList = new ArrayList<Auto_VoteList_Vote.VoteVVDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AutoVoteList_Q_Adapter(List<Auto_VoteList_Vote.VoteVVDataEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_VoteList_Vote.VoteVVDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_VoteList_Vote.VoteVVDataEntity getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_autovotelist_questions, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_autoVotelist_Qtitle);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.item_autoVotelist_Qtime);
            mViewHolder.mbuilding = (TextView) convertView.findViewById(R.id.item_autoVotelist_Qbuilding);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_VoteList_Vote.VoteVVDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
//        TimeUtil.getDayAllTime(Long.parseLong(mBean.getCreate_time()))
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getCreate_time())));
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());
        mViewHolder.mbuilding.setText("适用于：" + mBean.getBuilding());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"mBean.getId() "+mBean.getId(),Toast.LENGTH_SHORT).show();
                //这里要跳去选举详情
                Intent mIntent_report=new Intent(context, AutonomousAct_Third.class);
                mIntent_report.putExtra("title",TAG_VOTE_DETAIL);
                mIntent_report.putExtra("question_id",mBean.getId());
                mIntent_report.putExtra("is_voted",mBean.getIs_voted());
                mIntent_report.putExtra("type",2);

                context.startActivity(mIntent_report);
            }
        });


        return convertView;
    }


    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_VoteList_Vote.VoteVVDataEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        TextView mTitle;
        TextView mTime;
        TextView mbuilding;

    }
}
