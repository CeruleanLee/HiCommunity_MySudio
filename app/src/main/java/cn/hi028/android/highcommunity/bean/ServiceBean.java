/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：第三方服务<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/7<br>
 */
public class ServiceBean extends BaseBean {
	String url;
	String pic;
	String name;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ServiceBean [url=" + url + ", pic=" + pic + ", name=" + name
				+ "]";
	}

}
