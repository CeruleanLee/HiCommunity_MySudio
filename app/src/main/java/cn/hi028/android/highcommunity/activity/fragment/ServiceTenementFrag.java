/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.view.CustomListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.SeriTenementAdapter;
import cn.hi028.android.highcommunity.bean.CityBean;
import cn.hi028.android.highcommunity.bean.TenementBean;
import cn.hi028.android.highcommunity.bean.TenementHouseBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：租房列表<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2015-12-29<br>
 */
@EFragment(resName = "fragment_service_tenement")
public class ServiceTenementFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "ServiceTenementFrag";
    @ViewById(R.id.lv_tenement)
    public ListView lv_tenement;//
    SeriTenementAdapter adapter;
    @ViewById(R.id.tv_Nodata)
    public TextView tv_Nodata;//
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View mProgress;
    String vid;
    public
    @AfterViews
    void initView() {
        adapter = new SeriTenementAdapter(this);
        lv_tenement.setEmptyView(tv_Nodata);
        lv_tenement.setAdapter(adapter);
//       +
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_Nodata.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
        HTTPHelper.GetTenementList(mIbpiTenement, HighCommunityApplication.mUserInfo.getV_id() + "");
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiTenement = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            adapter.getData().clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            adapter.getData().clear();
            if (null == message)
                return;
            TenementBean bean = (TenementBean) message;
            if (!ListUtils.isEmpty(bean.getCurr_village()) || !ListUtils.isEmpty(bean.getNearby_village())) {
                List<TenementHouseBean> data = new ArrayList<TenementHouseBean>();
                for (int i = 0; i < ListUtils.getSize(bean.getCurr_village()); i++) {
                    bean.getCurr_village().get(i).setSortLetters("当前小区");
                    data.add(bean.getCurr_village().get(i));
                }
                for (int i = 0; i < ListUtils.getSize(bean.getNearby_village()); i++) {
                    bean.getNearby_village().get(i).setSortLetters("附近小区");
                    data.add(bean.getNearby_village().get(i));
                }
                adapter.setData(data);
            }

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolvTenementList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClickRight() {
        super.onClickRight();
        if (HighCommunityUtils.isLogin()) {
            VallageAct.toStartAct(this, 1, false);
        } else {
            VallageAct.toStartAct(this, 0, false);
        }
    }
}
