package cn.hi028.android.highcommunity.bean.NearbyOrderdetail2;

import java.util.List;

import cn.hi028.android.highcommunity.bean.GoodsOrderSubmitBean;

public class Root_Data {

	 private String shop_name;

	 private String order_num;

	 private String create_time;

	 private String pay_time;

	 private String finished_time;

	 private String consign;

	 private String tel;

	 private String address;

	 private String status;

	 private String pay_type;

	 private String pay_num;

	 private String total_price;

	 private List<GoodsOrderSubmitBean> goods_info ;

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getFinished_time() {
		return finished_time;
	}

	public void setFinished_time(String finished_time) {
		this.finished_time = finished_time;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_num() {
		return pay_num;
	}

	public void setPay_num(String pay_num) {
		this.pay_num = pay_num;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public List<GoodsOrderSubmitBean> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<GoodsOrderSubmitBean> goods_info) {
		this.goods_info = goods_info;
	}
	 
	 

}
