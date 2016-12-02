package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyMoreGoodsGridAdapter;
import cn.hi028.android.highcommunity.bean.SupplyGoodsMoreBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：直供商品更多Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/30<br>
 */

public class SupplyShopMoreFrag extends BaseFragment {
    public static final String Tag = "SupplyShopMoreFrag--->";
    public static final String FRAGMENTTAG = "SupplyShopMoreFrag";
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity> mList
            = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
    SupplyMoreGoodsGridAdapter mAdapter;

    @Bind(R.id.tv_Automotion_Nodata)
    TextView mNodata;
    @Bind(R.id.frag_supplymore_gridview)
    PullToRefreshGridView mGridview;

    private PopupWindow mWatingWindow;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_supply_more, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        Log.d(Tag, "initView");
        mAdapter = new SupplyMoreGoodsGridAdapter(mList, getActivity());
        mGridview.setMode(PullToRefreshBase.Mode.DISABLED);
//        mAdapter = new SupplyMoreGoodsGridAdapter(mList, getActivity());
//        mList = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
////        List<Auto_MotionBean.MotionDataEntity> list, Context context, View view, int screenWidth, ListView listView
//        DisplayMetrics mdm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mdm);
////        mAdapter = new AutoMyMoitionAdapter(mList, getActivity(), getActivity().getWindow().getDecorView(), mdm.widthPixels, mGridview);
//
//        mGridview.setEmptyView(mNodata);
//        mGridview.setAdapter(mAdapter);
        initDatas();
    }
    String category_id=0+"";
    /**
     * 1=>最新,2=>销量,3=>价格升序,4=>价格降序  每次进入默认最新
     **/
    int sort = 1;
    private void initDatas() {
        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
        HTTPHelper.GetSupplyGoodsListMore(mIbpi,category_id,sort+"");
    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            Log.d(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWatingWindow.dismiss();
            if (message==null)return;
            SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity mData= (SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity) message;
            mList = mData.getGoods();
            mAdapter.AddNewData(mList);
//            HighCommunityUtils.GetInstantiation()
//                    .setThirdServiceGridViewHeight(mGridview, mAdapter, 4);
            mGridview.setAdapter(mAdapter);
//			mLoadingView.loadSuccess();
//			mLoadingView.setVisibility(View.GONE);
//			LogUtil.d(Tag+"---~~~initViewonSuccess");
////						if (null == message) return;
//			LogUtil.d(Tag+"---~~~ initView   message:"+message);
//			ThirdServiceBean mBean = (ThirdServiceBean) message;
//			mAdapter.AddNewData(mBean.getServices());
//			mGridView.setAdapter(mAdapter);
//			pagerAdapter.setImageIdList(mBean.getBanners());
//			HighCommunityUtils.GetInstantiation()
//			.setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
//			tatalLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public Object onResolve(String result) {
//			Log.e("renk", result);
            return HTTPHelper.ResolveSupplyMoreGoodsListDataEntity(result);
        }
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };
    @Override
    public void onPause() {
        super.onPause();
        Log.d(Tag, "onPause");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(Tag, "onResume");
//        initDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }





}