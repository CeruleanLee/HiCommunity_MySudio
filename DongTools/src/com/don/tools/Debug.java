package com.don.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
 * 
 * @author dong
 * @category 调试信息，用于输出调试信息
 * 
 * @version
 * 
 */
public class Debug {

	private static String default_tag = "Education";
//	protected static boolean debugMode = NativeParametersFunc.IsDebug();
	public static boolean debugMode = true;

	/**
	 * 判断是否处于调试模式
	 * 
	 * @return 是否处于调试模式
	 */
	public static boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * 输出警告信息
	 * 
	 * @param source
	 *            来源
	 * @param message
	 *            错误信息
	 */
	public static void warning(String tag, String source, String message) {
		if (!debugMode)
			return;
		Log.w(tag, source + " - " + message);
		Exception e = new Exception(source + " - " + message);
		e.printStackTrace();
	}

	/**
	 * 输出警告信息
	 * 
	 * @param message
	 *            警告信息
	 */
	public static void warning(String tag, String message) {
		if (!debugMode)
			return;
		Log.w(tag, message);
	}

	/**
	 * 输出调试信息
	 * 
	 * @param message
	 *            调试信息
	 */
	public static void print(String tag, String message) {
		if (!debugMode)
			return;
		Log.v(tag, message);
	}

	/**
	 * 输出错误信息
	 * 
	 * @param message
	 *            错误信息
	 */
	public static void error(String tag, String message) {
		Log.e(tag, message);
		Exception e = new Exception(message);
		e.printStackTrace();
	}

	/**
	 * 输出详细信息
	 * 
	 * @param method
	 *            方法名
	 * @param message
	 *            详细信息
	 */
	public static void verbose(String tag, String method, String message) {
		if (!debugMode)
			return;
		Log.v(tag, method + " - " + message);
		if (!debugMode)
			return;
	}

	/**
	 * 输出详细信息
	 * 
	 * @param message
	 *            详细信息
	 */
	public static void verbose(String tag, String message) {
		if (!debugMode)
			return;
		Log.v(tag, message);
	}

	/**
	 * 输出详细信息
	 * 
	 * @param message
	 *            详细信息
	 */
	public static void verbose(String message) {
		if (!debugMode)
			return;
		Log.v(default_tag, message);
	}

	public static void info(String tag, String message) {
		if (!debugMode) {
			return;
		}

	}

	/**
	 * 退出当前程序
	 */
	public static void forceExit() {
		if (!debugMode)
			return;
		System.exit(0);
	}

	/**
	 * 显示剩余内存
	 */
	public static void displayAvailMemory(Context context, String tag) {

		StringBuffer momoryInfo = new StringBuffer();
		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(outInfo);
		if (tag != null) {
			momoryInfo.append(tag);
		}
		Long m = outInfo.availMem >> 20;

		momoryInfo.append("================剩余内存:---->")
				.append((outInfo.availMem >> 10) / 1024).append("M  ")
				.append((outInfo.availMem >> 10) % 1024).append("k")
				.append("----------即总共").append(outInfo.availMem >> 10)
				.append("k");

		Log.i("momory", momoryInfo.toString());
	}
}
