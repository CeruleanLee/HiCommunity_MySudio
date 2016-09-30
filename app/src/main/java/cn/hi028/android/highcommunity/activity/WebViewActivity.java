package cn.hi028.android.highcommunity.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import cn.hi028.android.highcommunity.R;

public class WebViewActivity extends BaseFragmentActivity {

	ImageView goBack;
	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		goBack = (ImageView) findViewById(R.id.img_back);
		webview = (WebView) findViewById(R.id.activity_webview);
		String url = getIntent().getStringExtra("url");
		setWebView(webview);
		webview.loadUrl(url);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	private void setWebView(WebView webview) {
		WebSettings setting = webview.getSettings();
		setting.setBuiltInZoomControls(false);
		setting.setSupportZoom(false);
		setting.setDefaultTextEncodingName("utf-8");
		webview.setWebChromeClient(new WebChromeClient());
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
	}
}
