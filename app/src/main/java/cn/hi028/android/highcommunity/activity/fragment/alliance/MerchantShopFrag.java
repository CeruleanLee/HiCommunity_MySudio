package cn.hi028.android.highcommunity.activity.fragment.alliance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ShowPayActivity;
import cn.hi028.android.highcommunity.activity.alliance.GoodsDetailActivity3;
import cn.hi028.android.highcommunity.adapter.MerchantGoodLeftAdapter;
import cn.hi028.android.highcommunity.adapter.MerchantGoodRightAdapter;
import cn.hi028.android.highcommunity.bean.Goods_info;
import cn.hi028.android.highcommunity.bean.MerchantGoodTitleBean;
import cn.hi028.android.highcommunity.bean.MerchantShopGoodBean;
import cn.hi028.android.highcommunity.bean.SubmitOrderBean;
import cn.hi028.android.highcommunity.lisenter.PayPop2FragFace;
import cn.hi028.android.highcommunity.lisenter.ShopAddSubListener;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.PaylistPopupWindow;
/**
 * 惠生活-商家联盟-item点击 -商品
 * @author Administrator
 *
 */
public class MerchantShopFrag extends BaseFragment implements OnClickListener,
		OnItemClickListener, ShopAddSubListener, PayPop2FragFace {
	ListView leftView, rightView;
	private SubmitOrderBean mOrderBean;
	TextView carCount, allprice;Button goPay;
	/** 商家id */
	private String id;
	RelativeLayout shopcar;

	// PayPopupListAdapter popadapter;
	// RelativeLayout listrl;
	RelativeLayout bottomrl;

	private String shopName;

	public String getMyId() {
		return id;
	}

	public void setMyId(String id, String name) {
		this.id = id;
		this.shopName = name;
	}

	ArrayList<MerchantShopGoodBean> goodslist;
	ArrayList<MerchantGoodTitleBean> leftlist;
	ArrayList<Goods_info> rightlist;

	// ArrayList<Goods_info> popdata;
	MerchantGoodLeftAdapter leftAdapter;
	MerchantGoodRightAdapter rightAdapter;

	private ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_merchant_shop, container,
				false);
		initView(view);
		initListener();
		return view;

	}

	private void initView(View view) {
		leftView = (ListView) view
				.findViewById(R.id.ac_merchant_content_left_listview);
		rightView = (ListView) view
				.findViewById(R.id.ac_merchant_content_right_listview);
		carCount = (TextView) view.findViewById(R.id.ac_merchant_shop_count);
		allprice = (TextView) view
				.findViewById(R.id.ac_merchant_shop_car_allprice);
		goPay = (Button) view.findViewById(R.id.ac_merchant_shop_car_go_pay);
		shopcar = (RelativeLayout) view
				.findViewById(R.id.frag_merchant_list_shoppingcar_rl);
		// listrl = (RelativeLayout) view
		// .findViewById(R.id.frag_merchant_popup_rl);
		bottomrl = (RelativeLayout) view
				.findViewById(R.id.frag_merchant_bottom_rl);
		listview = (ListView) view.findViewById(R.id.popup_pay_listview);
		goodslist = new ArrayList<MerchantShopGoodBean>();
		leftlist = new ArrayList();
		rightlist = new ArrayList<Goods_info>();
		shopcar.setOnClickListener(this);
	}

	private void initListener() {
		goPay.setOnClickListener(this);
		leftView.setOnItemClickListener(this);
		// rightView.setOnItemClickListener(this);

		leftAdapter = new MerchantGoodLeftAdapter(getActivity(), leftlist);
		rightAdapter = new MerchantGoodRightAdapter(getActivity(),
				MerchantShopFrag.this, rightlist);
		HTTPHelper.GetMerchantShops(mIbpi, id);
	}

	IBpiHttpHandler mIbpi = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			LogUtil.d("----------info:messsage:------"+message);
			if (message != null) {
				goodslist.clear();
				goodslist = (ArrayList<MerchantShopGoodBean>) message;
				Log.e("renk", goodslist.toString());
				for (int i = 0; i < goodslist.size(); i++) {
					MerchantGoodTitleBean bean = new MerchantGoodTitleBean();
					MerchantShopGoodBean info = goodslist.get(i);
					bean.setId(info.getCategory_id());
					bean.setName(info.getCategory_name());
					leftlist.add(bean);
				}
				rightlist.addAll(goodslist.get(0).getGoods_info());

				// 展示数据

				leftView.setAdapter(leftAdapter);
				rightView.setAdapter(rightAdapter);
				leftAdapter.notifyDataSetChanged();
				rightAdapter.notifyDataSetChanged();
			}

		}

		int onresolve = 0;

		@Override
		public Object onResolve(String result) {
			Log.e("renk", "159>shopfrag");
			Log.e("renk", "160>shopfrag" + ++onresolve);
			Log.e("renk", result);
			return HTTPHelper.GetGoodsList(result);
		}

		@Override
		public void onError(int id, String message) {
			Log.d("renk", message);
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
				HighCommunityApplication.toLoginAgain(getActivity());
			}

		}
	};

	@Override
	public void onItemClick(AdapterView<?> list, View arg1, int position,
			long arg3) {
		if (list == leftView) {
			if (leftCurrentPosition == position) {
				return;
			}
			leftAdapter.setIndex(position);
			leftAdapter.notifyDataSetChanged();

			leftCurrentPosition = position;
			MerchantGoodTitleBean infoleft = (MerchantGoodTitleBean) list
					.getItemAtPosition(position);
			// int id = Integer.parseInt(infoleft.getId());
			rightlist.clear();
			if (goodslist.get(position).getGoods_info() != null)
				rightlist.addAll(goodslist.get(position).getGoods_info());
			rightAdapter.notifyDataSetChanged();
		}

	}

	/** popupwindow展示数据列表 */
	ArrayList<Goods_info> poplist = new ArrayList<Goods_info>();

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.frag_merchant_list_shoppingcar_rl:
			getPopList();
			if (poplist.size() > 0) {
				bottomrl.setVisibility(View.INVISIBLE);
				PaylistPopupWindow pop = new PaylistPopupWindow(getActivity(),
						poplist);
				pop.setFace(this);
				pop.setAmount(Double.parseDouble(getAllPrice()));
				pop.setNumber(Integer.parseInt(carCount.getText().toString()));
				pop.showPopwindow();
			} else {
				Toast.makeText(getActivity(), "请选择商品", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.ac_merchant_shop_car_go_pay:
			/**
			 * 点击去支付 在此处请求服务器 获得订单号 total_price 总金额 goods_info 这是一个json字符串 商品信息
			 * merchant_id 商家id 把这些参数从页面上去出来 放进去就行 然后在getOrderNo中去解析服务器返回的数据
			 * 其中就包含 订单号
			 */

			if (poplist == null || poplist.size() < 1) {
				Toast.makeText(getActivity(), "请选择商品数量", Toast.LENGTH_SHORT).show();
				return;

			}

			String total_price = getAllPrice();// 把对应的数据取出来 放到里面就行
			// String goods_info
			// ="[{\"goods_id\":1,\"goods_name\":\"测试1\",\"goods_price\":0.01,\"number\":1,\"goods_total_price\":0.01}]";
			// String merchant_id = "123456789";//这个id可以从商家页面传过来 就是上一个页面 没搞懂0.0
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < poplist.size(); i++) {
				DecimalFormat df = new DecimalFormat("0.00");
				String rs = df.format(Double.parseDouble(poplist.get(i)
						.getPrice()) * poplist.get(i).getCounts());
				sb.append(
						"{\"goods_id\":\"" + poplist.get(i).getGoods_id()
								+ "\"")
						.append(",")
						.append("\"goods_name\":\""
								+ poplist.get(i).getGoods_name() + "\"")
						.append(",")
						.append("\"goods_price\":\""
								+ poplist.get(i).getPrice() + "\"")
						.append(",")
						.append("\"number\":\"" + poplist.get(i).getCounts()
								+ "\"")
						.append(",")
						.append("\"goods_total_price\":\"" + rs + "\"")
						.append(",")
						.append("\"goods_image\":\""
								+ poplist.get(i).getThumb_pic() + "\"")
						.append("}");
				if (i != poplist.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]");

			HTTPHelper.GetOrderNo(getOrderNo, total_price, sb.toString(), id);

			break;
		}
	}

	int countincar = 0;
	/** 左边列表当前位置 ***/
	int leftCurrentPosition = 0;

	// 右边列表item点击加，传人goods_id
	@Override
	public void add(int position) {
		// int a = rightlist.get(position).getCounts();
		/** id.转换为string */
		String posit = position + "";
		List<Goods_info> plist = goodslist.get(leftCurrentPosition)
				.getGoods_info();
		Goods_info currentinfo = new Goods_info();
		for (Goods_info good_info : plist) {
			if (good_info.getGoods_id().equals(posit)) {
				int b = good_info.getCounts();
				good_info.setCounts(++b);
				currentinfo = good_info;
			}
		}
		rightlist.clear();
		rightlist.addAll(goodslist.get(leftCurrentPosition).getGoods_info());
		rightAdapter.notifyDataSetChanged();

		getPopList();
		setCountInCar();
	}

	/** 设置数量和金额 **/
	public void setCountInCar() {

		allprice.setText("¥" + getAllPrice());
		carCount.setText(getAllCount() + "");
	}

	public String getAllPrice() {
		DecimalFormat df = new DecimalFormat("0.00");
		double price = 0.0;
		for (MerchantShopGoodBean bean : goodslist) {
			if (bean.getGoods_info() != null) {
				for (Goods_info info : bean.getGoods_info()) {
					price += info.getCounts()
							* Double.parseDouble(info.getPrice());
				}
			}
		}
		return df.format(price);
	}

	public int getAllCount() {
		DecimalFormat df = new DecimalFormat("0.##");
		int count = 0;
		for (MerchantShopGoodBean bean : goodslist) {
			if (bean.getGoods_info() != null) {
				for (Goods_info info : bean.getGoods_info()) {
					count += info.getCounts();
				}
			}
		}
		return count;
	}

	// 右边列表goods_id
	@Override
	public void sub(int position) {

		/** id.转换为string */
		String posit = position + "";
		List<Goods_info> plist = goodslist.get(leftCurrentPosition)
				.getGoods_info();
		Goods_info currentinfo = new Goods_info();
		for (Goods_info good_info : plist) {
			if (good_info.getGoods_id().equals(posit)) {
				int b = good_info.getCounts();
				if (b > 0) {
					good_info.setCounts(--b);
					currentinfo = good_info;
				} else {
					return;
				}

			}
		}
		rightlist.clear();
		rightlist.addAll(goodslist.get(leftCurrentPosition).getGoods_info());
		rightAdapter.notifyDataSetChanged();

		getPopList();
		setCountInCar();

	}

	public void getPopList() {
		poplist.clear();
		for (MerchantShopGoodBean bean : goodslist) {
			if (bean.getGoods_info() != null) {
				for (Goods_info info : bean.getGoods_info()) {
					if (info.getCounts() > 0) {
						poplist.add(info);
					}
				}
			}
		}
	}

	/**
	 * ac回调设置商品个数
	 **/
	public void setBackCallCount(ArrayList<Goods_info> infos) {

		List<Goods_info> plist = goodslist.get(leftCurrentPosition)
				.getGoods_info();
		if (infos == null) {
			for (int i = 0; i < goodslist.size(); i++) {
				List<Goods_info> goodsInfos = goodslist.get(i).getGoods_info();
				for (Goods_info info : goodsInfos) {
					info.setCounts(0);
				}
				goodslist.get(i).setGoods_info(goodsInfos);
			}
		} else {
			for (int i = 0; i < goodslist.size(); i++) {
				List<Goods_info> goodsInfos = goodslist.get(i).getGoods_info();
				for (Goods_info info : goodsInfos) {
					for (Goods_info backinfo : infos) {
						if (info.getGoods_id().equals(backinfo.getGoods_id())) {
							info.setCounts(backinfo.getCounts());
						}
					}
				}
				goodslist.get(i).setGoods_info(goodsInfos);
			}
		}
		rightlist.clear();
		rightlist.addAll(goodslist.get(leftCurrentPosition).getGoods_info());
		rightAdapter.notifyDataSetChanged();
		getPopList();
		setCountInCar();
	}

	/** 点击商品，得到id **/
	int position;

	/** 点击item中imageview去商品详情 **/
	@Override
	public void goDetail(int position) {
		this.position = position;
		Goods_info info = new Goods_info();
		for (Goods_info goods_info : rightlist) {
			if (goods_info.getGoods_id().equals(position + "")) {
				info = goods_info;
				break;
			}
		}
		Intent intent = new Intent(getActivity(), GoodsDetailActivity3.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", info.getGoods_id());
		bundle.putInt("count", info.getCounts());
		bundle.putInt("allcount", getAllCount());
		bundle.putString("price", getAllPrice());
		bundle.putString("sales", info.getSales());
		bundle.putString("storeid", id);
		bundle.putString("storename", shopName);
		bundle.putParcelable("good_info", info);
		bundle.putParcelableArrayList("list", poplist);
		intent.putExtras(bundle);
		getActivity().startActivityForResult(intent, 888);
		Log.e("renk", "msf>349");
		Log.e("renk", info.toString());

	}

	// Popupwindow 关闭后返回列表数据，更新rightlist 列表数据
	@Override
	public void backAllList(List<Goods_info> glist) {

		for (MerchantShopGoodBean good_bean : goodslist) {
			if (good_bean.getGoods_info() != null) {
				for (Goods_info info : good_bean.getGoods_info()) {
					for (Goods_info goods_info : glist) {
						if (info.getGoods_id().equals(goods_info.getGoods_id())) {
							info.setCounts(goods_info.getCounts());
						}
					}
				}
			}
		}
		rightlist.clear();
		rightlist.addAll(goodslist.get(leftCurrentPosition).getGoods_info());
		rightAdapter.notifyDataSetChanged();

	}

	/** pop回调设置数量和金额 **/
	@Override
	public void setNumAndAmount(int num, double amount) {
		bottomrl.setVisibility(View.VISIBLE);
		setCountInCar();
	}

	@Override
	public void goPay(ArrayList<Goods_info> goods) {
		if (poplist == null || poplist.size() < 1) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < goods.size(); i++) {
			DecimalFormat df = new DecimalFormat("0.00");
			String rs = df.format(Double.parseDouble(goods.get(i).getPrice())
					* goods.get(i).getCounts());
			sb.append("{\"goods_id\":\"" + goods.get(i).getGoods_id() + "\"")
					.append(",")
					.append("\"goods_name\":\"" + goods.get(i).getGoods_name()
							+ "\"")
					.append(",")
					.append("\"goods_price\":\"" + goods.get(i).getPrice()
							+ "\"")
					.append(",")
					.append("\"number\":\"" + goods.get(i).getCounts() + "\"")
					.append(",")
					.append("\"goods_total_price\":\"" + rs + "\"")
					.append(",")
					.append("\"goods_image\":\"" + goods.get(i).getThumb_pic()
							+ "\"").append("}");
			if (i != goods.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		HTTPHelper
				.GetOrderNo(getOrderNo, getAllPrice(goods), sb.toString(), id);

	}

	public String getAllPrice(ArrayList<Goods_info> popBackList) {
		DecimalFormat df = new DecimalFormat("0.00");
		double price = 0.0;
		for (Goods_info bean : popBackList) {
			price += bean.getCounts() * Double.parseDouble(bean.getPrice());
		}
		return df.format(price);
	}

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

			Intent intent = new Intent(getActivity(), ShowPayActivity.class);
			intent.putParcelableArrayListExtra("gods_list", poplist);
			intent.putExtra("total_price", getAllPrice());
			intent.putExtra("shop", shopName);
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
				HighCommunityApplication.toLoginAgain(getActivity());
			}
		}
	};
	
}
