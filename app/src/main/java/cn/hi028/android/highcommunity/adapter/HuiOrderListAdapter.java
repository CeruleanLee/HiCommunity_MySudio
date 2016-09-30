/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.adapter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.activity.fragment.HuiOrderFrag;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：惠生活订单<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015年10月9日<br>
 */
public class HuiOrderListAdapter extends BaseExpandableListAdapter {
    /**
     *
     */
    HuiOrderFrag frag;
    private List<HuiOrderBean> data = new ArrayList<HuiOrderBean>();
    int flagTp;

    public HuiOrderListAdapter(HuiOrderFrag frag, int flagTp) {
        this.frag = frag;
        this.flagTp = flagTp;
    }

    @Override
    public int getGroupCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ListUtils.getSize(data.get(groupPosition).getGoods());
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewParentHolder holder;
        if (convertView == null) {
            holder = new ViewParentHolder();
            convertView = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_mar_order_parent, null);
            holder.tv_orderId = (TextView) convertView
                    .findViewById(R.id.tv_orderId);
            holder.tv_order_state = (TextView) convertView
                    .findViewById(R.id.tv_order_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewParentHolder) convertView.getTag();
        }
        holder.tv_orderId.setText("订单号："
                + data.get(groupPosition).getOrder_num());
        if (data.get(groupPosition).getStatus() == 0) {
            holder.tv_order_state.setText("待付款");
        } else if (data.get(groupPosition).getStatus() == 1 || data.get(groupPosition).getStatus() == 2) {
            holder.tv_order_state.setText("待收货");
        } else if (data.get(groupPosition).getStatus() == 3) {
            holder.tv_order_state.setText("待评价");
        } else if (data.get(groupPosition).getStatus() == -1) {
            holder.tv_order_state.setText("订单过期");
        } else {
            holder.tv_order_state.setText("交易成功");
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewChildHolder holder = null;
        View cView = null;
        View view = null;
        if (cView == null) {
            holder = new ViewChildHolder();
            cView = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_huilife_order, null);
            holder.img_goods_pic = (ImageView) cView
                    .findViewById(R.id.img_goods_pic);
            holder.tv_goods_name = (TextView) cView
                    .findViewById(R.id.tv_goods_name);
            holder.tv_goods_price = (TextView) cView
                    .findViewById(R.id.tv_goods_price);
            holder.tv_goods_num = (TextView) cView
                    .findViewById(R.id.tv_goods_num);
            holder.tv_goods_total = (TextView) cView
                    .findViewById(R.id.tv_goods_total);
            view = LayoutInflater.from(frag.getActivity()).inflate(
                    R.layout.adapter_mar_order_footer, null);
            holder.tv_goods_total_num = (TextView) view
                    .findViewById(R.id.tv_goods_total_num);
            holder.tv_total = (TextView) view
                    .findViewById(R.id.tv_goods_total);
            holder.tv_order_operate1 = (TextView) view
                    .findViewById(R.id.tv_order_operate1);
            holder.tv_order_operate2 = (TextView) view
                    .findViewById(R.id.tv_order_operate2);
        }
        holder.tv_goods_name.setText(data.get(groupPosition).getGoods()
                .get(childPosition).getGoods_name());
        holder.tv_goods_price.setText("￥"
                + data.get(groupPosition).getGoods().get(childPosition).getGoods_price());
        holder.tv_goods_num.setText("x" + data.get(groupPosition).getGoods().get(childPosition).getNumber());
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP +
                data.get(groupPosition).getGoods().get(childPosition)
                        .getPic().get(0), holder.img_goods_pic);
        LinearLayout lin = new LinearLayout(frag.getActivity());
        lin.setOrientation(LinearLayout.VERTICAL);
        lin.removeAllViews();
        lin.addView(cView);
        if (ListUtils.getSize(data.get(groupPosition).getGoods()) == childPosition + 1) {
            lin.addView(view);
            View vLine = new View(frag.getActivity());
            vLine.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            vLine.setBackgroundResource(R.color.defult_color_white);
            lin.addView(vLine);
        }
        holder.tv_goods_total.setText("小计：￥" + data.get(groupPosition).getGoods().get(childPosition).getGoods_total_price());
        holder.tv_goods_total_num.setText("共" + data.get(groupPosition).getSum() + "件商品");
        holder.tv_total.setText("合计：￥" + data.get(groupPosition).getReal_price());
        if (data.get(groupPosition).getStatus() == 0) {
            holder.tv_order_operate1.setText("取消订单");
            holder.tv_order_operate2.setText("付款");
            holder.tv_order_operate1.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(v, groupPosition);
                }
            });
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
                    mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_PAY);
                    mIntent.putExtra(PaymentActivity.INTENTTAG, data.get(groupPosition).getId() + "");
                    frag.startActivity(mIntent);
                }
            });
        } else if (data.get(groupPosition).getStatus() == 1 || data.get(groupPosition).getStatus() == 2) {
            holder.tv_order_operate2.setText("确认收货");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reciveOrder(v, groupPosition);
                }
            });
        } else if (data.get(groupPosition).getStatus() == 3) {
            holder.tv_order_operate2.setText("评价");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(MenuLeftThirdAct.class));
                    mIntent.putExtra(MenuLeftThirdAct.ACTIVITYTAG, Constacts.MENU_LEFTTHIRD_ORDER_COMMENT);
                    mIntent.putExtra(MenuLeftThirdAct.INTENTTAG, data.get(groupPosition));
                    frag.startActivity(mIntent);
                }
            });
        } else if (data.get(groupPosition).getStatus() == -1) {
            holder.tv_order_operate2.setText("取消订单");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    cancelOrder(v, groupPosition);
                }
            });
        } else if (data.get(groupPosition).getStatus() == 4) {
            holder.tv_order_operate2.setText("删除订单");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(v, groupPosition);
                }
            });
        }
        lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
                mIntent.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_ORDER_DETAIL);
                mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, data.get(groupPosition).getId() + "");
                frag.startActivity(mIntent);
            }
        });
        return lin;
    }

    /**
     * 取消订单
     *
     * @param v
     * @param groupPosition
     */
    public void cancelOrder(final View v, final int groupPosition) {
        ECAlertDialog dialog = ECAlertDialog.buildAlert(frag.getActivity(), "你确定取消订单么？", "确定", "取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(frag.getActivity(), v, Gravity.CENTER);
                HTTPHelper.cancelOrder(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        data.remove(groupPosition);
                        HighCommunityUtils.GetInstantiation().ShowToast("成功取消", 0);
                        notifyDataSetChanged();
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
                }, data.get(groupPosition).getId() + "");
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
     * @param groupPosition
     */
    public void reciveOrder(final View v, final int groupPosition) {
        ECAlertDialog dialog = ECAlertDialog.buildAlert(frag.getActivity(), "你确认收货么？", "确定", "取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(frag.getActivity(), v, Gravity.CENTER);
                HTTPHelper.reciveOrder(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        data.get(groupPosition).setStatus(3);
                        notifyDataSetChanged();
                        HighCommunityUtils.GetInstantiation().ShowToast("成功收货", 0);
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
                }, data.get(groupPosition).getId() + "");
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public List<HuiOrderBean> getData() {
        return data;
    }

    public void setData(List<HuiOrderBean> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
        }
        notifyDataSetChanged();

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (int i = 0, count = getGroupCount(); i < count; i++) {
            frag.viewList.get(flagTp).expandGroup(i);
        }
    }

    class ViewChildHolder {
        ImageView img_goods_pic;
        TextView tv_goods_name, tv_goods_price, tv_goods_num, tv_goods_total,
                tv_goods_total_num, tv_total, tv_order_operate1, tv_order_operate2;
    }

    class ViewParentHolder {
        TextView tv_orderId, tv_order_state;
    }

    PopupWindow waitPop;
}
