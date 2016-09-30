package cn.hi028.android.highcommunity.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.PayPopListBean;
import cn.hi028.android.highcommunity.lisenter.PopListAddSubListener;
import cn.hi028.android.highcommunity.lisenter.ShopAddSubListener;

public class PayPopupListAdapter extends MyBaseAdapter<Goods_info> {
	ShopAddSubListener face;

	public PayPopupListAdapter(Context context, List<Goods_info> data) {
		super(context, data);

	}

	public void setFace(ShopAddSubListener face) {
		this.face = face;
	}

	@Override
	public int getItemResource(int pos) {
		return R.layout.item_pop_pay_list;
	}

	@Override
	public View getItemView(final int position, View convertView,
			MyBaseAdapter<Goods_info>.ViewHolder holder, ViewGroup parent) {
		Goods_info info = data.get(position);
		Log.d("renk", info.toString());
		TextView name = holder.getView(R.id.item_pop_shop_name);
		TextView price = holder.getView(R.id.item_pop_price_tv);
		TextView count = holder.getView(R.id.item_pop_count);
		ImageView sub = holder.getView(R.id.item_pop_sub_iv);
		ImageView add = holder.getView(R.id.item_pop_add_iv);
		name.setText(info.getGoods_name());
		price.setText(info.getPrice() + "");
		count.setText("" + info.getCounts());
		sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				face.sub(position);
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				face.add(position);
			}
		});
		return convertView;
	}

}
