/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

import cn.hi028.android.highcommunity.params.HuiSuppGdParams;

/**
 *@功能：订单对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-27<br>
 */
public class HuiOrderBean extends BaseBean{
  private String   order_num;
    private int      status;
    private int    id;
    private float   real_price;
    private int  sum;
      private List<HuiSuppGdParams> goods;
    private float   ticket_value;
    private float         total_price;
    private float    zero_money;
    private int          create_time;
    private String      tel;
    private String      real_name;
    private String address;

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

    public float getZero_money() {
        return zero_money;
    }

    public void setZero_money(float zero_money) {
        this.zero_money = zero_money;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

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

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getReal_price() {
        return real_price;
    }

    public void setReal_price(float real_price) {
        this.real_price = real_price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<HuiSuppGdParams> getGoods() {
        return goods;
    }

    public void setGoods(List<HuiSuppGdParams> goods) {
        this.goods = goods;
    }
}
