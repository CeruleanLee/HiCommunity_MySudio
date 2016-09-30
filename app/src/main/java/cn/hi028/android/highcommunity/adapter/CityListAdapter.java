/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.CitysBean;


/**
 * @功能：城市数据列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/26<br>
 */
public class CityListAdapter extends BaseFragmentAdapter {

    Context mContext;
    List<CitysBean> mlist = new ArrayList<CitysBean>();
    CitysBean bean;
    String buttonText = null;

    public CityListAdapter(Context mContext, Object object) {
        this.mContext = mContext;
        if (object instanceof List<?>) {
            this.mlist = ((List<CitysBean>) object);
        } else if (object instanceof String) {
            buttonText = (String) object;
        }
    }

    ;

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public CitysBean getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewgroup) {
        ViewHolder viewholder;
        bean = mlist.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item, null);
            viewholder.mTeacherName = (TextView) convertView
                    .findViewById(R.id.simple_list_item);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.mTeacherName.setText(bean.getCity());
        return convertView;
    }


    private class ViewHolder {
        TextView mTeacherName;
    }
}
