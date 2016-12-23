package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/23 0023
 */
public class SupplyPayGoodsEntity {

    /**
     * merchant : 嗨社区
     * info : [{"cart_id":"296","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"302","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"306","pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard":"20个/盒","price":"15.00","num":"1","little_amount":"15.00"}]
     * total_amount : 75
     */

    private String merchant;
    private int total_amount=-1;
    private List<SupplyPayInfoEntity> info;

    @Override
    public String toString() {
        return "SupplyPayGoodsEntity{" +
                "merchant='" + merchant + '\'' +
                ", total_amount=" + total_amount +
                ", info=" + info +
                '}';
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public List<SupplyPayInfoEntity> getInfo() {
        return info;
    }

    public void setInfo(List<SupplyPayInfoEntity> info) {
        this.info = info;
    }
}
