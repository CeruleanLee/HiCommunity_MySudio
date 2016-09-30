/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.bean.CarftsDetailBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：手艺人详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/30<br>
 */
@EFragment(resName = "frag_carftsdetails")
public class ServiceCaftsDetailFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "ServiceCaftsDetailFrag";

	@ViewById(R.id.progress_serviceCarftsDetails)
	View mProgress;
	@ViewById(R.id.tv_serviceCarftsDetails_Nodata)
	TextView mNoData;
	@ViewById(R.id.civ_servicecarftsDetail_image)
	CircleImageView mAvatar;
	@ViewById(R.id.tv_ServiceCarftsDetail_CarftsMan)
	TextView mCarftsMan;
	@ViewById(R.id.tv_ServiceCarftsDetail_ServiceName)
	TextView mServiceName;
	@ViewById(R.id.tv_ServiceCarftsDetail_ServiceIntro)
	TextView mIntro;
	@ViewById(R.id.tv_ServiceCarftsDetail_ServiceIntro_address)
	TextView address;
	@ViewById(R.id.tv_ServiceCarftsDetail_ServicePrice)
	TextView mPrice;
	@ViewById(R.id.tv_ServiceCarftsDetail_ServiceContact)
	TextView mContact;

	CarftsDetailBean mBean = new CarftsDetailBean();

	@AfterViews
	void initView() {
		mProgress.setVisibility(View.VISIBLE);
		String id = getActivity().getIntent().getStringExtra(
				ServiceSecondAct.INTENTTAG);
		if (TextUtils.isEmpty(id)) {
			mNoData.setVisibility(View.VISIBLE);
		} else {
			mNoData.setVisibility(View.GONE);
			HTTPHelper.GetCarftDetail(mIbpi, id);
		}

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
			mBean = (CarftsDetailBean) message;
			ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(),
					mAvatar);
			mCarftsMan.setText(mBean.getName());
			mServiceName.setText(mBean.getTitle());
			mIntro.setText(mBean.getContent());
			mPrice.setText(mBean.getPrice() + "元/次");
			mContact.setText(mBean.getName() + "  (" + mBean.getTel() + ")");
			address.setText(mBean.getAddress());
		}

		@Override
		public Object onResolve(String result) {
			HighCommunityUtils.LogOut(result);
			return HTTPHelper.ResolveCarftsDetails(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {
			mProgress.setVisibility(View.GONE);
		}
	};

	@Click(R.id.tv_ServiceCarftsDetail_ServiceCall)
	void call() {
		if (!TextUtils.isEmpty(mBean.getTel())) {
			HighCommunityUtils.GetInstantiation().callDialogPhone(
					getActivity(), mBean.getTel());
		} else {
			HighCommunityUtils.GetInstantiation().ShowToast("联系方式不正确", 0);
		}
	}
}
