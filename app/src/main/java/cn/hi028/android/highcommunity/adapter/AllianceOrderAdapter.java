package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ShowPayActivity;
import cn.hi028.android.highcommunity.bean.AllianceOrderBean;
import cn.hi028.android.highcommunity.bean.AlliancePayBean;
import cn.hi028.android.highcommunity.lisenter.EvaluateInterface;
import cn.hi028.android.highcommunity.lisenter.OnRefreshListener;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.AllianceItemView;

/**
 * Created by Administrator on 2016/8/10.
 * 
 */
public class AllianceOrderAdapter extends BaseFragmentAdapter {

	public Context mContext;
	List<AllianceOrderBean> mList = new ArrayList<AllianceOrderBean>();
	private int mWhichTab = -1;
	private OnRefreshListener mListener;
	private EvaluateInterface face;

	public AllianceOrderAdapter(Context context, int tab,
			OnRefreshListener listener, EvaluateInterface face) {
		this.mContext = context;
		this.mWhichTab = tab;
		this.mListener = listener;
		this.face = face;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public AllianceOrderBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_alliance_order, null);
			mViewHolder.mAllianceStoreName = (TextView) convertView.findViewById(R.id.alliance_store_name);
			mViewHolder.mAllianceOrderStatus = (TextView) convertView.findViewById(R.id.alliance_order_status);
			mViewHolder.mContainer = (LinearLayout) convertView.findViewById(R.id.container);
			mViewHolder.mAllianceOrderCounts = (TextView) convertView.findViewById(R.id.alliance_order_counts);
			mViewHolder.mAllianceOrderPrice = (TextView) convertView.findViewById(R.id.alliance_order_price);
			mViewHolder.mAllianceOrderNumber = (TextView) convertView.findViewById(R.id.alliance_order_number);
			mViewHolder.mBtnCancleAction = (TextView) convertView.findViewById(R.id.btn_cancle_action);
			mViewHolder.mBtnAction = (TextView) convertView.findViewById(R.id.btn_action);
//支付前的状态
			mViewHolder.mForActionBefore =  (RelativeLayout) convertView.findViewById(R.id.for_action_status_before);
			//支付后的状态
			mViewHolder.mForActionAfter = (LinearLayout) convertView.findViewById(R.id.for_action_status_after);
			
