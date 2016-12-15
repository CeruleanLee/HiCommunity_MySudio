package cn.hi028.android.highcommunity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiUniveralImage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.NewSupplyMoreAct;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * @功能：直供商品分类adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class SupplyCategoryListAdapter extends BaseFragmentAdapter {
    public static final String TAG = "SupplyCategoryListAdapter：";
    List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity> mList = new ArrayList<NewSupplyBean.NewSupplyDataEntity.CategoryEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;

    public SupplyCategoryListAdapter(List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity> list, Context context) {
        super();
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
    ImageView mBigGoodsimg, msmallGoodsimg1, msmallGoodsimg2;
    TextView mbigTvTag, msmallTvTag1, msmallTvTag2;
    TextView mbigTitle, msmallTitle1, msmallTitle2;
    TextView mbigNowPrice, msmallNowPrice1, msmallNowPrice2;
    ImageView mBigShopcart, msmallShopcart1, msmallShopcart2;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_supply_coegory, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_category_tv_title);
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
                Toast.makeText(context, "到更多:"+mBean.getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent_report = new Intent(context, NewSupplyMoreAct.class);
                mIntent_report.putExtra("category_id", mBean.getId());
                context.startActivity(mIntent_report);
            }
        });
        mViewHolder.mBigView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "详情:"+mBean.getGoods().get(0).getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id",mBean.getGoods().get(0).getId());
                context.startActivity(mIntent);
            }
        });
        mViewHolder.mSmallview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "详情:"+mBean.getGoods().get(1).getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id",mBean.getGoods().get(1).getId());
                context.startActivity(mIntent);
            }
        });
        mViewHolder.mSmallview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "详情:"+mBean.getGoods().get(2).getId(), Toast.LENGTH_SHORT).show();
                Intent mIntent=new Intent(context, SupplyGoodsDetailActivity.class);
                mIntent.putExtra("id",mBean.getGoods().get(2).getId());
                context.startActivity(mIntent);
            }
        });

        return convertView;
    }
    /**
     * 第2个小图
     * @param mBean
     * @return
     */
    private View getSmallView2(NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean) {
        View smallView1 = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_small, null);
        msmallGoodsimg2 = (ImageView) smallView1.findViewById(R.id.category_small_goodsimg_goodsimg);
//        ViewTreeObserver vto = msmallGoodsimg2.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//                removeOnGlobalLayoutListener(msmallGoodsimg2,this);
//                int newwidth= msmallGoodsimg2.getWidth();
//                int newheight = msmallGoodsimg2.getHeight();
//                Log.d(TAG,"here sec"+"--->"+ newwidth +"*"+ newheight);
//                RelativeLayout.LayoutParams prams=new RelativeLayout.LayoutParams(newwidth,newwidth);
//                msmallGoodsimg2.setLayoutParams(prams);
//            }
//        });
        msmallTvTag2 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_tv_tag);
        msmallTitle2 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_goodsTitle);
        msmallNowPrice2 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_nowPrice);
        msmallShopcart2 = (ImageView) smallView1.findViewById(R.id.category_small_goodsimg_shopcart);
        if (mBean.getGoods().get(2).getCover_pic() == null || mBean.getGoods().get(2).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, msmallGoodsimg2);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(2).getCover_pic(), msmallGoodsimg2);
        }
        msmallTvTag2.setText(mBean.getGoods().get(2).getLabel());
        msmallTitle2.setText(mBean.getGoods().get(2).getName());
        msmallNowPrice2.setText("￥:" + mBean.getGoods().get(2).getPrice());
        return smallView1;
    }
    BitmapUtils bitmapUtils;
    /**
     * 第一个小图
     * @param mBean
     * @return
     */
    private View getSmallView1(NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean) {
        View smallView1 = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_small, null);
        msmallGoodsimg1 = (ImageView) smallView1.findViewById(R.id.category_small_goodsimg_goodsimg);
