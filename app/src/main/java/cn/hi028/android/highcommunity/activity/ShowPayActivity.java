package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOderDetailActivity;
import cn.hi028.android.highcommunity.activity.alliance.ShangJiaOrderDetailAct;
import cn.hi028.android.highcommunity.adapter.ShowPayAdapter;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.AddressModel;
import cn.hi028.android.highcommunity.bean.AddressOrderBean;
import cn.hi028.android.highcommunity.bean.AliParamBean;
import cn.hi028.android.highcommunity.bean.AlliancePayBean;
import cn.hi028.android.highcommunity.bean.Good_infoModel;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.OrderResult;
import cn.hi028.android.highcommunity.bean.SubmitOrderBean;
import cn.hi028.android.highcommunity.bean.WechatParamBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.alipay.AlipayUtils;
import cn.hi028.android.highcommunity.utils.alipay.AlipayUtils.onPayListener;
import cn.hi028.android.highcommunity.utils.alipay.PayResult;
import cn.hi028.android.highcommunity.utils.wchatpay.WchatPayUtils;

/**
 * 联盟商品详情 支付跳转过来的界面
 * @author Administrator
 *
 */
public class ShowPayActivity extends BaseFragmentActivity implements
OnClickListener, OnItemClickListener {
	private static final int WECHAT = 1;
	private static final int ALIPAY = 2;
	ListView listview;
	ShowPayAdapter adapter;
	private TextView mTvPay;
	private RadioButton mBtnWeiXin;
	private RadioButton mBtnAli;
	private ImageView mBackBtn;
	private TextView totalPay;
	private int isChecked;
	private int isNotChecked;
	private SubmitOrderBean mOrderBean;
	private AddressOrderBean addr;
	ArrayList<Goods_info> godsList;
	private TextView recipient;
	private TextView phone;
	private TextView defaultFlag;
	private TextView addressDtl;
	private RelativeLayout showAddress;
	private TextView noAddress;
	private String total_price;
	private boolean mIsPaying;
	private AddressBean address;
	private OrderResult mOrderResult;//对象没有被初始化 
	private int witchType = -1;
	private WechatParamBean mWechatBean;
	private AliParamBean mAliBean;
	private AlliancePayBean mPayBean;
	private boolean isAllianceOrder = false;
	private String mOrderNumber = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_pay);
		if (getIntent() != null && getIntent().hasExtra("payParams")) {
			isAllianceOrder = true;
			mPayBean = (AlliancePayBean) getIntent().getSerializableExtra(
					"payParams");
			godsList = convert(mPayBean.getGoods_info());
			total_price = mPayBean.getTotal_price();
			mOrderNumber = mPayBean.getOrder_num();
		} else {
			isAllianceOrder = false;
			godsList = getIntent().getParcelableArrayListExtra("gods_list");
			total_price = getIntent().getStringExtra("total_price");
			mOrderBean = (SubmitOrderBean) getIntent().getSerializableExtra(
					"MerchantShopFrag");
			mOrderNumber = mOrderBean.getOrder_num();
		}
		init();
	}

	private ArrayList<Goods_info> convert(List<Good_infoModel> goodsModels) {
		if (goodsModels == null) {
			if (godsList == null) {
				return new ArrayList<Goods_info>();
			} else {
				godsList.clear();
				return godsList;
			}
		}
		if (godsList == null) {
			godsList = new ArrayList<Goods_info>();
		} else {
			godsList.clear();
		}
		for (Good_infoModel goos_info : goodsModels) {
			Goods_info info = new Goods_info();
			info.setThumb_pic(goos_info.getThumb_pic());
			info.setGoods_name(goos_info.getGoods_name());
			info.setPrice(String.valueOf(goos_info.getGoods_price()));
			info.setCounts(goos_info.getNumber());
			info.setAll_price(goos_info.getGoods_total_price());
			godsList.add(info);
		}
		return godsList;
	}

	private void init() {
		adapter = new ShowPayAdapter(this, godsList);
		listview = (ListView) findViewById(R.id.ac_show_pay_listview);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		mTvPay = (TextView) findViewById(R.id.ac_show_pay_tv);
		totalPay = (TextView) findViewById(R.id.ac_show_pay_all_price);
		mTvPay.setOnClickListener(this);
		recipient = (TextView) findViewById(R.id.recipient);
		phone = (TextView) findViewById(R.id.phone);
		defaultFlag = (TextView) findViewById(R.id.default_flag);
		addressDtl = (TextView) findViewById(R.id.address);
		showAddress = (RelativeLayout) findViewById(R.id.ac_show_pay_address_rl);
		showAddress.setOnClickListener(this);
		noAddress = (TextView) findViewById(R.id.no_address);
		noAddress.setOnClickListener(this);
		mBackBtn = (ImageView) findViewById(R.id.ac_showpay_title_go_back);
		mBackBtn.setOnClickListener(this);
		mBtnWeiXin = (RadioButton) findViewById(R.id.rad_btn_weixin_pay);
		mBtnAli = (RadioButton) findViewById(R.id.rad_btn_ali_pay);
		totalPay.setText("¥" + total_price);
		if (isAllianceOrder) {
			if (mPayBean.getAddress()!=null) {
				showAddress.setVisibility(View.VISIBLE);
				noAddress.setVisibility(View.GONE);
				addr = convert(mPayBean.getAddress());
				recipient.setText(addr.getConsign());
				phone.setText(addr.getTel());
				addressDtl.setText(addr.getAddress());
			} else {
				showAddress.setVisibility(View.GONE);
				noAddress.setVisibility(View.VISIBLE);
			}
		} else {
			if (mOrderBean != null) {
				if (mOrderBean.getAddress() != null) {
					showAddress.setVisibility(View.VISIBLE);
					noAddress.setVisibility(View.GONE);
					addr = mOrderBean.getAddress();
					recipient.setText(addr.getConsign());
					phone.setText(addr.getTel());
					addressDtl.setText(addr.getAddress());
				} else {
					showAddress.setVisibility(View.GONE);
					noAddress.setVisibility(View.VISIBLE);
				}
			}
		}
		isChecked = R.mipmap.img_payment_checked;
		isNotChecked = R.mipmap.img_payment_uncheck;

		mBtnWeiXin.setChecked(true);// 设置默认微信选中img_payment_checked
		witchType = WECHAT;
		mBtnWeiXin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBtnWeiXin.setChecked(true);
				mBtnAli.setChecked(false);
				Toast.makeText(ShowPayActivity.this, "微信支付选中",
						Toast.LENGTH_SHORT).show();

			}
		});
		mBtnAli.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBtnWeiXin.setChecked(false);
				mBtnAli.setChecked(true);
				Toast.makeText(ShowPayActivity.this, "支付宝支付选中",
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	private AddressOrderBean convert(AddressModel addressModel) {
		AddressOrderBean bean = new AddressOrderBean();
		bean.setAddress(addressModel.getAddress());
		bean.setConsign(addressModel.getConsign());
		bean.setTel(addressModel.getTel());
		bean.setId(addressModel.getId());
		return bean;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_show_pay_tv:
			if (TextUtils.isEmpty(recipient.getText().toString().trim())
					&& TextUtils
					.isEmpty(addressDtl.getText().toString().trim())) {
				Toast.makeText(ShowPayActivity.this, "请填写收货地址",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(mOrderNumber)) {
				Toast.makeText(ShowPayActivity.this, "订单异常",Toast.LENGTH_SHORT).show();
				return;
			}
			if (addr == null) {
				return;
			}
			String orderIdStr = mOrderNumber;
			if (mBtnWeiXin.isChecked()) {
				witchType = WECHAT;
				HTTPHelper.getPayParam(payRequest, orderIdStr, WECHAT,
						addr.getConsign(), addr.getTel(), addr.getAddress());
			} else if (mBtnAli.isChecked()) {
				witchType = ALIPAY;
				HTTPHelper.getPayParam(payRequest, orderIdStr, ALIPAY,
						addr.getConsign(), addr.getTel(), addr.getAddress());
			}
			break;
		case R.id.ac_show_pay_address_rl:

			Intent mIntent = new Intent(this,
					GeneratedClassUtils.get(AddressAct.class));
			startActivityForResult(mIntent, 1001);
			break;

		case R.id.no_address:
			Intent mAddress = new Intent(this,
					GeneratedClassUtils.get(AddressAct.class));
			startActivityForResult(mAddress, 1001);
			// TODO 设置收货地址
			break;
		case R.id.ac_showpay_title_go_back:
			onBackPressed();
			break;
		}
	}

	public void goBack(View v) {
		onBackPressed();

	}

	@Override
	public void onBackPressed() {
		if (mIsPaying) {
			return;
		}
		finish();
	}

	/**
	 * 在支付界面点击支付请求的接口
	 */
	IBpiHttpHandler payRequest = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			if (null == message)
				return;
			switch (witchType) {
			case WECHAT:

				mWechatBean = (WechatParamBean) message;
				Log.d("------=-====------",mWechatBean.getOut_trade_no());
				if (mWechatBean != null) {
					WchatPayUtils
					.getInstance()
					.init(ShowPayActivity.this)
					.apay(ShowPayActivity.this, mWechatBean.getAppid(),
							mWechatBean.getPartnerid(),
							mWechatBean.getPrepayid(),
							mWechatBean.getNoncestr(),
							mWechatBean.getPackages(),
							mWechatBean.getSign(),
							mWechatBean.getTimestamp());
				} else {
					Toast.makeText(ShowPayActivity.this, "支付失败请重试",
							Toast.LENGTH_LONG).show();
				}
				break;
			case ALIPAY:
				mAliBean = (AliParamBean) message;
				if (mAliBean != null) {
					AlipayUtils.getInstance().payGoods(ShowPayActivity.this,
							mAliBean.getNotify_url(), mAliBean.getSubject(),
							mAliBean.getBody(), mAliBean.getTotal_fee(),
							mAliBean.getOut_trade_no(), new onPayListener() {

						@Override
						public void onPay(PayResult payResult) {
							// 这里就是你支付过后返回的消息 成功or失败
							payResult.getResult();
							String resultStatus = payResult
									.getResultStatus();
							// 判断resultStatus
							// 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
							if (TextUtils.equals(resultStatus, "9000")) {
								Toast.makeText(ShowPayActivity.this,
										"支付成功", Toast.LENGTH_SHORT)
										.show();									
								// TODO支付成功后跳转，并传递相应的数据过去
								if (isAllianceOrder) {
									finish();
								} else {
									Intent intent = new Intent(ShowPayActivity.this,
											ShangJiaOrderDetailAct.class);
									intent.putExtra("out_trade_no",
											mAliBean.getOut_trade_no());
									LogUtil.d("~~~联盟商品详情页支付成功，传递订单号到商家订单详情页：订单号为："+mAliBean.getOut_trade_no());
									startActivityForResult(intent, 1010);
									finish();
								}
							} else {
								// 判断resultStatus 为非"9000"则代表可能支付失败
								// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
								if (TextUtils.equals(resultStatus,
										"8000")) {
									Toast.makeText(
											ShowPayActivity.this,
											"支付结果确认中",
											Toast.LENGTH_SHORT).show();

								} else {
									// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
									Toast.makeText(
											ShowPayActivity.this,
											"支付失败", Toast.LENGTH_SHORT)
											.show();
								}
							}

						}
					});
				} else {
					Toast.makeText(ShowPayActivity.this, "支付失败请重试",
							Toast.LENGTH_LONG).show();
				}

				break;
			default:
				Toast.makeText(ShowPayActivity.this, "支付失败请重试",
						Toast.LENGTH_LONG).show();
				break;
			}
		}

		@Override
		public Object onResolve(String result) {
			switch (witchType) {
			case WECHAT:
				return HTTPHelper.ResolveWechatParams(result);
			case ALIPAY:
				return HTTPHelper.ResolveAliParams(result);
			default:
				return "";
			}
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
				HighCommunityApplication.toLoginAgain(ShowPayActivity.this);
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 跳转到activity
		Intent intent = new Intent(this, AllianceOderDetailActivity.class);
		intent.putExtra("order_num", mOrderNumber);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && requestCode == 1001 && resultCode == 0x22) {
			address = (AddressBean) data.getSerializableExtra("address");
			recipient.setText(address.getReal_name());
			phone.setText(address.getTel());
			if ("0".equals(address.getIsDefault())) {
				defaultFlag.setVisibility(View.GONE);
			} else {
				defaultFlag.setVisibility(View.VISIBLE);
			}
			addressDtl.setText(address.getAddress());
			showAddress.setVisibility(View.VISIBLE);
			noAddress.setVisibility(View.GONE);
		}

		if(requestCode == 1010){
			finish();
		}

	}
}
