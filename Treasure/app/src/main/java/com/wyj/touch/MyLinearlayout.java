package com.wyj.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.wyj.treasure.utils.LogUtil;

/**
 * Created by wangyujie
 * Date 2018/3/17
 * Time 22:17
 * TODO
 */

public class MyLinearlayout extends LinearLayout {
    public MyLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("MyLinearlayout ---dispatchTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("MyLinearlayout ---dispatchTouchEvent---抬起");
                break;

        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("MyLinearlayout ---onInterceptTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("MyLinearlayout ---onInterceptTouchEvent---抬起");
                break;

        }
        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("MyLinearlayout ---onTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("MyLinearlayout ---onTouchEvent---抬起");
                break;

        }
        super.onTouchEvent(event);
        return super.onTouchEvent(event);

    }
}
