/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.SaveBitmap;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.CommunityFrag;
import cn.hi028.android.highcommunity.adapter.GridAdapter;
import cn.hi028.android.highcommunity.adapter.LabelGrideAdapter;
import cn.hi028.android.highcommunity.bean.LabelBean;
import cn.hi028.android.highcommunity.bean.UserCenterBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import photo.activity.AlbumActivity;
import photo.activity.GalleryActivity;
import photo.util.Bimp;
import photo.util.ImageItem;

/**
 * @功能：标签页面（发帖）<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/16<br>
 */
@EActivity(resName = "act_label")
public class LabelAct extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "LabelAct";
    public static final String INTENTTAG = "LabelActIntent";
    @ViewById(R.id.iv_label_gridview)
    PullToRefreshGridView mGridView;
    @ViewById(R.id.tv_label_title)
    TextView mTitle;
    @ViewById(R.id.tv_label_RightnMenu)
    TextView mPublish;
    @ViewById(R.id.tv_label_PostContent)
    EditText mContent;
    @ViewById(R.id.ptrgv_label_PostImage)
    PullToRefreshGridView mPostImage;
    @ViewById(R.id.tv_label_Postlocation)
    TextView mLocation;
    @ViewById(R.id.ll_labellayout_mainlayout)
    LinearLayout mMainLayout;
    @ViewById(R.id.rl_labellaout_contentlayout)
    RelativeLayout mContentLayout;

    LabelGrideAdapter mAdapter;
    GridAdapter mPostAdapter;
    LabelBean LastBean, CurrentBean;
    ECAlertDialog mDialog;
    boolean clicked = false;

    int requesetPhoto = 0x000001, requestFile = 0x000002,
            requestCropImage = 0x000003;
    PopupWindow mPhotoPopupWindow = null, mWaitingWindow;
    Uri mPhotoUri = null;
    List<String> mImages = new ArrayList<String>();
    String mLabelId = null;
    String mGid = "";
    boolean isDelStatus = false;
    LabelBean temp = null;
    int type = 0;

    @AfterViews
    void initView() {
        type = getIntent().getIntExtra(INTENTTAG, 0);
        mGid = getIntent().getStringExtra(ACTIVITYTAG);
        System.out.println("xxxddd:mGid:" + mGid);
        mAdapter = new LabelGrideAdapter(this);
        mPostAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        mGridView.setOnItemClickListener(mItemListener);
        mPostImage.setAdapter(mPostAdapter);
        mPostImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                  if (mPostAdapter.getItem(i).startsWith("drawable://")) {
                                                      if (mPhotoPopupWindow == null) {
                                                          mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                                                                  .ShowPhotoPopupWindow(LabelAct.this, mPhoto, mFile);
                                                      }
                                                      mPhotoPopupWindow.showAtLocation(mContent, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
                                                  } else {
                                                      Intent intent = new Intent(LabelAct.this,
                                                              GalleryActivity.class);
                                                      intent.putExtra("position", "1");
                                                      intent.putExtra("ID", i);
                                                      startActivity(intent);
                                                  }
                                              }
                                          }

        );
        HighCommunityUtils.InitLabelList(6);
        HTTPHelper.getUserCenter(mLocationIbpi, HighCommunityApplication.mUserInfo.getId() + "");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mGridView.getLeft() <= ev.getX() && ev.getX() <= mGridView.getRight()
                    && mGridView.getTop() < ev.getY() && ev.getY() < mGridView.getBottom()) {
            } else {
                if (isDelStatus) {
                    isDelStatus = false;
                    if (temp != null && temp.isDel() == true) {
                        temp.setDel(false);
                        mAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onResume() {
        mAdapter.notifyDataSetChanged();
        setHeight();
        super.onResume();
        
    }


    public void setHeight() {
        HighCommunityUtils.GetInstantiation().setGridViewHeightBasedOnChildren(mPostImage, mPostAdapter, 4);
    }

    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LabelBean mCurrent = mAdapter.getItem(i);
            if (mCurrent.getId() == "-1") {
                HighCommunityUtils.GetInstantiation().ShowInput(LabelAct.this, mContent, mBack, "请输入类型名称");
                return;
            } else if (mCurrent.getId() == "-2") {
                if (mDialog == null) {
                    mDialog = ECAlertDialog.buildAlert(LabelAct.this, "是否删除选中标签?", "确认", "取消"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(LabelAct.this, mContent, Gravity.CENTER);
                                    HTTPHelper.Dellabel(new BpiHttpHandler.IBpiHttpHandler() {
                                        @Override
                                        public void onError(int id, String message) {
                                        	
                                        }

                                        @Override
                                        public void onSuccess(Object message) {
                                            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
                                            mAdapter.remove(mLabelId);
                                            mAdapter.notifyDataSetChanged();
                                            mLabelId = "";
                                            mWaitingWindow.dismiss();
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
                                    }, mLabelId);
                                }
                            }, null);
                    mDialog.setTitle("提示");
                }
                if (mLabelId == null || mLabelId.equals("")) {
                    HighCommunityUtils.GetInstantiation().ShowToast("请选中需要删除的标签", 0);
                    return;
                } else if (1 <= Integer.parseInt(mLabelId) && Integer.parseInt(mLabelId) <= 8) {
                    HighCommunityUtils.GetInstantiation().ShowToast("不能删除系统自带标签", 0);
                    return;
                }
                mDialog.show();
                return;
            }
            if (LastBean == null) {
                LastBean = mAdapter.getItem(i);
                LastBean.setClicked(true);
                mLabelId = LastBean.getId();
            } else {
                CurrentBean = mAdapter.getItem(i);
                LastBean.setClicked(false);
                CurrentBean.setClicked(true);
                mLabelId = CurrentBean.getId();

                LastBean = CurrentBean;
                CurrentBean = null;
            }
            if (TextUtils.isEmpty(LastBean.getPic())) {
                mAdapter.CanDelete(true);
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    HighCommunityUtils.InputCallBack mBack = new HighCommunityUtils.InputCallBack() {
        @Override
        public void onInput(String input) {
            if (input.length() > 4) {
                HighCommunityUtils.GetInstantiation().ShowToast("名称长度不能超过4个字", 0);
            } else {
                HTTPHelper.Addlabel(mLabelIbpi, HighCommunityApplication.mUserInfo.getId() + "", input);
            }

        }
    };

    BpiHttpHandler.IBpiHttpHandler mLabelIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            LabelBean mLabel = (LabelBean) message;
            Constacts.mCustomLabel.add(0, mLabel);
            mAdapter.AddNewData(mLabel);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveLabel(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    BpiHttpHandler.IBpiHttpHandler mLocationIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            Constacts.mUserCenter = (UserCenterBean) message;
            mLocation.setText(Constacts.mUserCenter.getUserInfo().getVillage());
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveUserCenter(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    @Click(R.id.iv_label_back)
    void back() {
        this.finish();
    }

    @Click(R.id.tv_label_RightnMenu)
    void publish() {
        if (clicked) {
            return;
        }
        String content = mContent.getText().toString().trim();
        if (mImages != null && mImages.size() > 0) {
            mImages.clear();
        }
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            if (!Bimp.tempSelectBitmap.get(i).getImagePath().startsWith("drawable://"))
                mImages.add(Bimp.tempSelectBitmap.get(i).getImagePath());
        }
        if (TextUtils.isEmpty(mLabelId)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择分类", 0);
            clicked = false;
            return;
        } else if (TextUtils.isEmpty(content)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入内容", 0);
            clicked = false;
            return;
        } else if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
            HighCommunityUtils.GetInstantiation().ShowToast("请先登录,再发帖", 0);
            clicked = false;
            return;
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(this, mGridView, Gravity.CENTER);
        HTTPHelper.PostingMsg(mIbpi, mLabelId, content, HighCommunityApplication.mUserInfo.getId() + "",
                HighCommunityApplication.mUserInfo.getV_id() + "", mImages, mGid, HighCommunityApplication.mUserInfo.getToken());
    }

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
            Intent intent = new Intent(LabelAct.this,
                    AlbumActivity.class);
            startActivity(intent);
            LabelAct.this.overridePendingTransition(com.jeremyfeinstein.slidingmenu.lib.R.anim.activity_translate_in,
                    com.jeremyfeinstein.slidingmenu.lib.R.anim.activity_translate_out);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requesetPhoto) {
            // 拍照返回
            if (resultCode != RESULT_OK) {
                return;
            }
//            RequestCropImage(mPhotoUri);
            ImageItem mItem = new ImageItem();
            mItem.setImagePath(mPhotoUri.getPath());
            Bimp.tempSelectBitmap.add(0, mItem);
            mPostAdapter.notifyDataSetChanged();
        } else if (requestCode == requestFile) {
            // 图库返回
            if (data != null) {
                // 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                Uri mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                } else {
                    // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap image = extras.getParcelable("data");
                        if (image != null) {
                            mImageCaptureUri = SaveBitmap.SaveBitmap(image);
                        }
                    }
                }
                RequestCropImage(mImageCaptureUri);
            }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPhotoPopupWindow != null && mPhotoPopupWindow.isShowing()) {
                mPhotoPopupWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (null != mWaitingWindow && mWaitingWindow.isShowing())
                mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null != mWaitingWindow && mWaitingWindow.isShowing())
                mWaitingWindow.dismiss();
            if (null == message)
                return;
            CommunityFrag.isNeedRefresh = true;
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
//
            if (type == 0) {
                Intent goMainIntent = new Intent(LabelAct.this, MainActivity.class);
                goMainIntent.putExtra("communityFlag", 0x22);
                startActivity(goMainIntent);
            } else {
                LabelAct.this.finish();
            }
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

        }
    };

}
