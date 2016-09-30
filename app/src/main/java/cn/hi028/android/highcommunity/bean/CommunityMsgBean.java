package cn.hi028.android.highcommunity.bean;

/**
 * 与我相关消息
 * Created by 赵海 on 2016/3/26.
 */
public class CommunityMsgBean extends BaseBean {
    private String head_pic;
    private String nick;
    private int reply_time;
    private String   reply_content;
    private int message_id;
    private String message_content;
    private String status;
    private String message_pic;

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
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

    public int getReply_time() {
        return reply_time;
    }

    public void setReply_time(int reply_time) {
        this.reply_time = reply_time;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage_pic() {
        return message_pic;
    }

    public void setMessage_pic(String message_pic) {
        this.message_pic = message_pic;
    }
}
