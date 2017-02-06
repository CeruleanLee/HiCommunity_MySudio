package cn.hi028.android.highcommunity.activity.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.CerFailedAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;

/**
 * @功能：自治大厅 认证失败-展示认证失败信息界面 数据从commitAct获取 <br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_CerFailedMsg extends BaseFragment {
    public static final String Tag = "~~~CerFailedMsg";
    public static final String FRAGMENTTAG = "AutoFrag_CerFailedMsg";

    CerFailedAdapter mAdapter;
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList;
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.tv_cerFailed_noData)
    TextView mNoData;
    @Bind(R.id.cerFailed_listView)
    PullToRefreshListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_cer_failed, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        Log.d(Tag, "initView");
        DisplayMetrics mdm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mdm);

        mProgress.setVisibility(View.VISIBLE);
        mAdapter = new CerFailedAdapter(mList, getActivity(), mdm.widthPixels);
        mListView.setEmptyView(mNoData);
        mListView.setAdapter(mAdapter);
        mListView.setPullToRefreshEnabled(false);
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
}
