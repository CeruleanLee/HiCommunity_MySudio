package cn.hi028.android.highcommunity.activity.fragment.alliance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOderDetailActivity;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.adapter.AllianceOrderAdapter;
import cn.hi028.android.highcommunity.bean.AllianceOrderBean;
import cn.hi028.android.highcommunity.lisenter.OnRefreshListener;
import cn.hi028.android.highcommunity.utils.HTTPHelper;

/**
 * 联盟订单 全部订单
 * Created by Administrator on 2016/8/11.
 */
public class AllianceAllOrderFrag extends BaseFragment implements
OnRefreshListener {
	View mProgress;
	TextView mNodata;
	PullToRefreshListView mAllOrderListView;

	private AllianceOrderAdapter mAllOrderAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_alliance_all_order,
				container, false);
		mProgress = view.findViewById(R.id.progress_ticket_notice);
		mNodata = (TextView) view.findViewById(R.id.tv_ticket_Nodata);
		mAllOrderListView = (PullToRefreshListView) view
				.findViewById(R.id.all_listView);
		mAllOrderListView.setVisibility(View.VISIBLE);
		mAllOrderAdapter = new AllianceOrderAdapter(getActivity(),
				AllianceOrder.TAB_ALL_ORDER, this, null);
		mAllOrderListView.setAdapter(mAllOrderAdapter);
		mAllOrderListView.setEmptyView(mNodata);
		mAllOrderListView
		.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(
					PullToRefreshBase<ListView> refreshView) {
				HTTPHelper.GetAllianceList(mIbpi,
						AllianceOrder.TAB_ALL_ORDER);
			}
		});
		mAllOrderListView.setOnItemClickListener(listener);
		return view;
	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> view, View arg1, int arg2, long arg3) {
			LogUtil.d("------item点击");
			AllianceOrderBean info = (AllianceOrderBean) view
					.getItemAtPosition(arg2);
			String num = info.getOrder_num();
			Intent intent = new Intent(getActivity(), AllianceOderDetailActivity.class);
			intent.putExtra("order_num", num);
			LogUtil.d("------order_num="+num);
			getActivity().startActivity(intent);
		}
	};
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		mProgress.setVisibility(View.VISIBLE);
		HTTPHelper.GetAllianceList(mIbpi, AllianceOrder.TAB_ALL_ORDER);
	}

	private BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			mProgress.setVisibility(View.GONE);
			resetUi((List<AllianceOrderBean>) message);
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveAllianceOrderBean(result);
		}

		@Override
		public void onError(int id, String message) {
			mProgress.setVisibility(View.GONE);

		}

		@Override
		public void cancleAsyncTask() {
			mProgress.setVisibility(View.GONE);
		}

	};

	private void resetUi(List<AllianceOrderBean> allianceOrderBeans) {
		if (allianceOrderBeans.size() < 1) {
			mNodata.setVisibility(View.VISIBLE);
		} else {
			mNodata.setVisibility(View.GONE);
		}
		mAllOrderAdapter.AddNewData(allianceOrderBeans);
		mAllOrderListView.onRefreshComplete();
	}

	@Override
	public void onRefresh() {
		HTTPHelper.GetAllianceList(mIbpi, AllianceOrder.TAB_ALL_ORDER);
	}
	public void onResume() {

		super.onResume();
		LogUtil.d("------进入onResume");

		onRefresh();

	};

}
