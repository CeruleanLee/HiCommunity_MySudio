package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/20 0020.
 * 说明：系统消息bean
 */
public class SystemMessageBean {


    /**
     * success : true
     * code : 2000
     * msg : 获取系统消息成功
     * data : [{"title":"订单已签收","pic":"upload/goods/goods_201609011648369366.png","content":"诺亚方舟羊犀店","order_num":"2016101409274221939","create_time":"1476950219","type":"1"}]
     */

    private boolean success;
    private String code;
    private String msg;
    private List<SystemMsgDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<SystemMsgDataEntity> data) {
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

    public List<SystemMsgDataEntity> getData() {
        return data;
    }

    public static class SystemMsgDataEntity {
        /**
         * title : 订单已签收
         * pic : upload/goods/goods_201609011648369366.png
         * content : 诺亚方舟羊犀店
         * order_num : 2016101409274221939
         * create_time : 1476950219
         * type : 1
         */

        private String title;
        private String pic;
        private String content;
        private String order_num;
        private String create_time;
        private String type;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public String getPic() {
            return pic;
        }

        public String getContent() {
            return content;
        }

        public String getOrder_num() {
            return order_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getType() {
            return type;
        }
    }
}
