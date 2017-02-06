/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import net.duohuo.dhroid.adapter.RecyclingPagerAdapter;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * ImagePagerAdapter
 */
public class SimplePageAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<String> imageIdList;
    private boolean isInfiniteLoop;

    public SimplePageAdapter(Context context) {
        this.context = context;
        imageIdList = new ArrayList<String>();
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
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + imageIdList.get(position), holder.imageView);
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
    public SimplePageAdapter setInfiniteLoop(boolean isInfiniteLoop) {
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
