package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsData implements Serializable {
	private List<String> goods_pic;

	private String goods_name;

	private String price;

	private String storage;

	private String intro;

	private String detail;

	private String tel;

	private String delivery;

	private List<Attr> attr;

	private List<MerchantEvaluationInfoListBean> comments;

	public List<String> getGoods_pic() {
		return goods_pic;
	}

	public void setGoods_pic(List<String> goods_pic) {
		this.goods_pic = goods_pic;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public List<Attr> getAttr() {
		return attr;
	}

	public void setAttr(List<Attr> attr) {
		this.attr = attr;
	}

	public List<MerchantEvaluationInfoListBean> getComments() {
		return comments;
	}

	public void setComments(List<MerchantEvaluationInfoListBean> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "GoodsData [goods_pic=" + goods_pic + ", goods_name="
				+ goods_name + ", price=" + price + ", storage=" + storage
				+ ", intro=" + intro + ", detail=" + detail + ", tel=" + tel
				+ ", delivery=" + delivery + ", attr=" + attr + ", comments="
				+ comments + "]";
	}


}
