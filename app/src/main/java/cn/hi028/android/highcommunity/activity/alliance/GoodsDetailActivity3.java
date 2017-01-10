package cn.hi028.android.highcommunity.activity.alliance;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.duohuo.dhroid.util.LogUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.GoodImageDetailOrEvaluationActivity;
import cn.hi028.android.highcommunity.activity.ShowPayActivity;
import cn.hi028.android.highcommunity.adapter.EvaluationAdapter;
import cn.hi028.android.highcommunity.bean.GoodsData;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.bean.SubmitOrderBean;
import cn.hi028.android.highcommunity.lisenter.PayPop2FragFace;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.DrawableCenterTextView;
import cn.hi028.android.highcommunity.view.NoScrollListview;
import cn.hi028.android.highcommunity.view.PaylistPopupWindow;
import cn.hi028.android.highcommunity.view.ScrollWebView;
import cn.hi028.android.highcommunity.view.ScrollWebView.OnScrollChangeListener;
import cn.hi028.android.highcommunity.view.snap.McoyProductContentPage;
import cn.hi028.android.highcommunity.view.snap.McoyProductDetailInfoPage;
import cn.hi028.android.highcommunity.view.snap.McoySnapPageLayout;
import cn.hi028.android.highcommunity.view.snap.McoySnapPageLayout.PageSnapedListener;

/**
 * 就是我用的联盟商家商品详情
 * @author Administrator
 *
 */
