/***************************************************************************

 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import cn.hi028.android.highcommunity.adapter.MyCarftsAdapter;
import cn.hi028.android.highcommunity.bean.CarftsBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：我的手艺<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/29<br>
 */
@EFragment(resName = "frag_service_carfts")
public class MyCarftsFrag extends BaseFragment {
	public static final String FRAGMENTTAG = "MyCarftsFrag";
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

	MyCarftsAdapter mAdapter;
	List<CarftsBean> mList;
	PopupWindow mWaitingWindow;

	@AfterViews
	void initView() {
		mProgress.setVisibility(View.VISIBLE);
		mSearch.setOnFocusChangeListener(mFocusListener);
		mSearch.addTextChangedListener(mWatcher);
		mListView.setEmptyView(mNoData);
		HTTPHelper.GetMyCarfsList(mIbpi);
		mAdapter = new MyCarftsAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setMode(PullToRefreshBase.Mode.DISABLED);
		mListView.setOnItemClickListener(mItemListener);
		mListView.getRefreshableView().setOnItemLongClickListener(
				new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						final CarftsBean mBean = mAdapter.getItem(i - 1);
						ECAlertDialog dialog = ECAlertDialog.buildAlert(
								getActivity(), "是否删除该手艺", "删除", "取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mWaitingWindow = HighCommunityUtils
												.GetInstantiation()
												.ShowWaittingPopupWindow(
														getActivity(),
														mListView,
														Gravity.CENTER);
										HTTPHelper.DeleteMyCarfsList(
												mDeleteIbpi, mBean.getId());
									}
								}, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						dialog.show();
						return true;
					}
				});
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

	BpiHttpHandler.IBpiHttpHandler mDeleteIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mWaitingWindow.dismiss();
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			mWaitingWindow.dismiss();
			HTTPHelper.GetMyCarfsList(mIbpi);
			HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
					0);
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
			mWaitingWindow.dismiss();
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
