/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ActiveAct;
import cn.hi028.android.highcommunity.adapter.MyActivityAdapter;
import cn.hi028.android.highcommunity.bean.ActiveBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * @功能：我收藏的活动<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-2-18<br>
 */
@EFragment(resName = "frag_mycollection_act")
public class MyCollectionActFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "MyCollectionActFrag";
    @ViewById(R.id.ptrlv_activity_listView)
    PullToRefreshListView mListView;
    @ViewById(R.id.progress_activity_notice)
    View mProgress;
    @ViewById(R.id.tv_activity_Nodata)
    TextView mNodata;
    @ViewById(R.id.tv_activity_create)
    TextView mCreate;

    List<ActiveBean> mlist;
    MyActivityAdapter mAdapter;

    @AfterViews
    void iniView() {
        mCreate.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
        mListView.setEmptyView(mNodata);
        mAdapter = new MyActivityAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                new GetDataTask().execute();
//            }
//        });
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                HTTPHelper.GetMyCollectActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
                new GetDataTask().execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    public void onResume() {
        HTTPHelper.GetMyCollectActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
        super.onResume();
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mListView.onRefreshComplete();
            mProgress.setVisibility(View.GONE);

        }

        @Override
        public void onSuccess(Object message) {
            mListView.onRefreshComplete();
            mProgress.setVisibility(View.GONE);
            if (message == null)
                return;
            mlist = (List<ActiveBean>) message;
            mAdapter.AddNewData(mlist);

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveActivitylist(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
        }
    };

    @Click(R.id.tv_activity_create)
    void create() {
        Intent ActCreate = new Intent(getActivity(), GeneratedClassUtils.get(ActiveAct.class));
        ActCreate.putExtra(ActiveAct.ACTIVITYTAG, Constacts.ACTIVITY_CREATE);
        startActivity(ActCreate);
    }


    private class GetDataTask extends AsyncTask<Void, Void,String > {

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
            HTTPHelper.GetMyCollectActivityList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
            // Call onRefreshComplete when the list has been refreshed.
            //				mListView.onRefreshComplete();
        }
    }
}