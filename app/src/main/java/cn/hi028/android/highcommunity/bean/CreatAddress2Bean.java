package cn.hi028.android.highcommunity.bean;

/**
 * Created by Lee_yting on 2016/12/5 0005.
 * 说明：
 */
public class CreatAddress2Bean {


    /**
     * success : true
     * code : 2000
     * msg : 新建地址成功
     * data : {"id":8}
     */

    private boolean success;
    private int code;
    private String msg;
    private CreatAddress2DataEntity data;

    @Override
    public String toString() {
        return "CreatAddress2Bean{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(CreatAddress2DataEntity data) {
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

    public CreatAddress2DataEntity getData() {
        return data;
    }

    public static class CreatAddress2DataEntity {
        /**
         * id : 8
         */

        private int id;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "CreatAddress2DataEntity{" +
                    "id=" + id +
                    '}';
        }
    }
}
