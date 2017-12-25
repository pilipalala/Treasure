package com.wyj.treasure.customcontrol;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorSetActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_animatorset)
    TextView tvAnimatorset;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_animator_set);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("AnimatorSet的用法");
        toolbar.setNavigationOnClickListener(v -> finish());

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
    @OnClick({R.id.btn_playSequentially, R.id.btn_playTogether})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_playSequentially:
                doPlaySequentiallyAnimator();
                break;
            case R.id.btn_playTogether:
                doPlayTogetherAnimator();
                break;
        }
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
