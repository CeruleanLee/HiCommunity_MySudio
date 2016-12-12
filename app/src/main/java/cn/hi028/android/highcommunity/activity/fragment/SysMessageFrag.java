/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SystemMsgAdapter;
import cn.hi028.android.highcommunity.bean.SystemMessageBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * @功能：消息中心页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016-01-30<br>
 */

public class SysMessageFrag extends BaseFragment {

    public static final String TAG = "~MsgCenterFrag->";
    public static final String Tag = "~MsgCenterFrag->";
    public static final String FRAGMENTTAG = "SysMessageFrag";

    public List<ListView> listViewList; // Tab页面列表
    public List<View> proPressList; // Tab页面列表
    public List<TextView> noDataList; // Tab页面列表
    private List<BaseAdapter> adapterList; // Tab页面列表
    List<SystemMessageBean.SystemMsgDataEntity> mSystemMsgList=new ArrayList<SystemMessageBean.SystemMsgDataEntity>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_system_message2, null);
        getPageView2(view);
        initView();

        return view;
    }
    TextView mNodata;
//    public SystemMsgAdapter adapter = new SystemMsgAdapter(getActivity(),mSystemMsgList);
    public SystemMsgAdapter adapter ;
    ListView ptfl;
    View mProgress;
    ListView lv_list;
    void getPageView2(View view) {
        Log.e(TAG,"getPageView");
        adapter = new SystemMsgAdapter(getActivity(),mSystemMsgList);

        ptfl = (ListView) view.findViewById(R.id.sysMsg_listView);
        mProgress = view.findViewById(R.id.ll_sysMsg_Progress);
//        ptfl.getRefreshableView().setDivider(null);
//        ptfl.getRefreshableView().setDividerHeight(DhUtil.dip2px(getActivity(), 20));
//        ptfl.getRefreshableView().setPadding(20,-20,20,40);
        mProgress.setVisibility(View.VISIBLE);
        mNodata = (TextView) view.findViewById(R.id.tv_sysMsg_noData);
        ptfl.setEmptyView(mNodata);

//        lv_list = ptfl.getRefreshableView();
//        ptfl.setMode(PullToRefreshBase.Mode.DISABLED);
        ptfl.setAdapter(adapter);


    }

    void initView() {
        Log.d(TAG,"------into SysMessageFrag");
        HTTPHelper.GetSystemMsg(mIbpi);
    }

/**应该是系统消息的view**/
//    View getPageView() {
//        Log.e(TAG,"getPageView");
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_system_message, null);
//        SystemMsgAdapter adapter=new SystemMsgAdapter(getActivity(),mSystemMsgList);
////        NoticeAdapter adapter = new NoticeAdapter(getActivity());
//      /**改到这里了 **/
//        PullToRefreshListView ptfl = (PullToRefreshListView) view.findViewById(R.id.sysMsg_listView);
//        View mProgress = view.findViewById(R.id.ll_sysMsg_Progress);
//        ptfl.getRefreshableView().setDivider(null);
//        ptfl.getRefreshableView().setDividerHeight(DhUtil.dip2px(getActivity(), 20));
//        ptfl.getRefreshableView().setPadding(20,-20,20,40);
//        mProgress.setVisibility(View.VISIBLE);
//        TextView mNodata = (TextView) view.findViewById(R.id.tv_sysMsg_noData);
//        proPressList.add(mProgress);
//        noDataList.add(mNodata);
//        ListView lv_list = ptfl.getRefreshableView();
//        ptfl.setMode(PullToRefreshBase.Mode.DISABLED);
//        lv_list.setAdapter(adapter);
//        adapterList.add(adapter);
//        listViewList.add(lv_list);
//        return view;
//    }

/**系统相关的网络**/
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.d(TAG,"------onErrorg");

            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.GONE);
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            Log.d(TAG,"------onSuccess");
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.GONE);
            if (null == message)
                return;
            mSystemMsgList = (List<SystemMessageBean.SystemMsgDataEntity>) message;
            Log.d(TAG,"------onSuccess1");

            adapter.AddNewData(mSystemMsgList);
            Log.d(TAG,"------onSuccess2");

            ptfl.setAdapter(adapter);
            ptfl.setVisibility(View.VISIBLE);
            Log.d(TAG,"------onSuccess3");

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSystemMsgDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

}
