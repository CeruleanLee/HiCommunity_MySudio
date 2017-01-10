/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.TicketAdapter;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：钱包优惠券页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/29<br>
 */
@EFragment(resName = "frag_wallettickit")
public class WalletTickitFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "WalletScoreFrag";
    @ViewById(R.id.tv_walletTickit_listView)
    PullToRefreshListView mRecodeList;
    @ViewById(R.id.progress_walletTickit)
    View mProgress;
    @ViewById(R.id.tv_walletTickit_Nodata)
    TextView mNoData;

    List<AllTicketBean> mBean;
    TicketAdapter mAdapter;

    @AfterViews
    void initView() {
        mAdapter = new TicketAdapter(getActivity(), 0);
        mRecodeList.setMode(PullToRefreshBase.Mode.DISABLED);
        mRecodeList.setAdapter(mAdapter);
        mRecodeList.setEmptyView(mNoData);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            HTTPHelper.getTicketList(mIbpi, null, null);
        }
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNoData.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mBean = (List<AllTicketBean>) message;
            mAdapter.setData(mBean);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveTicket(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

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
