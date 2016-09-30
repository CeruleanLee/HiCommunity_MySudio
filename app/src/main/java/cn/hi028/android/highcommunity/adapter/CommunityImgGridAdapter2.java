/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MainActivity;
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.activity.fragment.CommunityFrag;
import cn.hi028.android.highcommunity.bean.PicBean;
import cn.hi028.android.highcommunity.bean.UrlsBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：社区图片Grid适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/30<br>
 */
public class CommunityImgGridAdapter2 extends BaseFragmentAdapter {

    Context mContent;
    List<PicBean> mList = new ArrayList<PicBean>();
    public Bitmap bitmaps[];
	private FinalBitmap finalImageLoader;
	private int wh;
    
    
    
    public CommunityImgGridAdapter2(Context mContent) {
        this.mContent = mContent;
   
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PicBean getItem(int position) {
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
            mViewHolder.mImage = (ImageView) view
                    .findViewById(R.id.iv_communityImg_Item_image);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }
        final String bigurl = mList.get(i).getBig();
        String smallUrl = mList.get(i).getSmall();
        final int position = i;
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + smallUrl, mViewHolder.mImage, R.mipmap.default_no_pic, null);
        mViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlsBean mUrls = new UrlsBean();
                for (int i = 0; i < mList.size(); i++) {
                    mUrls.getmUrlList().add(Constacts.IMAGEHTTP + mList.get(i).getBig());
                }
                CommunityFrag.isNeedRefresh = false;
                Intent mBigPhoto = new Intent(mContent, PhotoScanActivity.class);
                mBigPhoto.putExtra("data", mUrls);
                mBigPhoto.putExtra("ID", position);
                mContent.startActivity(mBigPhoto);
                ((Activity) mContent).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
            }
        });

        return view;
    }

    private class ViewHolder {
        ImageView mImage;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<PicBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
