package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Report;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CreatReport;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Motion;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_NameList;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Notice;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_SuperVise;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Vote;

/**
 * * @功能：自治大厅 三级Act<br>
 *
 * @作者： Lee_yting<br>
 * @时间：2016/10/9<br>
 */
public class AutonomousAct_Third extends BaseFragmentActivity {

    public static final int TAG_REPORT_DETAIL = 0;
    public static final int TAG_INQUIRY_DETAIL = 1;
    public static final int TAG_MOTION_DETAIL = 2;
    public static final int TAG_VOTE_DETAIL = 3;
    public static final int TAG_QUESTION_DETAIL = 4;
    /**创建汇报**/
    public static final int TAG_CREAT_REPORT=5;
    /**创建询问**/
    public static final int TAG_CREAT_INQUIRY=6;
//    public static final int TAG_GROUPCHAT = 5;


    @Bind(R.id.auto_third_img_back)
    ImageView img_Back;
    @Bind(R.id.auto_thirdc_tv_title)
    TextView tv_Title;
    @Bind(R.id.auto_third_fraglayout)
    LinearLayout auto_Fraglayout;
//    @Bind(R.id.title_status_height)
//    LinearLayout mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auto_third_details);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
//        if (!super.isVersionBiger()) {
//            mHeight.setVisibility(View.GONE);
//        }
        int tag = getIntent().getIntExtra("title", -1);
        if (tag == -1) return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (tag) {
            case TAG_REPORT_DETAIL://汇报详情
                tv_Title.setText("详情");
                AutoDetail_Report mReportDetail=new AutoDetail_Report();
                Bundle mbundle=new Bundle();
                if (getIntent().getStringExtra("reportDetail_id")!=null){

                    mbundle.putString("reportDetail_id",getIntent().getStringExtra("reportDetail_id"));
                    mReportDetail.setArguments(mbundle);
                    ft.replace(R.id.auto_third_fraglayout,mReportDetail,AutoDetail_Report.FRAGMENTTAG);
                }
                break;
            case TAG_INQUIRY_DETAIL://询问详情
                tv_Title.setText("投票");
                AutoFrag_Vote mVoteFrag = new AutoFrag_Vote();
                ft.replace(R.id.auto_sec_fraglayout, mVoteFrag, AutoFrag_Vote.FRAGMENTTAG);
                break;
            case TAG_MOTION_DETAIL://提案详情
                tv_Title.setText("名单");
                AutoFrag_NameList mNameList = new AutoFrag_NameList();
                ft.replace(R.id.auto_sec_fraglayout, mNameList, AutoFrag_NameList.FRAGMENTTAG);
                break;
            case TAG_VOTE_DETAIL://选举详情
                tv_Title.setText("提案");
                AutoFrag_Motion mMotion = new AutoFrag_Motion();
                ft.replace(R.id.auto_sec_fraglayout, mMotion, AutoFrag_Motion.FRAGMENTTAG);
                break;
            case TAG_QUESTION_DETAIL://问卷调查详情
                tv_Title.setText("监督");
                AutoFrag_SuperVise mSuperVise = new AutoFrag_SuperVise();
                ft.replace(R.id.auto_sec_fraglayout, mSuperVise, AutoFrag_SuperVise.FRAGMENTTAG);
                break;
            case TAG_CREAT_REPORT://创建汇报
                tv_Title.setText(" ");
                AutoFrag_CreatReport mCreatReport=new AutoFrag_CreatReport();
                Bundle mbundle=new Bundle();
                if (getIntent().getStringExtra("reportDetail_id")!=null){

                    mbundle.putString("reportDetail_id",getIntent().getStringExtra("reportDetail_id"));
                    mCreatReport.setArguments(mbundle);
                    ft.replace(R.id.auto_third_fraglayout,mReportDetail,AutoDetail_Report.FRAGMENTTAG);
                }
                break;
//            case TAG_GROUPCHAT:
//                tv_Title.setText("群聊");
//                AutoFrag_Groupchat mGroupchat = new AutoFrag_Groupchat();
//                ft.replace(R.id.auto_sec_fraglayout, mGroupchat, AutoFrag_Groupchat.FRAGMENTTAG);
//                break;
        }
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick(R.id.auto_third_img_back)
    public void onClick() {
        onBackPressed();
    }
}
