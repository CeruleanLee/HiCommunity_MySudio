/***************************************************************************


 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.params.HuiChipsOrderparams;
import cn.hi028.android.highcommunity.params.HuiSuppGdParams;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：惠生活物品直供支付界面<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-18<br>
 */
@EFragment(resName = "frag_huilife_chipsbuy")
public class HuiLifeChipsOrderFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "HuiLifeChipsOrderFrag";
    @ViewById(R.id.img_goods_pic)
    ImageView img_goods_pic;
    @ViewById(R.id.tv_goods_name)
    TextView tv_goods_name;
    @ViewById(R.id.tv_goods_price)
    TextView tv_goods_price;
    @ViewById(R.id.tv_goods_add)
    TextView tv_goods_add;
    @ViewById(R.id.tv_goods_reduce)
    TextView tv_goods_reduce;
    @ViewById(R.id.tv_goods_num)
    TextView tv_goods_num;
    @ViewById(R.id.tv_reserve_name)
    TextView tv_reserve_name;
    @ViewById(R.id.tv_reserve_phone)
    TextView tv_reserve_phone;
    @ViewById(R.id.tv_reserve_address)
    TextView tv_reserve_address;
    @ViewById(R.id.tv_address_default)
    TextView tv_address_default;
    @ViewById(R.id.tv_total_actual)
    TextView tv_total_actual;
    @ViewById(R.id.fl_huiLife_NoAddress)
    TextView mNoAddress;
    @ViewById(R.id.btn_pay)
    Button btn_pay;
    @ViewById(R.id.fl_huiLife_addressChooice)
    FrameLayout fl_huiLife_addressChooice;
    HuiChipsOrderparams orderParams;
    PopupWindow popWait;

    @AfterViews
    void initView() {
        orderParams = (HuiChipsOrderparams) getActivity().getIntent().getSerializableExtra(PaymentActivity.INTENTTAG);
        updateData(orderParams);
    }

    @Click(R.id.tv_goods_add)
    public void addNum() {
        if (orderParams.getNum() < orderParams.getRaise().getLimitNum()) {
            orderParams.setNum(orderParams.getNum() + 1);
        } else {
            HighCommunityUtils.GetInstantiation().ShowToast("所点数目已经达到库存最大数目了", 0);
        }
        updateOrder();
    }

    @Click(R.id.tv_goods_reduce)
    public void reduceNum() {
        if (orderParams.getNum() > 0) {
            orderParams.setNum(orderParams.getNum() - 1);
        }
        updateOrder();
    }

    @Click(R.id.fl_huiLife_addressChooice)
    void ChooiceAddress() {
        Intent mAddress = new Intent(getActivity(), GeneratedClassUtils.get(AddressAct.class));
        startActivityForResult(mAddress, 0x21);
    }

    @Click(R.id.fl_huiLife_NoAddress)
    void CreateAddress() {
        Intent mAddress = new Intent(getActivity(), GeneratedClassUtils.get(AddressAct.class));
        startActivityForResult(mAddress, 0x21);
    }

    @Click(R.id.btn_pay)
    void payChips() {
        if (orderParams.getNum() > 0) {
            if (HighCommunityUtils.isLogin(getActivity())) {
                if (TextUtils.isEmpty(orderParams.getDefaultAddress().getId())) {
                    HighCommunityUtils.GetInstantiation().ShowToast("请选择收货地址", 0);
                    return;
                }
                popWait = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
                HTTPHelper.submintHuiChipsOrder(mIbpiChipsOrder, orderParams.getRaise().getRid() + "", HighCommunityApplication.mUserInfo.getId() + "", orderParams.getNum() + "", orderParams.getRaise().getCurrent_price() + "", orderParams.getDefaultAddress().getId() + "");
            }
        } else {
            HighCommunityUtils.GetInstantiation().ShowToast("所点数目不能为空", 0);
        }
    }

    /**
     * 参与众筹回调
     */
    BpiHttpHandler.IBpiHttpHandler mIbpiChipsOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            popWait.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            popWait.dismiss();
            if (null == message)
                return;
            HighCommunityUtils.GetInstantiation().ShowToast("参与成功", 0);
            getActivity().finish();
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiChipsOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            popWait.dismiss();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0x21 && resultCode == 0x22) {
            AddressBean address = (AddressBean) data.getSerializableExtra("address");
            orderParams.setDefaultAddress(address);
            tv_reserve_name.setText(address.getReal_name());
            tv_reserve_phone.setText(address.getTel());
            tv_reserve_address.setText(address.getAddress());
            if ("0".equals(address.getIsDefault())) {
                tv_address_default.setVisibility(View.GONE);
            } else {
                tv_address_default.setVisibility(View.VISIBLE);
            }
            fl_huiLife_addressChooice.setVisibility(View.VISIBLE);
            mNoAddress.setVisibility(View.GONE);
        }
    }

    public void updateData(HuiChipsOrderparams data) {
        HuiSuppGdParams gdParams = new HuiSuppGdParams();
        if (data.getDefaultAddress() != null && data.getDefaultAddress().getId() != null) {
            tv_reserve_name.setText(data.getDefaultAddress().getReal_name());
            tv_reserve_phone.setText(data.getDefaultAddress().getTel());
            tv_reserve_address.setText(data.getDefaultAddress().getAddress());
            fl_huiLife_addressChooice.setVisibility(View.VISIBLE);
            mNoAddress.setVisibility(View.GONE);
        } else {
            mNoAddress.setVisibility(View.VISIBLE);
            fl_huiLife_addressChooice.setVisibility(View.GONE);
        }
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.getRaise().getCover_pic(), img_goods_pic, R.mipmap.default_no_pic, null);
        tv_goods_name.setText(data.getRaise().getR_name());
        tv_goods_price.setText("￥" + data.getRaise().getCurrent_price());
        data.setTotal_price(orderParams.getNum() * orderParams.getRaise().getCurrent_price());
        orderParams = data;
        updateOrder();
    }

    public void updateOrder() {
        tv_goods_num.setText(orderParams.getNum() + "");
        tv_total_actual.setText("￥" + CommonUtils.f2Bi(orderParams.getTotal_price()) + "");
    }
}
