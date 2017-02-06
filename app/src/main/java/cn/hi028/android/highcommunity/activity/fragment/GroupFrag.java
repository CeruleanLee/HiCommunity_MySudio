/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/


package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.GroupDataAct;
import cn.hi028.android.highcommunity.activity.GroupMessageAct;
import cn.hi028.android.highcommunity.activity.SearchActivity;
import cn.hi028.android.highcommunity.adapter.GroupAdapter;
import cn.hi028.android.highcommunity.adapter.GroupMineAdapter;
import cn.hi028.android.highcommunity.bean.GroupBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：群组列表<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/15<br>
 */
@EFragment(resName = "frag_group")
public class GroupFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "ServiceFrag";

    @ViewById(R.id.tv_group_hot)
    RadioButton mHot;
    @ViewById(R.id.tv_group_new)
    RadioButton mNew;
    @ViewById(R.id.tv_group_mine)
    RadioButton mMine;
    @ViewById(R.id.et_groupclass_search)
    ImageView mKeywords;
    @ViewById(R.id.tv_group_nodata)
    TextView mNodata;
    @ViewById(R.id.pg_grouplayout_progress)
    View mProgress;
    @ViewById(R.id.tv_group_create)
    ImageView mCreate;
    @ViewById(R.id.ptrlv_group_listview)
    PullToRefreshListView mListView;

    GroupAdapter mAdapter;
    GroupMineAdapter mMineAdapter;
    int type = 0;//0-最热，1-最新，2-分类，3-搜索
    String keywords = "";
    List<GroupBean> mlist = new ArrayList<GroupBean>();
    Handler mHandler = new Handler();

    @AfterViews
    void initView() {
        mProgress.setVisibility(View.VISIBLE);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setHeaderRefreshText("", "");
        mListView.setFooterRefreshText("", "");
        mListView.setEmptyView(mNodata);
        mAdapter = new GroupAdapter(getActivity());
        mMineAdapter = new GroupMineAdapter(getActivity());
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(type);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListView.onRefreshComplete();
                    }
                }, 500);
            }
        });
        mListView.setOnItemClickListener(mItemListener);
        mHot.setOnClickListener(mListener);
        mNew.setOnClickListener(mListener);
        mMine.setOnClickListener(mListener);
        mCreate.setOnClickListener(mListener);
        mKeywords.setOnClickListener(mListener);
    }

    @Override
    public void onResume() {
        getData(type);
        super.onResume();
    }

    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent mInt = new Intent(getActivity(), GeneratedClassUtils.get(GroupDataAct.class));
            mInt.putExtra(GroupDataAct.ACTIVITYTAG, "Detils");
            if (type == 2) {
                GroupBean mOwn = mMineAdapter.getItem(i - 1);
                if (mOwn.getId() != -1 && mOwn.getIsin().equals("0")) {
                    mInt.putExtra(GroupDataAct.INTENTTAG, mOwn.getId() + "");
                    startActivity(mInt);
                } else if (mOwn.getId() != -1 && mOwn.getIsin().equals("1")) {
                    //跳转到对应的群组的群消息界面
                    Intent mGmessage = new Intent(getActivity(), GeneratedClassUtils.get(GroupMessageAct.class));
                    mGmessage.putExtra(GroupMessageAct.ACTIVITYTAG, mOwn.getPic());
                    mGmessage.putExtra(GroupMessageAct.INTENTTAG, mOwn.getId() + "");
                    startActivity(mGmessage);
                }
            } else {
                GroupBean mBean = mAdapter.getItem(i - 1);
                if (mBean.getIsin().equals("0")) {
                    mInt.putExtra(GroupDataAct.INTENTTAG, mBean.getId() + "");
                    startActivity(mInt);
                } else {
                    //跳转到对应群组的群消息界面
                    Intent mGmessage = new Intent(getActivity(), GeneratedClassUtils.get(GroupMessageAct.class));
                    mGmessage.putExtra(GroupMessageAct.ACTIVITYTAG, mBean.getPic());
                    mGmessage.putExtra(GroupMessageAct.INTENTTAG, mBean.getId() + "");
                    startActivity(mGmessage);
                }
            }
        }
    };

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_group_hot:
                    if (type != 0) {
                        type = 0;
                        getData(type);
                    }
                    break;
                case R.id.tv_group_new:
                    if (type != 1) {
                        type = 1;
                        getData(type);
                    }
                    break;
                case R.id.tv_group_mine:
                    if (!HighCommunityUtils.isLogin()) {
                        HighCommunityUtils.GetInstantiation().ShowToast("游客模式下,不能查看", 0);
                        if (type == 0) {
                            mHot.setChecked(true);
                            return;
                        } else if (type == 1) {
                            mNew.setChecked(true);
                            return;
                        } else if (type == 3) {
                            return;
                        }
                        return;
                    }
                    if (type != 2) {
                        type = 2;
                        getData(type);
                    }
                    break;
                case R.id.tv_group_create:
                    if (HighCommunityUtils.GetInstantiation().isLogin(getActivity())) {
                        Intent mInt = new Intent(getActivity(), GeneratedClassUtils.get(GroupDataAct.class));
                        mInt.putExtra(GroupDataAct.ACTIVITYTAG, "Create");
                        startActivity(mInt);
                    }
                    break;
                case R.id.et_groupclass_search:
                	
                	Intent intent = new Intent(getActivity(), GeneratedClassUtils.get(SearchActivity.class));
                    intent.putExtra(Constacts.SEARCH_TYPE,Constacts.SEARCH_TYPE_GROUP);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void getData(int type) {
        HTTPHelper.GetGroup(mIbpi, HighCommunityApplication.mUserInfo.getId(), type, keywords);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
            if (type == 2) {
                mListView.setAdapter(mMineAdapter);
                mMineAdapter.clear();
            }
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            mListView.onRefreshComplete();
            if (null == message)
                return;
            mlist = (List<GroupBean>) message;
            if (type != 2) {
                mListView.setAdapter(mAdapter);
                mAdapter.AddNewData(mlist);
            } else {
                mListView.setAdapter(mMineAdapter);
                mMineAdapter.AddNewData(mlist);
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveGroup(result);
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
}
