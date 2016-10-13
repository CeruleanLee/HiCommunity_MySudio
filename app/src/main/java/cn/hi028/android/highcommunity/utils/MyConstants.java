package cn.hi028.android.highcommunity.utils;



/**
 * @功能：自治大厅接口 httphelper <br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */
public interface MyConstants {
	String HOST = "http://028hi.cn/api/";

	/**自治大厅初始化接口
	 * 请求方式 post
	 * 参数 token
	 */
	String AUTOACT_INIT = HOST + "yinit/index.html";
	String URL_GET_DUOBAO_LIST = HOST + "/Duobao-XM/unpay_list?uid={0}";

}
