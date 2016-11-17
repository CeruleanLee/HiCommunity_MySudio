package cn.hi028.android.highcommunity.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import net.duohuo.dhroid.activity.ActivityTack;

import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * Created by Lee_yting on 2016/11/17 0017.
 * 说明：UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * static  final String Tag="CrashHandler--->";
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    static final String Tag = "CrashHandler--->";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mExceptionHandler;

    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext;

    //保证只有一个CrashHandler实例
    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        Log.e(Tag, "获取crash 单例");
        return INSTANCE;
    }

    private Handler msgHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.e(Tag, "msgHandler  msg " + msg.what);
            Log.e(Tag, "msgHandler  msg2  " + msg.arg1);


                    Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                    showCrashDialog();
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_not_network), Toast.LENGTH_SHORT).show();



        }

    };

        public void init(Context context) {
            mContext = context;
            //获取系统默认的UncaughtException处理器
            mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            dialog2 = ECAlertDialog.buildAlert(mContext, "抱歉，程序奔溃了……即将退出", "我知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Log.e(Tag, "退出 app1");
                    ActivityTack.getInstanse().exit(mContext.getApplicationContext());
//                System.exit(0);
                    Log.e(Tag, "退出 app2");
                }
            });
            //设置该CrashHandler为程序的默认处理器
            Thread.setDefaultUncaughtExceptionHandler(this);
        }


        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (!handleException(ex) && mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(thread, ex);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppManager.getAppManager().finishAllActivity();
            }
        }
    ECAlertDialog dialog2;



    private boolean handleException(Throwable throwable) {
            if (throwable == null) {
                return false;
            }
            new Thread(new Runnable() {

                @Override
                public void run() {
                Looper.prepare();
                Log.e(Tag, "Looper ");
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                        ActivityTack.getInstanse().exit(mContext.getApplicationContext());
////                showCrashDialog();
//                ECAlertDialog dialog2 = ECAlertDialog.buildAlert(mContext, "抱歉，程序奔溃了……即将退出", "我知道了", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Log.e(Tag, "退出 app1");
//                        ActivityTack.getInstanse().exit(mContext.getApplicationContext());
////                System.exit(0);
//                        Log.e(Tag, "退出 app2");
//                    }
//                });
//                dialog2.setTitle("提示");
//                dialog2.show();
                Log.e(Tag, "Looper  2 ");
                Looper.loop();
//                    Log.e(Tag, "msgHandler  1 ");
//                    msgHandler.sendEmptyMessage(0);
////                    Message msg = msgHandler.obtainMessage();
////                    msg.arg1 = 0;
////                    msgHandler.sendMessage(msg);
//                    Log.e(Tag, "msgHandler  2 ");

                }
            }) {
            }.start();

            return true;
        }


        /****
         * 重启app
         */
        public void restartApp() {
            Log.e(Tag, "重启app");
            ActivityTack.getInstanse().exit(mContext.getApplicationContext());
            Log.e(Tag, "重启app ok");

//        Intent intent = new Intent(mContext, WelcomeAct.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        }

        /****
         * crash弹窗
         */
        public void showCrashDialog() {
            Log.e(Tag, "显示奔溃弹窗");
//        Toast.makeText(this, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
//        HighCommunityUtils.GetInstantiation().ShowToast("很抱歉,程序出现异常,即将退出.", 0);
//        restartApp();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
//        AppManager.getAppManager().finishAllActivity();
            ECAlertDialog dialog2 = ECAlertDialog.buildAlert(mContext.getApplicationContext(), "抱歉，程序奔溃了……即将退出", "我知道了", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Log.e(Tag, "退出 app1");

                    ActivityTack.getInstanse().exit(mContext.getApplicationContext());
//                System.exit(0);
                    Log.e(Tag, "退出 app2");
                }
            });
            dialog2.setTitle("提示");
            dialog2.show();
        }
    }
