package cn.hi028.android.highcommunity.activity.fragment;

import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;

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
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.TicketAct;
import cn.hi028.android.highcommunity.adapter.NewHuiBuyAdapter;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.bean.Autonomous.NewHuiPayDetail_OederBean;
import cn.hi028.android.highcommunity.bean.Autonomous.NewPaySuccessBean;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyPaydetailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.SupplyPayConsignEntity;
import cn.hi028.android.highcommunity.bean.Autonomous.SupplyPayGoodsEntity;
import cn.hi028.android.highcommunity.bean.HSuppGdDefBean;
import cn.hi028.android.highcommunity.bean.WpayBean;
import cn.hi028.android.highcommunity.params.HuiSuppOrderParams2;
import cn.hi028.android.highcommunity.utils.CommonUtils;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.alipay.AlipayUtils;
import cn.hi028.android.highcommunity.utils.alipay.PayResult;
import cn.hi028.android.highcommunity.utils.wchatpay.WchatPayUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import cn.hi028.android.highcommunity.view.LinearLayoutForListView;
import cn.hi028.android.highcommunity.wxapi.WXPayEntryActivity;

/**
 * @功能：新版惠生活商品支付界面<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/14<br>
 */
public class NewHuiBuyFrag extends BaseFragment {
    static final String Tag = "NewHuiBuyFrag--->";
    @Bind(R.id.cl_goods)
    LinearLayoutForListView cl_goods;
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
    RelativeLayout fl_huiLife_addressChooice;
    @Bind(R.id.fl_yhq)
    FrameLayout fl_yhq;
    @Bind(R.id.btn_pay)
    Button btn_pay;
    //    public static HuiSupportBean entyBean;
    //    public static List<NewSupplyCarlistBean.SupplyCarlistDataEntity> listData;
    int type = 0;
    public HuiSuppOrderParams2 orderParams;
    SupplyPayConsignEntity mConsign;//订单参数
    //    HSuppGdDefBean data;
    PopupWindow waitPop,mWaittingPop;
    NewHuiBuyAdapter adapter;
    View view;
    String carIdList,order_num;
    List<SupplyPayGoodsEntity> mList = new ArrayList<SupplyPayGoodsEntity>();
    @Bind(R.id.rb_pay_wx)
    RadioButton rbPayWx;
    @Bind(R.id.rb_pay_ipay)
    RadioButton rbPayIpay;
    @Bind(R.id.rg_huil_ife)
    RadioGroup rgPay;
    @Bind(R.id.tv_nodata)
    TextView tv_reeor;
    @Bind(R.id.hui_pay_bottom_layout)
    FrameLayout layout_pay_bottom;
    @Bind(R.id.layout_scrollview)
    ScrollView layout_scrollview;
int orderType=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_newhui_buy, null);
        ButterKnife.bind(this, view);
        fragment=this;
        Bundle bundle = getArguments();
        carIdList = bundle.getString("carIdList");
        order_num = bundle.getString("order_num");
        orderType= bundle.getInt("orderType",-1);
        Log.d(Tag, "carIdList=" + carIdList);
        Log.d(Tag, "order_num=" + order_num);

//        initView();
        WchatPayUtils.getInstance().init(getActivity());
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
        edt_pay_num.setText("0.00");
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
                        edt_pay_num.setText("0.0");
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
//        fl_huiLife_addressChooice.setVisibility(View.GONE);
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

        rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_pay_wx:
                        payType = 1;
                        orderParams.setPay_type(1);
                        break;
                    case R.id.rb_pay_ipay:
                        payType = 2;
                        orderParams.setPay_type(2);
                        break;

                }
            }
        });
        rbPayWx.setChecked(true);
        tv_wallet.requestFocus();
    }

    int payType = 1;

    @OnClick({R.id.fl_yhq, R.id.btn_pay, R.id.fl_huiLife_addressChooice, R.id.fl_huiLife_NoAddress,
            R.id.rb_pay_wx, R.id.rb_pay_ipay})
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
                payType = 1;
                orderParams.setPay_type(1);
                break;
            case R.id.rb_pay_ipay:
                payType = 2;
                orderParams.setPay_type(2);
                break;

        }
    }

    /**
     * 转到优惠券界面
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
        if (getActivity().hasWindowFocus()){

            mWaittingPop=HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
        }

        if (orderType==-1)return;
        if (carIdList != null && !carIdList.equals("")) {

            HTTPHelper.NewSupplyShowPay(mShowPayIbpi, carIdList, orderType);
        }else if (order_num != null && !order_num.equals("")){
            HTTPHelper.NewSupplyShowPay(mShowPayIbpi, order_num, orderType);

        }
    }

    NewSupplyPaydetailBean.SupplyPayDataEntity mBean;
    /**
     * 展示支付界面
     */
    BpiHttpHandler.IBpiHttpHandler mShowPayIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWaittingPop!=null){
                mWaittingPop.dismiss();
            }

            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
            tv_reeor.setText(message);
            layout_scrollview.setVisibility(View.GONE);
            layout_pay_bottom.setVisibility(View.GONE);
            tv_reeor.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "onSuccess:0");
            if (mWaittingPop!=null){
                mWaittingPop.dismiss();
            }
            if (null == message){
                Log.e(Tag, "onSuccess:null ");

                return;
            }
            tv_reeor.setVisibility(View.GONE);
            if (orderType==1){

                NewHuiPayDetail_OederBean.NewHuiPayDetail_OederDataEntity orderDetailBean= (NewHuiPayDetail_OederBean.NewHuiPayDetail_OederDataEntity) message;
                mBean=new NewSupplyPaydetailBean.SupplyPayDataEntity();
                mBean.setTotal_fee(orderDetailBean.getTotal_fee());
mBean.setZero_money(orderDetailBean.getZero_money());

                mBean.setConsign(orderDetailBean.getConsign());
                List<SupplyPayGoodsEntity> goods=new ArrayList<SupplyPayGoodsEntity>();
                goods.add(orderDetailBean.getGoods());
                mBean.setGoods(goods);

            }else if (orderType==0){
                mBean = (NewSupplyPaydetailBean.SupplyPayDataEntity) message;
            }
            if (orderType!=1&&orderType!=0){
                return;
            }
            Log.e(Tag, "onSuccess:" + mBean.toString());

            orderParams = new HuiSuppOrderParams2();
            orderParams.setToken(HighCommunityApplication.mUserInfo.getToken());
            Log.e(Tag, "onSuccess:" + mBean.getTotal_fee() + "," + mBean.getZero_money());
            orderParams.setTotal_amount(mBean.getTotal_fee());
            orderParams.setTotal_fee(mBean.getTotal_fee());
