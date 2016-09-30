/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

/**
 * @功能：认证对象数据<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-11<br>
 */
public class CertifiBean extends BaseBean {
    private int vid;//"1",
    private String real_name;// "小白",
    private String tel;// "15645645",
    private String address;// "保利花园二期1栋一1801"
    private String status;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
