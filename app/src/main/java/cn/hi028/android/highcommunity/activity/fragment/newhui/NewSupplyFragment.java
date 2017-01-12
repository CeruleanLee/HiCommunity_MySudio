package cn.hi028.android.highcommunity.activity.fragment.newhui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyCategoryListAdapter2;
import cn.hi028.android.highcommunity.adapter.SupplyPurchaseListAdapter;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LinearLayoutForListView;
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
    LinearLayoutForListView mCategoryListview;
    @Bind(R.id.supply_purchase_listview)
    LinearLayoutForListView mPurchaseListview;

//    @Bind(R.id.pg_progress)
//    ProgressBar mProgress;
    @Bind(R.id.tv_NoticeDetails_noData)
    TextView mNodata;
    @Bind(R.id.supply_scrollview)
    ScrollView mScrollview;
    @Bind(R.id.piclistView)
    MyNineGridView piclistView;
    @Bind(R.id.container_newsupply)
    LinearLayout containerNewsupply;

//    @Bind(R.id.progress_layout)
//    LinearLayout progress_layout;

    @Bind(R.id.layout_hasdata)
    LinearLayout layout_hasdata;

    @Bind(R.id.layout_splashTV)
    RelativeLayout layout_splashTV;
    @Bind(R.id.layout_merchantTV)
    RelativeLayout layout_merchantTV;
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
//            progress_layout.setVisibility(View.GONE);
            layout_hasdata.setVisibility(View.VISIBLE);
            Log.e(Tag, "onSuccess  1");

//            mProgress.setVisibility(View.GONE);
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
//progress_layout.setVisibility(View.GONE);
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
            layout_hasdata.setVisibility(View.GONE);
            containerNewsupply.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.VISIBLE);
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            Log.e(Tag, "onSuccess");

            containerNewsupply.setVisibility(View.VISIBLE);
            layout_hasdata.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);
            Log.e(Tag, "onSuccess  3");

            if (null == message) {
                Log.e(Tag, "onSuccess  4");

                return;
            }
            Log.e(Tag, "onSuccess  5");
            if (mWatingWindow!=null){

//                mWatingWindow.dismiss();
                mHandler.postDelayed(mRunable,500);
            }
            mBean = (NewSupplyBean.NewSupplyDataEntity) message;
            if (mBean!=null){
                Log.e(Tag, "onSuccess  6");
                if (mBean.getCategory()!=null&&mBean.getCategory().size()>0){

                    initCategoryList(mBean);
                }else{

                }
                if (mBean.getPurchase()!=null&&mBean.getPurchase().size()>0){

                    initPurchaseList(mBean);

                }else{
                    layout_splashTV.setVisibility(View.GONE);
                }
                layout_merchantTV.setVisibility(View.GONE);
//                if (mBean.getMerchant()!=null&&mBean.getMerchant().size()>0){
//
//                    initmerchantList(mBean);
//
//
//                }else{
//                    layout_merchantTV.setVisibility(View.GONE);
//                }


            }
            if (isFistRequest){
                isFistRequest=false;

//                mHandler.postDelayed(mRunable,500);
            }else{


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

        @Override
        public void shouldLogin(boolean isShouldLogin) {
            if (isShouldLogin){
//                HighCommunityUtils.GetInstantiation().ShowToast("去登陆", 0);


            }
        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (mWatingWindow!=null){

                mWatingWindow.dismiss();
            }
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
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
   SupplyPurchaseListAdapter mPurchaseAdapter;

    /**
     * 填充限时抢购数据
     **/
    private void initPurchaseList(NewSupplyBean.NewSupplyDataEntity mBean) {
        mPurchaseAdapter = new SupplyPurchaseListAdapter(mBean.getPurchase(), getActivity());
        mPurchaseListview.setAdapter(mPurchaseAdapter);

    }

    public CountDownTimer countDownTimer;
    SupplyCategoryListAdapter2 mCategoryAdapter;

    /**
     * 填充分类列表数据
     *
     * @param mBean
     */
    private void initCategoryList(NewSupplyBean.NewSupplyDataEntity mBean) {
        Log.e(Tag, "initCategoryList");

        mCategoryAdapter = new SupplyCategoryListAdapter2(mBean.getCategory(), getActivity());

        Log.e(Tag, "setAdapter");

        mCategoryListview.setAdapter(mCategoryAdapter);
        Log.e(Tag, "setAdapter ok");



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mHandler.removeCallbacks(mRunable);
        unregistNetworkReceiver();
    }

    @Override
    public void onResume() {
        Log.e(Tag, "---onResume ");
        if (isFistRequestHttp) {
            Log.e(Tag, "---onResume  1 ");

            isFistRequestHttp = false;
//            initData();
        }else{
            Log.e(Tag, "---onResume  2 ");
//            initData();
//            mScrollview.scrollTo(0,120);
        }
        registNetworkReceiver();
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.e(Tag, "---onPause ");

        super.onPause();
    }


    @Override
    public void onStop() {
        Log.e(Tag, "---onStop ");


        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.e(Tag, "---onDetach ");

        super.onDetach();
    }




    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    //有网络
                    Log.e(Tag,"有网络");
//                    initData();
                    isNoNetwork = false;
                } else {
                    //没有网络
                    Log.e(Tag,"没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;

}
