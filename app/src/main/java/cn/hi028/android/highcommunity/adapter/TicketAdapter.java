/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;
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
    BitmapUtils mBitmapUtils;

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
        mBitmapUtils = MBitmapHolder.getBitmapUtils(act);
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
            mViewHolder.ticket_unit_flag = (ImageView) convertView.findViewById(R.id.img_unit_type);
            mViewHolder.ticket_type = (ImageView) convertView.findViewById(R.id.img_type);
            mViewHolder.tv_useType = (TextView) convertView.findViewById(R.id.tv_ticket_useType);
            mViewHolder.tv_ticket_useType_prize = (TextView) convertView.findViewById(R.id.tv_ticket_useType_prize);
            mViewHolder.tv_ticket = (TextView) convertView.findViewById(R.id.tv_ticket);
            mViewHolder.tv_ticket_use = (TextView) convertView.findViewById(R.id.tv_ticket_use);
            mViewHolder.tv_ticket_use_prize = (TextView) convertView.findViewById(R.id.tv_ticket_use_prize);
            mViewHolder.tv_ticket_time = (TextView) convertView.findViewById(R.id.tv_ticket_time);
            mViewHolder.tv_ticket_time_prize = (TextView) convertView.findViewById(R.id.tv_ticket_time_prize);
            mViewHolder.fl_bg = convertView.findViewById(R.id.fl_bg);
            mViewHolder.fl_bg_prize = convertView.findViewById(R.id.fl_bg_prize);
            mViewHolder.tv_isused = (TextView) convertView.findViewById(R.id.prize_isused);
            mViewHolder.prize_img = (ImageView) convertView.findViewById(R.id.ticket_prize_img);
            mViewHolder.tv_prize_name = (TextView) convertView.findViewById(R.id.img_type_tv);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        AllTicketBean mBean = data.get(position);
        mViewHolder.fl_bg_prize.setVisibility(View.GONE);
        mViewHolder.fl_bg.setVisibility(View.VISIBLE);
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
        if (mBean.getUse_to() == 4) {
            //兑换奖品
            mViewHolder.fl_bg_prize.setVisibility(View.VISIBLE);
            mViewHolder.fl_bg.setVisibility(View.GONE);
            if (mBean.getPic() != null) {
                mBitmapUtils.display(mViewHolder.prize_img, Constacts.IMAGEHTTP + mBean.getPic());
            }
            if (mBean.getName() != null) {
                mViewHolder.tv_prize_name.setText(mBean.getName());
            }
            if (mBean.getIs_used().equals("0")) {//未兑换
                mViewHolder.tv_isused.setText("未兑换");

                mViewHolder.fl_bg_prize.setBackgroundResource(R.mipmap.bg_ticket_red);
            } else if (mBean.getIs_used().equals("1")) {
                mViewHolder.tv_isused.setText("已兑换");
                mViewHolder.fl_bg_prize.setBackgroundResource(R.mipmap.bg_ticket_grey);
            }
            mViewHolder.tv_ticket_use_prize.setText("兑换码：" +mBean.getCode());
            mViewHolder.tv_ticket_useType_prize.setText("活动使用");
            mViewHolder.tv_ticket_time_prize.setText("当日有效");
        } else if (1 == data.get(position).getUse_to()) {
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
        ImageView ticket_type, ticket_unit_flag, prize_img;
        TextView tv_ticket, tv_isused, tv_prize_name;
        TextView tv_ticket_time,tv_ticket_time_prize;
        TextView tv_ticket_use,tv_ticket_use_prize;
        TextView tv_useType,tv_ticket_useType_prize;
        View fl_bg,fl_bg_prize;
    }

}
