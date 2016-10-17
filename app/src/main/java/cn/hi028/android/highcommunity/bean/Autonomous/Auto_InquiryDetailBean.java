package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/17 0017.
 * 说明：询问详情
 */
public class Auto_InquiryDetailBean {

    /**
     * success : true
     * code : 2000
     * msg : 数据获取成功
     * data : {"id":"22","create_time":"1470536235","content":"这一季度的收支报告什么时候出来","name":"黄凯","head_pic":"img/lx.png","reply":[{"id":78,"from_id":11,"pic":"img/lx.png","from_name":"黄凯","content":"就快出来了,请耐心等待","sub_reply":[{"from_id":11,"pic":"img/lx.png","from_name":"黄凯","to_name":"黄凯","content":"恩,好的","reply_time":1470536286},{"from_id":12,"pic":"img/lx.png","from_name":"纪国新","to_name":"黄凯","content":"sdfsd","reply_time":1470622814}],"reply_time":1470536269}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private InquiryDetailDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(InquiryDetailDataEntity data) {
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

    public InquiryDetailDataEntity getData() {
        return data;
    }

    public static class InquiryDetailDataEntity {
        /**
         * id : 22
         * create_time : 1470536235
         * content : 这一季度的收支报告什么时候出来
         * name : 黄凯
         * head_pic : img/lx.png
         * reply : [{"id":78,"from_id":11,"pic":"img/lx.png","from_name":"黄凯","content":"就快出来了,请耐心等待","sub_reply":[{"from_id":11,"pic":"img/lx.png","from_name":"黄凯","to_name":"黄凯","content":"恩,好的","reply_time":1470536286},{"from_id":12,"pic":"img/lx.png","from_name":"纪国新","to_name":"黄凯","content":"sdfsd","reply_time":1470622814}],"reply_time":1470536269}]
         */

        private String id;
        private String create_time;
        private String content;
        private String name;
        private String head_pic;
        private List<InquiryDetailReplyEntity> reply;

        public void setId(String id) {
            this.id = id;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public void setReply(List<InquiryDetailReplyEntity> reply) {
            this.reply = reply;
        }

        public String getId() {
            return id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public List<InquiryDetailReplyEntity> getReply() {
            return reply;
        }

        public static class InquiryDetailReplyEntity {
            /**
             * id : 78
             * from_id : 11
             * pic : img/lx.png
             * from_name : 黄凯
             * content : 就快出来了,请耐心等待
             * sub_reply : [{"from_id":11,"pic":"img/lx.png","from_name":"黄凯","to_name":"黄凯","content":"恩,好的","reply_time":1470536286},{"from_id":12,"pic":"img/lx.png","from_name":"纪国新","to_name":"黄凯","content":"sdfsd","reply_time":1470622814}]
             * reply_time : 1470536269
             */

            private int id;
            private int from_id;
            private String pic;
            private String from_name;
            private String content;
            private int reply_time;
            private List<SubReplyEntity> sub_reply;

            public void setId(int id) {
                this.id = id;
            }

            public void setFrom_id(int from_id) {
                this.from_id = from_id;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public void setFrom_name(String from_name) {
                this.from_name = from_name;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setReply_time(int reply_time) {
                this.reply_time = reply_time;
            }

            public void setSub_reply(List<SubReplyEntity> sub_reply) {
                this.sub_reply = sub_reply;
            }

            public int getId() {
                return id;
            }

            public int getFrom_id() {
                return from_id;
            }

            public String getPic() {
                return pic;
            }

            public String getFrom_name() {
                return from_name;
            }

            public String getContent() {
                return content;
            }

            public int getReply_time() {
                return reply_time;
            }

            public List<SubReplyEntity> getSub_reply() {
                return sub_reply;
            }

            public static class SubReplyEntity {
                /**
                 * from_id : 11
                 * pic : img/lx.png
                 * from_name : 黄凯
                 * to_name : 黄凯
                 * content : 恩,好的
                 * reply_time : 1470536286
                 */

                private int from_id;
                private String pic;
                private String from_name;
                private String to_name;
                private String content;
                private int reply_time;

                public void setFrom_id(int from_id) {
                    this.from_id = from_id;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public void setFrom_name(String from_name) {
                    this.from_name = from_name;
                }

                public void setTo_name(String to_name) {
                    this.to_name = to_name;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setReply_time(int reply_time) {
                    this.reply_time = reply_time;
                }

                public int getFrom_id() {
                    return from_id;
                }

                public String getPic() {
                    return pic;
                }

                public String getFrom_name() {
                    return from_name;
                }

                public String getTo_name() {
                    return to_name;
                }

                public String getContent() {
                    return content;
                }

                public int getReply_time() {
                    return reply_time;
                }
            }
        }
    }
}
