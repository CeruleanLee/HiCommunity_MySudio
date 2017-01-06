package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/17 0017.
 * 说明：监督页；list bean
 */
public class Auto_SuperViseBean2 {

    /**
     * success : true
     * code : 2000
     * msg : 数据获取成功
     * data : {"huibao":[],"xunwen":[],"liuyan":[{"id":"1","title":"哈哈哈","time":"1483670540","type":1}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private SuperViseBean2DataEntity data;

    @Override
    public String toString() {
        return "Auto_SuperViseBean2{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SuperViseBean2DataEntity getData() {
        return data;
    }

    public void setData(SuperViseBean2DataEntity data) {
        this.data = data;
    }

    public static class SuperViseBean2DataEntity {
        private List<SuperViseDataEntity> huibao;
        private List<SuperViseDataEntity> xunwen;

        @Override
        public String toString() {
            return "SuperViseBean2DataEntity{" +
                    "huibao=" + huibao +
                    ", xunwen=" + xunwen +
                    ", liuyan=" + liuyan +
                    '}';
        }

        /**
         * id : 1
         * title : 哈哈哈
         * time : 1483670540
         * type : 1
         */

        private List<SuperViseDataEntity> liuyan;

        public List<SuperViseDataEntity> getHuibao() {
            return huibao;
        }

        public void setHuibao(List<SuperViseDataEntity> huibao) {
            this.huibao = huibao;
        }

        public List<SuperViseDataEntity> getXunwen() {
            return xunwen;
        }

        public void setXunwen(List<SuperViseDataEntity> xunwen) {
            this.xunwen = xunwen;
        }

        public List<SuperViseDataEntity> getLiuyan() {
            return liuyan;
        }

        public void setLiuyan(List<SuperViseDataEntity> liuyan) {
            this.liuyan = liuyan;
        }
        public static class SuperViseDataEntity {
            /**
             * id : 1
             * title : 第一季度财务汇报
             * time : 1470188339
             */

            private String id;
            private String title;
            private String time;
            private String type;//新增是否业主type =1 业主

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getTime() {
                return time;
            }

            @Override
            public String toString() {
                return "SuperViseDataEntity{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", time='" + time + '\'' +
                        '}';
            }
        }
        public static class LiuyanEntity {
            private String id;
            private String title;
            private String time;
            private String type;

            @Override
            public String toString() {
                return "LiuyanEntity{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", time='" + time + '\'' +
                        ", type=" + type +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
