package com.wyj.treasure.activity.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionsSlideActivity extends BaseActivity {

    @BindView(R.id.red_box)
    TextView redBox;
    @BindView(R.id.green_box)
    TextView greenBox;
    @BindView(R.id.blue_box)
    TextView blueBox;
    @BindView(R.id.black_box)
    TextView blackBox;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_transitions_slide);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("属性动画");
        toolbar.setNavigationOnClickListener(v -> finish());

    }


    private void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }


    /**
     * @param view （1）当点击事件发生之后调用TransitionManager的beginDelayedTransition()方法，
     *             并且传递了rlRoot和一个Fade对象最为参数。
     *             之后，framework会立即调用transition类的captureStartValues()
     *             方法为每个view保存其当前的可见状态(visibility)。
     *             <p>
     *             （2）当beginDelayedTransition返回之后，在上面的代码中将每个view设置为不可见
     *             <p>
     *             （3）在接下来的显示中framework会调用transition类的captureEndValues()方法，记录每个view最新的可见状态。
     *             <p>
     *             （4）接着，framework调用transition的createAnimator()方法。
     *             transition会分析每个view的开始和结束时的数据发现view在开始时是可见的，
     *             结束时是不可见的。Fade（transition的子类）会利用这些信息创建一个用于
     *             把view的alpha属性变为0的AnimatorSet，并且将此AnimatorSet对象返回。
     *             <p>
     *             （5）framework会运行返回的Animator，导致所有的View都渐渐消失。
     *             <p>
     *             <p>
     *             这个简单的例子强调了transition框架的两个主要优点。
     *             第一、Transitions抽象和封装了属性动画，Animator的概念对开发者来说是透明的，
     *             因此它极大的精简了代码量。开发者所做的所有事情只是改变一下view前后的状态数据，
     *             Transition就会自动的根据状态的区别去生成动画效果。
     *             第二、不同场景之间变换的动画效果可以简单的通过使用不同的Transition类来改变
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.red_box, R.id.green_box, R.id.blue_box, R.id.rl_root, R.id.black_box})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.red_box:
                TransitionManager.beginDelayedTransition(rlRoot, new Fade());
                toggleVisibility(redBox, greenBox, blueBox, blackBox);
                break;
            case R.id.green_box:
                TransitionManager.beginDelayedTransition(rlRoot, new Slide());
                toggleVisibility(redBox, greenBox, blueBox, blackBox);
                break;
            case R.id.blue_box:
                TransitionManager.beginDelayedTransition(rlRoot, new Explode());
                toggleVisibility(redBox, greenBox, blueBox, blackBox);
                break;
            case R.id.black_box:
                Intent i = new Intent(this, TransitionsRedActivity.class);
                View sharedView = redBox;
                String transitionBlue = getString(R.string.square_red_name);
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, sharedView, transitionBlue);
                startActivity(i, transitionActivityOptions.toBundle());
                break;
            case R.id.rl_root:
                Pair red = new Pair<>(redBox, ViewCompat.getTransitionName(redBox));
                Pair green = new Pair<>(greenBox, ViewCompat.getTransitionName(greenBox));
                Pair blue = new Pair<>(blueBox, ViewCompat.getTransitionName(blueBox));
                Pair black = new Pair<>(blackBox, ViewCompat.getTransitionName(blackBox));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, red, green, blue, black);
                Intent intent = new Intent(this, TransitionsRedActivity.class);
                ActivityCompat.startActivity(this,
                        intent, optionsCompat.toBundle());
                break;
        }
    }
}
