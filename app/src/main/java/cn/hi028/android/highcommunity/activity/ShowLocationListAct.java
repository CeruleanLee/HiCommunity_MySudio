package cn.hi028.android.highcommunity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.ListAdapter;
import cn.hi028.android.highcommunity.adapter.ShowLocListAdapter;
import cn.hi028.android.highcommunity.adapter.ShowSearchListAdapter;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：展示定位列表Activity<br>
 * @作者： Lee_yting<br>
 * @版本：2.0<br>
 * @时间：2016/12/5<br>
 */
public class ShowLocationListAct extends BaseFragmentActivity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
    static final String Tag = "ShowLocationListAct:";
    @Bind(R.id.auto_sec_img_back)
    ImageView mImgBack;
    @Bind(R.id.auto_sec_tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_address_Nodata)
    TextView mNodata;
    @Bind(R.id.loc_list)
    ListView mListView;
    List<Poi> list = new ArrayList<Poi>();
    ShowLocListAdapter mAdapter;
    List<PoiInfo> searchResultPoiList = new ArrayList<PoiInfo>();
    List<PoiInfo> searchDetailResultPoiList = new ArrayList<PoiInfo>();
    ShowSearchListAdapter mDetailSearchAdapter;
    ShowSearchListAdapter mSearchAdapter;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.loc_list2)
    PullToRefreshListView mListView2;
    @Bind(R.id.search)
    EditText mSearchEd;
    @Bind(R.id.button2)
    Button mSearchBut;
    @Bind(R.id.searchkey)
    AutoCompleteTextView keyWorldsView;
    // 搜索周边相关
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private List<PoiInfo> dataList;
    private ListAdapter adapter;
    private List<String> suggest;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;

    LatLng center = new LatLng(39.92235, 116.380338);
    int radius = 100;
    LatLng southwest = new LatLng(39.92235, 116.380338);
    LatLng northeast = new LatLng(39.947246, 116.414977);
    int searchType = 0;  // 搜索的类型，在显示时区分
    private int locType;
    private double longitude = 104.164;// 精度
    private double latitude = 30.6;// 维度
    //    private float radius;// 定位精度半径，单位是米
    private String addrStr;// 反地理编码
    private String province;// 省份信息
    private String city;// 城市信息
    private String district;// 区县信息
    private float direction;// 手机方向信息
    private PopupWindow mWatingWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location_list);
        ButterKnife.bind(this);
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        mAddressListener = (MyAddressChangerListener) new LabelAct();
        initView();

    }

    private void initView() {
        mListView2.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        mListView.setVisibility(View.VISIBLE);
        mListView2.setVisibility(View.GONE);
        mAdapter = new ShowLocListAdapter(list, this);
//        mListView.setAdapter(mAdapter);
        mSearchAdapter = new ShowSearchListAdapter(searchResultPoiList, this);
        mListView2.setAdapter(mSearchAdapter);

        BDLocation location = getIntent().getParcelableExtra("BDLocation");
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();


            Log.e(Tag, "传过来的对象：" + location.toString());
            StringBuffer sb = new StringBuffer(256);
            list = location.getPoiList();// POI数据
            Log.e(Tag, "传过来的对象：1-id:" + list.get(0).getId());

            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(list.get(0).getId()));

            // 返回该 poi 详情检索参数对象
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                    mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
//                            .poiUid(p.getId()));
                }
                for (int k = 0; k < list.size(); k++) {
                    Log.e(Tag, "遍历：" + k);

                    mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                            .poiUid(list.get(k).getId()));
                }
                Log.d(Tag,"searchResultPoiList.size():"+searchResultPoiList.size());
                        mDetailSearchAdapter = new ShowSearchListAdapter(searchResultPoiList, this);
                mListView.setAdapter(mDetailSearchAdapter);

            }
            Log.i("BaiduLocationApiDem", sb.toString());
        } else {
            Log.e(Tag, "传过来的对象 ： null");
            list = new ArrayList<Poi>();
        }
