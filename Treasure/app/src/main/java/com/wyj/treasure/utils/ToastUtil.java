package com.wyj.treasure.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by wangyujie
 * Date 2017/8/2
 * Time 21:41
 * TODO Toast工具类
 */

public class ToastUtil {
    private static Toast toast;

    public static Toast show(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }

    public static Toast show(Context context, int text) {
        return show(context, String.valueOf(Double.valueOf(text)));
    }

}
