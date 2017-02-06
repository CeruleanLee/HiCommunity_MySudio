/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.VallageCityFrag;
import cn.hi028.android.highcommunity.bean.VallageCityBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：小区城市选择适配器<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-30<br>
 */
public class VallageCityAdapter extends BaseAdapter implements SectionIndexer {
    VallageCityFrag frag;
    public List<VallageCityBean> getData() {
        return data;
    }
    public void setData(List<VallageCityBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return (data.get(position).getType() == 0 || data.get(position).getType() == 1) ? 0 : 1;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    List<VallageCityBean> data = new ArrayList<VallageCityBean>();

    public VallageCityAdapter(VallageCityFrag frag) {
        this.frag = frag;
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (getItemViewType(position) == 0) {
                convertView = LayoutInflater.from(frag.getActivity()).inflate(
                        R.layout.adapter_vallage_select, null);
                viewHolder.tv_vallage_address = (TextView) convertView.findViewById(R.id.tv_vallage_address);
                viewHolder.tv_vallage_name = (TextView) convertView
                        .findViewById(R.id.tv_vallage_name);
                viewHolder.tv_header_title = (TextView) convertView
                        .findViewById(R.id.tv_header_title);
                convertView.setTag(R.layout.adapter_vallage_select, viewHolder);
            } else {
                convertView = LayoutInflater.from(frag.getActivity()).inflate(
                        R.layout.adapter_vallage_city, null);
                viewHolder.tv_header_title = (TextView) convertView.findViewById(R.id.tv_header_title);
                viewHolder.tv_vallage_city = (TextView) convertView
                        .findViewById(R.id.tv_vallage_city);
                convertView.setTag(R.layout.adapter_vallage_city, viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag(getItemViewType(position) == 0 ? R.layout.adapter_vallage_select : R.layout.adapter_vallage_city);
        }
        if (getItemViewType(position) == 0) {//小区
            viewHolder.tv_vallage_name.setText(data.get(position).getVallage().getName());
            viewHolder.tv_vallage_address.setText(data.get(position).getVallage().getAddress());
        } else {//城市
            viewHolder.tv_vallage_city.setText(data.get(position).getCity().getName());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemViewType(position) == 0) {//小区
                    if (HighCommunityUtils.isLogin()) {
                        final PopupWindow waitPop = HighCommunityUtils.GetInstantiation().ShowPopupWindow(frag.getActivity(), v, Gravity.CENTER, "小区选择中...");
                        HTTPHelper.IsSelectVillage(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                waitPop.dismiss();
                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                            }

                            @Override
                            public void onSuccess(Object message) {
                                waitPop.dismiss();
                                frag.mBackHandledInterface.onResultActivity(data.get(position).getVallage().getId());
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
                                waitPop.dismiss();
                            }

                            @Override
                            public void shouldLogin(boolean isShouldLogin) {

                            }

                            @Override
                            public void shouldLoginAgain(boolean isShouldLogin, String msg) {
                                if (isShouldLogin){
                                    HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                                    HighCommunityApplication.toLoginAgain(frag.getActivity());
                                }
                            }
                        }, HighCommunityApplication.mUserInfo.getId() + "", data.get(position).getVallage().getId());
                    } else {
                        frag.mBackHandledInterface.onResultActivity(data.get(position).getVallage().getId());
                    }
                } else {//城市
                    frag.toCountySelect(data.get(position).getCity());
                }
            }
        });
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tv_header_title.setVisibility(View.VISIBLE);
            viewHolder.tv_header_title.setText(data.get(position).getSortLetters());
        } else {
            viewHolder.tv_header_title.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_header_title, tv_vallage_city, tv_vallage_address, tv_vallage_name;
        ;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = data.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return data.get(position).getSortLetters().charAt(0);
    }
}
