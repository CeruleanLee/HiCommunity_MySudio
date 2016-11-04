package cn.hi028.android.highcommunity.utils;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.don.tools.BpiHttpHandler;

import java.util.HashMap;

import cn.hi028.android.highcommunity.R;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 赵海 on 2016/3/4.
 */
public class ShareUtils {
    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     */
    public static void showShare(Activity context, String platformToShare, int shareType, PlatformActionListener listener, String title, String content, String url) {
        ShareSDK.initSDK(context);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(content);
        sp.setTitle(title);
        sp.setUrl(url);
        sp.setTitleUrl(url);
        sp.setImageData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        sp.setShareType(shareType);
        Platform weibo = ShareSDK.getPlatform(platformToShare);
        weibo.setPlatformActionListener(listener); // 设置分享事件回调
// 执行图文分享
        weibo.share(sp);
    }

    public static void showShareComm(final Activity context, String platformToShare, String url) {
        showShare(context, platformToShare, Platform.SHARE_WEBPAGE, new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HighCommunityUtils.GetInstantiation().ShowToast("分享成功", 0);
                        HTTPHelper.shareResult(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {

                            }

                            @Override
                            public void onSuccess(Object message) {

                            }

                            @Override
                            public Object onResolve(String result) {
                                return null;
                            }

                            @Override
                            public void setAsyncTask(AsyncTask asyncTask) {

                            }

                            @Override
                            public void cancleAsyncTask() {

                            }
                        });
                    }
                });

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HighCommunityUtils.GetInstantiation().ShowToast("分享失败", 0);
                    }
                });

            }

            @Override
            public void onCancel(Platform platform, int i) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HighCommunityUtils.GetInstantiation().ShowToast("取消分享", 0);
                    }
                });

            }
        }, "嗨社区", "为用户提供快捷的物业服务、方便的周边生活、便利的邻里社交和实惠的优选商品，涉及物业、家政、餐饮、电商等多重功能，并提供邻里在线交流互动平台", url);
    }

    public static void showShare(final Activity context, String platformToShare, String url) {
        showShare(context, platformToShare, Platform.SHARE_WEBPAGE, new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HighCommunityUtils.GetInstantiation().ShowToast("分享成功", 0);
                        HTTPHelper.shareResult(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {

                            }

                            @Override
                            public void onSuccess(Object message) {

                            }

                            @Override
                            public Object onResolve(String result) {
                                return null;
                            }

                            @Override
                            public void setAsyncTask(AsyncTask asyncTask) {

                            }

                            @Override
                            public void cancleAsyncTask() {

                            }
                        });
                    }
                });

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HighCommunityUtils.GetInstantiation().ShowToast("分享失败", 0);
                    }
                });

            }

            @Override
            public void onCancel(Platform platform, int i) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HighCommunityUtils.GetInstantiation().ShowToast("取消分享", 0);
                    }
                });

            }
        }, "嗨社区", "\"嗨社区\"平台旨在为社区居民提供人性化、现代化、网络化" +
                "便民服务的基础上，重点整合生鲜、养老、医疗、连锁、社区生活服务站等功能，进一步" +
                "涵盖商务服务、政务服务、公共服务和志愿服务，紧紧围绕生活服务，解决居民对供给" +
                "和质量的需求，实现便捷、便宜的生活方式。", url);
    }
}
//}, "嗨社区", "嗨社区，智慧社区云服务管理平台充分利用互联网" + "云计算,移动互联网等新一代信息技术的集成应用。以服务社区为中心,以\"简政,兴业,惠民\"为目标\n" +
//        "最终实现5A模式\"智慧家园,幸福生活\"。", url);