//        mListView.setEmptyView(mNodata);
        mAdapter.AddNewData(list);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(Tag, "点击" + position + "name:" + list.get(position).getName());
//                mAddressListener.onAddressChange(list.get(position).getName());
                Intent mIntent = new Intent();
                mIntent.putExtra("address", list.get(position).getName());
                setResult(8, mIntent);
                ShowLocationListAct.this.finish();

            }
        });
//        setUiForListView2();
        //搜索
        Log.d(Tag, "初始化搜索");
        sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);

/**
 * 当输入关键字变化时，动态更新建议列表
 */
        keyWorldsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                Log.d(Tag, "动态更新建议列表 afterTextChanged ");

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                Log.d(Tag, "动态更新建议列表 beforeTextChanged ");

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Log.d(Tag, "动态更新建议列表 onTextChanged ");
                loadIndex=0;
                if (cs.length() <= 0) {
                    return;
                }
                if (cs.length() > 0) {
                    mSearchBut.setVisibility(View.VISIBLE);
                    mSearchBut.setText("搜索");
                } else if (cs.length() == 0) {
                    mSearchBut.setVisibility(View.VISIBLE);
                    mSearchBut.setText("取消");

                }
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city("成都"));
            }
        });
//        dataList = new ArrayList<PoiInfo>();
//        checkPosition = 0;
//        adapter = new ListAdapter(0, dataList, this);
//        mListView2.setAdapter(adapter);
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                // TODO Auto-generated method stub
//                checkPosition = position;
//                adapter.setCheckposition(position);
//                adapter.notifyDataSetChanged();
//                PoiInfo ad = (PoiInfo) adapter.getItem(position);
                Log.e(Tag, "点击name:" + searchResultPoiList.get(position).name.toString());
//              mAddressListener.onAddressChange(searchResultPoiList.get(position).name.toString());
                Intent mIntent = new Intent();
                mIntent.putExtra("address", searchResultPoiList.get(position).name.toString());
                setResult(8, mIntent);
                ShowLocationListAct.this.finish();

            }
        });
        mListView2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                searchType = 1;
                String citystr = "成都";
                String keystr = keyWorldsView.getText().toString();

                mPoiSearch.searchInCity((new PoiCitySearchOption()).city(citystr).keyword(keystr).pageNum(loadIndex));
                loadIndex++;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                return;

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//纬度:30.611042,经度:104.164013
                Log.d(Tag, "附近搜索 点击 ");
                Log.d(Tag, "附近搜索 latitude: " + latitude);
                Log.d(Tag, "附近搜索 longitude: " + longitude);
                searchType = 2;
//                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyWorldsView.getText()
//                        .toString()).sortType(PoiSortType.distance_from_near_to_far).location(center)
//                        .radius(radius).pageNum(loadIndex);
//                mPoiSearch.searchNearby(nearbySearchOption);
                PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
                poiNearbySearchOption.keyword("电子科技大学");
                poiNearbySearchOption.keyword("四川大学");
                poiNearbySearchOption.location(new LatLng(latitude, longitude));
                poiNearbySearchOption.radius(1000);  // 检索半径，单位是米
                poiNearbySearchOption.pageCapacity(15);  // 默认每页10条
                mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowLocationListAct.this, mSearchBut, Gravity.CENTER);
                mWatingWindow.setOutsideTouchable(true);
                mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
