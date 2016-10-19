package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.SaveBitmap;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.RequestParams;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ImageUtil;
import net.duohuo.dhroid.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
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

public class AutoCommitDataFrag extends BaseFragment {
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
    private onCounter mCounter;

    View contentView;
    Context context;

    PopupWindow mPhotoPopupWindow = null, mWaitingWindow;
    boolean IsClicked = false;
    int requesetPhoto = 0x000001, requestFile = 0x000002;//requestCropImage = 0x000003
    Uri mPhotoUri = null;
    int ClickId;
    String idZUri, idFUri, epropertyUri;
    public Auto_InitBean.Auto_Init_DataEntity mData;
    String username;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.frag_autonomous_commitdata, null);
        ButterKnife.bind(this, contentView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        ButterKnife.bind(this, contentView);
        Bundle bundle = getArguments();
        mData = bundle.getParcelable("data");

        username = HighCommunityApplication.mUserInfo.getUsername();
        Log.d(Tag, "用户名：" + username);
//        Toast.makeText(getActivity(),"用户名："+username,Toast.LENGTH_SHORT).show();

        initView();
        return contentView;
    }

    private void initView() {
        ed_QuName.setText(mData.getVillage().getVillage_name());
        ed_TelNum.setText(username);

        ed_LouNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(Tag, "是否有焦点" + hasFocus);
                Toast.makeText(getActivity(), "是否有焦点" + hasFocus, Toast.LENGTH_SHORT).show();

                if (!hasFocus) {
                    ed_LouNum.setChecked(false);
                }
            }
        });

    }

    private PopupWindow mWindow;


    @OnClick({R.id.autoAct_ed_quName, R.id.autoAct_ed_louNum, R.id.autoAct_ed_danyuanNum, R.id.autoAct_ed_menNum, R.id.autoAct_ed_idZ, R.id.autoAct_ed_idF, R.id.autoAct_eproperty, R.id.auto_getviryCode, R.id.autoAct_telNum, R.id.autoAct_putverifyCode, R.id.autoAct_commit})
    public void onClick(View view) {
        if (view.getId() == R.id.autoAct_ed_louNum) {
            ed_LouNum.setChecked(true);
            ed_MenNum.setChecked(false);
            ed_DanyuanNum.setChecked(false);
            mmBuildingNums.clear();
            Toast.makeText(getActivity(), "楼栋号被点击", Toast.LENGTH_SHORT).show();
            setBuildingNum();

            ClickId = R.id.autoAct_ed_louNum;
        } else if (view.getId() == R.id.autoAct_ed_menNum) {
            ed_MenNum.setChecked(true);
            ed_LouNum.setChecked(false);
            ed_DanyuanNum.setChecked(false);
            mDoorNumList.clear();
            setDoorNum();
            ClickId = R.id.autoAct_ed_menNum;

        } else if (view.getId() == R.id.autoAct_ed_danyuanNum) {
            ed_DanyuanNum.setChecked(true);
            ed_LouNum.setChecked(false);
            ed_MenNum.setChecked(false);
//                ed_LouNum.toggle();
            mUnitNumList.clear();
            setUnitNum();
            ClickId = R.id.autoAct_ed_danyuanNum;
        } else {
            ed_LouNum.setChecked(false);
            ed_MenNum.setChecked(false);
            ed_DanyuanNum.setChecked(false);
            switch (view.getId()) {
                case R.id.tv_secondtitle_name:
                    ClickId = R.id.tv_secondtitle_name;
                    break;
                case R.id.auto_nodata:
                    ClickId = R.id.auto_nodata;
                    break;
                case R.id.autoAct_ed_name://用户名
                    ClickId = R.id.autoAct_ed_name;

                    break;
//            case R.id.autoAct_ed_louNum://楼栋号
//                ed_LouNum.setChecked(true);
//                mmBuildingNums.clear();
//                Toast.makeText(getActivity(), "楼栋号被点击", Toast.LENGTH_SHORT).show();
//                setBuildingNum();
//
//                ClickId = R.id.autoAct_ed_louNum;
//
//                break;
//            case R.id.autoAct_ed_danyuanNum://单元号
////                ed_DanyuanNum.setChecked(true);
////                ed_LouNum.toggle();
//                mUnitNumList.clear();
//                setUnitNum();
//                ClickId = R.id.autoAct_ed_danyuanNum;
//                break;
//            case R.id.autoAct_ed_menNum://门牌号
////                ed_MenNum.setChecked(true);
//                mDoorNumList.clear();
//                setDoorNum();
//                ClickId = R.id.autoAct_ed_menNum;
//
//                break;
                case R.id.auto_getviryCode://获取验证码
                    ClickId = R.id.auto_getviryCode;
                    getVerifyCode();

                    break;
                case R.id.autoAct_telNum://填写电话
                    Toast.makeText(getActivity(), "系统要求使用注册时的手机号进行验证", Toast.LENGTH_SHORT).show();
                    ClickId = R.id.autoAct_telNum;
                    break;
                case R.id.autoAct_putverifyCode://写验证码
                    ClickId = R.id.autoAct_putverifyCode;
                    break;
                case R.id.autoAct_commit:// 提交
                    ClickId = R.id.autoAct_commit;
                    toCommitData();
                    break;
                case R.id.autoAct_ed_idZ:
                    ClickId = R.id.autoAct_ed_idZ;
                    if (mPhotoPopupWindow == null) {
                        mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                                .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                    }
                    mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
                            HighCommunityApplication.SoftKeyHight);

                    break;
                case R.id.autoAct_ed_idF:
                    ClickId = R.id.autoAct_ed_idF;
                    if (mPhotoPopupWindow == null) {
                        mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                                .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                    }
                    mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
                            HighCommunityApplication.SoftKeyHight);

                    break;
                case R.id.autoAct_eproperty:
                    ClickId = R.id.autoAct_eproperty;
                    if (mPhotoPopupWindow == null) {
                        mPhotoPopupWindow = HighCommunityUtils.GetInstantiation()
                                .ShowPhotoPopupWindow(getActivity(), mPhoto, mFile);
                    }
                    mPhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0,
                            HighCommunityApplication.SoftKeyHight);

                    break;

            }
        }
    }

    List<String> mDoorNumList = new ArrayList<String>();
    ECListDialog mDoorChoice;
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
    int quID = 0;   int louID = 0; int UnitID = 0; int doorID = 0;
    /**
     * 提交数据
     */
    private void toCommitData() {
//        if (IsClicked) {
//            return;
//        }
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
//        } else if (!RegexValidateUtil.checkMobileNumber(tel)) {
//            HighCommunityUtils.GetInstantiation().ShowToast("手机号格式不正确", 0);
//            IsClicked = false;
//            return;


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
//       mWaitingWindow = HighCommunityUtils
//                .GetInstantiation()
//                .ShowWaittingPopupWindow(getActivity(), mAvatar, Gravity.CENTER);



        HTTPHelper.Auto_Commit(mCommitIbpi, name, quStr, louStr, UnitStr, doorStr, tel, captcha, idZUri, idFUri, epropertyUri);

        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("name", name);
        mParamMap.put("village_id", quStr);
        mParamMap.put("building_id", louStr);
        mParamMap.put("unit_id", UnitStr);
        mParamMap.put("door_id", doorStr);
        mParamMap.put("tel", tel);
        mParamMap.put("captcha", captcha);
        try {
            mParamMap.put("IDCard", ImageUtil.getImage(idZUri));
            mParamMap.put("IDCard_F", ImageUtil.getImage(idFUri));
            mParamMap.put("house_certificate", ImageUtil.getImage(epropertyUri));
        } catch (FileNotFoundException e) {

        }
        Toast.makeText(getActivity(),"提交数据："+mParamMap.toString(),Toast.LENGTH_SHORT).show();
        Log.d(Tag,"提交数据："+mParamMap.toString());
//        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
//                + "   ling params:" + mParamMap.toString());

    }



    private static HashMap<String, String> getBaseParamMap() {
        HashMap<String, String> maps = new HashMap<String, String>();
        if (!TextUtils.isEmpty((HighCommunityApplication.mUserInfo.getToken())))
            LogUtil.d("------User token------"+HighCommunityApplication.mUserInfo.getToken());
        maps.put("token", HighCommunityApplication.mUserInfo.getToken());
        return maps;
    }

    /**
     * 获取验证码
     **/

    private void getVerifyCode() {
        mCounter = new onCounter(60000, 1000);
        mCounter.start();
        HTTPHelper.Auto_Send(mIbpi, ed_TelNum.getText().toString());
        mWindow = HighCommunityUtils.GetInstantiation()
                .ShowWaittingPopupWindow(getActivity(), ed_TelNum, Gravity.CENTER);
//        if (RegexValidateUtil.checkMobileNumber(ed_TelNum.getText()
//                .toString())) {
//            mCounter = new onCounter(60000, 1000);
//            mCounter.start();
//            HTTPHelper.Auto_Send(mIbpi, ed_TelNum.getText().toString());
//            mWindow = HighCommunityUtils.GetInstantiation()
//                    .ShowWaittingPopupWindow(getActivity(), ed_TelNum, Gravity.CENTER);
//        } else {
//            HighCommunityUtils.GetInstantiation().ShowToast(
//                    "请输入正确的电话号码", 0);
//        }
    }

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
            LogUtil.d(Tag + "-------------  initView   onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(),
                    0);


