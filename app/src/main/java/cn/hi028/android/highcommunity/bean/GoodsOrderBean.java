/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

import cn.hi028.android.highcommunity.params.HuiSuppGdParams;

/**
 *@功能：直供物品定單<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-26<br>
 */
public class GoodsOrderBean extends  BaseBean{
  private String  tel;
    private String        real_name;
    private String     address;
    private float        zero_money;
    private float  ticket_value;
    private float       total_price;
    private String      out_trade_no;
    private float   total_fee;

    public float getReal_pri() {
        return getTotal_price()-getZero_money()-getTicket_value();
    }

    public void setReal_pri(float real_pri) {
        this.real_pri = real_pri;
    }

    private float real_pri;
    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    private String notify_url;

    public List<HuiSuppGdParams> getGoods() {
        return goods;
    }

    public void setGoods(List<HuiSuppGdParams> goods) {
        this.goods = goods;
    }

    private List<HuiSuppGdParams> goods;
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    private String      order_id;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getZero_money() {
        return zero_money;
    }

    public void setZero_money(float zero_money) {
        this.zero_money = zero_money;
    }

    public float getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(float ticket_value) {
        this.ticket_value = ticket_value;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public float getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(float total_fee) {
        this.total_fee = total_fee;
    }
}
