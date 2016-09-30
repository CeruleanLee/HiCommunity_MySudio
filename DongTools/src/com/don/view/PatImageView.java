package com.don.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author dong
 * @category 自定义ImageView 便于图像操作（放大 缩小 移动 旋转）
 */
public class PatImageView extends ImageView {
	private Matrix matrix;
	private Matrix savedMatrix;
	private boolean long_touch = false;
	private static int NONE = 0;
	private static int DRAG = 1; // 拖动
	private static int ZOOM = 2; // 收缩
	private static int ROTA = 3; // 旋转
	private int mode = NONE;
	private PointF startPoint;
	private PointF middlePoint;
	private float oldDistance;
	private float oldAngle;
	float newAngle;
	private Vibrator vibrator;
	float scale = 1f;
	float degrees = 0;
	float chageX=0,chageY=0;
	private GestureDetector gdetector;
	private Bitmap bitmap = null;

	// private Matrix myMatrix = null;

	public PatImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PatImageView(final Context context) {
		super(context);
		init(context);
	}

	public void init(final Context context) {
		matrix = new Matrix();
		savedMatrix = new Matrix();
		matrix.setTranslate(0f, 0f);
		setScaleType(ScaleType.MATRIX);
		setImageMatrix(matrix);
		startPoint = new PointF();
		middlePoint = new PointF();
		oldDistance = 1f;
		gdetector = new GestureDetector(context,
				new GestureDetector.OnGestureListener() {
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return true;
					}

					@Override
					public void onShowPress(MotionEvent e) {
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						long_touch = true;
					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						return true;
					}

					@Override
					public boolean onDown(MotionEvent e) {
						return true;
					}
				});

		setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN: // 第一个手指touch
					savedMatrix.set(matrix);
					startPoint.set(event.getX(), event.getY());
					mode = DRAG;
					long_touch = false;
					break;
				case MotionEvent.ACTION_POINTER_DOWN: // 第二个手指touch
					oldDistance = getDistance(event); // 计算第二个手指touch时，两指之间的距离
					oldAngle = getDegree(event); // 计算第二个手指touch时，两指所形成的直线和x轴的角度
					if (oldDistance > 10f) {
						savedMatrix.set(matrix);
						middlePoint = midPoint(event);
						if (!long_touch) {
							mode = ZOOM;
						} else {
							mode = ROTA;
						}
					}
					break;
				case MotionEvent.ACTION_UP:
					break;
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_MOVE:
					if (vibrator != null)
						vibrator.cancel();
					if (mode == DRAG) {
						matrix.set(savedMatrix);
						chageX = event.getX() - startPoint.x;
						chageY = event.getY() - startPoint.y;
						matrix.postTranslate(chageX, chageY);
					}

					if (mode == ZOOM) {
						float newDistance = getDistance(event);

						if (newDistance > 10f) {
							matrix.set(savedMatrix);
							scale = newDistance / oldDistance;
							matrix.postScale(scale, scale, 0, 0);
						}
					}

					if (mode == ROTA) {
						newAngle = getDegree(event);
						matrix.set(savedMatrix);
						degrees = newAngle - oldAngle;
					}
					break;
				}
				setImageMatrix(matrix);
				invalidate();
				gdetector.onTouchEvent(event);
				return true;
			}
		});
	}

	public void setImageBitmaps(Bitmap bm) {
		// TODO Auto-generated method stub
		matrix = new Matrix();
		savedMatrix = new Matrix();
		chageX=0;
		chageY=0;
		setImageMatrix(matrix);
		super.setImageBitmap(bm);
	}

	// 计算两个手指之间的距离
	private float getDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// 计算两个手指所形成的直线和x轴的角度
	private float getDegree(MotionEvent event) {
		return (float) (Math.atan((event.getY(1) - event.getY(0))
				/ (event.getX(1) - event.getX(0))) * 180f);
	}

	// 计算两个手指之间，中间点的坐标
	private PointF midPoint(MotionEvent event) {
		PointF point = new PointF();
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);

		return point;
	}

	public void ReleaseBitmap() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}
}