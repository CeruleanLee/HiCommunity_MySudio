package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoNoticeAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NoticeListBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅认证完成 公告Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_Notice extends BaseFragment {
    public static final String Tag = "~~~AutonomousMainFrag~~~";
    public static final String FRAGMENTTAG = "AutoFrag_Notice";
    @Bind(R.id.frag_Autonotice_listview)
    ListView mListview;
    @Bind(R.id.tv_Autonotice_Nodata)
    TextView mNodata;
    AutoNoticeAdapter mAdapter;
    private List<Auto_NoticeListBean.NoticeListDataEntity> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_notice, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        LogUtil.d(Tag + "initView");
        mList = new ArrayList<Auto_NoticeListBean.NoticeListDataEntity>();
        mAdapter = new AutoNoticeAdapter(mList, getActivity());
        mListview.setEmptyView(mNodata);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url="http://028hi.cn/ywh/notice/detail.html?id="+mList.get(position).getId();
                BrowseActivity.toBrowseActivity(getActivity(), mList.get(position).getTitle(), url);
            }
        });

        initDatas();
    }

    private void initDatas() {
        HTTPHelper.AutoNoticeList(mIbpi);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }
        @Override
        public void onSuccess(Object message) {
            mList = (List<Auto_NoticeListBean.NoticeListDataEntity>) message;
            mAdapter.AddNewData(mList);
            mListview.setAdapter(mAdapter);


        }

        @Override
        public Object onResolve(String result) {
			LogUtil.d(Tag+" ~~~result"+result);
            return HTTPHelper.ResolveAutoNoticeListEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }
        @Override
        public void cancleAsyncTask() {

        }
    };


    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(Tag + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");

        //		mLoadingView.startLoading();
//        registNetworkReceiver();
    }


    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    initDatas();
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}
