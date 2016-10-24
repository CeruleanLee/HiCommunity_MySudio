package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoNamelist_YWHAdapter;
import cn.hi028.android.highcommunity.adapter.AutoNamelist_YZAdapter;
import cn.hi028.android.highcommunity.adapter.AutoNamelist_YZDBAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NameListBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅认证完成主界面-名单<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_NameList extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_NameList~~~";
    public static final String FRAGMENTTAG = "AutoFrag_NameList";
    @Bind(R.id.frag_AutoName_ownerCommittee)
    RadioButton mCommittee;
    @Bind(R.id.frag_AutoName_ownerRepresent)
    RadioButton mRepresent;
    @Bind(R.id.frag_AutoName_owner)
    RadioButton mOwner;
    @Bind(R.id.frag_AutoName_RadioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.frag_AutoName_fragContainer)
    RelativeLayout mFragContainer;
//    @Bind(R.id.tv_Autonotice_Nodata)
//    TextView mNodata;
    @Bind(R.id.frag_AutoName_list_CO)
    ListView mListCO;
    @Bind(R.id.frag_AutoName_list_Re)
    ListView mListRe;
    @Bind(R.id.frag_AutoName_list_Owner)
    ListView mListOwner;
    AutoNamelist_YWHAdapter mYWHadapter;
    AutoNamelist_YZDBAdapter mYZDBadapter;
    AutoNamelist_YZAdapter mYZadapter;
    private List<Auto_NameListBean.NameListDataEntity.YwhEntity> mYWHList;
    private List<Auto_NameListBean.NameListDataEntity.YzdbEntity> mYWDBList;
    private List<Auto_NameListBean.NameListDataEntity.YzEntity> mYZList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_namelist, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LogUtil.d(Tag + "initView");

        mYWHList=new ArrayList<Auto_NameListBean.NameListDataEntity.YwhEntity>();
        mYWDBList=new ArrayList<Auto_NameListBean.NameListDataEntity.YzdbEntity>();
        mYZList=new ArrayList<Auto_NameListBean.NameListDataEntity.YzEntity>();
        mYWHadapter=new AutoNamelist_YWHAdapter(mYWHList,getActivity());
        mYZDBadapter=new AutoNamelist_YZDBAdapter(mYWDBList,getActivity());
        mYZadapter=new AutoNamelist_YZAdapter(mYZList,getActivity());

//        mListCO.setEmptyView(mNodata);
//        mListRe.setEmptyView(mNodata);
//        mListOwner.setEmptyView(mNodata);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.frag_AutoName_ownerCommittee:
                        mFragContainer.setVisibility(View.VISIBLE);
                        mListRe.setVisibility(View.GONE);
                        mListOwner.setVisibility(View.GONE);
                        mListCO.setVisibility(View.VISIBLE);
                        break;
                    case R.id.frag_AutoName_ownerRepresent:
                        mFragContainer.setVisibility(View.VISIBLE);
                        mListCO.setVisibility(View.GONE);
                        mListOwner.setVisibility(View.GONE);
                        mListRe.setVisibility(View.VISIBLE);
                        break;
                    case R.id.frag_AutoName_owner:
                        mFragContainer.setVisibility(View.VISIBLE);
                        mListRe.setVisibility(View.GONE);
                        mListCO.setVisibility(View.GONE);
                        mListOwner.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
//        mRepresent.setChecked(true);
        initDatas();
    }
    private void initDatas() {
        if (!isNoNetwork){
            HTTPHelper.AutoNamelistList(mIbpi);
        }

    }
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
			LogUtil.d(Tag+"---~~~ initView   message:"+message);
            Auto_NameListBean.NameListDataEntity mBean = (Auto_NameListBean.NameListDataEntity) message;
            mYWHadapter.AddNewData(mBean.getYwh());
            mYZDBadapter.AddNewData(mBean.getYzdb());
            mYZadapter.AddNewData(mBean.getYz());

            mListCO.setAdapter(mYWHadapter);
            mListRe.setAdapter(mYZDBadapter);
            mListOwner.setAdapter(mYZadapter);
            mFragContainer.setVisibility(View.VISIBLE);
            mListRe.setVisibility(View.GONE);
            mListOwner.setVisibility(View.GONE);
            mListCO.setVisibility(View.VISIBLE);
        }

        @Override
        public Object onResolve(String result) {
			LogUtil.d(Tag+"---~~~iresult"+result);
			return HTTPHelper.ResolveAutoNamelistListEntity(result);
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
        LogUtil.d(Tag + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");
        registNetworkReceiver();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
                    LogUtils.d("有网络");
                    initDatas();
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}
