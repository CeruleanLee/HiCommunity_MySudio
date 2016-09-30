package cn.hi028.android.highcommunity.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.AddressModel;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;

public class ShowPayAdapter extends MyBaseAdapter<Goods_info> {

	public ShowPayAdapter(Context context, List<Goods_info> data) {
		super(context, data);
	}
	
	@Override
	public int getItemResource(int pos) {
		return R.layout.item_show_pay_goods_list;
	}

	@Override
	public View getItemView(int position, View convertView,
			MyBaseAdapter<Goods_info>.ViewHolder holder, ViewGroup parent) {
		 ImageView head = holder.getView(R.id.item_show_pay_goods_list_head_iv);
		TextView name = holder
				.getView(R.id.item_show_pay_goods_list_good_name_tv);
		TextView price = holder
				.getView(R.id.item_show_pay_goods_list_good_price_tv);
		TextView allprice = holder
				.getView(R.id.item_show_pay_goods_list_good_all_price);
		TextView amount = holder
				.getView(R.id.item_show_pay_goods_list_good_amount);
		Goods_info info = data.get(position);
		name.setText(info.getGoods_name());
		price.setText("¥"+info.getPrice()+"元");
		amount.setText("x"+info.getCounts());
		DecimalFormat df = new DecimalFormat("0.00");
		if (!TextUtils.isEmpty(info.getAll_price())) {
			allprice.setText("小计:"+info.getAll_price());
		} else {
			String rs = df.format(Double.parseDouble(info.getPrice())*info.getCounts());
			allprice.setText("小计:"+rs);
		}
		String imageUrl = Constacts.IMAGEHTTP + info.getThumb_pic();
		Log.e("showPayAdapter", "imageUrl-" +  imageUrl);
		int width = CommonUtils.dip2px(context, 75);
		Picasso.with(context)
				.load(imageUrl)
				.resize(width, width)
				.centerCrop()
		.into(head);
		return convertView;

	}
}
