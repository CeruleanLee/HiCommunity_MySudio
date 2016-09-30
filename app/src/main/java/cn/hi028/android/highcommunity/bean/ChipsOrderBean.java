/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import android.text.TextUtils;

import cn.hi028.android.highcommunity.utils.CommonUtils;

/**
 * @功能：我的众筹订单对象<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-29<br>
 */
public class ChipsOrderBean extends BaseBean {
    private int order_id;//订单id
    private String order_num;//众筹订单号
    private float join_price;
    private int num;
    private String name;
    private String cover_pic;
    private float price;
    private int remain_time;
    private int state;
    private float total_price;
    private float zero_money;
    private float zero_real_money = 0.0f;
    private String ticket_id;
    private float ticket_value = 0.0f;

    public String getTicket_id() {
        if (TextUtils.isEmpty(ticket_id)) {
            ticket_id = "" + 0;
        }
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public float getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(float ticket_value) {
        this.ticket_value = ticket_value;
    }

    public float getZero_real_money() {
        return zero_real_money;
    }

    public void setZero_real_money(float zero_real_money) {
        this.zero_real_money = zero_real_money;
    }


    public float getReal_pri() {
        return CommonUtils.floatTo(getTotal_price() - zero_real_money - getTicket_value());
    }

    public void setReal_pri(float real_pri) {
        this.real_pri = real_pri;
    }

    private float real_pri;
    private AddressBean address;

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

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public float getJoin_price() {
        return join_price;
    }

    public void setJoin_price(float join_price) {
        this.join_price = join_price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRemain_time() {
        return remain_time;
    }

    public void setRemain_time(int remain_time) {
        this.remain_time = remain_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

	@Override
	public String toString() {
		return "ChipsOrderBean [order_id=" + order_id + ", order_num="
				+ order_num + ", join_price=" + join_price + ", num=" + num
				+ ", name=" + name + ", cover_pic=" + cover_pic + ", price="
				+ price + ", remain_time=" + remain_time + ", state=" + state
				+ ", total_price=" + total_price + ", zero_money=" + zero_money
				+ ", zero_real_money=" + zero_real_money + ", ticket_id="
				+ ticket_id + ", ticket_value=" + ticket_value + ", real_pri="
				+ real_pri + ", address=" + address + ", getTicket_id()="
				+ getTicket_id() + ", getTicket_value()=" + getTicket_value()
				+ ", getZero_real_money()=" + getZero_real_money()
				+ ", getReal_pri()=" + getReal_pri() + ", getTotal_price()="
				+ getTotal_price() + ", getZero_money()=" + getZero_money()
				+ ", getAddress()=" + getAddress() + ", getOrder_id()="
				+ getOrder_id() + ", getOrder_num()=" + getOrder_num()
				+ ", getJoin_price()=" + getJoin_price() + ", getNum()="
				+ getNum() + ", getName()=" + getName() + ", getCover_pic()="
				+ getCover_pic() + ", getPrice()=" + getPrice()
				+ ", getRemain_time()=" + getRemain_time() + ", getState()="
				+ getState() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
    
}
