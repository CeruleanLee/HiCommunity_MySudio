package cn.hi028.android.highcommunity.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Lee_yting on 2016/11/17 0017.
 * 说明：UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler_my implements Thread.UncaughtExceptionHandler {
static  final String Tag="CrashHandler--->";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mExceptionHandler;

    //CrashHandler实例
    private static CrashHandler_my INSTANCE=new CrashHandler_my();

    private Context mContext;

    //保证只有一个CrashHandler实例
    private CrashHandler_my(){

    }

    public static CrashHandler_my getInstance(){
        Log.e(Tag,"获取crash 单例");
        return INSTANCE;
    }

    public void init(Context context){
        mContext=context;
        //获取系统默认的UncaughtException处理器
        mExceptionHandler =Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mExceptionHandler!=null){
            mExceptionHandler.uncaughtException(thread, ex);
        }else {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AppManager.getAppManager().finishAllActivity();
        }
    }

    private boolean handleException (Throwable throwable){
        if(throwable==null){
            return false;
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
//                Looper.prepare();
//                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                showCrashDialog();

//                Looper.loop();
            }
        }){
        }.start();

        return true;
    }

    /****
     * crash弹窗
     */
    public void showCrashDialog() {
        Log.e(Tag,"显示奔溃弹窗");
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
//        AppManager.getAppManager().finishAllActivity();
//        Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
//        HighCommunityUtils.GetInstantiation().ShowToast("很抱歉,程序出现异常,即将退出.", 0);
//        ECAlertDialog dialog2 = ECAlertDialog.buildAlert(mContext, "抱歉，程序奔溃了……", "我知道了", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                System.exit(0);
//            }
//        });
//        dialog2.show();
    }
}