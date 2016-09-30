/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiLifeSuppBuyFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiSuppPayFrag;
import cn.hi028.android.highcommunity.params.HuiSuppGdParams;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：惠生活支付商品<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-28<br>
 */
public class HuiGdPayAdapter extends BaseAdapter {
	BaseFragment frag;

	public List<HuiSuppGdParams> getData() {
		return data;
	}

	public void setData(List<HuiSuppGdParams> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	List<HuiSuppGdParams> data = new ArrayList<HuiSuppGdParams>();

	public HuiGdPayAdapter(BaseFragment frag) {
		this.frag = frag;
	}

	@Override
	public int getCount() {
		return ListUtils.getSize(data);
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(frag.getActivity()).inflate(
					R.layout.adapter_hui_gd_payitem, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_goods_name = (TextView) convertView
					.findViewById(R.id.tv_goods_name);
			viewHolder.tv_goods_price = (TextView) convertView
					.findViewById(R.id.tv_goods_price);
			viewHolder.tv_goods_num = (TextView) convertView
					.findViewById(R.id.tv_goods_num);
			viewHolder.img_goods_pic = (ImageView) convertView
					.findViewById(R.id.img_goods_pic);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_goods_name.setText(data.get(position).getGoods_name());
		viewHolder.tv_goods_price.setText(data.get(position).getGoods_price()
				+ "");
		viewHolder.tv_goods_num.setText("x" + data.get(position).getNumber()
				+ "");
		if (data.get(position) != null && data.get(position).getPic() != null
				&& data.get(position).getPic().get(0) != null)
			ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP
					+ data.get(position).getPic().get(0),
					viewHolder.img_goods_pic, R.mipmap.default_no_pic, null);
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	class ViewHolder {
		ImageView img_goods_pic;
		TextView tv_goods_name;
		TextView tv_goods_price;
		TextView tv_goods_num;
	}
}
