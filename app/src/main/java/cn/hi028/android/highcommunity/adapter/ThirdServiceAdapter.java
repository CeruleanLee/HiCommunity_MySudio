/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.ServiceBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * @功能：第三方服务adapter<br>
 * 服务页 生活服务中间的girdview适配
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
public class ThirdServiceAdapter extends BaseFragmentAdapter {

    public Context mContext;
    private List<ServiceBean> mList = new ArrayList<ServiceBean>();
    BitmapUtils  mBitmapUtils;
    
    public ThirdServiceAdapter(Context mContext) {
        this.mContext = mContext;
        mBitmapUtils= MBitmapHolder.getBitmapUtils(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ServiceBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_thirdservice_item, null);
            mViewHolder.mPic = (ImageView) convertView
                    .findViewById(R.id.iv_thirdservice_img);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_thirdservice_name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final ServiceBean mBean = mList.get(i);
        int pad = HighCommunityUtils.GetInstantiation().dip2px(8);
        mViewHolder.mPic.setPadding(pad, pad, pad, pad);
        BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mPic);
        mViewHolder.mName.setText(mBean.getName());
        return convertView;
    }

    public class ViewHolder {
        ImageView mPic;
        TextView mName;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<ServiceBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
