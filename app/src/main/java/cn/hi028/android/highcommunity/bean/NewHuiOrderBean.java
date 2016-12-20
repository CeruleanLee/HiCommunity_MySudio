/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

import cn.hi028.android.highcommunity.params.NewHuiSuppGdParams;

/**
 *@功能：v2.0订单对象<br>
 *@作者： Lee_yting<br>
 *@版本：2.0<br>
 *@时间：2016/12/18<br>
 */
public class NewHuiOrderBean extends BaseBean{
    /**
     * id : 46
     * order_num : 20161220112847126850
     * create_time : 1482204527
     * pay_time : 0
     * send_time : 0
     * finish_time : 0
     * comment_time : 0
     * status : 待付款
     * pay_type : 1
     * old_fee : 11.00
     * total_fee : 0.01
     * zero_money : 0.00
     * ticket_val : 0.00
     * mark : 微信
     * consign : 张大山
     * address : 成都市青羊区万达广场
     * tel : 13698525225
     * merchant : 德克士
     * list : [{"goods_id":"33","pic":"upload/new-goods/cover_pic/cover_pic_201612091720426342.png","name":"按时打算","standard_name":"按时打","goods_price":"11.00","goods_number":"1"}]
     */
    private int id;//订单id
    private String order_num;//订单编号
    private int status;
    private float old_fee;//合计金额
    private float total_fee;//实付款(当且仅当status不等于-1和0时返回)
    private String merchant;//商家名称
    private int count;//商品总数量
//这些是在订单详情中会返回
    private int create_time;
    private String pay_time;
    private String send_time;
    private String finish_time;
    private String comment_time;
    private String pay_type;
    private String zero_money;
    private String ticket_val;
    private String mark;
    private String consign;
    private String address;
    private String tel;
    private List<NewHuiSuppGdParams> list;

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getZero_money() {
        return zero_money;
    }

    public void setZero_money(String zero_money) {
        this.zero_money = zero_money;
    }

    public String getTicket_val() {
        return ticket_val;
    }

    public void setTicket_val(String ticket_val) {
        this.ticket_val = ticket_val;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getConsign() {
        return consign;
    }

    public void setConsign(String consign) {
        this.consign = consign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getOld_fee() {
        return old_fee;
    }

    public void setOld_fee(float old_fee) {
        this.old_fee = old_fee;
    }

    public float getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(float total_fee) {
        this.total_fee = total_fee;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NewHuiSuppGdParams> getList() {
        return list;
    }

    public void setList(List<NewHuiSuppGdParams> list) {
        this.list = list;
    }
}
