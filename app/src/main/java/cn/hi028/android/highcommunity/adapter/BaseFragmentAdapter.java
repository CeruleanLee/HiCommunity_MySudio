/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @功能：基类BaseAdapter，用于刷新和下拉<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/7<br>
 */
public class BaseFragmentAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    /**
     * 追加数据
     **/
    public void AddNewData(Object mObject) {
        if (mObject == null)
            return;
    }

    /**
     * 刷新数据
     **/
    public void RefreshData(Object mObject) {
        if (mObject == null)
            return;
    }
}
