/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 *@功能：众筹商品对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-15<br>
 */
public class ChipsBean extends BaseBean {
  private int  rid	;//int	众筹商品id
    private String    cover_pic	;//string	众筹商品封面图片
    private String     r_name;//	string	众筹商品名称
    private int  people_num;//	int	参与人数
    private float  current_price;//	float	当前价格
    private List<String> r_pic;//[ -
    private int storage;// 89
    private String  end_time;// 708791
    private List<PointBean>  point;//
    public String  r_describe;// 新鲜的双流草莓,香甜可口,入口即化 规格:500g/篮
    public int limitNum;

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public List<String> getR_pic() {
        return r_pic;
    }

    public void setR_pic(List<String> r_pic) {
        this.r_pic = r_pic;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<PointBean> getPoint() {
        return point;
    }

    public void setPoint(List<PointBean> point) {
        this.point = point;
    }

    public String getR_describe() {
        return r_describe;
    }

    public void setR_describe(String r_describe) {
        this.r_describe = r_describe;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public int getPeople_num() {
        return people_num;
    }

    public void setPeople_num(int people_num) {
        this.people_num = people_num;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }
}
