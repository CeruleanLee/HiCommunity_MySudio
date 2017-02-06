package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.SaveBitmap;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_DoorBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InitBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_UnitBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECListDialog;

/**
 * @功能：自治大厅 提交资料界面<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoCommitDataFrag extends BaseFragment implements View.OnTouchListener {
    public static final String Tag = "~~~AutoCommit~~~";
    public static final String FRAGMENTTAG = "AutoCommitDataFrag";
    @Bind(R.id.autoAct_ed_name)
    EditText ed_Name;
    @Bind(R.id.autoAct_ed_quName)
    TextView ed_QuName;
    @Bind(R.id.autoAct_ed_louNum)
    CheckedTextView ed_LouNum;
    @Bind(R.id.autoAct_ed_danyuanNum)
    CheckedTextView ed_DanyuanNum;
    @Bind(R.id.autoAct_ed_menNum)
    CheckedTextView ed_MenNum;
    @Bind(R.id.autoAct_ed_idZ)
    ImageView img_IdZ;
    @Bind(R.id.autoAct_ed_idF)
    ImageView img_IdF;
    @Bind(R.id.autoAct_eproperty)
    ImageView img_Eproperty;
    @Bind(R.id.auto_getviryCode)
    TextView but_GetviryCode;
    @Bind(R.id.autoAct_telNum)
    TextView ed_TelNum;
    @Bind(R.id.autoAct_putverifyCode)
    EditText ed_PutverifyCode;
    @Bind(R.id.autoAct_commit)
    TextView but_Commit;
    @Bind(R.id.commitLayout)
    LinearLayout activityMain;
    private onCounter mCounter;

    View contentView;
    Context context;

    PopupWindow mPhotoPopupWindow = null, mWaitingWindow = null;
    boolean IsClicked = false;
    int requesetPhoto = 0x000001, requestFile = 0x000002;
    Uri mPhotoUri = null;
    int ClickId;
    String idZUri, idFUri, epropertyUri;
    public Auto_InitBean.Auto_Init_DataEntity mData, mLastData;
    String username;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        contentView = LayoutInflater.from(context).inflate(R.layout.frag_autonomous_commitdata, null);
        ButterKnife.bind(this, contentView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        ButterKnife.bind(this, contentView);
        Bundle bundle = getArguments();
        bundle.setClassLoader(getClass().getClassLoader());
        mData = bundle.getParcelable("data");
        Log.e(Tag, "STATUS--->" + mData.getStatus());
        Log.e(Tag, "mData--->" + mData.toString());
        username = HighCommunityApplication.mUserInfo.getUsername();
        Log.d(Tag, "用户名：" + username);
        initView();
        return contentView;
    }

    String village_id;

    private void initView() {
        ed_QuName.setText(mData.getVillage().getVillage_name());
        village_id = mData.getVillage().getVillage_id();
        ed_TelNum.setText(username);
        ed_LouNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(Tag, "是否有焦点" + hasFocus);
                if (hasFocus) {
                    mmBuildingNums.clear();
                    setBuildingNum();
                }
            }
        });
        ed_DanyuanNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mUnitNumList.clear();
                    setUnitNum();
                }
            }
        });
        ed_MenNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(Tag, "是否有焦点" + hasFocus);
                if (hasFocus) {
                    mDoorNumList.clear();
                    setDoorNum();
                }
            }
        });
        activityMain.requestFocus();
        activityMain.setFocusable(true);
        activityMain.setOnTouchListener(this);
        ed_LouNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_LouNum.setFocusable(true);
                ed_LouNum.setFocusableInTouchMode(true);
                ed_LouNum.requestFocus();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }

                return false;
            }
        });
        ed_DanyuanNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_DanyuanNum.setFocusable(true);
                ed_DanyuanNum.setFocusableInTouchMode(true);
                ed_DanyuanNum.requestFocus();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
        ed_MenNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_MenNum.setFocusable(true);
                ed_MenNum.setFocusableInTouchMode(true);
                ed_MenNum.requestFocus();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }

                return false;
            }
        });

    }

    private PopupWindow mWindow;

    List<String> mDoorNumList = new ArrayList<String>();
    ECListDialog mDoorChoice;
    String mDoorId;

    /**
     * 设置门牌号
     */
    private void setDoorNum() {
        if (mUnitID == "" || mUnitList == null) {
            Log.d(Tag, "menpai case2 ");
            Toast.makeText(getActivity(), "请先选择单元号", Toast.LENGTH_SHORT).show();

        } else {
            for (int i = 0; i < mDoorList.size(); i++) {
                mDoorNumList.add(i, mDoorList.get(i).getDoor_name());
            }
            mDoorChoice = new ECListDialog(getActivity(), mDoorNumList, "请选择门牌号");
            mDoorChoice.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
                @Override
                public void onDialogItemClick(Dialog d, int position) {
                    ed_MenNum.setText(mDoorNumList.get(position));
                    mDoorId = mDoorList.get(position).getDoor_id();
                    Log.d(Tag, "ed_MenNum--" + ed_MenNum.getText().toString());
                }
            });
            mDoorChoice.show();
        }
    }

    List<String> mUnitNumList = new ArrayList<String>();
    ECListDialog mUnitChoice;
    String mUnitID = "";

    /**
     * 设置单元号
     */
    private void setUnitNum() {
        if (mBuildingID == "" || mUnitList == null) {
            Log.d(Tag, "danyuan  case2 ");
            Toast.makeText(getActivity(), "请先选择楼栋号", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < mUnitList.size(); i++) {
                mUnitNumList.add(i, mUnitList.get(i).getUnit_name());
            }
            mUnitChoice = new ECListDialog(getActivity(), mUnitNumList, "请选择楼栋号");
            mUnitChoice.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
                @Override
                public void onDialogItemClick(Dialog d, int position) {
                    ed_DanyuanNum.setText(mUnitNumList.get(position));
                    mUnitID = mUnitList.get(position).getUnit_id();
                    Log.d(Tag, "ed_DanyuanNum--" + ed_DanyuanNum.getText().toString() + ",mUnitID---" + mUnitID);
                    HTTPHelper.Auto_GetDoor(mDoorbpi, mUnitID);
                }
            });
            mUnitChoice.show();
        }

    }

    List<String> mmBuildingNums = new ArrayList<String>();
    ECListDialog mBuildingChoice;
    String mBuildingID = "";

    /**
     * 获取楼栋list 设置用户选择的楼栋号
     */
    private void setBuildingNum() {

        for (int i = 0; i < mData.getBuilding().size(); i++) {
            mmBuildingNums.add(i, mData.getBuilding().get(i).getBuilding_name());
        }
        mBuildingChoice = new ECListDialog(getActivity(), mmBuildingNums, "请选择楼栋号");
        mBuildingChoice.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(Dialog d, int position) {
                ed_LouNum.setText(mmBuildingNums.get(position));
                mBuildingID = mData.getBuilding().get(position).getBuilding_id();
                Log.d(Tag, "ed_LouNum--" + ed_LouNum.getText().toString() + ",mBuildingID---" + mBuildingID);
                HTTPHelper.Auto_GetUnit(mUnitIbpi, mBuildingID);
            }
        });
        mBuildingChoice.show();
    }

    int quID = 0;
    int louID = 0;
    int UnitID = 0;
    int doorID = 0;

    /**
     * 提交数据
     */
    private void toCommitData() {
        if (IsClicked) {
            return;
        }
        if (mCounter != null) {

            mCounter.cancel();
        }
        IsClicked = true;
        String name = ed_Name.getText().toString().trim();

        String quStr = ed_QuName.getText().toString().trim();
        String louStr = ed_LouNum.getText().toString().trim();
        String UnitStr = ed_DanyuanNum.getText().toString().trim();
        String doorStr = ed_MenNum.getText().toString().trim();
        String tel = ed_TelNum.getText().toString().trim();
        String captcha = ed_PutverifyCode.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            HighCommunityUtils.GetInstantiation().ShowToast("姓名不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(quStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("小区不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(louStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("楼栋不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(UnitStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("单元不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(doorStr)) {
            HighCommunityUtils.GetInstantiation().ShowToast("门牌号不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(tel)) {
            HighCommunityUtils.GetInstantiation().ShowToast("手机号不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(idZUri)) {
            HighCommunityUtils.GetInstantiation().ShowToast("身份证正面图片不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(idFUri)) {
            HighCommunityUtils.GetInstantiation().ShowToast("身份证背面图片不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(epropertyUri)) {
            HighCommunityUtils.GetInstantiation().ShowToast("产权证图片不能为空", 0);
            IsClicked = false;
            return;
        } else if (TextUtils.isEmpty(captcha)) {
            HighCommunityUtils.GetInstantiation().ShowToast("验证码不能为空", 0);
            IsClicked = false;
            return;
        }
        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(context, contentView, Gravity.CENTER);
        HTTPHelper.Auto_Commit(mCommitIbpi, name, village_id, mBuildingID, mUnitID, mDoorId, tel, captcha, idZUri, idFUri, epropertyUri);
    }

    private PopupWindow mWatingWindow;

    private static HashMap<String, String> getBaseParamMap() {
        HashMap<String, String> maps = new HashMap<String, String>();
        if (!TextUtils.isEmpty((HighCommunityApplication.mUserInfo.getToken())))
            LogUtil.d("------User token------" + HighCommunityApplication.mUserInfo.getToken());
        maps.put("token", HighCommunityApplication.mUserInfo.getToken());
        return maps;
    }

    /**
     * 获取验证码
     **/

    private void getVerifyCode() {
        if (mCounter != null) {
            mCounter.onFinish();
        }
        mCounter = new onCounter(60000, 1000);
        mCounter.start();
        HTTPHelper.Auto_Send(mIbpi, ed_TelNum.getText().toString());
        mWindow = HighCommunityUtils.GetInstantiation()
                .ShowWaittingPopupWindow(getActivity(), ed_TelNum, Gravity.CENTER);
    }

    @OnClick({R.id.autoAct_ed_name, R.id.autoAct_ed_quName, R.id.autoAct_ed_louNum, R.id.autoAct_ed_danyuanNum, R.id.autoAct_ed_menNum, R.id.autoAct_ed_idZ, R.id.autoAct_ed_idF, R.id.autoAct_eproperty, R.id.auto_getviryCode, R.id.autoAct_telNum, R.id.autoAct_putverifyCode, R.id.autoAct_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoAct_ed_name:
                ed_Name.setFocusable(true);
                ed_Name.requestFocus();
                ed_Name.setFocusableInTouchMode(true);
                ClickId = R.id.autoAct_ed_name;
                break;
            case R.id.autoAct_ed_quName:
                setFouce(ed_QuName);
                break;
            case R.id.autoAct_ed_louNum:
                setFouce(ed_LouNum);
                ClickId = R.id.autoAct_ed_louNum;
                break;
            case R.id.autoAct_ed_danyuanNum:
                setFouce(ed_DanyuanNum);
                ClickId = R.id.autoAct_ed_danyuanNum;
                break;
            case R.id.autoAct_ed_menNum:
                setFouce(ed_MenNum);
                ClickId = R.id.autoAct_ed_menNum;
                break;
            case R.id.autoAct_ed_idZ:
                setFouce(img_IdZ);
                ClickId = R.id.autoAct_ed_idZ;
                if (mPhotoPopupWindow == null) {
                    mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                            .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                }
                mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
                        HighCommunityApplication.SoftKeyHight);

                break;
            case R.id.autoAct_ed_idF:
                setFouce(img_IdF);
                ClickId = R.id.autoAct_ed_idF;
                if (mPhotoPopupWindow == null) {
                    mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                            .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                }
                mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
                        HighCommunityApplication.SoftKeyHight);

                break;
            case R.id.autoAct_eproperty:
                setFouce(img_Eproperty);
                ClickId = R.id.autoAct_eproperty;
                if (mPhotoPopupWindow == null) {
                    mPhotoPopupWindow = HighCommunityUtils.GetInstantiation().ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                }
                mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
                        HighCommunityApplication.SoftKeyHight);

                break;
            case R.id.auto_getviryCode:
                ClickId = R.id.auto_getviryCode;
                getVerifyCode();
                break;
            case R.id.autoAct_telNum:
                Toast.makeText(getActivity(), "系统要求使用注册手机号进行验证", Toast.LENGTH_SHORT).show();
                ClickId = R.id.autoAct_telNum;
                break;
            case R.id.autoAct_putverifyCode:
                ed_PutverifyCode.setFocusable(true);
                ed_PutverifyCode.requestFocus();
                ed_PutverifyCode.setFocusableInTouchMode(true);
                ClickId = R.id.autoAct_putverifyCode;
                break;
            case R.id.autoAct_commit:
                ClickId = R.id.autoAct_commit;

                toCommitData();
                break;
        }
    }

    private void setFouce(View view) {
        view.setFocusable(true);
        view.requestFocus();
        view.setFocusableInTouchMode(true);
        if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
            Log.d(Tag, "!=null");
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Log.d(Tag, "=null ");
        } else {
        }

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mWatingWindow.dismiss();
            //3s后执行代码
            getActivity().finish();
        }
    };

    /**
     * 验证码倒计时类
     */
    public class onCounter extends CountDownTimer {

        public onCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            but_GetviryCode.setClickable(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            but_GetviryCode.setText((millisUntilFinished / 1000) + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            but_GetviryCode.setText("获取验证码");
            but_GetviryCode.setClickable(true);
        }

    }

    /**
     * 提交数据的handler
     **/
    BpiHttpHandler.IBpiHttpHandler mCommitIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
                    0);
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
            mWatingWindow.dismiss();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }

        }
    };
    /**
     * 获取验证码的handler
     **/
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.d(Tag, "onError---" + message);
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.d(Tag, "onSuccess---" + message);
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
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
            if (null != mWindow && mWindow.isShowing()) {
                mWindow.dismiss();
            }
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
    List<Auto_UnitBean.UnitDataEntity> mUnitList;
    /**
     * 根据楼栋获取单元数据的handler
     **/
    BpiHttpHandler.IBpiHttpHandler mUnitIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mUnitList = (List<Auto_UnitBean.UnitDataEntity>) message;

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveUnitDataEntity(result);
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
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
    List<Auto_DoorBean.DoorDataEntity> mDoorList;
    /**
     * 根据单元获取门牌号的handler
     **/
    BpiHttpHandler.IBpiHttpHandler mDoorbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mDoorList = (List<Auto_DoorBean.DoorDataEntity>) message;
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveDoorDataEntity(result);
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
            if (isShouldLogin) {
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
            startActivityForResult(imageCaptureIntent, requesetPhoto);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requesetPhoto) {
            // 拍照返回
            if (resultCode != getActivity().RESULT_OK) {
                return;
            }
            switch (ClickId) {
                case R.id.autoAct_ed_idZ:
                    idZUri = mPhotoUri.getPath();
                    ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(),
                            img_IdZ);
                    break;
                case R.id.autoAct_ed_idF:
                    idFUri = mPhotoUri.getPath();
                    ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(), img_IdF);
                    break;
                case R.id.autoAct_eproperty:
                    epropertyUri = mPhotoUri.getPath();
                    ImageLoaderUtil.disPlay("file://" + mPhotoUri.getPath(),
                            img_Eproperty);
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

                    case R.id.autoAct_ed_idZ:
                        idZUri = CommonUtils.resolvePhotoFromIntent(getActivity(), data);
                        ImageLoaderUtil.disPlay("file://" + idZUri, img_IdZ);
                        break;
                    case R.id.autoAct_ed_idF:
                        idFUri = CommonUtils.resolvePhotoFromIntent(getActivity(), data);
                        ImageLoaderUtil.disPlay("file://" + idFUri, img_IdF);
                        break;
                    case R.id.autoAct_eproperty:
                        epropertyUri = CommonUtils.resolvePhotoFromIntent(getActivity(), data);
                        ImageLoaderUtil.disPlay("file://" + epropertyUri, img_Eproperty);
                        break;
                }
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


    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(Tag + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        activityMain.setFocusable(true);
        activityMain.setFocusableInTouchMode(true);
        activityMain.requestFocus();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return false;
    }

}
