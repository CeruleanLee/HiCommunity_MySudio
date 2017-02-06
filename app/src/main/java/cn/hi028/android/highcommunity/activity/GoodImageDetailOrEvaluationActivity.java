package cn.hi028.android.highcommunity.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.EvaluationAdapter;
import cn.hi028.android.highcommunity.bean.GoodsData;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;

public class GoodImageDetailOrEvaluationActivity extends BaseFragmentActivity {

    WebView webview;
    private WebSettings setting;
    /**
     * 0为detail，1为评价
     **/
    int type;
    TextView title;
    private ImageView ibBack;
    ListView listview;
    EvaluationAdapter adapter;
    private List<MerchantEvaluationInfoListBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_goods_detail_or_evaluation);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        webview = (WebView) findViewById(R.id.ac_good_detail_webview);
        title = (TextView) findViewById(R.id.ac_good_title_name);
        listview = (ListView) findViewById(R.id.ac_good_evaluation_listview);
        ibBack = (ImageView) findViewById(R.id.ac_good_title_go_back);
        setting = webview.getSettings();

        ibBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                goBack(ibBack);
            }
        });

        setting.setDefaultTextEncodingName("utf-8");
        setting.setDisplayZoomControls(false);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        if (type == 1) {
            title.setText("商品评价");
            webview.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            data = new ArrayList<MerchantEvaluationInfoListBean>();
            GoodsData goodsdata = (GoodsData) bundle.get("data");
            data = goodsdata.getComments();
            if (data != null) {
                adapter = new EvaluationAdapter(this, data);
                adapter.setType(2);
                listview.setAdapter(adapter);
            }
        } else {
            webview.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
            title.setText("图文详情");
            String content = bundle.getString("detail");
            setUi(content);
        }
    }

    private void setUi(String str) {
        webview.loadUrl(str);
    }

    public void goBack(View v) {
        this.finish();
    }
}
