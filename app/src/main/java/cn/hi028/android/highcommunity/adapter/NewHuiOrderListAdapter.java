/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.adapter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.activity.fragment.NewHuiOrderFrag;
import cn.hi028.android.highcommunity.bean.NewHuiOrderBean;
import cn.hi028.android.highcommunity.params.NewHuiSuppGdParams;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：v2.0惠生活订单<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：206/12/19<br>
 */
public class NewHuiOrderListAdapter extends BaseExpandableListAdapter {
    NewHuiOrderFrag frag;
    private List<NewHuiOrderBean> mList = new ArrayList<NewHuiOrderBean>();

    int flagTp;

    public NewHuiOrderListAdapter(NewHuiOrderFrag frag, int flagTp) {
        this.frag = frag;
        this.flagTp = flagTp;
    }

    @Override
    public int getGroupCount() {
        return ListUtils.getSize(mList);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ListUtils.getSize(mList.get(groupPosition).getList());
//        return data.get(groupPosition).getList().size();
//        return groupPosition;
    }

    @Override
    public NewHuiOrderBean getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public NewHuiSuppGdParams getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getList().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewParentHolder holder;
        if (convertView == null) {
            holder = new ViewParentHolder();
            convertView = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_mar_order_parent, null);
            holder.tv_merchant = (TextView) convertView.findViewById(R.id.tv_merchant);
            holder.tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewParentHolder) convertView.getTag();
        }
        NewHuiOrderBean newHuiOrderBean = mList.get(groupPosition);

