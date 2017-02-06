/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.adapter.HuiChipsAdapter;
import cn.hi028.android.highcommunity.bean.ChipsBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LoadingView;

/**
 * @功能：厨房众筹<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-15<br>
 */
@EFragment(resName = "frag_hui_chips")
public class HuiChipsFrag extends BaseFragment {
    GridView gv_chips;
    HuiChipsAdapter adapter;
    TextView mNoData;
    View mProgress;
    ViewGroup layoutContainer;
    LoadingView mLoadingView;
    private PopupWindow mWatingWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hui_chips, null);
        findView(view);
        initView();
        return view;
    }

    private void findView(View view) {
        gv_chips = (GridView) view.findViewById(R.id.gv_chips);
        layoutContainer = (ViewGroup) view.findViewById(R.id.frag_chips_container);
        mLoadingView = (LoadingView) view.findViewById(R.id.loadingView);
        mNoData = (TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
        mProgress = view.findViewById(R.id.ll_NoticeDetails_Progress);
        mLoadingView.setVisibility(View.GONE);
    }

    void initView() {
        adapter = new HuiChipsAdapter(this, getActivity());
        gv_chips.setAdapter(adapter);
        gv_chips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(HuiLifeSecondAct.class));
                mIntent.putExtra(HuiLifeSecondAct.ACTIVITYTAG, Constacts.HUILIFE_CHIPS_DETAIL);
                mIntent.putExtra(HuiLifeSecondAct.INTENTTAG, adapter.getData().get(position));
                startActivity(mIntent);
            }
        });
        initDatas();
    }

    private void initDatas() {
        if (getActivity().hasWindowFocus()) {
            mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), layoutContainer, Gravity.CENTER);

        }

        HTTPHelper.GetHuiChipsList(mIbpi);

    }
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            if (mWatingWindow != null) {
                mWatingWindow.dismiss();
            }
                mNoData.setText(message);
                mNoData.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            mNoData.setVisibility(View.GONE);
            if (null == message)
                return;
            List<ChipsBean> data = (List<ChipsBean>) message;
            adapter.setData(data);
            if (mWatingWindow != null) {
                mWatingWindow.dismiss();
            }
            layoutContainer.setVisibility(View.VISIBLE);

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiChipsList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
}
