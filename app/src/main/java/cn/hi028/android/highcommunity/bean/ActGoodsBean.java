package cn.hi028.android.highcommunity.bean;

/**
 * Created by Lee_yting on 2016/11/25 0025.
 * 说明：扫一扫活动商品bean
 */
public class ActGoodsBean {


    /**
     * success : true
     * code : 2000
     * msg : 下单成功
     * data : {"zero_money":0,"ticket_value":0,"order_id":186,"total_price":"3.3","out_trade_no":"2016112512042011032","total_fee":"3.3","status":0,"notify_url":"http://028hi.cn/Alipay/notify_url_goods.php"}
     */

    private boolean success;
    private int code;
    private String msg;
    private ActGoodsDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(ActGoodsDataEntity data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ActGoodsDataEntity getData() {
        return data;
    }

    public static class ActGoodsDataEntity {
        /**
         * zero_money : 0
         * ticket_value : 0
         * order_id : 186
         * total_price : 3.3
         * out_trade_no : 2016112512042011032
         * total_fee : 3.3
         * status : 0
         * notify_url : http://028hi.cn/Alipay/notify_url_goods.php
         */

        private int zero_money;
        private int ticket_value;
        private int order_id;
        private String total_price;
        private String out_trade_no;
        private String total_fee;
        private int status;
        private String notify_url;

        public void setZero_money(int zero_money) {
            this.zero_money = zero_money;
        }

        public void setTicket_value(int ticket_value) {
            this.ticket_value = ticket_value;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public int getZero_money() {
            return zero_money;
        }

        public int getTicket_value() {
            return ticket_value;
        }

        public int getOrder_id() {
            return order_id;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public int getStatus() {
            return status;
        }

        public String getNotify_url() {
            return notify_url;
        }
    }
}
