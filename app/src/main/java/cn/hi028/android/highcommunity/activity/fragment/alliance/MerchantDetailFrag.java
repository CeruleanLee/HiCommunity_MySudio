package cn.hi028.android.highcommunity.activity.fragment.alliance;

import com.alibaba.fastjson.JSON;
import com.don.tools.BpiHttpHandler.IBpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.MerchantDetailBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
/**
 * 惠生活-商家联盟-item点击 -详情
 * @author Administrator
 *
 */
public class MerchantDetailFrag extends BaseFragment {

	TextView name;
	TextView introduction;
	TextView address;
	TextView time;
	TextView telephone;
	/***/
	private String id;

	public void setMyId(String id2) {
		this.id = id2;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_merchant_detail, null);
		initView(view);
		init();
		return view;
	}

	private void init() {
		HTTPHelper.GetMerchantDetail(mIbpi, id);
	}

	private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			if (message != null) {
				MerchantDetailBean bean = JSON.parseObject(message.toString(),
						MerchantDetailBean.class);
				setUi(bean);
			}
		}

		@Override
		public Object onResolve(String result) {
			Log.e("==========", result);
			return result;
		}

		@Override
		public void onError(int id, String message) {

		}

		@Override
		public void cancleAsyncTask() {

		}
	};

	private void initView(View view) {
		name = (TextView) view.findViewById(R.id.frag_mer_detail_merchant_name);
		introduction = (TextView) view
				.findViewById(R.id.frag_merchant_introduction);
		address = (TextView) view.findViewById(R.id.frag_merchant_address);
		time = (TextView) view.findViewById(R.id.frag_merchant_time);
		telephone = (TextView) view.findViewById(R.id.frag_merchant_telephone);
		telephone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:"
								+ telephone
										.getText()
										.toString()
										.trim()
										.substring(
												5,
												telephone.getText().toString()
														.trim().length())));
				startActivity(intent2);
			}
		});
	}

	/**
	 * 得到数据展示
	 * 
	 * @param bean
	 **/
	protected void setUi(MerchantDetailBean bean) {

		name.setText("商家名称：" + bean.getShop_name());
		introduction.setText("商家简介：" + bean.getIntro());
		address.setText("地址：" + bean.getAddress());
		time.setText("配送时间：" + bean.getDelivery());
		telephone.setText("商家电话：" + bean.getTel());
	}
}
