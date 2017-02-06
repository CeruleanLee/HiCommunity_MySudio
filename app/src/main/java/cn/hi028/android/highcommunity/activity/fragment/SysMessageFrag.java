/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
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

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SystemMsgAdapter;
import cn.hi028.android.highcommunity.bean.SystemMessageBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

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
    public List<ListView> listViewList;
    public List<View> proPressList;
    public List<TextView> noDataList;
    private List<BaseAdapter> adapterList;
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
    public SystemMsgAdapter adapter ;
    ListView ptfl;
    View mProgress;
    ListView lv_list;
    void getPageView2(View view) {
        adapter = new SystemMsgAdapter(getActivity(),mSystemMsgList);
        ptfl = (ListView) view.findViewById(R.id.sysMsg_listView);
        mProgress = view.findViewById(R.id.ll_sysMsg_Progress);
        mProgress.setVisibility(View.VISIBLE);
        mNodata = (TextView) view.findViewById(R.id.tv_sysMsg_noData);
        ptfl.setEmptyView(mNodata);
        ptfl.setAdapter(adapter);

    }

    void initView() {
        HTTPHelper.GetSystemMsg(mIbpi);
    }

/**系统相关的网络**/
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.GONE);
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.GONE);
            if (null == message)
                return;
            mSystemMsgList = (List<SystemMessageBean.SystemMsgDataEntity>) message;
            adapter.AddNewData(mSystemMsgList);
            ptfl.setAdapter(adapter);
            ptfl.setVisibility(View.VISIBLE);
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
