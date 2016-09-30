package cn.hi028.android.highcommunity.bean;

public class MyZCOrderDetailBean {
	private String orginal_price;

	private String status;

	private String current_price;

	private String zero_money;

	private String tel;

	private String join_price;

	private String out_trade_no;

	private String total_price;

	private String real_price;

	private String num;

	private String address;

	private String cover_pic;

	private String pay_type;

	private String real_name;

	private String name;

	private String create_time;

	private int ticket_value;

	public void setOrginal_price(String orginal_price) {
		this.orginal_price = orginal_price;
	}

	public String getOrginal_price() {
		return this.orginal_price;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setCurrent_price(String current_price) {
		this.current_price = current_price;
	}

	public String getCurrent_price() {
		return this.current_price;
	}

	public void setZero_money(String zero_money) {
		this.zero_money = zero_money;
	}

	public String getZero_money() {
		return this.zero_money;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel() {
		return this.tel;
	}

	public void setJoin_price(String join_price) {
		this.join_price = join_price;
	}

	public String getJoin_price() {
		return this.join_price;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_trade_no() {
		return this.out_trade_no;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public String getTotal_price() {
		return this.total_price;
	}

	public void setReal_price(String real_price) {
		this.real_price = real_price;
	}

	public String getReal_price() {
		return this.real_price;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNum() {
		return this.num;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setCover_pic(String cover_pic) {
		this.cover_pic = cover_pic;
	}

	public String getCover_pic() {
		return this.cover_pic;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_type() {
		return this.pay_type;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getReal_name() {
		return this.real_name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_time() {
		return this.create_time;
	}

	public void setTicket_value(int ticket_value) {
		this.ticket_value = ticket_value;
	}

	public int getTicket_value() {
		return this.ticket_value;
	}

	@Override
	public String toString() {
		return "MyZCOrderDetailBean [orginal_price=" + orginal_price
				+ ", status=" + status + ", current_price=" + current_price
				+ ", zero_money=" + zero_money + ", tel=" + tel
				+ ", join_price=" + join_price + ", out_trade_no="
				+ out_trade_no + ", total_price=" + total_price
				+ ", real_price=" + real_price + ", num=" + num + ", address="
				+ address + ", cover_pic=" + cover_pic + ", pay_type="
				+ pay_type + ", real_name=" + real_name + ", name=" + name
				+ ", create_time=" + create_time + ", ticket_value="
				+ ticket_value + "]";
	}

}
