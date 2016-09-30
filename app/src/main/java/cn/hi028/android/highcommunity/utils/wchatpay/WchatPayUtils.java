package cn.hi028.android.highcommunity.utils.wchatpay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by 赵海 on 2016/3/15.
 */
public class WchatPayUtils {
	static WchatPayUtils wchatPayUtils;
	IWXAPI api = null;

	public static WchatPayUtils getInstance() {
		if (wchatPayUtils == null) {
			wchatPayUtils = new WchatPayUtils();

		}
		return wchatPayUtils;
	}

	public WchatPayUtils init(Context context) {
		api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		return wchatPayUtils;
	}

	/**
	 * @param context
	 * @param appid
	 * @param partnerid
	 * @param prepayid
	 * @param noncestr
	 * @param packages
	 * @param sign
	 * @param timestamp
	 */
	public boolean apay(final Activity context, String appid, String partnerid,
			String prepayid, String noncestr, String packages, String sign,
			String timestamp) {
		if (isWXAppInstalledAndSupported()) {
			boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
			if (isPaySupported) {
				try {
					PayReq req = new PayReq();
					req.appId = appid;
					req.partnerId = partnerid;
					req.prepayId = prepayid;
					req.nonceStr = noncestr;
					req.timeStamp = timestamp;
					req.packageValue = packages;
					req.sign = sign;// gcreateSign(parameters);
					// 将该app注册到微信
					api.registerApp(Constants.APP_ID);
					api.sendReq(req);
					return true;
				} catch (Exception e) {
					Log.e("PAY_GET", "异常：" + e.getMessage());
					Toast.makeText(context, "支付异常", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "该微信版本不支持支付", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(context, "亲，安装微信客户端再支付吧", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	private boolean isWXAppInstalledAndSupported() {
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
				&& api.isWXAppSupportAPI();

		return sIsWXAppInstalledAndSupported;

	}

	/**
	 * 微信支付签名算法sign
	 *
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createSign(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=CB14CF625DB77DFEC359FF915D4EF9FB");
		String sign = MD5.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;
	}
}
