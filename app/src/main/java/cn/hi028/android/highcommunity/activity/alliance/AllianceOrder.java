package cn.hi028.android.highcommunity.activity.alliance;

import net.duohuo.dhroid.util.LogUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.BaseFragmentActivity;
import cn.hi028.android.highcommunity.activity.fragment.alliance.AllianceAllOrderFrag;
import cn.hi028.android.highcommunity.activity.fragment.alliance.AllianceOrderUnCommFrag;
import cn.hi028.android.highcommunity.activity.fragment.alliance.AllianceOrderUnPayFrag;
import cn.hi028.android.highcommunity.activity.fragment.alliance.AllianceOrderUnRecFrag;
/**
 * 联盟订单
 * @author Administrator
 *
 */
public class AllianceOrder extends BaseFragmentActivity {
    private static final int TAB_COUNT = 4;
    public static final int TAB_UNPAY = 0;
    public static final int TAB_UNREC = 1;
    public static final int TAB_UNCOMM = 2;
    public static final int TAB_ALL_ORDER = 3;
    public static final String TAG = "AllianceOrder";
    TextView allianceTitleUnPay;
    View allianceTitleUnPayLine;
    TextView allianceTitleUnRec;
    View allianceTitleUnRecLine;
    TextView allianceTitleUnComm;
    View allianceTitleUnCommLine;
    TextView allianceTitleAllOrder;
    View allianceTitleAllOrderLine;
    TextView tvSecondtitleName;
    ImageView back;
    private int mCurrentTab = 0;
    private AllianceOrderUnPayFrag mFragUnpay = null;
    private AllianceOrderUnRecFrag mFragUnRec = null;
    private AllianceOrderUnCommFrag mFragUnComm = null;
    private AllianceAllOrderFrag mFragAllOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_order);
        tvSecondtitleName = (TextView) findViewById(R.id.alliance_title).findViewById(R.id.tv_secondtitle_name);
        tvSecondtitleName.setText("联盟订单");
        back = (ImageView) findViewById(R.id.alliance_title).findViewById(R.id.img_back);
        allianceTitleUnPay = (TextView) findViewById(R.id.alliance_title_unPay);
        allianceTitleUnRec = (TextView) findViewById(R.id.alliance_title_unRec);
        allianceTitleUnComm = (TextView) findViewById(R.id.alliance_title_unComm);
        allianceTitleAllOrder = (TextView) findViewById(R.id.alliance_title_all_order);
        allianceTitleUnPayLine = findViewById(R.id.alliance_title_unPay_line);
        allianceTitleUnRecLine = findViewById(R.id.alliance_title_unRec_line);
        allianceTitleUnCommLine = findViewById(R.id.alliance_title_unComm_line);
        allianceTitleAllOrderLine = findViewById(R.id.alliance_title_all_order_line);
        registListener();
        mCurrentTab = TAB_UNPAY;
        switchFragment(mCurrentTab);
    }
/**
 * 注册监听
 */
    private void registListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        allianceTitleUnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(TAB_UNPAY);
            }
        });
        allianceTitleUnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(TAB_UNREC);
            }
        });
        allianceTitleUnComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(TAB_UNCOMM);
            }
        });
        allianceTitleAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(TAB_ALL_ORDER);
            }
        });
    }
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	LogUtil.d("--------act 进入onresume");
	switchFragment(mCurrentTab);
	
	
}


    /**
     * 选择哪个fragment
     **/
    private void switchFragment(int currentTab) {
        this.mCurrentTab = currentTab;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        resetView();
        switch (currentTab) {
            case TAB_UNPAY:
                allianceTitleUnPay.setTextColor(getResources().getColor(R.color.Defult_Color_AppGreen));
                allianceTitleUnPayLine.setVisibility(View.VISIBLE);
                if (mFragUnpay == null) {
                    mFragUnpay = new AllianceOrderUnPayFrag();
                    transaction.add(R.id.main_tab_content, mFragUnpay);
                } else {
                    transaction.show(mFragUnpay);
                }
                break;
            case TAB_UNREC:
                allianceTitleUnRec.setTextColor(getResources().getColor(R.color.Defult_Color_AppGreen));
                allianceTitleUnRecLine.setVisibility(View.VISIBLE);
                if (mFragUnRec == null) {
                    mFragUnRec = new AllianceOrderUnRecFrag();
                    transaction.add(R.id.main_tab_content, mFragUnRec);
                } else {
                    transaction.show(mFragUnRec);
                }
                break;
            case TAB_UNCOMM:
                allianceTitleUnComm.setTextColor(getResources().getColor(R.color.Defult_Color_AppGreen));
                allianceTitleUnCommLine.setVisibility(View.VISIBLE);
                if (mFragUnComm == null) {
                    mFragUnComm = new AllianceOrderUnCommFrag();
                    transaction.add(R.id.main_tab_content, mFragUnComm);
                } else {
                    transaction.show(mFragUnComm);
                }
                break;
            case TAB_ALL_ORDER:
                allianceTitleAllOrder.setTextColor(getResources().getColor(R.color.Defult_Color_AppGreen));
                allianceTitleAllOrderLine.setVisibility(View.VISIBLE);
                if (mFragAllOrder == null) {
                    mFragAllOrder = new AllianceAllOrderFrag();
                    transaction.add(R.id.main_tab_content, mFragAllOrder);
                } else {
                    transaction.show(mFragAllOrder);
                }
                break;
        }
        transaction.commit();
    }
/**
 *重置view
 */
    private void resetView() {
        allianceTitleUnPay.setTextColor(getResources().getColor(R.color.defult_text_color));
        allianceTitleUnPayLine.setVisibility(View.GONE);
        allianceTitleUnRec.setTextColor(getResources().getColor(R.color.defult_text_color));
        allianceTitleUnRecLine.setVisibility(View.GONE);
        allianceTitleUnComm.setTextColor(getResources().getColor(R.color.defult_text_color));
        allianceTitleUnCommLine.setVisibility(View.GONE);
        allianceTitleAllOrder.setTextColor(getResources().getColor(R.color.defult_text_color));
        allianceTitleAllOrderLine.setVisibility(View.GONE);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mFragUnpay != null) {
            transaction.hide(mFragUnpay);
        }
        if (mFragUnRec != null) {
            transaction.hide(mFragUnRec);
        }
        if (mFragUnComm != null) {
            transaction.hide(mFragUnComm);
        }
        if (mFragAllOrder != null) {
            transaction.hide(mFragAllOrder);
        }
    }

}
