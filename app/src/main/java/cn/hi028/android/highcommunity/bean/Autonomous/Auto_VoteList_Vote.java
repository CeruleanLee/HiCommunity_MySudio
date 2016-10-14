package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/14 0014.
 * 说明：自治大厅选举 bean
 */
public class Auto_VoteList_Vote {


    /**
     * success : true
     * code : 2000
     * msg : 获取投票成功
     * data : [{"id":"3","title":"您对社区的看法","create_time":"1470223959","building":"所有楼栋","unit":"所有单元","is_voted":0},{"id":"2","title":"关于游乐园发展前景的调查问卷","create_time":"1470223651","building":"2栋","unit":"所有单元","is_voted":0}]
     */

    private boolean success;
    private String code;
    private String msg;
    private List<VoteVVDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<VoteVVDataEntity> data) {
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

    public List<VoteVVDataEntity> getData() {
        return data;
    }

    public static class VoteVVDataEntity {
        /**
         * id : 3
         * title : 您对社区的看法
         * create_time : 1470223959
         * building : 所有楼栋
         * unit : 所有单元
         * is_voted : 0
         */

        private String id;
        private String title;
        private String create_time;
        private String building;
        private String unit;
        private int is_voted;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setIs_voted(int is_voted) {
            this.is_voted = is_voted;
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

        public String getBuilding() {
            return building;
        }

        public String getUnit() {
            return unit;
        }

        public int getIs_voted() {
            return is_voted;
        }
    }
}
