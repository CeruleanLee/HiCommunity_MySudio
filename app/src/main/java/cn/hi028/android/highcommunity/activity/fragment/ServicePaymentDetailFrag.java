/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.bean.BillBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：缴费详情页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/18<br>
 */
@EFragment(resName = "frag_paymentdetail")
public class ServicePaymentDetailFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "ServicePaymentDetailFrag";

    @ViewById(R.id.tv_payment_detail_type)
    ImageView mType;
    @ViewById(R.id.tv_payment_detail_name)
    TextView mName;
    @ViewById(R.id.tv_payment_detail_finished)
    TextView mFinish;
    @ViewById(R.id.tv_payment_detail_OwnerName)
    TextView mOwner;
    @ViewById(R.id.tv_payment_detail_UnitPrice)
    TextView mUnitPrice;
    @ViewById(R.id.tv_payment_detail_OwnerNumber)
    TextView mOwnerNumber;
    @ViewById(R.id.tv_payment_detail_OwnerDate)
    TextView mDate;
    @ViewById(R.id.tv_payment_detail_Dosage)
    TextView mDosage;
    @ViewById(R.id.tv_payment_detail_OwnerDosage)
    TextView mOwnerDosage;
    @ViewById(R.id.tv_payment_detail_TotalPrice)
    TextView mTotalPrice;
    @ViewById(R.id.tv_payment_detail_Submit)
    TextView mSubmit;
    @ViewById(R.id.tv_payment_detail_finished)
    TextView mFinished;

    @ViewById(R.id.progress_PaymentDetails)
    View mProgress;
    @ViewById(R.id.tv_PaymentDetails_Nodata)
    TextView mNodta;

    BillBean mBean;
    String type="";
    @AfterViews
    void initView() {
        mProgress.setVisibility(View.VISIBLE);
        mNodta.setVisibility(View.GONE);
       type = getActivity().getIntent().getStringExtra(ServiceSecondAct.INTENTTAG);
        String fid = getActivity().getIntent().getStringExtra(ServicePaymentDetailFrag.FRAGMENTTAG);
        if (TextUtils.isEmpty(type)) {
            mNodta.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
            return;
        }
        if (type.equals("1")) {
            ImageLoaderUtil.disPlay("drawable://" + R.mipmap.img_paytype_elec, mType);
            mName.setText("电费");
        } else if (type.equals("2")) {
            ImageLoaderUtil.disPlay("drawable://" + R.mipmap.img_paytype_water, mType);
            mName.setText("水费");
        } else if (type.equals("3")) {
            ImageLoaderUtil.disPlay("drawable://" + R.mipmap.img_paytype_qi, mType);
            mName.setText("气费");
        } else {
            ImageLoaderUtil.disPlay("drawable://" + R.mipmap.img_paytype_wuguan, mType);
            mName.setText("物业费");
        }

        HTTPHelper.getBill(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", type, fid);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodta.setVisibility(View.VISIBLE);
            mNodta.setText(message);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (message == null) {
                mNodta.setVisibility(View.VISIBLE);
                return;
            }
            mBean = (BillBean) message;
            mOwner.setText(mBean.getReal_name());
            mOwnerNumber.setText(mBean.getAddress());
            mDate.setText(mBean.getDate());
            mDosage.setText(mBean.getSection());
            mOwnerDosage.setText(mBean.getDosage());
            mUnitPrice.setText(mBean.getPrice());
            mTotalPrice.setText(mBean.getTotal_price());
            if (mBean.getStatus().equals("1")) {
                mFinish.setVisibility(View.VISIBLE);
                mSubmit.setVisibility(View.GONE);
            } else {
                mFinish.setVisibility(View.GONE);
                mSubmit.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveBill(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    @Click(R.id.tv_payment_detail_Submit)
    void submit() {
        Intent mPay = new Intent(getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
        mPay.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.PAYMENTBILL_PAY);
        mPay.putExtra(PaymentActivity.INTENTTAG, mBean.getId());
        mPay.putExtra(PaymentActivity.INTENTTAGTYPE, type);
        startActivity(mPay);
        getActivity().finish();
    }
}
