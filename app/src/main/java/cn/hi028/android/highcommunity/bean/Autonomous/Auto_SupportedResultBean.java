package cn.hi028.android.highcommunity.bean.Autonomous;

/**
 * Created by Lee_yting on 2016/10/27 0027.
 * 说明：
 */
public class Auto_SupportedResultBean {


    /**
     * success : true
     * code : 2000
     * msg : 投票成功
     * data : {"vote_percent":0.2}
     */

    private boolean success;
    private int code;
    private String msg;
    private SupportedResultDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(SupportedResultDataEntity data) {
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

    public SupportedResultDataEntity getData() {
        return data;
    }

    public static class SupportedResultDataEntity {
        /**
         * vote_percent : 0.2
         */

        private double vote_percent;

        public void setVote_percent(double vote_percent) {
            this.vote_percent = vote_percent;
        }

        public double getVote_percent() {
            return vote_percent;
        }
    }
}
