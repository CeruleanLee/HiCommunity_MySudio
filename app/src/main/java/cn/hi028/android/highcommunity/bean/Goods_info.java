package cn.hi028.android.highcommunity.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods_info extends BaseBean implements Parcelable {

	private String goods_name;

	private String thumb_pic;

	private String price;

	private String sales;

	private String goods_id;

	private int counts = 0;
	
	/**
	 * 接口获取到联盟订单付款界面总金额，用于ShowPayAdapter显示总金额
	 * 区别于订单提交时用单价x数量计算
	 */
	private String all_price = "";

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_name() {
		return this.goods_name;
	}

	public void setThumb_pic(String thumb_pic) {
		this.thumb_pic = thumb_pic;
	}

	public String getThumb_pic() {
		return this.thumb_pic;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return this.price;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getSales() {
		return this.sales;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_id() {
		return this.goods_id;
	}

	public Goods_info() {
		super();
	}

	public String getAll_price() {
		return all_price;
	}

	public void setAll_price(String all_price) {
		this.all_price = all_price;
	}

	@Override
	public String toString() {
		return "Goods_info [goods_name=" + goods_name + ", thumb_pic="
				+ thumb_pic + ", price=" + price + ", sales=" + sales
				+ ", goods_id=" + goods_id + ", counts=" + counts + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.goods_name);
		dest.writeString(this.thumb_pic);
		dest.writeString(this.price);
		dest.writeString(this.sales);
		dest.writeString(this.goods_id);
		dest.writeInt(this.counts);
	}

	protected Goods_info(Parcel in) {
		this.goods_name = in.readString();
		this.thumb_pic = in.readString();
		this.price = in.readString();
		this.sales = in.readString();
		this.goods_id = in.readString();
		this.counts = in.readInt();
	}

	public static final Creator<Goods_info> CREATOR = new Creator<Goods_info>() {
		@Override
		public Goods_info createFromParcel(Parcel source) {
			return new Goods_info(source);
		}

		@Override
		public Goods_info[] newArray(int size) {
			return new Goods_info[size];
		}
	};

}
