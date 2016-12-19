/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.params;

import net.duohuo.dhroid.util.ListUtils;

import java.util.List;

import cn.hi028.android.highcommunity.bean.GdCarBean;

/**
 * @功能：惠生活物品直供订单参数<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-21<br>
 */
public class HuiSuppOrderParams extends BaseParams {
    private String ticket_id;//int	优惠券id	      no
    private float zero_money = 0.0f;//	string	用户抵用零钱包金额	      no

    private int uid;//int	用户id	yes
    private String aid;//	int	收货地址id	yes
    private float real_price;//string	实际付款	yes
    private float total_price;//string	应付款	yes
    private float ticket_value = 0.0f;//int	优惠券id
    List<GdCarBean> goods;        //商品信息数组	    yes

    private int num;

    public float getTicket_value() {return ticket_value;}

    public void setTicket_value(float ticket_value) {
        this.ticket_value = ticket_value;
    }

    public List<GdCarBean> getGoods() {
        return goods;
    }
    public void setGoods(List<GdCarBean> goods) {
        this.goods = goods;
    }

    public int getNum() {
        num = 0;
        for (int i = 0; i < ListUtils.getSize(goods); i++) {
            num = num + goods.get(i).getNum();
        }
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public float getReal_price() {
        return getTotal_price() - getZero_money() - getTicket_value();
    }

    public void setReal_price(float real_price) {
        this.real_price = real_price;
    }

    public float getTotal_price() {
        total_price = 0;
        for (int i = 0; i < ListUtils.getSize(goods); i++) {
            total_price = total_price + goods.get(i).getTotal_pri();
        }
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public float getZero_money() {
        return zero_money;
    }

    public void setZero_money(float zero_money) {
        this.zero_money = zero_money;
    }

    public List<GdCarBean> getGoods(List<GdCarBean> data) {
        return goods;
    }
}