//                initLocation();

            }
        });
        //城市内搜索，已固定成都
        mSearchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Tag, "城市内搜索 点击 ");

                searchType = 1;
                String citystr = "成都";
                String keystr = keyWorldsView.getText().toString();
                mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowLocationListAct.this, mSearchBut, Gravity.CENTER);
                mPoiSearch.searchInCity((new PoiCitySearchOption()).city(citystr).keyword(keystr).pageNum(loadIndex));
                loadIndex++;
            }
        });

    }


    private int checkPosition;
    LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();

    /**
     * 定位
     */
    private void initLocation() {
        //重新设置
        checkPosition = 0;
        adapter.setCheckposition(0);
        // 定位初始化
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener);// 注册定位监听接口

        /**
         * 设置定位参数
         */
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
//		option.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
        option.setOpenGps(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        mLocationClient.setLocOption(option);
//        mLocationClient.start(); // 调用此方法开始定位
    }


    /**
     * 定位SDK监听函数
     *
     * @author
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
//            Log.d(Tag, "城市内搜索 onReceiveLocation ");
//
//            if (location == null) {
//                return;
//            }
//
//            locType = location.getLocType();
//            Log.i("mybaidumap", "当前定位的返回值是：" + locType);
//
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//            if (location.hasRadius()) {// 判断是否有定位精度半径
//                radius = location.getRadius();
//            }
//            if (locType == BDLocation.TypeNetWorkLocation) {
//                addrStr = location.getAddrStr();// 获取反地理编码(文字描述的地址)
//                Log.i("mybaidumap", "当前定位的地址是：" + addrStr);
//            }
//            direction = location.getDirection();// 获取手机方向，【0~360°】,手机上面正面朝北为0°
//            province = location.getProvince();// 省份
//            city = location.getCity();// 城市
//            district = location.getDistrict();// 区县
//            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            //将当前位置加入List里面
//            PoiInfo info = new PoiInfo();
//            info.address = location.getAddrStr();
//            info.city = location.getCity();
////            info.location = ll;
//            info.name = location.getAddrStr();
//            dataList.add(info);
//            adapter.notifyDataSetChanged();
//            Log.i("mybaidumap", "province是：" + province + " city是" + city + " 区县是: " + district);
//            //poi 搜索周边
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    Looper.prepare();
////                    searchNeayBy();
//                    Looper.loop();
//                }
//            }).start();
        }
    }

    StringBuilder sb2, sb3;

    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param result
     */
    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        Log.d(Tag, "接受周边地理位置结果 onGetPoiResult ");

        sb2 = new StringBuilder();
        sb3 = new StringBuilder();
        mWatingWindow.dismiss();
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Log.d(Tag, "没有找到检索结果  ");

            Toast.makeText(ShowLocationListAct.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            Log.d(Tag, "检索结果正常返回  ");
            Toast.makeText(ShowLocationListAct.this, "检索结果正常返回", Toast.LENGTH_LONG).show();

            switch (searchType) {
                case 2:
                    searchForNearby(result);
                    break;
                case 3:
//                    showBound(searchbound);
                    break;
                case 1:
                    searchForText(result);
                    break;
            }

            return;


        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            Log.d(Tag, "接受周边地理位置结果 error  当输入关键字在本市没有找到");

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 附近搜索结果展示
     *
     * @param result
     */
    private void searchForNearby(PoiResult result) {
        if (result != null) {
            if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                //添加StringBuffer 遍历当前页返回的POI (默认只返回10个)

                sb3.append("共搜索到").append(result.getAllPoi().size()).append("个POI\n");
                for (PoiInfo poiInfo : result.getAllPoi()) {
                    sb3.append("名称：").append(poiInfo.name).append("\n");
                    sb3.append("---地址：").append(poiInfo.address).append("\n");
                }
                Log.d(Tag, "搜索到的POI信息:" + sb3.toString());
                searchResultPoiList.clear();
                searchResultPoiList = result.getAllPoi();
                if (searchResultPoiList != null) {
                    mListView.setVisibility(View.GONE);
                    mListView2.setVisibility(View.VISIBLE);
//                  mSearchAdapter.ClearData();
//                    mSearchAdapter.AddNewData(searchResultPoiList);
                }
//                 通过AlertDialog显示当前页搜索到的POI
                new AlertDialog.Builder(this)
                        .setTitle("搜索到的POI信息")
                        .setMessage(sb3.toString())
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).create().show();

/*****                                                      *****/
//                    dataList.addAll(result.getAllPoi());
//                    Log.d(Tag, "检索结果正常返回  dataList:" + dataList.size() + "," + dataList.toString());
//                    dataList.toString();
//                    adapter.AddNewData(result.getAllPoi());
//                    Message msg = new Message();
//                    msg.what = 0;
//                    handler.sendMessage(msg);
            }
        }
        Log.d(Tag, "接受周边地理位置结果 sb " + sb2);


    }

    /***
     * 指定搜索文字结果展示
     *
     * @param result
     */
    private void searchForText(PoiResult result) {
        if (result != null) {
            if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                //添加StringBuffer 遍历当前页返回的POI (默认只返回10个)

                sb2.append("共搜索到").append(result.getAllPoi().size()).append("个POI\n");
                for (PoiInfo poiInfo : result.getAllPoi()) {
                    sb2.append("名称：").append(poiInfo.name).append("\n");
                    sb2.append("---地址：").append(poiInfo.address).append("\n");
                }
                Log.d(Tag, "搜索到的POI信息:" + sb2.toString());

                if (loadIndex == 0) {
                    searchResultPoiList.clear();
                }
                searchResultPoiList = result.getAllPoi();
                if (searchResultPoiList != null) {
                    mListView.setVisibility(View.GONE);
                    mListView2.setVisibility(View.VISIBLE);
                    if (loadIndex == 0) {
                        mSearchAdapter.ClearData();
                    }
                    mSearchAdapter.AddNewData(searchResultPoiList);
                }
                // 通过AlertDialog显示当前页搜索到的POI
//                    new AlertDialog.Builder(this)
//                            .setTitle("搜索到的POI信息")
//                            .setMessage(sb2.toString())
//                            .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                }
//                            }).create().show();

/*****                                                      *****/
//                    dataList.addAll(result.getAllPoi());
//                    Log.d(Tag, "检索结果正常返回  dataList:" + dataList.size() + "," + dataList.toString());
//                    dataList.toString();
//                    adapter.AddNewData(result.getAllPoi());
//                    Message msg = new Message();
//                    msg.what = 0;
//                    handler.sendMessage(msg);
            }
        }
        Log.d(Tag, "接受周边地理位置结果 sb " + sb2);

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        Log.d(Tag, "接受周边地理详情结果 onGetPoiDetailResult");

        Toast.makeText(ShowLocationListAct.this, "onGetPoiDetailResult", Toast.LENGTH_LONG).show();
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(Tag, "result:"+result.getName() + ": " + result.getAddress());
            PoiInfo poiInfo=new PoiInfo();
            poiInfo.address=result.getAddress();
            poiInfo.name=result.getName();
            Log.d(Tag, "poiInfo:"+poiInfo.name + ": " + poiInfo.address);
            searchDetailResultPoiList.add(poiInfo);
//            result.get
        }

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        Toast.makeText(ShowLocationListAct.this, "onGetPoiIndoorResult", Toast.LENGTH_LONG).show();

    }


    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param res
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        Log.d(Tag, "获取在线建议搜索结果 onGetSuggestionResult");
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        suggest = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
        sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, suggest);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
    }


    /**
     * 用于显示poi的overly
     *
     * @author Administrator
     */
    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption()


                    .poiUid(poiInfo.uid));
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (mLocationClient != null) {
            mLocationClient.stop();
        }

        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

    }

    @OnClick(R.id.auto_sec_img_back)
    public void onClick() {
        this.finish();
    }

    MyAddressChangerListener mAddressListener;

    public interface MyAddressChangerListener {
        void onAddressChange(String address);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mWatingWindow != null && mWatingWindow.isShowing()) {
                mWatingWindow.dismiss();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
