/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.view.MyLetterListView;

import net.duohuo.dhroid.activity.BackHandledFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.SearchActivity;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.VallageSelectAdapter;
import cn.hi028.android.highcommunity.bean.CountyBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：小区选择界面<br>
 * @作者：赵海<br>
 * @版本：1.0<br>
 * @时间：2015/12/10<br>
 */
@EFragment(resName = "frag_villagechoice")
public class VallageSelctFrag extends BackHandledFragment implements MyLetterListView.OnTouchingLetterChangedListener {
    @ViewById(R.id.ll_village)
    LinearLayout ll_village;

    public static final String FRAGMENTTAG = "VallageCityFrag";
    @ViewById(R.id.fl_vallage_search)
    FrameLayout fl_vallage_search;
    @ViewById(R.id.lv_vallage)
    ListView lv_vallage;
    @ViewById(R.id.tv_dialog)
    TextView tv_dialog;

    @ViewById(R.id.tv_secondtitle_name)
    TextView tv_secondtitle_name;
    @ViewById(R.id.img_back)
    ImageView img_back;
    @ViewById(R.id.mlv_village_Letter)
    MyLetterListView mLetter;
    @ViewById(R.id.title_secondTitle_Hight)
    View mHight;
    VallageSelectAdapter adapter;
    CountyBean countyBean;
    float searchY;

    @AfterViews
    public void initView() {
        if (!((VallageAct) getActivity()).isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        adapter = new VallageSelectAdapter(this);
        lv_vallage.setAdapter(adapter);
        tv_secondtitle_name.setText("小区城市选择");
        img_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mLetter.setOnTouchingLetterChangedListener(this);
        mLetter.setTv_dialog(tv_dialog);
        Bundle bundle = getArguments();
        countyBean = (CountyBean) bundle.getSerializable("county");
        HTTPHelper.GetVallage(mIbpiVallage, countyBean.getCity_code());
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiVallage = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            List<VallageBean> mList = (List<VallageBean>) message;
            if (!ListUtils.isEmpty(mList))
                adapter.setData(mList);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVallage(result);
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

    @Override
    public void onTouchingLetterChanged(String s) {
        // 该字母首次出现的位置
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            lv_vallage.setSelection(position);
        }
    }

    @Click(R.id.fl_vallage_search)
    public void onSearch() {
        searchY = fl_vallage_search.getY();
        SearchActivity.toSearch(this, ll_village, searchY, fl_vallage_search.getHeight(), Constacts.SEARCH_TYPE_VALLAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SearchActivity.searchResult(ll_village, searchY);
        if (requestCode == 0x22 && resultCode == 0x22 && data != null) {
            mBackHandledInterface.onResultActivity(data.getIntExtra(Constacts.SEARCH_RESULT, 0));
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
