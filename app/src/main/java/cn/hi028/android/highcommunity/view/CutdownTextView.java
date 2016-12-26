package cn.hi028.android.highcommunity.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
/**
 * @功能：直供页倒计时类<br>
 * @作者： Lee_yting<br>
 * @时间：2016/11/29<br>
 */
public class CutdownTextView extends TextView{

	public CutdownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	private SimpleDateFormat dateFormat;
	private CountDownTimer countDownTimer;
	private String patternHour = "HH:mm:ss";
	private String patternMinute = "mm:ss.SS";
	private static final int STATE_HOUR = 1;
	private static final int STATE_MINUTE = 2;
	private int currentState;

	public void startCutdown(long time,long period){
		stopCutdown();
		if(time <= 0){
			if(listener != null){
				listener.onFinish();
			}
			return;
		}
		if(time >= 60*60*1000){
			dateFormat = new SimpleDateFormat(patternHour);
			currentState = STATE_HOUR;
		}else{
			dateFormat = new SimpleDateFormat(patternMinute);
			currentState = STATE_MINUTE;
		}
		if(countDownTimer == null){
			countDownTimer = new CountDownTimer(time, period) {

				@Override
				public void onTick(long millisUntilFinished) {
						if(currentState == STATE_HOUR && millisUntilFinished < 60*60*1000){
					dateFormat.applyPattern(patternMinute);
					currentState = STATE_MINUTE;
				}
				setText(dateFormat.format(millisUntilFinished+3600000*16));
				}
				@Override
				public void onFinish() {
					stopCutdown();
					setText(dateFormat.format(0));
					if(listener != null){
						listener.onFinish();
					}
				}
			};
			countDownTimer.start();
		}
	}
	public void startCutdown(int position,long time,long period){
		stopCutdown();
		if(time <= 0){
			if(listener != null){
				listener.onFinish();
			}
			return;
		}
		if(time >= 60*60*1000){
			dateFormat = new SimpleDateFormat(patternHour);
			currentState = STATE_HOUR;
		}else{
			dateFormat = new SimpleDateFormat(patternMinute);
			currentState = STATE_MINUTE;
		}
		if(countDownTimer == null){
			countDownTimer = new CountDownTimer(time, period) {
				
				@Override
				public void onTick(long millisUntilFinished) {

					if(currentState == STATE_HOUR && millisUntilFinished < 60*60*1000){
						dateFormat.applyPattern(patternMinute);
						currentState = STATE_MINUTE;
					}
					setText(dateFormat.format(millisUntilFinished+3600000*16));
					if (millisUntilFinished==0){
						setText("抢购结束");

					}else{

//						setText(TimeUtil.getCountTime(millisUntilFinished / 1000));
					}
				}
				@Override
				public void onFinish() {
					setText("抢购结束");
					if(listener != null){
						listener.onFinish();
					}
					stopCutdown();
				}
			};
			countDownTimer.start();
		}
	}
	public void stopCutdown(){
		if(countDownTimer != null){
			countDownTimer.cancel();
			countDownTimer = null;
		}
	}
	private OnCountDownFinishListener listener;
	public void setOnCountDownFinishListener(OnCountDownFinishListener listener){
		this.listener = listener;
	}
	public interface OnCountDownFinishListener{
		public void onFinish();
	}
	
	private void print(int position,String str){
		if(position != 0){
			System.out.println(str);
		}
	}
}
