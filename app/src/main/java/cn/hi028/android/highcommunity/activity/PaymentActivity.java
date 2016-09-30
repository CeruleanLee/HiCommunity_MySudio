/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.duohuo.dhroid.activity.BaseActivity;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.*;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：支付统一生成页面<br>  除了联盟
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/18<br>
 */
@EActivity(resName = "act_payment")
public class PaymentActivity extends BaseFragmentActivity {

    public static final String ACTIVITYTAG = "PaymentActivity";
    public static final String INTENTTAG = "PaymentActivityIntent";
    public static final String INTENTTAGTYPE = "PayActIntentTp";
    @ViewById(R.id.img_back)
    ImageView img_back;
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.img_right)
    ImageView img_right;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;

    @AfterViews
    void initView() {
        img_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               finish();
            }
        });
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        int flag = getIntent().getIntExtra(ACTIVITYTAG, -1);
        if (-1 == flag)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (flag) {
            case Constacts.SERVICE_PAYMENT:
                mTitle.setText("缴费");
//                ServicePaymentFrag mPayment = (ServicePaymentFrag) new ServicePaymentFrag_();
//                ft.replace(R.id.ll_paymentAct, mPayment, ServicePaymentFrag.FRAGMENTTAG);
                break;
            case Constacts.HUILIFE_SUPPORT_PAY:
            	LogUtil.d("------PaymentActivity------1");
                mTitle.setText("支付");
                HuiSuppPayFrag mhuiSuppPayment = (HuiSuppPayFrag) new HuiSuppPayFrag_();
                ft.replace(R.id.ll_paymentAct, mhuiSuppPayment, HuiSuppPayFrag.FRAGMENTTAG);
                break;
            case Constacts.PAYMENTBILL_PAY:
            	LogUtil.d("------PaymentActivity------2");
                mTitle.setText("支付");
                BillPayFrag mBill = (BillPayFrag) new BillPayFrag_();
                ft.replace(R.id.ll_paymentAct, mBill, BillPayFrag.FRAGMENTTAG);
                break;
            case Constacts.HUILIFE_CHIPS_ORDER:
            	LogUtil.d("------PaymentActivity------3");
                mTitle.setText("订单");
                HuiLifeChipsOrderFrag mPayment = (HuiLifeChipsOrderFrag) new HuiLifeChipsOrderFrag_();
                ft.replace(R.id.ll_paymentAct, mPayment, HuiLifeChipsOrderFrag.FRAGMENTTAG);
                break;
            case Constacts.HUILIFE_CHIPS_ORDER_PAY:
            	LogUtil.d("------PaymentActivity------4");
                mTitle.setText("支付");
                HuiChipsPayFrag mPayChipsMent = (HuiChipsPayFrag) new HuiChipsPayFrag_();
                ft.replace(R.id.ll_paymentAct, mPayChipsMent, HuiChipsPayFrag.FRAGMENTTAG);
                break;
        }
        ft.commit();

    }
    @Click(R.id.img_back)
    void onBack() {
        finish();
    }

}
