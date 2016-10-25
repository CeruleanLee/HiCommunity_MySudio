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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoCommitDataFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InitBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * Created by Lee_yting on 2016/10/14 0014.
 * 说明：
 */
public class AutoCommitAct extends BaseFragmentActivity {
    String Tag = "~~~1   AutoCommitAct";

    public static final String ACTIVITYTAG = "AutoCommitAct";
    public static final String INTENTTAG = "AutoCommitAct";
    boolean isVerified, isCommitData;
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
    int mStatus;
    public Auto_InitBean.Auto_Init_DataEntity mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_autcommit);
        ButterKnife.bind(this);
        isCommitData = getIntent().getBooleanExtra("isCommitData", false);
//        mStatus = getIntent().getIntExtra("mStatus", -1);
//        mData=getIntent().getParcelableExtra("mData");
//        Toast.makeText(this,"isCommitData:"+isCommitData+"mStatus"+mStatus,Toast.LENGTH_SHORT).show();
//        if (mData!=null){
//            Toast.makeText(this,"传过来的status:"+mData.getStatus(),Toast.LENGTH_SHORT).show();
//
//        }else {
//            Toast.makeText(this,"过来的data  null",Toast.LENGTH_SHORT).show();
//
//        }

        initDatas();
    }
    private void initDatas() {

        Log.d(Tag,"initDatas ");
        HTTPHelper.InitAutoAct(mIbpi);
//        Log.d(Tag,"mdata.getatus:"+mData.getStatus());
        Log.d(Tag,"initDatas 2");


    }
    private void initView() {
        Log.d(Tag,"initView");
        if (mData.getStatus() == 2) {
            Log.d(Tag,"mStatus 2");
            //填写资料
            toCommitData();
//            layout_CommitData.setVisibility(View.VISIBLE);
//            layout_Checking.setVisibility(View.GONE);
//            layout_CheckFail.setVisibility(View.GONE);
        } else if (mData.getStatus()== 0) {
            Log.d(Tag,"mStatus 0");
//正在审核
            layout_Checking.setVisibility(View.VISIBLE);
            layout_CommitData.setVisibility(View.GONE);
            layout_CheckFail.setVisibility(View.GONE);
        } else if (mData.getStatus() == -1) {
            Log.d(Tag,"mStatus -1");
            //审核失败
            layout_CheckFail.setVisibility(View.VISIBLE);
            layout_Checking.setVisibility(View.GONE);
            layout_CommitData.setVisibility(View.GONE);
        }

    }
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message) {
                Log.d(Tag,"onSuccess message null");
                return;
            }
            Log.d(Tag,"onSuccess message 不空");
            mData = (Auto_InitBean.Auto_Init_DataEntity) message;
           if (mData==null){

               Log.d(Tag,"onSuccess message kong空!!!!!!");
           }
            initView();
            Log.d(Tag,"kong空才怪");
        }
        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveDataEntity(result);
        }
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }
        @Override
        public void cancleAsyncTask() {
        }
    };
    @OnClick({R.id.commitAct_img_back, R.id.commit_but_checkAgain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commitAct_img_back:
                onBackPressed();
                break;
            case R.id.commit_but_checkAgain:

                break;
        }
    }

    /**
     * 进入提交资料页
     */
    private void toCommitData() {
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
//
//        ft.commit();
        AutoCommitDataFrag mCommitFrag = new AutoCommitDataFrag();
        Log.d(Tag,"判断前");

        Bundle mBundle=new Bundle();
        if (mData!=null){
            Log.d(Tag,"toCommitData mData no null");
            Log.d(Tag,"mData "+mData.toString());
            mBundle.putParcelable("data",mData);
            mCommitFrag.setArguments(mBundle);
            ft2.replace(R.id.commit_commitData, mCommitFrag, AutoCommitDataFrag.FRAGMENTTAG);
            ft2.commit();
        }
        Log.d(Tag,"toCommitData mData null");
        layout_CommitData.setVisibility(View.VISIBLE);
        layout_Checking.setVisibility(View.GONE);
        layout_CheckFail.setVisibility(View.GONE);
    }

}
