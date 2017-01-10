package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler.IBpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.MerchantActivity;
import cn.hi028.android.highcommunity.adapter.ShopNearByAdapter;
import cn.hi028.android.highcommunity.bean.NearByShopData;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 联盟商家
 */
public class ShopNearbyFrag extends BaseFragment implements
		OnRefreshListener2<ListView>, OnItemClickListener {

	private PullToRefreshListView listView;

	ShopNearByAdapter adapter;

	private List<NearByShopData> datainfo;

	String district;

	int page = 1;
TextView tv_nodata;
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_hui_shops, null);
		Log.i("renk", "onCreateView");
		initView(view);
		return view;
	}

	void initView(View view) {
		datainfo = new ArrayList<NearByShopData>();
		listView = (PullToRefreshListView) view
				.findViewById(R.id.refreshlistview);
		tv_nodata = (TextView) view.findViewById(R.id.tv_huishop_Nodata);
		listView.setEmptyView(tv_nodata);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);
		adapter = new ShopNearByAdapter(getActivity(), datainfo);

		HTTPHelper.GetNearbyShops(mIbpi, district, page);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		// Toast.makeText(getActivity(), "This" + district, 0).show();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		HTTPHelper.GetNearbyShops(mIbpi, district, page);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		HTTPHelper.GetNearbyShops(mIbpi, district, ++page);

	}

	@Override
	public void onItemClick(AdapterView<?> group, View arg1, int position,
			long arg3) {
		NearByShopData info = (NearByShopData) group
				.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(), MerchantActivity.class);
		intent.putExtra("id", info.getId());
		intent.putExtra("shop", info.getShop_name());
		startActivity(intent);
	}

	private IBpiHttpHandler mIbpi = new IBpiHttpHandler() {

		@Override
		public void setAsyncTask(AsyncTask asyncTask) {

		}

		@Override
		public void onSuccess(Object message) {

			if (message != null) {

				if (page > 1) {
					// 追加数据
					datainfo.addAll((ArrayList<NearByShopData>) message);
				} else {

					datainfo.clear();
					datainfo.addAll((ArrayList<NearByShopData>) message);
				}

				adapter.notifyDataSetChanged();
				listView.onRefreshComplete();
			}

		}

		@Override
		public Object onResolve(String result) {

			return HTTPHelper.NearByShopList(result);
		}

		@Override
		public void onError(int id, String message) {
			Log.d("renk", message);
			listView.onRefreshComplete();

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

//	@Override
//	public void onAttach(Context context) {
//		Log.i("renk", "onattach");
//		super.onAttach(context);
//	}

	@Override
	public void onStart() {
		Log.i("renk", "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.i("renk", "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i("renk", "onPause");
		// TODO Auto-generated method stub
		super.onPause();
		
	}
     
	@Override
	public void onDestroy() {
		Log.i("renk", "onDestroy");
		Log.i("renk", "onDestroy");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i("renk", "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i("renk", "onDetach");
		// TODO Auto-generated method stub
		super.onDetach();
	}

}
