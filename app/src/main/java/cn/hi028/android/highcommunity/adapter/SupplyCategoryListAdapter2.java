package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.NewSupplyMoreAct3;
import cn.hi028.android.highcommunity.activity.alliance.SupplyGoodsDetailActivity2;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;
import cn.hi028.android.highcommunity.view.MyNoScrollMeasureListview;

/**
 * @功能：直供商品分类adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class SupplyCategoryListAdapter2 extends BaseFragmentAdapter {
    public static final String TAG = "SuppCategoryAdapter2：";
    List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity> mList = new ArrayList<NewSupplyBean.NewSupplyDataEntity.CategoryEntity>();
    private Context context;
    private LayoutInflater layoutInflater;


    public SupplyCategoryListAdapter2(List<NewSupplyBean.NewSupplyDataEntity.CategoryEntity> list, Context context) {
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
BitmapUtils bitmapUtils;
    @Override
    public int getCount() {
        return mList.size();
    }
int n=0;
    @Override
    public NewSupplyBean.NewSupplyDataEntity.CategoryEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        Log.e(TAG,"~~~getView");
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_supply_coegory2, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_category_tv_title);
            mViewHolder.mLine = convertView.findViewById(R.id.position_line);
            mViewHolder.mTvMore = (TextView) convertView.findViewById(R.id.item_category_tv_more);
            mViewHolder.mBigView = (RelativeLayout) convertView.findViewById(R.id.item_category_bigpic);
            mViewHolder.mSmallview1 = (RelativeLayout) convertView.findViewById(R.id.item_category_smallpic1);
            mViewHolder.mSmallview2 = (RelativeLayout) convertView.findViewById(R.id.item_category_smallpic2);
            mViewHolder.itemCateLayout1 = (RelativeLayout) convertView.findViewById(R.id.item_cate_layout_1);
//1

            mViewHolder.mBigGoodsimg = (ImageView) convertView.findViewById(R.id.category_big_goodsimg);
            mViewHolder.mbigTvTag = (TextView) convertView.findViewById(R.id.category_big_tv_tag);
            mViewHolder.mbigTitle = (TextView) convertView.findViewById(R.id.category_big_goodsTitle);
            mViewHolder.mbigNowPrice = (TextView) convertView.findViewById(R.id.category_big_nowPrice);
            mViewHolder.mBigShopcart = (ImageView) convertView.findViewById(R.id.category_big_shopcart);
            //2

            mViewHolder. msmallGoodsimg1 = (ImageView) convertView.findViewById(R.id.category_small_goodsimg_goodsimg);
            mViewHolder.msmallTvTag1 = (TextView) convertView.findViewById(R.id.category_small_goodsimg_tv_tag);
            mViewHolder.msmallTitle1 = (TextView) convertView.findViewById(R.id.category_small_goodsimg_goodsTitle);
            mViewHolder.msmallNowPrice1 = (TextView) convertView.findViewById(R.id.category_small_goodsimg_nowPrice);
            mViewHolder.msmallShopcart1 = (ImageView) convertView.findViewById(R.id.category_small_goodsimg_shopcart);
            //3
            mViewHolder.msmallGoodsimg2 = (ImageView) convertView.findViewById(R.id.category_small_goodsimg_goodsimg2);
            mViewHolder.msmallTvTag2 = (TextView) convertView.findViewById(R.id.category_small_goodsimg_tv_tag2);
            mViewHolder.msmallTitle2 = (TextView) convertView.findViewById(R.id.category_small_goodsimg_goodsTitle2);
            mViewHolder.msmallNowPrice2 = (TextView) convertView.findViewById(R.id.category_small_goodsimg_nowPrice2);
            mViewHolder. msmallShopcart2 = (ImageView) convertView.findViewById(R.id.category_small_goodsimg_shopcart2);
        convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        /**************/
        if(parent instanceof MyNoScrollMeasureListview&&((MyNoScrollMeasureListview) parent).isMeasure){
            Log.e(TAG,"~~~isMeasure");
            Log.e(TAG,"~~~isMeasure position---"+position);
            return convertView;

        }
        Log.e(TAG,"~~!  isMeasure ~position---"+position);
        Log.e(TAG,"~~~!  isMeasure");
        final NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean = mList.get(position);
       if (mBean.getGoods().size()==3) {

           Log.e(TAG,"~~~   size 3");

           convertView.setVisibility(View.VISIBLE);
           mViewHolder.mTitle.setText(mBean.getName());

           if (mBean.getGoods().get(0).getCover_pic() == null || mBean.getGoods().get(0).getCover_pic().equals("")) {
               BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.mBigGoodsimg);
           } else {
//            mViewHolder. mBigGoodsimg.setImageURI(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic());
               BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic(), mViewHolder. mBigGoodsimg);
           }
           if (mBean.getGoods().get(0).getLabel()!=null&&!mBean.getGoods().get(0).getLabel().equals("")){

               mViewHolder.mbigTvTag.setText(mBean.getGoods().get(0).getLabel());
           }else{
               mViewHolder. mbigTvTag.setVisibility(View.GONE);
           }
           mViewHolder.mbigTitle.setText(mBean.getGoods().get(0).getName());
           mViewHolder.mbigNowPrice.setText("￥:" + mBean.getGoods().get(0).getPrice());
           mViewHolder. mBigShopcart.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                if (HighCommunityUtils.isLogin(context)) {
//                    waitPop = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(context, mBigShopcart, Gravity.CENTER);
//                    HTTPHelper.addNewHuiGoodsToCar(mIbpiAddShopCar, mBean.getGoods().get(0).getId(), mBean.getGoods().get(0).getId());
//                }
                   Intent mIntent = new Intent(context, SupplyGoodsDetailActivity2.class);
                   mIntent.putExtra("id", mBean.getGoods().get(0).getId());
                   context.startActivity(mIntent);
               }
           });
           //第一个小图
           /***********************************/

           if (mBean.getGoods().get(1).getCover_pic() == null || mBean.getGoods().get(1).getCover_pic().equals("")) {
               BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.msmallGoodsimg1);
           } else {
//            mViewHolder. msmallGoodsimg1.setImageURI(Constacts.IMAGEHTTP + mBean.getGoods().get(1).getCover_pic());
               BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(1).getCover_pic(), mViewHolder. msmallGoodsimg1);
           }
           if (mBean.getGoods().get(1).getLabel()!=null&&!mBean.getGoods().get(1).getLabel().equals("")){

               mViewHolder. msmallTvTag1.setText(mBean.getGoods().get(1).getLabel());
           }else{
               mViewHolder.msmallTvTag1.setVisibility(View.GONE);
           }
           mViewHolder. msmallTitle1.setText(mBean.getGoods().get(1).getName());
           mViewHolder. msmallNowPrice1.setText("￥:" + mBean.getGoods().get(1).getPrice());
           mViewHolder. msmallShopcart1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent mIntent = new Intent(context, SupplyGoodsDetailActivity2.class);
                   mIntent.putExtra("id", mBean.getGoods().get(1).getId());
                   context.startActivity(mIntent);
               }
           });
           //第2个小图
           /************************************/


           if (mBean.getGoods().get(2).getCover_pic() == null || mBean.getGoods().get(2).getCover_pic().equals("")) {
               BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.msmallGoodsimg2);
           } else {
//            mViewHolder.msmallGoodsimg2.setImageURI(Constacts.IMAGEHTTP + mBean.getGoods().get(2).getCover_pic());
               BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(2).getCover_pic(), mViewHolder.msmallGoodsimg2);
           }
           if (mBean.getGoods().get(2).getLabel()!=null&&!mBean.getGoods().get(2).getLabel().equals("")){

               mViewHolder.msmallTvTag2.setText(mBean.getGoods().get(2).getLabel());
           }else{
               mViewHolder. msmallTvTag2.setVisibility(View.GONE);
           }
           mViewHolder. msmallTitle2.setText(mBean.getGoods().get(2).getName());
           mViewHolder.msmallNowPrice2.setText("￥:" + mBean.getGoods().get(2).getPrice());
           mViewHolder.msmallShopcart2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent mIntent = new Intent(context, SupplyGoodsDetailActivity2.class);
                   mIntent.putExtra("id", mBean.getGoods().get(2).getId());
                   context.startActivity(mIntent);
               }
           });
           mViewHolder.itemCateLayout1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                Toast.makeText(context, "到更多:"+mBean.getId(), Toast.LENGTH_SHORT).show();
                   Intent mIntent_report = new Intent(context, NewSupplyMoreAct3.class);
