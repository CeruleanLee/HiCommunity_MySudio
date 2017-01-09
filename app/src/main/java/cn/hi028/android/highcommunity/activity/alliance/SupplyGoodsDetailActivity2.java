package cn.hi028.android.highcommunity.activity.alliance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.lzy.widget.VerticalSlide;

import net.duohuo.dhroid.view.AutoScrollViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.GoodImageDetailOrEvaluationActivity;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.activity.fragment.newhui.NewBottomPageFrag;
import cn.hi028.android.highcommunity.activity.fragment.newhui.NewTopPageFrag;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyCarlistBean;
import cn.hi028.android.highcommunity.bean.GoodsData;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.bean.NewSupplyGoodsDetailBean;
import cn.hi028.android.highcommunity.lisenter.PayPop2FragFace;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 新版直供商品详情2  换了框架
 * v2.0版本用
 * @author Lee_yting
 */
public class SupplyGoodsDetailActivity2 extends BaseFragmentActivity implements
        OnClickListener, PayPop2FragFace, NewTopPageFrag.StandardChangeListener {

    static String Tag = "SupplyGoodsDetailActivity";
    private static final int TAB_PICDETAIL = 0;
    public static final int TAB_COMMENTDETAIL = 1;
    int currentTab = 0;
    int good_count;
    ImageView back;
    TextView conttv;
    Button addToCar;
    TextView caramount, mAllprice, telephone, time,tv_mynodata;
    FrameLayout shopcar;
    LinearLayout progress_layout;
    LinearLayout payrl;
    RelativeLayout layout_hasData;
    RadioGroup mRadioGroup;


    /**
     * 接口获取的data
     **/
    NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity goodsdata;
    ArrayList<Goods_info> goodslist;
    private String goods_price;
    private int goods_count;
    private String telPhone = "";
    private Goods_info goods_info = null;
    /**
     * 获取商品详情数据的商品id
     **/
    String id = -1 + "";
    String goodsId;
    String standardId = "";

    Handler mHandler = new Handler();
    /**
     * 购物车价格合计
     **/
    float mCarPriceSum;
    private VerticalSlide verticalSlide;
    private NewTopPageFrag topFragment;
    private NewBottomPageFrag bottomFragment;
    private FloatingActionButton fab;
    PopupWindow mWaittingPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(Tag, "~~~啦啦啦  进入详情onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail_activity_supply2);
        id = getIntent().getStringExtra("id");
        goodsId = id;
        verticalSlide = (VerticalSlide) findViewById(R.id.dragLayout);
        init();
    }

    private void init() {
        findView();
        registerListener();
        initData();
    }

    private void findView() {
        caramount = (TextView) findViewById(R.id.ac_shop_count);
        tv_mynodata = (TextView) findViewById(R.id.tv_mynodata);
        back = (ImageView) findViewById(R.id.ac_good_title_go_back);
        payrl = (LinearLayout) findViewById(R.id.shop_deatil_bottom_pay_rl);
        progress_layout = (LinearLayout) findViewById(R.id.progress_layout);


        layout_hasData = (RelativeLayout) findViewById(R.id.layout_hasData);
        mAllprice = (TextView) findViewById(R.id.ac_shop_car_price);
        addToCar = (Button) findViewById(R.id.ac_shop_car_addtocar);
        shopcar = (FrameLayout) findViewById(R.id.ac_shop_car_fl);

    }

    private void initViewNew() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        if (goodsdata != null) {
            bundle.putParcelable("goodsdata", goodsdata);
        }
        topFragment = new NewTopPageFrag();
        topFragment.setArguments(bundle);
        transaction.replace(R.id.first, topFragment);
/******* 上 下 *************/
        Bundle bundle2 = new Bundle();
        if (goodsdata != null) {
            bundle2.putParcelable("goodsdata", goodsdata);
        }
        bottomFragment = new NewBottomPageFrag();
        bottomFragment.setArguments(bundle2);
        transaction.replace(R.id.second, bottomFragment);

        transaction.commit();
    }

    @Override
    public void onStandardChange(boolean isAddCar, float singlePrice, String standardId) {
        this.singlePrice = singlePrice;
        this.standardId = standardId;
        updateCarNum(isAddCar, singlePrice);
    }

    private Handler popupHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (SupplyGoodsDetailActivity2.this.hasWindowFocus() && mViewPager != null) {
                        mWaittingPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity2.this, caramount, Gravity.CENTER);
                    }
                    break;
            }
        }

    };

    private void initData() {
        popupHandler.sendEmptyMessageDelayed(0, 50);
//        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity.this, caramount, Gravity.CENTER);
        HTTPHelper.getGdCarList2(mGetCarIbpi);
    }

    IBpiHttpHandler mGetCarIbpi = new IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();
            }
            layout_hasData.setVisibility(View.GONE);
            progress_layout.setVisibility(View.GONE);

            tv_mynodata.setText(message);
            tv_mynodata.setVisibility(View.VISIBLE);


            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.d(Tag, "onSuccess");
            if (null == message)
                return;
            progress_layout.setVisibility(View.GONE);
            layout_hasData.setVisibility(View.VISIBLE);

            mCarPriceSum = 0.00f;
            List<NewSupplyCarlistBean.SupplyCarlistDataEntity> mlist = (List<NewSupplyCarlistBean.SupplyCarlistDataEntity>) message;
            for (int i = 0; i < mlist.size(); i++) {
                mCarPriceSum += mlist.get(i).getSum();
            }

            HTTPHelper.GetNewSupplyGoodsDetail(mIbpi, id);
