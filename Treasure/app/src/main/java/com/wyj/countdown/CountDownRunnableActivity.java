package com.wyj.countdown;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountDownRunnableActivity extends BaseActivity {

    @BindView(R.id.timeBtn)
    Button timeBtn;
    private int time = 10;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_runnable);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.timeBtn)
    public void onClick() {
        new Thread(new MyThreadTimer()).start();
    }

    /**
     * 自定义倒计时类，实现Runnable接口
     */
    private class MyThreadTimer implements Runnable {
        @Override
        public void run() {
            while (time > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeBtn.setClickable(false);
                        timeBtn.setText(time+"秒");
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time--;
            }
            //倒计时结束，循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    timeBtn.setClickable(true);
                    timeBtn.setText("倒计时开始");
                }
            });
            time = 10;//最后再恢复倒计时时长  
        }
    }
}
