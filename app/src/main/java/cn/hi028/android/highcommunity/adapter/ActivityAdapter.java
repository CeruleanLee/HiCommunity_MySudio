/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ActiveAct;
import cn.hi028.android.highcommunity.bean.ActiveBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：活动列表适配器<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/12<br>
 */
public class ActivityAdapter extends BaseFragmentAdapter {

    Context mContext;
    List<ActiveBean> mList = new ArrayList<ActiveBean>();

    public ActivityAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_activityitem, null);
            mViewHolder.mImage = (ImageView) convertView
                    .findViewById(R.id.iv_activityitem_image);
            mViewHolder.mTitle = (TextView) convertView
                    .findViewById(R.id.tv_activityitem_title);
            mViewHolder.mJoin = (TextView) convertView
                    .findViewById(R.id.tv_activityitem_isJoin);
            mViewHolder.mNumber = (TextView) convertView
                    .findViewById(R.id.tv_activityitem_Numbers);
            mViewHolder.mName = (TextView) convertView
                    .findViewById(R.id.tv_activityitem_Name);
            mViewHolder.mDeadTime = (TextView) convertView
                    .findViewById(R.id.tv_activityitem_DeadTime);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final ActiveBean mBean = mList.get(position);
        if (mBean.getPic() != null && mBean.getPic().size() > 0) {
            System.out.print("bpi url:" + mBean.getPic().get(0));
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getPic().get(0), mViewHolder.mImage);
        }
        mViewHolder.mName.setText(mBean.getNick());
        mViewHolder.mTitle.setText(mBean.getTitle());
        mViewHolder.mNumber.setText(mBean.getUsers() + "人参与");
        if (mBean.getIsJoin().equals("1")) {
            mViewHolder.mJoin.setSelected(true);
        } else {
            mViewHolder.mJoin.setSelected(false);
        }
        mViewHolder.mDeadTime.setText(mBean.getEnd_time());
        mViewHolder.mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HighCommunityUtils.GetInstantiation().isLogin(mContext)) {
                    if (mBean.getIsJoin().equals("0")) {
                        final PopupWindow mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(mContext, view, Gravity.CENTER);
                        HTTPHelper.JoinActivity(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                mWatingWindow.dismiss();
                            }

                            @Override
                            public void onSuccess(Object message) {
                                mBean.setIsJoin("1");
                                mWatingWindow.dismiss();
                                HighCommunityUtils.GetInstantiation().ShowToast("成功参加该活动", 0);
                                notifyDataSetChanged();
                            }

                            @Override
                            public Object onResolve(String result) {
                                return null;
                            }

                            @Override
                            public void setAsyncTask(AsyncTask asyncTask) {

                            }

                            @Override
                            public void cancleAsyncTask() {
                                mWatingWindow.dismiss();
                            }

                            @Override
                            public void shouldLogin(boolean isShouldLogin) {

                            }

                            @Override
                            public void shouldLoginAgain(boolean isShouldLogin, String msg) {
                                if (isShouldLogin){
                                    HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                                    HighCommunityApplication.toLoginAgain(mContext);
                                }
                            }
                        }, HighCommunityApplication.mUserInfo.getId() + "", mBean.getId());
                    }
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ActCreate = new Intent(mContext, GeneratedClassUtils.get(ActiveAct.class));
                ActCreate.putExtra(ActiveAct.ACTIVITYTAG, Constacts.ACTIVITY_DETAILS);
                ActCreate.putExtra(ActiveAct.INTENTTAG, mBean.getId());
                mContext.startActivity(ActCreate);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView mImage;
        TextView mTitle;
        TextView mJoin;
        TextView mNumber;
        TextView mName;
        TextView mDeadTime;

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ActiveBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public void RefreshData(Object mObject) {
        super.RefreshData(mObject);
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<ActiveBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

}
