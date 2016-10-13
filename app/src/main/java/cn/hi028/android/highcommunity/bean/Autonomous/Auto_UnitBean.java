package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/13 0013.
 * 说明：根据楼栋获取单元数据Bean
 */
public class Auto_UnitBean {


    /**
     * success : true
     * code : 2000
     * msg : 获取单元成功
     * data : [{"unit_id":"5","unit_name":"1单元"},{"unit_id":"6","unit_name":"2单元"},{"unit_id":"7","unit_name":"3单元"}]
     */

    private boolean success;
    private String code;
    private String msg;
    private List<UnitDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<UnitDataEntity> data) {
        this.data = data;
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

    public List<UnitDataEntity> getData() {
        return data;
    }

    public static class UnitDataEntity {
        /**
         * unit_id : 5
         * unit_name : 1单元
         */

        private String unit_id;
        private String unit_name;

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getUnit_id() {
            return unit_id;
        }

        public String getUnit_name() {
            return unit_name;
        }
    }
}
