package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AllianceOrderBean implements Serializable {
	private String status;
	private String order_num;
	private float total_price;
	private String shop_name;

	private List<AllianceOrderGoodsInfoBean> goods_info;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;

	}

	public float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	public String getShop_name() {
		return shop_name;

	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;

	}

	public List<AllianceOrderGoodsInfoBean> getGoods_info() {
		return goods_info;

	}

	public void setGoods_info(List<AllianceOrderGoodsInfoBean> goods_info) {
		this.goods_info = goods_info;
	}

	public static class AllianceOrderGoodsInfoBean implements Serializable {
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

		@Override
		public String toString() {
			return "AllianceOrderGoodsInfoBean [thumb_pic=" + thumb_pic
					+ ", goods_name=" + goods_name + ", goods_price="
					+ goods_price + ", number=" + number
					+ ", goods_total_price=" + goods_total_price + "]";
		}

	}

	@Override
	public String toString() {
		return "AllianceOrderBean [status=" + status + ", order_num="
				+ order_num + ", total_price=" + total_price + ", shop_name="
				+ shop_name + ", goods_info=" + goods_info + "]";
	}

}
