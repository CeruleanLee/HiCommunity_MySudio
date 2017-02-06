/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.PicBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：惠生活物业直供商品详情图片<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-15<br>
 */
public class HuiSuppPicAdapter extends BaseAdapter {
    Context context;
    List<PicBean> data = new ArrayList<PicBean>();

    public List<PicBean> getData() {
        return data;
    }

    public void setData(List<PicBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public HuiSuppPicAdapter(Context mContext) {
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView image = new SquareImageView(context);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.get(position).getSmall(), image, R.mipmap.default_no_pic, null);
        return image;
    }
}
