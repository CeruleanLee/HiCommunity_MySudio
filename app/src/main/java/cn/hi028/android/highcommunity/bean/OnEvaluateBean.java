package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

public class OnEvaluateBean implements Serializable {

	private String goods_id;

	private String thumb_pic;

	private String goods_name;

	private String goods_price;

	private String shop_name;

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_id() {
		return this.goods_id;
	}

	public void setThumb_pic(String thumb_pic) {
		this.thumb_pic = thumb_pic;
	}

	public String getThumb_pic() {
		return this.thumb_pic;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_name() {
		return this.goods_name;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_price() {
		return this.goods_price;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_name() {
		return this.shop_name;
	}

	@Override
	public String toString() {
		return "OnEvaluateBean [goods_id=" + goods_id + ", thumb_pic="
				+ thumb_pic + ", goods_name=" + goods_name + ", goods_price="
				+ goods_price + ", shop_name=" + shop_name + "]";
	}

}
