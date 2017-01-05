/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.SaveBitmap;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.GridAdapter;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ChangeBirthDialog;
import photo.activity.AlbumActivity;
import photo.activity.GalleryActivity;
import photo.util.Bimp;
import photo.util.ImageItem;

/**
 * @功能：创建活动<br>\
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/13<br>
 */
//@EFragment(resName = "frag_activitycreate")
public class ActivityCreateFrag extends BaseFragment {
    public static final String Tag = "ActivityCreateFrag->";

    public static final String FRAGMENTTAG = "ActivityCreateFrag";

    @Bind(R.id.ptrgv_activityCreate_PostImage)
    PullToRefreshGridView mGridView;
    @Bind(R.id.et_activityCreate_name)
    EditText mName;
    @Bind(R.id.et_activityCreate_activityContent)
    EditText mContent;
    @Bind(R.id.et_activityCreate_StartTime)
    TextView mStartTime;
    @Bind(R.id.et_activityCreate_EndTime)
    TextView mEndTime;
    @Bind(R.id.tv_activityCreate_submit)
    TextView tv_activityCreate_submit;
    @Bind(R.id.et_activityCreate_Location)
    EditText mLocation;
    @Bind(R.id.et_activityCreate_Phone)
    EditText mPhone;
    @Bind(R.id.et_activityCreate_QQ)
    EditText mQQ;
    @Bind(R.id.et_activityCreate_Weixin)
    EditText mWeixin;

    private static final int TAKE_PICTURE = 0x000004;
    PopupWindow mPhotoPopupWindow;
    Uri mPhotoUri;
    GridAdapter mAdapter;
    String startTime, endTime;
    PopupWindow mWaitingWindow;
    boolean isClicked = false;
    List<String> mImages = new ArrayList<String>();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_activitycreate, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    /**
     * 初始化VIew
     */
//    @AfterViews
    void initView() {
        mAdapter = new GridAdapter(getActivity());
        mGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        mGridView.setAdapter(mAdapter);
        mStartTime.setOnClickListener(mClick);
        mEndTime.setOnClickListener(mClick);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                 if (mAdapter.getItem(i).startsWith("drawable://")) {
                                                     if (mPhotoPopupWindow == null) {
                                                         mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                                                                 .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                                                     }
                                                     mPhotoPopupWindow.showAtLocation(mContent, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
                                                 } else {
                                                     Intent intent = new Intent(getActivity(),
                                                             GalleryActivity.class);
                                                     intent.putExtra("position", "1");
                                                     intent.putExtra("ID", i);
                                                     startActivity(intent);
                                                 }
                                             }
                                         }
        );
        //初始化图片数组
        HighCommunityUtils.InitPicList(6);
        tv_activityCreate_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    @Override
    public void onResume() {
//        mAdapter.notifyDataSetChanged();
        setHeight();
        super.onResume();
    }


    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.et_activityCreate_StartTime://开始时间
                    ChangeBirthDialog mStartDialog = new ChangeBirthDialog(
                            getActivity());
                    mStartDialog.initData();
                    mStartDialog.show();
                    mStartDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                        @Override
                        public void onClick(String year, String month, String day, String hour, String min) {
                            mStartTime.setText(year + "年" + month + "月" + day + "日 " + hour + ":" + min);
                        }
                    });
                    break;
                case R.id.et_activityCreate_EndTime://结束时间
                    ChangeBirthDialog mEndDialog = new ChangeBirthDialog(
                            getActivity());
                    mEndDialog.initData();
                    mEndDialog.show();
                    mEndDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                        public void onClick(String year, String month, String day, String hour, String min) {
                            mEndTime.setText(year + "年" + month + "月" + day + "日 " + hour + ":" + min);
                        }
                    });
                    break;
            }
        }
    };

    /**
     * 设置高度
     */
    private void setHeight() {
        HighCommunityUtils.GetInstantiation().setGridViewHeightBasedOnChildren(mGridView, mAdapter, 4);
    }

    /**
     * 提交创建
     */
//    @Click(R.id.tv_activityCreate_submit)
    void submit() {
        if (isClicked) {
            return;
        }
        isClicked = true;
        if (mImages != null && mImages.size() > 0) {
            mImages.clear();
        }
        for (int i = 0; i < Bimp.tempSelectBitmap.size() - 1; i++) {
            if (!Bimp.tempSelectBitmap.get(i).getImagePath().startsWith("drawable://"))
                mImages.add(Bimp.tempSelectBitmap.get(i).getImagePath());
        }
        startTime = mStartTime.getText().toString().trim();
        endTime = mEndTime.getText().toString().trim();
        String title = mName.getText().toString().trim();
        String Content = mContent.getText().toString().trim();
        String location = mLocation.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String qq = mQQ.getText().toString().trim();
        String weixin = mWeixin.getText().toString().trim();
        if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
            HighCommunityUtils.GetInstantiation().ShowToast("请先登录,再操作", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(title)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入活动标题", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(Content)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入活动介绍", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(startTime)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择开始时间", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(endTime)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择结束时间", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(location)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入活动地址", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(phone)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入联系方式", 0);
            isClicked = false;
            return;
//        } else if (TextUtils.isEmpty(qq)) {
//            HighCommunityUtils.GetInstantiation().ShowToast("请输入QQ号码", 0);
//            isClicked = false;
//            return;
//        } else if (TextUtils.isEmpty(weixin)) {
//            HighCommunityUtils.GetInstantiation().ShowToast("请输入微信号", 0);
//            isClicked = false;
//            return;
        } else if (mImages.size() < 1) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择图片", 0);
            isClicked = false;
            return;
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mContent, Gravity.CENTER);
        HTTPHelper.CreateActivity(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", title, Content, phone, weixin, qq, location, startTime, endTime, mImages);
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
                        TAKE_PICTURE);
            }
            startActivityForResult(imageCaptureIntent, TAKE_PICTURE);
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
            Intent intent = new Intent(getActivity(),
                    AlbumActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(com.jeremyfeinstein.slidingmenu.lib.R.anim.activity_translate_in,
                    com.jeremyfeinstein.slidingmenu.lib.R.anim.activity_translate_out);
//            pop.dismiss();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 6 && resultCode == getActivity().RESULT_OK) {
                    ImageItem mItem = new ImageItem();
                    mItem.setImagePath(mPhotoUri.getPath());
                    Bimp.tempSelectBitmap.add(0, mItem);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPhotoPopupWindow != null && mPhotoPopupWindow.isShowing()) {
                mPhotoPopupWindow.dismiss();
                return true;
            }
        }
        return false;
    }

    /**
     * 活动发布回掉
     */
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            isClicked = false;
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
        }

        @Override
        public void onSuccess(Object message) {
            isClicked = false;
            mWaitingWindow.dismiss();
            if (message == null)
                return;
            HighCommunityUtils.GetInstantiation().ShowToast("活动发布成功", 0);
            getActivity().finish();
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
            isClicked = false;
            mWaitingWindow.dismiss();
        }
    };
}
