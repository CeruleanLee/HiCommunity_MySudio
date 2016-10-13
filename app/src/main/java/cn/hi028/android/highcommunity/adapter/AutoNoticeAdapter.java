/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NoticeListBean;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅公告adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoNoticeAdapter extends BaseFragmentAdapter {

    public AddressListFrag mFrag;
    List<Auto_NoticeListBean.NoticeListDataEntity> mList = new ArrayList<Auto_NoticeListBean.NoticeListDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AutoNoticeAdapter(List<Auto_NoticeListBean.NoticeListDataEntity> list,Context context) {
        super();
        this.mList = list;
        if(this.mList == null){
            this.mList = new ArrayList<Auto_NoticeListBean.NoticeListDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_NoticeListBean.NoticeListDataEntity getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_autonotice, null);
            mViewHolder.mTitle = (TextView) convertView
                    .findViewById(R.id.item_autonotice_title);
            mViewHolder.mTime = (TextView) convertView
                    .findViewById(R.id.item_autonotice_time);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_NoticeListBean.NoticeListDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
//        TimeUtil.getDayAllTime(Long.parseLong(mBean.getCreate_time()))
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getCreate_time())));
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());
        return convertView;
    }


    class ViewHolder {
        TextView mTitle;
        TextView mTime;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_NoticeListBean.NoticeListDataEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }


}
