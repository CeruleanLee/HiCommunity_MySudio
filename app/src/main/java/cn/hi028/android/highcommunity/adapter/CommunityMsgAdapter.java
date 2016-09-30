package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.bean.CommunityMsgBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * 与我相关列表
 * Created by 赵海 on 2016/3/26.
 */
public class CommunityMsgAdapter extends BaseAdapter {
    Context mContext;

    public List<CommunityMsgBean> getMlist() {
        return mlist;
    }

    public void setMlist(List<CommunityMsgBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    List<CommunityMsgBean> mlist = new ArrayList<CommunityMsgBean>();

    public CommunityMsgAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public CommunityMsgBean getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewholder;
        if (convertView == null) {
            mViewholder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_msg_related, null);
            mViewholder.mAvatar = (CircleImageView) convertView.
                    findViewById(R.id.civl_communityItem_avatar);
            mViewholder.mName = (TextView) convertView.
                    findViewById(R.id.tv_communityItem_name);
            mViewholder.mTime = (TextView) convertView.
                    findViewById(R.id.tv_communityItem_time);
            mViewholder.mState = (TextView) convertView.
                    findViewById(R.id.tv_community_state);
            mViewholder.mpic = (ImageView) convertView.
                    findViewById(R.id.img_community_pic);
            mViewholder.mCommon = (TextView) convertView.
                    findViewById(R.id.tv_communityItem_content);
            mViewholder.mCommunity = (TextView) convertView.
                    findViewById(R.id.tv_community);

            convertView.setTag(mViewholder);
        } else {
            mViewholder = (ViewHolder) convertView.getTag();
        }
        String replay=mlist.get(position).getReply_content();
        if (TextUtils.isEmpty(replay)){
            mViewholder.mCommon.setVisibility(View.GONE);
        }else{
            mViewholder.mCommon.setVisibility(View.VISIBLE);
            mViewholder.mCommon.setText(replay);
        }

        mViewholder.mCommunity.setText(mlist.get(position).getMessage_content());
        mViewholder.mName.setText(mlist.get(position).getNick());
        mViewholder.mTime.setText(TimeUtil.getChatTime(mlist.get(position).getReply_time()));
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mlist.get(position).getHead_pic(), mViewholder.mAvatar, R.mipmap.defult_avatar, null);
        if (TextUtils.isEmpty(mlist.get(position).getMessage_pic())) {
            mViewholder.mpic.setVisibility(View.GONE);
        } else {
            mViewholder.mpic.setVisibility(View.VISIBLE);
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mlist.get(position).getMessage_pic(), mViewholder.mpic);

        }
        mViewholder.mState.setText(mlist.get(position).getStatus());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCommunity = new Intent(mContext, GeneratedClassUtils.get(CommunityDetailAct.class));
                mCommunity.putExtra(CommunityDetailAct.ACTIVITYTAG, "Details");
                mCommunity.putExtra(CommunityDetailAct.INTENTTAG, mlist.get(position).getMessage_id());
                mContext.startActivity(mCommunity);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        CircleImageView mAvatar;
        TextView mName;
        TextView mTime;
        TextView mState;
        TextView mCommon;
        TextView mCommunity;
        ImageView mpic;
    }
}
