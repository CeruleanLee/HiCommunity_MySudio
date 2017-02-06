/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.CarftsBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：手艺人列表适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015-12-29<br>
 */
public class CarftsAdapter extends BaseFragmentAdapter {
	Context mContext;
	List<CarftsBean> mList = new ArrayList<CarftsBean>();

	public CarftsAdapter(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public CarftsBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_servicecarfts, null);
			mViewHolder.mAvatar = (CircleImageView) convertView
					.findViewById(R.id.civl_ServiceCarftsItem_avatar);
			mViewHolder.mTitle = (TextView) convertView
					.findViewById(R.id.tv_ServiceCarftsItem_status);
			mViewHolder.mPhone = (TextView) convertView
					.findViewById(R.id.tv_ServiceCarftsItem_Name);
			mViewHolder.mPrice = (TextView) convertView
					.findViewById(R.id.tv_ServiceCarftsItem_phone);	
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		CarftsBean mBean = mList.get(position);
		mViewHolder.mTitle.setText(mBean.getTitle());
		ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(),
				mViewHolder.mAvatar);
		mViewHolder.mPhone.setText(mBean.getName() + ":" + mBean.getTel());
		mViewHolder.mPrice.setText(mBean.getPrice() + "元/次");
		return convertView;
	}

	private class ViewHolder {
		CircleImageView mAvatar;
		TextView mTitle;
		TextView mPhone;
		TextView mPrice;
	}

	@Override
	public void AddNewData(Object mObject) {
		if (mObject instanceof List<?>) {
			mList = (List<CarftsBean>) mObject;
		}
		notifyDataSetChanged();
		super.AddNewData(mObject);
	}

	@Override
	public void RefreshData(Object mObject) {
		if (mObject instanceof List<?>) {
			mList.addAll((List<CarftsBean>) mObject);
		}
		notifyDataSetChanged();
		super.RefreshData(mObject);
	}

}
