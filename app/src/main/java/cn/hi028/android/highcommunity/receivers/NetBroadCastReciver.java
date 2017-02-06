package cn.hi028.android.highcommunity.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2017/2/4 0004
 */
public class NetBroadCastReciver extends BroadcastReceiver {
    /**
     * 只有当网络改变的时候才会 经过广播。
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        //判断wifi是打开还是关闭
        if(WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())){ //此处无实际作用，只是看开关是否开启
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;

                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        //此处是主要代码，
        //如果是在开启wifi连接和有网络状态下
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(NetworkInfo.State.CONNECTED==info.getState()){
                //连接状态
                Log.e("pzf", "有网络连接");
                //执行后续代码
                //new AutoRegisterAndLogin().execute((String)null);
                //ps:由于boradCastReciver触发器组件，他和Service服务一样，都是在主线程的，所以，如果你的后续操作是耗时的操作，请new Thread获得AsyncTask等，进行异步操作
            }else{
                Log.e("pzf", "无网络连接");
            }
        }

    }

}