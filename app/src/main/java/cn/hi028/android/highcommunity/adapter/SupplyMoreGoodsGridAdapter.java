package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity2;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.bean.SupplyGoodsMoreBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：直供商品分类adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class SupplyMoreGoodsGridAdapter extends BaseFragmentAdapter {
    public static final String Tag = "SupplyMoreGoodsGridAdapter：";
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity> mList
            = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;

    public SupplyMoreGoodsGridAdapter(List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
//        bitmapUtils = MBitmapHolder.getBitmapUtils(context);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    ViewHolder mViewHolder = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_supplygoodsmore, null);
            mViewHolder.mGoodsimg = (ImageView) convertView.findViewById(R.id.supplygoodsmore_goodsimg);
            mViewHolder.mTvTag = (TextView) convertView.findViewById(R.id.supplygoodsmore_tv_tag);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.supplygoodsmore_goodsTitle);
            mViewHolder.mNowPrice = (TextView) convertView.findViewById(R.id.supplygoodsmore_nowPrice);
            mViewHolder.mOldPrice = (TextView) convertView.findViewById(R.id.supplygoodsmore_oldPrice);
            mViewHolder.mSaledNum = (TextView) convertView.findViewById(R.id.supplygoodsmore_saledNum);
            mViewHolder.mShopcart = (ImageView) convertView.findViewById(R.id.supplygoodsmore_shopcart);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity mBean = mList.get(position);

        if (mBean.getCover_pic()== null || mBean.getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.mGoodsimg);
        } else {

            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getCover_pic(), mViewHolder.mGoodsimg);
        }
        mViewHolder.mTvTag.setText(mBean.getLabel());
        mViewHolder.mTitle.setText(mBean.getName());
        mViewHolder.mNowPrice.setText("￥:"+mBean.getPrice());
        mViewHolder.mTvTag.setText(mBean.getLabel());
        Spannable spanStrikethrough = new SpannableString("￥：" + mBean.getOld_price());
        StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
//			spanStrikethrough.setSpan(stSpan, 0, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        Log.e(TAG,"长度--->"+spanStrikethrough.length());
        try {
            spanStrikethrough.setSpan(stSpan, 0, spanStrikethrough.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }catch (Exception ex){

        }
        mViewHolder.mOldPrice.setText(spanStrikethrough);
        mViewHolder.mSaledNum.setText("已售"+mBean.getSale());

       mViewHolder.mShopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "加入购物车", Toast.LENGTH_SHORT).show();
//                if (HighCommunityUtils.isLogin(context)) {
//                    waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(context, mViewHolder.mShopcart, Gravity.CENTER);
//                    HTTPHelper.addNewHuiGoodsToCar(mIbpiAddShopCar, mBean.getId(), mBean.getId());
//                }
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
        return convertView;
    }


    @Override
    public void AddNewData(Object mObject) {
        Log.d(Tag, "---~~~AddNewData");

        if (mObject instanceof List<?>) {
            mList = (List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreGoodsEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView mGoodsimg;
        TextView mTvTag;
        TextView mTitle;
        TextView mNowPrice;
        TextView mOldPrice;
        TextView mSaledNum;
        ImageView mShopcart;
    }


    /**
     * 加入购物车弹窗
     */
    PopupWindow waitPop;
    /**
     * 加入购物车回调
     */
    BpiHttpHandler.IBpiHttpHandler mIbpiAddShopCar = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            waitPop.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast("成功加入购物车", 0);
        }

        @Override
        public Object onResolve(String result) {
            Log.e(Tag,"onResolve result"+result);
            return  result;
//            return HTTPHelper.ResolveHuiSupportList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }

        @Override
        public void shouldLogin(boolean isShouldLogin) {

        }

        @Override
        public void shouldLoginAgain(boolean isShouldLogin, String msg) {
            if (isShouldLogin){
                HighCommunityUtils.GetInstantiation().ShowToast(msg, 0);
                HighCommunityApplication.toLoginAgain(context);
            }
        }
    };



}
