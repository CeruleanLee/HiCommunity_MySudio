package cn.hi028.android.highcommunity.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpClient;
import com.don.tools.BpiHttpHandler;
import com.don.tools.DongUtils;
import com.don.tools.FileDownloadThread;
import com.don.tools.GeneratedClassUtils;
import com.don.tools.TimeFormat;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.PersistentCookieStore;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import org.apache.http.cookie.Cookie;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.CommunityDetailAct;
import cn.hi028.android.highcommunity.activity.WelcomeAct;
import cn.hi028.android.highcommunity.adapter.BaseFragmentAdapter;
import cn.hi028.android.highcommunity.bean.OperateBean;
import cn.hi028.android.highcommunity.view.ECAlertDialog;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import photo.util.Bimp;
import photo.util.ImageItem;
import photo.util.PublicWay;

/**
 * @author dong
 * @category dong工具类
 */
public class HighCommunityUtils extends DongUtils {
	public static HighCommunityUtils mDongUtils = null;
	public static final String DATA_DIRECTORY = "/sdcard/HighCommunity";

	/**
	 * @param context
	 *            上下文对象
	 * @param photo
	 *            选择照相监听
	 * @param file
	 *            选择图片选择
	 * @return
	 */
	public PopupWindow ShowPhotoPopupWindow(Context context,
			View.OnClickListener photo, View.OnClickListener file) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.photo_popupwindow, null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.MATCH_PARENT,
				HighCommunityUtils.DisplayMetricsHeight
						- HighCommunityUtils.GetInstantiation().dip2px(63));
		TextView mPhoto = (TextView) view
				.findViewById(R.id.PhotoPopupWindow_photo);
		TextView mFile = (TextView) view
				.findViewById(R.id.PhotoPopupWindow_file);
		TextView mCancal = (TextView) view
				.findViewById(R.id.PhotoPopupWindow_cancal);
		View mOut = view.findViewById(R.id.PhotoPopupWindow_OutView);
		mCancal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		mOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		mPhoto.setOnClickListener(photo);
		mFile.setOnClickListener(file);
		mPhotoPopupWindow.setOutsideTouchable(true);
		return mPhotoPopupWindow;
	}

	/**
	 * show Popupwindow to select take photo or get picture or select Expression
	 *
	 * @param context
	 * @param mBack
	 * @return
	 */
	public PopupWindow ShowInput(Context context, View parent,
			final InputCallBack mBack, String hint) {
		View view = LayoutInflater.from(context).inflate(R.layout.photo_input,
				null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);// HighCommunityUtils.DisplayMetricsHeight
														// -
		View mOut = view.findViewById(R.id.tv_photoInput_outView);
		ImageView mImage = (ImageView) view
				.findViewById(R.id.civ_input_spokerImage);
		final EditText mContent = (EditText) view
				.findViewById(R.id.ev_input_spokerContent);
		ImageView submit = (ImageView) view
				.findViewById(R.id.iv_input_spokerButton);
		mContent.requestFocus();
		mContent.setHint(hint);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TextUtils.isEmpty(mContent.getText().toString())) {
					HighCommunityUtils.GetInstantiation().ShowToast("评论内容不能为空",
							0);
					return;
				}
				mBack.onInput(mContent.getText().toString());
				mPhotoPopupWindow.dismiss();
			}
		});
		mPhotoPopupWindow.setFocusable(true);
		mPhotoPopupWindow.setOutsideTouchable(false);
		mPhotoPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPhotoPopupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mPhotoPopupWindow
				.setOnDismissListener(new PopupWindow.OnDismissListener() {
					@Override
					public void onDismiss() {
					}
				});
		mPhotoPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				return false;
			}
		});
		mPhotoPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		mPhotoPopupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getHead_pic())) {
			ImageLoaderUtil.disPlay("drawable://"
					+ R.mipmap.img_commnity_defult_pic, mImage);
		} else {
			ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP
					+ HighCommunityApplication.mUserInfo.getHead_pic(), mImage);
		}
		mOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getX() < 0
						|| motionEvent.getX() > view.getWidth()
						|| motionEvent.getY() < 0
						|| motionEvent.getY() > view.getHeight()) {
					mPhotoPopupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		mPhotoPopupWindow.setOutsideTouchable(true);
		mPhotoPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0,
				HighCommunityApplication.SoftKeyHight);
		return mPhotoPopupWindow;
	}

	public interface InputCallBack {
		public void onInput(String input);
	}

	public void popupInputMethodWindow(final View mContent) {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = null;
				imm = (InputMethodManager) mContent.getContext()
						.getSystemService(Service.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 0);
	}

	/**
	 * show Popupwindow to show a big photo
	 *
	 * @param context
	 * @param url
	 * @return
	 */
	public PopupWindow ShowPhoto(Context context, String url) {
		View view = LayoutInflater.from(context).inflate(R.layout.photo_detils,
				null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		ImageView expresstion = (ImageView) view
				.findViewById(R.id.big_photo_detils);
		expresstion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		ImageLoaderUtil.disPlay(url, expresstion);
		mPhotoPopupWindow.setOutsideTouchable(true);
		return mPhotoPopupWindow;
	}

	/**
	 * show Popupwindow to show a big photo
	 *
	 * @param context
	 * @param mBean
	 * @param mid
	 * @return
	 */
	public PopupWindow ShowCommunityShare(final BaseFragmentActivity context,
			final OperateBean mBean, final int mid, final View mView,
			final OnDeleteClick mDelete) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.pupopwindow_community, null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.MATCH_PARENT,
				HighCommunityUtils.DisplayMetricsHeight
						- HighCommunityUtils.GetInstantiation().dip2px(63));
		LinearLayout weibo = (LinearLayout) view
				.findViewById(R.id.ll_community_share_weibo);
		LinearLayout qq = (LinearLayout) view
				.findViewById(R.id.ll_community_share_qq);
		LinearLayout weoxin = (LinearLayout) view
				.findViewById(R.id.ll_community_share_weixin);
		TextView delete = (TextView) view
				.findViewById(R.id.tv_community_share_delete);
		final TextView collection = (TextView) view
				.findViewById(R.id.tv_community_share_collection);
		TextView report = (TextView) view
				.findViewById(R.id.tv_community_share_report);
		TextView cancle = (TextView) view
				.findViewById(R.id.tv_community_share_cancle);
		View out = view.findViewById(R.id.v_community_out_space);
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if (i == KeyEvent.KEYCODE_BACK) {
					mPhotoPopupWindow.dismiss();
					System.out.println("hahahahha----------nimabi");
					return true;
				} else {
					return false;
				}
			}
		});
		if (mBean.getDelete().equals("1")) {
			delete.setVisibility(View.VISIBLE);
		} else {
			delete.setVisibility(View.GONE);
		}
		if (mBean.getOperate().equals("1")) {
			collection.setText("取消收藏");
		} else {
			collection.setText("收藏");
		}
		if (mBean.getReport().equals("1")) {
			report.setText("已举报");
		} else {
			report.setText("举报");
		}
		weibo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShareComm(context, SinaWeibo.NAME,
						"http://028hi.cn/api/message/share.html?mid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		qq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShareComm(context, QQ.NAME,
						"http://028hi.cn/api/message/share.html?mid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		weoxin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShareComm(context, Wechat.NAME,
						"http://028hi.cn/api/message/share.html?mid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final PopupWindow mWaitingWindow = HighCommunityUtils
						.GetInstantiation().ShowWaittingPopupWindow(context,
								mView, Gravity.CENTER);
				HTTPHelper.DeleteMsg(new BpiHttpHandler.IBpiHttpHandler() {
					@Override
					public void onError(int id, String message) {
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
						HighCommunityUtils.GetInstantiation().ShowToast(
								message, 0);
					}

					@Override
					public void onSuccess(Object message) {
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
						mPhotoPopupWindow.dismiss();
						HighCommunityUtils.GetInstantiation().ShowToast(
								message.toString(), 0);
						mDelete.OnClick();
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
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
					}
				}, mid + "");
			}
		});
		collection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final PopupWindow mWaitingWindow = HighCommunityUtils
						.GetInstantiation().ShowWaittingPopupWindow(context,
								mView, Gravity.CENTER);
				if (mBean.getOperate().equals("1")) {
					HTTPHelper.CancelFavorite(
							new BpiHttpHandler.IBpiHttpHandler() {
								@Override
								public void onError(int id, String message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message, 0);
								}

								@Override
								public void onSuccess(Object message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									collection.setText("收藏");
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message.toString(), 0);
									mPhotoPopupWindow.dismiss();
									mBean.setOperate("0");
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
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
								}
							}, HighCommunityApplication.mUserInfo.getId() + "",
							mid + "");
				} else {
					HTTPHelper.Favorite(new BpiHttpHandler.IBpiHttpHandler() {
						@Override
						public void onError(int id, String message) {
							if (null != mWaitingWindow
									&& mWaitingWindow.isShowing())
								mWaitingWindow.dismiss();
							HighCommunityUtils.GetInstantiation().ShowToast(
									message, 0);
						}

						@Override
						public void onSuccess(Object message) {
							if (null != mWaitingWindow
									&& mWaitingWindow.isShowing())
								mWaitingWindow.dismiss();
							HighCommunityUtils.GetInstantiation().ShowToast(
									message.toString(), 0);
							mBean.setOperate("1");
							collection.setText("取消收藏");
							mPhotoPopupWindow.dismiss();
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
							if (null != mWaitingWindow
									&& mWaitingWindow.isShowing())
								mWaitingWindow.dismiss();
						}
					}, HighCommunityApplication.mUserInfo.getId() + "", mid
							+ "");
				}
			}
		});
		report.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mBean.getReport().equals("1")) {
					HighCommunityUtils.GetInstantiation().ShowToast("已举报", 0);
				} else {
					Intent mReport = new Intent(context, GeneratedClassUtils
							.get(CommunityDetailAct.class));
					mReport.putExtra(CommunityDetailAct.ACTIVITYTAG, "report");
					mReport.putExtra(CommunityDetailAct.INTENTTAG, mid + "");
					context.startActivity(mReport);
					mPhotoPopupWindow.dismiss();
				}
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		out.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		return mPhotoPopupWindow;
	}

	/**
	 * show Popupwindow to show a big photo
	 *
	 * @param context
	 * @param mBean
	 * @param mid
	 * @return
	 */
	public PopupWindow ShowMyCollectionShare(
			final BaseFragmentActivity context, final OperateBean mBean,
			final int mid, final View mView, final CancalFravite mCancal,
			final OnDeleteClick mDelete) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.pupopwindow_community, null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.MATCH_PARENT,
				HighCommunityUtils.DisplayMetricsHeight
						- HighCommunityUtils.GetInstantiation().dip2px(63));
		LinearLayout weibo = (LinearLayout) view
				.findViewById(R.id.ll_community_share_weibo);
		LinearLayout qq = (LinearLayout) view
				.findViewById(R.id.ll_community_share_qq);
		LinearLayout weoxin = (LinearLayout) view
				.findViewById(R.id.ll_community_share_weixin);
		TextView delete = (TextView) view
				.findViewById(R.id.tv_community_share_delete);
		final TextView collection = (TextView) view
				.findViewById(R.id.tv_community_share_collection);
		TextView report = (TextView) view
				.findViewById(R.id.tv_community_share_report);
		TextView cancle = (TextView) view
				.findViewById(R.id.tv_community_share_cancle);
		View out = view.findViewById(R.id.v_community_out_space);
		if (mBean.getDelete().equals("1")) {
			delete.setVisibility(View.VISIBLE);
		} else {
			delete.setVisibility(View.GONE);
		}
		if (mBean.getOperate().equals("1")) {
			collection.setText("取消收藏");
		} else {
			collection.setText("收藏");
		}
		if (mBean.getReport().equals("1")) {
			report.setText("已举报");
		} else {
			report.setText("举报");
		}
		weibo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShareComm(context, SinaWeibo.NAME,
						"http://028hi.cn/api/message/share.html?mid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		qq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShareComm(context, QQ.NAME,
						"http://028hi.cn/api/message/share.html?mid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		weoxin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShareComm(context, Wechat.NAME,
						"http://028hi.cn/api/message/share.html?mid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final PopupWindow mWaitingWindow = HighCommunityUtils
						.GetInstantiation().ShowWaittingPopupWindow(context,
								mView, Gravity.CENTER);
				HTTPHelper.DeleteMsg(new BpiHttpHandler.IBpiHttpHandler() {
					@Override
					public void onError(int id, String message) {
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
						HighCommunityUtils.GetInstantiation().ShowToast(
								message, 0);
					}

					@Override
					public void onSuccess(Object message) {
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
						mPhotoPopupWindow.dismiss();
						HighCommunityUtils.GetInstantiation().ShowToast(
								message.toString(), 0);
						mDelete.OnClick();
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
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
					}
				}, mid + "");
			}
		});
		collection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final PopupWindow mWaitingWindow = HighCommunityUtils
						.GetInstantiation().ShowWaittingPopupWindow(context,
								mView, Gravity.CENTER);
				if (mBean.getOperate().equals("1")) {
					HTTPHelper.CancelFavorite(
							new BpiHttpHandler.IBpiHttpHandler() {
								@Override
								public void onError(int id, String message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message, 0);
								}

								@Override
								public void onSuccess(Object message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									collection.setText("收藏");
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message.toString(), 0);
									mPhotoPopupWindow.dismiss();
									mBean.setOperate("0");
									mCancal.OnCancal();
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
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
								}
							}, HighCommunityApplication.mUserInfo.getId() + "",
							mid + "");
				} else {
					HTTPHelper.Favorite(new BpiHttpHandler.IBpiHttpHandler() {
						@Override
						public void onError(int id, String message) {
							if (null != mWaitingWindow
									&& mWaitingWindow.isShowing())
								mWaitingWindow.dismiss();
							HighCommunityUtils.GetInstantiation().ShowToast(
									message, 0);
						}

						@Override
						public void onSuccess(Object message) {
							if (null != mWaitingWindow
									&& mWaitingWindow.isShowing())
								mWaitingWindow.dismiss();
							HighCommunityUtils.GetInstantiation().ShowToast(
									message.toString(), 0);
							mBean.setOperate("1");
							collection.setText("取消收藏");
							mPhotoPopupWindow.dismiss();
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
							if (null != mWaitingWindow
									&& mWaitingWindow.isShowing())
								mWaitingWindow.dismiss();
						}
					}, HighCommunityApplication.mUserInfo.getId() + "", mid
							+ "");
				}
			}
		});
		report.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mBean.getReport().equals("1")) {
					HighCommunityUtils.GetInstantiation().ShowToast("已举报", 0);
				} else {
					Intent mReport = new Intent(context, GeneratedClassUtils
							.get(CommunityDetailAct.class));
					mReport.putExtra(CommunityDetailAct.ACTIVITYTAG, "report");
					mReport.putExtra(CommunityDetailAct.INTENTTAG, mid + "");
					context.startActivity(mReport);
					mPhotoPopupWindow.dismiss();
				}
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		out.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		return mPhotoPopupWindow;
	}

	public interface CancalFravite {
		public void OnCancal();
	}

	/**
	 * show Popupwindow show activity share
	 *
	 * @param context
	 * @param mBean
	 * @param mid
	 * @param mView
	 * @param mDelete
	 * @return
	 */
	public PopupWindow ShowActivityShare(final Activity context,
			final OperateBean mBean, final String mid, final View mView,
			final OnDeleteClick mDelete) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.pupopwindow_activity, null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.MATCH_PARENT,
				HighCommunityUtils.DisplayMetricsHeight
						- HighCommunityUtils.GetInstantiation().dip2px(63));
		LinearLayout weibo = (LinearLayout) view
				.findViewById(R.id.ll_activity_share_weibo);
		LinearLayout qq = (LinearLayout) view
				.findViewById(R.id.ll_activity_share_qq);
		LinearLayout weoxin = (LinearLayout) view
				.findViewById(R.id.ll_activity_share_weixin);
		TextView delete = (TextView) view
				.findViewById(R.id.tv_activity_share_delete);
		final TextView collection = (TextView) view
				.findViewById(R.id.tv_activity_share_collection);
		TextView cancle = (TextView) view
				.findViewById(R.id.tv_activity_share_cancle);
		View out = view.findViewById(R.id.v_activity_out_space);
		if (mBean.getDelete().equals("1")) {
			delete.setVisibility(View.VISIBLE);
		} else {
			delete.setVisibility(View.GONE);
		}
		if (mBean.getOperate().equals("1")) {
			collection.setText("取消收藏");
		} else {
			collection.setText("收藏");
		}
		weibo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShare(context, SinaWeibo.NAME,
						"http://028hi.cn/api/activity/share.html?aid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		qq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShare(context, QQ.NAME,
						"http://028hi.cn/api/activity/share.html?aid=" + mid);
				mPhotoPopupWindow.dismiss();
			}
		});
		weoxin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShare(context, Wechat.NAME,
						"http://028hi.cn/api/activity/share.html?aid=" + mid);
				mPhotoPopupWindow.dismiss();

			}
		});
		collection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final PopupWindow mWaitingWindow = HighCommunityUtils
						.GetInstantiation().ShowWaittingPopupWindow(context,
								mView, Gravity.CENTER);
				if (mBean.getOperate().equals("1")) {
					HTTPHelper.CancelActivityCollection(
							new BpiHttpHandler.IBpiHttpHandler() {
								@Override
								public void onError(int id, String message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message, 0);
								}

								@Override
								public void onSuccess(Object message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									mBean.setOperate("0");
									collection.setText("收藏");
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message.toString(), 0);
									mPhotoPopupWindow.dismiss();
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
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
								}
							}, HighCommunityApplication.mUserInfo.getId() + "",
							mid + "");
				} else {
					HTTPHelper.FavoriteActivity(
							new BpiHttpHandler.IBpiHttpHandler() {
								@Override
								public void onError(int id, String message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message, 0);
								}

								@Override
								public void onSuccess(Object message) {
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
									HighCommunityUtils.GetInstantiation()
											.ShowToast(message.toString(), 0);
									mBean.setOperate("1");
									collection.setText("取消收藏");
									mPhotoPopupWindow.dismiss();
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
									if (null != mWaitingWindow
											&& mWaitingWindow.isShowing())
										mWaitingWindow.dismiss();
								}
							}, HighCommunityApplication.mUserInfo.getId() + "",
							mid + "");
				}
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final PopupWindow mWaitingWindow = HighCommunityUtils
						.GetInstantiation().ShowWaittingPopupWindow(context,
								mView, Gravity.CENTER);
				HTTPHelper.DeleteAct(new BpiHttpHandler.IBpiHttpHandler() {
					@Override
					public void onError(int id, String message) {
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
						HighCommunityUtils.GetInstantiation().ShowToast(
								message, 0);
					}

					@Override
					public void onSuccess(Object message) {
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
						mPhotoPopupWindow.dismiss();
						HighCommunityUtils.GetInstantiation().ShowToast(
								message.toString(), 0);
						mDelete.OnClick();
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
						if (null != mWaitingWindow
								&& mWaitingWindow.isShowing())
							mWaitingWindow.dismiss();
					}
				}, mid + "");
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		out.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		return mPhotoPopupWindow;
	}

	/**
	 * show Popupwindow show group share
	 *
	 * @param context
	 * @param url
	 * @return
	 */
	public PopupWindow ShowGroupShare(final Activity context, final String url) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.pupopwindow_community, null, false);
		final PopupWindow mPhotoPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.MATCH_PARENT,
				HighCommunityUtils.DisplayMetricsHeight
						- HighCommunityUtils.GetInstantiation().dip2px(63));
		LinearLayout weibo = (LinearLayout) view
				.findViewById(R.id.ll_community_share_weibo);
		LinearLayout qq = (LinearLayout) view
				.findViewById(R.id.ll_community_share_qq);
		LinearLayout weoxin = (LinearLayout) view
				.findViewById(R.id.ll_community_share_weixin);
		TextView delete = (TextView) view
				.findViewById(R.id.tv_community_share_delete);
		TextView collection = (TextView) view
				.findViewById(R.id.tv_community_share_collection);
		TextView report = (TextView) view
				.findViewById(R.id.tv_community_share_report);
		TextView cancle = (TextView) view
				.findViewById(R.id.tv_community_share_cancle);
		View out = view.findViewById(R.id.v_community_out_space);
		delete.setVisibility(View.GONE);
		collection.setVisibility(View.GONE);
		report.setVisibility(View.GONE);
		weibo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShare(context, SinaWeibo.NAME, url);
				mPhotoPopupWindow.dismiss();
			}
		});
		qq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShare(context, QQ.NAME, url);
				mPhotoPopupWindow.dismiss();
			}
		});
		weoxin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShareUtils.showShare(context, Wechat.NAME, url);
				mPhotoPopupWindow.dismiss();

			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		out.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPhotoPopupWindow.dismiss();
			}
		});
		return mPhotoPopupWindow;
	}

	public interface OnDeleteClick {
		void OnClick();
	}

	int musicID = -1;
	float audioCurrentVolumn = 0;
	SoundPool sp = null;
	Vibrator mVibrator = null;

	/**
	 * @param context
	 * @author dong
	 * @category 初始化声音振动
	 */
	public void initSoundVibrator(Context context) {
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		musicID = sp.load(context, R.raw.remind, 1);
		AudioManager am = (AudioManager) context
				.getSystemService(context.AUDIO_SERVICE);
		audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		mVibrator = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
	}

	public static HighCommunityUtils GetInstantiation() {
		if (mDongUtils == null) {
			mDongUtils = new HighCommunityUtils();
			mNowZero = TimeFormat.TimedateFormat_DATEParse(TimeFormat
					.getTheTime());
		}
		return mDongUtils;
	}

	// 取消通知
	public void cancelNotification(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		// notificationManager.cancel(1);
	}

	public void download(String url, String tempName) {
		// 获取SD卡目录
		String dowloadDir = Environment.getExternalStorageDirectory()
				+ "/EducationTemp/";
		File file = new File(dowloadDir);
		// 创建下载目录
		if (!file.exists()) {
			file.mkdirs();
		}

		String chat_id = tempName.substring(0, tempName.indexOf("_"));
		// 启动文件下载线程
		File tempfile = new File(dowloadDir + tempName);
		if (!tempfile.exists()) {
			new downloadTask(url, chat_id, dowloadDir + tempName).start();
		}
	}

	public class downloadTask extends Thread {
		private int blockSize, downloadSizeMore;
		private int threadNum = 1;
		String urlStr, threadNo, fileName;
		String Chat_id;

		public downloadTask(String urlStr, String Chat_id, String fileName) {
			this.urlStr = urlStr;
			this.fileName = fileName;
			this.Chat_id = Chat_id;
		}

		@Override
		public void run() {
			FileDownloadThread[] fds = new FileDownloadThread[threadNum];
			String kookie = null;
			try {
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				PersistentCookieStore mPersistentCookieStore = BpiHttpClient.mPersistentCookieStore;
				if (mPersistentCookieStore != null) {
					StringBuffer stringbuffer = new StringBuffer();
					for (int i = 0; i < mPersistentCookieStore.getCookies()
							.size(); i++) {
						Cookie cookie = mPersistentCookieStore.getCookies()
								.get(i);
						if (!stringbuffer.toString().equals("")) {
							stringbuffer.append(";");
						}
						stringbuffer.append(cookie.getName() + "="
								+ cookie.getValue());
					}
					kookie = stringbuffer.toString();
					conn.setRequestProperty("Cookie", stringbuffer.toString());
				}
				InputStream in = conn.getInputStream();
				int fileSize = conn.getContentLength();
				blockSize = fileSize / threadNum;
				downloadSizeMore = (fileSize % threadNum);
				File file = new File(fileName);
				for (int i = 0; i < threadNum; i++) {
					FileDownloadThread fdt = new FileDownloadThread(url, file,
							i * blockSize, (i + 1) * blockSize - 1, kookie);
					fdt.setName("Thread" + i);
					fdt.start();
					fds[i] = fdt;
				}
				boolean finished = false;
				while (!finished) {
					int downloadedSize = downloadSizeMore;
					finished = true;
					for (int i = 0; i < fds.length; i++) {
						downloadedSize += fds[i].getDownloadSize();
						if (!fds[i].isFinished()) {
							finished = false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/*
	 * 动态设置ListView组件的高度
	 */
	public void setListViewHeightBasedOnChildren(
			PullToRefreshListView listView, BaseFragmentAdapter mAdapter) {
		// ListAdapter listAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (1 * (mAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);

	}

	/*
	 * 动态设置第三方服务GridView高度
	 */
	public void setThirdServiceGridViewHeight(PullToRefreshGridView listView,
			BaseFragmentAdapter mAdapter, int ColumnNum) {
		// ListAdapter listAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		if (mAdapter.getCount() == 0) {
			params.height = 0;
			listView.setLayoutParams(params);
			return;
		}
		int totalHeight = 0;
		int mCount = 0;
		if (mAdapter.getCount() <= ColumnNum) {
			mCount = 1;
		} else if (mAdapter.getCount() % ColumnNum == 0) {
			mCount = mAdapter.getCount() / ColumnNum;
		} else {
			mCount = mAdapter.getCount() / ColumnNum + 1;
		}
		for (int i = 0; i < mCount; i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		params.height = totalHeight + (0 * (mAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/*
	 * 动态设置Gridview组件的高度
	 */
	public void setGridViewHeightBasedOnChildren(
			PullToRefreshGridView listView, BaseFragmentAdapter mAdapter,
			int ColumnNum) {
		// ListAdapter listAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		if (mAdapter.getCount() == 0) {
			params.height = 0;
			listView.setLayoutParams(params);
			return;
		}
		int totalHeight = 0;
		int mCount = 0;
		if (mAdapter.getCount() <= ColumnNum) {
			mCount = 1;
		} else if (mAdapter.getCount() % ColumnNum == 0) {
			mCount = mAdapter.getCount() / ColumnNum;
		} else {
			mCount = mAdapter.getCount() / ColumnNum + 1;
		}
		for (int i = 0; i < mCount; i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		params.height = totalHeight + (4 * (mAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * 初始化静态图片数组，并加入第一张图片
	 *
	 * @param num
	 */
	public static void InitPicList(int num) {
		// 清除之前的数据
		PublicWay.num = num;
		Bimp.tempSelectBitmap.clear();
		ImageItem mItem = new ImageItem();
		mItem.setImagePath("drawable://" + R.mipmap.img_upload_addpic);
		Bimp.tempSelectBitmap.add(mItem);
	}

	/**
	 * 初始化静态图片数组，并加入第一张图片
	 *发帖界面添加图片
	 * @param num
	 */
	public static void InitLabelList(int num) {
		// 清除之前的数据
		PublicWay.num = num;
		Bimp.tempSelectBitmap.clear();
		ImageItem mItem = new ImageItem();
		mItem.setImagePath("drawable://" + R.mipmap.img_upload_addpic_white);
//		mItem.setImagePath("drawable://" + R.mipmap.img_upload_addpic);
		Bimp.tempSelectBitmap.add(mItem);
	}

	/**
	 * 打電話
	 *
	 * @param context
	 * @param phone
	 *            电话号码不能为空
	 */
	public static void callPhone(Context context, String phone) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri data = Uri.parse("tel:" + phone);
		intent.setData(data);
		context.startActivity(intent);
	}

	/**
	 * 拨打电话
	 *
	 * @param context
	 * @param phone
	 */
	public static void callDialogPhone(final Context context, final String phone) {
		ECAlertDialog dialog = ECAlertDialog.buildAlert(context, phone, "拨打",
				"取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callPhone(context, phone);
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		dialog.show();
	}

	/**
	 * 判断用户是否登录
	 *
	 * @return
	 */
	public static boolean isLogin() {
		if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isLogin(final Context context) {
		if (TextUtils.isEmpty(HighCommunityApplication.mUserInfo.getToken())) {
			ECAlertDialog dialog = ECAlertDialog.buildAlert(context,
					"你还未登录，确定登录么？", "登录", "取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(context,
									WelcomeAct.class);
							HighCommunityApplication.isLogOut = true;
							context.startActivity(intent);
						}
					}, null);
			dialog.setTitle("登录提示");
			dialog.show();
			return false;
		} else {
			return true;
		}
	}

	public static void LogOut(Object obj) {
		StackTraceElement throwable = new Throwable().getStackTrace()[1];
		Log.e("renk",
				throwable.getClassName() + " line:" + throwable.getLineNumber());
		if (obj != null)
			Log.e("renk", obj.toString());
		else
			Log.e("renk", "打印为空！");
	}
}
