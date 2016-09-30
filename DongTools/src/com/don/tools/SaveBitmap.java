package com.don.tools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class SaveBitmap {
    public static boolean isHaveSD = false;

    public static Uri SaveBitmap(Bitmap bitmap) {
        File myCaptureFile = new File(getTheNewUrl());
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        bitmap.recycle();
        return Uri.fromFile(myCaptureFile);

    }

    // 生成新地址
    public static String getTheNewUrl() {
        String fileName = "";
        // String sdStatus = Environment.getExternalStorageState();
        // if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
        // String name = System.currentTimeMillis() + ".jpg";
        // isHaveSD = false;
        // } else {
        isHaveSD = true;
        String name = System.currentTimeMillis() + ".jpg";
        File file = new File(DongConstants.SDCARDAVATAR);
        if(!file.exists())
            file.mkdir();
        fileName = file.getAbsolutePath() + "/" + name;
        return fileName;
    }

    // Uri转真实地址
    public static String getRealPath(Context mContext, Uri fileUrl) {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (filePathUri.getScheme().compareTo("file") == 0) // file:///开头的uri
        {
            fileName = filePathUri.toString();
            fileName = filePathUri.toString().replace("file://", "");
            // 替换file://
        }
        if (fileUrl.getScheme().toString().compareTo("content") == 0) // content://开头的uri
        {
            Cursor cursor = mContext.getContentResolver().query(fileUrl, null,
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                fileName = cursor.getString(column_index); // 取出文件路径
                cursor.close();
            }
        }
        return fileName;
    }

    // 读取压缩合适图片(以宽)
    public static Bitmap createNewBitmapAndCompressByFile(String filePath,
                                                          float size, int Width) {
        int offset = 100;
        File file = new File(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 为true里只读图片的信息，如果长宽，返回的bitmap为null
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = false;
        /**
         * 计算图片尺寸 //TODO 按比例缩放尺寸
         */
        BitmapFactory.decodeFile(filePath, options);

        int bmpheight = options.outHeight;
        int bmpWidth = options.outWidth;
        int inSampleSize = bmpWidth / Width;
        // if(bmpheight / wh[1] < bmpWidth / wh[0]) inSampleSize = inSampleSize
        // * 2 / 3;//TODO 如果图片太宽而高度太小，则压缩比例太大。所以乘以2/3
        if (inSampleSize > 1)
            options.inSampleSize = inSampleSize;// 设置缩放比例
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[100 * 1024];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {

            System.gc();
            bitmap = null;
        }
        if (size == 0)
            return bitmap;
        bitmap = ZOOMSizeByte(bitmap, size);
        return bitmap;
    }

    // 读取压缩合适图片(以宽高)
    public static Bitmap createNewBitmapAndCompressByFile(String filePath,
                                                          float size, int Width, int Height) {
        int offset = 100;
        File file = new File(filePath);
        long fileSize = file.length();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 为true里只读图片的信息，如果长宽，返回的bitmap为null
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = false;
        /**
         * 计算图片尺寸 //TODO 按比例缩放尺寸
         */
        BitmapFactory.decodeFile(filePath, options);

        int bmpheight = options.outHeight;
        int bmpWidth = options.outWidth;
        int inSampleSize = bmpheight / Height > bmpWidth / Width ? bmpheight
                / Height : bmpWidth / Width;
        // if(bmpheight / wh[1] < bmpWidth / wh[0]) inSampleSize = inSampleSize
        // * 2 / 3;//TODO 如果图片太宽而高度太小，则压缩比例太大。所以乘以2/3
        if (inSampleSize > 1)
            options.inSampleSize = inSampleSize;// 设置缩放比例
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[100 * 1024];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {

            System.gc();
            bitmap = null;
        }
        if (size == 0)
            return bitmap;
        bitmap = ZOOMSizeByte(bitmap, size);
        return bitmap;
    }

    public static Bitmap ZOOMSizeByte(Bitmap bitmap, float size) {
        if (bitmap == null)
            return null;
        int offset = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] buffer = baos.toByteArray();
        float Msize = ((float) buffer.length) / (1024 * 1024);
        Debug.verbose("mSize:----------->" + Msize + "          " + "buffer size:------>" + buffer.length);
        offset = (int) (offset * (size / Msize));
        if (offset > 4)
            offset -= 2;
        if (offset > 100)
            offset = 100;
        if (offset <= 0)
            offset = 1;
        baos.reset();
        buffer = null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, offset, baos);
        buffer = baos.toByteArray();
        Debug.verbose("压缩比例 offset-------------->" + offset);
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
    }

}
