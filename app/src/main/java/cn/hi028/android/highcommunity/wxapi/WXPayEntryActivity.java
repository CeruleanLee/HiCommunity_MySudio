package cn.hi028.android.highcommunity.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ShowCaptureActivity;
import cn.hi028.android.highcommunity.activity.fragment.BillPayFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiChipsPayFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiSuppPayFrag;
import cn.hi028.android.highcommunity.utils.wchatpay.Constants;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    TextView tv_payresult, tv_close;
    public static int toFrag = -1;//0：物业直供，1：众筹；2：账单 3:活动页支付
    BaseResp resp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resp != null && resp.errCode == 0) {
                    if (toFrag == 0) {
                        HuiSuppPayFrag.toOrderDetail();
                    } else if (toFrag == 1) {
                        HuiChipsPayFrag.toDetail();
                    } else if (toFrag == 2) {
                        BillPayFrag.toDetail();
                    }else if (toFrag==3){
                        ShowCaptureActivity.finishThisAct();
                    }
                }
                toFrag = -1;
                finish();

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(final BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("提示");
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//            builder.show();
//        }
        this.resp = resp;
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String resultCode = "";
            if (resp.errCode == 0) {
                resultCode = "微信支付成功";
            } else if (resp.errCode == -1) {
                resultCode = "微信支付失败";
            } else if (resp.errCode == -2) {
                resultCode = "微信支付取消";
            }
            tv_payresult = (TextView) findViewById(R.id.tv_payresult);
            tv_payresult.setText(resultCode);
        }

    }
}