package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
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
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;
import cn.hi028.android.highcommunity.bean.Autonomous.CommonHttpResultBean;
import cn.hi028.android.highcommunity.bean.UrlsBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * @功能：认证失败adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/28<br>
 */
public class CerFailedAdapter extends BaseFragmentAdapter {

    final String Tag = "-CerSuccesstAdapter->";
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    BitmapUtils mBitmapUtils;

    // 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
    private int mScreentWidth;
    private View view2, view3;
    private ListView listView;
    private HttpUtils mHttpUtils, mSupportHttpUtils;
    View clickView;
    private boolean isClose = true;
    int clickPosition = -1;

    public CerFailedAdapter(List<Auto_CertificationInitBean.CertificationInitDataEntity> list, Context context, int screenWidth) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        mBitmapUtils = MBitmapHolder.getBitmapUtils(context);
        mScreentWidth = screenWidth;
        mHttpUtils = new HttpUtils();
        mHttpUtils.configCurrentHttpCacheExpiry(0);
        mHttpUtils.configSoTimeout(4000);
        mHttpUtils.configTimeout(4000);
        mSupportHttpUtils = new HttpUtils();
        mSupportHttpUtils.configCurrentHttpCacheExpiry(0);
        mSupportHttpUtils.configSoTimeout(4000);
        mSupportHttpUtils.configTimeout(4000);
        Log.e(Tag, "屏幕宽度--->" + mScreentWidth);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_CertificationInitBean.CertificationInitDataEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_cer_failed, null);
            mViewHolder.mHSView = (HorizontalScrollView) convertView.findViewById(R.id.item_aotumotion_HScrollView);
            mViewHolder.mContentLayout = convertView.findViewById(R.id.item_aotumotion_contentLayout);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.cer_name);
            mViewHolder.mTel = (TextView) convertView.findViewById(R.id.cer_tel);
            mViewHolder.mAdress = (TextView) convertView.findViewById(R.id.tv_cer_adress);
            mViewHolder.mReason = (TextView) convertView.findViewById(R.id.cer_reason);
            mViewHolder.mImgCerIdZ = (ImageView) convertView.findViewById(R.id.img_cer_idZ);
            mViewHolder.mImgCerIdF = (ImageView) convertView.findViewById(R.id.img_cer_idF);
            mViewHolder.mImgCerPropertye = (ImageView) convertView.findViewById(R.id.img_cer_propertye);
            mViewHolder.mImgTag = (ImageView) convertView.findViewById(R.id.img_cer_Tag);
            mViewHolder.action = convertView.findViewById(R.id.ll_action);
            mViewHolder.but_delete = (Button) convertView.findViewById(R.id.item_aotumotion_but_delete);
            // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
            ViewGroup.LayoutParams lp = mViewHolder.mContentLayout.getLayoutParams();
            lp.width = mScreentWidth;
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_CertificationInitBean.CertificationInitDataEntity mBean = mList.get(position);
        mViewHolder.mName.setText("业主姓名：" + mBean.getName());
        mViewHolder.mTel.setText("联系方式：" + mBean.getTel());
        mViewHolder.mAdress.setText(mBean.getAddress());
        mBitmapUtils.display(mViewHolder.mImgCerIdZ, Constacts.IMAGEHTTP + mBean.getIDCard());
        mBitmapUtils.display(mViewHolder.mImgCerIdF, Constacts.IMAGEHTTP + mBean.getIDCard_F());
        mBitmapUtils.display(mViewHolder.mImgCerPropertye, Constacts.IMAGEHTTP + mBean.getHouse_certificate());
        if (mBean.getStatus().equals("1")) {
            //已认证
//            mViewHolder.mImgTag.setImageDrawable(context.getResources().getDrawable());
            mViewHolder.mImgTag.setImageResource(R.mipmap.img_cersuccess);
//            mBitmapUtils.display(mViewHolder.mImgTag, "drawable://" + R.mipmap.img_cersuccess);
//            mBitmapUtils.display(mViewHolder.mImgTag, Constacts.IMAGEHTTP + mBean.getIDCard());
        } else if (mBean.getStatus().equals("0")){
            //认证中
            mViewHolder.mImgTag.setImageResource(R.mipmap.img_cerchecking);
        }else if (mBean.getStatus().equals("-1")){
            //认证失败
            mViewHolder.mImgTag.setImageResource(R.mipmap.img_cerfalied);
        }
        mViewHolder.mImgCerIdZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlsBean mUrls = new UrlsBean();
                mUrls.getmUrlList().add(Constacts.IMAGEHTTP + mBean.getIDCard());
                Intent mBigPhoto = new Intent(context, PhotoScanActivity.class);
                mBigPhoto.putExtra("data", mUrls);
                mBigPhoto.putExtra("ID", position);
                context.startActivity(mBigPhoto);
                ((Activity) context).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
            }
        });
        mViewHolder.mImgCerIdF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlsBean mUrls = new UrlsBean();
                mUrls.getmUrlList().add(Constacts.IMAGEHTTP + mBean.getIDCard_F());
                Intent mBigPhoto = new Intent(context, PhotoScanActivity.class);
                mBigPhoto.putExtra("data", mUrls);
                mBigPhoto.putExtra("ID", position);
                context.startActivity(mBigPhoto);
                ((Activity) context).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
            }
        });
        mViewHolder.mImgCerPropertye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlsBean mUrls = new UrlsBean();
                mUrls.getmUrlList().add(Constacts.IMAGEHTTP + mBean.getHouse_certificate());
                Intent mBigPhoto = new Intent(context, PhotoScanActivity.class);
                mBigPhoto.putExtra("data", mUrls);
                mBigPhoto.putExtra("ID", position);
                context.startActivity(mBigPhoto);
                ((Activity) context).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
            }
        });
