package com.wyj.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;


import com.wyj.treasure.R;
import com.wyj.treasure.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证码输入框
 */
public class PasswordInputView extends AppCompatEditText {
    static String HAN_YI_LING_HEART_JANE = "font/HanYiLingHeartJane.TTF";
    private final Bitmap passwdBitmap;
    private final Bitmap bitmap;
    private final Context mContext;
    //是否显示明文密码
    private boolean implicitPassword;

    //边框颜色
    private int borderColor;
    //密码框宽度
    int passwdWidth;
    //密码框间隔
    int borderWidth;
    //密码长度
    private int passwordLength = 5;
    //密码字体颜色
    private int passwordColor;
    private float passwordWidth;

    private Paint passwordPaint;
    private Paint borderPaint;
    private int textLength;
    private float mWidth, mHeight;
    //隐式密码的半径
    private float radius = 22;
    private InputListener inputListener;
    private static final String TAG = "PasswordInputView";

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        borderColor = Color.parseColor("#30333A");
        borderWidth = (int) (MyUtils.getScreenWidth(context) * 0.011);

        //密码框背景
        passwdBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_passwd);
        //隐式密码
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.posture_selected);
        setMaxLines(passwordLength);
        passwordColor = Color.WHITE;
        implicitPassword = false;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        //边框颜色
        borderColor = a.getColor(R.styleable.PasswordInputView_pivBorderColor, borderColor);
        //边框宽度
        borderWidth = (int) a.getDimension(R.styleable.PasswordInputView_pivBorderWidth, borderWidth);
        //密码长度
        passwordLength = a.getInt(R.styleable.PasswordInputView_pivPasswordLength, passwordLength);
        //显式密码颜色
        passwordColor = a.getColor(R.styleable.PasswordInputView_pivPasswordColor, passwordColor);
        //是否是隐式密码
        implicitPassword = a.getBoolean(R.styleable.PasswordInputView_pivImplicitPassword, implicitPassword);
        a.recycle();


        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);

        //显示密码paint
        passwordPaint = new Paint();
        passwordPaint.setAntiAlias(true);
        passwordPaint.setTextSize(86);
        passwordPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), HAN_YI_LING_HEART_JANE));
        passwordPaint.setColor(passwordColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        ViewGroup.MarginLayoutParams MarginLayoutParam = (ViewGroup.MarginLayoutParams) layoutParams;
        int leftMargin = MarginLayoutParam.leftMargin;
        int rightMargin = MarginLayoutParam.rightMargin;

        Log.e("leftMargin", "PasswordInputView_105-->onMeasure: " + leftMargin + "  " + rightMargin);
        passwordWidth = (MyUtils.getScreenWidth(mContext) - (leftMargin + rightMargin) - (borderWidth * passwordLength)) / passwordLength;

        int width = (int) ((passwordWidth + borderWidth) * passwordLength);
        int height = (int) (passwordWidth + borderWidth);

//        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(width, height);
//        } else if (widthMode == MeasureSpec.AT_MOST) {// | | 1 | | 2 | |
//            setMeasuredDimension(width, heightSize);
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSize, height);
//        }

        // 当布局参数设置为wrap_content时，设置默认值
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT
                && layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(width, height);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(width, heightSize);
        } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        Log.e(TAG, "PasswordInputView_125-->onDraw: " + paddingBottom);
        canvas.drawColor(borderColor);
        drawPassWdBg(canvas);
        if (implicitPassword) {
            //implicitPassword
            implicitPassword(canvas);
        } else {
            //explicitPassword
            explicitPassword(canvas);
        }

    }

    /**
     * 显式密码
     *
     * @param canvas
     */
    private void explicitPassword(Canvas canvas) {
        String text = String.valueOf(this.getText());
        // 密码在密码框里x轴的位置
        float cx;
        for (int i = 0; i < textLength; i++) {
            float testWidth = passwordPaint.measureText(String.valueOf(text.charAt(i)));
            cx = (passwdWidth + borderWidth) * i + (passwdWidth / 2) - testWidth / 2;
            canvas.drawText(String.valueOf(text.charAt(i)), cx, getBaselineHeight(passwordPaint), passwordPaint);
        }
    }

    /**
     * 隐式密码
     *
     * @param canvas
     */
    private void implicitPassword(Canvas canvas) {
        Rect srcBitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < textLength; i++) {
            float left = centerList.get(i) - radius;
            float top = mHeight / 2 - radius;
            float right = centerList.get(i) + radius;
            float bottom = mHeight / 2 + radius;
            RectF dstRect = new RectF(left, top, right, bottom);
            canvas.drawBitmap(bitmap, srcBitmapRect, dstRect, borderPaint);
        }
    }

    /**
     * 绘制密码框背景
     *
     * @param canvas
     */
    private void drawPassWdBg(Canvas canvas) {
        Rect srcRect = new Rect(0, 0, passwdBitmap.getWidth(), passwdBitmap.getHeight());
        for (int i = 0; i < passwordLength; i++) {
            float left = centerList.get(i) - (passwdWidth / 2);
            float top = mHeight / 2 - (passwdWidth / 2);
            float right = centerList.get(i) + (passwdWidth / 2);
            float bottom = mHeight / 2 + (passwdWidth / 2);
            RectF dstRect = new RectF(left, top, right, bottom);
            canvas.drawBitmap(passwdBitmap, srcRect, dstRect, borderPaint);
        }
    }

    /**
     * 基线
     *
     * @param paint
     * @return
     */
    public int getBaselineHeight(Paint paint) {
        int baseLine;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        baseLine = getHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        return baseLine;
    }

    //设置每个密码的中心点
    private List<Integer> centerList;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        centerList = new ArrayList<>();
        passwdWidth = w / passwordLength - borderWidth;
        for (int i = 0; i < passwordLength; i++) {
            centerList.add((w / passwordLength) * i + (passwdWidth + borderWidth) / 2);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
        if (inputListener != null) {
            inputListener.onTextChanged(text);
            if (text.toString().length() == passwordLength) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputListener.onInputFinish(text.toString());
                    }
                }, 500);

            }
        }
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setInputListener(InputListener listener) {
        this.inputListener = listener;

    }

    public interface InputListener {
        void onTextChanged(CharSequence text);

        void onInputFinish(String text);

    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

}