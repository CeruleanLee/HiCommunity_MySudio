package cn.hi028.android.highcommunity.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;

public class Service_SurveyWorldActivity extends Activity {
	private WebView mWebView;
	private ImageView back;

	private ValueCallback<Uri> mUploadMessage;// 琛ㄥ崟鐨勬暟鎹俊鎭�
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private Uri imageUri;
	private TextView mTitle;

	private void init() {
		mWebView = (WebView) findViewById(R.id.community_webview);
		mTitle = (TextView) findViewById(R.id.tv_secondtitle_name);
		mTitle.setText("调查天地");
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		String appCachePath = getApplicationContext().getCacheDir()
				.getAbsolutePath();
		mWebView.getSettings().setAppCachePath(appCachePath);
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.setWebChromeClient(new WebChromeClient());
		back = (ImageView) findViewById(R.id.img_back);
		WebSettings webSettings = mWebView.getSettings();
		// 设置WebView编码
		webSettings.setDefaultTextEncodingName("UTF-8");
		mWebView.getSettings().setRenderPriority(
				WebSettings.RenderPriority.HIGH);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 自适应屏幕
		WebSettings settings = mWebView.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 支持H5伸缩
		// webSettings.setBuiltInZoomControls(true);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community);
		init();
		// 这里就是在加载页面
		mWebView.loadUrl("http://028hi.cn/community/survey/index.html?token="
				+ HighCommunityApplication.mUserInfo.getToken());// loadurl这个方法是直接加载链接地址
		// mWebView.loadDataWithBaseURL(null,
		// "http://028hi.cn/ywh/owner/index.html", "text/html", "utf-8",
		// null);//这个方法是加载包含H5标签的内容，你服务器娶过来的应该就是包含H5标签的文字，到时候你直接把他放到上面就行了

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			// @Override
			// public void onPageStarted(WebView view, String url, Bitmap
			// favicon) {
			// super.onPageStarted(view, url, favicon);
			// }onactivity>result
			// @Override
			// public void onPageFinished(WebView view, String url) {
			// super.onPageFinished(view, url);
			// }
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			// android5.0以上
			@Override
			public boolean onShowFileChooser(WebView webView,
					ValueCallback<Uri[]> filePathCallback,
					FileChooserParams fileChooserParams) {
				mUploadCallbackAboveL = filePathCallback;
				take();
				return true;

			}

			// android 3.0以下：
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				take();
			}

			// android 3.0以上，android4.0以下：
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType) {
				mUploadMessage = uploadMsg;
				take();
			}

			// android4.4.4
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				take();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage && null == mUploadCallbackAboveL)
				return;
			Uri result = data == null || resultCode != RESULT_OK ? null : data
					.getData();
			if (mUploadCallbackAboveL != null) {
				onActivityResultAboveL(requestCode, resultCode, data);
			} else if (mUploadMessage != null) {
				Log.e("result", result + "");
				if (result == null) {
					// mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage = null;

					Log.e("imageUri", imageUri + "");
				} else {
					mUploadMessage.onReceiveValue(result);
					mUploadMessage = null;
				}

			}
		}
	}

	@SuppressLint("NewApi")
	private void onActivityResultAboveL(int requestCode, int resultCode,
			Intent data) {
		if (requestCode != FILECHOOSER_RESULTCODE
				|| mUploadCallbackAboveL == null) {
			return;
		}

		Uri[] results = null;
		if (resultCode == Activity.RESULT_OK) {
			if (data == null) {
				results = new Uri[] { imageUri };
			} else {
				String dataString = data.getDataString();
				ClipData clipData = data.getClipData();

				if (clipData != null) {
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++) {
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}

				if (dataString != null)
					results = new Uri[] { Uri.parse(dataString) };
			}

		}
		if (results != null) {
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		} else {
			results = new Uri[] { imageUri };
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}

		return;
	}

	private void take() {
		File imageStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyApp");
		// Create the storage directory if it does not exist
		if (!imageStorageDir.exists()) {
			imageStorageDir.mkdirs();
		}
		File file = new File(imageStorageDir + File.separator + "IMG_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg");
		imageUri = Uri.fromFile(file);

		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(
				captureIntent, 0);
		for (ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent i = new Intent(captureIntent);
			i.setComponent(new ComponentName(res.activityInfo.packageName,
					res.activityInfo.name));
			i.setPackage(packageName);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			cameraIntents.add(i);

		}
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("image/*");
		Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
				cameraIntents.toArray(new Parcelable[] {}));
		Service_SurveyWorldActivity.this.startActivityForResult(chooserIntent,
				FILECHOOSER_RESULTCODE);

	}

}
