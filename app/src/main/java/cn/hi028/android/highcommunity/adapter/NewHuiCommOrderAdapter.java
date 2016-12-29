/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.params.NewHuiSuppGdParams;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 *@功能：评论订单<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-02-01<br>
 */
public class NewHuiCommOrderAdapter extends BaseAdapter {
    BaseFragment frag;

    public List<NewHuiSuppGdParams> getData() {
        return data;
    }

    public void setData(List<NewHuiSuppGdParams> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<NewHuiSuppGdParams> data=new ArrayList<NewHuiSuppGdParams>();
    public NewHuiCommOrderAdapter(BaseFragment frag){
        this.frag=frag;
    }
    @Override
    public int getCount() {
        return  ListUtils.getSize(data);
    }

    @Override
    public Object getItem(int position) {
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
                    R.layout.adapter_comment_order, null);
            viewHolder = new ViewHolder();
            viewHolder.edt_goods_comment = (EditText) convertView.findViewById(R.id.edt_goods_comment);
            viewHolder.tv_goods_price = (TextView) convertView
                    .findViewById(R.id.tv_goods_price);
            viewHolder.tv_goods_standard = (TextView) convertView
                    .findViewById(R.id.tv_goods_standard);
            viewHolder.tv_goods_name = (TextView) convertView
                    .findViewById(R.id.tv_goods_name);
            viewHolder.img_goods_pic = (ImageView) convertView
                    .findViewById(R.id.img_goods_pic);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.edt_goods_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data.get(position).setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.tv_goods_name.setText(data.get(position).getName());
        viewHolder.tv_goods_price.setText("￥"+CommonUtils.f2Bi(data.get(position).getGoods_price()));
        if (data.get(position).getStandard_name()!=null){
            viewHolder.tv_goods_standard.setText("规格："+data.get(position).getStandard_name());
        }


        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP+data.get(position).getPic(),viewHolder.img_goods_pic,R.mipmap.default_no_pic,null);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView img_goods_pic;
        TextView tv_goods_name;
        EditText edt_goods_comment;
        TextView tv_goods_price,tv_goods_standard;
    }
}