//        holder.tv_orderId.setText("订单号：" + newHuiOrderBean.getOrder_num());
        holder.tv_merchant.setText(newHuiOrderBean.getMerchant());
        if (newHuiOrderBean.getStatus() == 0) {
            holder.tv_order_state.setText("待付款");
        } else if (newHuiOrderBean.getStatus() == 1 || newHuiOrderBean.getStatus() == 2) {
            holder.tv_order_state.setText("待收货");
        } else if (newHuiOrderBean.getStatus() == 3) {
            holder.tv_order_state.setText("待评价");
        } else if (newHuiOrderBean.getStatus() == -1) {
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
            cView = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_huilife_order, null);
            holder.img_goods_pic = (ImageView) cView.findViewById(R.id.img_goods_pic);
            holder.tv_goods_name = (TextView) cView.findViewById(R.id.tv_goods_name);
            holder.tv_goods_standard = (TextView) cView.findViewById(R.id.tv_goods_standard);
            holder.tv_goods_price = (TextView) cView.findViewById(R.id.tv_goods_price);
            holder.tv_goods_num = (TextView) cView.findViewById(R.id.tv_goods_num);
            holder.tv_goods_total = (TextView) cView.findViewById(R.id.tv_goods_total);
            view = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_mar_order_footer, null);

            holder.tv_orderId = (TextView) view.findViewById(R.id.tv_orderId);

            holder.tv_goods_total_num = (TextView) view.findViewById(R.id.tv_goods_total_num);
            holder.tv_total = (TextView) view.findViewById(R.id.tv_goods_total);
            holder.tv_goods_realpay_price = (TextView) view.findViewById(R.id.tv_goods_realpay_price);
            holder.tv_order_operate1 = (TextView) view.findViewById(R.id.tv_order_operate1);
            holder.tv_order_operate2 = (TextView) view.findViewById(R.id.tv_order_operate2);
        }
        holder.tv_goods_name.setText(mList.get(groupPosition).getList().get(childPosition).getName());
        holder.tv_goods_standard.setText(mList.get(groupPosition).getList().get(childPosition).getStandard_name());
        holder.tv_goods_price.setText("￥" + mList.get(groupPosition).getList().get(childPosition).getGoods_price());
        holder.tv_goods_num.setText("x" + mList.get(groupPosition).getList().get(childPosition).getGoods_number());
        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mList.get(groupPosition).getList().get(childPosition)
                .getPic(), holder.img_goods_pic);
        LinearLayout lin = new LinearLayout(frag.getActivity());
        lin.setOrientation(LinearLayout.VERTICAL);
        lin.removeAllViews();
        lin.addView(cView);
        if (ListUtils.getSize(mList.get(groupPosition).getList()) == childPosition + 1) {
            lin.addView(view);
            View vLine = new View(frag.getActivity());
            vLine.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            vLine.setBackgroundResource(R.color.defult_color_white);
            lin.addView(vLine);
        }
        holder.tv_goods_total.setText("小计：￥" + mList.get(groupPosition).getList().get(childPosition).getGoods_price()*mList.get(groupPosition).getList().get(childPosition).getGoods_number());
        holder.tv_goods_total_num.setText("共" + mList.get(groupPosition).getCount() + "件商品");
        holder.tv_total.setText("合计：￥" + mList.get(groupPosition).getOld_fee());

        if (mList.get(groupPosition).getStatus()!=-1&&mList.get(groupPosition).getStatus()!=0&&mList.get(groupPosition).getTotal_fee()!=0){
            holder.tv_goods_realpay_price.setVisibility(View.VISIBLE);
            holder.tv_total.setText("合计：￥" + mList.get(groupPosition).getOld_fee());
            String str = "实付：￥" + mList.get(groupPosition).getTotal_fee();
            SpannableString ss = new SpannableString(str);
            ss.setSpan(new ForegroundColorSpan(0x666666), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.RED), 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_goods_realpay_price.setText(ss);
        }else{
            holder.tv_goods_realpay_price.setVisibility(View.GONE);
        }
        holder.tv_orderId.setText("订单号：" + mList.get(groupPosition).getOrder_num());
        if (mList.get(groupPosition).getStatus() == 0) {
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
//                    Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
//                    mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_PAY);
//                    mIntent.putExtra(PaymentActivity.INTENTTAG, mList.get(groupPosition).getId() + "");
//                    frag.startActivity(mIntent);
                    Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(HuiLifeSecondAct.class));
                    mIntent.putExtra(HuiLifeSecondAct.ACTIVITYTAG, Constacts.NEW_HUILIFE_ORDER);
                    mIntent.putExtra("order_num", mList.get(groupPosition).getOrder_num() + "");
                    mIntent.putExtra("isFromOrder", true);
                    mIntent.putExtra(HuiLifeSecondAct.INTENTTAG, 1);
                    //TODO 这里要改
                    frag.startActivity(mIntent);


                }
            });
        } else if (mList.get(groupPosition).getStatus() == 1 || mList.get(groupPosition).getStatus() == 2) {
            holder.tv_order_operate2.setText("确认收货");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reciveOrder(v, groupPosition);
                }
            });
        } else if (mList.get(groupPosition).getStatus() == 3) {
            holder.tv_order_operate2.setText("评价");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(frag.getActivity(), GeneratedClassUtils.get(MenuLeftThirdAct.class));
                    mIntent.putExtra(MenuLeftThirdAct.ACTIVITYTAG, Constacts.MENU_LEFTTHIRD_ORDER_COMMENT);
                    mIntent.putExtra(MenuLeftThirdAct.INTENTTAG, mList.get(groupPosition));
                    frag.startActivity(mIntent);
                }
            });
        } else if (mList.get(groupPosition).getStatus() == -1) {
            holder.tv_order_operate2.setText("取消订单");
            holder.tv_order_operate1.setVisibility(View.INVISIBLE);
            holder.tv_order_operate2.setVisibility(View.VISIBLE);
            holder.tv_order_operate2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    cancelOrder(v, groupPosition);
                }
            });
        } else if (mList.get(groupPosition).getStatus() == 4) {
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
                mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, mList.get(groupPosition).getId() + "");
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
                HTTPHelper.cancelOrder2(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        mList.remove(groupPosition);
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
                }, mList.get(groupPosition).getId() + "");
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
                        mList.get(groupPosition).setStatus(3);
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
                }, mList.get(groupPosition).getId() + "");
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

    public List<NewHuiOrderBean> getData() {
        return mList;
    }

    public void setData(List<NewHuiOrderBean> data) {
        if (data != null) {

            this.mList.clear();
            this.mList = data;
//            this.data.addAll(data);
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
        TextView tv_goods_name,tv_goods_standard, tv_goods_price, tv_goods_num, tv_goods_total,
                tv_goods_total_num, tv_total,tv_goods_realpay_price, tv_order_operate1, tv_orderId, tv_order_operate2;
    }

    class ViewParentHolder {
        TextView tv_order_state, tv_merchant;
    }

    PopupWindow waitPop;
}
