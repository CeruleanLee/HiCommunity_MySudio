package cn.hi028.android.highcommunity.utils;

import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.os.Environment;

public class MBitmapHolder {
	
	public static BitmapUtils getBitmapUtils(Context context){
		String diskCachePath = Environment.getExternalStorageDirectory().getPath()+
				"/"+context.getPackageName()+"/cache/imgs";
		return new BitmapUtils(context, diskCachePath );
	}
}
