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
import android.widget.Toast;

import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Inquiry;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InquiryDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：询问 评论详情adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/17<br>
 */
public class Auto_InquiryDetailAdapter extends BaseFragmentAdapter {
    static final String Tag = "~~~000 Auto_InquiryDetailAdapter：";
    AutoDetail_Inquiry mFrag;
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

    public Auto_InquiryDetailAdapter(List<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity> mList, Context mContext,AutoDetail_Inquiry mFrag) {
        super();
        this.mContext = mContext;
        this.mList = mList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mFrag = mFrag;

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
            convertView = layoutInflater.inflate(R.layout.item_reportdetail_replay, null);
            mViewHolder.mAvatar = (CircleImageView) convertView.findViewById(R.id.item_reportdetail_Image);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.item_reportdetail_Name);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.item_reportdetail_time);
            mViewHolder.mContent = (TextView) convertView.findViewById(R.id.item_reportdetail_Content);
            mViewHolder.mReplyLayout = (LinearLayout) convertView.findViewById(R.id.item_reportdetail_reply);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity mBean = mList.get(position);
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getPic(), mViewHolder.mAvatar);
        mViewHolder.mName.setText(mBean.getFrom_name() + "：");
        mViewHolder.mTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(Long.parseLong(mBean.getReply_time() + "")));
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
                int fromNameLength = mBean.getSub_reply().get(i).getFrom_name().length();
                SpannableString mSpan = new SpannableString(mBean.getSub_reply().get(i).getFrom_name() + " 回复 " + mBean.getSub_reply().get(i).getTo_name()
                        + ":" + mBean.getSub_reply().get(i).getContent());
                mText.setClickable(true);
                mText.setFocusable(true);
                mText.setMovementMethod(LinkMovementMethod.getInstance());//超链接
                final String From_name = mBean.getSub_reply().get(i).getFrom_name();
                final String To_name = mBean.getSub_reply().get(i).getTo_name();
                final String To_id = mBean.getSub_reply().get(i).getTo_id() + "";//主评论人id
                final String From_id = mBean.getSub_reply().get(i).getFrom_id() + "";//小评论里的评论人id
                Log.d(Tag, "~~~From_name:" + From_name + ",To_name" + To_name + ",From_id" + From_id + ",To_id" + To_id);
                mSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(mContext.getResources().getColor(R.color.Defult_Color_AppGreen));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View view) {
                        Log.d(Tag, "点击了：" + From_name + ",hostId" + From_id + ",mBean.getId()评论id " + mBean.getId());
                        mFrag.setText("回复:" + From_name, From_id, mBean.getId() + "", true);
                        mTempReplies = mBean;
                        avoidHintColor(view);
                        mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
//                        mTempReply.setGid(hostId);
                        mTempReply.setFrom_id(HighCommunityApplication.mUserInfo.getId());
                        mTempReply.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
                        mTempReply.setTo_name(From_name);
                        Log.d(Tag, "点击结束的小回复bean+" + mTempReply.toString());
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
                        avoidHintColor(view);
                        Log.d(Tag, "点击了：" + To_name + ",To_id" + To_id + ",mBean.getId()评论id " + mBean.getId());

                        mFrag.setText("回复:" + To_name, To_id, mBean.getId() + "", true);
                        mTempReplies = mBean;
                        mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
//                     mTempReply.setGid(hostId);
                        mTempReply.setFrom_id(HighCommunityApplication.mUserInfo.getId());
                        mTempReply.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
                        mTempReply.setTo_name(To_name);
                    }
                }, fromNameLength + 4, fromNameLength + 4 + toNameLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mText.setText(mSpan);
                mText.setLayoutParams(mparams);
                mText.setTextColor(mFrag.getActivity().getResources().getColor(R.color.defult_text_color));
                mText.setPadding(padding, padding / 2, padding, padding / 2);
                mViewHolder.mReplyLayout.addView(mText);
            }
        } else {
            mViewHolder.mReplyLayout.setVisibility(View.GONE);
        }
        mViewHolder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了主评论name ", Toast.LENGTH_SHORT).show();
                Log.d(Tag, "主评论id： " + mBean.getId() + "");
                //对监督主评论的回复   parentid=主评论id
                mTempReplies = mBean;
                mFrag.setText("回复:" + mBean.getFrom_name(), mBean.getFrom_id() + "", mBean.getId() + "", true);
                mTempReply = new Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity.InquiryDetailSubReplyEntity();
//                mTempReply.set(mBean.getId());

                mTempReply.setFrom_id(HighCommunityApplication.mUserInfo.getId());
                mTempReply.setFrom_name(HighCommunityApplication.mUserInfo.getNick());
                mTempReply.setTo_name(mBean.getFrom_name());
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
//            mTempBean.setReply_time(System.currentTimeMillis() / 1000);
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
