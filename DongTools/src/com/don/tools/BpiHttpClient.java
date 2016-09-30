package com.don.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * @author dong 2014年1月4日 15:25:24
 * @category 管理网络连接
 */
public class BpiHttpClient {

    /**
     * @author linxd
     * @category post数据，默认解析接口 {@link AsyncDefaultHttppHandler}
     **/
    public static void post(String url, HashMap<String, String> hmData) {
        RequestParams param = new RequestParams(hmData);
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "dong params:" + param.toString());
        getInstance().post(url, param, AsyncDefaultHttppHandler.getInstance());
    }

    /**
     * @author linxd
     * @category 专用上传接口用于上传文件
     */
    public static void postFile(String url, HashMap<String, String> hmData,
                                IBpiHttpHandler httpHandler, String fileTag, String file) {
        RequestParams param = new RequestParams(hmData);
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "dong params:" + param.toString());
        try {
            if (file != null) {
                param.put(fileTag, new File(file));
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getInstance().post(url, param, BpiHttpHandler.getInstance(httpHandler));
    }

    /**
     * @author linxd
     * @category post数据，自定义解析接口 {@link AsyncHttpResponseHandler}
     **/
    public static void post(String url, HashMap<String, String> hmData,
                            AsyncHttpResponseHandler handler) {
        RequestParams param = new RequestParams(hmData);
        getInstance().post(url, param, handler);
    }

    /**
     * @author linxd
     * @category post数据，自定义解析接口 {@link BpiHttpHandler}
     **/
    public static void post(String url, HashMap<String, String> hmData,
                            IBpiHttpHandler httpHandler) {
        RequestParams param = new RequestParams(hmData);
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url + "   ling params:" + param.toString());
        getInstance().post(url, param, BpiHttpHandler.getInstance(httpHandler));
    }

    /**
     * @author linxd
     * @category post数据，自定义解析接口,带方法名便于区别寻找 {@link BpiHttpHandler}
     **/
    public static void post(String url, HashMap<String, String> hmData,
                            IBpiHttpHandler httpHandler, String Methods) {
        RequestParams param = new RequestParams(hmData);
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "ling params:" + param.toString());
        getInstance().post(url, param,
                BpiHttpHandler.getInstance(httpHandler, Methods));
    }

    /**
     * @author linxd
     * @category post数据, 数组参数，自定义解析接口 {@link BpiHttpHandler}
     **/
    public static void post(String url, HashMap<String, String> hmData,
                            String ArrayKey, String[] StringArray,
                            IBpiHttpHandler httpHandler, String Methods) {
        RequestParams param = new RequestParams(hmData);
        param.put(ArrayKey, StringArray);
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "dong params:" + param.toString());
        getInstance().post(url, param, BpiHttpHandler.getInstance(httpHandler, Methods));
    }

    /**
     * @author linxd
     * @category get数据，默认解析接口 {@link AsyncDefaultHttppHandler}
     **/
    public static void get(String url, HashMap<String, String> hmData) {
        RequestParams param = new RequestParams(hmData);
        getInstance().get(url, param, AsyncDefaultHttppHandler.getInstance());
    }

    /**
     * @author linxd
     * @category post数据，自定义解析接口,带方法名便于区别寻找 {@link BpiHttpHandler}
     **/
    public static void get(String url, HashMap<String, String> hmData,
                           IBpiHttpHandler httpHandler) {
        RequestParams param = new RequestParams(hmData);
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "ling params:" + param.toString());
        getInstance().get(url, param,
                BpiHttpHandler.getInstance(httpHandler));
    }

    /**
     * @author linxd
     * @category get数据，自定义解析接口 {@link AsyncHttpResponseHandler}
     **/
    public static void get(String url, HashMap<String, String> hmData,
                           AsyncHttpResponseHandler handler) {
        RequestParams param = new RequestParams(hmData);
        getInstance().get(url, param, handler);
    }

    /**
     * @author linxd
     * @category post数据，数据放入header中，默认解析接口 {@link AsyncDefaultHttppHandler}
     **/
    public static void postHeader(String url, HashMap<String, String> hmData) {
        AsyncHttpClient asyncClient = new AsyncHttpClient();
        for (String key : hmData.keySet()) {
            String value = hmData.get(key);
            asyncClient.addHeader(key, value);
        }
        asyncClient.post(url, AsyncDefaultHttppHandler.getInstance());
    }

    /**
     * @author linxd
     * @category post数据，数据放入header中，默认解析接口 {@link AsyncDefaultHttppHandler}
     **/
    public static void postHeader(String url, HashMap<String, String> hmData,
                                  AsyncDefaultHttppHandler handler) {
        AsyncHttpClient asyncClient = new AsyncHttpClient();
        for (String key : hmData.keySet()) {
            String value = hmData.get(key);
            asyncClient.addHeader(key, value);
        }
        asyncClient.post(url, handler);
    }

    /**
     * @author linxd
     * @category get数据，数据放入header中，默认解析接口 {@link AsyncDefaultHttppHandler}
     **/
    public static void getHeader(String url, HashMap<String, String> hmData) {
        AsyncHttpClient asyncClient = new AsyncHttpClient();
        for (String key : hmData.keySet()) {
            String value = hmData.get(key);
            asyncClient.addHeader(key, value);
        }

        asyncClient.get(url, AsyncDefaultHttppHandler.getInstance());
    }

    /**
     * @author linxd
     * @category get数据，数据放入header中，默认解析接口 {@link AsyncDefaultHttppHandler}
     **/
    public static void getHeader(String url, HashMap<String, String> hmData,
                                 AsyncDefaultHttppHandler handler) {
        AsyncHttpClient asyncClient = new AsyncHttpClient();
        for (String key : hmData.keySet()) {
            String value = hmData.get(key);
            asyncClient.addHeader(key, value);
        }
        asyncClient.get(url, handler);
    }

    private static class SingletonHolder {
        public static final AsyncHttpClient INSTANCE = new AsyncHttpClient();
    }

    /**
     * @author linxd 初始化Cookeis
     */
    public static PersistentCookieStore mPersistentCookieStore = null;

    public static void initCookies(Context mContext) {
        mPersistentCookieStore = new PersistentCookieStore(mContext);
        getInstance().setCookieStore(mPersistentCookieStore);
    }

    public static AsyncHttpClient getInstance() {
        // 10秒超时 dong
        SingletonHolder.INSTANCE.setTimeout(20 * 1000);
        return SingletonHolder.INSTANCE;
    }
}
