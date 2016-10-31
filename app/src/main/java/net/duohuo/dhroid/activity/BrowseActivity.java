package net.duohuo.dhroid.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.Debug;
import com.don.tools.GeneratedClassUtils;

import net.duohuo.dhroid.util.ParamsContacts;
import net.duohuo.dhroid.view.ProgressWebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;

@EActivity(resName = "activity_browse")
public class BrowseActivity extends BaseFragmentActivity {
    @ViewById(R.id.pw_browse)
    ProgressWebView webview;
    @ViewById(R.id.tv_secondtitle_name)
    TextView tv_title_name;
    @ViewById(R.id.img_back)
    ImageView img_back;
    private String url, title;

    public static void toBrowseActivity(Context activity, String title,
                                        String url) {
        Intent intent = new Intent(activity, GeneratedClassUtils.get(BrowseActivity.class));
        intent.putExtra(ParamsContacts.BROWSE_TITLE, title);
        intent.putExtra(ParamsContacts.BROWSE_URL, url);
        activity.startActivity(intent);
    }

    @AfterViews
    void onCreate() {
        // 获取参数
        url = getIntent().getStringExtra(ParamsContacts.BROWSE_URL);
        title = getIntent().getStringExtra(ParamsContacts.BROWSE_TITLE);
        tv_title_name.setText(title);
        img_back.setVisibility(View.VISIBLE);
        loadWeb(url);
        Debug.info("Browse url---->", url);
    }

    private void loadWeb(String url) {
        // ~~~ 绑定控件
        webview.setDownloadListener(new DownloadListener() {

            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // 设置支持javascript脚本
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // // 设置可以访问的文件
        // webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置webviewclient
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        webview.loadUrl(url);
        webview.setWebViewClient(myWebViewClient);
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Click(R.id.img_back)
    void back() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }
}
