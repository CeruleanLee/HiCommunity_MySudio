package cn.hi028.android.highcommunity.bean;

import java.util.List;

public class MerchantShopGoodBean extends BaseBean {
	private List<Goods_info> goods_info;

	private String category_name;

	private String category_id;

	public List<Goods_info> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<Goods_info> goods_info) {
		this.goods_info = goods_info;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public MerchantShopGoodBean() {
		super();
	}

	public MerchantShopGoodBean(List<Goods_info> goods_info,
			String category_name, String category_id) {
		super();
		this.goods_info = goods_info;
		this.category_name = category_name;
		this.category_id = category_id;
	}

}
