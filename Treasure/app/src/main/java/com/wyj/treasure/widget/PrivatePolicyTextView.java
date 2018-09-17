package com.wyj.treasure.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wyj.treasure.R;

/**
 * @author wangyujie
 * @date 2018/9/6.14:42
 * @describe 带下划线的 TextView
 */
public class PrivatePolicyTextView extends View {

    private static final String TAG = "PrivatePolicyTextView";

    private String str1;
    private String str2;
    private int color1;
    private int color2;
    private float size = 40;
    private Rect rect;
    private Paint paint;
    private float str1Width, str2Width;

    private onPrivatePolicyClickListener listener;

    public interface onPrivatePolicyClickListener {
        void onClick();
    }

    public void setOnPrivatePolocyClickListener(onPrivatePolicyClickListener listener) {
        this.listener = listener;
    }

    public PrivatePolicyTextView(Context context) {
        this(context, null);
    }

    public PrivatePolicyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrivatePolicyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PrivatePolicyTextView,
                defStyleAttr, 0);
        try {
            str1 = ta.getString(R.styleable.PrivatePolicyTextView_str1);
            str2 = ta.getString(R.styleable.PrivatePolicyTextView_str2);
            color1 = ta.getColor(R.styleable.PrivatePolicyTextView_color1, Color.GRAY);
            color2 = ta.getColor(R.styleable.PrivatePolicyTextView_color2, Color.GRAY);
            size = ta.getDimension(R.styleable.PrivatePolicyTextView_fontSize, size);
        } finally {
            ta.recycle();
        }
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(size);
        paint.setColor(color1);
        rect = new Rect();
        paint.getTextBounds(str1 + str2, 0, (str1 + str2).length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = paint.measureText(str1 + str2);
        while (width > getWidth()) {
            size--;
            paint.setTextSize(size);
            width = paint.measureText(str1 + str2);
        }
        paint.setColor(color1);
        paint.setTextSize(size);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawText(str1, 0, getHeight() / 2 + rect.height() / 2, paint);
        str1Width = paint.measureText(str1);

        if (!TextUtils.isEmpty(str2)) {
            paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            paint.setAntiAlias(true);
            paint.setColor(color2);
            canvas.drawText(str2, str1Width, getHeight() / 2 + rect.height() / 2, paint);
            str2Width = paint.measureText(str2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() >= str1Width && event.getX() <= (str2Width + str1Width)
                && event.getAction() == MotionEvent.ACTION_UP
                && listener != null) {
            listener.onClick();
        }
        return true;
    }
}
