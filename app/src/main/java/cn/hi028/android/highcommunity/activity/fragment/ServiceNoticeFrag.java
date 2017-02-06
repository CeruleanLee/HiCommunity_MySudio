/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.activity.BrowseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceAct;
import cn.hi028.android.highcommunity.adapter.NoticeAdapter;
import cn.hi028.android.highcommunity.bean.NoticeBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：物业和政务公告<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/28<br>
 */
@EFragment(resName = "frag_service_notice")
public class ServiceNoticeFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "ServiceNoticeFrag";

    @ViewById(R.id.ptrlv_service_notice_listView)
    PullToRefreshListView mListView;
    @ViewById(R.id.progress_service_notice)
    View mProgress;
    @ViewById(R.id.tv_serviceNotice_Nodata)
    TextView mNodata;

    NoticeAdapter mAdapter;
    List<NoticeBean> mlist = new ArrayList<NoticeBean>();
    int mCount = 0;
    int type = 1;
    String url = "http://028hi.cn/api/notice/site.html?nid=";
    int flag = -1;

    @AfterViews
    void initView() {
        flag = getActivity().getIntent().getIntExtra(ServiceAct.ACTIVITYTAG, -1);
        mProgress.setVisibility(View.VISIBLE);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setEmptyView(mNodata);
        if (-1 == flag) {
            return;
        } else if (flag == Constacts.SERVICE_NOTICE) {
            type = 2;
        } else {
            type = 1;
        }

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCount = 0;
                HTTPHelper.GetNotice(mIbpi, type + "", HighCommunityApplication.mUserInfo.getV_id() + "", mCount + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTPHelper.GetNotice(mIbpi, type + "", HighCommunityApplication.mUserInfo.getV_id() + "", mCount + "");
            }
        });
        if (flag == Constacts.SERVICE_NOTICE_ONE) {
            mAdapter = new NoticeAdapter(getActivity(), 1);
        } else {
            mAdapter = new NoticeAdapter(getActivity());
        }

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = mAdapter.getItem(i - 1).getId();
                BrowseActivity.toBrowseActivity(getActivity(), "公告", url + id);
            }
        });
    }

    @Override
    public void onResume() {
        mCount = 0;
        HTTPHelper.GetNotice(mIbpi, type + "", HighCommunityApplication.mUserInfo.getV_id() + "", mCount + "");
        super.onResume();
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mlist = (List<NoticeBean>) message;
            if (mCount == 0) {
                mAdapter.AddNewData(mlist);
            } else {
                mAdapter.RefreshData(mlist);
            }
            ++mCount;
            mListView.onRefreshComplete();
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveNotice(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
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

}
