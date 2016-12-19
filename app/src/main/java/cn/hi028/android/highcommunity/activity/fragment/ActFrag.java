/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：新版我的<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/19<br>
 */
public class ActFrag extends BaseFragment {
    public static final String Tag = "~~~ActFrag~~~";
    public static final String FRAGMENTTAG = "ActFrag";
    private View mFragmeView;
    PullToRefreshListView mListView;
    private LinearLayout mTopic, mCollection, mBill, mWallet, mSetting, mCart,
            mOrder, mAllianceOrder, mZhongCou, mCarft,mMyMsg,mSysMsg;;
    private TextView mTopicNum, mCollectionNum, mLeftBillNum, mLeftWalletNum,
            mSettingNum, mTitle, mLeftName, mLeftSex, mLeftLocation,
            mLeftCartNum, mLeftOrderNum, mLeftAllianceOrder, mLeftZhongCouNum,
            mLeftCarftNum;
    private CircleImageView mAvatar;
    /**社区或是群组  惠生活：0直供 1众筹**/
    private RadioGroup mGroup,mNewHuiLifeGroup;
    private LinearLayout mStatusHight;
    /**社区  群组 直供  众筹**/
    private RadioButton mLeftButton, mRightButton,mSupplyBut,mChipsBut;
    /**左上角侧滑图片   右上角消息中心   有消息时提示的小红点      左上角订单更新之类的提醒的小红点**/
    private ImageView mLeftMenu, mRightMenu, mRightTop, mLeftTop;
    LinearLayout mLeftLocation_layout;
    private int mTouchMode = -1;
    // public static boolean isForeground = false;
    public static String TAG = "MainActivity";

    private PopupWindow mWindow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(Tag, "onCreateView");
        if (mFragmeView == null) {
            iniView();
        }
        ViewGroup parent = (ViewGroup) mFragmeView.getParent();
        if (parent != null)
            parent.removeView(mFragmeView);
        return mFragmeView;
    }

    /**
     * 初始化VIew
     */
    void iniView() {
        Log.e(Tag, "iniView");
        mFragmeView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_myusercenter_act, null);
//		 mListView.setEmptyView(mNodata);
//		 mListView.setAdapter(mAdapter);
//		 mListView.setMode(PullToRefreshBase.Mode.BOTH);


        initDatas();
    }

    private void initDatas() {


    }
    /**
     * 初始化左侧菜单栏
     */
    private void initLeftMenu() {
//        menu = new SlidingMenu(this);
//        menu.setMode(SlidingMenu.LEFT);
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//// 设置滑动菜单视图的宽度
//        menu.setFadeDegree(0.35f);// 设置渐入渐出效果的值
//        menu.setEnabled(true);
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
//        // 为侧滑菜单设置布局
//        menu.setMenu(R.layout.leftmenu);
//        //找控件
//        mLeftLayout = (PullToRefreshScrollView) menu.getMenu().findViewById(
//                R.id.ptrsv_leftmenu_layout);
//        mUserinfo = (RelativeLayout) menu.getMenu().findViewById(
//                R.id.rl_leftmenu_userinfo);
//        mTopic = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_topic);
//        mCollection = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_collection);
//        mCart = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_cart);
//        mOrder = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_Order);
//        mAllianceOrder = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_alliance_Order);
//        mZhongCou = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_ZhongCou);
//        mMyMsg = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_MyMsg);
//        mSysMsg = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_SysMsg);
//        mCarft = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_Carft);
//        mBill = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_bill);
//        mWallet = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_wallet);
//        mSetting = (LinearLayout) menu.getMenu().findViewById(
//                R.id.ll_leftMenu_setting);
//        mAvatar = (CircleImageView) menu.getMenu().findViewById(
//                R.id.img_LeftFrag_Avatar);
//        mLeftLocation = (TextView) menu.getMenu().findViewById(
//                R.id.tx_LeftFrag_userlocation);
//
//        mLeftLocation_layout = (LinearLayout) menu.getMenu().findViewById(
//                R.id.tx_LeftFrag_userlocation_layout);
//        mLeftSex = (TextView) menu.getMenu().findViewById(
//                R.id.tx_LeftFrag_userSex);
//        mTopicNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_topic_Num);
//        mCollectionNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_topic_Num);
//        mLeftBillNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_topic_Num);
//        mLeftWalletNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_wallet_Num);
//        mSettingNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_topic_Num);
//        mLeftName = (TextView) menu.getMenu().findViewById(
//                R.id.tx_LeftFrag_userName);
//        mLeftCartNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_Cart_Num);
//        mLeftCarftNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_Carft_Num);
//        mLeftOrderNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_Order_Num);
//        mLeftAllianceOrder = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_alliance_Order_Num);
//        mLeftZhongCouNum = (TextView) menu.getMenu().findViewById(
//                R.id.tv_leftMenu_ZhongCou_Num);
//
//        mLeftLayout.setMode(PullToRefreshBase.Mode.DISABLED);
//        mUserinfo.setOnClickListener(mLeftMenuListener);
//        mTopic.setOnClickListener(mLeftMenuListener);
//        mCollection.setOnClickListener(mLeftMenuListener);
//        mBill.setOnClickListener(mLeftMenuListener);
//        mWallet.setOnClickListener(mLeftMenuListener);
//        mSetting.setOnClickListener(mLeftMenuListener);
//        mCart.setOnClickListener(mLeftMenuListener);
//        mLeftName.setOnClickListener(mLeftMenuListener);
//        mAvatar.setOnClickListener(mLeftMenuListener);
//        mCarft.setOnClickListener(mLeftMenuListener);
//        mZhongCou.setOnClickListener(mLeftMenuListener);
//        mOrder.setOnClickListener(mLeftMenuListener);
//        mAllianceOrder.setOnClickListener(mLeftMenuListener);
//        //
//        mMyMsg.setOnClickListener(mLeftMenuListener);
//        mSysMsg.setOnClickListener(mLeftMenuListener);
//        if (HighCommunityApplication.mUserInfo.getId() != 0)
//            HTTPHelper.getUserCenter(mIbpi,
//                    HighCommunityApplication.mUserInfo.getId() + "");
//        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
//            @Override
//            public void onOpened() {
//                if (HighCommunityApplication.mUserInfo.getId() != 0)
//                    HTTPHelper.getUserCenter(mIbpi,
//                            HighCommunityApplication.mUserInfo.getId() + "");
//            }
//        });
//        menu.setVisibility(View.GONE);
    }

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
                    case R.id.rl_leftmenu_userinfo:
                    case R.id.img_LeftFrag_Avatar:
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
            if (Constacts.mUserCenter.getNearby() == 0) {
                mLeftAllianceOrder.setVisibility(View.GONE);
            } else {
                mLeftAllianceOrder.setVisibility(View.VISIBLE);
                mLeftAllianceOrder.setText(Constacts.mUserCenter.getNearby()
                        + "");

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
                mLeftTop.setVisibility(View.VISIBLE);
            } else {
                mLeftTop.setVisibility(View.GONE);
            }
        } else {

        }
    }

}
