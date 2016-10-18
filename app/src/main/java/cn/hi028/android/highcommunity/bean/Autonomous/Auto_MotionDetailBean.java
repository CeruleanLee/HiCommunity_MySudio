package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/18 0018.
 * 说明：提案详情bean
 */
public class Auto_MotionDetailBean {

    /**
     * success : true
     * code : 2000
     * msg : 数据获取成功
     * data : [{"id":"1","title":"建议小区在公共区域新增几台饮水机","publish_man":"张三","create_time":"1470535638","content":"建议小区在公共区域新增几台饮水机建议小区在公共区域新增几台饮水机建议小区在公共区域新增几台饮水机","vote_percent":"0.8","isSuggest":"0"}]
     */

    private boolean success;
    private int code;
    private String msg;
    private List<MotionDetailDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<MotionDetailDataEntity> data) {
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

    public List<MotionDetailDataEntity> getData() {
        return data;
    }

    public static class MotionDetailDataEntity {
        /**
         * id : 1
         * title : 建议小区在公共区域新增几台饮水机
         * publish_man : 张三
         * create_time : 1470535638
         * content : 建议小区在公共区域新增几台饮水机建议小区在公共区域新增几台饮水机建议小区在公共区域新增几台饮水机
         * vote_percent : 0.8
         * isSuggest : 0
         */

        private String id;
        private String title;
        private String publish_man;
        private String create_time;
        private String content;
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

        public void setContent(String content) {
            this.content = content;
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

        public String getContent() {
            return content;
        }

        public String getVote_percent() {
            return vote_percent;
        }

        public String getIsSuggest() {
            return isSuggest;
        }
    }
}
