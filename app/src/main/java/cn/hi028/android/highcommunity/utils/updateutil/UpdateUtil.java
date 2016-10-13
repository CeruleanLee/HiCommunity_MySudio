package cn.hi028.android.highcommunity.utils.updateutil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import net.duohuo.dhroid.util.LogUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cn.hi028.android.highcommunity.R;

/**
 * Created by Lee_yting on 2016/10/8
 * 说明：版本更新工具类
 */
public class UpdateUtil implements View.OnClickListener {
    int nowVersionName;
    static boolean isUpdate;
    HashMap<String, String> mHashMap;
    //    int versionCode;
    Context context;
    Activity act;
    private static final int NO_UPDATE = 0;
    private static final int UPDATE = 1;
    private Handler mUpdateHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case NO_UPDATE:
                    Toast.makeText(context, "不需要更新", Toast.LENGTH_SHORT).show();

//                        mProgress.setProgress(progress);
                    break;
                case UPDATE:
                    Toast.makeText(context, "需要更新", Toast.LENGTH_SHORT).show();
                    if (act != null) {
                        showNoticeDialog();

                    } else {
                        LogUtil.d("~~~~~~act null ");
                        Toast.makeText(context, "act null", Toast.LENGTH_SHORT).show();

                    }
                    //安装APK
//                        installApk();
                    break;
                default:
                    break;
            }
        }
    };

    private Dialog dialog;
    private View mUpdateOkBtn;
    private View mUpdateCancelBtn;
    private TextView updateContentTv;
    private boolean isLoaded;
    /**
     * 弹出更新提示框
     */
    private void showNoticeDialog() {
        if (dialog == null) {
            dialog = new Dialog(act);
            dialog.setContentView(R.layout.umeng_update_dialog);
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景为透明
            window.setWindowAnimations(R.style.dialogWindowAnim);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
            mUpdateOkBtn = window.findViewById(R.id.umeng_update_id_ok);
            mUpdateCancelBtn = window.findViewById(R.id.umeng_update_id_cancel);
            mUpdateOkBtn.setOnClickListener(this);
            mUpdateCancelBtn.setOnClickListener(this);
            updateContentTv = (TextView) window.findViewById(R.id.umeng_update_content);
        }
        String updateContent;
        //如果文件存在且文件md5值正确
        if (isLoaded){
//        if(isLoaded = FileUtils.isLoaded(FileDownloader.getFilePath(parseObject.getNew_md5()+".apk"),parseObject.getNew_md5())){
//            updateContent = String.format(context.getString(R.string.Update_loaded_Content),
//                    AppVerison.getAppVersionName(context),
//                    parseObject.getVersion(),
//                    parseObject.getUpdate_log());
            updateContent="暂时还没有下载";
        }else{
            updateContent = String.format(context.getString(R.string.Update_Content), getAppVersionName(context), mHashMap.get("version"),
                    "暂无",mHashMap.get("content"));
        }
        updateContentTv.setText(updateContent.replace("\\n", "\n").replace("\\r","\r"));
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == mUpdateCancelBtn) {
            dialog.dismiss();
        } else if (v == mUpdateOkBtn) {
            dialog.dismiss();
            if (isLoaded) {
                //去安装
                Toast.makeText(context, "准备去安装", Toast.LENGTH_LONG).show();
//                Log.d("UpdateVersionService", "正在安装");
//                String fileName = FileDownloader.getFilePath(parseObject.getNew_md5() + ".apk");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
//                context.startActivity(intent);
            } else {
                //下载新版本
                Intent intent = new Intent(context, UpdateService.class);
                intent.putExtra("update_url", "http://028hi.cn:8080/"+mHashMap.get("filepath"));
                intent.putExtra("App_Name", "嗨社区");
//                intent.putExtra("MD5", parseObject.getNew_md5());
//                intent.putExtra("delta", parseObject.isDelta());
                context.startService(intent);
                Toast.makeText(context, "新版本正在后台下载", Toast.LENGTH_LONG).show();
            }
        }
    }



    public UpdateUtil(Activity act, Context context) {
        this.act = act;
        this.context = context;
    }


    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {

        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo("cn.hi028.android.highcommunity", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.d("~~~~~~获取现在的应用版本号：+" + versionCode);
        return versionCode;
    }

    /**
     * 获取App版本名
     *
     * @param context 上下文
     * @return App版本号
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解析xml
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public HashMap<String, String> parseXml(InputStream inStream) throws Exception {

        LogUtil.d("~~~~~~解析xml---");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {

            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;

                if ("version".equals(childElement.getNodeName())) {
                    hashMap.put("version", childElement.getFirstChild().getNodeValue());
                } else if (("content".equals(childElement.getNodeName()))) {
                    hashMap.put("content", childElement.getFirstChild().getNodeValue());
                } else if (("filepath".equals(childElement.getNodeName()))) {
                    hashMap.put("filepath", childElement.getFirstChild().getNodeValue());
                }
            }
        }
        LogUtil.d("~~~~~~从服务器上下来的信息：" + hashMap.toString());

        return hashMap;
    }

    /**
     * 检测是否需要更新
     *
     * @return
     */
    public void checkUpdate() {

        LogUtil.d("~~~~~~checkUpdate---");

//        versionCode = getVersionCode(getApplicationContext());
        String appVersionName = getAppVersionName(context);

        String mVersionName = appVersionName.replace(".", "");

        nowVersionName = Integer.valueOf(mVersionName);
        thread2.start();
        //本地的XML更新
        // InputStream inStream = ParseXmlService.class
        //
        // .getResourceAsStream("version.xml");
        // InputStream inStream = ParseXmlService.class
        // .getClassLoader()
        // .getResourceAsStream(
        // "version1.xml");
    }
    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            URL url;
            HttpURLConnection conn;
            InputStream is;
            try {
                url = new URL("http://028hi.cn:8080/Apk/version.xml");
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                is = conn.getInputStream();
                mHashMap = parseXml(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message message = new Message();
            message.obj = mHashMap;
            mHandler.sendMessage(message);


        }
    });
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            HashMap<String, String> mMap = (HashMap<String, String>) msg.obj;
            if (null != mMap) {
                String versionStr = mMap.get("version");
                String mVersionStr = versionStr.replace(".", "");
                int serviceCode = Integer.valueOf(mVersionStr);
                Toast.makeText(context, "~~~~~~现在的版本号：" + nowVersionName + ",从服务器下来的版本号：" + serviceCode + "---", Toast.LENGTH_SHORT).show();
                LogUtil.d("~~~~~~现在的版本号：" + nowVersionName + ",从服务器下来的版本号：" + serviceCode + "---");
                if (serviceCode > nowVersionName) {
                    isUpdate = true;
                    mUpdateHandler.sendEmptyMessage(UPDATE);
                    LogUtil.d("~~~~~~!!!是否需要更新：" + isUpdate);
                    Toast.makeText(context, "是否需要更新：" + isUpdate, Toast.LENGTH_SHORT).show();
                } else {
                    isUpdate = false;
                    LogUtil.d("~~~~~~!!!是否需要更新：" + isUpdate);
                    mUpdateHandler.sendEmptyMessage(NO_UPDATE);
                    Toast.makeText(context, "是否需要更新：" + isUpdate, Toast.LENGTH_SHORT).show();
                }
            }
        }

    };


}