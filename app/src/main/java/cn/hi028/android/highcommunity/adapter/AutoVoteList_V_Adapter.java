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
 * @功能：自治大厅 投票列表 - 选举adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoVoteList_V_Adapter extends BaseFragmentAdapter {
    public static final int TAG_QUESTION_DETAIL = 4;
    public AddressListFrag mFrag;
    List<Auto_VoteList_Vote.VoteVVDataEntity> mList = new ArrayList<Auto_VoteList_Vote.VoteVVDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AutoVoteList_V_Adapter(List<Auto_VoteList_Vote.VoteVVDataEntity> list, Context context) {
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
            convertView = layoutInflater.inflate(R.layout.item_autovotelist_vote, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_autoVotelist_Vtitle);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.item_autoVotelist_Vtime);
            mViewHolder.mVBuilding = (TextView) convertView.findViewById(R.id.item_autoVotelist_Vbuilding);
            mViewHolder.mIsJoin = (TextView) convertView.findViewById(R.id.item_autoVotelist_isJoin);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_VoteList_Vote.VoteVVDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getCreate_time())));
        mViewHolder.mVBuilding.setText("适用于："+mBean.getBuilding());
        if (mBean.getIs_voted()==1){
            mViewHolder.mIsJoin.setVisibility(View.VISIBLE);
        }else{
            mViewHolder.mIsJoin.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里写反了  跳去了问询详情
                Intent mIntent_report=new Intent(context, AutonomousAct_Third.class);
                mIntent_report.putExtra("title",TAG_QUESTION_DETAIL);
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
    static class ViewHolder {
        TextView mTitle;
        TextView mTime;
        TextView mVBuilding,mIsJoin;
    }
}
