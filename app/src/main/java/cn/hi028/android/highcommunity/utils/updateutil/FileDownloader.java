package cn.hi028.android.highcommunity.utils.updateutil;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lee_yting on 2016/10/5.gg
 */
public class FileDownloader {

    private Context context;
    private static final int MSG_START_DOWNLOAD = 1;
    private static final int MSG_DOWNLOADNG = 2;
    private static final int MSG_SUCCESS = 3;
    private static final int MSG_FALED = 4;
    private boolean delta;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if(callback == null){
                return;
            }
            switch (msg.what){
                case MSG_START_DOWNLOAD:
                    callback.startDownload((Integer) msg.obj);
                    break;
                case MSG_DOWNLOADNG:
                    callback.downloading(msg.arg1,msg.arg2);
                    break;
                case MSG_SUCCESS:
                    callback.success((File) msg.obj);
                    break;
                case MSG_FALED:
                    callback.failed((String) msg.obj);
                    break;
            }
        }
    };
    /**
     * 文件保存目录
     */
    public static String dirPath;
    static{
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator+"Download"+ File.separator+"Hi";
    }
    public static String getFilePath(String fileName){
        return dirPath+ File.separator+fileName;
    }
    private DownloadCallback callback;
    public FileDownloader(Context context) {
        this.context = context;
        createDownloadDir();
    }

    /**
     * 创建文件保存的目录
     */
    private void createDownloadDir(){
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 下载入口
     * @param fileUrl
     * @param fileName
     * @param callback
     */
    public void download(boolean delta, String fileUrl, String fileName, DownloadCallback callback){
        Log.d("FileDownloader", "delta:"+delta);
        Log.d("FileDownloader", "fileUrl:"+fileUrl);
        this.callback = callback;
        this.delta = delta;
        String filePath = dirPath+ File.separator+fileName;
        DownloadThread thread = new DownloadThread(fileUrl,filePath);
        thread.start();
    }

    /**
     * 下载文件的线程，支持断点续传(利用缓存文件实现)
     */
    private class DownloadThread extends Thread {
        private String fileUrl;
        private String filePath;
        public DownloadThread(String fileUrl, String filePath){
            this.filePath = filePath;
            this.fileUrl = fileUrl;
        }
        @Override
        public void run() {
            InputStream is = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(8000);
                conn.setConnectTimeout(8000);
                //判断是否存在缓存文件
                int startPos = 0;
                if(isTemFileExist(filePath)){
                    startPos = (int) getTemFileSize(filePath);
                    //-1是防止文件已经下载好但是没有重命名而导致错误
                    if(startPos != 0){
                        startPos -= 1;
                    }
                    conn.addRequestProperty("Range","bytes="+startPos+"-");
                }
                int responseCode = conn.getResponseCode();
                if(responseCode == 200 || responseCode == 206){//如果设置了访问数据的范围，将返回206
                    int contentLength = conn.getContentLength()+startPos;
                    handler.sendMessage(handler.obtainMessage(MSG_START_DOWNLOAD,contentLength));
                    is = conn.getInputStream();
                    File temFile = getTemFile(filePath);
                    raf = new RandomAccessFile(temFile,"rw");
                    raf.seek(startPos);
                    byte[] buff = new byte[1024*4];
                    int len;
                    int downloadCount = startPos;
                    while((len = is.read(buff)) != -1){
                        raf.write(buff,0,len);
                        downloadCount += len;
                        handler.sendMessage(handler.obtainMessage(MSG_DOWNLOADNG,downloadCount,contentLength));
                    }
                    //缓存文件下载后之后
                    File file = new File(filePath);
                    temFile.renameTo(file);
                    handler.sendMessage(handler.obtainMessage(MSG_SUCCESS,file));
                    return;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(raf != null){
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            handler.sendMessage(handler.obtainMessage(MSG_FALED,"下载失败"));
        }

        /**
         * 判断缓存文件是否存在
         * @param filePath
         * @return
         */
        private boolean isTemFileExist(String filePath){
            File temFile = getTemFile(filePath);
            return temFile.exists();
        }

        /**
         * 获取缓存文件大小
         * @param filePath
         * @return
         */
        private long getTemFileSize(String filePath){
            File temFile = getTemFile(filePath);
            return temFile.length();
        }

        /**
         * 获取缓存文件
         * @param filePath
         * @return
         */
        private File getTemFile(String filePath){
            return new File(filePath+".tem");
        }
    }
    public interface DownloadCallback{
        void success(File file);
        void downloading(int progress, int total);
        void startDownload(int fileSize);
        void failed(String msg);
    }
}
