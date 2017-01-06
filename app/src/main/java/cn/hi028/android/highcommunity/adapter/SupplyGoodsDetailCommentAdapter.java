/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.don.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：新版直供商品详情评价adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/30<br>
 */
public class SupplyGoodsDetailCommentAdapter extends BaseFragmentAdapter {
static final String Tag="SupplyGoodsDetailCommentAdapter:";
    List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity> mList = new ArrayList<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public SupplyGoodsDetailCommentAdapter(List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_merchant_evaluation, null);
            mViewHolder.head = (CircleImageView) convertView.findViewById(R.id.item_evaluation_iv);
            mViewHolder.username = (TextView) convertView.findViewById(R.id.item_username_tv);
            mViewHolder.merchantname = (TextView) convertView.findViewById(R.id.item_evalutaion_merchantname_tv);
            mViewHolder.time = (TextView) convertView.findViewById(R.id.item_evaluation_time);
            mViewHolder.content = (TextView) convertView.findViewById(R.id.item_evalution_content_tv);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity mBean = mList.get(position);
        if (mBean.getHead_pic() == null || mBean.getHead_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, mViewHolder.head);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getHead_pic(), mViewHolder.head);
        }
        mViewHolder.username.setText(mBean.getNick());
        mViewHolder.merchantname.setVisibility(View.GONE);

        mViewHolder.time.setText(TimeUtil.longToString(Long.parseLong(mBean.getTime())*1000,"yyyy年MM月dd日"));
//        mViewHolder.time.setText(TimeUtil.getDayAllTime(Long.parseLong(mBean.getTime())));
//        mViewHolder.time.setText(getTime(Long.parseLong(mBean.getTime())));
        mViewHolder.content.setText(mBean.getContent());
        return convertView;
    }
    public String getTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(new Date(time));
    }
    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.CommentEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        CircleImageView head;
        TextView username;
        TextView merchantname;
        TextView time;
        TextView content;

    }
}
