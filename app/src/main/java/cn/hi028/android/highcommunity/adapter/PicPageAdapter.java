/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import net.duohuo.dhroid.adapter.RecyclingPagerAdapter;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.activity.fragment.CommunityFrag;
import cn.hi028.android.highcommunity.bean.UrlsBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * ImagePagerAdapter适配器
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class PicPageAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<String> imageIdList = new ArrayList<String>();
    private boolean isInfiniteLoop;

    public PicPageAdapter(Context context) {
        this.context = context;
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        return imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    public int getPosition(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ScaleType.FIT_XY);
            holder.imageView.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + imageIdList.get(position),
                holder.imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlsBean mUrls = new UrlsBean();
                for (int i = 0; i < imageIdList.size(); i++) {
                    mUrls.getmUrlList().add(Constacts.IMAGEHTTP + imageIdList.get(i));
                }
                CommunityFrag.isNeedRefresh = false;
                Intent mBigPhoto = new Intent(context, PhotoScanActivity.class);
                mBigPhoto.putExtra("data", mUrls);
                mBigPhoto.putExtra("ID", position);
                context.startActivity(mBigPhoto);
                ((Activity) context).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
            }
        });
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public PicPageAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    public List<String> getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(List<String> imageIdList) {
        if (null != imageIdList) {
            this.imageIdList = imageIdList;
        }
        notifyDataSetChanged();
    }
}
