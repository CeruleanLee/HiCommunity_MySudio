/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.don.view.DrawableCenterTextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
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
public class ActFrag extends BaseFragment {
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
    private int mTouchMode = -1;
    // public static boolean isForeground = false;

    private PopupWindow mWindow;

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
        Log.e(Tag, "iniView");
//		 mListView.setEmptyView(mNodata);
//		 mListView.setAdapter(mAdapter);
//		 mListView.setMode(PullToRefreshBase.Mode.BOTH);
        findView(mFragmeView);

        initDatas();
    }

    private void initDatas() {


    }

    /**
     * 找控件
     */
    private void findView(View mFragmeView) {

//
//        mLeftLayout = (PullToRefreshScrollView) mFragmeView.findViewById(R.id.ptrsv_leftmenu_layout);
//        mUserinfo = (RelativeLayout) mFragmeView.findViewById(R.id.rl_leftmenu_userinfo);
//        mTopic = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_topic);
//        mCollection = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_collection);
//        mCart = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_cart);
//        mOrder = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_Order);
//        mAllianceOrder = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_alliance_Order);
//        mZhongCou = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_ZhongCou);
//        mMyMsg = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_MyMsg);
//        mSysMsg = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_SysMsg);
//        mCarft = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_Carft);
//        mBill = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_bill);
//        mWallet = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_wallet);
//        mSetting = (LinearLayout) mFragmeView.findViewById(R.id.ll_leftMenu_setting);
//        mAvatar = (CircleImageView) mFragmeView.findViewById(R.id.img_LeftFrag_Avatar);
//        mLeftLocation = (TextView) mFragmeView.findViewById(R.id.tx_LeftFrag_userlocation);
//
//        mLeftLocation_layout = (LinearLayout) mFragmeView.findViewById(R.id.tx_LeftFrag_userlocation_layout);
//        mLeftSex = (TextView) mFragmeView.findViewById(R.id.tx_LeftFrag_userSex);
//        mTopicNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_topic_Num);
//        mCollectionNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_topic_Num);
//        mLeftBillNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_topic_Num);
//        mLeftWalletNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_wallet_Num);
//        mSettingNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_topic_Num);
//        mLeftName = (TextView) mFragmeView.findViewById(R.id.tx_LeftFrag_userName);
//        mLeftCartNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_Cart_Num);
//        mLeftCarftNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_Carft_Num);
//        mLeftOrderNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_Order_Num);
//        mLeftAllianceOrder = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_alliance_Order_Num);
//        mLeftZhongCouNum = (TextView) mFragmeView.findViewById(R.id.tv_leftMenu_ZhongCou_Num);

        myinforEdit.setOnClickListener(mLeftMenuListener);
        mAvatar.setOnClickListener(mLeftMenuListener);
//        mUserinfo.setOnClickListener(mLeftMenuListener);
        //TODO  新增
        mOrder_all_layout.setOnClickListener(mLeftMenuListener);
        mOrder_Topay_layout.setOnClickListener(mLeftMenuListener);
        mOrder_Torec_layout.setOnClickListener(mLeftMenuListener);
        mOrder_Com_layout.setOnClickListener(mLeftMenuListener);

        mTopic.setOnClickListener(mLeftMenuListener);
        mCollection.setOnClickListener(mLeftMenuListener);
        mCart.setOnClickListener(mLeftMenuListener);
        mBill.setOnClickListener(mLeftMenuListener);
        //TODO
        mAddress.setOnClickListener(mLeftMenuListener);

        mWallet.setOnClickListener(mLeftMenuListener);
        mZhongCou.setOnClickListener(mLeftMenuListener);
        mCarft.setOnClickListener(mLeftMenuListener);
        mMyMsg.setOnClickListener(mLeftMenuListener);
        mSysMsg.setOnClickListener(mLeftMenuListener);
        mSetting.setOnClickListener(mLeftMenuListener);
        //TODO 取消这两个点击
        mLeftName.setOnClickListener(mLeftMenuListener);
        mOrder.setOnClickListener(mLeftMenuListener);
        //
        if (HighCommunityApplication.mUserInfo.getId() != 0)
            HTTPHelper.getUserCenter(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");

    }

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
    };
    /**
     * 左侧菜单控件监听器
     */
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
                        //TODO
                        Toast.makeText(getActivity(), "头像大图", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myinfor_edit:

//                    case R.id.rl_leftmenu_userinfo:
                    case R.id.tx_LeftFrag_userName:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_USERINFO);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG,
                                HighCommunityApplication.mUserInfo.getId() + "");
                        break;
                    case R.id.ll_leftMenu_topic:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_TOPIC);
                        Constacts.mUserCenter.setMessage(0);
                        break;
                    case R.id.ll_leftMenu_collection:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_COLLECTION);
                        break;
                    case R.id.ll_leftMenu_wallet:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_WALLET);
                        break;
                    case R.id.ll_leftMenu_bill:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_BILL);
                        Constacts.mUserCenter.setFee(0);
                        break;
                    case R.id.ll_leftMenu_Order:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        break;
                    case R.id.ll_leftMenu_order_all:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        break;
                    case R.id.ll_leftMenu_order_topay:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        break;
                    case R.id.ll_leftMenu_order_torec:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        break;
                    case R.id.ll_leftMenu_order_com:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ORDER);
                        mLeftjump.putExtra(MenuLeftAct.INTENTTAG, 0);
                        Constacts.mUserCenter.setOrder(0);
                        break;
                    case R.id.ll_leftMenu_alliance_Order:
                        Intent intent = new Intent(getActivity(),
                                AllianceOrder.class);
                        startActivity(intent);
                        return;
                    case R.id.ll_leftMenu_cart:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_GDCAR);
                        // Constacts.mUserCenter.setCart(0);
                        break;
                    case R.id.ll_leftMenu_Carft:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_CARFTS);
                        break;
                    case R.id.ll_leftMenu_ZhongCou:
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_ZHONGCOU);
                        Constacts.mUserCenter.setCho(0);
                        break;
                    case R.id.ll_leftMenu_MyMsg://我的消息
                        Log.d(Tag, "点击我的消息");
