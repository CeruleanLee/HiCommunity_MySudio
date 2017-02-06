package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.NewSupplyMoreAct3;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity2;
import cn.hi028.android.highcommunity.adapter.SupplyMoreGoodsGridAdapter3;
import cn.hi028.android.highcommunity.bean.SupplyGoodsMoreBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.mynew.SpaceItemDecoration3;


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
        view = inflater.inflate(R.layout.frag_supply_more, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        mAdapter = new SupplyMoreGoodsGridAdapter3(mList, getActivity());
        mGridview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = 8;
        mGridview.addItemDecoration(new SpaceItemDecoration3(18));
        mGridview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SupplyMoreGoodsGridAdapter3.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity data) {
                Intent mIntent = new Intent(getActivity(), SupplyGoodsDetailActivity2.class);
                mIntent.putExtra("id", data.getId());
                getActivity().startActivity(mIntent);
            }
        });
        initDatas();
    }

    String category_id = 0 + "";
    /**
     * 1=>最新,2=>销量,3=>价格升序,4=>价格降序  每次进入默认最新
     **/
    int sort = 1;

    private void initDatas() {
        if (view != null) {
            mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
        }
        HTTPHelper.GetSupplyGoodsListMore(mIbpi, category_id, sort + "");
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWatingWindow != null) {
                mWatingWindow.dismiss();
            }
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (mWatingWindow != null) {

                mWatingWindow.dismiss();
            }
            if (message == null) return;
            SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity mData = (SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity) message;
            mList.clear();
            mAdapter.ClearData();
            mList = mData.getGoods();
            mAdapter.AddNewData(mList);

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSupplyMoreGoodsListDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            if (mWatingWindow != null) {

                mWatingWindow.dismiss();
            }

        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWatingWindow != null) {
            mWatingWindow.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onSortChange(String category_id, int sort) {
        this.category_id = category_id;
        this.sort = sort;
        initDatas();
    }

    public void updateSort(String category_id, int sort) {
        this.category_id = category_id;
        this.sort = sort;
        initDatas();
    }
}
