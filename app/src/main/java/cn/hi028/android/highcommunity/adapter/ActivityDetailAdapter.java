/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.ActivityDetailsFrag;
import cn.hi028.android.highcommunity.bean.RepliesBean;
import cn.hi028.android.highcommunity.bean.ReplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：活动详情adapter<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015-1-15<br>
 */
public class ActivityDetailAdapter extends BaseFragmentAdapter {
    ActivityDetailsFrag mFrag;
    List<RepliesBean> mList = new ArrayList<RepliesBean>();
    RepliesBean mTempReplies = new RepliesBean();
    ReplyBean mTempReply = new ReplyBean();
    PopupWindow mWatingWindow;

    public ActivityDetailAdapter(ActivityDetailsFrag mContext) {
        this.mFrag = mContext;
        mTempReplies.setReply(new ArrayList<ReplyBean>());
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mFrag.getActivity()).inflate(R.layout.adapter_activitydetail_reply, null);
            mViewHolder.mAvatar = (CircleImageView) convertView
                    .findViewById(R.id.civ_activityDetilsItem_Image);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_activityDetilsItem_Name);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.tv_activityDetilsItem_time);
            mViewHolder.mContent = (TextView) convertView
                    .findViewById(R.id.tv_activityDetilsItem_Content);
            mViewHolder.mReply = (LinearLayout) convertView
                    .findViewById(R.id.ll_activityDetilsItem_reply);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final RepliesBean mBean = mList.get(position);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mViewHolder.mAvatar);
        mViewHolder.mName.setText(mBean.getNick());
        mViewHolder.mTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(Long.parseLong(mBean.getReply_time())));
        mViewHolder.mContent.setText(mBean.getReply_content());
        if (mBean.getReply().size() > 0) {
            mViewHolder.mReply.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams mparams = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int padding = HighCommunityUtils.GetInstantiation().dip2px(8);
            mViewHolder.mReply.removeAllViews();
            for (int i = 0; i < mBean.getReply().size(); i++) {
                TextView mText = new TextView(mFrag.getActivity());
                SpannableString mSpan = new SpannableString(mBean.getReply().get(i).getHost() + ":" + mBean.getReply().get(i).getReply_content());
                mSpan.setSpan(new ForegroundColorSpan(mFrag.getActivity().getResources().getColor(R.color.Defult_Color_AppGreen)),
                        0, mBean.getReply().get(i).getHost().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                mText.setClickable(true);
                mText.setFocusable(true);
                mText.setMovementMethod(LinkMovementMethod.getInstance());
                mText.setText(mSpan);
                mText.setLayoutParams(mparams);
                mText.setTextColor(mFrag.getActivity().getResources().getColor(R.color.defult_text_color));
                mText.setPadding(padding, padding / 2, padding, padding / 2);
                mViewHolder.mReply.addView(mText);
            }
        } else {
            mViewHolder.mReply.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFrag.setToWho("回复:" + mBean.getNick(), mBean.getId());
                mTempReplies = mBean;
                avoidHintColor(view);
                mTempReply = new ReplyBean();
                mTempReply.setGid(mBean.getId());
                mTempReply.setHid(HighCommunityApplication.mUserInfo.getId() + "");
                mTempReply.setHost(HighCommunityApplication.mUserInfo.getNick());
                mTempReply.setGuest(mBean.getNick());
            }
        });
        return convertView;
    }

    /**
     * 动态刷新数据
     *
     * @param isReplay
     * @param content
     */
    public void setNewData(boolean isReplay, String content) {
        if (isReplay) {
            mTempReply.setReply_content(content);
            mTempReplies.getReply().add(mTempReply);
        } else {
            RepliesBean mTempBean = new RepliesBean();
            mTempBean.setHead_pic(HighCommunityApplication.mUserInfo.getHead_pic());
            mTempBean.setId(HighCommunityApplication.mUserInfo.getId() + "");
            mTempBean.setNick(HighCommunityApplication.mUserInfo.getNick());
            mTempBean.setReply_time(System.currentTimeMillis() / 1000 + "");
            mTempBean.setReply_content(content);
            mTempBean.setReply(new ArrayList<ReplyBean>());
            mList.add(mTempBean);
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        CircleImageView mAvatar;
        TextView mName;
        TextView mTime;
        TextView mContent;
        LinearLayout mReply;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<RepliesBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }

    private void avoidHintColor(View view) {
        if (view instanceof TextView)
            ((TextView) view).setHighlightColor(mFrag.getActivity().getResources().getColor(R.color.defult_color_transparent));
    }
}