//        ViewTreeObserver vto = msmallGoodsimg1.getViewTreeObserver();
//                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//                removeOnGlobalLayoutListener(msmallGoodsimg1,this);
//                int newwidth= msmallGoodsimg1.getWidth();
//                int newheight = msmallGoodsimg1.getHeight();
//                Log.d(TAG,"here"+"--->"+ newwidth +"*"+ newheight);
//                RelativeLayout.LayoutParams prams=new RelativeLayout.LayoutParams(newwidth,newwidth);
//                msmallGoodsimg1.setLayoutParams(prams);
//            }
//        });
        msmallTvTag1 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_tv_tag);
        msmallTitle1 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_goodsTitle);
        msmallNowPrice1 = (TextView) smallView1.findViewById(R.id.category_small_goodsimg_nowPrice);
        msmallShopcart1 = (ImageView) smallView1.findViewById(R.id.category_small_goodsimg_shopcart);
        if (mBean.getGoods().get(1).getCover_pic() == null || mBean.getGoods().get(1).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, msmallGoodsimg1);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(1).getCover_pic(), msmallGoodsimg1);
        }
        msmallTvTag1.setText(mBean.getGoods().get(1).getLabel());
        msmallTitle1.setText(mBean.getGoods().get(1).getName());
        msmallNowPrice1.setText("￥:" + mBean.getGoods().get(1).getPrice());
//        ViewGroup.LayoutParams layoutParams = msmallGoodsimg1.getLayoutParams();
//        float densityMargin = context.getResources().getDisplayMetrics().density;
//        Log.d(TAG,"layoutParams.width:"+layoutParams.width);
//        Log.d(TAG,"layoutParams.height:"+layoutParams.height);
//        Log.d(TAG,"msmallGoodsimg1.getWidth():"+msmallGoodsimg1.getWidth());
//        Log.d(TAG,"msmallGoodsimg1.getHeight():"+msmallGoodsimg1.getHeight());
//        Log.d(TAG,"msmallGoodsimg1.getMeasuredWidth():"+msmallGoodsimg1.getMeasuredWidth());
//        Log.d(TAG,"msmallGoodsimg1.getMeasuredHeight():"+msmallGoodsimg1.getMeasuredHeight());
//
//        Log.d(TAG,"densityMargin:"+densityMargin);
//        final int[] newheight = new int[1];
//        final int[] newwidth = new int[1];
//        ViewTreeObserver vto = msmallGoodsimg1.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//                removeOnGlobalLayoutListener(msmallGoodsimg1,this);
//                int newwidth= msmallGoodsimg1.getWidth();
//                int newheight = msmallGoodsimg1.getHeight();
//                Log.d(TAG,"here"+"--->"+ newwidth +"*"+ newheight);
//                RelativeLayout.LayoutParams prams=new RelativeLayout.LayoutParams(newwidth,newwidth);
//        msmallGoodsimg1.setLayoutParams(prams);
//
//            }
//        });
//        Log.d(TAG,"new22"+"--->"+newwidth[0]+"*"+newheight[0]);

//        layoutParams.height=msmallGoodsimg1.getWidth();
//        layoutParams.width=msmallGoodsimg1.getWidth();
////        RelativeLayout.LayoutParams prams=new RelativeLayout.LayoutParams(msmallGoodsimg2.getWidth(),msmallGoodsimg2.getWidth());
//        msmallGoodsimg1.setLayoutParams(layoutParams);
//        bitmapUtils.display(msmallGoodsimg1,Constacts.IMAGEHTTP + mBean.getGoods().get(1).getCover_pic());

        return smallView1;
    }
    @SuppressLint("NewApi")
    public static void removeOnGlobalLayoutListener(View view,ViewTreeObserver.OnGlobalLayoutListener victim){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            view.getViewTreeObserver().removeOnGlobalLayoutListener(victim);
        }else{
            view.getViewTreeObserver().removeGlobalOnLayoutListener(victim);
        }
    }
    /**
     * 动态加载大图
     * @param mBean
     * @return
     */
    @NonNull
    private View getBigView(NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean) {
        View bigView = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_big, null);
        mBigGoodsimg = (ImageView) bigView.findViewById(R.id.category_big_goodsimg);
        mbigTvTag = (TextView) bigView.findViewById(R.id.category_big_tv_tag);
        mbigTitle = (TextView) bigView.findViewById(R.id.category_big_goodsTitle);
        mbigNowPrice = (TextView) bigView.findViewById(R.id.category_big_nowPrice);
        mBigShopcart = (ImageView) bigView.findViewById(R.id.category_big_shopcart);
        if (mBean.getGoods().get(0).getCover_pic() == null || mBean.getGoods().get(0).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mBigGoodsimg);
        } else {

            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic(), mBigGoodsimg);
        }
        mbigTvTag.setText(mBean.getGoods().get(0).getLabel());
        mbigTitle.setText(mBean.getGoods().get(0).getName());
        mbigNowPrice.setText("￥:" + mBean.getGoods().get(0).getPrice());
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
        TextView mTvMore;
        RelativeLayout itemCateLayout1;
        RelativeLayout mBigView;
        RelativeLayout mSmallview1;
        RelativeLayout mSmallview2;

    }


}
