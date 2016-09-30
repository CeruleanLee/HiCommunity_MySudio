/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 *@功能：紧急保修<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-11<br>
 */
public class RepairJJBean extends BaseBean{
   private String  name;// 开锁
    private String   tel;// 1564565456
    private int  type;// 1
private String sortLetters;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
