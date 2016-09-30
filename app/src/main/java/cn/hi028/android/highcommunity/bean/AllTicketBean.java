/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能：所有类型的优惠券<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/29<br>
 */
public class AllTicketBean extends BaseBean {
    String tid;
    float ticket_value;
    int use_to;
    String least;
    int end_time;
    String ticket_type;
    String use_name;


    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public float getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(float ticket_value) {
        this.ticket_value = ticket_value;
    }

    public String getLeast() {
        return least;
    }

    public void setLeast(String least) {
        this.least = least;
    }

    public int getUse_to() {
        return use_to;
    }

    public void setUse_to(int use_to) {
        this.use_to = use_to;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public String getUse_name() {
        return use_name;
    }

    public void setUse_name(String use_name) {
        this.use_name = use_name;
    }
}
