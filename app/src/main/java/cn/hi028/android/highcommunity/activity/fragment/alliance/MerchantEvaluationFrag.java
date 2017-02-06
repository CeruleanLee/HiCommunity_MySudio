package cn.hi028.android.highcommunity.activity.fragment.alliance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.EvaluationAdapter;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 惠生活-商家联盟-item点击 -评估
 * @author Administrator
 *
 */
public class MerchantEvaluationFrag extends BaseFragment implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	PullToRefreshListView refreshview;
	ListView listView;
	/** 商家id */
	String id;
	EvaluationAdapter adapter;
	ArrayList<MerchantEvaluationInfoListBean> listbean;
	private int page = 1;
	public void setMyId(String id) {
		this.id = id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.merchant_evaluation_frag, null);
		refreshview = (PullToRefreshListView) view
				.findViewById(R.id.evalution_frag_refreshview);
		listView = (ListView) view.findViewById(R.id.evalution_frag_list);
		init();
		return view;
	}

	private void init() {
		
		listbean = new ArrayList<MerchantEvaluationInfoListBean>();
		HTTPHelper.GetMerchantEvaluation(mIbpi, id, page);
		refreshview.setMode(Mode.BOTH);
		refreshview.setVisibility(View.VISIBLE);
		refreshview.setOnItemClickListener(this);
		refreshview.setOnRefreshListener(this);
		adapter = new EvaluationAdapter(getActivity(), listbean);
		refreshview.setAdapter(adapter);
	}

	private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}
		
		@Override
		public void onSuccess(Object message) {
			if (page > 1) {
				listbean.addAll((ArrayList<MerchantEvaluationInfoListBean>) message);
			} else {
				listbean = (ArrayList<MerchantEvaluationInfoListBean>) message;
			}
			adapter = new EvaluationAdapter(getActivity(), listbean);
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			refreshview.setRefreshing(false);
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.GetEvaluationList(result);
		}

		@Override
		public void onError(int id, String message) {

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

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		HTTPHelper.GetMerchantEvaluation(mIbpi, id, page);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		HTTPHelper.GetMerchantEvaluation(mIbpi, id, ++page);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}

}
