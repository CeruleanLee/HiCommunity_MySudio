package cn.hi028.android.highcommunity.bean;

public class Deal {
	public double deal_id;
	public String image;
	public String tiny_image;

	public String title;
	public String min_title;

	public String description;

	public double market_price;
	public double current_price;
	public double promotion_price;

	public String sale_num;

	public double score;
	public int comment_num;

	public String deal_url;
	public String deal_murl;
	
	public Deal() {
		super();
	}

	public Deal(double deal_id, String image, String tiny_image, String title,
			String min_title, String description, double market_price,
			double current_price, double promotion_price, String sale_num,
			double score, int comment_num, String deal_url, String deal_murl) {
		super();
		this.deal_id = deal_id;
		this.image = image;
		this.tiny_image = tiny_image;
		this.title = title;
		this.min_title = min_title;
		this.description = description;
		this.market_price = market_price;
		this.current_price = current_price;
		this.promotion_price = promotion_price;
		this.sale_num = sale_num;
		this.score = score;
		this.comment_num = comment_num;
		this.deal_url = deal_url;
		this.deal_murl = deal_murl;
	}

	@Override
	public String toString() {
		return "Deal [deal_id=" + deal_id + ", image=" + image
				+ ", tiny_image=" + tiny_image + ", title=" + title
				+ ", min_title=" + min_title + ", description=" + description
				+ ", market_price=" + market_price + ", current_price="
				+ current_price + ", promotion_price=" + promotion_price
				+ ", sale_num=" + sale_num + ", score=" + score
				+ ", comment_num=" + comment_num + ", deal_url=" + deal_url
				+ ", deal_murl=" + deal_murl + "]";
	}
	
	
	
	

}
