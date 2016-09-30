/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 *@功能：租房列表对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-04<br>
 */
public class TenementBean extends BaseBean {
   List<TenementHouseBean> curr_village;

    public List<TenementHouseBean> getNearby_village() {
        return nearby_village;
    }

    public void setNearby_village(List<TenementHouseBean> nearby_village) {
        this.nearby_village = nearby_village;
    }

    List<TenementHouseBean> nearby_village;

    public List<TenementHouseBean> getCurr_village() {
        return curr_village;
    }

    public void setCurr_village(List<TenementHouseBean> curr_village) {
        this.curr_village = curr_village;
    }
}
