package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyMorePagerAdapter;
import cn.hi028.android.highcommunity.bean.SupplyGoodsMoreBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能： 直供商品 更多>>
 * @作者： Lee_yting<br>
 * @版本：新版本 2.0<br>
 * @时间：2016/11/30<br>
 */
public class NewSupplyMoreAct3 extends FragmentActivity {
    public static final String Tag = "NewSupplyMoreAct--->";
    SupplyMorePagerAdapter mPagerAdapter;
    @Bind(R.id.supplyMore_back)
    ImageView mBack;
    @Bind(R.id.supplyMore_rg_category)
    RadioGroup mRgCategory;
    @Bind(R.id.supplyMore_rb_newest)
    RadioButton mRbNewest;
    @Bind(R.id.supplyMore_rb_nums)
    RadioButton mRbNums;
    @Bind(R.id.supplyMore_rb_price)
    RadioButton mRbPrice;
    @Bind(R.id.supplyMore_rg_sort)
    RadioGroup mRgSort;
    @Bind(R.id.HorizontalScrollView)
    HorizontalScrollView mHorizontalScrollView;
//    @Bind(R.id.id_stickynavlayout_viewpager)
    ViewPager mViewpager;


//    private SimpleViewPagerIndicator mIndicator;
    List<RadioButton> mRadioButList = new ArrayList<RadioButton>();
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreCategoryEntity> mDataCategory = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreCategoryEntity>();

    private String[] mTitles = new String[] { "最新", "销量", "价格" };

    int category_id = 0;
    /**
     * 1=>最新,2=>销量,3=>价格升序,4=>价格降序  每次进入默认最新
     **/
    int sort = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_newsupplymore3);
        ButterKnife.bind(this);
        String str_category_id = 0 + "";
        str_category_id = getIntent().getStringExtra("category_id");
        if (str_category_id == null || str_category_id == "") {
            Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        category_id = Integer.parseInt(str_category_id);
//        mySortChangeListener= (MySortChangeListener) this;
//        initView();
        mViewpager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
//        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
//        mIndicator.setTitles(mTitles);

        initDatas();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initDatas() {

        HTTPHelper.GetSupplyGoodsListMore(mIbpi, category_id + "", 1 + "");
    }

    boolean isCategorySizeMuch = false;
    public static final int rbutWith = 80;
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            Log.d(Tag, "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (message == null) return;


            SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity mData = (SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity) message;
            mDataCategory = mData.getCategory();


            mHorizontalScrollView.removeAllViews();
            RadioGroup mGroup=new RadioGroup(NewSupplyMoreAct3.this);
            mGroup.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            mGroup.setOrientation(LinearLayout.HORIZONTAL);

            mRgCategory.removeAllViews();
            Log.e(Tag, "mDataCategory.size(): " + mDataCategory.size());
            if (mDataCategory.size() > 4) {
                isCategorySizeMuch = true;
//                mRgCategory.getLayoutParams().width = RadioGroup.LayoutParams.MATCH_PARENT;
            } else {
                isCategorySizeMuch = false;
//                mRgCategory.getLayoutParams().width = RadioGroup.LayoutParams.MATCH_PARENT;
            }

            RadioButton defaultRadioBut = (RadioButton) LayoutInflater.from(NewSupplyMoreAct3.this).inflate(R.layout.radiobut_newsupplymore, null);
            defaultRadioBut.setId(0);
            if (isCategorySizeMuch) {
                defaultRadioBut.setWidth(0);
//                defaultRadioBut.setLayoutParams(new RadioGroup.LayoutParams(rbutWith, RadioGroup.LayoutParams.MATCH_PARENT));
                defaultRadioBut.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT, 1f));

            } else {

                defaultRadioBut.setWidth(0);
                defaultRadioBut.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT, 1f));
            }

            defaultRadioBut.setText("所有");
            mRgCategory.addView(defaultRadioBut);
//            mGroup.addView(defaultRadioBut);

            for (int i = 0; i < mDataCategory.size(); i++) {
                RadioButton newRadioBut = (RadioButton) LayoutInflater.from(NewSupplyMoreAct3.this).inflate(R.layout.radiobut_newsupplymore, null);
                newRadioBut.setId(Integer.parseInt(mDataCategory.get(i).getId()));
                if (isCategorySizeMuch) {
                    Log.e(Tag, "isCategorySizeMuch ");

//                    newRadioBut.setWidth(rbutWith);
//                    newRadioBut.setLayoutParams(new RadioGroup.LayoutParams(rbutWith, RadioGroup.LayoutParams.MATCH_PARENT));
                    newRadioBut.setWidth(80);
                    newRadioBut.setLayoutParams(new RadioGroup.LayoutParams(300, RadioGroup.LayoutParams.MATCH_PARENT, 1f));
                } else {
                    Log.e(Tag, "isCategorySizeMuch false ");

                    newRadioBut.setWidth(80);
                    newRadioBut.setLayoutParams(new RadioGroup.LayoutParams(300, RadioGroup.LayoutParams.MATCH_PARENT));
//                    newRadioBut.setWidth(0);
//                    newRadioBut.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT, 1f));
                }

                newRadioBut.setText(mDataCategory.get(i).getName());
//                mRgCategory.addView(newRadioBut);
                mGroup.addView(newRadioBut);
            }
            Log.e(Tag, "mRgCategory.size(): " + mRgCategory.getChildCount());
            Log.e(Tag, "mGroup.size(): " + mGroup.getChildCount());
