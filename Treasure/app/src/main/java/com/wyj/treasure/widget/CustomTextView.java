package com.vtrump.masterkegel.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 可根据字符串所占的长度（非字符串长度）自动缩小字体大小， 以适应显示区域的宽度
 *
 * @author sheng
 */
public class CustomTextView extends TextView {

    // Attributes
    private Paint testPaint;
    private float cTextSize;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        testPaint = new Paint();
        testPaint.set(this.getPaint());
        cTextSize = getTextSize();// 这个返回的单位为px
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     * 在此方法中学习到：getTextSize返回值是以像素(px)为单位的，而setTextSize()是以sp为单位的，
     * 因此要这样设置setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     */
    private void refitText(String text, float textWidth) {
        Log.e("refit", "refit:" + text + "width:" + textWidth);
        if (textWidth > 0) {
            // 获得当前TextView的有效宽度
            float availableWidth = textWidth - getPaddingLeft()
                    - getPaddingRight();
            testPaint.setTextSize(cTextSize);
            // 所有字符串所占像素宽度
            float textWidths = testPaint.measureText(text);
            while (textWidths > availableWidth) {
                cTextSize--;
                testPaint.setTextSize(cTextSize);// 这里传入的单位是px
                textWidths = testPaint.measureText(text);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, cTextSize);// 这里制定传入的单位是px
            invalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        refitText(getText().toString(),this.getWidth());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        refitText(text.toString(), getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refitText(getText().toString(), getWidth());
    }
}
