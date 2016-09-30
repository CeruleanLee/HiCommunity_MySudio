/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 *@功能：租房对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-30<br>
 */
public class TenementHouseBean extends BaseBean {
  String  village;// 带看撒娇的,小区
    String     size;// 三室二厅,
    String     content;// 降到了卡萨,  	租房简介
    String      tel;// 156465456,
    String     price;// 1520,
    String    ten;// 合租,
    String    id;// 5   租房id
    String   name;//	varchar	租房业主姓名
    String  floor;//	varchar	住房所在层数
    int bos;//	int	住房面积大小
   List<String> pic;
    int  type;//0,;简装，1为精装
int plot;

    public int getPlot() {
        return plot;
    }

    public void setPlot(int plot) {
        this.plot = plot;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getBos() {
        return bos;
    }

    public void setBos(int bos) {
        this.bos = bos;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    String   sortLetters;

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  List<String> getPic() {
        return pic;
    }

    public void setPic( List<String> pic) {
        this.pic = pic;
    }
}
