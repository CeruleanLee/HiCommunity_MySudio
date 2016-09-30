package cn.hi028.android.highcommunity.utils;

import org.w3c.dom.Text;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.ShakeBean;

/**
 * 功能：提示对话框<br>
 * 作者：赵海<br>
 * 时间：2015年3月26日<br>
 * 版本：1.0<br>
 */
public class ShakeToastUtils extends Dialog {
    public interface onCityListener {
        public void onCity(int state, String proviceName, String cityName);
    }

    Context context;
    private onDialogListener dialogListener;
    ShakeBean shakeBean;

    public interface onDialogListener {
        public void onDialog(View v);
    }

    ;

    public ShakeToastUtils(Context context, ShakeBean shakeBean) {
        super(context, R.style.dialog_style);
        this.context = context;
        this.shakeBean = shakeBean;
    }

    void initFialdNoView(int type) {
        this.setContentView(R.layout.dialog_shake_faild);
        TextView tv = (TextView) findViewById(R.id.tv_shake);
        if (type == 0) {
            findViewById(R.id.ll_shake_jh).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_shake_once).setVisibility(View.GONE);
            tv.setText("确定");
        } else {
            findViewById(R.id.ll_shake_jh).setVisibility(View.GONE);
            findViewById(R.id.ll_shake_once).setVisibility(View.VISIBLE);
            tv.setText("再来一次");
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (dialogListener != null) {
                    dismiss();
                    dialogListener.onDialog(v);

                }
            }
        });
    }

    void initSuccessView() {
        this.setContentView(R.layout.dialog_shake_yhq);
        TextView tv = (TextView) findViewById(R.id.tv_shake);
        if (shakeBean.getType() == 4) {
            findViewById(R.id.ll_shake_yhq).setVisibility(View.GONE);
            findViewById(R.id.ll_shake_jf).setVisibility(View.VISIBLE);
            TextView tv_jf_num = (TextView) findViewById(R.id.tv_jf_num);
            tv_jf_num.setText(shakeBean.getValue()+"积分");
        } else {
            findViewById(R.id.ll_shake_yhq).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_shake_jf).setVisibility(View.GONE);
            ImageView ticket_unit_flag = (ImageView) findViewById(R.id.img_unit_type);
            ImageView ticket_type = (ImageView) findViewById(R.id.img_type);
            TextView tv_ticket = (TextView) findViewById(R.id.tv_ticket);
            TextView tv_use_type = (TextView) findViewById(R.id.tv_use_type);
            TextView tv_use_num = (TextView) findViewById(R.id.tv_use_num);
            TextView tv_ticket_use = (TextView) findViewById(R.id.tv_ticket_use);
            if (shakeBean.getType() != 1) {
                ticket_unit_flag.setVisibility(View.VISIBLE);
                ticket_type.setSelected(false);
            } else {
                ticket_type.setSelected(true);
                ticket_unit_flag.setVisibility(View.GONE);
            }
            String[] userType = {"物业使用", "直供使用", "众筹使用"};
            String[] userType1 = {"物业优惠券", "直供优惠券", "众筹优惠券"};
            tv_ticket_use.setText(userType[shakeBean.getType() - 1]);
            tv_ticket.setText(shakeBean.getValue() + "");
            tv_use_num.setText(shakeBean.getValue() + "");
            tv_use_type.setText(userType1[shakeBean.getType() - 1] + (shakeBean.getType() == 1 ? "折" : "元"));
        }
        tv.setText("确定");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (dialogListener != null) {
                    dismiss();
                    dialogListener.onDialog(v);

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shakeBean.getLeftCount() == -1) {
            initFialdNoView(0);
        } else {

            if (shakeBean.getWin() == 1) {
                initSuccessView();
            } else {
                if (shakeBean.getLeftCount() == 0) {
                    initFialdNoView(0);
                } else {
                    initFialdNoView(1);
                }
            }
        }

    }

    /**
     * @return the dialogListener
     */
    public onDialogListener getDialogListener() {
        return dialogListener;
    }

    /**
     * @param dialogListener the dialogListener to set
     */
    public void setDialogListener(onDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }
}