package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅认证完成 创建询问  需要业主代表权限<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_CreatInquiry extends BaseFragment {
    public static final String Tag = "~~~AutoFrag_CreatReport~~~";
    public static final String FRAGMENTTAG = "AutoFrag_CreatReport";
    int owner_id;
    @Bind(R.id.creatInquiry_content)
    EditText mContent;
    @Bind(R.id.creatInquiry_commit)
    TextView mCommit;
boolean isMeassage=false;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_creat_inquiry, null);
        Bundle bundle = getArguments();
        owner_id = bundle.getInt("owner_id", -1);
        isMeassage=bundle.getBoolean("isMessage",false);
        findView(view);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void findView(View view) {
//        mTitle= (EditText) view.findViewById(R.id.creatReport_title);
//        mContent= (EditText) view.findViewById(R.id.creatReport_content);
//        mCommit= (TextView) view.findViewById(R.id.creatReport_commit);
    }

    private void initView() {
        LogUtil.d(Tag + "initView");
//        Toast.makeText(getActivity(), "isMeassage"+isMeassage, Toast.LENGTH_SHORT).show();
        if (isMeassage){
            mContent.setHint("留言内容");
        }else{
            mContent.setHint("询问内容");
        }
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMeassage){

                    HTTPHelper.AutoCreatMessage(mIbpi, owner_id + "", mContent.getText().toString());
                }else{

                    HTTPHelper.AutoCreatInquiry(mIbpi, owner_id + "", mContent.getText().toString());
                }
            }
        });

    }


    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);
            getActivity().onBackPressed();

        }

        @Override
        public Object onResolve(String result) {
            LogUtil.d(Tag + " ~~~result" + result);
            return result;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {

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

    @OnClick(R.id.creatInquiry_commit)
    public void onClick() {
    }



}
