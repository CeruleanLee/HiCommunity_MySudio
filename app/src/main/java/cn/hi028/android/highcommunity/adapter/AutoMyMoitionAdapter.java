package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.bean.Autonomous.CommonHttpResultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 提案adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoMyMoitionAdapter extends BaseFragmentAdapter {
    BpiHttpHandler.IBpiHttpHandler mIbpi, mDeleteIbpi;
    public static final String TAG = "~~~MoitionAdapter-";
    public static final int TAG_MOTION_DETAIL = 2;
    List<Auto_MotionBean.MotionDataEntity> mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    View view;
    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;
    // 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
    private int mScreentWidth;
    private View view2,view3;
    private ListView listView;
    private HttpUtils mHttpUtils,mSupportHttpUtils;
View clickView;
    public AutoMyMoitionAdapter(List<Auto_MotionBean.MotionDataEntity> list, Context context, View view, int screenWidth, ListView listView) {
        super();
        Log.e(TAG, "---构造---");
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.view = view;
        this.listView = listView;
        mScreentWidth = screenWidth;
        mHttpUtils = new HttpUtils();
        mHttpUtils.configCurrentHttpCacheExpiry(0);
        mHttpUtils.configSoTimeout(4000);
        mHttpUtils.configTimeout(4000);
        mSupportHttpUtils = new HttpUtils();
        mSupportHttpUtils.configCurrentHttpCacheExpiry(0);
        mSupportHttpUtils.configSoTimeout(4000);
        mSupportHttpUtils.configTimeout(4000);
        Log.e(TAG, "屏幕宽度--->" + mScreentWidth);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_MotionBean.MotionDataEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    ViewHolder mViewHolder = null;
    private boolean isClose = true;
int clickPosition=-1;
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_automotion_my, null);
            mViewHolder.mHSView = (HorizontalScrollView) convertView.findViewById(R.id.item_aotumotion_HScrollView);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_aotumotion_title);
            mViewHolder.mContentLayout = convertView.findViewById(R.id.item_aotumotion_contentLayout);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.item_aotumotion_time);
            mViewHolder.mTv_Support = (TextView) convertView.findViewById(R.id.item_aotumotion_tv_support);
            mViewHolder.mBut_Support = (CheckedTextView) convertView.findViewById(R.id.item_aotumotion_but_support);
            mViewHolder.action = convertView.findViewById(R.id.ll_action);
            mViewHolder.but_delete = (Button) convertView.findViewById(R.id.item_aotumotion_but_delete);

            // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
            LayoutParams lp = mViewHolder.mContentLayout.getLayoutParams();
            lp.width = mScreentWidth;

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        view3=convertView;
        final Auto_MotionBean.MotionDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getCreate_time())));
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());
        mViewHolder.mTv_Support.setText("支持率：" + mBean.getVote_percent() + "%");
        if (mBean.getIsSuggest().equals("1")) {
            mViewHolder.mBut_Support.setChecked(true);
            mViewHolder.mBut_Support.setText(" 已支持 ");

        } else  {
            mViewHolder.mBut_Support.setChecked(false);
            mViewHolder.mBut_Support.setText(" 支持 ");
        }
        //if (mBean.getIsSuggest().equals("1"))
        //位置放到view里   方便知道点击的是那一条item
        mViewHolder.but_delete.setTag(position);
        mViewHolder.mBut_Support.setTag(position);
        mViewHolder.mTv_Support.setTag(position);
        Log.e(TAG,"支持按钮的tag--->"+mViewHolder.mBut_Support.getTag()+"支支持文字的tag--->"+mViewHolder.mTv_Support.getTag());

        //监听支持
        mViewHolder.mBut_Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView=v;
                clickPosition=position;
                Log.e(TAG,"监听 支持按钮的tag--->"+mViewHolder.mBut_Support.getTag()+"监听 支支持文字的tag--->"+mViewHolder.mTv_Support.getTag());

                Log.e(TAG,"position--->"+position);
                Log.e(TAG,"clickPosition--->"+clickPosition);
                Log.e(TAG,"v.getTag()--->"+v.getTag());
                HTTPHelper.SupportMotion(mIbpi, mBean.getId());
