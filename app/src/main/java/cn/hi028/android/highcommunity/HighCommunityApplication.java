package cn.hi028.android.highcommunity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.don.tools.BpiHttpClient;
import com.don.tools.MyImageDownloader;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.BaseApplication;

import org.xutils.x;

import java.lang.reflect.Field;

import cn.hi028.android.highcommunity.bean.UserInfoBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import cn.jpush.android.api.JPushInterface;

/**
 * @功能：嗨社区application<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/7<br>
 */
public class HighCommunityApplication extends BaseApplication implements
        BDLocationListener {
    public LocationClient mLocationClient;
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
    @Override
    public void onCreate() {
        LogUtils.d("~~~初始化App");
        super.onCreate();

        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush

        app = this;
        // EducationCrachHandler.getInstance().init(this);
        // NativeParametersFunc.c(this, getPackageName(), DOMAINNAME,
        // DOMAINNAME,
        // ":8888");
        TypeFaceYaHei = Typeface.createFromAsset(getAssets(), "ltjianhei.ttf");
        try
        {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, TypeFaceYaHei);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
//		Fresco.in

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
        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationClient.registerLocationListener(this);
        x.Ext.init(this);
        isAliPayInStalled=isAliPayInStalled();
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
     * @return
     */

    public static boolean isAliPayInStalled(){
        boolean isAliPayInStalled=false;
//        PackageManager pm=app.getPackageManager();
//        List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
//        for (PackageInfo packageInfo : list2) {
//            String appName=packageInfo.applicationInfo.loadLabel(app.getPackageManager()).toString();
//            String packageName=packageInfo.packageName;
//            if(packageName.equals("com.eg.android.AlipayGphone")){
//                isAliPayInStalled=true;
//                return isAliPayInStalled;
//            }
//        }
        return isAliPayInStalled;
    }
    static PopupWindow waitPop;
    public static void showDialog(final View v){

        ECAlertDialog dialog = ECAlertDialog.buildAlert(app, "请先安装支付宝", "确定", "取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(app, v, Gravity.CENTER);
                waitPop.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    //----检测更新--------------------------------------------------------






}
