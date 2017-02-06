/***************************************************************************
 * Copyright (c) by CHENGDU YINUO INFOMATION TECH.CO.,LID, Inc. All Rights Reserved
 **************************************************************************/
package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.CitySearchAdapter;
import cn.hi028.android.highcommunity.adapter.GroupAdapter;
import cn.hi028.android.highcommunity.adapter.MyCarftsAdapter;
import cn.hi028.android.highcommunity.bean.GroupBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 功能：搜索<br>
 * 作者：赵海<br>
 * 时间：2015年3月30日<br>
 * 版本：1.0<br>
 */
@EActivity(resName = "activtiy_search")
public class SearchActivity extends BaseFragmentActivity implements TextWatcher {
    public static final String ACTIVITYTAG = "SearchActivity";
    @ViewById(R.id.tv_cancel)
    TextView tv_cancel;
    @ViewById(R.id.lv_search)
    ListView lv_search;// 搜索列表
    @ViewById(R.id.edt_search)
    EditText edt_search;// 搜索输入框
    @ViewById(R.id.tv_billpay_Nodata)
    TextView tv_billpay_Nodata;// 搜索输入框
    @ViewById(R.id.title_searchTitle_Hight)
    View mHight;
    BaseAdapter adapter;
    private int searchType = 0;
    public String QuxianId = "";
    String mSearchText = "";
    Fragment mFrag;

    /**
     * 搜索回调
     *
     * @param v
     * @param y
     */
    public static void searchResult(View v, float y) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
        animation.setDuration(500);
        animation.setFillAfter(true);
        v.startAnimation(animation);
    }

    /**
     * 搜索跳转
     *
     * @param frag
     * @param viewParent 父View
     * @param y          跳转按钮对应Y位置 getY
     * @param height     跳转按钮高度 getHeight
     * @param searchType 搜索类型
     */
    public static void toSearch(final Fragment frag, final View viewParent, float y, int height, final int searchType) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -y - height);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewParent.clearAnimation();
                viewParent.setVisibility(View.GONE);
                Intent intent = new Intent(frag.getActivity(), GeneratedClassUtils.get(SearchActivity.class));
                intent.putExtra(Constacts.SEARCH_TYPE, searchType);
