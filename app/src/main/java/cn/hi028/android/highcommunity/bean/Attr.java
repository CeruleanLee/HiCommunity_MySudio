package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

public class Attr implements Serializable {
	private String attr_name;

	private String attr_value;

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getAttr_name() {
		return this.attr_name;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}

	public String getAttr_value() {
		return this.attr_value;
	}

	@Override
	public String toString() {
		return "Attr [attr_name=" + attr_name + ", attr_value=" + attr_value
				+ "]";
	}
	
}
