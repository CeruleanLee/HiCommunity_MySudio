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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.CustomListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressAct;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.adapter.NewHuiBuyAdapter;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyPaydetailBean;
import cn.hi028.android.highcommunity.bean.HSuppGdDefBean;
import cn.hi028.android.highcommunity.params.HuiSuppOrderParams2;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：新版惠生活商品支付界面<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/14<br>
 */
public class NewHuiBuyFrag extends BaseFragment {
    static final String Tag = "NewHuiBuyFrag--->";
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
    @Bind(R.id.fl_huiLife_Message)
    EditText et_Message;//订单留言
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
//    public static HuiSupportBean entyBean;
    //    public static List<NewSupplyCarlistBean.SupplyCarlistDataEntity> listData;
    int type = 0;
    public HuiSuppOrderParams2 orderParams;
    NewSupplyPaydetailBean.SupplyPayDataEntity.SupplyPayConsignEntity mConsign;//订单参数
//    HSuppGdDefBean data;
    PopupWindow waitPop;
    NewHuiBuyAdapter adapter;
    View view;
    String carIdList;
    List<NewSupplyPaydetailBean.SupplyPayDataEntity.SupplyPayGoodsEntity> mList = new ArrayList<NewSupplyPaydetailBean.SupplyPayDataEntity.SupplyPayGoodsEntity>();
    @Bind(R.id.rb_pay_wx)
    RadioButton rbPayWx;
    @Bind(R.id.rb_pay_ipay)
    RadioButton rbPayIpay;
    @Bind(R.id.rg_huil_ife)
    RadioGroup rgPay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_newhui_buy, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        carIdList = bundle.getString("carIdList");
        Log.d(Tag, "carIdList=" + carIdList);
//        initView();
        initData();
        return view;
    }

    public void initView() {
        edt_pay_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isClickable()) {
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
                        if (mBean != null) {
                            if (money > mBean.getZero_money()) {
                                edt_pay_num.setText(mBean.getZero_money() + "");
                            } else {
                                if (money > (orderParams.getTotal_amount() - orderParams.getTicket_value())) {
                                    edt_pay_num.setText((orderParams.getTotal_amount() - orderParams.getTicket_value()) + "");
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
//        if (type == 0) {
//            NewSupplyCarlistBean.SupplyCarlistDataEntity gdParams = new NewSupplyCarlistBean.SupplyCarlistDataEntity();
//            gdParams.setId(entyBean.getGid() + "");
//            gdParams.setStorage(entyBean.getNumber());
//            gdParams.setName(entyBean.getGoods_name());
//            gdParams.setCover_pic(entyBean.getPic().get(0).getSmall());
//            gdParams.setPrice(entyBean.getPrice());
//            gdParams.setNum(1);
//            List<NewSupplyCarlistBean.SupplyCarlistDataEntity> dataGdParams = new ArrayList<NewSupplyCarlistBean.SupplyCarlistDataEntity>();
//            dataGdParams.add(gdParams);
//            adapter.setData(dataGdParams);
//            HTTPHelper.GetHuiSuppGoodsMsg(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", entyBean.getGid() + "");
//        } else {
//            adapter.setData(listData);
//            HTTPHelper.GetHuiSuppGoodsMsg(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", listData.get(0).getId() + "");
//        }

        rbPayWx.setChecked(true);

    }

    @OnClick({R.id.fl_yhq, R.id.btn_pay, R.id.fl_huiLife_addressChooice, R.id.fl_huiLife_NoAddress,
            R.id.rb_pay_wx,R.id.rb_pay_ipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_yhq:
                onTicket();
                break;
            case R.id.btn_pay:
                payClick();
                break;
            case R.id.fl_huiLife_addressChooice:
                ChooiceAddress();
                break;
            case R.id.fl_huiLife_NoAddress:
                CreateAddress();
                break;
            case R.id.rb_pay_wx:
                orderParams.setPay_type(1);
                break;
            case R.id.rb_pay_ipay:
                orderParams.setPay_type(2);
                break;

        }
    }

    /**
     * 转到优惠券界面   v2.0没有了
     */
    public void onTicket() {
        Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(TicketAct.class));
        mIntent.putExtra(TicketAct.TICKET_TYPE, 2);
        mIntent.putExtra(TicketAct.TICKET_PRICE, orderParams.getTotal_amount() + "");
        startActivityForResult(mIntent, 0x22);
    }


    @Override
    public void onResume() {
//        initData();
        super.onResume();
    }

    private void initData() {
        if (carIdList != null && !carIdList.equals("")) {

            HTTPHelper.NewSupplyShowPay(mShowPayIbpi, carIdList, 0);
        }
    }

    NewSupplyPaydetailBean.SupplyPayDataEntity mBean;
    /**
     * 展示支付界面
     */
    BpiHttpHandler.IBpiHttpHandler mShowPayIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mBean = (NewSupplyPaydetailBean.SupplyPayDataEntity) message;
            orderParams = new HuiSuppOrderParams2();
            orderParams.setToken(HighCommunityApplication.mUserInfo.getToken());
            Log.e(Tag,"onSuccess:"+mBean.getTotal_fee()+","+mBean.getZero_money());
            orderParams.setTotal_amount(mBean.getTotal_fee());
            orderParams.setTotal_fee(mBean.getTotal_fee());
            orderParams.setZero_money(mBean.getZero_money());
            orderParams.setCart_ids(carIdList);
            setOrderList(mBean);
            setFee(mBean);
            setUserAddress(mBean);
            initView();
//            HSuppGdDefBean data = (HSuppGdDefBean) message;
//            updateData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveNewSupplyShowPay(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {
        }
    };

    /**
     * 数据获取成功后设置收货人地址留言等
     *
     * @param mBean
     */
    private void setUserAddress(NewSupplyPaydetailBean.SupplyPayDataEntity mBean) {
        mConsign = mBean.getConsign();
        if (mConsign != null) {
            updateData(mConsign);
        }
    }

    /**
     * 数据获取成功后设置应付款、零钱等数据
     *
     * @param mBean
     */
    private void setFee(NewSupplyPaydetailBean.SupplyPayDataEntity mBean) {
        tv_wallet.setText("零钱包（" + mBean.getZero_money() + "元）");
        if (mBean.getZero_money() < 0.1) {
            edt_pay_num.setClickable(false);
            edt_pay_num.setFocusable(false);
        } else {

            edt_pay_num.setFocusable(true);
            edt_pay_num.setClickable(true);

        }

    }

    /**
     * 数据获取成功后设置订单列表的数据
     *
     * @param mBean
     */
    private void setOrderList(NewSupplyPaydetailBean.SupplyPayDataEntity mBean) {
        mList = mBean.getGoods();
        adapter = new NewHuiBuyAdapter(NewHuiBuyFrag.this, getActivity(), mList);
        cl_goods.setAdapter(adapter);



    }

    /**
     * 获取订单内容
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
//            updateData(data);
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

    //    @Click(R.id.btn_pay)
    void payClick() {
        if (HighCommunityUtils.isLogin(getActivity())) {

            if (orderParams.getTotal_fee() == 0) {
                HighCommunityUtils.GetInstantiation().ShowToast("实际支付不能为零", 0);
                return;
            }
            if (TextUtils.isEmpty(orderParams.getAddress_id() + "")) {
                HighCommunityUtils.GetInstantiation().ShowToast("请选择收货地址", 0);
                return;
            }
            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
            JSONObject params = new JSONObject();
            try {
                params.put("token", HighCommunityApplication.mUserInfo.getToken());
                if (!TextUtils.isEmpty(orderParams.getTicket_id())) {
                    params.put("ticket_id", orderParams.getTicket_id());
                }
                if (orderParams.getZero_money() != 0.0f) {
                    params.put("zero_money", orderParams.getZero_money());
                }
                params.put("pay_type", orderParams.getPay_type());
                params.put("address_id", orderParams.getAddress_id());
                params.put("total_amoun", orderParams.getTotal_amount());
                params.put("total_fee", orderParams.getTotal_fee());
                orderParams.setMark(et_Message.getText().toString().trim());
                params.put("mark", orderParams.getMark());
                if (orderParams.getCart_ids() != null && !orderParams.getCart_ids().equals("")) {
                    params.put("cart_ids", orderParams.getCart_ids());
                }
                if (orderParams.getOrder_num() != null && !orderParams.getOrder_num().equals("")) {
                    params.put("order_num", orderParams.getCart_ids());
                }
//                JSONArray jsonArray = new JSONArray();
//                for (int i = 0; i < ListUtils.getSize(orderParams.getGoods()); i++) {
//                    JSONObject goods = new JSONObject();
//                    goods.put("goods_id", orderParams.getGoods().get(i).getGid());
//                    goods.put("goods_price", orderParams.getGoods().get(i).getPrice());
//                    goods.put("number", orderParams.getGoods().get(i).getNum());
//                    jsonArray.put(goods);
//                }
                Log.e(Tag, "params:" + params.toString());
                HTTPHelper.submitNewHuiOrder(mIbpiOrder, params.toString());
                Log.e(Tag, "------a2");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 支付回调
     */
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
            Log.e(Tag,"message："+message.toString());
