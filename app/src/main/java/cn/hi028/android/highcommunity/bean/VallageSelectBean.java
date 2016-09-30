/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 *@功能：小区选择对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-04<br>
 */
public class VallageSelectBean extends BaseBean{
    VallageBean curr_village;
    List<VallageBean>  his_village;
    List<CityBean>city_village;
    List<VallageBean>  nearby_village;
    CityBean  current_city;
    public List<VallageBean> getNearby_village() {
        return nearby_village;
    }

    public void setNearby_village(List<VallageBean> nearby_village) {
        this.nearby_village = nearby_village;
    }

    public CityBean getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(CityBean current_city) {
        this.current_city = current_city;
    }

    public VallageBean getCurr_village() {
        return curr_village;
    }

    public void setCurr_village(VallageBean curr_village) {
        this.curr_village = curr_village;
    }

    public List<VallageBean> getHis_village() {
        return his_village;
    }

    public void setHis_village(List<VallageBean> his_village) {
        this.his_village = his_village;
    }

    public List<CityBean> getCity_village() {
        return city_village;
    }

    public void setCity_village(List<CityBean> city_village) {
        this.city_village = city_village;
    }
}
