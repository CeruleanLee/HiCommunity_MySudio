/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.RepairJJAdapter;
import cn.hi028.android.highcommunity.bean.RepairJJBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 *@功能：紧急保修<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-01-11<br>
 */
@EFragment(resName ="frag_repair_jj")
public class SeriRepairJJFrag extends BaseFragment{
    public static final String FRAGMENTTAG = "SeriRepairJJFrag";
    @ViewById(R.id.lv_repair_jj)
    ListView lv_repair_jj;
    @ViewById(R.id.tv_NoticeDetails_noData)
    TextView mNoData;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View mProgress;
    RepairJJAdapter adapter;
    @AfterViews
    public void initView() {
        adapter=new RepairJJAdapter(this);
        lv_repair_jj.setAdapter(adapter);
        mProgress.setVisibility(View.VISIBLE);
        HTTPHelper.GetRepairJJList(mIbpiRepairJJ, HighCommunityApplication.mUserInfo.getV_id()+"");
    }
    BpiHttpHandler.IBpiHttpHandler mIbpiRepairJJ = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
            HighCommunityUtils.GetInstantiation().ShowToast(message,0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message) {
                mNoData.setVisibility(View.VISIBLE);
                return;
            }
            List<RepairJJBean>data=(List<RepairJJBean>)message;
            if (ListUtils.isEmpty(data)){
                mNoData.setVisibility(View.VISIBLE);
            }else{
                adapter.setData(data);
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveRepaiJJList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
        }
    };
}
