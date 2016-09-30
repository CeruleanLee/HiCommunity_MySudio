package cn.hi028.android.highcommunity.bean;

import java.util.List;

public class ShopNearByListInfo extends BaseBean {
	private boolean success;

	private String code;

	private String msg;

	private List<NearByShopData> data;

	public ShopNearByListInfo() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<NearByShopData> getData() {
		return data;
	}

	public void setData(List<NearByShopData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ShopNearByListInfo [success=" + success + ", code=" + code
				+ ", msg=" + msg + ", data=" + data + "]";
	}

}
