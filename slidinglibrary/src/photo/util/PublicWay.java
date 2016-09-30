package photo.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放所有的list在最后退出时一起关闭
 *
 * @author king
 * @version 2014年10月18日  下午11:50:49
 * @QQ:595163260
 */
public class PublicWay {
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static int num = 6;

}
