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

}
