package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_QuestionDeatailBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅 问卷调查详情Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoDetail_Questions extends BaseFragment {
    public static final String Tag = "~~~AutoDetail_Questions~~~";
    public static final String FRAGMENTTAG = "AutoDetail_Questions";

    Auto_QuestionDeatailBean.QuestionDeatailDataEntity mBean;
    String motion_id;
    @Bind(R.id.txt_title)
    TextView mTitle;
    @Bind(R.id.lly_test)
    LinearLayout test_layout;
    @Bind(R.id.submit)
    Button mSubmit;
Context mContext;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_detail_questions, null);
        ButterKnife.bind(this, view);
//        Bundle bundle = getArguments();
//        motion_id = bundle.getString("motion_id");
//        Log.d(Tag, "motion_id:" + motion_id);

        initView();
        return view;
    }


    private void initView() {
        LogUtil.d(Tag + "initView");
//        mProgress.setVisibility(View.VISIBLE);
//        mCommentListview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//        mList = new ArrayList<Auto_ReportDetailBean.ReportDetailDataEntity.ReportDetailReplyEntity>();
//        mAdapter = new Auto_ReportDetailAdapter(mList, getActivity(), this);

        initDatas();
    }


    private void initDatas() {
        mContext=getActivity();
        mInflater=LayoutInflater.from(mContext);
//TODO 这里的id 是测试值   需要改
        HTTPHelper.GetQuestionDetail(mIbpi, 2 + "");
    }
    //问题列表
    List<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity> questionList;
    //答案列表
    List<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity.QuestionDeatailOptionsEntity> answerList;
    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater mInflater;

    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist=new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) {
                return;
            }
            mBean = (Auto_QuestionDeatailBean.QuestionDeatailDataEntity) message;
            initQuestionHead();
            questionList=mBean.getTitles();
            for (int i = 0; i < questionList.size(); i++) {
                que_view=mInflater.inflate(R.layout.frag_auto_detail_questions_question_layout,null);
                TextView txt_que=(TextView)que_view.findViewById(R.id.txt_question_item);
               //答案选项要加入的 布局
                LinearLayout add_layout=(LinearLayout)que_view.findViewById(R.id.lly_answer);
if (questionList.get(i).getType().equals("1")){//单选
    set(txt_que,questionList.get(i).getName(),1);

}else{
    set(txt_que,questionList.get(i).getName(),2);

}
answerList=questionList.get(i).getOptions();
                imglist2=new ArrayList<ImageView>();
                for (int j = 0; j < answerList.size(); j++) {
                    ans_view=mInflater.inflate(R.layout.frag_auto_detail_questions_answer_layout, null);
                    TextView txt_ans=(TextView)ans_view.findViewById(R.id.txt_answer_item);
                    ImageView image=(ImageView)ans_view.findViewById(R.id.image);
                    View line_view=ans_view.findViewById(R.id.vw_line);
//                    if(j==the_answer_list.size()-1){
//                        //最后一条答案下面不要线是指布局的问题
//                        line_view.setVisibility(View.GONE);
//                    }
//                    //判断单选多选加载不同选项图片
//                    if(the_quesition_list.get(i).getType().equals("1")){
//                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
//                    }else{
//                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
//                    }
//                    Log.e("---", "------"+image);
//                    imglist2.add(image);
//                    txt_ans.setText(the_answer_list.get(j).getAnswer_content());
//                    LinearLayout lly_answer_size=(LinearLayout)ans_view.findViewById(R.id.lly_answer_size);
//                    lly_answer_size.setOnClickListener(new answerItemOnClickListener(i,j,the_answer_list,txt_ans));
//                    add_layout.addView(ans_view);
                }
 /*for(int r=0; r<imglist2.size();r++){
                Log.e("---", "imglist2--------"+imglist2.get(r));
            }*/

                imglist.add(imglist2);

                test_layout.addView(que_view);

            }
