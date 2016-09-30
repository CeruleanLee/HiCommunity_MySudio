package cn.hi028.android.highcommunity.bean;

/**
 * 摇一摇用户
 * Created by 赵海 on 2016/4/24.
 */
public class ShakeUser extends  BaseBean{
String    nick;
    String create_time;
    String tip;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
