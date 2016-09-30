/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.bean.CouponBean;
import cn.hi028.android.highcommunity.bean.OrderBean;
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
 * @功能：物业费等费用的支付页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/22<br>
 */
@EFragment(resName = "frag_billpay")
public class BillPayFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "BillPayFrag";

    @ViewById(R.id.tv_amount_pay)
    TextView mTotal;
    @ViewById(R.id.tv_billpay_discount)
    TextView mDiscount;
    @ViewById(R.id.tv_billpay_wallet)
    TextView mWallet;//零钱包（共55元）
    @ViewById(R.id.et_billpay_sum)
    EditText mSum;
    @ViewById(R.id.rb_billpay_group)
    RadioGroup mPayTypeGroup;
    @ViewById(R.id.rb_billpay_weixinpay)
    RadioButton mWeixin;
    @ViewById(R.id.rb_billpay_alipay)
    RadioButton mAlipay;
    @ViewById(R.id.tv_billpay_realprice)
    TextView mRealPrice;

    @ViewById(R.id.progress_billpay)
    View mProgress;
    @ViewById(R.id.btn_billpay_pay)
    Button btn_billpay_pay;
    @ViewById(R.id.tv_billpay_Nodata)
    TextView mNodata;
    String fid = "";
    boolean isAliPay = false;
    CouponBean mBean;
    float ticketPrice = 0.0f;
    String ticketId = "";
    PopupWindow mWaitingWindow;
    public static BillPayFrag frag;
    String payType = "";

    @AfterViews
    void initView() {
        frag = this;
        WchatPayUtils.getInstance().init(getActivity());
        mSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    float money = 0.0f;
                    try {
                        money = Float.parseFloat(s.toString());
                    } catch (Exception e) {
                        mSum.setText("");
                        HighCommunityUtils.GetInstantiation().ShowToast("输入正确的金额", 0);
                        return;
                    }
                    if (money >= 0) {
                        if (mBean != null) {
                            if (money > mBean.getMoney()) {
                                mSum.setText(mBean.getMoney() + "");
                            } else {
                                if (money > (mBean.getTotal() - mBean.getTicket_value())) {
                                    mSum.setText((mBean.getTotal() - mBean.getTicket_value()) + "");
                                    return;
                                }
                                mBean.setZero_money(money);
                                updateOrder();
                            }
                        } else {
                            mSum.setText("");
                        }
                    } else {
                        mSum.setText((-money) + "");
                    }
                } else {
                    mBean.setZero_money(0);
                    updateOrder();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        payType = getActivity().getIntent().getStringExtra(PaymentActivity.INTENTTAGTYPE);
        fid = getActivity().getIntent().getStringExtra(PaymentActivity.INTENTTAG);
        if (!TextUtils.isEmpty(fid)) {
            mProgress.setVisibility(View.VISIBLE);
            HTTPHelper.getCoupon(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", fid);
        } else {
            mNodata.setVisibility(View.VISIBLE);
        }
        mPayTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rb_billpay_weixinpay) {
                    isAliPay = false;
                } else if (checkedId == R.id.rb_billpay_alipay) {
                    isAliPay = true;
                }
            }
        });
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mBean = (CouponBean) message;
            mTotal.setText("￥" + mBean.getTotal());
            mWallet.setText("零钱包（共" + mBean.getMoney() + "元）");
//            if (mBean.getTickets().size() > 0) {
//                mDiscount.setText("￥" + mBean.getTickets().get(0).getDiscount() * mBean.getTotal());
//                mRealPrice.setText(mBean.getTotal() + "");
//            } else {
//
//
//            }
            mDiscount.setText("0");
            mRealPrice.setText(mBean.getTotal() + "");
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCoupon(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    @Click(R.id.btn_billpay_pay)
    void pay() {
        if (mBean.getReal_price() == 0) {
            HighCommunityUtils.GetInstantiation().ShowToast("实际支付不能为零", 0);
            return;
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mAlipay, Gravity.CENTER);
        HTTPHelper.CreateOrder(mOrderIbpi, mBean.getReal_price() + "", fid, mBean.getZero_money() + "", mBean.getTicket_id());

    }

    BpiHttpHandler.IBpiHttpHandler mOrderIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.getWidth();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (null == message)
                return;
            OrderBean bean = (OrderBean) message;
            if (isAliPay) {
                AlipayUtils.getInstance().payGoods(getActivity(), bean.getSubject(),
                        bean.getBody(), CommonUtils.str2Bi(bean.getTotal_fee()), bean.getOut_trade_no(), CommonUtils.f2Bi(mBean.getZero_money()) + "", mBean.getTicket_id(), bean.getNotify_url(), new AlipayUtils.onPayListener() {
                            @Override
                            public void onPay(PayResult result) {
                                String resultStatus = result.getResultStatus();
                                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                if (TextUtils.equals(resultStatus, "9000")) {
                                    Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                                    toDetail();
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
            } else {
                mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_billpay_pay, Gravity.CENTER);
                HTTPHelper.submitBillWPayOrder(mIbpiWPaySubOrder, mBean.getOrder_id(), bean.getOut_trade_no(), mBean.getReal_price() + "", mBean.getTicket_id(), CommonUtils.f2Bi(mBean.getZero_money()) + "", bean.getBody());
            }
        }


        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    public static void toDetail() {
        Intent mPayDetail = new Intent(frag.getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
        mPayDetail.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICEPAYMENT_DETAILS);
        mPayDetail.putExtra(ServiceSecondAct.INTENTTAG, frag.payType + "");
        mPayDetail.putExtra(ServicePaymentDetailFrag.FRAGMENTTAG, frag.fid);
        frag.getActivity().startActivity(mPayDetail);
        frag.getActivity().finish();
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiWPaySubOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (null == message) {
                return;
            }
            WXPayEntryActivity.toFrag = 2;
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
            mWaitingWindow.dismiss();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0x22 && resultCode == TicketAct.TICKET_RESULT) {
            AllTicketBean aticketBean = (AllTicketBean) data.getSerializableExtra("ticket");
            mBean.setTicket_id(aticketBean.getTid());
            mBean.setTicket_value(((10 - aticketBean.getTicket_value()) / 10f) * mBean.getTotal());
            mDiscount.setText(mBean.getTicket_value() + "");
            float realToatal = mBean.getTotal() - mBean.getTicket_value();
            if (realToatal < mBean.getZero_money()) {
                mSum.setText("");
            }
            updateOrder();
        }
    }

    @Click(R.id.fl_billpay_discountLayout)
    void choice() {
        if (mBean != null) {
            Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(TicketAct.class));
            mIntent.putExtra(TicketAct.TICKET_TYPE, 1);
            mIntent.putExtra(TicketAct.TICKET_PRICE, mBean.getTotal() + "");
            startActivityForResult(mIntent, 0x22);
        } else {
            HighCommunityUtils.GetInstantiation().ShowToast("支付信息不能为空", 0);
        }
    }

    public void updateOrder() {
        mTotal.setText(CommonUtils.f2Bi(mBean.getTotal()) + "");
        mRealPrice.setText("￥" + CommonUtils.f2Bi(mBean.getReal_price()) + "");
    }
}
