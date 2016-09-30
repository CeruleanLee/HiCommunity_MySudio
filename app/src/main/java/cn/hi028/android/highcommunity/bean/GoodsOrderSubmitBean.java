package cn.hi028.android.highcommunity.bean;

public class GoodsOrderSubmitBean extends BaseBean {
	private String thumb_pic;
	private String goods_name;
	private float goods_price;
	private int number;
	private float goods_total_price;
	
	public String getThumb_pic() {
		return thumb_pic;
	}
	public void setThumb_pic(String thumb_pic) {
		this.thumb_pic = thumb_pic;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public float getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(float goods_price) {
		this.goods_price = goods_price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public float getGoods_total_price() {
		return goods_total_price;
	}
	public void setGoods_total_price(float goods_total_price) {
		this.goods_total_price = goods_total_price;
	}
	
}
