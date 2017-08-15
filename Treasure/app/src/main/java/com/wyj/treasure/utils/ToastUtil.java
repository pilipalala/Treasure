package com.wyj.treasure.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.wyj.treasure.MyApplication;

/**
 * Created by wangyujie
 * Date 2017/8/2
 * Time 21:41
 * TODO Toast工具类
 */

public class ToastUtil {
    private static Toast toast;

    public static Toast show(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(text);
        toast.show();
        return toast;
    }

    public static Toast show( int text) {
        return show( String.valueOf(Double.valueOf(text)));
    }

}
