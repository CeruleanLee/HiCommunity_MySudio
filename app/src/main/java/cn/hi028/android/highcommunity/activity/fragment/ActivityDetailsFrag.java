package cn.hi028.android.highcommunity.activity.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.view.AutoScrollViewPager;
import net.duohuo.dhroid.view.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ActiveAct;
import cn.hi028.android.highcommunity.adapter.ActivityAssistAdapter;
import cn.hi028.android.highcommunity.adapter.ActivityDetailAdapter;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.bean.ActivityDetailBean;
import cn.hi028.android.highcommunity.bean.OperateBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：活动详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/13<br>
 */
//@EFragment(resName = "frag_activitydetails")
public class ActivityDetailsFrag extends BaseFragment {
    public static final String Tag = "ActivityDetails->";

    public static final String FRAGMENTTAG = "ActivityDetailsFrag";
   @Bind(R.id.ptrlv_activitydetails_listview)
    PullToRefreshListView mlistView;
   @Bind(R.id.tv_activitydetails_replay)
    ImageView mReplay;
   @Bind(R.id.tv_activitydetails_join)
    TextView mJoin;

   @Bind(R.id.progress_ActivityDetails)
    View mProgress;
   @Bind(R.id.tv_ActivityDetails_Nodata)
    TextView mNodata;

    String replayBG_grey="#aaaaaa";
    String replayBG_blue="#545BDD";
    
    public PicPageAdapter pagerAdapter;
    public PopupWindow mWindow;
    String rid = "";
    String aid = "";
    String ReplayContent = "";
    boolean isReplay = false;
    boolean isReplayCanRepaly=false;

    AutoScrollViewPager mViewPage;
    CirclePageIndicator mDicator;
    TextView mTitle, mNumber, mLocation, mDeadTime, mName, UnJoin, mQQ, mWeixin, mPhone, mContent, mAssistNumber;
    PullToRefreshGridView mGridView;
    LinearLayout mJoinedLayout;
    ActivityDetailBean mBean;
    ActivityDetailAdapter mAdapter;
    ActivityAssistAdapter mAssistPicAdapter;
    PopupWindow mWaitingWindow, mShareWindow;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_activitydetails, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 初始化VIew
     */
//    @AfterViews
    void initView() {
        Log.e(Tag,"initView");
        mProgress.setVisibility(View.VISIBLE);
        mAdapter = new ActivityDetailAdapter(this);
        mAssistPicAdapter = new ActivityAssistAdapter(getActivity());
        aid = getActivity().getIntent().getStringExtra(ActiveAct.INTENTTAG);
        LinearLayout header = (LinearLayout) LayoutInflater.
                from(getActivity()).inflate(R.layout.header_activity_details, null);
        mViewPage = (AutoScrollViewPager) header
                .findViewById(R.id.asvp_activity_header_viewpage);
        mDicator = (CirclePageIndicator) header
                .findViewById(R.id.cpi_activity_header_dot);
        mTitle = (TextView) header
                .findViewById(R.id.tv_activity_header_title);
        mNumber = (TextView) header
                .findViewById(R.id.tv_activity_header_number);
        mLocation = (TextView) header
                .findViewById(R.id.tv_activity_header_location);
        mDeadTime = (TextView) header
                .findViewById(R.id.tv_activity_header_deadtime);
        mName = (TextView) header
                .findViewById(R.id.tv_activity_header_Username);
        UnJoin = (TextView) header
                .findViewById(R.id.tv_activity_header_unjoin_layout);
        mJoinedLayout = (LinearLayout) header
                .findViewById(R.id.tv_activity_header_joined_layout);
        mQQ = (TextView) header
                .findViewById(R.id.tv_activity_header_qq);
        mWeixin = (TextView) header
                .findViewById(R.id.tv_activity_header_weixin);
        mPhone = (TextView) header
                .findViewById(R.id.tv_activity_header_phone);
        mContent = (TextView) header
                .findViewById(R.id.tv_activity_header_actContent);
        mGridView = (PullToRefreshGridView) header
                .findViewById(R.id.ptrgv_activitydetail_member_grideview);
        mAssistNumber = (TextView) header
                .findViewById(R.id.tv_activitydetail_commentNumber);
        mlistView.setMode(PullToRefreshBase.Mode.DISABLED);
        mlistView.getRefreshableView().addHeaderView(header);
        mlistView.setAdapter(mAdapter);
        Log.e(Tag,"HTTPHelper.ActivityDetail---");

        HTTPHelper.ActivityDetail(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", aid);
        mGridView.setAdapter(mAssistPicAdapter);
        pagerAdapter = new PicPageAdapter(getActivity()
        ).setInfiniteLoop(true);
        mViewPage.setAdapter(pagerAdapter);
        mDicator.setViewPager(mViewPage);
        mDicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPage.setInterval(2000);
        mViewPage.startAutoScroll();
        mViewPage.setCurrentItem(0);
        mReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay();
            }
        });
        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });
    }

    /**
     * 活动详情请求回掉
     */
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mBean = (ActivityDetailBean) message;
            mAdapter.AddNewData(mBean.getReplies());
            pagerAdapter.setImageIdList(mBean.getDetail().get(0).getPic());
            mTitle.setText("【" + mBean.getDetail().get(0).getTitle() + "】");
            mNumber.setText(mBean.getDetail().get(0).getUsers() + "人参与");
            mLocation.setText(mBean.getDetail().get(0).getAddress());
            mDeadTime.setText("截至" + mBean.getDetail().get(0).getEnd_time());
            mName.setText("发起人:" + mBean.getDetail().get(0).getNick());
            if (mBean.getDetail().get(0).getIsJoin().equals("0")) {
                UnJoin.setVisibility(View.VISIBLE);
                mJoinedLayout.setVisibility(View.GONE);
                mJoin.setClickable(true);
                mJoin.setText("参与活动");
               
                mReplay.setBackgroundColor(Color.parseColor(replayBG_grey));
                isReplayCanRepaly=false;
//                mReplay.setBackgroundResource(R.color.Defult_Color_Grey);
//                mReplay.setClickable(false);
            } else {
                UnJoin.setVisibility(View.GONE);
                mJoinedLayout.setVisibility(View.VISIBLE);
                mQQ.setText(mBean.getDetail().get(0).getQq());
                mWeixin.setText(mBean.getDetail().get(0).getWeixin());
                mPhone.setText((mBean.getDetail().get(0)).getPhone());
                mJoin.setClickable(false);
                mJoin.setBackgroundResource(R.color.Defult_Color_Grey);
                mJoin.setText("已参与该活动");
               
                mReplay.setBackgroundColor(Color.parseColor(replayBG_blue));
                isReplayCanRepaly=true;
                
//                mReplay.setClickable(true);
            }
            mContent.setText(mBean.getDetail().get(0).getContent());
            mAssistPicAdapter.AddNewData(mBean.getMembers());
            mAssistNumber.setText("评论:" + mBean.getDetail().get(0).getReplys());
            HighCommunityUtils.GetInstantiation().setGridViewHeightBasedOnChildren(mGridView, mAssistPicAdapter, 7);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveActivityDetail(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

//    @Click(R.id.tv_activitydetails_replay)
    void replay() {
    	if (isReplayCanRepaly) {
			
    		setToWho("", null);
		}else {
			Toast.makeText(getActivity(), "参与活动才能参与回复哟~", Toast.LENGTH_SHORT).show();
		}
    }

    /**
     * 回复评论
     * @param hint
     * @param rid
     */
    public void setToWho(String hint, String rid) {
        if (TextUtils.isEmpty(hint)) {
            isReplay = false;
        } else {
            isReplay = true;
        }
        this.rid = rid;
        HighCommunityUtils.GetInstantiation().ShowInput(getActivity(), mJoin, mBack, hint);
        HighCommunityUtils.GetInstantiation().popupInputMethodWindow(mJoin);
    }

    HighCommunityUtils.InputCallBack mBack = new HighCommunityUtils.InputCallBack() {
        @Override
        public void onInput(String input) {
            ReplayContent = input;
            mWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mDeadTime, Gravity.CENTER);
            HTTPHelper.CommentActivity(mCommentBpi, HighCommunityApplication.mUserInfo.getId() + "", aid, input, rid);
        }
    };
    /**
     * 评论回掉
     */
    BpiHttpHandler.IBpiHttpHandler mCommentBpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWindow.dismiss();
            if (null == message)
                return;
            mAdapter.setNewData(isReplay, ReplayContent);
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
            mWindow.dismiss();
        }
    };

    /**
     * 右上角点击事件
     */
    public void onRight() {
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mAssistNumber, Gravity.CENTER);
        HTTPHelper.GetActivityOpreate(mOpreateIbpi, HighCommunityApplication.mUserInfo.getId() + "", aid);
    }

    /**
     * 右上角点击回掉
     */
    BpiHttpHandler.IBpiHttpHandler mOpreateIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (null == message)
                return;
            OperateBean mBean = (OperateBean) message;
            mShareWindow = HighCommunityUtils.
                    GetInstantiation().ShowActivityShare(getActivity(), mBean, aid, mContent, mdelete);
            mShareWindow.showAtLocation(mDeadTime, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveOperateBean(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mWaitingWindow.dismiss();
        }
    };
    /**
     * 删除回掉
     */
    HighCommunityUtils.OnDeleteClick mdelete = new HighCommunityUtils.OnDeleteClick() {
        @Override
        public void OnClick() {
            getActivity().finish();
        }
    };

    /**
     * 参与活动
     */
