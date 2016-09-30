package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 * 功能：自定义网格布局，防止与scorllview冲突<br>
 * 作者：赵海<br>
 * 时间：2014-5-23<br>
 * 版本：1.0.0<br>
 */
public class CustomList extends ListView {

	public CustomList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomList(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomList(Context context) {
		super(context);
	}

	/**
	 * 设置gridview不滚动
	 */

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
