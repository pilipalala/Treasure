package com.wyj.treasure.customcontrol;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;


/**
 * scale —— ScaleAnimation
 * alpha —— AlphaAnimation
 * rotate —— RotateAnimation
 * translate —— TranslateAnimation
 * set —— AnimationSet
 * <p>
 * <p>
 * ndroid:duration                  setDuration(long)	 动画持续时间，以毫秒为单位
 * android:fillAfter                    setFillAfter(boolean)	如果设置为true，控件动画结束时，将保持动画最后时的状态
 * android:fillBefore                 setFillBefore(boolean)	如果设置为true,控件动画结束时，还原到开始动画前的状态
 * android:fillEnabled              setFillEnabled(boolean)	与android:fillBefore 效果相同，都是在动画结束时，将控件还原到初始化状态
 * android:repeatCount           setRepeatCount(int)	重复次数
 * android:repeatMode            setRepeatMode(int)	重复类型，有reverse和restart两个值，取值为RESTART或 REVERSE，必须与repeatCount一起使用才能看到效果。因为这里的意义是重复的类型，即回放时的动作。
 * android:interpolator            setInterpolator(Interpolator) 设定插值器，其实就是指定的动作效果，比如弹跳效果等
 */
public class Interpolator extends BaseActivity {

    @Override
    protected int getContentViewID() {
        return R.layout.activity_interpolator;
    }

    @Override
    protected void initData() {

    }
}
