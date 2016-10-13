package cn.hi028.android.highcommunity.utils.updateutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by LG on 2016/10/4.
 * Tips:
 */
public class ApkUtils {
    /**
     * 安装APk
     * @param file
     */
    public static void installApk(Context context, File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    /**
     * 卸载APk
     * @param packName
     */
    public static void uninstallApk(Context context, String packName){
        Uri uri = Uri.parse("package:"+packName);//获取删除包名的URI
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);//设置我们要执行的卸载动作
        intent.setData(uri);//设置获取到的URI
        context.startActivity(intent);
    }
}
