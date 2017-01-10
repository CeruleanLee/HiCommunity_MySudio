/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.adapter.CarftsAdapter;
import cn.hi028.android.highcommunity.bean.CarftsBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：手艺人列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/29<br>
 */
@EFragment(resName = "frag_service_carfts")
public class ServiceCarftsFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "ServiceCarftsFrag";

	@ViewById(R.id.progress_serviceCarfts)
	View mProgress;
	@ViewById(R.id.tv_serviceCarfts_Nodata)
	TextView mNoData;
	@ViewById(R.id.et_carftssearch_content)
	EditText mSearch;
	@ViewById(R.id.iv_carftsearch_tag)
	ImageView mTag;
	@ViewById(R.id.ptrlv_serviceCarfts_listView)
	PullToRefreshListView mListView;

	CarftsAdapter mAdapter;
	List<CarftsBean> mList;

	@AfterViews
	void initView() {
		mProgress.setVisibility(View.VISIBLE);
		mSearch.setOnFocusChangeListener(mFocusListener);
		mSearch.addTextChangedListener(mWatcher);
		mListView.setEmptyView(mNoData);
		HTTPHelper.GetCarfsList(mIbpi);
		mAdapter = new CarftsAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setMode(PullToRefreshBase.Mode.DISABLED);
		mListView.setOnItemClickListener(mItemListener);
	}

	View.OnFocusChangeListener mFocusListener = new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View view, boolean b) {
			if (b) {
				mTag.setVisibility(View.GONE);
			} else {
				mTag.setVisibility(View.VISIBLE);
			}
		}
	};

	AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i,
				long l) {
			Intent mIntent = new Intent(getActivity(),
					GeneratedClassUtils.get(ServiceSecondAct.class));
			mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG,
					Constacts.SERVICECARFTS_DETAILS);
			mIntent.putExtra(ServiceSecondAct.INTENTTAG, mAdapter
					.getItem(i - 1).getId());
			startActivity(mIntent);
		}
	};

	TextWatcher mWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1,
				int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1,
				int i2) {

		}

		@Override
		public void afterTextChanged(Editable editable) {

		}
	};

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mProgress.setVisibility(View.GONE);
		}

		@Override
		public void onSuccess(Object message) {
			mProgress.setVisibility(View.GONE);
			if (null == message)
				return;
			mList = (List<CarftsBean>) message;
			mAdapter.AddNewData(mList);
		}

		@Override
		public Object onResolve(String result) {
			HighCommunityUtils.LogOut(result);
			return HTTPHelper.ResolveCarftsList(result);
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
