/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.myradiobutton.GridRadioAdapter;
import cn.hi028.android.highcommunity.view.myradiobutton.GridRadioAdapter.OnItemCheckedListener;
import cn.hi028.android.highcommunity.view.myradiobutton.RadioItemModel;

/**
 * @功能：举报<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/22<br>
 */
@EFragment(resName = "frag_report")
public class ReportFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "ReportFrag";

    @ViewById(R.id.tv_report_submit)
    TextView submit;
    @ViewById(R.id.report_radiobut_gv)
    GridView mGridView;
    private GridRadioAdapter mAdapter;
    private List<RadioItemModel> mItemList;
    RadioItemModel mType1, mType2, mType3, mType4, mType5;
    String type = "";
    String mid = "";
    PopupWindow mWaitingWindow;

    @AfterViews
    void initView() {
        mid = getActivity().getIntent().getStringExtra(CommunityDetailAct.INTENTTAG);
        initData();
        mAdapter = new GridRadioAdapter(getActivity(), mItemList);
        mGridView.setAdapter(mAdapter);
        mAdapter.setOnItemCheckedListener(new OnItemCheckedListener() {

            @Override
            public void onItemChecked(RadioItemModel model, int position) {
                type = position + 1 + "";
            }
        });
    }

    private void initData() {
        mItemList = new ArrayList<RadioItemModel>();
        ColorStateList csl = getResources().getColorStateList(R.color.Defult_Color_demoGrey);
        mType1 = new RadioItemModel("广告", false, false, csl);
        mItemList.add(mType1);
        mType2 = new RadioItemModel("淫秽色情", false, false, csl);
        mItemList.add(mType2);
        mType3 = new RadioItemModel("有害信息", false, false, csl);
        mItemList.add(mType3);
        mType4 = new RadioItemModel("违法信息", false, false, csl);
        mItemList.add(mType4);
        mType5 = new RadioItemModel("垃圾信息", false, false, csl);
        mItemList.add(mType5);
    }

    @Click(R.id.tv_report_submit)
    void submit() {
        if (TextUtils.isEmpty(type)) {
            HighCommunityUtils.GetInstantiation().ShowToast("请选择举报原因", 0);
            return;
        } else if (HighCommunityApplication.mUserInfo.getId() == 0) {
            HighCommunityUtils.GetInstantiation().ShowToast("请先登录再操作", 0);
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mGridView, Gravity.CENTER);
        HTTPHelper.Report(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", mid, type);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
            getActivity().finish();
        }

        @Override
        public Object onResolve(String result) {
            return result;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mWaitingWindow.dismiss();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

}
