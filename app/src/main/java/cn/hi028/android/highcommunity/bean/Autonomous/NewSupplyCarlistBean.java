package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * @说明：v2.0 购物车列表
 * @作者： Lee_yting
 * @时间：2016/12/14 0016
 */
public class NewSupplyCarlistBean {

    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : [{"id":"295","num":"1","cover_pic":"img/logo.jpg","name":"东北大米","standard_name":"10公斤/袋","standard_id":"2","price":"50.00","sum":"50.00"},{"id":"296","num":"1","cover_pic":"img/logo.jpg","name":"东北大米","standard_name":"5公斤/袋","standard_id":"1","price":"30.00","sum":"30.00"},{"id":"297","num":"1","cover_pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard_name":"20个/盒","standard_id":"7","price":"15.00","sum":"15.00"},{"id":"298","num":"1","cover_pic":"img/logo.jpg","name":"【中华特色馆】西安馆 陕西苹果 红富士 红苹果 苹果 5斤装/箱","standard_name":"5kg/箱","standard_id":"6","price":"28.00","sum":"28.00"}]
     */

    private boolean success;
    private int code;
    private String msg;
    private List<SupplyCarlistDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<SupplyCarlistDataEntity> data) {
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

    public List<SupplyCarlistDataEntity> getData() {
        return data;
    }

    public static class SupplyCarlistDataEntity {
        /**
         * id : 295
         * num : 1
         * cover_pic : img/logo.jpg
         * name : 东北大米
         * standard_name : 10公斤/袋
         * standard_id : 2
         * price : 50.00
         * sum : 50.00
         */

        private String id;
        private int num;
        private String cover_pic;
        private String name;
        private String standard_name;
        private String standard_id;
        private float price;
        private float sum;
        private boolean  isCheck=false;
        private int storage=999;


        public int getStorage() {
            return storage;
        }

        public void setStorage(int storage) {
            this.storage = storage;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        @Override
        public String toString() {
            return "SupplyCarlistDataEntity{" +
                    "id='" + id + '\'' +
                    ", num='" + num + '\'' +
                    ", cover_pic='" + cover_pic + '\'' +
                    ", name='" + name + '\'' +
                    ", standard_name='" + standard_name + '\'' +
                    ", standard_id='" + standard_id + '\'' +
                    ", price='" + price + '\'' +
                    ", sum='" + sum + '\'' +
                    '}';
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setCover_pic(String cover_pic) {
            this.cover_pic = cover_pic;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStandard_name(String standard_name) {
            this.standard_name = standard_name;
        }

        public void setStandard_id(String standard_id) {
            this.standard_id = standard_id;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setSum(float sum) {
            this.sum = sum;
        }

        public String getId() {
            return id;
        }

        public int getNum() {
            return num;
        }

        public String getCover_pic() {
            return cover_pic;
        }

        public String getName() {
            return name;
        }

        public String getStandard_name() {
            return standard_name;
        }

        public String getStandard_id() {
            return standard_id;
        }

        public float getPrice() {
            return price;
        }

        public float getSum() {
            return getNum()*getPrice();
        }
    }

    @Override
    public String toString() {
        return "NewSupplyCarlistBean{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
