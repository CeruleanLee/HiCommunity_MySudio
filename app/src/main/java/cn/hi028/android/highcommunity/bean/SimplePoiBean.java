package cn.hi028.android.highcommunity.bean;

/**
 * Created by Lee on 2016/12/14.
 * 说明：只包含名称和地址的热点
 */
public class SimplePoiBean {

    private String addr;//	地址信息
    private String name;//poi名称

    @Override
    public String toString() {
        return "SimplePoiBean{" +
                "addr='" + addr + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
