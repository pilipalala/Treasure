package com.wyj.treasure.permission;

import android.os.Build;

import com.wyj.treasure.utils.LogUtil;

import java.lang.reflect.Method;

/**
 * @author wangyujie
 *         on 2017/11/14.14:17
 *         TODO
 */

public class PermissionUtils {

    /*这个类里面所有的都是静态方法*/

    private PermissionUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @return 是否是6.0版本
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.M;
    }

    /**
     * @param reflectClass 
     * @param requestCode
     */
    public static void executeSuccessMethod(Class<?> reflectClass, int requestCode) {
        /**
         * 获取class中所有方法  ---  遍历 --- 找到我们打了标记的方法 --- 并且请求码一致
         * */
        Method[] methods = reflectClass.getDeclaredMethods();
        //遍历
        for (Method method : methods) {
            LogUtil.e(method.getName());
            //获取该方法上面有没有打这个成功的标记
            PermissionSuccess successMethed = method.getAnnotation(PermissionSuccess.class);
            if (successMethed != null) {
                //代表该方法打了标记
                //并且我们的请求码必须和requestCode一样
                int methedCode = successMethed.requestCode();
                if (methedCode == requestCode) {

                }
            }
        }

    }
}
