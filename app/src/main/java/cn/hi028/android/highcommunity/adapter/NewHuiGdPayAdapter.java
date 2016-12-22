/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

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
import cn.hi028.android.highcommunity.params.NewHuiSuppGdParams;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：惠生活支付商品<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-28<br>
 */
public class NewHuiGdPayAdapter extends BaseAdapter {
	BaseFragment frag;

	public List<NewHuiSuppGdParams> getData() {
		return data;
	}

	public void setData(List<NewHuiSuppGdParams> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	List<NewHuiSuppGdParams> data = new ArrayList<NewHuiSuppGdParams>();

	public NewHuiGdPayAdapter(BaseFragment frag) {
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
					R.layout.item_newsupp_showpay, null);
			viewHolder = new ViewHolder();

			viewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
			viewHolder.tv_goods_total = (TextView) convertView.findViewById(R.id.tv_goods_total);
			viewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);
			viewHolder.tv_standard = (TextView) convertView.findViewById(R.id.tv_goods_standard);
			viewHolder.tv_goods_num = (TextView) convertView.findViewById(R.id.tv_goods_num);
			viewHolder.img_goods_pic = (ImageView) convertView.findViewById(R.id.img_goods_pic);





		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_goods_total.setVisibility(View.GONE);
		viewHolder.tv_goods_name.setText(data.get(position).getName());
		viewHolder.tv_goods_price.setText(data.get(position).getGoods_price()
				+ "");
		viewHolder.tv_goods_num.setText("x" + data.get(position).getGoods_number()
				+ "");
		if (data.get(position) != null && data.get(position).getPic() != null
				&& data.get(position).getPic() != null)
			ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP
					+ data.get(position).getPic(),
					viewHolder.img_goods_pic, R.mipmap.default_no_pic, null);
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	class ViewHolder {
		ImageView img_goods_pic;
		TextView tv_goods_name,tv_goods_total;
		TextView tv_goods_price;
		TextView tv_goods_num,tv_standard;
	}
}
