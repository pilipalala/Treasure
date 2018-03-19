package com.wyj.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wyj.treasure.utils.LogUtil;

/**
 * Created by wangyujie
 * Date 2018/3/17
 * Time 22:19
 * TODO
 */

public class MyTextView extends TextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("MyButton ---onTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("MyButton ---onTouchEvent---抬起");
                break;

        }


        return super.onTouchEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("MyButton ---dispatchTouchEvent---按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("MyButton ---dispatchTouchEvent---抬起");
                break;

        }
        return super.dispatchTouchEvent(event);

    }
}
