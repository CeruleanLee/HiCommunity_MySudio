package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/17 0017.
 * 说明：汇报详情
 */
public class Auto_ReportDetailBean {


    /**
     * success : true
     * code : 2000
     * msg : 数据获取成功
     * data : {"id":"21","title":"格兰鼎城第三季度小区财政开支明细记录","create_time":"1470536096","content":"格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录","name":"黄凯","reply":[{"id":76,"from_id":11,"pic":"img/lx.png","from_name":"黄凯","content":"欢迎大家提交意见","sub_reply":[{"from_id":11,"pic":"img/lx.png","from_name":"黄凯","to_name":"黄凯","content":"好的","reply_time":1470536176},{"from_id":13,"pic":"img/lx.png","from_name":"寇训凡","to_name":"黄凯","content":"啦啦啦","reply_time":1470643217}],"reply_time":1470536157},{"id":86,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744960},{"id":87,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744965},{"id":88,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744965},{"id":89,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744966},{"id":90,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744966},{"id":91,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744966}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private ReportDetailDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(ReportDetailDataEntity data) {
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

    public ReportDetailDataEntity getData() {
        return data;
    }

    public static class ReportDetailDataEntity {
        /**
         * id : 21
         * title : 格兰鼎城第三季度小区财政开支明细记录
         * create_time : 1470536096
         * content : 格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录格兰鼎城第三季度小区财政开支明细记录
         * name : 黄凯
         * reply : [{"id":76,"from_id":11,"pic":"img/lx.png","from_name":"黄凯","content":"欢迎大家提交意见","sub_reply":[{"from_id":11,"pic":"img/lx.png","from_name":"黄凯","to_name":"黄凯","content":"好的","reply_time":1470536176},{"from_id":13,"pic":"img/lx.png","from_name":"寇训凡","to_name":"黄凯","content":"啦啦啦","reply_time":1470643217}],"reply_time":1470536157},{"id":86,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744960},{"id":87,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744965},{"id":88,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744965},{"id":89,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744966},{"id":90,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744966},{"id":91,"from_id":8,"pic":"img/lx.png","from_name":"付秀梅","content":"要得，硬是巴适","sub_reply":[],"reply_time":1470744966}]
         */

        private String id;
        private String title;
        private String create_time;
        private String content;
        private String name;
        private List<ReportDetailReplyEntity> reply;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public void setReply(List<ReportDetailReplyEntity> reply) {
            this.reply = reply;
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

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public List<ReportDetailReplyEntity> getReply() {
            return reply;
        }

        public static class ReportDetailReplyEntity {
            /**
             * id : 76
             * from_id : 11
             * pic : img/lx.png
             * from_name : 黄凯
             * content : 欢迎大家提交意见
             * sub_reply : [{"from_id":11,"pic":"img/lx.png","from_name":"黄凯","to_name":"黄凯","content":"好的","reply_time":1470536176},{"from_id":13,"pic":"img/lx.png","from_name":"寇训凡","to_name":"黄凯","content":"啦啦啦","reply_time":1470643217}]
             * reply_time : 1470536157
             */

            private int id;
            private int from_id;
            private String pic;
            private String from_name;
            private String content;
            private long reply_time;
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

            public void setReply_time(long reply_time) {
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

            public long getReply_time() {
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
                 * content : 好的
                 * reply_time : 1470536176
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
