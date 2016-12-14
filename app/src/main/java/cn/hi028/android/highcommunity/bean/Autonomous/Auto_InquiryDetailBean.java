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
     * data : {"id":"6","create_time":"1470190355","content":"本季度财务状况请尽快提交","name":"陈晨","head_pic":"img/lx.png","reply":[{"id":80,"from_id":12,"pic":"img/lx.png","from_name":"纪国新","content":"gg","sub_reply":[{"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","to_id":12,"to_name":"纪国新","content":"great","reply_time":1476845503},{"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","to_id":46,"to_name":"啊李","content":"xyz","reply_time":1476845513}],"reply_time":1470622140},{"id":112,"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","content":"阔以","sub_reply":[],"reply_time":1476845442}]}
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
         * id : 6
         * create_time : 1470190355
         * content : 本季度财务状况请尽快提交
         * name : 陈晨
         * head_pic : img/lx.png
         * reply : [{"id":80,"from_id":12,"pic":"img/lx.png","from_name":"纪国新","content":"gg","sub_reply":[{"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","to_id":12,"to_name":"纪国新","content":"great","reply_time":1476845503},{"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","to_id":46,"to_name":"啊李","content":"xyz","reply_time":1476845513}],"reply_time":1470622140},{"id":112,"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","content":"阔以","sub_reply":[],"reply_time":1476845442}]
         */

        private String id;
        private String name;
        private String content;
        private String create_time;
        private String head_pic;
        private List<InquiryDetailReplyEntity> rep_reply;//留言详情中用到   业主代表的回复
        private List<InquiryDetailReplyEntity> reply;

        public List<InquiryDetailReplyEntity> getRep_reply() {
            return rep_reply;
        }

        public void setRep_reply(List<InquiryDetailReplyEntity> rep_reply) {
            this.rep_reply = rep_reply;
        }

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
             * id : 80
             * from_id : 12
             * pic : img/lx.png
             * from_name : 纪国新
             * content : gg
             * sub_reply : [{"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","to_id":12,"to_name":"纪国新","content":"great","reply_time":1476845503},{"from_id":46,"pic":"upload/head_pic/201609/201609240825178876.jpg","from_name":"啊李","to_id":46,"to_name":"啊李","content":"xyz","reply_time":1476845513}]
             * reply_time : 1470622140
             */

            private int id;
            private int from_id;
            private String pic;
            private String from_name;
            private String content;
            private int reply_time;
            private List<InquiryDetailSubReplyEntity> sub_reply;

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

            public void setSub_reply(List<InquiryDetailSubReplyEntity> sub_reply) {
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

            public List<InquiryDetailSubReplyEntity> getSub_reply() {
                return sub_reply;
            }

            public static class InquiryDetailSubReplyEntity {
                /**
                 * from_id : 46
                 * pic : upload/head_pic/201609/201609240825178876.jpg
                 * from_name : 啊李
                 * to_id : 12
                 * to_name : 纪国新
                 * content : great
                 * reply_time : 1476845503
                 */

                private int from_id;
                private String pic;
                private String from_name;
                private int to_id;
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

                public void setTo_id(int to_id) {
                    this.to_id = to_id;
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

                public int getTo_id() {
                    return to_id;
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
