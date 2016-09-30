/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.activity.fragment.ServiceTenementFrag;
import cn.hi028.android.highcommunity.activity.fragment.VallageCityFrag;
import cn.hi028.android.highcommunity.bean.TenementHouseBean;
import cn.hi028.android.highcommunity.utils.CharacterParser;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.pinyinUtils.PinyinCityComparator;

/**
 *@功能：租房列表适配器<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-30<br>
 */
public class SeriTenementAdapter extends BaseAdapter  implements SectionIndexer {
    ServiceTenementFrag frag;

    public List<TenementHouseBean> getData() {
        return data;
    }

    public void setData(List<TenementHouseBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<TenementHouseBean> data=new ArrayList<TenementHouseBean>();
    public SeriTenementAdapter( ServiceTenementFrag frag){
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_service_tenement, null);
            holder = new ViewHolder();
            holder.tv_header_title = (TextView) convertView.findViewById(R.id.tv_header_title);
            holder.tv_vallage_name=(TextView) convertView.findViewById(R.id.tv_vallage_name);
            holder.tv_tenement_type=(TextView) convertView.findViewById(R.id.tv_tenement_type);
            holder.tv_tenment_info=(TextView) convertView.findViewById(R.id.tv_vallage_info);
            holder.tv_tenement_price=(TextView) convertView.findViewById(R.id.tv_tenement_price);
            holder.tv_tenement_apart=(TextView) convertView.findViewById(R.id.tv_vallage_apartment);
            holder.img_house_pic=(ImageView) convertView.findViewById(R.id.img_house_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_vallage_name.setText(data.get(position).getVillage());
        holder.tv_tenement_price.setText(data.get(position).getPrice()+"元/月");
        holder.tv_tenement_type.setText(data.get(position).getTen());
        holder.tv_tenment_info.setText(data.get(position).getContent());
        holder.tv_tenement_apart.setText(data.get(position).getSize());
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP+data.get(position).getPic().get(0),holder.img_house_pic);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.tv_header_title.setVisibility(View.VISIBLE);
            holder.tv_header_title.setText(data.get(position).getSortLetters());
        } else {
            holder.tv_header_title.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
                mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_TENEMENT_DETAIL);
                mIntent.putExtra(ServiceSecondAct.INTENTTAG, data.get(position).getId());
                frag.startActivity(mIntent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView tv_vallage_name,tv_tenement_type,tv_tenment_info,tv_tenement_price,tv_header_title,tv_tenement_apart;
        ImageView img_house_pic;
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
            if (firstChar == sectionIndex){
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
