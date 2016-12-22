package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.ShowVoteResultAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteResultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.LinearForVoteResult;

public class Auto_Five extends BaseFragmentActivity {

    String Tag = "~~~ AutoAct_Four";

    public static final String ACTIVITYTAG = "AutoAct_Four";
    public static final String INTENTTAG = "AutoAct_Four";
    String title_id;
    @Bind(R.id.auto_four_img_back)
    ImageView mImgBack;

    @Bind(R.id.four_content_title)
    TextView mrContentTitle;
    @Bind(R.id.four_listiew)
    LinearForVoteResult mListiew;
    @Bind(R.id.four_back)
    Button mfourBack;
    List<Auto_VoteResultBean.VoteResultDataEntity> mList=new ArrayList<Auto_VoteResultBean.VoteResultDataEntity>();
    ShowVoteResultAdapter mAdapter;
    @Bind(R.id.auto_four_tv_title)
    TextView mFourTvTitle;
    String vote_title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auto_act_four);
        ButterKnife.bind(this);
        title_id = getIntent().getStringExtra("title_id");
//        initView();
        initDtas();
    }

    private void initDtas() {
        HTTPHelper.getSingleVotedData(mIbpi, title_id);
    }

    Auto_VoteResultBean.VoteResultDataEntity mData;
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
            mData= (Auto_VoteResultBean.VoteResultDataEntity) message;
//            Log.d(Tag, "onSuccess message 不空");
            mList.add(mData);
            setData();
            mAdapter = new ShowVoteResultAdapter(Auto_Five.this,mList);
//                mList = mData.getOptions();
            mListiew.setAdapter(mAdapter);
            mfourBack.setVisibility(View.VISIBLE);
        }

        private void setData() {
            mrContentTitle.setText(vote_title);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSingleVotedDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {
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