//            orderParams.setZero_money(mBean.getZero_money());
            if (orderType==0&&carIdList!=null){

                orderParams.setCart_ids(carIdList);
            }
            if (orderType==1&&order_num!=null){

                orderParams.setOrder_num(order_num);
            }
            setOrderList(mBean);
            setFee(mBean);
            setUserAddress(mBean);
            initView();
//            HSuppGdDefBean data = (HSuppGdDefBean) message;
//            updateData(data);
        }

        @Override
        public Object onResolve(String result) {
            Log.e(Tag, "onResolve:" + result);
if (result.contains("\"consign\":false")){
    Log.e(Tag, "onResolve:替换" );

    result=  result.replace("\"consign\":false","\"consign\":null");
    Log.e(Tag, "onResolve:替换ok" +"onResolve:" + result);

}
            if (orderType==0){

                return HTTPHelper.ResolveNewSupplyShowPay(result);
            }
            else if (orderType==1){

                return HTTPHelper.ResolveNewSupplyShowPay_Order(result);
            }
            return null;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {
            if (mWaittingPop!=null){
                mWaittingPop.dismiss();
            }
        }
    };

    /**
     * 数据获取成功后设置收货人地址留言等
     *
     * @param mBean
     */
    private void setUserAddress(NewSupplyPaydetailBean.SupplyPayDataEntity mBean) {
        mConsign = mBean.getConsign();
        updateData(mConsign);
//        if (mConsign != null) {
//        }
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
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        cl_goods.setLayoutParams(layoutParams);
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
                if (orderType==0){


                    HTTPHelper.submitNewHuiOrder(mIbpiOrder, orderParams.getTicket_id() + "", orderParams.getZero_money() + "", orderParams.getPay_type() + "",
                            orderParams.getAddress_id() + "", orderParams.getTotal_amount() + "", orderParams.getTotal_fee() + "",
                            orderParams.getMark(), orderParams.getCart_ids(),orderType + "");
                    Log.e(Tag, "------a2");
                }else if (orderType==1){
                    HTTPHelper.submitNewHuiOrder(mIbpiOrder, orderParams.getTicket_id() + "", orderParams.getZero_money() + "", orderParams.getPay_type() + "",
                            orderParams.getAddress_id() + "", orderParams.getTotal_amount() + "", orderParams.getTotal_fee() + "",
                            orderParams.getMark(), orderParams.getOrder_num(),orderType + "");
                    Log.e(Tag, "------a2");


                }
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
            Log.e(Tag, "payType == " + payType);

            Log.e(Tag, "message：" + message.toString());

            if (payType == -1) return;
            if (payType == 2) {
                NewPaySuccessBean.PaySuccessDataEntity mBean = (NewPaySuccessBean.PaySuccessDataEntity) message;
                AlipayUtils.getInstance().payGoods(getActivity(), mBean.getSubject(), mBean.getBody(), mBean.getTotal_fee(), mBean.getOut_trade_no(), mBean.getTicket_id(),
                        mBean.getZero_money(), mBean.getNotify_url(), new AlipayUtils.onPayListener() {

                            @Override
                            public void onPay(PayResult result) {

                                String resultStatus = result.getResultStatus();
                                Log.e(Tag, "resultStatus:" + resultStatus);
                                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                if (TextUtils.equals(resultStatus, "9000")) {
                                    Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                                    showMsgDialog(1);
                                } else {
                                    // 判断resultStatus 为非"9000"则代表可能支付失败
                                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                                    if (TextUtils.equals(resultStatus, "8000")) {
                                        Toast.makeText(getActivity(), "支付结果确认中", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                        showMsgDialog(2);
                                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();


                                    }
                                }
                            }
                        });
            } else if (payType == 1) {
                WXPayEntryActivity.toFrag = 4;

                mWpayBean = (WpayBean) message;

                Log.e(Tag, "WpayBean:" + mWpayBean.toString());
                WchatPayUtils.getInstance().apay(getActivity(), mWpayBean.getAppid(), mWpayBean.getPartnerid(), mWpayBean.getPrepayid(), mWpayBean.getNoncestr(), mWpayBean.getPackages(), mWpayBean.getSign(), mWpayBean.getTimestamp());


            }

        }

        @Override
        public Object onResolve(String result) {
            if (payType == 1) {
                Log.e(Tag, "payType == 1" + result);

                return HTTPHelper.ResolveHotGoodsWXOrder(result);
            } else if (payType == 2) {
                Log.e(Tag, "payType == 2" + result);

                return HTTPHelper.ResolveNewHuiOrder(result);

            }
            return result;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };
    WpayBean mWpayBean;
    static NewHuiBuyFrag fragment;
    public static void toOrderDetail() {
        Intent mLeftjump = new Intent(fragment.getActivity(), GeneratedClassUtils.get(MenuLeftAct.class));
        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                Constacts.MENU_LEFT_ORDER);
        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
        Constacts.mUserCenter.setOrder(0);
        fragment.getActivity().startActivity(mLeftjump);

//        Toast.makeText(fragment.getActivity(),"结束本页，去订单列表，再从订单列表到详情",Toast.LENGTH_SHORT).show();
//        Intent mIntent = new Intent(fragment.getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
//        mIntent.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_ORDER_DETAIL);
//        mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, mBean.geti + "");
//        fragment.startActivity(mIntent);
        fragment.getActivity().finish();
    }
    public static void toOrderTopayDetail() {
        Intent mLeftjump = new Intent(fragment.getActivity(), GeneratedClassUtils.get(MenuLeftAct.class));
        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                Constacts.MENU_LEFT_ORDER);
        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 1);
        Constacts.mUserCenter.setOrder(1);
        fragment.getActivity().startActivity(mLeftjump);

