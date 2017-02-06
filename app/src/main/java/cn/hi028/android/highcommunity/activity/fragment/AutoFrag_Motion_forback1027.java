package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.adapter.AutoMoitionAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅 公共提案<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */
public class AutoFrag_Motion_forback1027 extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_Motion~~~";
    public static final String FRAGMENTTAG = "AutoFrag_Motion";
    /**
     * 创建提案
     **/
    public static final int TAG_CREAT_MOTION = 7;
    AutoMoitionAdapter mAdapter;
    List<Auto_MotionBean.MotionDataEntity> mList;
    @Bind(R.id.tv_Automotion_Nodata)
    TextView tv_Nodata;
    @Bind(R.id.frag_Automotion_listview)
    ListView mListview;
    @Bind(R.id.img_Automotion_creat)
    ImageButton but_CreatMotion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_auto_public_motion, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        Log.d(Tag, "initView");
        mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
        mAdapter = new AutoMoitionAdapter(mList, getActivity(), getActivity().getWindow().getDecorView());
        mListview.setEmptyView(tv_Nodata);
        mListview.setAdapter(mAdapter);
    }

    private void initDatas() {

        HTTPHelper.GetAutoMotionList(mIbpi);
    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mList = (List<Auto_MotionBean.MotionDataEntity>) message;
            mAdapter.AddNewData(mList);
            mListview.setAdapter(mAdapter);
        }

        @Override
        public Object onResolve(String result) {
//			Log.e("renk", result);
            return HTTPHelper.ResolveMotionDataEntity(result);
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
                HighCommunityApplication.toLoginAgain(getActivity());
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.img_Automotion_creat)
    public void onClick() {

        ceratMotion();

    }

    private void ceratMotion() {

        Intent mIntent_report = new Intent(getActivity(), AutonomousAct_Third.class);
        mIntent_report.putExtra("title", TAG_CREAT_MOTION);
        startActivity(mIntent_report);

    }


}
