package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;
import java.util.List;

public class MerchantEvaluationInfoListBean implements Serializable {
	private String nick;

	private String head_pic;

	private String shop_name;

	private String goods_name;

	private String create_time;

	private String content;

	private List<String> pic;

	private List<String> comment_pic;

	public List<String> getComment_pic() {
		return comment_pic;
	}

	public void setComment_pic(List<String> comment_pic) {
		this.comment_pic = comment_pic;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public String getHead_pic() {
		return this.head_pic;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_name() {
		return this.shop_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_name() {
		return this.goods_name;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_time() {
		return this.create_time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setPic(List<String> pic) {
		this.pic = pic;
	}

	public List<String> getPic() {
		return this.pic;
	}

	@Override
	public String toString() {
		return "MerchantEvaluationInfoListBean [nick=" + nick + ", head_pic="
				+ head_pic + ", shop_name=" + shop_name + ", goods_name="
				+ goods_name + ", create_time=" + create_time + ", content="
				+ content + ", pic=" + pic + "]";
	}

}
