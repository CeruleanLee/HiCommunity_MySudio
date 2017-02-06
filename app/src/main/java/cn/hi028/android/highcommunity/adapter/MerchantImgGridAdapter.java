/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.don.tools.BpiUniveralImage;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：商家推荐Grid适配器<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/29<br>
 */
public class MerchantImgGridAdapter extends BaseFragmentAdapter {

static final String Tag="MerchantImgGridAdapter:";
    Context mContent;
    List<NewSupplyBean.NewSupplyDataEntity.MerchantEntity> mList;
    public MerchantImgGridAdapter(List<NewSupplyBean.NewSupplyDataEntity.MerchantEntity> mList,Context mContent) {
        this.mContent = mContent;
        this.mList = mList;
        Log.d(Tag,"构造");

        Log.d(Tag,"mList---"+mList.size());

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NewSupplyBean.NewSupplyDataEntity.MerchantEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder mViewHolder;
        if (view == null) {
            mViewHolder = new ViewHolder();
            view = LayoutInflater.from(mContent).inflate(R.layout.adapter_communityimg, null);
            mViewHolder.mImage = (ImageView) view.findViewById(R.id.iv_communityImg_Item_image);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }
        NewSupplyBean.NewSupplyDataEntity.MerchantEntity mBean = mList.get(i);
        Log.d(Tag,"smallUrl---"+mBean.getLogo());

        if (mBean.getLogo() == null || mBean.getLogo().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.mImage);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getLogo(), mViewHolder.mImage);
        }
        Log.d(Tag,"disPlay   ok");

        return view;
    }

    private class ViewHolder {
        ImageView mImage;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<NewSupplyBean.NewSupplyDataEntity.MerchantEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
