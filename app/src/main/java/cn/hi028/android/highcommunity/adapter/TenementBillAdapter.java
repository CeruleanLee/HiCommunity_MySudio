/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.activity.fragment.ServicePaymentDetailFrag;
import cn.hi028.android.highcommunity.activity.fragment.TenementBillFrag;
import cn.hi028.android.highcommunity.bean.BillSimpleBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：物业账单<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-01-30<br>
 */
public class TenementBillAdapter extends BaseAdapter {
    TenementBillFrag frag;
    List<BillSimpleBean> data = new ArrayList<BillSimpleBean>();
    int flagTp;

    public List<BillSimpleBean> getData() {
        return data;
    }

    public void setData(List<BillSimpleBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public TenementBillAdapter(TenementBillFrag frag, int flagTp) {
        this.frag = frag;
        this.flagTp = flagTp;
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public BillSimpleBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_tenement_bill, null);
            viewHolder = new ViewHolder();
            viewHolder.mType = (ImageView) convertView
                    .findViewById(R.id.img_tenementBill_Pic);
            viewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.tv_tenementBill_time);
            viewHolder.mContent = (TextView) convertView
                    .findViewById(R.id.tv_tenementBill_Content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BillSimpleBean mBean = data.get(position);
        viewHolder.mTime.setText(mBean.getTime());
        viewHolder.mContent.setText(mBean.getName());
        int res = 0;
        switch (mBean.getType()) {
            case 1:
                res = R.mipmap.img_paytype_elec;
                break;
            case 2:
                res = R.mipmap.img_paytype_water;
                break;
            case 3:
                res = R.mipmap.img_paytype_qi;
                break;
            case 4:
                res = R.mipmap.img_paytype_wuguan;
                break;
            case 5:
                res = R.mipmap.img_paytype_wuguan;
                break;
        }
        ImageLoaderUtil.disPlay("drawable://" + res, viewHolder.mType);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mPayDetail = new Intent(frag.getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
                mPayDetail.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICEPAYMENT_DETAILS);
                mPayDetail.putExtra(ServiceSecondAct.INTENTTAG, mBean.getType() + "");
                mPayDetail.putExtra(ServicePaymentDetailFrag.FRAGMENTTAG, mBean.getFid());
                frag.getActivity().startActivity(mPayDetail);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView mType;
        TextView mTime;
        TextView mContent;
    }
}
