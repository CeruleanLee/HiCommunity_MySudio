/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.tools.SaveBitmap;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.bean.CertifiBean;
import cn.hi028.android.highcommunity.bean.PersonalInfoBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.PhotoUtils;
import cn.hi028.android.highcommunity.view.ECListDialog;

/**
 * @功能：修改个人信息<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/28<br>
 */
@EFragment(resName = "frag_editpersonal")
public class EditPersonalFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "EditPersonalFrag";

    @ViewById(R.id.civ_EditPersonal_Avatar)
    CircleImageView mAvatar;
    @ViewById(R.id.tv_UserInfo_NickName)
    EditText mName;
    @ViewById(R.id.tv_EditPersonal_UserAge)
    EditText mAge;
    @ViewById(R.id.tv_EditPersonal_UserSex)
    TextView mSex;
    @ViewById(R.id.et_EditPersonal_UserIntro)
    EditText mIntro;
    @ViewById(R.id.tv_EditPersonal_AuthZiliao)
    TextView tvAuth;
    boolean isClicked = false;

    PopupWindow mPhotoWindow, mWaitingWindow;
    PersonalInfoBean mBean;
    Uri mPhotoUri, mUpdateUri;
    String imagePath, sex;
    ECListDialog mChoice;
    List<String> mSexChoice = new ArrayList<String>();
    int requesetPhoto = 0x000001, requestFile = 0x000002,
            requestCropImage = 0x000003;
    BpiHttpHandler.IBpiHttpHandler mIbpiRepairCeri = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            if (id == 1002) {
                Intent mAuth = new Intent(getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
                mAuth.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_SECOND_PERSONALAUTH);
                startActivity(mAuth);
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
                HighCommunityUtils.GetInstantiation().ShowToast("用户认证已经通过审核", 0);
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

    @AfterViews
    void initView() {
        mName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        mBean = (PersonalInfoBean) getActivity().getIntent().getSerializableExtra(MenuLeftSecondAct.INTENTTAG);
        if (mBean != null) {
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mAvatar);
            mName.setText(mBean.getNick());
            mAge.setText(mBean.getAge());
            mIntro.setText(mBean.getSign());
            sex = mBean.getSex();
            if (mBean.getSex().equals("0")) {
                mSex.setText("男");
            } else {
                mSex.setText("女");
            }
        }
        mSexChoice.add("男");
        mSexChoice.add("女");
        mChoice = new ECListDialog(getActivity(), mSexChoice);
        mChoice.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(Dialog d, int position) {
                if (position == 0) {
                    sex = "0";
                    mSex.setText("男");
                } else {
                    sex = "1";
                    mSex.setText("女");
                }
            }
        });
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhotoWindow == null) {
                    mPhotoWindow = HighCommunityUtils.GetInstantiation().ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                }
                mPhotoWindow.showAtLocation(mAge, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
            }
        });
        mSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChoice.show();
            }
        });
    }

    @Click(R.id.tv_EditPersonal_AuthZiliao)
    public void authClick() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowPopupWindow(getActivity(), tvAuth, Gravity.CENTER, "正在验证用户信息");
            HTTPHelper.GetRepairCertification(mIbpiRepairCeri, HighCommunityApplication.mUserInfo.getId() + "");
        }
    }

    @Click(R.id.tv_EditPersonal_submit)
    void submit() {
        if (isClicked) {
            return;
        }
        isClicked = true;
        String name = mName.getText().toString().trim();
        String age = mAge.getText().toString().trim();
        String intro = mIntro.getText().toString().trim();
        boolean flag = false;
        if (!TextUtils.isEmpty(sex) && !mBean.getSex().equals(sex)) {
            flag = true;
        } else if (!TextUtils.isEmpty(imagePath)) {
            flag = true;
        } else if (!mBean.getNick().equals(name)) {
            flag = true;
        } else if (!mBean.getAge().equals(age)) {
            flag = true;
        } else if (!mBean.getSign().equals(intro)) {
            flag = true;
        }
        if (flag) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mAge, Gravity.CENTER);
            HTTPHelper.EditPersonalInfo(mIbpi, mBean.getId(), name, age, sex, intro, imagePath);
        } else {
            isClicked = false;
        }
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
            HighCommunityUtils.GetInstantiation().ShowToast("修改成功", 0);
            getActivity().finish();
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
    };

    /**
     * 拍照
     **/
    View.OnClickListener mPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPhotoWindow != null)
                mPhotoWindow.dismiss();
            mPhotoUri = Uri.fromFile(new File(SaveBitmap.getTheNewUrl()));
            Intent imageCaptureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (SaveBitmap.isHaveSD) {
                imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,
                        requesetPhoto);
            }
            startActivityForResult(imageCaptureIntent, 1);
        }
    };
    /**
     * 从相册
     **/
    View.OnClickListener mFile = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mPhotoWindow != null)
                mPhotoWindow.dismiss();
            handleSelectImageIntent();
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
            if (mPhotoUri != null)
                RequestCropImage(mPhotoUri);
            else
                HighCommunityUtils.GetInstantiation().ShowToast("未获取到图片，建议重试", 0);
        } else if (requestCode == requestFile) {
            // 图库返回
            if (data != null) {
                // 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
//                Uri mImageCaptureUri = data.getData();
//                if (mImageCaptureUri != null) {
//                } else {
//                    // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
//                    Bundle extras = data.getExtras();
//                    if (extras != null) {
//                        Bitmap image = extras.getParcelable("data");
//                        if (image != null) {
//                            mImageCaptureUri = SaveBitmap.SaveBitmap(image);
//                        }
//                    }
//                }
                 mPhotoUri = Uri.fromFile(new File(PhotoUtils.resolvePhotoFromIntent(getActivity(),
                        data)));
                if (mPhotoUri != null)
                    RequestCropImage(mPhotoUri);
                else
                    HighCommunityUtils.GetInstantiation().ShowToast("未获取到图片，建议重试", 0);
            }
        } else if (requestCode == requestCropImage) {
            // 裁剪返回
            mUpdateUri = mPhotoUri;
            imagePath = mUpdateUri.getPath();
            ImageLoaderUtil.disPlay("file://" + imagePath, mAvatar);
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
            if (mPhotoWindow != null && mPhotoWindow.isShowing()) {
                mPhotoWindow.dismiss();
                return true;
            }
            if (mWaitingWindow != null && mWaitingWindow.isShowing()) {
                mPhotoWindow.dismiss();
                return true;
            }
        }
        return false;
    }
}
