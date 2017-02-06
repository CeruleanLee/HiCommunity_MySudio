/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.don.tools.BpiHttpHandler;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.AddressModifyBean;
import cn.hi028.android.highcommunity.bean.CitysBean;
import cn.hi028.android.highcommunity.bean.CreatAddress2Bean;
import cn.hi028.android.highcommunity.bean.DistrictBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.MHttpHolder;
import cn.hi028.android.highcommunity.utils.RegexValidateUtil;
import cn.hi028.android.highcommunity.view.ShowListUtils;

/**
 * @功能：修改地址界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/21<br>  2.0版本换接口
 */
public class AddressModifyFrag extends BaseFragment {
    static final String Tag = "AddressModifyFrag";
    public static final String FRAGMENTTAG = "AddressModifyFrag";
    @Bind(R.id.et_addressModify_name)
    EditText mName;
    @Bind(R.id.et_addressModify_phone)
    EditText mPhone;
    @Bind(R.id.et_addressModify_city)
    TextView mCity;
    @Bind(R.id.et_addressModify_quxian)
    TextView mQuxian;
    @Bind(R.id.et_addressModify_xiaoqu)
    TextView mXiaoqu;
    @Bind(R.id.et_addressModify_build)
    EditText mBuild;
    @Bind(R.id.et_addressModify_unit)
    EditText mUnit;
    @Bind(R.id.et_addressModify_doorNumber)
    EditText mDoorNumber;
    @Bind(R.id.ll_AddressModify_DefultLayout)
    LinearLayout mDefultLayout;
    @Bind(R.id.tv_AddressModify_SetDefult)
    TextView mIsDefult;
    @Bind(R.id.progress_addressModify)
    View mProgress;
    @Bind(R.id.tv_addressModify_Nodata)
    TextView mNodata;
    @Bind(R.id.et_addressModify_area)
    TextView mArea;
    @Bind(R.id.et_addressModify_detailAdress)
    EditText mDetailAdress;
    @Bind(R.id.vMasker)
    View vMasker;

    boolean defult = false;
    String defulttag = "0";
    String aid = "";
    PopupWindow mWaitingWindow, mListWindow;
    AddressModifyBean mModifyBean;
    ArrayList<CitysBean> mCityBean;
    ArrayList<DistrictBean> mDistrictBean;
    ArrayList<VallageBean> mVillageBean = new ArrayList<VallageBean>();
    String CityId, QuxianId, XiaoquId;
    /**
     * 城市区县小区选择器
     **/
    OptionsPickerView pvOptions;
    @Bind(R.id.tv_addressModify_submit)
    TextView tvAddressModifySubmit;
    private ArrayList<CitysBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<DistrictBean>> options2Items = new ArrayList<>();
    ArrayList<DistrictBean> options2Items_01 = new ArrayList<DistrictBean>();
    int isModify;
    AddressBean modifyData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_addressmodify, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    public void initView() {
        Log.e(Tag, "initView");
        Bundle bundle = getArguments();
        if (bundle != null) {
            modifyData = bundle.getParcelable("modifyData");
            isModify = bundle.getInt("isModify", -1);
            Log.e(Tag, "传过来的对象：" + modifyData.toString());
        } else {
            modifyData = new AddressBean();
            Log.e(Tag, "传过来的对象：null");
        }
        if (isModify == 1) {
            setData(modifyData);
        }
        mHttpUtils = MHttpHolder.getHttpUtils();
        Log.e(Tag, "传过来的aid：" + aid);
        mDefultLayout.setOnClickListener(mClick);
        mCity.setOnClickListener(mClick);
        mArea.setOnClickListener(mClick);
        mDetailAdress.setOnClickListener(mClick);
        //选项选择器
        pvOptions = new OptionsPickerView(getActivity());
        //选项1
        options1Items.add(new CitysBean("成都市", "5101"));
        //监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getCity() + " " + options2Items.get(options1).get(option2).getDistrict();
                city = options1Items.get(options1).getCity();
                district = options2Items.get(options1).get(option2).getDistrict();
                mArea.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });
        mName.requestFocus();
    }

    private void setData(AddressBean modifyData) {
        Log.e(Tag, "setData");
        mName.setText(modifyData.getReal_name());
        mPhone.setText(modifyData.getTel());
        String address = modifyData.getAddress();
        String City = "";
        String detail = "";
        if (address.contains("区")) {
            City = address.substring(0, address.indexOf("区") + 1);
            detail = address.substring(address.indexOf("区") + 1);
        } else if (address.contains("县")) {
            City = address.substring(0, address.indexOf("县") + 1);
            detail = address.substring(address.indexOf("县") + 1);
        } else if (address.contains("市")) {
            int index = address.indexOf("市");
            City = address.substring(0, address.indexOf("市", index + 1) + 1);
            detail = address.substring(address.indexOf("市") + 1);
        }
        mArea.setText(City);
        mDetailAdress.setText(detail);
        if (modifyData.getIsDefault().equals("0")) {
            mIsDefult.setSelected(false);
            defulttag = 0 + "";
        } else if (modifyData.getIsDefault().equals("1")) {
            defulttag = 1 + "";
            mIsDefult.setSelected(true);
        }
        if (modifyData.getId() != null && modifyData.getId() != "") {
            aid = modifyData.getId();
        }
    }
    String city = "";
    String district = "";
    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_AddressModify_DefultLayout:
                    if (defult) {
                        defult = false;
                        mIsDefult.setSelected(false);
                        defulttag = "0";
                    } else {
                        defult = true;
                        defulttag = "1";
                        mIsDefult.setSelected(true);
                    }
                    break;
                case R.id.et_addressModify_city:
                    mListWindow = ShowListUtils.GetInstantiation().ShowCityList(getActivity(), mCity, mBack, mCityBean);
                    break;
                case R.id.et_addressModify_area:
                    hideInputManager();
                    pvOptions.show();

                    break;
                case R.id.et_addressModify_detailAdress:
                    break;
            }
        }
    };
