package cn.hi028.android.highcommunity.bean;

public class PayPopListBean {

	String name;
	String price;
	int count;
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public PayPopListBean() {
		super();
	}

	@Override
	public String toString() {
		return "PayPopListBean [name=" + name + ", price=" + price + ", count="
				+ count + ", id=" + id + "]";
	}

}