if (mBean.getReason()!=null){
    mViewHolder.mReason.setText("失败原因："+mBean.getReason());


}else{
    mViewHolder.mReason.setVisibility(View.GONE);
}
        //位置放到view里   方便知道点击的是那一条item
        mViewHolder.but_delete.setTag(position);

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


//        TimeUtil.getDayAllTime(Long.parseLong(mBean.getCreate_time()))
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());




        return convertView;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_CertificationInitBean.CertificationInitDataEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView mName,mReason;
        TextView mTel;
        TextView mAdress;
        ImageView mImgCerIdZ;
        ImageView mImgCerIdF;
        ImageView mImgCerPropertye, mImgTag;


        HorizontalScrollView mHSView;
        View mContentLayout;
        Button but_delete;
        View action;
    }

    private void deleteHttp(String id) {
        /**删除认证失败的房屋**/
        String url = "http://028hi.cn/api/yinit/delete.html";
        RequestParams params = new RequestParams();
        params.addBodyParameter("token", HighCommunityApplication.mUserInfo.getToken());
        params.addBodyParameter("id", id);
        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.e(Tag, "http 访问失败的 arg1--->" + arg1.toString());
//            HighCommunityUtils.GetInstantiation().ShowToast(arg1.toString(), 0);
                Toast.makeText(context, arg1.toString(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                String content = arg0.result;
                Log.e(Tag, "http 访问success的 content--->" + content);
                CommonHttpResultBean mInitBean = new Gson().fromJson(content, CommonHttpResultBean.class);
                //                ResponseGoodsItem responseGoodsItem = new Gson().fromJson(content, ResponseGoodsItem.class);
                if (mInitBean != null) {
                    Toast.makeText(context, mInitBean.getMsg(), Toast.LENGTH_SHORT).show();
//                HighCommunityUtils.GetInstantiation().ShowToast(mInitBean.getMsg(), 0);
                }
            }
        });
    }
}