//            setCarAmount();
//            adapter.setData(mlist);
//            mListView.onRefreshComplete();
        }

        @Override
        public Object onResolve(String result) {


            return HTTPHelper.ResolvGdCarList2(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            if (mWaittingPop!=null){
                mWaittingPop.dismiss();
            }
        }

    };


    Bundle bundle = new Bundle();

    private void registerListener() {
        back.setOnClickListener(this);
        shopcar.setOnClickListener(this);

        addToCar.setOnClickListener(this);
//        call.setOnClickListener(this);
//        telephone.setOnClickListener(this);

    }
    AutoScrollViewPager mViewPager;
    @Override
    protected void onResume() {
        super.onResume();
//        mViewPager.startAutoScroll();
    }
    private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();
            }
            layout_hasData.setVisibility(View.GONE);
            tv_mynodata.setText(message);
            tv_mynodata.setVisibility(View.VISIBLE);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }
        @Override
        public void onSuccess(Object message) {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();
            }
            layout_hasData.setVisibility(View.VISIBLE);
            tv_mynodata.setVisibility(View.GONE);
            if (message == null) return;
            goodsdata = (NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity) message;
            Log.e(Tag, "商品详情数据message-" + message);
            Log.e(Tag, "商品详情数据-" + goodsdata.toString());
            goodsId = goodsdata.getId();
            setCarAmount(goodsdata);
            initViewNew();
//            setUi(goodsdata);
//            mPicDetail.setChecked(true);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSupplyGoodsDetailEntity(result);
        }



        @Override
        public void cancleAsyncTask() {
            if (mWaittingPop != null) {
                mWaittingPop.dismiss();

            }
        }
    };
    /**
     * 传递给商品评价的列表
     **/
    private List<MerchantEvaluationInfoListBean> comment;
    public PicPageAdapter pagerAdapter;


    List<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity> mStandardList = new ArrayList<NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity.StandardEntity>();


    private void aboutCallService(GoodsData msg) {
        if (null != msg.getTel()) {
            telephone.setText("客服电话：" + msg.getTel());
            telPhone = msg.getTel();
        }
        if (null != msg.getDelivery())
            time.setText("服务时间：" + msg.getDelivery());
    }


    /**
     * 返回
     */
    public void goBack() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("list", goodslist);
        setResult(888, data);
        this.finish();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,
                GoodImageDetailOrEvaluationActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ac_good_title_go_back:
//                goBack();
                onBackPressed();
                break;
            case R.id.ac_shop_car_fl://点击购物车跳转购物车列表
                Intent mIntent = new Intent(this, GeneratedClassUtils.get(MenuLeftAct.class));
                mIntent.putExtra(MenuLeftAct.ACTIVITYTAG,
                        Constacts.MENU_LEFT_GDCAR);
                startActivity(mIntent);
                break;

            case R.id.ac_shop_car_addtocar:
                addCarGoods();
                break;
