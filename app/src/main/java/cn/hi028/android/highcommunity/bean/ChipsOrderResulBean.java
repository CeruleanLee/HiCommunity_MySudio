/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 *@功能：众筹订单返回结果<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-31<br>
 */
public class ChipsOrderResulBean extends BaseBean {
  public String   subject;
    public String           body;
    public String     out_trade_no;
    public float         total_fee;
    public String    notify_url;
    public int       ticket_id;
    public float     zero_money;
    public int           uid;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