			//取消订单 确认收货的容器
			mViewHolder.mActionBtnLayout = (LinearLayout) convertView.findViewById(R.id.action_btn_layout);
			mViewHolder.mAllianceOrderCounts2 = (TextView) convertView.findViewById(R.id.alliance_order_counts2);
			mViewHolder.mAllianceOrderPrice2 = (TextView) convertView.findViewById(R.id.alliance_order_price2);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		final AllianceOrderBean mBean = mList.get(position);
		if (!TextUtils.isEmpty(mBean.getShop_name())) {
			mViewHolder.mAllianceStoreName.setText(mBean.getShop_name());
		}
		if (!TextUtils.isEmpty(mBean.getStatus())) {
			mViewHolder.mAllianceOrderStatus.setText(mBean.getStatus());
			switchStatus(mViewHolder, mBean, position);
		}
		if (!TextUtils.isEmpty(mBean.getOrder_num())) {
			mViewHolder.mAllianceOrderNumber.setText(String.format(
					mContext.getString(R.string.str_format_item_order_number),
					mBean.getOrder_num()));
		}
		List<AllianceOrderBean.AllianceOrderGoodsInfoBean> list = mBean.getGoods_info();
		//item单独加载
		if (list != null && list.size() > 0) {
			mViewHolder.mContainer.setVisibility(View.VISIBLE);
			mViewHolder.mContainer.removeAllViewsInLayout();
			for (AllianceOrderBean.AllianceOrderGoodsInfoBean bean : list) {
				mViewHolder.mContainer.setOrientation(LinearLayout.VERTICAL);
				AllianceItemView view = new AllianceItemView(mContext);
				view.onBindData(bean);
				mViewHolder.mContainer.addView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
			}
		}
		return convertView;
	}

	private void switchStatus(ViewHolder mViewHolder,
			final AllianceOrderBean mBean, final int position) {
		String statuStr = mBean.getStatus();
		if (statuStr.equals("待付款")) {
			mViewHolder.mForActionBefore.setVisibility(View.VISIBLE);
			mViewHolder.mActionBtnLayout.setVisibility(View.VISIBLE);
			mViewHolder.mForActionAfter.setVisibility(View.GONE);
			if (mBean.getTotal_price() > 0) {
				mViewHolder.mAllianceOrderPrice.setText(String.format(mContext
						.getString(R.string.str_format_itme_total_prices),
						mBean.getTotal_price()));
			}
			mViewHolder.mBtnCancleAction.setVisibility(View.VISIBLE);
			mViewHolder.mBtnCancleAction.setText("取消订单");
			mViewHolder.mBtnCancleAction
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							HTTPHelper.CancelOrder(mIbpiCancel,
									mBean.getOrder_num());
						}
					});
			mViewHolder.mBtnAction.setVisibility(View.VISIBLE);
			mViewHolder.mBtnAction.setText("付款");
			mViewHolder.mBtnAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HTTPHelper.GoPay(mIbpiGoPay, mBean.getOrder_num());
				}
			});
		} else if (statuStr.equals("已付款")) {
			mViewHolder.mForActionBefore.setVisibility(View.GONE);
			mViewHolder.mActionBtnLayout.setVisibility(View.GONE);
			mViewHolder.mForActionAfter.setVisibility(View.VISIBLE);
			if (mBean.getTotal_price() > 0) {
				mViewHolder.mAllianceOrderPrice2.setText(String.format(mContext
						.getString(R.string.str_format_itme_total_prices),
						mBean.getTotal_price()));
			}
		} else if (statuStr.equals("待收货")) {
			mViewHolder.mForActionBefore.setVisibility(View.VISIBLE);
			mViewHolder.mActionBtnLayout.setVisibility(View.VISIBLE);
			mViewHolder.mForActionAfter.setVisibility(View.GONE);
			if (mBean.getTotal_price() > 0) {
				mViewHolder.mAllianceOrderPrice.setText(String.format(mContext
						.getString(R.string.str_format_itme_total_prices),
						mBean.getTotal_price()));
			}
			mViewHolder.mBtnCancleAction.setVisibility(View.GONE);
			mViewHolder.mBtnAction.setVisibility(View.VISIBLE);
			mViewHolder.mBtnAction.setText("确认收货");
			mViewHolder.mBtnAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HTTPHelper.ConfirmOrder(mIbpiConfirm, mBean.getOrder_num());
					notifyDataSetChanged();
				}
			});
		} else if (statuStr.equals("已收货")) {
			mViewHolder.mForActionBefore.setVisibility(View.GONE);
			mViewHolder.mActionBtnLayout.setVisibility(View.GONE);
			mViewHolder.mForActionAfter.setVisibility(View.VISIBLE);
			if (mBean.getTotal_price() > 0) {
				mViewHolder.mAllianceOrderPrice2.setText(String.format(mContext
						.getString(R.string.str_format_itme_total_prices),
						mBean.getTotal_price()));
			}
		} else if (statuStr.equals("待评价")) {
			mViewHolder.mForActionBefore.setVisibility(View.VISIBLE);
			mViewHolder.mActionBtnLayout.setVisibility(View.VISIBLE);
			mViewHolder.mForActionAfter.setVisibility(View.GONE);
			if (mBean.getTotal_price() > 0) {
				mViewHolder.mAllianceOrderPrice.setText(String.format(mContext
						.getString(R.string.str_format_itme_total_prices),
						mBean.getTotal_price()));
			}
			mViewHolder.mBtnCancleAction.setVisibility(View.GONE);
			mViewHolder.mBtnAction.setVisibility(View.VISIBLE);
			mViewHolder.mBtnAction.setText("评价");
			mViewHolder.mBtnAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (face != null)
						face.goEvaluate(mBean.getOrder_num(), position);
					Log.e("renk", mBean.toString());
				}
			});
		} else if (statuStr.equals("评价")) {
			mViewHolder.mForActionBefore.setVisibility(View.GONE);
			mViewHolder.mActionBtnLayout.setVisibility(View.GONE);
			mViewHolder.mForActionAfter.setVisibility(View.VISIBLE);
			if (mBean.getTotal_price() > 0) {
				mViewHolder.mAllianceOrderPrice2.setText(String.format(mContext
						.getString(R.string.str_format_itme_total_prices),
						mBean.getTotal_price()));
			}
		}
		if (mBean.getGoods_info() != null && mBean.getGoods_info().size() > 0) {
			mViewHolder.mAllianceOrderCounts.setText(String.format(
					mContext.getString(R.string.str_format_item_count),
					String.valueOf(mBean.getGoods_info().size())));
		}
	}

	private class ViewHolder {
		TextView mAllianceStoreName;
		TextView mAllianceOrderStatus;
		/**放订单详情的容器**/
		LinearLayout mContainer;
		TextView mAllianceOrderCounts;
		TextView mAllianceOrderPrice;
		TextView mAllianceOrderNumber;
		TextView mBtnCancleAction;
		TextView mBtnAction;
		RelativeLayout mForActionBefore;
		LinearLayout mForActionAfter;
		LinearLayout mActionBtnLayout;
		TextView mAllianceOrderCounts2;
		TextView mAllianceOrderPrice2;
	}

	@Override
	public void AddNewData(Object mObject) {
		if (mObject instanceof List<?>) {
			mList = (List<AllianceOrderBean>) mObject;
		}
		notifyDataSetChanged();
		super.AddNewData(mObject);
	}

	public void ClearData() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public void RefreshData(Object mObject) {
		super.RefreshData(mObject);
	}

	/**
	 * 取消订单操作
	 */
	BpiHttpHandler.IBpiHttpHandler mIbpiCancel = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Object message) {
			HighCommunityUtils.GetInstantiation().ShowToast("取消订单成功", 0);
			if (mListener != null) {
				mListener.onRefresh();
			}
		}

		@Override
		public Object onResolve(String result) {
			return result;
		}

		@Override
		public void onError(int id, String message) {
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
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
				HighCommunityApplication.toLoginAgain(mContext);
			}
		}
	};
	/**
	 * 确认订单操作
	 */
	BpiHttpHandler.IBpiHttpHandler mIbpiConfirm = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Object message) {
			HighCommunityUtils.GetInstantiation().ShowToast("确认收货成功,快去评价吧~", 0);
			if (mListener != null) {
				mListener.onRefresh();
			}
		}

		@Override
		public Object onResolve(String result) {
			return result;
		}

		@Override
		public void onError(int id, String message) {
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void cancleAsyncTask() {
			// TODO Auto-generated method stub

		}

		@Override
		public void shouldLogin(boolean isShouldLogin) {

		}

		@Override
		public void shouldLoginAgain(boolean isShouldLogin, String msg) {

			if (isShouldLogin){
				HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
				HighCommunityApplication.toLoginAgain(mContext);
			}
		}
	};

	/**
	 * 去支付界面
	 */
	BpiHttpHandler.IBpiHttpHandler mIbpiGoPay = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Object message) {
			AlliancePayBean result = (AlliancePayBean) message;
			Log.e("renk", result.toString());
			if (result != null) {
				Intent intent = new Intent(mContext, ShowPayActivity.class);
				intent.putExtra("payParams", result);
				mContext.startActivity(intent);
			} else {
				HighCommunityUtils.GetInstantiation().ShowToast("访问异常", 0);
			}
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveAllianceGoPay(result);
		}

		@Override
		public void onError(int id, String message) {
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void cancleAsyncTask() {
			// TODO Auto-generated method stub

		}

		@Override
		public void shouldLogin(boolean isShouldLogin) {

		}

		@Override
		public void shouldLoginAgain(boolean isShouldLogin, String msg) {
			if (isShouldLogin){
				HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
				HighCommunityApplication.toLoginAgain(mContext);
			}
		}
	};
}
