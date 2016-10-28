package cn.hi028.android.highcommunity.bean.Autonomous;

/**
 * Created by Lee_yting on 2016/10/28 0028.
 * 说明：
 */
public class CommonHttpResultBean {


    /**
     * success : true
     * code : 2000
     * msg : 删除成功
     */

    private boolean success;
    private String code;
    private String msg;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
