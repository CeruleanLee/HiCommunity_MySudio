package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity2;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;
import cn.hi028.android.highcommunity.utils.TimeUtil;
import cn.hi028.android.highcommunity.view.CutdownTextView;
import cn.hi028.android.highcommunity.view.MyNoScrollMeasureListview2;

/**
 * @功能：直供商品限时抢购adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class SupplyPurchaseListAdapter extends BaseFragmentAdapter {
    public static final String TAG = "SupplyPurchaseListAdapter：";
    List<NewSupplyBean.NewSupplyDataEntity.PurchaseEntity> mList = new ArrayList<NewSupplyBean.NewSupplyDataEntity.PurchaseEntity>();

    private Context context;
    private LayoutInflater layoutInflater;
    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;

    public SupplyPurchaseListAdapter(List<NewSupplyBean.NewSupplyDataEntity.PurchaseEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<NewSupplyBean.NewSupplyDataEntity.PurchaseEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        bitmapUtils = MBitmapHolder.getBitmapUtils(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NewSupplyBean.NewSupplyDataEntity.PurchaseEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    ViewHolder mViewHolder = null;

    onCounter mCounter;
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.view_newlife_flashsale, null);
            mViewHolder.mImg = (ImageView) convertView.findViewById(R.id.flasfscale_img);
            mViewHolder.mNowPrice = (TextView) convertView.findViewById(R.id.flasfscale_nowPrice);
            mViewHolder.mPastPrice = (TextView) convertView.findViewById(R.id.flasfscale_pastPrice);
            mViewHolder.mCounterTime = (CutdownTextView) convertView.findViewById(R.id.flasfscale_counterTime);
            mViewHolder.mCounterTime2 = (CutdownTextView) convertView.findViewById(R.id.flasfscale_counterTime2);
            mViewHolder.mTojoin = (TextView) convertView.findViewById(R.id.flashsale_tojoin);
            mViewHolder.mGoodstitle = (TextView) convertView.findViewById(R.id.flashsale_goodstitle);
            mViewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.flashsale_progressBar);
            mViewHolder.mTvProgress = (TextView) convertView.findViewById(R.id.flashsale_tv_progress);
            mViewHolder.flashsaleTvKucun = (TextView) convertView.findViewById(R.id.flashsale_tv_kucun);
            mViewHolder.layout11 = (RelativeLayout) convertView.findViewById(R.id.layout11);
            mViewHolder.layout22 = (RelativeLayout) convertView.findViewById(R.id.layout22);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if(parent instanceof MyNoScrollMeasureListview2 &&((MyNoScrollMeasureListview2) parent).isMeasure){
            Log.e(TAG,"~~~isMeasure position---"+position);
            return convertView;
        }
        Log.d(TAG,"~~!  isMeasure ~position---"+position);
        final NewSupplyBean.NewSupplyDataEntity.PurchaseEntity mBean = mList.get(position);

        if (mBean.getCover_pic() == null || mBean.getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.mImg);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getCover_pic(), mViewHolder.mImg);
        }
        mViewHolder.mNowPrice.setText("￥:"+mBean.getPrice());
        Spannable spanStrikethrough = new SpannableString("￥" + mBean.getOld_price());
        StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
        try {
            spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }catch (Exception ex){

        }
        mViewHolder.mPastPrice.setText(spanStrikethrough);
        mViewHolder.mGoodstitle.setText(mBean.getName());
        if ( mBean.getPercent().contains("%")){

            String[] strings = mBean.getPercent().split("%");
            mViewHolder.mProgressBar.setProgress(Integer.parseInt(strings[0]));
        }
        mViewHolder.mTvProgress.setText(mBean.getPercent());
        mViewHolder.flashsaleTvKucun.setText("共"+mBean.getStorage()+"份");
        long nowTime = System.currentTimeMillis();
        long time22 = Long.parseLong(mBean.getRemainTime())*1000 - nowTime;
        startCutdown(mViewHolder.mCounterTime, position,(time22),1000);
        mViewHolder.mTojoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity2.class);
                mIntent.putExtra("id",mBean.getId());
                context.startActivity(mIntent);

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity2.class);
                mIntent.putExtra("id",mBean.getId());
                context.startActivity(mIntent);
            }
        });
        mViewHolder.layout11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity2.class);
                mIntent.putExtra("id",mBean.getId());
                context.startActivity(mIntent);
            }
        });
        mViewHolder.layout22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity2.class);
                mIntent.putExtra("id",mBean.getId());
                context.startActivity(mIntent);
            }
        });

        return convertView;
    }
    BitmapUtils bitmapUtils;
    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<NewSupplyBean.NewSupplyDataEntity.PurchaseEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }
    class ViewHolder{
        ImageView mImg;
        TextView mNowPrice;
        TextView mPastPrice;
        CutdownTextView mCounterTime;
        TextView mTojoin;
        TextView mGoodstitle;
        ProgressBar mProgressBar;
        TextView mTvProgress;
        TextView flashsaleTvKucun;
        CutdownTextView mCounterTime2;
        RelativeLayout layout11,layout22;

    }

    class onCounter extends CountDownTimer {

        public onCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mViewHolder.mCounterTime.setText(TimeUtil.getCountTime(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
        }

    }

    private void startCutdown(CutdownTextView view, final int position, long time, int period){
        view.setOnCountDownFinishListener(new CutdownTextView.OnCountDownFinishListener() {

            @Override
            public void onFinish() {
//                String url = UrlHandler.handlUrl(Constants.URL_UPDATE_PERIOD_INFO, getItem(position).period);
//                mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
//
//                    @Override
//                    public void onFailure(HttpException arg0, String arg1) {
//
//                    }
//                    @Override
//                    public void onSuccess(ResponseInfo<String> arg0) {
//                        try {
//                            System.out.println("getView---------"+position+"被回调拉！！！");
//                            String content = arg0.result;
//                            ResponseRevealedPeriodInfo2 response = new Gson().fromJson(content, ResponseRevealedPeriodInfo2.class);
//                            list.set(position, response.result);
//                            int viewPosition = position - adapterView.getFirstVisiblePosition();
//                            getView(position, adapterView.getChildAt(viewPosition), adapterView);
//                        } catch (Exception e) {
//                        }
//                    }
//                });
            }
        });
        view.startCutdown(position,time, period);

    }
}
