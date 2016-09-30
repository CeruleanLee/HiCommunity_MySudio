/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.GeneratedClassUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.ListUtils;
import net.duohuo.dhroid.view.AutoScrollViewPager;
import net.duohuo.dhroid.view.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.HuiLifeSecondAct;
import cn.hi028.android.highcommunity.activity.PaymentActivity;
import cn.hi028.android.highcommunity.adapter.PicPageAdapter;
import cn.hi028.android.highcommunity.bean.ChipsBean;
import cn.hi028.android.highcommunity.bean.PointBean;
import cn.hi028.android.highcommunity.params.HuiChipsOrderparams;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：众筹详情<br>
 * @作者： 赵海<br>
 * @版本：1.0<br>
 * @时间：2016-01-22<br>
 */
@EFragment(resName = "frag_huichips_detail")
public class HuiChipsDetailFrag extends BaseFragment {
	@ViewById(R.id.tv_name)
	TextView tv_name;
	@ViewById(R.id.tv_count)
	TextView tv_count;
	@ViewById(R.id.tv_chips_person_num)
	TextView tv_chips_person_num;
	@ViewById(R.id.tv_chips_price)
	TextView tv_chips_price;
	@ViewById(R.id.tv_chips_time)
	TextView tv_chips_time;
	@ViewById(R.id.lc_chips)
	LineChart lc_chips;
	@ViewById(R.id.tv_info)
	TextView tv_info;
	@ViewById(R.id.tv_join_chips)
	TextView tv_join_chips;
	@ViewById(R.id.view_pager)
	AutoScrollViewPager viewPager;
	@ViewById(R.id.view_cpi)
	CirclePageIndicator vgcpi;
	public PicPageAdapter pagerAdapter;
	ChipsBean bean;
	PopupWindow popWait;

	onCounter mCounter;

