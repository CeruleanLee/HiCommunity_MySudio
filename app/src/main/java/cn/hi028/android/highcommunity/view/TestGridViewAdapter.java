package cn.hi028.android.highcommunity.view;

import java.util.ArrayList;
import java.util.List;

import net.duohuo.dhroid.util.ImageLoaderUtil;
import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.PhotoScanActivity;
import cn.hi028.android.highcommunity.activity.fragment.CommunityFrag;
import cn.hi028.android.highcommunity.bean.PicBean;
import cn.hi028.android.highcommunity.bean.UrlsBean;
import cn.hi028.android.highcommunity.utils.Constacts;

public class TestGridViewAdapter extends BaseAdapter {

	Activity mContent;
	List<PicBean> mList = new ArrayList<PicBean>();

	ArrayList<String> list;
	//	public Bitmap bitmaps[];
	private FinalBitmap finalImageLoader;
	private int wh;

	public TestGridViewAdapter(Activity context,ArrayList<String> data) {
		this.mContent=context;
		this.wh=(SysUtils.getScreenWidth(context)-SysUtils.Dp2Px(context, 99))/3;
		this.list=data;
		this.finalImageLoader=FinalBitmap.create(context);
		this.finalImageLoader.configLoadingImage(R.drawable.ic_launcher);//ͼƬ�������ǰ��ʾ��ͼƬ
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}


	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		Holder holder;
		if (view==null) {
			view=LayoutInflater.from(mContent).inflate(R.layout.item_gridview, null);
			holder=new Holder();
			holder.imageView=(ImageView) view.findViewById(R.id.imageView);
			view.setTag(holder);
		}else {
			holder= (Holder) view.getTag();
		}
		finalImageLoader.display(holder.imageView, list.get(position));
		final String bigurl = mList.get(position).getBig();
		String smallUrl = mList.get(position).getSmall();
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(wh,wh);
		view.setLayoutParams(param);



		//        final int position2 = position;
		//        ImageLoaderUtil.disPlay(Constacts.IMAGEHTTP + smallUrl, mViewHolder.mImage, R.mipmap.default_no_pic, null);
		//        mViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View view) {
		//                UrlsBean mUrls = new UrlsBean();
		//                for (int i = 0; i < mList.size(); i++) {
		//                    mUrls.getmUrlList().add(Constacts.IMAGEHTTP + mList.get(i).getBig());
		//                }
		//                CommunityFrag.isNeedRefresh = false;
		//                Intent mBigPhoto = new Intent(mContent, PhotoScanActivity.class);
		//                mBigPhoto.putExtra("data", mUrls);
		//                mBigPhoto.putExtra("ID", position);
		//                mContent.startActivity(mBigPhoto);
		//                ((Activity) mContent).overridePendingTransition(R.anim.dyn_pic_scan_miss, R.anim.dyn_pic_scan_miss_no);
		//            }
		//        });







		return view;
	}

	class Holder{
		ImageView imageView;
	}

	public void AddNewData(Object mObject) {
		if (mObject instanceof List<?>) {
			mList = (List<PicBean>) mObject;
		}
		notifyDataSetChanged();
	}


}
