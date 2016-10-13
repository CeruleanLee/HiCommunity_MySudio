package cn.hi028.android.highcommunity.utils.updateutil;

import java.io.File;
import java.io.IOException;

/**
 * Created by LG on 2016/10/3.
 */
public class FileUtils {

    /**
     * 判断新版本文件是否已经下载：
     * 判断条件：1.文件存在
     *           2.文件MD5一致
     * @param new_md5
     * @return
     */
    public static boolean isLoaded(String filePath, String new_md5) {
        File file = new File(filePath);
        try {
            if(file.exists() && MD5Utils.getFileMD5String(file).equals(new_md5)){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
