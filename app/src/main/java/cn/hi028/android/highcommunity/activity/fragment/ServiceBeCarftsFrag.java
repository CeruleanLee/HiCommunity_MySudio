/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;
import com.don.tools.SaveBitmap;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.io.File;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ShouYiRenText;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.RegexValidateUtil;

/**
 * @功能：成为手艺人页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/30<br>
 */
@EFragment(resName = "frag_become_carfts")
public class ServiceBeCarftsFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "ServiceBeCarftsFrag";
	@ViewById(R.id.iv_becarfts_name)
	EditText mName;
	@ViewById(R.id.iv_becarfts_location)
	EditText mLocation;
	@ViewById(R.id.iv_becarfts_constact)
	EditText mPhone;
	@ViewById(R.id.iv_becomeworker_service_title)
	EditText mTitle;
	@ViewById(R.id.iv_becomeworker_service_intro)
	EditText mIntro;
	@ViewById(R.id.iv_becomeworker_service_price)
	EditText mPrice;
	@ViewById(R.id.iv_becarfts_avatar)
	ImageView mAvatar;
	@ViewById(R.id.iv_becarfts_idcard_obverse)
	ImageView mObverse;
	@ViewById(R.id.iv_becarfts_idcard_back)
	ImageView mBack;
	@ViewById(R.id.iv_becarfts_idcard_withHands)
	ImageView mPicture;
	@ViewById(R.id.iv_becarfts_ziliao_cover)
	ImageView mCover;
	@ViewById(R.id.iv_becomeworker_ziliao_inside)
	ImageView mInside;
	@ViewById(R.id.frag_cn_mb)
	CheckBox agree;
	@ViewById(R.id.tv_register_agree)
	TextView agreeTxt;
	boolean IsClicked = false;

	int requesetPhoto = 0x000001, requestFile = 0x000002,
			requestCropImage = 0x000003;
	PopupWindow mPhotoPopupWindow = null, mWaitingWindow;
	Uri mPhotoUri = null;
	int ClickId;
	String AvatarUri, ObverseUri, BackUri, PhotoUri, CoverUri, InsideUri;

	@AfterViews
	void initView() {
		mAvatar.setOnClickListener(mClickListener);
		mObverse.setOnClickListener(mClickListener);
		mBack.setOnClickListener(mClickListener);
		agreeTxt.setOnClickListener(mClickListener);
		mPicture.setOnClickListener(mClickListener);
		mCover.setOnClickListener(mClickListener);
		mInside.setOnClickListener(mClickListener);
		mPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i,
					int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1,
					int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				String temp = editable.toString();
				int posDot = temp.indexOf(".");
				if (posDot <= 0)
					return;
				if (temp.length() - posDot - 1 > 2) {
					editable.delete(posDot + 3, posDot + 4);
				}
			}
		});
	}

	View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.iv_becarfts_avatar:
				ClickId = R.id.iv_becarfts_avatar;
				break;
			case R.id.iv_becarfts_idcard_obverse:
				ClickId = R.id.iv_becarfts_idcard_obverse;
				break;
			case R.id.iv_becarfts_idcard_back:
				ClickId = R.id.iv_becarfts_idcard_back;
				break;
			case R.id.iv_becarfts_idcard_withHands:
				ClickId = R.id.iv_becarfts_idcard_withHands;
				break;
			case R.id.iv_becarfts_ziliao_cover:
				ClickId = R.id.iv_becarfts_ziliao_cover;
				break;
			case R.id.iv_becomeworker_ziliao_inside:
				ClickId = R.id.iv_becomeworker_ziliao_inside;
				break;
			case R.id.tv_register_agree:
			    BrowseActivity.toBrowseActivity(getActivity(), "用户协议", "http://028hi.cn/api/default/agreement.html");
