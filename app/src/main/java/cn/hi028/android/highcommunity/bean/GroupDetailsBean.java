/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：群组详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/26<br>
 */
public class GroupDetailsBean extends BaseBean {
    int gid;
    String name;
    String owner;
    String ow_nick;
    String intro;
    String isin;
    String pic;
    List<GridMembersBean> members;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOw_nick() {
        return ow_nick;
    }

    public void setOw_nick(String ow_nick) {
        this.ow_nick = ow_nick;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<GridMembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<GridMembersBean> members) {
        this.members = members;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
