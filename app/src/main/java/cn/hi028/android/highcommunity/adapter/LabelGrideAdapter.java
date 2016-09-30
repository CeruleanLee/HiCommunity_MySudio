/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.LabelBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：发帖标签适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
public class LabelGrideAdapter extends BaseFragmentAdapter {

    public Context mContext;
    private List<LabelBean> mList = new ArrayList<LabelBean>();
    boolean CanDelete = false;

    public LabelGrideAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = Constacts.getLocalLabel();
    }
    
    @Override
    public int getCount() {
        return mList.size();
    }
    
    @Override
    public LabelBean getItem(int position) {
        return mList.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    
    public void CanDelete(boolean CanDelete) {
        this.CanDelete = CanDelete;
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_label_item, null);
            mViewHolder.mImage = (CircleImageView) convertView
                    .findViewById(R.id.civ_labelitem_img);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_labelitem_name);
            mViewHolder.mClicked = (ImageView) convertView
                    .findViewById(R.id.civ_labelitem_Clicked);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final LabelBean mbean = mList.get(i);
//        if (mList.size() == i && !CanDelete) {
//            convertView.setVisibility(View.GONE);
//        } else {
//            convertView.setVisibility(View.VISIBLE);
//        }
        if (TextUtils.isEmpty(mbean.getPic())) {
            ImageLoaderUtil.disPlay("drawable://" + R.mipmap.img_label_new, mViewHolder.mImage);
        } else {
            ImageLoaderUtil.disPlay(mbean.getPic(), mViewHolder.mImage);
        }
        mViewHolder.mName.setText(mbean.getLabel_name());
        if (mbean.isClicked()) {
            mViewHolder.mClicked.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mClicked.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView mImage;
        TextView mName;
        ImageView mClicked;
    }

    public void remove(String id) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId().equals(id)) {
                mList.remove(i);
            }
        }
        for (int i = 0; i < Constacts.mCustomLabel.size(); i++) {
            if (Constacts.mCustomLabel.get(i).getId().equals(id)) {
                Constacts.mCustomLabel.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void AddNewData(Object mObject) {
        List<LabelBean> temp = new ArrayList<LabelBean>();
        if (mObject instanceof List<?>) {
            temp = (List<LabelBean>) mObject;
            for (int i = 0; i < temp.size(); i++) {
                mList.add(0, temp.get(i));
            }
        } else if (mObject instanceof LabelBean) {
            mList.add(0, (LabelBean) mObject);
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
