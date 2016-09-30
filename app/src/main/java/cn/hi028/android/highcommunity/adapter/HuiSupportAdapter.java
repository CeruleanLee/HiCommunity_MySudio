/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.don.tools.BpiUniveralImage;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.res.IntegerRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.activity.fragment.CommunityFrag;
import cn.hi028.android.highcommunity.bean.HuiSupportBean;
import cn.hi028.android.highcommunity.bean.UrlsBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：直供幻灯片<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-15<br>
 */
public class HuiSupportAdapter extends PagerAdapter {
    Context context;

    Map<Integer, ImageView> viewlist = new HashMap<Integer, ImageView>();

    public List<HuiSupportBean> getData() {
        return data;
    }

    public void setData(List<HuiSupportBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<HuiSupportBean> data = new ArrayList<HuiSupportBean>();

    public HuiSupportAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        //设置成最大，使用户看不到边界
        return ListUtils.getSize(data);//Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //对ViewPager页号求模取出View列表中要显示的项
//        position %= data.size();
//        if (position < 0) {
//            position = data.size() + position;
//        }
//        if (viewlist.size() == 0) {
//            for (int i = 0; i < data.size(); i++) {
//                ImageView imageView = new ImageView(context);
//                viewlist.put(i, imageView);
//            }
//        }
        ImageView view = new ImageView(context);
        viewlist.put(position, view);
//        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//        ViewParent vp = view.getParent();
//        if (vp != null) {
//            ViewGroup parent = (ViewGroup) vp;
//            parent.removeView(view);
//        }
        container.addView(view);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.get(position).getPic().
                get(data.get(position).getCurrentPicPosition()).getBig(), view, R.mipmap.default_no_pic, null);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlsBean mUrls = new UrlsBean();
                for (int i = 0; i < data.get(position).getPic().size(); i++) {
                    mUrls.getmUrlList().add(Constacts.IMAGEHTTP + data.get(position).getPic().get(i).getBig());
                }
                CommunityFrag.isNeedRefresh = false;
                Intent mBigPhoto = new Intent(context, PhotoScanActivity.class);
                mBigPhoto.putExtra("data", mUrls);
                mBigPhoto.putExtra("ID", data.get(position).getCurrentPicPosition());
                context.startActivity(mBigPhoto);
                ((Activity) context).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
            }
        });
        return view;
    }

    public void updateImage(int position) {
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.get(position).getPic().
                get(data.get(position).getCurrentPicPosition()).getBig(), viewlist.get(position), R.mipmap.default_no_pic, null);
    }

//    public int getCurrentPo(int currentPositon) {
//        int curr = currentPositon % ListUtils.getSize(getData());
//        if (curr < 0) {
//            curr = ListUtils.getSize(getData()) + curr;
//        }
//        return curr;
//    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

