/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.alliance;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;

import net.duohuo.dhroid.util.LogUtil;
import net.duohuo.dhroid.view.CustomListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.OderDetailsProductAdapter;
import cn.hi028.android.highcommunity.bean.GoodsOrderSubmitBean;
import cn.hi028.android.highcommunity.bean.NearbyOrderDetailBean;
import cn.hi028.android.highcommunity.bean.NearbyOrderdetail2.NearbyOrderDeatai_Root;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;
/**
 * 商家订单详情
 * @author Administrator
 *
 */
@ContentView(R.layout.common_order_detail)
public class ShangJiaOrderDetailAct extends Activity implements OnClickListener{
	

	@ViewInject(R.id.img_back)
	private ImageView back;
	
	@ViewInject(R.id.tv_reserve_name)
	TextView tv_reserve_name;
	@ViewInject(R.id.tv_order_id)
	TextView tv_order_id;
	@ViewInject(R.id.tv_order_time)
	TextView tv_order_time;
	@ViewInject(R.id.fl_order_op)
	FrameLayout fl_order_op;
	@ViewInject(R.id.tv_reserve_phone)
	TextView tv_reserve_phone;
	@ViewInject(R.id.tv_reserve_address)
	TextView tv_reserve_address;
	@ViewInject(R.id.tv_coupon)
	TextView tv_coupon;
	@ViewInject(R.id.tv_reserve_wallet)
	TextView tv_reserve_wallet;
	@ViewInject(R.id.tv_price)
	TextView tv_price;
	@ViewInject(R.id.tv_total_pay)
	TextView tv_total_pay;
	@ViewInject(R.id.tv_order_operate2)
	TextView tv_order_operate2;
	@ViewInject(R.id.tv_order_operate1)
	TextView tv_order_operate1;
	@ViewInject(R.id.cl_goods)
	CustomListView cl_goods;
	
//	tv_order_operate2
	OderDetailsProductAdapter adapter;
	List<GoodsOrderSubmitBean> goods_info = new ArrayList<GoodsOrderSubmitBean>();
	NearbyOrderDetailBean data;
	private String out_trade_no;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d("~~~jinru商家订单");
		x.view().inject(this);
		out_trade_no = getIntent().getStringExtra("out_trade_no");
		LogUtil.d("~~~传递过来的订单号为："+out_trade_no);
		
		initViews();
		
	}
	
	public void initViews() {
		back = (ImageView) findViewById(R.id.img_back);
		back.setOnClickListener(this);
		tv_order_operate2.setOnClickListener(this);
		adapter = new OderDetailsProductAdapter(this, goods_info);
		cl_goods.setAdapter(adapter);
		HTTPHelper.getOderDetail(mIbpi, out_trade_no);
		
	}
String orderSendToDeatailAct;
	public void updateData(NearbyOrderDetailBean bean) {
		orderSendToDeatailAct=bean.getOrder_num();
		tv_order_id.setText("订单号：" + bean.getOrder_num());
		tv_order_time.setText("下单时间：" + TimeUtil.getDayAllTime(bean.getCreate_time()));
		tv_reserve_name.setText(bean.getConsign());
		tv_reserve_phone.setText(bean.getTel());
		tv_reserve_address.setText(bean.getAddress());
		tv_total_pay.setText("￥" + bean.getTotal_price() + "");
		tv_price.setText("￥" + CommonUtils.f2Bi(bean.getTotal_price()));
		goods_info.clear();
		goods_info.addAll(bean.getGoods_info());
		adapter.notifyDataSetChanged();
		// tv_order_operate1.setVisibility(View.GONE);
		// tv_order_operate2.setVisibility(View.GONE);
		
	}
	
	
	IBpiHttpHandler mIbpi = new IBpiHttpHandler() {
		
		private NearbyOrderDeatai_Root nearbyOrderDeatai_Root;

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			
		}
		
		@Override
		public void onSuccess(Object message) {
			if (message != null) {
			}
			LogUtil.d("~~~ 获取商家联盟订单详情：message:"+message.toString());
//			nearbyOrderDeatai_Root = new Gson().fromJson(message.toString(), NearbyOrderDeatai_Root.class);
			data = (NearbyOrderDetailBean) message;
			LogUtil.d("~~~ 获取商家联盟订单详情：data:"+data.toString());
			updateData(data);
			
		}
		
		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveNearbyOrderDetail(result);
		}
		
		@Override
		public void onError(int id, String message) {
			
		}
		
		@Override
		public void cancleAsyncTask() {
		}

		@Override
		public void shouldLogin(boolean isShouldLogin) {

		}

		@Override
		public void shouldLoginAgain(boolean isShouldLogin, String msg) {
			if (isShouldLogin){
				HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
				HighCommunityApplication.toLoginAgain(ShangJiaOrderDetailAct.this);
			}
		}

	};
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_order_operate2:
			Intent intent =new Intent(ShangJiaOrderDetailAct.this,AllianceOderDetailActivity.class);
			intent.putExtra("order_num", orderSendToDeatailAct);
			startActivity(intent);
			break;
		}
		
		
	}
	
}
