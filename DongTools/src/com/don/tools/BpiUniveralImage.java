package com.don.tools;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;


import com.don.dongtools.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author dong 2014年1月4日 15:26:00
 * @category 管理图片的缓存和下载，展示。<br>
 */
public class BpiUniveralImage {

    /**
     * @author tanlet 自动下载和管理缓存图片，并根据View的大小来展示图片
     */
    public static void displayImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    /**
     * 需特殊處理bitmap
     *
     * @param url
     * @param imageView
     * @param listener
     * @author linxd
     */
    public static void displayImage(String url, ImageView imageView,
                                    ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(url, imageView, listener);
    }

    /**
     * @author linxd 單獨圖片下載
     */
    public static void loadImage(String url,
                                 SimpleImageLoadingListener mSimpleImageLoadingListener) {
        ImageLoader.getInstance().loadImage(url, mSimpleImageLoadingListener);
    }

    /**
     * @author lly 获取网络图片
     * url 图片地址
     */
    public static Bitmap loadImage(String url) {
        System.out.println("Image Url:------>" + url);
        return ImageLoader.getInstance().loadImageSync(url);
    }

    /**
     * @param url
     * @param imageView
     * @param Tag       imageview的Tag标记
     * @author dong
     * @category 防止listview图片异步错位
     */
    public static void displayImage(String url, ImageView imageView, String Tag) {
        imageView.setTag(R.id.tag_first, Tag);
        ImageLoader.getInstance().displayImage(url, imageView,
                mTagImageLoadingListener);
    }


    /**
     * @param url
     * @param imageView
     * @param Tag       imageview的Tag标记
     * @param isGray    imageview的是否需要灰度标记
     * @author dong
     * @category 防止listview图片异步错位(灰度过滤)
     */
    public static void displayImage(String url, ImageView imageView,
                                    String Tag, boolean isGray) {
        imageView.setTag(R.id.tag_first, Tag);
        imageView.setTag(R.id.tag_second, isGray);
        ImageLoader.getInstance().displayImage(url, imageView,
                mTagImageLoadingListener);
    }

    public static void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * @author dong
     * @category ImageLoadingListener图片下载监听器
     */
    static ImageLoadingListener mTagImageLoadingListener = new ImageLoadingListener() {
        @Override
        public void onLoadingCancelled(String arg0, View arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLoadingComplete(String url, View view, Bitmap bitmap) {
            // TODO Auto-generated method stub
            if (bitmap == null || view == null)
                return;
            if (view.getTag(R.id.tag_first) == null
                    || !view.getTag(R.id.tag_first).toString().equals(url)) {
                return;
            }
//			view.setBackgroundDrawable(new BitmapDrawable(bitmap));
//			if (arg1.getTag(R.id.tag_second) != null
//					&& Boolean.valueOf(arg1.getTag(R.id.tag_second).toString())) {
//				BitmapDrawable mDrawable = new BitmapDrawable(arg2);
//				mDrawable.mutate();
//				ColorMatrix cm = new ColorMatrix();
//				cm.setSaturation(0);
//				ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
//				mDrawable.setColorFilter(cf);
//				// arg2 = mDrawable.getBitmap();
//				((ImageView)arg1).setImageDrawable(mDrawable);
//				return;
//			}
        }

        @Override
        public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLoadingStarted(String arg0, View arg1) {
            // TODO Auto-generated method stub

        }

    };
}
