package cn.hi028.android.highcommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.jeremyfeinstein.slidingmenu.lib.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import java.util.ArrayList;

import cn.hi028.android.highcommunity.bean.UrlsBean;
import photo.activity.BaseActivity;
import photo.activity.ImageFile;
import photo.util.Bimp;
import photo.util.PublicWay;
import photo.util.Res;
import photo.zoom.PhotoView;
import photo.zoom.PhotoViewAttacher;
import photo.zoom.ViewPagerFixed;

/**
 * 这是个用户图片浏览的界面
 *
 * @author ling
 * @version 2016年2月23日  13:13:53
 * @QQ:513198868
 */
public class PhotoScanActivity extends BaseActivity {
    private Intent intent;
    // 返回按钮
    private Button back_bt;
    //底部显示预览图片位置的textview
    private TextView mPosition;
    //当前的位置
    private int location = 0;
    
    private ArrayList<View> listViews = null;
    private ViewPagerFixed pager;
    private MyPageAdapter adapter;
    private View mHight;
    
    private Context mContext;
    private Handler mHandler;
    
    UrlsBean mBean;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("photo_scan_layout"));// 切屏到主界面
        PublicWay.activityList.add(this);
        mContext = this;
        mHight = this.findViewById(R.id.title_secondTitle_Hight);
        mHight.setBackgroundColor(Color.parseColor("#000000"));
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        } else {
            mHight.setVisibility(View.VISIBLE);
        }
        back_bt = (Button) findViewById(Res.getWidgetID("photo_scan_back"));
        mPosition = (TextView) findViewById(Res.getWidgetID("photo_scan_position"));
        back_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBean = (UrlsBean) getIntent().getSerializableExtra("data");
        isShowOkBt();
        // 为发送按钮设置文字
        pager = (ViewPagerFixed) findViewById(Res.getWidgetID("photo_scan_viewPager"));
        pager.setOnPageChangeListener(pageChangeListener);
        if (mBean != null && mBean.getmUrlList().size() > 0)
            for (int i = 0; i < mBean.getmUrlList().size(); i++) {
                initListViews(mBean.getmUrlList().get(i));
            }

        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        pager.setPageMargin((int) getResources().getDimensionPixelOffset(Res.getDimenID("ui_10_dip")));
        int id = getIntent().getIntExtra("ID", 0);
        pager.setCurrentItem(id);
        mPosition.setText(id + 1 + "/" + mBean.getmUrlList().size());
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
            mPosition.setText(arg0 + 1 + "/" + mBean.getmUrlList().size());
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void initListViews(String bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        View v = LayoutInflater.from(this).inflate(Res.getLayoutID("adapter_scan_pic"), null);
        PhotoView img = (PhotoView) v.findViewById(Res.getWidgetID("img_pic"));
        final ProgressBar pg = (ProgressBar) v.findViewById(Res.getWidgetID("pg_progress"));
        ImageLoaderUtil.disPlay(bm, img, 0, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                pg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                pg.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
//        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT));
        img.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                PhotoScanActivity.this.finish();
            }
        });
        listViews.add(v);
    }

    // 返回按钮添加的监听器
    private class BackListener implements OnClickListener {

        public void onClick(View v) {
            intent.setClass(PhotoScanActivity.this, ImageFile.class);
            startActivity(intent);
        }
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
//            send_bt.setText(Res.getString("finish") + "(" + (Bimp.tempSelectBitmap.size() - 1) + "/" + PublicWay.num + ")");
//            send_bt.setPressed(true);
//            send_bt.setClickable(true);
//            send_bt.setTextColor(Color.WHITE);
        } else {
//            send_bt.setPressed(false);
//            send_bt.setClickable(false);
//            send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    /**
     * 监听返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;

        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
