/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.view.AutoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.ServiceSecondAct;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.bean.TenementHouseBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：租房详情<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-04<br>
 */
@EFragment(resName = "fragment_ser_tenement_detail")
public class ServiceTenDetailFrag extends BaseFragment {
    public PicPageAdapter pagerAdapter;
    @ViewById(R.id.tv_tenement_name)
    TextView tv_tenement_name;
    @ViewById(R.id.tv_tenement_call)
    TextView tv_tenement_call;
    @ViewById(R.id.tv_current_page)
    TextView tv_current_page;
    @ViewById(R.id.tv_vallage_name)
    TextView tv_vallage_name;
    @ViewById(R.id.tv_vallage_apartment)
    TextView tv_vallage_apartment;
    @ViewById(R.id.tv_tenement_type)
    TextView tv_tenement_type;
    @ViewById(R.id.tv_tenement_price)
    TextView tv_tenement_price;
    @ViewById(R.id.tv_tenement_info)
    TextView tv_tenement_info;
    @ViewById(R.id.tv_tenement_size)
    TextView tv_tenement_size;
    @ViewById(R.id.tv_tenement_area)
    TextView tv_tenement_area;
    @ViewById(R.id.tv_tenement_zhuangxiu)
    TextView tv_tenement_zhuangxiu;
    @ViewById(R.id.tv_tenement_level)
    TextView tv_tenement_level;
    @ViewById(R.id.tv_tenement_vallage)
    TextView tv_tenement_vallage;

    @ViewById(R.id.view_pager)
    AutoScrollViewPager viewPager;

    @AfterViews
    void initView() {
        pagerAdapter = new PicPageAdapter(getActivity()).setInfiniteLoop(true);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tv_current_page.setText((i + 1) + "/" + ListUtils.getSize(pagerAdapter.getImageIdList()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        String id = getActivity().getIntent().getStringExtra(ServiceSecondAct.INTENTTAG);
        HTTPHelper.GetTenementDetail(mIbpiTenementDetail, id);
    }

    BpiHttpHandler.IBpiHttpHandler mIbpiTenementDetail = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            TenementHouseBean bean = (TenementHouseBean) message;
            setUI(bean);

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolvTenementDetail(result);
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
    };

    @Override
    public void onPause() {
        super.onPause();
        viewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.startAutoScroll();
    }

    public void setUI(final TenementHouseBean tenementHouseBean) {
        pagerAdapter.setImageIdList(tenementHouseBean.getPic());
        tv_tenement_name.setText(tenementHouseBean.getName() + "(" + tenementHouseBean.getTel() + ")");
        tv_tenement_area.setText(tenementHouseBean.getBos() + "m²");
        tv_current_page.setText("1/" + ListUtils.getSize(tenementHouseBean.getPic()));
        tv_tenement_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighCommunityUtils.callDialogPhone(getActivity(), tenementHouseBean.getTel());
            }
        });
        tv_vallage_name.setText(tenementHouseBean.getVillage());
        tv_tenement_size.setText(tenementHouseBean.getSize());
        tv_tenement_info.setText(tenementHouseBean.getContent());
        tv_tenement_level.setText(tenementHouseBean.getFloor() + "层");
        tv_tenement_price.setText(tenementHouseBean.getPrice() + "元/月");
        tv_tenement_type.setText(tenementHouseBean.getTen());
        tv_tenement_vallage.setText(tenementHouseBean.getVillage());
        tv_vallage_apartment.setText(tenementHouseBean.getSize());
        tv_tenement_zhuangxiu.setText(tenementHouseBean.getType() == 0 ? "简装" : "精装");
    }
}
