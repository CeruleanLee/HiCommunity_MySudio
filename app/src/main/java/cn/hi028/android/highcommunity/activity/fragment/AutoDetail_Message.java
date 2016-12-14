package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.view.CircleImageView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.Auto_MessageDetailAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InquiryDetailBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;
import cn.hi028.android.highcommunity.view.MyNoScrollListview;

/**
 * @功能：自治大厅 留言详情Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoDetail_Message extends BaseFragment {
    public static final String Tag = "AutoDetail_Message：";
    public static final String FRAGMENTTAG = "AutoDetail_Message";
    @Bind(R.id.inquirydetail_spokerImage)
    CircleImageView mSpeakerImage;
    @Bind(R.id.inquirydetail_spokerContent)
    EditText mSpeakerContent;
    @Bind(R.id.inquirydetail_spokerButton)
    ImageView mSpeakerButton;
    @Bind(R.id.inquirydetail_commentListview)
    MyNoScrollListview mCommentListview;

    @Bind(R.id.auto_inquirydetails_noData)
    TextView mNoData;
    @Bind(R.id.inquirydetail_spokerLayout)
    LinearLayout mSpokerLayout;

    List<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity> mList,mRepresentList;
    Auto_InquiryDetailBean.InquiryDetailDataEntity mBean = new Auto_InquiryDetailBean.InquiryDetailDataEntity();
    public boolean isReplay = false;
    /**
     * 被评论人id
     ***/
    String toid = null;
    /**
     * 评论id   对监督评论传0，对评论回复传id
     ****/
    String ParentId = null;
    /**
     * 监督id  可以intent得到 且data 数组返回
     **/
    String watch_id;
    @Bind(R.id.inquirydetail_representListview)
    MyNoScrollListview mRepresentListview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_detail_message, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        watch_id = bundle.getString("message_id");
        Log.d(Tag, "owner_id:" + watch_id);
//        watch_id = getActivity().getIntent().getStringExtra("reportDetail_id");

        initView();
        return view;
    }

    Auto_MessageDetailAdapter mAdapter,mRepresentAdapter;

    String content;

    private void initView() {
        Log.e(Tag, "initView");
        mCommentListview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mList = new ArrayList<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity>();
        mRepresentList = new ArrayList<Auto_InquiryDetailBean.InquiryDetailDataEntity.InquiryDetailReplyEntity>();

        mAdapter = new Auto_MessageDetailAdapter(mList, getActivity(), this,false);
        mRepresentAdapter = new Auto_MessageDetailAdapter(mRepresentList, getActivity(), this,true);


        mCommentListview.setAdapter(mAdapter);
        mRepresentListview.setAdapter(mRepresentAdapter);

        if (HighCommunityApplication.mUserInfo.getId() == 0) {
            mSpokerLayout.setVisibility(View.GONE);
        } else {
            mSpokerLayout.setVisibility(View.VISIBLE);
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + HighCommunityApplication.mUserInfo.getHead_pic(), mSpeakerImage);
        }
        if (watch_id != null) {
            initDatas();
        }
        boolean flag = getActivity().getIntent().getBooleanExtra(FRAGMENTTAG, false);
        if (flag) {
            InputMethodManager mManager = (InputMethodManager) mSpeakerContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mManager.showSoftInput(mSpeakerContent, InputMethodManager.SHOW_FORCED);
        }
        initSpoker();
        mSpeakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
                    LogUtil.d("");

                    HighCommunityUtils.GetInstantiation().ShowShouldLogin();
                    return;
                }
                content = mSpeakerContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    HTTPHelper.CommentReportDetail(mCommentIbpi, HighCommunityApplication.mUserInfo.getOwner_id(), toid, watch_id, ParentId, content);
                } else {
                    HighCommunityUtils.GetInstantiation().ShowToast("请输入内容", 0);
                }
            }
        });
    }

