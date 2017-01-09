package cn.hi028.android.highcommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
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
import cn.hi028.android.highcommunity.adapter.ShowLocListAdapter;
import cn.hi028.android.highcommunity.adapter.ShowSearchListAdapter;
import cn.hi028.android.highcommunity.bean.SimplePoiBean;
import cn.hi028.android.highcommunity.utils.BDHttpUtil;
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
    @Bind(R.id.tv_address_noaddress)
    TextView mShowNoAddress;
    @Bind(R.id.loc_list)
    ListView mPoiListView;
    ShowLocListAdapter mSLocListAdapter;
    List<PoiInfo> searchResultPoiList = new ArrayList<PoiInfo>();
    ShowSearchListAdapter mSearchAdapter;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.loc_list2)
    PullToRefreshListView mSearchResultListView;
    @Bind(R.id.search)
    EditText mSearchEd;
    @Bind(R.id.button2)
    Button mSearchBut;
    @Bind(R.id.searchkey)
    AutoCompleteTextView keyWorldsView;
    // 搜索周边相关
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;

    private List<String> suggestList;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 1;

    int searchType = 1;  // 搜索的类型，在显示时区分
    private double longitude = 104.164;// 精度
    private double latitude = 30.6;// 维度
    private PopupWindow mWatingWindow;
    // 定义地图引擎管理类
    private BMapManager mapManager;

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

    List<SimplePoiBean> mSimplePOiList = new ArrayList<SimplePoiBean>();
    List<Poi> list = new ArrayList<Poi>();
    BDLocation mlocation;

    private void initView() {
        mPoiListView.setVisibility(View.VISIBLE);
        mSLocListAdapter = new ShowLocListAdapter(mSimplePOiList, this);
        mPoiListView.setAdapter(mSLocListAdapter);
//关键字搜索相关
//        mSearchResultListView.setMode(PullToRefreshBase.Mode.BOTH);
//        mSearchResultListView.onRefreshComplete();
        mSearchResultListView.setVisibility(View.GONE);
        mSearchResultListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {


                Log.e(Tag, "刷新  top");


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e(Tag, "刷新  bottom loadIndex:"+loadIndex);
                Log.e(Tag, "刷新 loadIndex: "+loadIndex);

                searchType = 1;
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }

                String citystr = "成都";
                String keystr = keyWorldsView.getText().toString();
                mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowLocationListAct.this, mSearchBut, Gravity.CENTER);
                mPoiSearch.searchInCity((new PoiCitySearchOption()).city(citystr).keyword(keystr).pageNum(loadIndex));
                loadIndex++;
                Log.e(Tag, "刷新 loadIndex2   ~~~: "+loadIndex);

            }
        });
        dingwei();
//        BDLocation location = getIntent().getParcelableExtra("BDLocation");

        mPoiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(Tag, "点击" + position + "name:" + mSimplePOiList.get(position).getName());
//                mAddressListener.onAddressChange(list.get(position).getName());
                Intent mIntent = new Intent();
                mIntent.putExtra("address", mSimplePOiList.get(position).getName());
                setResult(8, mIntent);
                ShowLocationListAct.this.finish();

            }
        });
//        setUiForListView2();
        //搜索
        Log.e(Tag, "初始化搜索");
        sugAdapter = new ArrayAdapter<String>(this, R.layout.item_justoneline_string);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);
        keyWorldsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPoiListView.setVisibility(View.GONE);
                if (mSearchAdapter != null) {
                    mSearchAdapter.ClearData();
                }
                mSearchResultListView.setVisibility(View.VISIBLE);
            }
        });

