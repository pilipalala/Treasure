package com.wyj.materialdesign.recyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.StatusBarUtil;
import com.wyj.treasure.widget.ObservaleScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HeadimageScaleActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.scrollView)
    ObservaleScrollView scrollView;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    // 记录首次按下位置
    private float mFirstPosition = 0;
    // 是否正在放大
    private Boolean mScaling = false;

    private DisplayMetrics metric;

    /*image设置android:layout_gravity="center"
                    android:scaleType="centerCrop"*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        StatusBarUtil.setImgTransparent(this);
        setContentView(R.layout.activity_headimage_scale);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 设置图片初始大小 这里我设为满屏的16:9
        ViewGroup.LayoutParams lp = image.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 9 / 16;
        image.setLayoutParams(lp);
        llRoot.getBackground().mutate().setAlpha(0);
        scrollView.setScrollViewListener(new ObservaleScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservaleScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y <= image.getHeight() && y >= 0) {

                    float scale = (float) y / image.getHeight();
                    float alpha = (255 * scale);
                    text.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                    llRoot.getBackground().mutate().setAlpha((int) alpha);
                } else if (y > image.getHeight()) {
                    llRoot.getBackground().mutate().setAlpha(255);
                }
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ViewGroup.LayoutParams lp = image
                        .getLayoutParams();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 手指离开后恢复图片
                        mScaling = false;
                        replyImage();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mScaling) {
                            if (scrollView.getScrollY() == 0) {
                                mFirstPosition = motionEvent.getY();// 滚动到顶部时记录位置，否则正常返回
                            } else {
                                break;
                            }
                        }
                        int distance = (int) ((motionEvent.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                        if (distance < 0) { // 当前位置比记录位置要小，正常返回
                            break;
                        }
                        // 处理放大
                        mScaling = true;
                        lp.width = metric.widthPixels + distance;
                        lp.height = (metric.widthPixels + distance) * 9 / 16;
                        image.setLayoutParams(lp);
                        return true; // 返回true表示已经完成触摸事件，不再处理
                }
                return false;
            }
        });
    }

    // 回弹动画 (使用了属性动画)
    public void replyImage() {
        final ViewGroup.LayoutParams lp = image
                .getLayoutParams();
        final float w = image.getLayoutParams().width;// 图片当前宽度
        final float h = image.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 9 / 16;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F)
                .setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                image.setLayoutParams(lp);
            }
        });
        anim.start();
    }
}
