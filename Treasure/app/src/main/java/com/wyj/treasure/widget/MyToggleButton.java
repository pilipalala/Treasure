package com.wyj.treasure.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wyj.treasure.R;


/**
 * Created by wangyujiew .
 * Data 2017/5/14M
 * Time 10:03
 * TODO 自定义开关
 */

public class MyToggleButton extends View implements View.OnClickListener {
    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;
    private int slidLeftMax;
    private Paint paint;
    private boolean isOpen = false;
    private int slideLeft;
    private float startX;
    private float lastX;
    /**
     * true:点击事件生效，滑动事件不生效
     * false:点击事件不生效，滑动事件生效
     */
    private boolean isEnableClick = true;
    private OnChangeListener listener;

    /**
     * 1、构造方法实例化
     * 2、测量-measure(int,int)-->onMeasure()
     * 如果当前view是一个viewgroup，还有义务测量孩子   孩子有建议权
     * 3、指定位置layout()-->onLayout();
     * 指定控件的位置，一般view不用写这个方法，
     * ViewGroup的时候才需要， 一般的view不需要重写该方法
     * 4、绘制视图 draw()-->onDraw(canvas)
     */
    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);/*设置抗锯齿*/
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);
        slidLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();
        setOnClickListener(this);


    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;

    }

    /**
     * 视图测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, paint);
    }

    @Override
    public void onClick(View view) {
        if (isEnableClick) {
            isOpen = !isOpen;
            flushView();
        }
    }

    private void flushView() {
        if (isOpen) {
            slideLeft = slidLeftMax;
            listener.OnChangeListener(true);
        } else {
            slideLeft = 0;
            listener.OnChangeListener(false);
        }
        invalidate();/*会导致onDraw()执行*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /**
                 * 1、记录按下的坐标
                 * */
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_UP:
                if (!isEnableClick) {
                    //显示按钮开
                    isOpen = slideLeft > slidLeftMax / 2;
                    flushView();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 2、计算结束值
                 * */
                float endX = event.getX();
                /**
                 * 3、计算偏移量
                 * */
                float distance = endX - startX;

                slideLeft += distance;

                /*
                * 4、屏蔽非法值
                * */
                if (slideLeft < 0) {
                    slideLeft = 0;
                } else if (slideLeft > slidLeftMax) {
                    slideLeft = slidLeftMax;
                }
                /**
                 * 5、刷新
                 * */
                invalidate();
                /**
                 * 6数据还原
                 * */
                startX = event.getX();
                if (Math.abs(lastX - startX) > 5) {
                    isEnableClick = false;
                }
                break;

        }
        return true;

    }

    public interface OnChangeListener {
        void OnChangeListener(boolean isOpen);
    }
}
