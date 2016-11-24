package cn.hi028.android.highcommunity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;

public class ShowCaptureActivity extends Activity {

    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcapture);
        ButterKnife.bind(this);
        tv.setText(getIntent().getStringExtra("result"));

    }
}
