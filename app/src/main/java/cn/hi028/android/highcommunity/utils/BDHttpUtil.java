package cn.hi028.android.highcommunity.utils;


import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.bean.BDRequestLocationBean;
import cn.hi028.android.highcommunity.bean.SimplePoiBean;

/**
 * Created by Lee on 2016/12/25.
 * 说明：
 */
public class BDHttpUtil {
    static final String Tag = "BDHttpUtil--->";
    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 3000;
    //读取超时时间
    private static final int READ_TIMEOUT = 5000;
    // 参数编码
    private static final String ENCODE_CHARSET = "utf-8";
    /**
     * 百度地图host
     */
    private static final String HOST = "http://api.map.baidu.com/geocoder/v2/";
    static BDRequestLocationBean mBean = null;

    public static BDRequestLocationBean getLocation(double longitude, double latitude) {

        HttpUtils mHttpUtils = MHttpHolder.getHttpUtils();
        RequestParams params = new RequestParams();
//        params.addBodyParameter("callback", "renderReverse");
        params.addBodyParameter("location", longitude + "," + latitude);
        params.addBodyParameter("output", "json");
        params.addBodyParameter("pois", 1 + "");
        params.addBodyParameter("ak", "M3QEKDRLq6dsa2N8WakLdHfFKhKykWi2");
        params.addBodyParameter("mcode", "40:CD:86:08:98:1A:E2:DE:C3:0C:A9:97:3B:1B:82:A0:68:92:B4:EE;cn.hi028.android.highcommunity");

        mHttpUtils.send(HttpRequest.HttpMethod.POST, HOST, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String content = responseInfo.result;
                Log.e(Tag, "http 访问success的 content--->" + content);
                mBean = new Gson().fromJson(content, BDRequestLocationBean.class);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e(Tag, "http 访问失败的 arg1--->" + s.toString());
                HighCommunityUtils.GetInstantiation().ShowToast(s.toString(), 0);

            }
        });
        return mBean;
    }

    public static List<BDRequestLocationBean.ResultEntity.PoisEntity> getPOiList(double longitude, double latitude) {

        BDRequestLocationBean location = getLocation(longitude, latitude);
        if (location != null) {

            List<BDRequestLocationBean.ResultEntity.PoisEntity> poisList = location.getResult().getPois();
            return poisList;
        } else {
            return null;
        }

    }
    public static List<SimplePoiBean> getSimplePOiList(double longitude, double latitude) {

        BDRequestLocationBean location = getLocation(longitude, latitude);
        if (location != null) {

            List<BDRequestLocationBean.ResultEntity.PoisEntity> poisList = location.getResult().getPois();
            List<SimplePoiBean> mSimplePOiList   =new ArrayList<SimplePoiBean>();
            for (int i = 0; i < poisList.size(); i++) {
                SimplePoiBean mSPoiBean=new SimplePoiBean();
                mSPoiBean.setName(poisList.get(i).getName());
                mSPoiBean.setAddr(poisList.get(i).getAddr());
                mSimplePOiList.add(mSPoiBean);
            }
            return mSimplePOiList;
        } else {
            return null;
        }

    }

