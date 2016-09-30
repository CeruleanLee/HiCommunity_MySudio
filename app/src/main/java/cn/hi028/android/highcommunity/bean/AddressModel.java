package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 支付商品的用户信息
 * @author lyf
 *
 */
public class AddressModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户地址id  */
	@JSONField
	private int id;
	/** 真实姓名  */
	@JSONField
	private String consign;
	/** 用户电话  */
	@JSONField
	private String tel;
	/** 用户地址  */
	@JSONField
	private String address;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConsign() {
		return consign;
	}
	public void setConsign(String consign) {
		this.consign = consign;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
