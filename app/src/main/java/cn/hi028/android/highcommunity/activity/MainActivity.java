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
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jaeger.library.StatusBarUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.duohuo.dhroid.activity.ActivityTack;
import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.activity.fragment.ActFrag;
import cn.hi028.android.highcommunity.activity.fragment.HuiLifeFrag;
import cn.hi028.android.highcommunity.activity.fragment.NeighborFrag;
import cn.hi028.android.highcommunity.activity.fragment.ServiceFrag;
import cn.hi028.android.highcommunity.bean.UserCenterBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.MyLocationListener;
import cn.hi028.android.highcommunity.utils.NToast;
import cn.hi028.android.highcommunity.utils.updateutil.UpdateUtil;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import photo.util.Res;

/**
 * 主界面
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener, NeighborFrag.MyChangeListener,HuiLifeFrag.MyChangeListener4HuiLife {
    static  final String Tag="MainActivity--->";
    public static String TAG = "MainActivity--->";

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
    private final int[] tab_menu_title = new int[]{R.string.tb_first, R.string.tb_second, R.string.tb_fourth, R.id.main_tab_five};
    ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();// frags集合
    BaseFragment actFrag, serviceFrag, huiLifeFrag, neighborFrag;
    private ImageView MiddleButton;
    private RelativeLayout mUserinfo;
    private PullToRefreshScrollView mLeftLayout;
    public SlidingMenu menu;
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
    private RadioButton mLeftButton, mRightButton,mRightButton_right,mSupplyBut,mChipsBut;
    /**左上角侧滑图片   右上角消息中心   有消息时提示的小红点      左上角订单更新之类的提醒的小红点**/
    private ImageView mLeftMenu, mRightMenu, mRightTop, mLeftTop;
    LinearLayout mLeftLocation_layout;
    private int mTouchMode = -1;

    private PopupWindow mWindow;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//		来自labelact
        if (intent.getIntExtra("communityFlag", 0) == 0x22) {
//            tabSelector(1);
//			NeighborFrag mFrag = (NeighborFrag) getSupportFragmentManager()
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
        }else if (intent.getIntExtra("actFlag", 0) == 0x66) {
            tabSelector(3);
            ActFrag mFrag = (ActFrag) getSupportFragmentManager()
                    .findFragmentByTag(ActFrag.FRAGMENTTAG);
            if (null == mFrag)
                return;
//            mFrag.setCurrentPage(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_main);
        //检查更新
        new UpdateUtil(MainActivity.this, getApplicationContext()).initUpdate();
        Res.init(this);
        //TODO 还没调试完
        Log.d(TAG, "connect  1");
//调试   福利彩票
        connect("F2sMSChp+KkI6SSaEWUnITkc36aZPpJZqj81iEXN8zId1Zx5/yGTsbphE0dY6jOh96NnNqVdWJIYgvzyeiwnVg==");
        Log.d(TAG, "connect  2");

        initView();
        initLeftMenu();
        initMenu(savedInstanceState);

        if (HighCommunityUtils.isLogin()) {
            JPushInterface.init(getApplicationContext());
            setTag();
            setStyleCustom();
            registerMessageReceiver();
            Log.d("userinfor", "用户信息：" + HighCommunityApplication.mUserInfo.toString());
        }
        Log.e(TAG,"准备定位");
        mLocationClient = new LocationClient(this); //声明LocationClie
        Log.e(TAG,"准备定位2");
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.disableCache(false);//禁止启用缓存定位
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                Log.i("BaiduLocationApiDem","1,"+ location.getAddress().address+"2,"+location.getAddress().city+"3,"+
                        location.getAddrStr()+"4,"+location.getCity()+"5,"+location.getCountry()+"6,"+location.getStreet()+"7,"+
                        location.hasAddr()
                );
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ntostring : ");
                    sb.append(location.toString());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.i("BaiduLocationApiDem", sb.toString());
                Toast.makeText(MainActivity.this,"定位信息："+location.getCity()+" "+location.getDistrict()+" "+location.getStreet()+" "+location.getAddress(),Toast.LENGTH_SHORT).show();
            }
        });//注册监听函数
    }
    public void setTag() {
        Log.d(Tag,"唯一标识：" + HighCommunityApplication.mUserInfo.getId());
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
        super.onResume();
        int checkNum=getIntent().getIntExtra("checkupdata",-1);
        if (checkNum==6){
            boolean isUpdate=  new UpdateUtil(MainActivity.this,getApplicationContext()).checkIsToUpdate();
            if (isUpdate){
                new UpdateUtil(MainActivity.this, getApplicationContext()).initUpdate();
            }else{
                Toast.makeText(MainActivity.this, "已经是最新版本了", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        if (HighCommunityUtils.isLogin()) {
            unregisterReceiver(mMessageReceiver);
        }
        super.onDestroy();
    }

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

    @Override
    public void onHuiLifeChange(int i) {

        if (i==0) {//直供选中
            mSupplyBut.setChecked(true);
            mChipsBut.setChecked(false);

        } else if (i==1){//众筹选中
            mSupplyBut.setChecked(false);
            mChipsBut.setChecked(true);
;
        }

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                int extras = intent.getIntExtra(KEY_EXTRAS, 0);
                if (extras == 1) {
                    mRightTop.setVisibility(View.GONE);
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
    public void goneTitleBar(){
        Log.e(Tag, "goneTitleBar ");

        titleBar.setVisibility(View.GONE);
        Log.e(Tag, "goneTitleBar ok");


    }
    public void visibleTitleBar(){
        Log.e(Tag, "goneTitleBar ");

        titleBar.setVisibility(View.GONE);
        Log.e(Tag, "goneTitleBar ok");


    }
View titleBar;
    void initView() {
        titleBar =this.findViewById(R.id.title);
        mTitle = (TextView) this.findViewById(R.id.tv_mainlevel_title);
        mLeftMenu = (ImageView) this.findViewById(R.id.tv_mainlevel_LeftButton);
        mRightMenu = (ImageView) this.findViewById(R.id.iv_mainlevel_RightButton);
        mRightTop = (ImageView) this.findViewById(R.id.iv_mainlevel_RightNewMessage);
        mLeftTop = (ImageView) this.findViewById(R.id.iv_mainlevel_LeftNewMessage);
        mGroup = (RadioGroup) this.findViewById(R.id.rg_maintitle_group);
        mNewHuiLifeGroup = (RadioGroup) this.findViewById(R.id.rg_title_newHuilifegroup);
        mLeftButton = (RadioButton) this.findViewById(R.id.rb_maintitle_leftbutton);
        mRightButton = (RadioButton) this.findViewById(R.id.rb_maintitle_rightbutton);
        mRightButton_right = (RadioButton) this.findViewById(R.id.rb_maintitle_rightbutton_right);
        mSupplyBut = (RadioButton) this.findViewById(R.id.rb_title_supply);
        mChipsBut = (RadioButton) this.findViewById(R.id.rb_title_chips);
        MiddleButton = (ImageView) this.findViewById(R.id.main_tab_third);
        mGroup.setVisibility(View.VISIBLE);
        mNewHuiLifeGroup.setVisibility(View.VISIBLE);
        mRightMenu.setVisibility(View.VISIBLE);
        mLeftButton.setOnClickListener(mTitleListener);
        mRightButton.setOnClickListener(mTitleListener);
        mRightButton_right.setOnClickListener(mTitleListener);
        mSupplyBut.setOnClickListener(mTitleListener);
        mChipsBut.setOnClickListener(mTitleListener);
        mRightMenu.setOnClickListener(mTitleListener);
        goneTitleBar();
    }

    /**
     * 初始化左侧菜单栏
     */
    private void initLeftMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
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
        mMyMsg = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_MyMsg);
        mSysMsg = (LinearLayout) menu.getMenu().findViewById(
                R.id.ll_leftMenu_SysMsg);
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
        //
        mMyMsg.setOnClickListener(mLeftMenuListener);
        mSysMsg.setOnClickListener(mLeftMenuListener);
        if (HighCommunityApplication.mUserInfo.getId() != 0)
            HTTPHelper.getUserCenter(mIbpi,
                    HighCommunityApplication.mUserInfo.getId() + "");
        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                if (HighCommunityApplication.mUserInfo.getId() != 0){
                }
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

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(MainActivity.this);
            }
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
            if (leftFlag) {
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
                    case R.id.ll_leftMenu_MyMsg://我的消息
                        Log.d(TAG,"点击我的消息");
                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_LEFT_MESSAGECENTER);
                        break;
                    case R.id.ll_leftMenu_SysMsg://系统消息
                        Log.d(TAG,"点击系统消息");

                        mLeftjump.putExtra(MenuLeftAct.ACTIVITYTAG,
                                Constacts.MENU_SYSMESSAGE);
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
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }
        neighborFrag = new NeighborFrag();
        serviceFrag = new ServiceFrag();
        huiLifeFrag = new HuiLifeFrag();
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
                        mRightTop.setVisibility(View.GONE);
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

            @Override
            public void shouldLogin(boolean isShouldLogin) {

            }

            @Override
            public void shouldLoginAgain(boolean isShouldLogin, String msg) {
                if (isShouldLogin){
                    HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                    HighCommunityApplication.toLoginAgain(MainActivity.this);
                }
            }
        });
    }

    /**
     * 菜单模块选择
     *
     * @param position 0-邻里 1-服务 2-惠生活 3-活动
     */
    private void tabSelector(int position) {
        Log.d(Tag,"tabSelector:" + position);
        if (0 == position) {
            mTitle.setVisibility(View.GONE);
            mGroup.setVisibility(View.VISIBLE);
            mNewHuiLifeGroup.setVisibility(View.GONE);

        } else if (position==2){
            mTitle.setVisibility(View.GONE);
            mGroup.setVisibility(View.GONE);
            mNewHuiLifeGroup.setVisibility(View.VISIBLE);

        }else {
            mGroup.setVisibility(View.GONE);
            mTitle.setVisibility(View.VISIBLE);
            mNewHuiLifeGroup.setVisibility(View.GONE);

        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < tab_menu_text_ID.length; i++) {//
            if (position == i) {
                menu_tvs[i].setSelected(true);
            } else {
                menu_tvs[i].setSelected(false);
            }
            if (i==position) {
                //TODO
                Log.e(Tag,"选中的position："+i+",fragment"+fragments.get(position).getTag());
                ft.show(fragments.get(position));
//                ft.commit();

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
                if (HighCommunityUtils.GetInstantiation().isLogin(MainActivity.this)) {
                    Intent mLabel = new Intent(this, LabelAct.class);
                    startActivity(mLabel);
                }
                break;
            case R.id.main_tab_fourth://
                mTitle.setText("惠生活");
                tabSelector(2);
                break;
            case R.id.main_tab_five://
                mTitle.setText(" ");
                tabSelector(3);
                break;
            case R.id.tx_LeftFrag_userlocation_layout:
            case R.id.tx_LeftFrag_userlocation:
                VallageAct.toStartAct(MainActivity.this, 1, false);
                break;
            default:
                break;
        }
    }

    View.OnClickListener mTitleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NeighborFrag mFrag = (NeighborFrag) getSupportFragmentManager().findFragmentByTag(NeighborFrag.FRAGMENTTAG);
            HuiLifeFrag mHuiLifeFrag = (HuiLifeFrag) getSupportFragmentManager().findFragmentByTag(HuiLifeFrag.FRAGMENTTAG);
            if (null == mFrag)
                return;
            switch (view.getId()) {
                case R.id.rb_maintitle_leftbutton:
                    mFrag.setCurrentPage(0);
                    break;
                case R.id.rb_maintitle_rightbutton:
                    mFrag.setCurrentPage(1);
                    break;
                case R.id.rb_maintitle_rightbutton_right:
                    mFrag.setCurrentPage(2);
                    break;
                case R.id.rb_title_supply://直供
                    mHuiLifeFrag.setCurrentPage(0);
                    break;
                case R.id.rb_title_chips://众筹
                    mHuiLifeFrag.setCurrentPage(1);
                    break;
                case R.id.tv_mainlevel_LeftButton:
                    int requestLocation=-1;
                    Toast.makeText(getApplicationContext(),"点击左上角",Toast.LENGTH_SHORT).show();
                    if (mLocationClient.isStarted()){
                        Log.e(TAG,"isStarted--->");

                        mLocationClient.stop();

                    }else{
                        mLocationClient.start();
                        mLocationClient.requestLocation();
                    }
                    Log.e(TAG,"requestLocation--->");
                    break;
                case R.id.iv_mainlevel_RightButton:
                    if (HighCommunityUtils.isLogin(MainActivity.this)) {

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    break;
            }
        }
    };
    private final static int SCANNIN_GREQUEST_CODE = 1;
    public static LocationClient mLocationClient=null;
    public BDLocationListener myListener = new MyLocationListener();
    @Override
    public void onChange(int flag) {
        if (flag==0) {
            mLeftButton.setChecked(true);
            mRightButton.setChecked(false);
            mRightButton_right.setChecked(false);
        } else if (flag==1){
            mLeftButton.setChecked(false);
            mRightButton.setChecked(true);
            mRightButton_right.setChecked(false);
        }else if (flag==2){
            mRightButton_right.setChecked(true);
            mLeftButton.setChecked(false);
            mRightButton.setChecked(false);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("renk", "onactivityresult");
        Log.e("renk", "onactivityresult");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
//                    //显示扫描到的内容
                    Log.e(TAG,"扫描到的内容:"+bundle.getString("result"));
//                    mTextView.setText(bundle.getString("result"));
//                    //显示
//                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }

    }
    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this,0, null);
        StatusBarUtil.setColor(this,0xffffffff);
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link_init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param——callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {
        Log.d(Tag, "--connect" );

        if (getApplicationInfo().packageName.equals(HighCommunityApplication.getCurProcessName(getApplicationContext()))) {
            Log.d(Tag, "--connect  ===" );

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d(Tag, "--onTokenIncorrect" );

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d(Tag, "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                    NToast.shortToast(MainActivity.this,"连接融云成功: userid"+userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d(Tag, "--onError" + errorCode);
                    NToast.shortToast(MainActivity.this,"连接融云fail: errorCode"+errorCode);

                }
            });
        }
        Log.d(Tag, "--connect  !===" );

    }


}
