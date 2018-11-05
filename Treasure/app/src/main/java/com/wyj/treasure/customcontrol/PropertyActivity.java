package com.wyj.treasure.customcontrol;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertyActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.mypointview)
    MyPointView mypointview;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_property;
    }

    @Override
    protected void initData() {
       setTitle("PropertyValuesHolder与Keyframe");

    }

    @OnClick({R.id.bt_start, R.id.bt_start_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -15f, 15f, 10f, -10f, 0f);
                PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("backgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);

                ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(tv, rotation, colorHolder);
                animator1.setDuration(3000);
                animator1.setInterpolator(new AccelerateInterpolator());
                animator1.start();
                break;
            case R.id.bt_start_point:
//                rotation();
                startAnim();

                break;
        }
    }

    private void startAnim() {
        /**
         * 左右震动效果
         */
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -15f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 15f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -15f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 15f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -15f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 15f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -15f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 15f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -15f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder1 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);


        /**
         * scaleX放大1.2倍
         */
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.2f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.2f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.2f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.2f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.2f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.2f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.2f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.2f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.2f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX", scaleXframe0, scaleXframe1, scaleXframe2, scaleXframe3, scaleXframe4, scaleXframe5, scaleXframe6, scaleXframe7, scaleXframe8, scaleXframe9, scaleXframe10);


        /**
         * scaleY放大1.2倍
         */
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.2f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.2f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.2f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.2f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.2f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.2f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.2f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.2f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.2f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY", scaleYframe0, scaleYframe1, scaleYframe2, scaleYframe3, scaleYframe4, scaleYframe5, scaleYframe6, scaleYframe7, scaleYframe8, scaleYframe9, scaleYframe10);

        /**
         * 构建动画
         */
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(tv, frameHolder2, frameHolder3);
        animator.setDuration(300);
        animator.start();
    }

    private void rotation() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -15f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 15f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -15f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 15f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -15f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 15f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -15f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 15f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -15f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(tv, frameHolder);
        animator.setDuration(1000);
        animator.start();
    }
}
