package cn.hi028.android.highcommunity.activity;

import android.os.Bundle;

import cn.hi028.android.highcommunity.R;

public class AutoAct_Four extends BaseFragmentActivity{
String vote_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auto_act_four);
        vote_id=getIntent().getStringExtra("vote_id");

    }
}
