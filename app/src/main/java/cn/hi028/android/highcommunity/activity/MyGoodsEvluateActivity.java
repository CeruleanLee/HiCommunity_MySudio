package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.don.tools.BpiHttpHandler;
import com.squareup.picasso.Picasso;

import net.duohuo.dhroid.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.OnEvaluateBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * 商品评价
 * @author Administrator
 *
 */
public class MyGoodsEvluateActivity extends BaseFragmentActivity implements OnClickListener {

	List<OnEvaluateBean> BeanList;

	ImageView goods_image;
	TextView goods_name, goods_price, shop_name, comit;
	EditText content;
	int type = 0;
	TextView titlename;
	String id;
ImageView goBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evluate);
		LogUtil.d("------MyGoodsEvluateActivity");
		id = getIntent().getStringExtra("order_num");
		BeanList = new ArrayList<OnEvaluateBean>();
		goods_image = (ImageView) findViewById(R.id.ac_evluate_goods_imageview);
		goods_name = (TextView) findViewById(R.id.ac_evluate_goods_name_tv);
		goods_price = (TextView) findViewById(R.id.ac_evluate_goods_price_tv);
		shop_name = (TextView) findViewById(R.id.ac_evluate_shop_name_tv);
		comit = (TextView) findViewById(R.id.ac_evaluate_send_tv);
		content = (EditText) findViewById(R.id.ac_evluate_edit_text);
		titlename = (TextView) findViewById(R.id.tv_secondtitle_name);
		goBack=(ImageView) findViewById(R.id.img_back);
		titlename.setText("评价");
		comit.setOnClickListener(this);
		goBack.setOnClickListener(this);
		HTTPHelper.GetEvaluate(mIbpi, id);
	}

	private BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {

		}

		@Override
		public void onSuccess(Object message) {
			if (type == 0) {
				BeanList = JSON.parseArray(message.toString(),
						OnEvaluateBean.class);
				Log.e("renk", BeanList.toString());
				setUi(BeanList);
			} else {
				Toast.makeText(MyGoodsEvluateActivity.this, "亲，评论成功，谢谢！", Toast.LENGTH_SHORT)
						.show();
				Log.e("renk", message.toString());
				finish();
			}
		}

		@Override
		public Object onResolve(String result) {
			return result;
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			// TODO Auto-generated method stub

		}

		@Override
		public void cancleAsyncTask() {
			// TODO Auto-generated method stub

		}

		@Override
		public void shouldLogin(boolean isShouldLogin) {

		}

		@Override
		public void shouldLoginAgain(boolean isShouldLogin, String msg) {

		}
	};
	OnEvaluateBean bean;

	private void setUi(List<OnEvaluateBean> beanList) {
		bean = beanList.get(0);
		Picasso.with(this).load(Constacts.IMAGEHTTP + bean.getThumb_pic())
				.placeholder(R.drawable.default_ptr_flip).into(goods_image);
		goods_name.setText(bean.getGoods_name());
		goods_price.setText("¥" + bean.getGoods_price());
		shop_name.setText(bean.getShop_name());
	}

	@Override
	public void onClick(View v) {
		if (v == comit) {
			if (TextUtils.isEmpty(content.getText())) {
				Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
			} else {
				try {
					//v1.0
//					JSONObject obj = new JSONObject();
//					obj.put("goods_id", bean.getGoods_id());
//					obj.put("content", content.getText().toString().trim());
//					type = 1;
//					HTTPHelper.SendEvaluate(mIbpi, id, obj);
					//v2.0json化的数组字符串, 如[{"gid":1,"content":"味道真错"},{"gid":1,"content":"物美价廉"}]
//					gid表示商品id, content为某个商品对应的评论内容
					JSONObject obj = new JSONObject();
					obj.put("gid", bean.getGoods_id());
					obj.put("content", content.getText().toString().trim());
					type = 1;
					HTTPHelper.SendEvaluate2(mIbpi, obj,id);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}else if (v==goBack) {
			onBackPressed();
		}
	}
}
