package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * 紧急维修对象
 * Created by 赵海 on 2016/3/11.
 */
public class RepairJinJBean extends BaseBean{

    public List<RepairJJBean> getService() {
        return service;
    }

    public void setService(List<RepairJJBean> service) {
        this.service = service;
    }

    List<RepairJJBean> service;
    List<RepairJJBean> property;

    public List<RepairJJBean> getProperty() {
        return property;
    }

    public void setProperty(List<RepairJJBean> property) {
        this.property = property;
    }
}
