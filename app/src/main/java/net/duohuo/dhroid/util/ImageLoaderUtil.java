/**
 * 文件名：ImageLoaderUtils.java
 * 全路径：com.smartgame.android.utils.ImageLoaderUtils
 */
package net.duohuo.dhroid.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.hi028.android.highcommunity.R;


/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2014年12月12日<br>
 * 版本：1.0<br>
 */
public class ImageLoaderUtil {

    private static ImageLoader imageLoader = ImageLoader.getInstance();
    private static DisplayImageOptions options;

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static boolean checkImageLoader() {
        return imageLoader.isInited();
    }

    public static void disPlay(String uri, ImageAware imageAware, int defaultPic) {
        setOptions(defaultPic, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageAware, options);
    }

    public static void disPlay(String uri, ImageView imageView, int defaultPic,
                               ImageLoadingListener listener) {
        setOptions(defaultPic, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageView, options, listener);
    }

    public static void disPlayRounded(String uri, ImageView imageView,
                                      int defaultPic, int cornerRadiusPixels,
                                      ImageLoadingListener listener) {
        setOptions(defaultPic, new RoundedVignetteBitmapDisplayer(
                cornerRadiusPixels, 2));
        imageLoader.displayImage(uri, imageView, options, listener);
    }

    public static void disPlay(String uri, ImageView imageView) {
        setOptions(0, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageView, options);
    }

    public static void disBigPlay(String uri, ImageView imageView) {
        setOptions(R.drawable.loading, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageView, options);
    }

    /**
     * 根据uri获取图片的bitmap
     *
     * @param uri    图片的uri地址
     * @param width  指定图片的最大宽度
     * @param height 指定图片的最大高度
     * @return
     */
    public static Bitmap getImageBitmap(String uri, int width, int height) {
        setOptions(0, new SimpleBitmapDisplayer());
        ImageSize targetSize = new ImageSize(width, height); // result Bitmap
        // will be fit
        // to this size
        return imageLoader.loadImageSync(uri, targetSize, options);
    }

    private static void setOptions(int defaultPic, BitmapDisplayer displayer) {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultPic)
                .showImageForEmptyUri(defaultPic).showImageOnFail(defaultPic)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY).displayer(displayer)
                .build();
    }

    public static void clear() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }

    public static void clearAll() {
        clear();
        // AnimateFirstDisplayListener.displayedImages.clear();
        // AlbumUtil.clear();
        // PhotoSelectUtil.clear();
    }

    public static void resume() {
        imageLoader.resume();
    }

    /**
     * 暂停加载
     */
    public static void pause() {
        imageLoader.pause();
    }

    /**
     * 停止加载
     */
    public static void stop() {
        imageLoader.stop();
    }

    /**
     * 销毁加载
     */
    public static void destroy() {
        imageLoader.destroy();
    }
}
