package cn.hi028.android.highcommunity.bean.Autonomous;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/23 0023
 */
public class SupplyPayConsignEntity {
    /**
     * id : 10
     * name : 王
     * tel : 13526985241
     * isDefault : 0
     * address : 成都市金牛区万达广场
     */

    private int id;
    private String name;
    private String tel;
    private String isDefault;
    private String address;

    @Override
    public String toString() {
        return "SupplyPayConsignEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
