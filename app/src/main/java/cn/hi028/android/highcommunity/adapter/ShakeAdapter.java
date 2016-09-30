/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.ShakeUser;
import cn.hi028.android.highcommunity.bean.ShakeUser;


/**
 * @功能：城市数据列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/26<br>
 */
public class ShakeAdapter extends BaseFragmentAdapter {

    Context mContext;

    public List<ShakeUser> getMlist() {
        return mlist;
    }

    public void setMlist(List<ShakeUser> mlist) {
        if (mlist==null)
            return;
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    List<ShakeUser> mlist = new ArrayList<ShakeUser>();
    ShakeUser bean;
    String buttonText = null;

    public ShakeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    ;

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public ShakeUser getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewgroup) {
        ViewHolder viewholder;
        ShakeUser bean = mlist.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.adapter_shake_top, null);
            viewholder.mSHakeName = (TextView) convertView
                    .findViewById(R.id.tv_shake_name);
            viewholder.mSHakeConent = (TextView) convertView
                    .findViewById(R.id.tv_shake_content);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.mSHakeName.setText(bean.getNick());
        viewholder.mSHakeConent.setText(bean.getTip());
        return convertView;
    }


    private class ViewHolder {
        TextView mSHakeName, mSHakeConent;
    }
}
