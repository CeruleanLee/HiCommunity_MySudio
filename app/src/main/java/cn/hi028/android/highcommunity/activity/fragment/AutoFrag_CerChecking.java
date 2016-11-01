package cn.hi028.android.highcommunity.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.CerSuccesstAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;

/**
 * @功能：自治大厅 认证中<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_CerChecking extends BaseFragment {
    public static final String Tag = "~~~CerChecking~~~";
    public static final String FRAGMENTTAG = "AutoFrag_CerChecking";

    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
    CerSuccesstAdapter mAdapter;
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.tv_cerChecking_noData)
    TextView mNoData;
    @Bind(R.id.cerChecking_listView)
    PullToRefreshListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_cer_checking, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        Log.d(Tag, "initView");
        mProgress.setVisibility(View.VISIBLE);
        mAdapter = new CerSuccesstAdapter(mList, getActivity());
        mListView.setEmptyView(mNoData);
        mListView.setAdapter(mAdapter);
        mListView.setPullToRefreshEnabled(false);
//        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//            }
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(Tag, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Tag, "onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void updateList(List<Auto_CertificationInitBean.CertificationInitDataEntity> mList) {
        this.mList = mList;

    }
}
