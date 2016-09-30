package cn.hi028.android.highcommunity.bean.NearbyOrderdetail2;

import java.util.List;

public class NearbyOrderDeatai_Root {
	private boolean success;

	private String code;

	private String msg;

	private Root_Data data;



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
	public Root_Data getData() {
		return data;
	}
	public void setData(Root_Data data) {
		this.data = data;
	}
}
