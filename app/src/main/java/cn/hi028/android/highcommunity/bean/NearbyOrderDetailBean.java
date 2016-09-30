package cn.hi028.android.highcommunity.bean;

import java.util.List;

public class NearbyOrderDetailBean extends BaseBean {
	private String out_trade_no;// string 订单号
	private String order_num;
	private long create_time;// int 下单时间
	private int pay_time;// 支付时间
	private long finished_time; // 支付时间
	private String consign; // 收货人
	private String tel;// string 联系电话
	private String address;// string 收货地址
	private int status;// int 0=>未支付,1=>待收货,2=>已完成
	private String pay_type;// string 支付宝/微信/无
	private String pay_num;// 支付流水号
	private float total_price;// float 商品总价
	private List<GoodsOrderSubmitBean> goods_info;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	
	public long getCreate_time() {
		return create_time;
	}
	
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	
	public int getPay_time() {
		return pay_time;
	}
	
	public void setPay_time(int pay_time) {
		this.pay_time = pay_time;
	}

	public long getFinished_time() {
		return finished_time;
	}

	public void setFinished_time(long finished_time) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	public List<GoodsOrderSubmitBean> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<GoodsOrderSubmitBean> goods_info) {
		this.goods_info = goods_info;
	}

	@Override
	public String toString() {
		return "NearbyOrderDetailBean [order_num=" + out_trade_no
				+ ", create_time=" + create_time + ", pay_time=" + pay_time
				+ ", finished_time=" + finished_time + ", consign=" + consign
				+ ", tel=" + tel + ", address=" + address + ", status="
				+ status + ", pay_type=" + pay_type + ", pay_num=" + pay_num
				+ ", total_price=" + total_price + ", goods_info=" + goods_info
				+ "]";
	}

}