//				intent.putExtra("cityCode",cityCode);
                frag.startActivityForResult(intent, 0x22);
                frag.getActivity().overridePendingTransition(R.anim.animation_search_out, R.anim.animation_search_in);
            }
        });
        viewParent.startAnimation(animation);

    }

    /**
     * 搜索跳转
     *
     * @param frag
     * @param viewParent 父View
     * @param height     跳转按钮高度 getHeight
     * @param QuxianId   区县ID
     */
    public static void toSearch(final Fragment frag, final View viewParent, int height, final String QuxianId) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -height);
        animation.setDuration(200);
        animation.setFillAfter(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        Intent intent = new Intent(frag.getActivity(), GeneratedClassUtils.get(SearchActivity.class));
        intent.putExtra(Constacts.SEARCH_TYPE, Constacts.SEARCH_TYPE_VALLAGE);
        intent.putExtra(ACTIVITYTAG, QuxianId);
        frag.startActivityForResult(intent, 0x22);
        frag.getActivity().overridePendingTransition(R.anim.animation_search_out, R.anim.animation_search_in);

    }

    @AfterViews
    void initView() {
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_search.setEmptyView(tv_billpay_Nodata);
        edt_search.addTextChangedListener(this);
        searchType = getIntent().getIntExtra(Constacts.SEARCH_TYPE,
                Constacts.SEARCH_TYPE_VALLAGE);
        if (searchType == Constacts.SEARCH_TYPE_VALLAGE) {//城市搜索
            edt_search.setHint("小区名称");
            QuxianId = getIntent().getStringExtra(ACTIVITYTAG);
            adapter = new CitySearchAdapter(this);
            lv_search.setAdapter(adapter);
        } else if (searchType == Constacts.SEARCH_TYPE_GROUP) {// 商品搜索
            edt_search.setHint("群组名称");
            adapter = new GroupAdapter(this);
            lv_search.setAdapter(adapter);
            lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mInt = new Intent(SearchActivity.this, GeneratedClassUtils.get(GroupDataAct.class));
                    GroupBean mBean = (GroupBean) adapter.getItem(position);
                    mInt.putExtra(GroupDataAct.ACTIVITYTAG, "Detils");
                    mInt.putExtra(GroupDataAct.INTENTTAG, mBean.getId() + "");
                    startActivity(mInt);
                }
            });
        } else if (searchType == Constacts.SEARCH_TYPE_CARFTS) {//手艺人搜索
            edt_search.setHint("手艺人名称");
            adapter = new MyCarftsAdapter(this);
            lv_search.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        getData(mSearchText);
        super.onResume();
    }

    BpiHttpHandler.IBpiHttpHandler mVillageIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            List<VallageBean> data = (List<VallageBean>) message;
            ((CitySearchAdapter) adapter).setData(data);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVillage(result);
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
                HighCommunityApplication.toLoginAgain(SearchActivity.this);
            }
        }
    };

    /**
     * 点击事件
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:// 取消输入
                break;
            case R.id.img_clear_edt: // 清除输入内容
                edt_search.setText("");
                break;
            default:
                break;
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {// 内容不为空
            mSearchText = s.toString();
            getData(mSearchText);
        } else {// 内容为空
            if (searchType == Constacts.SEARCH_TYPE_VALLAGE) {// 小区搜索
                if (((CitySearchAdapter) adapter).getData() != null) {
                    ((CitySearchAdapter) adapter).getData().clear();
                    ((CitySearchAdapter) adapter).notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void getData(String s) {
        if (searchType == Constacts.SEARCH_TYPE_VALLAGE && QuxianId == null) {
            HTTPHelper.searchVillages(new BpiHttpHandler.IBpiHttpHandler() {
                @Override
                public void onError(int id, String message) {
                    ((CitySearchAdapter) adapter).getData().clear();
                    ((CitySearchAdapter) adapter).notifyDataSetChanged();
                }

                @Override
                public void onSuccess(Object message) {
                    if (null == message)
                        return;
                    List<VallageBean> data = (List<VallageBean>) message;
                    ((CitySearchAdapter) adapter).setData(data);
                }

                @Override
                public Object onResolve(String result) {
                    return HTTPHelper.ResolveVillage(result);
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
                        HighCommunityApplication.toLoginAgain(SearchActivity.this);
                    }
                }
            }, "510104", s);
        } else if (searchType == Constacts.SEARCH_TYPE_VALLAGE && QuxianId != null) {
            HTTPHelper.getVillagesByDistrict(mVillageIbpi, QuxianId,
                    Constacts.location.getLongitude() + "", Constacts.location.getLatitude() + "", s);
        } else if (searchType == Constacts.SEARCH_TYPE_GROUP) {
            HTTPHelper.searchGroup(new BpiHttpHandler.IBpiHttpHandler() {
                @Override
                public void onError(int id, String message) {
                    ((GroupAdapter) adapter).clear();
                    ((GroupAdapter) adapter).notifyDataSetChanged();
                }

                @Override
                public void onSuccess(Object message) {
                    if (null == message)
                        return;
                    List<GroupBean> data = (List<GroupBean>) message;
                    ((GroupAdapter) adapter).AddNewData(data);
                }

                @Override
                public Object onResolve(String result) {
                    return HTTPHelper.ResolveGroup(result);
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
                        HighCommunityApplication.toLoginAgain(SearchActivity.this);
                    }
                }
            }, s.toString());
        }
    }


    @Override
    public void onBack(View v) {
        super.onBack(v);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SearchActivity.this.setResult(RESULT_CANCELED, null);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
