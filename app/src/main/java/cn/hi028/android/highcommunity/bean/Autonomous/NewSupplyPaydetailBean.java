package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * @说明：新版订单支付详情页 购物车跳转支付   普通订单支付
 * @作者： Lee_yting
 * @时间：2016/12/17 0019
 */
public class NewSupplyPaydetailBean {


    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"total_fee":225,"goods":[{"merchant":"嗨社区","info":[{"cart_id":"296","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"302","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"306","pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard":"20个/盒","price":"15.00","num":"1","little_amount":"15.00"}],"total_amount":75},{"merchant":"麦当劳","info":[{"cart_id":"300","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"10L/桶","price":"90.00","num":"1","little_amount":"90.00"},{"cart_id":"301","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"},{"cart_id":"303","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"5L/桶","price":"50.00","num":"1","little_amount":"50.00"},{"cart_id":"304","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"}],"total_amount":150}],"zero_money":"170.89","consign":{"id":"10","name":"王","tel":"13526985241","isDefault":"0","address":"成都市金牛区万达广场"}}
     */

    private boolean success;
    private int code;
    private String msg;
    private SupplyPayDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(SupplyPayDataEntity data) {
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

    public SupplyPayDataEntity getData() {
        return data;
    }

    public static class SupplyPayDataEntity {
        /**
         * total_fee : 225
         * goods : [{"merchant":"嗨社区","info":[{"cart_id":"296","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"302","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"306","pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard":"20个/盒","price":"15.00","num":"1","little_amount":"15.00"}],"total_amount":75},{"merchant":"麦当劳","info":[{"cart_id":"300","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"10L/桶","price":"90.00","num":"1","little_amount":"90.00"},{"cart_id":"301","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"},{"cart_id":"303","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"5L/桶","price":"50.00","num":"1","little_amount":"50.00"},{"cart_id":"304","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"}],"total_amount":150}]
         * zero_money : 170.89
         * consign : {"id":"10","name":"王","tel":"13526985241","isDefault":"0","address":"成都市金牛区万达广场"}
         */

        private float total_fee;
        private float zero_money;
        private SupplyPayConsignEntity consign;
        private List<SupplyPayGoodsEntity> goods;

        @Override
        public String toString() {
            return "SupplyPayDataEntity{" +
                    "total_fee=" + total_fee +
                    ", zero_money='" + zero_money + '\'' +
                    ", consign=" + consign +
                    ", goods=" + goods +
                    '}';
        }

        public void setTotal_fee(float total_fee) {
            this.total_fee = total_fee;
        }

        public void setZero_money(float zero_money) {
            this.zero_money = zero_money;
        }

        public void setConsign(SupplyPayConsignEntity consign) {
            this.consign = consign;
        }

        public void setGoods(List<SupplyPayGoodsEntity> goods) {
            this.goods = goods;
        }

        public float getTotal_fee() {
            return total_fee;
        }

        public float getZero_money() {
            return zero_money;
        }

        public SupplyPayConsignEntity getConsign() {
            return consign;
        }

        public List<SupplyPayGoodsEntity> getGoods() {
            return goods;
        }




    }
}
