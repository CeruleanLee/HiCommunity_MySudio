/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.tools.BpiUniveralImage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.MBitmapHolder;

/**
 * @功能：已认证<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/28<br>
 */
public class CerSuccesstAdapter extends BaseFragmentAdapter {

    final String Tag = "-CerSuccesstAdapter->";
    List<Auto_CertificationInitBean.CertificationInitDataEntity> mList = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    BitmapUtils mBitmapUtils;
    public CerSuccesstAdapter(List<Auto_CertificationInitBean.CertificationInitDataEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_CertificationInitBean.CertificationInitDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        mBitmapUtils= MBitmapHolder.getBitmapUtils(context);

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_cer_success, null);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.cer_name);
            mViewHolder.mTel = (TextView) convertView.findViewById(R.id.cer_tel);
            mViewHolder.mAdress = (TextView) convertView.findViewById(R.id.cer_adress);
            mViewHolder.mImgCerIdZ = (ImageView) convertView.findViewById(R.id.img_cer_idZ);
            mViewHolder.mImgCerIdF = (ImageView) convertView.findViewById(R.id.img_cer_idF);
            mViewHolder.mImgCerPropertye = (ImageView) convertView.findViewById(R.id.img_cer_propertye);
            mViewHolder.mImgTag = (ImageView) convertView.findViewById(R.id.img_cer_Tag);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Auto_CertificationInitBean.CertificationInitDataEntity mBean = mList.get(position);
        mViewHolder.mName.setText("业主姓名："+mBean.getName());
        mViewHolder.mTel.setText("联系方式："+mBean.getTel());
        mViewHolder.mAdress.setText("详细地址："+mBean.getAddress());
        mBitmapUtils.display(mViewHolder.mImgCerIdZ, Constacts.IMAGEHTTP + mBean.getIDCard());
        mBitmapUtils.display(mViewHolder.mImgCerIdF, Constacts.IMAGEHTTP + mBean.getIDCard_F());
        mBitmapUtils.display(mViewHolder.mImgCerPropertye, Constacts.IMAGEHTTP + mBean.getHouse_certificate());
        if (mBean.getStatus().equals("0")) {
            mBitmapUtils.display(mViewHolder.mImgTag, "drawable://" + R.mipmap.defult_avatar);
//            mBitmapUtils.display(mViewHolder.mImgTag, Constacts.IMAGEHTTP + mBean.getIDCard());
        } else if (mBean.getStatus().equals("1")){
            mBitmapUtils.display(mViewHolder.mImgTag, "drawable://" + R.mipmap.defult_avatar);
        }else if (mBean.getStatus().equals("2")){
            mBitmapUtils.display(mViewHolder.mImgTag, "drawable://" + R.mipmap.defult_avatar);

        }
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
        TextView mName;
        TextView mTel;
        TextView mAdress;
        ImageView mImgCerIdZ;
        ImageView mImgCerIdF;
        ImageView mImgCerPropertye,mImgTag;
    }
}
