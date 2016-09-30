/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：修改收货地址时获取的数据<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/23<br>
 */
public class AddressModifyBean extends BaseBean {
    AddressDetailBean address;
    List<DistrictBean> districts;
    List<CitysBean> cities;

    public AddressDetailBean getAddress() {
        return address;
    }

    public void setAddress(AddressDetailBean address) {
        this.address = address;
    }

    public List<DistrictBean> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictBean> districts) {
        this.districts = districts;
    }

    public List<CitysBean> getCities() {
        return cities;
    }

    public void setCities(List<CitysBean> cities) {
        this.cities = cities;
    }
}
