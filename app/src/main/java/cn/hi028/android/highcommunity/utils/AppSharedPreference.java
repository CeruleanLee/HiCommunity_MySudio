package cn.hi028.android.highcommunity.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lee_yting on 2016/11/4 0004.
 * 说明：SharedPreferences   存储数据，比如是否第一次打开app
 */
public class AppSharedPreference {

    public static final void putBoolean(Context context, String sharedName, boolean value){

    }
    public static final void putValue(Context context,String sharedName,String key,Object value){
        SharedPreferences preferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(value instanceof String){
            editor.putString(key, value.toString());
        }else if(value instanceof Boolean){
            editor.putBoolean(key, Boolean.parseBoolean(value+""));
        }
        editor.commit();
    }
    public static final boolean getBooleanValue(Context context,String sharedName,String key,boolean defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }









}
