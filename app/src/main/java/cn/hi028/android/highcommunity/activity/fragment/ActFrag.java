/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.don.view.DrawableCenterTextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressAct;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.MenuLeftSecondAct;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.bean.PersonalInfoBean;
import cn.hi028.android.highcommunity.bean.UserCenterBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：新版我的<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/19<br>
 */
public class ActFrag extends Fragment {
    public static final String Tag = "ActFrag~~~";
    public static final String FRAGMENTTAG = "ActFrag";
    @Bind(R.id.myinfor_edit)
    TextView myinforEdit;
    @Bind(R.id.rl_leftmenu_userinfo)
    RelativeLayout mUserinfo;
    @Bind(R.id.img_LeftFrag_Avatar)
    CircleImageView mAvatar;
    @Bind(R.id.tx_LeftFrag_userName)
    TextView mLeftName;
    @Bind(R.id.tx_LeftFrag_userSex)
    TextView mLeftSex;
    @Bind(R.id.tx_LeftFrag_userlocation)
    TextView mLeftLocation;
    @Bind(R.id.tx_LeftFrag_userlocation_layout)
    LinearLayout mLeftLocation_layout;
    @Bind(R.id.tv_leftMenu_Order_Num)
    TextView mLeftOrderNum;
    @Bind(R.id.ll_leftMenu_Order)
    LinearLayout mOrder;
    @Bind(R.id.tv_leftMenu_order_all_tv)
    DrawableCenterTextView mOrder_AllTv;
    @Bind(R.id.tv_leftMenu_order_all_Num)
    TextView mOrder_allNum;
    @Bind(R.id.ll_leftMenu_order_all)
    RelativeLayout mOrder_all_layout;
    @Bind(R.id.tv_leftMenu_order_topay_tv)
    DrawableCenterTextView mOrder_TopayTv;
    @Bind(R.id.tv_leftMenu_order_topay_Num)
    TextView mOrder_TopayNum;
    @Bind(R.id.ll_leftMenu_order_topay)
    RelativeLayout mOrder_Topay_layout;
    @Bind(R.id.tv_leftMenu_order_torec_tv)
    DrawableCenterTextView mOrder_TorecTv;
    @Bind(R.id.tv_leftMenu_order_torec_Num)
    TextView mOrder_TorecNum;
    @Bind(R.id.ll_leftMenu_order_torec)
    RelativeLayout mOrder_Torec_layout;
    @Bind(R.id.tv_leftMenu_order_all_com_tv)
    DrawableCenterTextView mOrder_ComTv;
    @Bind(R.id.tv_leftMenu_order_all_com_num)
    TextView mOrder_ComNum;
    @Bind(R.id.ll_leftMenu_order_com)
    RelativeLayout mOrder_Com_layout;
    @Bind(R.id.tv_leftMenu_topic_Num)
    TextView mTopicNum;
    @Bind(R.id.ll_leftMenu_topic)
    LinearLayout mTopic;
    @Bind(R.id.tv_leftMenu_collection_Num)
    TextView mCollectionNum;
    @Bind(R.id.ll_leftMenu_collection)
    LinearLayout mCollection;
    @Bind(R.id.tv_leftMenu_Cart_Num)
    TextView mLeftCartNum;
    @Bind(R.id.ll_leftMenu_cart)
    LinearLayout mCart;
    @Bind(R.id.tv_leftMenu_bill_Num)
    TextView mLeftBillNum;
    @Bind(R.id.ll_leftMenu_bill)
    LinearLayout mBill;
    @Bind(R.id.tv_leftMenu_myAddress_Num)
    TextView mAddressNum;
    @Bind(R.id.ll_leftMenu_myAddress)
    LinearLayout mAddress;
    @Bind(R.id.tv_leftMenu_wallet_Num)
    TextView mLeftWalletNum;
    @Bind(R.id.ll_leftMenu_wallet)
    LinearLayout mWallet;
    @Bind(R.id.tv_leftMenu_ZhongCou_Num)
    TextView mLeftZhongCouNum;
    @Bind(R.id.ll_leftMenu_ZhongCou)
    LinearLayout mZhongCou;
    @Bind(R.id.tv_leftMenu_Carft_Num)
    TextView mLeftCarftNum;
    @Bind(R.id.ll_leftMenu_Carft)
    LinearLayout mCarft;
    @Bind(R.id.tv_leftMenu_MyMsg_Num)
    TextView mMsgNum;
    @Bind(R.id.ll_leftMenu_MyMsg)
    LinearLayout mMyMsg;
    @Bind(R.id.tv_leftMenu_SysMsg_Num)
    TextView mSysMsgNum;
    @Bind(R.id.ll_leftMenu_SysMsg)
    LinearLayout mSysMsg;
    @Bind(R.id.tv_leftMenu_setting_Num)
    TextView mSettingNum;
    @Bind(R.id.ll_leftMenu_setting)
    LinearLayout mSetting;
    @Bind(R.id.ptrsv_leftmenu_layout)
    ScrollView mLeftLayout;
    private View mFragmeView;
    PullToRefreshListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(Tag, "onCreateView");
        mFragmeView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_myusercenter_act, null);
        ButterKnife.bind(this, mFragmeView);
        iniView();
        ViewGroup parent = (ViewGroup) mFragmeView.getParent();
        if (parent != null) {
            parent.removeView(mFragmeView);
        }
        return mFragmeView;
    }

    /**
     * 初始化VIew
     */
    void iniView() {
        findView(mFragmeView);
        initData();
    }

    /**
     * 找控件
     */
    private void findView(View mFragmeView) {

        myinforEdit.setOnClickListener(mLeftMenuListener);
        mAvatar.setOnClickListener(mLeftMenuListener);
        mOrder_all_layout.setOnClickListener(mLeftMenuListener);
        mOrder_Topay_layout.setOnClickListener(mLeftMenuListener);
        mOrder_Torec_layout.setOnClickListener(mLeftMenuListener);
        mOrder_Com_layout.setOnClickListener(mLeftMenuListener);
        mTopic.setOnClickListener(mLeftMenuListener);
        mCollection.setOnClickListener(mLeftMenuListener);
        mCart.setOnClickListener(mLeftMenuListener);
        mBill.setOnClickListener(mLeftMenuListener);

        mWallet.setOnClickListener(mLeftMenuListener);
        mZhongCou.setOnClickListener(mLeftMenuListener);
        mCarft.setOnClickListener(mLeftMenuListener);
        mMyMsg.setOnClickListener(mLeftMenuListener);
        mSysMsg.setOnClickListener(mLeftMenuListener);
        mSetting.setOnClickListener(mLeftMenuListener);
        //TODO 取消这两个点击
        mLeftName.setOnClickListener(mLeftMenuListener);
        mOrder.setOnClickListener(mLeftMenuListener);
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HighCommunityUtils.isLogin(getActivity())) {
                    Intent mAddress = new Intent(getActivity(), GeneratedClassUtils.get(AddressAct.class));
                    mAddress.putExtra(AddressAct.ACTIVITYTAG, "fromSetting");
                    startActivity(mAddress);
                }
            }
        });
        mBean = new PersonalInfoBean();
        myinforEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPHelper.getPersonalInfo(mIbpi2, HighCommunityApplication.mUserInfo.getId() + "");

            }
        });

    }

    BpiHttpHandler.IBpiHttpHandler mIbpi2 = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mBean = (PersonalInfoBean) message;
            Intent mModify = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftSecondAct.class));
            mModify.putExtra(MenuLeftSecondAct.ACTIVITYTAG, Constacts.MENU_LEFTSECOND_PERSONAL);
            mModify.putExtra(MenuLeftSecondAct.INTENTTAG, mBean);
            startActivity(mModify);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolvePersonalInfo(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
    PersonalInfoBean mBean;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            Constacts.mUserCenter = (UserCenterBean) message;
            setleftData();
        }

        @Override
        public Object onResolve(String result) {
            Log.e("renk", result);
            return HTTPHelper.ResolveUserCenter(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
    View.OnClickListener mLeftMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mLeftjump = new Intent(getActivity(),
                    GeneratedClassUtils.get(MenuLeftAct.class));
            if (view.getId() == R.id.ll_leftMenu_setting) {
                mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                        Constacts.MENU_LEFT_SETTING);
                getActivity().startActivity(mLeftjump);
                return;
            }
            if (HighCommunityUtils.isLogin(getActivity())) {
                switch (view.getId()) {
                    case R.id.img_LeftFrag_Avatar:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_USERINFO);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG,
                                HighCommunityApplication.mUserInfo.getId() + "");
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_topic:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_TOPIC);
                        Constacts.mUserCenter.setMessage(0);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_collection:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_COLLECTION);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_wallet:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_WALLET);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_bill:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_BILL);
                        Constacts.mUserCenter.setFee(0);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_Order:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_order_all:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_order_topay:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 1);
                        Constacts.mUserCenter.setOrder(1);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_order_torec:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 2);
                        Constacts.mUserCenter.setOrder(2);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_order_com:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 3);
                        Constacts.mUserCenter.setOrder(3);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_alliance_Order:
                        Intent intent = new Intent(getActivity(),
                                AllianceOrder.class);
                        startActivity(intent);
                        return;
                    case R.id.ll_leftMenu_cart:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_GDCAR);
                        getActivity().startActivity(mLeftjump);
                        // Constacts.mUserCenter.setCart(0);
                        break;
                    case R.id.ll_leftMenu_Carft:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_CARFTS);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_ZhongCou:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ZHONGCOU);
                        Constacts.mUserCenter.setCho(0);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_MyMsg://我的消息
                        Log.d(Tag, "点击我的消息");
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_MESSAGECENTER);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.ll_leftMenu_SysMsg://系统消息
                        Log.d(Tag, "点击系统消息");
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_SYSMESSAGE);
                        Constacts.mUserCenter.setCho(0);
                        getActivity().startActivity(mLeftjump);
                        break;
                    case R.id.tx_LeftFrag_userlocation_layout:
                    case R.id.tx_LeftFrag_userlocation:
