/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;
import java.util.ArrayList;
import java.util.List;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.HuiLifeSuppBuyFrag;
import cn.hi028.android.highcommunity.bean.GdCarBean;
import cn.hi028.android.highcommunity.bean.GdCarBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 *@功能：惠生活<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-28<br>
 */
public class HuiGdBuyAdapter  extends BaseAdapter {
    HuiLifeSuppBuyFrag frag;

    public List<GdCarBean> getData() {
        return data;
    }

    public void setData(List<GdCarBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<GdCarBean> data=new ArrayList<GdCarBean>();
    public HuiGdBuyAdapter(HuiLifeSuppBuyFrag frag){
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
                    R.layout.adapter_supp_goods_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            viewHolder.tv_goods_total = (TextView) convertView
                    .findViewById(R.id.tv_goods_total);
            viewHolder.tv_goods_price = (TextView) convertView
                    .findViewById(R.id.tv_goods_price);
            viewHolder.tv_goods_reduce = (TextView) convertView
                    .findViewById(R.id.tv_goods_reduce);
            viewHolder.tv_goods_add = (TextView) convertView
                    .findViewById(R.id.tv_goods_add);
            viewHolder.tv_goods_num = (TextView) convertView
                    .findViewById(R.id.tv_goods_num);
            viewHolder.img_goods_pic = (ImageView) convertView
                    .findViewById(R.id.img_goods_pic);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_goods_name.setText(data.get(position).getGoods_name());
        viewHolder.tv_goods_price.setText(data.get(position).getPrice() + "");
        viewHolder.tv_goods_total.setText("小计：￥" + CommonUtils.f2Bi(data.get(position).getTotal_pri()));
        viewHolder.tv_goods_num.setText(data.get(position).getNum() + "");
        viewHolder.tv_goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getNum() < data.get(position).getStorage()) {
                    data.get(position).setNum(data.get(position).getNum() + 1);
                    notifyDataSetChanged();
                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("数目不能超过库存", 0);
                }
            }
        });
        viewHolder.tv_goods_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getNum() > 0) {
                    data.get(position).setNum(data.get(position).getNum() - 1);
                    notifyDataSetChanged();
                }
            }
        });
        viewHolder.tv_goods_name.setText(data.get(position).getGoods_name());
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP+data.get(position).getPic(),viewHolder.img_goods_pic,R.mipmap.default_no_pic,null);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        frag.orderParams.setGoods(data);
        frag.updateOrder();
    }

    class ViewHolder{
        ImageView img_goods_pic;
        TextView tv_goods_name;
        TextView tv_goods_total;
        TextView tv_goods_price;
        TextView tv_goods_add;
        TextView tv_goods_reduce;
        TextView tv_goods_num;
    }
}


