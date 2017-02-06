/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Message;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InquiryDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：留言 评论详情adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/17<br>
 */
public class Auto_MessageDetailAdapter extends BaseFragmentAdapter {
    static final String Tag = "留言详情Adapter：";
    AutoDetail_Message mFrag;
    List<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity> mList = new ArrayList<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity>();
    /**
     * 主评论 bean
     **/
    Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity mTempReplies = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity();
    /**
     * 小评论 bean
     **/
    Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
    Context mContext;
    private LayoutInflater layoutInflater;
boolean isRepresent;
    public Auto_MessageDetailAdapter(List<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity> mList, Context mContext, AutoDetail_Message mFrag, boolean isRepresent) {
        super();
        this.mContext = mContext;
        this.mList = mList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mFrag = mFrag;
this.isRepresent=isRepresent;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        CircleImageView mAvatar;
        TextView mName;
        TextView mTime;
        TextView mContent;
        LinearLayout mReplyLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_messagedetail_replay, null);
            mViewHolder.mAvatar = (CircleImageView) convertView.findViewById(R.id.item_reportdetail_Image);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.item_reportdetail_Name);
            mViewHolder.mContent = (TextView) convertView.findViewById(R.id.item_reportdetail_Content);
            mViewHolder.mReplyLayout = (LinearLayout) convertView.findViewById(R.id.item_reportdetail_reply);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity mBean = mList.get(position);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mAvatar);
        Log.e(Tag, "isRepresent:" + isRepresent);

        if (isRepresent){
            mViewHolder.mName.setText( "代表回复：");

        }else{

            mViewHolder.mName.setText(mBean.getFrom_name() + "：");
        }
        mViewHolder.mContent.setText(mBean.getContent());
        if (mBean.getSub_reply().size() > 0) {
            mViewHolder.mReplyLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams mparams = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int padding = HighCommunityUtils.GetInstantiation().dip2px(8);
            mViewHolder.mReplyLayout.removeAllViews();
            for (int i = 0; i < mBean.getSub_reply().size(); i++) {
                TextView mText = new TextView(mContext);
                int toNameLength = mBean.getSub_reply().get(i).getTo_name().length();
                int fromNameLength;
                if (isRepresent){
                    fromNameLength="代表".length();
                }else{

                    fromNameLength = mBean.getSub_reply().get(i).getFrom_name().length();
                }
                SpannableString mSpan = new SpannableString(mBean.getSub_reply().get(i).getFrom_name() + " 回复 " + mBean.getSub_reply().get(i).getTo_name()
                        + ":" + mBean.getSub_reply().get(i).getContent());
                mText.setClickable(true);
                mText.setFocusable(true);
                mText.setMovementMethod(LinkMovementMethod.getInstance());//超链接
                final String From_name ;
                if (isRepresent){
                    From_name = "代表";
                }else{
                   From_name = mBean.getSub_reply().get(i).getFrom_name();
                }
                final String To_name = mBean.getSub_reply().get(i).getTo_name();
                final String To_id = mBean.getSub_reply().get(i).getTo_id() + "";//主评论人id
                final String From_id = mBean.getSub_reply().get(i).getFrom_id() + "";//小评论里的评论人id
                mSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(mContext.getResources().getColor(R.color.Defult_Color_AppGreen));
                        ds.setUnderlineText(false);
                    }
                    @Override
                    public void onClick(View view) {
                        if (!isRepresent){
                            mFrag.setText("回复:" + From_name, From_id, mBean.getId() + "", true);
                            mTempReplies = mBean;
                            avoidHintColor(view);
                            mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
                            mTempReply.setFrom_id(HighCommunityApplication.mUserInfo.getId());
                            mTempReply.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
                            mTempReply.setTo_name(From_name);
                            Log.d(Tag, "点击结束的小回复bean+" + mTempReply.toString());
                        }
                    }
                }, 0, fromNameLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(mContext.getResources().getColor(R.color.Defult_Color_AppGreen));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View view) {
                        if (!isRepresent){
                            avoidHintColor(view);
                            mFrag.setText("回复:" + To_name, To_id, mBean.getId() + "", true);
                            mTempReplies = mBean;
                            mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
                            mTempReply.setFrom_id(HighCommunityApplication.mUserInfo.getId());
                            mTempReply.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
                            mTempReply.setTo_name(To_name);
                        }
                    }
                }, fromNameLength + 4, fromNameLength + 4 + toNameLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mText.setText(mSpan);
                mText.setLayoutParams(mparams);
                mText.setTextColor(mFrag.getActivity().getResources().getColor(R.color.defult_text_color));
                mText.setPadding(padding, 0, padding, padding / 2);
                mViewHolder.mReplyLayout.addView(mText);
            }
        } else {
            mViewHolder.mReplyLayout.setVisibility(View.GONE);
        }
        mViewHolder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRepresent){
                    mTempReplies = mBean;
                    mFrag.setText("回复:" + mBean.getFrom_name(), mBean.getFrom_id() + "", mBean.getId() + "", true);
                    mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
                    mTempReply.setFrom_id(HighCommunityApplication.mUserInfo.getId());
                    mTempReply.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
                    mTempReply.setTo_name(mBean.getFrom_name());
                }
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
            mTempReply.setContent(content);
            mTempReplies.getSub_reply().add(mTempReply);
        } else {
            Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity mTempBean = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity();
            mTempBean.setId(Integer.parseInt(id));
            mTempBean.setPic(HighCommunityApplication.mUserInfo.getHead_pic());
            mTempBean.setFrom_id(HighCommunityApplication.mUserInfo.getId());
            mTempBean.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
            mTempBean.setContent(content);
            mTempBean.setSub_reply(new ArrayList<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity>());
            mList.add(mTempBean);
        }
        notifyDataSetChanged();
    }


    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity>) mObject;
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
