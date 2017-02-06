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
import android.text.TextUtils;
import android.text.format.DateFormat;

import net.duohuo.dhroid.activity.BaseActivity;
import net.duohuo.dhroid.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.hi028.android.highcommunity.HighCommunityApplication;

public class PhotoUtils {
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x3;
    public static final int REQUEST_CODE_LOAD_IMAGE = 0x4;
    public static String TAG = "PhotoUtils";
    /**
     * request code for tack pic
     */

    static PhotoUtils photoUtils;
    static String mFilePath;

    public static PhotoUtils getInstance() {
        if (photoUtils == null) {
            photoUtils = new PhotoUtils();
        }
        return photoUtils;
    }


    public static File getTackPicFilePath() {
        File localFile = new File(getExternalStorePath()
                + "/HighCommunity/.tempchat", System.currentTimeMillis()
                + "temp.jpg");
        if ((!localFile.getParentFile().exists())
                && (!localFile.getParentFile().mkdirs())) {
            LogUtil.e("hhe", "SD卡不存在");
            localFile = null;
        }
        return localFile;
    }

    public String handleTackPicture(BaseActivity activity) {
        if (!isExistExternalStore()) {
            HighCommunityUtils.GetInstantiation().ShowToast("无内存卡", 0);
            return null;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getTackPicFilePath();
        if (file != null) {
            Uri uri = Uri.fromFile(file);
            if (uri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            mFilePath = file.getAbsolutePath();
        }
        activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        return file.getAbsolutePath();
    }

    public void handleSelectImageIntent(BaseActivity activity) {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, REQUEST_CODE_LOAD_IMAGE);
    }

    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 400f;// 这里设置高度为800f
        float ww = 240f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 25) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static final String IMESSAGE_IMAGE = getExternalStorePath()
            + "/HighCommunity/image";

    /**
     * 是否有外存卡
     *
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
     *
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
        String appPath = IMESSAGE_IMAGE;
        if (context == null || intent == null || appPath == null) {
            LogUtil.e(TAG, "resolvePhotoFromIntent fail, invalid argument");
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
                return BitmapFactory.decodeResource(
                        HighCommunityApplication.getApp().getResources(), resource, options);
            }
            return BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
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
    /**
     * 当前SDK版本号
     */
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
}
