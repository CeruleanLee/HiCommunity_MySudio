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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SuperViseBean;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治 监督_汇报adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoSuperviseAdapter_Re extends BaseFragmentAdapter {
    public static final int TAG_REPORT_DETAIL = 0;
    public AddressListFrag mFrag;
    List<Auto_SuperViseBean.SuperViseDataEntity> mList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AutoSuperviseAdapter_Re(List<Auto_SuperViseBean.SuperViseDataEntity> list, Context context) {
        super();
        this.mList = list;
        if(this.mList == null){
            this.mList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_supervise_report, null);
            mViewHolder.mTitle = (TextView) convertView
                    .findViewById(R.id.item_supervise_report_title);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.item_supervise_report_time);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_SuperViseBean.SuperViseDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
//        TimeUtil.getDayAllTime(Long.parseLong(mBean.getCreate_time()))
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getTime())));
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"position "+position,Toast.LENGTH_SHORT).show();
                Intent mIntent_report=new Intent(context, AutonomousAct_Third.class);
                mIntent_report.putExtra("title",TAG_REPORT_DETAIL);
                mIntent_report.putExtra("reportDetail_id",mBean.getId());
                context.startActivity(mIntent_report);
            }
        });




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


}
