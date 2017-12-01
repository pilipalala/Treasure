package com.wyj.treasure.viewcustom.colortracktextview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorTrackActivity extends BaseActivity {


    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.color_text)
    ColorTrackTextView colorText;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_color_track);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_right, R.id.tv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                colorText.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                ValueAnimator rightAnimator = ObjectAnimator.ofFloat(0, 1f).setDuration(2000);
                rightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        colorText.setCurrentProgress((float) animation.getAnimatedValue());
                    }
                });
                rightAnimator.start();
                break;
            case R.id.tv_left:
                colorText.setDirection(ColorTrackTextView.Direction.LEFE_TO_RIGHT);
                ValueAnimator leftAnimator = ObjectAnimator.ofFloat(0, 1f).setDuration(2000);
                leftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        colorText.setCurrentProgress((float) animation.getAnimatedValue());
                    }
                });
                leftAnimator.start();
                break;
        }
    }
}
