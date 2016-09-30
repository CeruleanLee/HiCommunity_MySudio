/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.utils;

import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.format.DateFormat;
import net.duohuo.dhroid.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.hi028.android.highcommunity.HighCommunityApplication;


/**
 *@功能：<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-29<br>
 */
public class CommonUtils {
    public static final String TAG = "CommonUtils";
    public static String f2Bi(float d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }
    public static String str2Bi(String d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }
    public static float floatTo(float d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }
    public static final String IMESSAGE_IMAGE = getExternalStorePath() + "/HighCommunity/image";
    /**
     * 是否有外存卡
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
    /**
     * 外置存储卡的路径
     * @return
     */
    public static String getExternalStorePath() {
        if (isExistExternalStore()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * @param context
     * @param intent
     * @return
     */
    public static String resolvePhotoFromIntent(Context context, Intent intent) {
        String appPath=IMESSAGE_IMAGE;
        if (context == null || intent == null || appPath == null) {
            LogUtil.e(TAG,
                    "resolvePhotoFromIntent fail, invalid argument");
            return null;
        }
        Uri uri = Uri.parse(intent.toURI());
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        try {

            String pathFromUri = null;
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int columnIndex = cursor
                        .getColumnIndex(MediaStore.MediaColumns.DATA);
                // if it is a picasa image on newer devices with OS 3.0 and up
                if (uri.toString().startsWith(
                        "content://com.google.android.gallery3d")) {
                    // Do this in a background thread, since we are fetching a
                    // large image from the web
                    pathFromUri = saveBitmapToLocal(appPath,
                            createChattingImageByUri(intent.getData()));
                } else {
                    // it is a regular local image file
                    pathFromUri = cursor.getString(columnIndex);
                }
                cursor.close();
                LogUtil.d(TAG, "photo from resolver, path: " + pathFromUri);
                return pathFromUri;
            } else {

                if (intent.getData() != null) {
                    pathFromUri = intent.getData().getPath();
                    if (new File(pathFromUri).exists()) {
                        LogUtil.d(TAG, "photo from resolver, path: "
                                + pathFromUri);
                        return pathFromUri;
                    }
                }

                // some devices (OS versions return an URI of com.android
                // instead of com.google.android
                if ((intent.getAction() != null)
                        && (!(intent.getAction().equals("inline-data")))) {
                    // use the com.google provider, not the com.android
                    // provider.
                    // Uri.parse(intent.getData().toString().replace("com.android.gallery3d","com.google.android.gallery3d"));
                    pathFromUri = saveBitmapToLocal(appPath, (Bitmap) intent
                            .getExtras().get("data"));
                    LogUtil.d(TAG, "photo from resolver, path: " + pathFromUri);
                    return pathFromUri;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        LogUtil.e(TAG, "resolve photo from intent failed ");
        return null;
    }
    private static MessageDigest md = null;
    /**
     *
     * @param uri
     * @return
     */
    public static Bitmap createChattingImageByUri(Uri uri) {
        return createChattingImage(0, null, null, uri, 0.0F, 400, 800);
    }

    public static boolean checkByteArray(byte[] b) {
        return b != null && b.length > 0;
    }
    /**
     *
     * @param options
     * @param data
     * @param path
     * @param uri
     * @param resource
     * @return
     */
    public static Bitmap decodeMuilt(BitmapFactory.Options options,
                                     byte[] data, String path, Uri uri, int resource) {
        try {

            if (!checkByteArray(data) && TextUtils.isEmpty(path) && uri == null
                    && resource <= 0) {
                return null;
            }

            if (checkByteArray(data)) {
                return BitmapFactory.decodeByteArray(data, 0, data.length,
                        options);
            }

            if (uri != null) {
                InputStream inputStream = HighCommunityApplication.getApp()
                        .getContentResolver().openInputStream(uri);
                Bitmap localBitmap = BitmapFactory.decodeStream(inputStream,
                        null, options);
                inputStream.close();
                return localBitmap;
            }

            if (resource > 0) {
                return BitmapFactory.decodeResource(HighCommunityApplication.getApp()
                        .getResources(), resource, options);
            }
            return BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param resource
     * @param path
     * @param b
     * @param uri
     * @param dip
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createChattingImage(int resource, String path,
                                             byte[] b, Uri uri, float dip, int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        int outWidth = 0;
        int outHeight = 0;
        int sampleSize = 0;
        try {

            do {
                if (dip != 0.0F) {
                    options.inDensity = (int) (160.0F * dip);
                }
                options.inJustDecodeBounds = true;
                decodeMuilt(options, b, path, uri, resource);
                //
                outWidth = options.outWidth;
                outHeight = options.outHeight;

                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                if (outWidth <= width || outHeight <= height) {
                    sampleSize = 0;
                    setInNativeAlloc(options);
                    Bitmap decodeMuiltBitmap = decodeMuilt(options, b, path,
                            uri, resource);
                    return decodeMuiltBitmap;
                } else {
                    options.inSampleSize = (int) Math.max(outWidth / width,
                            outHeight / height);
                    sampleSize = options.inSampleSize;
                }
            } while (sampleSize != 0);

        } catch (IncompatibleClassChangeError e) {
            e.printStackTrace();
            throw ((IncompatibleClassChangeError) new IncompatibleClassChangeError(
                    "May cause dvmFindCatchBlock crash!").initCause(e));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            BitmapFactory.Options catchOptions = new BitmapFactory.Options();
            if (dip != 0.0F) {
                catchOptions.inDensity = (int) (160.0F * dip);
            }
            catchOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            if (sampleSize != 0) {
                catchOptions.inSampleSize = sampleSize;
            }
            setInNativeAlloc(catchOptions);
            try {
                return decodeMuilt(options, b, path, uri, resource);
            } catch (IncompatibleClassChangeError twoE) {
                twoE.printStackTrace();
                throw ((IncompatibleClassChangeError) new IncompatibleClassChangeError(
                        "May cause dvmFindCatchBlock crash!").initCause(twoE));
            } catch (Throwable twoThrowable) {
                twoThrowable.printStackTrace();
            }
        }

        return null;
    }
    public static boolean inNativeAllocAccessError = false;
    /** 当前SDK版本号 */
    private static int mSdkint = -1;
    public static void setInNativeAlloc(BitmapFactory.Options options) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && !inNativeAllocAccessError) {
            try {
                BitmapFactory.Options.class.getField("inNativeAlloc")
                        .setBoolean(options, true);
                return;
            } catch (Exception e) {
                inNativeAllocAccessError = true;
            }
        }
    }

    public static String md5(final String c) {
        if (md == null) {
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        if (md != null) {
            md.update(c.getBytes());
            return byte2hex(md.digest());
        }
        return "";
    }
    public static String byte2hex(byte b[]) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = (new StringBuffer(String.valueOf(hs))).toString();
        }

        return hs.toUpperCase();
    }
    /**
     * save image from uri
     *
     * @param outPath
     * @param bitmap
     * @return
     */
    public static String saveBitmapToLocal(String outPath, Bitmap bitmap) {
        try {
            String imagePath = outPath
                    + md5(DateFormat.format("yyyy-MM-dd-HH-mm-ss",
                    System.currentTimeMillis()).toString()) + ".jpg";
            File file = new File(imagePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    bufferedOutputStream);
            bufferedOutputStream.close();
            LogUtil.d(TAG, "photo image from data, path:" + imagePath);
            return imagePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
	public static void createfragment(FragmentManager fm, Fragment fragment,
			int ra, boolean cancel) {
		// 创建处理事务
		FragmentTransaction ft2 = fm.beginTransaction();
		// 设置界面
		try {
			if (!(fm.getFragments().contains(fragment))) {
				ft2.add(ra, fragment);
			}
		} catch (Exception e) {
			ft2.add(ra, fragment);
		}  

		if (cancel) {
			// 添加返回键弹出功能
			ft2.addToBackStack(null);
		}
		ft2.show(fragment);
		// 完成修改
		ft2.commitAllowingStateLoss();
		
	}
	/**
     * dp转px
     **/
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     **/
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }
}
