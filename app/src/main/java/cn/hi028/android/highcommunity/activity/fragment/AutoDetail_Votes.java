package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.google.gson.Gson;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutoAct_Four;
import cn.hi028.android.highcommunity.bean.Autonomous.AutoDetail_QuestionVotedBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_QuestionDeatailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Title_CommitQuestionAnswer;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅 问卷调查详情Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoDetail_Votes extends BaseFragment {
    public static final String Tag = "~~~Detail_Questions:";
    public static final String FRAGMENTTAG = "AutoDetail_Questions";

    Auto_QuestionDeatailBean.QuestionDeatailDataEntity mBean;
    String question_id;
    @Bind(R.id.txt_title)
    TextView mTitle;
    @Bind(R.id.lly_test)
    LinearLayout test_layout;
    @Bind(R.id.submit)
    Button mSubmit;
    Context mContext;
    @Bind(R.id.back)
    Button mback;
    @Bind(R.id.txt_content)
    TextView mContent;

    int type;
    @Bind(R.id.watchResult)
    Button mWatchResult;
    View rootView;
    //问题列表
    List<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity> questionList;
    //答案列表
    List<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity.QuestionDeatailOptionsEntity> answerList;
    @Bind(R.id.pg_progress)
    ProgressBar mProgress;
    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater mInflater;
    int is_voted;

    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist = new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");

        rootView = inflater.inflate(R.layout.frag_auto_detail_questions, null);
        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        question_id = bundle.getString("question_id");
        is_voted = bundle.getInt("is_voted");
        type = bundle.getInt("type");
        Log.d(Tag, "question_id:" + question_id + ",is_voted=" + is_voted + ",type=" + type);

        initView();
        return rootView;
    }

    AutoDetail_QuestionVotedBean.QuestionVotedDataEntity mAnswersBean;
    List<String> radioAnswers;
    List<String> mutilOptionsAnswers;

    private void initView() {
        mContext = getActivity();
        mInflater = LayoutInflater.from(mContext);
        LogUtil.d(Tag + "initView");
        radioAnswers = new ArrayList<String>();
        mutilOptionsAnswers = new ArrayList<String>();
        mSubmit.setOnClickListener(new submitOnClickListener());
        initDatas();
    }

    private void initDatas() {
        mProgress.setVisibility(View.VISIBLE);
        Log.d(Tag, "initDatas");
        questionList = new ArrayList<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity>();
        answerList = new ArrayList<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity.QuestionDeatailOptionsEntity>();
        test_layout.removeAllViews();
        if (is_voted == 1) {
            Log.d(Tag, "initDatas==1");

            HTTPHelper.GetQuestionAnswerArray(mAnswersIbpi, question_id);

        } else {
            Log.d(Tag, "initDatas!=1");
            mSubmit.setVisibility(View.VISIBLE);
            mWatchResult.setVisibility(View.GONE);
            mback.setVisibility(View.GONE);
            HTTPHelper.GetQuestionDetail(mIbpi, question_id);
        }
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (message == null) {
                return;
            }
            mBean = (Auto_QuestionDeatailBean.QuestionDeatailDataEntity) message;
            if (mBean == null) return;
            initQuestionHead();
            questionList = mBean.getTitles();
            for (int i = 0; i < questionList.size(); i++) {
                que_view = mInflater.inflate(R.layout.frag_auto_detail_questions_question_layout, null);
                TextView txt_que = (TextView) que_view.findViewById(R.id.txt_question_item);
                //答案选项要加入的 布局
                LinearLayout add_layout = (LinearLayout) que_view.findViewById(R.id.lly_answer);
                if (questionList.get(i).getMax_option() == null) {
                    questionList.get(i).setMax_option(-1 + "");
                }

                if (questionList.get(i).getMax_option().equals("1")) {
                    questionList.get(i).setType("1");

                }
                if (questionList.get(i).getType().equals("1") || questionList.get(i).getMax_option().equals("1")) {//单选
                    set(txt_que, i + 1 + "." + "\u3000" + questionList.get(i).getName(), 0);

                } else {
                    set(txt_que, i + 1 + "." + questionList.get(i).getName(), 1);//多选

                }
                answerList = questionList.get(i).getOptions();
                if (is_voted == 1) {
                    Log.d(Tag, "准备已参与布局");
                    if (questionList.get(i).getType().equals("1") && radioAnswers != null) {//单选
                        for (int x = 0; x < answerList.size(); x++) {
                            Log.d(Tag, "1 answerList.get(x).getId()--->" + answerList.get(x).getId());
                            for (int y = 0; y < radioAnswers.size(); y++) {
                                Log.d(Tag, "1 radioAnswers.get(y)--->" + radioAnswers.get(y));
                                if (answerList.get(x).getId().equals(radioAnswers.get(y))) {
                                    Log.d(Tag, "1 等了");
                                    answerList.get(x).setAns_state(1);
                                }
                                ;
                            }
                        }
                    } else if (questionList.get(i).getType().equals("2") && mutilOptionsAnswers != null) {
                        for (int x = 0; x < answerList.size(); x++) {
                            Log.d(Tag, "2 answerList.get(x).getId()--->" + answerList.get(x).getId());
                            for (int y = 0; y < mutilOptionsAnswers.size(); y++) {
                                Log.d(Tag, "2 radioAnswers.get(y)--->" + mutilOptionsAnswers.get(y));
                                if (answerList.get(x).getId().equals(mutilOptionsAnswers.get(y))) {
                                    Log.d(Tag, "2 多选等了");
                                    answerList.get(x).setAns_state(1);
                                }
                                ;
                            }
                        }

                    }
                }

                Log.d(Tag, "Suucess里的answerList--->" + answerList.toString());

                imglist2 = new ArrayList<ImageView>();
                for (int j = 0; j < answerList.size(); j++) {
                    ans_view = mInflater.inflate(R.layout.frag_auto_detail_questions_answer_layout, null);
                    TextView txt_ans = (TextView) ans_view.findViewById(R.id.txt_answer_item);
                    ImageView image = (ImageView) ans_view.findViewById(R.id.image);
                    View line_view = ans_view.findViewById(R.id.vw_line);
                    if (j == answerList.size() - 1) {
                        //最后一条答案下面不要线是指布局的问题
                        line_view.setVisibility(View.GONE);
                    }
                    //判断单选多选加载不同选项图片
                    if (questionList.get(i).getType().equals("2") && !questionList.get(i).getMax_option().equals("1")) {

                        //多选
                        if (answerList.get(j).getAns_state() == 0) {
                            //如果未被选中
                            txt_ans.setTextColor(Color.parseColor("#595757"));
                            image.setBackgroundResource(R.drawable.multiselect_false);
                        } else {
                            txt_ans.setTextColor(Color.parseColor("#EA5514"));
                            image.setBackgroundResource(R.drawable.multiselect_true);
                        }
                    } else {
                        //单选
                        if (answerList.get(j).getAns_state() == 0) {
                            //如果当前未被选中
                            txt_ans.setTextColor(Color.parseColor("#595757"));
                            image.setBackgroundResource(R.drawable.radio_false);
                        } else {
                            //如果当前已被选中
                            txt_ans.setTextColor(Color.parseColor("#EA5514"));
                            image.setBackgroundResource(R.drawable.radio_true);
                        }
                    }
                    Log.e("---", "------" + image);
                    imglist2.add(image);
                    txt_ans.setText(answerList.get(j).getOption());
                    LinearLayout lly_answer_size = (LinearLayout) ans_view.findViewById(R.id.lly_answer_size);
                    if (is_voted == 0) {

                        lly_answer_size.setOnClickListener(new answerItemOnClickListener(i, j, answerList, txt_ans));
                    }

                    add_layout.addView(ans_view);
                }
                imglist.add(imglist2);

                test_layout.addView(que_view);

            }
            if (is_voted == 1) {

                mSubmit.setVisibility(View.GONE);
                if (type == 1) {

                    mback.setVisibility(View.VISIBLE);
                    mWatchResult.setVisibility(View.GONE);
                } else {
                    mWatchResult.setVisibility(View.VISIBLE);
                    mback.setVisibility(View.GONE);
                }
            }

        }

        private void initQuestionHead() {
            mTitle.setText(mBean.getVote().getTitle());
            mContent.setText("\u3000\u3000" + mBean.getVote().getAbstractX());

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

    /**
     * 设置文字后缀
     **/
    private void set(TextView tv_test, String content, int type) {
        //为了加载问题后面的* 和*多选
        // TODO Auto-generated method stub
        String w;
        if (questionList.get(0).getMax_option().equals("1")) {
            return;
        }

        if (type == 1) {

            w = content + " (可多选)";
        } else {
            w = content + "";
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

    @OnClick({R.id.back, R.id.watchResult})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.watchResult:
                Intent mIntent = new Intent(getActivity(), AutoAct_Four.class);
                mIntent.putExtra("vote_id", question_id);
                getActivity().startActivity(mIntent);
                break;
        }
    }

    class answerItemOnClickListener implements View.OnClickListener {
        private int i;
        private int j;
        private TextView txt;
        int mutilOptionCount = 0;
        private List<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity.QuestionDeatailOptionsEntity> the_answer_lists;

        public answerItemOnClickListener(int i, int j, List<Auto_QuestionDeatailBean.QuestionDeatailDataEntity.QuestionDeatailTitlesEntity.QuestionDeatailOptionsEntity> the_answer_list, TextView text) {
            this.i = i;//问题
            this.j = j;//答案
            this.the_answer_lists = the_answer_list;
            this.txt = text;

        }

        //实现点击选项后改变选中状态以及对应图片
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断当前问题是单选还是多选
            if (questionList.get(i).getType().equals("2")) {
                //多选
                if (the_answer_lists.get(j).getAns_state() == 0) {
                    //如果未被选中
                    imglist.get(i).get(j).setBackgroundResource(R.drawable.multiselect_true);
                    the_answer_lists.get(j).setAns_state(1);
                    mutilOptionCount++;
                    questionList.get(i).setQue_state(1);
                } else {
                    imglist.get(i).get(j).setBackgroundResource(R.drawable.multiselect_false);
                    the_answer_lists.get(j).setAns_state(0);
                    mutilOptionCount--;
                    //判断问题是否完成
                    if (mutilOptionCount < the_answer_lists.size()) {

                        questionList.get(i).setQue_state(0);
                    } else {
                        questionList.get(i).setQue_state(1);

                    }
                }
            } else {
//                单选
                for (int z = 0; z < the_answer_lists.size(); z++) {
                    the_answer_lists.get(z).setAns_state(0);
                    imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                }
                if (the_answer_lists.get(j).getAns_state() == 0) {
                    //如果当前未被选中
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_true));
                    the_answer_lists.get(j).setAns_state(1);
                    questionList.get(i).setQue_state(1);
                } else {
                    //如果当前已被选中
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                    the_answer_lists.get(j).setAns_state(0);
                    questionList.get(i).setQue_state(0);
                }

            }
        }

    }

    class submitOnClickListener implements View.OnClickListener {
        public submitOnClickListener() {
            super();
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断是否答完题
            boolean isState = true;
            //最终要的json数组
            JSONArray jsonArray = new JSONArray();
            JSONArray optionIdArray = new JSONArray();
            List<Title_CommitQuestionAnswer> mAnswerList = new ArrayList<Title_CommitQuestionAnswer>();
            //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
            for (int i = 0; i < questionList.size(); i++) {
                Title_CommitQuestionAnswer mCommitAnswer = new Title_CommitQuestionAnswer();
                answerList = questionList.get(i).getOptions();
                //判断是否有题没答完
                if (questionList.get(i).getQue_state() == 0) {
                    Toast.makeText(getActivity(), "您第" + (i + 1) + "题没有答完", Toast.LENGTH_LONG).show();
                    jsonArray = null;
                    isState = false;
                    break;
                } else {
                    List<String> selectedoptions = new ArrayList<String>();
                    mCommitAnswer.setId(questionList.get(i).getId());
                    for (int j = 0; j < answerList.size(); j++) {
                        if (answerList.get(j).getAns_state() == 1) {
                            JSONObject json = new JSONObject();
                            try {
                                selectedoptions.add(answerList.get(j).getId());
                                json.put("questionId", questionList.get(i).getId());
                                json.put("optionId", answerList.get(j).getId());
                                jsonArray.add(json);
                                String toJson = new Gson().toJson(jsonArray);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    mCommitAnswer.setMutilOptionId(selectedoptions);

                }
                mAnswerList.add(mCommitAnswer);

            }
            if (isState) {
                if (jsonArray.size() > 0) {
                    Log.e("af", jsonArray.toString());
                    Log.e(Tag, "mAnswerList--->" + mAnswerList.toString());
                    JSONArray mJsonArray = new JSONArray();
                    JSONObject jsonObject_inner = null;
                    for (int z = 0; z < mAnswerList.size(); z++) {
                        jsonObject_inner = new JSONObject();
                        JSONArray jsonArray_Inner = new JSONArray();
                        List<String> MutilOptionId = mAnswerList.get(z).getMutilOptionId();
                        for (int w = 0; w < MutilOptionId.size(); w++) {
                            jsonArray_Inner.add(MutilOptionId.get(w));
                        }
                        jsonObject_inner.put(mAnswerList.get(z).getId(), jsonArray_Inner);
                        mJsonArray.add(jsonObject_inner);
                    }
                    Log.e(Tag, "mJsonArray--->" + mJsonArray.toString());
                    if (mJsonArray.size() == 1 && Integer.parseInt(questionList.get(0).getMax_option()) != 0 && mAnswerList.get(0).getMutilOptionId().size() > Integer.parseInt(questionList.get(0).getMax_option())) {
                        Toast.makeText(getActivity(), "最多可选" + questionList.get(0).getMax_option() + "项", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), rootView, Gravity.CENTER);
                    HTTPHelper.commitAnswers(mCommentIbpi, question_id, mJsonArray.toString());
                }

            }

        }
    }

    private PopupWindow mWatingWindow;
    BpiHttpHandler.IBpiHttpHandler mCommentIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
            is_voted = 1;
            questionList.clear();
            answerList.clear();
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
            mWatingWindow.dismiss();
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

    BpiHttpHandler.IBpiHttpHandler mAnswersIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
        }

        @Override
        public void onSuccess(Object message) {
            mAnswersBean = (AutoDetail_QuestionVotedBean.QuestionVotedDataEntity) message;
            radioAnswers = mAnswersBean.getRadio();
            mutilOptionsAnswers = mAnswersBean.getCheckbox();
            HTTPHelper.GetQuestionDetail(mIbpi, question_id);
        }


        @Override
        public Object onResolve(String result) {

            return HTTPHelper.ResolveQuestionVotedDataEntity(result);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
