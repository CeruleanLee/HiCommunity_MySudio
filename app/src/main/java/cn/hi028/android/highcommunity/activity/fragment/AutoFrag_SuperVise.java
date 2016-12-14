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
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.adapter.AutoSuperviseAdapter_Inq;
import cn.hi028.android.highcommunity.adapter.AutoSuperviseAdapter_Re;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SuperViseBean;
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
    @Bind(R.id.tv_Supervise_Nodata)
    TextView tv_Nodata;
    @Bind(R.id.frag_Supervise_listview_Report)
    ListView mListview_Report;
    @Bind(R.id.frag_Supervise_listview_Inquiry)
    ListView mListview_Inquiry;
    @Bind(R.id.frag_Supervise_listview_Message)
    ListView mListview_Message;
    List<List<Auto_SuperViseBean.SuperViseDataEntity>> mList;
    List<Auto_SuperViseBean.SuperViseDataEntity> mReportList;
    List<Auto_SuperViseBean.SuperViseDataEntity> mInquiryList;
    List<Auto_SuperViseBean.SuperViseDataEntity> mMessageList;

    AutoSuperviseAdapter_Re mReportAdapter;
    AutoSuperviseAdapter_Inq mInquiryAdapter;
    AutoSuperviseAdapter_Inq mMessageAdapter;

    @Bind(R.id.listviewContainer)
    RelativeLayout listviewContainer;
    @Bind(R.id.img_Supervise_creat)
    ImageButton but_Creat;
    int selectId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("HJJ", "ArrayListFragment **** onCreateView...");
        View view = inflater.inflate(R.layout.frag_auto_superviselist, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        owner_id = bundle.getInt("owner_id", -1);
        Log.d(Tag, "owner_id=" + owner_id);
        initView();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("HJJ", "ArrayListFragment **** onResume...");
        initDatas();
        Log.e(Tag, "selectId=="+selectId);
        Log.e(Tag, "isOwner=="+isOwner);
//        if (selectId==TAG_CREAT_MESSAGE) {
//
//            if (isOwner) {
//                but_Creat.setVisibility(View.VISIBLE);
//            } else {
//                but_Creat.setVisibility(View.GONE);
//            }
//        }
    }


    public void initView() {
        Log.e(Tag, "initView");
        mListview_Report.setEmptyView(tv_Nodata);
        mListview_Inquiry.setEmptyView(tv_Nodata);
        mListview_Message.setEmptyView(tv_Nodata);
        mReportList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
        mInquiryList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
        mMessageList = new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();

        mReportAdapter = new AutoSuperviseAdapter_Re(mReportList, getActivity());
        mInquiryAdapter = new AutoSuperviseAdapter_Inq(mInquiryList, getActivity(), false);
        mMessageAdapter = new AutoSuperviseAdapter_Inq(mMessageList, getActivity(), true);

//        mListview_Report.setAdapter(mReportAdapter);
//        mListview_Inquiry.setAdapter(mInquiryAdapter);
        but_Report.setChecked(true);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.frag_Supervise_Report:
//                        isReportSelected = true;
                        selectId = TAG_CREAT_REPORT;
                        mListview_Report.setVisibility(View.VISIBLE);
                        mListview_Inquiry.setVisibility(View.GONE);
                        mListview_Message.setVisibility(View.GONE);
                        if (isOwner) {
                            but_Creat.setVisibility(View.GONE);
                        }else {
                            but_Creat.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.frag_Supervise_Inquiry:
//                        isReportSelected = false;
                        selectId = TAG_CREAT_INQUIRY;
                        mListview_Inquiry.setVisibility(View.VISIBLE);
                        mListview_Report.setVisibility(View.GONE);
                        mListview_Message.setVisibility(View.GONE);
                        if (isOwner) {
                            but_Creat.setVisibility(View.GONE);
                        }else {
                            but_Creat.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.frag_Supervise_Message:
                        selectId = TAG_CREAT_MESSAGE;
                        mListview_Message.setVisibility(View.VISIBLE);
                        mListview_Inquiry.setVisibility(View.GONE);
                        mListview_Report.setVisibility(View.GONE);
                        if (isOwner) {
                            but_Creat.setVisibility(View.VISIBLE);
                        } else {
                            but_Creat.setVisibility(View.GONE);


                        }
                        break;
                }
            }
        });

    }

    private void initDatas() {
        Log.e("HJJ", "ArrayListFragment **** initDatas...");
        HTTPHelper.GetAutoSuperviseList(mIbpi, owner_id);
    }

    boolean isOwner;

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.e(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mList = (List<List<Auto_SuperViseBean.SuperViseDataEntity>>) message;
            Log.d(Tag, "list 长度" + mList.size());
            Log.d(Tag, "list string" + mList.toString());

            if (mList != null) {
                mReportList = mList.get(0);
                Log.d(Tag, "list type" + mReportList.get(0).getType());

                if (mReportList.get(0).getType() != null && mReportList.get(0).getType().equals("1")) {
                    isOwner = true;
                    but_Creat.setVisibility(View.GONE);
                    but_mMessage.setVisibility(View.VISIBLE);
                } else {
                    isOwner = false;
//                    but_mMessage.setVisibility(View.GONE);
                    but_Creat.setVisibility(View.VISIBLE);
                }


                mReportAdapter.AddNewData(mReportList);
                mListview_Report.setAdapter(mReportAdapter);

                mInquiryList = mList.get(1);
                mInquiryAdapter.AddNewData(mInquiryList);
                mListview_Inquiry.setAdapter(mInquiryAdapter);

                mMessageList = mList.get(2);
                mMessageAdapter.AddNewData(mMessageList);
                mListview_Message.setAdapter(mMessageAdapter);

                initVisibility();
            }
//            if (!isFirstLoading){
//                isFirstLoading=true;
//            }

        }

        @Override
        public Object onResolve(String result) {
//			LogUtil.d(Tag+"---~~~iresult"+result);
            return HTTPHelper.ResolveSuperViseDataEntity(result);
//            return null;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    private void initVisibility() {
        if (but_Report.isChecked()) {
            listviewContainer.setVisibility(View.VISIBLE);
            mListview_Inquiry.setVisibility(View.GONE);
            mListview_Report.setVisibility(View.VISIBLE);
            mListview_Message.setVisibility(View.GONE);
            if (isOwner) {
                but_Creat.setVisibility(View.GONE);
            }else {
                but_Creat.setVisibility(View.VISIBLE);
            }

        } else if (but_Inquiry.isChecked()) {

            listviewContainer.setVisibility(View.VISIBLE);
            mListview_Inquiry.setVisibility(View.VISIBLE);
            mListview_Report.setVisibility(View.GONE);
            mListview_Message.setVisibility(View.GONE);
            if (isOwner) {
                but_Creat.setVisibility(View.GONE);
            }else {
                but_Creat.setVisibility(View.VISIBLE);
            }
        } else if (but_mMessage.isChecked()) {

            listviewContainer.setVisibility(View.VISIBLE);
            mListview_Inquiry.setVisibility(View.GONE);
            mListview_Report.setVisibility(View.GONE);
            mListview_Message.setVisibility(View.VISIBLE);
            if (selectId==TAG_CREAT_MESSAGE) {

                if (isOwner) {
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
//Intent mIntent=new Intent(getActivity(), AutonomousAct_Second.class);

}
