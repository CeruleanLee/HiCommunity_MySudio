/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：通知详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/29<br>
 */
public class NoticeDetailsBean extends BaseBean {
    String title;
    String content;
    String create_time;
    String publish_man;
    List<String> pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public String getPublish_man() {
        return publish_man;
    }

    public void setPublish_man(String publish_man) {
        this.publish_man = publish_man;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
