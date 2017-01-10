/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.EditText;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.WelcomeAct;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 *@功能：修改密码<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-02-15<br>
 */
@EFragment(resName = "frag_modify_psd")
public class ModifyPsdFrag extends BaseFragment{
    public static final String FRAGMENTTAG = "ModifyPsdFrag";
    @ViewById(R.id.edt_old_psd)
    EditText edt_old_psd;
    @ViewById(R.id.edt_new_psd)
    EditText edt_new_psd;
    @ViewById(R.id.edt_conform_psd)
    EditText edt_conform_psd;
    @AfterViews
    void initView(){

    }
    @Click(R.id.tv_modify)
 public   void onModify(){
        String oldPsd=edt_old_psd.getText().toString().trim();
        String newPsd=edt_new_psd.getText().toString().trim();
        String conformPsd=edt_conform_psd.getText().toString().trim();
        if (TextUtils.isEmpty(oldPsd)){
            HighCommunityUtils.GetInstantiation().ShowToast("输入的旧密码不能为空",0);
            return;
        }
        if (TextUtils.isEmpty(newPsd)){
            HighCommunityUtils.GetInstantiation().ShowToast("输入的新密码不能为空",0);
            return;
        }
        if (TextUtils.isEmpty(conformPsd)){
            HighCommunityUtils.GetInstantiation().ShowToast("再次输入的新密码不能为空",0);
            return;
        }
        if (TextUtils.equals(conformPsd,oldPsd)){
            HighCommunityUtils.GetInstantiation().ShowToast("两次新密码不一致",0);
            return;
        }
        if(HighCommunityUtils.GetInstantiation().isLogin(getActivity())){
            HTTPHelper.modifyPsd(new BpiHttpHandler.IBpiHttpHandler(){
                @Override
                public void onError(int id, String message) {
                    HighCommunityUtils.GetInstantiation().ShowToast(message,0);
                }

                @Override
                public void onSuccess(Object message) {
                    HighCommunityApplication.isLogOut = true;
                    HighCommunityApplication.share.edit().putString(Constacts.APPTOKEN, "").commit();
                    HighCommunityUtils.GetInstantiation().ShowToast("修改成功",0);
                    Intent mToMain = new Intent(getActivity(), WelcomeAct.class);
                    startActivity(mToMain);
                   getActivity().finish();
                }

                @Override
                public Object onResolve(String result) {
                    return null;
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
                    if (isShouldLogin){
                        HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                        HighCommunityApplication.toLoginAgain(getActivity());
                    }
                }
            },oldPsd,newPsd);
        }
    }

}
