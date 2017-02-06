/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.CustomListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.adapter.HuiGdPayAdapter;
import cn.hi028.android.highcommunity.bean.ChipOrderDetailBean;
import cn.hi028.android.highcommunity.bean.chiporder.ChipOrderDetail;
import cn.hi028.android.highcommunity.params.HuiSuppGdParams;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：惠生活 众筹订单详情<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-02-01<br>
 */
@EFragment(resName = "frag_hlife_order_detail")
public class HuiChipOrderDetailFrag extends BaseFragment {
	public static final String FRAGMENTTAG = "HuiOrderDetailFrag";
	@ViewById(R.id.tv_reserve_name)
	TextView tv_reserve_name;
	@ViewById(R.id.tv_order_id)
	TextView tv_order_id;
	@ViewById(R.id.tv_order_time)
	TextView tv_order_time;
	@ViewById(R.id.fl_order_op)
	FrameLayout fl_order_op;

	@ViewById(R.id.tv_reserve_phone)
	TextView tv_reserve_phone;
	@ViewById(R.id.tv_reserve_address)
	TextView tv_reserve_address;
	@ViewById(R.id.tv_coupon)
	TextView tv_coupon;
	@ViewById(R.id.tv_reserve_wallet)
	TextView tv_reserve_wallet;
	@ViewById(R.id.tv_price)
	TextView tv_price;
	@ViewById(R.id.tv_total_pay)
	TextView tv_total_pay;
	@ViewById(R.id.tv_order_operate2)
	TextView tv_order_operate2;
	@ViewById(R.id.tv_order_operate1)
	TextView tv_order_operate1;
	@ViewById(R.id.ll_NoticeDetails_Progress)
	View ll_NoticeDetails_Progress;
	@ViewById(R.id.cl_goods)
	CustomListView cl_goods;
	String out_trade_no;
	HuiGdPayAdapter adapter;
	ChipOrderDetail data;
	PopupWindow waitPop;

	@AfterViews
	void initView() {
		fl_order_op.setVisibility(View.GONE);
		adapter = new HuiGdPayAdapter(this);
		cl_goods.setAdapter(adapter);
		out_trade_no = getActivity().getIntent().getStringExtra(
				MenuLeftSecondAct.INTENTTAG);
		ll_NoticeDetails_Progress.setVisibility(View.VISIBLE);
		HTTPHelper.getChipOrderDetail(mIbpiOrder, out_trade_no);
	}

	BpiHttpHandler.IBpiHttpHandler mIbpiOrder = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			ll_NoticeDetails_Progress.setVisibility(View.GONE);
			getActivity().finish();
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			ll_NoticeDetails_Progress.setVisibility(View.GONE);
			if (null == message)
				return;
			ChipOrderDetailBean bean = (ChipOrderDetailBean) message;
			updateData(bean);
			Log.e("renk", bean.toString());
		}

		@Override
		public Object onResolve(String result) {
			Log.e("renk", result);
			return HTTPHelper.ResolveGoodsOrder(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {
			ll_NoticeDetails_Progress.setVisibility(View.GONE);
		}

		@Override
		public void shouldLogin(boolean isShouldLogin) {
		}

		@Override
		public void shouldLoginAgain(boolean isShouldLogin, String msg) {
			if (isShouldLogin){
				HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
				HighCommunityApplication.toLoginAgain(getActivity());
			}
		}
	};

	/**
	 * 更新数据
	 *
	 * @param bean
	 */
	public void updateData(ChipOrderDetailBean bean) {

		HuiSuppGdParams goodsBean = new HuiSuppGdParams();
		List<HuiSuppGdParams> list = new ArrayList<HuiSuppGdParams>();
		goodsBean.setNumber(bean.getNum());
		goodsBean.setGoods_name(bean.getName());
		goodsBean.setGoods_price(bean.getJoin_price());
		goodsBean.setPic(Arrays.asList(new String[] { bean.getCover_pic() }));
		list.add(goodsBean);
		adapter.setData(list);
		tv_order_id.setText("订单号：" + bean.getOut_trade_no());
		tv_order_time.setText("下单时间："
				+ TimeUtil.getDayAllTime(bean.getCreate_time()));
		tv_reserve_name.setText(bean.getReal_name());
		tv_reserve_phone.setText(bean.getTel());
		tv_reserve_address.setText(bean.getAddress());
		tv_coupon.setText(bean.getTicket_value() + "");
		tv_reserve_wallet.setText(bean.getZero_money() + "");
		tv_total_pay.setText("￥" + bean.getTotal_price() + "");
		tv_price.setText("￥" + CommonUtils.f2Bi(bean.getReal_price()));
		tv_order_operate1.setVisibility(View.GONE);
		tv_order_operate2.setVisibility(View.GONE);
	}
}
