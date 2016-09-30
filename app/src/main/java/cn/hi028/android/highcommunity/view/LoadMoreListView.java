package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.duohuo.dhroid.util.LogUtil;

import cn.hi028.android.highcommunity.R;


/**
 * Created by lee on 2016/7/29.
 */
public class LoadMoreListView extends ListView implements View.OnClickListener {

	private TextView mFooterTickerTv;
	private ProgressBar mFooterPb;
	private TextView mFooterFailedTv;

	public LoadMoreListView(Context context) {
		super(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	private void init() {
		initFooterView();

		setOnScrollListener(scrollListener);

	}

	/**
	 * 监听ListView的滚动
	 */

	 OnScrollListener scrollListener = new OnScrollListener() {
		 @Override
		 public void onScrollStateChanged(AbsListView view, int scrollState) {

		 }

		 @Override
		 public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			 if(lastItem == firstVisibleItem+visibleItemCount-1){
				 return;
			 }
			 lastItem = firstVisibleItem+visibleItemCount-1;
			 Log.d("XM", firstVisibleItem+","+visibleItemCount+","+totalItemCount);
			 if(!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
				 //满足条件后，加载下一页数据
				 Log.d("XM", ","+firstVisibleItem+","+totalItemCount+","+visibleItemCount);

				 if(loadingMoreListener != null){
					 isLoading = true;
					 mFooterFailedTv.setVisibility(View.GONE);
					 loadingMoreListener.loadMore();
				 }
			 }
			 if(firstVisibleItem==0){
				  final View topChildView = view.getChildAt(0);
				  isReachTop=topChildView.getTop()==0;
			  }
			 
			 
			 
		 }
	 };
boolean isReachTop;
	 private int lastItem;
	 private boolean isLoading = true;

	 public void showFooterView(){
		 if(mFooterView != null)
			 mFooterView.setVisibility(View.VISIBLE);
	 }
	 public void hideFooterView(){
		 if(mFooterView != null)
			 mFooterView.setVisibility(View.GONE);
	 }
	 /**
	  * 初始化脚部视图
	  */
	  private void initFooterView() {
		  mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.v_loadmore,null);
		  addFooterView(mFooterView);
		  mFooterPb = (ProgressBar) mFooterView.findViewById(R.id.v_loadmore_progressBar);
		  mFooterTickerTv = (TextView) mFooterView.findViewById(R.id.v_loadmore_textView);
		  mFooterFailedTv = (TextView) mFooterView.findViewById(R.id.v_loadmore_failedTv);
		  mFooterFailedTv.setOnClickListener(onClickListener);
	  }
	  OnClickListener onClickListener = new OnClickListener() {

		  @Override
		  public void onClick(View v) {
			  mFooterFailedTv.setVisibility(View.GONE);
			  if(loadingMoreListener != null){
				  loadingMoreListener.retryMore();
			  }
		  }
	  };

	  @Override
	  public void onClick(View v) {
		  //        if (v.getId() == R.id.shihuaFrag_goToTop)
		  //        {
		  //            this.scrollTo(0,0);
		  ////            this.smoothScrollTo(0, 0);
		  //        }

	  }

	  public interface OnLoadingMoreListener{
		  void loadMore();
		  void retryMore();
	  }
	  private OnLoadingMoreListener loadingMoreListener;
	  private View mFooterView;
	  public void setOnLoadingMoreListener(OnLoadingMoreListener listener){
		  this.loadingMoreListener = listener;
	  }
	  /**
	   * 没有更多数据了
	   */
	  public void setNoMore(){
		  mFooterPb.setVisibility(View.GONE);
		  mFooterTickerTv.setText("没有更多数据了!");
		  isLoading = true;
	  }
	  /**
	   * 当外部数据加载成功后，应该调用这个方法
	   */
	  public void loadSuccess(){
		  isLoading = false;
	  }
	  public void loadFailed(){
		  //        mFooterFailedTv.setVisibility(View.VISIBLE);
		  isLoading = false;
	  }
	  //    @Override
	  //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	  //    	// TODO Auto-generated method stub
	  //    	int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
	  //    	
	  //    	
	  //    	
	  //    	super.onMeasure(widthMeasureSpec, expandSpec);
	  //    }

	  //    private interface isListviewReachTop{
	  //    	boolean isListviewReachTop();
	  //    };
	  //    

	  @Override
	  public boolean onInterceptTouchEvent(MotionEvent ev) {

		  switch (ev.getAction()) {
		    case MotionEvent.ACTION_DOWN:
		    	 LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview拦截 ACTION_DOWN");
		    	
		    	
		    	
		  
		    case MotionEvent.ACTION_MOVE:
		    	 LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview拦截 ACTION_MOVE");
//		    	 if (isListViewReachTopEdge(this)) {
//		    		 LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview拦截 准备返回true");
//					  return true;
//				  }	
		    	
		  
		        break;
		    case MotionEvent.ACTION_UP:
		    	 LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview拦截 ACTION_UP");
		  
		    case MotionEvent.ACTION_CANCEL:
		   	 LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview拦截 ACTION_CANCEL");
		        break;
		    default:
		        break;
		  }
		  LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview拦截 准备返回super:"+ super.onInterceptTouchEvent(ev));
		 
		          return super.onInterceptTouchEvent(ev);
//		  return true;
	  }

	  /**
//	   * @param flag
	   */
	  //    private void setParentScrollAble(boolean flag) {
	  //
	  //        parentScrollView.requestDisallowInterceptTouchEvent(!flag);
	  //    }
	  @Override
	  public boolean onTouchEvent(MotionEvent ev) {
		  // TODO Auto-generated method stub
		 
		  //	this.
		    switch (ev.getAction()) {
		    case MotionEvent.ACTION_DOWN:
		    	LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview触摸 ACTION_DOWN");
		    case MotionEvent.ACTION_MOVE:
		    	LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview触摸 ACTION_MOVE");
		    	 if (isReachTop) {
		    		 LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview触摸 ACTION_MOVE return false");
					  return false;
				  }	
		    	
		  
		        break;
		    case MotionEvent.ACTION_UP:
		    	LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview触摸 ACTION_UP");
		    case MotionEvent.ACTION_CANCEL:
		    	LogUtil.d("~~~~~~~~~~~~~~~~~~~~  listview触摸 ACTION_CANCEL");
		        break;
		    default:
		        break;
		  }

		  return super.onTouchEvent(ev);
	  }
	  public boolean isListViewReachTopEdge(final ListView listView) {
		  boolean result=false;
		  if(listView.getFirstVisiblePosition()==0){
			  final View topChildView = listView.getChildAt(0);
			  result=topChildView.getTop()==0;
		  }
		  return result ;
	  }

}
