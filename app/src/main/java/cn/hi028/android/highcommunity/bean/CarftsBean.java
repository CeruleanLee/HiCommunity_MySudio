/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：手艺人对象<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/29<br>
 */
public class CarftsBean extends BaseBean {
	String id;
	String head_pic;
	String title;
	String name;
	String tel;
	String status;
	String address;
	String price;
	

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CarftsBean [id=" + id + ", head_pic=" + head_pic + ", title="
				+ title + ", name=" + name + ", tel=" + tel + ", status="
				+ status + ", address=" + address + "]";
	}
	
}
