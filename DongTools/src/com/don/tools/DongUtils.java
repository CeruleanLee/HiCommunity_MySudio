package com.don.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.dongtools.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dong
 * @category dong工具类
 */
public class DongUtils {
    public static DongUtils mDongUtils = null;
    // 平板检测标记
    public static boolean isTabletDevice = false;
    // 屏幕宽高密度
    public static int DisplayMetricsWidth = 480;
    public static int DisplayMetricsHeight = 800;
    public static float DisplayMetricsDensity = 120;
    public static float DisplayMetricsScaledDensity = 1;
    // 分辨率换算
    public static float HeightScale = 1, WidthScale = 1;
    public static int BehindOffset = 220;
    // ApplicationContext
    public static Context mApplicationContext = null;
    // 内容提示
    public String mRequestTimeOut = null;
    public String mRequestEnd = null;
    // PopupWindow
    public static PopupWindow mPopupWindow = null;
    // 当前日期0点
    public static long mNowZero = 0;
    private Toast mToast;

    public enum ActivityState {
        ACTVITYTOP, ACTIVITYUNFIND, ACTIVITYBACK
    }

    /**
     * @return
     * @author dong
     * @category 单例，获得初始化
     */
    public static DongUtils GetInstantiation() {
        if (mDongUtils == null) {
            mDongUtils = new DongUtils();
            mNowZero = TimeFormat.TimedateFormat_DATEParse(TimeFormat
                    .getTheTime());
        }
        return mDongUtils;
    }

    /**
     * @author dong
     * @category 文件夹初始化
     */
    public void initFileDir(String name) {
        File sdDir = Environment.getExternalStorageDirectory();
        DongConstants.SDCARDAVATAR = sdDir.getAbsolutePath() + "/" + name;
        // Constants.SDCARDPDF = Environment.getExternalStorageDirectory()
        // .getPath() + "/EductionPDF";
        File file = new File(DongConstants.SDCARDAVATAR);
        if (!file.exists()) {
            file.mkdirs();
        }
        // file = new File(Constants.SDCARDPDF);
        // if (!file.exists()) {
        // file.mkdir();
        // }
    }

    /**
     * @param mContext
     * @author dong
     * @category 设置ApplicationContext 便于全局Toast等的初始化
     */
    public void SetApplicationContext(Context mContext) {
        this.mApplicationContext = mContext;
    }

    /**
     * @param mContext
     * @author dong
     * @category 初始图片下载管理器
     */
    public void initUniveralImage(Context mContext, BaseImageDownloader mBaseImageDownloader) {
        File cacheDir = StorageUtils.getCacheDirectory(mContext);// 缓存文件目录
        // 默认下载Options
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.ic_stub) // resource or
                // drawable
                .showImageForEmptyUri(0)
                // resource or drawable
                // .showImageOnFail(R.drawable.ic_error) // resource or drawable
                // .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(200)
                .cacheInMemory(true) // default
//                .cacheOnDisc(true) // default
                .cacheOnDisk(true)
                // .preProcessor(...)
                // .postProcessor(...)
                // .extraForDownloader(...)
                .considerExifParams(true) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                // .decodingOptions(...)
                .displayer(new SimpleBitmapDisplayer()) // default
//                .displayer(new FadeInBitmapDisplayer(100))
                // .handler(new Handler()) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                // .memoryCacheExtraOptions(480, 800) // default = device screen
                // dimensions
                // .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(15)
                // .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(6 * 1024 * 1024) // necessary
                .diskCacheSize(50 * 1024 * 1024)
                .imageDownloader(mBaseImageDownloader)
                // in
                // common
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * @param mContext
     * @author dong
     * @category 初始图片下载管理器
     */
    public void initUniveralImage(Context mContext) {
        File cacheDir = StorageUtils.getCacheDirectory(mContext);// 缓存文件目录
        // 默认下载Options
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.ic_stub) // resource or
                // drawable
                .showImageForEmptyUri(0)
                // resource or drawable
                // .showImageOnFail(R.drawable.ic_error) // resource or drawable
                // .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(200)
                .cacheInMemory(true) // default
                .cacheOnDisc(true) // default
                // .preProcessor(...)
                // .postProcessor(...)
                // .extraForDownloader(...)
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
//                .decodingOptions(android.graphics.BitmapFactory.Options)
                .displayer(new SimpleBitmapDisplayer()) // default
                // .handler(new Handler()) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                // .memoryCacheExtraOptions(480, 800) // default = device screen
                // dimensions
                // .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(6 * 1024 * 1024) // necessary
                // in
                // common
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * @param url
     * @author dong
     * @category 清除指定Url的本地缓存
     */
    public void ClearTheBitmapCache(String url) {
//		MemoryCacheUtil.removeFromCache(url, ImageLoader.getInstance()
//				.getMemoryCache());
//		DiscCacheUtil.removeFromCache(url, ImageLoader.getInstance()
//				.getDiscCache());
    }

