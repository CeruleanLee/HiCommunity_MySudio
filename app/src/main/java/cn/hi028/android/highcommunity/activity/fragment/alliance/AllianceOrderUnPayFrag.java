package cn.hi028.android.highcommunity.activity.fragment.alliance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOderDetailActivity;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.adapter.AllianceOrderAdapter;
import cn.hi028.android.highcommunity.bean.AllianceOrderBean;
import cn.hi028.android.highcommunity.lisenter.OnRefreshListener;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 
 * @author Administrator
 *联盟订单未支付状态下的frg
 */
public class AllianceOrderUnPayFrag extends BaseFragment implements OnRefreshListener{
	/**正在加载的页面**/
	View mProgress;
	/**emptyview界面**/
	TextView mNodata;
	PullToRefreshListView mUnPayListView;

	private AllianceOrderAdapter mUnPayAdapter = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d("------AllianceOrderUnPayFrag-onCreate");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_alliance_unpay_order,
				container, false);
		mProgress = view.findViewById(R.id.progress_ticket_notice);
		mNodata = (TextView) view.findViewById(R.id.tv_ticket_Nodata);
		mUnPayListView = (PullToRefreshListView) view.findViewById(R.id.unpay_listView);
		mUnPayListView.setVisibility(View.VISIBLE);
		mUnPayAdapter = new AllianceOrderAdapter(getActivity(),AllianceOrder.TAB_UNPAY, this, null);
		mUnPayListView.setAdapter(mUnPayAdapter);
		mUnPayListView.setEmptyView(mNodata);

		mUnPayListView.setOnItemClickListener(listener);

		mUnPayListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(
					PullToRefreshBase<ListView> refreshView) {
				HTTPHelper.GetAllianceList(mIbpi,AllianceOrder.TAB_UNPAY);
			}
		});
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		mProgress.setVisibility(View.VISIBLE);
		HTTPHelper.GetAllianceList(mIbpi, AllianceOrder.TAB_UNPAY);
	}

	private BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {
			LogUtil.d("------联盟订单未支付状态成功请求数据后的message："+message);
			mProgress.setVisibility(View.GONE);
			resetUi((List<AllianceOrderBean>) message);
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveAllianceOrderBean(result);
		}

		@Override
		public void onError(int id, String message) {
			LogUtil.d("------联盟订单未支付状态请求数据失败："+message);
			mProgress.setVisibility(View.GONE);
		}

		@Override
		public void cancleAsyncTask() {
			mProgress.setVisibility(View.GONE);
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

	private void resetUi(List<AllianceOrderBean> allianceOrderBeans) {
		if (allianceOrderBeans.size() < 1) {
			mNodata.setVisibility(View.VISIBLE);
		} else {
			mNodata.setVisibility(View.GONE);
		}
		mUnPayAdapter.AddNewData(allianceOrderBeans);
		mUnPayListView.onRefreshComplete();
	}

	@Override
	public void onRefresh() {
		LogUtil.d("--------进入刷新");
		HTTPHelper.GetAllianceList(mIbpi, AllianceOrder.TAB_UNPAY);
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
	public void onResume() {
		
		super.onResume();
        LogUtil.d("------进入onResume");
        
		onRefresh();
		
	};

}
