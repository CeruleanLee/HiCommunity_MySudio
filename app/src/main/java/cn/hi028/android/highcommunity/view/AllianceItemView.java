package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.AllianceOrderBean;
import cn.hi028.android.highcommunity.utils.Constacts;

/**
 * Created by Administrator on 2016/8/10.
 *联盟订单listview里边的item的view 
 */
public class AllianceItemView extends LinearLayout {
    private Context mContext;
    private ImageView mItemPic;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemPrices;
    private TextView mItemCounts;

    public AllianceItemView(Context context) {
        super(context);
        init(context);
    }

    public AllianceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AllianceItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View v = LayoutInflater.from(context).inflate(R.layout.item_alliance_order_item, null);
        mItemPic = (ImageView) v.findViewById(R.id.alliance_order_item_pic);
        mItemName = (TextView) v.findViewById(R.id.alliance_order_item_name);
        mItemPrice = (TextView) v.findViewById(R.id.alliance_order_item_price);
        mItemPrices = (TextView) v.findViewById(R.id.alliance_order_item_prices);
        mItemCounts = (TextView) v.findViewById(R.id.alliance_order_item_counts);
        this.addView(v);
    }

    public void onBindData(AllianceOrderBean.AllianceOrderGoodsInfoBean bean) {
        if (!TextUtils.isEmpty(bean.getThumb_pic())) {
            Picasso.with(mContext).load(Constacts.IMAGEHTTP + bean.getThumb_pic())
                    .into(mItemPic);
        }
        if (!TextUtils.isEmpty(bean.getGoods_name())) {
            mItemName.setText(bean.getGoods_name());
        }
        if (bean.getGoods_price() > 0) {
            mItemPrice.setText(String.format(mContext.getString(R.string.str_format_itme_price), String.valueOf(bean.getGoods_price())));
        }
        if (bean.getGoods_total_price() > 0) {
            mItemPrices.setText(String.format(mContext.getString(R.string.str_format_itme_prices), String.valueOf(bean.getGoods_total_price())));
        }
        if (bean.getNumber() > 0) {
            mItemCounts.setText(String.valueOf(bean.getNumber()));
        }
    }
}
