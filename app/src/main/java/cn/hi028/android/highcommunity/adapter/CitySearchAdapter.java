/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.SearchActivity;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：城市搜素<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-02-03<br>
 */
public class CitySearchAdapter extends BaseAdapter {
    SearchActivity act;
    public CitySearchAdapter(SearchActivity act) {
        this.act = act;
    }
    public List<VallageBean> getData() {
        return data;
    }
    public void setData(List<VallageBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    List<VallageBean> data = new ArrayList<VallageBean>();
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
            convertView = LayoutInflater.from(act).inflate(
                    R.layout.adapter_vallage_select, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_vallage_address = (TextView) convertView.findViewById(R.id.tv_vallage_address);
            viewHolder.tv_vallage_name = (TextView) convertView
                    .findViewById(R.id.tv_vallage_name);
            viewHolder.tv_header_title = (TextView) convertView
                    .findViewById(R.id.tv_header_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_vallage_name.setText(data.get(position).getName());
        viewHolder.tv_vallage_address.setText(data.get(position).getAddress());
        // 根据position获取分类的首字母的Char ascii值
        viewHolder.tv_header_title.setVisibility(View.GONE);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((SearchActivity) act).QuxianId != null) {
                    Intent mInt = act.getIntent();
                    mInt.putExtra(Constacts.SEARCH_RESULT, data.get(position));
                    act.setResult(0x22, mInt);
                    act.finish();
                } else {
                    final PopupWindow waitPop = HighCommunityUtils.GetInstantiation().ShowPopupWindow(act, v, Gravity.CENTER, "小区选择中...");
                    if (HighCommunityUtils.isLogin()) {
                        HTTPHelper.IsSelectVillage(new BpiHttpHandler.IBpiHttpHandler
                                () {
                            @Override
                            public void onError(int id, String message) {
                                waitPop.dismiss();
                            }

                            @Override
                            public void onSuccess(Object message) {
                                waitPop.dismiss();
                                Intent mInt = act.getIntent();
                                mInt.putExtra(Constacts.SEARCH_RESULT, data.get(position).getId());
                                mInt.putExtra(Constacts.SEARCH_RESULT_Address, data.get(position).getName());
                                act.setResult(0x22, mInt);
                                act.finish();
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
                                    HighCommunityApplication.toLoginAgain(act);
                                }
                            }
                        }, HighCommunityApplication.mUserInfo.getId() + "", data.get(position).getId());
                    } else {
                        Intent mInt = act.getIntent();
                        mInt.putExtra(Constacts.SEARCH_RESULT, data.get(position).getId());
                        act.setResult(0x22, mInt);
                        act.finish();
                    }
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_vallage_address, tv_vallage_name, tv_header_title;
    }
}
