/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 *@功能：县区对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-31<br>
 */
public class CountyBean extends BaseBean{
 private int   city_code;// 510104,
    private String   name;// 锦江区

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
