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
import java.util.Collections;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.VallageSelctFrag;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.CharacterParser;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.pinyinUtils.PinyinValComparator;

/**
 *@功能：小区选择<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2015-12-30<br>
 */
public class VallageSelectAdapter extends BaseAdapter  implements SectionIndexer {
    VallageSelctFrag frag;

    public List<VallageBean> getData() {
        return data;
    }

    public void setData(List<VallageBean> data) {
        this.data = data;
        for (VallageBean vallageBean:data){
// 汉字转换成拼音
        String username = vallageBean.getName();
        // 若没有username
        if (username != null) {
            String pinyin = CharacterParser.getInstance().getSelling(username);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                vallageBean.setSortLetters(sortString.toUpperCase());
            } else {
                vallageBean.setSortLetters("#");
            }
        } else {
            vallageBean.setSortLetters("#");
        }
        }
        // 根据a-z进行排序
        Collections.sort(data, new PinyinValComparator());
        notifyDataSetChanged();
    }

    List<VallageBean> data=new ArrayList<VallageBean>();
    public VallageSelectAdapter(VallageSelctFrag frag){
        this.frag=frag;
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
            convertView = LayoutInflater.from(frag.getActivity()).inflate(
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
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tv_header_title.setVisibility(View.VISIBLE);
            viewHolder.tv_header_title.setText(data.get(position).getSortLetters());
        } else {
            viewHolder.tv_header_title.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final PopupWindow waitPop= HighCommunityUtils.GetInstantiation().ShowPopupWindow(frag.getActivity(),v, Gravity.CENTER,"小区选择中...");
                    if (HighCommunityUtils.isLogin()) {
                        HTTPHelper.IsSelectVillage(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                waitPop.dismiss();
                            }

                            @Override
                            public void onSuccess(Object message) {
                                waitPop.dismiss();
                                frag.mBackHandledInterface.onResultActivity(data.get(position).getId());
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
                        }, HighCommunityApplication.mUserInfo.getId() + "", data.get(position).getId());
                    }else{
                        frag.mBackHandledInterface.onResultActivity(data.get(position).getId());
                    }
            }
        });
        return convertView;
    }
class ViewHolder{
    TextView tv_vallage_address, tv_vallage_name,tv_header_title;
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
            if (firstChar == sectionIndex){
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
