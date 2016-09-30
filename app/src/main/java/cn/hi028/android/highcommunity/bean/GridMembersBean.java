/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：群主成员<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/26<br>
 */
public class GridMembersBean extends BaseBean {
    int uid;
    String head_pic;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }
}
