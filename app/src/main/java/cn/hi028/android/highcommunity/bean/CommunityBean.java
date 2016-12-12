/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：评论<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/20<br>
 *     ps：v2.0接口修改后新增site
 */
public class CommunityBean extends BaseBean {
    int mid;
    boolean isPra;
    String gid;//群组id
    String vid;//v2.0 版本失效
    String title;
    List<PicBean> pic;
    String create_time;
    String village_name;//v2.0 版本失效
    String g_name;//type=1时群组名称  来自XX群组
    /**v2.0新增接口  发帖地址**/
    String site;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    int uid;//发帖用户id
    String head_pic;
    String nick;
    int sex;
    int age;
    int d_count;//帖子评论数
    int p_count;//赞帖总数
    int id;
    String content;
    int type;//2:活动；3：群组
    String label;
    String welcome;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getAg_pic() {
        return ag_pic;
    }

    public void setAg_pic(String ag_pic) {
        this.ag_pic = ag_pic;
    }

    String  ag_pic;

    public void setIsPra(boolean isPra) {
        this.isPra = isPra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getVillage_name() {
        return village_name;
    }
//
//    public void setVillage_name(String village_name) {
//        this.village_name = village_name;
//    }

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

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getD_count() {
        return d_count;
    }

    public void setD_count(int d_count) {
        this.d_count = d_count;
    }

    public int getP_count() {
        return p_count;
    }

    public void setP_count(int p_count) {
        this.p_count = p_count;
    }

//    public String getVid() {return vid;}
//
//    public void setVid(String vid) {
//        this.vid = vid;
//    }

    public boolean isPra() {
        return isPra;
    }

    public void setPra(boolean pra) {
        isPra = pra;
    }

    @Override
    public String toString() {
        return "CommunityBean{" +
                "mid=" + mid +
                ", isPra=" + isPra +
                ", gid='" + gid + '\'' +
                ", vid='" + vid + '\'' +
                ", title='" + title + '\'' +
                ", pic=" + pic +
                ", create_time='" + create_time + '\'' +
                ", village_name='" + village_name + '\'' +
                ", g_name='" + g_name + '\'' +
                ", site='" + site + '\'' +
                ", uid=" + uid +
                ", head_pic='" + head_pic + '\'' +
                ", nick='" + nick + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", d_count=" + d_count +
                ", p_count=" + p_count +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", label='" + label + '\'' +
                ", welcome='" + welcome + '\'' +
                ", ag_pic='" + ag_pic + '\'' +
                '}';
    }
}
