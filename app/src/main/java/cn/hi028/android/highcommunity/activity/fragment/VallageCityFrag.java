/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.don.tools.BpiHttpHandler;
import com.don.view.MyLetterListView;

import net.duohuo.dhroid.activity.BackHandledFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.SearchActivity;
import cn.hi028.android.highcommunity.activity.VallageAct;
import cn.hi028.android.highcommunity.adapter.VallageCityAdapter;
import cn.hi028.android.highcommunity.bean.CityBean;
import cn.hi028.android.highcommunity.bean.VallageCityBean;
import cn.hi028.android.highcommunity.bean.VallageSelectBean;
import cn.hi028.android.highcommunity.utils.CharacterParser;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.LocUtils;
import cn.hi028.android.highcommunity.utils.pinyinUtils.PinyinCityComparator;

/**
 * @功能：小区选择界面<br>
 * @作者：赵海<br>
 * @版本：1.0<br>
 * @时间：2015/12/10<br>
 */
@EFragment(resName = "frag_villagechoice")
public class VallageCityFrag extends BackHandledFragment implements MyLetterListView.OnTouchingLetterChangedListener {
    public static final String FRAGMENTTAG = "VallageCityFrag";
    @ViewById(R.id.ll_village)
    LinearLayout ll_village;

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
    @ViewById(R.id.tv_NoticeDetails_noData)
    TextView mNoData;
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View mProgress;
    VallageCityAdapter adapter;
    float searchY;

    @AfterViews
    public void initView() {
        if (!((VallageAct) getActivity()).isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        adapter = new VallageCityAdapter(this);
        lv_vallage.setAdapter(adapter);
        tv_secondtitle_name.setText("小区城市选择");
        img_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                getActivity().finish();
            }
        });
        mLetter.setOnTouchingLetterChangedListener(this);
        mLetter.setTv_dialog(tv_dialog);
        int id = getActivity().getIntent().getIntExtra(VallageAct.TYPE, 0);
        mProgress.setVisibility(View.VISIBLE);
        if (id == 0) {
            if (Constacts.location != null) {
                HTTPHelper.GetCityFirst(mIbpiCity, Constacts.location.getCity(), Constacts.location.getLatitude() + "", Constacts.location.getLongitude() + "");
            } else {
                LocUtils.startLocation(getActivity(), new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        if (bdLocation == null) {
                            getActivity().finish();
                            return;
                        }
                        Constacts.location = bdLocation;
                        HTTPHelper.GetCityFirst(mIbpiCity, Constacts.location.getCity(), Constacts.location.getLatitude() + "", Constacts.location.getLongitude() + "");
                    }
                });
            }
        } else {
            HTTPHelper.GetCity(mIbpiCity, HighCommunityApplication.mUserInfo.getId() + "", HighCommunityApplication.mUserInfo.getV_id() + "");
        }
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiCity = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            VallageSelectBean bean = (VallageSelectBean) message;
            List<VallageCityBean> data = new ArrayList<VallageCityBean>();

            if (bean.getCurr_village() != null) {
                VallageCityBean cityBean = new VallageCityBean();
                cityBean.setVallage(bean.getCurr_village());
                cityBean.setType(0);
                cityBean.setSortLetters("当前小区");
                data.add(cityBean);
            }
            for (int i = 0; i < ListUtils.getSize(bean.getNearby_village()); i++) {
                VallageCityBean vallageCityBean = new VallageCityBean();
                vallageCityBean.setVallage(bean.getNearby_village().get(i));
                vallageCityBean.setType(1);
                vallageCityBean.setSortLetters("附近小区");
                data.add(vallageCityBean);
            }
            for (int i = 0; i < ListUtils.getSize(bean.getHis_village()); i++) {
                VallageCityBean vallageCityBean = new VallageCityBean();
                vallageCityBean.setVallage(bean.getHis_village().get(i));
                vallageCityBean.setType(1);
                vallageCityBean.setSortLetters("历史浏览");
                data.add(vallageCityBean);
            }
            List<VallageCityBean> dataCity = new ArrayList<VallageCityBean>();
            for (int i = 0; i < ListUtils.getSize(bean.getCity_village()); i++) {
                VallageCityBean vallageCityBean = new VallageCityBean();
                vallageCityBean.setCity(bean.getCity_village().get(i));
                vallageCityBean.setType(2);
// 汉字转换成拼音
                String username = bean.getCity_village().get(i).getName();
                // 若没有username
                if (username != null) {
                    String pinyin = CharacterParser.getInstance().getSelling(username);
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        bean.getCity_village().get(i).setSortLetters(sortString.toUpperCase());
                        vallageCityBean.setSortLetters(sortString.toUpperCase());
                    } else {
                        bean.getCity_village().get(i).setSortLetters("#");
                        vallageCityBean.setSortLetters("#");
                    }
                } else {
                    bean.getCity_village().get(i).setSortLetters("#");
                }
                dataCity.add(vallageCityBean);
            }
            // 根据a-z进行排序
            Collections.sort(dataCity, new PinyinCityComparator());
            if (bean.getCurrent_city() != null) {
                VallageCityBean vallageCityBean = new VallageCityBean();
                vallageCityBean.setCity(bean.getCurrent_city());
                vallageCityBean.setType(2);
                vallageCityBean.setSortLetters("当前城市");
                data.add(vallageCityBean);
            }
            data.addAll(dataCity);
            adapter.setData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mProgress.setVisibility(View.GONE);
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

    /**
     * 跳转到区县
     *
     * @param cityBean
     */
    public void toCountySelect(CityBean cityBean) {
        VallageCountyFrag second = new VallageCountyFrag_();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("city", cityBean);
        second.setArguments(bundle);
        ft.replace(R.id.fragment, second);
        ft.addToBackStack("tag");
        ft.commit();
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        // 该字母首次出现的位置
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            lv_vallage.setSelection(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SearchActivity.searchResult(ll_village, searchY);
        if (requestCode == 0x22 && resultCode == 0x22 && data != null) {
            mBackHandledInterface.onResultActivity_Address(data.getStringExtra(Constacts.SEARCH_RESULT_Address));
            mBackHandledInterface.onResultActivity(data.getIntExtra(Constacts.SEARCH_RESULT, 0));
//            Constacts.SEARCH_RESULT, data.get(position).getName()
        }
    }

    @Click(R.id.fl_vallage_search)
    public void onSearch() {
        searchY = fl_vallage_search.getY();
        SearchActivity.toSearch(this, ll_village, searchY, fl_vallage_search.getHeight(), Constacts.SEARCH_TYPE_VALLAGE);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
