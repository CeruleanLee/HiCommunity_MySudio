package cn.hi028.android.highcommunity.bean;

/**
 * Created by 赵海 on 2016/4/24.
 * 摇一摇对象
 */
public class ShakeBean extends  BaseBean{
 public int   leftCount;
    public int            win;
    public int     type;
    public String           value;

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
