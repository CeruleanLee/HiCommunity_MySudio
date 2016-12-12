package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/17 0017.
 * 说明：监督页；list bean
 */
public class Auto_SuperViseBean {


    /**注意这里分组用的是数组 1和2 来标识
     * success : true
     * code : 2000
     * msg : 数据获取成功
     * data : [[{"id":"1","title":"第一季度财务汇报","time":"1470188339"},{"id":"7","title":"中期考试报告","time":"1470302302"},{"id":"9","title":"我们都是中国人","time":"1470304228"},{"id":"10","title":"我们都是中国人","time":"1470304461"},{"id":"21","title":"格兰鼎城第三季度小区财政开支明细记录","time":"1470536096"},{"id":"23","title":"公司年度总结","time":"1470811833"},{"id":"24","title":"天下第二也很好","time":"1470812312"}],[{"id":"6","title":"本季度财务状况请尽快提交","time":"1470190355"},{"id":"19","title":"一种","time":"1470359946"},{"id":"20","title":"亲illKKK","time":"1470389220"},{"id":"22","title":"这一季度的收支报告什么时候出来","time":"1470536235"}]]
     */

    private boolean success;
    private int code;
    private String msg;
    private List<List<SuperViseDataEntity>> data;

    @Override
    public String toString() {
        return "Auto_SuperViseBean{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<List<SuperViseDataEntity>> data) {
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

    public List<List<SuperViseDataEntity>> getData() {
        return data;
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
}
