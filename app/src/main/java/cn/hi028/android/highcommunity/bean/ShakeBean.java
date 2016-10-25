package cn.hi028.android.highcommunity.bean;

/**
 * Created by 赵海 on 2016/4/24.
 * 摇一摇对象
 */
public class ShakeBean extends  BaseBean{
 public int   leftCount; //剩余次数
    public int            win;//0=未中奖,1=>中奖
    public int     type;//类型:1=>物业折扣券,2=>直供现金券,3=>众筹现金券,4=>积分
    public String           value;//面值或者折扣率

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
