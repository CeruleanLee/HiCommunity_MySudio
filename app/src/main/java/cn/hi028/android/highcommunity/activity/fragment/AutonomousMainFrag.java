package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutoCommitAct;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Second;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InitBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.view.ECAlertDialog;

/**
 * @功能：自治大厅认证完成主界面<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutonomousMainFrag extends BaseFragment implements OnClickListener {
    public static final String Tag = "~~~AutonomousMain";
    public static final String FRAGMENTTAG = "AutonomousMainFrag";

    public static final int TAG_NOTIC = 0;
    public static final int TAG_VOTE = 1;
    public static final int TAG_NAMELIST = 2;
    public static final int TAG_MOTION = 3;
    public static final int TAG_SUPERVISE = 4;
    public static final int TAG_CERTIFICATION = 5;


    @Bind(R.id.autoFrg_notic)
    RelativeLayout auto_Notice;
    @Bind(R.id.autoFrg_vote)
    RelativeLayout auto_Vote;
    @Bind(R.id.autoFrg_namelist)
    RelativeLayout auto_Namelist;
    @Bind(R.id.autoFrg_motion)
    RelativeLayout auto_Motion;
    @Bind(R.id.autoFrg_supervise)
    RelativeLayout auto_Supervise;
    @Bind(R.id.autoFrg_groupchat)
    RelativeLayout auto_Groupchat;
    View contentView;
    Context context;

    Intent mIntent;
    Intent intent;
    boolean isVerified, isCommitData;
    public Auto_InitBean.Auto_Init_DataEntity mData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.frag_autonomous_identified2, null);
        ButterKnife.bind(this, contentView);
        initView();
    }

    private void initView() {
        if (context == null) {
            Log.d(Tag, "content null");
        } else {

            Log.d(Tag, "content  不null");
            mIntent = new Intent(context, AutonomousAct_Second.class);
            intent = new Intent(getActivity(), AutoCommitAct.class);
        }
//        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag,"onCreateView");
//        Bundle bundle = getArguments();
//        mData = bundle.getParcelable("data");
        return contentView;
    }

    private void initDatas() {
        Log.d(Tag,"initDatas");
        HTTPHelper.InitAutoAct(mIbpi);
        Log.d(Tag,"initDatas2");

    }

    boolean isLocationNoData;
    int mStatus;
    String errorStr;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
//            Toast.makeText(getActivity(), "访问shibai", Toast.LENGTH_SHORT).show();
//            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
            errorStr = message;
            isLocationNoData = true;
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message) {
                return;
            }
            mData = (Auto_InitBean.Auto_Init_DataEntity) message;
            isLocationNoData = false;
            mStatus = mData.getStatus();
            if (mData.getType() != -1) {

                HighCommunityApplication.mUserInfo.setUser_Type(mData.getType() + "");
                Log.e(Tag,"用户业主大厅属性："+ HighCommunityApplication.mUserInfo.getUser_Type());
            }
            if (mData.getOwner_id() != -1) {

                HighCommunityApplication.mUserInfo.setOwner_id(mData.getOwner_id() + "");
            }
//            Toast.makeText(getActivity(), "mStatus " + mStatus, Toast.LENGTH_SHORT).show();
            if (mStatus == 1) {

                isVerified = true;
            } else {
                isVerified = false;

            }
            if (mStatus == 0 || mStatus == -1) {
                isCommitData = true;
            } else {
                isCommitData = false;
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

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Tag,"onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Tag,"onResume");
        initDatas();

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.autoFrg_notic, R.id.autoFrg_vote, R.id.autoFrg_namelist, R.id.autoFrg_motion, R.id.autoFrg_supervise, R.id.autoFrg_groupchat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoFrg_notic:
                if (isVerified) {
                    mIntent.putExtra("title", TAG_NOTIC);
                    startActivity(mIntent);
                } else {
                    showDialog();
                }
                break;
            case R.id.autoFrg_vote:
                if (isVerified) {

                    mIntent.putExtra("title", TAG_VOTE);
                    startActivity(mIntent);
                } else {
                    showDialog();
                }
                break;
            case R.id.autoFrg_namelist:
                if (isVerified) {

                    mIntent.putExtra("title", TAG_NAMELIST);
                    startActivity(mIntent);
                } else {
                    showDialog();
                }
                break;
            case R.id.autoFrg_motion:
                if (isVerified) {
                    mIntent.putExtra("title", TAG_MOTION);
                    startActivity(mIntent);
                } else {
                    showDialog();
                }
                break;

            case R.id.autoFrg_supervise:
                if (isVerified) {
                    mIntent.putExtra("title", TAG_SUPERVISE);
                    mIntent.putExtra("owner_id", mData.getOwner_id());
                    startActivity(mIntent);
                } else {
                    showDialog();
                }

                break;
            case R.id.autoFrg_groupchat:
                //certification
                mIntent.putExtra("title", TAG_CERTIFICATION);
//                mIntent.putExtra("owner_id", mData.getOwner_id());
                startActivity(mIntent);


//                if (isVerified) {
//                    Toast.makeText(getActivity(), "功能完善中，敬请期待~", Toast.LENGTH_SHORT).show();
////                mIntent.putExtra("title", TAG_GROUPCHAT);
////                startActivity(mIntent);
//                } else {
//                    showDialog();
//                }
                break;
        }
    }

    private void showDialog() {
        if (isLocationNoData) {
            //该小区无数据
            showNoDataDialog();
        } else if (isCommitData) {
            //已经提交了数据在审核状态
            showCheckingDialog();
        } else {
            //去提交数据界面
            showCommitDialog();
        }

    }



    /****
     * 该小区无数据弹窗
     */
    private void showNoDataDialog() {

        ECAlertDialog dialog2 = ECAlertDialog.buildAlert(getActivity(), errorStr, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        dialog2.show();


    }

    /****
     * 前往资料填写弹窗
     */
    public void showCommitDialog() {
        ECAlertDialog dialog2 = ECAlertDialog.buildAlert(getActivity(), "该功能需进行业主认证，前往认证？", "确定", "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                intent.putExtra("isCommitData", isCommitData);
                intent.putExtra("mStatus", mStatus);
                startActivity(intent);
            }
        }, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        dialog2.show();
    }

    Bundle mBundle = new Bundle();

    /****
     * 前往资料审核弹窗
     */
    public void showCheckingDialog() {
        ECAlertDialog dialog2 = ECAlertDialog.buildAlert(getActivity(), "资料审核中，前往查看审核状态", "确定", "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

//                if (mData!=null){
//
//                    Log.d(Tag,"mData "+mData.toString());
//                    mBundle.putParcelable("data",mData);
//                    start
//                }
                if (mData != null) {
                    intent.setExtrasClassLoader(getClass().getClassLoader());
                    intent.putExtra("isCommitData", isCommitData);
//    intent.setClassLoader(getClass().getClassLoader());
                    intent.putExtra("mData", mData);
                    intent.putExtra("mStatus", mStatus);
                    startActivity(intent);
                } else {
//    Toast.makeText(getActivity(),"data null",Toast.LENGTH_SHORT).show();
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        dialog2.show();
    }
}
