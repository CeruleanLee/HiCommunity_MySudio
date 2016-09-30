/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能：url地址<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/2/25<br>
 */
public class UrlsBean extends BaseBean {
    List<String> mUrlList = new ArrayList<String>();

    public List<String> getmUrlList() {
        return mUrlList;
    }

    public void setmUrlList(List<String> mUrlList) {
        this.mUrlList = mUrlList;
    }
}
