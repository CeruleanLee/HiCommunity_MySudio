package cn.hi028.android.highcommunity.bean.Autonomous;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/23 0023
 */
public class NewHuiPayDetail_OederBean {

    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"goods":{"merchant":"嗨社区","info":[{"pic":"img/logo.jpg","name":"【中华特色馆】西安馆 陕西苹果 红富士 红苹果 苹果 5斤装/箱","standard":"5kg/箱","price":"28.00","num":"1","little_amount":"28.00"}],"total_amount":28},"total_fee":28,"zero_money":"5000.00","consign":{"id":"12","name":"张大山","tel":"13698525225","isDefault":"1","address":"成都市青羊区万达广场"}}
     */

    private boolean success;
    private int code;
    private String msg;
    private NewHuiPayDetail_OederDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(NewHuiPayDetail_OederDataEntity data) {
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

    public NewHuiPayDetail_OederDataEntity getData() {
        return data;
    }

    public static class NewHuiPayDetail_OederDataEntity {
        /**
         * goods : {"merchant":"嗨社区","info":[{"pic":"img/logo.jpg","name":"【中华特色馆】西安馆 陕西苹果 红富士 红苹果 苹果 5斤装/箱","standard":"5kg/箱","price":"28.00","num":"1","little_amount":"28.00"}],"total_amount":28}
         * total_fee : 28
         * zero_money : 5000.00
         * consign : {"id":"12","name":"张大山","tel":"13698525225","isDefault":"1","address":"成都市青羊区万达广场"}
         */


        private float total_fee;
        private float zero_money;
        private SupplyPayConsignEntity consign;
        private SupplyPayGoodsEntity goods;

        @Override
        public String toString() {
            return "NewHuiPayDetail_OederDataEntity{" +
                    "total_fee=" + total_fee +
                    ", zero_money=" + zero_money +
                    ", consign=" + consign +
                    ", goods=" + goods +
                    '}';
        }

        public float getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(float total_fee) {
            this.total_fee = total_fee;
        }

        public float getZero_money() {
            return zero_money;
        }

        public void setZero_money(float zero_money) {
            this.zero_money = zero_money;
        }

        public SupplyPayConsignEntity getConsign() {
            return consign;
        }

        public void setConsign(SupplyPayConsignEntity consign) {
            this.consign = consign;
        }

        public SupplyPayGoodsEntity getGoods() {
            return goods;
        }

        public void setGoods(SupplyPayGoodsEntity goods) {
            this.goods = goods;
        }
    }
}
