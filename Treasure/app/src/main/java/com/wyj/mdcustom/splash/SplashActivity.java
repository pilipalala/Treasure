package com.wyj.mdcustom.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();
    private FrameLayout frameLayout;
    private long STAITTIME = 5000;


    /*动画特效---加载过渡特效*/
    private SplashView splashView;

    /**
     * 自定义控件：
     * 1.自汇控件--重写onDraw){}
     * 2.继承控件；3.组合控件
     * <p>
     * <p>
     * 3个动画 分别是
     * 小圆旋转的动画
     * 聚合的动画
     * 空心圆的扩散效果，显示主界面
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout = new FrameLayout(this);
        ContentView contentView = new ContentView(this);
        frameLayout.addView(contentView);
        splashView = new SplashView(this);
        frameLayout.addView(splashView);
        setContentView(frameLayout);

        //开启加载数据---数据加载前,开启动画
        startSpashDataLoad();
    }

    private void startSpashDataLoad() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //完毕后进入进场动画---动画1结束，动画23开启
                splashView.splashDisappear();
            }
        }, STAITTIME);

    }
}
