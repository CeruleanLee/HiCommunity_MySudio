/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.don.tools.GeneratedClassUtils;
import com.lidroid.xutils.BitmapUtils;

import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.adapter.RecyclingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.activity.ServiceAct;
import cn.hi028.android.highcommunity.activity.WebViewActivity;
import cn.hi028.android.highcommunity.bean.BannerBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * 图片ViewPager适配器
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

	private Context context;
	private List<BannerBean> imageIdList = new ArrayList<BannerBean>();
	private boolean isInfiniteLoop;
    BitmapUtils  mBitmapUtils; 
	public ImagePagerAdapter(Context context) {
		this.context = context;
		imageIdList = new ArrayList<BannerBean>();
		isInfiniteLoop = false;
		  mBitmapUtils= MBitmapHolder.getBitmapUtils(context);
	}

	@Override
	public int getCount() {
		// Infinite loop
		// return ListUtils.getSize(getImageIdList());
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
		final BannerBean mBean = imageIdList.get(position);
//		ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getPath(),holder.imageView);
		
		 mBitmapUtils.display(holder.imageView, Constacts.IMAGEHTTP + mBean.getPath());
//		 mBitmapUtils.display(holder.imageView, Constacts.IMAGEHTTP + "upload/ywh/owner_pic/20161027114748898872.jpg");
		//upload/ywh/owner_pic/20161027114748898872.jpg

		// ImageLoaderUtil.disPlay(UrlConfig.pic_URL +getImageIdList().get(position).getOi_pic_url(), holder.imageView);
		// view.requestFocus();
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBean.getType().equals("3")) {
					BrowseActivity.toBrowseActivity(context, "外卖",
							mBean.getPath());
				} else if ("1".equals(mBean.getType())) {
					Intent mIntent = new Intent(context, GeneratedClassUtils
							.get(ServiceAct.class));
					mIntent.putExtra(ServiceAct.ACTIVITYTAG,
							Constacts.SERVICE_PAYMENT);
					context.startActivity(mIntent);
				} else if ("2".equals(mBean.getType())) {
					if (HighCommunityUtils.isLogin(context)) {
						Intent mIntent = new Intent(context,
								GeneratedClassUtils.get(ServiceAct.class));
						mIntent.putExtra(ServiceAct.ACTIVITYTAG,
								Constacts.SERVICE_SHAKE);
						context.startActivity(mIntent);
					}
				} else {
					if (!TextUtils.isEmpty(mBean.getUrl())) {
						Intent intent = new Intent(context,
								WebViewActivity.class);
						intent.putExtra("url", mBean.getUrl());
						context.startActivity(intent);
					}
				}

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
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	public List<BannerBean> getImageIdList() {
		return imageIdList;
	}

	public void setImageIdList(List<BannerBean> imageIdList) {
		if (null != imageIdList) {
			this.imageIdList = imageIdList;
		}
		notifyDataSetChanged();
	}
}
