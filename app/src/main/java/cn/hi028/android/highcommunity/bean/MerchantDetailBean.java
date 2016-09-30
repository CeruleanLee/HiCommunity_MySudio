package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

public class MerchantDetailBean implements Serializable {
	private String delivery;

	private String address;

	private String intro;

	private String tel;

	private String shop_name;

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getDelivery() {
		return this.delivery;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel() {
		return this.tel;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_name() {
		return this.shop_name;
	}

	public MerchantDetailBean() {
		super();
	}

	public MerchantDetailBean(String delivery, String address, String intro,
			String tel, String shop_name) {
		super();
		this.delivery = delivery;
		this.address = address;
		this.intro = intro;
		this.tel = tel;
		this.shop_name = shop_name;
	}

	@Override
	public String toString() {
		return "MerchantDetailBean [delivery=" + delivery + ", address="
				+ address + ", intro=" + intro + ", tel=" + tel
				+ ", shop_name=" + shop_name + "]";
	}

}
