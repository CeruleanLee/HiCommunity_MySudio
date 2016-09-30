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

import com.don.tools.BpiUniveralImage;

import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.adapter.RecyclingPagerAdapter;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * ImagePagerAdapter适配器
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class PicPagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<String> imageIdList = new ArrayList<String>();
    private boolean isInfiniteLoop;

    public PicPagerAdapter(Context context) {
        this.context = context;
        imageIdList = new ArrayList<String>();
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
//        return ListUtils.getSize(getImageIdList());
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
            holder.imageView.setScaleType(ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final String mBean = imageIdList.get(position);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + getImageIdList().get(position),
                holder.imageView);
//        view.requestFocus();
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
    public PicPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
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
