package cn.hi028.android.highcommunity.view;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.PayPopupListAdapter;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.lisenter.PayPop2FragFace;
import cn.hi028.android.highcommunity.lisenter.ShopAddSubListener;

public class PaylistPopupWindow implements ShopAddSubListener {

	Context context;
	PopupWindow mPopWindow;
	LayoutInflater inflater;
	ListView listview;
	PayPopupListAdapter adapter;

	TextView price;
	TextView goPay;
	TextView count;
	PayPop2FragFace face;

	ArrayList<Goods_info> data;
	int number;
	double amount;

	public void setAmount(double d) {
		this.amount = d;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setFace(PayPop2FragFace face) {
		this.face = face;
	}

	public PaylistPopupWindow(Context context, List<Goods_info> data) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		if (data != null) {
			this.data = (ArrayList<Goods_info>) data;
			adapter = new PayPopupListAdapter(context, this.data);
			adapter.setFace(this);
		}

	}

	/**
	 * 展示
	 * 
//	 * @param location
	 */
	public void showPopwindow() {
		View view = inflater.inflate(R.layout.popup_pay_list_dailog, null);
		if (mPopWindow != null && mPopWindow.isShowing()) {
			return;
		}

		price = (TextView) view.findViewById(R.id.popup_pay_shop_car_allprice);
		count = (TextView) view.findViewById(R.id.popup_pay_shop_count);
		goPay = (TextView) view.findViewById(R.id.popup_pay_shop_car_go_pay);
		listview = (ListView) view.findViewById(R.id.popup_pay_listview);

		goPay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (face != null) {
					face.goPay(data);
				}
			}
		});
		listview.setAdapter(adapter);
		price.setText("" + amount);
		count.setText("" + number);
		mPopWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopWindow.setFocusable(true);
		mPopWindow.setTouchable(true);
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		mPopWindow.setOutsideTouchable(true);
		// mPopWindow.setAnimationStyle(R.anim.popup_commn_in);
		mPopWindow.setBackgroundDrawable(new ColorDrawable());
		mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		mPopWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mPopWindow.setOutsideTouchable(true);
		mPopWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				if (face != null) {
//					for (int i = data.size() - 1; i >= 0; i--) {
//						if (data.get(i).getCounts() == 0) {
//							data.remove(i);
//						}
//					}
					face.backAllList(data);
					face.setNumAndAmount(number, amount);
				}
				mPopWindow = null;
			}
		});
		mPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public void add(int position) {
		Goods_info info = data.get(position);
		int count = info.getCounts();
		info.setCounts(++count);
		adapter.notifyDataSetChanged();
		setNum();
	}

	@Override
	public void sub(int position) {
		Goods_info info = data.get(position);
		int count = info.getCounts();
		if (count > 0) {
			info.setCounts(--count);
		} else if (count == 0) {
			// data.remove(position);
		}
		adapter.notifyDataSetChanged();
		setNum();
	}

	private void setNum() {
		price.setText("" + getAllAmount());
		count.setText("" + getAllCount());
	}

	private int getAllCount() {
		int count = 0;
		for (Goods_info info : data) {
			count += info.getCounts();
		}
		return count;
	}

	private String getAllAmount() {

		DecimalFormat df = new DecimalFormat("0.00");
		double price = 0;
		for (Goods_info info : data) {
			price += info.getCounts() * Double.parseDouble(info.getPrice());
		}
		return df.format(price);

	}

	@Override
	public void goDetail(int id) {
		// TODO Auto-generated method stub

	}
}
