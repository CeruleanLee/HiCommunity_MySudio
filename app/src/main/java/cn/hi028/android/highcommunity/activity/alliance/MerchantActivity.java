package cn.hi028.android.highcommunity.activity.alliance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.fragment.alliance.MerchantDetailFrag;
import cn.hi028.android.highcommunity.activity.fragment.alliance.MerchantEvaluationFrag;
import cn.hi028.android.highcommunity.activity.fragment.alliance.MerchantShopFrag;
import cn.hi028.android.highcommunity.bean.Goods_info;

/**
 * 联盟商家item点击 进来的 包含了三个frag的act
 * @author Administrator
 *
 */
public class MerchantActivity extends BaseFragmentActivity implements
		OnClickListener, CompoundButton.OnCheckedChangeListener {

	LinearLayout content;

	MerchantShopFrag shop;
	MerchantEvaluationFrag evaluation;
	MerchantDetailFrag detail;

	LinearLayout back;
	ImageView gobackIMG;
	private String id;
	RadioButton shoprb, evaluationrb, detailrb;
	ArrayList<BaseFragment> fragments;
	BaseFragment currentFragment;
	private String shopName="";
	private TextView titlename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merchant);
		LogUtil.d("------MerchantActivity");
		id = getIntent().getStringExtra("id");
		shopName = getIntent().getStringExtra("shop");
		init();
	}

	private void init() {
		content = (LinearLayout) findViewById(R.id.ac_merchant_content_ll);
		back = (LinearLayout) findViewById(R.id.ll_ac_merchant_title_go_back);
		gobackIMG=(ImageView) findViewById(R.id.ac_merchant_title_go_back);
		shoprb = (RadioButton) findViewById(R.id.ac_merchant_shop_rb);
		evaluationrb = (RadioButton) findViewById(R.id.ac_merchant_evaluation_rb);
		detailrb = (RadioButton) findViewById(R.id.ac_merchant_detail_rb);
		titlename = (TextView) findViewById(R.id.ac_merchant_title_name);
		titlename.setText(shopName);

		fragments = new ArrayList<BaseFragment>();
		shop = new MerchantShopFrag();
		evaluation = new MerchantEvaluationFrag();
		detail = new MerchantDetailFrag();
		fragments.add(shop);
		fragments.add(evaluation);
		fragments.add(detail);

		shop.setMyId(id, shopName);
		evaluation.setMyId(id);
		detail.setMyId(id);
		gobackIMG.setOnClickListener(this);
		back.setOnClickListener(this);
		shoprb.setOnCheckedChangeListener(this);
		shoprb.setChecked(true);
		evaluationrb.setOnCheckedChangeListener(this);
		detailrb.setOnCheckedChangeListener(this);
	}

	public void oncheck(int index) {
		FragmentTransaction transation = getSupportFragmentManager()
				.beginTransaction();
		if (currentFragment != null) {
			transation.hide(currentFragment);
		}
		if (!fragments.get(index).isAdded()) {
			transation.add(R.id.ac_merchant_content_ll, fragments.get(index));
		}
		transation.show(fragments.get(index));
		// transation.replace(R.id.ac_merchant_content_ll,
		// fragments.get(index));
		transation.addToBackStack("" + index);
		currentFragment = fragments.get(index);
		transation.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_ac_merchant_title_go_back:
		onBackPressed();
			break;
		case R.id.ac_merchant_title_go_back:
			onBackPressed();
			break;
		}
		/*
		 * if (v == back) { this.finish(); }
		 */

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		switch (buttonView.getId()) {
		case R.id.ac_merchant_shop_rb:
			if (isChecked)
				oncheck(0);
			break;
		case R.id.ac_merchant_evaluation_rb:
			if (isChecked)
				oncheck(1);
			break;
		case R.id.ac_merchant_detail_rb:
			if (isChecked)
				oncheck(2);
			break;
		}
	}

	int onresume = 0;

	@Override
	protected void onResume() {
		super.onResume();
		// oncheck(index);
		Log.e("renk", "OnResume");
		Log.e("renk", "OnResume" + ++onresume);

	}
	
	@Override
	protected void onActivityResult(int requse, int result, Intent data) {
		/** 商品详情，请求码 */
		if (requse == 888 && result == 888 && currentFragment == shop) {
			ArrayList<Goods_info> goodslist = data.getParcelableArrayListExtra("list");
			if (goodslist != null && goodslist.size()>0) {
				shop.setBackCallCount(goodslist);
			} else {
				shop.setBackCallCount(null);
			}
			
//			Toast.makeText(this, "requse" + requse + "" + result, 0).show();
			Log.e("renk", "onActivityResult>meac");
			
		}
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		
	}
	
}
