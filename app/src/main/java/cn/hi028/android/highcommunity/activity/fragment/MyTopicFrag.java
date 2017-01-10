package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.adapter.CommunityListAdapter2;
import cn.hi028.android.highcommunity.bean.CommunityBean;
import cn.hi028.android.highcommunity.bean.CommunityListBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @说明：我的话题
 * @作者： Lee_yting
 * @时间：2017/1/5 0005
 */
public class MyTopicFrag extends Fragment {
    public static final String TAG = "MyTopicFrag";

    public static final String FRAGMENTTAG = "MyTopicFrag";
    private View mFragmeView;
    private int mCount = -1;
    CommunityListAdapter2 mAdapter;
    private PullToRefreshListView mListView;
    private TextView mNodata;
    CommunityListBean mList = new CommunityListBean();
    CommunityBean mBean = null;
    String vid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmeView == null) {
            initView();
        }
        ViewGroup parent = (ViewGroup) mFragmeView.getParent();
        if (parent != null)
            parent.removeView(mFragmeView);
        return mFragmeView;
    }
    private void initView() {
        mFragmeView = LayoutInflater.from(getActivity()).inflate(
                R.layout.frag_community_list_mytopic, null);
        vid = getActivity().getIntent().getStringExtra(FRAGMENTTAG);
        mListView = (PullToRefreshListView) mFragmeView.findViewById(R.id.ptrlv_community_listview);
        mNodata = (TextView) mFragmeView.findViewById(R.id.tv_community_Nodata);
        mAdapter = new CommunityListAdapter2((MenuLeftAct) getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mNodata);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mBean = mAdapter.getItem(i - 1);
                Intent mCommunity = new Intent(getActivity(), GeneratedClassUtils.get(CommunityDetailAct.class));
                mCommunity.putExtra(CommunityDetailAct.ACTIVITYTAG, "Details");
                mCommunity.putExtra(CommunityDetailAct.INTENTTAG, mBean.getMid());
                startActivityForResult(mCommunity, 1);
            }
        });
    }

    @Override
    public void onResume() {
        mCount = -1;
        HTTPHelper.GetMyMessage(mIbpi, HighCommunityApplication.mUserInfo.getId());
        super.onResume();
    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mNodata.setText(message);
            mNodata.setVisibility(View.VISIBLE);
            mListView.onRefreshComplete();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mList = (CommunityListBean) message;
            if (null == mList||mList.getData()==null)
                return;
            mAdapter.ClearData();
            mAdapter.AddNewData(mList.getData());
            Log.e(TAG,"AddNewData onSuccess");
//            if (mCount == 0) {
//            } else if (mCount == 1) {
//                mAdapter.RefreshData(mList.getData());
//            } else if (mCount == -1) {
//                mAdapter.SetData(mList.getData());
//            }
            mListView.onRefreshComplete();
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

    public boolean onKeyDown() {
        return mAdapter.onKeyDown();
    }

}