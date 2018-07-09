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

public class RemoteWaveView extends View {
    private Paint mPaint;

    private int mHeight;
    private int mWidth;

    private int pointSize = 200;
    private float[] shakeRatioArray = {1f, 1.2f, 1.3f};
    private float[] waveOffset = {0f, 2f, 4f};
    Map<Integer, List<Point>> lines;
    private Canvas mCanvas;

    public RemoteWaveView(Context context) {
        this(context, null);
    }

    public RemoteWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemoteWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lines = new HashMap<>();
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(150);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(6);
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
        mCanvas = canvas;
        canvas.translate(0, mHeight * 0.4f);
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
                double fx = Math.sin(rad + offset) - shakeRatioArray[j];
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

        List<Path> paths = new ArrayList<>();
        for (Integer key : lines.keySet()) {
            List<Point> points = lines.get(key);

            Path path = new Path();
            path.moveTo(0, mHeight);
            for (int i = 1; i < pointSize; i++) {
                Point p = points.get(i);
                path.lineTo(p.x, p.y);
            }
            path.lineTo(mWidth, mHeight);
            path.lineTo(0, mHeight);
            path.close();
            paths.add(path);

            for (int i = 0; i < paths.size(); i++) {
                LinearGradient shader = new LinearGradient(0, mHeight, 0, 0,
                        Color.parseColor("#ff741A97"),
                        Color.parseColor("#80911CE6"),
                        Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                mPaint.setAntiAlias(true);
                canvas.drawPath(paths.get(i), mPaint);
            }
        }
    }

    public void start() {
        handler.sendEmptyMessage(1);
    }

    public void stop() {
        handler.removeMessages(1);
        mFactor = 0.1f;
    }

    private float mFactor = 0.1f;

    public void setmFactor(float factor) {
        mFactor = factor == 0 ? (mFactor = 0.1f) : (mFactor = factor);
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