/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.util.LogUtil;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.activity.GroupMessageAct;
import cn.hi028.android.highcommunity.activity.LabelAct;
import cn.hi028.android.highcommunity.adapter.GroupMessageListAdapter;
import cn.hi028.android.highcommunity.bean.CommunityBean;
import cn.hi028.android.highcommunity.bean.CommunityListBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：群消息的信息流<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-2-16<br>
 */
public class GroupMessageFrag extends Fragment {

    public static final String FRAGMENTTAG = "GroupMessageFrag";
//    private View mFragmeView;
    private int mCount = -1;
    GroupMessageListAdapter mAdapter;
    private PullToRefreshListView mListView;
    private ImageView mChange;
    private TextView mNodata;
    private View mProgess;
    CommunityListBean mList = new CommunityListBean();
    CommunityBean mBean = null;
    String vid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	View view=inflater.inflate(R.layout.frag_groupmessage_list, null);
            initView(view);
        
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null)
//            parent.removeView(view);
        return view;
    }

    private void initView(View view) {
//        mFragmeView = LayoutInflater.from(getActivity()).inflate(
//                R.layout.frag_groupmessage_list, null);
        vid = getActivity().getIntent().getStringExtra(GroupMessageAct.INTENTTAG);
        mProgess = view.findViewById(R.id.progress_GroupMessage);
        mListView = (PullToRefreshListView) view.findViewById(R.id.ptrlv_GroupMessage_listview);
        mChange = (ImageView) view.findViewById(R.id.iv_GroupMessage_change);
        mNodata = (TextView) view.findViewById(R.id.tv_GroupMessage_Nodata);
        mAdapter = new GroupMessageListAdapter((GroupMessageAct) getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mNodata);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                RefreshData(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                RefreshData(1);
            }
        });
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mPublish = new Intent(getActivity(), LabelAct.class);
                mPublish.putExtra(LabelAct.ACTIVITYTAG, vid);
                mPublish.putExtra(LabelAct.INTENTTAG, 1);
                startActivity(mPublish);
            }
        });

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
        initData();
    }

    
    
    private void initData() {
		// TODO Auto-generated method stub
    	mCount = -1;
		
    	HTTPHelper.GetGroupMessage(mIbpi, HighCommunityApplication.mUserInfo.getId(), vid);
	}


    private void RefreshData(int type) {
        String time = "";
        if (type == 0) {
        	//平常贴
            mCount = 0;
            if (mList.getData() != null && mList.getData().size() > 0) {
                time = mList.getData().get(0).getCreate_time();
            }
        } else {
        	//群组贴
            mCount = 1;
            if (mList.getData() != null && mList.getData().size() > 0) {
                time = mList.getData().get(mList.getData().size() - 1).getCreate_time();
            }
        }
        HTTPHelper.RefreshGroupMessage(mIbpi, type, time, HighCommunityApplication.mUserInfo.getId(), vid);//
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mListView.onRefreshComplete();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
        	LogUtil.d("---群组消息返回数据：---"+message.toString());
        	
        	
            if (null == message)
                return;
            mList = (CommunityListBean) message;
            if (mCount == 0) {
                mAdapter.AddNewData(mList.getData());
            } else if (mCount == 1) {
                mAdapter.RefreshData(mList.getData());
            } else if (mCount == -1) {
                mAdapter.SetData(mList.getData());
            }
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
    };

	@Override
	public void onResume() {
		
		mAdapter.ClearData();
        initData();
		
		super.onResume();
	}

}
