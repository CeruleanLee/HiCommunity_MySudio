package cn.hi028.android.highcommunity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.NewSupplyMoreAct_2;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * @功能：直供商品分类adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class SupplyCategoryListAdapter extends BaseFragmentAdapter {
    public static final String TAG = "SuppCategoryAdapter：";
    List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity> mList = new ArrayList<NewSupplyBean.NewSupplyDataEntity.CategoryEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    int   bigImgWith=HighCommunityApplication.bigImgWith;
    //因为高太多，有减掉了40
    int bigImgHeight=HighCommunityApplication.bigImgHeight;
    int smallImgWith=HighCommunityApplication.smallImgWith;
    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;

    public SupplyCategoryListAdapter(List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity> list, Context context) {
        super();
        Log.e(TAG, "SupplyCategoryListAdapter");

        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<NewSupplyBean.NewSupplyDataEntity.CategoryEntity>();
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
    public NewSupplyBean.NewSupplyDataEntity.CategoryEntity getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_supply_coegory, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_category_tv_title);
            mViewHolder.mLine = convertView.findViewById(R.id.position_line);
            mViewHolder.mTvMore = (TextView) convertView.findViewById(R.id.item_category_tv_more);
            mViewHolder.mBigView = (RelativeLayout) convertView.findViewById(R.id.item_category_bigpic);
            mViewHolder.mSmallview1 = (RelativeLayout) convertView.findViewById(R.id.item_category_smallpic1);
            mViewHolder.mSmallview2 = (RelativeLayout) convertView.findViewById(R.id.item_category_smallpic2);
            mViewHolder.itemCateLayout1 = (RelativeLayout) convertView.findViewById(R.id.item_cate_layout_1);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getName());
        //动态加载大图
