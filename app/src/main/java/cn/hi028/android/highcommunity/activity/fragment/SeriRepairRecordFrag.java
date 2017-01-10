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
import cn.hi028.android.highcommunity.adapter.SeriRepairRecordAdapter;
import cn.hi028.android.highcommunity.bean.RepairBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：报修记录Frag<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-06<br>
 */
@EFragment(resName = "frag_seri_repair_record")
public class SeriRepairRecordFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "SeriRepairRecordFrag";
    @ViewById(R.id.ptrlv_repair_record)
    PullToRefreshListView mListView;
    SeriRepairRecordAdapter adapter;
    @ViewById(R.id.tv_NoticeDetails_noData)
    TextView mNoData;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View mProgress;
    @AfterViews
    void initView() {
        adapter=new SeriRepairRecordAdapter(this.getActivity());
        mListView.setAdapter(adapter);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mProgress.setVisibility(View.VISIBLE);
        HTTPHelper.GetRepairRecordList(mIbpiRepair, HighCommunityApplication.mUserInfo.getId() + "");
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiRepair = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
            HighCommunityUtils.GetInstantiation().ShowToast(message,0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message){
                mNoData.setVisibility(View.VISIBLE);
                return;
            }
            List<RepairBean> data = (  List<RepairBean> ) message;
            adapter.setmList(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveRepairRecordList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
                getActivity().finish();
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
