package com.wyj.treasure.mdcustom.behaviorcustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * on 2017/10/31.12:52
 * TODO
 */
@CoordinatorLayout.DefaultBehavior(SlidingCardBehavior.class)
public class SlidingCardLayout extends FrameLayout {

    private int mHraderViewHeight;

    public SlidingCardLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_card, this);
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        TextView hrader = (TextView) findViewById(R.id.header);
        SimpleAdapter adapter = new SimpleAdapter(list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingCardLayout, defStyleAttr, 0);
        hrader.setBackgroundColor(array.getColor(R.styleable.SlidingCardLayout_android_colorBackground, Color.BLACK));
        hrader.setText(array.getText(R.styleable.SlidingCardLayout_android_text));
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mHraderViewHeight = findViewById(R.id.header).getMeasuredHeight();
        }
    }
    public int getHeaderHeight() {
        return mHraderViewHeight;
    }
}
