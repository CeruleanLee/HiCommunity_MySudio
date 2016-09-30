package cn.hi028.android.highcommunity.bean;

import java.util.List;

public class SubmitOrderBean extends BaseBean {
	private AddressOrderBean address;
	private List<GoodsOrderSubmitBean> goods_info;
	private float total_price;
	private String order_num;
	
	public AddressOrderBean getAddress() {
		return address;
	}
	public void setAddress(AddressOrderBean address) {
		this.address = address;
	}
	public List<GoodsOrderSubmitBean> getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(List<GoodsOrderSubmitBean> goods_info) {
		this.goods_info = goods_info;
	}
	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	
}
