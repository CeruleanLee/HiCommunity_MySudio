/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import java.net.URISyntaxException;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.bean.CertifiBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：缴费界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/28<br>
 */
@EFragment(resName = "frag_service_payment")
public class ServicePaymentFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "ServicePaymentFrag";

	private PopupWindow mWaitingWindow;
	@ViewById(R.id.img_seri_repair_order)
	ImageView img_seri_repair_order;

	@ViewById(R.id.tv_payment_service_wuguan)
	TextView wuguan;
	@ViewById(R.id.tv_payment_service_water)
	TextView water;
	@ViewById(R.id.tv_payment_service_dian)
	TextView dian;
	@ViewById(R.id.tv_payment_service_qi)
	TextView qi;

	Intent mPayDetail;

	@AfterViews
	void initView() {
		LogUtil.d("~~~~~~~~~~  进入水电缴费");
		wuguan.setOnClickListener(mListener);
		water.setOnClickListener(mListener);
		dian.setOnClickListener(mListener);
		qi.setOnClickListener(mListener);
		mPayDetail = new Intent(getActivity(),
				GeneratedClassUtils.get(ServiceSecondAct.class));
		mPayDetail.putExtra(ServiceSecondAct.ACTIVITYTAG,
				Constacts.SERVICEPAYMENT_DETAILS);
	}

	BpiHttpHandler.IBpiHttpHandler mIbpiRepairCeri = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			if (id == 1002) {
				ShowDialog();
			} else {
				HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
			}

		}

		@Override
		public void onSuccess(Object message) {
			if (null == message)
				return;
			CertifiBean bean = (CertifiBean) message;
			if (bean.getStatus() != null && bean.getStatus().equals("1")) {
				startActivity(mPayDetail);
			} else {
				HighCommunityUtils.GetInstantiation().ShowToast("用户认证正在审核中", 0);
			}
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveCertification(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {
		}
	};

	private void ShowDialog() {
		ECAlertDialog.buildAlert(getActivity(), "你还未认证,是否去认证?", "去认证", "取消",
				new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(mPayDetail);
				Intent mAuth = new Intent(getActivity(),
						GeneratedClassUtils.get(ServiceSecondAct.class));
				mAuth.putExtra(ServiceSecondAct.ACTIVITYTAG,
						Constacts.SERVICE_SECOND_PERSONALAUTH);
				startActivity(mAuth);
			}
		}, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}

		}).show();
	}
	final String payUrl="alipays://platformapi/startapp?appId=20000193&userId={0}";// 用户的alipay-userid
	View.OnClickListener mListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// if (!HasAuth) {
			// ShowDialog();
			// return;
			// }
			switch (view.getId()) {
			case R.id.tv_payment_service_wuguan:
				if (HighCommunityUtils.isLogin(getActivity())) {
					HTTPHelper.GetRepairCertification(mIbpiRepairCeri,
							HighCommunityApplication.mUserInfo.getId() + "");
				}
				mPayDetail.putExtra(ServiceSecondAct.INTENTTAG, "4");
				break;
			case R.id.tv_payment_service_water:

				if (!HighCommunityApplication.isAliPayInStalled()) {
					LogUtil.d("------未安装支付宝");
					showUnInstallPayDialog();
				}else{
					final String Url = "alipays://platformapi/startapp?appId=20000193";
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
					//					HighCommunityUtils.GetInstantiation().ShowToast("功能待完善", 0);
					//					String url=UrlHandler.handlUrl(payUrl, alipay_userid);
					//					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					//					final String qqUrl = "alipays://platformapi/startapp?appId=20000193";//&userId= 用户的alipay-userid

					//					 BrowseActivity.toBrowseActivity(getActivity(), "公告", url + id);

					//				AlipaySDK.auth(getActivity(), arg1)
				}

				//				 mPayDetail.putExtra(ServiceSecondAct.INTENTTAG, "2");
				//				 if (HighCommunityUtils.isLogin(getActivity())) {
				//				 HTTPHelper.GetRepairCertification(mIbpiRepairCeri,
				//				 HighCommunityApplication.mUserInfo.getId() + "");
				//				 }
				break;
			case R.id.tv_payment_service_dian:
				if (!HighCommunityApplication.isAliPayInStalled()) {
					LogUtil.d("------未安装支付宝");
					showUnInstallPayDialog();
				}else{
					HighCommunityUtils.GetInstantiation().ShowToast("功能待完善", 0);
					final String Url = "alipays://platformapi/startapp?appId=20000193";
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
					//					String url=UrlHandler.handlUrl(payUrl, alipay_userid);
					//					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					//					final String qqUrl = "alipays://platformapi/startapp?appId=20000193";//&userId= 用户的alipay-userid

					//					 BrowseActivity.toBrowseActivity(getActivity(), "公告", url + id);


				}
				//				 if (HighCommunityUtils.isLogin(getActivity())) {
				//				 HTTPHelper.GetRepairCertification(mIbpiRepairCeri,
				//				 HighCommunityApplication.mUserInfo.getId() + "");
				//				 }
				//				mPayDetail.putExtra(ServiceSecondAct.INTENTTAG, "1");
				break;
			case R.id.tv_payment_service_qi:

				if (!HighCommunityApplication.isAliPayInStalled()) {
					LogUtil.d("------未安装支付宝");
					showUnInstallPayDialog();
				}else{
					final String Url = "alipays://platformapi/startapp?appId=20000193";
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
					//					HighCommunityUtils.GetInstantiation().ShowToast("功能待完善", 0);
					//					String url=UrlHandler.handlUrl(payUrl, alipay_userid);
					//					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					//					final String qqUrl = "alipays://platformapi/startapp?appId=20000193";//&userId= 用户的alipay-userid

					//					 BrowseActivity.toBrowseActivity(getActivity(), "公告", url + id);
				}
				//				if (url.startsWith("intent://")) {
				//					Intent intent;
				//					try {
				//					intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
				//					intent.addCategory("android.intent.category.BROWSABLE");
				//					intent.setComponent(null);
				//					intent.setSelector(null);
				//					getActivity().startActivity(intent);
				//					} catch (URISyntaxException e) {
				//					e.printStackTrace();
				//					}
				//					return true;
				//					} 


				//				 mPayDetail.putExtra(ServiceSecondAct.INTENTTAG, "3");
				//				 if (HighCommunityUtils.isLogin(getActivity())) {
				//				 HTTPHelper.GetRepairCertification(mIbpiRepairCeri,
				//				 HighCommunityApplication.mUserInfo.getId() + "");
				//				 }
				//				final String Url = "alipays://platformapi/startapp?appId=20000193";
				//				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));

				break;
			}
		}
	};


	/****
	 * 显示支付宝未安装弹窗
	 */
	public void showUnInstallPayDialog() {
		ECAlertDialog dialog2=ECAlertDialog.buildAlert(getActivity(), "未安装支付宝，请前往安装。", "确定", "取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				BrowseActivity.toBrowseActivity(getActivity(), null, "http://d.alipay.com");
//				http://d.alipay.com
			}
		}, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});
		dialog2.show();
	}
	//	Toast.makeText(MainActivity.this,"HighCommunityApplication.isAliPayInStalled()"+HighCommunityApplication.isAliPayInStalled(), 0).show();
	/**
	 * 预定报修
	 */
	@Click(R.id.img_seri_repair_order)
	public void onClickOrder() {
		if (HighCommunityUtils.isLogin(getActivity())) {
			mWaitingWindow = HighCommunityUtils.GetInstantiation()
					.ShowPopupWindow(getActivity(), img_seri_repair_order,
							Gravity.CENTER, "正在验证用户信息");
			HTTPHelper.GetRepairCertification(mIbpiRepairCeri1,
					HighCommunityApplication.mUserInfo.getId() + "");
		}
	}

	BpiHttpHandler.IBpiHttpHandler mIbpiRepairCeri1 = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mWaitingWindow.dismiss();
			if (id == 1002) {
				ECAlertDialog.buildAlert(getActivity(), "你还未认证,是否去认证?", "去认证",
						"取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						Intent mAuth = new Intent(getActivity(),
								GeneratedClassUtils
								.get(ServiceSecondAct.class));
						mAuth.putExtra(ServiceSecondAct.ACTIVITYTAG,
								Constacts.SERVICE_SECOND_PERSONALAUTH);
						startActivity(mAuth);
					}
				}, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {

					}

				}).show();
			} else {
				HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
			}

		}

		@Override
		public void onSuccess(Object message) {
			mWaitingWindow.dismiss();
			if (null == message)
				return;
			CertifiBean bean = (CertifiBean) message;
			if (bean.getStatus() != null && bean.getStatus().equals("1")) {
				Intent mIntent = new Intent(getActivity(),
						GeneratedClassUtils.get(ServiceSecondAct.class));
				mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG,
						Constacts.SERVICE_REPAIR_DETAIL_ORDER);
				mIntent.putExtra(ServiceSecondAct.INTENTTAG, bean);
				startActivity(mIntent);
			} else {
				HighCommunityUtils.GetInstantiation().ShowToast("用户认证正在审核中", 0);
			}
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveCertification(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {
			mWaitingWindow.dismiss();
		}
	};

	/**
	 * 紧急报修
	 */
	@Click(R.id.img_seri_repairr_jj)
	public void onClickJJ() {
		Intent mIntent = new Intent(getActivity(),
				GeneratedClassUtils.get(ServiceSecondAct.class));
		mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG,
				Constacts.SERVICE_REPAIR_DETAIL_JJ);
		startActivity(mIntent);
	}

	/**
	 * 报修记录
	 */
	@Click(R.id.img_seri_repair_record)
	public void onClickRecord() {
		if (HighCommunityUtils.isLogin(getActivity())) {
			Intent mIntent = new Intent(getActivity(),
					GeneratedClassUtils.get(ServiceSecondAct.class));
			mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG,
					Constacts.SERVICE_REPAIR_DETAIL_RECORD);
			startActivity(mIntent);
		}
	}

}
