/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

import cn.hi028.android.highcommunity.utils.CommonUtils;

/**
 * @功能：获取优惠券、零钱数据<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/22<br>
 */
public class CouponBean extends BaseBean {
    float total;
    float money;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    private String order_id;
    List<TicketBean> tickets;
    private float real_price;//string	实际付款	yes
    private float total_price;//string	应付款	yes
    private String ticket_id;//int	优惠券id	      no

    private float ticket_value = 0.0f;//int	优惠券id
    private float zero_money = 0.0f;//	string	用户抵用零钱包金额	      no

    public float getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(float ticket_value) {

        this.ticket_value = CommonUtils.floatTo(ticket_value);
    }

    public float getZero_money() {
        return zero_money;
    }

    public void setZero_money(float zero_money) {
        this.zero_money = zero_money;
    }

    public float getReal_price() {
        return getTotal() - getZero_money() - getTicket_value();
    }

    public void setReal_price(float real_price) {
        this.real_price = real_price;
    }

    public float getTotal_price() {
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public List<TicketBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketBean> tickets) {
        this.tickets = tickets;
    }
}
