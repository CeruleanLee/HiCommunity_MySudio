package cn.hi028.android.highcommunity.adapter;

import java.util.List;

import com.hp.hpl.sparta.Document.Index;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.MerchantGoodTitleBean;

public class MerchantGoodLeftAdapter extends
		MyBaseAdapter<MerchantGoodTitleBean> {

	protected int lastposition = 0;
	int index= 0;
	
	public void setIndex(int index) {
		this.index = index;
	}

	public MerchantGoodLeftAdapter(Context context,
			List<MerchantGoodTitleBean> data) {
		super(context, data);
	}

	@Override
	public int getItemResource(int pos) {

		return R.layout.item_merchant_goods_left_list;
	}

	@Override
	public View getItemView(int position, View convertView,
			MyBaseAdapter<MerchantGoodTitleBean>.ViewHolder holder,
			ViewGroup parent) {
		MerchantGoodTitleBean info = data.get(position);

		TextView content = holder
				.getView(R.id.item_merchant_left_list_content_tv);
		content.setText(info.getName());
		content.setTextColor(Color.parseColor(index==position?"#1FC796":"#4A4A4A"));
		content.setTextColor(Color.parseColor(index==position?"#1FC796":"#4A4A4A"));

		if (info.isIscheck()) {

		}
		return convertView;
	}

}
