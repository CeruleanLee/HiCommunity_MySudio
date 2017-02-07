package cn.hi028.android.highcommunity.activity;

import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import io.rong.imkit.RongIM;

/**
 * @功能：聊天列表页面<br>
 * @作者： Lee_yting<br>
 * @版本：3.0<br>
 * @时间：2017/2/6<br>
 */
public class ConversationListActivity extends BaseFragmentActivity {
    public static final String ACTIVITYTAG = "ConversationListActivity";
    public static final String TAG = "ConversationAct-->";
    @Bind(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button4)
    public void onClick() {
if (RongIM.getInstance()!=null){
    RongIM.getInstance().startConversationList(this);
}

    }


}
