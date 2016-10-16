package cn.hi028.android.highcommunity.utils.updateutil;

/**
 * Created by Lee on 2016/10/16.
 * 说明：解析更新xml的bean
 */
public class CheckAppUpdateResponse {

    private String version;//更新版本
    private String updatetime;//更新时间
    private String content;//更新日志
    private String filepath;//更新路径
    private boolean isupdate;//是否更新

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public boolean isupdate() {
        return isupdate;
    }

    public void setIsupdate(boolean isupdate) {
        this.isupdate = isupdate;
    }

    @Override
    public String toString() {
        return "CheckAppUpdateResponse{" +
                "version='" + version + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", content='" + content + '\'' +
                ", filepath='" + filepath + '\'' +
                ", isupdate=" + isupdate +
                '}';
    }
}