public class GoodsDetailActivity3 extends BaseFragmentActivity implements
OnClickListener, PayPop2FragFace {

	static String Tag="~~~GoodsDetailActivity3~~~";
	private static final int TAB_PICDETAIL = 0;
	public static final int TAB_COMMENTDETAIL = 1;
	int currentTab=0; 
	String good_id;
	int good_count;
	ImageView back;
	TextView name,price;ImageView headimg;
	TextView subimg,addimg,kucun,conttv,detail,goodname,guige,origin;
	//	TextView time,telephone;
	TextView guige_,origin_,edible_;
	Button goPay;
	TextView caramount,mAllprice,telephone,time;
	View viewline1,viewline2,viewline3;
	ImageButton call;
	FrameLayout shopcar;
	RelativeLayout tuwenxiangqing;RelativeLayout goodevaluation;LinearLayout payrl;
	RadioButton mPicDetail,mCommentDetail;
	ScrollView mScrollView2;
	private TextView edible,scrollText;
	GoodsData goodsdata;
	ArrayList<Goods_info> goodslist;
	private String goods_price;
	private int goods_count;
	private String good_sales;
	private String storeId;
	private String storeName;
	private String telPhone = "";
	private Goods_info goods_info = null;
	private McoySnapPageLayout mcoySnapPageLayout = null;
	private McoyProductContentPage bottomPage = null;
	private McoyProductDetailInfoPage topPage = null;
	View top_view,bottom_View;
	ScrollWebView mWebview;
	NoScrollListview mCommentListview;
	CheckBox toSeeMore;
	ViewGroup moreDetailGroup;
	DrawableCenterTextView tv_noData,tv_empty;



	int width,height ;

	Handler mHandler = new Handler(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.d(Tag+"——————————————————啦啦啦  进入详情3");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_activity3);
		mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);

		topPage = new McoyProductDetailInfoPage(GoodsDetailActivity3.this,
				getLayoutInflater().inflate(
						R.layout.page_top, null));
		bottomPage = new McoyProductContentPage(GoodsDetailActivity3.this,
				getLayoutInflater().inflate(
						R.layout.page_bottom2, null));
		mcoySnapPageLayout.setSnapPages(topPage, bottomPage);
		top_view=topPage.getRootView();
		bottom_View=bottomPage.getRootView();
		getDatas();
		HTTPHelper.GetGoodDetail(mIbpi, good_id);
		init();
		registerListener();
		mcoySnapPageLayout.setPageSnapListener(new PageSnapedListener() {
			@Override
			public void onSnapedCompleted(int derection) {
				if (mcoySnapPageLayout.getCurrentScreen()==0) {
					scrollText.setText("—— 继续拖动，查看图文详情 ——");
				}else if (mcoySnapPageLayout.getCurrentScreen()==1) {
					scrollText.setText("—— 继续拖动，查看商品信息 ——");
				}

			}
		});
		mWebview.setOnScrollChangeListener(new OnScrollChangeListener() {

			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
			}
			@Override
			public void onPageTop(int l, int t, int oldl, int oldt) {
			}
			@Override
			public void onPageEnd(int l, int t, int oldl, int oldt) {
			}
		});
	}
	private void getDatas() {
		Bundle bundle = getIntent().getExtras();
		try {
			good_id = bundle.getString("id");
			good_count = bundle.getInt("count");
			good_sales = bundle.getString("sales");
			goods_count = bundle.getInt("allcount");
			goods_price = bundle.getString("price");
			goodslist = bundle.getParcelableArrayList("list");
			storeId = bundle.getString("storeid");
			storeName = bundle.getString("storename");
			goods_info = bundle.getParcelable("good_info");
			Log.e("renk", good_id);
			Log.e("renk", goodslist.toString());
			Log.e("renk", good_id);
			Log.e("renk", good_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		initView();

		if (goods_count > 99) {
			caramount.setText("99");
		} else {
			caramount.setText(goods_count + "");
		}
	}
	Bundle bundle = new Bundle();
	private void registerListener() {
		back.setOnClickListener(this);
		subimg.setOnClickListener(this);
		addimg.setOnClickListener(this);
		shopcar.setOnClickListener(this);
		goPay.setOnClickListener(this);
		call.setOnClickListener(this);
		telephone.setOnClickListener(this);

		mPicDetail.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mWebview.setVisibility(View.VISIBLE);
					mCommentListview.setVisibility(View.GONE);
					mCommentDetail.setChecked(false);

				}
			}
		});
		mCommentDetail.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mWebview.setVisibility(View.GONE);
					mCommentListview.setVisibility(View.VISIBLE);
					mPicDetail.setChecked(false);
				}
			}
		});
		toSeeMore.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					moreDetailGroup.setVisibility(View.VISIBLE);
					top_view.invalidate();
					LogUtil.d("~~~ 准备滑动top_view.getHeight() "+top_view.getHeight()+"---height="+height);
					mHandler.post(new Runnable() {  
						@Override  
						public void run() {  
							((ScrollView) top_view).fullScroll(ScrollView.FOCUS_DOWN);  
						}  
					}); 
					//					mcoySnapPageLayout.scrollTo(0,top_view.getBottom());
					//					LogUtil.d("~~~ 准备滑动完成"+scrollText.getX()+"---scrollText.getY()="+scrollText.getY());

				}else{
					moreDetailGroup.setVisibility(View.GONE);
				}
			}
		});
		toSeeMoreLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (toSeeMore.isChecked()) {
					moreDetailGroup.setVisibility(View.VISIBLE);
					top_view.invalidate();
					LogUtil.d("~~~ 准备滑动top_view.getHeight() "+top_view.getHeight()+"---height="+height);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							((ScrollView) top_view).fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
					//					mcoySnapPageLayout.scrollTo(0,top_view.getBottom());
					//					LogUtil.d("~~~ 准备滑动完成"+scrollText.getX()+"---scrollText.getY()="+scrollText.getY());

				}else{
					moreDetailGroup.setVisibility(View.GONE);
				}
			}
		});

	}
	RelativeLayout toSeeMoreLayout;
	private void initView() {
		caramount = (TextView) findViewById(R.id.ac_shop_count);
		back = (ImageView) findViewById(R.id.ac_good_title_go_back);
		payrl = (LinearLayout) findViewById(R.id.shop_deatil_bottom_pay_rl);
		mAllprice = (TextView) findViewById(R.id.ac_shop_car_price);

		goPay = (Button) findViewById(R.id.ac_shop_car_go_pay);
		shopcar = (FrameLayout) findViewById(R.id.ac_shop_car_fl);

		name = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_name);
		price = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_price);
		headimg = (ImageView) top_view.findViewById(R.id.ac_shop_goods_head_iv);
		subimg = (TextView) top_view.findViewById(R.id.ac_shop_goods_right_sub_iv);
		addimg = (TextView) top_view.findViewById(R.id.ac_shop_goods_right_add_iv);
		kucun = (TextView) top_view.findViewById(R.id.ac_shop_detail_goods_count);
		conttv = (TextView) top_view.findViewById(R.id.ac_shop_goods_list_right_counts);
		detail = (TextView) top_view.findViewById(R.id.ac_shop_detail_tv);
		goodname = (TextView) top_view.findViewById(R.id.shop_detail_ac_goods_name);
		guige = (TextView) top_view.findViewById(R.id.ac_shop_detail_speci_tv);
		origin = (TextView) top_view.findViewById(R.id.ac_shop_origin_tv);

		guige_ = (TextView) top_view.findViewById(R.id.ac_shop_good_speci);
		origin_ = (TextView) top_view.findViewById(R.id.ac_shop_origin);
		edible_ = (TextView) top_view.findViewById(R.id.ac_shop_edible);
		edible = (TextView) top_view.findViewById(R.id.ac_shop_edible_tv);
		toSeeMore=(CheckBox) top_view.findViewById(R.id.toSeeMore);

		toSeeMoreLayout=(RelativeLayout) top_view.findViewById(R.id.seemoreLayout);
		moreDetailGroup=(ViewGroup) top_view.findViewById(R.id.moredetail_layout);
		telephone = (TextView) top_view.findViewById(R.id.shop_detail_service_telephone);
		time = (TextView) top_view.findViewById(R.id.ac_shop_detail_service_time);
		call = (ImageButton) top_view.findViewById(R.id.call);

		moreDetailGroup.post(new Runnable() {  
			@Override  
			public void run() {  
				width = moreDetailGroup.getWidth();  
				height = moreDetailGroup.getHeight();  
				LogUtil.d("~~~width="+width+",height="+height);
				moreDetailGroup.setVisibility(View.GONE);  
			}  
		});




		//		moreDetailGroup.setVisibility(View.GONE);
		// toSeeMore.setChecked(false);
		mPicDetail=(RadioButton) bottom_View.findViewById(R.id.ac_shopdetail_mypicdetail);
		mCommentDetail=(RadioButton) bottom_View.findViewById(R.id.ac_shopdetail_mycommentdetail);
		mWebview=(ScrollWebView) bottom_View.findViewById(R.id.ac_good_detail_webview);
		mCommentListview=(NoScrollListview) bottom_View.findViewById(R.id.ac_good_evaluation_listview);
		tv_noData=(DrawableCenterTextView) bottom_View.findViewById(R.id.ac_good_nodata);
		tv_empty=(DrawableCenterTextView) bottom_View.findViewById(R.id.ac_good_comment_empty);
		viewline1 = top_view.findViewById(R.id.view11);
		viewline2 = top_view.findViewById(R.id.view12);
		viewline3 = top_view.findViewById(R.id.view13);

		scrollText=(TextView) top_view.findViewById(R.id.scroll_Text);
		//		mScrollView2=(ScrollView) findViewById(R.id.scrollView2);
		//		mScrollView2.smoothScrollTo(0, 20);

		LogUtil.d("~~~ top_view.getHeight()=="+top_view.getHeight());

	}

	@Override
	protected void onResume() {
		super.onResume();
		//		switchFragment(0);
	}
	private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			goodsdata=(GoodsData) message;
			LogUtil.d("~~~商品详情数据message~~~---"+message);
			LogUtil.d("~~~商品详情数据~~~---"+goodsdata.toString());
			//			switchFragment(0);
			setUi((GoodsData) message);
			mPicDetail.setChecked(true);
		}

		@Override
		public Object onResolve(String result) {
			Log.e("renk", "106>goodsdata");
			Log.e("renk", result);
			Gson gson = new Gson();
			return gson.fromJson(result, GoodsData.class);
		}

		@Override
		public void onError(int id, String message) {

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
				HighCommunityApplication.toLoginAgain(GoodsDetailActivity3.this);
			}
		}
	};
	/** 传递给商品评价的列表 **/
	private List<MerchantEvaluationInfoListBean> comment;

	/**
	 * 展示数据
	 * 
//	 * @param message
	 **/
	public void setUi(GoodsData msg) {
		if (msg == null) {
			return;
		}
		goodsdata = msg;
		if (null != msg.getGoods_name())
			name.setText(msg.getGoods_name());
		if (null != msg.getPrice())
			price.setText(msg.getPrice() + "");
		if (null != msg.getGoods_pic())
			Picasso.with(this)
			.load(Constacts.IMAGEHTTP + msg.getGoods_pic().get(0))
			.into(headimg);
		if (null != msg.getStorage())
			kucun.setText("库存" + msg.getStorage());
		conttv.setText(good_count + "");
		if (null != msg.getIntro())
			detail.setText(msg.getIntro());
		if (null != msg.getGoods_name())
			goodname.setText(msg.getGoods_name());
		if (msg.getAttr() != null) {
			int size = msg.getAttr().size();
			if (size > 0 && size < 2) {
				guige_.setText(msg.getAttr().get(0).getAttr_name() + ":");
				guige.setText(msg.getAttr().get(0).getAttr_value());
				origin_.setVisibility(View.GONE);
				origin.setVisibility(View.GONE);
				edible_.setVisibility(View.GONE);
				edible.setVisibility(View.GONE);
				viewline2.setVisibility(View.GONE);
				viewline3.setVisibility(View.GONE);
			} else if (size > 1 && size < 3) {
				guige_.setText(msg.getAttr().get(0).getAttr_name() + ":");
				guige.setText(msg.getAttr().get(0).getAttr_value());
				origin_.setText(msg.getAttr().get(1).getAttr_name() + ":");
				origin.setText(msg.getAttr().get(1).getAttr_value());
				edible_.setVisibility(View.GONE);
				edible.setVisibility(View.GONE);
				viewline2.setVisibility(View.GONE);
				viewline3.setVisibility(View.GONE);
			} else if (size > 2 && size < 4) {
				guige_.setText(msg.getAttr().get(0).getAttr_name() + ":");
				guige.setText(msg.getAttr().get(0).getAttr_value());
				origin_.setText(msg.getAttr().get(1).getAttr_name() + ":");
				origin.setText(msg.getAttr().get(1).getAttr_value());
				edible_.setText(msg.getAttr().get(2).getAttr_name() + ":");
				edible.setText(msg.getAttr().get(2).getAttr_value());
			} else {
				guige_.setVisibility(View.GONE);
				guige.setVisibility(View.GONE);
				origin_.setVisibility(View.GONE);
				origin.setVisibility(View.GONE);
				edible_.setVisibility(View.GONE);
				edible.setVisibility(View.GONE);
				viewline2.setVisibility(View.GONE);
				viewline3.setVisibility(View.GONE);
			}
		}
		if (null != msg.getTel()) {
			telephone.setText("客服电话：" + msg.getTel());
			telPhone = msg.getTel();
		}else {
			telephone.setVisibility(View.GONE);
		}
		if (null != msg.getDelivery()){

			time.setText("服务时间：" + msg.getDelivery());
		}else {
			telephone.setVisibility(View.GONE);
		}


		if (null != msg.getDetail()){
			loadPicDetail(msg);
		}else {
			mWebview.setVisibility(View.GONE);
			tv_noData.setText("暂无图文详情");
//			tv_noData.setVisibility(View.VISIBLE);
		}
		if (null != msg.getComments()){
			comment = msg.getComments();
			EvaluationAdapter mEvaluationAdapter = new EvaluationAdapter(GoodsDetailActivity3.this, comment);
			mEvaluationAdapter.setType(2);
//			mCommentListview.setEmptyView(tv_empty);
			mCommentListview.setAdapter(mEvaluationAdapter);
			setCarAmount();

		}
	}
	/**获取图文详情数据**/
	private void loadPicDetail(GoodsData msg) {
		contentdetail = msg.getDetail();
		WebSettings mSetting = mWebview.getSettings();
		mSetting.setJavaScriptEnabled(true); 
		mSetting.setDefaultTextEncodingName("utf-8");
		//		mSetting.setDisplayZoomControls(false);
		mSetting.setUseWideViewPort(true);
		mSetting.setLoadWithOverviewMode(true);
		mSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS); 
		mWebview.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				//	super.onPageFinished(view, url);
				//	layoutCall.setVisibility(View.VISIBLE);
			}
		});
		LogUtil.d("----------准备loadUrl-------------："+contentdetail);
		mWebview.loadUrl(contentdetail);
		// contentevaluation = (ArrayList<String>) msg.getComments();
		//		aboutCallService(msg);
	}

	//	private void aboutCallService(GoodsData msg) {
	//		if (null != msg.getTel()) {
	//			telephone.setText("客服电话：" + msg.getTel());
	//			telPhone = msg.getTel();
	//		}
	//		if (null != msg.getDelivery())
	//			time.setText("服务时间：" + msg.getDelivery());
	//	}

	String contentdetail;
	ArrayList<String> contentevaluation;

	/** 返回 */
	public void goBack() {
		Intent data = new Intent();
		data.putParcelableArrayListExtra("list", goodslist);
		setResult(888, data);
		this.finish();
	}

	private boolean hasThisGoodsInfo(ArrayList<Goods_info> goodslist,
			Goods_info goods_info) {
		if (goodslist == null || goodslist.size() < 1 || goods_info == null) {
			return false;
		}
		for (Goods_info info : goodslist) {
			if (info.getGoods_id().equals(goods_info.getGoods_id())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this,
				GoodImageDetailOrEvaluationActivity.class);
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.ac_good_title_go_back:
			goBack();
			break;
			// 图文详情
			//		case R.id.ac_shop_look_map_detail:
			//			bundle.putInt("type", 0);
			//			bundle.putString("detail", contentdetail);
			//			intent.putExtras(bundle);
			//			startActivity(intent);
			//			break;
			//			// 评价
			//		case R.id.ac_shop_look_good_detail:
			//			bundle.putInt("type", 1);
			//			bundle.putSerializable("data", goodsdata);
			//			intent.putExtras(bundle);
			//			startActivity(intent);
			//			break;
		case R.id.ac_shop_goods_right_add_iv:
			good_count++;
			goods_count++;
			conttv.setText(good_count + "");
			double price = Double.parseDouble(goods_price);
			if (hasThisGoodsInfo(goodslist, goods_info)) {
				for (Goods_info goods_info : goodslist) {
					if (goods_info.getGoods_id().equals(good_id)) {
						goods_info.setCounts(good_count);
						price += Double.parseDouble(goods_info.getPrice());
						BigDecimal bigDecimal = new BigDecimal(price);
						price = bigDecimal.setScale(2,
								BigDecimal.ROUND_HALF_DOWN).doubleValue();
						goods_price = String.valueOf(price);
						break;
					}
				}
			} else {
				if (goodslist == null) {
					goodslist = new ArrayList<Goods_info>();
				}
				goodslist.add(goods_info);
				price += Double.parseDouble(goods_info.getPrice());
				BigDecimal bigDecimal = new BigDecimal(price);
				price = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN)
						.doubleValue();
				goods_price = String.valueOf(price);
			}
			setCarAmount();
			break;
		case R.id.ac_shop_goods_right_sub_iv:
			if (good_count > 0) {
				good_count--;
				goods_count--;
				conttv.setText(good_count + "");
				double allprice = Double.parseDouble(goods_price);
				for (Goods_info goods_info : goodslist) {
					if (goods_info.getGoods_id().equals(good_id)) {
						goods_info.setCounts(good_count);
						allprice -= Double.parseDouble(goods_info.getPrice());
						BigDecimal bigDecimal = new BigDecimal(allprice);
						allprice = bigDecimal.setScale(2,
								BigDecimal.ROUND_HALF_DOWN).doubleValue();
						goods_price = String.valueOf(allprice);
						break;
					}
				}
				setCarAmount();
			}
			break;
		case R.id.ac_shop_car_fl:
			if (null != goodslist && goodslist.size() > 0) {
				payrl.setVisibility(View.INVISIBLE);
				upDateList();
				PaylistPopupWindow pop = new PaylistPopupWindow(this, goodslist);
				pop.setFace(this);
				double total_price = Double.parseDouble(goods_price);
				BigDecimal decimal = new BigDecimal(total_price);
				total_price = decimal.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				pop.setAmount(total_price);
				pop.setNumber(goods_count);
				pop.showPopwindow();
			} else if (goodslist.size() == 0) {
				Goods_info info = new Goods_info();
				if (!TextUtils.isEmpty(good_id)) {
					return;
				}
				if (good_count < 1) {
					return;
				}
				if (goodsdata == null) {
					return;
				}
				if (!TextUtils.isEmpty(good_sales)) {
					return;
				}
				info.setGoods_id(good_id);
				info.setCounts(good_count);
				info.setGoods_name(goodsdata.getGoods_name());
				info.setPrice(goodsdata.getPrice());
				info.setThumb_pic(goodsdata.getGoods_pic().get(0));
				info.setSales(good_sales);
				goodslist.add(info);
				if (good_count > 0) {
					payrl.setVisibility(View.INVISIBLE);
					PaylistPopupWindow pop = new PaylistPopupWindow(this,
							goodslist);
					pop.setFace(this);
					double total_price = Double.parseDouble(goods_price);
					BigDecimal decimal = new BigDecimal(total_price);
					total_price = decimal.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					pop.setAmount(total_price);
					pop.setNumber(goods_count);
					pop.showPopwindow();
				}
			}
			break;
			// activity 中点击去支付
		case R.id.ac_shop_car_go_pay:
			if (good_count==0) {
				Toast.makeText(GoodsDetailActivity3.this, "请选择商品数量", Toast.LENGTH_SHORT).show();
				return;
			}
			upDateList();
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < goodslist.size(); i++) {
				DecimalFormat df = new DecimalFormat("0.00");
				String rs = df.format(Double.parseDouble(goodslist.get(i)
						.getPrice()) * goodslist.get(i).getCounts());
				sb.append(
						"{\"goods_id\":\"" + goodslist.get(i).getGoods_id()
						+ "\"")
						.append(",")
						.append("\"goods_name\":\""
								+ goodslist.get(i).getGoods_name() + "\"")
								.append(",")
								.append("\"goods_price\":\""
										+ goodslist.get(i).getPrice() + "\"")
										.append(",")
										.append("\"number\":\"" + goodslist.get(i).getCounts()
												+ "\"")
												.append(",")
												.append("\"goods_total_price\":\"" + rs + "\"")
												.append(",")
												.append("\"goods_image\":\""
														+ goodslist.get(i).getThumb_pic() + "\"")
														.append("}");
				if (i != goodslist.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]");

			HTTPHelper.GetOrderNo(getOrderNo, goods_price, sb.toString(),
					storeId);
			break;
		case R.id.call:
			Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ telPhone));
			startActivity(intent2);
			break;

		}
	}

	public String gettotalprice() {
		String pric = mAllprice.getText().toString();
		return pric;
	}

	private void upDateList() {
		for (Goods_info info : goodslist) {
			if (info.getGoods_id().equals(good_id)) {
				info.setCounts(good_count);
			}
		}
	}

	private void setCarAmount() {
		if (goods_count > 99) {
			caramount.setText("99");
		} else {
			caramount.setText(goods_count + "");
		}
		BigDecimal total_price = new BigDecimal(goods_price);
		double all = total_price.setScale(2, BigDecimal.ROUND_HALF_DOWN)
				.doubleValue();
		if (all == 0) {
			mAllprice.setText("0.00");
		} else {
			mAllprice.setText(all + "");
		}
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

	/** pop回调设置数量和金额 **/
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
		HTTPHelper.GetOrderNo(getOrderNo, getAllPrice(popBackList), sb.toString(), storeId);
	}

	public String getAllPrice(ArrayList<Goods_info> popBackList) {
		DecimalFormat df = new DecimalFormat("0.00");
		double price = 0.0;
		for (Goods_info bean : popBackList) {
			price += bean.getCounts() * Double.parseDouble(bean.getPrice());
		}
		return df.format(price);
	}

	private SubmitOrderBean mOrderBean;
	/**
	 * 请求服务器 获得订单号的方法
	 */
	IBpiHttpHandler getOrderNo = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			if (null == message)
				return;

			mOrderBean = (SubmitOrderBean) message;

			Intent intent = new Intent(GoodsDetailActivity3.this,
					ShowPayActivity.class);
			intent.putParcelableArrayListExtra("gods_list", goodslist);
			intent.putExtra("total_price",
					String.valueOf(mOrderBean.getTotal_price()));
			intent.putExtra("shop", storeName);
			if (mOrderBean != null) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("MerchantShopFrag", mOrderBean);
				intent.putExtras(bundle);
			}
			startActivity(intent);
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveSubmitOrder(result);
		}

		@Override
		public void onError(int id, String message) {
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
				HighCommunityApplication.toLoginAgain(GoodsDetailActivity3.this);
			}
		}
	};

	@Override
	public void onBackPressed() {
		goBack();
	}




}
