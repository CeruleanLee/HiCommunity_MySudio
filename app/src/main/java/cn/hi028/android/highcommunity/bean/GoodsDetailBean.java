package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

public class GoodsDetailBean implements Serializable {
	private boolean success;

	private String code;

	private String msg;

	private GoodsData data;

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

	public GoodsData getData() {
		return data;
	}

	public void setData(GoodsData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GoodsDetailBean [success=" + success + ", code=" + code
				+ ", msg=" + msg + ", data=" + data + "]";
	}

}
