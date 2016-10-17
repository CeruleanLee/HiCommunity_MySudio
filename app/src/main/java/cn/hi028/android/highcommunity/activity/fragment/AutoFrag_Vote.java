package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoVoteList_Q_Adapter;
import cn.hi028.android.highcommunity.adapter.AutoVoteList_V_Adapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteList_Vote;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

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
    @Bind(R.id.frag_AutoVote_vote)
    RadioButton but_Vote;
    @Bind(R.id.frag_AutoVote_Question)
    RadioButton but_Question;
    @Bind(R.id.tv_AutoVote_Nodata)
    TextView tv_Nodata;
    @Bind(R.id.frag_AutoVote_listview_vote)
    ListView listview_Vote;
    @Bind(R.id.frag_AutoVote_listview_questions)
    ListView listview_Questions;
    @Bind(R.id.frag_AutoVote_RadioGroup)
    RadioGroup mRadioGroup;
//    String type = 1 + "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_votelist, null);
        ButterKnife.bind(this, view);
        initView();
        initDatas();
        return view;
    }

    List<Auto_VoteList_Vote.VoteVVDataEntity> mVoteList;
    List<Auto_VoteList_Vote.VoteVVDataEntity> mQuestionList;

    public void initView() {
        LogUtil.d(Tag + "initView");
        listview_Vote.setEmptyView(tv_Nodata);
        listview_Questions.setEmptyView(tv_Nodata);
        mVoteAdapter = new AutoVoteList_V_Adapter(mVoteList, getActivity());
        mQuestionAdapter = new AutoVoteList_Q_Adapter(mQuestionList, getActivity());
        listview_Vote.setAdapter(mVoteAdapter);
        listview_Questions.setAdapter(mQuestionAdapter);
//        but_Vote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(getActivity(), "选举页显示   调查页隐藏", Toast.LENGTH_SHORT).show();
////                    Log.d(Tag,"V CHECKED");
////                    type = 2 + "";
////                    but_Question.setChecked(false);
////                    mQuestionAdapter.ClearData();
////                    HTTPHelper.GetAutoVoteList(mIbpi2, type);
////                    Log.d(Tag,"V 准备显示隐藏");
//                    listview_Questions.setVisibility(View.VISIBLE);
//                    listview_Vote.setVisibility(View.GONE);
//
////                    Log.d(Tag,"V 显示隐藏完成");
//                } else {
//                    Toast.makeText(getActivity(), "选举页 gone", Toast.LENGTH_SHORT).show();
//////                    Log.d(Tag,"V UNCHECKED");
////                    listview_Vote.setVisibility(View.GONE);
//                }
//
//
//            }
//        });
//        but_Question.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(getActivity(), "调查页显示   选举页隐藏", Toast.LENGTH_SHORT).show();
////
////                    Log.d(Tag,"Q CHECKED");
////                    but_Vote.setChecked(false);
//////                    type = 1 + "";
//////                    mVoteAdapter.ClearData();
//////                    HTTPHelper.GetAutoVoteList(mIbpi1, type);
////                    Log.d(Tag,"Q  准备显示 隐藏");
//
//                    listview_Questions.setVisibility(View.GONE);
//                    listview_Vote.setVisibility(View.VISIBLE);
////                    Log.d(Tag,"Q  显示隐藏完成");
//                } else {
//                    Toast.makeText(getActivity(), "问卷页 gone", Toast.LENGTH_SHORT).show();
//
//////                    Log.d(Tag,"Q UNCHECKED");
////                    listview_Questions.setVisibility(View.GONE);
//                }
//            }
//        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.frag_AutoVote_vote:
                        listview_Questions.setVisibility(View.VISIBLE);
                        listview_Vote.setVisibility(View.GONE);
                        break;
                    case R.id.frag_AutoVote_Question:
                        listview_Questions.setVisibility(View.GONE);
                        listview_Vote.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }

    private void initDatas() {

        HTTPHelper.GetAutoVoteList(mIbpi1, 1+"");
        HTTPHelper.GetAutoVoteList(mIbpi2, 2+"");
//        listview_Questions.setVisibility(View.VISIBLE);

    }


    BpiHttpHandler.IBpiHttpHandler mIbpi2 = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {

            mQuestionList = (List<Auto_VoteList_Vote.VoteVVDataEntity>) message;
            mQuestionAdapter.ClearData();
            mQuestionAdapter.AddNewData(mQuestionList);
            listview_Questions.setAdapter(mQuestionAdapter);
            listview_Vote.setVisibility(View.VISIBLE);

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
    };
    BpiHttpHandler.IBpiHttpHandler mIbpi1 = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mVoteList = (List<Auto_VoteList_Vote.VoteVVDataEntity>) message;
            mVoteAdapter.ClearData();
            mVoteAdapter.AddNewData(mVoteList);
            listview_Vote.setAdapter(mVoteAdapter);
            listview_Questions.setVisibility(View.GONE);
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
    };

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");

        //		mLoadingView.startLoading();
        registNetworkReceiver();
    }

    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

//    Intent mIntent = new Intent(getActivity(), AutonomousAct_Second.class);


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    //有网络
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    //					if(nextPage == 1){
                    initDatas();
                    //					HTTPHelper.GetThirdService(mIbpi);
                    //					}
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    //					if(nextPage == 1){
                    //					}
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}
