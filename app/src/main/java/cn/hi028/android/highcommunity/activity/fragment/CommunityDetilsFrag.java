/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.don.view.CircleImageView;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.duohuo.dhroid.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.activity.GroupDataAct;
import cn.hi028.android.highcommunity.activity.MenuLeftAct;
import cn.hi028.android.highcommunity.adapter.AssistHeadPicAdapter;
import cn.hi028.android.highcommunity.adapter.CommDetailAdapter;
import cn.hi028.android.highcommunity.adapter.CommunityImgGridAdapter;
import cn.hi028.android.highcommunity.bean.MessageDetailsBean;
import cn.hi028.android.highcommunity.bean.OperateBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：帖子详情<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/22<br>
 */
@EFragment(resName = "frag_comm_detail")
public class CommunityDetilsFrag extends BaseFragment {

	public static final String FRAGMENTTAG = "CommunityDetilsFrag";
	final String  Tag="CommunityDetilsFrag--->";
	CircleImageView mAvatar;
	TextView mName;
	TextView mSex;
	ImageView mMore;
	TextView mTime;
	TextView mForm;
	TextView mContent;
	PullToRefreshGridView mContentGrid;
	TextView mLocation;
	ImageView mAssist;
	PullToRefreshGridView mAssisGrid;
	ImageView mainComment;
	View mPicLayout,layout_Assist;
	@ViewById(R.id.ptrlv_communitydetails_listview)
	ListView mList;
	@ViewById(R.id.ll_communitydetails_spokerLayout)
	LinearLayout mSpeakerLayout;
	@ViewById(R.id.civ_communitydetails_spokerImage)
	CircleImageView mSpeakerImage;
	@ViewById(R.id.ev_communitydetails_spokerContent)
	EditText mSpeakerContent;
	@ViewById(R.id.ev_communitydetails_spokerButton)
	ImageView mSpeakerButton;

	CommDetailAdapter mAdapter;
	AssistHeadPicAdapter mAssistAdapter;
	CommunityImgGridAdapter mImageAdapter;
	PopupWindow mWindow, mWatingWindow;
	String toid = null, ParentId = null;
	public boolean isReplay = false;
	String content;
	int mPraisesNum = 0;

	MessageDetailsBean mBean = new MessageDetailsBean();
	OperateBean mOperateBean = new OperateBean();
	int mid = 0;
	@ViewById(R.id.ll_NoticeDetails_Progress)
	View mProgress;
	@ViewById(R.id.tv_NoticeDetails_noData)
	TextView mNodata;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=LayoutInflater.from(getActivity()).inflate(R.layout.frag_comm_detail, null);
		return view;
	}



	@AfterViews
	void initView() {
		mProgress.setVisibility(View.VISIBLE);
		mList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		mAdapter = new CommDetailAdapter(this);
		mAssistAdapter = new AssistHeadPicAdapter(getActivity());
		mImageAdapter = new CommunityImgGridAdapter(getActivity());
		initHeader();
		mList.setAdapter(mAdapter);
		mContentGrid.setAdapter(mImageAdapter);
		if (HighCommunityApplication.mUserInfo.getId() == 0) {
			mSpeakerLayout.setVisibility(View.GONE);
		} else {
			mSpeakerLayout.setVisibility(View.VISIBLE);
			ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + HighCommunityApplication.mUserInfo.getHead_pic(), mSpeakerImage);
		}
		mid = getActivity().getIntent().getIntExtra(CommunityDetailAct.INTENTTAG, 0);
		boolean flag = getActivity().getIntent().getBooleanExtra(FRAGMENTTAG, false);
		if (mid != 0)
			//			waitWindow2=HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
			HTTPHelper.GetMessageDetails2(mIbpi, mid);
		if (flag) {
			InputMethodManager mManager = (InputMethodManager) mSpeakerContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			mManager.showSoftInput(mSpeakerContent, InputMethodManager.SHOW_FORCED);
		}
		mMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mWindow == null) {
					mWindow = HighCommunityUtils.GetInstantiation()
							.ShowCommunityShare((CommunityDetailAct) getActivity(), mOperateBean, mid, mAssist, new HighCommunityUtils.OnDeleteClick() {
								@Override
								public void OnClick() {
									getActivity().finish();
								}
							});
				}
				mWindow.showAtLocation(mMore, Gravity.BOTTOM, 0, HighCommunityApplication.SoftKeyHight);
			}
		});
		mainComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initSpoker();
				mSpeakerContent.requestFocus();
				InputMethodManager iManager=(InputMethodManager) mSpeakerContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				iManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
			}
		});


	}

	private void initHeader() {
		LinearLayout header = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.header_comm_list, null);
		mAvatar = (CircleImageView) header.findViewById(R.id.civl_commDetails_avatar);
		mName = (TextView) header.findViewById(R.id.tv_commDetails_name);
		mSex = (TextView) header.findViewById(R.id.tv_commDetails_sex);
		mMore = (ImageView) header.findViewById(R.id.iv_commDetails_more);
		mTime = (TextView) header.findViewById(R.id.tv_commDetails_time);
		mForm = (TextView) header.findViewById(R.id.tv_commDetails_from);
		mContent = (TextView) header.findViewById(R.id.tv_commDetails_content);
		mContentGrid = (PullToRefreshGridView) header.findViewById(R.id.ptrgv_commDetails_piclistView);
		mLocation = (TextView) header.findViewById(R.id.tv_commDetails_location);
		mAssist = (ImageView) header.findViewById(R.id.tv_commDetails_Assist);
		mAssisGrid = (PullToRefreshGridView) header.findViewById(R.id.ptrgv_commDetails_member_grideview);
		mainComment=(ImageView) header.findViewById(R.id.tv_commDetails_Maincomment);
		mPicLayout= header.findViewById(R.id.layout_piclist);
		layout_Assist= header.findViewById(R.id.layout_Assist);
		//        mList.getRefreshableView().addHeaderView(header);
		//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		mList.addHeaderView(header);

	}

	BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			mProgress.setVisibility(View.GONE);
			mNodata.setVisibility(View.VISIBLE);
			mNodata.setText(message);
		}

		@Override
		public void onSuccess(Object message) {
			mProgress.setVisibility(View.GONE);
			mNodata.setVisibility(View.GONE);
			if (null == message)
				return;
			mBean = (MessageDetailsBean) message;
			HTTPHelper.GetPoerate(mMoreIbpi, HighCommunityApplication.mUserInfo.getId() + "", mid + "");
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveMessageDetails(result);
		}

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void cancleAsyncTask() {
		}
	};

