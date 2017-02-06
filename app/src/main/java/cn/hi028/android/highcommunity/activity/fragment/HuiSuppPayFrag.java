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
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.view.CustomListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.adapter.HuiGdPayAdapter;
import cn.hi028.android.highcommunity.bean.GoodsOrderBean;
import cn.hi028.android.highcommunity.bean.WpayBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.alipay.AlipayUtils;
import cn.hi028.android.highcommunity.utils.alipay.PayResult;
import cn.hi028.android.highcommunity.utils.wchatpay.WchatPayUtils;
import cn.hi028.android.highcommunity.wxapi.WXPayEntryActivity;

/**
 * @功能：惠生活购买支付<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-20<br>
 */
@EFragment(resName = "frag_hlife_supppay")
public class HuiSuppPayFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "HuiSuppPayFrag";
    @ViewById(R.id.tv_reserve_name)
    TextView tv_reserve_name;
    @ViewById(R.id.tv_reserve_phone)
    TextView tv_reserve_phone;
    @ViewById(R.id.tv_reserve_address)
    TextView tv_reserve_address;
    @ViewById(R.id.tv_coupon)
    TextView tv_coupon;
    @ViewById(R.id.tv_reserve_wallet)
    TextView tv_reserve_wallet;
    @ViewById(R.id.tv_price)
    TextView tv_price;
    @ViewById(R.id.tv_total_pay)
    TextView tv_total_pay;
    @ViewById(R.id.btn_pay)
    Button btn_pay;
    @ViewById(R.id.rg_huil_ife)
    RadioGroup rg_huil_ife;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View ll_NoticeDetails_Progress;
    @ViewById(R.id.cl_goods)
    CustomListView cl_goods;
    String order_id;
    public HuiGdPayAdapter adapter;
    GoodsOrderBean order = null;
    int payType = 0;
    PopupWindow waitPop = null;
    static HuiSuppPayFrag fragment;

    @AfterViews
    void initView() {
        fragment = this;
        adapter = new HuiGdPayAdapter(this);
        cl_goods.setAdapter(adapter);
        order_id = getActivity().getIntent().getStringExtra(PaymentActivity.INTENTTAG);
        ll_NoticeDetails_Progress.setVisibility(View.VISIBLE);
        HTTPHelper.getSuppOrder(mIbpiOrder, order_id);
        rg_huil_ife.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_pay_ipay) {
                    payType = 1;
                } else if (checkedId == R.id.rb_pay_wx) {
                    payType = 0;
                }
            }
            
        });
        WchatPayUtils.getInstance().init(getActivity());
    }

    @Click(R.id.btn_pay)
    public void onPay() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            if (CommonUtils.floatTo(order.getReal_pri()) > 0) {
                if (order != null && payType == 1) {
                	
                    AlipayUtils.getInstance().payGoods(getActivity(), "物业直供商品", listData(), CommonUtils.f2Bi(order.getReal_pri()) + "",
                            order.getOut_trade_no(), "0", order.getZero_money() + "", order.getNotify_url(), new AlipayUtils.onPayListener() {

                                @Override
                                public void onPay(PayResult result) {
                                	
                                    String resultStatus = result.getResultStatus();
                                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                    if (TextUtils.equals(resultStatus, "9000")) {
                                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                                        toOrderDetail();
                                    } else {
                                        // 判断resultStatus 为非"9000"则代表可能支付失败
                                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                                        if (TextUtils.equals(resultStatus, "8000")) {
                                            Toast.makeText(getActivity(), "支付结果确认中", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                } else if (order != null && payType == 0) {
                    waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
                    HTTPHelper.submitSuppWPayOrder(mIbpiWPaySubOrder, order.getOrder_id());
                }
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast("支付金额，必须大于零", 0);
            }
        }
    }

    public static void toOrderDetail() {
        Intent mIntent = new Intent(fragment.getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
        mIntent.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_ORDER_DETAIL);
        mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, fragment.order.getOrder_id() + "");
        fragment.startActivity(mIntent);
        fragment.getActivity().finish();
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiWPaySubOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            waitPop.dismiss();
            if (null == message) {
                return;
            }
            WXPayEntryActivity.toFrag = 0;
            WpayBean mBean = (WpayBean) message;
            WchatPayUtils.getInstance().apay(getActivity(), mBean.getAppid(), mBean.getPartnerid(), mBean.getPrepayid(), mBean.getNoncestr(), mBean.getPackages(), mBean.getSign(), mBean.getTimestamp());
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveWpayBean(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
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

    public String listData() {
        String goodsList = "";
        for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
            goodsList = goodsList + adapter.getData().get(i).getGoods_name();
        }
        return goodsList;
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            ll_NoticeDetails_Progress.setVisibility(View.GONE);
            getActivity().finish();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            ll_NoticeDetails_Progress.setVisibility(View.GONE);
            if (null == message)
                return;
            GoodsOrderBean bean = (GoodsOrderBean) message;
            order = (GoodsOrderBean) message;
            updateData(bean);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiSuppOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            ll_NoticeDetails_Progress.setVisibility(View.GONE);
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

    public void updateData(GoodsOrderBean bean) {
        adapter.setData(bean.getGoods());
        tv_reserve_name.setText(bean.getReal_name());
        tv_reserve_phone.setText(bean.getTel());
        tv_reserve_address.setText(bean.getAddress());
        tv_coupon.setText(bean.getTicket_value() + "");
        tv_reserve_wallet.setText(bean.getZero_money() + "");
        tv_total_pay.setText("￥" + bean.getTotal_price() + "");
        tv_price.setText("￥" + CommonUtils.f2Bi(bean.getReal_pri()));
    }
}
