package cn.hi028.android.highcommunity.utils.updateutil;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.yyh.lib.bsdiff.PatchUtils;

import java.io.File;

import cn.hi028.android.highcommunity.R;

/**
 * Created by LYT on 2016/10/2.
 */
public class UpdateVersionService extends Service implements FileDownloader.DownloadCallback {
    private FileDownloader fileDownloader;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notiBuilder;
    private static final int NOTIFY_ID = 1;
    /*** 是否正在下载**/
    public static boolean downloading;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fileDownloader = new FileDownloader(this);
        downloading = false;
        initNotification();
    }

    private void initNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notiBuilder = new NotificationCompat.Builder(this);
        notiBuilder.setContentTitle("正在下载：清单");
        notiBuilder.setSmallIcon(R.drawable.ic_launcher);
    }

    private boolean delta;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!downloading){
            downloading = true;
            String fileUrl = intent.getStringExtra("update_url");
            delta = intent.getBooleanExtra("delta",false);
            String fileName;
            if(delta){
                fileName = intent.getStringExtra("MD5")+".patch";
            }else{
                fileName = intent.getStringExtra("MD5")+".apk";
            }
            fileDownloader.download(delta,fileUrl,fileName,this);
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void success(final File file) {
        reset();
//        notiBuilder.setContentTitle(" ");
//        notiBuilder.setContentText("下载完成，请点击安装");
//        notiBuilder.setProgress(0,0,false);
//        notificationManager.notify(
//                NOTIFY_ID,
//                notiBuilder.build());

        notificationManager.cancel(NOTIFY_ID); //取消通知
        if(delta){
            //如果是增量更新就 合成新APK
            new Thread(){
                @Override
                public void run() {
                    try {
//                         String srcDir = Environment.getExternalStorageDirectory().toString() + "/qingdan2.5.apk";
                        // 指定包名的程序源文件路径
//                        String srcDir = getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir;
                        String srcDir = getPackageManager().getApplicationInfo("com.eqingdan", 0).sourceDir;
                        Log.d("UpdateVersionService", "srcDir"+srcDir);
                        String destDir = file.getAbsolutePath().replace("patch","apk");
                        int result = PatchUtils.getInstance().patch(srcDir, destDir, file.getAbsolutePath());
                        if (result == 0) {
                            ApkUtils.installApk(UpdateVersionService.this,new File(destDir));
                            stopSelf();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }else{
            ApkUtils.installApk(this,file);
            stopSelf();
        }
    }
    private int percent;
    @Override
    public void downloading(int progress,int total) {
        log(progress+","+total);
        int percentNow = progress*100/total;
        //进度每增长5才更新Notifation,更新频率太快会很卡
        if(percentNow >= percent + 5 || percentNow == 100){
            notiBuilder.setContentText(progress*100/total+"%");
            notiBuilder.setProgress(total,progress,false);
            notificationManager.notify(
                    NOTIFY_ID,
                    notiBuilder.build());
            percent += 5;
        }
    }
    @Override
    public void startDownload(int fileSize) {
        notiBuilder.setContentText("0%");
        notiBuilder.setProgress(fileSize,0,false);
    }

    @Override
    public void failed(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        reset();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void reset(){
        percent = 0;
        downloading = false;
    }
    private void log(Object obj){
        Log.d("UpdateVersionService", "obj:" + obj);
    }

}
