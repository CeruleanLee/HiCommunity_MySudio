package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.don.tools.BpiUniveralImage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.PicBean;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * @功能：adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/28<br>
 */
public class PicDetailListAdapter extends BaseFragmentAdapter {
    public static final String TAG = "PicDetailListAdapter：";
    List<PicBean> mList = new ArrayList<PicBean>();
    private Context context;
    private LayoutInflater layoutInflater;

    public PicDetailListAdapter(List<PicBean> list, Context context) {
        super();
        Log.e(TAG, "SupplyCategoryListAdapter");

        this.mList = list;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        bitmapUtils = MBitmapHolder.getBitmapUtils(context);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PicBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    ViewHolder mViewHolder = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_picdetaillist, null);
            mViewHolder.pic_img = (ImageView)convertView.findViewById(R.id.pic_img);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final PicBean mBean = mList.get(position);

        if (mBean.getImgUrl() == null || mBean.getImgUrl().equals("")) {
            BpiUniveralImage.displayImage("drawable://" + R.mipmap.default_no_pic, mViewHolder.pic_img);
        } else {
//            mViewHolder.pic_img.setImageURI(mBean.getImgUrl());
            BpiUniveralImage.displayImage(mBean.getImgUrl(), mViewHolder.pic_img);
        }
//        mViewHolder.pic_img.getLayoutParams().height=(Integer.parseInt(mBean.getHeight()));
        return convertView;
    }

    BitmapUtils bitmapUtils;

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<PicBean>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }
    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }
    class ViewHolder {
        ImageView pic_img;
    }

}