//            GoodsOrderBean bean = (GoodsOrderBean) message;
//            Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
//            mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_PAY);
//            mIntent.putExtra(PaymentActivity.INTENTTAG, bean.getOrder_id());
//            startActivity(mIntent);
//            getActivity().finish();
        }

        @Override
        public Object onResolve(String result) {

            return result;
//            return HTTPHelper.ResolveHuiSuppOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };

    //    @Click(R.id.fl_huiLife_addressChooice)
    void ChooiceAddress() {
        Intent mAddress = new Intent(getActivity(), GeneratedClassUtils.get(AddressAct.class));
        startActivityForResult(mAddress, 0x21);
    }

    //    @Click(R.id.fl_huiLife_NoAddress)
    void CreateAddress() {
        Intent mAddress = new Intent(getActivity(), GeneratedClassUtils.get(AddressAct.class));
        startActivityForResult(mAddress, 0x21);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(Tag, "------onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0x21 && resultCode == 0x22) {
            AddressBean address = (AddressBean) data.getSerializableExtra("address");
            orderParams.setAddress_id(Integer.parseInt(address.getId()));
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
            float realToatal = orderParams.getTotal_amount() - orderParams.getTicket_value();
            if (realToatal < orderParams.getZero_money()) {
                edt_pay_num.setText("");
            }
            tv_coupon.setText("￥" + orderParams.getTicket_value() + "");
            updateOrder();
        }
    }


    public void updateData(NewSupplyPaydetailBean.SupplyPayDataEntity.SupplyPayConsignEntity mConsign) {




        if (mConsign != null) {
            fl_huiLife_addressChooice.setVisibility(View.VISIBLE);
            orderParams.setAddress_id(mConsign.getId());

            tv_reserve_name.setText(mConsign.getName());
            tv_reserve_phone.setText(mConsign.getTel());
            tv_reserve_address.setText(mConsign.getAddress());
            if ("0".equals(mConsign.getIsDefault())) {
                tv_address_default.setVisibility(View.GONE);
            } else {
                tv_address_default.setVisibility(View.VISIBLE);
            }
            mNoAddress.setVisibility(View.GONE);
        } else {
            mNoAddress.setVisibility(View.VISIBLE);
            fl_huiLife_addressChooice.setVisibility(View.GONE);
        }

        updateOrder();
    }

    public void updateOrder() {
        Log.e(Tag,"updateOrder:"+orderParams.toString());

        tv_total_pay.setText("合计金额￥" + CommonUtils.f2Bi(orderParams.getTotal_amount()));
        tv_total_actual.setText("￥" + CommonUtils.f2Bi(orderParams.getTotal_fee()));

        Log.e(Tag, "orderParams.getTotal_amount()--" + orderParams.getTotal_amount() + ",tv_coupon.toString()--" + tv_coupon.getText().toString()
                + ",orderParams.getTicket_value()--" + orderParams.getTicket_value());

//        if (orderParams.getTotal_amount() < 100 && tv_coupon.getText().toString().contains("￥")) {
//            tv_coupon.setText(" - ");
//            tv_total_actual.setText("￥" + CommonUtils.f2Bi(orderParams.getTotal_fee()) + orderParams.getTicket_value());
//            orderParams.setTicket_value(0.0f);
////            tv_coupon.setText("￥" + orderParams.getTicket_value() + "");
//        }

        if (orderParams.getTotal_amount() <= 0 || orderParams.getTotal_fee() <= 0) {
            if (orderParams.getTotal_amount() <= 0) {
                tv_total_pay.setText("合计金额￥" + 0.0 + "");
                tv_total_actual.setText("￥" + 0.0 + "");
            }
            btn_pay.setClickable(false);
            btn_pay.setBackgroundResource(R.color.Defult_Color_Grey);
        } else {
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
