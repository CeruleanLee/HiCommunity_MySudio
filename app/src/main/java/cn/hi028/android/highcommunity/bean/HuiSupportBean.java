/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：惠生活对象<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-17<br>
 */
public class HuiSupportBean extends BaseBean {
    private int gid;// 27
    private String goods_name;// 大米
    private int number;// 10
    private float price;// 50.00;//
    private List<PicBean> pic;//
    private String describe;// 正宗东北大米
    private int sales;// 0
    private List<HuiSuppCommBean> goods_comment;//{ -
    private int count;// 1
    private float original_price;

    public float getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(float original_price) {
        this.original_price = original_price;
    }

    public int getCurrentPicPosition() {
        return currentPicPosition;
    }

    public void setCurrentPicPosition(int currentPicPosition) {
        this.currentPicPosition = currentPicPosition;
    }

    private int currentPicPosition = 0;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public List<HuiSuppCommBean> getGoods_comment() {
        return goods_comment;
    }

    public void setGoods_comment(List<HuiSuppCommBean> goods_comment) {
        this.goods_comment = goods_comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
