package com.don.tools;

import android.os.AsyncTask;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author dong 2014年1月4日 15:25:51
 * @category 网络请求解析器
 */
public class BpiHttpHandler extends AsyncDefaultHttppHandler {
    private final String TAG_SUCCESS = "success";
    private final String TAG_CONTENT = "data";
    private final String TAG_MSG = "msg";
    private final String TAG_CODE = "code";
    private final int ID_SUCESS = 1;
    private String METHODETAG = "";
    AsyncTask<String, String, Object> mAsyncTask = null;

    private IBpiHttpHandler mhttpHandler = null;

    public BpiHttpHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BpiHttpHandler(IBpiHttpHandler httpHandler) {
        super();
        mhttpHandler = httpHandler;
        initAsyncTask();
    }

    public BpiHttpHandler(IBpiHttpHandler httpHandler, String MethodTAG) {
        super();
        mhttpHandler = httpHandler;
        METHODETAG = MethodTAG;
        initAsyncTask();
    }

    @Override
    public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
        // super.onFailure(arg0, arg1);
        if (this.mhttpHandler != null) {
            mhttpHandler.cancleAsyncTask();
        }
    }

    @Override
    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
        if (this.mhttpHandler != null) {
            try {
                String str = "";
                if (arg2 != null) {
                    str = new String(arg2);
                    Debug.verbose(DongConstants.EDUCATIONHTTPTAG, METHODETAG + ":"
                            + str);
                } else {
                    Debug.verbose(DongConstants.EDUCATIONHTTPTAG, METHODETAG + ":"
                            + arg1);
                }
                JSONObject jsonSuccess = new JSONObject(str);
                boolean isSuccess = jsonSuccess.getBoolean(TAG_SUCCESS);
                int code = jsonSuccess.getInt(TAG_CODE);
                String msg = jsonSuccess.getString(TAG_MSG);
                if (msg.contains("请先登陆")) {
                    this.mhttpHandler.shouldLoginAgain(true,msg);
//                    HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
//                    HighCommunityApplication.toLoginAgain(getActivity());

                } else {

//                    this.mhttpHandler.shouldLogin(false);
                    if (isSuccess) {
                        String data = "";
                        if (jsonSuccess.has(TAG_CONTENT)) {
                            data = jsonSuccess.getString(TAG_CONTENT);
                            Debug.verbose(DongConstants.EDUCATIONHTTPTAG,
                                    "resolve data String :" + data);
                            if (mAsyncTask != null && !mAsyncTask.isCancelled()) {
                                mAsyncTask.execute(data);
                            } else {
                                mhttpHandler.cancleAsyncTask();
                                mAsyncTask = null;
                            }
                        } else {
                            this.mhttpHandler.onSuccess(msg);
                        }
                    } else {
                        this.mhttpHandler.onError(code, msg);
                    }
                }
            } catch (JSONException e) {
                Debug.verbose(DongConstants.EDUCATIONHTTPTAG,
                        "解析JSON出错:" + e.toString());
            }
        }
    }

    public void initAsyncTask() {
        mAsyncTask = null;
        mAsyncTask = new AsyncTask<String, String, Object>() {
            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                if (isCancelled()) {
                    mhttpHandler.cancleAsyncTask();
                    return;
                }
                mhttpHandler.onSuccess(result);
            }

            @Override
            protected Object doInBackground(String... params) {
                if (isCancelled()) {
                    mhttpHandler.cancleAsyncTask();
                    return null;
                }
                return mhttpHandler.onResolve(params[0]);
            }
        };
        if (mhttpHandler != null)
            mhttpHandler.setAsyncTask(mAsyncTask);
    }

    public static BpiHttpHandler getInstance(IBpiHttpHandler httpHandler) {
        return new BpiHttpHandler(httpHandler);
    }

    /**
     * 带方法名进行区分
     **/
    public static BpiHttpHandler getInstance(IBpiHttpHandler httpHandler,
                                             String methodTag) {
        return new BpiHttpHandler(httpHandler, methodTag);
    }

    public static BpiHttpHandler getInstance(IBpiHttpHandler bpiHandler,
                                             IHttpHandler httpHandler) {
        BpiHttpHandler handler = getInstance(bpiHandler);
        handler.setHttpHandler(httpHandler);
        return handler;
    }

    public interface IBpiHttpHandler {

        public void onError(int id, String message);

        public void onSuccess(Object message);

        public Object onResolve(String result);

        /**
         * 中断操作 dong
         **/
        public void setAsyncTask(AsyncTask asyncTask);

        /**
         * 中断操作 dong
         **/
        public void cancleAsyncTask();

        /**
         * 判断是否跳转登陆界面
         * @param isShouldLogin
         */
        public void shouldLogin(boolean isShouldLogin);
        /**
         * 判断是否跳转登陆界面
         * @param isShouldLogin
         */
        public void shouldLoginAgain(boolean isShouldLogin,String msg);
    }
}
