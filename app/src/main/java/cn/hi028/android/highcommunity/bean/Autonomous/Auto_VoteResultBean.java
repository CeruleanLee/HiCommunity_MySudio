package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/20 0020.
 * 说明：
 */
public class Auto_VoteResultBean {


    /**
     * success : true
     * code : 2000
     * msg : 获取投票结果成功
     * data : [{"title":"东北F4谁是你的最爱","options":[{"option":"小沈阳","pic":"upload/ywh/option_pic/201610110946322863.jpg","vote_percent":"50%"},{"option":"宋小宝","pic":"upload/ywh/option_pic/201610110946321089.jpg","vote_percent":"50%"},{"option":"赵四","pic":"upload/ywh/option_pic/201610110946322026.jpg","vote_percent":"0%"},{"option":"王小利","pic":"upload/ywh/option_pic/201610110946326419.jpg","vote_percent":"0%"}]}]
     */

    private boolean success;
    private String code;
    private String msg;
    private List<VoteResultDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<VoteResultDataEntity> data) {
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

    public List<VoteResultDataEntity> getData() {
        return data;
    }

    public static class VoteResultDataEntity {
        /**
         * title : 东北F4谁是你的最爱
         * options : [{"option":"小沈阳","pic":"upload/ywh/option_pic/201610110946322863.jpg","vote_percent":"50%"},{"option":"宋小宝","pic":"upload/ywh/option_pic/201610110946321089.jpg","vote_percent":"50%"},{"option":"赵四","pic":"upload/ywh/option_pic/201610110946322026.jpg","vote_percent":"0%"},{"option":"王小利","pic":"upload/ywh/option_pic/201610110946326419.jpg","vote_percent":"0%"}]
         */

        private String title;
        private List<VoteResultOptionsEntity> options;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setOptions(List<VoteResultOptionsEntity> options) {
            this.options = options;
        }

        public String getTitle() {
            return title;
        }

        public List<VoteResultOptionsEntity> getOptions() {
            return options;
        }

        public static class VoteResultOptionsEntity {
            /**
             * option : 小沈阳
             * pic : upload/ywh/option_pic/201610110946322863.jpg
             * vote_percent : 50%
             */

            private String option;
            private String pic;
            private String vote_percent;

            public void setOption(String option) {
                this.option = option;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public void setVote_percent(String vote_percent) {
                this.vote_percent = vote_percent;
            }

            public String getOption() {
                return option;
            }

            public String getPic() {
                return pic;
            }

            public String getVote_percent() {
                return vote_percent;
            }
        }
    }
}
