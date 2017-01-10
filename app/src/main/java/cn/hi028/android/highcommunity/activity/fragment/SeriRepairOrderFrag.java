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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.SaveBitmap;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.CustomGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.adapter.GridAdapter;
import cn.hi028.android.highcommunity.bean.CertifiBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.RegexValidateUtil;
import cn.hi028.android.highcommunity.view.ChangeBirthDialog;
import photo.activity.AlbumActivity;
import photo.activity.GalleryActivity;
import photo.util.Bimp;
import photo.util.ImageItem;

/**
 * @功能：预约保修<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-11<br>
 */
@EFragment(resName = "frag_repair_order")
public class SeriRepairOrderFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "SeriRepairOrderFrag";
    @ViewById(R.id.edt_repair_name)
    EditText edt_repair_name;
    @ViewById(R.id.edt_repair_content)
    EditText edt_repair_content;
    @ViewById(R.id.edt_repair_phone)
    EditText edt_repair_phone;
    @ViewById(R.id.tv_repair_address)
    TextView tv_repair_address;
    @ViewById(R.id.tv_repair_time)
    TextView tv_repair_time;
    @ViewById(R.id.tv_order)
    TextView tv_order;
    @ViewById(R.id.cg_add_pic)
    CustomGridView cg_add_pic;
    CertifiBean data = null;
    GridAdapter adapter;

    private static final int TAKE_PICTURE = 0x000004;
    PopupWindow mPhotoPopupWindow = null, mWaitingWindow;
    Uri mPhotoUri = null;
    boolean isClicked = false;
    List<String> mImages = new ArrayList<String>();

    @AfterViews
    public void initView() {
        data = (CertifiBean) getActivity().getIntent().getSerializableExtra(ServiceSecondAct.INTENTTAG);
        if (data != null) {
            edt_repair_name.setText(data.getReal_name());
            edt_repair_phone.setText(data.getTel());
            tv_repair_address.setText(data.getAddress());
        }
        adapter = new GridAdapter(getActivity());
        cg_add_pic.setAdapter(adapter);
        cg_add_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.getItem(i).startsWith("drawable://")) {
                    if (mPhotoPopupWindow == null) {
                        mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                                .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                    }
                    mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
                } else {
                    Intent intent = new Intent(getActivity(),
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", i);
                    startActivity(intent);
                }
            }
        });
        //初始化图片数组
        HighCommunityUtils.InitPicList(3);
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
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
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() <= 3 && resultCode == getActivity().RESULT_OK) {
                    ImageItem mItem = new ImageItem();
                    mItem.setImagePath(mPhotoUri.getPath());
                    Bimp.tempSelectBitmap.add(0, mItem);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Click(R.id.ll_repair_time)
    public void selectTime() {
        ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                getActivity());
        mChangeBirthDialog.initData();
        mChangeBirthDialog.show();
        mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

            @Override
            public void onClick(String year, String month, String day, String hour, String min) {
                tv_repair_time.setText(year + "年" + month + "月" + day + "日 " + hour + ":" + min);
            }
        });
    }

    /**
     * 提交预约信息
     */
    @Click(R.id.tv_order)
    public void submit() {
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
        String nameStr = edt_repair_name.getText().toString().trim();
        String phoneStr = edt_repair_phone.getText().toString().trim();
        String timeStr = tv_repair_time.getText().toString().trim();
        String content = edt_repair_content.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("姓名不能为空", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(phoneStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("电话不能为空", 0);
            isClicked = false;
            return;
        } else if (!RegexValidateUtil.checkMobileNumber(phoneStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入正确的手机号", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(timeStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择预约日期", 0);
            isClicked = false;
            return;
        } else if (TextUtils.isEmpty(content)) {
            HighCommunityUtils.GetInstantiation().ShowToast("报修描述不能为空", 0);
            isClicked = false;
            return;
        }
//        else if (mImages.size() < 1) {
//            HighCommunityUtils.GetInstantiation().ShowToast("请选择图片", 0);
//            isClicked = false;
//            return;
//        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), tv_order, Gravity.CENTER);
        HTTPHelper.OrderRepairMsg(mIbpi, nameStr, phoneStr, data.getAddress(), timeStr, content, mImages);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (null != mWaitingWindow && mWaitingWindow.isShowing())
                mWaitingWindow.dismiss();
            isClicked = false;
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null != mWaitingWindow && mWaitingWindow.isShowing())
                mWaitingWindow.dismiss();
            isClicked = false;
            if (null == message)
                return;
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
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
            if (null != mWaitingWindow && mWaitingWindow.isShowing())
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
}