//            mList = mBean.getReply();
//            mAdapter.AddNewData(mList);
//            mCommentListview.setAdapter(mAdapter);
//            mList = (List<Auto_NoticeListBean.NoticeListDataEntity>) message;
//            mAdapter.AddNewData(mList);
//            mListview.setAdapter(mAdapter);


        }

        private void initQuestionHead() {
            mTitle.setText(mBean.getVote().getTitle());

        }

        @Override
        public Object onResolve(String result) {
            LogUtil.d(Tag + " ~~~result" + result);
            return HTTPHelper.ResolveQuestionDeatailDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {

        }
    };
    private void set(TextView tv_test, String content,int type) {
        //为了加载问题后面的* 和*多选
        // TODO Auto-generated method stub
        String w;
        if(type==1){
            w = content+"*[多选题]";
        }else{
            w = content+"*";
        }

        int start = content.length();
        int end = w.length();
        Spannable word = new SpannableString(w);
        word.setSpan(new AbsoluteSizeSpan(25), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_test.setText(word);
    }


//    class answerItemOnClickListener implements View.OnClickListener {
//        private int i;
//        private int j;
//        private TextView txt;
//        private ArrayList<Answer> the_answer_lists;
//        public answerItemOnClickListener(int i,int j, ArrayList<Answer> the_answer_list,TextView text){
//            this.i=i;
//            this.j=j;
//            this.the_answer_lists=the_answer_list;
//            this.txt=text;
//
//        }
//        //实现点击选项后改变选中状态以及对应图片
//        @Override
//        public void onClick(View arg0) {
//            // TODO Auto-generated method stub
//            //判断当前问题是单选还是多选
//            /*Log.e("------", "选择了-----第"+i+"题");
//            for(int q=0;q<imglist.size();q++){
//                for(int w=0;w<imglist.get(q).size();w++){
////                  Log.e("---", "共有------"+imglist.get(q).get(w));
//                }
//            }
//            Log.e("----", "点击了---"+imglist.get(i).get(j));*/
//
//            if(the_quesition_list.get(i).getType().equals("1")){
//                //多选
//                if(the_answer_lists.get(j).getAns_state()==0){
//                    //如果未被选中
//                    txt.setTextColor(Color.parseColor("#EA5514"));
//                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_true));
//                    the_answer_lists.get(j).setAns_state(1);
//                    the_quesition_list.get(i).setQue_state(1);
//                }else{
//                    txt.setTextColor(Color.parseColor("#595757"));
//                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
//                    the_answer_lists.get(j).setAns_state(0);
//                    the_quesition_list.get(i).setQue_state(1);
//                }
//            }else{
//                //单选
//
//                for(int z=0;z<the_answer_lists.size();z++){
//                    the_answer_lists.get(z).setAns_state(0);
//                    imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
//                }
//                if(the_answer_lists.get(j).getAns_state()==0){
//                    //如果当前未被选中
//                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_true));
//                    the_answer_lists.get(j).setAns_state(1);
//                    the_quesition_list.get(i).setQue_state(1);
//                }else{
//                    //如果当前已被选中
//                    the_answer_lists.get(j).setAns_state(1);
//                    the_quesition_list.get(i).setQue_state(1);
//                }
//
//            }
//            //判断当前选项是否选中
//
//
//
//        }
//
//    }



    BpiHttpHandler.IBpiHttpHandler mCommentIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
//            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccess(Object message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
//            if (isReplay) {
//                mAdapter.setNewData(isReplay, content, null);
//            } else {
//                mAdapter.setNewData(isReplay, content, message.toString());
//            }
            initDatas();
        }


        @Override
        public Object onResolve(String result) {

            return result;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };


    /**********
     * --------------------------------------------------------------------
     **********/

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(Tag + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");

        //		mLoadingView.startLoading();
//        registNetworkReceiver();
    }


    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.item_aotumotion_but_support)
    public void onClick() {
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    initDatas();
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}
