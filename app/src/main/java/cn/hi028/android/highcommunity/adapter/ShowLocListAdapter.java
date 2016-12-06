/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.Poi;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;

/**
 * @功能：附近定位Listadapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class ShowLocListAdapter extends BaseFragmentAdapter {

    List<Poi> mList = new ArrayList<Poi>();
    private Context context;
    private LayoutInflater layoutInflater;
    public ShowLocListAdapter(List<Poi> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Poi>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Poi getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_loclist, null);
            mViewHolder.mAddress = (TextView) convertView.findViewById(R.id.item_loclist_address);
            mViewHolder.mAddressDetail = (TextView) convertView.findViewById(R.id.item_loclist_addressDetail);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Poi mBean = mList.get(position);

        mViewHolder.mAddress.setText(mBean.getName());
        mViewHolder.mAddressDetail.setText(mBean.getName());
        return convertView;
    }
    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Poi>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }
    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }
    static class ViewHolder {
        TextView mAddress;
        TextView mAddressDetail;
    }
}
