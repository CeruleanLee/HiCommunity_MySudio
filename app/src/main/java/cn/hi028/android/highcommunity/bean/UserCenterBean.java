/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
public class UserCenterBean extends BaseBean {
	UserCenter userInfo;
	int message;
	int fee;
	int cart;
	int order;
	int cho;
	int wallet;
	int nearby;

	public int getNearby() {
		return nearby;
	}

	public void setNearby(int nearby) {
		this.nearby = nearby;
	}

	public UserCenter getUserInfo() {
		return userInfo;
		
	}

	public void setUserInfo(UserCenter userInfo) {
		this.userInfo = userInfo;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getCart() {
		return cart;
	}

	public void setCart(int cart) {
		this.cart = cart;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getCho() {
		return cho;
	}

	public void setCho(int cho) {
		this.cho = cho;
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public class UserCenter {
		String head_pic;
		String nick;
		String age;
		String sex;
		String village;

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getHead_pic() {
			return head_pic;
		}

		public void setHead_pic(String head_pic) {
			this.head_pic = head_pic;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getVillage() {
			return village;
		}

		public void setVillage(String village) {
			this.village = village;
		}

		@Override
		public String toString() {
			return "UserCenter [head_pic=" + head_pic + ", nick=" + nick
					+ ", age=" + age + ", sex=" + sex + ", village=" + village
					+ "]";
		}

	}

	@Override
	public String toString() {
		return "UserCenterBean [userInfo=" + userInfo + ", message=" + message
				+ ", fee=" + fee + ", cart=" + cart + ", order=" + order
				+ ", cho=" + cho + ", wallet=" + wallet + ", nearby=" + nearby
				+ "]";
	}

}
