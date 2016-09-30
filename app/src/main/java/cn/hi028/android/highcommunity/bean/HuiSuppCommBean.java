/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 *@功能：物品直供评论对象<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-17<br>
 */
public class HuiSuppCommBean extends BaseBean {
   private int id;// 2,
    private String       content;// 商品质量很棒,
    private long   time;// 0,
    private int    uid;// 2,
    private String    head_pic;// upload/ticket/ticket_201510261335426107.jpg,
    private String   nick;// 苏先生
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
