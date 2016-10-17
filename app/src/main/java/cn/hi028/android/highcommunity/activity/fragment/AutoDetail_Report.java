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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.view.CircleImageView;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.Auto_ReportDetailAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_ReportDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 汇报详情Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoDetail_Report extends BaseFragment {
    public static final String Tag = "~~~AutoDetail_Report~~~";
    public static final String FRAGMENTTAG = "AutoDetail_Report";
    @Bind(R.id.reportdetail_spokerImage)
    CircleImageView mSpeakerImage;
    @Bind(R.id.reportdetail_spokerContent)
    EditText mSpeakerContent;
    @Bind(R.id.reportdetail_spokerButton)
    ImageView mSpeakerButton;
    @Bind(R.id.reportdetail_commentListview)
    ListView mCommentListview;
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.auto_reportDetails_noData)
    TextView mNoData;
    List<Auto_ReportDetailBean.ReportDetailDataEntity.ReportDetailReplyEntity> mList;
    @Bind(R.id.reportdetail_spokerLayout)
    LinearLayout mSpokerLayout;
    @Bind(R.id.loading_message)
    TextView loadingMessage;
Auto_ReportDetailBean.ReportDetailDataEntity mBean=new Auto_ReportDetailBean.ReportDetailDataEntity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_detail_report, null);
        ButterKnife.bind(this, view);
        reportDetailID=getActivity().getIntent().getIntExtra("reportDetail_id",-1);
        initView();
        return view;
    }

    Auto_ReportDetailAdapter mAdapter;
int reportDetailID;
    private void initView() {
        LogUtil.d(Tag + "initView");
        mProgress.setVisibility(View.VISIBLE);
        mCommentListview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mList = new ArrayList<Auto_ReportDetailBean.ReportDetailDataEntity.ReportDetailReplyEntity>();
        mAdapter = new Auto_ReportDetailAdapter(mList, getActivity(), this);
        initHeader();
        mCommentListview.setAdapter(mAdapter);
        if (HighCommunityApplication.mUserInfo.getId()==0){
            mSpokerLayout.setVisibility(View.GONE);
        }else{
            mSpokerLayout.setVisibility(View.VISIBLE);
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP+HighCommunityApplication.mUserInfo.getHead_pic(),mSpeakerImage);
        }
        if (reportDetailID!=-1){
            initDatas();
        }
    }

    TextView mTitle, mReportorName, mReportTime, mContent;




    private void initDatas() {
        HTTPHelper.AutoNoticeList(mIbpi);
    }

    public void setText(String text, String to_id, String parentId, boolean isReplay) {
        this.isReplay = isReplay;
        mSpeakerContent.setHint(text);
        toid = to_id;
        ParentId = parentId;
        InputMethodManager mManager = (InputMethodManager) mSpeakerContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mManager.showSoftInput(mSpeakerContent, 0);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");

            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message==null){return;}
            mBean= (Auto_ReportDetailBean.ReportDetailDataEntity) message;
setHeadData();
            mList=mBean.getReply();
            mAdapter.AddNewData(mList);
            mCommentListview.setAdapter(mAdapter);
//            mList = (List<Auto_NoticeListBean.NoticeListDataEntity>) message;
//            mAdapter.AddNewData(mList);
//            mListview.setAdapter(mAdapter);


        }

        @Override
        public Object onResolve(String result) {
            LogUtil.d(Tag + " ~~~result" + result);
            return HTTPHelper.ResolveAutoNoticeListEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    private void setHeadData() {
        mTitle.setText(mBean.getTitle());
        mReportorName.setText(mBean.getName());
mReportTime.setText(TimeUtil.getDayTime(Long.parseLong(mBean.getCreate_time())));
    mContent.setText("  "+mBean.getContent());




    }
    private void initHeader() {
        LinearLayout header = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_auto_report_detail, null);
        mTitle = (TextView) header.findViewById(R.id.reportdetail_title);
        mReportorName = (TextView) header.findViewById(R.id.reportdetail_name);
        mReportTime = (TextView) header.findViewById(R.id.reportdetail_time);
        mContent = (TextView) header.findViewById(R.id.reportdetail_content);
        mCommentListview.addHeaderView(header);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(Tag + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");

        //		mLoadingView.startLoading();
//        registNetworkReceiver();
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
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    initDatas();
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}
