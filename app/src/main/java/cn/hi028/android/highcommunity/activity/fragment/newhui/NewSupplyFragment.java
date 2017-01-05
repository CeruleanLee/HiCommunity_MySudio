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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import net.duohuo.dhroid.activity.BaseFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyCategoryListAdapter2;
import cn.hi028.android.highcommunity.adapter.SupplyPurchaseListAdapter;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.MyNoScrollMeasureListview;
import cn.hi028.android.highcommunity.view.nine.MyNineGridView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @功能：新版直供商品<br> 直供主页
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class NewSupplyFragment extends BaseFragment {
    public static final String Tag = "NewSupplyFragment--->";
    public static final String FRAGMENTTAG = "NewSupplyFragment";
    public static boolean isFistRequest = true;
    public static boolean isFistRequestHttp = true;
    View view;
    @Bind(R.id.supply_category_listview)
    MyNoScrollMeasureListview  mCategoryListview;
    @Bind(R.id.supply_purchase_listview)
    MyNoScrollMeasureListview mPurchaseListview;

    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    @Bind(R.id.tv_NoticeDetails_noData)
    TextView mNodata;
    @Bind(R.id.supply_scrollview)
    ScrollView mScrollview;
    @Bind(R.id.piclistView)
    MyNineGridView piclistView;
    @Bind(R.id.container_newsupply)
    RelativeLayout containerNewsupply;

    @Bind(R.id.progress_layout)
    LinearLayout progress_layout;

    @Bind(R.id.layout_hasdata)
    LinearLayout layout_hasdata;
    private PopupWindow mWatingWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.fragment_newsupply, null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    Handler mHandler = new Handler();
    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if (mWatingWindow!=null){

                mWatingWindow.dismiss();
            }
        }
    };
    public abstract class MyCallback extends Callback<NewSupplyBean>
    {

        public NewSupplyBean parseNetworkResponse(Response response) throws IOException
        {
            String string = response.body().string();
            NewSupplyBean user = new Gson().fromJson(string, NewSupplyBean.class);
            return user;
        }
    }
    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {

            Log.e(Tag, "onBefore" );
        }

        @Override
        public void onAfter(int id)
        {


            Log.e(Tag, "onAfter" );
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            Log.e(Tag, "onResponse：onError" );

        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(Tag, "onResponse：complete" + response);
            NewSupplyBean mNewSupplyBean = new Gson().fromJson(response, NewSupplyBean.class);
            Log.e(Tag, "onSuccess");

            containerNewsupply.setVisibility(View.VISIBLE);
            progress_layout.setVisibility(View.GONE);
            layout_hasdata.setVisibility(View.VISIBLE);
            Log.e(Tag, "onSuccess  1");

            mProgress.setVisibility(View.GONE);
            Log.e(Tag, "onSuccess  2");

            mNodata.setVisibility(View.GONE);
            Log.e(Tag, "onSuccess  3");

            if (null == mNewSupplyBean) {
                Log.e(Tag, "onSuccess  4");

                return;
            }
            Log.e(Tag, "onSuccess  5");

            mBean = (NewSupplyBean.NewSupplyDataEntity) mNewSupplyBean.getData();
            Log.e(Tag, "onSuccess  6");

            initCategoryList(mBean);
            if (mWatingWindow!=null){

                mWatingWindow.dismiss();
            }





        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(Tag, "inProgress:" + progress);
        }
    }

    private void initData() {
if (getActivity().hasWindowFocus()){

        mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), view, Gravity.CENTER);
}
progress_layout.setVisibility(View.GONE);
        layout_hasdata.setVisibility(View.GONE);
        HTTPHelper.GetNewSupplyGoods(mIbpi);
        Log.e(Tag, "   initData");


        String url = "http://028hi.cn/api/sgoods/home.html";
        Log.e(Tag, "   initData  2");

//        OkHttpUtils.post().url(url).build().execute(new MyStringCallback());
//



    }

    NewSupplyBean.NewSupplyDataEntity mBean;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            if (mWatingWindow != null) {

                mWatingWindow.dismiss();
            }
            progress_layout.setVisibility(View.GONE);
            layout_hasdata.setVisibility(View.GONE);
            containerNewsupply.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "onSuccess");

            containerNewsupply.setVisibility(View.VISIBLE);
            progress_layout.setVisibility(View.GONE);
            layout_hasdata.setVisibility(View.VISIBLE);
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
                if (mWatingWindow!=null){

                    mWatingWindow.dismiss();
                }

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
            if (mWatingWindow!=null){

                mWatingWindow.dismiss();
            }
        }
    };

    /**
     * 填充商家推荐数据
     **/
    private void initmerchantList(NewSupplyBean.NewSupplyDataEntity mBean) {
        Log.e(Tag, "0填充商家");

        List<String> imgUrlList = new ArrayList<String>();
        List<String> idList = new ArrayList<String>();

        for (int i = 0; i < mBean.getMerchant().size(); i++) {
            imgUrlList.add(i, Constacts.IMAGEHTTP + mBean.getMerchant().get(i).getLogo());
            idList.add(i, mBean.getMerchant().get(i).getId());

        }
        Log.e(Tag, "imgUrlList-----" + imgUrlList.size());
        piclistView.setUrlList(imgUrlList, idList);

    }

    /**
     * 填充限时抢购数据
     **/
    private void initPurchaseList(NewSupplyBean.NewSupplyDataEntity mBean) {
        final SupplyPurchaseListAdapter mPurchaseAdapter = new SupplyPurchaseListAdapter(mBean.getPurchase(), getActivity());
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

        SupplyCategoryListAdapter2 mCategoryAdapter = new SupplyCategoryListAdapter2(mBean.getCategory(), getActivity());

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
        Log.e(Tag, "---onResume ");
        super.onResume();
        if (isFistRequestHttp) {
            isFistRequestHttp = false;
//            initData();
        }
    }
}
