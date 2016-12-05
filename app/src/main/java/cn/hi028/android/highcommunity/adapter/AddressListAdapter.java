/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.GeneratedClassUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressModifyAct;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.AddressBean;

/**
 * @功能：地址列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/21<br>
 */
public class AddressListAdapter extends BaseFragmentAdapter {

    public AddressListFrag mFrag;
    List<AddressBean> mList = new ArrayList<AddressBean>();

    public AddressListAdapter(AddressListFrag mFrag) {
        this.mFrag = mFrag;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public AddressBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mFrag.getActivity()).inflate(R.layout.adapter_addressitem, null);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_addressItem_name);
            mViewHolder.mPhone = (TextView) convertView
                    .findViewById(R.id.tv_addressItem_Phone);
            mViewHolder.mDefult = (TextView) convertView
                    .findViewById(R.id.tv_addressItem_isDefult);
            mViewHolder.mAddress = (TextView) convertView
                    .findViewById(R.id.tv_addressItem_address);
            mViewHolder.mModify = (ImageView) convertView
                    .findViewById(R.id.iv_addressItem_Modify);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final AddressBean mBean = mList.get(position);
        mViewHolder.mName.setText(mBean.getReal_name());
        mViewHolder.mPhone.setText(mBean.getTel());
        mViewHolder.mAddress.setText(mBean.getAddress());
        if (mBean.getIsDefault().equals("1")) {
            mViewHolder.mDefult.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mDefult.setVisibility(View.GONE);
        }
        mViewHolder.mModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mModify = new Intent(mFrag.getActivity(), GeneratedClassUtils.get(AddressModifyAct.class));

//                mModify.putExtra("modifyData", (Parcelable) mBean);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("modifyData", mBean);
                mModify.putExtras(mBundle);
                mModify.putExtra(AddressModifyAct.INTENTTAG, mBean.getId());
                if (TextUtils.isEmpty(mFrag.from)) {
                    mModify.putExtra(AddressModifyAct.INTENTTAGDELETE_TAG, 1);
                }
                mFrag.startActivity(mModify);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView mName;
        TextView mPhone;
        TextView mDefult;
        TextView mAddress;
        ImageView mModify;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<AddressBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }
}
