package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;

public class EvalueAdapter extends BaseAdapter{
	
	private ArrayList<MerchantEvaluationInfoListBean> mData;
	private Activity activity;
	private ViewHolder holder;
	
	public EvalueAdapter(Activity activity,ArrayList<MerchantEvaluationInfoListBean> mData){
		this.mData = mData;
		this.activity = activity;
	}
	
	private class ViewHolder{
		
		ImageView head;
		TextView merchantname;
		TextView username;
		TextView time;
		TextView content;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
//	Toast.makeText(activity, "zoudaozhelile", Toast.LENGTH_SHORT).show();
		
		MerchantEvaluationInfoListBean evalue = mData.get(position);
		if(convertView == null){
			
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_merchant_evaluation, null);
			holder = new ViewHolder();
			
			holder.head = (ImageView) convertView.findViewById(R.id.item_evaluation_iv);
			holder.username = (TextView) convertView.findViewById(R.id.item_username_tv);
			holder.merchantname = (TextView) convertView.findViewById(R.id.item_evalutaion_merchantname_tv);
			holder.time = (TextView) convertView.findViewById(R.id.item_evaluation_time);
			holder.content = (TextView) convertView.findViewById(R.id.item_evalution_content_tv);
			
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.username.setText(evalue.getNick());
		holder.merchantname.setText(evalue.getShop_name());
		holder.time.setText(evalue.getCreate_time());
		holder.content.setText(evalue.getContent());
		
		
		
		return convertView;
	}
	
	public String getTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(new Date(time));
	}

}
