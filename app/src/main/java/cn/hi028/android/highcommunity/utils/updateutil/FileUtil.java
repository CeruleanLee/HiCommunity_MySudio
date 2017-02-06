package cn.hi028.android.highcommunity.utils.updateutil;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lee_yting on 2016/10/9 0009.
 * 说明：
 */
public class FileUtil {
    public static File updateDir = null;
    public static File updateFile = null;
    /***********保存升级APK的目录***********/
    public static final String AppDir = "hishequ";

    public static boolean isCreateFileSucess;

    /**
     * 方法描述：createFile方法
     * @return
     * @see FileUtil
     */
    public static void createFile(String app_name) {

        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            isCreateFileSucess = true;
            updateDir = new File(Environment.getExternalStorageDirectory()+ "/" + AppDir +"/");
            updateFile = new File(updateDir + "/" + app_name + ".apk");
            Log.d("~~~~~~","创建的文件名："+updateFile);
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    isCreateFileSucess = false;
                    e.printStackTrace();
                }
            }

        }else{
            isCreateFileSucess = false;
        }
    }
}
