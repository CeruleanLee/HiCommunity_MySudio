/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.params;

import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.ChipsBean;

/**
 * @功能：众筹对象<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-25<br>
 */
public class HuiChipsOrderparams extends BaseParams {

    ChipsBean raise;
    AddressBean defaultAddress;
    int num = 1;
    float total_price = 0.0f;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getTotal_price() {
        return getNum() * raise.getCurrent_price();
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public ChipsBean getRaise() {
        return raise;
    }

    public void setRaise(ChipsBean raise) {
        this.raise = raise;
    }

    public AddressBean getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(AddressBean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
