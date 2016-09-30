/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;

/**
 * @功能：添加图片<br>
 * @作者：zha<br>
 * @版本：1.0<br>
 * @时间：2016/1/4<br>
 */
public class AddPicAdapter extends BaseFragmentAdapter {

    public Context mContext;
    private List<String> mList = new ArrayList<String>();

    public AddPicAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList.add("drawable://" + R.mipmap.img_upload_addpic);
    }

    @Override
    public int getCount() {
        if (mList.size() > 3) {
            return 3;
        } else {
            return mList.size();
        }
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_post_message, null);
            mViewHolder.mImage = (ImageView) convertView
                    .findViewById(R.id.iv_PostMessage_Image);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoaderUtil.disPlay(mList.get(i), mViewHolder.mImage);
        return convertView;
    }

    private class ViewHolder {
        ImageView mImage;
    }

    @Override
    public void AddNewData(Object mObject) {
        Uri temp = null;
        if (mObject instanceof Uri) {
            temp = (Uri) mObject;
            System.out.println("uri path:" + temp.getPath());
            mList.add(0, "file://" + temp.getPath());
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