//            case R.id.call:
//                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telPhone));
//                startActivity(intent2);
//                break;

        }
    }

    /**
     * 加入购物车
     **/
    public void addCarGoods() {
        if (HighCommunityUtils.isLogin(SupplyGoodsDetailActivity2.this)) {
            if (waitPop != null && mRadioGroup != null) {
                waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(SupplyGoodsDetailActivity2.this, mRadioGroup, Gravity.CENTER);
            }
            HTTPHelper.addNewHuiGoodsToCar(mIbpiAddShopCar, goodsId, standardId);

        }
    }

    /**
     * 加入购物车弹窗
     */
    PopupWindow waitPop;
    /**
     * 加入购物车回调
     */
    IBpiHttpHandler mIbpiAddShopCar = new IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (waitPop != null) {

                waitPop.dismiss();
            }
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (waitPop != null) {

                waitPop.dismiss();
            }
            HighCommunityUtils.GetInstantiation().ShowToast("成功加入购物车", 0);

            updateCarNum(true, singlePrice);
        }


        @Override
        public Object onResolve(String result) {
            Log.e(Tag, "onResolve result" + result);
            return result;
//            return HTTPHelper.ResolveHuiSupportList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };
    float singlePrice = 0;


    private void updateCarNum(boolean isAddCar, float singlePrice) {
        if (isAddCar) {
            int carNum = Integer.parseInt(caramount.getText().toString());
            caramount.setText(carNum + 1 + "");
            mCarPriceSum += singlePrice;
            float priceSum_F = (float) (Math.round(mCarPriceSum * 100)) / 100;
            Log.e(Tag, "mCarPriceSum:" + mCarPriceSum+"float:"+priceSum_F);
            mAllprice.setText("购物车合计 ￥:" + priceSum_F);
        }


    }

    /**
     * set底部购物车数量等
     *
     * @param msg
     */
    private void setCarAmount(NewSupplyGoodsDetailBean.SupplyGoodsDetailDataEntity msg) {
        if (msg.getCartNum() == null || msg.getCartNum() == "") {

            caramount.setText("0");
        } else {
            caramount.setText(msg.getCartNum());
        }
        mAllprice.setText("购物车合计 ￥:" + mCarPriceSum);
//        if (msg.getStandard().get(0).getPrice()!=null||!msg.getStandard().get(0).getPrice().equals("")){
//            mAllprice.setText("购物车合计 ￥:" + Float.parseFloat(msg.getStandard().get(0).getPrice())*Integer.parseInt(caramount.getText().toString().trim()));
//        }else{
//        }

    }

    // Popupwindow 关闭后返回列表数据，更新rightlist 列表数据
    @Override
    public void backAllList(List<Goods_info> glist) {
        payrl.setVisibility(View.VISIBLE);
        goodslist = (ArrayList<Goods_info>) glist;
        double price = 0.0;
        int count = 0;
        for (Goods_info info : goodslist) {
            if (goods_info.getGoods_id().equals(info.getGoods_id())) {
                good_count = info.getCounts();
                conttv.setText(String.valueOf(good_count));
            }
            price += info.getCounts() * Double.parseDouble(info.getPrice());
            count += info.getCounts();
        }
        DecimalFormat bd = new DecimalFormat("0.00");
        goods_price = bd.format(price);
        mAllprice.setText(goods_price);
        goods_count = count;
        if (count > 99) {
            caramount.setText("99");
        } else {
            caramount.setText(goods_count + "");
        }
    }

    /**
     * pop回调设置数量和金额
     **/
    @Override
    public void setNumAndAmount(int num, double amount) {
    }

    @Override
    public void goPay(ArrayList<Goods_info> popBackList) {
        // popwindow 点击去支付，
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < popBackList.size(); i++) {
            DecimalFormat df = new DecimalFormat("0.00");
            String rs = df.format(Double.parseDouble(popBackList.get(i)
                    .getPrice()) * popBackList.get(i).getCounts());
            sb.append(
                    "{\"goods_id\":\"" + popBackList.get(i).getGoods_id()
                            + "\"")
                    .append(",")
                    .append("\"goods_name\":\""
                            + popBackList.get(i).getGoods_name() + "\"")
                    .append(",")
                    .append("\"goods_price\":\""
                            + popBackList.get(i).getPrice() + "\"")
                    .append(",")
                    .append("\"number\":\"" + popBackList.get(i).getCounts()
                            + "\"")
                    .append(",")
                    .append("\"goods_total_price\":\"" + rs + "\"")
                    .append(",")
                    .append("\"goods_image\":\""
                            + popBackList.get(i).getThumb_pic() + "\"")
                    .append("}");
            if (i != popBackList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
//        HTTPHelper.GetOrderNo(getOrderNo, getAllPrice(popBackList), sb.toString(), storeId);
    }

    public String getAllPrice(ArrayList<Goods_info> popBackList) {
        DecimalFormat df = new DecimalFormat("0.00");
        double price = 0.0;
        for (Goods_info bean : popBackList) {
            price += bean.getCounts() * Double.parseDouble(bean.getPrice());
        }
        return df.format(price);
    }


    @Override
    public void onPause() {
        super.onPause();
//        mViewPager.stopAutoScroll();
    }


    /**
     * 当前页
     **/
    int currentPo = 0;
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表

}
