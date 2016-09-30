/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：小区对象<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-31<br>
 */
public class VallageBean extends BaseBean {
    private int id;//1152,
    private String name;// 锦江城市花园一期,
    private String address;// 锦江区喜树街780号

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    private String sortLetters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
