/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MyIntegralCore;
import cn.hi028.android.highcommunity.adapter.WalletScoreAdapter;
import cn.hi028.android.highcommunity.bean.ScoreBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：钱包积分页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/29<br>
 */
@EFragment(resName = "frag_walletscore")
public class WalletScoreFrag extends BaseFragment implements OnClickListener {
	public static final String FRAGMENTTAG = "WalletScoreFrag";
	@ViewById(R.id.tv_walletscore_rules)
	TextView mRules;
	@ViewById(R.id.tv_walletscore_score)
	TextView mScore;
	@ViewById(R.id.tv_walletscore_money)
	TextView mMoney;
	@ViewById(R.id.tv_walletscore_inputNumber)
	EditText mNumber;
	@ViewById(R.id.tv_walletscore_exChange)
	TextView mSubmit;
	@ViewById(R.id.tv_walletscore_recode)
	PullToRefreshListView mRecodeList;
	@ViewById(R.id.progress_walletScore)
	View mProgress;
	@ViewById(R.id.tv_walletScore_Nodata)
	TextView mNoData;

	ScoreBean mBean;
	WalletScoreAdapter mAdapter;
	PopupWindow mWaitingWindow;

	@AfterViews
	void initView() {

		mAdapter = new WalletScoreAdapter(getActivity());
		mRecodeList.setMode(PullToRefreshBase.Mode.DISABLED);
		mRecodeList.setAdapter(mAdapter);
		mRecodeList.setEmptyView(mNoData);
		mProgress.setVisibility(View.VISIBLE);
		HTTPHelper.getWalletScore(mIbpi,
				HighCommunityApplication.mUserInfo.getId() + "", "0");
	}

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mProgress.setVisibility(View.GONE);
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			mProgress.setVisibility(View.GONE);
			if (null == message)
				return;
			mBean = (ScoreBean) message;
			mScore.setText("我的积分:" + mBean.getScores());
			mMoney.setText("我的零钱:" + mBean.getZero_money());
			mAdapter.AddNewData(mBean.getRecord());
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveScore(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {

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

	@Click(R.id.tv_walletscore_rules)
	void rules() {
		getActivity().startActivity(
				new Intent(getActivity(), MyIntegralCore.class));
	}

	@Click(R.id.tv_walletscore_exChange)
	void submit() {
		String score = mNumber.getText().toString().trim();
		if (TextUtils.isEmpty(score)) {
			HighCommunityUtils.GetInstantiation().ShowToast("请输入积分数量", 0);
			return;
		} else {
			int number = Integer.parseInt(score);
			if (number % 100 != 0) {
				HighCommunityUtils.GetInstantiation().ShowToast("请输入整百数值积分", 0);
				return;
			}
		}
		mWaitingWindow = HighCommunityUtils.GetInstantiation()
				.ShowWaittingPopupWindow(getActivity(), mMoney, Gravity.CENTER);
		HTTPHelper.ConvertScore(mScoreIbpi,
				HighCommunityApplication.mUserInfo.getId() + "", score);
	}

	BpiHttpHandler.IBpiHttpHandler mScoreIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mWaitingWindow.dismiss();
			mNumber.setText("");
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			mNumber.setText("");
			HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
					0);
			mWaitingWindow.dismiss();
			HTTPHelper.getWalletScore(mIbpi,
					HighCommunityApplication.mUserInfo.getId() + "", "0");
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

	@Override
	public void onClick(DialogInterface dialog, int which) {
	}
}
