package cn.hi028.android.highcommunity.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

public class BitmapHandler {

	public static Bitmap createCircleBitmap(Bitmap srcBitmap){
		if(srcBitmap == null){
			return srcBitmap;
		}
		int bmWidth = srcBitmap.getWidth();
		int bmHeight = srcBitmap.getHeight();
		int min = bmWidth < bmHeight ? bmWidth : bmHeight;
		Bitmap bitmap = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  
		canvas.drawBitmap(srcBitmap, 0, 0, paint);  
		return bitmap;
	}
	/**
	 * 矩形圆角Bitmap
	 * @param srcBitmap
	 * @return
	 */
	public static Bitmap createRoundBitmap(Bitmap srcBitmap){
		if(srcBitmap == null){
			return srcBitmap;
		}
		int bmWidth = srcBitmap.getWidth();
		int bmHeight = srcBitmap.getHeight();
		int min = bmWidth < bmHeight ? bmWidth : bmHeight;
		Bitmap bitmap = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		RectF rect = new RectF(0, 0, min, min);
		canvas.drawRoundRect(rect , 10, 10, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  
		canvas.drawBitmap(srcBitmap, 0, 0, paint);  
		return bitmap;
	}
}
