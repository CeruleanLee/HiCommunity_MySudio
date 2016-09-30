/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能：评论列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/21<br>
 */
public class CommunityListBean extends BaseBean {

    public CommunityListBean() {
        this.data = new ArrayList<CommunityBean>();
    }

    List<CommunityBean> data;

    public List<CommunityBean> getData() {
        return data;
    }

    public void setData(List<CommunityBean> data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "CommunityListBean [data=" + data + "]";
	}

}