//        Toast.makeText(fragment.getActivity(),"结束本页，去订单列表，再从订单列表到详情",Toast.LENGTH_SHORT).show();
//        Intent mIntent = new Intent(fragment.getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
//        mIntent.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_ORDER_DETAIL);
//        mIntent.putExtra(MenuLeftSecondAct.INTENTTAG, mBean.geti + "");
//        fragment.startActivity(mIntent);
        fragment.getActivity().finish();
    }
    public void showMsgDialog(int x) {
        String msg;
        if (x == 1) {
            msg = "支付成功";
        } else {
            msg = "支付失败";
        }
        ECAlertDialog dialog2;
        if (x==1){

            dialog2 = ECAlertDialog.buildAlert(getActivity(), msg, "我知道了", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    toOrderDetail();
//                getActivity().finish();
                }
            });
        }else{
            dialog2 = ECAlertDialog.buildAlert(getActivity(), msg, "我知道了", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    toOrderTopayDetail();
//                getActivity().finish();
                }
            });
        }
        dialog2.setTitle("提示");
        dialog2.show();
    }

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
            Bundle bundle = data.getBundleExtra("data");
            AddressBean address =bundle.getParcelable("address");
//            AddressBean address = (AddressBean) data.getSerializableExtra("address");
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
//            orderParams.setTotal_fee(realToatal);
            if (realToatal < orderParams.getZero_money()) {
                edt_pay_num.setText("");
            }
            tv_coupon.setText("￥" + orderParams.getTicket_value() + "");
            updateOrder();
        }
    }


    public void updateData(SupplyPayConsignEntity mConsign) {

        orderParams.setZero_money(0.0f);
        if (mConsign != null) {
            Log.e(Tag,"mConsign != null");
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
            Log.e(Tag,"mConsign = null");

            mNoAddress.setVisibility(View.VISIBLE);
            fl_huiLife_addressChooice.setVisibility(View.GONE);
        }

        updateOrder();
    }

    public void updateOrder() {
        Log.e(Tag, "updateOrder:" + orderParams.toString());
//        float parseFloat = Float.parseFloat(edt_pay_num.getText().toString());
        Log.e(Tag, "parseFloat:" + orderParams.getZero_money());

        orderParams.setTotal_fee((float)(Math.round((orderParams.getTotal_amount()-orderParams.getZero_money()-orderParams.getTicket_value())*100))/100);
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

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        // TODO Auto-generated method stub
//        super.onWindowFocusChanged(hasFocus);
//        if(hasFocus){
//
////            showPopupWindow(getApplicationContext());
//        }
//    }

}
