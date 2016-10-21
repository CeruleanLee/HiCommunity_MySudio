package cn.hi028.android.highcommunity.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.duohuo.dhroid.activity.ActivityTack;
import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.activity.fragment.ActFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiLifeFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiLifeFrag_;
import cn.hi028.android.highcommunity.activity.fragment.NeighborFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServiceFrag;
import cn.hi028.android.highcommunity.bean.UserCenterBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.updateutil.UpdateUtil;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import photo.util.Res;

/**
 * 主界面
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener, NeighborFrag.MyChangeListener {
    /**
     * 底部tabs
     **/
    private final int[] tab_menu_text_ID = new int[]{R.id.main_tab_firth,
            R.id.main_tab_second, R.id.main_tab_fourth, R.id.main_tab_five};
    private View[] menu_tvs = null;
    private long exitTime = 0;
    /**
     * 底部tabs 名称
     **/
    private final int[] tab_menu_title = new int[]{R.string.tb_first,
            R.string.tb_second, R.string.tb_fourth, R.id.main_tab_five};
    ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();// frags集合
    BaseFragment actFrag, serviceFrag, huiLifeFrag, neighborFrag;
    private ImageView MiddleButton;
    private RelativeLayout mUserinfo;
    private PullToRefreshScrollView mLeftLayout;
    public SlidingMenu menu;
    private LinearLayout mTopic, mCollection, mBill, mWallet, mSetting, mCart,
            mOrder, mAllianceOrder, mZhongCou, mCarft;
    private TextView mTopicNum, mCollectionNum, mLeftBillNum, mLeftWalletNum,
            mSettingNum, mTitle, mLeftName, mLeftSex, mLeftLocation,
            mLeftCartNum, mLeftOrderNum, mLeftAllianceOrder, mLeftZhongCouNum,
            mLeftCarftNum;
    private CircleImageView mAvatar;
    /**
     * 社区或是群组
     **/
    private RadioGroup mGroup;
    private LinearLayout mStatusHight;
    /**
     * 社区  群组
     **/
    private RadioButton mLeftButton, mRightButton;
    /**
     * 左上角侧滑图片   右上角消息中心   有消息时提示的小红点      左上角订单更新之类的提醒的小红点
     **/
    private ImageView mLeftMenu, mRightMenu, mRightTop, mLeftTop;
    LinearLayout mLeftLocation_layout;
    private int mTouchMode = -1;
    // public static boolean isForeground = false;
    public static String TAG = "MainActivity";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("------------进入onNewIntent");
        setIntent(intent);
