/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 *@功能：城市对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-30<br>
 */
public class CityBean extends BaseBean
{
  private int  city_code;// "5101",
    private String  name;// "成都市"

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    private String sortLetters;

    public int getCity_code() {
        return city_code;
    }

    public void setCity_code(int city_code) {
        this.city_code = city_code;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
