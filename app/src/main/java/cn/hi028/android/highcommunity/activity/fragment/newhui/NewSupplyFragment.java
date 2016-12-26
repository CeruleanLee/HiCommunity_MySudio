package cn.hi028.android.highcommunity.activity.fragment.newhui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshGridViewNoScroll;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyCategoryListAdapter;
import cn.hi028.android.highcommunity.adapter.SupplyPurchaseListAdapter;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.MyNoScrollListview;
import cn.hi028.android.highcommunity.view.nine.MyNineGridView;

/**
 * @功能：新版直供商品<br>  直供主页
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class NewSupplyFragment extends BaseFragment {
    public static final String Tag = "NewSupplyFragment--->";
    public static final String FRAGMENTTAG = "NewSupplyFragment";
    public static  boolean isFistRequest = true;
    public static  boolean isFistRequestHttp = true;
    View view;
    @Bind(R.id.supply_category_listview)
    MyNoScrollListview mCategoryListview;
    @Bind(R.id.supply_purchase_listview)
    MyNoScrollListview mPurchaseListview;
    @Bind(R.id.supply_merchant_listview)
    PullToRefreshGridViewNoScroll mMerchantListview;
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.tv_NoticeDetails_noData)
    TextView mNodata;
    @Bind(R.id.supply_scrollview)
    ScrollView mScrollview;
    @Bind(R.id.piclistView)
    MyNineGridView piclistView;
    @Bind(R.id.container_newsupply)
    LinearLayout containerNewsupply;
    private PopupWindow mWatingWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.fragment_newsupply, null);
        ButterKnife.bind(this, view);
        return view;
    }
Handler mHandler=new Handler();
Runnable mRunable=new Runnable() {
    @Override
    public void run() {
        mWatingWindow.dismiss();
    }
};

    private void initData() {

        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
        HTTPHelper.GetNewSupplyGoods(mIbpi);
    }

    NewSupplyBean.NewSupplyDataEntity mBean;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            containerNewsupply.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "onSuccess");

            containerNewsupply.setVisibility(View.VISIBLE);
            Log.e(Tag, "onSuccess  1");

            mProgress.setVisibility(View.GONE);
            Log.e(Tag, "onSuccess  2");

            mNodata.setVisibility(View.GONE);
            Log.e(Tag, "onSuccess  3");

            if (null == message) {
                Log.e(Tag, "onSuccess  4");

                return;
            }
            Log.e(Tag, "onSuccess  5");

            mBean = (NewSupplyBean.NewSupplyDataEntity) message;
            Log.e(Tag, "onSuccess  6");

            initCategoryList(mBean);
if (isFistRequest){
    isFistRequest=false;

    mHandler.postDelayed(mRunable,1000);
}else{
    mWatingWindow.dismiss();

}

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveNewSupplyDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
        }
    };

    /**
     * 填充商家推荐数据
     **/
    private void initmerchantList(NewSupplyBean.NewSupplyDataEntity mBean) {
        Log.e(Tag, "0填充商家");
//        MerchantImgGridAdapter mGridAdapter = new MerchantImgGridAdapter(mBean.getMerchant(), getActivity());
//        Log.e(Tag, "1填充商家");
//
//        mMerchantListview.setAdapter(mGridAdapter);
//        Log.e(Tag, "商家---" + mGridAdapter.getCount());
//        Log.e(Tag, "2填充商家");

        List<String> imgUrlList = new ArrayList<String>();
        List<String> idList = new ArrayList<String>();

        for (int i = 0; i < mBean.getMerchant().size(); i++) {
            imgUrlList.add(i, Constacts.IMAGEHTTP + mBean.getMerchant().get(i).getLogo());
            idList.add(i,  mBean.getMerchant().get(i).getId());

        }
        Log.e(Tag, "imgUrlList-----" + imgUrlList.size());
        piclistView.setUrlList(imgUrlList,idList);

    }

    /**
     * 填充限时抢购数据
     **/
    private void initPurchaseList(NewSupplyBean.NewSupplyDataEntity mBean) {
        final SupplyPurchaseListAdapter mPurchaseAdapter = new SupplyPurchaseListAdapter(mBean.getPurchase(), getActivity(), mMerchantListview);
        mPurchaseListview.setAdapter(mPurchaseAdapter);
        initmerchantList(mBean);
    }

    public CountDownTimer countDownTimer;

    /**
     * 填充分类列表数据
     *
     * @param mBean
     */
    private void initCategoryList(NewSupplyBean.NewSupplyDataEntity mBean) {
        Log.e(Tag, "initCategoryList");

        SupplyCategoryListAdapter mCategoryAdapter = new SupplyCategoryListAdapter(mBean.getCategory(), getActivity());

        Log.e(Tag, "setAdapter");

        mCategoryListview.setAdapter(mCategoryAdapter);
        Log.e(Tag, "setAdapter ok");

        initPurchaseList(mBean);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mHandler.removeCallbacks(mRunable);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFistRequestHttp){
            isFistRequestHttp=false;
            initData();
        }
    }
}
