package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lee_yting on 2016/12/2 0002.
 * 说明：自定义虚线
 */
public class DashedLineView extends View {

    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0Xffe5e5e5);//颜色可以自己设置
        Path path = new Path();
        path.moveTo(0, 0);//起始坐标
        path.lineTo(0,500);//终点坐标
        PathEffect effects = new DashPathEffect(new float[]{8,8,8,8},2);//设置虚线的间隔和点的长度
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}