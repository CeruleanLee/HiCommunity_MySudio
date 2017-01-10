/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.SaveBitmap;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.GroupDataAct;
import cn.hi028.android.highcommunity.bean.GroupBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.PhotoUtils;

/**
 * @功能:创建群组<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/22<br>
 */
@EFragment(resName = "frag_creategroup")
public class CreateGroupFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "CreateGroupFrag";

    @ViewById(R.id.civ_creategroup_avatar)
    CircleImageView mAvatar;
    @ViewById(R.id.civ_creategroup_name)
    EditText mName;
    @ViewById(R.id.tv_creategrpu_intro)
    EditText mIntro;
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;

    int requesetPhoto = 0x000001, requestFile = 0x000002,
            requestCropImage = 0x000003;
    PopupWindow mPhotoPopupWindow = null, mWaitingWindow;
    Uri mPhotoUri = null;
    String imageUri = "";

    @AfterViews
    void initView() {
        mTitle.setText("创建群组");
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhotoPopupWindow == null) {
                    mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                            .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                }
                mPhotoPopupWindow.showAtLocation(mAvatar, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
            }
        });
    }

    @Click(R.id.civ_creategroup_submit)
    void submit() {
        String mingzi = mName.getText().toString().trim();
        String jieshao = mIntro.getText().toString().trim();
        if (TextUtils.isEmpty(mingzi)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入群组名字", 0);
            return;
        }
        if (TextUtils.isEmpty(jieshao)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入群介绍", 0);
            return;
        }
        if (TextUtils.isEmpty(imageUri)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择群头像", 0);
            return;
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mName, Gravity.CENTER);
        HTTPHelper.CreateGroup(mIbpi, HighCommunityApplication.mUserInfo.getId(), mingzi, jieshao, imageUri);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (message == null)
                return;
            GroupBean mBean = new GroupBean();
            mBean.setIsin("0");
            mBean.setId(Integer.parseInt(message.toString()));
            mBean.setIntro(mIntro.getText().toString().trim());
            mBean.setName(mName.getText().toString().trim());
            Intent mBack = new Intent();
            mBack.putExtra(GroupDataAct.ACTIVITYTAG, mBean);
            getActivity().setResult(000, mBack);
            getActivity().finish();
        }

        @Override
        public Object onResolve(String result) {
            try {
                JSONObject mJson = new JSONObject(result);
                return mJson.get("gid");
            } catch (JSONException e) {
                return null;
            }
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
            startActivityForResult(imageCaptureIntent, 1);
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
        }
    };

    public void handleSelectImageIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestFile);
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
//                mImageCaptureUri = SaveBitmap.SaveBitmap(image);
//                        }
//                    }
//                }
                Uri uri = Uri.fromFile(new File(PhotoUtils.resolvePhotoFromIntent(getActivity(),
                        data)));
                if (uri != null)
                    RequestCropImage(uri);
                else
                    HighCommunityUtils.GetInstantiation().ShowToast("未获取到图片，建议重试", 0);
            }
        } else if (requestCode == requestCropImage) {
            // 裁剪返回
            imageUri = mPhotoUri.getPath();
            ImageLoaderUtil.disPlay("file://" + imageUri, mAvatar);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPhotoPopupWindow != null && mPhotoPopupWindow.isShowing()) {
                mPhotoPopupWindow.dismiss();
                return true;
            }
            if (mWaitingWindow != null && mWaitingWindow.isShowing()) {
                mWaitingWindow.dismiss();
                return true;
            }
        }
        return false;
    }

    @Click(R.id.img_back)
    void back() {
        getActivity().finish();
    }
}
