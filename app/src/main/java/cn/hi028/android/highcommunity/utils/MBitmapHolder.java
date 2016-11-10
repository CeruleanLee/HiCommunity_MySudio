package cn.hi028.android.highcommunity.utils;

import android.content.Context;
import android.os.Environment;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Lee_yting on 2016/10/8 .
 * 说明：
 */
public class MBitmapHolder {
	
	public static BitmapUtils getBitmapUtils(Context context){
		String diskCachePath = Environment.getExternalStorageDirectory().getPath()+
				"/"+context.getPackageName()+"/cache/imgs";
		return new BitmapUtils(context, diskCachePath );
	}
}
