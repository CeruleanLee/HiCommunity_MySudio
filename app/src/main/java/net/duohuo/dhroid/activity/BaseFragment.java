package net.duohuo.dhroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//		InjectUtil.inject(this);
    }

    public void onClickRight() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Fragment");
//        registNetworkReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Fragment");
//        unregistNetworkReceiver();
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }




}
