package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.NewSupplyMoreAct3;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity;
import cn.hi028.android.highcommunity.adapter.SupplyMoreGoodsGridAdapter3;
import cn.hi028.android.highcommunity.bean.SupplyGoodsMoreBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.mynew.MyItemDecoration;


/**
 * @功能：直供商品更多Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/30<br>
 */

public class SupplyShopMoreFrag extends BaseFragment implements NewSupplyMoreAct3.MySortChangeListener {
    public static final String Tag = "SupplyShopMoreFrag--->";
    public static final String FRAGMENTTAG = "SupplyShopMoreFrag";
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity> mList
            = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
    SupplyMoreGoodsGridAdapter3 mAdapter;


    @Bind(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView mGridview;

    private PopupWindow mWatingWindow;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_supply_more, null);
        ButterKnife.bind(this, view);
//        vp.setObjectForPosition(view,1);
        initView();
        return view;
    }

    void initView() {
        Log.d(Tag, "initView");
        mAdapter = new SupplyMoreGoodsGridAdapter3(mList, getActivity());
//        mGridview.setMode(PullToRefreshBase.Mode.DISABLED);
        mGridview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mGridview.setAdapter(mAdapter);
        mGridview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SupplyMoreGoodsGridAdapter3.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity data) {
                Intent mIntent = new Intent(getActivity(), SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", data.getId());
                getActivity().startActivity(mIntent);
            }
        });
        mGridview.addItemDecoration(new MyItemDecoration());
//        mAdapter = new SupplyMoreGoodsGridAdapter(mList, getActivity());
//        mList = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
////        List<Auto_MotionBean.MotionDataEntity> list, Context context, View view, int screenWidth, ListView listView
//        DisplayMetrics mdm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mdm);
////        mAdapter = new AutoMyMoitionAdapter(mList, getActivity(), getActivity().getWindow().getDecorView(), mdm.widthPixels, mGridview);
//
//        mGridview.setEmptyView(mNodata);
//        mGridview.setAdapter(mAdapter);
//        initDatas();
    }

    String category_id = 0 + "";
    /**
     * 1=>最新,2=>销量,3=>价格升序,4=>价格降序  每次进入默认最新
     **/
    int sort = 1;

    private void initDatas() {
        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
        Log.e(Tag, "调用 initDatas+category_id：" + category_id + " sort " + sort);

        HTTPHelper.GetSupplyGoodsListMore(mIbpi, category_id, sort + "");
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
            Log.d(Tag, "---~~~onSuccess");
            mWatingWindow.dismiss();
            if (message == null) return;
            SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity mData = (SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity) message;
            mList.clear();
            mAdapter.ClearData();
//           if (mList.size()>0){
//               Log.d(Tag, "---~~~clear1");
//
//               mList.clear();
//           }
//            if (mAdapter.getCount()>0){
//                Log.d(Tag, "---~~~clear2");
//                mAdapter.ClearData();
//            }
            mList = mData.getGoods();
            mAdapter.AddNewData(mList);
//            HighCommunityUtils.GetInstantiation().setThirdServiceGridViewHeight(mGridview, mAdapter, 4);
//            mGridview.setAdapter(mAdapter);

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
            mWatingWindow.dismiss();

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
        if (mWatingWindow != null) {
            mWatingWindow.dismiss();
        }
//        initDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onSortChange(String category_id, int sort) {
        Log.e(Tag, "调用 onSortChange");
        this.category_id = category_id;
        this.sort = sort;
        initDatas();
    }

    public void updateSort(String category_id, int sort) {
        Log.e(Tag, "调用 updateSort");
        this.category_id = category_id;
        this.sort = sort;
        initDatas();
    }
}
