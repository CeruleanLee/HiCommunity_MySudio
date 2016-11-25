package cn.hi028.android.highcommunity.bean;

/**
 * 微信支付对象
 * Created by 赵海 on 2016/3/17.
 */
public class WpayBean extends  BaseBean {
 private String   appid	;//string	应用APPID
    private String    noncestr		;//string	随机字符串
    private String     partnerid	;//	string	商户id
    private String     prepayid		;//string	预支付id
    private String     timestamp	;//	string	时间戳
    private String    sign		;//string	签名
    private String packages;


    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

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

    @Override
    public String toString() {
        return "WpayBean{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                ", packages='" + packages + '\'' +
                '}';
    }
}
