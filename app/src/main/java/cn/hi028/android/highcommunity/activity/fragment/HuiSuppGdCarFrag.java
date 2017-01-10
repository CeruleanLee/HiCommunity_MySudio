/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.adapter.HuiSuppGdcarAdapter;
import cn.hi028.android.highcommunity.bean.GdCarBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：购物车<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-26<br>
 */
@EFragment(R.layout.frag_huisupp_gdcar)
public class HuiSuppGdCarFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "HuiSuppGdCarFrag";
    @ViewById(R.id.progress_gdcar_notice)
    View mProgress;
    @ViewById(R.id.tv_gdcar_Nodata)
    TextView mNodata;
    @ViewById(R.id.btn_pay)
    public Button btn_pay;

    @ViewById(R.id.ll_price)
    public LinearLayout ll_price;
    @ViewById(R.id.tv_price)
    public TextView tv_price;
    @ViewById(R.id.img_goods_ch)
    public ImageView img_goods_ch;
    @ViewById(R.id.ptrlv_gdcar_listView)
    PullToRefreshListView mListView;
    public HuiSuppGdcarAdapter adapter;

    @AfterViews
    void initView() {
        mProgress.setVisibility(View.VISIBLE);
        adapter = new HuiSuppGdcarAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mNodata);
        img_goods_ch.setSelected(false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        HTTPHelper.getGdCarList(mIbpi);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            List<GdCarBean> mlist = (List<GdCarBean>) message;
            adapter.setData(mlist);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolvGdCarList(result);
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

    @Click(R.id.img_goods_ch)
    public void checkClick() {
        if (img_goods_ch.isSelected()) {
            for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                adapter.getData().get(i).setCheck(false);
            }
        } else {
            for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                adapter.getData().get(i).setCheck(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Click(R.id.btn_pay)
    public void payClick() {
        if (btn_pay.isSelected()) {
            if (adapter.selectNum > 0) {
                final PopupWindow waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
                HTTPHelper.deleteGdCarList(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast("成功删除", 0);
                        for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                            if (adapter.getData().get(i).isCheck() == true) {
                                adapter.getData().remove(i);
                                i--;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public Object onResolve(String result) {
                        return null;
                    }

                    @Override
                    public void setAsyncTask(AsyncTask asyncTask) {

                    }

                    @Override
                    public void cancleAsyncTask() {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast("取消删除", 0);
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
                }, list());
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast("没有所选商品，不需要删除", 0);
            }

        } else {
            if (adapter.total_num > 0) {
                List<GdCarBean> list = new ArrayList<GdCarBean>();
                for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                    if (adapter.getData().get(i).isCheck() == true) {
                        list.add(adapter.getData().get(i));
                    }
                }
                Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(HuiLifeSecondAct.class));
                mIntent.putExtra(HuiLifeSecondAct.ACTIVITYTAG, Constacts.HUILIFE_SUPPORT_ORDER);
                mIntent.putExtra(HuiLifeSecondAct.INTENTTAG, 1);
                HuiLifeSuppBuyFrag.listData = list;
                startActivity(mIntent);
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast("所选商品为零，不能结算", 0);
            }
        }
    }

    public String list() {
        String str = "";
        List<GdCarBean> list = new ArrayList<GdCarBean>();
        for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
            if (adapter.getData().get(i).isCheck() == true) {
                list.add(adapter.getData().get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                str = str + ",";
            }
            str = str + list.get(i).getCart_id();
        }
        return str;
    }
}
