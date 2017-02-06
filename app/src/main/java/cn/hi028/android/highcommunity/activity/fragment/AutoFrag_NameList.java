package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoNamelist_YWHAdapter;
import cn.hi028.android.highcommunity.adapter.AutoNamelist_YZAdapter;
import cn.hi028.android.highcommunity.adapter.AutoNamelist_YZDBAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NameListBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.SearchView;

/**
 * @功能：自治大厅认证完成主界面-名单<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */
public class AutoFrag_NameList extends BaseFragment {
    public static final String Tag = "AutoFrag_NameList:";
    public static final String FRAGMENTTAG = "AutoFrag_NameList";
    @Bind(R.id.frag_AutoName_ownerCommittee)
    RadioButton mCommittee;
    @Bind(R.id.frag_AutoName_ownerRepresent)
    RadioButton mRepresent;
    @Bind(R.id.frag_AutoName_owner)
    RadioButton mOwner;
    @Bind(R.id.frag_AutoName_RadioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.frag_AutoName_fragContainer)
    RelativeLayout mFragContainer;
    @Bind(R.id.frag_AutoName_list_CO)
    ListView mListCO;
    @Bind(R.id.frag_AutoName_list_Re)
    ListView mListRe;
    @Bind(R.id.frag_AutoName_list_Owner)
    ListView mListOwner;
    @Bind(R.id.tv_Autonotice_Nodata)
    TextView tv_Autonotice_Nodata;
    AutoNamelist_YWHAdapter mYWHadapter;
    AutoNamelist_YZDBAdapter mYZDBadapter;
    AutoNamelist_YZAdapter mYZadapter;
    //搜索view
    @Bind(R.id.search_layout)
    SearchView mSearchView;
    //搜索结果列表
    @Bind(R.id.lv_search_results)
    ListView mSearchResultsListview;
    @Bind(R.id.tv_create)
    TextView mTvSearch;
    @Bind(R.id.namelist_Owner_layout)
    LinearLayout mOwnerLayout;
    private List<Auto_NameListBean.NameListDataEntity.YwhEntity> mYWHList;
    private List<Auto_NameListBean.NameListDataEntity.YzdbEntity> mYWDBList;
    private List<Auto_NameListBean.NameListDataEntity.YzEntity> mYZList;
    /**
     * 自动补全列表adapter
     */
    private AutoNamelist_YZAdapter autoCompleteAdapter;
    /**
     * 搜索过程中自动补全数据
     */
    private List<Auto_NameListBean.NameListDataEntity.YzEntity> autoCompleteData;
    /**
     * 搜索结果列表adapter
     */
    AutoNamelist_YZAdapter resultAdapter;
    /**
     * 搜索结果的数据
     */
    private List<Auto_NameListBean.NameListDataEntity.YzEntity> resultData;
    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 7;
    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_auto_namelist, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mYWHList = new ArrayList<Auto_NameListBean.NameListDataEntity.YwhEntity>();
        mYWDBList = new ArrayList<Auto_NameListBean.NameListDataEntity.YzdbEntity>();
        mYWHadapter = new AutoNamelist_YWHAdapter(mYWHList, getActivity());
        mYZDBadapter = new AutoNamelist_YZDBAdapter(mYWDBList, getActivity());
        mListCO.setEmptyView(tv_Autonotice_Nodata);
        mListRe.setEmptyView(tv_Autonotice_Nodata);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.frag_AutoName_ownerCommittee:
                        mFragContainer.setVisibility(View.VISIBLE);
                        mListRe.setVisibility(View.GONE);
                        mListOwner.setVisibility(View.GONE);
                        mOwnerLayout.setVisibility(View.GONE);
                        mListCO.setVisibility(View.VISIBLE);
                        mTvSearch.setVisibility(View.GONE);
                        mSearchView.setVisibility(View.GONE);
                        mSearchResultsListview.setVisibility(View.GONE);
                        break;
                    case R.id.frag_AutoName_ownerRepresent:
                        mFragContainer.setVisibility(View.VISIBLE);
                        mListCO.setVisibility(View.GONE);
                        mListOwner.setVisibility(View.GONE);
                        mOwnerLayout.setVisibility(View.GONE);
                        mListRe.setVisibility(View.VISIBLE);
                        mTvSearch.setVisibility(View.GONE);
                        mSearchView.setVisibility(View.GONE);
                        mSearchResultsListview.setVisibility(View.GONE);
                        break;
                    case R.id.frag_AutoName_owner:
                        mFragContainer.setVisibility(View.VISIBLE);
                        mListRe.setVisibility(View.GONE);
                        mListCO.setVisibility(View.GONE);
                        mSearchView.setVisibility(View.VISIBLE);
                        mOwnerLayout.setVisibility(View.VISIBLE);
                        if (mSearchView.isSearching()) {
                            mSearchView.setVisibility(View.VISIBLE);
                            mSearchResultsListview.setVisibility(View.VISIBLE);
                        } else {
                            mSearchView.setVisibility(View.VISIBLE);
                            mListOwner.setVisibility(View.VISIBLE);
                            mOwnerLayout.setVisibility(View.VISIBLE);
                            mSearchResultsListview.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });
        mCommittee.setChecked(true);
    }

    /**
     * 初始化搜索数据
     */
    private void initSearchData() {
        Log.d(Tag, "initSearchData");

        if (mYZList != null && mYZList.size() != 0) {
            //初始化自动补全数据
            getAutoCompleteData(null);
            //初始化搜索结果数据
            getResultData(null);
            initSearchView();
        } else {
            Log.e(Tag, "mYZList  =null|| mYZList.size()=0");
            mSearchResultsListview.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化搜索视图
     */
    private void initSearchView() {
        //设置监听
        mSearchView.setSearchViewListener(new SearchView.SearchViewListener() {
            /**
             * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
             * @param text
             */
            @Override
            public void onRefreshAutoComplete(String text) {
                Log.e(Tag, "text.length():" + text.length());
                if (text.length() == 0) {
                    mListOwner.setVisibility(View.VISIBLE);
                } else {
                    mListOwner.setVisibility(View.GONE);
                }
                getAutoCompleteData(text);
            }

            /**
             * 点击搜索键时edit text触发的回调
             *
             * @param text
             */
            @Override
            public void onSearch(String text) {
                mListOwner.setVisibility(View.GONE);
                //更新result数据
                getResultData(text);
                mSearchResultsListview.setVisibility(View.VISIBLE);
                //第一次获取结果 还未配置适配器
                if (mSearchResultsListview.getAdapter() == null) {
                    //获取搜索数据 设置适配器
                    mSearchResultsListview.setAdapter(resultAdapter);
                } else {
                    //更新搜索数据
                    resultAdapter.notifyDataSetChanged();
                }
            }

        });
        //设置adapter
        mSearchView.setAutoCompleteAdapter(autoCompleteAdapter);
        mSearchResultsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mSearchView.setSearchingChangeListener(new SearchView.SearchingChangeListener() {
            @Override
            public void onSearchingChange(Boolean isSearching) {
                if (isSearching) {
                    mSearchResultsListview.setVisibility(View.VISIBLE);
                    mListOwner.setVisibility(View.GONE);
                } else {
                    resultAdapter.ClearData();
                    mSearchResultsListview.setVisibility(View.GONE);
                    mListOwner.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>();
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < mYZList.size(); i++) {
                if (mYZList == null) {
                }
                if (mYZList.get(i).getName() == null) {
                }
                if (text.trim() == null) {
                }
                if (mYZList.get(i).getName().contains(text.trim())) {
                    autoCompleteData.add(mYZList.get(i));
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new AutoNamelist_YZAdapter(autoCompleteData, getActivity());
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < mYZList.size(); i++) {
                if (mYZList.get(i).getName().contains(text.trim())) {
                    resultData.add(mYZList.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new AutoNamelist_YZAdapter(resultData, getActivity());
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    private void initDatas() {
        HTTPHelper.AutoNamelistList(mIbpi);

    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Auto_NameListBean.NameListDataEntity mBean = (Auto_NameListBean.NameListDataEntity) message;
            if (mBean.getYz() != null) {
                mOwner.setVisibility(View.VISIBLE);
                mYZList = new ArrayList<Auto_NameListBean.NameListDataEntity.YzEntity>();
                mYZadapter = new AutoNamelist_YZAdapter(mYZList, getActivity());
                mYZadapter.AddNewData(mBean.getYz());
                mListOwner.setAdapter(mYZadapter);
                mYZList = mBean.getYz();
                initSearchData();
            } else {
                mOwner.setVisibility(View.GONE);

            }
            mRadioGroup.setVisibility(View.VISIBLE);
            mYWHadapter.AddNewData(mBean.getYwh());
            mYZDBadapter.AddNewData(mBean.getYzdb());
            mListCO.setAdapter(mYWHadapter);
            mListRe.setAdapter(mYZDBadapter);
            mFragContainer.setVisibility(View.VISIBLE);
            mListRe.setVisibility(View.GONE);
            mListOwner.setVisibility(View.GONE);
            mListCO.setVisibility(View.VISIBLE);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveAutoNamelistListEntity(result);
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
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Tag, "onResume");
        initDatas();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
