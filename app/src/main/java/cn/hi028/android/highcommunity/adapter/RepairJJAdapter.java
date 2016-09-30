/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.SeriRepairJJFrag;
import cn.hi028.android.highcommunity.activity.fragment.VallageCityFrag;
import cn.hi028.android.highcommunity.bean.RepairJJBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：紧急保修适配器<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-30<br>
 */
public class RepairJJAdapter extends BaseAdapter implements SectionIndexer {
    SeriRepairJJFrag frag;

    public List<RepairJJBean> getData() {
        return data;
    }

    public void setData(List<RepairJJBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    List<RepairJJBean> data = new ArrayList<RepairJJBean>();

    public RepairJJAdapter(SeriRepairJJFrag frag) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_repari_jj, null);
            viewHolder.tv_repair_name = (TextView) convertView.findViewById(R.id.tv_repair_name);
            viewHolder.tv_repair_phone = (TextView) convertView
                    .findViewById(R.id.tv_repair_phone);
            viewHolder.tv_header_title = (TextView) convertView
                    .findViewById(R.id.tv_header_title);
            viewHolder.ll_title = (LinearLayout) convertView
                    .findViewById(R.id.ll_title);
            convertView.setTag(R.layout.adapter_vallage_select, viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_repair_name.setText(data.get(position).getName());
        viewHolder.tv_repair_phone.setText(data.get(position).getTel());
        viewHolder.tv_repair_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighCommunityUtils.callDialogPhone(frag.getActivity(), data.get(position).getTel());
            }
        });
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.ll_title.setVisibility(View.VISIBLE);
            viewHolder.tv_header_title.setText(data.get(position).getSortLetters());
        } else {
            viewHolder.ll_title.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_header_title, tv_repair_name, tv_repair_phone;
        LinearLayout ll_title;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = data.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return data.get(position).getSortLetters().charAt(0);
    }
}
