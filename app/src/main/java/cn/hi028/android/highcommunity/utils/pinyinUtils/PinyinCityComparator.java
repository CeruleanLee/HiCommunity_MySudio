/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.utils.pinyinUtils;

import java.util.Comparator;

import cn.hi028.android.highcommunity.bean.VallageCityBean;

/**
 *@功能：<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-30<br>
 */
public class PinyinCityComparator implements Comparator<VallageCityBean> {

    public int compare(VallageCityBean o1, VallageCityBean o2) {
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
