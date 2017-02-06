package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import cn.hi028.android.highcommunity.adapter.AutoSuperviseAdapter_Inq;
import cn.hi028.android.highcommunity.adapter.AutoSuperviseAdapter_Re;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SuperViseBean2;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅 监督页面<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */
public class AutoFrag_SuperVise extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_SuperVise:";
    public static final String FRAGMENTTAG = "AutoFrag_SuperVise";
    public static final int TAG_REPORT_DETAIL = 0;
    public static final int TAG_CREAT_REPORT = 5;
    public static final int TAG_CREAT_INQUIRY = 6;
    public static final boolean isMessage = false;
    /**
     * 创建留言
     **/
    public static final int TAG_CREAT_MESSAGE = 9;

    int owner_id;
    @Bind(R.id.frag_Supervise_Report)
    RadioButton but_Report;
    @Bind(R.id.frag_Supervise_Inquiry)
    RadioButton but_Inquiry;
    @Bind(R.id.frag_Supervise_Message)
    RadioButton but_mMessage;
    @Bind(R.id.frag_Supervise_RadioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.tv_Supervise_Nodata1)
    TextView tv_Nodata1;
    @Bind(R.id.tv_Supervise_Nodata2)
    TextView tv_Nodata2;
    @Bind(R.id.tv_Supervise_Nodata3)
    TextView tv_Nodata3;
    @Bind(R.id.frag_Supervise_listview_Report)
    ListView mListview_Report;
    @Bind(R.id.frag_Supervise_listview_Inquiry)
    ListView mListview_Inquiry;
    @Bind(R.id.frag_Supervise_listview_Message)
    ListView mListview_Message;
    List<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity> mReportList;
    List<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity> mInquiryList;
    List<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity> mMessageList;

    AutoSuperviseAdapter_Re mReportAdapter;
    AutoSuperviseAdapter_Inq mInquiryAdapter;
    AutoSuperviseAdapter_Inq mMessageAdapter;

    @Bind(R.id.listviewContainer)
    RelativeLayout listviewContainer;
    @Bind(R.id.img_Supervise_creat)
    ImageButton but_Creat;
    int selectId = -1;
    static final String user_type = HighCommunityApplication.mUserInfo.getUser_Type();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_auto_superviselist, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        owner_id = bundle.getInt("owner_id", -1);
        Log.d(Tag, "owner_id=" + owner_id);
        Log.d(Tag, "user_type=" + user_type);
        initView();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    TextView emptyView;
    TextView emptyView2;
    TextView emptyView3;

    public void initView() {
        emptyView = new TextView(getActivity());
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无汇报数据");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) mListview_Report.getParent()).addView(emptyView);
        emptyView2 = new TextView(getActivity());
        emptyView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView2.setText("暂无询问数据");
        emptyView2.setGravity(Gravity.CENTER);
        emptyView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView2.setVisibility(View.GONE);
        ((ViewGroup) mListview_Inquiry.getParent()).addView(emptyView2);
        emptyView3 = new TextView(getActivity());
        emptyView3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView3.setText("暂无留言数据");
        emptyView3.setGravity(Gravity.CENTER);
        emptyView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView3.setVisibility(View.GONE);
        ((ViewGroup) mListview_Message.getParent()).addView(emptyView3);
        mReportList = new ArrayList<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity>();
        mInquiryList = new ArrayList<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity>();
        mMessageList = new ArrayList<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity>();

        mReportAdapter = new AutoSuperviseAdapter_Re(mReportList, getActivity());
        mInquiryAdapter = new AutoSuperviseAdapter_Inq(mInquiryList, getActivity(), false);
        mMessageAdapter = new AutoSuperviseAdapter_Inq(mMessageList, getActivity(), true);

        mListview_Report.setEmptyView(tv_Nodata1);
        mListview_Inquiry.setEmptyView(tv_Nodata1);
        mListview_Message.setEmptyView(tv_Nodata1);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.frag_Supervise_Report:
                        emptyView2.setVisibility(View.GONE);
                        emptyView3.setVisibility(View.GONE);
                        if (mReportList.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        selectId = TAG_CREAT_REPORT;
                        mListview_Report.setVisibility(View.VISIBLE);
                        mListview_Inquiry.setVisibility(View.GONE);
                        mListview_Message.setVisibility(View.GONE);
                        if (user_type.equals("2")) {
                            but_Creat.setVisibility(View.VISIBLE);
                        } else {
                            but_Creat.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.frag_Supervise_Inquiry:
                        emptyView.setVisibility(View.GONE);
                        emptyView3.setVisibility(View.GONE);
                        if (mInquiryList.size() == 0) {
                            emptyView2.setVisibility(View.VISIBLE);
                        }
                        selectId = TAG_CREAT_INQUIRY;
                        mListview_Inquiry.setVisibility(View.VISIBLE);
                        mListview_Report.setVisibility(View.GONE);
                        mListview_Message.setVisibility(View.GONE);
                        if (user_type.equals("3")) {
                            but_Creat.setVisibility(View.VISIBLE);
                        } else {
                            but_Creat.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.frag_Supervise_Message:
                        emptyView.setVisibility(View.GONE);
                        emptyView2.setVisibility(View.GONE);
                        if (mMessageList.size() == 0) {
                            emptyView3.setVisibility(View.VISIBLE);
                        }
                        selectId = TAG_CREAT_MESSAGE;
                        mListview_Message.setVisibility(View.VISIBLE);
                        mListview_Inquiry.setVisibility(View.GONE);
                        mListview_Report.setVisibility(View.GONE);
                        if (user_type.equals("1")) {
                            but_Creat.setVisibility(View.VISIBLE);
                        } else {
                            but_Creat.setVisibility(View.GONE);
                        }
                        if (isOwner) {
                            but_Creat.setVisibility(View.VISIBLE);
                        } else {
                            but_Creat.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });
        but_Report.setChecked(true);
    }

    private void initDatas() {
        HTTPHelper.GetAutoSuperviseList(mIbpi, owner_id);
    }

    boolean isOwner;
    Auto_SuperViseBean2.SuperViseBean2DataEntity mData;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) {
                return;
            }
            mData = (Auto_SuperViseBean2.SuperViseBean2DataEntity) message;
            if (mData != null) {
                mReportList = mData.getHuibao();
                if (mReportList == null || mReportList.size() == 0) {
                    mReportList = new ArrayList<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity>();
                }
                if (user_type.equals("1")) {
                    isOwner = true;
                } else {
                    isOwner = false;
                }
                mListview_Report.setEmptyView(tv_Nodata1);
                mReportAdapter.ClearData();
                mReportAdapter.AddNewData(mReportList);
                mListview_Report.setAdapter(mReportAdapter);
                mInquiryList = mData.getXunwen();
                if (mInquiryList == null || mInquiryList.size() == 0) {
                    mInquiryList = new ArrayList<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity>();
                }
                mListview_Inquiry.setEmptyView(tv_Nodata1);
                mInquiryAdapter.ClearData();
                mInquiryAdapter.AddNewData(mInquiryList);
                mListview_Inquiry.setAdapter(mInquiryAdapter);
                mMessageList = mData.getLiuyan();
                if (mMessageList == null || mMessageList.size() == 0) {
                    mMessageList = new ArrayList<Auto_SuperViseBean2.SuperViseBean2DataEntity.SuperViseDataEntity>();
                }
                mListview_Message.setEmptyView(tv_Nodata1);
                mMessageAdapter.ClearData();
                mMessageAdapter.AddNewData(mMessageList);
                mListview_Message.setAdapter(mMessageAdapter);

                mListview_Report.setEmptyView(emptyView);
                mListview_Inquiry.setEmptyView(emptyView2);
                mListview_Message.setEmptyView(emptyView3);
                initVisibility();
            }

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSuperViseDataEntity2(result);
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

    private void initVisibility() {
        if (but_Report.isChecked()) {
            emptyView2.setVisibility(View.GONE);
            emptyView3.setVisibility(View.GONE);
            if (mReportList.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            }
            listviewContainer.setVisibility(View.VISIBLE);
            mListview_Inquiry.setVisibility(View.GONE);
            mListview_Report.setVisibility(View.VISIBLE);
            mListview_Message.setVisibility(View.GONE);
            if (user_type.equals("2")) {
                but_Creat.setVisibility(View.VISIBLE);
            } else {
                but_Creat.setVisibility(View.GONE);
            }
        } else if (but_Inquiry.isChecked()) {
            emptyView.setVisibility(View.GONE);
            emptyView3.setVisibility(View.GONE);
            if (mInquiryList.size() == 0) {
                emptyView2.setVisibility(View.VISIBLE);
            }
            listviewContainer.setVisibility(View.VISIBLE);
            mListview_Inquiry.setVisibility(View.VISIBLE);
            mListview_Report.setVisibility(View.GONE);
            mListview_Message.setVisibility(View.GONE);
            if (user_type.equals("3")) {
                but_Creat.setVisibility(View.VISIBLE);
            } else {
                but_Creat.setVisibility(View.GONE);
            }
        } else if (but_mMessage.isChecked()) {
            emptyView.setVisibility(View.GONE);
            emptyView2.setVisibility(View.GONE);
            if (mMessageList.size() == 0) {
                emptyView3.setVisibility(View.VISIBLE);
            }
            listviewContainer.setVisibility(View.VISIBLE);
            mListview_Inquiry.setVisibility(View.GONE);
            mListview_Report.setVisibility(View.GONE);
            mListview_Message.setVisibility(View.VISIBLE);
            if (selectId == TAG_CREAT_MESSAGE) {
                if (user_type.equals("1")) {
                    but_Creat.setVisibility(View.VISIBLE);

                } else {
                    but_Creat.setVisibility(View.GONE);

                }
            }
        }

    }


    @OnClick(R.id.img_Supervise_creat)
    public void onClick() {
        if (selectId == TAG_CREAT_REPORT) {
            ceratReport();
        } else if (selectId == TAG_CREAT_INQUIRY) {
            creatInquiry();

        } else if (selectId == TAG_CREAT_MESSAGE) {
            creatMessage();
        }
    }

    private void creatInquiry() {
        Intent mIntent_report = new Intent(getActivity(), AutonomousAct_Third.class);
        mIntent_report.putExtra("title", TAG_CREAT_INQUIRY);
        mIntent_report.putExtra("owner_id", owner_id);
        startActivity(mIntent_report);
    }

    private void creatMessage() {
        Intent mIntent_report = new Intent(getActivity(), AutonomousAct_Third.class);
        mIntent_report.putExtra("title", TAG_CREAT_MESSAGE);
        mIntent_report.putExtra("owner_id", owner_id);
        startActivity(mIntent_report);
    }

    private void ceratReport() {

        Intent mIntent_report = new Intent(getActivity(), AutonomousAct_Third.class);
        mIntent_report.putExtra("title", TAG_CREAT_REPORT);
        mIntent_report.putExtra("owner_id", owner_id);
        startActivity(mIntent_report);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
