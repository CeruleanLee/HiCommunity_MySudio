package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoVoteList_Q_Adapter;
import cn.hi028.android.highcommunity.adapter.AutoVoteList_V_Adapter;
import cn.hi028.android.highcommunity.adapter.HuiOrderAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteList_Vote;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.MyCustomViewPager;

/**
 * @功能：自治大厅 投票<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */
public class AutoFrag_Vote extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_Vote~~~";
    public static final String FRAGMENTTAG = "AutoFrag_Vote";
    AutoVoteList_Q_Adapter mQuestionAdapter;
    AutoVoteList_V_Adapter mVoteAdapter;
    @Bind(R.id.frag_AutoVote_RadioGroup_page)
    RadioGroup mRadioGroup;
    @Bind(R.id.frag_vote_ViewPager)
    MyCustomViewPager mViewPager;
    /**
     * 当前页
     **/
    int currentPo = 0;
    public List<ListView> listViewList;
    public List<View> proPressList;
    public List<TextView> noDataList;
    @Bind(R.id.frag_AutoVote_vote_page)
    RadioButton but_Vote;
    @Bind(R.id.frag_AutoVote_Question_page)
    RadioButton but_Question;
    private List<BaseAdapter> adapterList;
    ListView listview_Questions, listview_Vote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_auto_votelist_page, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    List<Auto_VoteList_Vote.VoteVVDataEntity> mVoteList;
    List<Auto_VoteList_Vote.VoteVVDataEntity> mQuestionList;

    public void initView() {
        mQuestionList = new ArrayList<Auto_VoteList_Vote.VoteVVDataEntity>();
        mVoteList = new ArrayList<Auto_VoteList_Vote.VoteVVDataEntity>();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.frag_AutoVote_vote_page:
                        Log.d(Tag, "checkedId   vote" + checkedId);
                        mViewPager.setCurrentItem(0);
                        HTTPHelper.GetAutoVoteList(mIbpi2, 2 + "");
                        break;
                    case R.id.frag_AutoVote_Question_page:
                        mViewPager.setCurrentItem(1);
                        HTTPHelper.GetAutoVoteList(mIbpi1, 1 + "");
                        break;
                }
            }
        });
        proPressList = new ArrayList<View>();
        noDataList = new ArrayList<TextView>();
        listViewList = new ArrayList<ListView>();
        adapterList = new ArrayList<BaseAdapter>();
        HuiOrderAdapter adapter = new HuiOrderAdapter();
        List<View> viewList = new ArrayList<View>();
        viewList.add(getVoteView());
        viewList.add(getQuestionView());
        mViewPager.setPagingEnabled(false);
        adapter.setViewList(viewList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                currentPo = i;
                Log.e(Tag, " Page i  " + i);
                if (i == 0) {//选举
                    but_Vote.setChecked(true);
                    but_Question.setChecked(false);
                } else if (i == 1) {
                    but_Question.setChecked(true);
                    but_Vote.setChecked(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    View getQuestionView() {
        View view_Q = LayoutInflater.from(getActivity()).inflate(R.layout.page_question, null);
        mQuestionAdapter = new AutoVoteList_Q_Adapter(mQuestionList, getActivity());
        listview_Questions = (ListView) view_Q.findViewById(R.id.frag_AutoVote_listview_questions);
        View mProgress = view_Q.findViewById(R.id.ll_AutoQ_Progress);
        mProgress.setVisibility(View.GONE);
        TextView mNodata = (TextView) view_Q.findViewById(R.id.tv_AutoVoteQ_Nodata);
        proPressList.add(mProgress);
        noDataList.add(mNodata);
        listview_Questions.setEmptyView(mNodata);
        listview_Questions.setAdapter(mQuestionAdapter);
        adapterList.add(mQuestionAdapter);
        listViewList.add(listview_Questions);
        return view_Q;
    }

    View getVoteView() {
        Log.e(Tag, "getPageView  v");
        View view_V = LayoutInflater.from(getActivity()).inflate(R.layout.page_vote, null);
        mVoteAdapter = new AutoVoteList_V_Adapter(mVoteList, getActivity());
        listview_Vote = (ListView) view_V.findViewById(R.id.frag_AutoVote_listview_vote);
        View mProgress = view_V.findViewById(R.id.ll_AutoV_Progress);
        mProgress.setVisibility(View.GONE);
        TextView mNodata = (TextView) view_V.findViewById(R.id.tv_AutoVoteV_Nodata);
        proPressList.add(mProgress);
        noDataList.add(mNodata);
        listview_Vote.setEmptyView(mNodata);
        listview_Vote.setAdapter(mVoteAdapter);
        adapterList.add(mVoteAdapter);
        listViewList.add(listview_Vote);
        HTTPHelper.GetAutoVoteList(mIbpi2, 2 + "");
        return view_V;
    }

    private void initDatas() {
        //1  问卷  2 选举
        HTTPHelper.GetAutoVoteList(mIbpi2, 2 + "");
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi1 = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) return;
            mQuestionList = (List<Auto_VoteList_Vote.VoteVVDataEntity>) message;
            if (mQuestionList == null) return;
            if (mQuestionAdapter != null && listview_Questions != null) {
                mQuestionAdapter.ClearData();
                mQuestionAdapter.AddNewData(mQuestionList);

                listview_Questions.setAdapter(mQuestionAdapter);
            } else {
                Log.e(Tag, "listview_Questions null");
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVoteVVDataEntity(result);
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
    BpiHttpHandler.IBpiHttpHandler mIbpi2 = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) return;
            mVoteList = (List<Auto_VoteList_Vote.VoteVVDataEntity>) message;
            if (mVoteList == null) return;
            if (mVoteAdapter == null) {
                Log.e(Tag, "mVoteAdapter null");
            }
            if (listview_Vote == null) {
                Log.e(Tag, "listview_Vote null");
            }
            if (mVoteAdapter != null && listview_Vote != null) {
                mVoteAdapter.ClearData();
                mVoteAdapter.AddNewData(mVoteList);
                listview_Vote.setAdapter(mVoteAdapter);
            } else {
                Log.e(Tag, "listview null");
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVoteVVDataEntity(result);
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
        initDatas();
        Log.d(Tag, "onResume");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
