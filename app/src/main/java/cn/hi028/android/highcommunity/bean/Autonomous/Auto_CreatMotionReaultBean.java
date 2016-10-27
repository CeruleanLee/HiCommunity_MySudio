package cn.hi028.android.highcommunity.bean.Autonomous;

/**
 * Created by Lee_yting on 2016/10/27 0027.
 * 说明：
 */
public class Auto_CreatMotionReaultBean {


    /**
     * success : true
     * code : 2000
     * msg : 发布提案成功
     * data : {"id":3}
     */

    private boolean success;
    private int code;
    private String msg;
    private CreatMotionReaultDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(CreatMotionReaultDataEntity data) {
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

    public CreatMotionReaultDataEntity getData() {
        return data;
    }

    public static class CreatMotionReaultDataEntity {
        /**
         * id : 3
         */

        private int id;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
