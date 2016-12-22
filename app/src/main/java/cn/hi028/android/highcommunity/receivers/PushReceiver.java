package cn.hi028.android.highcommunity.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.don.tools.GeneratedClassUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.hi028.android.highcommunity.activity.AutonomousAct_Second;
import cn.hi028.android.highcommunity.activity.MainActivity;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush--->";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            clickNotidifation(context, bundle);
//            打开自定义的Activity
//            Intent i = new Intent(context, MainActivity.class);
//            i.putExtras(bundle);
//          i.putExtra("checkupdata",6);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            //这里在监听网络变化    我已经在四个main frag里处理了 虽然不够好但先将就用
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);


            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                int type = extraJson.optInt("type", 0);
                if (type == 0) {
                    msgIntent.putExtra(MainActivity.KEY_EXTRAS, 1);
                    context.sendBroadcast(msgIntent);
                } else if (type == 1) {

                }

            } catch (JSONException e) {

            }

        }

    }

    /**
     * 点击通知的跳转
     * @param context
     * @param bundle
     * type=0  接收评论 点赞
     * type=2 更新提示
     */
    private void clickNotidifation(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                int type = extraJson.optInt("type", -1);
                Log.e(TAG, "type--->" + type);
                if (type == 0) {//接收到评论
                    msgIntent.putExtra(MainActivity.KEY_EXTRAS, 0);
                    context.sendBroadcast(msgIntent);
                    Intent i = new Intent(context, GeneratedClassUtils.get(MenuLeftAct.class));
                    i.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_MESSAGECENTER);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                } else if (type == 1) {
                    Intent i = new Intent(context, GeneratedClassUtils.get(MenuLeftAct.class));
                    i.putExtras(bundle);
                    i.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_ORDER);
                    i.putExtra(MenuLeftAct.INTENTTAG, 2);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                } else if (type == 1024) {// 处理更新通知   极光推送更新的时候记得type=1024
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtras(bundle);
                    i.putExtra("checkupdata", 6);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }else if (type == 2) {// 自治大厅公告type=2
                    Intent i = new Intent(context, AutonomousAct_Second.class);
                    i.putExtras(bundle);
                    i.putExtra("title", TAG_NOTIC);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            } catch (JSONException e) {

            }

        }

    }
    public static final int TAG_NOTIC = 0;
//    }
}
