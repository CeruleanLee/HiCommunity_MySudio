/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.fragment.CommunityDetilsFrag;
import cn.hi028.android.highcommunity.bean.RepliesBean;
import cn.hi028.android.highcommunity.bean.ReplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：社区评论详情adapter<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
public class CommDetailAdapter extends BaseFragmentAdapter {
    CommunityDetilsFrag mFrag;
    List<RepliesBean> mList = new ArrayList<RepliesBean>();
    RepliesBean mTempReplies = new RepliesBean();
    ReplyBean mTempReply = new ReplyBean();
    PopupWindow mWatingWindow;
    public CommDetailAdapter(CommunityDetilsFrag mContext) {

        this.mFrag = mContext;
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
            convertView = LayoutInflater.from(mFrag.getActivity()).inflate(R.layout.adapter_community_reply, null);
            mViewHolder.mAvatar = (CircleImageView) convertView
                    .findViewById(R.id.civ_commDetilsItem_Image);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_commDetilsItem_Name);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.tv_commDetilsItem_time);
            mViewHolder.mContent = (TextView) convertView
                    .findViewById(R.id.tv_commDetilsItem_Content);
            mViewHolder.mComment = (TextView) convertView
                    .findViewById(R.id.tv_commDetilsItem_replay);
            mViewHolder.mAssist = (TextView) convertView
                    .findViewById(R.id.tv_commDetilsItem_assist);
            mViewHolder.mReply = (LinearLayout) convertView
                    .findViewById(R.id.ll_commDetilsItem_reply);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final RepliesBean mBean = mList.get(position);
        mViewHolder.mAssist.setText(mBean.getComment_praise() + "");
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
                int hostLength = mBean.getReply().get(i).getHost().length();
                int GuestLength = mBean.getReply().get(i).getGuest().length();
                SpannableString mSpan = new SpannableString(mBean.getReply().get(i).getHost() + "回复" + mBean.getReply().get(i).getGuest()
                        + ":" + mBean.getReply().get(i).getReply_content());
                mText.setClickable(true);
                mText.setFocusable(true);
                mText.setMovementMethod(LinkMovementMethod.getInstance());
                final String host = mBean.getReply().get(i).getHost();
                final String gust = mBean.getReply().get(i).getGuest();
                final String hostId = mBean.getReply().get(i).getHid();
                final String gustId = mBean.getReply().get(i).getGid();
                mSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(mFrag.getActivity().getResources().getColor(R.color.Defult_Color_AppGreen));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View view) {
                        mFrag.setText("回复:" + host, hostId, mBean.getParentId(), true);
                        mTempReplies = mBean;
                        avoidHintColor(view);
                        mTempReply = new ReplyBean();
                        mTempReply.setGid(hostId);
                        mTempReply.setHid(HighCommunityApplication.mUserInfo.getId() + "");
                        mTempReply.setHost(HighCommunityApplication.mUserInfo.getNick());
                        mTempReply.setGuest(host);
                    }
                }, 0, hostLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(mFrag.getActivity().getResources().getColor(R.color.Defult_Color_AppGreen));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View view) {
                        avoidHintColor(view);
                        mFrag.setText("回复:" + gust, gustId, mBean.getParentId(), true);
                        mTempReplies = mBean;
                        mTempReply = new ReplyBean();
                        mTempReply.setGid(gustId);
                        mTempReply.setHid(HighCommunityApplication.mUserInfo.getId() + "");
                        mTempReply.setHost(HighCommunityApplication.mUserInfo.getNick());
                        mTempReply.setGuest(gust);
                    }
                }, hostLength + 2, hostLength + 2 + GuestLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mText.setText(mSpan);
                mText.setLayoutParams(mparams);
                mText.setTextColor(mFrag.getActivity().getResources().getColor(R.color.defult_text_color));
                mText.setPadding(padding, padding / 2, padding, padding / 2);
                mViewHolder.mReply.addView(mText);
            }
        } else {
            mViewHolder.mReply.setVisibility(View.GONE);
        }
        mViewHolder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTempReplies = mBean;
                mFrag.setText("回复:" + mBean.getNick(), mBean.getId(), mBean.getParentId(), true);
                mTempReply = new ReplyBean();
                mTempReply.setGid(mBean.getId());

                mTempReply.setHid(HighCommunityApplication.mUserInfo.getId() + "");
                mTempReply.setHost(HighCommunityApplication.mUserInfo.getNick());
                mTempReply.setGuest(mBean.getNick());
            }
        });
        mViewHolder.mAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
                    HighCommunityUtils.GetInstantiation().ShowShouldLogin();
                    return;
                }
                mWatingWindow = HighCommunityUtils.GetInstantiation()
                        .ShowWaittingPopupWindow(mFrag.getActivity(), mViewHolder.mAssist, Gravity.CENTER);
                HTTPHelper.AssistComment(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        if (mWatingWindow != null && mWatingWindow.isShowing()) {
                            mWatingWindow.dismiss();
                        }
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        if (mWatingWindow != null && mWatingWindow.isShowing()) {
                            mWatingWindow.dismiss();
                        }
                        mBean.setComment_praise(mBean.getComment_praise() + 1);
                        // TODO  这里应该要点赞图片变化的  但是接口没有数据可以判断你是不是点赞了  所以暂时先不处理自己点赞图片变化
//                        mViewHolder.mAssist.setSelected(true);
                        notifyDataSetChanged();
                    }

                    @Override
                    public Object onResolve(String result) {
                        return result;
                    }

                    @Override
                    public void setAsyncTask(AsyncTask asyncTask) {

                    }

                    @Override
                    public void cancleAsyncTask() {
                        if (mWatingWindow != null && mWatingWindow.isShowing()) {
                            mWatingWindow.dismiss();
                        }
                    }

                    @Override
                    public void shouldLogin(boolean isShouldLogin) {

                    }

                    @Override
                    public void shouldLoginAgain(boolean isShouldLogin, String msg) {
                        if (isShouldLogin){
                            HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                            HighCommunityApplication.toLoginAgain(mFrag.getActivity());
                        }
                    }
                }, HighCommunityApplication.mUserInfo.getId() + "", mBean.getParentId());
            }
        });
        mViewHolder.mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mDetails = new Intent(mFrag.getActivity(), GeneratedClassUtils.get(MenuLeftAct.class));
                mDetails.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_USERINFO);
                mDetails.putExtra(MenuLeftAct.INTENTTAG, mBean.getId() + "");
                mFrag.getActivity().startActivity(mDetails);
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
    public void setNewData(boolean isReplay, String content, String id) {
        if (isReplay) {
            mTempReply.setReply_content(content);
            mTempReplies.getReply().add(mTempReply);
        } else {
            RepliesBean mTempBean = new RepliesBean();
            mTempBean.setParentId(id);
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
        TextView mComment;
        TextView mAssist;
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
