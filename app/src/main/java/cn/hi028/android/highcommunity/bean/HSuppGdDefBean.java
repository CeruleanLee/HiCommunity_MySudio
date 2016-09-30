/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：惠生活商品对象默认解析对象<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-18<br>
 */
public class HSuppGdDefBean extends BaseBean {
    private float zero_money;
    private AddressBean default_address;
    private HuiGoodsInCartBean goods;

    public float getZero_real_money() {
        return zero_real_money;
    }

    public void setZero_real_money(float zero_real_money) {
        this.zero_real_money = zero_real_money;
    }

    private float zero_real_money;

    public float getZero_money() {
        return zero_money;
    }

    public void setZero_money(float zero_money) {
        this.zero_money = zero_money;
    }

    public AddressBean getDefault_address() {
        return default_address;
    }

    public void setDefault_address(AddressBean default_address) {
        this.default_address = default_address;
    }

    public HuiGoodsInCartBean getGoods() {
        return goods;
    }

    public void setGoods(HuiGoodsInCartBean goods) {
        this.goods = goods;
    }
}