/**
 * 当输入关键字变化时，动态更新建议列表
 */
        keyWorldsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                Log.e(Tag, "动态更新建议列表 afterTextChanged ");

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                Log.e(Tag, "动态更新建议列表 beforeTextChanged ");

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Log.e(Tag, "动态更新建议列表 onTextChanged ");
                loadIndex = 1;
                if (cs.length() <= 0) {
                    mSearchBut.setVisibility(View.GONE);
                    mSearchBut.setText("取消");
                    mPoiListView.setVisibility(View.VISIBLE);
                    mSearchResultListView.setVisibility(View.GONE);
                    return;
                }
                if (cs.length() > 0) {
                    mSearchBut.setVisibility(View.VISIBLE);
                    mSearchBut.setText("搜索");
                } else if (cs.length() == 0) {
                    mSearchBut.setVisibility(View.GONE);
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

        mSearchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                // TODO Auto-generated method stub
//                checkPosition = position;
//                adapter.setCheckposition(position);
//                adapter.notifyDataSetChanged();
//                PoiInfo ad = (PoiInfo) adapter.getItem(position);
                Log.e(Tag, "点击name:" + position+",,,"+searchResultPoiList.get(position-1).name.toString());
//              mAddressListener.onAddressChange(searchResultPoiList.get(position).name.toString());
                Intent mIntent = new Intent();
                mIntent.putExtra("address", searchResultPoiList.get(position-1).name.toString());
                setResult(8, mIntent);
                ShowLocationListAct.this.finish();

            }
        });

        //城市内搜索，已固定成都
        mSearchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = 1;
                loadIndex=1;
                Log.e(Tag, "城市内搜索 点击 loadIndex: "+loadIndex);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                mSearchResultListView.setVisibility(View.VISIBLE);


                String citystr = "成都";
                String keystr = keyWorldsView.getText().toString();
                mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(ShowLocationListAct.this, mSearchBut, Gravity.CENTER);
                mPoiSearch.searchInCity((new PoiCitySearchOption()).city(citystr).keyword(keystr).pageNum(0));

            }
        });
        mShowNoAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Tag, "点击" + "不显示地址");
//                mAddressListener.onAddressChange(list.get(position).getName());
                Intent mIntent = new Intent();
                mIntent.putExtra("address", "");
                setResult(8, mIntent);
                ShowLocationListAct.this.finish();
            }
        });
    }

    private void dingwei() {

        //定位init
        Log.e(Tag, "准备定位");
        mLocationClient = new LocationClient(this); //声明LocationClie
        Log.e(Tag, "准备定位2");
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
//        option.setScanSpan(5000);    //设置定时定位的时间间隔。单位毫秒
        option.disableCache(false);//禁止启用缓存定位
        option.setIsNeedLocationPoiList(true);
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location != null) {
                    mlocation = location;
                    if (mlocation != null) {
                        Log.e(Tag, "dingwei过来的对象 ： no null");
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        List<SimplePoiBean> SimplePOiList = BDHttpUtil.getSimplePOiList(longitude, latitude);
                        if (SimplePOiList != null && SimplePOiList.size() > 0) {
                            mSimplePOiList = SimplePOiList;
                            Log.e(Tag, "获取的poiList：no null" + SimplePOiList.size());
                            mSLocListAdapter.ClearData();
                            mSLocListAdapter.AddNewData(SimplePOiList);
                        } else {
                            Log.e(Tag, "获取的poiList： null");
                            list = mlocation.getPoiList();
                            List<SimplePoiBean> mSimplePOiList2 = new ArrayList<SimplePoiBean>();

                            for (int i = 0; i < list.size(); i++) {
                                SimplePoiBean mBean = new SimplePoiBean();
                                mBean.setName(list.get(i).getName());
                                mBean.setAddr("");
                                mSimplePOiList2.add(mBean);
                            }
                            mSimplePOiList = mSimplePOiList2;
                            Log.e(Tag, "获取的poiList： null 使用传递过来的数据" + mSimplePOiList2.size());
                            mSLocListAdapter.ClearData();
                            mSLocListAdapter.AddNewData(mSimplePOiList2);

                        }
                    } else {
                        Log.e(Tag, "传过来的对象 ： null");


                    }


                }
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
            }
        });//注册监听函数
        Log.e(Tag, "定位3");
        mLocationClient.start();

    }

    LocationClient mLocationClient;

    StringBuilder sb2, sb3;

    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param result
     */
    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        Log.e(Tag, "接受周边地理位置结果 onGetPoiResult ");

        sb2 = new StringBuilder();
        sb3 = new StringBuilder();
        mWatingWindow.dismiss();
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Log.e(Tag, "没有找到检索结果  ");
            if (mSearchResultListView!=null){
                mSearchResultListView.onRefreshComplete();
            }
            Toast.makeText(ShowLocationListAct.this, "未找到结果", Toast.LENGTH_LONG).show();

            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            Log.e(Tag, "检索结果正常返回  ");
