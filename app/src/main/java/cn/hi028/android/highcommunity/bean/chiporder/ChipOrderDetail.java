package cn.hi028.android.highcommunity.bean.chiporder;

public class ChipOrderDetail {
	private String status;

	private String out_trade_no;

	private String create_time;

	private String pay_type;

	private String cover_pic;

	private String name;

	private String orginal_price;

	private String join_price;

	private String current_price;

	private String num;

	private String real_name;

	private String tel;

	private String total_price;

	private int ticket_value;

	private String zero_money;

	private String real_price;

	private String address;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getCover_pic() {
		return cover_pic;
	}

	public void setCover_pic(String cover_pic) {
		this.cover_pic = cover_pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrginal_price() {
		return orginal_price;
	}

	public void setOrginal_price(String orginal_price) {
		this.orginal_price = orginal_price;
	}

	public String getJoin_price() {
		return join_price;
	}

	public void setJoin_price(String join_price) {
		this.join_price = join_price;
	}

	public String getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(String current_price) {
		this.current_price = current_price;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public int getTicket_value() {
		return ticket_value;
	}

	public void setTicket_value(int ticket_value) {
		this.ticket_value = ticket_value;
	}

	public String getZero_money() {
		return zero_money;
	}

	public void setZero_money(String zero_money) {
		this.zero_money = zero_money;
	}

	public String getReal_price() {
		return real_price;
	}

	public void setReal_price(String real_price) {
		this.real_price = real_price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ChipOrderDetail [status=" + status + ", out_trade_no="
				+ out_trade_no + ", create_time=" + create_time + ", pay_type="
				+ pay_type + ", cover_pic=" + cover_pic + ", name=" + name
				+ ", orginal_price=" + orginal_price + ", join_price="
				+ join_price + ", current_price=" + current_price + ", num="
				+ num + ", real_name=" + real_name + ", tel=" + tel
				+ ", total_price=" + total_price + ", ticket_value="
				+ ticket_value + ", zero_money=" + zero_money + ", real_price="
				+ real_price + ", address=" + address + "]";
	}
	
}
