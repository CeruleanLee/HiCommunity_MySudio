/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteResultBean;

/**
 * @功能：<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/14<br>
 */
public class ShowVoteResultAdapter extends BaseFragmentAdapter {
    static final String Tag = "NewHuiBuyAdapter:";
    List<Auto_VoteResultBean.VoteResultDataEntity> mList = new ArrayList<Auto_VoteResultBean.VoteResultDataEntity>();
    Context context;
    LayoutInflater inflater;


    public ShowVoteResultAdapter( Context context, List<Auto_VoteResultBean.VoteResultDataEntity> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return ListUtils.getSize(mList);
    }

    @Override
    public Auto_VoteResultBean.VoteResultDataEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.e(Tag, "getView:" + position);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.showvoteresult_bigitem, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_merchant = (TextView) convertView.findViewById(R.id.newsupply_showpay_merchant);
            viewHolder.tv_merchantSum = (TextView) convertView.findViewById(R.id.newsupply_showpay_merchantSum);
            viewHolder.listContainer = (LinearLayout) convertView.findViewById(R.id.newsupply_showpay_goodslist_container);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Auto_VoteResultBean.VoteResultDataEntity mBean = mList.get(position);
        viewHolder.tv_merchant.setText(mBean.getTitle());
        List<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity> optionsList = mBean.getOptions();
        Log.e(Tag, "mInfoList.size:" + optionsList.size());
        LinearLayout infoView = getGoodsInfoView(optionsList);
        viewHolder.listContainer.addView(infoView);

        return convertView;
    }

    private LinearLayout getGoodsInfoView(List<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity> optionsList) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        for (int i = 0; i < optionsList.size(); i++) {
            Log.e(Tag, "mInfoList.i :" + i);
            Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity mBean = optionsList.get(i);
            View mListView = inflater.inflate(R.layout.item_showvotedresult_small, null);
            TextView mName = (TextView) mListView.findViewById(R.id.item_votedResult_name);
            TextView mPercent = (TextView) mListView.findViewById(R.id.item_votedResult_percent);
            ProgressBar mProgress= (ProgressBar) mListView.findViewById(R.id.item_votedResult_progressBar);

          mName.setText(mBean.getOption());
           mPercent.setText(mBean.getVote_percent());
            String percentStr=mBean.getVote_percent().replace("%","");

            float percentFloat=Float.parseFloat(percentStr.trim());
            float percentFloat2 = (float) (Math.round(percentFloat * 100)) / 100;
            if (percentStr.contains(".")){
                Log.d(Tag,"percentStr."+percentStr);

                Log.d(Tag,"contains.");
                String[] split = percentStr.split("\\.");

                Log.d(Tag,"split."+split.length);
                Log.d(Tag,"split0."+split[0]);
                Log.d(Tag,"split."+split[1]);

                if (split[1].toString().length()>5){
                    mPercent.setText(percentFloat2+"%");
                }
            }
            int percentInt= (int) percentFloat2;
            Log.d(Tag,"百分比数--->"+percentStr+"，float percentFloat--->"+percentFloat+"，int 数字--->"+percentInt);
           mProgress.setProgress(percentInt);
            layout.addView(mListView);
        }
        return layout;
    }


    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_VoteResultBean.VoteResultDataEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tv_merchant, tv_merchantSum;
        LinearLayout listContainer;


    }

}


