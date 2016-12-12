package cn.hi028.android.highcommunity.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.ActGoodsBean;
import cn.hi028.android.highcommunity.bean.WpayBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.alipay.AlipayUtils;
import cn.hi028.android.highcommunity.utils.alipay.PayResult;
import cn.hi028.android.highcommunity.utils.wchatpay.WchatPayUtils;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import cn.hi028.android.highcommunity.wxapi.WXPayEntryActivity;
import sun.misc.BASE64Decoder;

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
    int payType = -1;
    PopupWindow waitPop = null;

    static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcapture);
        ButterKnife.bind(this);
        act=this;
        captureStr = getIntent().getStringExtra("result");
        tv.setVisibility(View.GONE);
        Log.e(Tag,"captureStr---"+captureStr.toString());
//        byte[] decode = Base64.decode(captureStr.getBytes());
        String s=decode(captureStr);
        Log.e(Tag,"decode---"+s.toString());
        mTitle.setText("订单支付");
        tv.setText(s);
        WchatPayUtils.getInstance().init(ShowCaptureActivity.this);
        handlerCaptureStr(s);
        initView();

    }

    // 将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String s) {
        if (s == null) return null;
        sun.misc.BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    // 解密
    public String decode(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {

                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    Map mMap;
    private void handlerCaptureStr(String captureStr) {
        if (!captureStr.contains("&")){
          return;
        }
        mMap=new HashMap();
//        String testStr="http://wap.fbrrr.com/access?phone=13568949782&from=290101&token=a869f855d12b4d88818bb560d6b7fc21";
//        String[] split1 = testStr.split("\\?");
//        Log.e(Tag,"1---"+split1.toString()+"---"+split1.length);
        String[] split2 = captureStr.split("&");
        Log.e(Tag,"2---"+split2.toString()+"---"+split2.length);
        for (int i = 0; i < split2.length; i++) {
            String[] split3 = split2[i].split("=");
            mMap.put(split3[0],split3[1]);
        }
        Log.e(Tag,"3---"+mMap.toString());
setView(mMap);

    }

    private void setView(Map mMap) {
        tvGoodsName.setText(mMap.get("name")+"");
        tvGoodsPrice.setText("小计：￥"+mMap.get("price"));
        finaltotalPrice=Float.parseFloat(mMap.get("price").toString());
        tvGoodsTotal.setText("合计金额￥"+mMap.get("price"));
        if (mMap.get("pic") == null || mMap.get("pic").toString().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, imgGoodsPic);
        } else {
            BpiUniveralImage.displayImage(mMap.get("pic").toString(), imgGoodsPic);
        }
        if (mMap.get("limit").toString()!=null&&mMap.get("limit").toString()!=""){

            limit=Integer.parseInt(mMap.get("limit").toString());
        }
    }

    private void initView() {




    }
int limit=-1;

float finaltotalPrice=0;int finalnum=1;
    @OnClick({R.id.img_back, R.id.tv_goods_reduce, R.id.tv_goods_add, R.id.rb_pay_wx, R.id.rb_pay_ipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_goods_reduce:
                Log.e(Tag,"Text().toString():"+tvGoodsNum.getText().toString()+",parseInt:"+Integer.parseInt(tvGoodsNum.getText().toString()));
                if (Integer.parseInt(tvGoodsNum.getText().toString()) > 1) {
                    int nowNUm=Integer.parseInt(tvGoodsNum.getText().toString())- 1;
                    finalnum=nowNUm;
                    tvGoodsNum.setText(nowNUm+"");
//                    (float)(Math.round(a*100))/100
             float nowTotalPrice=(float)Math.round(nowNUm*Float.parseFloat(mMap.get("price").toString())*100)/100;
                    finaltotalPrice=nowTotalPrice;
                    tvGoodsTotal.setText("合计金额￥"+nowTotalPrice);
                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("数目不能为0", 0);
                }
                break;
            case R.id.tv_goods_add:
                Log.e(Tag,"Text().toString():"+tvGoodsNum.getText().toString()+",parseInt:"+Integer.parseInt(tvGoodsNum.getText().toString()));
               if (limit==-1){
                   Log.e(Tag,"limit"+limit);
                   limit=3;
               }
                if (Integer.parseInt(tvGoodsNum.getText().toString()) < limit||limit==0) {
                    int nowNUm=Integer.parseInt(tvGoodsNum.getText().toString())+1;
                    finalnum=nowNUm;
                    tvGoodsNum.setText(nowNUm+"");
                    float nowTotalPrice=(float)Math.round(nowNUm*Float.parseFloat(mMap.get("price").toString())*100)/100;
                    finaltotalPrice=nowTotalPrice;
                    tvGoodsTotal.setText("合计金额￥"+nowTotalPrice);
                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("活动商品限购"+limit+"件", 0);
                }
                break;
            case R.id.rb_pay_wx:
                payType = 1;
                rbPayIpay.setChecked(false);
                rbPayWx.setChecked(true);
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowCaptureActivity.this, rbPayWx, Gravity.CENTER);
               Log.e(Tag,"rb_pay_wx");
                HTTPHelper.submitBuyHotGoodsWX(mIbpiWPaySubOrder,mMap.get("gid").toString(),finalnum+"", finaltotalPrice+"",payType+"");
                break;
            case R.id.rb_pay_ipay:
                payType = 0;
                rbPayIpay.setChecked(true);
                rbPayWx.setChecked(false);
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowCaptureActivity.this, rbPayIpay, Gravity.CENTER);
                HTTPHelper.submitBuyHotGoods(mIbpiOrder,mMap.get("gid").toString(),finalnum+"", finaltotalPrice+"",payType+"");

                break;
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
            ActGoodsBean.ActGoodsDataEntity mBean = (ActGoodsBean.ActGoodsDataEntity) message;
            if (payType==-1)return;
            if (payType == 0) {
                AlipayUtils.getInstance().payGoods(ShowCaptureActivity.this, "活动商品","   ", mBean.getTotal_fee(), mBean.getOut_trade_no(), "0",
                        mBean.getTicket_value()+"", mBean.getNotify_url(), new AlipayUtils.onPayListener() {

                            @Override
                            public void onPay(PayResult result) {

                                String resultStatus = result.getResultStatus();
                                Log.e(Tag,"resultStatus:"+resultStatus);
                                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                if (TextUtils.equals(resultStatus, "9000")) {
                                    Toast.makeText(ShowCaptureActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                    showMsgDialog(1);
                                } else {
                                    // 判断resultStatus 为非"9000"则代表可能支付失败
                                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                                    if (TextUtils.equals(resultStatus, "8000")) {
                                        Toast.makeText(ShowCaptureActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                                        showMsgDialog(2);
                                            Toast.makeText(ShowCaptureActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            } else if (payType == 1) {


            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHotGoodsOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };
    BpiHttpHandler.IBpiHttpHandler mIbpiWPaySubOrder = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag,"onError:"+message);
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag,"onSuccess:"+message.toString());
            waitPop.dismiss();
            if (null == message) {
                return;
            }
            WXPayEntryActivity.toFrag = 3;
            WpayBean mBean = (WpayBean) message;

            Log.e(Tag,"WpayBean:"+mBean.toString());
            WchatPayUtils.getInstance().apay(ShowCaptureActivity.this, mBean.getAppid(), mBean.getPartnerid(), mBean.getPrepayid(), mBean.getNoncestr(), mBean.getPackages(), mBean.getSign(), mBean.getTimestamp());

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveHotGoodsWXOrder(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };
//
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
    public static void finishThisAct() {
        act.finish();
    }
}
