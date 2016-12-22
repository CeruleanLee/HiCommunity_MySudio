/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.MenuLeftThirdAct;
import cn.hi028.android.highcommunity.adapter.HuiCommOrderAdapter;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 *@功能：惠生活-订单评论<br>
 *@作者： 赵海<br>
 *@版本：1.0<br>
 *@时间：2016-02-01<br>
 */
@EFragment(resName = "frag_comment_order")
public class HuiCommOrderFrag extends BaseFragment{
    public static final String FRAGMENTTAG = "HuiCommOrderFrag";
    @ViewById(R.id.ll_NoticeDetails_Progress)
    View ll_NoticeDetails_Progress;
    @ViewById(R.id.lv_list)
    ListView lv_list;
    @ViewById(R.id.btn_comment)
    Button btn_comment;
     HuiCommOrderAdapter adapter;
    HuiOrderBean data;
    @AfterViews
    void initView(){
         adapter=new HuiCommOrderAdapter(this);
         lv_list.setAdapter(adapter);
           data=(HuiOrderBean)getActivity().getIntent().getSerializableExtra(MenuLeftThirdAct.INTENTTAG);
         adapter.setData(data.getGoods());
    }
    PopupWindow waitPop;
    @Click(R.id.btn_comment)
    public void commOclick(){
        if (!isEmptyComment()){
            JSONObject jsonParams=new JSONObject();
            JSONArray params=new JSONArray();
            try {
                for (int i=0;i<ListUtils.getSize(adapter.getData());i++){
                    JSONObject goods=new JSONObject();
//                    goods.put("user_id", HighCommunityApplication.mUserInfo.getId()+"");
                    goods.put("gid",adapter.getData().get(i).getGoods_id()+"");
                    goods.put("content",adapter.getData().get(i).getComment());
                    params.put(goods);
                }
//                jsonParams.put("comment",params);
//                jsonParams.put("order_id",data.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_comment, Gravity.CENTER);
            HTTPHelper.commentOrder2(new BpiHttpHandler.IBpiHttpHandler() {
                @Override
                public void onError(int id, String message) {
                    waitPop.dismiss();
                    HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                }
                @Override
                public void onSuccess(Object message) {
                    waitPop.dismiss();
                    HighCommunityUtils.GetInstantiation().ShowToast("评论成功", 0);
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
                    waitPop.dismiss();
                }
            }, jsonParams.toString());
        }
    }
    private boolean isEmptyComment(){
        for(int i=0;i< ListUtils.getSize(adapter.getData());i++){
            if (TextUtils.isEmpty(adapter.getData().get(i).getComment())){
                HighCommunityUtils.GetInstantiation().ShowToast(adapter.getData().get(i).getGoods_name()+"的评论不能为空",0);
                return true;
            }
        }
        return false;

    }
}
