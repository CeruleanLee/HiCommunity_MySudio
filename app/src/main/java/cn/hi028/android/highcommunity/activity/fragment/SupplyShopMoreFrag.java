package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
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
    /**
     * 创建提案
     **/
    public static final int TAG_CREAT_MOTION = 7;
    SupplyMoreGoodsGridAdapter mAdapter;
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity> mList;
    @Bind(R.id.tv_Automotion_Nodata)
    TextView mNodata;
    @Bind(R.id.frag_supplymore_gridview)
    PullToRefreshGridView mGridview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        View view = inflater.inflate(R.layout.frag_supply_more, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        Log.d(Tag, "initView");
        mList = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
//        List<Auto_MotionBean.MotionDataEntity> list, Context context, View view, int screenWidth, ListView listView

        DisplayMetrics mdm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mdm);


//        mAdapter = new AutoMyMoitionAdapter(mList, getActivity(), getActivity().getWindow().getDecorView(), mdm.widthPixels, mGridview);
        mGridview.setEmptyView(mNodata);
        mGridview.setAdapter(mAdapter);
//        initDatas();
    }

    private void initDatas() {

        HTTPHelper.GetMyMotion(mIbpi);
    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.d(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mList = (List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>) message;
            mAdapter.AddNewData(mList);
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
            return HTTPHelper.ResolveMotionDataEntity(result);
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
        initDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }





}
