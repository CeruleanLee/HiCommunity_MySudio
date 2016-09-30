/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.activity.ServiceAct;
import cn.hi028.android.highcommunity.adapter.HuiChipsAdapter;
import cn.hi028.android.highcommunity.bean.ChipsBean;
import cn.hi028.android.highcommunity.bean.HSuppGdDefBean;
import cn.hi028.android.highcommunity.bean.VallageCityBean;
import cn.hi028.android.highcommunity.bean.VallageSelectBean;
import cn.hi028.android.highcommunity.utils.CharacterParser;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.pinyinUtils.PinyinCityComparator;
import cn.hi028.android.highcommunity.view.LoadingView;
import cn.hi028.android.highcommunity.view.LoadingView.OnLoadingViewListener;

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
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_hui_chips, null);
		
		findView(view);
		initView();
		return view;
	}
    
    private void findView(View view) {
    	gv_chips=(GridView) view.findViewById(R.id.gv_chips);
    	layoutContainer=(ViewGroup) view.findViewById(R.id.frag_chips_container);
    	mLoadingView=(LoadingView) view.findViewById(R.id.loadingView);
    	mNoData=(TextView) view.findViewById(R.id.tv_NoticeDetails_noData);
    	mProgress=view.findViewById(R.id.ll_NoticeDetails_Progress);
    
    }

	void initView() {
        adapter = new HuiChipsAdapter(this,getActivity());
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
        mLoadingView.setOnLoadingViewListener(onLoadingViewListener);
initDatas();
    }

    private void initDatas() {
    	mLoadingView.startLoading();
    	HTTPHelper.GetHuiChipsList(mIbpi);
		
	}

	OnLoadingViewListener onLoadingViewListener = new OnLoadingViewListener() {

		@Override
		public void onTryAgainClick() {
//			if(!isNoNetwork)
//				HTTPHelper.GetThirdService(mIbpi);
//			Toast.makeText(getActivity(), "------------OnLoadingViewListener", 0).show();
		}
	};
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mLoadingView.loadFailed();
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            List<ChipsBean> data = (List<ChipsBean>) message;
            adapter.setData(data);
            mLoadingView.loadSuccess();
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
    };
}
