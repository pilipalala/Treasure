package com.wyj.treasure.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wyj.treasure.R;

/**
 * Created by admin
 * on 2017/8/31.
 * TODO 圆形图片
 * 注意：CircleImageView的默认ScaleType为CENTER_CROP，且只能为CENTER_CROP。
 */

public class CircleImageView extends ImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private final Paint mFillPaint = new Paint();

    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private int mFillColor = DEFAULT_FILL_COLOR;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    private float mBorderRadius;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;
    private boolean mBorderOverlay;
    private boolean mDisableCircularTransformation;

    public CircleImageView(Context context) {
        super(context);

        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);

        mBorderWidth =
                a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR);
        mBorderOverlay =
                a.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY);
        mFillColor = a.getColor(R.styleable.CircleImageView_civ_fill_color, DEFAULT_FILL_COLOR);

        a.recycle();

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    /**
     * 在onDraw()方法中，使用setup()中设置好的各项参数，完成图片、底色和边框的绘制。到这里，流程执行结束。
     * @param canvas
     */
    @Override protected void onDraw(Canvas canvas) {
        // 如果禁止显示圆形（setDisableCircularTransformation()方法可设置），则不往下执行。
        if (mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }
        // 未设置图片，不往下执行
        if (mBitmap == null) {
            return;
        }
        // 如果底色不是透明，绘制底色
        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius,
                    mFillPaint);
        }
        // 使用上面设置好的mDrawableRect、mDrawableRadius、mBitmapPaint，绘制圆形图片，即内圆
        canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius,
                mBitmapPaint);
        // 如果边框宽度大于0，使用上面设置好的mBorderRect、mBorderRadius、mBorderPaint，绘制圆形边框，即外圆
        if (mBorderWidth > 0) {
            canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint);
        }
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    @Override public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setup();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }

        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    /**
     * @deprecated Use {@link #setBorderColor(int)} instead
     */
    @Deprecated public void setBorderColorResource(@ColorRes int borderColorRes) {
        setBorderColor(getContext().getResources().getColor(borderColorRes));
    }

    /**
     * Return the color drawn behind the circle-shaped drawable.
     *
     * @return The color drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated public int getFillColor() {
        return mFillColor;
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColor The color to be drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated public void setFillColor(@ColorInt int fillColor) {
        if (fillColor == mFillColor) {
            return;
        }

        mFillColor = fillColor;
        mFillPaint.setColor(fillColor);
        invalidate();
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     * drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated public void setFillColorResource(@ColorRes int fillColorRes) {
        setFillColor(getContext().getResources().getColor(fillColorRes));
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }

        mBorderWidth = borderWidth;
        setup();
    }

    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == mBorderOverlay) {
            return;
        }

        mBorderOverlay = borderOverlay;
        setup();
    }

    public boolean isDisableCircularTransformation() {
        return mDisableCircularTransformation;
    }

    public void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (mDisableCircularTransformation == disableCircularTransformation) {
            return;
        }

        mDisableCircularTransformation = disableCircularTransformation;
        initializeBitmap();
    }

    @Override public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }

    @Override public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    @Override public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }

        mColorFilter = cf;
        applyColorFilter();
        invalidate();
    }

    @Override public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    private void applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.setColorFilter(mColorFilter);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap =
                        Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                        BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initializeBitmap() {
        if (mDisableCircularTransformation) {
            mBitmap = null;
        } else {
            mBitmap = getBitmapFromDrawable(getDrawable());
        }
        setup();
    }

    /**
     * CircleImageView的核心。
     */
    private void setup() {
        // mReady和mSetupPending属性，在构造方法和setup()方法都有引用和赋值
        // 目的是确保setup()内部逻辑的执行是在构造方法执行完毕之后，即获取到自定义属性参数之后
        if (!mReady) {
            mSetupPending = true;
            return;
        }
        // 图片宽高为0，不往下执行
        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }
        // 只有传入了图片，才会往下执行
        if (mBitmap == null) {
            invalidate();
            return;
        }
        // BitmapShader类用来渲染头像
        // 参数1：要处理的bitmap对象，参数2和3：指定模式为对图片的边缘进行拉伸
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 图片画笔：设置抗锯齿
        mBitmapPaint.setAntiAlias(true);
        // 图片画笔：设置渲染器
        mBitmapPaint.setShader(mBitmapShader);

        // 边框画笔：设置样式为描边
        mBorderPaint.setStyle(Paint.Style.STROKE);
        // 边框画笔：设置抗锯齿
        mBorderPaint.setAntiAlias(true);
        // 边框画笔：设置颜色值
        mBorderPaint.setColor(mBorderColor);
        // 边框画笔：设置宽度
        mBorderPaint.setStrokeWidth(mBorderWidth);

        // 底色画笔：设置样式为填充
        mFillPaint.setStyle(Paint.Style.FILL);
        // 底色画笔：设置抗锯齿
        mFillPaint.setAntiAlias(true);
        // 底色画笔：设置颜色
        mFillPaint.setColor(mFillColor);

        // 获取原图的高度
        mBitmapHeight = mBitmap.getHeight();
        // 获取原图的宽度
        mBitmapWidth = mBitmap.getWidth();

        // 设置边框的显示区域
        mBorderRect.set(calculateBounds());
        // 计算边框的半径
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2.0f,
                (mBorderRect.width() - mBorderWidth) / 2.0f);

        // 设置图片的显示区域为上面计算出来的mBorderRect
        mDrawableRect.set(mBorderRect);
        // 如果边框不是覆盖在图片之上，并且边框宽度大于0，扩大图片的显示区域，增加mBorderWidth-1
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f);
        }
        // 计算图片的半径
        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f);
        // 该方法内部只有一行代码，mBitmapPaint.setColorFilter(mColorFilter)，给bitmap添加滤镜
        applyColorFilter();
        updateShaderMatrix();

        invalidate();/*触发View的onDraw()方法*/
    }

    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    /**
     * 该方法的作用是设置mBitmapShader变量的mShaderMatrix参数，
     * 对图片进行缩放setScale()和平移postTranslate()，
     * 使图片的显示区域缩放到与mDrawableRect一致，并通过平移确保显示图片的中心位置。
     */
    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        // 这里取最小的缩放比例，以尽量保证图片的质量
        // 判断如果宽度的比例大于高度的比例，取高度的缩放比，平移x轴方向
        // 否则取宽度的缩放比，平移y轴方向
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }
        // 设置mShaderMatrix：缩放
        mShaderMatrix.setScale(scale, scale);
        // 设置mShaderMatrix：平移
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left,
                (int) (dy + 0.5f) + mDrawableRect.top);
        // 将mShaderMatrix设置到mBitmapShader
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
}