//                        VallageAct.toStartAct(this, 1, 1);
                        break;
                }
            }

        }
    };
    public static String TYPE = "type";

    private void setleftData() {
        boolean leftFlag = false, rightFlag = false;
        if (Constacts.mUserCenter != null) {
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + Constacts.mUserCenter.getUserInfo().getHead_pic(), mAvatar);
            mLeftLocation.setText("所在小区:" + Constacts.mUserCenter.getUserInfo().getVillage());
            mLeftLocation_layout.setOnClickListener(mLeftMenuListener);
            mLeftName.setText(Constacts.mUserCenter.getUserInfo().getNick());
            mLeftSex.setText(Constacts.mUserCenter.getUserInfo().getAge());
            if (Constacts.mUserCenter.getUserInfo().getSex().equals("0")) {
                mLeftSex.setSelected(false);
            } else {
                mLeftSex.setSelected(true);
            }
            if (Constacts.mUserCenter.getMessage() == 0) {
                mTopicNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mTopicNum.setVisibility(View.VISIBLE);
                mTopicNum.setText(Constacts.mUserCenter.getMessage() + "");
            }
            if (Constacts.mUserCenter.getFee() == 0) {
                mLeftBillNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftBillNum.setVisibility(View.VISIBLE);
                mLeftBillNum.setText(Constacts.mUserCenter.getFee() + "");
            }
            if (Constacts.mUserCenter.getCart() == 0) {
                mLeftCartNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftCartNum.setVisibility(View.VISIBLE);
                mLeftCartNum.setText(Constacts.mUserCenter.getCart() + "");
            }
            if (Constacts.mUserCenter.getCho() == 0) {
                mLeftZhongCouNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftZhongCouNum.setVisibility(View.VISIBLE);
                mLeftZhongCouNum.setText(Constacts.mUserCenter.getCho() + "");
            }
            if (Constacts.mUserCenter.getWallet() == 0) {
                mLeftWalletNum.setVisibility(View.GONE);
            } else {
                mLeftWalletNum.setVisibility(View.GONE);
                mLeftWalletNum.setText(Constacts.mUserCenter.getWallet() + "");
            }

            if (Constacts.mUserCenter.getSorder() != null) {
                UserCenterBean.UserCenterOrder sorder = Constacts.mUserCenter.getSorder();
                if (Constacts.mUserCenter.getOrder() == 0) {
                    mOrder_allNum.setVisibility(View.GONE);
                } else {
                    leftFlag = true;
                    mOrder_allNum.setVisibility(View.GONE);
                    mOrder_allNum.setText(Constacts.mUserCenter.getOrder() + "");
                }
                if (sorder.getPay() == 0) {
                    mOrder_TopayNum.setVisibility(View.GONE);
                } else {
                    leftFlag = true;
                    mOrder_TopayNum.setVisibility(View.VISIBLE);
                    mOrder_TopayNum.setText(sorder.getPay() + "");
                }
                if (sorder.getConfirm() == 0) {
                    mOrder_TorecNum.setVisibility(View.GONE);
                } else {
                    leftFlag = true;
                    mOrder_TorecNum.setVisibility(View.VISIBLE);
                    mOrder_TorecNum.setText(sorder.getConfirm() + "");
                }
                if (sorder.getComment() == 0) {
                    mOrder_ComNum.setVisibility(View.GONE);
                } else {
                    leftFlag = true;
                    mOrder_ComNum.setVisibility(View.VISIBLE);
                    mOrder_ComNum.setText(sorder.getComment() + "");
                }
            }

            if (leftFlag) {
//                mLeftTop.setVisibility(View.VISIBLE);
            } else {
//                mLeftTop.setVisibility(View.GONE);
            }
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            isFirst = false;
        } else {
            initData();
        }
    }

    private void initData() {
        Log.e(Tag, "initdata");
        HTTPHelper.getUserCenter(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
    }

    boolean isFirst = true;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static int REQUEST_VILLAGE_CODE = 0x15;
    public static int RESULT_VILLAGE_CODE = 0x15;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VILLAGE_CODE && resultCode == RESULT_VILLAGE_CODE) {
            Log.e(Tag, "requestCode==0X1024:" + resultCode);
            Log.e(Tag, "requestCode2:" + requestCode);
            data.getStringExtra("AD");
            Log.e(Tag, "data.getStringExtra(Constacts.SEARCH_RESULT);:" + data.getStringExtra("AD"));
            String address2 = data.getStringExtra("address");
        }
    }
}
