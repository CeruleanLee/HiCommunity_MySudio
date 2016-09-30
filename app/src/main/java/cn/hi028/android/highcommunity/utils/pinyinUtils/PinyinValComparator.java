/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.utils.pinyinUtils;

import java.util.Comparator;

import cn.hi028.android.highcommunity.bean.CityBean;
import cn.hi028.android.highcommunity.bean.VallageBean;

/**
 *@功能：小区比较对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-31<br>
 */
public class PinyinValComparator  implements Comparator<VallageBean> {

    public int compare(VallageBean o1, VallageBean o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }


}
