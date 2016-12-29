/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.params;

/**
 *@功能：v2.0惠生活直供订单商品信息参数<br>
 *@作者： Lee_yting<br>
 *@版本：2.0<br>
 *@时间：2016/12/18<br>
 */
public class NewHuiSuppGdParams extends BaseParams{
    private int goods_id;
    private float goods_price;
    private int goods_number;
    private String name;
    private String  pic;
    private String standard_name;//商品规格名称
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
//    public List<String> getPic() {
//        return pic;
//    }
//
//    public void setPic(List<String> pic) {
//        this.pic = pic;
//    }
//    private List<String> pic;
    private float   goods_total_price;

    public float getGoods_total_price() {
        return goods_total_price;
    }

    public void setGoods_total_price(float goods_total_price) {
        this.goods_total_price = goods_total_price;
    }

    @Override
    public String toString() {
        return "NewHuiSuppGdParams{" +
                "pic='" + pic + '\'' +
                ", goods_id=" + goods_id +
                ", name='" + name + '\'' +
                ", standard_name='" + standard_name + '\'' +
                ", goods_price=" + goods_price +
                ", goods_number=" + goods_number +
                '}';
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandard_name() {
        return standard_name;
    }

    public void setStandard_name(String standard_name) {
        this.standard_name = standard_name;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }
}
