package cn.hi028.android.highcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Inquiry;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Motion;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Questions;
import cn.hi028.android.highcommunity.activity.fragment.AutoDetail_Report;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CreatInquiry;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_CreatReport;

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
        int owner_id=getIntent().getIntExtra("owner_id",-1);
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
                tv_Title.setText("详情");
                AutoDetail_Inquiry mInquiry = new AutoDetail_Inquiry();
String inquiry_id=getIntent().getStringExtra("inquiry_id");
                Bundle mInquiryDetailbundle=new Bundle();
                if (inquiry_id!=null){

                    mInquiryDetailbundle.putString("inquiry_id",inquiry_id);


                    mInquiry.setArguments(mInquiryDetailbundle);
                    ft.replace(R.id.auto_third_fraglayout, mInquiry, AutoDetail_Inquiry.FRAGMENTTAG);
                }
                break;
            case TAG_MOTION_DETAIL://提案详情
                tv_Title.setText("详情");
                String motion_id=getIntent().getStringExtra("motion_id");
                AutoDetail_Motion mMotionDetail = new AutoDetail_Motion();

                Bundle mMotionDetailbundle=new Bundle();
                if (motion_id!=null){
                    mMotionDetailbundle.putString("motion_id",motion_id);
                    mMotionDetail.setArguments(mMotionDetailbundle);
                    ft.replace(R.id.auto_third_fraglayout, mMotionDetail, AutoDetail_Motion.FRAGMENTTAG);
                }
                break;
            case TAG_VOTE_DETAIL://选举详情

            case TAG_QUESTION_DETAIL://问卷调查详情
                tv_Title.setText(" ");
                String question_id=getIntent().getStringExtra("question_id");
                int is_voted=getIntent().getIntExtra("is_voted",-1);
                int type=getIntent().getIntExtra("type",-1);
                Log.d("三级界面","question_id"+question_id+",is_voted"+is_voted+",type"+type);
                AutoDetail_Questions mQuestions = new AutoDetail_Questions();
                Bundle mQuestionsbundle=new Bundle();
                if (question_id!=null||is_voted!=-1||type!=-1){
                    mQuestionsbundle.putString("question_id",question_id);
                    mQuestionsbundle.putInt("is_voted",is_voted);
                    mQuestionsbundle.putInt("type",type);
                    mQuestions.setArguments(mQuestionsbundle);
                    ft.replace(R.id.auto_third_fraglayout, mQuestions, AutoDetail_Questions.FRAGMENTTAG);
                }
                break;
            case TAG_CREAT_REPORT://创建汇报
                tv_Title.setText(" ");
                AutoFrag_CreatReport mCreatReport=new AutoFrag_CreatReport();
                Bundle mCreatReportbundle=new Bundle();
                if (owner_id!=-1){
                    mCreatReportbundle.putInt("owner_id",owner_id);
                    mCreatReport.setArguments(mCreatReportbundle);
                    ft.replace(R.id.auto_third_fraglayout,mCreatReport,AutoFrag_CreatReport.FRAGMENTTAG);
                }
                break;
            case TAG_CREAT_INQUIRY://创建询问
                tv_Title.setText(" ");
                AutoFrag_CreatInquiry mCreatInquiry=new AutoFrag_CreatInquiry();
                Bundle mCreatInquirybundle=new Bundle();
                if (owner_id!=-1){
                    mCreatInquirybundle.putInt("owner_id",owner_id);
                    mCreatInquiry.setArguments(mCreatInquirybundle);
                    ft.replace(R.id.auto_third_fraglayout,mCreatInquiry,AutoFrag_CreatReport.FRAGMENTTAG);
                }
                break;
//            case TAG_GROUPCHAT:
//                tv_Title.setText("群聊");
//                AutoFrag_Groupchat mGroupchat = new AutoFrag_Groupchat();
//                ft.replace(R.id.auto_third_fraglayout, mGroupchat, AutoFrag_Groupchat.FRAGMENTTAG);
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
