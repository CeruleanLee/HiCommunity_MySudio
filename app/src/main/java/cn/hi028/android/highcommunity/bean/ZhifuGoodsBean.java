package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 需要支付的商品的实体类(用户信息，商品信息)
 * @author lyf
 *
 */
public class ZhifuGoodsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  用户信息  */
	@JSONField
	private AddressModel address;
	/**  商品信息  */
	@JSONField
	private Good_infoModel goods_info;
	/**  金额  */
	@JSONField
	private float total_price;
	/**  数量  */
	@JSONField
	private String oder_num;
	public AddressModel getAddress() {
		return address;
	}
	public void setAddress(AddressModel address) {
		this.address = address;
	}
	public Good_infoModel getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(Good_infoModel goods_info) {
		this.goods_info = goods_info;
	}
	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	public String getOder_num() {
		return oder_num;
	}
	public void setOder_num(String oder_num) {
		this.oder_num = oder_num;
	}
	
}
