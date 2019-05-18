package com.wyj.treasure.customcontrol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimatorSetActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_animatorset)
    TextView tvAnimatorset;
    @BindView(R.id.menu)
    Button menu;
    @BindView(R.id.item1)
    Button item1;
    @BindView(R.id.item2)
    Button item2;
    @BindView(R.id.item3)
    Button item3;
    @BindView(R.id.item4)
    Button item4;
    @BindView(R.id.item5)
    Button item5;
    private int mRadius = 500;
    private boolean mIsMenuOpen = false;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_animator_set;
    }

    @Override
    protected void initData() {
        setTitle("AnimatorSet的用法");

    }


    private void doPlaySequentiallyAnimator() {
        ObjectAnimator backgroundColor = ObjectAnimator.ofInt(tvAnimatorset, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(tvAnimatorset, "translationY", 0, 300, 0);
        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(tvText, "translationY", 0, 400, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(backgroundColor, translationY, translationY1);
        animatorSet.setDuration(1000);
        animatorSet.start();

    }


    /**
     * - 第一：playTogether和playSequentially在激活动画后，控件的动画情况与它们无关，他们只负责定时激活控件动画。
     * - 第二：playSequentially只有上一个控件做完动画以后，才会激活下一个控件的动画，如果上一控件的动画是无限循环，
     * 那下一个控件就别再指望能做动画了。
     * <p>
     * 总之：playTogether和playSequentially只是负责指定什么时候开始动画，不干涉动画自己的运行过程。
     * 换言之：playTogether和playSequentially只是赛马场上的每个赛道的门，门打开以后，赛道上的那匹马怎么跑跟它没什么关系。
     *
     * @param view
     */
    @OnClick({R.id.btn_playSequentially, R.id.btn_playTogether, R.id.btn_setStartDelay, R.id.menu, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_playSequentially:
                doPlaySequentiallyAnimator();
                break;
            case R.id.btn_playTogether:
                doPlayTogetherAnimator();
                break;
            case R.id.btn_setStartDelay:
                doSetStartDelay();
                break;
            case R.id.menu:
                if (!mIsMenuOpen) {
                    doAnimateOpen(item1, 5, 0);
                    doAnimateOpen(item2, 5, 1);
                    doAnimateOpen(item3, 5, 2);
                    doAnimateOpen(item4, 5, 3);
                    doAnimateOpen(item5, 5, 4);
                }

                break;
            case R.id.item1:
            case R.id.item2:
            case R.id.item3:
            case R.id.item4:
            case R.id.item5:
                doAnimateClose(item1, 5, 0);
                doAnimateClose(item2, 5, 1);
                doAnimateClose(item3, 5, 2);
                doAnimateClose(item4, 5, 3);
                doAnimateClose(item5, 5, 4);
                ToastUtil.show("item5");
                break;
        }
    }

    private void doAnimateClose(View view, int total, int index) {
        double angdeg = Math.toRadians(90) / (total - 1) * index;
        int translationX = -(int) (mRadius * Math.sin(angdeg));
        int translationY = -(int) (mRadius * Math.cos(angdeg));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0)
        );
        animatorSet.setDuration(300);
        animatorSet.start();
        mIsMenuOpen = false;
    }

    private void doAnimateOpen(View view, int total, int index) {
        mIsMenuOpen = true;
        double angdeg = Math.toRadians(90) / (total - 1) * index;
        int translationX = -(int) (mRadius * Math.sin(angdeg));
        int translationY = -(int) (mRadius * Math.cos(angdeg));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1f)
        );
        animatorSet.setDuration(300);
        animatorSet.start();

    }

    /**
     * - AnimatorSet的延时是仅针对性的延长AnimatorSet激活时间的，对单个动画的延时设置没有影响。
     * - AnimatorSet真正激活延时 = AnimatorSet.startDelay+第一个动画.startDelay
     * - 在AnimatorSet激活之后，第一个动画绝对是会开始运行的，后面的动画则根据自己是否延时自行处理。
     */
    private void doSetStartDelay() {
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tvAnimatorset, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tvText, "translationY", 0, 400, 0);
        tv2TranslateY.setStartDelay(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv2TranslateY).with(tv1TranslateY);
        animatorSet.setStartDelay(2000);
        animatorSet.setDuration(2000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        });
    }

    private void doPlayTogetherAnimator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tvAnimatorset, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tvAnimatorset, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tvText, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

}
