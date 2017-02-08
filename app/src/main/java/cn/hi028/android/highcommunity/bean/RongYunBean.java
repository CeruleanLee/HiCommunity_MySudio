package cn.hi028.android.highcommunity.bean;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2017/2/8 0008
 */
public class RongYunBean {


    /**
     * code : 200
     * userId : 122
     * token : j46k2TSHWSPUnRSFLiro/QALk1FW9Y7lTlXDMZIcV/rVf30DMv6ahywSdQm6W1wcR+tgSxKveQllWuRVDYa3ug==
     */

    private int code;
    private String userId;
    private String token;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }


    @Override
    public String toString() {
        return "RongYunBean{" +
                "code=" + code +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
