package cn.hi028.android.highcommunity.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.NearByShopData;
import cn.hi028.android.highcommunity.bean.ShopNearByListInfo;
import cn.hi028.android.highcommunity.utils.Constacts;

public class ShopNearByAdapter extends MyBaseAdapter<NearByShopData> {

	public ShopNearByAdapter(Context context, List<NearByShopData> data) {
		super(context, data);
	}

	@Override
	public int getItemResource(int pos) {
		return R.layout.item_nearby_shops_list;
	}

	@Override
	public View getItemView(int position, View convertView,
			MyBaseAdapter<NearByShopData>.ViewHolder holder, ViewGroup parent) {
		NearByShopData info = data.get(position);
		TextView address = holder.getView(R.id.item_nearby_shops_address);
		TextView counts = holder.getView(R.id.item_nearby_shops_count);
		TextView ShopName = holder
				.getView(R.id.item_nearby_shops_merchant_name);
		TextView telephone = holder.getView(R.id.item_nearby_shops_telephone);
		ImageView head = holder.getView(R.id.item_nearby_shops_image);
		info = data.get(position);
		Picasso.with(context).load(Constacts.IMAGEHTTP + info.getHead_pic())
				.into(head);
		address.setText(info.getAddress());
		counts.setText(info.getSales());
		ShopName.setText(info.getShop_name());
		telephone.setText(info.getTel());
		return convertView;
	}
}
