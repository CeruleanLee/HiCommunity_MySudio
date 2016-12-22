/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：用户信息<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/17<br>
 */
public class UserInfoBean extends BaseBean {
    int id;
    String username;
    String password;
    int v_id;
    String birthday;
    String head_pic;
    int sex;
    String nick;
    String sign;
    String reg_time;
    int state;
    String hobby;
    int counts;
    int login_time;
    int scores;
    String token;
    String zero_money;
    String history_vid;
    String third_uid;
String owner_id;//业主id
String user_Type;//用户在业主大厅的身份

    public String getUser_Type() {
        return user_Type;
    }

    public void setUser_Type(String user_Type) {
        this.user_Type = user_Type;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public int getId() {
        if (id == 0) {
            if (HighCommunityApplication.share != null)
                id = HighCommunityApplication.share.getInt("USERID", 0);
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (HighCommunityApplication.share != null)
            HighCommunityApplication.share.edit().putInt("USERID", id).commit();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getV_id() {
        if (v_id == 0) {
            if (HighCommunityApplication.share != null)
                v_id = HighCommunityApplication.share.getInt("VILLAGEID", 0);
        }
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
        if (HighCommunityApplication.share != null)
            HighCommunityApplication.share.edit().putInt("VILLAGEID", v_id).commit();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getLogin_time() {
        return login_time;
    }

    public void setLogin_time(int login_time) {
        this.login_time = login_time;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getToken() {
        if (token == null || token.equals("")) {
            if (HighCommunityApplication.share != null)
                token = HighCommunityApplication.share.getString(Constacts.APPTOKEN, "");
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getThird_uid() {
        return third_uid;
    }

    public void setThird_uid(String third_uid) {
        this.third_uid = third_uid;
    }

    public String getZero_money() {
        return zero_money;
    }

    public void setZero_money(String zero_money) {
        this.zero_money = zero_money;
    }

    public String getHistory_vid() {
        return history_vid;
    }

    public void setHistory_vid(String history_vid) {
        this.history_vid = history_vid;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", v_id=" + v_id +
                ", birthday='" + birthday + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", sex=" + sex +
                ", nick='" + nick + '\'' +
                ", sign='" + sign + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", state=" + state +
                ", hobby='" + hobby + '\'' +
                ", counts=" + counts +
                ", login_time=" + login_time +
                ", scores=" + scores +
                ", token='" + token + '\'' +
                ", zero_money='" + zero_money + '\'' +
                ", history_vid='" + history_vid + '\'' +
                ", third_uid='" + third_uid + '\'' +
                ", owner_id='" + owner_id + '\'' +
                '}';
    }
}
