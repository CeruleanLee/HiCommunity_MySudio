package cn.hi028.android.highcommunity.activity.alliance;

import net.duohuo.dhroid.util.LogUtil;

import com.don.tools.BpiHttpHandler;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.MyGoodsEvluateActivity;
import cn.hi028.android.highcommunity.activity.ShowPayActivity;
import cn.hi028.android.highcommunity.bean.Alli_Root;
import cn.hi028.android.highcommunity.bean.AlliancePayBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 联盟订单的详情
 * @author Administrator
 *
 */
public class AllianceOderDetailActivity extends BaseFragmentActivity {

	TextView tv_reserve_name;
	TextView tv_order_id;
	TextView tv_order_time;
	FrameLayout fl_order_op;
	TextView tv_reserve_phone;
	TextView tv_reserve_address;
	TextView tv_coupon;
	TextView tv_reserve_wallet;
	TextView tv_price;
	TextView tv_total_pay;
	Button tv_order_operate2;
	TextView tv_goods_price;
	TextView tv_goods_num,tv_goods_name;
	ImageView img_goods_pic;
	Button tv_order_operate1;
	View ll_NoticeDetails_Progress;
	private String out_trade_no;
	Alli_Root data;
	PopupWindow waitPop;
	String order_num;
	ViewGroup userinfroCantainer;
	ImageView img_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);	


		LogUtil.d("------act-onCreate");
		setView();
		initView();
	}

	private void setView() {
		tv_reserve_name = (TextView) findViewById(R.id.tv_reserve_name);
		tv_order_id = (TextView) findViewById(R.id.tv_order_id);
		tv_order_time = (TextView) findViewById(R.id.tv_order_time);
		fl_order_op = (FrameLayout) findViewById(R.id.fl_order_op);
		//				fl_order_op.setVisibility(View.INVISIBLE);
		tv_reserve_phone = (TextView) findViewById(R.id.tv_reserve_phone);
		tv_reserve_address = (TextView) findViewById(R.id.tv_reserve_address);
		tv_coupon = (TextView) findViewById(R.id.tv_coupon);
		tv_reserve_wallet = (TextView) findViewById(R.id.tv_reserve_wallet);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_total_pay = (TextView) findViewById(R.id.tv_total_pay);
		tv_order_operate2 = (Button) findViewById(R.id.tv_order_operate2);
		tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);
		tv_order_operate1 = (Button) findViewById(R.id.tv_order_operate1);
		tv_goods_num = (TextView) findViewById(R.id.tv_goods_num);
		tv_goods_name=(TextView) findViewById(R.id.tv_goods_name);
		img_goods_pic = (ImageView) findViewById(R.id.img_goods_pic);
		ll_NoticeDetails_Progress = findViewById(R.id.ll_NoticeDetails_Progress);
		userinfroCantainer=(ViewGroup) findViewById(R.id.order_userinfro);
		img_back=(ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	void initView() {

		out_trade_no = getIntent().getStringExtra("order_num");
		LogUtil.d("------MyOderDetailActivity-getorder_num="+out_trade_no);
		ll_NoticeDetails_Progress.setVisibility(View.VISIBLE);
		//		HTTPHelper.getAlliOrderDetail(mIbpiOrder, out_trade_no);
		HTTPHelper.getOderDetail(mIbpiOrder, out_trade_no);
		LogUtil.d("------2");
	}
	BpiHttpHandler.IBpiHttpHandler mIbpiOrder = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			LogUtil.d("------3    onError-------------"+message);
			ll_NoticeDetails_Progress.setVisibility(View.GONE);
			finish();
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			LogUtil.d("------4    onSuccess:"+message);
			ll_NoticeDetails_Progress.setVisibility(View.GONE);
			if (null == message)
				return;
			Gson gson = new Gson();
			Alli_Root bean = gson.fromJson(message.toString(),
					Alli_Root.class);
			updateData(bean);
			Log.e("------renk", bean.toString());
		}

		@Override
		public Object onResolve(String result) {
			LogUtil.d("------5");
			Log.e("------renk", result);
			return result;
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			LogUtil.d("------6");
		}

		@Override
		public void cancleAsyncTask() {
			LogUtil.d("------7");
			ll_NoticeDetails_Progress.setVisibility(View.GONE);
		}
	};

	/**
	 * 更新数据
	 * @param bean
	 */
	public void updateData(Alli_Root bean) {
		LogUtil.d("------8");
		data=bean;
		setDataForView(bean);
		setPayStatus(bean);
	}
	/**
	 * 底部支付逻辑判断
	 * @param bean
	 */
	private void setPayStatus(final Alli_Root bean) {
		LogUtil.d("------setPayStatus");

		int str=bean.getStatus();
		LogUtil.d("------str" +str);
		if (bean.getStatus()==0) {
			//未支付
			status0Unpay(bean);
		} else if (bean.getStatus() == 1 || bean.getStatus() == 2) {
			//已支付或已发货
			status1PayedOr2Sended(bean);
		} else if (bean.getStatus() == 3) {
			tv_order_operate2.setText("评价");
			tv_order_operate1.setVisibility(View.INVISIBLE);
			tv_order_operate2.setVisibility(View.VISIBLE);
			tv_order_operate2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					//					Toast.makeText(AllianceOderDetailActivity.this,"点击了评论", 0).show();
					//										Intent mIntent = new Intent(AllianceOderDetailActivity.this, GeneratedClassUtils.get(MyGoodsEvluateActivity.class));
					Intent mIntent = new Intent(AllianceOderDetailActivity.this,MyGoodsEvluateActivity.class);

					mIntent.putExtra("order_num", out_trade_no);
					startActivity(mIntent);
				}
			});
		} else if (bean.getStatus() == -1) {
			tv_order_operate2.setText("取消订单");
			tv_order_operate1.setVisibility(View.INVISIBLE);
			tv_order_operate2.setVisibility(View.VISIBLE);
			tv_order_operate2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					//					Toast.makeText(AllianceOderDetailActivity.this,"点击了取消", 0).show();
					cancelOrder(v, "取消订单", bean);
				}
			});
		} else if (bean.getStatus() == 4) {
			tv_order_operate2.setText("删除订单");
			tv_order_operate1.setVisibility(View.INVISIBLE);
			tv_order_operate2.setVisibility(View.VISIBLE);
			tv_order_operate2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//					Toast.makeText(AllianceOderDetailActivity.this,"点击了删除", 0).show();
					cancelOrder(v, "删除订单", bean);
				}
			});
		}
	}

	private void status1PayedOr2Sended(final Alli_Root bean) {
		tv_order_operate2.setText("确认收货");
		tv_order_operate1.setVisibility(View.INVISIBLE);
		tv_order_operate2.setVisibility(View.VISIBLE);
		tv_order_operate2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HTTPHelper.ConfirmOrder(mIbpiConfirm, bean.getOrder_num());
			}
		});
	}

	private void status0Unpay(final Alli_Root bean) {
		tv_order_operate1.setText("取消订单");
		tv_order_operate2.setText("付款");
		tv_order_operate1.setVisibility(View.VISIBLE);
		tv_order_operate2.setVisibility(View.VISIBLE);
		tv_order_operate1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//					Toast.makeText(AllianceOderDetailActivity.this,"点击了取消", 0).show();
				//					HTTPHelper.CancelOrder(mIbpiCancel,
				//							bean.getOrder_num());
				cancelOrder(v, "取消订单", bean);
			}
		});
		tv_order_operate2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtil.d("-----付款点击---0");
				HTTPHelper.GoPay(mIbpiGoPay, bean.getOrder_num());
			}
		});
	}
	/**
	 *为控件设置数据 part1
	 * @param bean
	 */
	private void setDataForView(Alli_Root bean) {
		Picasso.with(this).load(Constacts.IMAGEHTTP + bean.getGoods_info().get(0).getThumb_pic()).into(img_goods_pic);
		tv_order_id.setText("订单号：" + bean.getOrder_num());
		tv_order_time.setText("下单时间："+ TimeUtil.getDayAllTime(Long.parseLong(bean.getCreate_time())));
		tv_goods_num.setText(bean.getGoods_info().get(0).getNumber());
		tv_goods_price.setText(bean.getGoods_info().get(0).getGoods_price());
		tv_goods_name.setText(bean.getGoods_info().get(0).getGoods_name());
		if (TextUtils.isEmpty(bean.getConsign())&&TextUtils.isEmpty(bean.getTel())&&TextUtils.isEmpty(bean.getAddress())) {
			userinfroCantainer.setVisibility(View.GONE);
		}else{

			tv_reserve_name.setText(bean.getConsign());
			tv_reserve_phone.setText(bean.getTel());
			tv_reserve_address.setText(bean.getAddress());
		}
		//TODO
		//这里需要改
		tv_coupon.setText(bean.getTicket_value() + "");
		tv_reserve_wallet.setText(bean.getZero_money() + "");
		tv_total_pay.setText("￥" + bean.getTotal_price() + "");
		tv_price.setText("￥" + bean.getTotal_price());
	}



	/**
	 * 取消订单
	 *
	 * @param v
	 */
	public void cancelOrder(final View v, final String msg,final Alli_Root bean) {
		ECAlertDialog dialog = ECAlertDialog.buildAlert(this, "你确定" + msg + "么？", "确定", "取消", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(AllianceOderDetailActivity.this, v, Gravity.CENTER);

				BpiHttpHandler.IBpiHttpHandler mIbpiCancel = new BpiHttpHandler.IBpiHttpHandler() {

					@Override
					public void setAsyncTask(AsyncTask asyncTask) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Object message) {
						HighCommunityUtils.GetInstantiation().ShowToast(msg+"成功", 0);
						finish();

					}

					@Override
					public Object onResolve(String result) {
						return result;
					}

					@Override
					public void onError(int id, String message) {
						HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
					}

					@Override
					public void cancleAsyncTask() {
					}
				};

				HTTPHelper.CancelOrder(mIbpiCancel,bean.getOrder_num());
				waitPop.dismiss();

			}
		}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		dialog.show();
	}

	/**
	 * 确认收货
	 *
	 * @param v
	 */
	//	public void reciveOrder(final View v) {
	//		ECAlertDialog dialog = ECAlertDialog.buildAlert(AllianceOderDetailActivity.this, "你确认收货么？", "确定", "取消", new Dialog.OnClickListener() {
	//			@Override
	//			public void onClick(DialogInterface dialog, int which) {
	//				waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(AllianceOderDetailActivity.this, v, Gravity.CENTER);
	//				HTTPHelper.reciveOrder(new BpiHttpHandler.IBpiHttpHandler() {
	//					@Override
	//					public void onError(int id, String message) {
	//						waitPop.dismiss();
	//						HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
	//					}
	//
	//					@Override
	//					public void onSuccess(Object message) {
	//						waitPop.dismiss();
	//						HighCommunityUtils.GetInstantiation().ShowToast("成功收货", 0);
	//						finish();
	//					}
	//
	//					@Override
	//					public Object onResolve(String result) {
	//						return null;
	//					}
	//
	//					@Override
	//					public void setAsyncTask(AsyncTask asyncTask) {
	//
	//					}
	//
	//					@Override
	//					public void cancleAsyncTask() {
	//						waitPop.dismiss();
	//					}
	//					//TODO
	//				}, data.getTicket_value() + "");
	//			}
	//		}, new DialogInterface.OnClickListener() {
	//			@Override
	//			public void onClick(DialogInterface dialog, int which) {
	//
	//			}
	//		});
	//		dialog.show();
	//	}

	/**
	 * 去支付界面
	 */
	BpiHttpHandler.IBpiHttpHandler mIbpiGoPay = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Object message) {
			AlliancePayBean result = (AlliancePayBean) message;
			Log.e("renk", result.toString());
			if (result != null) {
				Intent intent = new Intent(AllianceOderDetailActivity.this, ShowPayActivity.class);
				intent.putExtra("payParams", result);
				startActivity(intent);
			} else {
				HighCommunityUtils.GetInstantiation().ShowToast("访问异常", 0);
			}
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveAllianceGoPay(result);
		}

		@Override
		public void onError(int id, String message) {
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void cancleAsyncTask() {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * 确认订单操作
	 */
	BpiHttpHandler.IBpiHttpHandler mIbpiConfirm = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Object message) {
			HighCommunityUtils.GetInstantiation().ShowToast("确认收货成功", 0);
			update();
		}

		@Override
		public Object onResolve(String result) {
			return result;
		}

		@Override
		public void onError(int id, String message) {
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void cancleAsyncTask() {
			// TODO Auto-generated method stub

		}
	};


	private void update() {
		HTTPHelper.getOderDetail(mIbpiOrder, out_trade_no);

	}
@Override
protected void onResume() {
	update();
	super.onResume();
}

}
