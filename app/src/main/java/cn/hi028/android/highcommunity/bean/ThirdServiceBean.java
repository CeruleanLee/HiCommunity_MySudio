/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：第三方服务/幻灯片对象<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/7<br>
 */
public class ThirdServiceBean extends BaseBean {

	List<ServiceBean> services;
	List<BannerBean> banners;

	public List<ServiceBean> getServices() {
		return services;
	}

	public void setServices(List<ServiceBean> services) {
		this.services = services;
	}

	public List<BannerBean> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerBean> banners) {
		this.banners = banners;
	}

	@Override
	public String toString() {
		return "ThirdServiceBean [services=" + services + ", banners="
				+ banners + "]";
	}

}
