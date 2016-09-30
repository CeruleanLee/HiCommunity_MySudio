package cn.hi028.android.highcommunity.bean;

/**
 * Created by 赵海 on 2016/3/24.
 * 订单详情对象
 */
public class ChipOrderDetailBean extends BaseBean {
    private int status;//	int	0=>未支付,1=>待收货,2=>已完成
    private String out_trade_no;//string	订单号
    private int create_time;//	int	 下单时间
    private String pay_type;//	string	支付宝/微信/无
    private String cover_pic;//	string	商品图片
    private String name;//	string	商品名称
    private float original_price;//	float	原始价格
    private float join_price;//	float	参与价格
    private float current_price;//	float	当前价格
    private int num;//	int	购买数量
    private String real_name;//	string	收货人姓名
    private String tel;//	string	联系电话
    private float total_price;//	float	商品总价
    private float ticket_value;//	float	优惠价抵扣额
    private float zero_money;//	float	零钱抵扣额
    private float real_price;//		实付款
    private String address;//	string	收货地址
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
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
	public float getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(float original_price) {
		this.original_price = original_price;
	}
	public float getJoin_price() {
		return join_price;
	}
	public void setJoin_price(float join_price) {
		this.join_price = join_price;
	}
	public float getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(float current_price) {
		this.current_price = current_price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
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
	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	public float getTicket_value() {
		return ticket_value;
	}
	public void setTicket_value(float ticket_value) {
		this.ticket_value = ticket_value;
	}
	public float getZero_money() {
		return zero_money;
	}
	public void setZero_money(float zero_money) {
		this.zero_money = zero_money;
	}
	public float getReal_price() {
		return real_price;
	}
	public void setReal_price(float real_price) {
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
		return "ChipOrderDetailBean [status=" + status + ", out_trade_no="
				+ out_trade_no + ", create_time=" + create_time + ", pay_type="
				+ pay_type + ", cover_pic=" + cover_pic + ", name=" + name
				+ ", original_price=" + original_price + ", join_price="
				+ join_price + ", current_price=" + current_price + ", num="
				+ num + ", real_name=" + real_name + ", tel=" + tel
				+ ", total_price=" + total_price + ", ticket_value="
				+ ticket_value + ", zero_money=" + zero_money + ", real_price="
				+ real_price + ", address=" + address + "]";
	}

  
}
