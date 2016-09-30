package net.duohuo.dhroid;

import android.app.Application;

import net.duohuo.dhroid.util.LogUtil;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDhroid();

    }

    void initDhroid() {
        Const.netadapter_page_no = "p";
        Const.netadapter_step = "step";
        Const.response_data = "data";
        Const.netadapter_step_default = 7;
        Const.netadapter_json_timeline = "pubdate";
        Const.DATABASE_VERSION = 20;
        Const.net_pool_size = 30;
        Const.net_error_try = true;
        Dhroid.init(this);
        LogUtil.e("[ECApplication] onCreate");
        // initImageLoader();
    }

}
