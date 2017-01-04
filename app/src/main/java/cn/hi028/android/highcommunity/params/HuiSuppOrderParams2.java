/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.params;

/**
 * @功能：v2.0惠生活物品直供订单参数<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/18<br>
 */
public class HuiSuppOrderParams2 extends BaseParams {

    private String token;//用户token
    private int ticket_id;//int	优惠券id	      no
    private float zero_money = 0.0f;//	string	用户抵用零钱包金额	      no
    private int pay_type;//支付类型(1=>微信,2=>支付宝)
    private int address_id;//收货地址id
    private float total_amount;//合计金额
    private float total_fee;//实付款
    private String mark;//备注
    private float ticket_value = 0.0f;//	优惠券value
    /**
     * cart_ids和order_num中有且仅传一个. 当传cart_ids表示从购物车列表跳转至支付界面;当传order_num时表示从待付款列表跳转至支付界面
     */
    private String cart_ids;//购物车id,以逗号间隔
    private String order_num;//订单号

    @Override
    public String toString() {
        return "HuiSuppOrderParams2{" +
                "token='" + token + '\'' +
                ", ticket_id='" + ticket_id + '\'' +
                ", zero_money=" + zero_money +
                ", pay_type=" + pay_type +
                ", address_id=" + address_id +
                ", total_amount=" + total_amount +
                ", total_fee=" + total_fee +
                ", mark='" + mark + '\'' +
                ", ticket_value=" + ticket_value +
                ", cart_ids='" + cart_ids + '\'' +
                ", order_num='" + order_num + '\'' +
                '}';
    }

    public float getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(float ticket_value) {
        this.ticket_value = ticket_value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public float getZero_money() {
        return zero_money;
    }

    public void setZero_money(float zero_money) {
        this.zero_money = zero_money;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public float getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(float total_fee) {
        this.total_fee = total_fee;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(String cart_ids) {
        this.cart_ids = cart_ids;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
}