//			    break;
//				Intent intent = new Intent(getActivity(), ShouYiRenText.class);
//				getActivity().startActivity(intent);
				return;
			}
			if (mPhotoPopupWindow == null) {
				mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
						.ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
			}
			mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
					HighCommunityApplication.SoftKeyHight);
		}
	};

	/**
	 * 拍照
	 **/
	View.OnClickListener mPhoto = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mPhotoPopupWindow != null)
				mPhotoPopupWindow.dismiss();
			mPhotoUri = Uri.fromFile(new File(SaveBitmap.getTheNewUrl()));
			Intent imageCaptureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			if (SaveBitmap.isHaveSD) {
				imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
				imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,
						requesetPhoto);
			}
			startActivityForResult(imageCaptureIntent, requesetPhoto);
		}
	};

	@Click(R.id.tv_becarfts_submit)
	void submit() {
		if (IsClicked) {
			return;
		}
		IsClicked = true;
		String name = mName.getText().toString().trim();
		String location = mLocation.getText().toString().trim();
		String phone = mPhone.getText().toString().trim();
		String title = mTitle.getText().toString().trim();
		String service = mIntro.getText().toString().trim();
		String price = mPrice.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			HighCommunityUtils.GetInstantiation().ShowToast("姓名不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(location)) {
			HighCommunityUtils.GetInstantiation().ShowToast("联系地址不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(phone)) {
			HighCommunityUtils.GetInstantiation().ShowToast("联系方式不能为空", 0);
			IsClicked = false;
			return;
		} else if (!RegexValidateUtil.checkMobileNumber(phone)) {
			HighCommunityUtils.GetInstantiation().ShowToast("手机号格式不正确", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(service)) {
			HighCommunityUtils.GetInstantiation().ShowToast("服务介绍不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(price)) {
			HighCommunityUtils.GetInstantiation().ShowToast("价格不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(title)) {
			HighCommunityUtils.GetInstantiation().ShowToast("服务项目不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(AvatarUri)) {
			HighCommunityUtils.GetInstantiation().ShowToast("头像不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(ObverseUri)) {
			HighCommunityUtils.GetInstantiation().ShowToast("身份证正面图片不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(BackUri)) {
			HighCommunityUtils.GetInstantiation().ShowToast("身份证背面图片不能为空", 0);
			IsClicked = false;
			return;
		} else if (TextUtils.isEmpty(PhotoUri)) {
			HighCommunityUtils.GetInstantiation().ShowToast("图片不能为空", 0);
			IsClicked = false;
			return;
		} else if (!agree.isChecked()) {
			HighCommunityUtils.GetInstantiation().ShowToast("请同意协议！", 0);
			IsClicked = false;
			return;
		}
		// else if (TextUtils.isEmpty(CoverUri)) {
		// HighCommunityUtils.GetInstantiation().ShowToast("资质封面不能为空", 0);
		// return;
		// } else if (TextUtils.isEmpty(InsideUri)) {
		// HighCommunityUtils.GetInstantiation().ShowToast("资质内页不能为空", 0);
		// return;
		// }
		mWaitingWindow = HighCommunityUtils
				.GetInstantiation()
				.ShowWaittingPopupWindow(getActivity(), mAvatar, Gravity.CENTER);
		HTTPHelper.ApplyForCarft(mIbpi, name, location, phone, title, service,
				price, AvatarUri, ObverseUri, BackUri, PhotoUri, CoverUri,
				InsideUri);
	}

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mWaitingWindow.dismiss();
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			mWaitingWindow.dismiss();
			if (message == null)
				return;
			getActivity().finish();
			HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
					0);
		}

		@Override
		public Object onResolve(String result) {
			return result;
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
	 * 从相册
	 **/
	View.OnClickListener mFile = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mPhotoPopupWindow != null)
				mPhotoPopupWindow.dismiss();
			handleSelectImageIntent();
			// Intent intent = new Intent();
			// intent.setType("image/*");
			// intent.setAction(Intent.ACTION_GET_CONTENT);
			// startActivityForResult(intent, requestFile);
		}
	};

	public void handleSelectImageIntent() {
		Intent i = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, requestFile);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == requesetPhoto) {
			// 拍照返回
			if (resultCode != getActivity().RESULT_OK) {
				return;
			}
			switch (ClickId) {
			case R.id.iv_becarfts_avatar:
				RequestCropImage(mPhotoUri);
				break;
			case R.id.iv_becarfts_idcard_obverse:
				ObverseUri = mPhotoUri.getPath();
				ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(),
						mObverse);
				break;
			case R.id.iv_becarfts_idcard_back:
				BackUri = mPhotoUri.getPath();
				ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(), mBack);
				break;
			case R.id.iv_becarfts_idcard_withHands:
				PhotoUri = mPhotoUri.getPath();
				ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(),
						mPicture);
				break;
			case R.id.iv_becarfts_ziliao_cover:
				CoverUri = mPhotoUri.getPath();
				ImageLoaderUtil
						.disPlay("file://" + mPhotoUri.getPath(), mCover);
				break;
			case R.id.iv_becomeworker_ziliao_inside:
				InsideUri = mPhotoUri.getPath();
				ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(),
						mInside);
				break;
			}
		} else if (requestCode == requestFile) {
			// 图库返回
			if (data != null) {
				// 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				if (mImageCaptureUri == null) {
					// 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap image = extras.getParcelable("data");
						if (image != null) {
							mImageCaptureUri = SaveBitmap.SaveBitmap(image);
						}
					}
				}
				switch (ClickId) {
				case R.id.iv_becarfts_avatar:
					RequestCropImage(mImageCaptureUri);
					break;
				case R.id.iv_becarfts_idcard_obverse:
					ObverseUri = CommonUtils.resolvePhotoFromIntent(
							getActivity(), data);
					// mImageCaptureUri.getPath();
					ImageLoaderUtil.disPlay("file://" + ObverseUri, mObverse);
					break;
				case R.id.iv_becarfts_idcard_back:
					BackUri = CommonUtils.resolvePhotoFromIntent(getActivity(),
							data);
					// mImageCaptureUri.getPath();
					ImageLoaderUtil.disPlay("file://" + BackUri, mBack);
					break;
				case R.id.iv_becarfts_idcard_withHands:
					PhotoUri = CommonUtils.resolvePhotoFromIntent(
							getActivity(), data);
					// mImageCaptureUri.getPath();
					ImageLoaderUtil.disPlay("file://" + PhotoUri, mPicture);
					break;
				case R.id.iv_becarfts_ziliao_cover:
					CoverUri = CommonUtils.resolvePhotoFromIntent(
							getActivity(), data);
					// mImageCaptureUri.getPath();
					ImageLoaderUtil.disPlay("file://" + CoverUri, mCover);
					break;
				case R.id.iv_becomeworker_ziliao_inside:
					InsideUri = CommonUtils.resolvePhotoFromIntent(
							getActivity(), data);
					// mImageCaptureUri.getPath();
					ImageLoaderUtil.disPlay("file://" + InsideUri, mInside);
					break;
				}
			}
		} else if (requestCode == requestCropImage) {
			// 裁剪返回
			AvatarUri = mPhotoUri.getPath();
			ImageLoaderUtil.disPlay("file://" + AvatarUri, mAvatar);
		}
	}

	/**
	 * 裁剪
	 **/
	private void RequestCropImage(Uri uri) {
		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("circleCrop", new String(""));
		mPhotoUri = Uri.fromFile(new File(SaveBitmap.getTheNewUrl()));
		intent.putExtra("output", mPhotoUri);
		startActivityForResult(intent, requestCropImage);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mPhotoPopupWindow != null && mPhotoPopupWindow.isShowing()) {
				mPhotoPopupWindow.dismiss();
				return true;
			} else if (mWaitingWindow != null && mWaitingWindow.isShowing()) {
				mWaitingWindow.dismiss();
				return true;
			}
		}
		return false;
	}

}
