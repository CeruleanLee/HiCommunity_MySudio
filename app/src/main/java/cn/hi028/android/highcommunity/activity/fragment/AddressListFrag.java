/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
import cn.hi028.android.highcommunity.activity.AddressAct;
import cn.hi028.android.highcommunity.activity.AddressModifyAct;
import cn.hi028.android.highcommunity.adapter.AddressListAdapter;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：收货地址页面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/20<br>
 */
@EFragment(resName = "frag_addresslist")
public class AddressListFrag extends BaseFragment {

    public static final String FRAGMENTTAG = "AddressListFrag";
    @ViewById(R.id.progress_address_notice)
    View mProgress;
    @ViewById(R.id.tv_address_Nodata)
    TextView mNodata;
    @ViewById(R.id.ptrlv_address_listView)
    PullToRefreshListView mListView;

    AddressListAdapter mAdapter;
    List<AddressBean> mList;
    public String from = "";

    @AfterViews
    void initView() {
        mProgress.setVisibility(View.VISIBLE);
        mAdapter = new AddressListAdapter(this);
        from = getActivity().getIntent().getStringExtra(AddressAct.ACTIVITYTAG);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mNodata);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.isEmpty(from)) {
                    Intent intent = getActivity().getIntent();
                    Bundle mbundle=new Bundle();
                    mbundle.putParcelable("address",mAdapter.getItem(position - 1));
                    intent.putExtra("data", mbundle);
                    getActivity().setResult(0x22, intent);
                    getActivity().finish();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        HTTPHelper.getAddressList2(mIbpi);
    }
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
            mAdapter.ClearData();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mList = (List<AddressBean>) message;
            if (mList.size()==0&&isFirstRequested){
                isFirstRequested=false;
//                HTTPHelper.getAddressList(mIbpi, HighCommunityApplication.mUserInfo.getId() + "");
            }else{
                isFirstRequested=false;
                mAdapter.AddNewData(mList);
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveAddressList(result);
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

    @Click(R.id.tv_address_create)
    void Click() {
        Intent mCreate = new Intent(getActivity(), GeneratedClassUtils.get(AddressModifyAct.class));
        mCreate.putExtra(AddressModifyAct.ACTIVITYTAG, 0);
        startActivity(mCreate);
    }
    boolean isFirstRequested=true;
}