//            Toast.makeText(ShowLocationListAct.this, "检索结果正常返回", Toast.LENGTH_LONG).show();
            Log.e(Tag, "检索结果正常返回  searchType:" + searchType);

            switch (searchType) {
                case 1:
                    searchForText(result);
                    break;
//                case 2:
//                    searchForNearby(result);
//                    break;

            }

            return;


        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            Log.e(Tag, "接受周边地理位置结果 error  当输入关键字在本市没有找到");

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
//            Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 指定搜索文字结果展示
     *
     * @param result
     */
    private void searchForText(PoiResult result) {
        Log.e(Tag, "searchForText result:" + result.toString());
        if (result != null) {
            mPoiListView.setVisibility(View.GONE);
            Log.e(Tag, "searchForText != null  :");
            mSearchResultListView.setVisibility(View.VISIBLE);
            if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                //添加StringBuffer 遍历当前页返回的POI (默认只返回10个)

                sb2.append("共搜索到").append(result.getAllPoi().size()).append("个POI\n");
                for (PoiInfo poiInfo : result.getAllPoi()) {
                    sb2.append("名称：").append(poiInfo.name).append("\n");
                    sb2.append("---地址：").append(poiInfo.address).append("\n");
                }
                Log.e(Tag, "搜索到的POI信息:" + sb2.toString());

                if (loadIndex == 1) {
                    searchResultPoiList.clear();
                }
                searchResultPoiList.addAll((List<PoiInfo>)(result.getAllPoi()));
                if (searchResultPoiList != null) {
                    Log.e(Tag, "搜索到的POI信息!= null  loadIndex:"+loadIndex);

                    Log.e(Tag, "搜索到的POI信息:填充数据");

//                    mPoiListView.setVisibility(View.GONE);
//                    mSearchResultListView.setVisibility(View.VISIBLE);
                    if (loadIndex == 1) {
                        Log.e(Tag, "搜索到的POI信息:填充数据  loadIndex == 0");
                        mSearchAdapter = new ShowSearchListAdapter(searchResultPoiList, this);
                        mSearchResultListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//                        mSearchAdapter.AddNewData(searchResultPoiList);

                        Log.e(Tag, "填充数据  长度："+searchResultPoiList.size());
                        mSearchResultListView.setAdapter(mSearchAdapter);
                        mSearchResultListView.onRefreshComplete();
//                        mSearchAdapter.ClearData();
                    } else {
                        Log.e(Tag, "搜索到的POI信息:填充数据  loadIndex " + loadIndex);
                        Log.e(Tag, "叠加 填充数据  长度："+searchResultPoiList.size());

//                        mSearchAdapter.AddNewData(result.getAllPoi());
                        mSearchAdapter.setData(searchResultPoiList);
                        mSearchResultListView.onRefreshComplete();
                    }

                }


            }
        }
        Log.e(Tag, "接受周边地理位置结果 sb " + sb2);

    }


    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        Log.e(Tag, "接受周边地理详情结果 onGetPoiDetailResult");


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
        Log.e(Tag, "获取在线建议搜索结果 onGetSuggestionResult");
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        suggestList = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggestList.add(info.key);
            }
        }
        sugAdapter = new ArrayAdapter<String>(this, R.layout.item_justoneline_string, suggestList);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
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
