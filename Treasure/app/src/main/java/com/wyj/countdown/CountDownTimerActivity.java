package com.wyj.countdown;

import android.os.CountDownTimer;
import android.widget.Button;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class CountDownTimerActivity extends BaseActivity {

    @BindView(R.id.timeBtn)
    Button timeBtn;
    private CountDownTime mTime;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_count_down_timer;
    }


    @Override
    protected void initData() {
        mTime = new CountDownTime(10000, 1000);//初始化对象
    }

    @OnClick(R.id.timeBtn)
    public void onClick() {
        mTime.start(); //开始计时
    }

    private class CountDownTime extends CountDownTimer {

        /**
         * 构造函数  单位都是毫秒
         *
         * @param millisInFuture    总的计时时长
         * @param countDownInterval 代表计时间隔
         */
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * //每计时一次回调一次该方法
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            timeBtn.setClickable(false);
            timeBtn.setText(millisUntilFinished / 1000 + "秒后重新开始");
        }

        /**
         * 计时结束回调该方法
         */
        @Override
        public void onFinish() {
            timeBtn.setClickable(true);
            timeBtn.setText("倒计时开始");
        }
    }
}