//			mLoadingView.loadSuccess();
//			mLoadingView.setVisibility(View.GONE);
//			LogUtil.d(Tag+"---~~~initViewonSuccess");
////						if (null == message) return;
//			LogUtil.d(Tag+"---~~~ initView   message:"+message);
//			ThirdServiceBean mBean = (ThirdServiceBean) message;
//			mAdapter.AddNewData(mBean.getServices());
//			mGridView.setAdapter(mAdapter);
//			pagerAdapter.setImageIdList(mBean.getBanners());
//			HighCommunityUtils.GetInstantiation()
//			.setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
//			tatalLayout.setVisibility(View.VISIBLE);

        }

        @Override
        public Object onResolve(String result) {
//			Log.e("renk", result);
//			LogUtil.d(Tag+"---~~~iresult"+result);
//			return HTTPHelper.ResolveThirdService(result);
            return null;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

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
//                        epropertyUri = mPhotoUri.getPath();
                        ImageLoaderUtil.disPlay("file://" + epropertyUri, img_Eproperty);
                        break;
                }
            }
        }
//        else if (requestCode == requestCropImage) {
//        }
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
//        startActivityForResult(intent, requestCropImage);
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

        //		mLoadingView.startLoading();
        registNetworkReceiver();
    }


    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    //有网络
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    //					if(nextPage == 1){
                    //					HTTPHelper.GetThirdService(mIbpi);
                    //					}
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    //					if(nextPage == 1){
                    //					}
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}
