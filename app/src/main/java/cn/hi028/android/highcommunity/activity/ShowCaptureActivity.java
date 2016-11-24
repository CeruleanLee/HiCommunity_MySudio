package cn.hi028.android.highcommunity.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.wchatpay.WchatPayUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

public class ShowCaptureActivity extends BaseFragmentActivity {
static final String Tag="ShowCaptureActivity:";
    @Bind(R.id.tv)
    TextView tv;
    String captureStr;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_secondtitle_name)
    TextView mTitle;
    @Bind(R.id.img_goods_pic)
    ImageView imgGoodsPic;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @Bind(R.id.tv_goods_total)
    TextView tvGoodsTotal;
    @Bind(R.id.tv_goods_reduce)
    TextView tvGoodsReduce;
    @Bind(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @Bind(R.id.tv_goods_add)
    TextView tvGoodsAdd;
    @Bind(R.id.ll_goods_num)
    LinearLayout llGoodsNum;
    @Bind(R.id.rb_pay_wx)
    RadioButton rbPayWx;
    @Bind(R.id.rb_pay_ipay)
    RadioButton rbPayIpay;
    @Bind(R.id.rg_huil_ife)
    RadioGroup rgHuilIfe;
    int payType = 0;
    PopupWindow waitPop = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcapture);
        ButterKnife.bind(this);
        captureStr = getIntent().getStringExtra("result");

        tv.setText(captureStr);
        mTitle.setText("订单支付");
        handlerCaptureStr(captureStr);
        initView();

    }
Map mMap;
    private void handlerCaptureStr(String captureStr) {
        mMap=new HashMap();
        String testStr="http://wap.fbrrr.com/access?phone=13568949782&from=290101&token=a869f855d12b4d88818bb560d6b7fc21";
        String[] split1 = testStr.split("\\?");
        Log.e(Tag,"1---"+split1.toString()+"---"+split1.length);
        String[] split2 = split1[1].split("&");
        Log.e(Tag,"2---"+split2.toString()+"---"+split2.length);
        for (int i = 0; i < split2.length; i++) {
            String[] split3 = split2[i].split("=");
            mMap.put(split3[0],split3[1]);
        }
        Log.e(Tag,"3---"+mMap.toString());
        Toast.makeText(this,mMap.toString(),Toast.LENGTH_SHORT).show();


    }

    private void initView() {
//        rgHuilIfe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                if (checkedId == R.id.rb_pay_ipay) {
//                    payType = 1;
//                } else if (checkedId == R.id.rb_pay_wx) {
//                    payType = 0;
//                }
//
//                onPay();
//            }
//
//        });

        WchatPayUtils.getInstance().init(ShowCaptureActivity.this);
//        if (mBean.getHead_pic() == null || mBean.getHead_pic().equals("")) {
//            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, imgGoodsPic);
//        } else {
//            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getHead_pic(), imgGoodsPic);
//        }

    }

    public void onPay() {
//        Log.d("--------", order.getOut_trade_no());
//        if (HighCommunityUtils.isLogin(ShowCaptureActivity.this)) {
//            if (CommonUtils.floatTo(order.getReal_pri()) > 0) {
//                if (order != null && payType == 1) {
//
//                    AlipayUtils.getInstance().payGoods(ShowCaptureActivity.this, "嗨社区-活动商品","", CommonUtils.f2Bi(order.getReal_pri()) + "",
//                            order.getOut_trade_no(), "0", "", "", new AlipayUtils.onPayListener() {
//
//                                @Override
//                                public void onPay(PayResult result) {
//
//                                    String resultStatus = result.getResultStatus();
//                                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                                    if (TextUtils.equals(resultStatus, "9000")) {
//                                        Toast.makeText(ShowCaptureActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                                      showMsgDialog(1);
//                                    } else {
//                                        // 判断resultStatus 为非"9000"则代表可能支付失败
//                                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                                        if (TextUtils.equals(resultStatus, "8000")) {
//                                            Toast.makeText(ShowCaptureActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                                            showMsgDialog(2);
////                                            Toast.makeText(ShowCaptureActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }
//                            });
//                } else if (order != null && payType == 0) {
//                    waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowCaptureActivity.this, btn_pay, Gravity.CENTER);
//                    HTTPHelper.submitSuppWPayOrder(mIbpiWPaySubOrder, order.getOrder_id());
//                }
//            } else {
//                HighCommunityUtils.GetInstantiation().ShowToast("支付金额，必须大于零", 0);
//            }
//        }
    }

    @OnClick({R.id.img_back, R.id.tv_goods_reduce, R.id.tv_goods_add, R.id.rb_pay_wx, R.id.rb_pay_ipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_goods_reduce:
                if (Integer.parseInt(tvGoodsNum.getText().toString()) > 1) {
                    tvGoodsNum.setText(Integer.parseInt(tvGoodsNum.getText().toString())  - 1);

                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("数目不能为0", 0);
                }
                break;
            case R.id.tv_goods_add:
                if (Integer.parseInt(tvGoodsNum.getText().toString()) < 4) {
                    tvGoodsNum.setText(Integer.parseInt(tvGoodsNum.getText().toString())  + 1);

                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("数目不能超过库存", 0);
                }
                break;
            case R.id.rb_pay_wx:
                payType = 1;
                rbPayIpay.setChecked(false);
                rbPayWx.setChecked(true);
//                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowCaptureActivity.this, rbPayWx, Gravity.CENTER);
onPay();
                break;
            case R.id.rb_pay_ipay:
                payType = 0;
                rbPayIpay.setChecked(true);
                rbPayWx.setChecked(false);
//                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowCaptureActivity.this, rbPayIpay, Gravity.CENTER);
                onPay();
                break;
        }
    }

    public void showMsgDialog(int x) {
        String msg;
        if (x==1){
            msg="支付成功";
        }else {
            msg="支付失败";
        }


        ECAlertDialog dialog2 = ECAlertDialog.buildAlert(ShowCaptureActivity.this, msg,"我知道了", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    ShowCaptureActivity.this.finish();
                }
            });
        dialog2.setTitle("提示");
        dialog2.show();
    }

}