//                Log.e(TAG,"监听里的 支持按钮  被点击的按钮--->"+mViewHolder.mBut_Support.getTag()+"传过去的参数  id--->"+mBean.getId());
////                String percentPer=supportHttp(mBean.getId());
////                HTTPHelper.SupportMotion(mIbpi, mBean.getId());
//                ViewHolder mVh= (ViewHolder) clickView.getTag();
//                mVh.mBut_Support.setChecked(true);
//                mVh.mBut_Support.setText(" 6支持 ");
////
//                Log.e(TAG,"监听里的   支持按钮的tag--->"+mViewHolder.mBut_Support.getTag()+"监听里的  支支持文字的tag--->"+mViewHolder.mTv_Support.getTag());
//
//                Log.e(TAG,"保存下来的  支持按钮的tag--->"+mVh.mBut_Support.getTag()+"保存下拉的   支支持文字的tag--->"+mVh.mTv_Support.getTag());
//                if (percentPer!=null&&percentPer!=""){
//                    mVh.mTv_Support.setText("支持率："+percentPer+"%");
//                }else{
//                    Log.e(TAG,"mResultData null");
//                }

            }
        });



        mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
            @Override
            public void onError(int id, String message) {
                Log.e(TAG, "onError");
                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

            }

            @Override
            public void onSuccess(Object message) {
                Log.e(TAG, "onSuccess");
                if (message == null) return;

                mResultData = (Auto_SupportedResultBean.SupportedResultDataEntity) message;
                Toast.makeText(context, "已支持", Toast.LENGTH_SHORT).show();
                mList.get(clickPosition).setVote_percent(mResultData.getVote_percent() + "");
                mList.get(clickPosition).setIsSuggest("1");
//                AddNewData(mList);
                notifyDataSetChanged();

            }

            @Override
            public Object onResolve(String result) {

                return HTTPHelper.ResolveSupportedResultData(result);
            }

            @Override
            public void setAsyncTask(AsyncTask asyncTask) {

            }

            @Override
            public void cancleAsyncTask() {

            }
        };
        mViewHolder.mContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent_report = new Intent(context, AutonomousAct_Third.class);
                mIntent_report.putExtra("title", TAG_MOTION_DETAIL);
                mIntent_report.putExtra("motion_id", mBean.getId());
                context.startActivity(mIntent_report);
            }
        });


        convertView.setOnTouchListener(new View.OnTouchListener() {
            private int x, y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (view2 != null) {

                            ViewHolder privateViewHolder = (ViewHolder) view2.getTag();
                            privateViewHolder.mHSView.smoothScrollBy(0, 0);
                        }
                        x = (int) event.getX();
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x3 = (int) event.getX();
                        int Y3 = (int) event.getY();
                        int dY = Math.abs(y - Y3);
                        int dX = Math.abs(x - x3);
                        if (dX > dY && dX > 20) {
//          v.requestdis
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        ViewHolder viewHolder2 = (ViewHolder) v.getTag();
                        view2 = v;
                        // 获得HorizontalScrollView滑动的水平方向值.
                        int scrollX = viewHolder2.mHSView.getScrollX();
                        // 获得操作区域的长度
                        int actionW = viewHolder2.action.getWidth();
                        // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                        // 如果水平方向的移动值<操作区域的长度的一半,就复原
                        if (isClose) {
                            if (scrollX < (actionW / 5)) {
                                isClose = true;
                                viewHolder2.mHSView.smoothScrollTo(0, 0);
                            } else// 否则的话显示操作区域
                            {
                                isClose = false;
                                viewHolder2.mHSView.smoothScrollTo(actionW, 0);
                            }

                        } else {

                            if (scrollX < (actionW * 4.0 / 5.0)) {
                                isClose = true;
                                viewHolder2.mHSView.smoothScrollTo(0, 0);
                            } else// 否则的话显示操作区域
                            {
                                isClose = false;
                                viewHolder2.mHSView.smoothScrollTo(actionW, 0);
                            }
                        }
                        return true;
                }
                return false;
            }
        });
        // 这里防止删除一条item后,ListView处于操作状态,直接还原
        if (mViewHolder.mHSView.getScrollX() != 0) {
            mViewHolder.mHSView.scrollTo(0, 0);
        }

        // 设置背景颜色,设置填充内容.
//        mViewHolder.content.setBackgroundResource(colors.get(position));
//        mViewHolder.tvContent.setText("" + R.color.blue);

        // 设置监听事件
        mViewHolder.but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteHttp(mBean.getId());
                mList.remove(position);

//
                // 刷新ListView内容
                notifyDataSetChanged();
            }
        });

