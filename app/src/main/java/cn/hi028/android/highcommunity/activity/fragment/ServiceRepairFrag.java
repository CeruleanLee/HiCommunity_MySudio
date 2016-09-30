package cn.hi028.android.highcommunity.activity.fragment; /***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

//package cn.hi028.android.highcommunity.activity.fragment;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.view.Gravity;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.Toast;
//
//import com.don.tools.BpiHttpHandler;
//import com.don.tools.GeneratedClassUtils;
//
//import net.duohuo.dhroid.activity.BaseFragment;
//
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EFragment;
//import org.androidannotations.annotations.ViewById;
//
//import java.util.List;
//
//import cn.hi028.android.highcommunity.HighCommunityApplication;
//import cn.hi028.android.highcommunity.R;
//import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
//import cn.hi028.android.highcommunity.bean.CertifiBean;
//import cn.hi028.android.highcommunity.bean.RepairBean;
//import cn.hi028.android.highcommunity.utils.Constacts;
//import cn.hi028.android.highcommunity.utils.HTTPHelper;
//import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
//import cn.hi028.android.highcommunity.view.ECAlertDialog;
//
///**
// * @功能：维修界面<br>
// * @作者： 赵海<br>
// * @版本：1.0<br>
// * @时间：2016-01-06<br>
// */
//@EFragment(resName = "frag_service_repair")
//public class ServiceRepairFrag extends BaseFragment {
//    public static final String FRAGMENTTAG = "ServicePaymentFrag";
//    private PopupWindow mWaitingWindow;
//    @ViewById(R.id.img_seri_repair_order)
//    ImageView img_seri_repair_order;
//
//    /**
//     * 预定报修
//     */
//    @Click(R.id.img_seri_repair_order)
//    public void onClickOrder() {
//        if (HighCommunityUtils.isLogin(getActivity())) {
//            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowPopupWindow(getActivity(), img_seri_repair_order, Gravity.CENTER, "正在验证用户信息");
//            HTTPHelper.GetRepairCertification(mIbpiRepairCeri, HighCommunityApplication.mUserInfo.getId() + "");
//        }
//    }
//
//    BpiHttpHandler.IBpiHttpHandler mIbpiRepairCeri = new BpiHttpHandler.IBpiHttpHandler() {
//        @Override
//        public void onError(int id, String message) {
//            mWaitingWindow.dismiss();
//            if (id == 1002) {
//                ECAlertDialog.buildAlert(getActivity(), "你还未认证,是否去认证?", "去认证", "取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent mAuth = new Intent(getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
//                        mAuth.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_SECOND_PERSONALAUTH);
//                        startActivity(mAuth);
//                    }
//                }, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//
//                }).show();
//            } else {
//                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
//            }
//
//        }
//
//        @Override
//        public void onSuccess(Object message) {
//            mWaitingWindow.dismiss();
//            if (null == message)
//                return;
//            CertifiBean bean = (CertifiBean) message;
//            if (bean.getStatus() != null && bean.getStatus().equals("1")) {
//                Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
//                mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_REPAIR_DETAIL_ORDER);
//                mIntent.putExtra(ServiceSecondAct.INTENTTAG, bean);
//                startActivity(mIntent);
//            } else {
//                HighCommunityUtils.GetInstantiation().ShowToast("用户认证正在审核中", 0);
//            }
//        }
//
//        @Override
//        public Object onResolve(String result) {
//            return HTTPHelper.ResolveCertification(result);
//        }
//
//        @Override
//        public void setAsyncTask(AsyncTask asyncTask) {
//
//        }
//
//        @Override
//        public void cancleAsyncTask() {
//            mWaitingWindow.dismiss();
//        }
//    };
//
//    /**
//     * 紧急报修
//     */
//    @Click(R.id.img_seri_repairr_jj)
//    public void onClickJJ() {
//        Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
//        mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_REPAIR_DETAIL_JJ);
//        startActivity(mIntent);
//    }
//
//    /**
//     * 报修记录
//     */
//    @Click(R.id.img_seri_repair_record)
//    public void onClickRecord() {
//        if (HighCommunityUtils.isLogin(getActivity())) {
//            Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(ServiceSecondAct.class));
//            mIntent.putExtra(ServiceSecondAct.ACTIVITYTAG, Constacts.SERVICE_REPAIR_DETAIL_RECORD);
//            startActivity(mIntent);
//        }
//    }
//}
