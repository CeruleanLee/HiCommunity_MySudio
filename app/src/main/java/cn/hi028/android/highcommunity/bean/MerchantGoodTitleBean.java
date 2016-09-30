package cn.hi028.android.highcommunity.bean;

public class MerchantGoodTitleBean {
	String name;
	String id;
	boolean ischeck = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public MerchantGoodTitleBean() {
		super();
	}

	@Override
	public String toString() {
		return "MerchantGoodTitleBean [name=" + name + ", id=" + id
				+ ", ischeck=" + ischeck + "]";
	}

	

}
