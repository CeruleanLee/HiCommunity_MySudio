/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *@功能：地址<br>
 *@作者： 李凌云<br>
 *@版本：1.0<br>
 *@时间：2016/1/21<br>
 */
public class AddressBean extends BaseBean implements Parcelable {
    String id;
    String address;
    String real_name;
    String tel;
    String isDefault;

    @Override
    public String toString() {
        return "AddressBean{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", real_name='" + real_name + '\'' +
                ", tel='" + tel + '\'' +
                ", isDefault='" + isDefault + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeString(this.real_name);
        dest.writeString(this.tel);
        dest.writeString(this.isDefault);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.real_name = in.readString();
        this.tel = in.readString();
        this.isDefault = in.readString();
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
