package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee on 2016/10/20.
 * 说明：
 */
public class AutoDetail_QuestionVotedBean {


    /**
     * success : true
     * code : 2000
     * msg : 获取数据成功
     * data : {"radio":["14"],"checkbox":["17","18"]}
     */

    private boolean success;
    private String code;
    private String msg;
    private QuestionVotedDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(QuestionVotedDataEntity data) {
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

    public QuestionVotedDataEntity getData() {
        return data;
    }

    public static class QuestionVotedDataEntity {
        /**
         * radio : ["14"]
         * checkbox : ["17","18"]
         */

        private List<String> radio;
        private List<String> checkbox;

        public void setRadio(List<String> radio) {
            this.radio = radio;
        }

        public void setCheckbox(List<String> checkbox) {
            this.checkbox = checkbox;
        }

        public List<String> getRadio() {
            return radio;
        }

        public List<String> getCheckbox() {
            return checkbox;
        }
    }
}
