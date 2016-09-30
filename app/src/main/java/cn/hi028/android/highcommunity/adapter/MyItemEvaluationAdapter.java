package cn.hi028.android.highcommunity.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.utils.Constacts;

public class MyItemEvaluationAdapter extends MyBaseAdapter<String> {

	public MyItemEvaluationAdapter(Context context, List<String> data) {
		super(context, data);
	}

	@Override
	public int getItemResource(int pos) {
		return R.layout.item_evaluation_item_gridview;
	}

	@Override
	public View getItemView(int position, View convertView,
			MyBaseAdapter<String>.ViewHolder holder, ViewGroup parent) {
		ImageView image = holder.getView(R.id.item_item_gridview);
		Picasso.with(context).load(Constacts.IMAGEHTTP + data.get(position))
				.placeholder(R.drawable.ic_launcher).into(image);
		return convertView;
	}

}
