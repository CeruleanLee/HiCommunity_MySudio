package cn.hi028.android.highcommunity.TestScroll;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.don.tools.BpiHttpHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyMoreGoodsGridAdapter3;
import cn.hi028.android.highcommunity.bean.SupplyGoodsMoreBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

public class TabFragment extends Fragment
{
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    // private TextView mTextView;
//    private List<String> mDatas = new ArrayList<String>();
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity> mDatas
            = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.testfragment_tab, container, false);
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.id_stickynavlayout_innerscrollview);
        initview();

        return view;

    }

    private void initview() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
        for (int i = 0; i < 50; i++)
        {
            /**
             *   this.id = id;
             this.name = name;
             this.label = label;
             this.cover_pic = cover_pic;
             this.sale = sale;
             this.price = price;
             this.old_price = old_price;
             * **/
            mDatas.add(new SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity(i+"","name"+i,"label"+i,"","sale"+i,22.00+"",28.00+""));
        }
//        mRecyclerView.setAdapter(new CommonAdapter<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>(getActivity(), R.layout.frag_supply_more, mDatas)
//        {
//            @Override
//            public void convert(ViewHolder holder, SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity supplyMoreGoodsEntity) {
//                holder.setText(R.id.supplygoodsmore_goodsTitle,supplyMoreGoodsEntity
//                        .getId()).setText(R.id.supplygoodsmore_nowPrice,"￥:" + supplyMoreGoodsEntity.getPrice())
//           .setText(R.id.supplygoodsmore_tv_tag,supplyMoreGoodsEntity.getLabel());
//            }
//
//        });
        mRecyclerView.setAdapter(new SupplyMoreGoodsGridAdapter3(mDatas, getActivity()));
    }

    public static TabFragment newInstance(String title) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    String category_id = 0 + "";
    /**
     * 1=>最新,2=>销量,3=>价格升序,4=>价格降序  每次进入默认最新
     **/
    int sort = 1;

    private void initDatas() {
        Log.e(Tag, "调用 initDatas+category_id：" + category_id + " sort " + sort);

        HTTPHelper.GetSupplyGoodsListMore(mIbpi, category_id, sort + "");
    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.d(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.d(Tag, "---~~~onSuccess");
            if (message == null) return;
            SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity mData = (SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity) message;
            mDatas.clear();
//            mAdapter.ClearData();
//           if (mList.size()>0){
//               Log.d(Tag, "---~~~clear1");
//
//               mList.clear();
//           }
//            if (mAdapter.getCount()>0){
//                Log.d(Tag, "---~~~clear2");
//                mAdapter.ClearData();
//            }
            mDatas = mData.getGoods();
//            initview();
//            mAdapter.AddNewData(mList);
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
    public static final String Tag = "SupplyShopMoreFrag--->";

}