//                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
//                                Constacts.MENU_MYMESSAGE);
//                        Constacts.mUserCenter.setCho(0);
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_MESSAGECENTER);
//                        if (HighCommunityUtils.isLogin(getActivity())) {
//                            mRightTop.setVisibility(View.GONE);
////                            Intent mLeftjump = new Intent(getActivity(),
////                                    GeneratedClassUtils.get(MenuLeftAct.class));
//                            mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
//                                    Constacts.MENU_LEFT_MESSAGECENTER);
////                            startActivity(mLeftjump);
//                        }
                        break;
                    case R.id.ll_leftMenu_SysMsg://系统消息
                        Log.d(Tag, "点击系统消息");

                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_SYSMESSAGE);
                        Constacts.mUserCenter.setCho(0);
                        break;
                }
                if (view.getId() != R.id.ll_leftMenu_setting) {
//					setleftData();
                    getActivity().startActivity(mLeftjump);
                }
            }

        }
    };

    private void setleftData() {
        boolean leftFlag = false, rightFlag = false;
        if (Constacts.mUserCenter != null) {
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + Constacts.mUserCenter.getUserInfo().getHead_pic(), mAvatar);
            mLeftLocation.setText("所在小区:" + Constacts.mUserCenter.getUserInfo().getVillage());
//            mLeftLocation_layout.setOnClickListener(this);
            mLeftName.setText(Constacts.mUserCenter.getUserInfo().getNick());
            mLeftSex.setText(Constacts.mUserCenter.getUserInfo().getAge());
            if (Constacts.mUserCenter.getUserInfo().getSex().equals("0")) {
                mLeftSex.setSelected(false);
            } else {
                mLeftSex.setSelected(true);
            }
            mTopicNum.setVisibility(View.GONE);
            // if (Constacts.mUserCenter.getMessage() == 0) {
            // } else {
            // leftFlag = true;
            // mTopicNum.setVisibility(View.VISIBLE);
            // mTopicNum.setText("+" + Constacts.mUserCenter.getMessage());
            // }
            if (Constacts.mUserCenter.getOrder() == 0) {
                mLeftOrderNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftOrderNum.setVisibility(View.VISIBLE);
                mLeftOrderNum.setText(Constacts.mUserCenter.getOrder() + "");
            }
            if (Constacts.mUserCenter.getCart() == 0) {
                mLeftCartNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftCartNum.setVisibility(View.VISIBLE);
                mLeftCartNum.setText(Constacts.mUserCenter.getCart() + "");
            }
            if (Constacts.mUserCenter.getFee() == 0) {
                mLeftBillNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftBillNum.setVisibility(View.VISIBLE);
                mLeftBillNum.setText(Constacts.mUserCenter.getFee() + "");
            }
            if (Constacts.mUserCenter.getCho() == 0) {
                mLeftZhongCouNum.setVisibility(View.GONE);
            } else {
                leftFlag = true;
                mLeftZhongCouNum.setVisibility(View.VISIBLE);
                mLeftZhongCouNum.setText(Constacts.mUserCenter.getCho() + "");
            }
            mLeftWalletNum.setVisibility(View.GONE);
            // if (Constacts.mUserCenter.getWallet() == 0) {
            // } else {
            // mLeftWalletNum.setVisibility(View.VISIBLE);
            // mLeftWalletNum.setText(Constacts.mUserCenter.getWallet());
            // }
            if (leftFlag) {
//                mLeftTop.setVisibility(View.VISIBLE);
            } else {
//                mLeftTop.setVisibility(View.GONE);
            }
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
