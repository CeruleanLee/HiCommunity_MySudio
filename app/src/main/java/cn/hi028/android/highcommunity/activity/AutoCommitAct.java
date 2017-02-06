package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoCommitDataFrag;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CerFailedMsg;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InitBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * Created by Lee_yting on 2016/10/14 0014.
 * 说明：提交资料act 包括 审核失败跳转 创建认证跳转
 */
public class AutoCommitAct extends BaseFragmentActivity {
    String Tag = "AutoCommitAct--->";
    public static final String ACTIVITYTAG = "AutoCommitAct";
    public static final String INTENTTAG = "AutoCommitAct";
    @Bind(R.id.commitAct_img_back)
    ImageView img_Back;
    @Bind(R.id.commit_nodata)
    TextView tv_Nodata;
    @Bind(R.id.commit_commitData)
    LinearLayout layout_CommitData;
    @Bind(R.id.commit_tv_checking)
    TextView tv_Checking;
    @Bind(R.id.commit_checking)
    LinearLayout layout_Checking;
    @Bind(R.id.commit_tv_checkFail)
    TextView tv_CheckFail;
    @Bind(R.id.commit_checkFail)
    LinearLayout layout_CheckFail;
    @Bind(R.id.commit_but_checkAgain)
    Button but_CheckAgain;
    public Auto_InitBean.Auto_Init_DataEntity mData;
    int tag_creatCer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(Tag, "oncreat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_autcommit);
        ButterKnife.bind(this);
        getIntent().setExtrasClassLoader(getClass().getClassLoader());
        tag_creatCer = getIntent().getIntExtra("tag_creatCer", -1);
//初始化HttpUtils
        mHttpUtils = new HttpUtils();
        mHttpUtils.configCurrentHttpCacheExpiry(0);
        mHttpUtils.configSoTimeout(4000);
        mHttpUtils.configTimeout(4000);
        if (tag_creatCer == 6) {
            initDatas();
        } else {
            initHttp();
        }
    }

    private HttpUtils mHttpUtils;

    private void initDatas() {
        Log.e(Tag, "initDatas ");
        HTTPHelper.AutoGetVillage(mIbpi);
    }

    private void initHttp() {
        Log.e(Tag, "进入初始化网络");
        String url = "http://028hi.cn/api/yinit/index.html";
        RequestParams params = new RequestParams();
        params.addBodyParameter("token", HighCommunityApplication.mUserInfo.getToken());
        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.e(Tag, "http 访问失败的 arg1--->" + arg1.toString());
                HighCommunityUtils.GetInstantiation().ShowToast(arg1.toString(), 0);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                String content = arg0.result;
                Log.e(Tag, "http 访问success的 content--->" + content);
                Auto_InitBean mInitBean = new Gson().fromJson(content, Auto_InitBean.class);
                if (mInitBean != null) {
                    mData = mInitBean.getData();
                    tv_CheckFail.setText("原因：" + mInitBean.getMsg());
                    initView();
                }
            }
        });
    }

    private void initView() {
        Log.e(Tag, "initView");
        if (mData.getStatus() == 2) {
            Log.e(Tag, "mStatus 2");
            //填写资料
            toCommitData();
        } else if (mData.getStatus() == 0) {
            Log.e(Tag, "mStatus 0");
//正在审核
            layout_Checking.setVisibility(View.VISIBLE);
            layout_CommitData.setVisibility(View.GONE);
            layout_CheckFail.setVisibility(View.GONE);
        } else if (mData.getStatus() == -1) {
            Log.e(Tag, "mStatus -1");
            //审核失败
            layout_CheckFail.setVisibility(View.VISIBLE);
            layout_Checking.setVisibility(View.GONE);
            layout_CommitData.setVisibility(View.GONE);
        }

    }

    //TODO 审核失败传数据传错了   应该是list   改了之后没注意忘记改了
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message) {
                Log.e(Tag, "onSuccess message null");
                return;
            }
            mData = (Auto_InitBean.Auto_Init_DataEntity) message;
            if (mData != null) {
                toCommitData();
                Log.e(Tag, "onSuccess message toCommitData!!!!!!");
            }
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVillageDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin) {
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(AutoCommitAct.this);
            }
        }
    };

    @OnClick({R.id.commitAct_img_back, R.id.commit_but_checkAgain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commitAct_img_back:
                onBackPressed();
                break;
            case R.id.commit_but_checkAgain:
                toCommitData();
                break;
        }
    }

    /**
     * 进入提交资料页
     */
    private void toCommitData() {
        Log.e(Tag, "toCommitData");
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        AutoCommitDataFrag mCommitFrag = new AutoCommitDataFrag();
        Bundle mBundle = new Bundle();
        if (mData != null) {
            Log.e(Tag, "toCommitData mData no null");
            Log.e(Tag, "mData " + mData.toString());
            mBundle.setClassLoader(getClass().getClassLoader());
            mBundle.putParcelable("data", mData);
            mCommitFrag.setArguments(mBundle);
            ft2.replace(R.id.commit_commitData, mCommitFrag, AutoCommitDataFrag.FRAGMENTTAG);
            ft2.commit();
        }
        Log.e(Tag, "toCommitData mData null");
        layout_CommitData.setVisibility(View.VISIBLE);
        layout_Checking.setVisibility(View.GONE);
        layout_CheckFail.setVisibility(View.GONE);
    }


    private void toShowFailedMsg() {
        FragmentManager fm3 = getSupportFragmentManager();
        FragmentTransaction ft3 = fm3.beginTransaction();
        AutoFrag_CerFailedMsg mFailedMsgFrag = new AutoFrag_CerFailedMsg();
        Bundle mBundle = new Bundle();
        if (mData != null) {
            Log.e(Tag, "toCommitData mData no null");
            Log.e(Tag, "mData " + mData.toString());
            mBundle.setClassLoader(getClass().getClassLoader());
            mBundle.putParcelable("data", mData);
            mFailedMsgFrag.setArguments(mBundle);
            ft3.replace(R.id.commit_commitData, mFailedMsgFrag, AutoFrag_CerFailedMsg.FRAGMENTTAG);
            ft3.commit();
        }

        layout_CommitData.setVisibility(View.VISIBLE);
        layout_Checking.setVisibility(View.GONE);
        layout_CheckFail.setVisibility(View.GONE);
    }

}
