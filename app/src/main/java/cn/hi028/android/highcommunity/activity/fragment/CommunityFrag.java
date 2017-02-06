package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MainActivity;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.CommunityListAdapter2;
import cn.hi028.android.highcommunity.bean.CommunityBean;
import cn.hi028.android.highcommunity.bean.CommunityListBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LoadingView;

/**
 * @功能：邻里界面<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-08<br>
 */
public class CommunityFrag extends Fragment {
    public static final String FRAGMENTTAG = "CommunityFrag";
    final String Tag = "数据流Frag--->";
    private int mCount = -1;
    private PullToRefreshListView mListView;
    private ImageView mChange;
    RelativeLayout layoutContainer;
    private LoadingView mLoadingView;
    private TextView mNodata;
    private View mProgress;
    CommunityListAdapter2 mAdapter;
    CommunityListBean mList = new CommunityListBean();
    CommunityListBean mListData = new CommunityListBean();
    CommunityBean mBean = null;
    public static boolean isNeedRefresh = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_community_list, null);
        findView(view);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);
        initReceiver();
        initView();
        return view;
    }

    private void initReceiver() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Constacts.BROADCAST);
        getActivity().registerReceiver(mReceiver, mFilter);
    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isNeedRefresh = true;
            onResume();
        }
    };

    private void initView() {
        mAdapter = new CommunityListAdapter2((MainActivity) getActivity(),this);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mNodata);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mProgress.setVisibility(View.VISIBLE);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask2().execute();
            }
        });
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HighCommunityUtils.isLogin()) {
                    VallageAct.toStartAct(getActivity(), 1, false);
                } else {
                    VallageAct.toStartAct(getActivity(), 0, false);
                }
                isGetInitMessage = true;

                isNeedRefresh = true;
            }
        });
        initDatas();
    }

    private void findView(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.ptrlv_community_listview);
        mChange = (ImageView) view.findViewById(R.id.iv_community_change);
        mNodata = (TextView) view.findViewById(R.id.tv_community_Nodata);
        mProgress = view.findViewById(R.id.progress_Community);
        layoutContainer = (RelativeLayout) view.findViewById(R.id.layoutContainer);
        mLoadingView = (LoadingView) view.findViewById(R.id.loadingView);
    }


    private void initDatas() {
        Log.e(Tag, "initDatas ");
        mLoadingView.startLoading();
        HTTPHelper.GetMessage2(mIbpi);
        Log.e(Tag, "GetMessage2 ");
    }
    int isNeedToClearData = -1;
    public static boolean isGetInitMessage;
    public boolean isGetInitMessage() {
        return isGetInitMessage;
    }
    public void setGetInitMessage(boolean getInitMessage) {
        isGetInitMessage = getInitMessage;
    }
public void setIsClick(boolean isGetInitMessage){
    this.isGetInitMessage=isGetInitMessage;
}
    @Override
    public void onResume() {
        Log.e(Tag, "---onResume---");
        /**刷新数据**/
        if (!isGetInitMessage) {
            RefreshDataForResume(0);
        }else{
            isGetInitMessage = !isGetInitMessage;
        }
        registNetworkReceiver();
        isNeedRefresh = false;
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregistNetworkReceiver();
    }
    int refreshType = 0;
    /**
     * @param type 刷新方式（0-下拉刷新，1-加载更多）
     */
    private void RefreshDataForResume(int type) {
        Log.e(Tag, "RefreshDataForResume  type--- " + type);
        refreshType = type;
        String time = "";
        if (type == 0) {
            mCount = 0;
            if (mAdapter != null && mAdapter.getCount() > 0) {
                Log.e(Tag, "0  mAdapter.getCount()--- " + mAdapter.getCount());
                time = mAdapter.getItem(0).getCreate_time();//mList.getData().get(0).getCreate_time();
            }
        } else {
            mCount = 1;
            if (mAdapter != null && mAdapter.getCount() > 0) {
                Log.e(Tag, "1  mAdapter.getCount()--- " + mAdapter.getCount());
                time = mAdapter.getItem(mAdapter.getCount() - 1).getCreate_time();//mList.getData().get(mList.getData().size() - 1).getCreate_time();
            }
        }
        //刷新方式（0-下拉刷新，1-加载更多）
        HTTPHelper.RefreshMessage2(mIbpi2, type, time);//
    }
    /**
     * 只做back监听
     * @return
     */
    public boolean onKeyDown() {
        if (mAdapter != null)
            return mAdapter.onKeyDown();
        return false;
    }
    /**
     * 只是获取信息流  不刷新
     **/
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
            if (mCount == -1) {
                mAdapter.ClearData();
            }
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mList = (CommunityListBean) message;
            mListData = mList;
            Log.e(Tag, "mCount:" + mCount + ",数据长度：" + mList.getData().size());
            mAdapter.ClearData();
            mAdapter.SetData(mList.getData());
            mListView.onRefreshComplete();
            mLoadingView.loadSuccess();
            layoutContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveMessage(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
    /***
     * 专为resume刷新
     ***/
    BpiHttpHandler.IBpiHttpHandler mIbpi2 = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
            if (mCount == -1) {
                mAdapter.ClearData();
            }
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mList = (CommunityListBean) message;
            mListData = mList;
            if (refreshType == 0) {
                mAdapter.AddNewData(mList.getData());
                mListView.setAdapter(mAdapter);
            } else if (refreshType == 1) {
                mAdapter.RefreshData(mList.getData());
            }
            mListView.onRefreshComplete();
            mLoadingView.loadSuccess();
            layoutContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveMessage(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };
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
                    //有网络
                    isNoNetwork = false;
                } else {
                    //没有网络
                    mLoadingView.noNetwork();
                    isNoNetwork = true;
                }
            }
        }
    }
    private boolean isNoNetwork;

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RefreshDataForResume(0);
        }
    }

    private class GetDataTask2 extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RefreshDataForResume(1);
        }
    }
}