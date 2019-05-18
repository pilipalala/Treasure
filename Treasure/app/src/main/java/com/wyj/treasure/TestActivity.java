package com.wyj.treasure;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.switchCompat)
    SwitchCompat mSwitchCompat;
    @BindView(R.id.test_view)
    TextView mTestView;
    @BindView(R.id.text_switcher)
    TextSwitcher mTextSwitcher;

    private String[] titles = {"越狱", "尼基塔", "黑吃黑", "权力的游戏", "迷失"};
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mSwitchCompat.setChecked(true);
        mSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.setChecked(isChecked);
            Log.e("TestActivity", "-->onCheckedChanged: " + isChecked);
        });
        /**
         * anim文件夹
         * anim文件夹下存放tween animation和frame animation
         * xml文件里只有scale、rotate、translate、alpha、set五个标签
         * 在代码中使用AnimationUtils.loadAnimation（）方法加载
         * 使用View.setAnimation(Animation)为View加载动画，使用View.startAnimation()开启动画
         * animator文件夹
         * animator文件夹下存放property animation，即属性动画
         * xml文件里有animator、objectAnimator和set三个标签
         * 在代码中使用AnimatorInflater.loadAnimator（）方法加载动画
         * 使用Animator.setTarget（View）为View加载动画，使用Animator.start（）开启动画
         * */
//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scalex);
//        animator.setTarget(mTestView);
//        animator.start();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_bottom_in);
        mTestView.startAnimation(animation);
        animation.startNow();


        mTextSwitcher.setFactory(() -> {
            TextView textView = new TextView(TestActivity.this);
            textView.setTextSize(20);
            textView.setTextColor(Color.parseColor("#ff3333"));
            textView.setSingleLine();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            textView.setLayoutParams(layoutParams);
            return textView;
        });

        mTextSwitcher.setOnClickListener(v -> ToastUtil.show(titles[index % titles.length]));

        mHandler.sendEmptyMessage(1);
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                index++;
                mTextSwitcher.setText(titles[index % titles.length]);
                mHandler.sendEmptyMessageDelayed(1, 3000);
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        mHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void haha(View view) {
        new ParticleSystem(this, 20, R.mipmap.collect, 10000)
                .setSpeedByComponentsRange(-0.1f, 0.1f, -0.1f, 0.02f)
                .setAcceleration(0.000003f, 180)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 360)
                .setInitialRotationRange(0, 360)
                .setRotationSpeed(60)
                .setScaleRange(0.5f, 3.0f)
                .addModifier(new AlphaModifier(255, 125, 1000, 3000))
                .setFadeOut(5000, new BounceInterpolator())
                .oneShot(view, 20);
    }
}
