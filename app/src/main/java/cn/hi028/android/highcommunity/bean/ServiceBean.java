/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：第三方服务<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/7<br>
 */
public class ServiceBean extends BaseBean {
    String url;
    String pic;
    String name;
    String type;
    ServiceEdata edata;

    @Override
    public String toString() {
        return "ServiceBean{" +
                "url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", edata=" + edata +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ServiceEdata getEdata() {
        return edata;
    }

    public void setEdata(ServiceEdata edata) {
        this.edata = edata;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class ServiceEdata extends BaseBean {
        String api_url;
        String id;
        String logo;
        String openid;
        String username;

        @Override
        public String toString() {
            return "ServiceEdata{" +
                    "api_url='" + api_url + '\'' +
                    ", id='" + id + '\'' +
                    ", logo='" + logo + '\'' +
                    ", openid='" + openid + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }

        public String getApi_url() {
            return api_url;
        }

        public void setApi_url(String api_url) {
            this.api_url = api_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }


}