//    @Click(R.id.tv_activitydetails_join)
    void join() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mDeadTime, Gravity.CENTER);
            HTTPHelper.JoinActivity(mJoinIbpi, HighCommunityApplication.mUserInfo.getId() + "", aid);
        }
    }

    /**
     * 参与回掉
     */
    BpiHttpHandler.IBpiHttpHandler mJoinIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (null == message)
                return;
            mBean.getDetail().get(0).setIsJoin("1");
            mJoin.setClickable(false);
            mJoin.setText("已参与该活动");
//            mReplay.setClickable(true);
//            mReplay.setBackgroundColor(Color.parseColor("#545BDD"));
//            mReplay.setBackgroundResource(Color.parseColor(replayBG_blue));
            isReplayCanRepaly=true;
            HighCommunityUtils.GetInstantiation().ShowToast("成功加入该活动", 0);
            HTTPHelper.ActivityDetail(mIbpi, HighCommunityApplication.mUserInfo.getId() + "", aid);
            if (mBean.getDetail().get(0).getIsJoin().equals("1")) {
                UnJoin.setVisibility(View.GONE);
                mJoinedLayout.setVisibility(View.VISIBLE);
                mQQ.setText(mBean.getDetail().get(0).getQq());
                mWeixin.setText(mBean.getDetail().get(0).getWeixin());
                mPhone.setText((mBean.getDetail().get(0)).getPhone());
                mJoin.setClickable(false);
                mJoin.setBackgroundResource(R.color.Defult_Color_Grey);
                mJoin.setText("已参与该活动");
                mReplay.setClickable(true);
            }
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
            mWaitingWindow.dismiss();
        }
    };

    public boolean onkeyDown() {
        if (mShareWindow != null && mShareWindow.isShowing()) {
            mShareWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
