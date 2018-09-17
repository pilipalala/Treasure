package com.wyj.floatingdialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.wyj.treasure.MyApplication;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.CommonUtils;


/**
 * @author wangyujie
 * @date 2018/8/22.11:57
 * @describe 悬浮移动按钮
 */
public abstract class BaseFloatView extends RelativeLayout {
    private static final String TAG = "BaseFloatView";

    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private final static int TOP = 3;
    private final static int BUTTOM = 4;

    private int dpi;
    private int screenHeight;
    private int screenWidth;
    private WindowManager.LayoutParams wmParams;
    private WindowManager wm;
    private float x, y;
    private float mTouchStartX;
    private float mTouchStartY;
    private float mInterceptStartX;
    private float mInterceptStartY;
    private boolean isScroll;
    public Context mContext;

    public BaseFloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = getContext();
        View view = LayoutInflater.from(context).inflate(getLayoutRes(), this);
        bindView(view, attrs);
        initWm();
    }

    public BaseFloatView(Context context) {
        this(context, null);
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v, AttributeSet attrs);

    private void initWm() {
        wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
        //通过像素密度来设置按钮的大小
        dpi = dpi(dm.densityDpi) * 2;
        //屏宽
        screenWidth = wm.getDefaultDisplay().getWidth();
        //屏高
        screenHeight = wm.getDefaultDisplay().getHeight();
        //布局设置
        wmParams = new WindowManager.LayoutParams();
        wmParams.width = dpi;
        wmParams.height = dpi;
        // 设置window type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置Window flag
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.y = (screenHeight - dpi) >> 2;
        wmParams.x = screenWidth;
        wm.addView(this, wmParams);
        hide();
    }

    /**
     * 根据密度选择控件大小
     */
    private int dpi(int densityDpi) {
        if (densityDpi <= 120) {
            return 36;
        } else if (densityDpi <= 160) {
            return 48;
        } else if (densityDpi <= 240) {
            return 72;
        } else if (densityDpi <= 320) {
            return 96;
        }
        return 108;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getRawX() - mInterceptStartX) <= 2
                        && Math.abs(ev.getRawY() - mInterceptStartY) <= 2) {
                    return false;
                }
                return true;
            case MotionEvent.ACTION_DOWN:
                mInterceptStartX = ev.getRawX();
                mInterceptStartY = ev.getRawY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void show() {
        if (isShown()) {
            return;
        }
        setVisibility(View.VISIBLE);
    }


    public void hide() {
        setVisibility(View.GONE);
    }

    public void destroy() {
        hide();
        wm.removeViewImmediate(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // 获取相对屏幕的坐标， 以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // setBackgroundDrawable(openDrawable);
                // invalidate();
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i((mTouchStartX - event.getX()) + " mTouchStartX");
                LogUtil.i((mTouchStartY - event.getY()) + " mTouchStartY");
                if (isScroll) {
                    updateViewPosition();
                } else {
                    // 当前不处于连续滑动状态 则滑动小于图标3则不滑动
                    if (Math.abs(mTouchStartX - event.getX()) > 3
                            || Math.abs(mTouchStartY - event.getY()) > 3) {
                        updateViewPosition();
                    } else {
                        break;
                    }
                }
                isScroll = true;
                break;
            case MotionEvent.ACTION_UP:
                // 拖动
                if (isScroll) {
                    autoView();
                }
                isScroll = false;
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return true;
    }

    /**
     * 自动移动位置
     */
    private void autoView() {
        // 得到view在屏幕中的位置
        int[] location = new int[2];
        getLocationOnScreen(location);
        //左侧
        if (location[0] < screenWidth / 2 - getWidth() / 2) {
            updateViewPosition(LEFT);
        } else {
            updateViewPosition(RIGHT);
        }
    }

    /**
     * 手指释放更新悬浮窗位置
     */
    private void updateViewPosition(int l) {
        switch (l) {
            case LEFT:
                wmParams.x = 0;
                break;
            case RIGHT:
                int x = screenWidth - dpi;
                wmParams.x = x;
                break;
            case TOP:
                wmParams.y = 0;
                break;
            case BUTTOM:
                wmParams.y = screenHeight - dpi;
                break;
        }
        wm.updateViewLayout(this, wmParams);
    }

    // 更新浮动窗口位置参数
    private void updateViewPosition() {
        wmParams.x = (int) (x - mTouchStartX);
        //是否存在状态栏（提升滑动效果）
        // 不设置为全屏（状态栏存在） 标题栏是屏幕的1/25
//        LogUtil.i(screenHeight / 25 + " screenHeight");
//        LogUtil.i(CommonUtils.getStatusBarHeight() + "  CommonUtils.getStatusBarHeight()");
        wmParams.y = (int) (y - mTouchStartY - CommonUtils.getStatusBarHeight());
        LogUtil.i((x - mTouchStartX) + " --->mTouchStartX");
        LogUtil.i((y - mTouchStartY) + " --->mTouchStartY");
        wm.updateViewLayout(this, wmParams);
    }

}
