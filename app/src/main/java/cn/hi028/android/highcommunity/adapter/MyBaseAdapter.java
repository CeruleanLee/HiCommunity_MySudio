package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	 *
	 * @return
	 */
	public abstract int getItemResource(int pos);

	/**
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