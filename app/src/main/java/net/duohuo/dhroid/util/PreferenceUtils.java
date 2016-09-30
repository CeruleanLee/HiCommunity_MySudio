package net.duohuo.dhroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 
 * 功能：sharePreferences工具类<br>
 * 作者：赵海<br>
 * 时间：2015年3月17日<br>
 * 版本：1.0<br>
 *
 */
public class PreferenceUtils {
	/**
	 * 获取String值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static String getPrefString(Context context, String key,
			final String defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}

	/**
	 * 保存String值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setPrefString(Context context, final String key,
			final String value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putString(key, value).commit();
	}

	/**
	 * 获取boolean值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static boolean getPrefBoolean(Context context, final String key,
			final boolean defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * 是否存在该键值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean hasKey(Context context, final String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).contains(
				key);
	}

	/**
	 * 保存boolean类型键值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setPrefBoolean(Context context, final String key,
			final boolean value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putBoolean(key, value).commit();
	}

	/**
	 * 保存int值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setPrefInt(Context context, final String key,
			final int value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}

	/**
	 * 获取int值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static int getPrefInt(Context context, final String key,
			final int defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * 保存浮点型值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param value
	 *            默认值
	 */
	public static void setPrefFloat(Context context, final String key,
			final float value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putFloat(key, value).commit();
	}

	/**
	 * 获取浮点型值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static float getPrefFloat(Context context, final String key,
			final float defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * 保存long类型值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setSettingLong(Context context, final String key,
			final long value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}

	/**
	 * 获取long类型值
	 * 
	 * @param context
	 * @param key
	 *            键
	 * @param defaultValue
	 *            值
	 * @return
	 */
	public static long getPrefLong(Context context, final String key,
			final long defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}

	/**
	 * 删除所有保存的键值
	 * 
	 * @param context
	 * @param p
	 */
	public static void clearPreference(Context context, SharedPreferences p) {
		if (p == null) {
			p = PreferenceManager.getDefaultSharedPreferences(context);
		}
		final Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}
}
