package com.don.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linxd
 * @category 时间格式转化管理
 */
public class TimeFormat {
    /**
     * 获得当前时间
     *
     * @return 毫秒数
     */
    public static long getTheTime() {
        return System.currentTimeMillis();
    }

    /**
     * 将毫秒数换算成 MM/dd HH:mm:ss
     *
     * @param s
     * @return
     */
    public static String TimeForamt_Refresh(long s) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm:ss");
        return formatter.format(s);
    }

    /**
     * 将毫秒数换算成YYYY.MM.dd
     *
     * @param s
     * @return
     */
    public static String TimeForamt_YYYY(long s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(s);
    }

    /**
     * 将秒数换算成x天x时x分x秒x毫秒
     *
     * @param s 毫秒数
     * @return
     * @author dong
     */
    public static String TimeFormat(long s) {
        int ss = 1;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = s / dd;
        long hour = (s - day * dd) / hh;
        long minute = (s - day * dd - hour * hh) / mi;
        long second = (s - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = s - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        // String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" +
        // milliSecond;
        // strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" +
        // strMilliSecond;
        // return strDay + "天" + strHour + "小时" + strMinute + "分" + strSecond +
        // "秒" + strMilliSecond;
        return strDay + "天" + strHour + "小时" + strMinute + "分" + strSecond
                + "秒";
    }


    /**
     * 毫秒数格式转成XX年XX月XX日 HH:MM:SSS dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_time(long s) {
        Date date = new Date(s);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy年MM月dd日 HH:mm:ss");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 毫秒数格式转成YYYY-MM-dd HH:MM dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_time2(long s) {
        Date date = new Date(s);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 毫秒数格式转成xxXX年XX月XX日 dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_DATE(long s) {
        Date date = new Date(s);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 秒数格式转成xxXX年XX月XX日 dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_date(long s) {
        Date date = new Date(s * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 格式转成xxXX年XX月XX日零时 dong
     *
     * @param s
     * @return 毫秒数
     * @category 回转
     */
    public static long TimedateFormat_DATEParse(long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(s);
        String str = simpleDateFormat.format(date);
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 毫秒数格式转成XX年XX月XX日 dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat(long s) {
        Date date = new Date(s);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 毫秒数格式转成HH:mm:ss dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_HH(long s) {
        Date date = new Date(s);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 秒数格式转成XXxx-XX-XX dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_short(long s) {
        Date date = new Date(s * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 秒数格式转成MM/dd HH:mm dong
     *
     * @param s
     * @return
     */
    public static String TimedateFormat_shortTime(long s) {
        Date date = new Date(s * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String str = simpleDateFormat.format(date);
        return str;
    }

}
