/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.ArrayList;

/**
 * @功能：修改收货地址时获取的数据<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/23<br>
 */
public class AddressModifyBean extends BaseBean {
    AddressDetailBean address;
    ArrayList<DistrictBean> districts;
    ArrayList<CitysBean> cities;

    public AddressDetailBean getAddress() {
        return address;
    }

    public void setAddress(AddressDetailBean address) {
        this.address = address;
    }

    public ArrayList<DistrictBean> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<DistrictBean> districts) {
        this.districts = districts;
    }

    public ArrayList<CitysBean> getCities() {
        return cities;
    }

    public void setCities(ArrayList<CitysBean> cities) {
        this.cities = cities;
    }
}
