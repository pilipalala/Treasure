package com.wyj.treasure.utils;

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

    public static int dip2px(float dpValue) {
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics());
        return (int) (dpValue * getDensity() + 0.5F);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / getDensity() + 0.5F);
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