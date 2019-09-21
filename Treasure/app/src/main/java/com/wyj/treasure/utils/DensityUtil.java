package com.wyj.treasure.utils;

import android.util.TypedValue;

import com.wyj.treasure.MyApplication;


public final class DensityUtil {

    private static float density = -1F;
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    private DensityUtil() {
    }

    public static float getDensity() {
        if (density <= 0F) {
            density = MyApplication.getContext().getResources().getDisplayMetrics().density;
        }
        return density;
    }

    /**
     * dip 转 px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics());
        return (int) (dpValue * getDensity() + 0.5F);
    }

    /**
     * 系统也提供了TypedValue类帮助我们转换
     * convert dp to its equivalent px
     */
    protected int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,MyApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * 系统也提供了TypedValue类帮助我们转换
     * convert sp to its equivalent px
     */
    protected int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,MyApplication.getContext().getResources().getDisplayMetrics());
    }
    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / getDensity() + 0.5F);
    }

    /**
     * px转sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getScreenWidth() {
        if (widthPixels <= 0) {
            widthPixels = MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    public static int getScreenHeight() {
        if (heightPixels <= 0) {
            heightPixels = MyApplication.getContext().getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }
}
