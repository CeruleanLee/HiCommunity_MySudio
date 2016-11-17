package cn.hi028.android.highcommunity.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Lee_yting on 2016/11/17 0017.
 * 说明：应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    public AppManager() {}

    public static AppManager getAppManager(){
        if(instance==null){
            instance=new AppManager();
        }
        return instance;
    }

    //添加Activity到堆栈
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void finishAllActivity(){
        for(int i=0;i<activityStack.size();i++){
            if(activityStack.get(i) != null){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
