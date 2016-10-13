package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/12 0012.
 * 说明：
 */
public class Auto_NoticeListBean {


    /**
     * success : true
     * code : 2000
     * msg : 获取公告成功
     * data : [{"id":"1","title":"商业街社区\u201c八\u2022一\u201d开展2016年夏季征兵宣传工作","create_time":"1471320667"},{"id":"3","title":"关于管理公约和议事规则征求意见的公告","create_time":"1471341394"},{"id":"4","title":"软件推送提示测试","create_time":"1471342448"},{"id":"5","title":"公告提示信号测试","create_time":"1471353814"}]
     */

    private boolean success;
    private String code;
    private String msg;
    private List<NoticeListDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<NoticeListDataEntity> data) {
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

    public List<NoticeListDataEntity> getData() {
        return data;
    }

    public static class NoticeListDataEntity {
        /**
         * id : 1
         * title : 商业街社区“八•一”开展2016年夏季征兵宣传工作
         * create_time : 1471320667
         */

        private String id;
        private String title;
        private String create_time;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCreate_time() {
            return create_time;
        }
    }
}
