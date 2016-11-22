/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * @功能：城市bean<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/23<br>
 */
public class CitysBean extends BaseBean implements IPickerViewData {
    String city;
    String city_code;

    @Override
    public String toString() {
        return "CitysBean{" +
                "city='" + city + '\'' +
                ", city_code='" + city_code + '\'' +
                '}';
    }

    public CitysBean(String city, String city_code) {
        this.city = city;
        this.city_code = city_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return city;
    }
}
