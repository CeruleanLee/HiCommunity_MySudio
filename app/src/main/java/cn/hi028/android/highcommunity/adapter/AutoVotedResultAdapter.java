/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.don.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteResultBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：自治大厅 选举结果adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoVotedResultAdapter extends BaseFragmentAdapter {
    static final String Tag="AutoVotedResultAdapter";
    public AddressListFrag mFrag;
    List<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity> mList = new ArrayList<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    public AutoVotedResultAdapter(List<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_votedresult, null);
            mViewHolder.mAvatar = (CircleImageView) convertView.findViewById(R.id.item_votedResult_avatar);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.item_votedResult_name);
            mViewHolder.mPercent = (TextView) convertView.findViewById(R.id.item_votedResult_percent);
mViewHolder.mProgress= (ProgressBar) convertView.findViewById(R.id.item_votedResult_progressBar);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity mBean = mList.get(position);


        if (mBean.getPic() == null || mBean.getPic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, mViewHolder.mAvatar);
        } else {
            if (mViewHolder.mAvatar!=null){

                BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mAvatar);
            }else{

            }
        }
        mViewHolder.mName.setText(mBean.getOption());
        mViewHolder.mPercent.setText(mBean.getVote_percent());
        String percentStr=mBean.getVote_percent().replace("%","");
        float percentFloat=Float.parseFloat(percentStr);
        int percentInt= (int) percentFloat;
        Log.d(Tag,"百分比数--->"+percentStr+"，float percentFloat--->"+percentFloat+"，int 数字--->"+percentInt);
        mViewHolder.mProgress.setProgress(percentInt);
        return convertView;
    }
    class ViewHolder {
        CircleImageView mAvatar;
        TextView mName;
        TextView mPercent;
        ProgressBar mProgress;
    }
    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }
    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

}
