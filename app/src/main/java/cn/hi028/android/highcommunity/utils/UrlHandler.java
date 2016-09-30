package cn.hi028.android.highcommunity.utils;

public class UrlHandler {
	/***
	 * http://cnblogs.davismy.com/Handler.ashx?op=GetTimeLine&channelpath={0}&page={1}
	 * {"data/123","1"}
	 * @param url
	 * @param params
	 * @return
	 */
	public  static final String handlUrl(String url,Object... params){
		for (int i = 0; i < params.length; i++) {
			url = url.replace("{"+i+"}", params[i]+"");
		}
		return url;
	}
}
