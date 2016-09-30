package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.don.tools.BpiUniveralImage;

import net.duohuo.dhroid.util.ImageLoaderUtil;

import photo.activity.GalleryActivity;
import photo.util.Bimp;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.utils.Constacts;
import photo.util.PublicWay;

/**
 * 图片显示适配器
 */
public class GridAdapter extends BaseFragmentAdapter {

	Context mConstext;
	private LayoutInflater inflater;
	private int selectedPosition = -1;

	public GridAdapter(Context context) {
		mConstext = context;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		if (Bimp.tempSelectBitmap.size() > PublicWay.num) {
			return PublicWay.num;
		} else {
			return Bimp.tempSelectBitmap.size();
		}
	}

	public String getItem(int arg0) {
		return Bimp.tempSelectBitmap.get(arg0).getImagePath();
	}

	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_published_grida,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (Bimp.tempSelectBitmap.get(position).getImagePath().startsWith("drawable://")) {
			ImageLoaderUtil.disPlay(Bimp.tempSelectBitmap.get(position).getImagePath(), holder.image);
		} else {
			ImageLoaderUtil.disPlay("file://" + Bimp.tempSelectBitmap.get(position).getImagePath(), holder.image);
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}
}