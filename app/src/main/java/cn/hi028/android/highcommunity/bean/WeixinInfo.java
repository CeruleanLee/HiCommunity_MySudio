/**
 * 
 */
package cn.hi028.android.highcommunity.bean;

/**
 * <p>
 * Title: TODO
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: 北京成嘉科技
 * </p>
 * 
 * @author 张元振
 * @date 2015-4-2下午4:03:55
 */
public class WeixinInfo {

	private String sex, nickname, unionid, province, openid, language, headimgurl, country, city;

	/**
	 * 
	 */
	public WeixinInfo() {
		super();
	}

	/**
	 * @param sex
	 * @param nickname
	 * @param unionid
	 * @param province
	 * @param openid
	 * @param language
	 * @param headimgurl
	 * @param country
	 * @param city
	 */
	public WeixinInfo(String sex, String nickname, String unionid, String province, String openid, String language,
			String headimgurl, String country, String city) {
		super();
		this.sex = sex;
		this.nickname = nickname;
		this.unionid = unionid;
		this.province = province;
		this.openid = openid;
		this.language = language;
		this.headimgurl = headimgurl;
		this.country = country;
		this.city = city;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the unionid
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * @param unionid
	 *            the unionid to set
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid
	 *            the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the headimgurl
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	/**
	 * @param headimgurl
	 *            the headimgurl to set
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeixinInfo [sex=" + sex + ", nickname=" + nickname + ", unionid=" + unionid + ", province=" + province
				+ ", openid=" + openid + ", language=" + language + ", headimgurl=" + headimgurl + ", country="
				+ country + ", city=" + city + "]";
	}

}