//    TextView  mReportTime, mContent;
//    LinearLayout mInforLayout;


    public void setText(String text, String to_id, String parentId, boolean isReplay) {
        this.isReplay = isReplay;
        mSpeakerContent.setHint(text);
        toid = to_id;
        ParentId = parentId;
        InputMethodManager mManager = (InputMethodManager) mSpeakerContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mManager.showSoftInput(mSpeakerContent, 0);
    }

    private void initDatas() {
        //TODO 这里的接口有点问题   改好后记得换回来
        HTTPHelper.GetMessageDetail(mIbpi, watch_id);
//        HTTPHelper.GetInquiryDetail(mIbpi, watch_id);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) {
                return;
            }
            mBean = (Auto_InquiryDetailBean.InquiryDetailDataEntity) message;

            mList = mBean.getReply();
            mAdapter.AddNewData(mList);
            mCommentListview.setAdapter(mAdapter);
            if (mBean.getRep_reply()!=null&&mBean.getRep_reply().size()!=0){

                mRepresentList=mBean.getRep_reply();
                mRepresentAdapter.AddNewData(mRepresentList);
                mRepresentListview.setAdapter(mRepresentAdapter);
                mRepresentListview.setVisibility(View.VISIBLE);
                initHeader(mRepresentListview);
            }else{
                mRepresentListview.setVisibility(View.GONE);
                initHeader(mCommentListview);
            }
            setHeadData();

//            mList = (List<Auto_NoticeListBean.NoticeListDataEntity>) message;
//            mAdapter.AddNewData(mList);
//            mListview.setAdapter(mAdapter);


        }

        @Override
        public Object onResolve(String result) {
            Log.e(Tag, " ~~~result" + result);
            return HTTPHelper.ResolveInquiryDetailDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    private void setHeadData() {
        if (mBean == null) {
            return;
//            mInforLayout.setVisibility(View.GONE);
        } else {
            ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mHeadImage);
            mHeadName.setTextColor(0xff1fc796);
            mHeadName.setText(mBean.getName());
            mHeadContent.setText(mBean.getContent());
            mHeadTime.setText(TimeUtil.getDayAllTime(Long.parseLong(mBean.getCreate_time())));
//            mReportTime.setText(TimeUtil.getDayTime(Long.parseLong(mBean.getCreate_time())));
//            String url = mBean.getContent();
//            CharSequence charSequence = Html.fromHtml(url);
//            mContent.setText("    " + charSequence);
        }


    }

    CircleImageView mHeadImage;
    TextView mHeadName;
    TextView mHeadContent;
    TextView mHeadTime;

    private void initHeader(MyNoScrollListview mListview) {
        LinearLayout header = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_auto_inquiry_detail, null);
        mHeadImage = (CircleImageView) header.findViewById(R.id.inquiry_Head_Image);
        mHeadName = (TextView) header.findViewById(R.id.inquiry_Head_Name);
        mHeadContent = (TextView) header.findViewById(R.id.inquiry_Head_Content);
        mHeadTime = (TextView) header.findViewById(R.id.inquiry_Head_time);
        mListview.addHeaderView(header, null, false);
    }

    BpiHttpHandler.IBpiHttpHandler mCommentIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccess(Object message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
            if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
//            if (isReplay) {
//                mAdapter.setNewData(isReplay, content, null);
//            } else {
//                mAdapter.setNewData(isReplay, content, message.toString());
//            }
            initSpoker();
            initDatas();
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

        }
    };

    private void initSpoker() {
//        CommunityFrag.isNeedRefresh = true;
        this.isReplay = false;
        mSpeakerContent.setHint("");
//对监督的评论
        toid = "0";
        ParentId = "0";
        mSpeakerContent.setText("");
    }

    public void finish() {
//        Intent result = new Intent();
//        result.putExtra("PinLun", mPraisesNum);
//        getActivity().setResult(getActivity().RESULT_OK, result);
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e(Tag, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Tag, "onResume");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