//        mDeleteIbpi = new BpiHttpHandler.IBpiHttpHandler() {
//            @Override
//            public void onError(int id, String message) {
//                Log.e(TAG, "onError");
//                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
//
//            }
//
//            @Override
//            public void onSuccess(Object message) {
//                Log.e(TAG, "onSuccess");
//                if (message == null) return;
//
//                mResultData = (Auto_SupportedResultBean.SupportedResultDataEntity) message;
//                Toast.makeText(context, "已支持", Toast.LENGTH_SHORT).show();
//                mBean.setVote_percent(mResultData.getVote_percent() + "");
//                mBean.setIsSuggest("1");
////                AddNewData(mList);
//                notifyDataSetChanged();
//
//            }
//
//            @Override
//            public Object onResolve(String result) {
//
//                return HTTPHelper.ResolveSupportedResultData(result);
//            }
//
//            @Override
//            public void setAsyncTask(AsyncTask asyncTask) {
//
//            }
//
//            @Override
//            public void cancleAsyncTask() {
//
//            }
//        };


        return convertView;
    }


    private PopupWindow mWatingWindow;

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_MotionBean.MotionDataEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {

        HorizontalScrollView mHSView;
        View mContentLayout;
        TextView mTitle;
        TextView mTime;
        TextView mTv_Support;
        CheckedTextView mBut_Support;
        Button but_delete;
        View action;
    }

    private void deleteHttp(String id) {

        String url = "http://028hi.cn/api/ysuggest/delete.html";
        RequestParams params = new RequestParams();
        params.addBodyParameter("token", HighCommunityApplication.mUserInfo.getToken());
        params.addBodyParameter("id", id);
        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.e(TAG, "http 访问失败的 arg1--->" + arg1.toString());
//            HighCommunityUtils.GetInstantiation().ShowToast(arg1.toString(), 0);
                Toast.makeText(context, arg1.toString(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                String content = arg0.result;
                Log.e(TAG, "http 访问success的 content--->" + content);
                CommonHttpResultBean mInitBean = new Gson().fromJson(content, CommonHttpResultBean.class);
                //                ResponseGoodsItem responseGoodsItem = new Gson().fromJson(content, ResponseGoodsItem.class);
                if (mInitBean != null) {
                    Toast.makeText(context, mInitBean.getMsg(), Toast.LENGTH_SHORT).show();
//                HighCommunityUtils.GetInstantiation().ShowToast(mInitBean.getMsg(), 0);
                }
            }
        });
    }


    public String supportHttp(String id) {
        final String[] mPercentStr = {""};
        final int[] num = new int[1];
        String url = "http://028hi.cn/api/ywatch/suggest.html";
        RequestParams params = new RequestParams();
//        params.addBodyParameter("uid", HighCommunityApplication.mUserInfo.getId()+"");
//        params.addBodyParameter("id", id);
        params.addBodyParameter("uid",122+"");
        params.addBodyParameter("id", 33+"");
        Log.e(TAG,"id--->"+id+"uid--->"+HighCommunityApplication.mUserInfo.getId());
        mSupportHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.e(TAG, "http 访问失败的 arg1--->" + arg1.toString());
                Toast.makeText(context, arg1.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                String content = arg0.result;
                Log.e(TAG, "http 访问success的 content--->" + content);
                Auto_SupportedResultBean mInitBean = new Gson().fromJson(content, Auto_SupportedResultBean.class);
                //                ResponseGoodsItem responseGoodsItem = new Gson().fromJson(content, ResponseGoodsItem.class);
                if (mInitBean != null) {
                    Toast.makeText(context, mInitBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Auto_SupportedResultBean.SupportedResultDataEntity mSupportedData=mInitBean.getData();
                    if (mSupportedData!=null){
                        mPercentStr[0] = mSupportedData.getVote_percent()+"";
//                        num[0] =mSupportedData.getVote_percent();
                    }

                }
//                mResultData = (Auto_SupportedResultBean.SupportedResultDataEntity) message;
//                Toast.makeText(context, "已支持", Toast.LENGTH_SHORT).show();
//                mList.get(position).setVote_percent(mResultData.getVote_percent() + "");
//                mList.get(position).setIsSuggest("1");
////                AddNewData(mList);
//                notifyDataSetChanged();
            }
        });
        return mPercentStr[0];
    }

    private  void support(String id){
        final String[] str = {""};
//        BpiHttpHandler.IBpiHttpHandler mIbpi=new BpiHttpHandler().IBpiHttpHandler(){
//
//        }
        mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
            @Override
            public void onError(int id, String message) {
                Log.e(TAG, "onError");
                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

            }

            @Override
            public void onSuccess(Object message) {
                Log.e(TAG, "onSuccess");
                if (message == null) return;

                mResultData = (Auto_SupportedResultBean.SupportedResultDataEntity) message;
                Toast.makeText(context, "已支持", Toast.LENGTH_SHORT).show();
//str[0] =mResultData.getVote_percent()+"";
//                if (mInitBean != null) {
//                    Toast.makeText(context, mInitBean.getMsg(), Toast.LENGTH_SHORT).show();
//                    Auto_SupportedResultBean.SupportedResultDataEntity mSupportedData=mInitBean.getData();
//                    if (mSupportedData!=null){
//                        mPercentStr[0] = mSupportedData.getVote_percent()+"";
////                        num[0] =mSupportedData.getVote_percent();
//                    }
//
//                }
//                mBean.setVote_percent(mResultData.getVote_percent() + "");
//                mBean.setIsSuggest("1");
////                AddNewData(mList);
//                notifyDataSetChanged();

            }

            @Override
            public Object onResolve(String result) {

                return HTTPHelper.ResolveSupportedResultData(result);
            }

            @Override
            public void setAsyncTask(AsyncTask asyncTask) {

            }

            @Override
            public void cancleAsyncTask() {

            }
        };
        HTTPHelper.SupportMotion(mIbpi, id);


//return str[0];
    }
}
