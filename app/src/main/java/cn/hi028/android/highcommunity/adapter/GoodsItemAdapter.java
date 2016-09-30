package cn.hi028.android.highcommunity.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.ShopDetailActivity;
import cn.hi028.android.highcommunity.bean.Shop;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class GoodsItemAdapter extends MyBaseAdapter<Shop> {
	private int width;

	public GoodsItemAdapter(Context context, List<Shop> data) {
		super(context, data);
		// this.width = (int) ((Tool1.K(context) - 2 *
		// Tool1.getDensity(context)) / 2);
		closeAnimation();
	}

	@Override
	public int getItemResource(int pos) {
		return R.layout.item_lv;
	}

	@Override
	public View getItemView(final int position, View view,
			MyBaseAdapter<Shop>.ViewHolder holder, ViewGroup parent) {
		try {
			final ImageView goodsImg = holder.getView(R.id.item_iv);
			RatingBar ratBar = holder.getView(R.id.ratingbar_Small);
			TextView name = holder.getView(R.id.item_tv_name);
			TextView address = holder.getView(R.id.item_tv_addr);
			TextView price = holder.getView(R.id.item_per_price);
			TextView minTitle = holder.getView(R.id.item_min_title);
			name.setText(data.get(position).shop_name);
			int distance = (int) data.get(position).distance;
			address.setText(distance + "米");
			ratBar.setRating((float) (data.get(position).deals.get(0).score));
			int realPrice = (int) ((data.get(position).deals.get(0).promotion_price) / 100);
			price.setText(realPrice + "元/人");
			minTitle.setText(data.get(position).deals.get(0).min_title);

			Picasso.with(context)
					.load(data.get(position).deals.get(0).tiny_image)
					.into(goodsImg, new Callback() {

						@Override
						public void onSuccess() {
							goodsImg.setScaleType(ScaleType.CENTER_CROP);
						}

						@Override
						public void onError() {
							goodsImg.setScaleType(ScaleType.FIT_XY);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ShopDetailActivity.class);
				intent.putExtra("url", data.get(position).shop_url);
				context.startActivity(intent);  
			}
		});
		return view;
	}

}