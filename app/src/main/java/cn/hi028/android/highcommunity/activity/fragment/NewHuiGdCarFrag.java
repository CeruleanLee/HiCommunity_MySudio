package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.adapter.NewHuiGdcarAdapter;
import cn.hi028.android.highcommunity.bean.Autonomous.NewSupplyCarlistBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：新版购物车<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/14<br>
 */
public class NewHuiGdCarFrag extends BaseFragment {
    public static final String Tag = "NewHuiGdCarFrag--->";
    public static final String FRAGMENTTAG = "NewHuiGdCarFrag";
    @Bind(R.id.progress_gdcar_notice)
    View mProgress;
    @Bind(R.id.tv_gdcar_Nodata)
    TextView mNodata;
    @Bind(R.id.btn_pay)
    public Button btn_pay;
    @Bind(R.id.ll_price)
    public LinearLayout ll_price;
    @Bind(R.id.tv_price)
    public TextView tv_price;
    @Bind(R.id.img_goods_ch)
    public ImageView img_goods_ch;
    @Bind(R.id.ptrlv_gdcar_listView)
    PullToRefreshListView mListView;
    public NewHuiGdcarAdapter adapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");
        view = inflater.inflate(R.layout.frag_huisupp_gdcar, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    public void initView() {
        Log.d(Tag, "initView");

        mProgress.setVisibility(View.VISIBLE);
        adapter = new NewHuiGdcarAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mNodata);
        img_goods_ch.setSelected(false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        HTTPHelper.getGdCarList2(mIbpi);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            Log.d(Tag, "onSuccess");

            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            List<NewSupplyCarlistBean.SupplyCarlistDataEntity> mlist = (List<NewSupplyCarlistBean.SupplyCarlistDataEntity>) message;
            adapter.setData(mlist);
        }

        @Override
        public Object onResolve(String result) {


            return HTTPHelper.ResolvGdCarList2(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }

    };
    @OnClick({R.id.img_goods_ch, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_goods_ch:
                checkClick();
                break;
            case R.id.btn_pay:
                payClick();
                break;
        }
    }
    public void checkClick() {
        if (img_goods_ch.isSelected()) {
            for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                adapter.getData().get(i).setCheck(false);
            }
        } else {
            for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                adapter.getData().get(i).setCheck(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void payClick() {
        if (btn_pay.isSelected()) {
            if (adapter.selectNum > 0) {
                final PopupWindow waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), btn_pay, Gravity.CENTER);
                HTTPHelper.deleteGdCarList2(new BpiHttpHandler.IBpiHttpHandler() {
                    @Override
                    public void onError(int id, String message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                    }

                    @Override
                    public void onSuccess(Object message) {
                        waitPop.dismiss();
                        HighCommunityUtils.GetInstantiation().ShowToast("成功删除", 0);
                        for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                            if (adapter.getData().get(i).isCheck() == true) {
                                adapter.getData().remove(i);
                                i--;
                            }
                        }
                        adapter.notifyDataSetChanged();
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
                        HighCommunityUtils.GetInstantiation().ShowToast("取消删除", 0);
                    }
                }, list());
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast("没有所选商品，不需要删除", 0);
            }

        } else {
            if (adapter.total_num > 0) {
                List<NewSupplyCarlistBean.SupplyCarlistDataEntity> list = new ArrayList<NewSupplyCarlistBean.SupplyCarlistDataEntity>();
                for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
                    if (adapter.getData().get(i).isCheck() == true) {
                        list.add(adapter.getData().get(i));
                    }
                }
                Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(HuiLifeSecondAct.class));
                mIntent.putExtra(HuiLifeSecondAct.ACTIVITYTAG, Constacts.NEW_HUILIFE_ORDER);
                mIntent.putExtra("carIdList", list());
                mIntent.putExtra(HuiLifeSecondAct.INTENTTAG, 1);
                //TODO 这里要改
                NewHuiBuyFrag.listData = list;
                startActivity(mIntent);
            } else {
                HighCommunityUtils.GetInstantiation().ShowToast("所选商品为零，不能结算", 0);
            }
        }
    }

    public String list() {
        String str = "";
        List<NewSupplyCarlistBean.SupplyCarlistDataEntity> list = new ArrayList<NewSupplyCarlistBean.SupplyCarlistDataEntity>();
        for (int i = 0; i < ListUtils.getSize(adapter.getData()); i++) {
            if (adapter.getData().get(i).isCheck() == true) {
                list.add(adapter.getData().get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                str = str + ",";
            }
            str = str + list.get(i).getId();
        }
        return str;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
