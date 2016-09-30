/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsFrag;
import cn.hi028.android.highcommunity.bean.ChipsBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 *@功能：惠生活界面<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-15<br>
 */
public class HuiChipsAdapter extends BaseAdapter {
    HuiChipsFrag frag;
    private BitmapUtils bitmapUtils;
    public List<ChipsBean> getData() {
        return data;
    }

    public void setData(List<ChipsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<ChipsBean> data=new ArrayList<ChipsBean>();
    public HuiChipsAdapter(HuiChipsFrag frag,Context context){
    	bitmapUtils = MBitmapHolder.getBitmapUtils(context);
        this.frag=frag;
    }
    @Override
    public int getCount() {
        return  ListUtils.getSize(data);
    }

    @Override
    public Object getItem(int position) {
        return  null;//data.get(position);
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
                    R.layout.adapter_huilife_chips, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_person = (TextView) convertView
                    .findViewById(R.id.tv_person);
            viewHolder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            viewHolder.img_pic = (ImageView) convertView
                    .findViewById(R.id.img_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_price.setText(data.get(position).getCurrent_price()+"元");
        viewHolder.tv_person.setText(data.get(position).getPeople_num()+"人");
        viewHolder.tv_name.setText(data.get(position).getR_name());
//        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP+data.get(position).getCover_pic(),viewHolder.img_pic,R.mipmap.default_no_pic,null);
        bitmapUtils.display(viewHolder.img_pic, Constacts.IMAGEHTTP+data.get(position).getCover_pic());
        return convertView;
    }
    class ViewHolder{
        TextView tv_price,tv_person,tv_name;
        ImageView img_pic;
    }
}