//
//    /**
//     * 发送HTTP_POST请求
//     *
//     * @see 本方法默认的连接和读取超时均为30秒
//     * @param reqURL
//     *            请求地址
//     * @param params
//     *            发送到远程主机的正文数据[a:1,b:2]
//     * @return String
//     */
//    public static String postRequest(String reqURL, String... params) {
//        StringBuilder resultData = new StringBuilder();
//        URL url = null;
//        try {
//
//            url = new URL(reqURL);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        HttpURLConnection urlConn = null;
//        InputStreamReader in = null;
//        BufferedReader buffer = null;
//        String inputLine = null;
//        DataOutputStream out = null;
//        if (url != null) {
//            try {
//                urlConn = (HttpURLConnection) url.openConnection();
//                urlConn.setDoInput(true);// 设置输入流采用字节流
//                urlConn.setDoOutput(true);// 设置输出流采用字节流
//                urlConn.setRequestMethod("POST");
//                urlConn.setUseCaches(false); // POST请求不能使用缓存
//                urlConn.setInstanceFollowRedirects(true);
//                urlConn.setConnectTimeout(CONNECTION_TIMEOUT);// 设置连接超时
//                urlConn.setReadTimeout(READ_TIMEOUT); // 设置读取超时
//                // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
//                urlConn.setRequestProperty("Content-Type",
//                        "application/x-www-form-urlencoded");
//                urlConn.setRequestProperty("Charset", ENCODE_CHARSET);//
//                String param = sendPostParams(params);
//                urlConn.setRequestProperty("Content-Length",
//                        param.getBytes().length + "");//
//                // urlConn.setRequestProperty("Connection", "Keep-Alive");
//                // //设置长连接
//                urlConn.connect();// 连接服务器发送消息
//                if (!"".equals(param)) {
//                    out = new DataOutputStream(urlConn.getOutputStream());
//                    // 将要上传的内容写入流中
//                    out.writeBytes(param);
//                    // 刷新、关闭
//                    out.flush();
//                    out.close();
//
//                }
//                in = new InputStreamReader(urlConn.getInputStream(),
//                        HttpUtil.ENCODE_CHARSET);
//                buffer = new BufferedReader(in);
//                if (urlConn.getResponseCode() == 200) {
//                    while ((inputLine = buffer.readLine()) != null) {
//                        resultData.append(inputLine);
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (buffer != null) {
//                        buffer.close();
//                    }
//
//                    if (in != null) {
//                        in.close();
//                    }
//
//                    if (urlConn != null) {
//                        urlConn.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return resultData.toString();
//    }
//
//    /**
//     * 发送HTTP_GET请求
//     *
//     * @see 本方法默认的连接和读取超时均为30秒
//     * @param httpUrl
//     *            请求地址
//     * @param map
//     *            发送到远程主机的正文数据[a:1,b:2]
//     * @return String
//     */
//    public static String getRequest(String httpUrl, String... params) {
//        StringBuilder resultData = new StringBuilder();
//        URL url = null;
//        try {
//
//            String paramurl = sendGetParams(httpUrl, params);
//            url = new URL(paramurl);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        HttpURLConnection urlConn = null;
//        InputStreamReader in = null;
//        BufferedReader buffer = null;
//        String inputLine = null;
//        if (url != null) {
//            try {
//                urlConn = (HttpURLConnection) url.openConnection();
//                urlConn.setRequestMethod("GET");
//                urlConn.setConnectTimeout(CONNECTION_TIMEOUT);
//                in = new InputStreamReader(urlConn.getInputStream(),
//                        HttpUtil.ENCODE_CHARSET);
//                buffer = new BufferedReader(in);
//                if (urlConn.getResponseCode() == 200) {
//                    while ((inputLine = buffer.readLine()) != null) {
//                        resultData.append(inputLine);
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (buffer != null) {
//                        buffer.close();
//                    }
//
//                    if (in != null) {
//                        in.close();
//                    }
//
//                    if (urlConn != null) {
//                        urlConn.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return resultData.toString();
//    }
//
//    /**
//     * Post追加参数
//     *
//     * @see <code>params</code>
//     * @param reqURL
//     *            请求地址
//     * @param params
//     *            发送到远程主机的正文数据[a:1,b:2]
//     * @return
//     */
//    private static String sendPostParams(String... params) {
//        StringBuilder sbd = new StringBuilder("");
//        if (params != null && params.length > 0) {
//            for (int i = 0; i < params.length; i++) {
//                String[] temp = params[i].split(":");
//                sbd.append(temp[0]);
//                sbd.append("=");
//                sbd.append(urlEncode(temp[1]));
//                sbd.append("&");
//
//            }
//            sbd.setLength(sbd.length() - 1);// 删掉最后一个
//        }
//        return sbd.toString();
//    }
//
//    /**
//     * Get追加参数
//     *
//     * @see <code>params</code>
//     * @param reqURL
//     *            请求地址
//     * @param params
//     *            发送到远程主机的正文数据[a:1,b:2]
//     * @return
//     */
//    private static String sendGetParams(String reqURL, String... params) {
//        StringBuilder sbd = new StringBuilder(reqURL);
//        if (params != null && params.length > 0) {
//            if (isexist(reqURL, "?")) {// 存在?
//                sbd.append("&");
//            } else {
//                sbd.append("?");
//            }
//            for (int i = 0; i < params.length; i++) {
//                String[] temp = params[i].split(":");
//                sbd.append(temp[0]);
//                sbd.append("=");
//                sbd.append(urlEncode(temp[1]));
//                sbd.append("&");
//
//            }
//            sbd.setLength(sbd.length() - 1);// 删掉最后一个
//        }
//        return sbd.toString();
//    }
//
//    // 查找某个字符串是否存在
//    private static boolean isexist(String str, String fstr) {
//        return str.indexOf(fstr) == -1 ? false : true;
//    }
//
//    /**
//     * 编码
//     *
//     * @param source
//     * @return
//     */
//    private static String urlEncode(String source) {
//        String result = source;
//        try {
//            result = java.net.URLEncoder
//                    .encode(source, HttpUtil.ENCODE_CHARSET);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//


}
