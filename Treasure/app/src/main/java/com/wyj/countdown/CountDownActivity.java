package com.wyj.countdown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountDownActivity extends BaseActivity {

    @BindView(R.id.btn_runnable)
    Button btnRunnable;
    @BindView(R.id.btn_countDownButton)
    CountDownTimerButton btnCountDownButton;
    @BindView(R.id.btn_countDownTimer)
    Button btnCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_runnable, R.id.btn_countDownButton, R.id.btn_countDownTimer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_runnable:
                startActivity(new Intent(CountDownActivity.this, CountDownRunnableActivity.class));
                break;
            case R.id.btn_countDownButton:
                Toast.makeText(CountDownActivity.this, "倒计时开始", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_countDownTimer:
                startActivity(new Intent(CountDownActivity.this, CountDownTimerActivity.class));
                break;
        }
    }
}
