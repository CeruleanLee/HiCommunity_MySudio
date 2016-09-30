/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：服务banner<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/7<br>
 */
public class BannerBean extends BaseBean {
	String type;
	String path;
	String id;
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BannerBean [type=" + type + ", path=" + path + ", id=" + id
				+ ", url=" + url + "]";
	}

}
