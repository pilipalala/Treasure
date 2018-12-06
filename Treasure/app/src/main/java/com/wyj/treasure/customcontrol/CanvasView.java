package com.wyj.treasure.customcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 * @date 2018/11/21.18:32
 * @describe 添加描述
 */
public class CanvasView extends View {

    private int mWidth, mHeight;
    private Paint mPaint = new Paint();

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        //抗锯齿
        mPaint.setAntiAlias(true);
        //填充颜色
        mPaint.setColor(Color.RED);
        //填充模式
        // Style.FILL //填充
        // Style.FILL_AND_STROKE //描边加填充
        // Style.STROKE //描边
        mPaint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        //scale
//        drawOne(canvas);
//        drawOneOther(canvas);

        //rotate
        drawTwo(canvas);
    }

    private void drawOne(Canvas canvas) {
        RectF rect = new RectF(-300, -300, 300, 300);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        for (int i = 0; i <= 20; i++) {
            canvas.scale(0.9f, 0.9f);
            canvas.drawRect(rect, mPaint);
        }
    }
    private void drawOneOther(Canvas canvas) {
        RectF rect = new RectF(-0, -300, 300, 0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect, mPaint);
//        canvas.scale(0.5f,0.5f);          // 画布缩放
//        canvas.scale(0.5f, 0.5f, 50, 0);  //画布缩放  <-- 缩放中心向右偏移了50个单位

        //当缩放比例为负数的时候会根据缩放中心轴(x轴y轴)进行翻转
        canvas.scale(-0.5f,-0.5f);          // 画布缩放

        mPaint.setColor(Color.BLUE);          // 绘制蓝色矩形
        canvas.drawRect(rect, mPaint);
    }
    private void drawTwo(Canvas canvas) {
        canvas.drawCircle(0,0,400,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,mPaint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,mPaint);
            canvas.rotate(10);
        }
    }
}
