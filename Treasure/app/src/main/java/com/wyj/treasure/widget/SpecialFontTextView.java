package com.wyj.treasure.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.wyj.treasure.R;


/**
 * @author wangyujie
 * @date 2018/9/1.14:28
 * @describe 特殊字体工具类
 */

public class SpecialFontTextView extends AppCompatTextView {

    static String HAN_YI_LING_HEART_JANE = "font/HanYiLingHeartJane.TTF";
    private static Typeface tf;

    public SpecialFontTextView(Context context) {
        this(context, null);
    }

    public SpecialFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = null;
        int font = 0;
        boolean isAutoScroll;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.SpecialFontTextView, defStyleAttr, 0);
            font = ta.getInteger(R.styleable.SpecialFontTextView_special_font, 0);
            isAutoScroll = ta.getBoolean(R.styleable.SpecialFontTextView_isAutoScroll, false);
        } finally {
            ta.recycle();
        }
        setTypeface(setFont(context, font));
        if (isAutoScroll) {
            setAutoScroll();
        }
    }
    /***
     * 设置字体
     *
     * @return
     */
    public static Typeface setFont(Context context, int font) {
        if (tf == null) {
            switch (font) {
                case 0:
                    tf = Typeface.createFromAsset(context.getAssets(), HAN_YI_LING_HEART_JANE);
                    break;
            }
        }
        return tf;
    }
    private void setAutoScroll() {
        //设置单行
        setSingleLine();
        //设置Ellipsize
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //获取焦点
        setFocusable(true);
        //走马灯的重复次数，-1代表无限重复
        setMarqueeRepeatLimit(-1);
        //强制获得焦点
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
