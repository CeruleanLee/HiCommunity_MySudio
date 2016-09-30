package cn.hi028.android.highcommunity.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.EvaluationAdapter;
import cn.hi028.android.highcommunity.bean.GoodsData;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.view.LoadMoreListView;
import cn.hi028.android.highcommunity.view.Mylistview;

public class Frag_CommentDetail extends BaseFragment{
Mylistview mListview;
EvaluationAdapter adapter;
Bundle bundle2 ;
private List<MerchantEvaluationInfoListBean> data;
@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle2= getArguments();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_commentdetail, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListview=(Mylistview) getActivity().findViewById(R.id.ac_good_evaluation_listview);
		
		data = new ArrayList<MerchantEvaluationInfoListBean>();
		GoodsData goodsdata = (GoodsData) bundle2.get("data");
		LogUtil.d("========== goodsdata  :"+goodsdata.toString());
		data = goodsdata.getComments();
		if (data != null) {
			adapter = new EvaluationAdapter(getActivity(), data);
			adapter.setType(2);
			mListview.setAdapter(adapter);
		}
//		mListview.setOnScrollListener(new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView arg0, int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
////				if (isListviewReachTop(mListview)) {
//////					mListview.setdi
////				}
//				
//			}
//		});

	}
	public boolean isListviewReachTop(ListView listview){
		boolean result =false;
		if (listview.getFirstVisiblePosition()==0) {
			return listview.getChildAt(0).getTop()==0;
		}
		
		
		
		
		return false;
		
		
	}
}
