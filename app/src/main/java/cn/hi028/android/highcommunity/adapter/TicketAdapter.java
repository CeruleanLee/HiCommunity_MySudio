/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：优惠券适配器<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-02-17<br>
 */
public class TicketAdapter extends BaseAdapter {
    int[] big_Num = {R.mipmap.img_zero_big, R.mipmap.img_one_big, R.mipmap.img_two_big, R.mipmap.img_three_big, R.mipmap.img_four_big, R.mipmap.img_five_big, R.mipmap.img_six_big, R.mipmap.img_seven_big, R.mipmap.img_eight_big, R.mipmap.img_night_big};
    int[] smail_Num = {R.mipmap.img_zero_simal, R.mipmap.img_one_simal, R.mipmap.img_two_simal, R.mipmap.img_three_simal, R.mipmap.img_four_simal, R.mipmap.img_five_simal, R.mipmap.img_six_simal, R.mipmap.img_seven_simal, R.mipmap.img_eight_simal, R.mipmap.img_night_simal};
    Map<String, Integer> big_Map = new HashMap<String, Integer>();
    Map<String, Integer> smail_Map = new HashMap<String, Integer>();
    Activity act;

    public List<AllTicketBean> getData() {
        return data;
    }

    public void setData(List<AllTicketBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<AllTicketBean> data = new ArrayList<AllTicketBean>();
    int type;

    public TicketAdapter(Activity act, int type) {
        this.act = act;
        this.type = type;
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(act).inflate(R.layout.adapter_ticket, null);
            mViewHolder.ticket_unit_flag = (ImageView) convertView
                    .findViewById(R.id.img_unit_type);
            mViewHolder.ticket_type = (ImageView) convertView
                    .findViewById(R.id.img_type);
            mViewHolder.tv_useType = (TextView) convertView
                    .findViewById(R.id.tv_ticket_useType);
            mViewHolder.tv_ticket = (TextView) convertView
                    .findViewById(R.id.tv_ticket);
            mViewHolder.tv_ticket_use = (TextView) convertView
                    .findViewById(R.id.tv_ticket_use);
            mViewHolder.tv_ticket_time = (TextView) convertView
                    .findViewById(R.id.tv_ticket_time);
            mViewHolder.fl_bg = (FrameLayout) convertView
                    .findViewById(R.id.fl_bg);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_ticket.setText(data.get(position).getTicket_value() + "");
        mViewHolder.tv_ticket_use.setText("满" + data.get(position).getLeast() + "消费");
        mViewHolder.tv_ticket_time.setText("有效期至" + TimeUtil.getDayTime(data.get(position).getEnd_time()));
        if ("优惠券".equals(data.get(position).getTicket_type())) {
            mViewHolder.ticket_type.setSelected(false);
            mViewHolder.ticket_unit_flag.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.ticket_unit_flag.setVisibility(View.GONE);
            mViewHolder.ticket_type.setSelected(true);
        }
        mViewHolder.tv_useType.setText(data.get(position).getUse_name());
        if (1 == data.get(position).getUse_to()) {
            mViewHolder.tv_ticket_use.setTextColor(act.getResources().getColor(R.color.color_ticket_least1));
            mViewHolder.fl_bg.setBackgroundResource(R.mipmap.bg_ticket_orange);
        } else if (2 == data.get(position).getUse_to()) {
            mViewHolder.tv_ticket_use.setTextColor(act.getResources().getColor(R.color.color_ticket_least2));

            mViewHolder.fl_bg.setBackgroundResource(R.mipmap.bg_ticket_blue);
        } else {
            mViewHolder.tv_ticket_use.setTextColor(act.getResources().getColor(R.color.color_ticket_least3));
            mViewHolder.fl_bg.setBackgroundResource(R.mipmap.bg_ticket_green);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != 0) {
                    Intent intent = act.getIntent();
                    intent.putExtra("ticket", data.get(position));
                    act.setResult(TicketAct.TICKET_RESULT, intent);
                    act.finish();
                }
            }
        });
        return convertView;
    }


    public class ViewHolder {
        ImageView ticket_type, ticket_unit_flag;
        TextView tv_ticket;
        TextView tv_ticket_time;
        TextView tv_ticket_use;
        TextView tv_useType;
        FrameLayout fl_bg;
    }

}
