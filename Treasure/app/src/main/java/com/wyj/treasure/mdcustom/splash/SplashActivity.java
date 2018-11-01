package com.wyj.treasure.mdcustom.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashView.OnAnimatorListener {
    Handler handler = new Handler();
    private FrameLayout frameLayout;
    private long STAITTIME = 2000;


    /*动画特效---加载过渡特效*/
    private SplashView splashView;
    private ContentView contentView;

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
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        frameLayout = new FrameLayout(this);
        contentView = new ContentView(this);
        splashView = new SplashView(this);
        frameLayout.setBackgroundResource(R.color.white);
        frameLayout.addView(contentView);
        frameLayout.addView(splashView);
        setContentView(frameLayout);

        //开启加载数据---数据加载前,开启动画
        startSplashDataLoad();
    }

    private void startSplashDataLoad() {
        splashView.setOnAnimatorListener(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //完毕后进入进场动画---动画1结束，动画23开启
                splashView.splashDisappear();
            }
        }, STAITTIME);

    }

    @Override
    public void onAnimationEnd() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
