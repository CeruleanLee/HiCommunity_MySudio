package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoCommitDataFrag;
import cn.hi028.android.highcommunity.activity.fragment.AutonomousMainFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InitBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * * @功能：自治大厅Act<br>
 *
 * @作者： Lee_yting<br>
 * @时间：2016/10/9<br>
 */
public class Service_AutonomousActivity extends BaseFragmentActivity {
    String Tag = "~~~1   Service_AutonomousActivity";

    public static final String ACTIVITYTAG = "AutonomousActivity";
    public static final String INTENTTAG = "AutonomousActivityIntent";
    @Bind(R.id.autoAct_img_back)
    ImageView img_Back;
    @Bind(R.id.tv_secondtitle_name)
    TextView tv_title;

    @Bind(R.id.auto_identified_tomian)
    LinearLayout identifiedToFrag;
    @Bind(R.id.auto_tv_check)
    TextView tv_Check;
    @Bind(R.id.auto_checking)
    LinearLayout checking_Layout;
    @Bind(R.id.auto_commitData)
    LinearLayout commitData_Layout;
    @Bind(R.id.auto_nodata)
    TextView tv_Nodata;

    boolean isVerified,isCommitData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_autonomous);
        ButterKnife.bind(this);
        initDatas();
        initViews();
    }

    private void initDatas() {
        LogUtil.d(Tag + "initDatas");
        HTTPHelper.InitAutoAct(mIbpi);
        LogUtil.d(Tag + "initDatas2");

    }
    int mStatus;
    public Auto_InitBean.Auto_Init_DataEntity mData;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message) {
                return;
            }
            mData = (Auto_InitBean.Auto_Init_DataEntity) message;
            mStatus=mData.getStatus();
            if (mStatus==1){

                isVerified=true;
            }else{
                isVerified=false;

            }
            if (mStatus==0||mStatus==1){
                isCommitData=true;
            }else{
                isCommitData=false;
            }
//            if (mData.getStatus() == 0 || mData.getStatus() == -1) {
//                //审核中 审核失败
//                dataChecking();
//            } else if (mData.getStatus() == 2) {
//
//                //进入提交资料页
//                toCommitData();
//
//            } else if (mData.getStatus() == 1) {
//                //进入frag
//                toAutoFrag();
//            }
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

    FragmentManager fm;
    FragmentTransaction ft;
    private void initViews() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        AutonomousMainFrag mAutFrag = new AutonomousMainFrag();
        ft.replace(R.id.auto_identified_tomian, mAutFrag, AutonomousMainFrag.FRAGMENTTAG);

//        AutoCommitDataFrag mCommitFrag = new AutoCommitDataFrag();
//        mBundle.putParcelable("data",mData);
//        mCommitFrag.setArguments(mBundle);
//        ft.replace(R.id.auto_commitData, mCommitFrag, AutoCommitDataFrag.FRAGMENTTAG);
        Bundle mBundle=new Bundle();
        ft.commit();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toAutoFrag();
//        initDatas();
    }

    /**
     * 去自治大厅主界面
     */
    private void toAutoFrag() {
        identifiedToFrag.setVisibility(View.VISIBLE);
        commitData_Layout.setVisibility(View.GONE);
        checking_Layout.setVisibility(View.GONE);
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
        Bundle mBundle=new Bundle();
        if (mData!=null){

            Log.d(Tag,"mData "+mData.toString());
            mBundle.putParcelable("data",mData);
            mCommitFrag.setArguments(mBundle);
            ft2.replace(R.id.auto_commitData, mCommitFrag, AutoCommitDataFrag.FRAGMENTTAG);
            ft2.commit();
        }
        commitData_Layout.setVisibility(View.VISIBLE);
        checking_Layout.setVisibility(View.GONE);
        identifiedToFrag.setVisibility(View.GONE);
    }

    /**
     * 资料审核中 或是审核失败
     */
    private void dataChecking() {
        checking_Layout.setVisibility(View.VISIBLE);
        identifiedToFrag.setVisibility(View.GONE);
        commitData_Layout.setVisibility(View.GONE);
//        tv_Check.setText();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
