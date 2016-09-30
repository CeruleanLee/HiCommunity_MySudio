package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.TicketAdapter;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * 优惠券界面Activity
 * Created by 赵海 on 2016/2/29.
 */
@EActivity(resName = "act_ticket")
public class TicketAct extends BaseFragmentActivity {
    @ViewById(R.id.tv_secondtitle_name)
    TextView mTitle;
    @ViewById(R.id.progress_ticket_notice)
    View mProgress;
    @ViewById(R.id.tv_ticket_Nodata)
    TextView mNodata;
    @ViewById(R.id.img_back)
    ImageView img_back;
    @ViewById(R.id.ptrlv_ticket_listView)
    PullToRefreshListView mListView;
    TicketAdapter adapter;
    public static String TICKET_TYPE = "ticket_type";
    public static String TICKET_PRICE = "ticket_price";
    public static int TICKET_RESULT = 0x23;

    @AfterViews
    public void initView() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText("优惠券");
        mProgress.setVisibility(View.VISIBLE);
        mNodata.setVisibility(View.GONE);
        adapter = new TicketAdapter(this, getIntent().getIntExtra(TICKET_TYPE, 0));
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mNodata);
        HTTPHelper.getTicketList(mIbpi, getIntent().getIntExtra(TICKET_TYPE, 0) + "", getIntent().getStringExtra(TICKET_PRICE));
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setText(message);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            adapter.setData((List<AllTicketBean>) message);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveTicket(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };
}
