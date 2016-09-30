package cn.hi028.android.highcommunity.bean;

import java.util.List;

public class AlliancePayBean extends BaseBean {
	private AddressModel address;
	private List<Good_infoModel> goods_info;
	private String total_price;
	private String order_num;
	public AddressModel getAddress() {
		return address;
	}
	public void setAddress(AddressModel address) {
		this.address = address;
	}
	public List<Good_infoModel> getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(List<Good_infoModel> goods_info) {
		this.goods_info = goods_info;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	@Override
	public String toString() {
		return "AlliancePayBean [address=" + address + ", goods_info="
				+ goods_info + ", total_price=" + total_price + ", order_num="
				+ order_num + "]";
	}
	
	
}
