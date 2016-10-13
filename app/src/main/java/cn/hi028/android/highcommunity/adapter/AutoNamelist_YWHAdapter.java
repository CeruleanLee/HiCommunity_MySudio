/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NameListBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：自治大厅业委会adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoNamelist_YWHAdapter extends BaseFragmentAdapter {

    List<Auto_NameListBean.NameListDataEntity.YwhEntity> mList = new ArrayList<Auto_NameListBean.NameListDataEntity.YwhEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AutoNamelist_YWHAdapter(List<Auto_NameListBean.NameListDataEntity.YwhEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_NameListBean.NameListDataEntity.YwhEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_NameListBean.NameListDataEntity.YwhEntity getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_autonamelist_ywh, null);
            mViewHolder.mYWHAvatar= (com.don.view.CircleImageView) convertView.findViewById(R.id.item_autonamelistYWH_avatar);
            mViewHolder.mYWHName = (TextView) convertView.findViewById(R.id.item_autonamelistYWH_name);
            mViewHolder.mYWHJob = (TextView) convertView.findViewById(R.id.item_autonamelistYWH_job);
            mViewHolder.mYWHTel = (TextView) convertView.findViewById(R.id.item_autonamelistYWH_tel);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_NameListBean.NameListDataEntity.YwhEntity mBean = mList.get(position);
        if (mBean.getHead_pic() == null || mBean.getHead_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, mViewHolder.mYWHAvatar);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getHead_pic(), mViewHolder.mYWHAvatar);
        }
        mViewHolder.mYWHName.setText(mBean.getName());
        mViewHolder.mYWHName.setText(mBean.getName());
        mViewHolder.mYWHJob.setText("职位："+mBean.getPosition());
        mViewHolder.mYWHTel.setText("电话："+mBean.getTel());
        return convertView;
    }


    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_NameListBean.NameListDataEntity.YwhEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }


    static class ViewHolder {
        com.don.view.CircleImageView mYWHAvatar;
        TextView mYWHName;
        TextView mYWHJob;
        TextView mYWHTel;

    }
}
