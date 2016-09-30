/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.don.tools.TimeFormat;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.CityListAdapter;
import cn.hi028.android.highcommunity.adapter.DistrictListAdapter;
import cn.hi028.android.highcommunity.adapter.VillageListAdapter;
import cn.hi028.android.highcommunity.bean.CitysBean;
import cn.hi028.android.highcommunity.bean.DistrictBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/26<br>
 */
public class ShowListUtils {

    // 当前日期0点
    public static long mNowZero = 0;

    public static ShowListUtils mListUtils = null;
    VillageListAdapter mVillageAdapter = null;

    /**
     * to show the city list
     *
     * @param context     上下文对象
     * @param mParentView 父控件
     * @param mback       点击回调
     * @param bean        数据
     * @return
     */
    public PopupWindow ShowCityList(Context context, View mParentView, final OnItemClickBack mback,
                                    List<CitysBean> bean) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.listivew, null, false);
        final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        ListView list = (ListView) view.findViewById(R.id.simple_list);
        final CityListAdapter mAdapter = new CityListAdapter(context, bean);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mback.onClick(mAdapter.getItem(i));
                mPhotoPopupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getX() < 0 || motionEvent.getX() > view.getWidth()
                        || motionEvent.getY() < 0 || motionEvent.getY() > view.getHeight()) {
                    mPhotoPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mPhotoPopupWindow.setOutsideTouchable(true);
        mPhotoPopupWindow.showAsDropDown(mParentView, 0, 0);
        return mPhotoPopupWindow;
    }

    /**
     * to show the district list
     *
     * @param context     上下文对象
     * @param mParentView 父控件
     * @param mback       点击回调
     * @param bean        数据
     * @return
     */
    public PopupWindow ShowDistrictList(Context context, View mParentView, final OnItemClickBack mback,
                                        List<DistrictBean> bean) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.listivew, null, false);
        final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        ListView list = (ListView) view.findViewById(R.id.simple_list);
        final DistrictListAdapter mAdapter = new DistrictListAdapter(context, bean);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mback.onClick(mAdapter.getItem(i));
                mPhotoPopupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getX() < 0 || motionEvent.getX() > view.getWidth()
                        || motionEvent.getY() < 0 || motionEvent.getY() > view.getHeight()) {
                    mPhotoPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mPhotoPopupWindow.setOutsideTouchable(true);
        mPhotoPopupWindow.showAsDropDown(mParentView, 0, 0);
        return mPhotoPopupWindow;
    }

    /**
     * to show the category of thread
     *
     * @param context     上下文对象
     * @param mParentView 父控件
     * @param mItemClick  点击回调
     * @param bean        数据
     * @return
     */
    public PopupWindow ShowVillageList(Context context, View mParentView, AdapterView.OnItemClickListener mItemClick,
                                       List<VallageBean> bean, final HighCommunityUtils.InputCallBack mBack) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.serchlistview, null, false);
        final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        EditText edit = (EditText) view.findViewById(R.id.searchview_searchText);
        ListView list = (ListView) view.findViewById(R.id.searchsimple_list);
        mVillageAdapter = new VillageListAdapter(context, bean);
        list.setAdapter(mVillageAdapter);
        list.setOnItemClickListener(mItemClick);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBack.onInput(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getX() < 0 || motionEvent.getX() > view.getWidth()
                        || motionEvent.getY() < 0 || motionEvent.getY() > view.getHeight()) {
                    mPhotoPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mPhotoPopupWindow.setOutsideTouchable(true);
        mPhotoPopupWindow.showAsDropDown(mParentView, 0, 0);
        return mPhotoPopupWindow;
    }

    public void RefreshVillage(List<VallageBean> mBean) {
        if (mVillageAdapter != null)
            mVillageAdapter.RefreshData(mBean);
    }

    public interface OnItemClickBack {
        public void onClick(Object mBack);
    }

    public static ShowListUtils GetInstantiation() {
        if (mListUtils == null) {
            mListUtils = new ShowListUtils();
            mNowZero = TimeFormat.TimedateFormat_DATEParse(TimeFormat
                    .getTheTime());
        }
        return mListUtils;
    }
}
