package cn.hi028.android.highcommunity.bean;

public class NearByShopData extends BaseBean {
	private String id;

	private String head_pic;

	private String shop_name;

	private String tel;

	private String address;

	private String sales;

	public NearByShopData() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
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

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "NearByShopData [id=" + id + ", head_pic=" + head_pic
				+ ", shop_name=" + shop_name + ", tel=" + tel + ", address="
				+ address + ", sales=" + sales + "]";
	}

	

}
