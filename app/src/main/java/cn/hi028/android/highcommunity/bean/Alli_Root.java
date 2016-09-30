package cn.hi028.android.highcommunity.bean;

import java.util.List;

public class Alli_Root {
	private String pay_num;

	private String pay_type;

	private int  status;

	private String address;

	private String order_num;

	private String tel;

	private String consign;

	private String create_time;

	private String finished_time;

	private String pay_time;

	private List<Alii_Goods_info> goods_info ;

	private String total_price;

	private String shop_name;
	private int ticket_value;
	private int zero_money;
	
	public int getTicket_value() {
		return ticket_value;
	}
	public void setTicket_value(int ticket_value) {
		this.ticket_value = ticket_value;
	}
	public int getZero_money() {
		return zero_money;
	}
	public void setZero_money(int zero_money) {
		this.zero_money = zero_money;
	}
	public void setPay_num(String pay_num){
	this.pay_num = pay_num;
	}
	public String getPay_num(){
	return this.pay_num;
	}
	public void setPay_type(String pay_type){
	this.pay_type = pay_type;
	}
	public String getPay_type(){
	return this.pay_type;
	}
	public void setStatus(int status){
	this.status = status;
	}
	public int getStatus(){
	return this.status;
	}
	public void setAddress(String address){
	this.address = address;
	}
	public String getAddress(){
	return this.address;
	}
	public void setOrder_num(String order_num){
	this.order_num = order_num;
	}
	public String getOrder_num(){
	return this.order_num;
	}
	public void setTel(String tel){
	this.tel = tel;
	}
	public String getTel(){
	return this.tel;
	}
	public void setConsign(String consign){
	this.consign = consign;
	}
	public String getConsign(){
	return this.consign;
	}
	public void setCreate_time(String create_time){
	this.create_time = create_time;
	}
	public String getCreate_time(){
	return this.create_time;
	}
	public void setFinished_time(String finished_time){
	this.finished_time = finished_time;
	}
	public String getFinished_time(){
	return this.finished_time;
	}
	public void setPay_time(String pay_time){
	this.pay_time = pay_time;
	}
	public String getPay_time(){
	return this.pay_time;
	}
	public void setGoods_info(List<Alii_Goods_info> goods_info){
	this.goods_info = goods_info;
	}
	public List<Alii_Goods_info> getGoods_info(){
	return this.goods_info;
	}
	public void setTotal_price(String total_price){
	this.total_price = total_price;
	}
	public String getTotal_price(){
	return this.total_price;
	}
	public void setShop_name(String shop_name){
	this.shop_name = shop_name;
	}
	public String getShop_name(){
	return this.shop_name;
	}
	
	
	
	
	
	
}