//                Intent mIntent_report = new Intent(context, textActivity.class);
                   mIntent_report.putExtra("category_id", mBean.getId());
                   context.startActivity(mIntent_report);
               }
           });
           mViewHolder.mBigView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                Toast.makeText(context, "详情:"+mBean.getGoods().get(0).getId(), Toast.LENGTH_SHORT).show();
                   Intent mIntent = new Intent(context, SupplyGoodsDetailActivity2.class);
                   mIntent.putExtra("id", mBean.getGoods().get(0).getId());
                   context.startActivity(mIntent);
               }
           });
           mViewHolder.mSmallview1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                Toast.makeText(context, "详情:"+mBean.getGoods().get(1).getId(), Toast.LENGTH_SHORT).show();
                   Intent mIntent = new Intent(context, SupplyGoodsDetailActivity2.class);
                   mIntent.putExtra("id", mBean.getGoods().get(1).getId());
                   context.startActivity(mIntent);
               }
           });
           mViewHolder.mSmallview2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                Toast.makeText(context, "详情:"+mBean.getGoods().get(2).getId(), Toast.LENGTH_SHORT).show();
                   Intent mIntent = new Intent(context, SupplyGoodsDetailActivity2.class);
                   mIntent.putExtra("id", mBean.getGoods().get(2).getId());
                   context.startActivity(mIntent);
               }
           });
       }else{
           Log.e(TAG,"~~~   size !3  position:"+position);

           convertView.setVisibility(View.GONE);
       }
        return convertView;

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


        ImageView mBigGoodsimg;
        TextView mbigTvTag;
        TextView mbigTitle;
        TextView mbigNowPrice;
        ImageView msmallGoodsimg1;
        TextView msmallTvTag1;
        TextView msmallTitle1;
        TextView msmallNowPrice1;
        ImageView msmallShopcart1;
        ImageView msmallGoodsimg2;
        TextView msmallTvTag2;
        TextView msmallTitle2;
        TextView msmallNowPrice2;
        ImageView msmallShopcart2;
        ImageView mBigShopcart;


    }


}
