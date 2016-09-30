package cn.hi028.android.highcommunity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.hi028.android.highcommunity.R;

/**
 * 引导页（功能介绍）
 */
public class GuideMainActivity extends Activity {

    private ViewPager pager;
    private ArrayList<View> list;

    private PagerAdapter adapter;

    private ImageView curDot;
    private int offset;
    private int curPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        System.gc();
        setContentView(R.layout.page_guide);
        initSubView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        pager = null;
        list.clear();
        list = null;
    }

    protected void initSubView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imge1 = new ImageView(this);
        ImageView imge2 = new ImageView(this);
        ImageView imge3 = new ImageView(this);
        ImageView imge4 = new ImageView(this);
        imge1.setLayoutParams(params);
        imge1.setScaleType(ImageView.ScaleType.FIT_XY);
        imge1.setImageResource(R.drawable.welcome_guid1);
        imge2.setLayoutParams(params);
        imge2.setScaleType(ImageView.ScaleType.FIT_XY);
        imge2.setImageResource(R.drawable.welcome_guid2);
        imge3.setLayoutParams(params);
        imge3.setScaleType(ImageView.ScaleType.FIT_XY);
        imge3.setImageResource(R.drawable.welcome_guid3);
        imge4.setLayoutParams(params);
        imge4.setScaleType(ImageView.ScaleType.FIT_XY);
        imge4.setImageResource(R.drawable.welcome_guid4);

//        RelativeLayout fouth = new RelativeLayout(this);
//        fouth.addView(imge4);
//        TextView entery = new TextView(this);
//        entery.setText("进入社区");
//        entery.setTextSize(16);
//        LinearLayout.LayoutParams tparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        tparams.gravity = Gravity.CENTER;
//        tparams.setMargins(0, 0, 0, HighCommunityUtils.GetInstantiation().dip2px(56));
//        entery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        fouth.addView(entery);
        pager = (ViewPager) findViewById(R.id.middle_pager);
        curDot = (ImageView) findViewById(R.id.cur_dot);

        list = new ArrayList<View>();
        list.add(imge1);
        list.add(imge2);
        list.add(imge3);
        list.add(imge4);

        adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        curDot.getViewTreeObserver().addOnPreDrawListener(
                new OnPreDrawListener() {
                    public boolean onPreDraw() {
                        offset = curDot.getWidth();
                        return true;
                    }
                });

        pager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int arg0) {
                moveCursorTo(arg0);
                curPos = arg0;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }

        });
    }

    private void moveCursorTo(int position) {
        TranslateAnimation anim = new TranslateAnimation(offset * curPos,
                offset * position, 0, 0);
        anim.setDuration(300);
        anim.setFillAfter(true);
        curDot.startAnimation(anim);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (list != null && list.size() > 0) {
                return list.size();
            }
            return 0;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