//        mViewHolder.mBigView
        // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
        View bigView = getBigView(mBean);
        mViewHolder.mBigView.addView(bigView);
        //第一个小图
        View smallView1 = getSmallView1(mBean);
        mViewHolder.mSmallview1.addView(smallView1);
        //第一个小图
        View smallView2 = getSmallView2(mBean);
        mViewHolder.mSmallview2.addView(smallView2);

        mViewHolder.itemCateLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "到更多:"+mBean.getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent_report = new Intent(context, NewSupplyMoreAct_2.class);
                mIntent_report.putExtra("category_id", mBean.getId());
                context.startActivity(mIntent_report);
            }
        });
        mViewHolder.mBigView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "详情:"+mBean.getGoods().get(0).getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", mBean.getGoods().get(0).getId());
                context.startActivity(mIntent);
            }
        });
        mViewHolder.mSmallview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "详情:"+mBean.getGoods().get(1).getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", mBean.getGoods().get(1).getId());
                context.startActivity(mIntent);
            }
        });
        mViewHolder.mSmallview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "详情:"+mBean.getGoods().get(2).getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", mBean.getGoods().get(2).getId());
                context.startActivity(mIntent);
            }
        });

        return convertView;
    }

    /**
     * 第2个小图
     *
     * @param mBean
     * @return
     */
    private View getSmallView2(final NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean) {
        View smallView1 = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_small, null);
        SimpleDraweeView msmallGoodsimg2;
        TextView msmallTvTag2;
        TextView msmallTitle2;
        TextView msmallNowPrice2;
        final ImageView msmallShopcart2;
        msmallGoodsimg2 = (SimpleDraweeView) smallView1.findViewById(R.id.category_small_goodsimg_goodsimg);
        msmallGoodsimg2.getLayoutParams().width=smallImgWith;
        msmallGoodsimg2.getLayoutParams().height=smallImgWith;
        msmallTvTag2 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_tv_tag);
        msmallTitle2 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_goodsTitle);
        msmallNowPrice2 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_nowPrice);
        msmallShopcart2 = (ImageView) smallView1.findViewById(R.id.category_small_goodsimg_shopcart);
        if (mBean.getGoods().get(2).getCover_pic() == null || mBean.getGoods().get(2).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, msmallGoodsimg2);
        } else {
            msmallGoodsimg2.setImageURI(Constacts.IMAGEHTTP + mBean.getGoods().get(2).getCover_pic());
//            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(2).getCover_pic(), msmallGoodsimg2);
        }
        if (mBean.getGoods().get(1).getLabel()!=null){

            msmallTvTag2.setText(mBean.getGoods().get(2).getLabel());
        }else{
            msmallTvTag2.setVisibility(View.GONE);
        }
        msmallTitle2.setText(mBean.getGoods().get(2).getName());
        msmallNowPrice2.setText("￥:" + mBean.getGoods().get(2).getPrice());
        msmallShopcart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", mBean.getGoods().get(2).getId());
                context.startActivity(mIntent);
            }
        });
        return smallView1;
    }

    BitmapUtils bitmapUtils;

    /**
     * 第一个小图
     *
     * @param mBean
     * @return
     */
    private View getSmallView1(final NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean) {
        View smallView1 = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_small, null);
        SimpleDraweeView msmallGoodsimg1;
        TextView msmallTvTag1;
        TextView msmallTitle1;
        TextView msmallNowPrice1;
        final ImageView msmallShopcart1;
        msmallGoodsimg1 = (SimpleDraweeView) smallView1.findViewById(R.id.category_small_goodsimg_goodsimg);
        msmallGoodsimg1.getLayoutParams().width=smallImgWith;
        msmallGoodsimg1.getLayoutParams().height=smallImgWith;
        msmallTvTag1 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_tv_tag);
        msmallTitle1 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_goodsTitle);
        msmallNowPrice1 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_nowPrice);
        msmallShopcart1 = (ImageView) smallView1.findViewById(R.id.category_small_goodsimg_shopcart);
        if (mBean.getGoods().get(1).getCover_pic() == null || mBean.getGoods().get(1).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, msmallGoodsimg1);
        } else {
            msmallGoodsimg1.setImageURI(Constacts.IMAGEHTTP + mBean.getGoods().get(1).getCover_pic());
//            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(1).getCover_pic(), msmallGoodsimg1);
        }
        if (mBean.getGoods().get(1).getLabel()!=null){

            msmallTvTag1.setText(mBean.getGoods().get(1).getLabel());
        }else{
            msmallTvTag1.setVisibility(View.GONE);
        }
        msmallTitle1.setText(mBean.getGoods().get(1).getName());
        msmallNowPrice1.setText("￥:" + mBean.getGoods().get(1).getPrice());
        msmallShopcart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", mBean.getGoods().get(1).getId());
                context.startActivity(mIntent);
            }
        });
        return smallView1;
    }

    @SuppressLint("NewApi")
    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(victim);
        } else {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(victim);
        }
    }

    /**
     * 动态加载大图
     *
     * @param mBean
     * @return
     */
    @NonNull
    private View getBigView(final NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean) {
        View bigView = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_big, null);
        SimpleDraweeView mBigGoodsimg;
        TextView mbigTvTag;
        TextView mbigTitle;
        TextView mbigNowPrice;
        final ImageView mBigShopcart;
        mBigGoodsimg = (SimpleDraweeView) bigView.findViewById(R.id.category_big_goodsimg);
        mBigGoodsimg.getLayoutParams().width=bigImgWith;
        mBigGoodsimg.getLayoutParams().height=bigImgHeight;
        mbigTvTag = (TextView) bigView.findViewById(R.id.category_big_tv_tag);
        mbigTitle = (TextView) bigView.findViewById(R.id.category_big_goodsTitle);
        mbigNowPrice = (TextView) bigView.findViewById(R.id.category_big_nowPrice);
        mBigShopcart = (ImageView) bigView.findViewById(R.id.category_big_shopcart);
        if (mBean.getGoods().get(0).getCover_pic() == null || mBean.getGoods().get(0).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mBigGoodsimg);
        } else {
            mBigGoodsimg.setImageURI(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic());
//            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic(), mBigGoodsimg);
        }
        if (mBean.getGoods().get(0).getLabel()!=null){

            mbigTvTag.setText(mBean.getGoods().get(0).getLabel());
        }else{
            mbigTvTag.setVisibility(View.GONE);
        }
        mbigTitle.setText(mBean.getGoods().get(0).getName());
        mbigNowPrice.setText("￥:" + mBean.getGoods().get(0).getPrice());
        mBigShopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (HighCommunityUtils.isLogin(context)) {
//                    waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(context, mBigShopcart, Gravity.CENTER);
//                    HTTPHelper.addNewHuiGoodsToCar(mIbpiAddShopCar, mBean.getGoods().get(0).getId(), mBean.getGoods().get(0).getId());
//                }
                Intent mIntent = new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id", mBean.getGoods().get(0).getId());
                context.startActivity(mIntent);
            }
        });
        return bigView;
    }


    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView mTitle;
        View mLine;
        TextView mTvMore;
        RelativeLayout itemCateLayout1;
        RelativeLayout mBigView;
        RelativeLayout mSmallview1;
        RelativeLayout mSmallview2;

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
            Log.e(TAG, "onResolve result" + result);
            return result;
        }
        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }
        @Override
        public void cancleAsyncTask() {
            waitPop.dismiss();
        }
    };


}