	@AfterViews
	void initView() {
		initChart();
		pagerAdapter = new PicPageAdapter(getActivity()
				).setInfiniteLoop(true);
		viewPager.setAdapter(pagerAdapter);
		vgcpi.setViewPager(viewPager);
		vgcpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {

			}

			@Override
			public void onPageSelected(int i) {

			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});
		viewPager.setInterval(2000);
		viewPager.startAutoScroll();
		viewPager.setCurrentItem(0);
		ChipsBean cip = (ChipsBean) getActivity().getIntent().getSerializableExtra(HuiLifeSecondAct.INTENTTAG);
		HTTPHelper.GetHuiChipsDetail(mIbpi, cip.getRid() + "");
	}

	/**
	 * 获取众筹详情
	 */
	 BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
		@Override
		public void onError(int id, String message) {
			HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		}

		@Override
		public void onSuccess(Object message) {
			if (null == message)
				return;
			ChipsBean data = (ChipsBean) message;
			updateData(data);
		}

		@Override
		public Object onResolve(String result) {
			return HTTPHelper.ResolveHuiChipsDetail(result);
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
		 viewPager.stopAutoScroll();
	 }

	 @Override
	 public void onResume() {
		 super.onResume();
		 viewPager.startAutoScroll();
	 }

	 @Click(R.id.tv_join_chips)
	 public void onClick() {
		 popWait = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), tv_join_chips, Gravity.CENTER);
		 HTTPHelper.GetHuiChipsOrder(mIbpiChipsOrder, bean.getRid() + "", HighCommunityApplication.mUserInfo.getId() + "");

	 }

	 /**
	  * 参与众筹回调
	  */
	 BpiHttpHandler.IBpiHttpHandler mIbpiChipsOrder = new BpiHttpHandler.IBpiHttpHandler() {
		 @Override
		 public void onError(int id, String message) {
			 popWait.dismiss();
			 HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
		 }

		 @Override
		 public void onSuccess(Object message) {
			 popWait.dismiss();
			 if (null == message)
				 return;
			 HuiChipsOrderparams data = (HuiChipsOrderparams) message;
			 Intent mIntent = new Intent(getActivity(), GeneratedClassUtils.get(PaymentActivity.class));
			 mIntent.putExtra(PaymentActivity.ACTIVITYTAG, Constacts.HUILIFE_CHIPS_ORDER);
			 mIntent.putExtra(PaymentActivity.INTENTTAG, data);
			 startActivity(mIntent);
			 getActivity().finish();
		 }

		 @Override
		 public Object onResolve(String result) {
			 return HTTPHelper.ResolveHuiChipsOrder(result);
		 }

		 @Override
		 public void setAsyncTask(AsyncTask asyncTask) {

		 }

		 @Override
		 public void cancleAsyncTask() {
			 popWait.dismiss();
		 }
	 };

	 void updateData(ChipsBean data) {
		 bean = data;
		 tv_chips_person_num.setText(data.getPeople_num() + "人");
		 tv_chips_price.setText(data.getCurrent_price() + "元");
		 mCounter = new onCounter(Long.parseLong(data.getEnd_time()) * 1000, 1000);
		 mCounter.start();
		 tv_count.setText("库存" + data.getStorage() + "件");
//		 tv_name.setText(ToSBC(data.getR_name()));
		 tv_name.setText(data.getR_name());
		 tv_info.setText(data.getR_describe());
		 pagerAdapter.setImageIdList(data.getR_pic());
		 setData(data.getPoint());
	 }

	 void initChart() {
		 XAxis xAxis = lc_chips.getXAxis();
		 xAxis.setTextSize(8f);
		 xAxis.setTextColor(getResources().getColor(R.color.color_blue_gray));
		 xAxis.setDrawGridLines(false);
		 xAxis.setDrawAxisLine(false);
		 xAxis.setSpaceBetweenLabels(1);
		 xAxis.setXOffset(10f);
		 xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

		 YAxis leftAxis = lc_chips.getAxisLeft();
		 leftAxis.removeAllLimitLines();
		 //        leftAxis.setAxisMaxValue(220f);
		 //        leftAxis.setAxisMinValue(-50f);
		 leftAxis.setStartAtZero(false);

		 leftAxis.setTextSize(8f);
		 leftAxis.setTextColor(getResources().getColor(R.color.color_blue_gray));
		 leftAxis.setDrawAxisLine(false);
		 leftAxis.enableGridDashedLine(4f, 10f, 0f);
		 leftAxis.setDrawLimitLinesBehindData(false);
		 lc_chips.getAxisRight().setEnabled(false);
		 lc_chips.setBackgroundColor(getResources().getColor(R.color.defult_color_white));
		 lc_chips.setGridBackgroundColor(getResources().getColor(R.color.defult_color_white));
		 lc_chips.animateX(2500, Easing.EasingOption.EaseInOutQuart);
		 lc_chips.setDescription("");
		 Legend l = lc_chips.getLegend();
		 l.setEnabled(false);
	 }

	 float maxValues = 0.0f;

	 private void setData(List<PointBean> data) {

		 ArrayList<String> xVals = new ArrayList<String>();
		 for (int i = 0; i < ListUtils.getSize(data); i++) {
			 if (i == ListUtils.getSize(data) - 1) {
				 xVals.add(data.get(i).getX() + "/人");
			 } else {
				 xVals.add(data.get(i).getX() + "");
			 }
		 }
		 ArrayList<Entry> yVals = new ArrayList<Entry>();
		 maxValues = 0.0f;
		 for (int i = 0; i < ListUtils.getSize(data); i++) {
			 if (data.get(i).getY() > maxValues) {
				 maxValues = data.get(i).getY();
			 }
			 yVals.add(new Entry(data.get(i).getY(), i));
		 }
		 YAxisValueFormatter custom = new YAxisValueFormatter() {
			 private DecimalFormat mFormat;

			 @Override
			 public String getFormattedValue(float value, YAxis yAxis) {
				 if (mFormat == null)
					 mFormat = new DecimalFormat("###,###,###,##0.0");
				 if (value >= maxValues) {
					 return mFormat.format(value) + "/元";
				 } else {
					 return mFormat.format(value) + "";
				 }
			 }
		 };
		 lc_chips.getAxisLeft().setValueFormatter(custom);
		 // create a dataset and give it a type
		 LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
		 set1.setColor(getResources().getColor(R.color.Defult_Color_AppGreen));
		 set1.setCircleColor(getResources().getColor(R.color.defult_color_white));
		 set1.setLineWidth(2f);
		 set1.setCircleSize(8f);
		 set1.setDrawCircleHole(true);
		 set1.setValueTextSize(9f);
		 set1.setDrawValues(false);
		 set1.setFillAlpha(65);
		 set1.setCircleColorHole(getResources().getColor(R.color.Defult_Color_AppGreen));
		 //        set1.setFillColor(getResources().getColor(R.color.Defult_Color_AppGreen));
		 ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		 dataSets.add(set1); // add the datasets
		 // create a data object with the datasets
		 LineData list = new LineData(xVals, dataSets);
		 // set data
		 lc_chips.setData(list);
	 }

	 public class onCounter extends CountDownTimer {

		 public onCounter(long millisInFuture, long countDownInterval) {
			 super(millisInFuture, countDownInterval);
		 }

		 @Override
		 public void onTick(long millisUntilFinished) {
			 tv_chips_time.setText(TimeUtil.getCountTime(millisUntilFinished / 1000));
		 }

		 @Override
		 public void onFinish() {
		 }

	 }

	 public static String ToSBC(String input) {
		 char c[] = input.toCharArray();
		 for (int i = 0; i < c.length; i++) {
			 if (c[i] == ' ') {
				 c[i] = '\u3000';
			 } else if (c[i] < '\177') {
				 c[i] = (char) (c[i] + 65248);
			 }
		 }
		 return new String(c);
	 }
} 

