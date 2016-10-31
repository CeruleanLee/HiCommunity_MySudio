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
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Certigication;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Motion;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_NameList;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Notice;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_SuperVise;
import cn.hi028.android.highcommunity.activity.fragment.AutoFrag_Vote;

/**
 * * @功能：自治大厅 6个模块二级Act<br>
 *
 * @作者： Lee_yting<br>
 * @时间：2016/10/9<br>
 */
public class AutonomousAct_Second extends BaseFragmentActivity {

    public static final int TAG_NOTIC = 0;
    public static final int TAG_VOTE = 1;
    public static final int TAG_NAMELIST = 2;
    public static final int TAG_MOTION = 3;
    public static final int TAG_SUPERVISE = 4;
    public static final int TAG_CERTIFICATION = 5;

    @Bind(R.id.auto_sec_img_back)
    ImageView img_Back;
    @Bind(R.id.auto_sec_tv_title)
    TextView tv_Title;
    @Bind(R.id.auto_sec_fraglayout)
    LinearLayout auto_Fraglayout;
//    @Bind(R.id.title_status_height)
//    LinearLayout mHeight;
int owner_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auto_second);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
//        if (!super.isVersionBiger()) {
//            mHeight.setVisibility(View.GONE);
//        }
        int tag = getIntent().getIntExtra("title", -1);

        owner_id = getIntent().getIntExtra("owner_id",-1);
        if (tag == -1) return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (tag) {
            case TAG_NOTIC:
                tv_Title.setText("公告");
                AutoFrag_Notice mNoticeFrag = new AutoFrag_Notice();
                ft.replace(R.id.auto_sec_fraglayout, mNoticeFrag, AutoFrag_Notice.FRAGMENTTAG);
                break;
            case TAG_VOTE:
                tv_Title.setText("投票");
                AutoFrag_Vote mVoteFrag = new AutoFrag_Vote();
                ft.replace(R.id.auto_sec_fraglayout, mVoteFrag, AutoFrag_Vote.FRAGMENTTAG);
                break;
            case TAG_NAMELIST:
                tv_Title.setText("名单");
                AutoFrag_NameList mNameList = new AutoFrag_NameList();
                ft.replace(R.id.auto_sec_fraglayout, mNameList, AutoFrag_NameList.FRAGMENTTAG);
                break;
            case TAG_MOTION:
                tv_Title.setText("提案");
                AutoFrag_Motion mMotion = new AutoFrag_Motion();
                ft.replace(R.id.auto_sec_fraglayout, mMotion, AutoFrag_Motion.FRAGMENTTAG);
                break;
            case TAG_SUPERVISE:
                tv_Title.setText("监督");
                AutoFrag_SuperVise mSuperVise = new AutoFrag_SuperVise();
                Bundle mBundle=new Bundle();
                if (owner_id!=-1){
                    mBundle.putInt("owner_id",owner_id);
                    mSuperVise.setArguments(mBundle);
                    ft.replace(R.id.auto_sec_fraglayout, mSuperVise, AutoFrag_SuperVise.FRAGMENTTAG);
                }
                break;
            case TAG_CERTIFICATION:
                tv_Title.setText("房屋管理");
                AutoFrag_Certigication mCertigication = new AutoFrag_Certigication();
                ft.replace(R.id.auto_sec_fraglayout, mCertigication, AutoFrag_Certigication.FRAGMENTTAG);
                break;
        }
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    @OnClick(R.id.auto_sec_img_back)
    public void onClick() {
        onBackPressed();
    }
}
