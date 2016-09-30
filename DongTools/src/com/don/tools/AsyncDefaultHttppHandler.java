package com.don.tools;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author dong 2014年1月4日 15:25:09
 * @category 默认解析http接口，只作错误输出和提示
 */
public class AsyncDefaultHttppHandler extends AsyncHttpResponseHandler {

    private static String ERROT_TAG = "bpi";
    private IHttpHandler httpHandler;

    public AsyncDefaultHttppHandler(IHttpHandler httpHandler) {
        super();
        this.httpHandler = httpHandler;
    }

    public AsyncDefaultHttppHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
        String str = "";
        if (arg2 != null)
            str = new String(arg2);
        Debug.verbose(ERROT_TAG, "onFailure:" + arg0);
        if (arg3 instanceof java.net.SocketException) {
            DongUtils.GetInstantiation().ShowTimeOut();
        } else if (arg3 instanceof java.net.ConnectException) {
            DongUtils.GetInstantiation().ShowTimeOut();
        } else if (str != null && arg1.equals("socket time out")) {
            DongUtils.GetInstantiation().ShowTimeOut();
        } else if (str != null && arg1.equals("can't resolve host")) {
            DongUtils.GetInstantiation().ShowTimeOut("无法连接服务器");
        }
        if (this.httpHandler != null) {
            this.httpHandler.onFailure(arg3, str);
            // this.onFailure(arg0, arg1);
        }
        // DongUtils.GetInstantiation().ErrorPopupWindow();
        // RimiApplication.ShowTimeOut(arg1);

    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        super.onFinish();
        Debug.verbose(ERROT_TAG, "Finish");
        if (this.httpHandler != null) {
            this.httpHandler.onFinish();
        }

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Debug.verbose(ERROT_TAG, "Start");
        if (this.httpHandler != null) {
            this.httpHandler.onStart();
        }

    }

    @Override
    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
        // TODO Auto-generated method stub
        // super.onSuccess(arg0, arg1);
        Debug.verbose(ERROT_TAG, "Success");
        if (this.httpHandler != null) {
            String str = "";
            if (arg2 != null)
                str = new String(arg2);
            this.httpHandler.onSuccess(arg0, str);
        }

    }

    public static AsyncDefaultHttppHandler getInstance(String url) {
        ERROT_TAG = url;
        return getInstance();

    }

    public static AsyncDefaultHttppHandler getInstance() {
        return new AsyncDefaultHttppHandler();
    }

    public static AsyncDefaultHttppHandler getInstance(IHttpHandler httpHandler) {
        return new AsyncDefaultHttppHandler(httpHandler);
    }

    public void setHttpHandler(IHttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }

    /**
     * @author tanlet<br>
     */
    public interface IHttpHandler {
        public void onFailure(Throwable arg0, String arg1);

        public void onFinish();

        public void onStart();

        public void onSuccess(int arg0, String arg1);
    }

}
