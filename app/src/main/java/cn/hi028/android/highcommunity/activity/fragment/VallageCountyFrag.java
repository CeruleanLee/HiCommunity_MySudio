/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.view.MyLetterListView;

import net.duohuo.dhroid.activity.BackHandledFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.VallageCountyAdapter;
import cn.hi028.android.highcommunity.bean.CityBean;
import cn.hi028.android.highcommunity.bean.CountyBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：小区选择界面<br>
 * @作者：赵海<br>
 * @版本：1.0<br>
 * @时间：2015/12/10<br>
 */
@EFragment(resName = "frag_villagechoice")
public class VallageCountyFrag extends BackHandledFragment {
    CityBean cityBean;
    //    public VallageCountyFrag(CityBean cityBean){
//        this.cityBean = cityBean;
//    }
    public static final String FRAGMENTTAG = "VallageCountyFrag";
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
    VallageCountyAdapter adapter;

    @AfterViews
    public void initView() {
        if (!((VallageAct) getActivity()).isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        fl_vallage_search.setVisibility(View.GONE);
        mLetter.setVisibility(View.GONE);
        adapter = new VallageCountyAdapter(this);
        lv_vallage.setAdapter(adapter);
        tv_secondtitle_name.setText("小区县区选择");
        img_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        Bundle bundle = getArguments();
        cityBean = (CityBean) bundle.getSerializable("city");
        HTTPHelper.GetCouty(mIbpiCounty, cityBean.getCity_code());
    }

    /**
     * 跳转到小区
     *
     * @param countyBean
     */
    public void toVallageSelect(CountyBean countyBean) {
        VallageSelctFrag second = new VallageSelctFrag_();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("county", countyBean);
        second.setArguments(bundle);
        ft.replace(R.id.fragment, second);
        ft.addToBackStack("tag");
        ft.commit();
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiCounty = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            List<CountyBean> mList = (List<CountyBean>) message;
            if (!ListUtils.isEmpty(mList))
                adapter.setData(mList);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCounty(result);
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

    //    public void toCountySelect(){
//        VallageCountyFrag second = new VallageCountyFrag();
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragment, second);
//        ft.addToBackStack("tag");
//        ft.commit();
//    }
    @Override
    public boolean onBackPressed() {
        return false;
    }
}
