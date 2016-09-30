package cn.hi028.android.highcommunity.view.snap;


import android.content.Context;
import android.view.View;
import cn.hi028.android.highcommunity.R;

public class McoyProductDetailInfoPage implements McoySnapPageLayout.McoySnapPage{
	
	private Context context;
	
	private View rootView = null;
	private McoyScrollView mcoyScrollView = null;
	
	public McoyProductDetailInfoPage (Context context, View rootView) {
		this.context = context;
		this.rootView = rootView;
		mcoyScrollView = (McoyScrollView) this.rootView
				.findViewById(R.id.page1_ScrollView);
	}
	
	@Override
	public View getRootView() {
		return rootView;
	}

	@Override
	public boolean isAtTop() {
		return true;
	}

	@Override
	public boolean isAtBottom() {
        int scrollY = mcoyScrollView.getScrollY();
        int height = mcoyScrollView.getHeight();
        int scrollViewMeasuredHeight = mcoyScrollView.getChildAt(0).getMeasuredHeight();

        if ((scrollY + height) >= scrollViewMeasuredHeight) {
            return true;
        }
        return false;
	}

}
