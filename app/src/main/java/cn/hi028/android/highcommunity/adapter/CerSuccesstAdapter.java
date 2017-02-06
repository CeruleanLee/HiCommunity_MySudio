package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CertificationInitBean;
import cn.hi028.android.highcommunity.bean.UrlsBean;
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
        mBitmapUtils = MBitmapHolder.getBitmapUtils(context);

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
            convertView = layoutInflater.inflate(R.layout.item_cer_success, null);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.cer_name);
            mViewHolder.mTel = (TextView) convertView.findViewById(R.id.cer_tel);
            mViewHolder.mAdress = (TextView) convertView.findViewById(R.id.tv_cer_adress);
            mViewHolder.mImgCerIdZ = (ImageView) convertView.findViewById(R.id.img_cer_idZ);
            mViewHolder.mImgCerIdF = (ImageView) convertView.findViewById(R.id.img_cer_idF);
            mViewHolder.mImgCerPropertye = (ImageView) convertView.findViewById(R.id.img_cer_propertye);
            mViewHolder.mImgTag = (ImageView) convertView.findViewById(R.id.img_cer_Tag);
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
            mViewHolder.mImgTag.setImageResource(R.mipmap.img_cersuccess);
        } else if (mBean.getStatus().equals("0")) {
            //认证中
            mViewHolder.mImgTag.setImageResource(R.mipmap.img_cerchecking);
        } else if (mBean.getStatus().equals("-1")) {
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
        ImageView mImgCerPropertye, mImgTag;
    }
}
