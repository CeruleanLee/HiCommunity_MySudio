package cn.hi028.android.highcommunity.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lee_yting on 2016/11/25 0025.
 * 说明：
 */
public class ActGoodsWXBean {

    /**
     * success : true
     * code : 2000
     * msg : 下单成功
     * data : {"appid":"wx4ef53eabd1de60a1","noncestr":"97063b79bf6b738","package":"Sign=WXPay","partnerid":"1300211601","prepayid":"wx20161125115851f21feeab360179550092","timestamp":"1480046331","sign":"BFA882DA6541F408D5C9548639812431"}
     */

    private boolean success;
    private int code;
    private String msg;
    private ActGoodsWXDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(ActGoodsWXDataEntity data) {
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

    public ActGoodsWXDataEntity getData() {
        return data;
    }

    public static class ActGoodsWXDataEntity {
        /**
         * appid : wx4ef53eabd1de60a1
         * noncestr : 97063b79bf6b738
         * package : Sign=WXPay
         * partnerid : 1300211601
         * prepayid : wx20161125115851f21feeab360179550092
         * timestamp : 1480046331
         * sign : BFA882DA6541F408D5C9548639812431
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppid() {
            return appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getSign() {
            return sign;
        }
    }
}