//	@Click(R.id.civl_commDetails_avatar)
//	void toUser() {
//		Intent mDetails = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftAct.class));
//		mDetails.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_USERINFO);
//		mDetails.putExtra(MenuLeftAct.INTENTTAG, mBean.getId() + "");
//		startActivity(mDetails);
//	}

	@Click(R.id.ev_communitydetails_spokerButton)
	void Comment() {
		if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
			LogUtil.d("");

			HighCommunityUtils.GetInstantiation().ShowShouldLogin();
			return;
		}
		content = mSpeakerContent.getText().toString();
		if (!TextUtils.isEmpty(content)) {
			mWatingWindow = HighCommunityUtils.GetInstantiation()
					.ShowWaittingPopupWindow(getActivity(), mMore, Gravity.CENTER);
			if (!isReplay) {

					mPraisesNum++;
			}
			HTTPHelper.CommentMessage(mCommentIbpi, mid + "", HighCommunityApplication.mUserInfo.getId() + "", toid, ParentId, content);
		} else {
			HighCommunityUtils.GetInstantiation().ShowToast("请输入内容", 0);
		}
	}

	public void setText(String text, String to_id, String parentId, boolean isReplay) {
		this.isReplay = isReplay;
		mSpeakerContent.setHint(text);
		toid = to_id;
		ParentId = parentId;
		InputMethodManager mManager = (InputMethodManager) mSpeakerContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		mManager.showSoftInput(mSpeakerContent, 0);
	}

	BpiHttpHandler.IBpiHttpHandler mMoreIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			if (mWatingWindow != null && mWatingWindow.isShowing())
				mWatingWindow.dismiss();
		}

		@Override
		public void onSuccess(Object message) {
			CommunityFrag.isNeedRefresh = false;
			if (mWatingWindow != null && mWatingWindow.isShowing())
				mWatingWindow.dismiss();
			if (null == message)
				return;
			mOperateBean = (OperateBean) message;
			initData();
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

		}
	};

	private void initData() {
		ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + mBean.getHead_pic(), mAvatar);
		mName.setText(mBean.getNick());
		if (mBean.getSex().equals("0")) {
			mSex.setSelected(false);
		} else {
			mSex.setSelected(true);
		}
		//头像点击监听
		mAvatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mDetails = new Intent(getActivity(), GeneratedClassUtils.get(MenuLeftAct.class));
				mDetails.putExtra(MenuLeftAct.ACTIVITYTAG, Constacts.MENU_LEFT_USERINFO);
				mDetails.putExtra(MenuLeftAct.INTENTTAG, mBean.getId() + "");
				startActivity(mDetails);
			}
		});
		mSex.setText(mBean.getAge() + "");
		mTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(Long.parseLong(mBean.getCreate_time())));
		if (TextUtils.isEmpty(mBean.getName())) {
			mForm.setVisibility(View.GONE);
		} else {
			mForm.setVisibility(View.VISIBLE);
			mForm.setText("来自" + mBean.getName());
		}
		mForm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Log.e(Tag, "点击来自xxx---type--->" + mViewHolder.mFrom.getText().toString() + ",---id--->" + mBean.getId());
				if (mBean.getGid()!=null||mBean.getGid()!="") {
					//跳转到群资料
					Intent mInt = new Intent(getActivity(), GeneratedClassUtils.get(GroupDataAct.class));
					mInt.putExtra(GroupDataAct.ACTIVITYTAG, "Detils");
					mInt.putExtra(GroupDataAct.INTENTTAG, mBean.getGid() + "");
					getActivity().startActivity(mInt);

				}
			}
		});
		SpannableString spanString = new SpannableString("     " + mBean.getTitle());
		spanString.setSpan(new ForegroundColorSpan(Color.RED), 5, 5 + mBean.getTitle().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mContent.setText(spanString);
		mContent.append("  " + mBean.getContent());
		mAdapter.AddNewData(mBean.getReplies());
		Log.e(Tag,"mBean.getLike()--->"+mBean.getLike());
		if (mBean.getLike()>0) {
			mPicLayout.setVisibility(View.VISIBLE);

//		if (mBean.getPraises().size()>0) {
			//        	mAssist.setChecked(true);
//			mAssist.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_zan2));
			//        	mAssist.setImageResource(R.mipmap.tag_community_item_assist_h);
		}else {
			mPicLayout.setVisibility(View.GONE);
//			layout_Assist.setVisibility(View.GONE);
//			mAssist.setVisibility(View.GONE);


			//			mAssist.setChecked(false);
			//			mAssist.setImageDrawable(getResources().getDrawable(R.drawable.tag_community_item_assist));
			//			mAssist.setImageResource(R.mipmap.tag_community_item_assist);;
		}


		//        mAssist.setText("赞:" + mBean.getLike());
		mAssisGrid.setAdapter(mAssistAdapter);
		if (mBean.getPraises() != null && !mBean.getPraises().equals("null") && mBean.getPraises().get(0) != null) {
			mAssistAdapter.AddNewData(mBean.getPraises());
			HighCommunityUtils.GetInstantiation().setGridViewHeightBasedOnChildren(mAssisGrid, mAssistAdapter, 6);
		}
		Log.e(Tag,"getSite"+mBean.getSite());
		if (mBean.getSite()!=null&&!mBean.getSite().equals("null")&&!mBean.getSite().equals("")){
			Log.e(Tag,"getSite"+1);

			mLocation.setText(mBean.getSite());
		}else if (mBean.getVillage_name()!=null&&!mBean.getVillage_name().equals("null")&&!mBean.getVillage_name().equals("")){
			Log.e(Tag,"getSite"+2);

			mLocation.setText(mBean.getVillage_name());

		}else{
			Log.e(Tag,"getSite"+3);

			mLocation.setVisibility(View.GONE);
		}

		mImageAdapter.AddNewData(mBean.getPic());
		HighCommunityUtils.GetInstantiation().setGridViewHeightBasedOnChildren(mContentGrid, mImageAdapter, 3);
		mList.setSelectionAfterHeaderView();
	}

	BpiHttpHandler.IBpiHttpHandler mCommentIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			if (null != mWindow && mWindow.isShowing())
				mWindow.dismiss();
			if (null != mWatingWindow && mWatingWindow.isShowing())
				mWatingWindow.dismiss();
			HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public void onSuccess(Object message) {
			if (null != mWindow && mWindow.isShowing())
				mWindow.dismiss();
			if (null != mWatingWindow && mWatingWindow.isShowing())
				mWatingWindow.dismiss();
			if (isReplay) {
				mAdapter.setNewData(isReplay, content, null);
			} else {
				mAdapter.setNewData(isReplay, content, message.toString());
			}
			initSpoker();
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
			if (null != mWindow && mWindow.isShowing())
				mWindow.dismiss();
			if (null != mWatingWindow && mWatingWindow.isShowing())
				mWatingWindow.dismiss();
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWindow != null && mWindow.isShowing()) {
				mWindow.dismiss();
				return true;
			} else if (mWatingWindow != null && mWatingWindow.isShowing()) {
				mWatingWindow.dismiss();
				return true;
			} else {
				Intent result = new Intent();
				result.putExtra("PinLun", mPraisesNum);
				getActivity().setResult(getActivity().RESULT_OK, result);
			}
		}
		return false;
	}

	private void initSpoker() {
		CommunityFrag.isNeedRefresh = true;
		this.isReplay = false;
		mSpeakerContent.setHint("");
		toid = "";
		ParentId = "";
		mSpeakerContent.setText("");
	}

	public void finish() {
		Intent result = new Intent();
		result.putExtra("PinLun", mPraisesNum);
		getActivity().setResult(getActivity().RESULT_OK, result);
	}
}
