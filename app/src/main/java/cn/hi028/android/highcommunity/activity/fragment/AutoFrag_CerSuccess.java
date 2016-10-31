package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutoCommitAct;
import cn.hi028.android.highcommunity.adapter.CerSuccesstAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;

/**
 * @功能：自治大厅 认证成功（已认证）<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/28<br>
 */
public class AutoFrag_CerSuccess extends BaseFragment {
    public static final String Tag = "~~~CerSuccess~~~";
    public static final String FRAGMENTTAG = "AutoFrag_CerSuccess";
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.tv_cerSuccess_noData)
    TextView mNoData;
    @Bind(R.id.cerSuccess_listView)
    PullToRefreshListView mListView;
    @Bind(R.id.img_cerSuccess_creat)
    ImageButton but_Creat;
    /**
     * 创建认证
     **/
    public static final int TAG_CREAT_CER= 6;
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
    CerSuccesstAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_cer_success, null);
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
//
//            }
//
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

    private void ceratCer() {

        Intent mCreatCerIntent = new Intent(getActivity(), AutoCommitAct.class);
        mCreatCerIntent.putExtra("tag_creatCer", TAG_CREAT_CER);
////        mIntent_report.putExtra("owner_id", owner_id);
        startActivity(mCreatCerIntent);

    }

    @OnClick(R.id.img_cerSuccess_creat)
    public void onClick() {
        ceratCer();
//        Toast.makeText(getActivity(),"点击创建",Toast.LENGTH_SHORT).show();
    }

    public void updateList(List<Auto_CertificationInitBean.CertificationInitDataEntity> mList) {
        this.mList = mList;

    }
}
