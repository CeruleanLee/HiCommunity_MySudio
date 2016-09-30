package cn.hi028.android.highcommunity.adapter;///***************************************************************************
// * Copyright (c) by raythinks.com, Inc. All Rights Reserved
// **************************************************************************/
//
//package cn.hi028.android.highcommunity.adapter;
//
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.don.tools.BpiUniveralImage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.hi028.android.highcommunity.R;
//import cn.hi028.android.highcommunity.activity.LabelAct;
//import cn.hi028.android.highcommunity.activity.fragment.ActivityCreateFrag;
//
///**
// * @功能：<br>
// * @作者： 李凌云<br>
// * @版本：1.0<br>
// * @时间：2016/1/19<br>
// */
//public class CreateActAdapter extends BaseFragmentAdapter {
//
//    public ActivityCreateFrag mContext;
//    private List<String> mList = new ArrayList<String>();
//
//    public CreateActAdapter(ActivityCreateFrag mContext) {
//        this.mContext = mContext;
//        this.mList.add("drawable://" + R.mipmap.img_upload_addpic);
//    }
//
//    @Override
//    public int getCount() {
//        if (mList.size() > 6) {
//            return 6;
//        } else {
//            return mList.size();
//        }
//    }
//
//    @Override
//    public String getItem(int position) {
//        return mList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        ViewHolder mViewHolder;
//        if (convertView == null) {
//            mViewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(mContext.getActivity()).inflate(R.layout.adapter_post_message, null);
//            mViewHolder.mImage = (ImageView) convertView
//                    .findViewById(R.id.iv_PostMessage_Image);
//            convertView.setTag(mViewHolder);
//        } else {
//            mViewHolder = (ViewHolder) convertView.getTag();
//        }
//        return convertView;
//    }
//
//    private class ViewHolder {
//        ImageView mImage;
//    }
//
//    @Override
//    public void AddNewData(Object mObject) {
//        Uri temp = null;
//        if (mObject instanceof Uri) {
//            temp = (Uri) mObject;
//            mList.add(0, "file://" + temp.getPath());
//        }
//        notifyDataSetChanged();
//        mContext.setHeight();
//        super.AddNewData(mObject);
//    }
//
//    @Override
//    public void RefreshData(Object mObject) {
//        super.RefreshData(mObject);
//    }
//}
