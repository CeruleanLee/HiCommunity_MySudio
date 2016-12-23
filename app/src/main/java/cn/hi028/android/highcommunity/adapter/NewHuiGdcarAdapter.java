/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;

import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.NewHuiGdCarFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyCarlistBean;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：新版购物车适配器<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/14<br>
 */
public class NewHuiGdcarAdapter extends BaseAdapter {
    static final  String Tag="NewHuiGdcarAdapter:";
    NewHuiGdCarFrag frag;
    private PopupWindow mWatingWindow;
    public NewHuiGdcarAdapter(NewHuiGdCarFrag frag) {
        this.frag = frag;

    }

    List<NewSupplyCarlistBean.SupplyCarlistDataEntity> data = new ArrayList<NewSupplyCarlistBean.SupplyCarlistDataEntity>();

    public List<NewSupplyCarlistBean.SupplyCarlistDataEntity> getData() {
        return data;
    }

    public void setData(List<NewSupplyCarlistBean.SupplyCarlistDataEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(data);
    }

    @Override
    public NewSupplyCarlistBean.SupplyCarlistDataEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
View view;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(frag.getActivity()).inflate(R.layout.adapter_huisupp_gdcar, null);
            view=convertView;
            viewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            viewHolder.tv_goods_standard = (TextView) convertView.findViewById(R.id.tv_goods_standard);
            viewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);
            viewHolder.tv_goods_num = (TextView) convertView.findViewById(R.id.tv_goods_num);
            viewHolder.tv_goods_add = (TextView) convertView.findViewById(R.id.tv_goods_add);
            viewHolder.tv_goods_reduce = (TextView) convertView.findViewById(R.id.tv_goods_reduce);
            viewHolder.tv_total_pay = (TextView) convertView.findViewById(R.id.tv_total_pay);
            viewHolder.img_goods_pic = (ImageView) convertView.findViewById(R.id.img_goods_pic);
            viewHolder.img_goods_ch = (ImageView) convertView.findViewById(R.id.img_goods_ch);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NewSupplyCarlistBean.SupplyCarlistDataEntity mBean = data.get(position);

        if (mBean.getCover_pic() == null || mBean.getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, viewHolder.img_goods_pic);
        } else {
            if (viewHolder.img_goods_pic != null) {
                BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getCover_pic(), viewHolder.img_goods_pic);
            } else {
                Log.d(Tag, "mViewHolder.img_goods_pic null");
            }
        }
//		ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getCover_pic(), viewHolder.img_goods_pic);
        viewHolder.tv_goods_name.setText(mBean.getName());
        viewHolder.tv_goods_price.setText(mBean.getPrice() + "");
//		viewHolder.tv_total_pay.setText("小计：￥"+CommonUtils.f2Bi(mBean.getSum());
        viewHolder.tv_total_pay.setText("小计：￥" + mBean.getSum());
        viewHolder.tv_goods_num.setText(mBean.getNum() + "");
        viewHolder.tv_goods_standard.setText(mBean.getStandard_name() + "");
        if (mBean.isCheck()) {
            viewHolder.img_goods_ch.setSelected(true);
        } else {
            viewHolder.img_goods_ch.setSelected(false);
        }
        viewHolder.img_goods_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBean.isCheck()) {
                    mBean.setCheck(false);
                } else {
                    mBean.setCheck(true);
                }
                notifyDataSetChanged();
            }
        });
        viewHolder.tv_goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBean.getNum() < mBean.getStorage()) {
                    mBean.setNum(mBean.getNum() + 1);
                    cartType = 1;
                    if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
                        HighCommunityUtils.GetInstantiation().ShowToast("请先登录再操作", 0);
                    } else {
                        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(frag.getActivity(), view, Gravity.CENTER);
                        HTTPHelper.changeGdCarNum(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                mWatingWindow.dismiss();
                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

                            }

                            @Override
                            public void onSuccess(Object message) {
                                mWatingWindow.dismiss();
Log.e(Tag,"加入购物车 onSuccess 返回数据："+message.toString());
//                                HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);

                            }

                            @Override
                            public Object onResolve(String result) {
                                return result;
                            }

                            @Override
                            public void setAsyncTask(AsyncTask asyncTask) {

                            }

                            @Override
                            public void cancleAsyncTask() {
                                mWatingWindow.dismiss();

                            }
                        }, mBean.getId(), cartType + "");
                    }
                    notifyDataSetChanged();
                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("数目不能超过库存", 0);
                }
            }
        });
        viewHolder.tv_goods_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBean.getNum() > 0) {
                    mBean.setNum(mBean.getNum() - 1);
                    cartType = 0;
                    if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
                        HighCommunityUtils.GetInstantiation().ShowToast("请先登录再操作", 0);
                    } else {
                        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(frag.getActivity(), view, Gravity.CENTER);

                        HTTPHelper.changeGdCarNum(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                mWatingWindow.dismiss();

                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

                            }

                            @Override
                            public void onSuccess(Object message) {
                                mWatingWindow.dismiss();
                                Log.e(Tag,"加入购物车 onSuccess 返回数据："+message.toString());
                            }
                            @Override
                            public Object onResolve(String result) {
                                return result;
                            }
                            @Override
                            public void setAsyncTask(AsyncTask asyncTask) {
                            }

                            @Override
                            public void cancleAsyncTask() {
                                mWatingWindow.dismiss();
                            }
                        }, mBean.getId(), cartType + "");
                    }
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    int cartType = 0;//类型(0=>减,1=>加)
    public float total_pri = 0.0f;
    public int total_num = 0;
    public int selectNum = 0;

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        boolean isCheck = true;
        total_pri = 0.0f;
        total_num = 0;
        selectNum = 0;
        for (int i = 0; i < ListUtils.getSize(getData()); i++) {
            if (getData().get(i).isCheck()) {
                selectNum = selectNum + 1;
                total_pri = total_pri + getData().get(i).getSum();
                total_num = total_num + getData().get(i).getNum();
            } else {
                isCheck = false;
            }

        }
        frag.tv_price.setText("￥" + CommonUtils.f2Bi(total_pri));
        if (frag.btn_pay.isSelected()) {
            frag.ll_price.setVisibility(View.INVISIBLE);
            frag.btn_pay.setText("删除（" + selectNum + "）");
        } else {
            frag.ll_price.setVisibility(View.VISIBLE);
            frag.btn_pay.setText("结算（" + total_num + "）");
        }
        frag.img_goods_ch.setSelected(isCheck);

    }
    public void ClearData() {
        data.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tv_goods_name, tv_goods_standard, tv_goods_price, tv_goods_num, tv_goods_add, tv_goods_reduce, tv_total_pay;
        ImageView img_goods_pic, img_goods_ch;
    }
}
