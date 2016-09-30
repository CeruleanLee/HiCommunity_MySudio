package com.don.tools;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.cookie.Cookie;

import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * 
 * @author dong
 * @category 设置Cookie下载图片
 */
public class MyImageDownloader extends BaseImageDownloader {
	PersistentCookieStore mPersistentCookieStore = null;

	public MyImageDownloader(Context context, int connectTimeout,
			int readTimeout) {
		super(context, connectTimeout, readTimeout);
		// TODO Auto-generated constructor stub
	}

	public MyImageDownloader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyImageDownloader(Context context,
			PersistentCookieStore persistentCookieStore) {
		super(context);
		// TODO Auto-generated constructor stub
		mPersistentCookieStore = persistentCookieStore;
	}

	@Override
	protected HttpURLConnection createConnection(String url, Object extra)
			throws IOException {
		// TODO Auto-generated method stub
		HttpURLConnection connection = super.createConnection(url, extra);
		mPersistentCookieStore=BpiHttpClient.mPersistentCookieStore;
		if (mPersistentCookieStore != null) {
			StringBuffer stringbuffer=new StringBuffer();
			for(int i=0;i<mPersistentCookieStore.getCookies().size();i++)
			{
				Cookie cookie=mPersistentCookieStore.getCookies().get(i);
				if(!stringbuffer.toString().equals(""))
				{
					stringbuffer.append(";");
				}
				stringbuffer.append(cookie.getName()+"="+cookie.getValue());
			}
//			System.out.println("dong toString:"+stringbuffer.toString());
			connection.setRequestProperty("Cookie",
					stringbuffer.toString());
		}
		return connection;
	}
	
}
