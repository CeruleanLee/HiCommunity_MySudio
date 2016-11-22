/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * @功能：详细地址<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/23<br>
 */
public class DistrictBean extends BaseBean implements IPickerViewData {
    String district;
    String district_code;

    @Override
    public String toString() {
        return "DistrictBean{" +
                "district='" + district + '\'' +
                ", district_code='" + district_code + '\'' +
                '}';
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    @Override
    public String getPickerViewText() {
        return district;
    }
}
