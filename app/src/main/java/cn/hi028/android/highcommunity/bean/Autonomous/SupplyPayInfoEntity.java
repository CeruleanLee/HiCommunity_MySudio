package cn.hi028.android.highcommunity.bean.Autonomous;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/23 0023
 */
public class SupplyPayInfoEntity {

    /**
     * cart_id : 296
     * pic : img/logo.jpg
     * name : 东北大米
     * standard : 5公斤/袋
     * price : 30.00
     * num : 1
     * little_amount : 30.00
     */

    private String cart_id;
    private String pic;
    private String name;
    private String standard;
    private String price;
    private String num;
    private String little_amount;

    @Override
    public String toString() {
        return "SupplyPayInfoEntity{" +
                "cart_id='" + cart_id + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", standard='" + standard + '\'' +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                ", little_amount='" + little_amount + '\'' +
                '}';
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLittle_amount() {
        return little_amount;
    }

    public void setLittle_amount(String little_amount) {
        this.little_amount = little_amount;
    }
}
