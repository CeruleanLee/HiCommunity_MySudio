/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：活动详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/15<br>
 */
public class ActivityDetailBean extends BaseBean {
    List<DetailBean> detail;
    List<PraisesBean> members;
    List<RepliesBean> replies;

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public List<PraisesBean> getMembers() {
        return members;
    }

    public void setMembers(List<PraisesBean> members) {
        this.members = members;
    }

    public List<RepliesBean> getReplies() {
        return replies;
    }

    public void setReplies(List<RepliesBean> replies) {
        this.replies = replies;
    }
}
