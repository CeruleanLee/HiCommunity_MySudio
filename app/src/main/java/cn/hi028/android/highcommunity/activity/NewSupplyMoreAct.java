package cn.hi028.android.highcommunity.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.SupplyMorePagerAdapter;

/**
 * @功能： 直供商品 更多>>
 * @作者： Lee_yting<br>
 * @版本：新版本 2.0<br>
 * @时间：2016/11/30<br>
 */
public class NewSupplyMoreAct extends BaseFragmentActivity {
    public static final String Tag = "NewSupplyMoreAct--->";
    SupplyMorePagerAdapter mPagerAdapter;
    @Bind(R.id.supplyMore_back)
    ImageView mBack;
    @Bind(R.id.supplyMore_rb_cg0)
    RadioButton mRbCg0;
    @Bind(R.id.supplyMore_rb_cg1)
    RadioButton mRbCg1;
    @Bind(R.id.supplyMore_rb_cg2)
    RadioButton mRbCg2;
    @Bind(R.id.supplyMore_rb_cg3)
    RadioButton mRbCg3;
    @Bind(R.id.supplyMore_rb_cg4)
    RadioButton mRbCg4;
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
    @Bind(R.id.supplymore_viewpager)
    ViewPager mViewpager;
    RadioButton[] mRButList;
    String category_id;
    /**
     * 1=>最新,2=>销量,3=>价格升序,4=>价格降序  每次进入默认最新
     **/
    int sort = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsupplymore);
        ButterKnife.bind(this);
        category_id = getIntent().getStringExtra("category_id");
        if (category_id == null || category_id == "") {
            Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
        }
        initView();
        initData();

    }

    private void initData() {



    }

    private void initView() {


        mRButList = new RadioButton[]{mRbCg0, mRbCg1, mRbCg2, mRbCg3, mRbCg4};
        mPagerAdapter = new SupplyMorePagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mPagerAdapter);

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int k = 0; k < mRButList.length; k++) {
                    if (i == k) {
                        mRButList[k].setChecked(true);
                    } else {
                        mRButList[k].setChecked(false);

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mRgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.supplyMore_rb_cg0:

                        setCurrentPage(0);
                        break;
                    case R.id.supplyMore_rb_cg1:
                        setCurrentPage(1);
                        break;
                    case R.id.supplyMore_rb_cg2:
                        setCurrentPage(2);
                        break;
                    case R.id.supplyMore_rb_cg3:
                        setCurrentPage(3);
                        break;
                    case R.id.supplyMore_rb_cg4:
                        setCurrentPage(4);
                        break;

                }
            }
        });
        mRgSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId!=R.id.supplyMore_rb_price){
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.img_sort_none),null);

                }
                switch (checkedId) {
                    case R.id.supplyMore_rb_newest:
                        sort = 1;
//                        setCurrentPage(4);
                        break;
                    case R.id.supplyMore_rb_nums:
                        sort = 2;
//                        setCurrentPage(4);
                        break;
                    case R.id.supplyMore_rb_price:
                        if (isSortAsc) {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_top),null);
                            mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.img_sort_top),null);

                            sort = 3;
                            isSortAsc = !isSortAsc;
                        } else {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_foot),null);
                            mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.img_sort_foot),null);
                            sort = 4;
                            isSortAsc = !isSortAsc;

                        }
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
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.img_sort_top),null);

                    sort = 3;
                    isSortAsc = !isSortAsc;
                } else {
//                            mRbPrice.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.img_sort_foot),null);
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.img_sort_foot),null);
                    sort = 4;
                    isSortAsc = !isSortAsc;

                }
            }
        });

    }

    /**
     * 价格是否升序  默认为是
     **/
    boolean isSortAsc = true;

    public  void setCurrentPage(int page){
        Log.d(Tag,"page"+page);
                    mViewpager.setCurrentItem(page);

//        if (page==0){
//            mViewpager.setCurrentItem(0);
//        }else{
//            mViewpager.setCurrentItem(0);
//        }
    }

    @OnClick(R.id.supplyMore_back)
    public void onClick() {
        onBackPressed();
    }
}