    /**
     * @param mContext
     * @author dong
     * @category 获得屏幕信息，用于分辨率转换
     */
    public void getScreenInfo(Context mContext) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            DisplayMetricsWidth = dm.widthPixels;
            DisplayMetricsHeight = dm.heightPixels;
            DisplayMetricsDensity = dm.density;
            DisplayMetricsScaledDensity = dm.scaledDensity;
            if (DisplayMetricsWidth > DisplayMetricsHeight) {
                WidthScale = (float) (DisplayMetricsWidth / 800.0);
                HeightScale = (float) (DisplayMetricsHeight / 480.0);
            } else {
                WidthScale = (float) (DisplayMetricsWidth / 480.0);
                HeightScale = (float) (DisplayMetricsHeight / 800.0);
            }
            BehindOffset = DisplayMetricsWidth - dip2px(BehindOffset);
        } catch (Exception e) {
            Debug.verbose("获取设备分辨率出错" + e.toString());
        }
    }

    /**
     * @author dong
     * @category 网络请求超时Toast提示
     */
    public void ShowTimeOut() {
        if (mApplicationContext == null)
            return;
        if (mRequestTimeOut == null) {
            mRequestTimeOut = mApplicationContext.getResources().getString(
                    R.string.RequestTimeout);
        }
        Toast.makeText(mApplicationContext, mRequestTimeOut, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * @author dong
     * @category 数据加载到最后一页Toast提示
     */
    public void ShowEnd() {
        if (mApplicationContext == null)
            return;
        if (mRequestEnd == null) {
            mRequestEnd = mApplicationContext.getResources().getString(
                    R.string.RequestEnd);
        }
        Toast.makeText(mApplicationContext, mRequestEnd, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * @author dong
     * @category 自动定义网络请求超时内容Toast提示
     */
    public void ShowTimeOut(String Title) {
        if (mApplicationContext == null)
            return;
        Toast.makeText(mApplicationContext, Title, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param message
     * @param time
     * @author dong
     * @category 全局Toast提示
     */
    public void ShowToast(String message, int time) {
        if (mApplicationContext == null)
            return;
        if (mToast == null) {
            mToast = Toast.makeText(mApplicationContext, message, time);
        } else {
            mToast.setText(message);
            mToast.setDuration(time);
        }
        mToast.show();
    }

    /**
     * @author dong
     * @category 全局Toast提示
     */
    public void ShowShouldLogin() {
        if (mApplicationContext == null)
            return;
        if (mRequestEnd == null) {
            mRequestEnd = mApplicationContext.getResources().getString(
                    R.string.ShouldLogin);
        }
        Toast.makeText(mApplicationContext, mRequestEnd, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * @param context
     * @param parent
     * @param gravity
     * @author dong
     * @category 弹出框提示
     */
    public PopupWindow ShowWaittingPopupWindow(Context context, View parent,
                                               int gravity) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.waitting_popupwindow, null, false);
        mPopupWindow = new PopupWindow(view, dip2px(95), dip2px(95), true);
        mPopupWindow.setOutsideTouchable(false);
        TextView mTextView = (TextView) view.findViewById(R.id.Window_message);
        mTextView.setText("加载中..");
        mPopupWindow.showAtLocation(parent, gravity, 0, 0);
        return mPopupWindow;
    }

    /**
     * @param context
     * @param parent
     * @param gravity
     * @param textString
     * @author dong
     * @category 弹出框提示
     */
    public PopupWindow ShowPopupWindow(Context context, View parent,
                                       int gravity, String textString) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.waitting_popupwindow, null, false);
        mPopupWindow = new PopupWindow(view, 300, 300, true);
        mPopupWindow.setOutsideTouchable(false);
        TextView mTextView = (TextView) view.findViewById(R.id.Window_message);
        mTextView.setText(textString);
        mPopupWindow.showAtLocation(parent, gravity, 0, 0);
        return mPopupWindow;
    }

    /**
     * @author dong
     * @category 关闭弹出框
     */
    public void ErrorPopupWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * @param email
     * @return
     * @author dong
     * @category 验证邮箱格式
     */
    public boolean isEmail(final String email) {
        Pattern p1 = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p1.matcher(email);
        boolean isExist = m.matches();
        if (isExist) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     * @author dong
     * @category 分享功能
     */
    public void shareMsg(Context context, String activityTitle,
                         String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/png");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * @param context
     * @return
     * @author dong
     * @category 判断是否是平板 (官方验证)
     */
    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * @return
     * @author linxd
     * @category 判断物理尺寸大小
     */
    public boolean isTabletDevice(Context mContext) {
        if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
            // test screen size, use reflection because isLayoutSizeAtLeast is
            // only available since 11
            Configuration con = mContext.getResources().getConfiguration();
            try {
                Method mIsLayoutSizeAtLeast = con.getClass().getMethod(
                        "isLayoutSizeAtLeast", int.class);
                Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con,
                        0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
                return r;
            } catch (Exception x) {
                x.printStackTrace();
                return false;
            }
        } else {
            double size = isTabletDeviceByResolution();
            if (size > 6) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * @return
     * @author linxd
     * @category 屏幕分辩率反算物理尺寸大小
     */
    public double isTabletDeviceByResolution() {
        double diagonal = Math.sqrt(Math.pow(DisplayMetricsWidth, 2)
                + Math.pow(DisplayMetricsHeight, 2));
        double size = diagonal / (DisplayMetricsDensity * 160);
        return size;
    }

    /**
     * @return
     * @author dong
     * @category 获取外置SD卡路径
     */
    public String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                Log.i("DonUtil:getSDCardPath", lineStr);
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    Log.e("DonUtil:getSDCardPath", "命令执行失败!");
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            Log.e("DonUtil:getSDCardPath", e.toString());
            return Environment.getExternalStorageDirectory().getPath();
        }

        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 像素转sp
     **/
    public int px2sp(float pxValue) {
        return (int) (pxValue / DisplayMetricsScaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px
     */
    public int sp2px(float spValue) {
        return (int) (spValue * DisplayMetricsScaledDensity + 0.5f);
    }

    /**
     * dip转px
     */
    public int dip2px(float dipValue) {
        return (int) (dipValue * DisplayMetricsDensity + 0.5f);
    }

    /**
     * px转dip
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / DisplayMetricsDensity + 0.5f);
    }

    /**
     * @param filepath
     * @author dong
     * @category 解析获取MimeType
     */
    public String getTheMimeType(String filepath) {
        String mFileType = null;
        int dotIndex = filepath.lastIndexOf(".");
        if (dotIndex >= 0) {
            if (dotIndex + 1 != filepath.length()) {
                String type = filepath.substring(dotIndex + 1,
                        filepath.length());
                MimeTypeMap mTypeMap = MimeTypeMap.getSingleton();
                mFileType = mTypeMap.getMimeTypeFromExtension(type);
            }
        }
        return mFileType;
    }

    /**
     * @param context
     * @return
     * @author dong
     * @category 获得当前网络状态
     */
    public int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
        NetworkInfo WifiInfo = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        State state = WifiInfo.getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            DongConstants.NETWORD_STATE = DongConstants.NETWORD_WIFI;
            return DongConstants.NETWORD_WIFI;
        }

        // 3G
        NetworkInfo m3GInfo = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        state = m3GInfo.getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            DongConstants.NETWORD_STATE = DongConstants.NETWORD_MOBILE;
            return DongConstants.NETWORD_MOBILE;
        }
        DongConstants.NETWORD_STATE = DongConstants.NETWORD_NONE;
        DongUtils.GetInstantiation().ShowToast("当前没有网络连接", 0);
        return DongConstants.NETWORD_NONE;
    }

    /**
     * @param time  当前消息时间
     * @param btime 前一条消息时间
     * @return
     * @author dong
     * @category 聊天信息时间格式化
     */
    public String ComputeTime(String time, String btime) {
        if (time == null)
            return null;
        long mTime = Long.valueOf(time);
        long bTime = Long.valueOf(btime);
        if ((mNowZero / 1000 - mTime) / 60 > 0) {
            // 相隔一天显示日期+时间
            if ((mTime - bTime) / 60 > 5)
                return TimeFormat.TimedateFormat_shortTime(mTime);
            else
                return null;
        } else if ((mTime - bTime) / 60 > 5) {
            // 相隔5分钟以上显示时间
            return TimeFormat.TimedateFormat_HH(mTime * 1000);
        } else {
            return null;
        }
    }

    /**
     * @return 当前应用的版本号
     * @author dong
     * @category 获取版本号
     */
    public String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @author dong
     * @category 清理缓存
     */
    public void ClearCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiscCache();
        File mAvatarPath = new File(DongConstants.SDCARDAVATAR);
        if (mAvatarPath.exists()) {
            if (mAvatarPath.isDirectory()) {
                if (mAvatarPath.list().length > 0) {
                    for (File file : mAvatarPath.listFiles()) {
                        file.delete();
                    }
                }
            }
            mAvatarPath.delete();
        }
    }

    /**
     * @param context
     * @return
     * @author dong
     * @category activity状态
     */
    public ActivityState getActivityState(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        String packName = context.getPackageName();
        Debug.verbose("dong packageName:" + packName);
        if (activityManager == null)
            return ActivityState.ACTIVITYUNFIND;
        List<RunningAppProcessInfo> processList = activityManager
                .getRunningAppProcesses();
        for (int i = 0; i < processList.size(); i++) {
            RunningAppProcessInfo process = processList.get(i);
            if (process.processName.equals(packName)) {
                if (process.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return ActivityState.ACTVITYTOP;
                } else {
                    return ActivityState.ACTIVITYBACK;
                }
            }
        }
        return ActivityState.ACTIVITYUNFIND;
    }


    /**
     * @param context
     * @param ServiceName
     * @author dong 检测service是否存在
     */
    public void CheckTheServiceIsRunning(Context context, String ServiceName) {
        // 检查Service状态
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (ServiceName.equals(service.service.getClassName())) {
                Debug.verbose("dong fuck :", "service 已存在");
            } else {
                Debug.verbose("dong fuck :", "service 不存在 启动中。。。。");
                Intent mIntent = new Intent(ServiceName);
                context.startActivity(mIntent);
            }
        }
    }

}
