package cn.hi028.android.highcommunity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.don.tools.BpiHttpClient;
import com.don.tools.MyImageDownloader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;

import net.duohuo.dhroid.BaseApplication;

import org.androidannotations.annotations.App;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.WelcomeAct;
import cn.hi028.android.highcommunity.bean.UserInfoBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.CrashHandler;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.MyLocationListener;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.OkHttpClient;

/**
 * @功能：嗨社区application<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/7<br>
 */
public class HighCommunityApplication extends BaseApplication implements
        BDLocationListener {
    public static LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    public static boolean isLogOut = false;
    public static SharedPreferences share;
    public static int SoftKeyHight = 0;
    public static UserInfoBean mUserInfo = new UserInfoBean();
    HighCommunityUtils mDongUtils = null;
    public static Typeface TypeFaceYaHei;

    public static HighCommunityApplication getApp() {
        return app;
    }

    static HighCommunityApplication app;
    static boolean isAliPayInStalled;
static  final String Tag="HiApplication->";

    @Override
    public void onCreate() {
        Log.d(Tag,"初始化App");
        super.onCreate();

        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        /**第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         输出详细的Bugly SDK的Log；
         每一条Crash都会被立即上报；
         自定义日志将会在Logcat中输出。
         建议在测试阶段建议设置成true，发布时设置为false。*/
        CrashReport.initCrashReport(getApplicationContext(), "63e6f78cb5", false);
        /**自定义的crash 处理**/
        Log.d(Tag,"~~~初始化CrashHandler");
        CrashHandler crashHandler= CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        app = this;
        // EducationCrachHandler.getInstance().init(this);
        // NativeParametersFunc.c(this, getPackageName(), DOMAINNAME,
        // DOMAINNAME,
        // ":8888");
        TypeFaceYaHei = Typeface.createFromAsset(getAssets(), "ltjianhei.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, TypeFaceYaHei);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Fresco.initialize(this);
        RongIM.init(this);
        mDongUtils = HighCommunityUtils.GetInstantiation();
        BpiHttpClient.initCookies(this);
        HighCommunityUtils.GetInstantiation().SetApplicationContext(this);
        Constacts.APPVERSION = mDongUtils.getVersion(getApplicationContext());
        mDongUtils.SetApplicationContext(getApplicationContext());
        mDongUtils.getScreenInfo(getApplicationContext());
        mDongUtils.initUniveralImage(getApplicationContext(),
                new MyImageDownloader(getApplicationContext(),
                        BpiHttpClient.mPersistentCookieStore));
        mDongUtils.initSoundVibrator(getApplicationContext());
        mDongUtils.initFileDir("hishequ");
        // mDongUtils.getNetworkState(this);
        share = getSharedPreferences("APPInformation", 0);
        //定位init
        mLocationClient = new LocationClient(this.getApplicationContext()); //声明LocationClie
        mLocationClient.registerLocationListener(this);//注册监听函数
        x.Ext.init(this);
        isAliPayInStalled = isAliPayInStalled();
//        Toast.makeText(getApplicationContext(), "是否安装支付宝：" + isAliPayInStalled, Toast.LENGTH_SHORT).show();

        Log.d(Tag,"---是否安装支付宝：" + isAliPayInStalled);
        /**配置okhttp**/
//       new UpdateUtil(getApplicationContext()).checkUpdate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }



    public static void SaveUser() {
        if (mUserInfo != null) {
            share.edit().putInt("USERID", mUserInfo.getId()).commit();
            share.edit().putInt("VILLAGEID", mUserInfo.getV_id()).commit();
        }
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        Constacts.location = location;
    }

    /**
     * 判断支付宝是否安装
     *
     * @return
     */

    public static boolean isAliPayInStalled() {
        boolean isAliPayInStalled = false;
        PackageManager pm = app.getPackageManager();

        List<PackageInfo> list2 = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : list2) {
            String appName = packageInfo.applicationInfo.loadLabel(app.getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            if (packageName.equals("com.eg.android.AlipayGphone")) {
                isAliPayInStalled = true;
                return isAliPayInStalled;
            }
        }
        return isAliPayInStalled;
    }

    static PopupWindow waitPop;

    public static void showDialog() {

        ECAlertDialog dialog = ECAlertDialog.buildAlert(app, "请先安装支付宝", "确定", "取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(app, v, Gravity.CENTER);
//                waitPop.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    public static int screenWidth;// 屏幕宽度，单位为px
    public static int screenHeight;// 屏幕高度，单位为px
    public static int densityDpi;// 屏幕密度，单位为dpi
    public float scale;// 缩放系数，值为 densityDpi/160
    public static float fontScale;// 文字缩放系数，同scale
    public static int bigImgWith,bigImgHeight,smallImgWith;
    public final static int SCREEN_ORIENTATION_VERTICAL = 1; // 屏幕状态：横屏
    public final static int SCREEN_ORIENTATION_HORIZONTAL = 2; // 屏幕状态：竖屏
    public static int screenOrientation = SCREEN_ORIENTATION_VERTICAL;// 当前屏幕状态，默认为竖屏

public static void toLoginAgain(Context context){
    HighCommunityApplication.isLogOut = true;
    Intent i = new Intent(context, WelcomeAct.class);
    i.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_MESSAGECENTER);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity(i);



}
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }



}
