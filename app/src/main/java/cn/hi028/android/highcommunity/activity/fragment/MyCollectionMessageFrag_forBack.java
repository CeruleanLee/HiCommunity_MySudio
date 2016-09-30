/***************************************************************************

 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.CommunityListAdapter;
import cn.hi028.android.highcommunity.adapter.MyCommunityListAdapter;
import cn.hi028.android.highcommunity.bean.CommunityBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：我收藏的话题<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-2-16<br>
 */
public class MyCollectionMessageFrag_forBack extends Fragment {

	public static final String FRAGMENTTAG = "MyCollectionMessageFrag";
	private View mFragmeView;
	private int mCount = -1;
	MyCommunityListAdapter mAdapter;
	private PullToRefreshListView mListView;
	private ImageView mChange;
	private TextView mNodata;
	List<CommunityBean> mList = new ArrayList<CommunityBean>();
	CommunityBean mBean = null;
	String vid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mFragmeView == null) {
			initView();
		}
		ViewGroup parent = (ViewGroup) mFragmeView.getParent();
		if (parent != null)
			parent.removeView(mFragmeView);
		return mFragmeView;
	}

	private void initView() {
		mFragmeView = LayoutInflater.from(getActivity()).inflate(
				R.layout.frag_community_list, null);
		vid = getActivity().getIntent().getStringExtra(FRAGMENTTAG);
		mListView = (PullToRefreshListView) mFragmeView
				.findViewById(R.id.ptrlv_community_listview);
		mChange = (ImageView) mFragmeView
				.findViewById(R.id.iv_community_change);
		mNodata = (TextView) mFragmeView.findViewById(R.id.tv_community_Nodata);
		mAdapter = new MyCommunityListAdapter((MenuLeftAct) getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setEmptyView(mNodata);
		mListView.setMode(PullToRefreshBase.Mode.BOTH);
		mChange.setVisibility(View.GONE);
		mListView.setMode(PullToRefreshBase.Mode.DISABLED);
		mListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
					}
				});
		mChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (HighCommunityUtils.isLogin()) {
					VallageAct.toStartAct(getActivity(), 1, false);
				} else {
					VallageAct.toStartAct(getActivity(), 0, false);
				}
			}
		});
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				mBean = mAdapter.getItem(i - 1);
				Intent mCommunity = new Intent(getActivity(),
						GeneratedClassUtils.get(CommunityDetailAct.class));
				mCommunity.putExtra(CommunityDetailAct.ACTIVITYTAG, "Details");
				mCommunity.putExtra(CommunityDetailAct.INTENTTAG,
						mBean.getMid());
				startActivityForResult(mCommunity, 1);
			}
		});
	}

	@Override
	public void onResume() {
		mCount = -1;
		HTTPHelper.GetMyCollectMessage(mIbpi,
				HighCommunityApplication.mUserInfo.getId());
		super.onResume();
	}

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mListView.onRefreshComplete();
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			if (null == message)
				return;
			mList = (List<CommunityBean>) message;
			if (mCount == 0) {
				mAdapter.AddNewData(mList);
			} else if (mCount == 1) {
				mAdapter.RefreshData(mList);
			} else if (mCount == -1) {
				mAdapter.SetData(mList);
			}
			mListView.onRefreshComplete();
		}

		@Override
		public Object onResolve(String result) {
			Log.e("renk", result);
			return HTTPHelper.ResolveMyCollectMessage(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {
			mListView.onRefreshComplete();
		}
	};

	public boolean onKeyDown() {
		if (mAdapter != null)
			return mAdapter.onBackPress();
		return false;
	}

}
