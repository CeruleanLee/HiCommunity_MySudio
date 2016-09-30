/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.utils.alipay;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.alipay.sdk.app.PayTask;

import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.activity.ShowPayActivity;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：支付宝支付公共类<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-19<br>
 */
public class AlipayUtils {

	/**
	 * 支付宝配置Begin
	 */
	// 商户PID
	public static final String PARTNER = "2088021521483800";// 就先用这个 不管
	// 商户收款账号
	public static final String SELLER = "hishequ@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANmxmL/EXl5joeIo/nqyvtrhRKGvdoNgYxo/ywy7t1xL73SVM3kAOmDV45+u8EqFGdAOv1BEjvpeslTSp6kvngoeRx3XMXPvDqPaTYUePut7IQrcHcaa4wHLngubgMsbaZ84trAKkNrrH9F5upHwKKkhLMkr/w7YImPxGR/BuVodAgMBAAECgYAkvptf0w9yszKB8BV0NHOOknN5Lxq79isEJTxqu8ypYY/bh8uWYvl3muK+81JP4cOHoNWeANR5Yj6Lnrr6DbWJA8Rbof3qDB+ctT0WVaq+ZnnGXGvy9nB85DsPLocpgoNqoSzWKqCEGHJe+ZiHWaJQqZ0nvFnl2hTyeChDgkAUKQJBAP5uQ4XUgXphKhz8ZcbCnhKnsAyvuGVGJQ/zQYZP68lY/6ANYE5B9T7eKiPeYjHRpA676KRyVV1losqzTsmOMzMCQQDbCVOfIbOxaS/Bz6yhj+zsPf+gBv52s5p7s/ei6AUA8/JiwI/CcsBArih4WoWBTF4UNDaJpBfyXU2NRXiZqj1vAkAw0LsHzFPjRQ0tboegcOqfLakrZfN9Cs5Flyh1OpREl1zEOozFtwX0v+9Z3mr8n/pYEVl7Y6xZNQzc0zZEmZHdAkB8KlJT8AfsNyOSa4aB45O76/1fmvdfFLsrCr8DgyFrA97QajZUVTZ0tAVPDImH3WGrdFnCNto4Uvnmbo5KFb5TAkEA2NprVYz77HtabeVh0DIJQIEhwrAeLZKlcAidyppwxfGf0WzKAjJiv3mnbuBZQG9EeUjaMNbh2PVIMUN1ck5n1w==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	/**
	 * 支付回调接口 String resultStatus = payResult.getResultStatus(); //
	 * 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档 if
	 * (TextUtils.equals(resultStatus, "9000")) {
	 * Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
	 * } else { // 判断resultStatus 为非"9000"则代表可能支付失败 //
	 * "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态） if
	 * (TextUtils.equals(resultStatus, "8000")) {
	 * Toast.makeText(PayDemoActivity.this, "支付结果确认中",
	 * Toast.LENGTH_SHORT).show();
	 * <p/>
	 * } else { // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
	 * Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
	 * <p/>
	 * } }
	 */
	public interface onPayListener {
		public void onPay(PayResult result);

	}

	static AlipayUtils alipayUtils;

	/**
	 * 获取该类对象
	 * 
	 * @return
	 */
	public static AlipayUtils getInstance() {
		if (alipayUtils == null) {
			alipayUtils = new AlipayUtils();
		}
		return alipayUtils;
	}

	/**
	 * 支付商品方法
	 * 
	 * @param act
	 *            相关activity
	 * @param gName
	 *            商品名称
	 * @param gInfo
	 *            商品介绍
	 * @param aMount
	 *            商品金额
	 * @param lin
	 *            支付回调接口
	 */

	public void payGoods(final ShowPayActivity act, String notifyUrl,
			String gName, String gInfo, String aMount, String out_trade_no,
			final onPayListener lin) {
		String orderInfo = getOrderInfo(gName, gInfo, aMount, notifyUrl,
				out_trade_no);
		this.act=act;
				if (!HighCommunityApplication.isAliPayInStalled()) {
					LogUtil.d("------未安装支付宝");
					showUnInstallPayDialog();
				}
		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				//				// 构造PayTask 对象
				//				PayTask alipay = new PayTask(act);
				//				// 调用支付接口，获取支付结果
				//				final String result = alipay.pay(payInfo, true);
				//				act.runOnUiThread(new Runnable() {
				//					@Override
				//					public void run() {
				//						lin.onPay(new PayResult(result));
				//					}
				//				});


				if (!HighCommunityApplication.isAliPayInStalled()) {
					LogUtil.d("------未安装支付宝");
					//					HighCommunityApplication.showDialog(new View(HighCommunityApplication.getApp()));
				}else{
					// 构造PayTask 对象
					PayTask alipay = new PayTask(act);
					// 调用支付接口，获取支付结果
					final String result = alipay.pay(payInfo, true);
					LogUtil.d("~~~~~~~~~~~~~~获取支付结果   resultL---"+result);
					
					
					act.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							lin.onPay(new PayResult(result));
						}
					});
				}

			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付商品方法
	 * 
	 * @param act
	 *            相关activity
	 * @param gName
	 *            商品名称
	 * @param gInfo
	 *            商品介绍
	 * @param aMount
	 *            商品金额
	 * @param out_trade_no
	 *            订单号
	 * @param notify_url
	 *            异步回调地址
	 * @param lin
	 *            支付回调接口
	 */
	Activity act;
	public void payGoods(final Activity act, String gName, String gInfo,
			String aMount, String out_trade_no, String ticket_id,
			String zero_money, String notify_url, final onPayListener lin) {
		String orderInfo = getOrderInfo(gName, gInfo, aMount, out_trade_no,
				zero_money, ticket_id, notify_url);
		this.act=act;
		if (!HighCommunityApplication.isAliPayInStalled()) {
			LogUtil.d("------未安装支付宝");
			showUnInstallPayDialog();
		}

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {

				if (!HighCommunityApplication.isAliPayInStalled()) {
					LogUtil.d("------未安装支付宝");
					//TODO
					//加一个弹窗
				}else{
					// 构造PayTask 对象
					PayTask alipay = new PayTask(act);
					// 调用支付接口，获取支付结果
					final String result = alipay.pay(payInfo, true);
					LogUtil.d("~~~~~~~~~~~~~~获取支付结果   resultL---"+result);
					act.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							lin.onPay(new PayResult(result));
						}
					});
				}
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}




	/**
	 * create the order info. 创建订单信息
	 */
	private String getOrderInfo(String subject, String body, String price,
			String notifyUrl, String outTradeNo) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;
	}

	/**
	 * create the order info. 创建订单信息
	 */
	private String getOrderInfo(String subject, String body, String price,
			String out_trade_no, String zero_money, String ticket_id,
			String notify_url) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" +price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";
		JSONObject json = new JSONObject();
		try {
			json.put("ticket_id", ticket_id);//这两个参数什么意思  优惠劵
			json.put("zero_money", zero_money);
			orderInfo += "&out_context=\"" + json.toString() + "\"";
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	public void showUnInstallPayDialog() {
		ECAlertDialog dialog2=ECAlertDialog.buildAlert(act, "未安装支付宝，请前往安装。", "确定", "取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				BrowseActivity.toBrowseActivity(act, null, "http://d.alipay.com");
				
			}
		}, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});
		dialog2.show();
	}
}
