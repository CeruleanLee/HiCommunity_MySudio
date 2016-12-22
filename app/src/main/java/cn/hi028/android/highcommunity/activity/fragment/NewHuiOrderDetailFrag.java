/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.CustomListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.adapter.NewHuiGdPayAdapter;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.bean.NewHuiOrderBean_StrStatus;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：v2.0订单详情<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/19<br>
 */
@EFragment(resName = "frag_hlife_order_detail")
public class NewHuiOrderDetailFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "NewHuiOrderDetailFrag";
    public static final String Tag = "NewHuiOrderDetailFrag：";

    @ViewById(R.id.tv_reserve_name)
    TextView tv_reserve_name;
    @ViewById(R.id.tv_order_id)
    TextView tv_order_id;
    @ViewById(R.id.tv_order_time)
    TextView tv_order_time;
    @ViewById(R.id.tv_pay_time)
    TextView tv_pay_time;
    @ViewById(R.id.tv_send_time)
    TextView tv_send_time;
    @ViewById(R.id.tv_receive_time)
    TextView tv_receive_time;
    @ViewById(R.id.tv_comment_time)
    TextView tv_comment_time;
    @ViewById(R.id.tv_reserve_mark)
    TextView tv_reserve_mark;
    @ViewById(R.id.tv_order_merchant)
    TextView tv_order_merchant;

    @ViewById(R.id.tv_reserve_phone)
    TextView tv_reserve_phone;
    @ViewById(R.id.tv_reserve_address)
    TextView tv_reserve_address;
    @ViewById(R.id.tv_coupon)
    TextView tv_coupon;
    @ViewById(R.id.tv_reserve_wallet)
    TextView tv_reserve_wallet;
    @ViewById(R.id.paytype_layout)
    LinearLayout paytype_layout;
    @ViewById(R.id.mark_layout)
    LinearLayout mark_layout;
    @ViewById(R.id.tv_reserve_paytype)
    TextView tv_reserve_paytype;
    @ViewById(R.id.tv_price)
    TextView tv_price;
    @ViewById(R.id.tv_total_pay)
    TextView tv_total_pay;
    @ViewById(R.id.tv_order_operate2)
    TextView tv_order_operate2;
    @ViewById(R.id.tv_order_operate1)
    TextView tv_order_operate1;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View ll_NoticeDetails_Progress;
    @ViewById(R.id.cl_goods)
    CustomListView cl_goods;
    String order_id;
    NewHuiGdPayAdapter adapter;
    NewHuiOrderBean_StrStatus data;
    PopupWindow waitPop;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(Tag, "onCreateView...");
        view = inflater.inflate(R.layout.frag_hlife_order_detail, null);

        return view;
    }
    @AfterViews
    void initView() {
        Log.e(Tag,"------HuiOrderDetailFrag-initView");
        adapter = new NewHuiGdPayAdapter(this);
        if (adapter==null){
            Log.e(Tag,"adapter null");

        }
        if (cl_goods==null){
            Log.e(Tag,"cl_goods  null");

        }
        cl_goods.setAdapter(adapter);
        order_id = getActivity().getIntent().getStringExtra(MenuLeftSecondAct.INTENTTAG);
        ll_NoticeDetails_Progress.setVisibility(View.VISIBLE);
        HTTPHelper.getSuppOrderDetail2(mIbpiOrder, order_id);
//        HTTPHelper.getSuppOrderDetail(mIbpiOrder, order_id);
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
            NewHuiOrderBean_StrStatus bean = (NewHuiOrderBean_StrStatus) message;
//            HuiOrderBean bean = (HuiOrderBean) message;
            updateData(bean);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiOrder2(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            ll_NoticeDetails_Progress.setVisibility(View.GONE);
        }
    };
    HuiOrderBean bean;
    /**
     * 更新数据
     *
     * @param bean
     */
    public void updateData(NewHuiOrderBean_StrStatus bean) {
        data = bean;
        if (bean.getStatus().equals("待付款")){
            bean.setStatus_id(0);
        }else if (bean.getStatus().equals("待收货")){
            bean.setStatus_id(1);
        }
        else if (bean.getStatus().equals("交易成功")){
            bean.setStatus_id(3);
        } else if (bean.getStatus().equals("订单过期")){
            bean.setStatus_id(4);
        }
        adapter.setData(bean.getList());
        tv_order_id.setText("订单号：" + bean.getOrder_num());
        tv_order_time.setText("下单时间：" + TimeUtil.getDayAllTime(bean.getCreate_time()));

        if (bean.getPay_time()!=0){
            tv_pay_time.setVisibility(View.VISIBLE);
            tv_pay_time.setText("支付时间：" + TimeUtil.getDayAllTime(bean.getPay_time()));
        }else{
            tv_pay_time.setVisibility(View.VISIBLE);

        }
        if (bean.getSend_time()!=0){
            tv_send_time.setVisibility(View.VISIBLE);
            tv_send_time.setText("发货时间：" + TimeUtil.getDayAllTime(bean.getSend_time()));
        }else{
            tv_send_time.setVisibility(View.VISIBLE);

        }
        if (bean.getFinish_time()!=0){
            tv_receive_time.setVisibility(View.VISIBLE);
            tv_receive_time.setText("收货时间：" + TimeUtil.getDayAllTime(bean.getFinish_time()));
        }else{
            tv_receive_time.setVisibility(View.VISIBLE);

        }
        if (bean.getComment_time()!=0){
            tv_comment_time.setVisibility(View.VISIBLE);
            tv_comment_time.setText("评论时间：" + TimeUtil.getDayAllTime(bean.getComment_time()));
        }else{
            tv_comment_time.setVisibility(View.VISIBLE);
        }
        tv_reserve_name.setText(bean.getConsign());
        tv_reserve_phone.setText(bean.getTel());
        tv_reserve_address.setText(bean.getAddress());
        tv_coupon.setText(bean.getTicket_val() + "");
        tv_reserve_wallet.setText(bean.getZero_money() + "");
        if (bean.getPay_type()==1){
            paytype_layout.setVisibility(View.VISIBLE);
            tv_reserve_paytype.setText("微信支付");
        }else if (bean.getPay_type()==1){
            paytype_layout.setVisibility(View.VISIBLE);
            tv_reserve_paytype.setText("支付宝支付");
        }else{
            paytype_layout.setVisibility(View.GONE);
        }
        if (bean.getMark()!=null){
            mark_layout.setVisibility(View.VISIBLE);
            tv_reserve_mark.setText( bean.getMark());
        }else{
            mark_layout.setVisibility(View.VISIBLE);
        }
        if (bean.getMerchant()!=null){
            tv_order_merchant.setVisibility(View.VISIBLE);
            tv_order_merchant.setText( bean.getMerchant());
        }else{
            tv_order_merchant.setVisibility(View.VISIBLE);
        }
        tv_total_pay.setText("￥" + bean.getOld_fee() + "");
        tv_price.setText("￥" + CommonUtils.f2Bi(bean.getTotal_fee()));
        if (bean.getStatus_id() == 0) {
            tv_order_operate1.setText("取消订单");
            tv_order_operate2.setText("付款");
            tv_order_operate1.setVisibility(View.VISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(v, "取消订单");
                }
            });
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
//                    mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_PAY);
//                    mIntent.putExtra(PaymentActivity.INTENTTAG, data.getId() + "");
//                    startActivity(mIntent);
                    Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(HuiLifeSecondAct.class));
                    mIntent.putExtra(HuiLifeSecondAct.ACTIVITYTAG, Constacts.NEW_HUILIFE_ORDER);
                    mIntent.putExtra("order_num", data.getOrder_num() + "");
                    mIntent.putExtra("isFromOrder", true);
                    mIntent.putExtra(HuiLifeSecondAct.INTENTTAG, 1);
                    //TODO 这里要改
                    getActivity().startActivity(mIntent);
                }
            });
        } else if (bean.getStatus_id() == 1 || bean.getStatus_id() == 2) {
            tv_order_operate2.setText("确认收货");
            tv_order_operate1.setVisibility(View.INVISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reciveOrder(v);
                }
            });
        } else if (bean.getStatus_id() == 3) {
            tv_order_operate2.setText("评价");
            tv_order_operate1.setVisibility(View.INVISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                   Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftThirdAct.class));
                    mIntent.putExtra(MenuLeftThirdAct.ACTIVITYTAG, Constacts.MENU_LEFTTHIRD_ORDER_COMMENT);
                    mIntent.putExtra(MenuLeftThirdAct.INTENTTAG, data);
                    startActivity(mIntent);
                }
            });
        } else if (bean.getStatus_id() == -1) {
            tv_order_operate2.setText("取消订单");
            tv_order_operate1.setVisibility(View.INVISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    cancelOrder(v, "取消订单");
                }
            });
        } else if (bean.getStatus_id() == 4) {
            tv_order_operate2.setText("删除订单");
            tv_order_operate1.setVisibility(View.INVISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(v, "删除订单");
                }
            });
        }
    }
    private PopupWindow mWatingWindow;

    /**
     * 取消订单
     *
     * @param v
     */
    public void cancelOrder(final View v, String msg) {
        ECAlertDialog dialog = ECAlertDialog.buildAlert(getActivity(), "你确定" + msg + "么？", "确定", "取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), v, Gravity.CENTER);
                HTTPHelper.cancelOrder2(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast("成功取消", 0);
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
                        waitPop.dismiss();
                    }
                }, data.getId() + "");
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /**
     * 确认收货
     *
     * @param v
     */
    public void reciveOrder(final View v) {
        ECAlertDialog dialog = ECAlertDialog.buildAlert(getActivity(), "你确认收货么？", "确定", "取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), v, Gravity.CENTER);
                HTTPHelper.reciveOrder(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast("成功收货", 0);
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
                        waitPop.dismiss();
                    }
                }, data.getId() + "");
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

}
