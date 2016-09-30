package com.don.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class DongConstants {
    public static final String EDUCATIONHTTPTAG = "DongTools Http";
    public static String SDCARDAVATAR = "";
    /** 状态标识 **/
    /**
     * 网络状态标识
     **/
    public static int NETWORD_STATE;
    /**
     * 网络状态标识-无
     **/
    public static final int NETWORD_NONE = 0x000000;
    /**
     * 网络状态标识-wifi
     **/
    public static final int NETWORD_WIFI = 0x000001;
    /**
     * 网络状态标识-2g,3g
     **/
    public static final int NETWORD_MOBILE = 0x000002;

    public static File getTackPicFilePath() {
        File localFile = new File(getExternalStorePath()
                + "/hishequ/.tempchat", System.currentTimeMillis()
                + "temp.jpg");
        if ((!localFile.getParentFile().exists())
                && (!localFile.getParentFile().mkdirs())) {
            Log.e("hhe", "SD卡不存在");
            localFile = null;
        }
        return localFile;
    }

    public static String getTackPicFileDir() {
        File localFile = new File(getExternalStorePath()
                + "/hishequ/.tempchat");
        if (localFile.exists()) {
            return localFile.getAbsolutePath();
        }
        return null;
    }

    /**
     * 外置存储卡的路径
     *
     * @return
     */
    public static String getExternalStorePath() {
        if (isExistExternalStore()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 是否有外存卡
     *
     * @return
     */
    public static boolean isExistExternalStore() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
