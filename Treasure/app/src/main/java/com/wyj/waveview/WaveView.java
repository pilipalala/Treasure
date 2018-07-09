package com.wyj.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveView extends View {
    private Paint mPaint;
    private Paint mPaint2;

    private int mHeight;
    private int mWidth;

    private int pointSize = 200;
    private float[] shakeRatioArray = {0.2f, -0.2f};
    private float[] waveOffset = {0f, 1.5f};
    Map<Integer, List<Point>> lines;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lines = new HashMap<>();
        mPaint = new Paint();
        mPaint2 = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#6495ED"));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(10);

        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(Color.parseColor("#33333333"));
        mPaint2.setAntiAlias(true);
        mPaint2.setDither(true);
        mPaint2.setStrokeWidth(16);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, mHeight / 2);
        initDraw();
        drawLine(canvas);
    }

    private float offset = 0.2f;

    private void initDraw() {
        lines.clear();
        offset += 0.15f;
        float dx = (float) mWidth / (pointSize - 1);// 必须为float，否则丢失

        for (int j = 0; j < shakeRatioArray.length; j++) {
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < pointSize; i++) {
                float x = dx * i;
                double rad = Math.toRadians(x) + waveOffset[j];
                double fx = Math.sin(rad + offset) + shakeRatioArray[j] * mFactor;
                float y = (float) (fx * getA(i) * mFactor);
                points.add(new Point(x, y));
            }
            lines.put(j, points);
        }
    }

    private float getA(int i) {
        float A;
        if (i < pointSize / 2) {
            A = i;
        } else {
            A = pointSize - i;
        }
        return A;
    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
//        canvas.drawColor(Color.parseColor("#000000"));

        List<Path> paths = new ArrayList<>();
        for (Integer key : lines.keySet()) {
            List<Point> points = lines.get(key);

            Path path = new Path();
            path.moveTo(0, 0);
            for (int i = 1; i < pointSize; i++) {
                Point p = points.get(i);
                path.lineTo(p.x, p.y);
                Point p0 = new Point(lines.get(0).get(i).x, lines.get(0).get(i).y);
                Point p1 = new Point(lines.get(1).get(i).x, lines.get(1).get(i).y);
                canvas.drawLine(p0.x, p0.y, p1.x, p1.y, mPaint2);
            }
            paths.add(path);

            for (int i = 0; i < paths.size(); i++) {
                mPaint.setAntiAlias(true);
                LinearGradient shader = new LinearGradient(mWidth, 0, 0, 0,
                        Color.parseColor("#ff6495ED"),
                        Color.parseColor("#808A2BE2"),
                        Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                canvas.drawPath(paths.get(i), mPaint);
            }
        }
    }

    public void start() {
        handler.sendEmptyMessage(1);
    }

    private float mFactor = 0.1f;

    public void setmFactor(float factor) {
        mFactor = factor == 0 ? (mFactor = 0.1f) : (mFactor = factor);
    }

    public void setStop() {
        this.mFactor = 0.1f;
        handler.removeMessages(1);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                postInvalidate();
                handler.sendEmptyMessageDelayed(1, 16);
            }
        }
    };

    class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}