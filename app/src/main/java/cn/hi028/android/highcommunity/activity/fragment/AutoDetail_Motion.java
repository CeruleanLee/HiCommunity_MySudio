package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionDetailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 提案详情Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoDetail_Motion extends BaseFragment {
    public static final String Tag = "~~~AutoDetail_Motion~~~";
    public static final String FRAGMENTTAG = "AutoDetail_Motion";
    @Bind(R.id.item_aotumotion_but_support)
    CheckedTextView but_Support;
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.loading_message)
    TextView mloadingMessage;
    @Bind(R.id.auto_motiondetails_noData)
    TextView mNoData;
    Auto_MotionDetailBean.MotionDetailDataEntity mBean;
    String motion_id;
    @Bind(R.id.motiondetail_title)
    TextView mTitle;
    @Bind(R.id.motiondetail_name)
    TextView mName;
    @Bind(R.id.motiondetail_content)
    TextView mContent;
    @Bind(R.id.motiondetail_time)
    TextView mTime;
    @Bind(R.id.motiondetail_vote_percent)
    TextView mVotePercent;
    View rootView;

    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");

        rootView = inflater.inflate(R.layout.frag_auto_detail_motion, null);
        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        motion_id = bundle.getString("motion_id");
        Log.d(Tag, "motion_id:" + motion_id);

        initView();
        return rootView;
    }


    private void initView() {
        LogUtil.d(Tag + "initView");
        mProgress.setVisibility(View.VISIBLE);
//        mCommentListview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//        mList = new ArrayList<Auto_ReportDetailBean.ReportDetailDataEntity.ReportDetailReplyEntity>();
//        mAdapter = new Auto_ReportDetailAdapter(mList, getActivity(), this);
//        initDatas();
    }



    private void initDatas() {

        HTTPHelper.GetMotionDetail(mIbpi, motion_id, HighCommunityApplication.mUserInfo.getId() + "");
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) {
                return;
            }
            mBean = (Auto_MotionDetailBean.MotionDetailDataEntity) message;
            setViewData(mBean);
//            mList = mBean.getReply();
//            mAdapter.AddNewData(mList);
//            mCommentListview.setAdapter(mAdapter);
//            mList = (List<Auto_NoticeListBean.NoticeListDataEntity>) message;
//            mAdapter.AddNewData(mList);
//            mListview.setAdapter(mAdapter);


        }



        @Override
        public Object onResolve(String result) {
            LogUtil.d(Tag + " ~~~result" + result);
            return HTTPHelper.ResolveMotionDetailData(result);
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

    private void setViewData(Auto_MotionDetailBean.MotionDetailDataEntity mBean) {
        mTitle.setText(mBean.getTitle());
        mName.setText("提案人："+mBean.getPublish_man());
        mContent.setText("\u3000\u3000"+mBean.getContent());
        mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getCreate_time())));
        mVotePercent.setText("支持率："+mBean.getVote_percent()+"%");
        if (mBean.getIsSuggest().equals("1")) {
            but_Support.setChecked(true);
            but_Support.setText(" 已支持 ");

        }else if (mBean.getIsSuggest().equals("0")){
            but_Support.setChecked(false);
            but_Support.setText(" 支持 ");
        }



    }



    /**********
     * --------------------------------------------------------------------
     **********/

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(Tag + "onPause");
    }
    private PopupWindow mWatingWindow;
    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");
        initDatas();


    }


    @OnClick(R.id.item_aotumotion_but_support)
    public void onClick() {

        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), rootView, Gravity.CENTER);

        HTTPHelper.SupportMotion(mSuuportIbpi,mBean.getId());

    }

    BpiHttpHandler.IBpiHttpHandler mSuuportIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

        }

        @Override
        public void onSuccess(Object message) {
            LogUtil.d(Tag + "onSuccess");
            mWatingWindow.dismiss();
            if (message==null)return;
            mResultData= (Auto_SupportedResultBean.SupportedResultDataEntity) message;
//initDatas();
            mVotePercent.setText("支持率："+mResultData.getVote_percent()+"%");
           but_Support.setChecked(true);
            but_Support.setText("已支持");
//            Toast.makeText(getActivity(),"已支持",Toast.LENGTH_SHORT).show();

        }

        @Override
        public Object onResolve(String result) {



            return HTTPHelper.ResolveSupportedResultData(result);
        }
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mWatingWindow.dismiss();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
