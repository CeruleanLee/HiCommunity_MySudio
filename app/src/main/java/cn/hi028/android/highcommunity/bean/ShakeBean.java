package cn.hi028.android.highcommunity.bean;

/**
 * Created by 赵海 on 2016/4/24.
 * 摇一摇对象
 */
public class ShakeBean extends BaseBean {
    public int leftCount; //剩余次数
    public int win;//0=未中奖,1=>中奖
    public int type;//类型:1=>物业折扣券,2=>直供现金券,3=>众筹现金券,4=>积分 5=>现场奖品
    public String value;//面值或者折扣率
    String pic;//奖品图片
    String name;//奖品名称
    String code;//奖品兑换码

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
