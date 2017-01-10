package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.ShowVoteResultAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteResultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LinearForVoteResult;

/**
 * 投票结果展示
 */
public class AutoAct_Four extends BaseFragmentActivity {

    String Tag = "~~~ AutoAct_Four";

    public static final String ACTIVITYTAG = "AutoAct_Four";
    public static final String INTENTTAG = "AutoAct_Four";
    String vote_id;
    @Bind(R.id.auto_four_img_back)
    ImageView mImgBack;

    @Bind(R.id.four_content_title)
    TextView mrContentTitle;
    @Bind(R.id.four_listiew)
    LinearForVoteResult mListiew;
    @Bind(R.id.four_back)
    Button mfourBack;
    List<Auto_VoteResultBean.VoteResultDataEntity> mList;
    ShowVoteResultAdapter mAdapter;
    @Bind(R.id.auto_four_tv_title)
    TextView mFourTvTitle;
String vote_title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auto_act_four);
        ButterKnife.bind(this);
        vote_id = getIntent().getStringExtra("vote_id");
        vote_title = getIntent().getStringExtra("vote_title");
//        initView();
        initDtas();
    }

    private void initView() {


//        mListiew.addFooterView(mfourBack);
        mListiew.setAdapter(mAdapter);

    }

    private void initDtas() {
        HTTPHelper.getVotedData(mIbpi, vote_id);
    }

    Auto_VoteResultBean.VoteResultDataEntity mData;
    List<Auto_VoteResultBean.VoteResultDataEntity> mBeanList;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message) {
                Log.d(Tag, "onSuccess message null");
                return;
            }
            Log.d(Tag, "onSuccess message 不空");
            mList = (List<Auto_VoteResultBean.VoteResultDataEntity>) message;
            setData();
            mAdapter = new ShowVoteResultAdapter(AutoAct_Four.this,mList);
//                mList = mData.getOptions();
                mListiew.setAdapter(mAdapter);
            mfourBack.setVisibility(View.VISIBLE);
        }

        private void setData() {
            mrContentTitle.setText(vote_title);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveVoteResultDataEntity(result);
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
                HighCommunityApplication.toLoginAgain(AutoAct_Four.this);
            }
        }
    };

    @OnClick({R.id.auto_four_img_back, R.id.four_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_four_img_back:
                this.finish();

//                onBackPressed();
                break;
            case R.id.four_back:
//                onBackPressed();
                this.finish();
                break;
        }
    }
}
