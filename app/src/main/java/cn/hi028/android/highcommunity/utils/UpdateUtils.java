//package cn.hi028.android.highcommunity.utils;
//
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Handler;
//import android.os.Message;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xutils.common.util.LogUtil;
//
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
///**
// * Created by Lee_yting on 2016/10/8 0008.
// * 说明：
// */
//public class UpdateUtils {
//    Context mContext;
//    HashMap<String, String> mHashMap;
//    private boolean isUpdate;
//    int versionCode;
//
//    public UpdateUtils(Context mContext) {
//        LogUtil.d("~~~~~~UpdateUtils---");
//        this.mContext = mContext;
//        checkUpdate();
//    }
//
//    public boolean checkIsUpdate() {
//        LogUtil.d("~~~~~~checkUpdate---");
//        checkUpdate();
//        return isUpdate;
//    }
//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            HashMap<String, String> mMap = (HashMap<String, String>) msg.obj;
//            if (null != mMap) {
//                int serviceCode = Integer.valueOf(mMap.get("version"));
////                Toast.makeText(mContext, "~~~~~~现在的版本号：" + versionCode + ",从服务器下来的版本号：" + serviceCode + "---", Toast.LENGTH_SHORT).show();
//                LogUtil.d("~~~~~~现在的版本号：" + versionCode + ",从服务器下来的版本号：" + serviceCode + "---");
//                if (serviceCode > versionCode) {
//                    isUpdate = true;
//                }
//            }
//            isUpdate = false;
//
//        }
//
//    };
//
//
//    /**
//     * 检测是否需要更新
//     *
//     * @return
//     */
//    private void checkUpdate() {
//        LogUtil.d("~~~~~~checkUpdate---");
//
//
//        versionCode = getVersionCode(mContext);
//        //本地的XML更新
//        // InputStream inStream = ParseXmlService.class
//        //
//        // .getResourceAsStream("version.xml");
//        // InputStream inStream = ParseXmlService.class
//        // .getClassLoader()
//        // .getResourceAsStream(
//        // "version1.xml");
//
//        //解析网络xml进行更新软件
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                URL url = null;
//                try {
//                    url = new URL("http://028hi.cn:8080/Apk/version.xml");
////http://gongzibai.h001.sjsdidc.cn/version.xml
//
//                } catch (MalformedURLException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                HttpURLConnection conn = null;
//                InputStream is = null;
//                try {
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setConnectTimeout(5000);
//                    is = conn.getInputStream();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//
//                ParseXmlService service = new ParseXmlService();
//                try {
//                    mHashMap = service.parseXml(is);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Message message = new Message();
//                message.obj = mHashMap;
//                mHandler.sendMessage(message);
//
//
//            }
//        });
//
//
//    }
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
//            LogUtil.d("~~~~~~从服务器上下来的信息："+hashMap.toString());
//
//            return hashMap;
//        }
//
//    }
//
//    /**
//     * 获取应用版本号
//     *
//     * @param context
//     * @return
//     */
//    private int getVersionCode(Context context) {
//        int versionCode = 0;
//        try {
//            versionCode = context.getPackageManager().getPackageInfo("cn.hi028.android.highcommunity", 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return versionCode;
//    }
//
//}
