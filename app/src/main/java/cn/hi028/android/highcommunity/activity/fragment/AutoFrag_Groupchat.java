package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Second;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅认证完成主界面<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_Groupchat extends BaseFragment implements OnClickListener {
    public static final String Tag = "~~~AutonomousMainFrag~~~";
    public static final String FRAGMENTTAG = "AutonomousMainFrag";

    public static final int TAG_NOTIC =0;
    public static final int TAG_VOTE =1;
    public static final int TAG_NAMELIST =2;
    public static final int TAG_MOTION =3;
    public static final int TAG_SUPERVISE =4;
    public static final int TAG_GROUPCHAT =5;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_autonomous_identified2, null);
        ButterKnife.bind(this, view);
        findView(view);
        registerListener();


        initView();
        return view;
    }


    private void registerListener() {
//		payment.setOnClickListener(this);
//		tenement.setOnClickListener(this);
    }


    private void findView(View view) {


    }


    void initView() {
        LogUtil.d(Tag + "initView");

        initDatas();
    }

    private void initDatas() {

//        HTTPHelper.GetThirdService(mIbpi);
    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            LogUtil.d(Tag + "-------------  initView   onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
//            if (!isNoNetwork) {
//                mLoadingView.loadFailed();
//            }
        }

        @Override
        public void onSuccess(Object message) {
//			mLoadingView.loadSuccess();
//			mLoadingView.setVisibility(View.GONE);
//			LogUtil.d(Tag+"---~~~initViewonSuccess");
////						if (null == message) return;
//			LogUtil.d(Tag+"---~~~ initView   message:"+message);
//			ThirdServiceBean mBean = (ThirdServiceBean) message;
//			mAdapter.AddNewData(mBean.getServices());
//			mGridView.setAdapter(mAdapter);
//			pagerAdapter.setImageIdList(mBean.getBanners());
//			HighCommunityUtils.GetInstantiation()
//			.setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
//			tatalLayout.setVisibility(View.VISIBLE);

        }

        @Override
        public Object onResolve(String result) {
//			Log.e("renk", result);
//			LogUtil.d(Tag+"---~~~iresult"+result);
//			return HTTPHelper.ResolveThirdService(result);
            return null;
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
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(getActivity());
            }
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

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
Intent mIntent=new Intent(getActivity(), AutonomousAct_Second.class);
    @OnClick({R.id.autoFrg_notic, R.id.autoFrg_vote, R.id.autoFrg_namelist, R.id.autoFrg_motion, R.id.autoFrg_supervise, R.id.autoFrg_groupchat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoFrg_notic:
                mIntent.putExtra("title",TAG_NOTIC);
                startActivity(mIntent);
                break;
            case R.id.autoFrg_vote:
                mIntent.putExtra("title",TAG_VOTE);
                startActivity(mIntent);
                break;
            case R.id.autoFrg_namelist:
                mIntent.putExtra("title",TAG_NAMELIST);
                startActivity(mIntent);
                break;
            case R.id.autoFrg_motion:
                mIntent.putExtra("title",TAG_MOTION);
                startActivity(mIntent);
                break;
            case R.id.autoFrg_supervise:
                mIntent.putExtra("title",TAG_SUPERVISE);
                startActivity(mIntent);
                break;
            case R.id.autoFrg_groupchat:
                mIntent.putExtra("title",TAG_GROUPCHAT);
                startActivity(mIntent);
                break;
        }
    }





}
