package cn.hi028.android.highcommunity.bean.service;

import java.util.List;

public class ServiceData {
	public List<Services> services ;

	public List<BannersBean> banners ;

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public List<BannersBean> getBanners() {
		return banners;
	}

	public void setBanners(List<BannersBean> banners) {
		this.banners = banners;
	}
	
}
