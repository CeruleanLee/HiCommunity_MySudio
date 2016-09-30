package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.don.tools.BpiHttpHandler;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.DhUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.adapter.ShakeAdapter;
import cn.hi028.android.highcommunity.bean.ShakeBean;
import cn.hi028.android.highcommunity.bean.ShakeUser;
import cn.hi028.android.highcommunity.lisenter.ShakeListener;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.ShakeToastUtils;
import cn.hi028.android.highcommunity.view.CustomList;

/**
 * Created by 赵海 on 2016/4/23.
 * 摇一摇功能
 */
@EFragment(resName = "frag_shake")
public class ShakeFrag extends BaseFragment {
    public static final String FRAGMENTTAG = "ShakeFrag";
    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    @ViewById(R.id.img_shake_center)
    ImageView img_shake;
    @ViewById(R.id.img_shake_more)
    ImageView img_shake_more;
    @ViewById(R.id.img_shake_time)
    ImageView img_shake_time;
    @ViewById(R.id.ll_shake_list)
    LinearLayout ll_shake_list;
    @ViewById(R.id.cl_shake_list)
    CustomList cl_shake_list;
    @ViewById(R.id.fl_shake)
    FrameLayout fl_shake;

    ShakeAdapter adapter;

    @AfterViews
    void initView() {
//        img_shake_time.setVisibility(View.INVISIBLE);
        WindowManager wm = getActivity().getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        fl_shake.setMinimumHeight(height - DhUtil.dip2px(getActivity(), 48));
        adapter = new ShakeAdapter(getActivity());
        cl_shake_list.setAdapter(adapter);
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(Context.VIBRATOR_SERVICE);
        mShakeListener = new ShakeListener(getActivity());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                startAnim();  //开始 摇一摇手掌动画
                mShakeListener.stop();
                startVibrato(); //开始 震动
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVibrator.cancel();
                        mShakeListener.start();
                        HTTPHelper.ShakeData(new BpiHttpHandler.IBpiHttpHandler() {
                            @Override
                            public void onError(int id, String message) {
                                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
                                img_shake_time.setVisibility(View.VISIBLE);
                                if ("您今天的机会已经用完".equals(message))
                                    img_shake_time.setImageResource(R.mipmap.img_shake_time_zero);
                            }

                            @Override
                            public void onSuccess(Object message) {
                                startReult();
                                if (message == null)
                                    return;
                                img_shake_time.setVisibility(View.VISIBLE);
                                ShakeBean shakeBean = (ShakeBean) message;
                                if (shakeBean.getLeftCount() == 0) {
                                    img_shake_time.setImageResource(R.mipmap.img_shake_time_zero);
                                } else if (shakeBean.getLeftCount() == 1) {
                                    img_shake_time.setImageResource(R.mipmap.img_shake_time_one);
                                } else if (shakeBean.getLeftCount() == 2) {
                                    img_shake_time.setImageResource(R.mipmap.img_shake_time_two);
                                } else if (shakeBean.getLeftCount() == 3) {
                                    img_shake_time.setImageResource(R.mipmap.img_shake_time_three);
                                }
                                ShakeToastUtils shakeToastUtils = new ShakeToastUtils(getActivity(), shakeBean);
                                shakeToastUtils.show();
                            }

                            @Override
                            public Object onResolve(String result) {
                                return HTTPHelper.ResolveShakeData(result);
                            }

                            @Override
                            public void setAsyncTask(AsyncTask asyncTask) {

                            }

                            @Override
                            public void cancleAsyncTask() {

                            }
                        });
                    }
                }, 2000);
            }
        });

    }

    @Click(R.id.img_shake_more)
    public void onMore() {
        if (ll_shake_list.getVisibility() == View.VISIBLE) {
            ll_shake_list.setVisibility(View.GONE);
        } else {
            ll_shake_list.setVisibility(View.VISIBLE);
            update();
        }
    }

    public void update() {
        HTTPHelper.ShakeDataList(new BpiHttpHandler.IBpiHttpHandler() {
            @Override
            public void onError(int id, String message) {

            }

            @Override
            public void onSuccess(Object message) {
                if (message == null)
                    return;
                List<ShakeUser> listData = (List<ShakeUser>) message;
                adapter.setMlist(listData);
            }

            @Override
            public Object onResolve(String result) {
                return HTTPHelper.ResolveShakeDataList(result);
            }

            @Override
            public void setAsyncTask(AsyncTask asyncTask) {

            }

            @Override
            public void cancleAsyncTask() {

            }
        });
    }

    public void startAnim() {   //定义摇一摇动画动画
        // 设置在动画开始时,旋转角度为0度
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        // 设置在动画执行50%时,旋转角度为360度
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        // 设置在动画结束时,旋转角度为0度
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        // 使用PropertyValuesHolder进行属性名称和值集合的封装
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
                "rotation", kf0, kf1, kf2);
        // 通过ObjectAnimator进行执行
        ObjectAnimator amin = ObjectAnimator.ofPropertyValuesHolder(img_shake,
                pvhRotation);
        // 设置执行时间(1000ms)
        amin.setDuration(1000).addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // 通过静态方法构建一个ObjectAnimator对象
                // 设置作用对象、属性名称、数值集合
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
        // 开始动画
        amin.start();

    }

    public void startVibrato() {
        MediaPlayer player;
        player = MediaPlayer.create(getActivity(), R.raw.shake);
        player.setLooping(false);
        player.start();
        //定义震动
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }

    public void startReult() {
        MediaPlayer player;
        player = MediaPlayer.create(getActivity(), R.raw.result);
        player.setLooping(false);
        player.start();
        //定义震动
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }
}