//		来自labelact
        if (intent.getIntExtra("communityFlag", 0) == 0x22) {
//            tabSelector(1);
//			NeighborFrag mFra
//
// g = (NeighborFrag) getSupportFragmentManager()
//					.findFragmentByTag(NeighborFrag.FRAGMENTTAG);
            ServiceFrag mFrag = (ServiceFrag)
                    getSupportFragmentManager().findFragmentByTag(ServiceFrag.FRAGMENTTAG);
            if (null == mFrag)
                return;
//			mFrag.setCurrentPage(0);
        } else if (intent.getIntExtra("communityFlag", 0) == 0x222) {
            tabSelector(0);
            NeighborFrag mFrag = (NeighborFrag) getSupportFragmentManager()
                    .findFragmentByTag(NeighborFrag.FRAGMENTTAG);
            if (null == mFrag)
                return;
            mFrag.setCurrentPage(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//		UmengUpdateAgent.setUpdateOnlyWifi(false);
//		UmengUpdateAgent.update(this);
        ActivityTack.getInstanse().clear();
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // // 隐藏状态栏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // //透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        LogUtil.d("----------onCreate1");
        setContentView(R.layout.activity_main);

        LogUtil.d("~~~~~~主界面视图完成");
//检查更新
//        new UpdateUtil(MainActivity.this, getApplicationContext()).checkUpdate();
        new UpdateUtil(MainActivity.this, getApplicationContext()).initUpdate();

        Res.init(this);
        initLeftMenu();
        initView();
        initMenu(savedInstanceState);

//		Toast.makeText(MainActivity.this,"HighCommunityApplication.isAliPayInStalled()"+HighCommunityApplication.isAliPayInStalled(), 0).show();
        if (HighCommunityUtils.isLogin()) {
            JPushInterface.init(getApplicationContext());
            setTag();
            setStyleCustom();
            registerMessageReceiver();
            Log.d("userinfor", "用户信息：" + HighCommunityApplication.mUserInfo.toString());
        }


    }

    public void setTag() {
        LogUtil.d("============================唯一标识：" + HighCommunityApplication.mUserInfo.getId());
        JPushInterface.setAliasAndTags(getApplicationContext(),
                HighCommunityApplication.mUserInfo.getId() + "", null,
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int code, String alias,
                                          Set<String> tags) {
                        String logs;
                        switch (code) {
                            case 0:
                                logs = "Set tag and alias success";
                                Log.i(TAG, logs);
                                break;

                            case 6002:
                                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                                Log.i(TAG, logs);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setTag();
                                    }
                                }, 60 * 6000);
                                break;

                            default:
                                logs = "Failed with errorCode = " + code;
                                Log.e(TAG, logs);
                        }

                    }

                });
    }

    @Override
    protected void onResume() {
        // isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        // isForeground = false;
        // if (HighCommunityUtils.isLogin()) {
        // unregisterReceiver(mMessageReceiver);
        // }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (HighCommunityUtils.isLogin()) {
            unregisterReceiver(mMessageReceiver);
        }
        super.onDestroy();
    }

    // for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "cn.hi028.android.highcommunity.related";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                int extras = intent.getIntExtra(KEY_EXTRAS, 0);
                // StringBuilder showMsg = new StringBuilder();
                // showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (extras == 1) {
                    mRightTop.setVisibility(View.VISIBLE);
                } else {
                    mRightTop.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
                MainActivity.this, R.layout.customer_notitfication_layout,
                R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.drawable.ic_launcher;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (HighCommunityApplication.mUserInfo.getId() != 0) {
            HTTPHelper.getUserCenter(mIbpi,
                    HighCommunityApplication.mUserInfo.getId() + "");
        }
    }

    void initView() {
        mTitle = (TextView) this.findViewById(R.id.tv_mainlevel_title);
//        mStatusHight = (LinearLayout) this
//                .findViewById(R.id.title_status_height);
        mLeftMenu = (ImageView) this.findViewById(R.id.tv_mainlevel_LeftButton);
        mRightMenu = (ImageView) this
                .findViewById(R.id.iv_mainlevel_RightButton);
        mRightTop = (ImageView) this
                .findViewById(R.id.iv_mainlevel_RightNewMessage);
        mLeftTop = (ImageView) this
                .findViewById(R.id.iv_mainlevel_LeftNewMessage);
        mGroup = (RadioGroup) this.findViewById(R.id.rg_maintitle_group);
        mLeftButton = (RadioButton) this
                .findViewById(R.id.rb_maintitle_leftbutton);
        mRightButton = (RadioButton) this
                .findViewById(R.id.rb_maintitle_rightbutton);
        MiddleButton = (ImageView) this.findViewById(R.id.main_tab_third);
        mGroup.setVisibility(View.VISIBLE);
        mLeftMenu.setVisibility(View.VISIBLE);
        mRightMenu.setVisibility(View.VISIBLE);
        mLeftMenu.setOnClickListener(mTitleListener);
        mLeftButton.setOnClickListener(mTitleListener);
        mRightButton.setOnClickListener(mTitleListener);
        mRightMenu.setOnClickListener(mTitleListener);
        mLeftMenu.setOnClickListener(mTitleListener);
//        mStatusHight.setVisibility(View.GONE);
    }

    /**
     * 初始化左侧菜单栏
     */
    private void initLeftMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//// 设置滑动菜单视图的宽度
        menu.setFadeDegree(0.35f);// 设置渐入渐出效果的值
        menu.setEnabled(true);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
        // 为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);
        //找控件
        mLeftLayout = (PullToRefreshScrollView) menu.getMenu().findViewById(
                R.id.ptrsv_leftmenu_layout);
        mUserinfo = (RelativeLayout) menu.getMenu().findViewById(
                R.id.rl_leftmenu_userinfo);
        mTopic = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_topic);
        mCollection = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_collection);
        mCart = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_cart);
        mOrder = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_Order);
        mAllianceOrder = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_alliance_Order);
        mZhongCou = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_ZhongCou);
        mCarft = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_Carft);
        mBill = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_bill);
        mWallet = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_wallet);
        mSetting = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_setting);
        mAvatar = (CircleImageView) menu.getMenu().findViewById(
                R.id.img_LeftFrag_Avatar);
        mLeftLocation = (TextView) menu.getMenu().findViewById(
                R.id.tx_LeftFrag_userlocation);

        mLeftLocation_layout = (LinearLayout) menu.getMenu().findViewById(
                R.id.tx_LeftFrag_userlocation_layout);
        mLeftSex = (TextView) menu.getMenu().findViewById(
                R.id.tx_LeftFrag_userSex);
        mTopicNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_topic_Num);
        mCollectionNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_topic_Num);
        mLeftBillNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_topic_Num);
        mLeftWalletNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_wallet_Num);
        mSettingNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_topic_Num);
        mLeftName = (TextView) menu.getMenu().findViewById(
                R.id.tx_LeftFrag_userName);
        mLeftCartNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_Cart_Num);
        mLeftCarftNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_Carft_Num);
        mLeftOrderNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_Order_Num);
        mLeftAllianceOrder = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_alliance_Order_Num);
        mLeftZhongCouNum = (TextView) menu.getMenu().findViewById(
                R.id.tv_leftMenu_ZhongCou_Num);

        mLeftLayout.setMode(PullToRefreshBase.Mode.DISABLED);
        mUserinfo.setOnClickListener(mLeftMenuListener);
        mTopic.setOnClickListener(mLeftMenuListener);
        mCollection.setOnClickListener(mLeftMenuListener);
        mBill.setOnClickListener(mLeftMenuListener);
        mWallet.setOnClickListener(mLeftMenuListener);
        mSetting.setOnClickListener(mLeftMenuListener);
        mCart.setOnClickListener(mLeftMenuListener);
        mLeftName.setOnClickListener(mLeftMenuListener);
        mAvatar.setOnClickListener(mLeftMenuListener);
        mCarft.setOnClickListener(mLeftMenuListener);
        mZhongCou.setOnClickListener(mLeftMenuListener);
        mOrder.setOnClickListener(mLeftMenuListener);
        mAllianceOrder.setOnClickListener(mLeftMenuListener);
        if (HighCommunityApplication.mUserInfo.getId() != 0)
            HTTPHelper.getUserCenter(mIbpi,
                    HighCommunityApplication.mUserInfo.getId() + "");
        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                if (HighCommunityApplication.mUserInfo.getId() != 0)
                    HTTPHelper.getUserCenter(mIbpi,
                            HighCommunityApplication.mUserInfo.getId() + "");
            }
        });
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

    private void setleftData() {
        boolean leftFlag = false, rightFlag = false;
        if (Constacts.mUserCenter != null) {
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP
                            + Constacts.mUserCenter.getUserInfo().getHead_pic(),
                    mAvatar);
            mLeftLocation.setText("所在小区:"
                    + Constacts.mUserCenter.getUserInfo().getVillage());
            mLeftLocation_layout.setOnClickListener(this);

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

    /**
     * 左侧菜单控件监听器
     */
    View.OnClickListener mLeftMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mLeftjump = new Intent(MainActivity.this,
                    GeneratedClassUtils.get(MenuLeftAct.class));
            if (view.getId() == R.id.ll_leftMenu_setting) {
                mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                        Constacts.MENU_LEFT_SETTING);
                MainActivity.this.startActivity(mLeftjump);
                return;
            }
            if (HighCommunityUtils.isLogin(MainActivity.this)) {
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
                        Intent intent = new Intent(MainActivity.this,
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
                }
                if (view.getId() != R.id.ll_leftMenu_setting) {
                    setleftData();
                    MainActivity.this.startActivity(mLeftjump);
                }
            }

        }
    };

    /**
     * 初始化菜单
     */
    private void initMenu(Bundle savedInstanceState) {
        LogUtil.d("------------进入initMenu");
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }
        neighborFrag = new NeighborFrag();
        serviceFrag = new ServiceFrag();
        huiLifeFrag = new HuiLifeFrag_();
        actFrag = new ActFrag();
        fragments.add(neighborFrag);
        fragments.add(serviceFrag);
        fragments.add(huiLifeFrag);
        fragments.add(actFrag);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_Content, neighborFrag, NeighborFrag.FRAGMENTTAG)
                .show(neighborFrag)
                .add(R.id.main_Content, huiLifeFrag, HuiLifeFrag.FRAGMENTTAG)
                .hide(huiLifeFrag)
                .add(R.id.main_Content, actFrag, ActFrag.FRAGMENTTAG)
                .hide(actFrag)
                .add(R.id.main_Content, serviceFrag, ServiceFrag.FRAGMENTTAG)
                .hide(serviceFrag).commit();
        menu_tvs = new TextView[tab_menu_text_ID.length];
        for (int i = 0; i < tab_menu_text_ID.length; i++) {
            menu_tvs[i] = (TextView) findViewById(tab_menu_text_ID[i]);
            menu_tvs[i].setOnClickListener(this);
        }
        MiddleButton.setOnClickListener(this);
        LogUtil.d("------------进入initMenu   end");
        tabSelector(1);
        mTitle.setText("服务");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void getMsgNum() {
        HTTPHelper.getMsgNum(new BpiHttpHandler.IBpiHttpHandler() {
            @Override
            public void onError(int id, String message) {

            }

            @Override
            public void onSuccess(Object message) {
                if (message != null) {
                    Integer msgNum = (Integer) message;
                    if (msgNum == 0) {
                        mRightTop.setVisibility(View.GONE);
                    } else {
                        mRightTop.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public Object onResolve(String result) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                } catch (JSONException e) {
                    json = new JSONObject();
                }
                return json.optInt("tips", 0);
            }

            @Override
            public void setAsyncTask(AsyncTask asyncTask) {

            }

            @Override
            public void cancleAsyncTask() {

            }
        });
    }

    /**
     * 菜单模块选择
     *
     * @param position
     */
    private void tabSelector(int position) {
        getMsgNum();
        LogUtil.d("------------tabSelector" + position);
        if (0 == position) {
            mTitle.setVisibility(View.GONE);
            mGroup.setVisibility(View.VISIBLE);
            if (mTouchMode != -1 && menu.getTouchModeAbove() != mTouchMode)
                menu.setTouchModeAbove(mTouchMode);
        } else {
            mGroup.setVisibility(View.GONE);
            mTitle.setVisibility(View.VISIBLE);
            if (position != 2) {
                if (menu.getTouchModeAbove() != SlidingMenu.TOUCHMODE_FULLSCREEN)
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            } else {
                if (menu.getTouchModeAbove() == SlidingMenu.TOUCHMODE_FULLSCREEN)
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < tab_menu_text_ID.length; i++) {//
            if (position == i) {
                menu_tvs[i].setSelected(true);
            } else {
                menu_tvs[i].setSelected(false);
            }
            if (position == i) {
                //TODO
                //这里出了问题 明天调试
                ft.show(fragments.get(i));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab_firth://
                tabSelector(0);
                break;
            case R.id.main_tab_second://
                tabSelector(1);
                mTitle.setText("服务");
                break;
            case R.id.main_tab_third://
                if (HighCommunityUtils.GetInstantiation()
                        .isLogin(MainActivity.this)) {
                    Intent mLabel = new Intent(this,
                            GeneratedClassUtils.get(LabelAct.class));
                    startActivity(mLabel);
                }
                break;
            case R.id.main_tab_fourth://
                mTitle.setText("惠生活");
                tabSelector(2);
                break;
            case R.id.main_tab_five://
                mTitle.setText("活动");
                tabSelector(3);
                break;
            case R.id.tx_LeftFrag_userlocation_layout:
            case R.id.tx_LeftFrag_userlocation:
                VallageAct.toStartAct(MainActivity.this, 1, false);
                // finish();
                break;
            default:
                break;
        }
    }

    View.OnClickListener mTitleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NeighborFrag mFrag = (NeighborFrag) getSupportFragmentManager()
                    .findFragmentByTag(NeighborFrag.FRAGMENTTAG);
            if (null == mFrag)
                return;
            switch (view.getId()) {
                case R.id.rb_maintitle_leftbutton:
                    mFrag.setCurrentPage(0);
                    break;
                case R.id.rb_maintitle_rightbutton:
                    mFrag.setCurrentPage(1);
                    break;
                case R.id.tv_mainlevel_LeftButton:
                    mLeftTop.setVisibility(View.GONE);
                    menu.showMenu();
                    break;
                case R.id.iv_mainlevel_RightButton:
                    if (HighCommunityUtils.isLogin(MainActivity.this)) {
                        mRightTop.setVisibility(View.GONE);
                        Intent mLeftjump = new Intent(MainActivity.this,
                                GeneratedClassUtils.get(MenuLeftAct.class));
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_MESSAGECENTER);
                        startActivity(mLeftjump);
                    }
                    break;
            }
        }
    };

    @Override
    public void onChange(boolean flag) {
        if (flag) {
            mLeftButton.setChecked(true);
            mRightButton.setChecked(false);
            mTouchMode = SlidingMenu.TOUCHMODE_FULLSCREEN;
            menu.setTouchModeAbove(mTouchMode);
        } else {
            mLeftButton.setChecked(false);
            mRightButton.setChecked(true);
            mTouchMode = SlidingMenu.TOUCHMODE_NONE;
            menu.setTouchModeAbove(mTouchMode);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (((NeighborFrag) fragments.get(0)).onKeyDown(keyCode, event)) {
                return true;
            } else {
                exit();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            HighCommunityUtils.GetInstantiation().ShowToast(
                    getResources().getString(R.string.toast_press_to_exit), 0);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        Log.e("renk", "onactivityresult");
        Log.e("renk", "onactivityresult");
        super.onActivityResult(arg0, arg1, arg2);
    }
}
