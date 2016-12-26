package cn.hi028.android.highcommunity.view.nine;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.activity.fragment.CommunityFrag;
import cn.hi028.android.highcommunity.bean.UrlsBean;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * Created by lee_yting on 2016/9/15.
 * 邻里九宫格布局
 */
public class MyNineGridView extends NineGridView {
    BitmapUtils  bitmapUtils;
    Context mContext;

    public MyNineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        bitmapUtils = MBitmapHolder.getBitmapUtils(context);
    }

    public MyNineGridView(Context context) {
        super(context);
        mContext=context;
        bitmapUtils = MBitmapHolder.getBitmapUtils(context);
    }
    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        bitmapUtils.display(imageView,url, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView view, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
//                Bitmap bitmap2= BitmapHandler.createRoundBitmap(bitmap);
                view.setImageBitmap(bitmap);
            }
            @Override
            public void onLoadFailed(ImageView view, String s, Drawable drawable) {
            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        bitmapUtils.display(imageView,url, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView view, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
//                Bitmap bitmap2= BitmapHandler.createRoundBitmap(bitmap);
                view.setImageBitmap(bitmap);
            }
            @Override
            public void onLoadFailed(ImageView view, String s, Drawable drawable) {

            }
        });
    }
    @Override
    protected void onClickImage(int position, String url, List<String> bigUrlList,boolean isIntent) {
        if (isIntent){
//        Toast.makeText(mContext, "点击了 " + position+" 跳转商家id: "+bigUrlListgUrlList.get(position), Toast.LENGTH_SHORT).show();
//            Intent mMerchant = new Intent(mContext, MerchantActivity.class);
//            mMerchant.putExtra("id",bigUrlList.get(position));
//            mContext.startActivity(mMerchant);
        }else{
            UrlsBean mUrls = new UrlsBean();
            mUrls.getmUrlList().addAll(bigUrlList);
            CommunityFrag.isNeedRefresh = false;
            Intent mBigPhoto = new Intent(mContext, PhotoScanActivity.class);
            mBigPhoto.putExtra("data", mUrls);
            mBigPhoto.putExtra("ID", position);
            mContext.startActivity(mBigPhoto);
            scanForActivity(mContext).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);

        }
//        if (mContext==null) {
//			return;
//		}else if (mContext instanceof Activity) {
//			
//			((Activity) mContext).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
//		}else if (mContext instanceof ContextWrapper) {
//			((ContextWrapper) mContext).getBaseContext()).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
//		}

    }
    
    private static Activity scanForActivity(Context cont) {  
        if (cont == null)  
            return null;  
        else if (cont instanceof Activity)  
            return (Activity) cont;  
        else if (cont instanceof ContextWrapper)  
            return scanForActivity(((ContextWrapper) cont).getBaseContext());  
      
        return null;  
    } 
}
