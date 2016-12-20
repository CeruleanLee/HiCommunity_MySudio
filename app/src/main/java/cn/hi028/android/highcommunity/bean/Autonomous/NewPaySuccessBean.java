package cn.hi028.android.highcommunity.bean.Autonomous;

import com.google.gson.annotations.SerializedName;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/20 0020
 */
public class NewPaySuccessBean {

    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"subject":"东北大米","body":"嗨社区-商家联盟商品","out_trade_no":"20161220094105788925975126","total_fee":"75.0","notify_url":"http://028hi.cn/union_notify.php","old_fee":"75.0","ticket_id":"null","zero_money":"0.0"}
     */

    private boolean success;
    private int code;
    private String msg;
    private PaySuccessDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(PaySuccessDataEntity data) {
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

    public PaySuccessDataEntity getData() {
        return data;
    }

    public static class PaySuccessDataEntity {
        /**
         * subject : 东北大米
         * body : 嗨社区-商家联盟商品
         * out_trade_no : 20161220094105788925975126
         * total_fee : 75.0
         * notify_url : http://028hi.cn/union_notify.php
         * old_fee : 75.0
         * ticket_id : null
         * zero_money : 0.0
         */

        private String subject;
        private String body;
        private String out_trade_no;
        private String total_fee;
        private String notify_url;
        private String old_fee;
        private String ticket_id;
        private String zero_money;

        /**
         * 微信支付的回调
         * appid : wx4ef53eabd1de60a1
         * noncestr : 5755718596d8858
         * package : Sign=WXPay
         * partnerid : 1300211601
         * prepayid : wx201612200946175902e94d500215191712
         * timestamp : 1482198377
         * sign : 7F089245C7DCC1C1CF02F3E69853456E
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;



        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public void setOld_fee(String old_fee) {
            this.old_fee = old_fee;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        public void setZero_money(String zero_money) {
            this.zero_money = zero_money;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public String getOld_fee() {
            return old_fee;
        }

        public String getTicket_id() {
            return ticket_id;
        }

        public String getZero_money() {
            return zero_money;
        }
    }
}
