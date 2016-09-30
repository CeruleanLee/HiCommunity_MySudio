/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.GroupBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：群组--我的数据适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/15<br>
 */
public class GroupMineAdapter extends BaseFragmentAdapter {

    Context mContext;
    List<GroupBean> mlist = new ArrayList<GroupBean>();

    public GroupMineAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public GroupBean getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewholder;
        if (convertView == null) {
            mViewholder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_gourpfrag_item, null);
            mViewholder.mAvatar = (CircleImageView) convertView.
                    findViewById(R.id.civ_groupitem_img);
            mViewholder.mName = (TextView) convertView.
                    findViewById(R.id.tv_groupitem_name);
            mViewholder.mIntro = (TextView) convertView.
                    findViewById(R.id.tv_groupitem_details);
            mViewholder.mMember = (TextView) convertView.
                    findViewById(R.id.tv_groupitem_number);
            mViewholder.mStatus = (ImageView) convertView.
                    findViewById(R.id.iv_groupitem_status);
            mViewholder.mItemLayout = (RelativeLayout) convertView.
                    findViewById(R.id.rl_groupitem_ItemLayout);
            mViewholder.mGroupName = (TextView) convertView.
                    findViewById(R.id.tv_groupitem_GroupLayout);
            convertView.setTag(mViewholder);
        } else {
            mViewholder = (ViewHolder) convertView.getTag();
        }
        final GroupBean mBean = mlist.get(i);
        if (mBean.getId() == -1) {
            mViewholder.mItemLayout.setVisibility(View.GONE);
            mViewholder.mGroupName.setVisibility(View.VISIBLE);
            mViewholder.mGroupName.setText(mBean.getName());
        } else {
            mViewholder.mItemLayout.setVisibility(View.VISIBLE);
            mViewholder.mGroupName.setVisibility(View.GONE);
            mViewholder.mName.setText(mBean.getName());
            if (!TextUtils.isEmpty(mBean.getPic()))
                ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getPic(), mViewholder.mAvatar);
            mViewholder.mIntro.setText(mBean.getIntro());
            mViewholder.mMember.setText(mBean.getMembers() + "人");
            if (mBean.getOwnerid() != HighCommunityApplication.mUserInfo.getId()) {
                mViewholder.mStatus.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(mBean.getIsin()) && mBean.getIsin().equals("1")) {
                    mViewholder.mStatus.setSelected(true);
                } else {
                    mViewholder.mStatus.setSelected(false);
                }
            } else {
                mViewholder.mStatus.setVisibility(View.GONE);
            }

            mViewholder.mStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupWindow mWaitingWindow =
                            HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(mContext, view, Gravity.CENTER);
                    if (!TextUtils.isEmpty(mBean.getIsin()) && mBean.getIsin().equals("0")) {
                        HTTPHelper.AttentionGroup(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                                mWaitingWindow.dismiss();
                            }

                            @Override
                            public void onSuccess(Object message) {
                                mWaitingWindow.dismiss();
                                mBean.setIsin("1");
                                HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
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
                                mWaitingWindow.dismiss();
                            }
                        }, HighCommunityApplication.mUserInfo.getId() + "", mBean.getId() + "");
                    } else {
                        HTTPHelper.CancelAttention(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                                mWaitingWindow.dismiss();
                            }

                            @Override
                            public void onSuccess(Object message) {
                                mWaitingWindow.dismiss();
                                mBean.setIsin("0");
                                HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
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
                                mWaitingWindow.dismiss();
                            }
                        }, HighCommunityApplication.mUserInfo.getId() + "", mBean.getId() + "");
                    }
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView mAvatar;
        TextView mName;
        TextView mIntro;
        TextView mMember;
        ImageView mStatus;
        RelativeLayout mItemLayout;
        TextView mGroupName;
    }

    public void clear() {
        mlist.clear();
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mlist.size() > 0) {
            mlist.clear();
        }
        List<GroupBean> mTemp;
        if (mObject instanceof List<?>) {
            mTemp = (List<GroupBean>) mObject;
        } else {
            return;
        }
        GroupBean mOwn = new GroupBean();
        mOwn.setName("我的创建");
        mOwn.setId(-1);
        mlist.add(mOwn);
        GroupBean mNotic = new GroupBean();
        mNotic.setName("我的关注");
        mNotic.setId(-1);
        for (int i = 0; i < mTemp.size(); i++) {
            if (mTemp.get(i).getOwnerid() != 0 && mTemp.get(i).getOwnerid() == HighCommunityApplication.mUserInfo.getId()) {
                mlist.add(mTemp.get(i));
            }
        }
        mlist.add(mNotic);
        for (int i = 0; i < mTemp.size(); i++) {
            if (mTemp.get(i).getOwnerid() != 0 && mTemp.get(i).getOwnerid() != HighCommunityApplication.mUserInfo.getId()) {
                mlist.add(mTemp.get(i));
            }
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData(Object mObject) {
        if (mlist != null) {
            mlist.clear();
        }
        notifyDataSetChanged();
    }

}