//            ((RadioButton) (mRgCategory.getChildAt(0))).setChecked(true);

//            for (int i = 0; i < mRgCategory.getChildCount(); i++) {
//                if (mRgCategory.getChildAt(i).getId() == category_id) {
//
//                    ((RadioButton) (mRgCategory.getChildAt(i))).setChecked(true);
//
//                }
//            }

            mHorizontalScrollView.addView(mGroup);
            Log.e(Tag, "mHorizontalScrollView.size(): " + mHorizontalScrollView.getChildCount());

            initView();
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveSupplyMoreGoodsListDataEntity(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    private void initView() {

        mPagerAdapter = new SupplyMorePagerAdapter(getSupportFragmentManager(), mRgCategory.getChildCount());
        mViewpager.setAdapter(mPagerAdapter);

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e(Tag, " onPageSelected");

                for (int k = 0; k < mRgCategory.getChildCount(); k++) {
                    if (i == k) {
                        Log.e(Tag, " onPageSelected  i == k");
                        ((RadioButton) (mRgCategory.getChildAt(i))).setChecked(true);
                    }
//                    else {
//                        Log.e(Tag," onPageSelected  i != k");
//                        ((RadioButton) (mRgCategory.getChildAt(k))).setChecked(false);
//
//                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mRgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e(Tag, "mRgCategory setOnCheckedChangeListener");
                for (int i = 0; i < mRgCategory.getChildCount(); i++) {

                    if (mRgCategory.getChildAt(i).getId() == checkedId) {

                        setCurrentPage(i);
                    }


                }
                category_id = checkedId;
//                mPagerAdapter.updateFragmentData(category_id,1);
                mPagerAdapter.updateFragmentData(page, category_id + "", sort);
            }
        });
        mRgSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e(Tag, "mRgSort ChangeListener");

                if (checkedId != R.id.supplyMore_rb_price) {
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.img_sort_none), null);

                }
                switch (checkedId) {
                    case R.id.supplyMore_rb_newest:
                        sort = 1;
                        mPagerAdapter.updateFragmentData(page, category_id + "", sort);

//                        setCurrentPage(4);
                        break;
                    case R.id.supplyMore_rb_nums:
                        sort = 2;
                        mPagerAdapter.updateFragmentData(page, category_id + "", sort);

//                        setCurrentPage(4);
                        break;
                    case R.id.supplyMore_rb_price:
                        if (isSortAsc) {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_top),null);
                            mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.img_sort_top), null);

                            sort = 3;
                            isSortAsc = !isSortAsc;
                        } else {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_foot),null);
                            mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.img_sort_foot), null);
                            sort = 4;
                            isSortAsc = !isSortAsc;

                        }
//                        mPagerAdapter.updateFragmentData(category_id,sort);

//                        setCurrentPage(4);
                        break;


                }
            }
        });
        mRbPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSortAsc) {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_top),null);
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.img_sort_top), null);

                    sort = 3;
                    isSortAsc = !isSortAsc;
                } else {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_foot),null);
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.img_sort_foot), null);
                    sort = 4;
                    isSortAsc = !isSortAsc;

                }
                mPagerAdapter.updateFragmentData(page, category_id + "", sort);
            }
        });
        for (int i = 0; i < mRgCategory.getChildCount(); i++) {
            if (mRgCategory.getChildAt(i).getId() == category_id) {

                ((RadioButton) (mRgCategory.getChildAt(i))).setChecked(true);
                setCurrentPage(i);
            }


        }
//        category_id = checkedId;
//                mPagerAdapter.updateFragmentData(category_id,1);
//        mPagerAdapter.updateFragmentData(page, category_id + "", sort);


    }

    /**
     * 价格是否升序  默认为是
     **/
    boolean isSortAsc = true;
    int page = 0;

    public void setCurrentPage(int page) {
        Log.d(Tag, "page" + page);
        this.page = page;
        mViewpager.setCurrentItem(page);

    }

    @OnClick(R.id.supplyMore_back)
    public void onClick() {
        onBackPressed();
    }



    public interface MySortChangeListener {
        public void onSortChange(String category_id, int sort);
    }
}
