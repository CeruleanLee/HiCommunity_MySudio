/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.params;

import java.util.List;

/**
 *@功能：惠生活直供订单商品信息参数<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-21<br>
 */
public class HuiSuppGdParams extends BaseParams{
    private int goods_id;
    private float goods_price;
    private int goods_number;
    private int         number;
    private String  goods_name;
    private String  thumb_pic;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public String getThumb_pic() {
		return thumb_pic;
	}

	public void setThumb_pic(String thumb_pic) {
		this.thumb_pic = thumb_pic;
	}

	private List<String> pic;
    private float   goods_total_price;

    public float getGoods_total_price() {
        return goods_total_price;
    }

    public void setGoods_total_price(float goods_total_price) {
        this.goods_total_price = goods_total_price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }


    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
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
