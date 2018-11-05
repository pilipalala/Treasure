package com.wyj.touch;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class TouchDemoActivity extends BaseActivity {

    @BindView(R.id.button1)
    MyTextView button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.my_layout)
    LinearLayout myLayout;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_touch_demo;
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("TouchDemoActivity ---dispatchTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("TouchDemoActivity ---dispatchTouchEvent---抬起");
                break;

        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("TouchDemoActivity ---dispatchTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("TouchDemoActivity ---dispatchTouchEvent---抬起");
                break;

        }
        return super.onTouchEvent(event);

    }

    @OnClick({R.id.button1, R.id.button2, R.id.my_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                LogUtil.v("点击了button1");
                break;
            case R.id.button2:
                LogUtil.v("点击了button2");
                break;
            case R.id.my_layout:
                LogUtil.v("点击了ViewGroup");
                break;
        }
    }
}
