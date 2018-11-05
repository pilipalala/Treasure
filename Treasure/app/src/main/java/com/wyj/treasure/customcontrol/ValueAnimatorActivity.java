package com.wyj.treasure.customcontrol;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ValueAnimatorActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.mypointview)
    MyPointView mypointview;
    private ValueAnimator animator;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_animator_anim;
    }

    @Override
    protected void initData() {
        setTitle("ValueAnimator的使用");
    }

    @OnClick({R.id.bt_start, R.id.bt_start_point, R.id.tv, R.id.bt_start_object, R.id.bt_start_argb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start_point:
//                mypointview.doPointFloatAnim();
                mypointview.doPointAnim();
                break;
            case R.id.bt_start_object:
                animator = ValueAnimator.ofObject(new CharEvaluator(), new Character('A'), new Character('Z'));
                animator.setDuration(10000);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        char value = (char) animation.getAnimatedValue();
                        tv.setText(String.valueOf(value));
                    }
                });

                animator.start();
                break;
            case R.id.bt_start_argb:
                animator = ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
                animator.setDuration(3000);
                animator.setEvaluator(new ArgbEvaluator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        LogUtil.i("value" + value);
                        tv.setBackgroundColor(value);
                    }
                });

                animator.start();
                break;
            case R.id.bt_start:
                ValueAnimator animator = ValueAnimator.ofInt(0, 500);
                animator.setDuration(3000);
                animator.setEvaluator(new MyEvaluator());
                animator.setInterpolator(new MyInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        LogUtil.i("value" + value);
                        tv.layout(tv.getLeft(), value, tv.getRight(), tv.getHeight() + value);
                    }
                });
                animator.start();
                break;
            case R.id.tv:
                ToastUtil.show("clicked me");
                break;
        }

    }


    /**
     * 自定义插值器 (加速器)
     * 对于input参数，它表示的是当前动画的进度，匀速增加的。什么叫动画的进度，动画的进度就是动画在时间上的进度，
     * 与我们的任何设置无关，随着时间的增长，动画的进度自然的增加，从0到1；input参数相当于时间的概念，
     * 我们通过setDuration()指定了动画的时长，在这个时间范围内，动画进度肯定是一点点增加的；
     * 就相当于我们播放一首歌，这首歌的进度是从0到1是一样的。
     * <p>
     * <p>
     * 而返回值则表示动画的数值进度，它的对应的数值范围是我们通过ofInt(),ofFloat()来指定的，这个返回值就表示当前时间所对应的数值的进度。
     */
    private class MyInterpolator implements TimeInterpolator {


        /**
         * @param input 参数是一个float类型，它取值范围是0到1，表示当前动画的进度，
         *              取0时表示动画刚开始，取1时表示动画结束，取0.5时表示动画中间的位置，其它类推。
         * @return 返回值：表示当前实际想要显示的进度。取值可以超过1也可以小于0，
         * 超过1表示已经超过目标值，小于0表示小于开始位置。
         */
        @Override
        public float getInterpolation(float input) {
            return 1 - input;
        }
    }

    /**
     * 只有定义动画时的数值类型与Evalutor的返回值类型一样时，才能使用这个Evalutor；
     * 很显然ofInt()定义的数值类型是Integer而我们定义的MyEvaluator，它的返回值类型也是Integer；
     * 所以我们定义的MyEvaluator可以给ofInt（）来用。
     * 同理，如果我们把实现的TypeEvaluator填充为为Float类型，那么这个Evalutor也就只能给FloatEvalutor用了。
     */
    private class MyEvaluator implements TypeEvaluator<Integer> {

        /**
         * @param fraction   就是加速器中的返回值，表示当前动画的数值进度，百分制的小数表示。
         * @param startValue
         * @param endValue   startValue和endValue分别对应ofInt(int start,int end)中的start和end的数值；
         * @return
         */
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int startInt = startValue;
            return (int) (200 + startInt + fraction * (endValue - startInt));
        }
    }

    private class CharEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int startInt = (int) startValue;
            int endInt = (int) endValue;
            int cutInt = (int) (startInt + fraction * (endInt - startInt));
            char resurt = (char) cutInt;
            return resurt;
        }
    }


}
