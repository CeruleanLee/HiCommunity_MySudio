package photo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import photo.adapter.FolderAdapter;
import photo.util.Bimp;
import photo.util.PublicWay;
import photo.util.Res;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author king
 * @version 2014年10月18日  下午11:48:06
 * @QQ:595163260
 */
public class ImageFile extends BaseActivity {

    private FolderAdapter folderAdapter;
    private Button bt_cancel;
    private View mHight;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//         隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_image_file"));
        PublicWay.activityList.add(this);
        mContext = this;
        mHight = this.findViewById(Res.getWidgetID("title_secondTitle_Hight"));
        if (!super.isVersionBiger()) {
            mHight.setVisibility(View.GONE);
        } else {
            mHight.setVisibility(View.VISIBLE);
        }
        bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
        bt_cancel.setOnClickListener(new CancelListener());
        GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
        TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
        textView.setText(Res.getString("photo"));
        folderAdapter = new FolderAdapter(this);
        gridView.setAdapter(folderAdapter);
    }

    private class CancelListener implements OnClickListener {// 取消按钮的监听

        public void onClick(View v) {
            //清空选择的图片
            Bimp.clear();
            finish();
//            Intent intent = new Intent();
//            intent.setClass(mContext, MainActivity.class);
//            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x22 && resultCode == 0x23) {
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent();
//			intent.setClass(mContext, MainActivity.class);
//			startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
