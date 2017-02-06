package cn.hi028.android.highcommunity.activity.alliance;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.view.DownHint;
/**
 * 联盟商家 商品详情
 * @author Administrator
 *
 */
public class ShopDetailActivity extends Activity {
	private WebView webView;
	String url = "";
	private DownHint down;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		webView = (WebView) findViewById(R.id.webView);
		down = (DownHint) findViewById(R.id.down_load_hint);
		down.showProgress("正在努力加载…");
		url = getIntent().getStringExtra("url");
		Log.e("url", url);
		// 修改ua使得web端正确判断
		// showProgress();
		setSettings();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void setSettings() {
		// 启用支持javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// settings.setBlockNetworkImage(false);
		// webView加载web资源
		webView.setVisibility(View.VISIBLE);
		// WebSettings webSettings = webView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");

		if (Build.VERSION.SDK_INT >= 19) {
			webView.getSettings().setLoadsImagesAutomatically(true);
		} else {
			webView.getSettings().setLoadsImagesAutomatically(false);
		}

		// webView.addJavascriptInterface();
		webView.setWebChromeClient(new WebChromeClient());
		webView.getSettings().setPluginState(PluginState.ON);
		webView.loadUrl(url);

		// 覆盖webView默认使用第三方或系统默认浏览器打开网页的行为，使网页用webView打开
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去webView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				 String newUrl = webView.getUrl();
				Log.e("newUrl", "newUrl:" + newUrl);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// down.hideView();
				if (!webView.getSettings().getLoadsImagesAutomatically()) {
					webView.getSettings().setLoadsImagesAutomatically(true);
				}
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						down.hideView();
					}
				}, 1000);
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				ShopDetailActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ShopDetailActivity.this.finish();
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}