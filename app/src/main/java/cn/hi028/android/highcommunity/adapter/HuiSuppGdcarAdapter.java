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


import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiSuppGdCarFrag;
import cn.hi028.android.highcommunity.bean.GdCarBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：购物车适配器<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-26<br>
 */
public class HuiSuppGdcarAdapter extends BaseAdapter {
	HuiSuppGdCarFrag frag;

	public HuiSuppGdcarAdapter(HuiSuppGdCarFrag frag) {
		this.frag = frag;

	}

	List<GdCarBean> data = new ArrayList<GdCarBean>();

	public List<GdCarBean> getData() {
		return data;
	}

	public void setData(List<GdCarBean> data) {
		this.data = data;
		notifyDataSetChanged();
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
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_huisupp_gdcar, null);
			viewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
			viewHolder.tv_goods_price = (TextView) convertView
					.findViewById(R.id.tv_goods_price);
			viewHolder.tv_goods_num = (TextView) convertView
					.findViewById(R.id.tv_goods_num);
			viewHolder.tv_goods_add = (TextView) convertView
					.findViewById(R.id.tv_goods_add);
			viewHolder.tv_goods_reduce = (TextView) convertView
					.findViewById(R.id.tv_goods_reduce);
			viewHolder.tv_total_pay = (TextView) convertView
					.findViewById(R.id.tv_total_pay);
			viewHolder.img_goods_pic = (ImageView) convertView
					.findViewById(R.id.img_goods_pic);
			viewHolder.img_goods_ch = (ImageView) convertView
					.findViewById(R.id.img_goods_ch);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.get(position).getPic(), viewHolder.img_goods_pic);
		viewHolder.tv_goods_name.setText(data.get(position).getGoods_name());
		viewHolder.tv_goods_price.setText(data.get(position).getPrice() + "");
		viewHolder.tv_total_pay.setText("小计：￥"+CommonUtils.f2Bi(data.get(position).getTotal_pri()));
		viewHolder.tv_goods_num.setText(data.get(position).getNum() + "");
		if (data.get(position).isCheck()) {
			viewHolder.img_goods_ch.setSelected(true);
		} else {
			viewHolder.img_goods_ch.setSelected(false);
		}
		viewHolder.img_goods_ch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (data.get(position).isCheck()) {
					data.get(position).setCheck(false);
				} else {
					data.get(position).setCheck(true);
				}
				notifyDataSetChanged();
			}
		});
		viewHolder.tv_goods_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (data.get(position).getNum() < data.get(position).getStorage()) {
					data.get(position).setNum(data.get(position).getNum() + 1);
					notifyDataSetChanged();
				} else {
					HighCommunityUtils.GetInstantiation().ShowToast("数目不能超过库存", 0);
				}
			}
		});
		viewHolder.tv_goods_reduce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (data.get(position).getNum() > 0) {
					data.get(position).setNum(data.get(position).getNum() - 1);
					notifyDataSetChanged();
				}
			}
		});
		return convertView;
	}

	public float total_pri = 0.0f;
	public int total_num = 0;
	public int selectNum=0;
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		boolean isCheck=true;
		total_pri = 0.0f;
		total_num = 0;
		selectNum=0;
		for (int i = 0; i < ListUtils.getSize(getData()); i++) {
			if (getData().get(i).isCheck()) {
				selectNum=selectNum+1;
				total_pri = total_pri + getData().get(i).getTotal_pri();
				total_num = total_num + getData().get(i).getNum();
			}else{
				isCheck=false;
			}

		}
		frag.tv_price.setText("￥" + CommonUtils.f2Bi(total_pri));
		if (frag.btn_pay.isSelected()){
			frag.ll_price.setVisibility(View.INVISIBLE);
			frag.btn_pay.setText("删除（" + selectNum + "）");
		}else{
			frag.ll_price.setVisibility(View.VISIBLE);
			frag.btn_pay.setText("结算（" + total_num + "）");
		}
		frag.img_goods_ch.setSelected(isCheck);

	}


	class ViewHolder {
		TextView tv_goods_name, tv_goods_price, tv_goods_num, tv_goods_add, tv_goods_reduce, tv_total_pay;
		ImageView img_goods_pic, img_goods_ch;
	}
}
