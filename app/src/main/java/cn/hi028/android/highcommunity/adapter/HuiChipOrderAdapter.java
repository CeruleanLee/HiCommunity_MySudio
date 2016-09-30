/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsOrderFrag;
import cn.hi028.android.highcommunity.bean.ChipsOrderBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import cn.hi028.android.highcommunity.view.ECListDialog;

/**
 * @功能：我的众筹<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-29<br>
 */
public class HuiChipOrderAdapter extends BaseAdapter {
    HuiChipsOrderFrag frag;
    String[] canCelOrder = {"取消订单", "要收货", "删除订单"};
    PopupWindow waitPop;

    public List<ChipsOrderBean> getData() {
        return data;
    }

    public void setData(List<ChipsOrderBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    List<ChipsOrderBean> data = new ArrayList<ChipsOrderBean>();
    int flagTp;

    public HuiChipOrderAdapter(HuiChipsOrderFrag frag, int flagTp) {
        this.frag = frag;
        this.flagTp = flagTp;
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public Object getItem(int position) {
        return null;//data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_chips_order, null);
            viewHolder = new ViewHolder();
            viewHolder.img_goods_pic = (ImageView) convertView
                    .findViewById(R.id.img_goods_pic);
            viewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            viewHolder.tv_goods_join_pri = (TextView) convertView
                    .findViewById(R.id.tv_goods_join_pri);
            viewHolder.tv_goods_price = (TextView) convertView
                    .findViewById(R.id.tv_goods_price);
            viewHolder.tv_orderId = (TextView) convertView
                    .findViewById(R.id.tv_orderId);
            viewHolder.tv_order_state = (TextView) convertView
                    .findViewById(R.id.tv_order_state);

            viewHolder.tv_order_operate1 = (TextView) convertView
                    .findViewById(R.id.tv_order_operate1);
            viewHolder.tv_order_operate2 = (TextView) convertView
                    .findViewById(R.id.tv_order_operate2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_order_operate1.setText(canCelOrder[flagTp]);
        viewHolder.tv_goods_name.setText(data.get(position).getName());
        viewHolder.tv_goods_join_pri.setText("参与价￥" + data.get(position).getJoin_price());
        viewHolder.tv_goods_price.setText("￥" + data.get(position).getPrice() + "元");
        viewHolder.tv_orderId.setText("订单号：" + data.get(position).getOrder_num());
        if (flagTp == 0) {
            if (data.get(position).getState() == 1) {
                viewHolder.tv_order_state.setText("众筹未结束");
                viewHolder.tv_order_operate2.setSelected(false);
                viewHolder.tv_order_operate2.setClickable(false);
            } else if (data.get(position).getState() == 2) {
                viewHolder.tv_order_state.setText("众筹结束等待付款");
                viewHolder.tv_order_operate2.setSelected(true);
                viewHolder.tv_order_operate2.setClickable(true);
            } else if (data.get(position).getState() == 3) {
                viewHolder.tv_order_state.setText("付款超时");
                viewHolder.tv_order_operate2.setSelected(false);
                viewHolder.tv_order_operate2.setClickable(false);
            }
            viewHolder.tv_order_operate2.setText("付款");
            viewHolder.tv_order_operate1.setSelected(true);
            viewHolder.tv_order_operate1.setVisibility(View.VISIBLE);
            viewHolder.tv_order_operate2.setVisibility(View.VISIBLE);
            viewHolder.tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getState() == 2) {
                        Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
                        mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_CHIPS_ORDER_PAY);
                        mIntent.putExtra(PaymentActivity.INTENTTAG, data.get(position).getOrder_id());
                        frag.startActivity(mIntent);
                    } else if (data.get(position).getState() == 1) {
                        HighCommunityUtils.GetInstantiation().ShowToast("众筹未结束，不能付款", 0);
                    } else if (data.get(position).getState() == 1) {
                        HighCommunityUtils.GetInstantiation().ShowToast("付款时间结束，不能付款", 0);
                    }
                }
            });
        } else if (flagTp == 1) {

            viewHolder.tv_order_operate2.setSelected(true);
            viewHolder.tv_order_operate2.setClickable(true);
            viewHolder.tv_order_operate1.setText("删除订单");
            viewHolder.tv_order_operate1.setVisibility(View.GONE);
            viewHolder.tv_order_operate2.setVisibility(View.VISIBLE);
            viewHolder.tv_order_operate2.setText("确认收货");
            viewHolder.tv_order_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ECAlertDialog dialog = ECListDialog.buildAlert(frag.getActivity(), "你确定" + canCelOrder[flagTp] + "么？", "确定", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(frag.getActivity(), v, Gravity.CENTER);
                            HTTPHelper.reciveChipsOrder(new BpiHttpHandler.IBpiHttpHandler() {
                                @Override
                                public void onError(int id, String message) {
                                    waitPop.dismiss();
                                    HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                                }

                                @Override
                                public void onSuccess(Object message) {
                                    waitPop.dismiss();
                                    data.remove(position);
                                    HighCommunityUtils.GetInstantiation().ShowToast("你已成功确认收货", 0);
                                    notifyDataSetChanged();
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
                                    waitPop.dismiss();
                                }
                            }, data.get(position).getOrder_num() + "");
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });
            viewHolder.tv_order_state.setText("待收货");
        } else if (flagTp == 2) {
            viewHolder.tv_order_operate1.setSelected(true);
            viewHolder.tv_order_operate1.setClickable(true);
            viewHolder.tv_order_operate2.setSelected(false);
            viewHolder.tv_order_operate2.setClickable(false);
            viewHolder.tv_order_operate1.setText("删除订单");
            viewHolder.tv_order_operate2.setText("付款");
            viewHolder.tv_order_operate1.setVisibility(View.VISIBLE);
            viewHolder.tv_order_operate2.setVisibility(View.VISIBLE);
            viewHolder.tv_order_state.setText("已完成");
        }

        viewHolder.tv_order_operate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ECAlertDialog dialog = ECListDialog.buildAlert(frag.getActivity(), "你确定" + canCelOrder[flagTp] + "么？", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(frag.getActivity(), v, Gravity.CENTER);
                        HTTPHelper.cancelChipsOrder(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                waitPop.dismiss();
                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                            }

                            @Override
                            public void onSuccess(Object message) {
                                waitPop.dismiss();
                                data.remove(position);
                                notifyDataSetChanged();
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
                                waitPop.dismiss();
                            }
                        }, data.get(position).getOrder_id() + "");
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + data.get(position).getCover_pic(), viewHolder.img_goods_pic, R.mipmap.default_no_pic, null);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
                mIntent.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_CHIP_ORDER_DETAIL);
                LogUtil.d("~~~众筹订单全list adapter中点击item传递的订单号："+data.get(position).getOrder_id());
                mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, data.get(position).getOrder_id() + "");
//                mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, data.get(position).getOrder_num());
                frag.startActivity(mIntent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView img_goods_pic;
        TextView tv_goods_name;
        TextView tv_orderId;
        TextView tv_order_state;
        TextView tv_goods_join_pri;
        TextView tv_goods_price;
        TextView tv_order_operate2;
        TextView tv_order_operate1;
    }
}
