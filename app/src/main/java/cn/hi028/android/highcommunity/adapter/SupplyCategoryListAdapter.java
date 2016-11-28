package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.don.tools.BpiUniveralImage;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.bean.NewSupplyBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * @功能：直供商品分类adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class SupplyCategoryListAdapter extends BaseFragmentAdapter {
    BpiHttpHandler.IBpiHttpHandler mIbpi;
    public static final String TAG = "SupplyCategoryListAdapter：";
    public static final int TAG_MOTION_DETAIL = 2;
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

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final NewSupplyBean.NewSupplyDataEntity.CategoryEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getName());
        //动态加载大图
        View bigView = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_big, null);
        mBigGoodsimg = (ImageView) bigView.findViewById(R.id.category_big_goodsimg);
        mbigTvTag = (TextView) bigView.findViewById(R.id.category_big_tv_tag);
        mbigTitle = (TextView) bigView.findViewById(R.id.category_big_goodsTitle);
        mbigNowPrice = (TextView) bigView.findViewById(R.id.category_big_nowPrice);
        mBigShopcart = (ImageView) bigView.findViewById(R.id.category_big_shopcart);
        if (mBean.getGoods().get(0).getCover_pic() == null || mBean.getGoods().get(0).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, mBigGoodsimg);
        } else {

            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic(), mBigGoodsimg);
        }
        mbigTvTag.setText(mBean.getGoods().get(0).getLabel());
        mbigTitle.setText(mBean.getGoods().get(0).getName());
        mbigNowPrice.setText("￥:" + mBean.getGoods().get(0).getPrice());
        mViewHolder.mBigView.addView(bigView);
        //第一个小图
        View smallView1 = LayoutInflater.from(context).inflate(R.layout.item_newsupply_type_small, null);
        msmallGoodsimg1 = (ImageView) bigView.findViewById(R.id.category_small_goodsimg_goodsimg);
        msmallTvTag1 = (TextView) bigView.findViewById(R.id.category_small_goodsimg_tv_tag);
        msmallTitle1 = (TextView) bigView.findViewById(R.id.category_small_goodsimg_goodsTitle);
        msmallNowPrice1 = (TextView) bigView.findViewById(R.id.category_small_goodsimg_nowPrice);
        msmallShopcart1 = (ImageView) bigView.findViewById(R.id.category_small_goodsimg_shopcart);
        if (mBean.getGoods().get(0).getCover_pic() == null || mBean.getGoods().get(0).getCover_pic().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.defult_avatar, msmallGoodsimg1);
        } else {
            BpiUniveralImage.displayImage(Constacts.IMAGEHTTP + mBean.getGoods().get(0).getCover_pic(), msmallGoodsimg1);
        }
        mbigTvTag.setText(mBean.getGoods().get(0).getLabel());
        mbigTitle.setText(mBean.getGoods().get(0).getName());
        mbigNowPrice.setText("￥:" + mBean.getGoods().get(0).getPrice());
        mViewHolder.mSmallview1.addView(smallView1);
        mViewHolder.mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "到更多", Toast.LENGTH_SHORT).show();
//                Intent mIntent_report = new Intent(context, AutonomousAct_Third.class);
//                mIntent_report.putExtra("title", TAG_MOTION_DETAIL);
//                mIntent_report.putExtra("motion_id", mBean.getId());
//                context.startActivity(mIntent_report);
            }
        });


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
        TextView mTvMore;
        RelativeLayout itemCateLayout1;
        RelativeLayout mBigView;
        RelativeLayout mSmallview1;
        RelativeLayout mSmallview2;
    }


}
