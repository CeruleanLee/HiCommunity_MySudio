package cn.hi028.android.highcommunity.activity.fragment;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.GoodsData;

public class Frag_PicDetail extends BaseFragment implements OnClickListener{
	WebView mWebview;
	private WebSettings mSetting;
	Bundle bundle2;
	TextView time,telephone;  View call;
	private String telPhone = "";
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bundle2  = getArguments();

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_picdetail, null);
	}
	RelativeLayout layoutCall;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GoodsData goodsdata = (GoodsData) bundle2.get("data");

		//		LogUtil.d("========== goodsdata  :"+goodsdata.toString());
		String content =goodsdata.getDetail();
		LogUtil.d("========="+content);
		LogUtil.d("========="+goodsdata);
		findVByID();
		call.setOnClickListener(this);
		telephone.setOnClickListener(this);
		if (goodsdata!=null) {

			aboutCallService(goodsdata);
		}


		mSetting = mWebview.getSettings();

		mSetting.setDefaultTextEncodingName("utf-8");
		//		mSetting.setDisplayZoomControls(false);
		//		mSetting.setUseWideViewPort(true);
		//		mSetting.setLoadWithOverviewMode(true);
		//		mWebview.setWebViewClient(new WebViewClient() {
		//			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
		//				view.loadUrl(url);
		//				return true;
		//			}
		//		});


		mWebview.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				layoutCall.setVisibility(View.VISIBLE);
			}
		});
		LogUtil.d("----------准备loadUrl-------------："+content);
		mWebview.loadUrl(content);
	}
	private void findVByID() {
		telephone = (TextView) getActivity().findViewById(R.id.shop_detail_service_telephone);
		time = (TextView)getActivity().findViewById(R.id.ac_shop_detail_service_time);
		call = getActivity().findViewById(R.id.call);
		mWebview=(WebView) getActivity().findViewById(R.id.ac_good_detail_webview);
		layoutCall=(RelativeLayout) getActivity().findViewById(R.id.layout1);
	}

	private void setUi(String str) {
		// webview.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
		mWebview.loadUrl(str);
	}

	private void aboutCallService(GoodsData msg) {
		if (null != msg.getTel()) {
			telephone.setText("客服电话：" + msg.getTel());
			telPhone = msg.getTel();
		}
		if (null != msg.getDelivery())
			time.setText("服务时间：" + msg.getDelivery());
	}
	@Override
	public void onClick(View arg0) {
		if (arg0==call) {
			Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ telPhone));
			startActivity(intent2);
		}

	}
}
