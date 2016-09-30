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

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.adapter.HuiChipsPayAdapter;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.bean.ChipsOrderBean;
import cn.hi028.android.highcommunity.bean.ChipsOrderResulBean;
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
 * @功能：惠生活购买支付<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-20<br>
 */
@EFragment(resName = "frag_hlife_chipspay")
public class HuiChipsPayFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "HuiChipsPayFrag";
    @ViewById(R.id.tv_reserve_name)
    TextView tv_reserve_name;
    @ViewById(R.id.tv_reserve_phone)
    TextView tv_reserve_phone;
    @ViewById(R.id.tv_reserve_address)
    TextView tv_reserve_address;
    @ViewById(R.id.tv_coupon)
    TextView tv_coupon;
    @ViewById(R.id.tv_wallet)
    TextView tv_wallet;
    @ViewById(R.id.edt_pay_num)
    EditText edt_pay_num;
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
    HuiChipsPayAdapter adapter;
    ChipsOrderBean order = null;
    int payType = 0;
    PopupWindow waitPop = null;
    static HuiChipsPayFrag frag;

    @AfterViews
    void initView() {
        frag = this;
        adapter = new HuiChipsPayAdapter(this);
        cl_goods.setAdapter(adapter);
        order_id = getActivity().getIntent().getIntExtra(PaymentActivity.INTENTTAG, 0) + "";
        ll_NoticeDetails_Progress.setVisibility(View.VISIBLE);
        HTTPHelper.getChipsOrder(mIbpiOrder, order_id);
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
        edt_pay_num.addTextChangedListener(new TextWatcher() {
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
                        edt_pay_num.setText("");
                        HighCommunityUtils.GetInstantiation().ShowToast("输入正确的金额", 0);
                        return;
                    }
                    if (money >= 0) {
                        if (order != null) {
                            if (money > order.getZero_money()) {
                                edt_pay_num.setText(order.getZero_money() + "");
                            } else {
                                if (money > (order.getTotal_price() - order.getTicket_value())) {
                                    edt_pay_num.setText((order.getTotal_price() - order.getTicket_value()) + "");
                                    return;
                                }
                                order.setZero_real_money(money);
                                updateData(order);
                            }
                        } else {
                            edt_pay_num.setText("");
                        }
                    } else {
                        edt_pay_num.setText((-money) + "");
                    }
                } else {
                    order.setZero_real_money(0);
                    updateData(order);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        WchatPayUtils.getInstance().init(getActivity());
    }

    @Click(R.id.btn_pay)
    public void onPay() {
        if (order.getReal_pri() == 0) {
            HighCommunityUtils.GetInstantiation().ShowToast("实际支付不能为零", 0);
            return;
        }
        if (HighCommunityUtils.isLogin(getActivity())) {
            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), tv_wallet, Gravity.CENTER);
            HTTPHelper.submitChipsOrder(mIbpiSubOrder, order.getOrder_id() + "", order.getReal_pri() + "", order.getTicket_id() + "", order.getZero_real_money() + "");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0x22 && resultCode == TicketAct.TICKET_RESULT) {
            AllTicketBean aticketBean = (AllTicketBean) data.getSerializableExtra("ticket");
            tv_coupon.setText("￥" + aticketBean.getTicket_value() + "");
            order.setTicket_id(aticketBean.getTid());
            order.setTicket_value(aticketBean.getTicket_value());
            float realToatal = order.getTotal_price() - order.getTicket_value();
            if (realToatal < order.getZero_money()) {
                edt_pay_num.setText("");
            }
            updateData(order);
        }
    }

    @Click(R.id.ll_ticket)
    public void onTicket() {
        Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(TicketAct.class));
        mIntent.putExtra(TicketAct.TICKET_TYPE, 3);
        mIntent.putExtra(TicketAct.TICKET_PRICE, order.getTotal_price() + "");
        startActivityForResult(mIntent, 0x22);
    }

    /**
     * 商品清单
     *
     * @return
     */
    public String listData() {
        String goodsList = "";
        for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
            goodsList = goodsList + adapter.getData().get(i).getName();
        }
        return goodsList;
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiSubOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message) {
                waitPop.dismiss();
                return;
            }
            OrderBean mBean = (OrderBean) message;
            if (order != null && payType == 1) {
                waitPop.dismiss();
                AlipayUtils.getInstance().payGoods(getActivity(), mBean.getSubject(),
                        mBean.getBody(), mBean.getTotal_fee(), mBean.getOut_trade_no(), order.getTicket_id() + "", order.getZero_real_money() + "", mBean.getNotify_url(), new AlipayUtils.onPayListener() {
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
            } else if (order != null && payType == 0) {
                HTTPHelper.submitChipsWPayOrder(mIbpiWPaySubOrder, mBean.getOut_trade_no(), order.getReal_pri() + "", order.getTicket_id(), order.getZero_real_money() + "", mBean.getBody());
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
            waitPop.dismiss();
        }
    };
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
            WXPayEntryActivity.toFrag=1;
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
    };
    /**
     * 订单信息回调对象
     */
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
            ChipsOrderBean bean = (ChipsOrderBean) message;
            updateData(bean);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveChipsOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            ll_NoticeDetails_Progress.setVisibility(View.GONE);
        }
    };

    /**
     * 跳转到详情
     */
    public static void toDetail() {
        Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
        mIntent.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_CHIP_ORDER_DETAIL);
        mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, frag.order.getOrder_id() + "");
        frag.startActivity(mIntent);
        frag.getActivity().finish();
    }

    public void updateData(ChipsOrderBean bean) {
        order = bean;
        adapter.setData(bean);
        tv_reserve_name.setText(bean.getName());
        tv_reserve_phone.setText(bean.getAddress().getTel());
        tv_reserve_address.setText(bean.getAddress().getAddress());
        tv_total_pay.setText("￥" + bean.getTotal_price() + "");
        tv_price.setText("￥" + CommonUtils.f2Bi(bean.getReal_pri()));
        tv_wallet.setText("零钱包（" + bean.getZero_money() + "元）");
    }
}
