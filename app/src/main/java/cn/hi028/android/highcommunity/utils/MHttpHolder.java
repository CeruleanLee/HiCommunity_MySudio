package cn.hi028.android.highcommunity.utils;


import com.lidroid.xutils.HttpUtils;

/**
 * Created by Lee_yting on 2016/10/8 .
 * 说明：
 */
public class MHttpHolder {

	public static HttpUtils getHttpUtils(){
		HttpUtils mHttpUtils= new HttpUtils();
		mHttpUtils.configCurrentHttpCacheExpiry(0);
		mHttpUtils.configSoTimeout(4000);
		mHttpUtils.configTimeout(4000);
		return mHttpUtils;
	}
}
