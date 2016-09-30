/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;
import net.duohuo.dhroid.view.CustomListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.adapter.HuiGdPayAdapter;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：订单详情<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-02-01<br>
 */
@EFragment(resName = "frag_hlife_order_detail")
public class HuiOrderDetailFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "HuiOrderDetailFrag";
    @ViewById(R.id.tv_reserve_name)
    TextView tv_reserve_name;
    @ViewById(R.id.tv_order_id)
    TextView tv_order_id;
    @ViewById(R.id.tv_order_time)
    TextView tv_order_time;

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
    @ViewById(R.id.tv_order_operate2)
    TextView tv_order_operate2;
    @ViewById(R.id.tv_order_operate1)
    TextView tv_order_operate1;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View ll_NoticeDetails_Progress;
    @ViewById(R.id.cl_goods)
    CustomListView cl_goods;
    String order_id;
    HuiGdPayAdapter adapter;
    HuiOrderBean data;
    PopupWindow waitPop;

    @AfterViews
    void initView() {
    	LogUtil.d("------HuiOrderDetailFrag-initView");
        adapter = new HuiGdPayAdapter(this);
        cl_goods.setAdapter(adapter);
        order_id = getActivity().getIntent().getStringExtra(MenuLeftSecondAct.INTENTTAG);
        ll_NoticeDetails_Progress.setVisibility(View.VISIBLE);
        HTTPHelper.getSuppOrderDetail(mIbpiOrder, order_id);
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
            HuiOrderBean bean = (HuiOrderBean) message;
            updateData(bean);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiOrder(result);
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
     * 更新数据
     *
     * @param bean
     */
    public void updateData(HuiOrderBean bean) {
        data = bean;
        adapter.setData(bean.getGoods());
        tv_order_id.setText("订单号：" + bean.getOrder_num());
        tv_order_time.setText("下单时间：" + TimeUtil.getDayAllTime(bean.getCreate_time()));
        tv_reserve_name.setText(bean.getReal_name());
        tv_reserve_phone.setText(bean.getTel());
        tv_reserve_address.setText(bean.getAddress());
        tv_coupon.setText(bean.getTicket_value() + "");
        tv_reserve_wallet.setText(bean.getZero_money() + "");
        tv_total_pay.setText("￥" + bean.getTotal_price() + "");
        tv_price.setText("￥" + CommonUtils.f2Bi(bean.getReal_price()));
        if (bean.getStatus() == 0) {
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
                    Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
                    mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_PAY);
                    mIntent.putExtra(PaymentActivity.INTENTTAG, data.getId() + "");
                    startActivity(mIntent);
                }
            });
        } else if (bean.getStatus() == 1 || bean.getStatus() == 2) {
            tv_order_operate2.setText("确认收货");
            tv_order_operate1.setVisibility(View.INVISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reciveOrder(v);
                }
            });
        } else if (bean.getStatus() == 3) {
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
        } else if (bean.getStatus() == -1) {
            tv_order_operate2.setText("取消订单");
            tv_order_operate1.setVisibility(View.INVISIBLE);
            tv_order_operate2.setVisibility(View.VISIBLE);
            tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    cancelOrder(v, "取消订单");
                }
            });
        } else if (bean.getStatus() == 4) {
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
                HTTPHelper.cancelOrder(new BpiHttpHandler.IBpiHttpHandler() {
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
