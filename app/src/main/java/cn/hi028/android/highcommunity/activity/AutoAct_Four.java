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
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.AutoVotedResultAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteResultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.view.MyNoScrollListview;

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
    MyNoScrollListview  mListiew;
    @Bind(R.id.four_back)
    Button mfourBack;
    List<Auto_VoteResultBean.VoteResultDataEntity.VoteResultOptionsEntity> mList;
    AutoVotedResultAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auto_act_four);
        ButterKnife.bind(this);
        vote_id = getIntent().getStringExtra("vote_id");
        initView();
        initDtas();
    }

    private void initView() {

        mAdapter=new AutoVotedResultAdapter(mList,this);
//        mListiew.addFooterView(mfourBack);
        mListiew.setAdapter(mAdapter);

    }

    private void initDtas() {
        HTTPHelper.getVotedData(mIbpi,vote_id);
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
                Log.d(Tag,"onSuccess message null");
                return;
            }
            Log.d(Tag,"onSuccess message 不空");
            mBeanList = (List<Auto_VoteResultBean.VoteResultDataEntity>) message;
            mData=mBeanList.get(0);
            if (mData!=null){

                setData();
                mList= mData.getOptions();
                mAdapter.AddNewData(mList);
                mListiew.setAdapter(mAdapter);
            }
        }

        private void setData() {
            mrContentTitle.setText(mData.getTitle());
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
    };
    @OnClick({R.id.auto_four_img_back, R.id.four_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_four_img_back:
                onBackPressed();
                break;
            case R.id.four_back:
                onBackPressed();
                break;
        }
    }
}
