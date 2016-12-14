package cn.hi028.android.highcommunity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
public class NewSupplyMoreAct extends BaseFragmentActivity {
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
    @Bind(R.id.supplymore_viewpager)
    ViewPager mViewpager;
    List<RadioButton> mRadioButList = new ArrayList<RadioButton>();
    List<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreCategoryEntity> mDataCategory = new ArrayList<SupplyGoodsMoreBean.SupplyGoodsMoreDataEntity.SupplyMoreCategoryEntity>();

    MySortChangeListener mySortChangeListener;

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
            finish();
        }
        mySortChangeListener= (MySortChangeListener) this;
//        initView();
        initDatas();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initDatas() {
        HTTPHelper.GetSupplyGoodsListMore(mIbpi, 0 + "", 1 + "");
    }


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
            Log.e(Tag, "mDataCategory.size(): " + mDataCategory.size());
            for (int i = 0; i < mDataCategory.size(); i++) {
                RadioButton newRadioBut = (RadioButton) LayoutInflater.from(NewSupplyMoreAct.this).inflate(R.layout.radiobut_newsupplymore, null);
                newRadioBut.setId(i);
                newRadioBut.setWidth(0);
                newRadioBut.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.MATCH_PARENT, 1f));
                newRadioBut.setText(mDataCategory.get(i).getName());
                mRgCategory.addView(newRadioBut);
            }
            Log.e(Tag, "mRgCategory.size(): " + mRgCategory.getChildCount());
            ((RadioButton) (mRgCategory.getChildAt(0))).setChecked(true);
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

        mPagerAdapter = new SupplyMorePagerAdapter(getSupportFragmentManager(),mDataCategory.size());
        mViewpager.setAdapter(mPagerAdapter);

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int k = 0; k < mRgCategory.getChildCount(); k++) {
                    if (i == k) {
                        ((RadioButton) (mRgCategory.getChildAt(i))).setChecked(true);
                    } else {
                        ((RadioButton) (mRgCategory.getChildAt(k))).setChecked(false);

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
//                for (int i = 0; i < mDataCategory.size(); i++) {
//                    if (checkedId==i){
//                        mRgCategory.getChildAt(i);
//                    }
//                }
                setCurrentPage(checkedId);
                category_id=checkedId+"";
                mySortChangeListener.onSortChange(category_id,1);
//                switch (checkedId) {
//                    case R.id.supplyMore_rb_cg0:
//                        setCurrentPage(0);
//                        break;
//                    case R.id.supplyMore_rb_cg1:
//                        setCurrentPage(1);
//                        break;
//                    case R.id.supplyMore_rb_cg2:
//                        setCurrentPage(2);
//                        break;
//                    case R.id.supplyMore_rb_cg3:
//                        setCurrentPage(3);
//                        break;
//                    case R.id.supplyMore_rb_cg4:
//                        setCurrentPage(4);
//                        break;
//
//                }
            }
        });
        mRgSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != R.id.supplyMore_rb_price) {
                    mRbPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.img_sort_none), null);

                }
                switch (checkedId) {
                    case R.id.supplyMore_rb_newest:
                        sort = 1;
                        mySortChangeListener.onSortChange(category_id,sort);

//                        setCurrentPage(4);
                        break;
                    case R.id.supplyMore_rb_nums:
                        sort = 2;
                        mySortChangeListener.onSortChange(category_id,sort);

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
                        mySortChangeListener.onSortChange(category_id,sort);

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
            }
        });

Log.e(Tag,"Integer.parseInt(category_id):  "+Integer.parseInt(category_id));

        ((RadioButton) (mRgCategory.getChildAt(Integer.parseInt(category_id)-1))).setChecked(true);

    }

    /**
     * 价格是否升序  默认为是
     **/
    boolean isSortAsc = true;

    public void setCurrentPage(int page) {
        Log.d(Tag, "page" + page);
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



    public interface MySortChangeListener {
        public void onSortChange(String category_id,int sort);
    }

}
