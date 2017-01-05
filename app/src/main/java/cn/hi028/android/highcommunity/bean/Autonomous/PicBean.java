package cn.hi028.android.highcommunity.bean.Autonomous;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2017/1/5 0005
 */
public class PicBean {

    String imgUrl;
    String  height;
    String  width;

    @Override
    public String toString() {
        return "PicBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                '}';
    }

    public String getImgUrl() {
        return Constacts.IMAGEHTTP +imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHeight() {
        if (height==null){
            return HighCommunityApplication.screenHeight/2+"";
        }else{

            return Integer.parseInt(height)*2+"";
        }
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
