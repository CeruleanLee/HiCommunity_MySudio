/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.VallageCityFrag;
import cn.hi028.android.highcommunity.activity.fragment.VallageCountyFrag;
import cn.hi028.android.highcommunity.bean.CountyBean;
import cn.hi028.android.highcommunity.utils.CharacterParser;
import cn.hi028.android.highcommunity.utils.pinyinUtils.PinyinCityComparator;

/**
 *@功能：区县适配器<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-30<br>
 */
public class VallageCountyAdapter extends BaseAdapter   {
    VallageCountyFrag frag;

    public List<CountyBean> getData() {
        return data;
    }

    public void setData(List<CountyBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<CountyBean> data=new ArrayList<CountyBean>();
    public VallageCountyAdapter(VallageCountyFrag frag){
        this.frag=frag;
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
            convertView = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_vallage_city, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_header_title = (TextView) convertView.findViewById(R.id.tv_header_title);
            viewHolder.tv_vallage_city = (TextView) convertView
                    .findViewById(R.id.tv_vallage_city);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_vallage_city.setText(data.get(position).getName());
            viewHolder.tv_header_title.setVisibility(View.GONE);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.toVallageSelect(data.get(position));
            }
        });
        return convertView;
    }
class ViewHolder{
    TextView tv_header_title,tv_vallage_city;
}

}
