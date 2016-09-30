package cn.hi028.android.highcommunity.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.hi028.android.highcommunity.R;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected Context context;
	protected List<T> data;
	private boolean isAnimation = true;
	private Random random;
	private int lastPosition = -1;
	private int animationid[] = { 0, 0 };
	private int numCount;
	public float density;

	public MyBaseAdapter(Context context, List<T> data) {
		this.context = context;
		this.data = data == null ? new ArrayList<T>() : data;
		random = new Random();
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		density = metrics.density;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= data.size())
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setAnimation(int[] animationid, int numCount) {
		this.animationid = animationid;
		this.numCount = numCount;
	}

	public void closeAnimation() {
		isAnimation = false;
	}

	/**
	 * �÷�����Ҫ����ʵ�֣���Ҫ����item���ֵ�resource id
	 * 
	 * @return
	 */
	public abstract int getItemResource(int pos);

	/**
	 * ʹ�ø�getItemView�����滻ԭ����getView��������Ҫ����ʵ��
	 * 
	 * @param position
	 * @param parent
	 * @param holder
	 * @return
	 */
	public abstract View getItemView(int position, View convertView,
			ViewHolder holder, ViewGroup parent);

	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = View
					.inflate(context, getItemResource(position), null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// final View v = convertView;
		// if (isAnimation && position > lastPosition) {
		// Animation animation = null;
		// if (animationid[0] == 0) {
		// switch (random.nextInt(2)) {
		// case 0:
		// animation = AnimationUtils.loadAnimation(
		// convertView.getContext(), R.anim.item_bottom_in_1);
		// break;
		// case 1:
		// animation = AnimationUtils.loadAnimation(
		// convertView.getContext(), R.anim.item_bottom_in_2);
		// break;
		// }
		// } else {
		// int pos = position % numCount;
		// int n = numCount / 2;
		// if (pos < n) {
		// animation = AnimationUtils.loadAnimation(
		// convertView.getContext(), animationid[0]);
		// if (pos == 0) {
		// animation.setDuration(700);
		// }
		// } else {
		// animation = AnimationUtils.loadAnimation(
		// convertView.getContext(), animationid[1]);
		// if (pos == numCount - 1) {
		// animation.setDuration(700);
		// }
		// }
		// }
		// convertView.startAnimation(animation);
		// animation.setAnimationListener(new AnimationListener() {
		// @Override
		// public void onAnimationStart(Animation animation) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		// // TODO Auto-generated method stub
		// v.clearAnimation();
		// }
		//
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// // TODO Auto-generated method stub
		// v.clearAnimation();
		// }
		// });
		// lastPosition = position;
		// }
		return getItemView(position, convertView, holder, parent);
	}

	public class ViewHolder {
		private SparseArray<View> views = new SparseArray<View>();
		private View convertView;

		public ViewHolder(View convertView) {
			this.convertView = convertView;
		}

		@SuppressWarnings("unchecked")
		public <T extends View> T getView(int resId) {
			View v = views.get(resId);
			if (null == v) {
				v = convertView.findViewById(resId);
				views.put(resId, v);
			}
			return (T) v;
		}
	}

	public void addAll(List<T> elem) {
		data.addAll(elem);
		notifyDataSetChanged();
	}

	public void remove(T elem) {
		data.remove(elem);
		notifyDataSetChanged();
	}

	public void remove(int index) {
		data.remove(index);
		notifyDataSetChanged();
	}

	public void removeAll() {
		data = new ArrayList<T>();
		notifyDataSetChanged();
	}

	public void replaceAll(List<T> elem) {
		data.clear();
		data.addAll(elem);
		notifyDataSetChanged();
	}
}