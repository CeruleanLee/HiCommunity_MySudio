package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.lisenter.ShopAddSubListener;
import cn.hi028.android.highcommunity.utils.Constacts;
/**
 * 联盟商家item点击frag商品 右边listvew 适配器
 * @author Administrator
 */
public class MerchantGoodRightAdapter extends MyBaseAdapter<Goods_info>
		implements OnClickListener {

	ShopAddSubListener shopListener;
	 AdapterView<?> adapterView;
	 
	public AdapterView<?> getAdapterView() {
		return adapterView;
	}

	public void setAdapterView(AdapterView<?> adapterView) {
		this.adapterView = adapterView;
	}

	public MerchantGoodRightAdapter(Context context, BaseFragment frag,
			List<Goods_info> data) {
		super(context, data);
		shopListener = (ShopAddSubListener) frag;
	}
	
	public MerchantGoodRightAdapter(Context context, BaseFragment frag,
			List<Goods_info> data,AdapterView<?> adapterView) {
		super(context, data);
		shopListener = (ShopAddSubListener) frag;
		this.adapterView = adapterView;
	}
	
	
	@Override
	public int getItemResource(int pos) {
		return R.layout.item_merchant_goods_right_list;
	}

	@Override
	public View getItemView(int position, View convertView,ViewHolder holder, ViewGroup parent) {
		final Goods_info info = data.get(position);
		LogUtil.d("----------info:"+info.toString());
		TextView name = holder.getView(R.id.item_merchant_goods_list_name_tv);
		TextView sales = holder.getView(R.id.item_merchant_goods_list_sale_tv);
		TextView price = holder.getView(R.id.item_merchant_goods_list_price);
		final ImageView image = holder
				.getView(R.id.item_merchant_goods_list_rigth_iv);
		TextView sub = holder.getView(R.id.item_merchant_goods_right_sub_iv);
		TextView add = holder.getView(R.id.item_merchant_goods_right_add_iv);
		TextView count = holder
				.getView(R.id.item_merchant_goods_list_right_counts);
		name.setText(info.getGoods_name());
		sales.setText("月售:" + info.getSales());
		price.setText("¥" + info.getPrice());
		int a = info.getCounts();
		count.setText(a + "");
		LogUtil.d("----------info.getThumb_pic()"+info.getThumb_pic());
		Picasso.with(context).load(Constacts.IMAGEHTTP + info.getThumb_pic())
				.into(image);
		image.setTag(info.getGoods_id());
		sub.setTag(info.getGoods_id());
		add.setTag(info.getGoods_id());
//		image.setOnClickListener(this);
		sub.setOnClickListener(this);
		add.setOnClickListener(this);
//		convertView.setTag(info.getGoods_id());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				shopListener.goDetail(Integer.parseInt((String) info.getGoods_id()));
				
				
			}
		});
		return convertView;
	}

	@Override
	public void onClick(View v) {
		int count = Integer.parseInt((String) v.getTag());
		switch (v.getId()) {
		case R.id.item_merchant_goods_right_sub_iv:
			shopListener.sub(count);
			break;
		case R.id.item_merchant_goods_right_add_iv:
			shopListener.add(count);
			break;

		}
	}
@Override
public void notifyDataSetChanged() {
	// TODO Auto-generated method stub
	notifyDataSetInvalidated();
	super.notifyDataSetChanged();
}
@Override
public void notifyDataSetInvalidated() {
	// TODO Auto-generated method stub
	super.notifyDataSetInvalidated();
}
}
