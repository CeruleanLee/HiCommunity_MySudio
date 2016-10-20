package cn.hi028.android.highcommunity.bean.Autonomous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lee on 2016/10/18.
 * 说明：问卷调查详情bean
 */
public class Auto_QuestionDeatailBean {


    /**
     * success : true
     * code : 2000
     * msg : 获取投票详情数据成功
     * data : {"vote":{"id":"2","title":"关于游乐园发展前景的调查问卷","type":"1","abstract":" 您好！我是奇幻城堡游乐园的市场调查员。为了更好的了解消费者需求，明确游乐园的发展方向，特别展开这次调查。您的建议将会是我们游乐园今后发展的宝贵信息，非常感谢您抽出时间填写这份调查问卷"},"titles":[{"id":"2","name":"您有没有去过游乐园？","type":"1","options":[{"id":"5","option":"有"},{"id":"6","option":"没有"}]},{"id":"3","name":"您在什么时候会选择去游乐园？ ","type":"2","options":[{"id":"7","option":"重大节日"},{"id":"8","option":"周末"},{"id":"9","option":"平常"},{"id":"10","option":"都不会去"}]},{"id":"4","name":"您多久去一次游乐园？","type":"1","options":[{"id":"11","option":"每年最少去一次"},{"id":"12","option":"不是每年都会去"},{"id":"13","option":"从来没去过"}]}]}
     */

    private boolean success;
    private String code;
    private String msg;
    private QuestionDeatailDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(QuestionDeatailDataEntity data) {
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

    public QuestionDeatailDataEntity getData() {
        return data;
    }

    public static class QuestionDeatailDataEntity {
        /**
         * vote : {"id":"2","title":"关于游乐园发展前景的调查问卷","type":"1","abstract":" 您好！我是奇幻城堡游乐园的市场调查员。为了更好的了解消费者需求，明确游乐园的发展方向，特别展开这次调查。您的建议将会是我们游乐园今后发展的宝贵信息，非常感谢您抽出时间填写这份调查问卷"}
         * titles : [{"id":"2","name":"您有没有去过游乐园？","type":"1","options":[{"id":"5","option":"有"},{"id":"6","option":"没有"}]},{"id":"3","name":"您在什么时候会选择去游乐园？ ","type":"2","options":[{"id":"7","option":"重大节日"},{"id":"8","option":"周末"},{"id":"9","option":"平常"},{"id":"10","option":"都不会去"}]},{"id":"4","name":"您多久去一次游乐园？","type":"1","options":[{"id":"11","option":"每年最少去一次"},{"id":"12","option":"不是每年都会去"},{"id":"13","option":"从来没去过"}]}]
         */

        private QuestionDeatailVoteEntity vote;
        private List<QuestionDeatailTitlesEntity> titles;

        public void setVote(QuestionDeatailVoteEntity vote) {
            this.vote = vote;
        }

        public void setTitles(List<QuestionDeatailTitlesEntity> titles) {
            this.titles = titles;
        }

        public QuestionDeatailVoteEntity getVote() {
            return vote;
        }

        public List<QuestionDeatailTitlesEntity> getTitles() {
            return titles;
        }

        public static class QuestionDeatailVoteEntity {
            /**
             * id : 2
             * title : 关于游乐园发展前景的调查问卷
             * type : 1
             * abstract :  您好！我是奇幻城堡游乐园的市场调查员。为了更好的了解消费者需求，明确游乐园的发展方向，特别展开这次调查。您的建议将会是我们游乐园今后发展的宝贵信息，非常感谢您抽出时间填写这份调查问卷
             */

            private String id;
            private String title;
            private String type;
            @SerializedName("abstract")
            private String abstractX;


            @Override
            public String toString() {
                return "QuestionDeatailVoteEntity{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", type='" + type + '\'' +
                        ", abstractX='" + abstractX + '\'' +
                        '}';
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setAbstractX(String abstractX) {
                this.abstractX = abstractX;
            }

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getType() {
                return type;
            }

            public String getAbstractX() {
                return abstractX;
            }
        }

        public static class QuestionDeatailTitlesEntity {
            /**
             * id : 2
             * name : 您有没有去过游乐园？
             * type : 1
             * options : [{"id":"5","option":"有"},{"id":"6","option":"没有"}]
             */

            private String id;
            private String name;
            private String type;
            private String max_option;
            private List<QuestionDeatailOptionsEntity> options;
            //是否解答
            private int que_state;

            public String getMax_option() {
                return max_option;
            }

            public void setMax_option(String max_option) {
                this.max_option = max_option;
            }

            @Override
            public String toString() {
                return "QuestionDeatailTitlesEntity{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", type='" + type + '\'' +
                        ", options=" + options +
                        ", que_state=" + que_state +
                        '}';
            }

            public int getQue_state() {
                return que_state;
            }

            public void setQue_state(int que_state) {
                this.que_state = que_state;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setOptions(List<QuestionDeatailOptionsEntity> options) {
                this.options = options;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getType() {
                return type;
            }

            public List<QuestionDeatailOptionsEntity> getOptions() {
                return options;
            }

            public static class QuestionDeatailOptionsEntity {
                /**
                 * id : 5
                 * option : 有
                 */

                private String id;
                private String option;
                private int ans_state;//选中为1  未选中为o;

                @Override
                public String toString() {
                    return "QuestionDeatailOptionsEntity{" +
                            "id='" + id + '\'' +
                            ", option='" + option + '\'' +
                            ", ans_state=" + ans_state +
                            '}';
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setOption(String option) {
                    this.option = option;
                }

                public String getId() {
                    return id;
                }

                public String getOption() {
                    return option;
                }

                public int getAns_state() {
                    return ans_state;
                }

                public void setAns_state(int ans_state) {
                    this.ans_state = ans_state;
                }
            }
        }
    }
}
