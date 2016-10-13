//package cn.hi028.android.highcommunity.utils;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import cn.hi028.android.highcommunity.R;
//
///**
// * Created by Lee_yting on 2016/10/8 0008.
// * 说明：全局工具类
// */
//public class AppUtils {
////    public static boolean  isUpdate;
////
////    /**
////     * 获取App版本号
////     *
////     * @param context 上下文
////     * @return App版本号
////     */
////    public static String getAppVersionName(Context context) {
////        return getAppVersionName(context, context.getPackageName());
////    }
////
////    /**
////     * 获取App版本号
////     *
////     * @param context     上下文
////     * @param packageName 包名
////     * @return App版本号
////     */
////    public static String getAppVersionName(Context context, String packageName) {
////        try {
////            PackageManager pm = context.getPackageManager();
////            PackageInfo pi = pm.getPackageInfo(packageName, 0);
////            return pi == null ? null : pi.versionName;
////        } catch (PackageManager.NameNotFoundException e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    /**
////     * 获取App版本码
////     *
////     * @param context 上下文
////     * @return App版本码
////     */
////    public static int getAppVersionCode(Context context) {
////        return getAppVersionCode(context, context.getPackageName());
////    }
////
////    /**
////     * 获取App版本码
////     *
////     * @param context     上下文
////     * @param packageName 包名
////     * @return App版本码
////     */
////    public static int getAppVersionCode(Context context, String packageName) {
////        try {
////            PackageManager pm = context.getPackageManager();
////            PackageInfo pi = pm.getPackageInfo(packageName, 0);
////            return pi == null ? -1 : pi.versionCode;
////        } catch (PackageManager.NameNotFoundException e) {
////            e.printStackTrace();
////            return -1;
////        }
////    }
////
////    public static boolean getIsUpdate() {
////        return UpdateManager.isUpdate();
////    }
////
////
////-----------------ParseXmlService.java
//
//    //解析xml
//    static class ParseXmlService {
//        public HashMap<String, String> parseXml(InputStream inStream) throws Exception {
//            HashMap<String, String> hashMap = new HashMap<String, String>();
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(inStream);
//            Element root = document.getDocumentElement();
//            NodeList childNodes = root.getChildNodes();
//            for (int j = 0; j < childNodes.getLength(); j++) {
//
//                Node childNode = (Node) childNodes.item(j);
//                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element childElement = (Element) childNode;
//
//                    if ("version".equals(childElement.getNodeName())) {
//                        hashMap.put("version", childElement.getFirstChild().getNodeValue());
//                    } else if (("content".equals(childElement.getNodeName()))) {
//                        hashMap.put("content", childElement.getFirstChild().getNodeValue());
//                    } else if (("filepath".equals(childElement.getNodeName()))) {
//                        hashMap.put("filepath", childElement.getFirstChild().getNodeValue());
//                    }
//                }
//            }
//            return hashMap;
//        }
//    }
//
//
//    //-------------更新管理.UpdateManager.java
//
//
//    static class UpdateManager {
//
//        private static final int DOWNLOAD = 1;
//
//        private static final int DOWNLOAD_FINISH = 2;
//
//        HashMap<String, String> mHashMap;
//
//        private String mSavePath;
//
//        private int progress;
//
//        private boolean cancelUpdate = false;
//
//        private Context mContext;
//
//        private ProgressBar mProgress;
//        private Dialog mDownloadDialog;
//
//        private Handler mHandler = new Handler() {
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//
//                    case DOWNLOAD:
//
//                        mProgress.setProgress(progress);
//                        break;
//                    case DOWNLOAD_FINISH:
//                        //安装APK
////                        installApk();
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            ;
//        };
//
//        public UpdateManager(Context context) {
//            this.mContext = context;
//        }
//
//        //检查更新
//        public void checkUpdate() {
//            if (isUpdate()) {
//
//                showNoticeDialog();
//            } else {
//                Toast.makeText(mContext, "不需要更新", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        /**
//         * 检测是否需要更新
//         *
//         * @return
//         */
//        private static boolean isUpdate() {
//
//            int versionCode = getVersionCode(mContext);
//
//            //本地的XML更新
//            // InputStream inStream = ParseXmlService.class
//            //
//            // .getResourceAsStream("version.xml");
//            // InputStream inStream = ParseXmlService.class
//            // .getClassLoader()
//            // .getResourceAsStream(
//            // "version1.xml");
//
//            //解析网络xml进行更新软件
//            URL url = null;
//            try {
//                url = new URL("http://028hi.cn:8080/Apk/version.xml");
////http://gongzibai.h001.sjsdidc.cn/version.xml
//
//            } catch (MalformedURLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            HttpURLConnection conn = null;
//            InputStream is = null;
//            try {
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setConnectTimeout(5000);
//                is = conn.getInputStream();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//            ParseXmlService service = new ParseXmlService();
//            try {
//                mHashMap = service.parseXml(is);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (null != mHashMap) {
//                int serviceCode = Integer.valueOf(mHashMap.get("version"));
//
//                if (serviceCode > versionCode) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        /**
//         * 获取应用版本号
//         *
//         * @param context
//         * @return
//         */
//        private static int getVersionCode(Context context) {
//            int versionCode = 0;
//            try {
//                versionCode = context.getPackageManager().getPackageInfo("cn.hi028.android.highcommunity", 0).versionCode;
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            return versionCode;
//        }
//
//
//        private void showNoticeDialog() {
//
//            AlertDialog.Builder builder = new Builder(mContext);
//            builder.setTitle("更新标题");
//            builder.setMessage("更新日志");
//
//            builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() {
//                @Override
//                public void onClick(DialogInterfacedialog, int which) {
//                    dialog.dismiss();
//
//                    showDownloadDialog();
//                }
//            });
//
//            builder.setNegativeButton(
//                    R.string.soft_update_later,
//                    new OnClickListener() {
//                        @Override
//                        public void onClick(
//                                DialogInterface
//                                        dialog,
//                                int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            Dialog noticeDialog = builder.create();
//            noticeDialog.show();
//        }
//
//
//        private void showDownloadDialog() {
//
//            AlertDialog.Builder builder = new Builder(mContext);
//            builder.setTitle(R.string.soft_updating);
//
//            final LayoutInflater inflater = LayoutInflater.from(mContext);
//            View v = inflater.inflate(R.layout.softupdate_progress, null);
//            mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
//            builder.setView(v);
//
//            builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    cancelUpdate = true;
//                }
//            });
//            mDownloadDialog = builder.create();
//            mDownloadDialog.show();
//
//            downloadApk();
//        }
//
//        private void downloadApk() {
//
//            new downloadApkThread().start();
//        }
//
//        private class downloadApkThread extends Thread {
//            @Override
//            public void run() {
//                try {
//
//                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                        String sdpath = Environment.getExternalStorageDirectory() + "/";
//                        mSavePath = sdpath + "download";
//                        URL url = new URL(mHashMap.get("url"));
//
//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.connect();
//
//                        int length = conn.getContentLength();
//
//                        InputStream is = conn.getInputStream();
//
//                        File file = new File(mSavePath);
//
//                        if (!file.exists()) {
//                            file.mkdir();
//                        }
//                        File apkFile = new File(mSavePath, mHashMap.get("name"));
//                        FileOutputStream fos = newFileOutputStream(apkFile);
//                        int count = 0;
//
//                        byte buf[] = new byte[1024];
//                        do {
//                            int numread = is.read(buf);
//                            count += numread;
//                            progress = (int) (((float) count / length) * 100);
//                            mHandler.sendEmptyMessage(DOWNLOAD);
//                            if (numread <= 0) {
//                                mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
//                                break;
//                            }
//
//                            fos.write(buf, 0, numread);
//                        } while (!cancelUpdate);
//                        fos.close();
//                        is.close();
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                mDownloadDialog.dismiss();
//            }
//        }
//
//
//        private void installApk() {
//            File apkfile = new File(mSavePath, mHashMap.get("name"));
//            if (!apkfile.exists()) {
//                return;
//            }
//
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setDataAndType(
//                    Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
//            mContext.startActivity(i);
//        }
//
//    }
//}