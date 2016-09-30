package cn.hi028.android.highcommunity.view;




import cn.hi028.android.highcommunity.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DownHint extends RelativeLayout {
	private LayoutInflater layoutinflater;
	private ProgressBar pb;
	private ImageView icon;
	private TextView name;
	private boolean isStart = false;
	private String load = "正在加载中...";
	private int loadNum = 2;

	public DownHint(Context context) {
		super(context);
		init(context);
	}

	public DownHint(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DownHint(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		layoutinflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutinflater.inflate(R.layout.down_hint, this);
		// View view = layoutinflater.inflate(R.layout.down, root)
		try {
			pb = (ProgressBar) view.findViewById(R.id.progress_circular);
			icon = (ImageView) view.findViewById(R.id.imageZengIco);
			name = (TextView) view.findViewById(R.id.TextViewTitle);
			//showProgress(load);
		} catch (Exception e) {  
		}
	}

	public void showProgress(final String content) {
		setVisibility(View.VISIBLE);
		icon.setVisibility(View.GONE);
		name.setVisibility(View.VISIBLE);
		name.setText(content + "");
		pb.setVisibility(View.VISIBLE);
		final int length = content.length();
		final long time = 1000;
		if (!isStart) {
			isStart = true;
			new Thread(new Runnable() {  
				@Override
				public void run() {
					while (isStart) {
						try {
							name.post(new Runnable() {

								@Override
								public void run() {
									if (loadNum < 0) {
										loadNum = 2;
									}
									name.setText(content.substring(0, length
											- loadNum));
								}
							});
							Thread.sleep(300);
							loadNum--;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

		}
	}

	public void showImage() {
		setVisibility(View.VISIBLE);
		icon.setVisibility(View.VISIBLE);
		name.setVisibility(View.VISIBLE);
		name.setText("û�ҵ����");
		pb.setVisibility(View.GONE);
		isStart = false;
	}

	public void hideView() {
		isStart = false;
		setVisibility(View.GONE);
	}

	public void setHintContent(String content) {
		name.setText(content);
	}

	public void setImage(int imgID) {
		icon.setImageResource(imgID);
	}

	public void setImageText(String content, int imgID) {
		name.setText(content);
		icon.setImageResource(imgID);
	}

	public void closeThread() {
		isStart = false;
	}
}
