package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 支付商品的信息
 * @author lyf
 *
 */
public class Good_infoModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  商品缩略图  */
	@JSONField
	private String thumb_pic;
	/**  商品名称  */
	@JSONField
	private String goods_name;
	/**  商品单价  */
	@JSONField
	private float goods_price;
	/**  商品数量  */
	@JSONField
	private int number;
	/**  商品小计  */
	@JSONField
	private String goods_total_price;
	public String getThumb_pic() {
		return thumb_pic;
	}
	public void setThumb_pic(String thumb_pic) {
		this.thumb_pic = thumb_pic;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public float getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(float goods_price) {
		this.goods_price = goods_price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getGoods_total_price() {
		return goods_total_price;
	}
	public void setGoods_total_price(String goods_total_price) {
		this.goods_total_price = goods_total_price;
	}
	@Override
	public String toString() {
		return "Good_infoModel [thumb_pic=" + thumb_pic + ", goods_name="
				+ goods_name + ", goods_price=" + goods_price + ", number="
				+ number + ", goods_total_price=" + goods_total_price + "]";
	}
	
	
}
