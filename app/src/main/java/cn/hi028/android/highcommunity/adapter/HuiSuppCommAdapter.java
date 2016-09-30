/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiSupplyFrag;
import cn.hi028.android.highcommunity.bean.HuiSuppCommBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：社区评论详情<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
public class HuiSuppCommAdapter extends BaseAdapter {
    HuiSupplyFrag frag;
    List<HuiSuppCommBean> data = new ArrayList<HuiSuppCommBean>();

    public List<HuiSuppCommBean> getData() {
        return data;
    }

    public void setData(List<HuiSuppCommBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public HuiSuppCommAdapter(HuiSupplyFrag frag) {
        this.frag = frag;
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_support_comment, null);
            viewHolder.tv_comment_content = (TextView) convertView.findViewById(R.id.tv_support_comment_content);
            viewHolder.tv_comment_time = (TextView) convertView
                    .findViewById(R.id.tv_support_comment_time);
            viewHolder.tv_comment_nickname = (TextView) convertView
                    .findViewById(R.id.tv_support_comment_uerName);
            viewHolder.civ_head = (CircleImageView) convertView
                    .findViewById(R.id.cimg_support_comment_head);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.get(position).getHead_pic(), viewHolder.civ_head);
        viewHolder.tv_comment_content.setText(data.get(position).getContent());
        viewHolder.tv_comment_nickname.setText(data.get(position).getNick());
        viewHolder.tv_comment_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(data.get(position).getTime()));
        return convertView;
    }

    class ViewHolder {
        TextView tv_comment_content, tv_comment_time, tv_comment_nickname;
        CircleImageView civ_head;
    }
}
