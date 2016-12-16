/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.util.LogUtil;
import net.duohuo.dhroid.view.CustomListView;

import org.androidannotations.annotations.Click;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressAct;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.adapter.NewHuiBuyAdapter;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyCarlistBean;
import cn.hi028.android.highcommunity.bean.GoodsOrderBean;
import cn.hi028.android.highcommunity.bean.HSuppGdDefBean;
import cn.hi028.android.highcommunity.bean.HuiSupportBean;
import cn.hi028.android.highcommunity.params.HuiSuppOrderParams;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：新版惠生活商品支付界面<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/14<br>
 */
public class NewHuiBuyFrag extends BaseFragment {
    static  final  String Tag="NewHuiBuyFrag--->";
    @Bind(R.id.cl_goods)
    CustomListView cl_goods;
    @Bind(R.id.tv_reserve_name)
    TextView tv_reserve_name;
    @Bind(R.id.tv_reserve_phone)
    TextView tv_reserve_phone;
    @Bind(R.id.tv_reserve_address)
    TextView tv_reserve_address;
    @Bind(R.id.tv_address_default)
    TextView tv_address_default;
    @Bind(R.id.tv_coupon)
    TextView tv_coupon;
    @Bind(R.id.tv_wallet)
    TextView tv_wallet;
    @Bind(R.id.edt_pay_num)
    EditText edt_pay_num;//零钱包输入金额
    @Bind(R.id.tv_total_actual)
    TextView tv_total_actual;
    @Bind(R.id.tv_total_pay)
    TextView tv_total_pay;
    @Bind(R.id.fl_huiLife_NoAddress)
    TextView mNoAddress;
    @Bind(R.id.fl_huiLife_addressChooice)
    FrameLayout fl_huiLife_addressChooice;
    @Bind(R.id.fl_yhq)
    FrameLayout fl_yhq;
    @Bind(R.id.btn_pay)
    Button btn_pay;
    @Bind(R.id.btn_order)
    Button btn_order;
    public static HuiSupportBean entyBean;
    public static List<NewSupplyCarlistBean.SupplyCarlistDataEntity> listData;
    int type = 0;
    public HuiSuppOrderParams orderParams;
    HSuppGdDefBean data;
    PopupWindow waitPop;
    NewHuiBuyAdapter adapter;
    View view;
String carIdList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_newhui_buy, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        carIdList = bundle.getString("carIdList");
        Log.d(Tag, "carIdList=" + carIdList);
        initView();
        return view;
    }
    public  void initView() {
        adapter = new NewHuiBuyAdapter(this);
        cl_goods.setAdapter(adapter);
        orderParams = new HuiSuppOrderParams();
        edt_pay_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isClickable()){
                    HighCommunityUtils.GetInstantiation().ShowToast("无可抵扣零钱", 0);
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
                        if (data != null) {
                            if (money > data.getZero_money()) {
                                edt_pay_num.setText(data.getZero_money() + "");
                            } else {
                                if (money > (orderParams.getTotal_price() - orderParams.getTicket_value())) {
                                    edt_pay_num.setText((orderParams.getTotal_price() - orderParams.getTicket_value()) + "");
                                    return;
                                }
                                orderParams.setZero_money(money);
                                updateOrder();
                            }
                        } else {
                            edt_pay_num.setText("");
                        }
                    } else {
                        edt_pay_num.setText((-money) + "");
                    }
                } else {
                    orderParams.setZero_money(0);
                    updateOrder();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        type = getActivity().getIntent().getIntExtra(HuiLifeSecondAct.INTENTTAG, 0);
        fl_huiLife_addressChooice.setVisibility(View.GONE);
        if (type == 0) {
            NewSupplyCarlistBean.SupplyCarlistDataEntity gdParams = new NewSupplyCarlistBean.SupplyCarlistDataEntity();
            gdParams.setId(entyBean.getGid()+"");
            gdParams.setStorage(entyBean.getNumber());
            gdParams.setName(entyBean.getGoods_name());
            gdParams.setCover_pic(entyBean.getPic().get(0).getSmall());
            gdParams.setPrice(entyBean.getPrice());
            gdParams.setNum(1);
            List<NewSupplyCarlistBean.SupplyCarlistDataEntity> dataGdParams = new ArrayList<NewSupplyCarlistBean.SupplyCarlistDataEntity>();
            dataGdParams.add(gdParams);
            adapter.setData(dataGdParams);
            HTTPHelper.GetHuiSuppGoodsMsg(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", entyBean.getGid() + "");
        } else {
            adapter.setData(listData);
            HTTPHelper.GetHuiSuppGoodsMsg(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", listData.get(0).getId() + "");
        }
    }

    @Click(R.id.fl_yhq)
    public void onTicket() {
        Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(TicketAct.class));
        mIntent.putExtra(TicketAct.TICKET_TYPE, 2);
        mIntent.putExtra(TicketAct.TICKET_PRICE, orderParams.getTotal_price() + "");
        startActivityForResult(mIntent, 0x22);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 獲取訂單內容
     */
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            HSuppGdDefBean data = (HSuppGdDefBean) message;
            updateData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHuiSuppGoodsMsg(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
        }
    };

    @Click(R.id.btn_pay)
    void payClick() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            if (orderParams.getNum() == 0) {
                HighCommunityUtils.GetInstantiation().ShowToast("商品总数不能为零", 0);
                return;
            }

            if (orderParams.getReal_price() == 0) {
                HighCommunityUtils.GetInstantiation().ShowToast("实际支付不能为零", 0);
                return;
            }
            if (TextUtils.isEmpty(orderParams.getAid())) {
                HighCommunityUtils.GetInstantiation().ShowToast("请选择收货地址", 0);
                return;
            }
            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
            JSONObject params = new JSONObject();
            try {
                params.put("user_id", HighCommunityApplication.mUserInfo.getId() + "");
                params.put("aid", orderParams.getAid());
                params.put("real_price", orderParams.getReal_price());
                params.put("total_price", orderParams.getTotal_price());
                if (orderParams.getZero_money() != 0.0f) {
                    params.put("zero_money", orderParams.getZero_money());
                }
                if (!TextUtils.isEmpty(orderParams.getTicket_id())) {
                    params.put("ticket_id", orderParams.getTicket_id());
                }
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < ListUtils.getSize(orderParams.getGoods()); i++) {
                    JSONObject goods = new JSONObject();
                    goods.put("goods_id", orderParams.getGoods().get(i).getGid());
                    goods.put("goods_price", orderParams.getGoods().get(i).getPrice());
                    goods.put("number", orderParams.getGoods().get(i).getNum());
                    jsonArray.put(goods);
                }
                params.put("goods", jsonArray);
            	LogUtil.d("------a1");
                HTTPHelper.submitHuiSuppOrder(mIbpiOrder, params.toString());
                LogUtil.d("------a2");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            waitPop.dismiss();
            if (null == message)
                return;
            GoodsOrderBean bean = (GoodsOrderBean) message;
            Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
            mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_PAY);
            mIntent.putExtra(PaymentActivity.INTENTTAG, bean.getOrder_id());
            startActivity(mIntent);
            getActivity().finish();
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
    };

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	LogUtil.d("------onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0x21 && resultCode == 0x22) {
            AddressBean address = (AddressBean) data.getSerializableExtra("address");
            orderParams.setAid(address.getId());
//            orderParams.setAid(address.getId());
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
        if (data != null && requestCode == 0x22 && resultCode == TicketAct.TICKET_RESULT) {
            AllTicketBean aticketBean = (AllTicketBean) data.getSerializableExtra("ticket");
            orderParams.setTicket_id(aticketBean.getTid());
            orderParams.setTicket_value(aticketBean.getTicket_value());
            float realToatal = orderParams.getTotal_price() - orderParams.getTicket_value();
            if (realToatal < orderParams.getZero_money()) {
                edt_pay_num.setText("");
            }
            tv_coupon.setText("￥" + orderParams.getTicket_value() + "");
            updateOrder();
        }
    }


    public void updateData(HSuppGdDefBean data) {
        this.data = data;
        orderParams.setZero_money(0.0f);
        orderParams.setUid(HighCommunityApplication.mUserInfo.getId());
        if (data.getDefault_address() != null && data.getDefault_address().getId() != null) {
            fl_huiLife_addressChooice.setVisibility(View.VISIBLE);
            orderParams.setAid(data.getDefault_address().getId());
            tv_reserve_name.setText(data.getDefault_address().getReal_name());
            tv_reserve_phone.setText(data.getDefault_address().getTel());
            tv_reserve_address.setText(data.getDefault_address().getAddress());
            if ("0".equals(data.getDefault_address().getIsDefault())) {
                tv_address_default.setVisibility(View.GONE);
            } else {
                tv_address_default.setVisibility(View.VISIBLE);
            }
            mNoAddress.setVisibility(View.GONE);
        } else {
            mNoAddress.setVisibility(View.VISIBLE);
            fl_huiLife_addressChooice.setVisibility(View.GONE);
        }
        tv_wallet.setText("零钱包（" + data.getZero_money() + "元）");
        if (data.getZero_money()<0.1){
            edt_pay_num.setClickable(false);
            edt_pay_num.setFocusable(false);
        }else {

            edt_pay_num.setFocusable(true);
            edt_pay_num.setClickable(true);

        }
        updateOrder();
    }

    public void updateOrder() {
        tv_total_pay.setText("合计金额￥" + CommonUtils.f2Bi(orderParams.getTotal_price()) + "");
        tv_total_actual.setText("￥" + CommonUtils.f2Bi(orderParams.getReal_price()) + "");

        Log.e(Tag,"orderParams.getTotal_price()--"+orderParams.getTotal_price()+",tv_coupon.toString()--"+tv_coupon.getText().toString()
                +",orderParams.getTicket_value()--"+orderParams.getTicket_value());

        if (orderParams.getTotal_price()<100&&tv_coupon.getText().toString().contains("￥")){
            tv_coupon.setText( " - ");
            tv_total_actual.setText("￥" + CommonUtils.f2Bi(orderParams.getReal_price()+orderParams.getTicket_value()) + "");
            orderParams.setTicket_value(0.0f);

//            tv_coupon.setText("￥" + orderParams.getTicket_value() + "");
        }

        if (orderParams.getTotal_price()<=0||orderParams.getReal_price()<=0){
if (orderParams.getTotal_price()<=0){
    tv_total_pay.setText("合计金额￥" + 0.0 + "");
    tv_total_actual.setText("￥" + 0.0 + "");
}
            btn_pay.setClickable(false);
            btn_pay.setBackgroundResource(R.color.Defult_Color_Grey);
        }else{
            btn_pay.setClickable(true);
            btn_pay.setBackgroundResource(R.color.Defult_Color_AppBotton);

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
