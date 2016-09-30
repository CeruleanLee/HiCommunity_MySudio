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
public class SinaInfo {

	private String uid;
	private String favourites_count;
	private String location;
	private String description;
	private String verified;
	private String friends_count;
	private String gender;
	private String screen_name;
	private String statuses_count;
	private String followers_count;
	private String profile_image_url;

	/**
	 * 
	 */
	public SinaInfo() {
		super();
	}

	/**
	 * @param uid
	 * @param favourites_count
	 * @param location
	 * @param description
	 * @param verified
	 * @param friends_count
	 * @param gender
	 * @param screen_name
	 * @param statuses_count
	 * @param followers_count
	 * @param profile_image_url
	 */
	public SinaInfo(String uid, String favourites_count, String location, String description, String verified,
			String friends_count, String gender, String screen_name, String statuses_count, String followers_count,
			String profile_image_url) {
		super();
		this.uid = uid;
		this.favourites_count = favourites_count;
		this.location = location;
		this.description = description;
		this.verified = verified;
		this.friends_count = friends_count;
		this.gender = gender;
		this.screen_name = screen_name;
		this.statuses_count = statuses_count;
		this.followers_count = followers_count;
		this.profile_image_url = profile_image_url;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the favourites_count
	 */
	public String getFavourites_count() {
		return favourites_count;
	}

	/**
	 * @param favourites_count
	 *            the favourites_count to set
	 */
	public void setFavourites_count(String favourites_count) {
		this.favourites_count = favourites_count;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the verified
	 */
	public String getVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(String verified) {
		this.verified = verified;
	}

	/**
	 * @return the friends_count
	 */
	public String getFriends_count() {
		return friends_count;
	}

	/**
	 * @param friends_count
	 *            the friends_count to set
	 */
	public void setFriends_count(String friends_count) {
		this.friends_count = friends_count;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the screen_name
	 */
	public String getScreen_name() {
		return screen_name;
	}

	/**
	 * @param screen_name
	 *            the screen_name to set
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	/**
	 * @return the statuses_count
	 */
	public String getStatuses_count() {
		return statuses_count;
	}

	/**
	 * @param statuses_count
	 *            the statuses_count to set
	 */
	public void setStatuses_count(String statuses_count) {
		this.statuses_count = statuses_count;
	}

	/**
	 * @return the followers_count
	 */
	public String getFollowers_count() {
		return followers_count;
	}

	/**
	 * @param followers_count
	 *            the followers_count to set
	 */
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}

	/**
	 * @return the profile_image_url
	 */
	public String getProfile_image_url() {
		return profile_image_url;
	}

	/**
	 * @param profile_image_url
	 *            the profile_image_url to set
	 */
	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SinaInfo [uid=" + uid + ", favourites_count=" + favourites_count + ", location=" + location
				+ ", description=" + description + ", verified=" + verified + ", friends_count=" + friends_count
				+ ", gender=" + gender + ", screen_name=" + screen_name + ", statuses_count=" + statuses_count
				+ ", followers_count=" + followers_count + ", profile_image_url=" + profile_image_url + "]";
	}

}
