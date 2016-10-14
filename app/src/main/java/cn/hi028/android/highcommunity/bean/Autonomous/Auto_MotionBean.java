package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/14 0014.
 * 说明：
 */
public class Auto_MotionBean {


    /**
     * success : true
     * code : 2000
     * msg : 数据获取成功
     * data : [{"id":"1","title":"建议小区在公共区域新增几台饮水机","publish_man":"张三","create_time":"1470535638","vote_percent":"0.8","isSuggest":"0"},{"id":"2","title":"全员参与投票选举业主委员会委员","publish_man":"张启明","create_time":"1470747811","vote_percent":"1.2","isSuggest":"1"}]
     */

    private boolean success;
    private int code;
    private String msg;
    private List<MotionDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<MotionDataEntity> data) {
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

    public List<MotionDataEntity> getData() {
        return data;
    }

    public static class MotionDataEntity {
        /**
         * id : 1
         * title : 建议小区在公共区域新增几台饮水机
         * publish_man : 张三
         * create_time : 1470535638
         * vote_percent : 0.8
         * isSuggest : 0
         */

        private String id;
        private String title;
        private String publish_man;
        private String create_time;
        private String vote_percent;
        private String isSuggest;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPublish_man(String publish_man) {
            this.publish_man = publish_man;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setVote_percent(String vote_percent) {
            this.vote_percent = vote_percent;
        }

        public void setIsSuggest(String isSuggest) {
            this.isSuggest = isSuggest;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getPublish_man() {
            return publish_man;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getVote_percent() {
            return vote_percent;
        }

        public String getIsSuggest() {
            return isSuggest;
        }
    }
}
