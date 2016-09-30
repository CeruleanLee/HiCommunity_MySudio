/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 *@功能：小区选择对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-04<br>
 */
public class VallageCityBean extends BaseBean{
    VallageBean vallage;
      CityBean city;
    String sortLetters;
    int type;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public VallageBean getVallage() {
        return vallage;
    }

    public void setVallage(VallageBean vallage) {
        this.vallage = vallage;
    }

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }
}