/**隐藏软键盘**/
    private void hideInputManager() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        Log.e(Tag,"isOpen:"+isOpen);
        if (imm.isActive()){
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0); //强制隐藏键盘 tips：没有直接绑定某个edittext  因为页面上有多个ed
        }
    }

    ShowListUtils.OnItemClickBack mBack = new ShowListUtils.OnItemClickBack() {
        @Override
        public void onClick(Object mBack) {
            Log.e(Tag, "ShowListUtils.OnItemClickBack");

            if (mBack instanceof CitysBean) {
                mCity.setText(((CitysBean) mBack).getCity());
                CityId = ((CitysBean) mBack).getCity_code();
                HTTPHelper.getAddressDistrist(mDistristIbpi, CityId);
            } else if (mBack instanceof DistrictBean) {
                mQuxian.setText(((DistrictBean) mBack).getDistrict());
                QuxianId = ((DistrictBean) mBack).getDistrict_code();
            }

        }
    };

    /**
     * 选择城市区县  城市已经固定只能选成都
     **/
    BpiHttpHandler.IBpiHttpHandler mDistristIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag, "选择城市区县  onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "选择城市区县  onSuccess");
            if (null == message)
                return;
            mDistrictBean = (ArrayList<DistrictBean>) message;
            options2Items_01 = mDistrictBean;
            //选项2
            options2Items.add(mDistrictBean);
            pvOptions.setPicker(options1Items, options2Items, true);
            pvOptions.setTitle("请选择所在区县");
            pvOptions.setCyclic(false, false, false);
            //设置默认选中的三级项目
            pvOptions.setSelectOptions(0, 0, 0);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveDistrict(result);
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


    void submit() {
        Log.e(Tag, "submit");
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String detail = mDetailAdress.getText().toString().trim();
        String area = mArea.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            HighCommunityUtils.GetInstantiation().ShowToast("姓名不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(phone)) {
            HighCommunityUtils.GetInstantiation().ShowToast("联系方式不能为空", 0);

            return;
        } else if (TextUtils.isEmpty(area)) {
            HighCommunityUtils.GetInstantiation().ShowToast("所在区县不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(detail)) {
            HighCommunityUtils.GetInstantiation().ShowToast("详细地址不能为空", 0);
            return;
        } else if (!RegexValidateUtil.checkMobileNumber(mPhone.getText().toString())) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入正确的电话号码", 0);
            return;
        }

        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mDoorNumber, Gravity.CENTER);
        postSubmit(city, district, detail, name, phone, defulttag);
    }

    private HttpUtils mHttpUtils;

    private void postSubmit(String city, String district, String detail, String name, String phone, String defulttag) {
        Log.e(Tag, "进入编辑收货地址");
        String url = "http://028hi.cn/api/saddress/edit.html";
        RequestParams params = new RequestParams();
        params.addBodyParameter("token", HighCommunityApplication.mUserInfo.getToken());
        Log.e(Tag, "aid==" + aid);

        if (aid != null && aid != "") {
            params.addBodyParameter("aid", aid);
        }
        params.addBodyParameter("city", city);
        params.addBodyParameter("district", district);
        params.addBodyParameter("detail", detail);
        params.addBodyParameter("name", name);
        params.addBodyParameter("tel", phone);
        params.addBodyParameter("isDefault", defulttag);
        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.e(Tag, "http 访问失败的 arg1--->" + arg1.toString());
                mWaitingWindow.dismiss();
                HighCommunityUtils.GetInstantiation().ShowToast(arg1.toString(), 0);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                String content = arg0.result;
                Log.e(Tag, "http 访问success的 content--->" + content);
                mWaitingWindow.dismiss();
                CreatAddress2Bean mInitBean = new Gson().fromJson(content, CreatAddress2Bean.class);
                if (mInitBean != null) {
                    HighCommunityUtils.GetInstantiation().ShowToast(mInitBean.getMsg(), 0);
                    getActivity().finish();

                }
            }
        });
    }

    BpiHttpHandler.IBpiHttpHandler mOpereteIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag, "提交数据失败");
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "提交数据成功");

            mWaitingWindow.dismiss();
            if (message != null) {
                CreatAddress2Bean.CreatAddress2DataEntity mBean = (CreatAddress2Bean.CreatAddress2DataEntity) message;
            } else {
                Log.e(Tag, "message null");
            }
            HighCommunityUtils.GetInstantiation().ShowToast("数据提交成功", 0);
            getActivity().finish();

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCreatAddress2Entity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
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

    public void onRight() {
        Log.e(Tag, "onRight");
        if (HighCommunityUtils.isLogin(getActivity())) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mDoorNumber, Gravity.CENTER);
            HTTPHelper.DeleteAddress2(mDeteleIbpi, modifyData.getId());
        }
    }

    BpiHttpHandler.IBpiHttpHandler mDeteleIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag, "删除onError");

            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "删除成功");

            mWaitingWindow.dismiss();
            if (message == null)
                return;
            HighCommunityUtils.GetInstantiation().ShowToast("删除成功", 0);
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

            Log.e(Tag, "cancleAsyncTask  delete");

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x22 && resultCode == 0x22 && data != null) {
            VallageBean mBean = (VallageBean) data.getSerializableExtra(Constacts.SEARCH_RESULT);
            if (mBean != null) {
                mXiaoqu.setText(mBean.getName());
                XiaoquId = mBean.getId() + "";
            }
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(Tag, "pvOptions.isShowing()--->" + pvOptions.isShowing());
        if (pvOptions != null && pvOptions.isShowing()) {
            pvOptions.dismiss();
            return true;
        }
        if (mListWindow != null && mListWindow.isShowing()) {
            mListWindow.dismiss();
            System.out.println("mlist window has dismissed");
            return true;
        } else if (mWaitingWindow != null && mWaitingWindow.isShowing()) {
            mWaitingWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        Log.e(Tag, "onResume");
        super.onResume();
        HTTPHelper.getAddressDistrist(mDistristIbpi, "5101");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_addressModify_submit)
    public void onClick() {
        Log.e(Tag, "tv_addressModify_submit  onClick");
        submit();
    }

}
